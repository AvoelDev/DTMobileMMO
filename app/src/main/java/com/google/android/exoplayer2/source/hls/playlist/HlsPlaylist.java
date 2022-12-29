package com.google.android.exoplayer2.source.hls.playlist;

import com.google.android.exoplayer2.offline.FilterableManifest;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class HlsPlaylist implements FilterableManifest<HlsPlaylist, RenditionKey> {
    public final String baseUri;
    public final List<String> tags;

    /* JADX INFO: Access modifiers changed from: protected */
    public HlsPlaylist(String baseUri, List<String> tags) {
        this.baseUri = baseUri;
        this.tags = Collections.unmodifiableList(tags);
    }
}
