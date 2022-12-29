package com.google.android.exoplayer2.source.hls.offline;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.offline.SegmentDownloadAction;
import com.google.android.exoplayer2.source.hls.playlist.RenditionKey;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public final class HlsDownloadAction extends SegmentDownloadAction<RenditionKey> {
    private static final int VERSION = 0;
    private static final String TYPE = "hls";
    public static final DownloadAction.Deserializer DESERIALIZER = new SegmentDownloadAction.SegmentDownloadActionDeserializer<RenditionKey>(TYPE, 0) { // from class: com.google.android.exoplayer2.source.hls.offline.HlsDownloadAction.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.android.exoplayer2.offline.SegmentDownloadAction.SegmentDownloadActionDeserializer
        public RenditionKey readKey(DataInputStream input) throws IOException {
            int renditionGroup = input.readInt();
            int trackIndex = input.readInt();
            return new RenditionKey(renditionGroup, trackIndex);
        }

        @Override // com.google.android.exoplayer2.offline.SegmentDownloadAction.SegmentDownloadActionDeserializer
        protected DownloadAction createDownloadAction(Uri uri, boolean isRemoveAction, byte[] data, List<RenditionKey> keys) {
            return new HlsDownloadAction(uri, isRemoveAction, data, keys);
        }
    };

    public HlsDownloadAction(Uri uri, boolean isRemoveAction, @Nullable byte[] data, List<RenditionKey> keys) {
        super(TYPE, 0, uri, isRemoveAction, data, keys);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.offline.DownloadAction
    public HlsDownloader createDownloader(DownloaderConstructorHelper constructorHelper) {
        return new HlsDownloader(this.uri, this.keys, constructorHelper);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.offline.SegmentDownloadAction
    public void writeKey(DataOutputStream output, RenditionKey key) throws IOException {
        output.writeInt(key.type);
        output.writeInt(key.trackIndex);
    }
}
