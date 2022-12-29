package com.google.firebase.analytics.connector;

import java.util.concurrent.Executor;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.1.1 */
/* loaded from: classes2.dex */
public final /* synthetic */ class zza implements Executor {
    public static final /* synthetic */ zza zza = new zza();

    private /* synthetic */ zza() {
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        runnable.run();
    }
}
