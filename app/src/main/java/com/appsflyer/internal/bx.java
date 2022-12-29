package com.appsflyer.internal;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;

/* loaded from: classes3.dex */
public final class bx extends FilterInputStream {
    private final int AFInAppEventParameterName;
    private short AFInAppEventType;
    private long[] AFKeystoreWrapper;
    private int AppsFlyer2dXConversionCallback;
    private int getLevel;
    private int init;
    private byte[] valueOf;
    private long[] values;

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final boolean markSupported() {
        return false;
    }

    public bx(InputStream inputStream, int i, int i2, short s, int i3, int i4) throws IOException {
        super(inputStream);
        this.AppsFlyer2dXConversionCallback = Integer.MAX_VALUE;
        this.AFInAppEventParameterName = Math.min(Math.max((int) s, 4), 8);
        int i5 = this.AFInAppEventParameterName;
        this.valueOf = new byte[i5];
        this.AFKeystoreWrapper = new long[4];
        this.values = new long[4];
        this.getLevel = i5;
        this.init = i5;
        this.AFKeystoreWrapper = cb.values(i ^ i4, i5 ^ i4);
        this.values = cb.values(i2 ^ i4, i3 ^ i4);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read() throws IOException {
        AFKeystoreWrapper();
        int i = this.getLevel;
        if (i >= this.init) {
            return -1;
        }
        byte[] bArr = this.valueOf;
        this.getLevel = i + 1;
        return bArr[i] & UByte.MAX_VALUE;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = i + i2;
        for (int i4 = i; i4 < i3; i4++) {
            AFKeystoreWrapper();
            int i5 = this.getLevel;
            if (i5 >= this.init) {
                if (i4 == i) {
                    return -1;
                }
                return i2 - (i3 - i4);
            }
            byte[] bArr2 = this.valueOf;
            this.getLevel = i5 + 1;
            bArr[i4] = bArr2[i5];
        }
        return i2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final long skip(long j) throws IOException {
        long j2 = 0;
        while (j2 < j && read() != -1) {
            j2++;
        }
        return j2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public final int available() throws IOException {
        AFKeystoreWrapper();
        return this.init - this.getLevel;
    }

    private void AFInAppEventParameterName() {
        long[] jArr = this.AFKeystoreWrapper;
        long[] jArr2 = this.values;
        short s = this.AFInAppEventType;
        int i = (s + 2) % 4;
        int i2 = (s + 3) % 4;
        jArr2[i2] = ((jArr[i2] * 2147483085) + jArr2[i]) / 2147483647L;
        jArr[i2] = ((jArr[s % 4] * 2147483085) + jArr2[i]) % 2147483647L;
        for (int i3 = 0; i3 < this.AFInAppEventParameterName; i3++) {
            byte[] bArr = this.valueOf;
            bArr[i3] = (byte) (bArr[i3] ^ ((this.AFKeystoreWrapper[this.AFInAppEventType] >> (i3 << 3)) & 255));
        }
        this.AFInAppEventType = (short) ((this.AFInAppEventType + 1) % 4);
    }

    private int AFKeystoreWrapper() throws IOException {
        int i;
        if (this.AppsFlyer2dXConversionCallback == Integer.MAX_VALUE) {
            this.AppsFlyer2dXConversionCallback = ((FilterInputStream) this).in.read();
        }
        if (this.getLevel == this.AFInAppEventParameterName) {
            byte[] bArr = this.valueOf;
            int i2 = this.AppsFlyer2dXConversionCallback;
            bArr[0] = (byte) i2;
            if (i2 < 0) {
                throw new IllegalStateException("unexpected block size");
            }
            int i3 = 1;
            do {
                int read = ((FilterInputStream) this).in.read(this.valueOf, i3, this.AFInAppEventParameterName - i3);
                if (read <= 0) {
                    break;
                }
                i3 += read;
            } while (i3 < this.AFInAppEventParameterName);
            if (i3 < this.AFInAppEventParameterName) {
                throw new IllegalStateException("unexpected block size");
            }
            AFInAppEventParameterName();
            this.AppsFlyer2dXConversionCallback = ((FilterInputStream) this).in.read();
            this.getLevel = 0;
            if (this.AppsFlyer2dXConversionCallback < 0) {
                int i4 = this.AFInAppEventParameterName;
                i = i4 - (this.valueOf[i4 - 1] & UByte.MAX_VALUE);
            } else {
                i = this.AFInAppEventParameterName;
            }
            this.init = i;
        }
        return this.init;
    }
}
