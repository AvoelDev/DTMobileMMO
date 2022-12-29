package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;
import java.util.List;

/* loaded from: classes.dex */
public class AdaptiveTrackSelection extends BaseTrackSelection {
    public static final float DEFAULT_BANDWIDTH_FRACTION = 0.75f;
    public static final float DEFAULT_BUFFERED_FRACTION_TO_LIVE_EDGE_FOR_QUALITY_INCREASE = 0.75f;
    public static final int DEFAULT_MAX_DURATION_FOR_QUALITY_DECREASE_MS = 25000;
    public static final int DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS = 10000;
    public static final int DEFAULT_MIN_DURATION_TO_RETAIN_AFTER_DISCARD_MS = 25000;
    public static final long DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS = 2000;
    private final float bandwidthFraction;
    private final BandwidthMeter bandwidthMeter;
    private final float bufferedFractionToLiveEdgeForQualityIncrease;
    private final Clock clock;
    private long lastBufferEvaluationMs;
    private final long maxDurationForQualityDecreaseUs;
    private final long minDurationForQualityIncreaseUs;
    private final long minDurationToRetainAfterDiscardUs;
    private final long minTimeBetweenBufferReevaluationMs;
    private float playbackSpeed;
    private int reason;
    private int selectedIndex;

    /* loaded from: classes.dex */
    public static final class Factory implements TrackSelection.Factory {
        private final float bandwidthFraction;
        private final BandwidthMeter bandwidthMeter;
        private final float bufferedFractionToLiveEdgeForQualityIncrease;
        private final Clock clock;
        private final int maxDurationForQualityDecreaseMs;
        private final int minDurationForQualityIncreaseMs;
        private final int minDurationToRetainAfterDiscardMs;
        private final long minTimeBetweenBufferReevaluationMs;

        public Factory(BandwidthMeter bandwidthMeter) {
            this(bandwidthMeter, 10000, 25000, 25000, 0.75f, 0.75f, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, Clock.DEFAULT);
        }

        public Factory(BandwidthMeter bandwidthMeter, int minDurationForQualityIncreaseMs, int maxDurationForQualityDecreaseMs, int minDurationToRetainAfterDiscardMs, float bandwidthFraction) {
            this(bandwidthMeter, minDurationForQualityIncreaseMs, maxDurationForQualityDecreaseMs, minDurationToRetainAfterDiscardMs, bandwidthFraction, 0.75f, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, Clock.DEFAULT);
        }

        public Factory(BandwidthMeter bandwidthMeter, int minDurationForQualityIncreaseMs, int maxDurationForQualityDecreaseMs, int minDurationToRetainAfterDiscardMs, float bandwidthFraction, float bufferedFractionToLiveEdgeForQualityIncrease, long minTimeBetweenBufferReevaluationMs, Clock clock) {
            this.bandwidthMeter = bandwidthMeter;
            this.minDurationForQualityIncreaseMs = minDurationForQualityIncreaseMs;
            this.maxDurationForQualityDecreaseMs = maxDurationForQualityDecreaseMs;
            this.minDurationToRetainAfterDiscardMs = minDurationToRetainAfterDiscardMs;
            this.bandwidthFraction = bandwidthFraction;
            this.bufferedFractionToLiveEdgeForQualityIncrease = bufferedFractionToLiveEdgeForQualityIncrease;
            this.minTimeBetweenBufferReevaluationMs = minTimeBetweenBufferReevaluationMs;
            this.clock = clock;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelection.Factory
        public AdaptiveTrackSelection createTrackSelection(TrackGroup group, int... tracks) {
            return new AdaptiveTrackSelection(group, tracks, this.bandwidthMeter, this.minDurationForQualityIncreaseMs, this.maxDurationForQualityDecreaseMs, this.minDurationToRetainAfterDiscardMs, this.bandwidthFraction, this.bufferedFractionToLiveEdgeForQualityIncrease, this.minTimeBetweenBufferReevaluationMs, this.clock);
        }
    }

    public AdaptiveTrackSelection(TrackGroup group, int[] tracks, BandwidthMeter bandwidthMeter) {
        this(group, tracks, bandwidthMeter, 10000L, 25000L, 25000L, 0.75f, 0.75f, DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, Clock.DEFAULT);
    }

