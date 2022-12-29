package com.google.android.gms.internal.drive;

import android.support.v4.media.TransportMediator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes3.dex */
public abstract class zzjr extends zzjb {
    private static final Logger logger = Logger.getLogger(zzjr.class.getName());
    private static final boolean zzog = zznd.zzfd();
    zzjt zzoh;

    public static int zzad(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int zzaf(int i) {
        return 4;
    }

    public static int zzag(int i) {
        return 4;
    }

    private static int zzai(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static int zzb(double d) {
        return 8;
    }

    public static int zzb(float f) {
        return 4;
    }

    public static zzjr zzb(byte[] bArr) {
        return new zza(bArr, 0, bArr.length);
    }

    public static int zzd(boolean z) {
        return 1;
    }

    public static int zzp(long j) {
        int i;
        if (((-128) & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if (((-34359738368L) & j) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if (((-2097152) & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        return (j & (-16384)) != 0 ? i + 1 : i;
    }

    public static int zzr(long j) {
        return 8;
    }

    public static int zzs(long j) {
        return 8;
    }

    private static long zzt(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public abstract void zza(int i, long j) throws IOException;

    public abstract void zza(int i, zzjc zzjcVar) throws IOException;

    public abstract void zza(int i, zzlq zzlqVar) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zza(int i, zzlq zzlqVar, zzmf zzmfVar) throws IOException;

    public abstract void zza(int i, String str) throws IOException;

    public abstract void zza(zzjc zzjcVar) throws IOException;

    public abstract void zzaa(int i) throws IOException;

    public abstract void zzb(int i, int i2) throws IOException;

    public abstract void zzb(int i, zzjc zzjcVar) throws IOException;

    public abstract void zzb(int i, boolean z) throws IOException;

    public abstract void zzb(zzlq zzlqVar) throws IOException;

    public abstract void zzc(byte b) throws IOException;

    public abstract void zzc(int i, int i2) throws IOException;

    public abstract void zzc(int i, long j) throws IOException;

    public abstract int zzca();

    public abstract void zzd(int i, int i2) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzd(byte[] bArr, int i, int i2) throws IOException;

    public abstract void zzf(int i, int i2) throws IOException;

    public abstract void zzl(long j) throws IOException;

    public abstract void zzl(String str) throws IOException;

    public abstract void zzn(long j) throws IOException;

    public abstract void zzx(int i) throws IOException;

    public abstract void zzy(int i) throws IOException;

    /* loaded from: classes3.dex */
    public static class zzb extends IOException {
        zzb() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        zzb(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        zzb(java.lang.String r3, java.lang.Throwable r4) {
            /*
                r2 = this;
                java.lang.String r3 = java.lang.String.valueOf(r3)
                int r0 = r3.length()
                java.lang.String r1 = "CodedOutputStream was writing to a flat byte array and ran out of space.: "
                if (r0 == 0) goto L11
                java.lang.String r3 = r1.concat(r3)
                goto L16
            L11:
                java.lang.String r3 = new java.lang.String
                r3.<init>(r1)
            L16:
                r2.<init>(r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.drive.zzjr.zzb.<init>(java.lang.String, java.lang.Throwable):void");
        }
    }

    private zzjr() {
    }

    public final void zze(int i, int i2) throws IOException {
        zzd(i, zzai(i2));
    }

    public final void zzb(int i, long j) throws IOException {
        zza(i, zzt(j));
    }

    public final void zza(int i, float f) throws IOException {
        zzf(i, Float.floatToRawIntBits(f));
    }

    public final void zza(int i, double d) throws IOException {
        zzc(i, Double.doubleToRawLongBits(d));
    }

    public final void zzz(int i) throws IOException {
        zzy(zzai(i));
    }

    /* loaded from: classes3.dex */
    static class zza extends zzjr {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        zza(byte[] bArr, int i, int i2) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            }
            int i3 = i2 + 0;
            if ((i2 | 0 | (bArr.length - i3)) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), 0, Integer.valueOf(i2)));
            }
            this.buffer = bArr;
            this.offset = 0;
            this.position = 0;
            this.limit = i3;
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzb(int i, int i2) throws IOException {
            zzy((i << 3) | i2);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzc(int i, int i2) throws IOException {
            zzb(i, 0);
            zzx(i2);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzd(int i, int i2) throws IOException {
            zzb(i, 0);
            zzy(i2);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzf(int i, int i2) throws IOException {
            zzb(i, 5);
            zzaa(i2);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zza(int i, long j) throws IOException {
            zzb(i, 0);
            zzl(j);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzc(int i, long j) throws IOException {
            zzb(i, 1);
            zzn(j);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzb(int i, boolean z) throws IOException {
            zzb(i, 0);
            zzc(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zza(int i, String str) throws IOException {
            zzb(i, 2);
            zzl(str);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zza(int i, zzjc zzjcVar) throws IOException {
            zzb(i, 2);
            zza(zzjcVar);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zza(zzjc zzjcVar) throws IOException {
            zzy(zzjcVar.size());
            zzjcVar.zza(this);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzd(byte[] bArr, int i, int i2) throws IOException {
            zzy(i2);
            write(bArr, 0, i2);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        final void zza(int i, zzlq zzlqVar, zzmf zzmfVar) throws IOException {
            zzb(i, 2);
            zzit zzitVar = (zzit) zzlqVar;
            int zzbm = zzitVar.zzbm();
            if (zzbm == -1) {
                zzbm = zzmfVar.zzn(zzitVar);
                zzitVar.zzo(zzbm);
            }
            zzy(zzbm);
            zzmfVar.zza(zzlqVar, this.zzoh);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zza(int i, zzlq zzlqVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zzb(3, 2);
            zzb(zzlqVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzb(int i, zzjc zzjcVar) throws IOException {
            zzb(1, 3);
            zzd(2, i);
            zza(3, zzjcVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzb(zzlq zzlqVar) throws IOException {
            zzy(zzlqVar.zzcx());
            zzlqVar.zzb(this);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzc(byte b) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzx(int i) throws IOException {
            if (i >= 0) {
                zzy(i);
            } else {
                zzl(i);
            }
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzy(int i) throws IOException {
            if (!zzjr.zzog || zzix.zzbr() || zzca() < 5) {
                while ((i & (-128)) != 0) {
                    try {
                        byte[] bArr = this.buffer;
                        int i2 = this.position;
                        this.position = i2 + 1;
                        bArr[i2] = (byte) ((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
                        i >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
                    }
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) i;
            } else if ((i & (-128)) == 0) {
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                zznd.zza(bArr3, i4, (byte) i);
            } else {
                byte[] bArr4 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                zznd.zza(bArr4, i5, (byte) (i | 128));
                int i6 = i >>> 7;
                if ((i6 & (-128)) == 0) {
                    byte[] bArr5 = this.buffer;
                    int i7 = this.position;
                    this.position = i7 + 1;
                    zznd.zza(bArr5, i7, (byte) i6);
                    return;
                }
                byte[] bArr6 = this.buffer;
                int i8 = this.position;
                this.position = i8 + 1;
                zznd.zza(bArr6, i8, (byte) (i6 | 128));
                int i9 = i6 >>> 7;
                if ((i9 & (-128)) == 0) {
                    byte[] bArr7 = this.buffer;
                    int i10 = this.position;
                    this.position = i10 + 1;
                    zznd.zza(bArr7, i10, (byte) i9);
                    return;
                }
                byte[] bArr8 = this.buffer;
                int i11 = this.position;
                this.position = i11 + 1;
                zznd.zza(bArr8, i11, (byte) (i9 | 128));
                int i12 = i9 >>> 7;
                if ((i12 & (-128)) == 0) {
                    byte[] bArr9 = this.buffer;
                    int i13 = this.position;
                    this.position = i13 + 1;
                    zznd.zza(bArr9, i13, (byte) i12);
                    return;
                }
                byte[] bArr10 = this.buffer;
                int i14 = this.position;
                this.position = i14 + 1;
                zznd.zza(bArr10, i14, (byte) (i12 | 128));
                byte[] bArr11 = this.buffer;
                int i15 = this.position;
                this.position = i15 + 1;
                zznd.zza(bArr11, i15, (byte) (i12 >>> 7));
            }
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzaa(int i) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = (byte) i;
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr2[i3] = (byte) (i >> 8);
                byte[] bArr3 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr3[i4] = (byte) (i >> 16);
                byte[] bArr4 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr4[i5] = (byte) (i >>> 24);
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzl(long j) throws IOException {
            if (zzjr.zzog && zzca() >= 10) {
                while ((j & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    zznd.zza(bArr, i, (byte) ((((int) j) & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128));
                    j >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                zznd.zza(bArr2, i2, (byte) j);
                return;
            }
            while ((j & (-128)) != 0) {
                try {
                    byte[] bArr3 = this.buffer;
                    int i3 = this.position;
                    this.position = i3 + 1;
                    bArr3[i3] = (byte) ((((int) j) & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
                    j >>>= 7;
                } catch (IndexOutOfBoundsException e) {
                    throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
                }
            }
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) j;
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzn(long j) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) j;
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) (j >> 8);
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) (j >> 16);
                byte[] bArr4 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr4[i4] = (byte) (j >> 24);
                byte[] bArr5 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr5[i5] = (byte) (j >> 32);
                byte[] bArr6 = this.buffer;
                int i6 = this.position;
                this.position = i6 + 1;
                bArr6[i6] = (byte) (j >> 40);
                byte[] bArr7 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                bArr7[i7] = (byte) (j >> 48);
                byte[] bArr8 = this.buffer;
                int i8 = this.position;
                this.position = i8 + 1;
                bArr8[i8] = (byte) (j >> 56);
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e);
            }
        }

        private final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.buffer, this.position, i2);
                this.position += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new zzb(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i2)), e);
            }
        }

        @Override // com.google.android.gms.internal.drive.zzjb
        public final void zza(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final void zzl(String str) throws IOException {
            int i = this.position;
            try {
                int zzad = zzad(str.length() * 3);
                int zzad2 = zzad(str.length());
                if (zzad2 == zzad) {
                    this.position = i + zzad2;
                    int zza = zznf.zza(str, this.buffer, this.position, zzca());
                    this.position = i;
                    zzy((zza - i) - zzad2);
                    this.position = zza;
                    return;
                }
                zzy(zznf.zza(str));
                this.position = zznf.zza(str, this.buffer, this.position, zzca());
            } catch (zznj e) {
                this.position = i;
                zza(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new zzb(e2);
            }
        }

        @Override // com.google.android.gms.internal.drive.zzjr
        public final int zzca() {
            return this.limit - this.position;
        }
    }

    public final void zzm(long j) throws IOException {
        zzl(zzt(j));
    }

    public final void zza(float f) throws IOException {
        zzaa(Float.floatToRawIntBits(f));
    }

    public final void zza(double d) throws IOException {
        zzn(Double.doubleToRawLongBits(d));
    }

    public final void zzc(boolean z) throws IOException {
        zzc(z ? (byte) 1 : (byte) 0);
    }

    public static int zzg(int i, int i2) {
        return zzab(i) + zzac(i2);
    }

    public static int zzh(int i, int i2) {
        return zzab(i) + zzad(i2);
    }

    public static int zzi(int i, int i2) {
        return zzab(i) + zzad(zzai(i2));
    }

    public static int zzj(int i, int i2) {
        return zzab(i) + 4;
    }

    public static int zzk(int i, int i2) {
        return zzab(i) + 4;
    }

    public static int zzd(int i, long j) {
        return zzab(i) + zzp(j);
    }

    public static int zze(int i, long j) {
        return zzab(i) + zzp(j);
    }

    public static int zzf(int i, long j) {
        return zzab(i) + zzp(zzt(j));
    }

    public static int zzg(int i, long j) {
        return zzab(i) + 8;
    }

    public static int zzh(int i, long j) {
        return zzab(i) + 8;
    }

    public static int zzb(int i, float f) {
        return zzab(i) + 4;
    }

    public static int zzb(int i, double d) {
        return zzab(i) + 8;
    }

    public static int zzc(int i, boolean z) {
        return zzab(i) + 1;
    }

    public static int zzl(int i, int i2) {
        return zzab(i) + zzac(i2);
    }

    public static int zzb(int i, String str) {
        return zzab(i) + zzm(str);
    }

    public static int zzc(int i, zzjc zzjcVar) {
        int zzab = zzab(i);
        int size = zzjcVar.size();
        return zzab + zzad(size) + size;
    }

    public static int zza(int i, zzkx zzkxVar) {
        int zzab = zzab(i);
        int zzcx = zzkxVar.zzcx();
        return zzab + zzad(zzcx) + zzcx;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzb(int i, zzlq zzlqVar, zzmf zzmfVar) {
        return zzab(i) + zza(zzlqVar, zzmfVar);
    }

    public static int zzb(int i, zzlq zzlqVar) {
        return (zzab(1) << 1) + zzh(2, i) + zzab(3) + zzc(zzlqVar);
    }

    public static int zzd(int i, zzjc zzjcVar) {
        return (zzab(1) << 1) + zzh(2, i) + zzc(3, zzjcVar);
    }

    public static int zzb(int i, zzkx zzkxVar) {
        return (zzab(1) << 1) + zzh(2, i) + zza(3, zzkxVar);
    }

    public static int zzab(int i) {
        return zzad(i << 3);
    }

    public static int zzac(int i) {
        if (i >= 0) {
            return zzad(i);
        }
        return 10;
    }

    public static int zzae(int i) {
        return zzad(zzai(i));
    }

    public static int zzo(long j) {
        return zzp(j);
    }

    public static int zzq(long j) {
        return zzp(zzt(j));
    }

    public static int zzah(int i) {
        return zzac(i);
    }

    public static int zzm(String str) {
        int length;
        try {
            length = zznf.zza(str);
        } catch (zznj unused) {
            length = str.getBytes(zzkm.UTF_8).length;
        }
        return zzad(length) + length;
    }

    public static int zza(zzkx zzkxVar) {
        int zzcx = zzkxVar.zzcx();
        return zzad(zzcx) + zzcx;
    }

    public static int zzb(zzjc zzjcVar) {
        int size = zzjcVar.size();
        return zzad(size) + size;
    }

    public static int zzc(byte[] bArr) {
        int length = bArr.length;
        return zzad(length) + length;
    }

    public static int zzc(zzlq zzlqVar) {
        int zzcx = zzlqVar.zzcx();
        return zzad(zzcx) + zzcx;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(zzlq zzlqVar, zzmf zzmfVar) {
        zzit zzitVar = (zzit) zzlqVar;
        int zzbm = zzitVar.zzbm();
        if (zzbm == -1) {
            zzbm = zzmfVar.zzn(zzitVar);
            zzitVar.zzo(zzbm);
        }
        return zzad(zzbm) + zzbm;
    }

    public final void zzcb() {
        if (zzca() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    final void zza(String str, zznj zznjVar) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zznjVar);
        byte[] bytes = str.getBytes(zzkm.UTF_8);
        try {
            zzy(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (zzb e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new zzb(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Deprecated
    public static int zzc(int i, zzlq zzlqVar, zzmf zzmfVar) {
        int zzab = zzab(i) << 1;
        zzit zzitVar = (zzit) zzlqVar;
        int zzbm = zzitVar.zzbm();
        if (zzbm == -1) {
            zzbm = zzmfVar.zzn(zzitVar);
            zzitVar.zzo(zzbm);
        }
        return zzab + zzbm;
    }

    @Deprecated
    public static int zzd(zzlq zzlqVar) {
        return zzlqVar.zzcx();
    }

    @Deprecated
    public static int zzaj(int i) {
        return zzad(i);
    }
}
