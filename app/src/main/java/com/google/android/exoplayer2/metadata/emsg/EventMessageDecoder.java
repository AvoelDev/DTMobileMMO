package com.google.android.exoplayer2.metadata.emsg;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataDecoder;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class EventMessageDecoder implements MetadataDecoder {
    @Override // com.google.android.exoplayer2.metadata.MetadataDecoder
    public Metadata decode(MetadataInputBuffer inputBuffer) {
        ByteBuffer buffer = inputBuffer.data;
        byte[] data = buffer.array();
        int size = buffer.limit();
        ParsableByteArray emsgData = new ParsableByteArray(data, size);
        String schemeIdUri = emsgData.readNullTerminatedString();
        String value = emsgData.readNullTerminatedString();
        long timescale = emsgData.readUnsignedInt();
        long presentationTimeUs = Util.scaleLargeTimestamp(emsgData.readUnsignedInt(), 1000000L, timescale);
        long durationMs = Util.scaleLargeTimestamp(emsgData.readUnsignedInt(), 1000L, timescale);
        long id = emsgData.readUnsignedInt();
        byte[] messageData = Arrays.copyOfRange(data, emsgData.getPosition(), size);
        return new Metadata(new EventMessage(schemeIdUri, value, durationMs, id, messageData, presentationTimeUs));
    }
}
