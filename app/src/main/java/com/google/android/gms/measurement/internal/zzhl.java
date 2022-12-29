package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzhl implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zzid zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhl(zzid zzidVar, long j) {
        this.zzb = zzidVar;
        this.zza = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zzL(this.zza, true);
        this.zzb.zzs.zzt().zzu(new AtomicReference());
    }
}
