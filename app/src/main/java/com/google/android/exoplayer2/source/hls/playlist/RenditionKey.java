package com.google.android.exoplayer2.source.hls.playlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class RenditionKey implements Comparable<RenditionKey> {
    public static final int TYPE_AUDIO = 1;
    public static final int TYPE_SUBTITLE = 2;
    public static final int TYPE_VARIANT = 0;
    public final int trackIndex;
    public final int type;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Type {
    }

    public RenditionKey(int type, int trackIndex) {
        this.type = type;
        this.trackIndex = trackIndex;
    }

    public String toString() {
        return this.type + "." + this.trackIndex;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RenditionKey that = (RenditionKey) o;
        return this.type == that.type && this.trackIndex == that.trackIndex;
    }

    public int hashCode() {
        int result = this.type;
        return (result * 31) + this.trackIndex;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NonNull RenditionKey other) {
        int result = this.type - other.type;
        if (result == 0) {
            return this.trackIndex - other.trackIndex;
        }
        return result;
    }
}
