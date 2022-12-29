package com.google.android.exoplayer2.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

/* loaded from: classes.dex */
final class SystemClock implements Clock {
    @Override // com.google.android.exoplayer2.util.Clock
    public long elapsedRealtime() {
        return android.os.SystemClock.elapsedRealtime();
    }

    @Override // com.google.android.exoplayer2.util.Clock
    public long uptimeMillis() {
        return android.os.SystemClock.uptimeMillis();
    }

    @Override // com.google.android.exoplayer2.util.Clock
    public void sleep(long sleepTimeMs) {
        android.os.SystemClock.sleep(sleepTimeMs);
    }

    @Override // com.google.android.exoplayer2.util.Clock
    public HandlerWrapper createHandler(Looper looper, @Nullable Handler.Callback callback) {
        return new SystemHandlerWrapper(new Handler(looper, callback));
    }
}
