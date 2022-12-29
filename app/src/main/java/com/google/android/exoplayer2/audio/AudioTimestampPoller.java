package com.google.android.exoplayer2.audio;

import android.annotation.TargetApi;
import android.media.AudioTimestamp;
import android.media.AudioTrack;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class AudioTimestampPoller {
    private static final int ERROR_POLL_INTERVAL_US = 500000;
    private static final int FAST_POLL_INTERVAL_US = 5000;
    private static final int INITIALIZING_DURATION_US = 500000;
    private static final int SLOW_POLL_INTERVAL_US = 10000000;
    private static final int STATE_ERROR = 4;
    private static final int STATE_INITIALIZING = 0;
    private static final int STATE_NO_TIMESTAMP = 3;
    private static final int STATE_TIMESTAMP = 1;
    private static final int STATE_TIMESTAMP_ADVANCING = 2;
    @Nullable
    private final AudioTimestampV19 audioTimestamp;
    private long initialTimestampPositionFrames;
    private long initializeSystemTimeUs;
    private long lastTimestampSampleTimeUs;
    private long sampleIntervalUs;
    private int state;

    public AudioTimestampPoller(AudioTrack audioTrack) {
        if (Util.SDK_INT >= 19) {
            this.audioTimestamp = new AudioTimestampV19(audioTrack);
            reset();
            return;
        }
        this.audioTimestamp = null;
        updateState(3);
    }

    public boolean maybePollTimestamp(long systemTimeUs) {
        if (this.audioTimestamp == null || systemTimeUs - this.lastTimestampSampleTimeUs < this.sampleIntervalUs) {
            return false;
        }
        this.lastTimestampSampleTimeUs = systemTimeUs;
        boolean updatedTimestamp = this.audioTimestamp.maybeUpdateTimestamp();
        switch (this.state) {
            case 0:
                if (updatedTimestamp) {
                    if (this.audioTimestamp.getTimestampSystemTimeUs() >= this.initializeSystemTimeUs) {
                        this.initialTimestampPositionFrames = this.audioTimestamp.getTimestampPositionFrames();
                        updateState(1);
                        return updatedTimestamp;
                    }
                    return false;
                } else if (systemTimeUs - this.initializeSystemTimeUs > 500000) {
                    updateState(3);
                    return updatedTimestamp;
                } else {
                    return updatedTimestamp;
                }
            case 1:
                if (updatedTimestamp) {
                    long timestampPositionFrames = this.audioTimestamp.getTimestampPositionFrames();
                    if (timestampPositionFrames > this.initialTimestampPositionFrames) {
                        updateState(2);
                        return updatedTimestamp;
                    }
                    return updatedTimestamp;
                }
                reset();
                return updatedTimestamp;
            case 2:
                if (!updatedTimestamp) {
                    reset();
                    return updatedTimestamp;
                }
                return updatedTimestamp;
            case 3:
                if (updatedTimestamp) {
                    reset();
                    return updatedTimestamp;
                }
                return updatedTimestamp;
            case 4:
                return updatedTimestamp;
            default:
                throw new IllegalStateException();
        }
    }

    public void rejectTimestamp() {
        updateState(4);
    }

    public void acceptTimestamp() {
        if (this.state == 4) {
            reset();
        }
    }

    public boolean hasTimestamp() {
        return this.state == 1 || this.state == 2;
    }

    public boolean isTimestampAdvancing() {
        return this.state == 2;
    }

    public void reset() {
        if (this.audioTimestamp != null) {
            updateState(0);
        }
    }

    public long getTimestampSystemTimeUs() {
        return this.audioTimestamp != null ? this.audioTimestamp.getTimestampSystemTimeUs() : C.TIME_UNSET;
    }

    public long getTimestampPositionFrames() {
        if (this.audioTimestamp != null) {
            return this.audioTimestamp.getTimestampPositionFrames();
        }
        return -1L;
    }

    private void updateState(int state) {
        this.state = state;
        switch (state) {
            case 0:
                this.lastTimestampSampleTimeUs = 0L;
                this.initialTimestampPositionFrames = -1L;
                this.initializeSystemTimeUs = System.nanoTime() / 1000;
                this.sampleIntervalUs = 5000L;
                return;
            case 1:
                this.sampleIntervalUs = 5000L;
                return;
            case 2:
            case 3:
                this.sampleIntervalUs = 10000000L;
                return;
            case 4:
                this.sampleIntervalUs = 500000L;
                return;
            default:
                throw new IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(19)
    /* loaded from: classes.dex */
    public static final class AudioTimestampV19 {
        private final AudioTimestamp audioTimestamp = new AudioTimestamp();
        private final AudioTrack audioTrack;
        private long lastTimestampPositionFrames;
        private long lastTimestampRawPositionFrames;
        private long rawTimestampFramePositionWrapCount;

        public AudioTimestampV19(AudioTrack audioTrack) {
            this.audioTrack = audioTrack;
        }

        public boolean maybeUpdateTimestamp() {
            boolean updated = this.audioTrack.getTimestamp(this.audioTimestamp);
            if (updated) {
                long rawPositionFrames = this.audioTimestamp.framePosition;
                if (this.lastTimestampRawPositionFrames > rawPositionFrames) {
                    this.rawTimestampFramePositionWrapCount++;
                }
                this.lastTimestampRawPositionFrames = rawPositionFrames;
                this.lastTimestampPositionFrames = (this.rawTimestampFramePositionWrapCount << 32) + rawPositionFrames;
            }
            return updated;
        }

        public long getTimestampSystemTimeUs() {
            return this.audioTimestamp.nanoTime / 1000;
        }

        public long getTimestampPositionFrames() {
            return this.lastTimestampPositionFrames;
        }
    }
}
