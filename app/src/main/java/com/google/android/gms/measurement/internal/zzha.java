package com.google.android.gms.measurement.internal;

import android.util.Log;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzha implements zzei {
    final /* synthetic */ zzfy zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzha(zzhb zzhbVar, zzfy zzfyVar) {
        this.zza = zzfyVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzei
    public final boolean zza() {
        return this.zza.zzL() && Log.isLoggable(this.zza.zzay().zzq(), 3);
    }
}
