package com.google.android.gms.internal.games;

import com.google.android.gms.common.internal.GmsLogger;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzck {
    private static final GmsLogger zza = new GmsLogger("Games");

    public static void zza(String str, String str2) {
        zza.e(zzf(str), str2);
    }

    public static void zzb(String str, String str2, Throwable th) {
        zza.e(zzf("GamesGmsClientImpl"), "Is player signed out?", th);
    }

    public static void zzc(String str, String str2, Throwable th) {
        zza.i(zzf("SnapshotContentsEntity"), "Failed to write snapshot data", th);
    }

    public static void zzd(String str, String str2) {
        zza.w(zzf(str), str2);
    }

    public static void zze(String str, String str2, Throwable th) {
        zza.w(zzf(str), str2, th);
    }

    private static String zzf(String str) {
        return String.format("%s[%s]", "PlayGamesServices", str);
    }
}
