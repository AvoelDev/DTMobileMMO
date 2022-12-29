package com.google.android.gms.measurement.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzhg implements Runnable {
    final /* synthetic */ zzid zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhg(zzid zzidVar) {
        this.zza = zzidVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.zzb.zzb();
    }
}
