package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzea {
    private static final Object zza = new Object();
    private final String zzb;
    private final zzdx zzc;
    private final Object zzd;
    private final Object zze;
    private final Object zzf = new Object();
    private volatile Object zzg = null;
    private volatile Object zzh = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzea(String str, Object obj, Object obj2, zzdx zzdxVar, zzdz zzdzVar) {
        this.zzb = str;
        this.zzd = obj;
        this.zze = obj2;
        this.zzc = zzdxVar;
    }

    public final Object zza(Object obj) {
        synchronized (this.zzf) {
            Object obj2 = this.zzg;
        }
        if (obj != null) {
            return obj;
        }
        if (zzdy.zza == null) {
            return this.zzd;
        }
        synchronized (zza) {
            if (zzab.zza()) {
                return this.zzh == null ? this.zzd : this.zzh;
            }
            try {
                for (zzea zzeaVar : zzeb.zzb()) {
                    if (!zzab.zza()) {
                        Object obj3 = null;
                        try {
                            zzdx zzdxVar = zzeaVar.zzc;
                            if (zzdxVar != null) {
                                obj3 = zzdxVar.zza();
                            }
                        } catch (IllegalStateException unused) {
                        }
                        synchronized (zza) {
                            zzeaVar.zzh = obj3;
                        }
                    } else {
                        throw new IllegalStateException("Refreshing flag cache must be done on a worker thread.");
                    }
                }
            } catch (SecurityException unused2) {
            }
            zzdx zzdxVar2 = this.zzc;
            if (zzdxVar2 == null) {
                return this.zzd;
            }
            try {
                return zzdxVar2.zza();
            } catch (IllegalStateException unused3) {
                return this.zzd;
            } catch (SecurityException unused4) {
                return this.zzd;
            }
        }
    }

    public final String zzb() {
        return this.zzb;
    }
}
