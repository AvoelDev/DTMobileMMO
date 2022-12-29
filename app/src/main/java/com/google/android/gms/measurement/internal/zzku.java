package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzku implements zzlg {
    final /* synthetic */ zzkz zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzku(zzkz zzkzVar) {
        this.zza = zzkzVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzlg
    public final void zza(String str, String str2, Bundle bundle) {
        zzfy zzfyVar;
        zzfy zzfyVar2;
        if (!TextUtils.isEmpty(str)) {
            this.zza.zzaz().zzp(new zzkt(this, str, "_err", bundle));
            return;
        }
        zzkz zzkzVar = this.zza;
        zzfyVar = zzkzVar.zzn;
        if (zzfyVar != null) {
            zzfyVar2 = zzkzVar.zzn;
            zzfyVar2.zzay().zzd().zzb("AppId not known when logging event", "_err");
        }
    }
}
