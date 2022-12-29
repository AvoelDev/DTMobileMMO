package com.google.android.exoplayer2;

import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
final class MediaPeriodQueue {
    private static final int MAXIMUM_BUFFER_AHEAD_PERIODS = 100;
    private int length;
    private MediaPeriodHolder loading;
    private long nextWindowSequenceNumber;
    private Object oldFrontPeriodUid;
    private long oldFrontPeriodWindowSequenceNumber;
    private MediaPeriodHolder playing;
    private MediaPeriodHolder reading;
    private int repeatMode;
    private boolean shuffleModeEnabled;
    private Timeline timeline;
    private final Timeline.Period period = new Timeline.Period();
    private final Timeline.Window window = new Timeline.Window();

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public boolean updateRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
        return updateForPlaybackModeChange();
    }

    public boolean updateShuffleModeEnabled(boolean shuffleModeEnabled) {
        this.shuffleModeEnabled = shuffleModeEnabled;
        return updateForPlaybackModeChange();
    }

    public boolean isLoading(MediaPeriod mediaPeriod) {
        return this.loading != null && this.loading.mediaPeriod == mediaPeriod;
    }

    public void reevaluateBuffer(long rendererPositionUs) {
        if (this.loading != null) {
            this.loading.reevaluateBuffer(rendererPositionUs);
        }
    }

    public boolean shouldLoadNextMediaPeriod() {
        return this.loading == null || (!this.loading.info.isFinal && this.loading.isFullyBuffered() && this.loading.info.durationUs != C.TIME_UNSET && this.length < 100);
    }

    @Nullable
    public MediaPeriodInfo getNextMediaPeriodInfo(long rendererPositionUs, PlaybackInfo playbackInfo) {
        if (this.loading == null) {
            return getFirstMediaPeriodInfo(playbackInfo);
        }
        return getFollowingMediaPeriodInfo(this.loading, rendererPositionUs);
    }

    public MediaPeriod enqueueNextMediaPeriod(RendererCapabilities[] rendererCapabilities, TrackSelector trackSelector, Allocator allocator, MediaSource mediaSource, Object uid, MediaPeriodInfo info) {
        long rendererPositionOffsetUs = this.loading == null ? info.startPositionUs : this.loading.getRendererOffset() + this.loading.info.durationUs;
        MediaPeriodHolder newPeriodHolder = new MediaPeriodHolder(rendererCapabilities, rendererPositionOffsetUs, trackSelector, allocator, mediaSource, uid, info);
        if (this.loading != null) {
            Assertions.checkState(hasPlayingPeriod());
            this.loading.next = newPeriodHolder;
        }
        this.oldFrontPeriodUid = null;
        this.loading = newPeriodHolder;
        this.length++;
        return newPeriodHolder.mediaPeriod;
    }

    public MediaPeriodHolder getLoadingPeriod() {
        return this.loading;
    }

    public MediaPeriodHolder getPlayingPeriod() {
        return this.playing;
    }

    public MediaPeriodHolder getReadingPeriod() {
        return this.reading;
    }

    public MediaPeriodHolder getFrontPeriod() {
        return hasPlayingPeriod() ? this.playing : this.loading;
    }

    public boolean hasPlayingPeriod() {
        return this.playing != null;
    }

    public MediaPeriodHolder advanceReadingPeriod() {
        Assertions.checkState((this.reading == null || this.reading.next == null) ? false : true);
        this.reading = this.reading.next;
        return this.reading;
    }

    public MediaPeriodHolder advancePlayingPeriod() {
        if (this.playing != null) {
            if (this.playing == this.reading) {
                this.reading = this.playing.next;
            }
            this.playing.release();
            this.length--;
            if (this.length == 0) {
                this.loading = null;
                this.oldFrontPeriodUid = this.playing.uid;
                this.oldFrontPeriodWindowSequenceNumber = this.playing.info.id.windowSequenceNumber;
            }
            this.playing = this.playing.next;
        } else {
            this.playing = this.loading;
            this.reading = this.loading;
        }
        return this.playing;
    }

    public boolean removeAfter(MediaPeriodHolder mediaPeriodHolder) {
        Assertions.checkState(mediaPeriodHolder != null);
        boolean removedReading = false;
        this.loading = mediaPeriodHolder;
        while (mediaPeriodHolder.next != null) {
            mediaPeriodHolder = mediaPeriodHolder.next;
            if (mediaPeriodHolder == this.reading) {
                this.reading = this.playing;
                removedReading = true;
            }
            mediaPeriodHolder.release();
            this.length--;
        }
        this.loading.next = null;
        return removedReading;
    }

    public void clear(boolean keepFrontPeriodUid) {
        MediaPeriodHolder front = getFrontPeriod();
        if (front != null) {
            this.oldFrontPeriodUid = keepFrontPeriodUid ? front.uid : null;
            this.oldFrontPeriodWindowSequenceNumber = front.info.id.windowSequenceNumber;
            front.release();
            removeAfter(front);
        } else if (!keepFrontPeriodUid) {
            this.oldFrontPeriodUid = null;
        }
        this.playing = null;
        this.loading = null;
        this.reading = null;
        this.length = 0;
    }

    public boolean updateQueuedPeriods(MediaSource.MediaPeriodId playingPeriodId, long rendererPositionUs) {
        int periodIndex = playingPeriodId.periodIndex;
        MediaPeriodHolder previousPeriodHolder = null;
        for (MediaPeriodHolder periodHolder = getFrontPeriod(); periodHolder != null; periodHolder = periodHolder.next) {
            if (previousPeriodHolder == null) {
                periodHolder.info = getUpdatedMediaPeriodInfo(periodHolder.info, periodIndex);
            } else if (periodIndex == -1 || !periodHolder.uid.equals(this.timeline.getPeriod(periodIndex, this.period, true).uid)) {
                return !removeAfter(previousPeriodHolder);
            } else {
                MediaPeriodInfo periodInfo = getFollowingMediaPeriodInfo(previousPeriodHolder, rendererPositionUs);
                if (periodInfo == null) {
                    return !removeAfter(previousPeriodHolder);
                }
                periodHolder.info = getUpdatedMediaPeriodInfo(periodHolder.info, periodIndex);
                if (!canKeepMediaPeriodHolder(periodHolder, periodInfo)) {
                    return !removeAfter(previousPeriodHolder);
                }
            }
            if (periodHolder.info.isLastInTimelinePeriod) {
                periodIndex = this.timeline.getNextPeriodIndex(periodIndex, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            }
            previousPeriodHolder = periodHolder;
        }
        return true;
    }

    public MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo, int newPeriodIndex) {
        return getUpdatedMediaPeriodInfo(mediaPeriodInfo, mediaPeriodInfo.id.copyWithPeriodIndex(newPeriodIndex));
    }

    public MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(int periodIndex, long positionUs) {
        long windowSequenceNumber = resolvePeriodIndexToWindowSequenceNumber(periodIndex);
        return resolveMediaPeriodIdForAds(periodIndex, positionUs, windowSequenceNumber);
    }

    private MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(int periodIndex, long positionUs, long windowSequenceNumber) {
        this.timeline.getPeriod(periodIndex, this.period);
        int adGroupIndex = this.period.getAdGroupIndexForPositionUs(positionUs);
        if (adGroupIndex == -1) {
            return new MediaSource.MediaPeriodId(periodIndex, windowSequenceNumber);
        }
        int adIndexInAdGroup = this.period.getFirstAdIndexToPlay(adGroupIndex);
        return new MediaSource.MediaPeriodId(periodIndex, adGroupIndex, adIndexInAdGroup, windowSequenceNumber);
    }

    private long resolvePeriodIndexToWindowSequenceNumber(int periodIndex) {
        int oldFrontPeriodIndex;
        Object periodUid = this.timeline.getPeriod(periodIndex, this.period, true).uid;
        int windowIndex = this.period.windowIndex;
        if (this.oldFrontPeriodUid != null && (oldFrontPeriodIndex = this.timeline.getIndexOfPeriod(this.oldFrontPeriodUid)) != -1) {
            int oldFrontWindowIndex = this.timeline.getPeriod(oldFrontPeriodIndex, this.period).windowIndex;
            if (oldFrontWindowIndex == windowIndex) {
                return this.oldFrontPeriodWindowSequenceNumber;
            }
        }
        for (MediaPeriodHolder mediaPeriodHolder = getFrontPeriod(); mediaPeriodHolder != null; mediaPeriodHolder = mediaPeriodHolder.next) {
            if (mediaPeriodHolder.uid.equals(periodUid)) {
                return mediaPeriodHolder.info.id.windowSequenceNumber;
            }
        }
        for (MediaPeriodHolder mediaPeriodHolder2 = getFrontPeriod(); mediaPeriodHolder2 != null; mediaPeriodHolder2 = mediaPeriodHolder2.next) {
            int indexOfHolderInTimeline = this.timeline.getIndexOfPeriod(mediaPeriodHolder2.uid);
            if (indexOfHolderInTimeline != -1) {
                int holderWindowIndex = this.timeline.getPeriod(indexOfHolderInTimeline, this.period).windowIndex;
                if (holderWindowIndex == windowIndex) {
                    return mediaPeriodHolder2.info.id.windowSequenceNumber;
                }
            }
        }
        long j = this.nextWindowSequenceNumber;
        this.nextWindowSequenceNumber = 1 + j;
        return j;
    }

    private boolean canKeepMediaPeriodHolder(MediaPeriodHolder periodHolder, MediaPeriodInfo info) {
        MediaPeriodInfo periodHolderInfo = periodHolder.info;
        return periodHolderInfo.startPositionUs == info.startPositionUs && periodHolderInfo.endPositionUs == info.endPositionUs && periodHolderInfo.id.equals(info.id);
    }

    private boolean updateForPlaybackModeChange() {
        MediaPeriodHolder lastValidPeriodHolder = getFrontPeriod();
        if (lastValidPeriodHolder == null) {
            return true;
        }
        while (true) {
            int nextPeriodIndex = this.timeline.getNextPeriodIndex(lastValidPeriodHolder.info.id.periodIndex, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            while (lastValidPeriodHolder.next != null && !lastValidPeriodHolder.info.isLastInTimelinePeriod) {
                lastValidPeriodHolder = lastValidPeriodHolder.next;
            }
            if (nextPeriodIndex == -1 || lastValidPeriodHolder.next == null || lastValidPeriodHolder.next.info.id.periodIndex != nextPeriodIndex) {
                break;
            }
            lastValidPeriodHolder = lastValidPeriodHolder.next;
        }
        boolean readingPeriodRemoved = removeAfter(lastValidPeriodHolder);
        lastValidPeriodHolder.info = getUpdatedMediaPeriodInfo(lastValidPeriodHolder.info, lastValidPeriodHolder.info.id);
        return (readingPeriodRemoved && hasPlayingPeriod()) ? false : true;
    }

    private MediaPeriodInfo getFirstMediaPeriodInfo(PlaybackInfo playbackInfo) {
        return getMediaPeriodInfo(playbackInfo.periodId, playbackInfo.contentPositionUs, playbackInfo.startPositionUs);
    }

    @Nullable
    private MediaPeriodInfo getFollowingMediaPeriodInfo(MediaPeriodHolder mediaPeriodHolder, long rendererPositionUs) {
        long startPositionUs;
        MediaPeriodInfo mediaPeriodInfo = mediaPeriodHolder.info;
        if (mediaPeriodInfo.isLastInTimelinePeriod) {
            int nextPeriodIndex = this.timeline.getNextPeriodIndex(mediaPeriodInfo.id.periodIndex, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            if (nextPeriodIndex == -1) {
                return null;
            }
            int nextWindowIndex = this.timeline.getPeriod(nextPeriodIndex, this.period, true).windowIndex;
            Object nextPeriodUid = this.period.uid;
            long windowSequenceNumber = mediaPeriodInfo.id.windowSequenceNumber;
            if (this.timeline.getWindow(nextWindowIndex, this.window).firstPeriodIndex == nextPeriodIndex) {
                long defaultPositionProjectionUs = (mediaPeriodHolder.getRendererOffset() + mediaPeriodInfo.durationUs) - rendererPositionUs;
                Pair<Integer, Long> defaultPosition = this.timeline.getPeriodPosition(this.window, this.period, nextWindowIndex, C.TIME_UNSET, Math.max(0L, defaultPositionProjectionUs));
                if (defaultPosition == null) {
                    return null;
                }
                nextPeriodIndex = ((Integer) defaultPosition.first).intValue();
                startPositionUs = ((Long) defaultPosition.second).longValue();
                if (mediaPeriodHolder.next != null && mediaPeriodHolder.next.uid.equals(nextPeriodUid)) {
                    windowSequenceNumber = mediaPeriodHolder.next.info.id.windowSequenceNumber;
                } else {
                    windowSequenceNumber = this.nextWindowSequenceNumber;
                    this.nextWindowSequenceNumber = 1 + windowSequenceNumber;
                }
            } else {
                startPositionUs = 0;
            }
            MediaSource.MediaPeriodId periodId = resolveMediaPeriodIdForAds(nextPeriodIndex, startPositionUs, windowSequenceNumber);
            return getMediaPeriodInfo(periodId, startPositionUs, startPositionUs);
        }
        MediaSource.MediaPeriodId currentPeriodId = mediaPeriodInfo.id;
        this.timeline.getPeriod(currentPeriodId.periodIndex, this.period);
        if (currentPeriodId.isAd()) {
            int adGroupIndex = currentPeriodId.adGroupIndex;
            int adCountInCurrentAdGroup = this.period.getAdCountInAdGroup(adGroupIndex);
            if (adCountInCurrentAdGroup == -1) {
                return null;
            }
            int nextAdIndexInAdGroup = this.period.getNextAdIndexToPlay(adGroupIndex, currentPeriodId.adIndexInAdGroup);
            if (nextAdIndexInAdGroup < adCountInCurrentAdGroup) {
                if (this.period.isAdAvailable(adGroupIndex, nextAdIndexInAdGroup)) {
                    return getMediaPeriodInfoForAd(currentPeriodId.periodIndex, adGroupIndex, nextAdIndexInAdGroup, mediaPeriodInfo.contentPositionUs, currentPeriodId.windowSequenceNumber);
                }
                return null;
            }
            return getMediaPeriodInfoForContent(currentPeriodId.periodIndex, mediaPeriodInfo.contentPositionUs, currentPeriodId.windowSequenceNumber);
        } else if (mediaPeriodInfo.endPositionUs != Long.MIN_VALUE) {
            int nextAdGroupIndex = this.period.getAdGroupIndexForPositionUs(mediaPeriodInfo.endPositionUs);
            if (nextAdGroupIndex == -1) {
                return getMediaPeriodInfoForContent(currentPeriodId.periodIndex, mediaPeriodInfo.endPositionUs, currentPeriodId.windowSequenceNumber);
            }
            int adIndexInAdGroup = this.period.getFirstAdIndexToPlay(nextAdGroupIndex);
            if (this.period.isAdAvailable(nextAdGroupIndex, adIndexInAdGroup)) {
                return getMediaPeriodInfoForAd(currentPeriodId.periodIndex, nextAdGroupIndex, adIndexInAdGroup, mediaPeriodInfo.endPositionUs, currentPeriodId.windowSequenceNumber);
            }
            return null;
        } else {
            int adGroupCount = this.period.getAdGroupCount();
            if (adGroupCount == 0) {
                return null;
            }
            int adGroupIndex2 = adGroupCount - 1;
            if (this.period.getAdGroupTimeUs(adGroupIndex2) != Long.MIN_VALUE || this.period.hasPlayedAdGroup(adGroupIndex2)) {
                return null;
            }
            int adIndexInAdGroup2 = this.period.getFirstAdIndexToPlay(adGroupIndex2);
            if (!this.period.isAdAvailable(adGroupIndex2, adIndexInAdGroup2)) {
                return null;
            }
            long contentDurationUs = this.period.getDurationUs();
            return getMediaPeriodInfoForAd(currentPeriodId.periodIndex, adGroupIndex2, adIndexInAdGroup2, contentDurationUs, currentPeriodId.windowSequenceNumber);
        }
    }

    private MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo info, MediaSource.MediaPeriodId newId) {
        long durationUs;
        long startPositionUs = info.startPositionUs;
        long endPositionUs = info.endPositionUs;
        boolean isLastInPeriod = isLastInPeriod(newId, endPositionUs);
        boolean isLastInTimeline = isLastInTimeline(newId, isLastInPeriod);
        this.timeline.getPeriod(newId.periodIndex, this.period);
        if (newId.isAd()) {
            durationUs = this.period.getAdDurationUs(newId.adGroupIndex, newId.adIndexInAdGroup);
        } else {
            durationUs = endPositionUs == Long.MIN_VALUE ? this.period.getDurationUs() : endPositionUs;
        }
        return new MediaPeriodInfo(newId, startPositionUs, endPositionUs, info.contentPositionUs, durationUs, isLastInPeriod, isLastInTimeline);
    }

    private MediaPeriodInfo getMediaPeriodInfo(MediaSource.MediaPeriodId id, long contentPositionUs, long startPositionUs) {
        this.timeline.getPeriod(id.periodIndex, this.period);
        if (id.isAd()) {
            if (!this.period.isAdAvailable(id.adGroupIndex, id.adIndexInAdGroup)) {
                return null;
            }
            return getMediaPeriodInfoForAd(id.periodIndex, id.adGroupIndex, id.adIndexInAdGroup, contentPositionUs, id.windowSequenceNumber);
        }
        return getMediaPeriodInfoForContent(id.periodIndex, startPositionUs, id.windowSequenceNumber);
    }

    private MediaPeriodInfo getMediaPeriodInfoForAd(int periodIndex, int adGroupIndex, int adIndexInAdGroup, long contentPositionUs, long windowSequenceNumber) {
        MediaSource.MediaPeriodId id = new MediaSource.MediaPeriodId(periodIndex, adGroupIndex, adIndexInAdGroup, windowSequenceNumber);
        boolean isLastInPeriod = isLastInPeriod(id, Long.MIN_VALUE);
        boolean isLastInTimeline = isLastInTimeline(id, isLastInPeriod);
        long durationUs = this.timeline.getPeriod(id.periodIndex, this.period).getAdDurationUs(id.adGroupIndex, id.adIndexInAdGroup);
        long startPositionUs = adIndexInAdGroup == this.period.getFirstAdIndexToPlay(adGroupIndex) ? this.period.getAdResumePositionUs() : 0L;
        return new MediaPeriodInfo(id, startPositionUs, Long.MIN_VALUE, contentPositionUs, durationUs, isLastInPeriod, isLastInTimeline);
    }

    private MediaPeriodInfo getMediaPeriodInfoForContent(int periodIndex, long startPositionUs, long windowSequenceNumber) {
        MediaSource.MediaPeriodId id = new MediaSource.MediaPeriodId(periodIndex, windowSequenceNumber);
        this.timeline.getPeriod(id.periodIndex, this.period);
        int nextAdGroupIndex = this.period.getAdGroupIndexAfterPositionUs(startPositionUs);
        long endUs = nextAdGroupIndex == -1 ? Long.MIN_VALUE : this.period.getAdGroupTimeUs(nextAdGroupIndex);
        boolean isLastInPeriod = isLastInPeriod(id, endUs);
        boolean isLastInTimeline = isLastInTimeline(id, isLastInPeriod);
        long durationUs = endUs == Long.MIN_VALUE ? this.period.getDurationUs() : endUs;
        return new MediaPeriodInfo(id, startPositionUs, endUs, C.TIME_UNSET, durationUs, isLastInPeriod, isLastInTimeline);
    }

    private boolean isLastInPeriod(MediaSource.MediaPeriodId id, long endPositionUs) {
        boolean z = false;
        int adGroupCount = this.timeline.getPeriod(id.periodIndex, this.period).getAdGroupCount();
        if (adGroupCount == 0) {
            return true;
        }
        int lastAdGroupIndex = adGroupCount - 1;
        boolean isAd = id.isAd();
        if (this.period.getAdGroupTimeUs(lastAdGroupIndex) != Long.MIN_VALUE) {
            return !isAd && endPositionUs == Long.MIN_VALUE;
        }
        int postrollAdCount = this.period.getAdCountInAdGroup(lastAdGroupIndex);
        if (postrollAdCount == -1) {
            return false;
        }
        boolean isLastAd = isAd && id.adGroupIndex == lastAdGroupIndex && id.adIndexInAdGroup == postrollAdCount + (-1);
        if (isLastAd || (!isAd && this.period.getFirstAdIndexToPlay(lastAdGroupIndex) == postrollAdCount)) {
            z = true;
        }
        return z;
    }

    private boolean isLastInTimeline(MediaSource.MediaPeriodId id, boolean isLastMediaPeriodInPeriod) {
        int windowIndex = this.timeline.getPeriod(id.periodIndex, this.period).windowIndex;
        return !this.timeline.getWindow(windowIndex, this.window).isDynamic && this.timeline.isLastPeriod(id.periodIndex, this.period, this.window, this.repeatMode, this.shuffleModeEnabled) && isLastMediaPeriodInPeriod;
    }
}
