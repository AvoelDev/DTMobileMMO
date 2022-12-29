package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import kotlin.UByte;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.net.telnet.TelnetCommand;

/* loaded from: classes.dex */
public final class Ac3Util {
    private static final int AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT = 1536;
    private static final int AUDIO_SAMPLES_PER_AUDIO_BLOCK = 256;
    public static final int TRUEHD_RECHUNK_SAMPLE_COUNT = 16;
    public static final int TRUEHD_SYNCFRAME_PREFIX_LENGTH = 10;
    private static final int[] BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD = {1, 2, 3, 6};
    private static final int[] SAMPLE_RATE_BY_FSCOD = {48000, 44100, 32000};
    private static final int[] SAMPLE_RATE_BY_FSCOD2 = {24000, 22050, 16000};
    private static final int[] CHANNEL_COUNT_BY_ACMOD = {2, 1, 2, 3, 3, 4, 4, 5};
    private static final int[] BITRATE_BY_HALF_FRMSIZECOD = {32, 40, 48, 56, 64, 80, 96, 112, 128, 160, PsExtractor.AUDIO_STREAM, 224, 256, 320, 384, 448, 512, 576, 640};
    private static final int[] SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1 = {69, 87, 104, 121, 139, 174, 208, TelnetCommand.BREAK, 278, 348, HttpStatus.SC_EXPECTATION_FAILED, 487, 557, 696, 835, 975, 1114, 1253, 1393};

    /* loaded from: classes.dex */
    public static final class SyncFrameInfo {
        public static final int STREAM_TYPE_TYPE0 = 0;
        public static final int STREAM_TYPE_TYPE1 = 1;
        public static final int STREAM_TYPE_TYPE2 = 2;
        public static final int STREAM_TYPE_UNDEFINED = -1;
        public final int channelCount;
        public final int frameSize;
        public final String mimeType;
        public final int sampleCount;
        public final int sampleRate;
        public final int streamType;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes.dex */
        public @interface StreamType {
        }

        private SyncFrameInfo(String mimeType, int streamType, int channelCount, int sampleRate, int frameSize, int sampleCount) {
            this.mimeType = mimeType;
            this.streamType = streamType;
            this.channelCount = channelCount;
            this.sampleRate = sampleRate;
            this.frameSize = frameSize;
            this.sampleCount = sampleCount;
        }
    }

    public static Format parseAc3AnnexFFormat(ParsableByteArray data, String trackId, String language, DrmInitData drmInitData) {
        int fscod = (data.readUnsignedByte() & PsExtractor.AUDIO_STREAM) >> 6;
        int sampleRate = SAMPLE_RATE_BY_FSCOD[fscod];
        int nextByte = data.readUnsignedByte();
        int channelCount = CHANNEL_COUNT_BY_ACMOD[(nextByte & 56) >> 3];
        if ((nextByte & 4) != 0) {
            channelCount++;
        }
        return Format.createAudioSampleFormat(trackId, MimeTypes.AUDIO_AC3, null, -1, -1, channelCount, sampleRate, null, drmInitData, 0, language);
    }

    public static Format parseEAc3AnnexFFormat(ParsableByteArray data, String trackId, String language, DrmInitData drmInitData) {
        data.skipBytes(2);
        int fscod = (data.readUnsignedByte() & PsExtractor.AUDIO_STREAM) >> 6;
        int sampleRate = SAMPLE_RATE_BY_FSCOD[fscod];
        int nextByte = data.readUnsignedByte();
        int channelCount = CHANNEL_COUNT_BY_ACMOD[(nextByte & 14) >> 1];
        if ((nextByte & 1) != 0) {
            channelCount++;
        }
        int numDepSub = (data.readUnsignedByte() & 30) >> 1;
        if (numDepSub > 0) {
            int lowByteChanLoc = data.readUnsignedByte();
            if ((lowByteChanLoc & 2) != 0) {
                channelCount += 2;
            }
        }
        String mimeType = MimeTypes.AUDIO_E_AC3;
        if (data.bytesLeft() > 0 && (data.readUnsignedByte() & 1) != 0) {
            mimeType = MimeTypes.AUDIO_E_AC3_JOC;
        }
        return Format.createAudioSampleFormat(trackId, mimeType, null, -1, -1, channelCount, sampleRate, null, drmInitData, 0, language);
    }

