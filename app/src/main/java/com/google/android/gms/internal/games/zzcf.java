package com.google.android.gms.internal.games;

import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public abstract class zzcf {
    private final AtomicReference zza = new AtomicReference();

    protected abstract zzce zza();

    public final void zzb() {
        zzce zzceVar = (zzce) this.zza.get();
        if (zzceVar != null) {
            zzceVar.zzd();
        }
    }

    public final void zzc(String str, int i) {
        zzce zzceVar = (zzce) this.zza.get();
        if (zzceVar == null) {
            zzce zza = zza();
            AtomicReference atomicReference = this.zza;
            while (true) {
                if (!atomicReference.compareAndSet(null, zza)) {
                    if (atomicReference.get() != null) {
                        zzceVar = (zzce) this.zza.get();
                        break;
                    }
                } else {
                    zzceVar = zza;
                    break;
                }
            }
        }
        zzceVar.zzc(str, i);
    }
}
