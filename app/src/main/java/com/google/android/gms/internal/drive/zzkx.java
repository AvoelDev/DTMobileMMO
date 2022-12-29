package com.google.android.gms.internal.drive;

/* loaded from: classes3.dex */
public class zzkx {
    private static final zzjx zzng = zzjx.zzci();
    private zzjc zzth;
    private volatile zzlq zzti;
    private volatile zzjc zztj;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzkx) {
            zzkx zzkxVar = (zzkx) obj;
            zzlq zzlqVar = this.zzti;
            zzlq zzlqVar2 = zzkxVar.zzti;
            if (zzlqVar == null && zzlqVar2 == null) {
                return zzbl().equals(zzkxVar.zzbl());
            }
            if (zzlqVar == null || zzlqVar2 == null) {
                if (zzlqVar != null) {
                    return zzlqVar.equals(zzkxVar.zzh(zzlqVar.zzda()));
                }
                return zzh(zzlqVar2.zzda()).equals(zzlqVar2);
            }
            return zzlqVar.equals(zzlqVar2);
        }
        return false;
    }

    private final zzlq zzh(zzlq zzlqVar) {
        if (this.zzti == null) {
            synchronized (this) {
                if (this.zzti == null) {
                    try {
                        this.zzti = zzlqVar;
                        this.zztj = zzjc.zznq;
                    } catch (zzkq unused) {
                        this.zzti = zzlqVar;
                        this.zztj = zzjc.zznq;
                    }
                }
            }
        }
        return this.zzti;
    }

    public final zzlq zzi(zzlq zzlqVar) {
        zzlq zzlqVar2 = this.zzti;
        this.zzth = null;
        this.zztj = null;
        this.zzti = zzlqVar;
        return zzlqVar2;
    }

    public final int zzcx() {
        if (this.zztj != null) {
            return this.zztj.size();
        }
        if (this.zzti != null) {
            return this.zzti.zzcx();
        }
        return 0;
    }

    public final zzjc zzbl() {
        if (this.zztj != null) {
            return this.zztj;
        }
        synchronized (this) {
            if (this.zztj != null) {
                return this.zztj;
            }
            if (this.zzti == null) {
                this.zztj = zzjc.zznq;
            } else {
                this.zztj = this.zzti.zzbl();
            }
            return this.zztj;
        }
    }
}
