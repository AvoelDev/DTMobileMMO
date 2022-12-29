package com.google.android.gms.internal.games;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzcd implements Runnable {
    final /* synthetic */ zzce zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzcd(zzce zzceVar) {
        this.zza = zzceVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzce.zzb(this.zza);
    }
}
