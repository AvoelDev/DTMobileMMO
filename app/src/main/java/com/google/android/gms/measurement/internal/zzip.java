package com.google.android.gms.measurement.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzip implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zzis zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzip(zzis zzisVar, long j) {
        this.zzb = zzisVar;
        this.zza = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zzs.zzd().zzf(this.zza);
        this.zzb.zza = null;
    }
}
