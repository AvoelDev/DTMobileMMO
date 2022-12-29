package com.google.android.exoplayer2.offline;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.RequirementsWatcher;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import java.util.HashMap;

/* loaded from: classes.dex */
public abstract class DownloadService extends Service {
    public static final String ACTION_ADD = "com.google.android.exoplayer.downloadService.action.ADD";
    public static final String ACTION_INIT = "com.google.android.exoplayer.downloadService.action.INIT";
    private static final String ACTION_RESTART = "com.google.android.exoplayer.downloadService.action.RESTART";
    private static final String ACTION_START_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.START_DOWNLOADS";
    private static final String ACTION_STOP_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.STOP_DOWNLOADS";
    private static final boolean DEBUG = false;
    public static final long DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL = 1000;
    public static final String KEY_DOWNLOAD_ACTION = "download_action";
    public static final String KEY_FOREGROUND = "foreground";
    private static final String TAG = "DownloadService";
    private static final HashMap<Class<? extends DownloadService>, RequirementsHelper> requirementsHelpers = new HashMap<>();
    @Nullable
    private final String channelId;
    @StringRes
    private final int channelName;
    private DownloadManager downloadManager;
    private DownloadManagerListener downloadManagerListener;
    private final ForegroundNotificationUpdater foregroundNotificationUpdater;
    private int lastStartId;
    private boolean startedInForeground;
    private boolean taskRemoved;

    protected abstract DownloadManager getDownloadManager();

    protected abstract Notification getForegroundNotification(DownloadManager.TaskState[] taskStateArr);

    @Nullable
    protected abstract Scheduler getScheduler();

    protected DownloadService(int foregroundNotificationId) {
        this(foregroundNotificationId, 1000L);
    }

    protected DownloadService(int foregroundNotificationId, long foregroundNotificationUpdateInterval) {
        this(foregroundNotificationId, foregroundNotificationUpdateInterval, null, 0);
    }

    protected DownloadService(int foregroundNotificationId, long foregroundNotificationUpdateInterval, @Nullable String channelId, @StringRes int channelName) {
        this.foregroundNotificationUpdater = new ForegroundNotificationUpdater(foregroundNotificationId, foregroundNotificationUpdateInterval);
        this.channelId = channelId;
        this.channelName = channelName;
    }

    public static Intent buildAddActionIntent(Context context, Class<? extends DownloadService> clazz, DownloadAction downloadAction, boolean foreground) {
        return new Intent(context, clazz).setAction(ACTION_ADD).putExtra(KEY_DOWNLOAD_ACTION, downloadAction.toByteArray()).putExtra(KEY_FOREGROUND, foreground);
    }

