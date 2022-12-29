package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.ogg.StreamReader;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.util.FlacStreamInfo;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.ByteCompanionObject;

/* loaded from: classes.dex */
final class FlacReader extends StreamReader {
    private static final byte AUDIO_PACKET_TYPE = -1;
    private static final int FRAME_HEADER_SAMPLE_NUMBER_OFFSET = 4;
    private static final byte SEEKTABLE_PACKET_TYPE = 3;
    private FlacOggSeeker flacOggSeeker;
    private FlacStreamInfo streamInfo;

    public static boolean verifyBitstreamType(ParsableByteArray data) {
        return data.bytesLeft() >= 5 && data.readUnsignedByte() == 127 && data.readUnsignedInt() == 1179402563;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    public void reset(boolean headerData) {
        super.reset(headerData);
        if (headerData) {
            this.streamInfo = null;
            this.flacOggSeeker = null;
        }
    }

    private static boolean isAudioPacket(byte[] data) {
        return data[0] == -1;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    protected long preparePayload(ParsableByteArray packet) {
        if (isAudioPacket(packet.data)) {
            return getFlacFrameBlockSize(packet);
        }
        return -1L;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    protected boolean readHeaders(ParsableByteArray packet, long position, StreamReader.SetupData setupData) throws IOException, InterruptedException {
        byte[] data = packet.data;
        if (this.streamInfo == null) {
            this.streamInfo = new FlacStreamInfo(data, 17);
            byte[] metadata = Arrays.copyOfRange(data, 9, packet.limit());
            metadata[4] = ByteCompanionObject.MIN_VALUE;
            List<byte[]> initializationData = Collections.singletonList(metadata);
            setupData.format = Format.createAudioSampleFormat(null, MimeTypes.AUDIO_FLAC, null, -1, this.streamInfo.bitRate(), this.streamInfo.channels, this.streamInfo.sampleRate, initializationData, null, 0, null);
        } else if ((data[0] & ByteCompanionObject.MAX_VALUE) == 3) {
            this.flacOggSeeker = new FlacOggSeeker();
            this.flacOggSeeker.parseSeekTable(packet);
        } else if (isAudioPacket(data)) {
            if (this.flacOggSeeker != null) {
                this.flacOggSeeker.setFirstFrameOffset(position);
                setupData.oggSeeker = this.flacOggSeeker;
            }
            return false;
        }
        return true;
    }

    private int getFlacFrameBlockSize(ParsableByteArray packet) {
        int blockSizeCode = (packet.data[2] & 255) >> 4;
        switch (blockSizeCode) {
            case 1:
                return PsExtractor.AUDIO_STREAM;
            case 2:
            case 3:
            case 4:
            case 5:
                return 576 << (blockSizeCode - 2);
            case 6:
            case 7:
                packet.skipBytes(4);
                packet.readUtf8EncodedLong();
                int value = blockSizeCode == 6 ? packet.readUnsignedByte() : packet.readUnsignedShort();
                packet.setPosition(0);
                return value + 1;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return 256 << (blockSizeCode - 8);
            default:
                return -1;
        }
    }

    /* loaded from: classes.dex */
    private class FlacOggSeeker implements SeekMap, OggSeeker {
        private static final int METADATA_LENGTH_OFFSET = 1;
        private static final int SEEK_POINT_SIZE = 18;
        private long firstFrameOffset = -1;
        private long pendingSeekGranule = -1;
        private long[] seekPointGranules;
        private long[] seekPointOffsets;

        public FlacOggSeeker() {
        }

        public void setFirstFrameOffset(long firstFrameOffset) {
            this.firstFrameOffset = firstFrameOffset;
        }

        public void parseSeekTable(ParsableByteArray data) {
            data.skipBytes(1);
            int length = data.readUnsignedInt24();
            int numberOfSeekPoints = length / 18;
            this.seekPointGranules = new long[numberOfSeekPoints];
            this.seekPointOffsets = new long[numberOfSeekPoints];
            for (int i = 0; i < numberOfSeekPoints; i++) {
                this.seekPointGranules[i] = data.readLong();
                this.seekPointOffsets[i] = data.readLong();
                data.skipBytes(2);
            }
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.OggSeeker
        public long read(ExtractorInput input) throws IOException, InterruptedException {
            if (this.pendingSeekGranule >= 0) {
                long result = -(this.pendingSeekGranule + 2);
                this.pendingSeekGranule = -1L;
                return result;
            }
            return -1L;
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.OggSeeker
        public long startSeek(long timeUs) {
            long granule = FlacReader.this.convertTimeToGranule(timeUs);
            int index = Util.binarySearchFloor(this.seekPointGranules, granule, true, true);
            this.pendingSeekGranule = this.seekPointGranules[index];
            return granule;
        }

        @Override // com.google.android.exoplayer2.extractor.ogg.OggSeeker
        public SeekMap createSeekMap() {
            return this;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public boolean isSeekable() {
            return true;
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public SeekMap.SeekPoints getSeekPoints(long timeUs) {
            long granule = FlacReader.this.convertTimeToGranule(timeUs);
            int index = Util.binarySearchFloor(this.seekPointGranules, granule, true, true);
            long seekTimeUs = FlacReader.this.convertGranuleToTime(this.seekPointGranules[index]);
            long seekPosition = this.firstFrameOffset + this.seekPointOffsets[index];
            SeekPoint seekPoint = new SeekPoint(seekTimeUs, seekPosition);
            if (seekTimeUs >= timeUs || index == this.seekPointGranules.length - 1) {
                return new SeekMap.SeekPoints(seekPoint);
            }
            long secondSeekTimeUs = FlacReader.this.convertGranuleToTime(this.seekPointGranules[index + 1]);
            long secondSeekPosition = this.firstFrameOffset + this.seekPointOffsets[index + 1];
            SeekPoint secondSeekPoint = new SeekPoint(secondSeekTimeUs, secondSeekPosition);
            return new SeekMap.SeekPoints(seekPoint, secondSeekPoint);
        }

        @Override // com.google.android.exoplayer2.extractor.SeekMap
        public long getDurationUs() {
            return FlacReader.this.streamInfo.durationUs();
        }
    }
}
