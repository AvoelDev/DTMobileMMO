package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.source.TrackGroupArray;
import java.util.List;

/* loaded from: classes.dex */
public final class ProgressiveDownloadHelper extends DownloadHelper {
    @Nullable
    private final String customCacheKey;
    private final Uri uri;

    public ProgressiveDownloadHelper(Uri uri) {
        this(uri, null);
    }

    public ProgressiveDownloadHelper(Uri uri, @Nullable String customCacheKey) {
        this.uri = uri;
        this.customCacheKey = customCacheKey;
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    protected void prepareInternal() {
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    public int getPeriodCount() {
        return 1;
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    public TrackGroupArray getTrackGroups(int periodIndex) {
        return TrackGroupArray.EMPTY;
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    public DownloadAction getDownloadAction(@Nullable byte[] data, List<TrackKey> trackKeys) {
        return new ProgressiveDownloadAction(this.uri, false, data, this.customCacheKey);
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    public DownloadAction getRemoveAction(@Nullable byte[] data) {
        return new ProgressiveDownloadAction(this.uri, true, data, this.customCacheKey);
    }
}