    public static SyncFrameInfo parseAc3SyncframeInfo(ParsableBitArray data) {
        String mimeType;
        int frameSize;
        int sampleRate;
        int sampleCount;
        int channelCount;
        int numblkscod;
        int audioBlocks;
        int initialPosition = data.getPosition();
        data.skipBits(40);
        boolean isEac3 = data.readBits(5) == 16;
        data.setPosition(initialPosition);
        int streamType = -1;
        if (isEac3) {
            data.skipBits(16);
            switch (data.readBits(2)) {
                case 0:
                    streamType = 0;
                    break;
                case 1:
                    streamType = 1;
                    break;
                case 2:
                    streamType = 2;
                    break;
                default:
                    streamType = -1;
                    break;
            }
            data.skipBits(3);
            frameSize = (data.readBits(11) + 1) * 2;
            int fscod = data.readBits(2);
            if (fscod == 3) {
                numblkscod = 3;
                sampleRate = SAMPLE_RATE_BY_FSCOD2[data.readBits(2)];
                audioBlocks = 6;
            } else {
                numblkscod = data.readBits(2);
                audioBlocks = BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[numblkscod];
                sampleRate = SAMPLE_RATE_BY_FSCOD[fscod];
            }
            sampleCount = audioBlocks * 256;
            int acmod = data.readBits(3);
            boolean lfeon = data.readBit();
            channelCount = CHANNEL_COUNT_BY_ACMOD[acmod] + (lfeon ? 1 : 0);
            data.skipBits(10);
            if (data.readBit()) {
                data.skipBits(8);
            }
            if (acmod == 0) {
                data.skipBits(5);
                if (data.readBit()) {
                    data.skipBits(8);
                }
            }
            if (streamType == 1 && data.readBit()) {
                data.skipBits(16);
            }
            if (data.readBit()) {
                if (acmod > 2) {
                    data.skipBits(2);
                }
                if ((acmod & 1) != 0 && acmod > 2) {
                    data.skipBits(6);
                }
                if ((acmod & 4) != 0) {
                    data.skipBits(6);
                }
                if (lfeon && data.readBit()) {
                    data.skipBits(5);
                }
                if (streamType == 0) {
                    if (data.readBit()) {
                        data.skipBits(6);
                    }
                    if (acmod == 0 && data.readBit()) {
                        data.skipBits(6);
                    }
                    if (data.readBit()) {
                        data.skipBits(6);
                    }
                    int mixdef = data.readBits(2);
                    if (mixdef == 1) {
                        data.skipBits(5);
                    } else if (mixdef == 2) {
                        data.skipBits(12);
                    } else if (mixdef == 3) {
                        int mixdeflen = data.readBits(5);
                        if (data.readBit()) {
                            data.skipBits(5);
                            if (data.readBit()) {
                                data.skipBits(4);
                            }
                            if (data.readBit()) {
                                data.skipBits(4);
                            }
                            if (data.readBit()) {
                                data.skipBits(4);
                            }
                            if (data.readBit()) {
                                data.skipBits(4);
                            }
                            if (data.readBit()) {
                                data.skipBits(4);
                            }
                            if (data.readBit()) {
                                data.skipBits(4);
                            }
                            if (data.readBit()) {
                                data.skipBits(4);
                            }
                            if (data.readBit()) {
                                if (data.readBit()) {
                                    data.skipBits(4);
                                }
                                if (data.readBit()) {
                                    data.skipBits(4);
                                }
                            }
                        }
                        if (data.readBit()) {
                            data.skipBits(5);
                            if (data.readBit()) {
                                data.skipBits(7);
                                if (data.readBit()) {
                                    data.skipBits(8);
                                }
                            }
                        }
                        data.skipBits((mixdeflen + 2) * 8);
                        data.byteAlign();
                    }
                    if (acmod < 2) {
                        if (data.readBit()) {
                            data.skipBits(14);
                        }
                        if (acmod == 0 && data.readBit()) {
                            data.skipBits(14);
                        }
                    }
                    if (data.readBit()) {
                        if (numblkscod == 0) {
                            data.skipBits(5);
                        } else {
                            for (int blk = 0; blk < audioBlocks; blk++) {
                                if (data.readBit()) {
                                    data.skipBits(5);
                                }
                            }
                        }
                    }
                }
            }
            if (data.readBit()) {
                data.skipBits(5);
                if (acmod == 2) {
                    data.skipBits(4);
                }
                if (acmod >= 6) {
                    data.skipBits(2);
                }
                if (data.readBit()) {
                    data.skipBits(8);
                }
                if (acmod == 0 && data.readBit()) {
                    data.skipBits(8);
                }
                if (fscod < 3) {
                    data.skipBit();
                }
            }
            if (streamType == 0 && numblkscod != 3) {
                data.skipBit();
            }
            if (streamType == 2 && (numblkscod == 3 || data.readBit())) {
                data.skipBits(6);
            }
            mimeType = MimeTypes.AUDIO_E_AC3;
            if (data.readBit()) {
                int addbsil = data.readBits(6);
                if (addbsil == 1 && data.readBits(8) == 1) {
                    mimeType = MimeTypes.AUDIO_E_AC3_JOC;
                }
            }
        } else {
            mimeType = MimeTypes.AUDIO_AC3;
            data.skipBits(32);
            int fscod2 = data.readBits(2);
            int frmsizecod = data.readBits(6);
            frameSize = getAc3SyncframeSize(fscod2, frmsizecod);
            data.skipBits(8);
            int acmod2 = data.readBits(3);
            if ((acmod2 & 1) != 0 && acmod2 != 1) {
                data.skipBits(2);
            }
            if ((acmod2 & 4) != 0) {
                data.skipBits(2);
            }
            if (acmod2 == 2) {
                data.skipBits(2);
            }
            sampleRate = SAMPLE_RATE_BY_FSCOD[fscod2];
            sampleCount = AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT;
            channelCount = CHANNEL_COUNT_BY_ACMOD[acmod2] + (data.readBit() ? 1 : 0);
        }
        return new SyncFrameInfo(mimeType, streamType, channelCount, sampleRate, frameSize, sampleCount);
    }

