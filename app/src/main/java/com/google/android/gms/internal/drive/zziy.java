package com.google.android.gms.internal.drive;

import android.support.v4.media.TransportMediator;
import java.io.IOException;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class zziy {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(byte[] bArr, int i, zziz zzizVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b >= 0) {
            zzizVar.zznk = b;
            return i2;
        }
        return zza(b, bArr, i2, zzizVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(int i, byte[] bArr, int i2, zziz zzizVar) {
        int i3 = i & TransportMediator.KEYCODE_MEDIA_PAUSE;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzizVar.zznk = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & ByteCompanionObject.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzizVar.zznk = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & ByteCompanionObject.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzizVar.zznk = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & ByteCompanionObject.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzizVar.zznk = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & ByteCompanionObject.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzizVar.zznk = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzb(byte[] bArr, int i, zziz zzizVar) {
        byte b;
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzizVar.zznl = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b2 = bArr[i2];
        long j2 = (j & 127) | ((b2 & ByteCompanionObject.MAX_VALUE) << 7);
        int i4 = 7;
        while (b2 < 0) {
            int i5 = i3 + 1;
            i4 += 7;
            j2 |= (b & ByteCompanionObject.MAX_VALUE) << i4;
            b2 = bArr[i3];
            i3 = i5;
        }
        zzizVar.zznl = j2;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & UByte.MAX_VALUE) << 24) | (bArr[i] & UByte.MAX_VALUE) | ((bArr[i + 1] & UByte.MAX_VALUE) << 8) | ((bArr[i + 2] & UByte.MAX_VALUE) << 16);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long zzb(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double zzc(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzb(bArr, i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static float zzd(byte[] bArr, int i) {
        return Float.intBitsToFloat(zza(bArr, i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzc(byte[] bArr, int i, zziz zzizVar) throws zzkq {
        int zza = zza(bArr, i, zzizVar);
        int i2 = zzizVar.zznk;
        if (i2 >= 0) {
            if (i2 == 0) {
                zzizVar.zznm = "";
                return zza;
            }
            zzizVar.zznm = new String(bArr, zza, i2, zzkm.UTF_8);
            return zza + i2;
        }
        throw zzkq.zzdj();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzd(byte[] bArr, int i, zziz zzizVar) throws zzkq {
        int zza = zza(bArr, i, zzizVar);
        int i2 = zzizVar.zznk;
        if (i2 >= 0) {
            if (i2 == 0) {
                zzizVar.zznm = "";
                return zza;
            }
            zzizVar.zznm = zznf.zzg(bArr, zza, i2);
            return zza + i2;
        }
        throw zzkq.zzdj();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zze(byte[] bArr, int i, zziz zzizVar) throws zzkq {
        int zza = zza(bArr, i, zzizVar);
        int i2 = zzizVar.zznk;
        if (i2 < 0) {
            throw zzkq.zzdj();
        }
        if (i2 <= bArr.length - zza) {
            if (i2 == 0) {
                zzizVar.zznm = zzjc.zznq;
                return zza;
            }
            zzizVar.zznm = zzjc.zzb(bArr, zza, i2);
            return zza + i2;
        }
        throw zzkq.zzdi();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(zzmf zzmfVar, byte[] bArr, int i, int i2, zziz zzizVar) throws IOException {
        int i3 = i + 1;
        int i4 = bArr[i];
        if (i4 < 0) {
            i3 = zza(i4, bArr, i3, zzizVar);
            i4 = zzizVar.zznk;
        }
        int i5 = i3;
        if (i4 < 0 || i4 > i2 - i5) {
            throw zzkq.zzdi();
        }
        Object newInstance = zzmfVar.newInstance();
        int i6 = i4 + i5;
        zzmfVar.zza(newInstance, bArr, i5, i6, zzizVar);
        zzmfVar.zzd(newInstance);
        zzizVar.zznm = newInstance;
        return i6;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(zzmf zzmfVar, byte[] bArr, int i, int i2, int i3, zziz zzizVar) throws IOException {
        zzlu zzluVar = (zzlu) zzmfVar;
        Object newInstance = zzluVar.newInstance();
        int zza = zzluVar.zza((zzlu) newInstance, bArr, i, i2, i3, zzizVar);
        zzluVar.zzd(newInstance);
        zzizVar.zznm = newInstance;
        return zza;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(int i, byte[] bArr, int i2, int i3, zzkp<?> zzkpVar, zziz zzizVar) {
        zzkl zzklVar = (zzkl) zzkpVar;
        int zza = zza(bArr, i2, zzizVar);
        zzklVar.zzam(zzizVar.zznk);
        while (zza < i3) {
            int zza2 = zza(bArr, zza, zzizVar);
            if (i != zzizVar.zznk) {
                break;
            }
            zza = zza(bArr, zza2, zzizVar);
            zzklVar.zzam(zzizVar.zznk);
        }
        return zza;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(byte[] bArr, int i, zzkp<?> zzkpVar, zziz zzizVar) throws IOException {
        zzkl zzklVar = (zzkl) zzkpVar;
        int zza = zza(bArr, i, zzizVar);
        int i2 = zzizVar.zznk + zza;
        while (zza < i2) {
            zza = zza(bArr, zza, zzizVar);
            zzklVar.zzam(zzizVar.zznk);
        }
        if (zza == i2) {
            return zza;
        }
        throw zzkq.zzdi();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(zzmf<?> zzmfVar, int i, byte[] bArr, int i2, int i3, zzkp<?> zzkpVar, zziz zzizVar) throws IOException {
        int zza = zza(zzmfVar, bArr, i2, i3, zzizVar);
        zzkpVar.add(zzizVar.zznm);
        while (zza < i3) {
            int zza2 = zza(bArr, zza, zzizVar);
            if (i != zzizVar.zznk) {
                break;
            }
            zza = zza(zzmfVar, bArr, zza2, i3, zzizVar);
            zzkpVar.add(zzizVar.zznm);
        }
        return zza;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(int i, byte[] bArr, int i2, int i3, zzmy zzmyVar, zziz zzizVar) throws zzkq {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                int zzb = zzb(bArr, i2, zzizVar);
                zzmyVar.zzb(i, Long.valueOf(zzizVar.zznl));
                return zzb;
            } else if (i4 == 1) {
                zzmyVar.zzb(i, Long.valueOf(zzb(bArr, i2)));
                return i2 + 8;
            } else if (i4 == 2) {
                int zza = zza(bArr, i2, zzizVar);
                int i5 = zzizVar.zznk;
                if (i5 < 0) {
                    throw zzkq.zzdj();
                }
                if (i5 <= bArr.length - zza) {
                    if (i5 == 0) {
                        zzmyVar.zzb(i, zzjc.zznq);
                    } else {
                        zzmyVar.zzb(i, zzjc.zzb(bArr, zza, i5));
                    }
                    return zza + i5;
                }
                throw zzkq.zzdi();
            } else if (i4 != 3) {
                if (i4 == 5) {
                    zzmyVar.zzb(i, Integer.valueOf(zza(bArr, i2)));
                    return i2 + 4;
                }
                throw zzkq.zzdk();
            } else {
                zzmy zzfb = zzmy.zzfb();
                int i6 = (i & (-8)) | 4;
                int i7 = 0;
                while (true) {
                    if (i2 >= i3) {
                        break;
                    }
                    int zza2 = zza(bArr, i2, zzizVar);
                    int i8 = zzizVar.zznk;
                    i7 = i8;
                    if (i8 == i6) {
                        i2 = zza2;
                        break;
                    }
                    int zza3 = zza(i7, bArr, zza2, i3, zzfb, zzizVar);
                    i7 = i8;
                    i2 = zza3;
                }
                if (i2 > i3 || i7 != i6) {
                    throw zzkq.zzdm();
                }
                zzmyVar.zzb(i, zzfb);
                return i2;
            }
        }
        throw zzkq.zzdk();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(int i, byte[] bArr, int i2, int i3, zziz zzizVar) throws zzkq {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 != 0) {
                if (i4 != 1) {
                    if (i4 != 2) {
                        if (i4 != 3) {
                            if (i4 == 5) {
                                return i2 + 4;
                            }
                            throw zzkq.zzdk();
                        }
                        int i5 = (i & (-8)) | 4;
                        int i6 = 0;
                        while (i2 < i3) {
                            i2 = zza(bArr, i2, zzizVar);
                            i6 = zzizVar.zznk;
                            if (i6 == i5) {
                                break;
                            }
                            i2 = zza(i6, bArr, i2, i3, zzizVar);
                        }
                        if (i2 > i3 || i6 != i5) {
                            throw zzkq.zzdm();
                        }
                        return i2;
                    }
                    return zza(bArr, i2, zzizVar) + zzizVar.zznk;
                }
                return i2 + 8;
            }
            return zzb(bArr, i2, zzizVar);
        }
        throw zzkq.zzdk();
    }
}
