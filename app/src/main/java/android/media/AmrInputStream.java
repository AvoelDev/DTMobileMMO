package android.media;

import com.ssj.ssjsdklib.SsjSDK;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;

/* loaded from: classes.dex */
public final class AmrInputStream extends InputStream {
    private static final int SAMPLES_PER_FRAME;
    private static final String TAG = "AmrInputStream";
    private InputStream mInputStream;
    private byte[] mBuf = new byte[SAMPLES_PER_FRAME * 2];
    private int mBufIn = 0;
    private int mBufOut = 0;
    private byte[] mOneByte = new byte[1];
    private int mGae = GsmAmrEncoderNew();

    private static native void GsmAmrEncoderCleanup(int i);

    private static native void GsmAmrEncoderDelete(int i);

    private static native int GsmAmrEncoderEncode(int i, byte[] bArr, int i2, byte[] bArr2, int i3) throws IOException;

    private static native void GsmAmrEncoderInitialize(int i);

    private static native int GsmAmrEncoderNew();

    static {
        System.loadLibrary("media_jni");
        SAMPLES_PER_FRAME = (SsjSDK.SAmpleRate * 20) / 1000;
    }

    public AmrInputStream(InputStream inputStream) {
        this.mInputStream = inputStream;
        GsmAmrEncoderInitialize(this.mGae);
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int rtn = read(this.mOneByte, 0, 1);
        if (rtn == 1) {
            return this.mOneByte[0] & UByte.MAX_VALUE;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] b, int offset, int length) throws IOException {
        if (this.mGae == 0) {
            throw new IllegalStateException("not open");
        }
        if (this.mBufOut >= this.mBufIn) {
            this.mBufOut = 0;
            this.mBufIn = 0;
            int i = 0;
            while (i < SAMPLES_PER_FRAME * 2) {
                int n = this.mInputStream.read(this.mBuf, i, (SAMPLES_PER_FRAME * 2) - i);
                if (n == -1) {
                    return -1;
                }
                i += n;
            }
            this.mBufIn = GsmAmrEncoderEncode(this.mGae, this.mBuf, 0, this.mBuf, 0);
        }
        if (length > this.mBufIn - this.mBufOut) {
            length = this.mBufIn - this.mBufOut;
        }
        System.arraycopy(this.mBuf, this.mBufOut, b, offset, length);
        this.mBufOut += length;
        return length;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            if (this.mInputStream != null) {
                this.mInputStream.close();
            }
            this.mInputStream = null;
            try {
                if (this.mGae != 0) {
                    GsmAmrEncoderCleanup(this.mGae);
                }
                try {
                    if (this.mGae != 0) {
                        GsmAmrEncoderDelete(this.mGae);
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    if (this.mGae != 0) {
                        GsmAmrEncoderDelete(this.mGae);
                    }
                    throw th;
                } finally {
                }
            }
        } catch (Throwable th2) {
            this.mInputStream = null;
            try {
                if (this.mGae != 0) {
                    GsmAmrEncoderCleanup(this.mGae);
                }
                try {
                    if (this.mGae != 0) {
                        GsmAmrEncoderDelete(this.mGae);
                    }
                    throw th2;
                } finally {
                }
            } catch (Throwable th3) {
                try {
                    if (this.mGae != 0) {
                        GsmAmrEncoderDelete(this.mGae);
                    }
                    throw th3;
                } finally {
                }
            }
        }
    }

    protected void finalize() throws Throwable {
        if (this.mGae != 0) {
            close();
            throw new IllegalStateException("someone forgot to close AmrInputStream");
        }
    }
}
