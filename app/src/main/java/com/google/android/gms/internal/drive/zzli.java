package com.google.android.gms.internal.drive;

import java.io.IOException;

/* loaded from: classes3.dex */
public final class zzli<K, V> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> void zza(zzjr zzjrVar, zzlj<K, V> zzljVar, K k, V v) throws IOException {
        zzkb.zza(zzjrVar, zzljVar.zztu, 1, k);
        zzkb.zza(zzjrVar, zzljVar.zztw, 2, v);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> int zza(zzlj<K, V> zzljVar, K k, V v) {
        return zzkb.zza(zzljVar.zztu, 1, k) + zzkb.zza(zzljVar.zztw, 2, v);
    }
}
