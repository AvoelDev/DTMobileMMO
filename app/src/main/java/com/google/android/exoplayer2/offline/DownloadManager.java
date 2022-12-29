package com.google.android.exoplayer2.offline;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes.dex */
public final class DownloadManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_MAX_SIMULTANEOUS_DOWNLOADS = 1;
    public static final int DEFAULT_MIN_RETRY_COUNT = 5;
    private static final String TAG = "DownloadManager";
    private final ActionFile actionFile;
    private final ArrayList<Task> activeDownloadTasks;
    private final DownloadAction.Deserializer[] deserializers;
    private final DownloaderConstructorHelper downloaderConstructorHelper;
    private boolean downloadsStopped;
    private final Handler fileIOHandler;
    private final HandlerThread fileIOThread;
    private final Handler handler;
    private boolean initialized;
    private final CopyOnWriteArraySet<Listener> listeners;
    private final int maxActiveDownloadTasks;
    private final int minRetryCount;
    private int nextTaskId;
    private boolean released;
    private final ArrayList<Task> tasks;

    /* loaded from: classes.dex */
    public interface Listener {
        void onIdle(DownloadManager downloadManager);

        void onInitialized(DownloadManager downloadManager);

        void onTaskStateChanged(DownloadManager downloadManager, TaskState taskState);
    }

    public DownloadManager(Cache cache, DataSource.Factory upstreamDataSourceFactory, File actionSaveFile, DownloadAction.Deserializer... deserializers) {
        this(new DownloaderConstructorHelper(cache, upstreamDataSourceFactory), actionSaveFile, deserializers);
    }

    public DownloadManager(DownloaderConstructorHelper constructorHelper, File actionFile, DownloadAction.Deserializer... deserializers) {
        this(constructorHelper, 1, 5, actionFile, deserializers);
    }

    public DownloadManager(DownloaderConstructorHelper constructorHelper, int maxSimultaneousDownloads, int minRetryCount, File actionFile, DownloadAction.Deserializer... deserializers) {
        Assertions.checkArgument(deserializers.length > 0, "At least one Deserializer is required.");
        this.downloaderConstructorHelper = constructorHelper;
        this.maxActiveDownloadTasks = maxSimultaneousDownloads;
        this.minRetryCount = minRetryCount;
        this.actionFile = new ActionFile(actionFile);
        this.deserializers = deserializers;
        this.downloadsStopped = true;
        this.tasks = new ArrayList<>();
        this.activeDownloadTasks = new ArrayList<>();
        Looper looper = Looper.myLooper();
        this.handler = new Handler(looper == null ? Looper.getMainLooper() : looper);
        this.fileIOThread = new HandlerThread("DownloadManager file i/o");
        this.fileIOThread.start();
        this.fileIOHandler = new Handler(this.fileIOThread.getLooper());
        this.listeners = new CopyOnWriteArraySet<>();
        loadActions();
        logd("Created");
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public void startDownloads() {
        Assertions.checkState(!this.released);
        if (this.downloadsStopped) {
            this.downloadsStopped = false;
            maybeStartTasks();
            logd("Downloads are started");
        }
    }

    public void stopDownloads() {
        Assertions.checkState(!this.released);
        if (!this.downloadsStopped) {
            this.downloadsStopped = true;
            for (int i = 0; i < this.activeDownloadTasks.size(); i++) {
                this.activeDownloadTasks.get(i).stop();
            }
            logd("Downloads are stopping");
        }
    }

    public int handleAction(byte[] actionData) throws IOException {
        Assertions.checkState(!this.released);
        ByteArrayInputStream input = new ByteArrayInputStream(actionData);
        DownloadAction action = DownloadAction.deserializeFromStream(this.deserializers, input);
        return handleAction(action);
    }

    public int handleAction(DownloadAction action) {
        Assertions.checkState(!this.released);
        Task task = addTaskForAction(action);
        if (this.initialized) {
            saveActions();
            maybeStartTasks();
            if (task.currentState == 0) {
                notifyListenersTaskStateChange(task);
            }
        }
        return task.id;
    }

    public int getTaskCount() {
        Assertions.checkState(!this.released);
        return this.tasks.size();
    }

    public int getDownloadCount() {
        int count = 0;
        for (int i = 0; i < this.tasks.size(); i++) {
            if (!this.tasks.get(i).action.isRemoveAction) {
                count++;
            }
        }
        return count;
    }

    @Nullable
    public TaskState getTaskState(int taskId) {
        Assertions.checkState(!this.released);
        for (int i = 0; i < this.tasks.size(); i++) {
            Task task = this.tasks.get(i);
            if (task.id == taskId) {
                return task.getDownloadState();
            }
        }
        return null;
    }

    public TaskState[] getAllTaskStates() {
        Assertions.checkState(!this.released);
        TaskState[] states = new TaskState[this.tasks.size()];
        for (int i = 0; i < states.length; i++) {
            states[i] = this.tasks.get(i).getDownloadState();
        }
        return states;
    }

    public boolean isInitialized() {
        Assertions.checkState(!this.released);
        return this.initialized;
    }

    public boolean isIdle() {
        Assertions.checkState(!this.released);
        if (this.initialized) {
            for (int i = 0; i < this.tasks.size(); i++) {
                if (this.tasks.get(i).isActive()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void release() {
        if (!this.released) {
            this.released = true;
            for (int i = 0; i < this.tasks.size(); i++) {
                this.tasks.get(i).stop();
            }
            final ConditionVariable fileIOFinishedCondition = new ConditionVariable();
            this.fileIOHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.offline.DownloadManager.1
                @Override // java.lang.Runnable
                public void run() {
                    fileIOFinishedCondition.open();
                }
            });
            fileIOFinishedCondition.block();
            this.fileIOThread.quit();
            logd("Released");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Task addTaskForAction(DownloadAction action) {
        int i = this.nextTaskId;
        this.nextTaskId = i + 1;
        Task task = new Task(i, this, action, this.minRetryCount);
        this.tasks.add(task);
        logd("Task is added", task);
        return task;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeStartTasks() {
        DownloadAction action;
        boolean isRemoveAction;
        if (this.initialized && !this.released) {
            boolean skipDownloadActions = this.downloadsStopped || this.activeDownloadTasks.size() == this.maxActiveDownloadTasks;
            for (int i = 0; i < this.tasks.size(); i++) {
                Task task = this.tasks.get(i);
                if (task.canStart() && ((isRemoveAction = (action = task.action).isRemoveAction) || !skipDownloadActions)) {
                    boolean canStartTask = true;
                    int j = 0;
                    while (true) {
                        if (j >= i) {
                            break;
                        }
                        Task otherTask = this.tasks.get(j);
                        if (otherTask.action.isSameMedia(action)) {
                            if (isRemoveAction) {
                                canStartTask = false;
                                logd(task + " clashes with " + otherTask);
                                otherTask.cancel();
                            } else if (otherTask.action.isRemoveAction) {
                                canStartTask = false;
                                skipDownloadActions = true;
                                break;
                            }
                        }
                        j++;
                    }
                    if (canStartTask) {
                        task.start();
                        if (!isRemoveAction) {
                            this.activeDownloadTasks.add(task);
                            skipDownloadActions = this.activeDownloadTasks.size() == this.maxActiveDownloadTasks;
                        }
                    }
                }
            }
        }
    }

    private void maybeNotifyListenersIdle() {
        if (isIdle()) {
            logd("Notify idle state");
            Iterator<Listener> it = this.listeners.iterator();
            while (it.hasNext()) {
                Listener listener = it.next();
                listener.onIdle(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTaskStateChange(Task task) {
        if (!this.released) {
            boolean stopped = !task.isActive();
            if (stopped) {
                this.activeDownloadTasks.remove(task);
            }
            notifyListenersTaskStateChange(task);
            if (task.isFinished()) {
                this.tasks.remove(task);
                saveActions();
            }
            if (stopped) {
                maybeStartTasks();
                maybeNotifyListenersIdle();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyListenersTaskStateChange(Task task) {
        logd("Task state is changed", task);
        TaskState taskState = task.getDownloadState();
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            Listener listener = it.next();
            listener.onTaskStateChanged(this, taskState);
        }
    }

    private void loadActions() {
        this.fileIOHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.offline.DownloadManager.2
            @Override // java.lang.Runnable
            public void run() {
                DownloadAction[] loadedActions;
                try {
                    loadedActions = DownloadManager.this.actionFile.load(DownloadManager.this.deserializers);
                    DownloadManager.logd("Action file is loaded.");
                } catch (Throwable e) {
                    Log.e(DownloadManager.TAG, "Action file loading failed.", e);
                    loadedActions = new DownloadAction[0];
                }
                final DownloadAction[] actions = loadedActions;
                DownloadManager.this.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.offline.DownloadManager.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DownloadAction[] downloadActionArr;
                        if (!DownloadManager.this.released) {
                            ArrayList arrayList = new ArrayList(DownloadManager.this.tasks);
                            DownloadManager.this.tasks.clear();
                            for (DownloadAction action : actions) {
                                DownloadManager.this.addTaskForAction(action);
                            }
                            DownloadManager.logd("Tasks are created.");
                            DownloadManager.this.initialized = true;
                            Iterator it = DownloadManager.this.listeners.iterator();
                            while (it.hasNext()) {
                                Listener listener = (Listener) it.next();
                                listener.onInitialized(DownloadManager.this);
                            }
                            if (!arrayList.isEmpty()) {
                                DownloadManager.this.tasks.addAll(arrayList);
                                DownloadManager.this.saveActions();
                            }
                            DownloadManager.this.maybeStartTasks();
                            for (int i = 0; i < DownloadManager.this.tasks.size(); i++) {
                                Task task = (Task) DownloadManager.this.tasks.get(i);
                                if (task.currentState == 0) {
                                    DownloadManager.this.notifyListenersTaskStateChange(task);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveActions() {
        if (!this.released) {
            final DownloadAction[] actions = new DownloadAction[this.tasks.size()];
            for (int i = 0; i < this.tasks.size(); i++) {
                actions[i] = this.tasks.get(i).action;
            }
            this.fileIOHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.offline.DownloadManager.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        DownloadManager.this.actionFile.store(actions);
                        DownloadManager.logd("Actions persisted.");
                    } catch (IOException e) {
                        Log.e(DownloadManager.TAG, "Persisting actions failed.", e);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logd(String message) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logd(String message, Task task) {
        logd(message + ": " + task);
    }

    /* loaded from: classes.dex */
    public static final class TaskState {
        public static final int STATE_CANCELED = 3;
        public static final int STATE_COMPLETED = 2;
        public static final int STATE_FAILED = 4;
        public static final int STATE_QUEUED = 0;
        public static final int STATE_STARTED = 1;
        public final DownloadAction action;
        public final float downloadPercentage;
        public final long downloadedBytes;
        public final Throwable error;
        public final int state;
        public final int taskId;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes.dex */
        public @interface State {
        }

        public static String getStateString(int state) {
            switch (state) {
                case 0:
                    return "QUEUED";
                case 1:
                    return "STARTED";
                case 2:
                    return "COMPLETED";
                case 3:
                    return "CANCELED";
                case 4:
                    return "FAILED";
                default:
                    throw new IllegalStateException();
            }
        }

        private TaskState(int taskId, DownloadAction action, int state, float downloadPercentage, long downloadedBytes, Throwable error) {
            this.taskId = taskId;
            this.action = action;
            this.state = state;
            this.downloadPercentage = downloadPercentage;
            this.downloadedBytes = downloadedBytes;
            this.error = error;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Task implements Runnable {
        public static final int STATE_QUEUED_CANCELING = 5;
        public static final int STATE_STARTED_CANCELING = 6;
        public static final int STATE_STARTED_STOPPING = 7;
        private final DownloadAction action;
        private volatile int currentState;
        private final DownloadManager downloadManager;
        private volatile Downloader downloader;
        private Throwable error;
        private final int id;
        private final int minRetryCount;
        private Thread thread;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes.dex */
        public @interface InternalState {
        }

        private Task(int id, DownloadManager downloadManager, DownloadAction action, int minRetryCount) {
            this.id = id;
            this.downloadManager = downloadManager;
            this.action = action;
            this.currentState = 0;
            this.minRetryCount = minRetryCount;
        }

        public TaskState getDownloadState() {
            int externalState = getExternalState();
            return new TaskState(this.id, this.action, externalState, getDownloadPercentage(), getDownloadedBytes(), this.error);
        }

        public boolean isFinished() {
            return this.currentState == 4 || this.currentState == 2 || this.currentState == 3;
        }

        public boolean isActive() {
            return this.currentState == 5 || this.currentState == 1 || this.currentState == 7 || this.currentState == 6;
        }

        public float getDownloadPercentage() {
            if (this.downloader != null) {
                return this.downloader.getDownloadPercentage();
            }
            return -1.0f;
        }

        public long getDownloadedBytes() {
            if (this.downloader != null) {
                return this.downloader.getDownloadedBytes();
            }
            return 0L;
        }

        public String toString() {
            return super.toString();
        }

        private static String toString(byte[] data) {
            return data.length > 100 ? "<data is too long>" : '\'' + Util.fromUtf8Bytes(data) + '\'';
        }

        private String getStateString() {
            switch (this.currentState) {
                case 5:
                case 6:
                    return "CANCELING";
                case 7:
                    return "STOPPING";
                default:
                    return TaskState.getStateString(this.currentState);
            }
        }

        private int getExternalState() {
            switch (this.currentState) {
                case 5:
                    return 0;
                case 6:
                case 7:
                    return 1;
                default:
                    return this.currentState;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void start() {
            if (changeStateAndNotify(0, 1)) {
                this.thread = new Thread(this);
                this.thread.start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean canStart() {
            return this.currentState == 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cancel() {
            if (changeStateAndNotify(0, 5)) {
                this.downloadManager.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.offline.DownloadManager.Task.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Task.this.changeStateAndNotify(5, 3);
                    }
                });
            } else if (changeStateAndNotify(1, 6)) {
                cancelDownload();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void stop() {
            if (changeStateAndNotify(1, 7)) {
                DownloadManager.logd("Stopping", this);
                this.thread.interrupt();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean changeStateAndNotify(int oldState, int newState) {
            return changeStateAndNotify(oldState, newState, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean changeStateAndNotify(int oldState, int newState, Throwable error) {
            if (this.currentState != oldState) {
                return false;
            }
            this.currentState = newState;
            this.error = error;
            boolean isInternalState = this.currentState != getExternalState();
            if (!isInternalState) {
                this.downloadManager.onTaskStateChange(this);
            }
            return true;
        }

        private void cancelDownload() {
            if (this.downloader != null) {
                this.downloader.cancel();
            }
            this.thread.interrupt();
        }

        @Override // java.lang.Runnable
        public void run() {
            DownloadManager.logd("Task is started", this);
            Throwable error = null;
            try {
                this.downloader = this.action.createDownloader(this.downloadManager.downloaderConstructorHelper);
                if (this.action.isRemoveAction) {
                    this.downloader.remove();
                } else {
                    int errorCount = 0;
                    long errorPosition = -1;
                    while (!Thread.interrupted()) {
                        try {
                            this.downloader.download();
                            break;
                        } catch (IOException e) {
                            long downloadedBytes = this.downloader.getDownloadedBytes();
                            if (downloadedBytes != errorPosition) {
                                DownloadManager.logd("Reset error count. downloadedBytes = " + downloadedBytes, this);
                                errorPosition = downloadedBytes;
                                errorCount = 0;
                            }
                            if (this.currentState == 1 && (errorCount = errorCount + 1) <= this.minRetryCount) {
                                DownloadManager.logd("Download error. Retry " + errorCount, this);
                                Thread.sleep(getRetryDelayMillis(errorCount));
                            } else {
                                throw e;
                            }
                        }
                    }
                }
            } catch (Throwable e2) {
                error = e2;
            }
            final Throwable finalError = error;
            this.downloadManager.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.offline.DownloadManager.Task.2
                @Override // java.lang.Runnable
                public void run() {
                    if (Task.this.changeStateAndNotify(1, finalError != null ? 4 : 2, finalError) || Task.this.changeStateAndNotify(6, 3) || Task.this.changeStateAndNotify(7, 0)) {
                        return;
                    }
                    throw new IllegalStateException();
                }
            });
        }

        private int getRetryDelayMillis(int errorCount) {
            return Math.min((errorCount - 1) * 1000, 5000);
        }
    }
}
