package com.google.android.gms.internal.drive;

import java.io.IOException;

/* loaded from: classes3.dex */
final class zzmz extends zzmx<zzmy, zzmy> {
    private static void zza(Object obj, zzmy zzmyVar) {
        ((zzkk) obj).zzrq = zzmyVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final void zzd(Object obj) {
        ((zzkk) obj).zzrq.zzbp();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ int zzn(zzmy zzmyVar) {
        return zzmyVar.zzcx();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ int zzs(zzmy zzmyVar) {
        return zzmyVar.zzfc();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ zzmy zzg(zzmy zzmyVar, zzmy zzmyVar2) {
        zzmy zzmyVar3 = zzmyVar;
        zzmy zzmyVar4 = zzmyVar2;
        return zzmyVar4.equals(zzmy.zzfa()) ? zzmyVar3 : zzmy.zza(zzmyVar3, zzmyVar4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ void zzc(zzmy zzmyVar, zzns zznsVar) throws IOException {
        zzmyVar.zza(zznsVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ void zza(zzmy zzmyVar, zzns zznsVar) throws IOException {
        zzmyVar.zzb(zznsVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ void zzf(Object obj, zzmy zzmyVar) {
        zza(obj, zzmyVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ zzmy zzr(Object obj) {
        return ((zzkk) obj).zzrq;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ void zze(Object obj, zzmy zzmyVar) {
        zza(obj, zzmyVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ zzmy zzez() {
        return zzmy.zzfb();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ void zza(zzmy zzmyVar, int i, zzjc zzjcVar) {
        zzmyVar.zzb((i << 3) | 2, zzjcVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzmx
    public final /* synthetic */ void zza(zzmy zzmyVar, int i, long j) {
        zzmyVar.zzb(i << 3, Long.valueOf(j));
    }
}
