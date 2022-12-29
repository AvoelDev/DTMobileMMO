package com.google.android.exoplayer2.source.hls.offline;

import android.net.Uri;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.source.hls.playlist.RenditionKey;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.UriUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class HlsDownloader extends SegmentDownloader<HlsPlaylist, RenditionKey> {
    public HlsDownloader(Uri playlistUri, List<RenditionKey> renditionKeys, DownloaderConstructorHelper constructorHelper) {
        super(playlistUri, renditionKeys, constructorHelper);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.offline.SegmentDownloader
    public HlsPlaylist getManifest(DataSource dataSource, Uri uri) throws IOException {
        return loadManifest(dataSource, uri);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.offline.SegmentDownloader
    public List<SegmentDownloader.Segment> getSegments(DataSource dataSource, HlsPlaylist playlist, boolean allowIncompleteList) throws IOException {
        ArrayList<Uri> mediaPlaylistUris = new ArrayList<>();
        if (playlist instanceof HlsMasterPlaylist) {
            HlsMasterPlaylist masterPlaylist = (HlsMasterPlaylist) playlist;
            addResolvedUris(masterPlaylist.baseUri, masterPlaylist.variants, mediaPlaylistUris);
            addResolvedUris(masterPlaylist.baseUri, masterPlaylist.audios, mediaPlaylistUris);
            addResolvedUris(masterPlaylist.baseUri, masterPlaylist.subtitles, mediaPlaylistUris);
        } else {
            mediaPlaylistUris.add(Uri.parse(playlist.baseUri));
        }
        ArrayList<SegmentDownloader.Segment> segments = new ArrayList<>();
        HashSet<Uri> seenEncryptionKeyUris = new HashSet<>();
        Iterator<Uri> it = mediaPlaylistUris.iterator();
        while (it.hasNext()) {
            Uri mediaPlaylistUri = it.next();
            try {
                HlsMediaPlaylist mediaPlaylist = (HlsMediaPlaylist) loadManifest(dataSource, mediaPlaylistUri);
                segments.add(new SegmentDownloader.Segment(mediaPlaylist.startTimeUs, new DataSpec(mediaPlaylistUri)));
                HlsMediaPlaylist.Segment lastInitSegment = null;
                List<HlsMediaPlaylist.Segment> hlsSegments = mediaPlaylist.segments;
                for (int i = 0; i < hlsSegments.size(); i++) {
                    HlsMediaPlaylist.Segment segment = hlsSegments.get(i);
                    HlsMediaPlaylist.Segment initSegment = segment.initializationSegment;
                    if (initSegment != null && initSegment != lastInitSegment) {
                        lastInitSegment = initSegment;
                        addSegment(segments, mediaPlaylist, initSegment, seenEncryptionKeyUris);
                    }
                    addSegment(segments, mediaPlaylist, segment, seenEncryptionKeyUris);
                }
            } catch (IOException e) {
                if (!allowIncompleteList) {
                    throw e;
                }
                segments.add(new SegmentDownloader.Segment(0L, new DataSpec(mediaPlaylistUri)));
            }
        }
        return segments;
    }

    private static HlsPlaylist loadManifest(DataSource dataSource, Uri uri) throws IOException {
        ParsingLoadable<HlsPlaylist> loadable = new ParsingLoadable<>(dataSource, uri, 4, new HlsPlaylistParser());
        loadable.load();
        return loadable.getResult();
    }

    private static void addSegment(ArrayList<SegmentDownloader.Segment> segments, HlsMediaPlaylist mediaPlaylist, HlsMediaPlaylist.Segment hlsSegment, HashSet<Uri> seenEncryptionKeyUris) {
        long startTimeUs = mediaPlaylist.startTimeUs + hlsSegment.relativeStartTimeUs;
        if (hlsSegment.fullSegmentEncryptionKeyUri != null) {
            Uri keyUri = UriUtil.resolveToUri(mediaPlaylist.baseUri, hlsSegment.fullSegmentEncryptionKeyUri);
            if (seenEncryptionKeyUris.add(keyUri)) {
                segments.add(new SegmentDownloader.Segment(startTimeUs, new DataSpec(keyUri)));
            }
        }
        Uri resolvedUri = UriUtil.resolveToUri(mediaPlaylist.baseUri, hlsSegment.url);
        segments.add(new SegmentDownloader.Segment(startTimeUs, new DataSpec(resolvedUri, hlsSegment.byterangeOffset, hlsSegment.byterangeLength, null)));
    }

    private static void addResolvedUris(String baseUri, List<HlsMasterPlaylist.HlsUrl> urls, List<Uri> out) {
        for (int i = 0; i < urls.size(); i++) {
            out.add(UriUtil.resolveToUri(baseUri, urls.get(i).url));
        }
    }
}
