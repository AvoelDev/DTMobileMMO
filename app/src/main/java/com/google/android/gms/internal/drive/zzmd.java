package com.google.android.gms.internal.drive;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class zzmd {
    private static final zzmd zzuw = new zzmd();
    private final ConcurrentMap<Class<?>, zzmf<?>> zzuy = new ConcurrentHashMap();
    private final zzmg zzux = new zzlf();

    public static zzmd zzej() {
        return zzuw;
    }

    public final <T> zzmf<T> zzf(Class<T> cls) {
        zzkm.zza(cls, "messageType");
        zzmf<T> zzmfVar = (zzmf<T>) this.zzuy.get(cls);
        if (zzmfVar == null) {
            zzmf<T> zze = this.zzux.zze(cls);
            zzkm.zza(cls, "messageType");
            zzkm.zza(zze, "schema");
            zzmf<T> zzmfVar2 = (zzmf<T>) this.zzuy.putIfAbsent(cls, zze);
            return zzmfVar2 != null ? zzmfVar2 : zze;
        }
        return zzmfVar;
    }

    public final <T> zzmf<T> zzq(T t) {
        return zzf(t.getClass());
    }

    private zzmd() {
    }
}
