package com.google.android.exoplayer2.util;

import kotlin.UByte;

/* loaded from: classes.dex */
public final class ParsableBitArray {
    private int bitOffset;
    private int byteLimit;
    private int byteOffset;
    public byte[] data;

    public ParsableBitArray() {
    }

    public ParsableBitArray(byte[] data) {
        this(data, data.length);
    }

    public ParsableBitArray(byte[] data, int limit) {
        this.data = data;
        this.byteLimit = limit;
    }

    public void reset(byte[] data) {
        reset(data, data.length);
    }

    public void reset(ParsableByteArray parsableByteArray) {
        reset(parsableByteArray.data, parsableByteArray.limit());
        setPosition(parsableByteArray.getPosition() * 8);
    }

    public void reset(byte[] data, int limit) {
        this.data = data;
        this.byteOffset = 0;
        this.bitOffset = 0;
        this.byteLimit = limit;
    }

    public int bitsLeft() {
        return ((this.byteLimit - this.byteOffset) * 8) - this.bitOffset;
    }

    public int getPosition() {
        return (this.byteOffset * 8) + this.bitOffset;
    }

    public int getBytePosition() {
        Assertions.checkState(this.bitOffset == 0);
        return this.byteOffset;
    }

    public void setPosition(int position) {
        this.byteOffset = position / 8;
        this.bitOffset = position - (this.byteOffset * 8);
        assertValidOffset();
    }

    public void skipBit() {
        int i = this.bitOffset + 1;
        this.bitOffset = i;
        if (i == 8) {
            this.bitOffset = 0;
            this.byteOffset++;
        }
        assertValidOffset();
    }

    public void skipBits(int numBits) {
        int numBytes = numBits / 8;
        this.byteOffset += numBytes;
        this.bitOffset += numBits - (numBytes * 8);
        if (this.bitOffset > 7) {
            this.byteOffset++;
            this.bitOffset -= 8;
        }
        assertValidOffset();
    }

    public boolean readBit() {
        boolean returnValue = (this.data[this.byteOffset] & (128 >> this.bitOffset)) != 0;
        skipBit();
        return returnValue;
    }

    public int readBits(int numBits) {
        if (numBits == 0) {
            return 0;
        }
        int returnValue = 0;
        this.bitOffset += numBits;
        while (this.bitOffset > 8) {
            this.bitOffset -= 8;
            byte[] bArr = this.data;
            int i = this.byteOffset;
            this.byteOffset = i + 1;
            returnValue |= (bArr[i] & UByte.MAX_VALUE) << this.bitOffset;
        }
        int returnValue2 = (returnValue | ((this.data[this.byteOffset] & UByte.MAX_VALUE) >> (8 - this.bitOffset))) & ((-1) >>> (32 - numBits));
        if (this.bitOffset == 8) {
            this.bitOffset = 0;
            this.byteOffset++;
        }
        assertValidOffset();
        return returnValue2;
    }

    public void readBits(byte[] buffer, int offset, int numBits) {
        int to = offset + (numBits >> 3);
        for (int i = offset; i < to; i++) {
            byte[] bArr = this.data;
            int i2 = this.byteOffset;
            this.byteOffset = i2 + 1;
            buffer[i] = (byte) (bArr[i2] << this.bitOffset);
            buffer[i] = (byte) (buffer[i] | ((this.data[this.byteOffset] & UByte.MAX_VALUE) >> (8 - this.bitOffset)));
        }
        int bitsLeft = numBits & 7;
        if (bitsLeft != 0) {
            buffer[to] = (byte) (buffer[to] & (255 >> bitsLeft));
            if (this.bitOffset + bitsLeft > 8) {
                byte b = buffer[to];
                byte[] bArr2 = this.data;
                int i3 = this.byteOffset;
                this.byteOffset = i3 + 1;
                buffer[to] = (byte) (b | ((bArr2[i3] & UByte.MAX_VALUE) << this.bitOffset));
                this.bitOffset -= 8;
            }
            this.bitOffset += bitsLeft;
            int lastDataByteTrailingBits = (this.data[this.byteOffset] & UByte.MAX_VALUE) >> (8 - this.bitOffset);
            buffer[to] = (byte) (buffer[to] | ((byte) (lastDataByteTrailingBits << (8 - bitsLeft))));
            if (this.bitOffset == 8) {
                this.bitOffset = 0;
                this.byteOffset++;
            }
            assertValidOffset();
        }
    }

    public void byteAlign() {
        if (this.bitOffset != 0) {
            this.bitOffset = 0;
            this.byteOffset++;
            assertValidOffset();
        }
    }

    public void readBytes(byte[] buffer, int offset, int length) {
        Assertions.checkState(this.bitOffset == 0);
        System.arraycopy(this.data, this.byteOffset, buffer, offset, length);
        this.byteOffset += length;
        assertValidOffset();
    }

    public void skipBytes(int length) {
        Assertions.checkState(this.bitOffset == 0);
        this.byteOffset += length;
        assertValidOffset();
    }

    public void putInt(int value, int numBits) {
        if (numBits < 32) {
            value &= (1 << numBits) - 1;
        }
        int firstByteReadSize = Math.min(8 - this.bitOffset, numBits);
        int firstByteRightPaddingSize = (8 - this.bitOffset) - firstByteReadSize;
        int firstByteBitmask = (65280 >> this.bitOffset) | ((1 << firstByteRightPaddingSize) - 1);
        this.data[this.byteOffset] = (byte) (this.data[this.byteOffset] & firstByteBitmask);
        int firstByteInputBits = value >>> (numBits - firstByteReadSize);
        this.data[this.byteOffset] = (byte) (this.data[this.byteOffset] | (firstByteInputBits << firstByteRightPaddingSize));
        int remainingBitsToRead = numBits - firstByteReadSize;
        int currentByteIndex = this.byteOffset + 1;
        while (true) {
            int currentByteIndex2 = currentByteIndex;
            if (remainingBitsToRead > 8) {
                currentByteIndex = currentByteIndex2 + 1;
                this.data[currentByteIndex2] = (byte) (value >>> (remainingBitsToRead - 8));
                remainingBitsToRead -= 8;
            } else {
                int lastByteRightPaddingSize = 8 - remainingBitsToRead;
                this.data[currentByteIndex2] = (byte) (this.data[currentByteIndex2] & ((1 << lastByteRightPaddingSize) - 1));
                int lastByteInput = value & ((1 << remainingBitsToRead) - 1);
                this.data[currentByteIndex2] = (byte) (this.data[currentByteIndex2] | (lastByteInput << lastByteRightPaddingSize));
                skipBits(numBits);
                assertValidOffset();
                return;
            }
        }
    }

    private void assertValidOffset() {
        Assertions.checkState(this.byteOffset >= 0 && (this.byteOffset < this.byteLimit || (this.byteOffset == this.byteLimit && this.bitOffset == 0)));
    }
}
