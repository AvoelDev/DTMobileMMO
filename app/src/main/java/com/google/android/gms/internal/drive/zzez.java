package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkk;

/* loaded from: classes3.dex */
public final class zzez extends zzkk<zzez, zza> implements zzls {
    private static final zzez zzhj = new zzez();
    private static volatile zzmb<zzez> zzhk;
    private int zzhd;
    private byte zzhi = 2;
    private int zzhe = 1;
    private long zzhf = -1;
    private long zzhg = -1;
    private long zzhh = -1;

    private zzez() {
    }

    /* loaded from: classes3.dex */
    public static final class zza extends zzkk.zza<zzez, zza> implements zzls {
        private zza() {
            super(zzez.zzhj);
        }

        public final zza zzk(int i) {
            zzdb();
            ((zzez) this.zzru).zzj(1);
            return this;
        }

        public final zza zzc(long j) {
            zzdb();
            ((zzez) this.zzru).setSequenceNumber(j);
            return this;
        }

        public final zza zzd(long j) {
            zzdb();
            ((zzez) this.zzru).zza(j);
            return this;
        }

        public final zza zze(long j) {
            zzdb();
            ((zzez) this.zzru).zzb(j);
            return this;
        }

        /* synthetic */ zza(zzfa zzfaVar) {
            this();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzj(int i) {
        this.zzhd |= 1;
        this.zzhe = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setSequenceNumber(long j) {
        this.zzhd |= 2;
        this.zzhf = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(long j) {
        this.zzhd |= 4;
        this.zzhg = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(long j) {
        this.zzhd |= 8;
        this.zzhh = j;
    }

    public static zza zzaj() {
        return zzhj.zzcw();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Type inference failed for: r3v14, types: [com.google.android.gms.internal.drive.zzmb<com.google.android.gms.internal.drive.zzez>, com.google.android.gms.internal.drive.zzkk$zzb] */
    @Override // com.google.android.gms.internal.drive.zzkk
    public final Object zza(int i, Object obj, Object obj2) {
        zzmb<zzez> zzmbVar;
        switch (zzfa.zzhl[i - 1]) {
            case 1:
                return new zzez();
            case 2:
                return new zza(null);
            case 3:
                return zza(zzhj, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0004\u0001Ԅ\u0000\u0002Ԑ\u0001\u0003Ԑ\u0002\u0004Ԑ\u0003", new Object[]{"zzhd", "zzhe", "zzhf", "zzhg", "zzhh"});
            case 4:
                return zzhj;
            case 5:
                zzmb<zzez> zzmbVar2 = zzhk;
                zzmb<zzez> zzmbVar3 = zzmbVar2;
                if (zzmbVar2 == null) {
                    synchronized (zzez.class) {
                        zzmb<zzez> zzmbVar4 = zzhk;
                        zzmbVar = zzmbVar4;
                        if (zzmbVar4 == null) {
                            ?? zzbVar = new zzkk.zzb(zzhj);
                            zzhk = zzbVar;
                            zzmbVar = zzbVar;
                        }
                    }
                    zzmbVar3 = zzmbVar;
                }
                return zzmbVar3;
            case 6:
                return Byte.valueOf(this.zzhi);
            case 7:
                this.zzhi = (byte) (obj != null ? 1 : 0);
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        zzkk.zza(zzez.class, zzhj);
    }
}
