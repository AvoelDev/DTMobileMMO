package com.google.android.exoplayer2;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public final class SeekParameters {
    public final long toleranceAfterUs;
    public final long toleranceBeforeUs;
    public static final SeekParameters EXACT = new SeekParameters(0, 0);
    public static final SeekParameters CLOSEST_SYNC = new SeekParameters(Long.MAX_VALUE, Long.MAX_VALUE);
    public static final SeekParameters PREVIOUS_SYNC = new SeekParameters(Long.MAX_VALUE, 0);
    public static final SeekParameters NEXT_SYNC = new SeekParameters(0, Long.MAX_VALUE);
    public static final SeekParameters DEFAULT = EXACT;

    public SeekParameters(long toleranceBeforeUs, long toleranceAfterUs) {
        Assertions.checkArgument(toleranceBeforeUs >= 0);
        Assertions.checkArgument(toleranceAfterUs >= 0);
        this.toleranceBeforeUs = toleranceBeforeUs;
        this.toleranceAfterUs = toleranceAfterUs;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SeekParameters other = (SeekParameters) obj;
        return this.toleranceBeforeUs == other.toleranceBeforeUs && this.toleranceAfterUs == other.toleranceAfterUs;
    }

    public int hashCode() {
        return (((int) this.toleranceBeforeUs) * 31) + ((int) this.toleranceAfterUs);
    }
}
