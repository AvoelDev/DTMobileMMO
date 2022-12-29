package com.google.android.exoplayer2.source.hls.playlist;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.MimeTypes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class HlsMasterPlaylist extends HlsPlaylist {
    public final List<HlsUrl> audios;
    public final Format muxedAudioFormat;
    public final List<Format> muxedCaptionFormats;
    public final List<HlsUrl> subtitles;
    public final List<HlsUrl> variants;

    /* loaded from: classes.dex */
    public static final class HlsUrl {
        public final Format format;
        public final String url;

        public static HlsUrl createMediaPlaylistHlsUrl(String url) {
            Format format = Format.createContainerFormat("0", MimeTypes.APPLICATION_M3U8, null, null, -1, 0, null);
            return new HlsUrl(url, format);
        }

        public HlsUrl(String url, Format format) {
            this.url = url;
            this.format = format;
        }
    }

    public HlsMasterPlaylist(String baseUri, List<String> tags, List<HlsUrl> variants, List<HlsUrl> audios, List<HlsUrl> subtitles, Format muxedAudioFormat, List<Format> muxedCaptionFormats) {
        super(baseUri, tags);
        this.variants = Collections.unmodifiableList(variants);
        this.audios = Collections.unmodifiableList(audios);
        this.subtitles = Collections.unmodifiableList(subtitles);
        this.muxedAudioFormat = muxedAudioFormat;
        this.muxedCaptionFormats = muxedCaptionFormats != null ? Collections.unmodifiableList(muxedCaptionFormats) : null;
    }

    @Override // com.google.android.exoplayer2.offline.FilterableManifest
    /* renamed from: copy */
    public HlsPlaylist copy2(List<RenditionKey> renditionKeys) {
        return new HlsMasterPlaylist(this.baseUri, this.tags, copyRenditionsList(this.variants, 0, renditionKeys), copyRenditionsList(this.audios, 1, renditionKeys), copyRenditionsList(this.subtitles, 2, renditionKeys), this.muxedAudioFormat, this.muxedCaptionFormats);
    }

    public static HlsMasterPlaylist createSingleVariantMasterPlaylist(String variantUrl) {
        List<HlsUrl> variant = Collections.singletonList(HlsUrl.createMediaPlaylistHlsUrl(variantUrl));
        List<HlsUrl> emptyList = Collections.emptyList();
        return new HlsMasterPlaylist(null, Collections.emptyList(), variant, emptyList, emptyList, null, null);
    }

    private static List<HlsUrl> copyRenditionsList(List<HlsUrl> renditions, int renditionType, List<RenditionKey> renditionKeys) {
        List<HlsUrl> copiedRenditions = new ArrayList<>(renditionKeys.size());
        for (int i = 0; i < renditions.size(); i++) {
            HlsUrl rendition = renditions.get(i);
            int j = 0;
            while (true) {
                if (j < renditionKeys.size()) {
                    RenditionKey renditionKey = renditionKeys.get(j);
                    if (renditionKey.type != renditionType || renditionKey.trackIndex != i) {
                        j++;
                    } else {
                        copiedRenditions.add(rendition);
                        break;
                    }
                }
            }
        }
        return copiedRenditions;
    }
}
