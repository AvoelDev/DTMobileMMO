package com.google.android.gms.measurement.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzky {
    final String zza;
    long zzb;

    private zzky(zzkz zzkzVar, String str) {
        this.zza = str;
        this.zzb = zzkzVar.zzav().elapsedRealtime();
    }
}
