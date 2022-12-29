package com.google.android.exoplayer2.util;

import kotlin.UByte;

/* loaded from: classes.dex */
public final class ParsableNalUnitBitArray {
    private int bitOffset;
    private int byteLimit;
    private int byteOffset;
    private byte[] data;

    public ParsableNalUnitBitArray(byte[] data, int offset, int limit) {
        reset(data, offset, limit);
    }

    public void reset(byte[] data, int offset, int limit) {
        this.data = data;
        this.byteOffset = offset;
        this.byteLimit = limit;
        this.bitOffset = 0;
        assertValidOffset();
    }

    public void skipBit() {
        int i = this.bitOffset + 1;
        this.bitOffset = i;
        if (i == 8) {
            this.bitOffset = 0;
            this.byteOffset = (shouldSkipByte(this.byteOffset + 1) ? 2 : 1) + this.byteOffset;
        }
        assertValidOffset();
    }

    public void skipBits(int numBits) {
        int oldByteOffset = this.byteOffset;
        int numBytes = numBits / 8;
        this.byteOffset += numBytes;
        this.bitOffset += numBits - (numBytes * 8);
        if (this.bitOffset > 7) {
            this.byteOffset++;
            this.bitOffset -= 8;
        }
        int i = oldByteOffset + 1;
        while (i <= this.byteOffset) {
            if (shouldSkipByte(i)) {
                this.byteOffset++;
                i += 2;
            }
            i++;
        }
        assertValidOffset();
    }

    public boolean canReadBits(int numBits) {
        int oldByteOffset = this.byteOffset;
        int numBytes = numBits / 8;
        int newByteOffset = this.byteOffset + numBytes;
        int newBitOffset = (this.bitOffset + numBits) - (numBytes * 8);
        if (newBitOffset > 7) {
            newByteOffset++;
            newBitOffset -= 8;
        }
        int i = oldByteOffset + 1;
        while (i <= newByteOffset && newByteOffset < this.byteLimit) {
            if (shouldSkipByte(i)) {
                newByteOffset++;
                i += 2;
            }
            i++;
        }
        return newByteOffset < this.byteLimit || (newByteOffset == this.byteLimit && newBitOffset == 0);
    }

    public boolean readBit() {
        boolean returnValue = (this.data[this.byteOffset] & (128 >> this.bitOffset)) != 0;
        skipBit();
        return returnValue;
    }

    public int readBits(int numBits) {
        int returnValue = 0;
        this.bitOffset += numBits;
        while (this.bitOffset > 8) {
            this.bitOffset -= 8;
            returnValue |= (this.data[this.byteOffset] & UByte.MAX_VALUE) << this.bitOffset;
            this.byteOffset = (shouldSkipByte(this.byteOffset + 1) ? 2 : 1) + this.byteOffset;
        }
        int returnValue2 = (returnValue | ((this.data[this.byteOffset] & UByte.MAX_VALUE) >> (8 - this.bitOffset))) & ((-1) >>> (32 - numBits));
        if (this.bitOffset == 8) {
            this.bitOffset = 0;
            this.byteOffset += shouldSkipByte(this.byteOffset + 1) ? 2 : 1;
        }
        assertValidOffset();
        return returnValue2;
    }

    public boolean canReadExpGolombCodedNum() {
        int initialByteOffset = this.byteOffset;
        int initialBitOffset = this.bitOffset;
        int leadingZeros = 0;
        while (this.byteOffset < this.byteLimit && !readBit()) {
            leadingZeros++;
        }
        boolean hitLimit = this.byteOffset == this.byteLimit;
        this.byteOffset = initialByteOffset;
        this.bitOffset = initialBitOffset;
        return !hitLimit && canReadBits((leadingZeros * 2) + 1);
    }

    public int readUnsignedExpGolombCodedInt() {
        return readExpGolombCodeNum();
    }

    public int readSignedExpGolombCodedInt() {
        int codeNum = readExpGolombCodeNum();
        return (codeNum % 2 == 0 ? -1 : 1) * ((codeNum + 1) / 2);
    }

    private int readExpGolombCodeNum() {
        int leadingZeros = 0;
        while (!readBit()) {
            leadingZeros++;
        }
        return (leadingZeros > 0 ? readBits(leadingZeros) : 0) + ((1 << leadingZeros) - 1);
    }

    private boolean shouldSkipByte(int offset) {
        return 2 <= offset && offset < this.byteLimit && this.data[offset] == 3 && this.data[offset + (-2)] == 0 && this.data[offset + (-1)] == 0;
    }

    private void assertValidOffset() {
        Assertions.checkState(this.byteOffset >= 0 && (this.byteOffset < this.byteLimit || (this.byteOffset == this.byteLimit && this.bitOffset == 0)));
    }
}
