package com.google.android.exoplayer2.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLDisplay;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EGLSurfaceTexture;
import com.google.android.exoplayer2.util.Util;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

@TargetApi(17)
/* loaded from: classes.dex */
public final class DummySurface extends Surface {
    private static final String EXTENSION_PROTECTED_CONTENT = "EGL_EXT_protected_content";
    private static final String EXTENSION_SURFACELESS_CONTEXT = "EGL_KHR_surfaceless_context";
    private static final String TAG = "DummySurface";
    private static int secureMode;
    private static boolean secureModeInitialized;
    public final boolean secure;
    private final DummySurfaceThread thread;
    private boolean threadReleased;

    public static synchronized boolean isSecureSupported(Context context) {
        boolean z;
        synchronized (DummySurface.class) {
            if (!secureModeInitialized) {
                secureMode = Util.SDK_INT < 24 ? 0 : getSecureModeV24(context);
                secureModeInitialized = true;
            }
            z = secureMode != 0;
        }
        return z;
    }

    public static DummySurface newInstanceV17(Context context, boolean secure) {
        assertApiLevel17OrHigher();
        Assertions.checkState(!secure || isSecureSupported(context));
        DummySurfaceThread thread = new DummySurfaceThread();
        return thread.init(secure ? secureMode : 0);
    }

    private DummySurface(DummySurfaceThread thread, SurfaceTexture surfaceTexture, boolean secure) {
        super(surfaceTexture);
        this.thread = thread;
        this.secure = secure;
    }

    @Override // android.view.Surface
    public void release() {
        super.release();
        synchronized (this.thread) {
            if (!this.threadReleased) {
                this.thread.release();
                this.threadReleased = true;
            }
        }
    }

    private static void assertApiLevel17OrHigher() {
        if (Util.SDK_INT < 17) {
            throw new UnsupportedOperationException("Unsupported prior to API level 17");
        }
    }

    @TargetApi(24)
    private static int getSecureModeV24(Context context) {
        if (Util.SDK_INT >= 26 || !("samsung".equals(Util.MANUFACTURER) || "XT1650".equals(Util.MODEL))) {
            if (Util.SDK_INT >= 26 || context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance")) {
                EGLDisplay display = EGL14.eglGetDisplay(0);
                String eglExtensions = EGL14.eglQueryString(display, 12373);
                if (eglExtensions == null || !eglExtensions.contains(EXTENSION_PROTECTED_CONTENT)) {
                    return 0;
                }
                return eglExtensions.contains(EXTENSION_SURFACELESS_CONTEXT) ? 1 : 2;
            }
            return 0;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class DummySurfaceThread extends HandlerThread implements Handler.Callback {
        private static final int MSG_INIT = 1;
        private static final int MSG_RELEASE = 2;
        @MonotonicNonNull
        private EGLSurfaceTexture eglSurfaceTexture;
        @MonotonicNonNull
        private Handler handler;
        @Nullable
        private Error initError;
        @Nullable
        private RuntimeException initException;
        @Nullable
        private DummySurface surface;

        public DummySurfaceThread() {
            super("dummySurface");
        }

        public DummySurface init(int secureMode) {
            start();
            this.handler = new Handler(getLooper(), this);
            this.eglSurfaceTexture = new EGLSurfaceTexture(this.handler);
            boolean wasInterrupted = false;
            synchronized (this) {
                this.handler.obtainMessage(1, secureMode, 0).sendToTarget();
                while (this.surface == null && this.initException == null && this.initError == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        wasInterrupted = true;
                    }
                }
            }
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
            if (this.initException != null) {
                throw this.initException;
            }
            if (this.initError != null) {
                throw this.initError;
            }
            return (DummySurface) Assertions.checkNotNull(this.surface);
        }

        public void release() {
            Assertions.checkNotNull(this.handler);
            this.handler.sendEmptyMessage(2);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case 1:
                        try {
                            initInternal(msg.arg1);
                            synchronized (this) {
                                notify();
                            }
                        } catch (Error e) {
                            Log.e(DummySurface.TAG, "Failed to initialize dummy surface", e);
                            this.initError = e;
                            synchronized (this) {
                                notify();
                            }
                        } catch (RuntimeException e2) {
                            Log.e(DummySurface.TAG, "Failed to initialize dummy surface", e2);
                            this.initException = e2;
                            synchronized (this) {
                                notify();
                            }
                        }
                        break;
                    case 2:
                        try {
                            releaseInternal();
                            break;
                        } catch (Throwable e3) {
                            try {
                                Log.e(DummySurface.TAG, "Failed to release dummy surface", e3);
                                break;
                            } finally {
                                quit();
                            }
                        }
                }
                return true;
            } catch (Throwable th) {
                synchronized (this) {
                    notify();
                    throw th;
                }
            }
        }

        private void initInternal(int secureMode) {
            Assertions.checkNotNull(this.eglSurfaceTexture);
            this.eglSurfaceTexture.init(secureMode);
            this.surface = new DummySurface(this, this.eglSurfaceTexture.getSurfaceTexture(), secureMode != 0);
        }

        private void releaseInternal() {
            Assertions.checkNotNull(this.eglSurfaceTexture);
            this.eglSurfaceTexture.release();
        }
    }
}
