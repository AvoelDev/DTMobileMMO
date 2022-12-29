package com.google.android.exoplayer2;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public final class PlaybackParameters {
    public static final PlaybackParameters DEFAULT = new PlaybackParameters(1.0f);
    public final float pitch;
    private final int scaledUsPerMs;
    public final boolean skipSilence;
    public final float speed;

    public PlaybackParameters(float speed) {
        this(speed, 1.0f, false);
    }

    public PlaybackParameters(float speed, float pitch) {
        this(speed, pitch, false);
    }

    public PlaybackParameters(float speed, float pitch, boolean skipSilence) {
        Assertions.checkArgument(speed > 0.0f);
        Assertions.checkArgument(pitch > 0.0f);
        this.speed = speed;
        this.pitch = pitch;
        this.skipSilence = skipSilence;
        this.scaledUsPerMs = Math.round(1000.0f * speed);
    }

    public long getMediaTimeUsForPlayoutTimeMs(long timeMs) {
        return this.scaledUsPerMs * timeMs;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PlaybackParameters other = (PlaybackParameters) obj;
        return this.speed == other.speed && this.pitch == other.pitch && this.skipSilence == other.skipSilence;
    }

    public int hashCode() {
        int result = Float.floatToRawIntBits(this.speed) + 527;
        return (((result * 31) + Float.floatToRawIntBits(this.pitch)) * 31) + (this.skipSilence ? 1 : 0);
    }
}
