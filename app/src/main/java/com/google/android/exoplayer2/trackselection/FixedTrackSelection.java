package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public final class FixedTrackSelection extends BaseTrackSelection {
    @Nullable
    private final Object data;
    private final int reason;

    /* loaded from: classes.dex */
    public static final class Factory implements TrackSelection.Factory {
        @Nullable
        private final Object data;
        private final int reason;

        public Factory() {
            this.reason = 0;
            this.data = null;
        }

        public Factory(int reason, @Nullable Object data) {
            this.reason = reason;
            this.data = data;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelection.Factory
        public FixedTrackSelection createTrackSelection(TrackGroup group, int... tracks) {
            Assertions.checkArgument(tracks.length == 1);
            return new FixedTrackSelection(group, tracks[0], this.reason, this.data);
        }
    }

    public FixedTrackSelection(TrackGroup group, int track) {
        this(group, track, 0, null);
    }

    public FixedTrackSelection(TrackGroup group, int track, int reason, @Nullable Object data) {
        super(group, track);
        this.reason = reason;
        this.data = data;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs) {
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public int getSelectedIndex() {
        return 0;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    public int getSelectionReason() {
        return this.reason;
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelection
    @Nullable
    public Object getSelectionData() {
        return this.data;
    }
}
