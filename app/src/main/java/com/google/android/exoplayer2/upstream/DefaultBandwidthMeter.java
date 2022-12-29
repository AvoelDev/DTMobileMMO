package com.google.android.exoplayer2.upstream;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.SlidingPercentile;

/* loaded from: classes.dex */
public final class DefaultBandwidthMeter implements BandwidthMeter, TransferListener<Object> {
    private static final int BYTES_TRANSFERRED_FOR_ESTIMATE = 524288;
    public static final long DEFAULT_INITIAL_BITRATE_ESTIMATE = 1000000;
    public static final int DEFAULT_SLIDING_WINDOW_MAX_WEIGHT = 2000;
    private static final int ELAPSED_MILLIS_FOR_ESTIMATE = 2000;
    private long bitrateEstimate;
    private final Clock clock;
    @Nullable
    private final Handler eventHandler;
    @Nullable
    private final BandwidthMeter.EventListener eventListener;
    private long sampleBytesTransferred;
    private long sampleStartTimeMs;
    private final SlidingPercentile slidingPercentile;
    private int streamCount;
    private long totalBytesTransferred;
    private long totalElapsedTimeMs;

    /* loaded from: classes.dex */
    public static final class Builder {
        @Nullable
        private Handler eventHandler;
        @Nullable
        private BandwidthMeter.EventListener eventListener;
        private long initialBitrateEstimate = 1000000;
        private int slidingWindowMaxWeight = 2000;
        private Clock clock = Clock.DEFAULT;

        public Builder setEventListener(Handler eventHandler, BandwidthMeter.EventListener eventListener) {
            Assertions.checkArgument((eventHandler == null || eventListener == null) ? false : true);
            this.eventHandler = eventHandler;
            this.eventListener = eventListener;
            return this;
        }

        public Builder setSlidingWindowMaxWeight(int slidingWindowMaxWeight) {
            this.slidingWindowMaxWeight = slidingWindowMaxWeight;
            return this;
        }

        public Builder setInitialBitrateEstimate(long initialBitrateEstimate) {
            this.initialBitrateEstimate = initialBitrateEstimate;
            return this;
        }

        public Builder setClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public DefaultBandwidthMeter build() {
            return new DefaultBandwidthMeter(this.eventHandler, this.eventListener, this.initialBitrateEstimate, this.slidingWindowMaxWeight, this.clock);
        }
    }

    public DefaultBandwidthMeter() {
        this(null, null, 1000000L, 2000, Clock.DEFAULT);
    }

    @Deprecated
    public DefaultBandwidthMeter(Handler eventHandler, BandwidthMeter.EventListener eventListener) {
        this(eventHandler, eventListener, 1000000L, 2000, Clock.DEFAULT);
    }

    @Deprecated
    public DefaultBandwidthMeter(Handler eventHandler, BandwidthMeter.EventListener eventListener, int maxWeight) {
        this(eventHandler, eventListener, 1000000L, maxWeight, Clock.DEFAULT);
    }

    private DefaultBandwidthMeter(@Nullable Handler eventHandler, @Nullable BandwidthMeter.EventListener eventListener, long initialBitrateEstimate, int maxWeight, Clock clock) {
        this.eventHandler = eventHandler;
        this.eventListener = eventListener;
        this.slidingPercentile = new SlidingPercentile(maxWeight);
        this.clock = clock;
        this.bitrateEstimate = initialBitrateEstimate;
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public synchronized long getBitrateEstimate() {
        return this.bitrateEstimate;
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onTransferStart(Object source, DataSpec dataSpec) {
        if (this.streamCount == 0) {
            this.sampleStartTimeMs = this.clock.elapsedRealtime();
        }
        this.streamCount++;
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onBytesTransferred(Object source, int bytes) {
        this.sampleBytesTransferred += bytes;
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onTransferEnd(Object source) {
        Assertions.checkState(this.streamCount > 0);
        long nowMs = this.clock.elapsedRealtime();
        int sampleElapsedTimeMs = (int) (nowMs - this.sampleStartTimeMs);
        this.totalElapsedTimeMs += sampleElapsedTimeMs;
        this.totalBytesTransferred += this.sampleBytesTransferred;
        if (sampleElapsedTimeMs > 0) {
            float bitsPerSecond = (float) ((this.sampleBytesTransferred * 8000) / sampleElapsedTimeMs);
            this.slidingPercentile.addSample((int) Math.sqrt(this.sampleBytesTransferred), bitsPerSecond);
            if (this.totalElapsedTimeMs >= AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS || this.totalBytesTransferred >= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                this.bitrateEstimate = this.slidingPercentile.getPercentile(0.5f);
            }
        }
        notifyBandwidthSample(sampleElapsedTimeMs, this.sampleBytesTransferred, this.bitrateEstimate);
        int i = this.streamCount - 1;
        this.streamCount = i;
        if (i > 0) {
            this.sampleStartTimeMs = nowMs;
        }
        this.sampleBytesTransferred = 0L;
    }

    private void notifyBandwidthSample(final int elapsedMs, final long bytes, final long bitrate) {
        if (this.eventHandler != null && this.eventListener != null) {
            this.eventHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter.1
                @Override // java.lang.Runnable
                public void run() {
                    DefaultBandwidthMeter.this.eventListener.onBandwidthSample(elapsedMs, bytes, bitrate);
                }
            });
        }
    }
}
