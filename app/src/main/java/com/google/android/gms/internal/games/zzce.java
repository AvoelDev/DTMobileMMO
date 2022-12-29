package com.google.android.gms.internal.games;

import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public abstract class zzce {
    private final Handler zza;
    private boolean zzc;
    final Object zzb = new Object();
    private final HashMap zzd = new HashMap();

    public zzce(Looper looper, int i) {
        this.zza = new zzcl(looper);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ void zzb(zzce zzceVar) {
        synchronized (zzceVar.zzb) {
            zzceVar.zzc = false;
            zzceVar.zzd();
        }
    }

    protected abstract void zza(String str, int i);

    public final void zzc(String str, int i) {
        synchronized (this.zzb) {
            if (!this.zzc) {
                this.zzc = true;
                this.zza.postDelayed(new zzcd(this), 1000L);
            }
            AtomicInteger atomicInteger = (AtomicInteger) this.zzd.get(str);
            if (atomicInteger == null) {
                atomicInteger = new AtomicInteger();
                this.zzd.put(str, atomicInteger);
            }
            atomicInteger.addAndGet(i);
        }
    }

    public final void zzd() {
        synchronized (this.zzb) {
            for (Map.Entry entry : this.zzd.entrySet()) {
                zza((String) entry.getKey(), ((AtomicInteger) entry.getValue()).get());
            }
            this.zzd.clear();
        }
    }
}