    public static int parseAc3SyncframeSize(byte[] data) {
        if (data.length < 5) {
            return -1;
        }
        int fscod = (data[4] & 192) >> 6;
        int frmsizecod = data[4] & 63;
        return getAc3SyncframeSize(fscod, frmsizecod);
    }

    public static int getAc3SyncframeAudioSampleCount() {
        return AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT;
    }

    public static int parseEAc3SyncframeAudioSampleCount(ByteBuffer buffer) {
        int fscod = (buffer.get(buffer.position() + 4) & 192) >> 6;
        return (fscod == 3 ? 6 : BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[(buffer.get(buffer.position() + 4) & 48) >> 4]) * 256;
    }

    public static int findTrueHdSyncframeOffset(ByteBuffer buffer) {
        int startIndex = buffer.position();
        int endIndex = buffer.limit() - 10;
        for (int i = startIndex; i <= endIndex; i++) {
            if ((buffer.getInt(i + 4) & (-16777217)) == -1167101192) {
                return i - startIndex;
            }
        }
        return -1;
    }

    public static int parseTrueHdSyncframeAudioSampleCount(byte[] syncframe) {
        if (syncframe[4] == -8 && syncframe[5] == 114 && syncframe[6] == 111 && (syncframe[7] & 254) == 186) {
            boolean isMlp = (syncframe[7] & UByte.MAX_VALUE) == 187;
            return 40 << ((syncframe[isMlp ? '\t' : '\b'] >> 4) & 7);
        }
        return 0;
    }

    public static int parseTrueHdSyncframeAudioSampleCount(ByteBuffer buffer, int offset) {
        boolean isMlp = (buffer.get((buffer.position() + offset) + 7) & UByte.MAX_VALUE) == 187;
        return 40 << ((buffer.get((isMlp ? 9 : 8) + (buffer.position() + offset)) >> 4) & 7);
    }

    private static int getAc3SyncframeSize(int fscod, int frmsizecod) {
        int halfFrmsizecod = frmsizecod / 2;
        if (fscod < 0 || fscod >= SAMPLE_RATE_BY_FSCOD.length || frmsizecod < 0 || halfFrmsizecod >= SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1.length) {
            return -1;
        }
        int sampleRate = SAMPLE_RATE_BY_FSCOD[fscod];
        if (sampleRate == 44100) {
            return (SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1[halfFrmsizecod] + (frmsizecod % 2)) * 2;
        }
        int bitrate = BITRATE_BY_HALF_FRMSIZECOD[halfFrmsizecod];
        if (sampleRate == 32000) {
            return bitrate * 6;
        }
        return bitrate * 4;
    }

    private Ac3Util() {
    }
}
