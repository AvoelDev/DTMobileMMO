package com.google.android.exoplayer2.source.hls.offline;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.offline.TrackKey;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.source.hls.playlist.RenditionKey;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

/* loaded from: classes.dex */
public final class HlsDownloadHelper extends DownloadHelper {
    private final DataSource.Factory manifestDataSourceFactory;
    @MonotonicNonNull
    private HlsPlaylist playlist;
    private int[] renditionTypes;
    private final Uri uri;

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    public /* bridge */ /* synthetic */ DownloadAction getDownloadAction(@Nullable byte[] bArr, List list) {
        return getDownloadAction(bArr, (List<TrackKey>) list);
    }

    public HlsDownloadHelper(Uri uri, DataSource.Factory manifestDataSourceFactory) {
        this.uri = uri;
        this.manifestDataSourceFactory = manifestDataSourceFactory;
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    protected void prepareInternal() throws IOException {
        DataSource dataSource = this.manifestDataSourceFactory.createDataSource();
        this.playlist = (HlsPlaylist) ParsingLoadable.load(dataSource, new HlsPlaylistParser(), this.uri);
    }

    public HlsPlaylist getPlaylist() {
        Assertions.checkNotNull(this.playlist);
        return this.playlist;
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    public int getPeriodCount() {
        Assertions.checkNotNull(this.playlist);
        return 1;
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    public TrackGroupArray getTrackGroups(int periodIndex) {
        Assertions.checkNotNull(this.playlist);
        if (this.playlist instanceof HlsMediaPlaylist) {
            this.renditionTypes = new int[0];
            return TrackGroupArray.EMPTY;
        }
        HlsMasterPlaylist masterPlaylist = (HlsMasterPlaylist) this.playlist;
        TrackGroup[] trackGroups = new TrackGroup[3];
        this.renditionTypes = new int[3];
        int trackGroupIndex = 0;
        if (!masterPlaylist.variants.isEmpty()) {
            this.renditionTypes[0] = 0;
            int trackGroupIndex2 = 0 + 1;
            trackGroups[0] = new TrackGroup(toFormats(masterPlaylist.variants));
            trackGroupIndex = trackGroupIndex2;
        }
        if (!masterPlaylist.audios.isEmpty()) {
            this.renditionTypes[trackGroupIndex] = 1;
            trackGroups[trackGroupIndex] = new TrackGroup(toFormats(masterPlaylist.audios));
            trackGroupIndex++;
        }
        if (!masterPlaylist.subtitles.isEmpty()) {
            this.renditionTypes[trackGroupIndex] = 2;
            trackGroups[trackGroupIndex] = new TrackGroup(toFormats(masterPlaylist.subtitles));
            trackGroupIndex++;
        }
        return new TrackGroupArray((TrackGroup[]) Arrays.copyOf(trackGroups, trackGroupIndex));
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    public HlsDownloadAction getDownloadAction(@Nullable byte[] data, List<TrackKey> trackKeys) {
        Assertions.checkNotNull(this.renditionTypes);
        return new HlsDownloadAction(this.uri, false, data, toRenditionKeys(trackKeys, this.renditionTypes));
    }

    @Override // com.google.android.exoplayer2.offline.DownloadHelper
    public HlsDownloadAction getRemoveAction(@Nullable byte[] data) {
        return new HlsDownloadAction(this.uri, true, data, Collections.emptyList());
    }

    private static Format[] toFormats(List<HlsMasterPlaylist.HlsUrl> hlsUrls) {
        Format[] formats = new Format[hlsUrls.size()];
        for (int i = 0; i < hlsUrls.size(); i++) {
            formats[i] = hlsUrls.get(i).format;
        }
        return formats;
    }

    private static List<RenditionKey> toRenditionKeys(List<TrackKey> trackKeys, int[] groups) {
        List<RenditionKey> representationKeys = new ArrayList<>(trackKeys.size());
        for (int i = 0; i < trackKeys.size(); i++) {
            TrackKey trackKey = trackKeys.get(i);
            representationKeys.add(new RenditionKey(groups[trackKey.groupIndex], trackKey.trackIndex));
        }
        return representationKeys;
    }
}
