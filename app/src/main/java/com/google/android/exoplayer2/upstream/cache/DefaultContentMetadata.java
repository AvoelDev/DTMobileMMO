package com.google.android.exoplayer2.upstream.cache;

import android.support.annotation.Nullable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class DefaultContentMetadata implements ContentMetadata {
    public static final DefaultContentMetadata EMPTY = new DefaultContentMetadata(Collections.emptyMap());
    private static final int MAX_VALUE_LENGTH = 10485760;
    private int hashCode;
    private final Map<String, byte[]> metadata;

    public static DefaultContentMetadata readFromStream(DataInputStream input) throws IOException {
        int size = input.readInt();
        HashMap<String, byte[]> metadata = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String name = input.readUTF();
            int valueSize = input.readInt();
            if (valueSize < 0 || valueSize > MAX_VALUE_LENGTH) {
                throw new IOException("Invalid value size: " + valueSize);
            }
            byte[] value = new byte[valueSize];
            input.readFully(value);
            metadata.put(name, value);
        }
        return new DefaultContentMetadata(metadata);
    }

    private DefaultContentMetadata(Map<String, byte[]> metadata) {
        this.metadata = Collections.unmodifiableMap(metadata);
    }

    public DefaultContentMetadata copyWithMutationsApplied(ContentMetadataMutations mutations) {
        Map<String, byte[]> mutatedMetadata = applyMutations(this.metadata, mutations);
        return isMetadataEqual(mutatedMetadata) ? this : new DefaultContentMetadata(mutatedMetadata);
    }

    public void writeToStream(DataOutputStream output) throws IOException {
        output.writeInt(this.metadata.size());
        for (Map.Entry<String, byte[]> entry : this.metadata.entrySet()) {
            output.writeUTF(entry.getKey());
            byte[] value = entry.getValue();
            output.writeInt(value.length);
            output.write(value);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.ContentMetadata
    public final byte[] get(String name, byte[] defaultValue) {
        if (this.metadata.containsKey(name)) {
            byte[] bytes = this.metadata.get(name);
            byte[] defaultValue2 = Arrays.copyOf(bytes, bytes.length);
            return defaultValue2;
        }
        return defaultValue;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.ContentMetadata
    public final String get(String name, String defaultValue) {
        if (this.metadata.containsKey(name)) {
            byte[] bytes = this.metadata.get(name);
            return new String(bytes, Charset.forName("UTF-8"));
        }
        return defaultValue;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.ContentMetadata
    public final long get(String name, long defaultValue) {
        if (this.metadata.containsKey(name)) {
            byte[] bytes = this.metadata.get(name);
            long defaultValue2 = ByteBuffer.wrap(bytes).getLong();
            return defaultValue2;
        }
        return defaultValue;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.ContentMetadata
    public final boolean contains(String name) {
        return this.metadata.containsKey(name);
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return isMetadataEqual(((DefaultContentMetadata) o).metadata);
    }

    private boolean isMetadataEqual(Map<String, byte[]> otherMetadata) {
        if (this.metadata.size() != otherMetadata.size()) {
            return false;
        }
        for (Map.Entry<String, byte[]> entry : this.metadata.entrySet()) {
            byte[] value = entry.getValue();
            byte[] otherValue = otherMetadata.get(entry.getKey());
            if (!Arrays.equals(value, otherValue)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            int result = 0;
            for (Map.Entry<String, byte[]> entry : this.metadata.entrySet()) {
                result += Arrays.hashCode(entry.getValue()) ^ entry.getKey().hashCode();
            }
            this.hashCode = result;
        }
        return this.hashCode;
    }

    private static Map<String, byte[]> applyMutations(Map<String, byte[]> otherMetadata, ContentMetadataMutations mutations) {
        HashMap<String, byte[]> metadata = new HashMap<>(otherMetadata);
        removeValues(metadata, mutations.getRemovedValues());
        addValues(metadata, mutations.getEditedValues());
        return metadata;
    }

    private static void removeValues(HashMap<String, byte[]> metadata, List<String> names) {
        for (int i = 0; i < names.size(); i++) {
            metadata.remove(names.get(i));
        }
    }

    private static void addValues(HashMap<String, byte[]> metadata, Map<String, Object> values) {
        for (String name : values.keySet()) {
            Object value = values.get(name);
            byte[] bytes = getBytes(value);
            if (bytes.length > MAX_VALUE_LENGTH) {
                throw new IllegalArgumentException(String.format("The size of %s (%d) is greater than maximum allowed: %d", name, Integer.valueOf(bytes.length), Integer.valueOf((int) MAX_VALUE_LENGTH)));
            }
            metadata.put(name, bytes);
        }
    }

    private static byte[] getBytes(Object value) {
        if (value instanceof Long) {
            return ByteBuffer.allocate(8).putLong(((Long) value).longValue()).array();
        }
        if (value instanceof String) {
            return ((String) value).getBytes(Charset.forName("UTF-8"));
        }
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        throw new IllegalArgumentException();
    }
}
