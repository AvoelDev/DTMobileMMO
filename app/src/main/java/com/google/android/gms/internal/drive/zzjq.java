package com.google.android.gms.internal.drive;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class zzjq extends zzjo {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzoc;
    private int zzod;
    private int zzoe;
    private int zzof;

    private zzjq(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzof = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i2 + i;
        this.pos = i;
        this.zzoe = this.pos;
        this.zzoc = z;
    }

    @Override // com.google.android.gms.internal.drive.zzjo
    public final int zzv(int i) throws zzkq {
        if (i < 0) {
            throw zzkq.zzdj();
        }
        int zzbz = i + zzbz();
        int i2 = this.zzof;
        if (zzbz > i2) {
            throw zzkq.zzdi();
        }
        this.zzof = zzbz;
        this.limit += this.zzod;
        int i3 = this.limit;
        int i4 = i3 - this.zzoe;
        int i5 = this.zzof;
        if (i4 > i5) {
            this.zzod = i4 - i5;
            this.limit = i3 - this.zzod;
        } else {
            this.zzod = 0;
        }
        return i2;
    }

    @Override // com.google.android.gms.internal.drive.zzjo
    public final int zzbz() {
        return this.pos - this.zzoe;
    }
}
