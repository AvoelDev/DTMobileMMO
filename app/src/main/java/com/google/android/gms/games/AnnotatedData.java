package com.google.android.gms.games;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public class AnnotatedData<T> {
    private final Object zza;
    private final boolean zzb;

    public AnnotatedData(Object obj, boolean z) {
        this.zza = obj;
        this.zzb = z;
    }

    public T get() {
        return (T) this.zza;
    }

    public boolean isStale() {
        return this.zzb;
    }
}
