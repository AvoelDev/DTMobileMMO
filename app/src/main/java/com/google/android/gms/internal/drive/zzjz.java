package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkk;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzjz extends zzjy<Object> {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzjy
    public final boolean zze(zzlq zzlqVar) {
        return zzlqVar instanceof zzkk.zzc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzjy
    public final zzkb<Object> zzb(Object obj) {
        return ((zzkk.zzc) obj).zzrw;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzjy
    public final zzkb<Object> zzc(Object obj) {
        return ((zzkk.zzc) obj).zzdg();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzjy
    public final void zzd(Object obj) {
        zzb(obj).zzbp();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzjy
    public final int zza(Map.Entry<?, ?> entry) {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzjy
    public final void zza(zzns zznsVar, Map.Entry<?, ?> entry) throws IOException {
        entry.getKey();
        throw new NoSuchMethodError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzjy
    public final Object zza(zzjx zzjxVar, zzlq zzlqVar, int i) {
        return zzjxVar.zza(zzlqVar, i);
    }
}
