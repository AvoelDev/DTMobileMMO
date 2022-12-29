package com.google.android.gms.measurement.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzio implements Runnable {
    final /* synthetic */ zzis zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzio(zzis zzisVar) {
        this.zza = zzisVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzik zzikVar;
        zzis zzisVar = this.zza;
        zzikVar = zzisVar.zzh;
        zzisVar.zza = zzikVar;
    }
}
