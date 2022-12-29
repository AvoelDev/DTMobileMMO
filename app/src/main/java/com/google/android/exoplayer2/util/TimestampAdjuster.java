package com.google.android.exoplayer2.util;

import com.google.android.exoplayer2.C;

/* loaded from: classes.dex */
public final class TimestampAdjuster {
    public static final long DO_NOT_OFFSET = Long.MAX_VALUE;
    private static final long MAX_PTS_PLUS_ONE = 8589934592L;
    private long firstSampleTimestampUs;
    private volatile long lastSampleTimestampUs = C.TIME_UNSET;
    private long timestampOffsetUs;

    public TimestampAdjuster(long firstSampleTimestampUs) {
        setFirstSampleTimestampUs(firstSampleTimestampUs);
    }

    public synchronized void setFirstSampleTimestampUs(long firstSampleTimestampUs) {
        Assertions.checkState(this.lastSampleTimestampUs == C.TIME_UNSET);
        this.firstSampleTimestampUs = firstSampleTimestampUs;
    }

    public long getFirstSampleTimestampUs() {
        return this.firstSampleTimestampUs;
    }

    public long getLastAdjustedTimestampUs() {
        return this.lastSampleTimestampUs != C.TIME_UNSET ? this.lastSampleTimestampUs + this.timestampOffsetUs : this.firstSampleTimestampUs != Long.MAX_VALUE ? this.firstSampleTimestampUs : C.TIME_UNSET;
    }

    public long getTimestampOffsetUs() {
        if (this.firstSampleTimestampUs == Long.MAX_VALUE) {
            return 0L;
        }
        return this.lastSampleTimestampUs != C.TIME_UNSET ? this.timestampOffsetUs : C.TIME_UNSET;
    }

    public void reset() {
        this.lastSampleTimestampUs = C.TIME_UNSET;
    }

    public long adjustTsTimestamp(long pts90Khz) {
        if (pts90Khz == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        if (this.lastSampleTimestampUs != C.TIME_UNSET) {
            long lastPts = usToPts(this.lastSampleTimestampUs);
            long closestWrapCount = (4294967296L + lastPts) / MAX_PTS_PLUS_ONE;
            long ptsWrapBelow = pts90Khz + (MAX_PTS_PLUS_ONE * (closestWrapCount - 1));
            long ptsWrapAbove = pts90Khz + (MAX_PTS_PLUS_ONE * closestWrapCount);
            pts90Khz = Math.abs(ptsWrapBelow - lastPts) < Math.abs(ptsWrapAbove - lastPts) ? ptsWrapBelow : ptsWrapAbove;
        }
        return adjustSampleTimestamp(ptsToUs(pts90Khz));
    }

    public long adjustSampleTimestamp(long timeUs) {
        if (timeUs == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        if (this.lastSampleTimestampUs != C.TIME_UNSET) {
            this.lastSampleTimestampUs = timeUs;
        } else {
            if (this.firstSampleTimestampUs != Long.MAX_VALUE) {
                this.timestampOffsetUs = this.firstSampleTimestampUs - timeUs;
            }
            synchronized (this) {
                this.lastSampleTimestampUs = timeUs;
                notifyAll();
            }
        }
        return this.timestampOffsetUs + timeUs;
    }

    public synchronized void waitUntilInitialized() throws InterruptedException {
        while (this.lastSampleTimestampUs == C.TIME_UNSET) {
            wait();
        }
    }

    public static long ptsToUs(long pts) {
        return (1000000 * pts) / 90000;
    }

    public static long usToPts(long us) {
        return (90000 * us) / 1000000;
    }
}
