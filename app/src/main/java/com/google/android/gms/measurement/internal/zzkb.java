package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
final class zzkb implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zzki zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzkb(zzki zzkiVar, long j) {
        this.zzb = zzkiVar;
        this.zza = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzki.zzj(this.zzb, this.zza);
    }
}
