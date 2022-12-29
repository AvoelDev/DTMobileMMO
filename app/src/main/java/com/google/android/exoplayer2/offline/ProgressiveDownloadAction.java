package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public final class ProgressiveDownloadAction extends DownloadAction {
    private static final int VERSION = 0;
    @Nullable
    public final String customCacheKey;
    private static final String TYPE = "progressive";
    public static final DownloadAction.Deserializer DESERIALIZER = new DownloadAction.Deserializer(TYPE, 0) { // from class: com.google.android.exoplayer2.offline.ProgressiveDownloadAction.1
        @Override // com.google.android.exoplayer2.offline.DownloadAction.Deserializer
        public ProgressiveDownloadAction readFromStream(int version, DataInputStream input) throws IOException {
            Uri uri = Uri.parse(input.readUTF());
            boolean isRemoveAction = input.readBoolean();
            int dataLength = input.readInt();
            byte[] data = new byte[dataLength];
            input.readFully(data);
            String customCacheKey = input.readBoolean() ? input.readUTF() : null;
            return new ProgressiveDownloadAction(uri, isRemoveAction, data, customCacheKey);
        }
    };

    public ProgressiveDownloadAction(Uri uri, boolean isRemoveAction, @Nullable byte[] data, @Nullable String customCacheKey) {
        super(TYPE, 0, uri, isRemoveAction, data);
        this.customCacheKey = customCacheKey;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.offline.DownloadAction
    public ProgressiveDownloader createDownloader(DownloaderConstructorHelper constructorHelper) {
        return new ProgressiveDownloader(this.uri, this.customCacheKey, constructorHelper);
    }

    @Override // com.google.android.exoplayer2.offline.DownloadAction
    protected void writeToStream(DataOutputStream output) throws IOException {
        output.writeUTF(this.uri.toString());
        output.writeBoolean(this.isRemoveAction);
        output.writeInt(this.data.length);
        output.write(this.data);
        boolean customCacheKeySet = this.customCacheKey != null;
        output.writeBoolean(customCacheKeySet);
        if (customCacheKeySet) {
            output.writeUTF(this.customCacheKey);
        }
    }

    @Override // com.google.android.exoplayer2.offline.DownloadAction
    public boolean isSameMedia(DownloadAction other) {
        return (other instanceof ProgressiveDownloadAction) && getCacheKey().equals(((ProgressiveDownloadAction) other).getCacheKey());
    }

    @Override // com.google.android.exoplayer2.offline.DownloadAction
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        ProgressiveDownloadAction that = (ProgressiveDownloadAction) o;
        return Util.areEqual(this.customCacheKey, that.customCacheKey);
    }

    @Override // com.google.android.exoplayer2.offline.DownloadAction
    public int hashCode() {
        int result = super.hashCode();
        return (result * 31) + (this.customCacheKey != null ? this.customCacheKey.hashCode() : 0);
    }

    private String getCacheKey() {
        return this.customCacheKey != null ? this.customCacheKey : CacheUtil.generateKey(this.uri);
    }
}