    public static void startWithAction(Context context, Class<? extends DownloadService> clazz, DownloadAction downloadAction, boolean foreground) {
        Intent intent = buildAddActionIntent(context, clazz, downloadAction, foreground);
        if (foreground) {
            Util.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }

    public static void start(Context context, Class<? extends DownloadService> clazz) {
        context.startService(new Intent(context, clazz).setAction(ACTION_INIT));
    }

    public static void startForeground(Context context, Class<? extends DownloadService> clazz) {
        Intent intent = new Intent(context, clazz).setAction(ACTION_INIT).putExtra(KEY_FOREGROUND, true);
        Util.startForegroundService(context, intent);
    }

    @Override // android.app.Service
    public void onCreate() {
        logd("onCreate");
        if (this.channelId != null) {
            NotificationUtil.createNotificationChannel(this, this.channelId, this.channelName, 2);
        }
        this.downloadManager = getDownloadManager();
        this.downloadManagerListener = new DownloadManagerListener();
        this.downloadManager.addListener(this.downloadManagerListener);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0082, code lost:
        if (r2.equals(com.google.android.exoplayer2.offline.DownloadService.ACTION_INIT) != false) goto L15;
     */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int onStartCommand(android.content.Intent r8, int r9, int r10) {
        /*
            r7 = this;
            r5 = 1
            r4 = 0
            r7.lastStartId = r10
            r7.taskRemoved = r4
            r2 = 0
            if (r8 == 0) goto L23
            java.lang.String r2 = r8.getAction()
            boolean r6 = r7.startedInForeground
            java.lang.String r3 = "foreground"
            boolean r3 = r8.getBooleanExtra(r3, r4)
            if (r3 != 0) goto L1f
            java.lang.String r3 = "com.google.android.exoplayer.downloadService.action.RESTART"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L7a
        L1f:
            r3 = r5
        L20:
            r3 = r3 | r6
            r7.startedInForeground = r3
        L23:
            if (r2 != 0) goto L27
            java.lang.String r2 = "com.google.android.exoplayer.downloadService.action.INIT"
        L27:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = "onStartCommand action: "
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r6 = " startId: "
            java.lang.StringBuilder r3 = r3.append(r6)
            java.lang.StringBuilder r3 = r3.append(r10)
            java.lang.String r3 = r3.toString()
            r7.logd(r3)
            r3 = -1
            int r6 = r2.hashCode()
            switch(r6) {
                case -871181424: goto L85;
                case -382886238: goto L8f;
                case -337334865: goto La3;
                case 1015676687: goto L7c;
                case 1286088717: goto L99;
                default: goto L4f;
            }
        L4f:
            r4 = r3
        L50:
            switch(r4) {
                case 0: goto L6b;
                case 1: goto L6b;
                case 2: goto Lad;
                case 3: goto Lcc;
                case 4: goto Ld2;
                default: goto L53;
            }
        L53:
            java.lang.String r3 = "DownloadService"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Ignoring unrecognized action: "
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.StringBuilder r4 = r4.append(r2)
            java.lang.String r4 = r4.toString()
            android.util.Log.e(r3, r4)
        L6b:
            r7.maybeStartWatchingRequirements()
            com.google.android.exoplayer2.offline.DownloadManager r3 = r7.downloadManager
            boolean r3 = r3.isIdle()
            if (r3 == 0) goto L79
            r7.stop()
        L79:
            return r5
        L7a:
            r3 = r4
            goto L20
        L7c:
            java.lang.String r6 = "com.google.android.exoplayer.downloadService.action.INIT"
            boolean r6 = r2.equals(r6)
            if (r6 == 0) goto L4f
            goto L50
        L85:
            java.lang.String r4 = "com.google.android.exoplayer.downloadService.action.RESTART"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L4f
            r4 = r5
            goto L50
        L8f:
            java.lang.String r4 = "com.google.android.exoplayer.downloadService.action.ADD"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L4f
            r4 = 2
            goto L50
        L99:
            java.lang.String r4 = "com.google.android.exoplayer.downloadService.action.STOP_DOWNLOADS"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L4f
            r4 = 3
            goto L50
        La3:
            java.lang.String r4 = "com.google.android.exoplayer.downloadService.action.START_DOWNLOADS"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L4f
            r4 = 4
            goto L50
        Lad:
            java.lang.String r3 = "download_action"
            byte[] r0 = r8.getByteArrayExtra(r3)
            if (r0 != 0) goto Lbd
            java.lang.String r3 = "DownloadService"
            java.lang.String r4 = "Ignoring ADD action with no action data"
            android.util.Log.e(r3, r4)
            goto L6b
        Lbd:
            com.google.android.exoplayer2.offline.DownloadManager r3 = r7.downloadManager     // Catch: java.io.IOException -> Lc3
            r3.handleAction(r0)     // Catch: java.io.IOException -> Lc3
            goto L6b
        Lc3:
            r1 = move-exception
            java.lang.String r3 = "DownloadService"
            java.lang.String r4 = "Failed to handle ADD action"
            android.util.Log.e(r3, r4, r1)
            goto L6b
        Lcc:
            com.google.android.exoplayer2.offline.DownloadManager r3 = r7.downloadManager
            r3.stopDownloads()
            goto L6b
        Ld2:
            com.google.android.exoplayer2.offline.DownloadManager r3 = r7.downloadManager
            r3.startDownloads()
            goto L6b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadService.onStartCommand(android.content.Intent, int, int):int");
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent rootIntent) {
        logd("onTaskRemoved rootIntent: " + rootIntent);
        this.taskRemoved = true;
    }

    @Override // android.app.Service
    public void onDestroy() {
        logd("onDestroy");
        this.foregroundNotificationUpdater.stopPeriodicUpdates();
        this.downloadManager.removeListener(this.downloadManagerListener);
        maybeStopWatchingRequirements();
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected Requirements getRequirements() {
        return new Requirements(1, false, false);
    }

    protected void onTaskStateChanged(DownloadManager.TaskState taskState) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void maybeStartWatchingRequirements() {
        if (this.downloadManager.getDownloadCount() != 0) {
            Class<?> cls = getClass();
            if (requirementsHelpers.get(cls) == null) {
                RequirementsHelper requirementsHelper = new RequirementsHelper(this, getRequirements(), getScheduler(), cls);
                requirementsHelpers.put(cls, requirementsHelper);
                requirementsHelper.start();
                logd("started watching requirements");
            }
        }
    }

    private void maybeStopWatchingRequirements() {
        RequirementsHelper requirementsHelper;
        if (this.downloadManager.getDownloadCount() <= 0 && (requirementsHelper = requirementsHelpers.remove(getClass())) != null) {
            requirementsHelper.stop();
            logd("stopped watching requirements");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stop() {
        this.foregroundNotificationUpdater.stopPeriodicUpdates();
        if (this.startedInForeground && Util.SDK_INT >= 26) {
            this.foregroundNotificationUpdater.showNotificationIfNotAlready();
        }
        if (Util.SDK_INT < 28 && this.taskRemoved) {
            stopSelf();
            logd("stopSelf()");
            return;
        }
        boolean stopSelfResult = stopSelfResult(this.lastStartId);
        logd("stopSelf(" + this.lastStartId + ") result: " + stopSelfResult);
    }

    private void logd(String message) {
    }

    /* loaded from: classes.dex */
    private final class DownloadManagerListener implements DownloadManager.Listener {
        private DownloadManagerListener() {
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onInitialized(DownloadManager downloadManager) {
            DownloadService.this.maybeStartWatchingRequirements();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onTaskStateChanged(DownloadManager downloadManager, DownloadManager.TaskState taskState) {
            DownloadService.this.onTaskStateChanged(taskState);
            if (taskState.state == 1) {
                DownloadService.this.foregroundNotificationUpdater.startPeriodicUpdates();
            } else {
                DownloadService.this.foregroundNotificationUpdater.update();
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public final void onIdle(DownloadManager downloadManager) {
            DownloadService.this.stop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class ForegroundNotificationUpdater implements Runnable {
        private final Handler handler = new Handler(Looper.getMainLooper());
        private boolean notificationDisplayed;
        private final int notificationId;
        private boolean periodicUpdatesStarted;
        private final long updateInterval;

        public ForegroundNotificationUpdater(int notificationId, long updateInterval) {
            this.notificationId = notificationId;
            this.updateInterval = updateInterval;
        }

        public void startPeriodicUpdates() {
            this.periodicUpdatesStarted = true;
            update();
        }

        public void stopPeriodicUpdates() {
            this.periodicUpdatesStarted = false;
            this.handler.removeCallbacks(this);
        }

        public void update() {
            DownloadManager.TaskState[] taskStates = DownloadService.this.downloadManager.getAllTaskStates();
            DownloadService.this.startForeground(this.notificationId, DownloadService.this.getForegroundNotification(taskStates));
            this.notificationDisplayed = true;
            if (this.periodicUpdatesStarted) {
                this.handler.removeCallbacks(this);
                this.handler.postDelayed(this, this.updateInterval);
            }
        }

        public void showNotificationIfNotAlready() {
            if (!this.notificationDisplayed) {
                update();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            update();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class RequirementsHelper implements RequirementsWatcher.Listener {
        private final Context context;
        private final Requirements requirements;
        private final RequirementsWatcher requirementsWatcher;
        @Nullable
        private final Scheduler scheduler;
        private final Class<? extends DownloadService> serviceClass;

        private RequirementsHelper(Context context, Requirements requirements, @Nullable Scheduler scheduler, Class<? extends DownloadService> serviceClass) {
            this.context = context;
            this.requirements = requirements;
            this.scheduler = scheduler;
            this.serviceClass = serviceClass;
            this.requirementsWatcher = new RequirementsWatcher(context, this, requirements);
        }

        public void start() {
            this.requirementsWatcher.start();
        }

        public void stop() {
            this.requirementsWatcher.stop();
            if (this.scheduler != null) {
                this.scheduler.cancel();
            }
        }

        @Override // com.google.android.exoplayer2.scheduler.RequirementsWatcher.Listener
        public void requirementsMet(RequirementsWatcher requirementsWatcher) {
            startServiceWithAction(DownloadService.ACTION_START_DOWNLOADS);
            if (this.scheduler != null) {
                this.scheduler.cancel();
            }
        }

        @Override // com.google.android.exoplayer2.scheduler.RequirementsWatcher.Listener
        public void requirementsNotMet(RequirementsWatcher requirementsWatcher) {
            startServiceWithAction(DownloadService.ACTION_STOP_DOWNLOADS);
            if (this.scheduler != null) {
                String servicePackage = this.context.getPackageName();
                boolean success = this.scheduler.schedule(this.requirements, servicePackage, DownloadService.ACTION_RESTART);
                if (!success) {
                    Log.e(DownloadService.TAG, "Scheduling downloads failed.");
                }
            }
        }

        private void startServiceWithAction(String action) {
            Intent intent = new Intent(this.context, this.serviceClass).setAction(action).putExtra(DownloadService.KEY_FOREGROUND, true);
            Util.startForegroundService(this.context, intent);
        }
    }
}
