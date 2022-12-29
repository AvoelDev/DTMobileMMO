package com.google.android.exoplayer2.text.pgs;

import android.graphics.Bitmap;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* loaded from: classes.dex */
public final class PgsDecoder extends SimpleSubtitleDecoder {
    private static final byte INFLATE_HEADER = 120;
    private static final int SECTION_TYPE_BITMAP_PICTURE = 21;
    private static final int SECTION_TYPE_END = 128;
    private static final int SECTION_TYPE_IDENTIFIER = 22;
    private static final int SECTION_TYPE_PALETTE = 20;
    private final ParsableByteArray buffer;
    private final CueBuilder cueBuilder;
    private byte[] inflatedData;
    private int inflatedDataSize;
    private Inflater inflater;

    public PgsDecoder() {
        super("PgsDecoder");
        this.buffer = new ParsableByteArray();
        this.cueBuilder = new CueBuilder();
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] data, int size, boolean reset) throws SubtitleDecoderException {
        if (maybeInflateData(data, size)) {
            this.buffer.reset(this.inflatedData, this.inflatedDataSize);
        } else {
            this.buffer.reset(data, size);
        }
        this.cueBuilder.reset();
        ArrayList<Cue> cues = new ArrayList<>();
        while (this.buffer.bytesLeft() >= 3) {
            Cue cue = readNextSection(this.buffer, this.cueBuilder);
            if (cue != null) {
                cues.add(cue);
            }
        }
        return new PgsSubtitle(Collections.unmodifiableList(cues));
    }

    private boolean maybeInflateData(byte[] data, int size) {
        boolean z = false;
        if (size != 0 && data[0] == 120) {
            if (this.inflater == null) {
                this.inflater = new Inflater();
                this.inflatedData = new byte[size];
            }
            this.inflatedDataSize = 0;
            this.inflater.setInput(data, 0, size);
            while (!this.inflater.finished() && !this.inflater.needsDictionary() && !this.inflater.needsInput()) {
                try {
                    if (this.inflatedDataSize == this.inflatedData.length) {
                        this.inflatedData = Arrays.copyOf(this.inflatedData, this.inflatedData.length * 2);
                    }
                    this.inflatedDataSize += this.inflater.inflate(this.inflatedData, this.inflatedDataSize, this.inflatedData.length - this.inflatedDataSize);
                } catch (DataFormatException e) {
                } finally {
                    this.inflater.reset();
                }
            }
            z = this.inflater.finished();
        }
        return z;
    }

    private static Cue readNextSection(ParsableByteArray buffer, CueBuilder cueBuilder) {
        int limit = buffer.limit();
        int sectionType = buffer.readUnsignedByte();
        int sectionLength = buffer.readUnsignedShort();
        int nextSectionPosition = buffer.getPosition() + sectionLength;
        if (nextSectionPosition > limit) {
            buffer.setPosition(limit);
            return null;
        }
        Cue cue = null;
        switch (sectionType) {
            case 20:
                cueBuilder.parsePaletteSection(buffer, sectionLength);
                break;
            case 21:
                cueBuilder.parseBitmapSection(buffer, sectionLength);
                break;
            case 22:
                cueBuilder.parseIdentifierSection(buffer, sectionLength);
                break;
            case 128:
                cue = cueBuilder.build();
                cueBuilder.reset();
                break;
        }
        buffer.setPosition(nextSectionPosition);
        return cue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class CueBuilder {
        private int bitmapHeight;
        private int bitmapWidth;
        private int bitmapX;
        private int bitmapY;
        private boolean colorsSet;
        private int planeHeight;
        private int planeWidth;
        private final ParsableByteArray bitmapData = new ParsableByteArray();
        private final int[] colors = new int[256];

        /* JADX INFO: Access modifiers changed from: private */
        public void parsePaletteSection(ParsableByteArray buffer, int sectionLength) {
            if (sectionLength % 5 == 2) {
                buffer.skipBytes(2);
                Arrays.fill(this.colors, 0);
                int entryCount = sectionLength / 5;
                for (int i = 0; i < entryCount; i++) {
                    int index = buffer.readUnsignedByte();
                    int y = buffer.readUnsignedByte();
                    int cr = buffer.readUnsignedByte();
                    int cb = buffer.readUnsignedByte();
                    int a = buffer.readUnsignedByte();
                    int r = (int) (y + (1.402d * (cr - 128)));
                    int g = (int) ((y - (0.34414d * (cb - 128))) - (0.71414d * (cr - 128)));
                    int b = (int) (y + (1.772d * (cb - 128)));
                    this.colors[index] = (a << 24) | (Util.constrainValue(r, 0, 255) << 16) | (Util.constrainValue(g, 0, 255) << 8) | Util.constrainValue(b, 0, 255);
                }
                this.colorsSet = true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parseBitmapSection(ParsableByteArray buffer, int sectionLength) {
            int totalLength;
            if (sectionLength >= 4) {
                buffer.skipBytes(3);
                boolean isBaseSection = (buffer.readUnsignedByte() & 128) != 0;
                int sectionLength2 = sectionLength - 4;
                if (isBaseSection) {
                    if (sectionLength2 >= 7 && (totalLength = buffer.readUnsignedInt24()) >= 4) {
                        this.bitmapWidth = buffer.readUnsignedShort();
                        this.bitmapHeight = buffer.readUnsignedShort();
                        this.bitmapData.reset(totalLength - 4);
                        sectionLength2 -= 7;
                    } else {
                        return;
                    }
                }
                int position = this.bitmapData.getPosition();
                int limit = this.bitmapData.limit();
                if (position < limit && sectionLength2 > 0) {
                    int bytesToRead = Math.min(sectionLength2, limit - position);
                    buffer.readBytes(this.bitmapData.data, position, bytesToRead);
                    this.bitmapData.setPosition(position + bytesToRead);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void parseIdentifierSection(ParsableByteArray buffer, int sectionLength) {
            if (sectionLength >= 19) {
                this.planeWidth = buffer.readUnsignedShort();
                this.planeHeight = buffer.readUnsignedShort();
                buffer.skipBytes(11);
                this.bitmapX = buffer.readUnsignedShort();
                this.bitmapY = buffer.readUnsignedShort();
            }
        }

        public Cue build() {
            if (this.planeWidth == 0 || this.planeHeight == 0 || this.bitmapWidth == 0 || this.bitmapHeight == 0 || this.bitmapData.limit() == 0 || this.bitmapData.getPosition() != this.bitmapData.limit() || !this.colorsSet) {
                return null;
            }
            this.bitmapData.setPosition(0);
            int[] argbBitmapData = new int[this.bitmapWidth * this.bitmapHeight];
            int argbBitmapDataIndex = 0;
            while (argbBitmapDataIndex < argbBitmapData.length) {
                int colorIndex = this.bitmapData.readUnsignedByte();
                if (colorIndex != 0) {
                    argbBitmapData[argbBitmapDataIndex] = this.colors[colorIndex];
                    argbBitmapDataIndex++;
                } else {
                    int switchBits = this.bitmapData.readUnsignedByte();
                    if (switchBits != 0) {
                        int runLength = (switchBits & 64) == 0 ? switchBits & 63 : ((switchBits & 63) << 8) | this.bitmapData.readUnsignedByte();
                        int color = (switchBits & 128) == 0 ? 0 : this.colors[this.bitmapData.readUnsignedByte()];
                        Arrays.fill(argbBitmapData, argbBitmapDataIndex, argbBitmapDataIndex + runLength, color);
                        argbBitmapDataIndex += runLength;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(argbBitmapData, this.bitmapWidth, this.bitmapHeight, Bitmap.Config.ARGB_8888);
            return new Cue(bitmap, this.bitmapX / this.planeWidth, 0, this.bitmapY / this.planeHeight, 0, this.bitmapWidth / this.planeWidth, this.bitmapHeight / this.planeHeight);
        }

        public void reset() {
            this.planeWidth = 0;
            this.planeHeight = 0;
            this.bitmapX = 0;
            this.bitmapY = 0;
            this.bitmapWidth = 0;
            this.bitmapHeight = 0;
            this.bitmapData.reset(0);
            this.colorsSet = false;
        }
    }
}
