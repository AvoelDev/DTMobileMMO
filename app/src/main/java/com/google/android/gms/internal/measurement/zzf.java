package com.google.android.gms.internal.measurement;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/* compiled from: com.google.android.gms:play-services-measurement@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzf {
    final zzax zza = new zzax();
    final zzg zzc = new zzg(null, this.zza);
    final zzg zzb = this.zzc.zza();
    final zzj zzd = new zzj();

    public zzf() {
        this.zzc.zzg("require", new zzw(this.zzd));
        this.zzd.zza("internal.platform", zze.zza);
        this.zzc.zzg("runtime.counter", new zzah(Double.valueOf((double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)));
    }

    public final zzap zza(zzg zzgVar, zzgx... zzgxVarArr) {
        zzap zzapVar = zzap.zzf;
        for (zzgx zzgxVar : zzgxVarArr) {
            zzapVar = zzi.zza(zzgxVar);
            zzh.zzc(this.zzc);
            if ((zzapVar instanceof zzaq) || (zzapVar instanceof zzao)) {
                zzapVar = this.zza.zza(zzgVar, zzapVar);
            }
        }
        return zzapVar;
    }
}
