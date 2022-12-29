package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.1.1 */
/* loaded from: classes2.dex */
final class zzmp extends zzmn {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzmn
    public final /* synthetic */ int zza(Object obj) {
        return ((zzmo) obj).zza();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzmn
    public final /* synthetic */ int zzb(Object obj) {
        return ((zzmo) obj).zzb();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzmn
    public final /* synthetic */ Object zzc(Object obj) {
        return ((zzke) obj).zzc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzmn
    public final /* bridge */ /* synthetic */ Object zzd(Object obj, Object obj2) {
        zzmo zzmoVar = (zzmo) obj2;
        return zzmoVar.equals(zzmo.zzc()) ? obj : zzmo.zzd((zzmo) obj, zzmoVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzmn
    public final /* synthetic */ Object zze() {
        return zzmo.zze();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzmn
    public final /* bridge */ /* synthetic */ void zzf(Object obj, int i, long j) {
        ((zzmo) obj).zzh(i << 3, Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzmn
    public final void zzg(Object obj) {
        ((zzke) obj).zzc.zzf();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzmn
    public final /* synthetic */ void zzh(Object obj, Object obj2) {
        ((zzke) obj).zzc = (zzmo) obj2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.measurement.zzmn
    public final /* synthetic */ void zzi(Object obj, zznf zznfVar) throws IOException {
        ((zzmo) obj).zzi(zznfVar);
    }
}
