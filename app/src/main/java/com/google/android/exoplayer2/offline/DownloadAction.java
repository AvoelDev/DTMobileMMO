package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.support.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/* loaded from: classes.dex */
public abstract class DownloadAction {
    public final byte[] data;
    public final boolean isRemoveAction;
    public final String type;
    public final Uri uri;
    public final int version;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract Downloader createDownloader(DownloaderConstructorHelper downloaderConstructorHelper);

    protected abstract void writeToStream(DataOutputStream dataOutputStream) throws IOException;

    /* loaded from: classes.dex */
    public static abstract class Deserializer {
        public final String type;
        public final int version;

        public abstract DownloadAction readFromStream(int i, DataInputStream dataInputStream) throws IOException;

        public Deserializer(String type, int version) {
            this.type = type;
            this.version = version;
        }
    }

    public static DownloadAction deserializeFromStream(Deserializer[] deserializers, InputStream input) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(input);
        String type = dataInputStream.readUTF();
        int version = dataInputStream.readInt();
        for (Deserializer deserializer : deserializers) {
            if (type.equals(deserializer.type) && deserializer.version >= version) {
                return deserializer.readFromStream(version, dataInputStream);
            }
        }
        throw new DownloadException("No deserializer found for:" + type + ", " + version);
    }

    public static void serializeToStream(DownloadAction action, OutputStream output) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(output);
        dataOutputStream.writeUTF(action.type);
        dataOutputStream.writeInt(action.version);
        action.writeToStream(dataOutputStream);
        dataOutputStream.flush();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DownloadAction(String type, int version, Uri uri, boolean isRemoveAction, @Nullable byte[] data) {
        this.type = type;
        this.version = version;
        this.uri = uri;
        this.isRemoveAction = isRemoveAction;
        this.data = data == null ? new byte[0] : data;
    }

    public final byte[] toByteArray() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            serializeToStream(this, output);
            return output.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public boolean isSameMedia(DownloadAction other) {
        return this.uri.equals(other.uri);
    }

    public boolean equals(@Nullable Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DownloadAction that = (DownloadAction) o;
        return this.type.equals(that.type) && this.version == that.version && this.uri.equals(that.uri) && this.isRemoveAction == that.isRemoveAction && Arrays.equals(this.data, that.data);
    }

    public int hashCode() {
        int result = this.uri.hashCode();
        return (((result * 31) + (this.isRemoveAction ? 1 : 0)) * 31) + Arrays.hashCode(this.data);
    }
}
