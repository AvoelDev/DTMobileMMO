package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ShuffleOrder;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ConcatenatingMediaSource extends CompositeMediaSource<MediaSourceHolder> implements PlayerMessage.Target {
    private static final int MSG_ADD = 0;
    private static final int MSG_ADD_MULTIPLE = 1;
    private static final int MSG_CLEAR = 4;
    private static final int MSG_MOVE = 3;
    private static final int MSG_NOTIFY_LISTENER = 5;
    private static final int MSG_ON_COMPLETION = 6;
    private static final int MSG_REMOVE = 2;
    private final boolean isAtomic;
    private boolean listenerNotificationScheduled;
    private final Map<MediaPeriod, MediaSourceHolder> mediaSourceByMediaPeriod;
    private final List<MediaSourceHolder> mediaSourceHolders;
    private final List<MediaSourceHolder> mediaSourcesPublic;
    private final List<EventDispatcher> pendingOnCompletionActions;
    private int periodCount;
    private ExoPlayer player;
    private final MediaSourceHolder query;
    private ShuffleOrder shuffleOrder;
    private final Timeline.Window window;
    private int windowCount;

    public ConcatenatingMediaSource() {
        this(false, (ShuffleOrder) new ShuffleOrder.DefaultShuffleOrder(0));
    }

    public ConcatenatingMediaSource(boolean isAtomic) {
        this(isAtomic, new ShuffleOrder.DefaultShuffleOrder(0));
    }

    public ConcatenatingMediaSource(boolean isAtomic, ShuffleOrder shuffleOrder) {
        this(isAtomic, shuffleOrder, new MediaSource[0]);
    }

    public ConcatenatingMediaSource(MediaSource... mediaSources) {
        this(false, mediaSources);
    }

    public ConcatenatingMediaSource(boolean isAtomic, MediaSource... mediaSources) {
        this(isAtomic, new ShuffleOrder.DefaultShuffleOrder(0), mediaSources);
    }

    public ConcatenatingMediaSource(boolean isAtomic, ShuffleOrder shuffleOrder, MediaSource... mediaSources) {
        for (MediaSource mediaSource : mediaSources) {
            Assertions.checkNotNull(mediaSource);
        }
        this.shuffleOrder = shuffleOrder.getLength() > 0 ? shuffleOrder.cloneAndClear() : shuffleOrder;
        this.mediaSourceByMediaPeriod = new IdentityHashMap();
        this.mediaSourcesPublic = new ArrayList();
        this.mediaSourceHolders = new ArrayList();
        this.pendingOnCompletionActions = new ArrayList();
        this.query = new MediaSourceHolder(null);
        this.isAtomic = isAtomic;
        this.window = new Timeline.Window();
        addMediaSources(Arrays.asList(mediaSources));
    }

    public final synchronized void addMediaSource(MediaSource mediaSource) {
        addMediaSource(this.mediaSourcesPublic.size(), mediaSource, null);
    }

    public final synchronized void addMediaSource(MediaSource mediaSource, @Nullable Runnable actionOnCompletion) {
        addMediaSource(this.mediaSourcesPublic.size(), mediaSource, actionOnCompletion);
    }

    public final synchronized void addMediaSource(int index, MediaSource mediaSource) {
        addMediaSource(index, mediaSource, null);
    }

    public final synchronized void addMediaSource(int index, MediaSource mediaSource, @Nullable Runnable actionOnCompletion) {
        Assertions.checkNotNull(mediaSource);
        MediaSourceHolder mediaSourceHolder = new MediaSourceHolder(mediaSource);
        this.mediaSourcesPublic.add(index, mediaSourceHolder);
        if (this.player != null) {
            this.player.createMessage(this).setType(0).setPayload(new MessageData(index, mediaSourceHolder, actionOnCompletion)).send();
        } else if (actionOnCompletion != null) {
            actionOnCompletion.run();
        }
    }

    public final synchronized void addMediaSources(Collection<MediaSource> mediaSources) {
        addMediaSources(this.mediaSourcesPublic.size(), mediaSources, null);
    }

    public final synchronized void addMediaSources(Collection<MediaSource> mediaSources, @Nullable Runnable actionOnCompletion) {
        addMediaSources(this.mediaSourcesPublic.size(), mediaSources, actionOnCompletion);
    }

    public final synchronized void addMediaSources(int index, Collection<MediaSource> mediaSources) {
        addMediaSources(index, mediaSources, null);
    }

    public final synchronized void addMediaSources(int index, Collection<MediaSource> mediaSources, @Nullable Runnable actionOnCompletion) {
        for (MediaSource mediaSource : mediaSources) {
            Assertions.checkNotNull(mediaSource);
        }
        List<MediaSourceHolder> mediaSourceHolders = new ArrayList<>(mediaSources.size());
        for (MediaSource mediaSource2 : mediaSources) {
            mediaSourceHolders.add(new MediaSourceHolder(mediaSource2));
        }
        this.mediaSourcesPublic.addAll(index, mediaSourceHolders);
        if (this.player != null && !mediaSources.isEmpty()) {
            this.player.createMessage(this).setType(1).setPayload(new MessageData(index, mediaSourceHolders, actionOnCompletion)).send();
        } else if (actionOnCompletion != null) {
            actionOnCompletion.run();
        }
    }

    public final synchronized void removeMediaSource(int index) {
        removeMediaSource(index, null);
    }

    public final synchronized void removeMediaSource(int index, @Nullable Runnable actionOnCompletion) {
        this.mediaSourcesPublic.remove(index);
        if (this.player != null) {
            this.player.createMessage(this).setType(2).setPayload(new MessageData(index, null, actionOnCompletion)).send();
        } else if (actionOnCompletion != null) {
            actionOnCompletion.run();
        }
    }

    public final synchronized void moveMediaSource(int currentIndex, int newIndex) {
        moveMediaSource(currentIndex, newIndex, null);
    }

    public final synchronized void moveMediaSource(int currentIndex, int newIndex, @Nullable Runnable actionOnCompletion) {
        if (currentIndex != newIndex) {
            this.mediaSourcesPublic.add(newIndex, this.mediaSourcesPublic.remove(currentIndex));
            if (this.player != null) {
                this.player.createMessage(this).setType(3).setPayload(new MessageData(currentIndex, Integer.valueOf(newIndex), actionOnCompletion)).send();
            } else if (actionOnCompletion != null) {
                actionOnCompletion.run();
            }
        }
    }

    public final synchronized void clear() {
        clear(null);
    }

    public final synchronized void clear(@Nullable Runnable actionOnCompletion) {
        this.mediaSourcesPublic.clear();
        if (this.player != null) {
            this.player.createMessage(this).setType(4).setPayload(actionOnCompletion != null ? new EventDispatcher(actionOnCompletion) : null).send();
        } else if (actionOnCompletion != null) {
            actionOnCompletion.run();
        }
    }

    public final synchronized int getSize() {
        return this.mediaSourcesPublic.size();
    }

    public final synchronized MediaSource getMediaSource(int index) {
        return this.mediaSourcesPublic.get(index).mediaSource;
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public final synchronized void prepareSourceInternal(ExoPlayer player, boolean isTopLevelSource) {
        super.prepareSourceInternal(player, isTopLevelSource);
        this.player = player;
        if (this.mediaSourcesPublic.isEmpty()) {
            notifyListener();
        } else {
            this.shuffleOrder = this.shuffleOrder.cloneAndInsert(0, this.mediaSourcesPublic.size());
            addMediaSourcesInternal(0, this.mediaSourcesPublic);
            scheduleListenerNotification(null);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final MediaPeriod createPeriod(MediaSource.MediaPeriodId id, Allocator allocator) {
        int mediaSourceHolderIndex = findMediaSourceHolderByPeriodIndex(id.periodIndex);
        MediaSourceHolder holder = this.mediaSourceHolders.get(mediaSourceHolderIndex);
        MediaSource.MediaPeriodId idInSource = id.copyWithPeriodIndex(id.periodIndex - holder.firstPeriodIndexInChild);
        DeferredMediaPeriod mediaPeriod = new DeferredMediaPeriod(holder.mediaSource, idInSource, allocator);
        this.mediaSourceByMediaPeriod.put(mediaPeriod, holder);
        holder.activeMediaPeriods.add(mediaPeriod);
        if (holder.isPrepared) {
            mediaPeriod.createPeriod();
        }
        return mediaPeriod;
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void releasePeriod(MediaPeriod mediaPeriod) {
        MediaSourceHolder holder = this.mediaSourceByMediaPeriod.remove(mediaPeriod);
        ((DeferredMediaPeriod) mediaPeriod).releasePeriod();
        holder.activeMediaPeriods.remove(mediaPeriod);
        if (holder.activeMediaPeriods.isEmpty() && holder.isRemoved) {
            releaseChildSource(holder);
        }
    }

    @Override // com.google.android.exoplayer2.source.CompositeMediaSource, com.google.android.exoplayer2.source.BaseMediaSource
    public final void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.mediaSourceHolders.clear();
        this.player = null;
        this.shuffleOrder = this.shuffleOrder.cloneAndClear();
        this.windowCount = 0;
        this.periodCount = 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    public final void onChildSourceInfoRefreshed(MediaSourceHolder mediaSourceHolder, MediaSource mediaSource, Timeline timeline, @Nullable Object manifest) {
        updateMediaSourceInternal(mediaSourceHolder, timeline);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    @Nullable
    public MediaSource.MediaPeriodId getMediaPeriodIdForChildMediaPeriodId(MediaSourceHolder mediaSourceHolder, MediaSource.MediaPeriodId mediaPeriodId) {
        for (int i = 0; i < mediaSourceHolder.activeMediaPeriods.size(); i++) {
            if (mediaSourceHolder.activeMediaPeriods.get(i).id.windowSequenceNumber == mediaPeriodId.windowSequenceNumber) {
                return mediaPeriodId.copyWithPeriodIndex(mediaPeriodId.periodIndex + mediaSourceHolder.firstPeriodIndexInChild);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.source.CompositeMediaSource
    public int getWindowIndexForChildWindowIndex(MediaSourceHolder mediaSourceHolder, int windowIndex) {
        return mediaSourceHolder.firstWindowIndexInChild + windowIndex;
    }

    @Override // com.google.android.exoplayer2.PlayerMessage.Target
    public final void handleMessage(int messageType, Object message) throws ExoPlaybackException {
        switch (messageType) {
            case 0:
                MessageData<MediaSourceHolder> addMessage = (MessageData) message;
                this.shuffleOrder = this.shuffleOrder.cloneAndInsert(addMessage.index, 1);
                addMediaSourceInternal(addMessage.index, (MediaSourceHolder) addMessage.customData);
                scheduleListenerNotification(addMessage.actionOnCompletion);
                return;
            case 1:
                MessageData<Collection<MediaSourceHolder>> addMultipleMessage = (MessageData) message;
                this.shuffleOrder = this.shuffleOrder.cloneAndInsert(addMultipleMessage.index, ((Collection) addMultipleMessage.customData).size());
                addMediaSourcesInternal(addMultipleMessage.index, (Collection) addMultipleMessage.customData);
                scheduleListenerNotification(addMultipleMessage.actionOnCompletion);
                return;
            case 2:
                MessageData<Void> removeMessage = (MessageData) message;
                this.shuffleOrder = this.shuffleOrder.cloneAndRemove(removeMessage.index);
                removeMediaSourceInternal(removeMessage.index);
                scheduleListenerNotification(removeMessage.actionOnCompletion);
                return;
            case 3:
                MessageData<Integer> moveMessage = (MessageData) message;
                this.shuffleOrder = this.shuffleOrder.cloneAndRemove(moveMessage.index);
                this.shuffleOrder = this.shuffleOrder.cloneAndInsert(((Integer) moveMessage.customData).intValue(), 1);
                moveMediaSourceInternal(moveMessage.index, ((Integer) moveMessage.customData).intValue());
                scheduleListenerNotification(moveMessage.actionOnCompletion);
                return;
            case 4:
                clearInternal();
                scheduleListenerNotification((EventDispatcher) message);
                return;
            case 5:
                notifyListener();
                return;
            case 6:
                List<EventDispatcher> actionsOnCompletion = (List) message;
                for (int i = 0; i < actionsOnCompletion.size(); i++) {
                    actionsOnCompletion.get(i).dispatchEvent();
                }
                return;
            default:
                throw new IllegalStateException();
        }
    }

    private void scheduleListenerNotification(@Nullable EventDispatcher actionOnCompletion) {
        if (!this.listenerNotificationScheduled) {
            this.player.createMessage(this).setType(5).send();
            this.listenerNotificationScheduled = true;
        }
        if (actionOnCompletion != null) {
            this.pendingOnCompletionActions.add(actionOnCompletion);
        }
    }

    private void notifyListener() {
        this.listenerNotificationScheduled = false;
        List<EventDispatcher> actionsOnCompletion = this.pendingOnCompletionActions.isEmpty() ? Collections.emptyList() : new ArrayList<>(this.pendingOnCompletionActions);
        this.pendingOnCompletionActions.clear();
        refreshSourceInfo(new ConcatenatedTimeline(this.mediaSourceHolders, this.windowCount, this.periodCount, this.shuffleOrder, this.isAtomic), null);
        if (!actionsOnCompletion.isEmpty()) {
            this.player.createMessage(this).setType(6).setPayload(actionsOnCompletion).send();
        }
    }

    private void addMediaSourceInternal(int newIndex, MediaSourceHolder newMediaSourceHolder) {
        if (newIndex > 0) {
            MediaSourceHolder previousHolder = this.mediaSourceHolders.get(newIndex - 1);
            newMediaSourceHolder.reset(newIndex, previousHolder.firstWindowIndexInChild + previousHolder.timeline.getWindowCount(), previousHolder.firstPeriodIndexInChild + previousHolder.timeline.getPeriodCount());
        } else {
            newMediaSourceHolder.reset(newIndex, 0, 0);
        }
        correctOffsets(newIndex, 1, newMediaSourceHolder.timeline.getWindowCount(), newMediaSourceHolder.timeline.getPeriodCount());
        this.mediaSourceHolders.add(newIndex, newMediaSourceHolder);
        prepareChildSource(newMediaSourceHolder, newMediaSourceHolder.mediaSource);
    }

    private void addMediaSourcesInternal(int index, Collection<MediaSourceHolder> mediaSourceHolders) {
        for (MediaSourceHolder mediaSourceHolder : mediaSourceHolders) {
            addMediaSourceInternal(index, mediaSourceHolder);
            index++;
        }
    }

    private void updateMediaSourceInternal(MediaSourceHolder mediaSourceHolder, Timeline timeline) {
        if (mediaSourceHolder == null) {
            throw new IllegalArgumentException();
        }
        DeferredTimeline deferredTimeline = mediaSourceHolder.timeline;
        if (deferredTimeline.getTimeline() != timeline) {
            int windowOffsetUpdate = timeline.getWindowCount() - deferredTimeline.getWindowCount();
            int periodOffsetUpdate = timeline.getPeriodCount() - deferredTimeline.getPeriodCount();
            if (windowOffsetUpdate != 0 || periodOffsetUpdate != 0) {
                correctOffsets(mediaSourceHolder.childIndex + 1, 0, windowOffsetUpdate, periodOffsetUpdate);
            }
            mediaSourceHolder.timeline = deferredTimeline.cloneWithNewTimeline(timeline);
            if (!mediaSourceHolder.isPrepared && !timeline.isEmpty()) {
                timeline.getWindow(0, this.window);
                long defaultPeriodPositionUs = this.window.getPositionInFirstPeriodUs() + this.window.getDefaultPositionUs();
                for (int i = 0; i < mediaSourceHolder.activeMediaPeriods.size(); i++) {
                    DeferredMediaPeriod deferredMediaPeriod = mediaSourceHolder.activeMediaPeriods.get(i);
                    deferredMediaPeriod.setDefaultPreparePositionUs(defaultPeriodPositionUs);
                    deferredMediaPeriod.createPeriod();
                }
                mediaSourceHolder.isPrepared = true;
            }
            scheduleListenerNotification(null);
        }
    }

    private void clearInternal() {
        for (int index = this.mediaSourceHolders.size() - 1; index >= 0; index--) {
            removeMediaSourceInternal(index);
        }
    }

    private void removeMediaSourceInternal(int index) {
        MediaSourceHolder holder = this.mediaSourceHolders.remove(index);
        Timeline oldTimeline = holder.timeline;
        correctOffsets(index, -1, -oldTimeline.getWindowCount(), -oldTimeline.getPeriodCount());
        holder.isRemoved = true;
        if (holder.activeMediaPeriods.isEmpty()) {
            releaseChildSource(holder);
        }
    }

    private void moveMediaSourceInternal(int currentIndex, int newIndex) {
        int startIndex = Math.min(currentIndex, newIndex);
        int endIndex = Math.max(currentIndex, newIndex);
        int windowOffset = this.mediaSourceHolders.get(startIndex).firstWindowIndexInChild;
        int periodOffset = this.mediaSourceHolders.get(startIndex).firstPeriodIndexInChild;
        this.mediaSourceHolders.add(newIndex, this.mediaSourceHolders.remove(currentIndex));
        for (int i = startIndex; i <= endIndex; i++) {
            MediaSourceHolder holder = this.mediaSourceHolders.get(i);
            holder.firstWindowIndexInChild = windowOffset;
            holder.firstPeriodIndexInChild = periodOffset;
            windowOffset += holder.timeline.getWindowCount();
            periodOffset += holder.timeline.getPeriodCount();
        }
    }

    private void correctOffsets(int startIndex, int childIndexUpdate, int windowOffsetUpdate, int periodOffsetUpdate) {
        this.windowCount += windowOffsetUpdate;
        this.periodCount += periodOffsetUpdate;
        for (int i = startIndex; i < this.mediaSourceHolders.size(); i++) {
            this.mediaSourceHolders.get(i).childIndex += childIndexUpdate;
            this.mediaSourceHolders.get(i).firstWindowIndexInChild += windowOffsetUpdate;
            this.mediaSourceHolders.get(i).firstPeriodIndexInChild += periodOffsetUpdate;
        }
    }

    private int findMediaSourceHolderByPeriodIndex(int periodIndex) {
        this.query.firstPeriodIndexInChild = periodIndex;
        int index = Collections.binarySearch(this.mediaSourceHolders, this.query);
        if (index < 0) {
            return (-index) - 2;
        }
        while (index < this.mediaSourceHolders.size() - 1 && this.mediaSourceHolders.get(index + 1).firstPeriodIndexInChild == periodIndex) {
            index++;
        }
        return index;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class MediaSourceHolder implements Comparable<MediaSourceHolder> {
        public int childIndex;
        public int firstPeriodIndexInChild;
        public int firstWindowIndexInChild;
        public boolean isPrepared;
        public boolean isRemoved;
        public final MediaSource mediaSource;
        public DeferredTimeline timeline = new DeferredTimeline();
        public List<DeferredMediaPeriod> activeMediaPeriods = new ArrayList();
        public final Object uid = new Object();

        public MediaSourceHolder(MediaSource mediaSource) {
            this.mediaSource = mediaSource;
        }

        public void reset(int childIndex, int firstWindowIndexInChild, int firstPeriodIndexInChild) {
            this.childIndex = childIndex;
            this.firstWindowIndexInChild = firstWindowIndexInChild;
            this.firstPeriodIndexInChild = firstPeriodIndexInChild;
            this.isPrepared = false;
            this.isRemoved = false;
            this.activeMediaPeriods.clear();
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull MediaSourceHolder other) {
            return this.firstPeriodIndexInChild - other.firstPeriodIndexInChild;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class EventDispatcher {
        public final Handler eventHandler;
        public final Runnable runnable;

        public EventDispatcher(Runnable runnable) {
            this.runnable = runnable;
            this.eventHandler = new Handler(Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper());
        }

        public void dispatchEvent() {
            this.eventHandler.post(this.runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class MessageData<T> {
        @Nullable
        public final EventDispatcher actionOnCompletion;
        public final T customData;
        public final int index;

        public MessageData(int index, T customData, @Nullable Runnable actionOnCompletion) {
            this.index = index;
            this.actionOnCompletion = actionOnCompletion != null ? new EventDispatcher(actionOnCompletion) : null;
            this.customData = customData;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class ConcatenatedTimeline extends AbstractConcatenatedTimeline {
        private final HashMap<Object, Integer> childIndexByUid;
        private final int[] firstPeriodInChildIndices;
        private final int[] firstWindowInChildIndices;
        private final int periodCount;
        private final Timeline[] timelines;
        private final Object[] uids;
        private final int windowCount;

        public ConcatenatedTimeline(Collection<MediaSourceHolder> mediaSourceHolders, int windowCount, int periodCount, ShuffleOrder shuffleOrder, boolean isAtomic) {
            super(isAtomic, shuffleOrder);
            this.windowCount = windowCount;
            this.periodCount = periodCount;
            int childCount = mediaSourceHolders.size();
            this.firstPeriodInChildIndices = new int[childCount];
            this.firstWindowInChildIndices = new int[childCount];
            this.timelines = new Timeline[childCount];
            this.uids = new Object[childCount];
            this.childIndexByUid = new HashMap<>();
            int index = 0;
            for (MediaSourceHolder mediaSourceHolder : mediaSourceHolders) {
                this.timelines[index] = mediaSourceHolder.timeline;
                this.firstPeriodInChildIndices[index] = mediaSourceHolder.firstPeriodIndexInChild;
                this.firstWindowInChildIndices[index] = mediaSourceHolder.firstWindowIndexInChild;
                this.uids[index] = mediaSourceHolder.uid;
                this.childIndexByUid.put(this.uids[index], Integer.valueOf(index));
                index++;
            }
        }

        @Override // com.google.android.exoplayer2.source.AbstractConcatenatedTimeline
        protected int getChildIndexByPeriodIndex(int periodIndex) {
            return Util.binarySearchFloor(this.firstPeriodInChildIndices, periodIndex + 1, false, false);
        }

        @Override // com.google.android.exoplayer2.source.AbstractConcatenatedTimeline
        protected int getChildIndexByWindowIndex(int windowIndex) {
            return Util.binarySearchFloor(this.firstWindowInChildIndices, windowIndex + 1, false, false);
        }

        @Override // com.google.android.exoplayer2.source.AbstractConcatenatedTimeline
        protected int getChildIndexByChildUid(Object childUid) {
            Integer index = this.childIndexByUid.get(childUid);
            if (index == null) {
                return -1;
            }
            return index.intValue();
        }

        @Override // com.google.android.exoplayer2.source.AbstractConcatenatedTimeline
        protected Timeline getTimelineByChildIndex(int childIndex) {
            return this.timelines[childIndex];
        }

        @Override // com.google.android.exoplayer2.source.AbstractConcatenatedTimeline
        protected int getFirstPeriodIndexByChildIndex(int childIndex) {
            return this.firstPeriodInChildIndices[childIndex];
        }

        @Override // com.google.android.exoplayer2.source.AbstractConcatenatedTimeline
        protected int getFirstWindowIndexByChildIndex(int childIndex) {
            return this.firstWindowInChildIndices[childIndex];
        }

        @Override // com.google.android.exoplayer2.source.AbstractConcatenatedTimeline
        protected Object getChildUidByChildIndex(int childIndex) {
            return this.uids[childIndex];
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return this.windowCount;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return this.periodCount;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class DeferredTimeline extends ForwardingTimeline {
        private final Object replacedId;
        private static final Object DUMMY_ID = new Object();
        private static final Timeline.Period period = new Timeline.Period();
        private static final DummyTimeline dummyTimeline = new DummyTimeline();

        public DeferredTimeline() {
            this(dummyTimeline, null);
        }

        private DeferredTimeline(Timeline timeline, Object replacedId) {
            super(timeline);
            this.replacedId = replacedId;
        }

        public DeferredTimeline cloneWithNewTimeline(Timeline timeline) {
            return new DeferredTimeline(timeline, (this.replacedId != null || timeline.getPeriodCount() <= 0) ? this.replacedId : timeline.getPeriod(0, period, true).uid);
        }

        public Timeline getTimeline() {
            return this.timeline;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int periodIndex, Timeline.Period period2, boolean setIds) {
            this.timeline.getPeriod(periodIndex, period2, setIds);
            if (Util.areEqual(period2.uid, this.replacedId)) {
                period2.uid = DUMMY_ID;
            }
            return period2;
        }

        @Override // com.google.android.exoplayer2.source.ForwardingTimeline, com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object uid) {
            Timeline timeline = this.timeline;
            if (DUMMY_ID.equals(uid)) {
                uid = this.replacedId;
            }
            return timeline.getIndexOfPeriod(uid);
        }
    }

    /* loaded from: classes.dex */
    private static final class DummyTimeline extends Timeline {
        private DummyTimeline() {
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getWindowCount() {
            return 1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Window getWindow(int windowIndex, Timeline.Window window, boolean setTag, long defaultPositionProjectionUs) {
            return window.set(null, C.TIME_UNSET, C.TIME_UNSET, false, true, defaultPositionProjectionUs > 0 ? C.TIME_UNSET : 0L, C.TIME_UNSET, 0, 0, 0L);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getPeriodCount() {
            return 1;
        }

        @Override // com.google.android.exoplayer2.Timeline
        public Timeline.Period getPeriod(int periodIndex, Timeline.Period period, boolean setIds) {
            return period.set(null, null, 0, C.TIME_UNSET, 0L);
        }

        @Override // com.google.android.exoplayer2.Timeline
        public int getIndexOfPeriod(Object uid) {
            return uid == null ? 0 : -1;
        }
    }
}
