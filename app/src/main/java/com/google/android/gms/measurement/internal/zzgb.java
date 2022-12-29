package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.1.1 */
/* loaded from: classes2.dex */
final class zzgb implements Runnable {
    final /* synthetic */ zzac zza;
    final /* synthetic */ zzgq zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgb(zzgq zzgqVar, zzac zzacVar) {
        this.zzb = zzgqVar;
        this.zza = zzacVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzkz zzkzVar;
        zzkz zzkzVar2;
        zzkz zzkzVar3;
        zzkzVar = this.zzb.zza;
        zzkzVar.zzA();
        if (this.zza.zzc.zza() == null) {
            zzkzVar3 = this.zzb.zza;
            zzkzVar3.zzN(this.zza);
            return;
        }
        zzkzVar2 = this.zzb.zza;
        zzkzVar2.zzT(this.zza);
    }
}