    public AdaptiveTrackSelection(TrackGroup group, int[] tracks, BandwidthMeter bandwidthMeter, long minDurationForQualityIncreaseMs, long maxDurationForQualityDecreaseMs, long minDurationToRetainAfterDiscardMs, float bandwidthFraction, float bufferedFractionToLiveEdgeForQualityIncrease, long minTimeBetweenBufferReevaluationMs, Clock clock) {
        super(group, tracks);
        this.bandwidthMeter = bandwidthMeter;
        this.minDurationForQualityIncreaseUs = 1000 * minDurationForQualityIncreaseMs;
        this.maxDurationForQualityDecreaseUs = 1000 * maxDurationForQualityDecreaseMs;
        this.minDurationToRetainAfterDiscardUs = 1000 * minDurationToRetainAfterDiscardMs;
        this.bandwidthFraction = bandwidthFraction;
        this.bufferedFractionToLiveEdgeForQualityIncrease = bufferedFractionToLiveEdgeForQualityIncrease;
        this.minTimeBetweenBufferReevaluationMs = minTimeBetweenBufferReevaluationMs;
        this.clock = clock;
        this.playbackSpeed = 1.0f;
        this.reason = 1;
        this.lastBufferEvaluationMs = C.TIME_UNSET;
        int selectedIndex = determineIdealSelectedIndex(Long.MIN_VALUE);
        this.selectedIndex = selectedIndex;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.TrackSelection
    public void enable() {
        this.lastBufferEvaluationMs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.TrackSelection
    public void onPlaybackSpeed(float playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs) {
        long nowMs = this.clock.elapsedRealtime();
        int currentSelectedIndex = this.selectedIndex;
        this.selectedIndex = determineIdealSelectedIndex(nowMs);
        if (this.selectedIndex != currentSelectedIndex) {
            if (!isBlacklisted(currentSelectedIndex, nowMs)) {
                Format currentFormat = getFormat(currentSelectedIndex);
                Format selectedFormat = getFormat(this.selectedIndex);
                if (selectedFormat.bitrate > currentFormat.bitrate && bufferedDurationUs < minDurationForQualityIncreaseUs(availableDurationUs)) {
                    this.selectedIndex = currentSelectedIndex;
                } else if (selectedFormat.bitrate < currentFormat.bitrate && bufferedDurationUs >= this.maxDurationForQualityDecreaseUs) {
                    this.selectedIndex = currentSelectedIndex;
                }
            }
            if (this.selectedIndex != currentSelectedIndex) {
                this.reason = 3;
            }
        }
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public int getSelectionReason() {
        return this.reason;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    @Nullable
    public Object getSelectionData() {
        return null;
    }

    @Override // com.google.android.exoplayer2.trackselection.BaseTrackSelection, com.google.android.exoplayer2.trackselection.TrackSelection
    public int evaluateQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        long nowMs = this.clock.elapsedRealtime();
        if (this.lastBufferEvaluationMs != C.TIME_UNSET && nowMs - this.lastBufferEvaluationMs < this.minTimeBetweenBufferReevaluationMs) {
            return queue.size();
        }
        this.lastBufferEvaluationMs = nowMs;
        if (queue.isEmpty()) {
            return 0;
        }
        int queueSize = queue.size();
        MediaChunk lastChunk = queue.get(queueSize - 1);
        long playoutBufferedDurationBeforeLastChunkUs = Util.getPlayoutDurationForMediaDuration(lastChunk.startTimeUs - playbackPositionUs, this.playbackSpeed);
        if (playoutBufferedDurationBeforeLastChunkUs >= this.minDurationToRetainAfterDiscardUs) {
            int idealSelectedIndex = determineIdealSelectedIndex(nowMs);
            Format idealFormat = getFormat(idealSelectedIndex);
            for (int i = 0; i < queueSize; i++) {
                MediaChunk chunk = queue.get(i);
                Format format = chunk.trackFormat;
                long mediaDurationBeforeThisChunkUs = chunk.startTimeUs - playbackPositionUs;
                long playoutDurationBeforeThisChunkUs = Util.getPlayoutDurationForMediaDuration(mediaDurationBeforeThisChunkUs, this.playbackSpeed);
                if (playoutDurationBeforeThisChunkUs >= this.minDurationToRetainAfterDiscardUs && format.bitrate < idealFormat.bitrate && format.height != -1 && format.height < 720 && format.width != -1 && format.width < 1280 && format.height < idealFormat.height) {
                    return i;
                }
            }
            return queueSize;
        }
        return queueSize;
    }

    private int determineIdealSelectedIndex(long nowMs) {
        long effectiveBitrate = ((float) this.bandwidthMeter.getBitrateEstimate()) * this.bandwidthFraction;
        int lowestBitrateNonBlacklistedIndex = 0;
        for (int i = 0; i < this.length; i++) {
            if (nowMs == Long.MIN_VALUE || !isBlacklisted(i, nowMs)) {
                Format format = getFormat(i);
                if (Math.round(format.bitrate * this.playbackSpeed) > effectiveBitrate) {
                    lowestBitrateNonBlacklistedIndex = i;
                } else {
                    return i;
                }
            }
        }
        int i2 = lowestBitrateNonBlacklistedIndex;
        return i2;
    }

    private long minDurationForQualityIncreaseUs(long availableDurationUs) {
        boolean isAvailableDurationTooShort = availableDurationUs != C.TIME_UNSET && availableDurationUs <= this.minDurationForQualityIncreaseUs;
        return isAvailableDurationTooShort ? ((float) availableDurationUs) * this.bufferedFractionToLiveEdgeForQualityIncrease : this.minDurationForQualityIncreaseUs;
    }
}
