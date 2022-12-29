package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.util.Assertions;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class SegmentDownloadAction<K extends Comparable<K>> extends DownloadAction {
    public final List<K> keys;

    protected abstract void writeKey(DataOutputStream dataOutputStream, K k) throws IOException;

    /* loaded from: classes.dex */
    protected static abstract class SegmentDownloadActionDeserializer<K> extends DownloadAction.Deserializer {
        protected abstract DownloadAction createDownloadAction(Uri uri, boolean z, byte[] bArr, List<K> list);

        protected abstract K readKey(DataInputStream dataInputStream) throws IOException;

        public SegmentDownloadActionDeserializer(String type, int version) {
            super(type, version);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadAction.Deserializer
        public final DownloadAction readFromStream(int version, DataInputStream input) throws IOException {
            Uri uri = Uri.parse(input.readUTF());
            boolean isRemoveAction = input.readBoolean();
            int dataLength = input.readInt();
            byte[] data = new byte[dataLength];
            input.readFully(data);
            int keyCount = input.readInt();
            List<K> keys = new ArrayList<>();
            for (int i = 0; i < keyCount; i++) {
                keys.add(readKey(input));
            }
            return createDownloadAction(uri, isRemoveAction, data, keys);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SegmentDownloadAction(String type, int version, Uri uri, boolean isRemoveAction, @Nullable byte[] data, List<K> keys) {
        super(type, version, uri, isRemoveAction, data);
        if (isRemoveAction) {
            Assertions.checkArgument(keys.isEmpty());
            this.keys = Collections.emptyList();
            return;
        }
        ArrayList<K> mutableKeys = new ArrayList<>(keys);
        Collections.sort(mutableKeys);
        this.keys = Collections.unmodifiableList(mutableKeys);
    }

    @Override // com.google.android.exoplayer2.offline.DownloadAction
    public final void writeToStream(DataOutputStream output) throws IOException {
        output.writeUTF(this.uri.toString());
        output.writeBoolean(this.isRemoveAction);
        output.writeInt(this.data.length);
        output.write(this.data);
        output.writeInt(this.keys.size());
        for (int i = 0; i < this.keys.size(); i++) {
            writeKey(output, this.keys.get(i));
        }
    }

    @Override // com.google.android.exoplayer2.offline.DownloadAction
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        SegmentDownloadAction<?> that = (SegmentDownloadAction) o;
        return this.keys.equals(that.keys);
    }

    @Override // com.google.android.exoplayer2.offline.DownloadAction
    public int hashCode() {
        int result = super.hashCode();
        return (result * 31) + this.keys.hashCode();
    }
}
