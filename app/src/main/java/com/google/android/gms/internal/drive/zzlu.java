package com.google.android.gms.internal.drive;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.drive.zzkk;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class zzlu<T> implements zzmf<T> {
    private static final int[] zzub = new int[0];
    private static final Unsafe zzuc = zznd.zzff();
    private final int[] zzud;
    private final Object[] zzue;
    private final int zzuf;
    private final int zzug;
    private final zzlq zzuh;
    private final boolean zzui;
    private final boolean zzuj;
    private final boolean zzuk;
    private final boolean zzul;
    private final int[] zzum;
    private final int zzun;
    private final int zzuo;
    private final zzly zzup;
    private final zzla zzuq;
    private final zzmx<?, ?> zzur;
    private final zzjy<?> zzus;
    private final zzll zzut;

    private zzlu(int[] iArr, Object[] objArr, int i, int i2, zzlq zzlqVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzly zzlyVar, zzla zzlaVar, zzmx<?, ?> zzmxVar, zzjy<?> zzjyVar, zzll zzllVar) {
        this.zzud = iArr;
        this.zzue = objArr;
        this.zzuf = i;
        this.zzug = i2;
        this.zzuj = zzlqVar instanceof zzkk;
        this.zzuk = z;
        this.zzui = zzjyVar != null && zzjyVar.zze(zzlqVar);
        this.zzul = false;
        this.zzum = iArr2;
        this.zzun = i3;
        this.zzuo = i4;
        this.zzup = zzlyVar;
        this.zzuq = zzlaVar;
        this.zzur = zzmxVar;
        this.zzus = zzjyVar;
        this.zzuh = zzlqVar;
        this.zzut = zzllVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> zzlu<T> zza(Class<T> cls, zzlo zzloVar, zzly zzlyVar, zzla zzlaVar, zzmx<?, ?> zzmxVar, zzjy<?> zzjyVar, zzll zzllVar) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        char charAt;
        int i6;
        char charAt2;
        int charAt3;
        int i7;
        int[] iArr;
        int i8;
        char c;
        char c2;
        int i9;
        char charAt4;
        int i10;
        char charAt5;
        int i11;
        char charAt6;
        int i12;
        char charAt7;
        char charAt8;
        char charAt9;
        char charAt10;
        char charAt11;
        int i13;
        int i14;
        char c3;
        char c4;
        int i15;
        int objectFieldOffset;
        String str;
        Class<?> cls2;
        int i16;
        int i17;
        int i18;
        int i19;
        Field zza;
        int i20;
        char charAt12;
        int i21;
        int i22;
        Field zza2;
        Field zza3;
        int i23;
        char charAt13;
        int i24;
        char charAt14;
        int i25;
        char charAt15;
        char charAt16;
        char charAt17;
        if (zzloVar instanceof zzme) {
            zzme zzmeVar = (zzme) zzloVar;
            int i26 = 0;
            boolean z = zzmeVar.zzec() == zzkk.zze.zzsg;
            String zzek = zzmeVar.zzek();
            int length = zzek.length();
            int charAt18 = zzek.charAt(0);
            if (charAt18 >= 55296) {
                int i27 = charAt18 & 8191;
                int i28 = 1;
                int i29 = 13;
                while (true) {
                    i = i28 + 1;
                    charAt17 = zzek.charAt(i28);
                    if (charAt17 < 55296) {
                        break;
                    }
                    i27 |= (charAt17 & 8191) << i29;
                    i29 += 13;
                    i28 = i;
                }
                charAt18 = (charAt17 << i29) | i27;
            } else {
                i = 1;
            }
            int i30 = i + 1;
            int charAt19 = zzek.charAt(i);
            if (charAt19 >= 55296) {
                int i31 = charAt19 & 8191;
                int i32 = 13;
                while (true) {
                    i2 = i30 + 1;
                    charAt16 = zzek.charAt(i30);
                    if (charAt16 < 55296) {
                        break;
                    }
                    i31 |= (charAt16 & 8191) << i32;
                    i32 += 13;
                    i30 = i2;
                }
                charAt19 = i31 | (charAt16 << i32);
            } else {
                i2 = i30;
            }
            if (charAt19 == 0) {
                iArr = zzub;
                charAt3 = 0;
                c2 = 0;
                i8 = 0;
                charAt = 0;
                charAt2 = 0;
                c = 0;
            } else {
                int i33 = i2 + 1;
                char charAt20 = zzek.charAt(i2);
                if (charAt20 >= 55296) {
                    int i34 = charAt20 & 8191;
                    int i35 = 13;
                    while (true) {
                        i3 = i33 + 1;
                        charAt11 = zzek.charAt(i33);
                        if (charAt11 < 55296) {
                            break;
                        }
                        i34 |= (charAt11 & 8191) << i35;
                        i35 += 13;
                        i33 = i3;
                    }
                    charAt20 = (charAt11 << i35) | i34;
                } else {
                    i3 = i33;
                }
                int i36 = i3 + 1;
                int charAt21 = zzek.charAt(i3);
                if (charAt21 >= 55296) {
                    int i37 = charAt21 & 8191;
                    int i38 = 13;
                    while (true) {
                        i4 = i36 + 1;
                        charAt10 = zzek.charAt(i36);
                        if (charAt10 < 55296) {
                            break;
                        }
                        i37 |= (charAt10 & 8191) << i38;
                        i38 += 13;
                        i36 = i4;
                    }
                    charAt21 = i37 | (charAt10 << i38);
                } else {
                    i4 = i36;
                }
                int i39 = i4 + 1;
                char charAt22 = zzek.charAt(i4);
                if (charAt22 >= 55296) {
                    int i40 = charAt22 & 8191;
                    int i41 = 13;
                    while (true) {
                        i5 = i39 + 1;
                        charAt9 = zzek.charAt(i39);
                        if (charAt9 < 55296) {
                            break;
                        }
                        i40 |= (charAt9 & 8191) << i41;
                        i41 += 13;
                        i39 = i5;
                    }
                    charAt22 = (charAt9 << i41) | i40;
                } else {
                    i5 = i39;
                }
                int i42 = i5 + 1;
                charAt = zzek.charAt(i5);
                if (charAt >= 55296) {
                    int i43 = charAt & 8191;
                    int i44 = 13;
                    while (true) {
                        i6 = i42 + 1;
                        charAt8 = zzek.charAt(i42);
                        if (charAt8 < 55296) {
                            break;
                        }
                        i43 |= (charAt8 & 8191) << i44;
                        i44 += 13;
                        i42 = i6;
                    }
                    charAt = (charAt8 << i44) | i43;
                } else {
                    i6 = i42;
                }
                int i45 = i6 + 1;
                charAt2 = zzek.charAt(i6);
                if (charAt2 >= 55296) {
                    int i46 = charAt2 & 8191;
                    int i47 = 13;
                    while (true) {
                        i12 = i45 + 1;
                        charAt7 = zzek.charAt(i45);
                        if (charAt7 < 55296) {
                            break;
                        }
                        i46 |= (charAt7 & 8191) << i47;
                        i47 += 13;
                        i45 = i12;
                    }
                    charAt2 = (charAt7 << i47) | i46;
                    i45 = i12;
                }
                int i48 = i45 + 1;
                charAt3 = zzek.charAt(i45);
                if (charAt3 >= 55296) {
                    int i49 = charAt3 & 8191;
                    int i50 = 13;
                    while (true) {
                        i11 = i48 + 1;
                        charAt6 = zzek.charAt(i48);
                        if (charAt6 < 55296) {
                            break;
                        }
                        i49 |= (charAt6 & 8191) << i50;
                        i50 += 13;
                        i48 = i11;
                    }
                    charAt3 = i49 | (charAt6 << i50);
                    i48 = i11;
                }
                int i51 = i48 + 1;
                int charAt23 = zzek.charAt(i48);
                if (charAt23 >= 55296) {
                    int i52 = 13;
                    int i53 = charAt23 & 8191;
                    int i54 = i51;
                    while (true) {
                        i10 = i54 + 1;
                        charAt5 = zzek.charAt(i54);
                        if (charAt5 < 55296) {
                            break;
                        }
                        i53 |= (charAt5 & 8191) << i52;
                        i52 += 13;
                        i54 = i10;
                    }
                    charAt23 = i53 | (charAt5 << i52);
                    i7 = i10;
                } else {
                    i7 = i51;
                }
                int i55 = i7 + 1;
                i26 = zzek.charAt(i7);
                if (i26 >= 55296) {
                    int i56 = 13;
                    int i57 = i26 & 8191;
                    int i58 = i55;
                    while (true) {
                        i9 = i58 + 1;
                        charAt4 = zzek.charAt(i58);
                        if (charAt4 < 55296) {
                            break;
                        }
                        i57 |= (charAt4 & 8191) << i56;
                        i56 += 13;
                        i58 = i9;
                    }
                    i26 = i57 | (charAt4 << i56);
                    i55 = i9;
                }
                iArr = new int[i26 + charAt3 + charAt23];
                i8 = (charAt20 << 1) + charAt21;
                int i59 = i55;
                c = charAt20;
                c2 = charAt22;
                i2 = i59;
            }
            Unsafe unsafe = zzuc;
            Object[] zzel = zzmeVar.zzel();
            Class<?> cls3 = zzmeVar.zzee().getClass();
            int i60 = i8;
            int[] iArr2 = new int[charAt2 * 3];
            Object[] objArr = new Object[charAt2 << 1];
            int i61 = i26 + charAt3;
            int i62 = i26;
            int i63 = i61;
            int i64 = 0;
            int i65 = 0;
            while (i2 < length) {
                int i66 = i2 + 1;
                int charAt24 = zzek.charAt(i2);
                char c5 = 55296;
                if (charAt24 >= 55296) {
                    int i67 = 13;
                    int i68 = charAt24 & 8191;
                    int i69 = i66;
                    while (true) {
                        i25 = i69 + 1;
                        charAt15 = zzek.charAt(i69);
                        if (charAt15 < c5) {
                            break;
                        }
                        i68 |= (charAt15 & 8191) << i67;
                        i67 += 13;
                        i69 = i25;
                        c5 = 55296;
                    }
                    charAt24 = i68 | (charAt15 << i67);
                    i13 = i25;
                } else {
                    i13 = i66;
                }
                int i70 = i13 + 1;
                int charAt25 = zzek.charAt(i13);
                int i71 = length;
                char c6 = 55296;
                if (charAt25 >= 55296) {
                    int i72 = 13;
                    int i73 = charAt25 & 8191;
                    int i74 = i70;
                    while (true) {
                        i24 = i74 + 1;
                        charAt14 = zzek.charAt(i74);
                        if (charAt14 < c6) {
                            break;
                        }
                        i73 |= (charAt14 & 8191) << i72;
                        i72 += 13;
                        i74 = i24;
                        c6 = 55296;
                    }
                    charAt25 = i73 | (charAt14 << i72);
                    i14 = i24;
                } else {
                    i14 = i70;
                }
                int i75 = i26;
                int i76 = charAt25 & 255;
                boolean z2 = z;
                if ((charAt25 & 1024) != 0) {
                    iArr[i64] = i65;
                    i64++;
                }
                int i77 = i64;
                if (i76 >= 51) {
                    int i78 = i14 + 1;
                    int charAt26 = zzek.charAt(i14);
                    char c7 = 55296;
                    if (charAt26 >= 55296) {
                        int i79 = charAt26 & 8191;
                        int i80 = 13;
                        while (true) {
                            i23 = i78 + 1;
                            charAt13 = zzek.charAt(i78);
                            if (charAt13 < c7) {
                                break;
                            }
                            i79 |= (charAt13 & 8191) << i80;
                            i80 += 13;
                            i78 = i23;
                            c7 = 55296;
                        }
                        charAt26 = i79 | (charAt13 << i80);
                        i78 = i23;
                    }
                    int i81 = i76 - 51;
                    int i82 = i78;
                    if (i81 == 9 || i81 == 17) {
                        i22 = 1;
                        objArr[((i65 / 3) << 1) + 1] = zzel[i60];
                        i60++;
                    } else {
                        if (i81 == 12 && (charAt18 & 1) == 1) {
                            objArr[((i65 / 3) << 1) + 1] = zzel[i60];
                            i60++;
                        }
                        i22 = 1;
                    }
                    int i83 = charAt26 << i22;
                    Object obj = zzel[i83];
                    if (obj instanceof Field) {
                        zza2 = (Field) obj;
                    } else {
                        zza2 = zza(cls3, (String) obj);
                        zzel[i83] = zza2;
                    }
                    char c8 = c2;
                    int objectFieldOffset2 = (int) unsafe.objectFieldOffset(zza2);
                    int i84 = i83 + 1;
                    Object obj2 = zzel[i84];
                    if (obj2 instanceof Field) {
                        zza3 = (Field) obj2;
                    } else {
                        zza3 = zza(cls3, (String) obj2);
                        zzel[i84] = zza3;
                    }
                    str = zzek;
                    i19 = (int) unsafe.objectFieldOffset(zza3);
                    cls2 = cls3;
                    i16 = i60;
                    objectFieldOffset = objectFieldOffset2;
                    i18 = 0;
                    c4 = c8;
                    c3 = charAt;
                    i15 = charAt24;
                    i2 = i82;
                } else {
                    char c9 = c2;
                    int i85 = i60 + 1;
                    Field zza4 = zza(cls3, (String) zzel[i60]);
                    c3 = charAt;
                    if (i76 == 9 || i76 == 17) {
                        c4 = c9;
                        objArr[((i65 / 3) << 1) + 1] = zza4.getType();
                    } else {
                        if (i76 == 27 || i76 == 49) {
                            c4 = c9;
                            i21 = i85 + 1;
                            objArr[((i65 / 3) << 1) + 1] = zzel[i85];
                        } else if (i76 == 12 || i76 == 30 || i76 == 44) {
                            c4 = c9;
                            if ((charAt18 & 1) == 1) {
                                i21 = i85 + 1;
                                objArr[((i65 / 3) << 1) + 1] = zzel[i85];
                            }
                        } else if (i76 == 50) {
                            int i86 = i62 + 1;
                            iArr[i62] = i65;
                            int i87 = (i65 / 3) << 1;
                            int i88 = i85 + 1;
                            objArr[i87] = zzel[i85];
                            if ((charAt25 & 2048) != 0) {
                                i85 = i88 + 1;
                                objArr[i87 + 1] = zzel[i88];
                                c4 = c9;
                                i62 = i86;
                            } else {
                                i62 = i86;
                                i85 = i88;
                                c4 = c9;
                            }
                        } else {
                            c4 = c9;
                        }
                        i15 = charAt24;
                        i85 = i21;
                        objectFieldOffset = (int) unsafe.objectFieldOffset(zza4);
                        if ((charAt18 & 1) == 1 || i76 > 17) {
                            str = zzek;
                            cls2 = cls3;
                            i16 = i85;
                            i17 = i14;
                            i18 = 0;
                            i19 = 0;
                        } else {
                            i17 = i14 + 1;
                            int charAt27 = zzek.charAt(i14);
                            if (charAt27 >= 55296) {
                                int i89 = charAt27 & 8191;
                                int i90 = 13;
                                while (true) {
                                    i20 = i17 + 1;
                                    charAt12 = zzek.charAt(i17);
                                    if (charAt12 < 55296) {
                                        break;
                                    }
                                    i89 |= (charAt12 & 8191) << i90;
                                    i90 += 13;
                                    i17 = i20;
                                }
                                charAt27 = i89 | (charAt12 << i90);
                                i17 = i20;
                            }
                            int i91 = (c << 1) + (charAt27 / 32);
                            Object obj3 = zzel[i91];
                            str = zzek;
                            if (obj3 instanceof Field) {
                                zza = (Field) obj3;
                            } else {
                                zza = zza(cls3, (String) obj3);
                                zzel[i91] = zza;
                            }
                            cls2 = cls3;
                            i16 = i85;
                            i19 = (int) unsafe.objectFieldOffset(zza);
                            i18 = charAt27 % 32;
                        }
                        if (i76 >= 18 && i76 <= 49) {
                            iArr[i63] = objectFieldOffset;
                            i63++;
                        }
                        i2 = i17;
                    }
                    i15 = charAt24;
                    objectFieldOffset = (int) unsafe.objectFieldOffset(zza4);
                    if ((charAt18 & 1) == 1) {
                    }
                    str = zzek;
                    cls2 = cls3;
                    i16 = i85;
                    i17 = i14;
                    i18 = 0;
                    i19 = 0;
                    if (i76 >= 18) {
                        iArr[i63] = objectFieldOffset;
                        i63++;
                    }
                    i2 = i17;
                }
                int i92 = i65 + 1;
                iArr2[i65] = i15;
                int i93 = i92 + 1;
                iArr2[i92] = (i76 << 20) | ((charAt25 & 256) != 0 ? DriveFile.MODE_READ_ONLY : 0) | ((charAt25 & 512) != 0 ? DriveFile.MODE_WRITE_ONLY : 0) | objectFieldOffset;
                i65 = i93 + 1;
                iArr2[i93] = (i18 << 20) | i19;
                cls3 = cls2;
                charAt = c3;
                i26 = i75;
                i60 = i16;
                length = i71;
                z = z2;
                c2 = c4;
                i64 = i77;
                zzek = str;
            }
            return new zzlu<>(iArr2, objArr, c2, charAt, zzmeVar.zzee(), z, false, iArr, i26, i61, zzlyVar, zzlaVar, zzmxVar, zzjyVar, zzllVar);
        }
        ((zzms) zzloVar).zzec();
        int i94 = zzkk.zze.zzsg;
        throw new NoSuchMethodError();
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(arrays).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(arrays);
            throw new RuntimeException(sb.toString());
        }
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final T newInstance() {
        return (T) this.zzup.newInstance(this.zzuh);
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01bf, code lost:
        if (java.lang.Double.doubleToLongBits(com.google.android.gms.internal.drive.zznd.zzn(r10, r6)) == java.lang.Double.doubleToLongBits(com.google.android.gms.internal.drive.zznd.zzn(r11, r6))) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0038, code lost:
        if (com.google.android.gms.internal.drive.zzmh.zzd(com.google.android.gms.internal.drive.zznd.zzo(r10, r6), com.google.android.gms.internal.drive.zznd.zzo(r11, r6)) != false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x006a, code lost:
        if (com.google.android.gms.internal.drive.zzmh.zzd(com.google.android.gms.internal.drive.zznd.zzo(r10, r6), com.google.android.gms.internal.drive.zznd.zzo(r11, r6)) != false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x007e, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzk(r10, r6) == com.google.android.gms.internal.drive.zznd.zzk(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0090, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzj(r10, r6) == com.google.android.gms.internal.drive.zznd.zzj(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a4, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzk(r10, r6) == com.google.android.gms.internal.drive.zznd.zzk(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b6, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzj(r10, r6) == com.google.android.gms.internal.drive.zznd.zzj(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00c8, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzj(r10, r6) == com.google.android.gms.internal.drive.zznd.zzj(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00da, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzj(r10, r6) == com.google.android.gms.internal.drive.zznd.zzj(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f0, code lost:
        if (com.google.android.gms.internal.drive.zzmh.zzd(com.google.android.gms.internal.drive.zznd.zzo(r10, r6), com.google.android.gms.internal.drive.zznd.zzo(r11, r6)) != false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0106, code lost:
        if (com.google.android.gms.internal.drive.zzmh.zzd(com.google.android.gms.internal.drive.zznd.zzo(r10, r6), com.google.android.gms.internal.drive.zznd.zzo(r11, r6)) != false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x011c, code lost:
        if (com.google.android.gms.internal.drive.zzmh.zzd(com.google.android.gms.internal.drive.zznd.zzo(r10, r6), com.google.android.gms.internal.drive.zznd.zzo(r11, r6)) != false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x012e, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzl(r10, r6) == com.google.android.gms.internal.drive.zznd.zzl(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0140, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzj(r10, r6) == com.google.android.gms.internal.drive.zznd.zzj(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0154, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzk(r10, r6) == com.google.android.gms.internal.drive.zznd.zzk(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0165, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzj(r10, r6) == com.google.android.gms.internal.drive.zznd.zzj(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0178, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzk(r10, r6) == com.google.android.gms.internal.drive.zznd.zzk(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x018b, code lost:
        if (com.google.android.gms.internal.drive.zznd.zzk(r10, r6) == com.google.android.gms.internal.drive.zznd.zzk(r11, r6)) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01a4, code lost:
        if (java.lang.Float.floatToIntBits(com.google.android.gms.internal.drive.zznd.zzm(r10, r6)) == java.lang.Float.floatToIntBits(com.google.android.gms.internal.drive.zznd.zzm(r11, r6))) goto L85;
     */
    @Override // com.google.android.gms.internal.drive.zzmf
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.drive.zzlu.equals(java.lang.Object, java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final int hashCode(T t) {
        int i;
        int zzu;
        int length = this.zzud.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int zzas = zzas(i3);
            int i4 = this.zzud[i3];
            long j = 1048575 & zzas;
            int i5 = 37;
            switch ((zzas & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    zzu = zzkm.zzu(Double.doubleToLongBits(zznd.zzn(t, j)));
                    i2 = i + zzu;
                    break;
                case 1:
                    i = i2 * 53;
                    zzu = Float.floatToIntBits(zznd.zzm(t, j));
                    i2 = i + zzu;
                    break;
                case 2:
                    i = i2 * 53;
                    zzu = zzkm.zzu(zznd.zzk(t, j));
                    i2 = i + zzu;
                    break;
                case 3:
                    i = i2 * 53;
                    zzu = zzkm.zzu(zznd.zzk(t, j));
                    i2 = i + zzu;
                    break;
                case 4:
                    i = i2 * 53;
                    zzu = zznd.zzj(t, j);
                    i2 = i + zzu;
                    break;
                case 5:
                    i = i2 * 53;
                    zzu = zzkm.zzu(zznd.zzk(t, j));
                    i2 = i + zzu;
                    break;
                case 6:
                    i = i2 * 53;
                    zzu = zznd.zzj(t, j);
                    i2 = i + zzu;
                    break;
                case 7:
                    i = i2 * 53;
                    zzu = zzkm.zze(zznd.zzl(t, j));
                    i2 = i + zzu;
                    break;
                case 8:
                    i = i2 * 53;
                    zzu = ((String) zznd.zzo(t, j)).hashCode();
                    i2 = i + zzu;
                    break;
                case 9:
                    Object zzo = zznd.zzo(t, j);
                    if (zzo != null) {
                        i5 = zzo.hashCode();
                    }
                    i2 = (i2 * 53) + i5;
                    break;
                case 10:
                    i = i2 * 53;
                    zzu = zznd.zzo(t, j).hashCode();
                    i2 = i + zzu;
                    break;
                case 11:
                    i = i2 * 53;
                    zzu = zznd.zzj(t, j);
                    i2 = i + zzu;
                    break;
                case 12:
                    i = i2 * 53;
                    zzu = zznd.zzj(t, j);
                    i2 = i + zzu;
                    break;
                case 13:
                    i = i2 * 53;
                    zzu = zznd.zzj(t, j);
                    i2 = i + zzu;
                    break;
                case 14:
                    i = i2 * 53;
                    zzu = zzkm.zzu(zznd.zzk(t, j));
                    i2 = i + zzu;
                    break;
                case 15:
                    i = i2 * 53;
                    zzu = zznd.zzj(t, j);
                    i2 = i + zzu;
                    break;
                case 16:
                    i = i2 * 53;
                    zzu = zzkm.zzu(zznd.zzk(t, j));
                    i2 = i + zzu;
                    break;
                case 17:
                    Object zzo2 = zznd.zzo(t, j);
                    if (zzo2 != null) {
                        i5 = zzo2.hashCode();
                    }
                    i2 = (i2 * 53) + i5;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    zzu = zznd.zzo(t, j).hashCode();
                    i2 = i + zzu;
                    break;
                case 50:
                    i = i2 * 53;
                    zzu = zznd.zzo(t, j).hashCode();
                    i2 = i + zzu;
                    break;
                case 51:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzkm.zzu(Double.doubleToLongBits(zze(t, j)));
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = Float.floatToIntBits(zzf(t, j));
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzkm.zzu(zzh(t, j));
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzkm.zzu(zzh(t, j));
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzg(t, j);
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzkm.zzu(zzh(t, j));
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzg(t, j);
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzkm.zze(zzi(t, j));
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = ((String) zznd.zzo(t, j)).hashCode();
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zznd.zzo(t, j).hashCode();
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zznd.zzo(t, j).hashCode();
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzg(t, j);
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzg(t, j);
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzg(t, j);
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzkm.zzu(zzh(t, j));
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzg(t, j);
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zzkm.zzu(zzh(t, j));
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zza((zzlu<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zzu = zznd.zzo(t, j).hashCode();
                        i2 = i + zzu;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int hashCode = (i2 * 53) + this.zzur.zzr(t).hashCode();
        return this.zzui ? (hashCode * 53) + this.zzus.zzb(t).hashCode() : hashCode;
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final void zzc(T t, T t2) {
        if (t2 == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < this.zzud.length; i += 3) {
            int zzas = zzas(i);
            long j = 1048575 & zzas;
            int i2 = this.zzud[i];
            switch ((zzas & 267386880) >>> 20) {
                case 0:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza(t, j, zznd.zzn(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzm(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzk(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzk(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzj(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzk(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzj(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza(t, j, zznd.zzl(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza(t, j, zznd.zzo(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza(t, j, zznd.zzo(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzj(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzj(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzj(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzk(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzj(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zza((zzlu<T>) t2, i)) {
                        zznd.zza((Object) t, j, zznd.zzk(t2, j));
                        zzb((zzlu<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zza(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzuq.zza(t, t2, j);
                    break;
                case 50:
                    zzmh.zza(this.zzut, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zza((zzlu<T>) t2, i2, i)) {
                        zznd.zza(t, j, zznd.zzo(t2, j));
                        zzb((zzlu<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzb(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zza((zzlu<T>) t2, i2, i)) {
                        zznd.zza(t, j, zznd.zzo(t2, j));
                        zzb((zzlu<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        if (this.zzuk) {
            return;
        }
        zzmh.zza(this.zzur, t, t2);
        if (this.zzui) {
            zzmh.zza(this.zzus, t, t2);
        }
    }

    private final void zza(T t, T t2, int i) {
        long zzas = zzas(i) & 1048575;
        if (zza((zzlu<T>) t2, i)) {
            Object zzo = zznd.zzo(t, zzas);
            Object zzo2 = zznd.zzo(t2, zzas);
            if (zzo != null && zzo2 != null) {
                zznd.zza(t, zzas, zzkm.zza(zzo, zzo2));
                zzb((zzlu<T>) t, i);
            } else if (zzo2 != null) {
                zznd.zza(t, zzas, zzo2);
                zzb((zzlu<T>) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int zzas = zzas(i);
        int i2 = this.zzud[i];
        long j = zzas & 1048575;
        if (zza((zzlu<T>) t2, i2, i)) {
            Object zzo = zznd.zzo(t, j);
            Object zzo2 = zznd.zzo(t2, j);
            if (zzo != null && zzo2 != null) {
                zznd.zza(t, j, zzkm.zza(zzo, zzo2));
                zzb((zzlu<T>) t, i2, i);
            } else if (zzo2 != null) {
                zznd.zza(t, j, zzo2);
                zzb((zzlu<T>) t, i2, i);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.drive.zzmf
    public final int zzn(T t) {
        int i;
        int i2;
        long j;
        int zzd;
        int zzb;
        int zzk;
        int zzv;
        int zzi;
        int zzab;
        int zzad;
        int zzb2;
        int zzi2;
        int zzab2;
        int zzad2;
        int i3 = 267386880;
        int i4 = 1048575;
        int i5 = 1;
        if (this.zzuk) {
            Unsafe unsafe = zzuc;
            int i6 = 0;
            int i7 = 0;
            while (i6 < this.zzud.length) {
                int zzas = zzas(i6);
                int i8 = (zzas & i3) >>> 20;
                int i9 = this.zzud[i6];
                long j2 = zzas & 1048575;
                int i10 = (i8 < zzke.DOUBLE_LIST_PACKED.id() || i8 > zzke.SINT64_LIST_PACKED.id()) ? 0 : this.zzud[i6 + 2] & 1048575;
                switch (i8) {
                    case 0:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzb(i9, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 1:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzb(i9, 0.0f);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 2:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzd(i9, zznd.zzk(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 3:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zze(i9, zznd.zzk(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 4:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzg(i9, zznd.zzj(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 5:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzg(i9, 0L);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 6:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzj(i9, 0);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 7:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzc(i9, true);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 8:
                        if (zza((zzlu<T>) t, i6)) {
                            Object zzo = zznd.zzo(t, j2);
                            if (zzo instanceof zzjc) {
                                zzb2 = zzjr.zzc(i9, (zzjc) zzo);
                                break;
                            } else {
                                zzb2 = zzjr.zzb(i9, (String) zzo);
                                break;
                            }
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 9:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzmh.zzc(i9, zznd.zzo(t, j2), zzap(i6));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 10:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzc(i9, (zzjc) zznd.zzo(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 11:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzh(i9, zznd.zzj(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 12:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzl(i9, zznd.zzj(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 13:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzk(i9, 0);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 14:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzh(i9, 0L);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 15:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzi(i9, zznd.zzj(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 16:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzf(i9, zznd.zzk(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 17:
                        if (zza((zzlu<T>) t, i6)) {
                            zzb2 = zzjr.zzc(i9, (zzlq) zznd.zzo(t, j2), zzap(i6));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 18:
                        zzb2 = zzmh.zzw(i9, zzd(t, j2), false);
                        break;
                    case 19:
                        zzb2 = zzmh.zzv(i9, zzd(t, j2), false);
                        break;
                    case 20:
                        zzb2 = zzmh.zzo(i9, zzd(t, j2), false);
                        break;
                    case 21:
                        zzb2 = zzmh.zzp(i9, zzd(t, j2), false);
                        break;
                    case 22:
                        zzb2 = zzmh.zzs(i9, zzd(t, j2), false);
                        break;
                    case 23:
                        zzb2 = zzmh.zzw(i9, zzd(t, j2), false);
                        break;
                    case 24:
                        zzb2 = zzmh.zzv(i9, zzd(t, j2), false);
                        break;
                    case 25:
                        zzb2 = zzmh.zzx(i9, zzd(t, j2), false);
                        break;
                    case 26:
                        zzb2 = zzmh.zzc(i9, zzd(t, j2));
                        break;
                    case 27:
                        zzb2 = zzmh.zzc(i9, (List<?>) zzd(t, j2), zzap(i6));
                        break;
                    case 28:
                        zzb2 = zzmh.zzd(i9, zzd(t, j2));
                        break;
                    case 29:
                        zzb2 = zzmh.zzt(i9, zzd(t, j2), false);
                        break;
                    case 30:
                        zzb2 = zzmh.zzr(i9, zzd(t, j2), false);
                        break;
                    case 31:
                        zzb2 = zzmh.zzv(i9, zzd(t, j2), false);
                        break;
                    case 32:
                        zzb2 = zzmh.zzw(i9, zzd(t, j2), false);
                        break;
                    case 33:
                        zzb2 = zzmh.zzu(i9, zzd(t, j2), false);
                        break;
                    case 34:
                        zzb2 = zzmh.zzq(i9, zzd(t, j2), false);
                        break;
                    case 35:
                        zzi2 = zzmh.zzi((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 36:
                        zzi2 = zzmh.zzh((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 37:
                        zzi2 = zzmh.zza((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 38:
                        zzi2 = zzmh.zzb((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 39:
                        zzi2 = zzmh.zze((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 40:
                        zzi2 = zzmh.zzi((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 41:
                        zzi2 = zzmh.zzh((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 42:
                        zzi2 = zzmh.zzj((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 43:
                        zzi2 = zzmh.zzf((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 44:
                        zzi2 = zzmh.zzd((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 45:
                        zzi2 = zzmh.zzh((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 46:
                        zzi2 = zzmh.zzi((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 47:
                        zzi2 = zzmh.zzg((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 48:
                        zzi2 = zzmh.zzc((List) unsafe.getObject(t, j2));
                        if (zzi2 > 0) {
                            if (this.zzul) {
                                unsafe.putInt(t, i10, zzi2);
                            }
                            zzab2 = zzjr.zzab(i9);
                            zzad2 = zzjr.zzad(zzi2);
                            zzb2 = zzab2 + zzad2 + zzi2;
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 49:
                        zzb2 = zzmh.zzd(i9, zzd(t, j2), zzap(i6));
                        break;
                    case 50:
                        zzb2 = this.zzut.zzb(i9, zznd.zzo(t, j2), zzaq(i6));
                        break;
                    case 51:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzb(i9, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 52:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzb(i9, 0.0f);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 53:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzd(i9, zzh(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 54:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zze(i9, zzh(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 55:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzg(i9, zzg(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 56:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzg(i9, 0L);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 57:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzj(i9, 0);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 58:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzc(i9, true);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 59:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            Object zzo2 = zznd.zzo(t, j2);
                            if (zzo2 instanceof zzjc) {
                                zzb2 = zzjr.zzc(i9, (zzjc) zzo2);
                                break;
                            } else {
                                zzb2 = zzjr.zzb(i9, (String) zzo2);
                                break;
                            }
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 60:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzmh.zzc(i9, zznd.zzo(t, j2), zzap(i6));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 61:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzc(i9, (zzjc) zznd.zzo(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 62:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzh(i9, zzg(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 63:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzl(i9, zzg(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 64:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzk(i9, 0);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 65:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzh(i9, 0L);
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 66:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzi(i9, zzg(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 67:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzf(i9, zzh(t, j2));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    case 68:
                        if (zza((zzlu<T>) t, i9, i6)) {
                            zzb2 = zzjr.zzc(i9, (zzlq) zznd.zzo(t, j2), zzap(i6));
                            break;
                        } else {
                            continue;
                            i6 += 3;
                            i3 = 267386880;
                        }
                    default:
                        i6 += 3;
                        i3 = 267386880;
                }
                i7 += zzb2;
                i6 += 3;
                i3 = 267386880;
            }
            return i7 + zza(this.zzur, t);
        }
        Unsafe unsafe2 = zzuc;
        int i11 = 0;
        int i12 = 0;
        int i13 = -1;
        int i14 = 0;
        while (i11 < this.zzud.length) {
            int zzas2 = zzas(i11);
            int[] iArr = this.zzud;
            int i15 = iArr[i11];
            int i16 = (zzas2 & 267386880) >>> 20;
            if (i16 <= 17) {
                i = iArr[i11 + 2];
                int i17 = i & i4;
                i2 = i5 << (i >>> 20);
                if (i17 != i13) {
                    i14 = unsafe2.getInt(t, i17);
                } else {
                    i17 = i13;
                }
                i13 = i17;
            } else {
                i = (!this.zzul || i16 < zzke.DOUBLE_LIST_PACKED.id() || i16 > zzke.SINT64_LIST_PACKED.id()) ? 0 : this.zzud[i11 + 2] & i4;
                i2 = 0;
            }
            long j3 = zzas2 & i4;
            switch (i16) {
                case 0:
                    j = 0;
                    if ((i14 & i2) != 0) {
                        i12 += zzjr.zzb(i15, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
                        continue;
                        i11 += 3;
                        i4 = 1048575;
                        i5 = 1;
                    }
                    break;
                case 1:
                    j = 0;
                    if ((i14 & i2) != 0) {
                        i12 += zzjr.zzb(i15, 0.0f);
                        break;
                    }
                    break;
                case 2:
                    j = 0;
                    if ((i14 & i2) != 0) {
                        zzd = zzjr.zzd(i15, unsafe2.getLong(t, j3));
                        i12 += zzd;
                        break;
                    }
                    break;
                case 3:
                    j = 0;
                    if ((i14 & i2) != 0) {
                        zzd = zzjr.zze(i15, unsafe2.getLong(t, j3));
                        i12 += zzd;
                        break;
                    }
                    break;
                case 4:
                    j = 0;
                    if ((i14 & i2) != 0) {
                        zzd = zzjr.zzg(i15, unsafe2.getInt(t, j3));
                        i12 += zzd;
                        break;
                    }
                    break;
                case 5:
                    j = 0;
                    if ((i14 & i2) != 0) {
                        zzd = zzjr.zzg(i15, 0L);
                        i12 += zzd;
                        break;
                    }
                    break;
                case 6:
                    if ((i14 & i2) != 0) {
                        i12 += zzjr.zzj(i15, 0);
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 7:
                    if ((i14 & i2) != 0) {
                        i12 += zzjr.zzc(i15, true);
                        j = 0;
                        i11 += 3;
                        i4 = 1048575;
                        i5 = 1;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 8:
                    if ((i14 & i2) != 0) {
                        Object object = unsafe2.getObject(t, j3);
                        if (object instanceof zzjc) {
                            zzb = zzjr.zzc(i15, (zzjc) object);
                        } else {
                            zzb = zzjr.zzb(i15, (String) object);
                        }
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 9:
                    if ((i14 & i2) != 0) {
                        zzb = zzmh.zzc(i15, unsafe2.getObject(t, j3), zzap(i11));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 10:
                    if ((i14 & i2) != 0) {
                        zzb = zzjr.zzc(i15, (zzjc) unsafe2.getObject(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 11:
                    if ((i14 & i2) != 0) {
                        zzb = zzjr.zzh(i15, unsafe2.getInt(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 12:
                    if ((i14 & i2) != 0) {
                        zzb = zzjr.zzl(i15, unsafe2.getInt(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 13:
                    if ((i14 & i2) != 0) {
                        zzk = zzjr.zzk(i15, 0);
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 14:
                    if ((i14 & i2) != 0) {
                        zzb = zzjr.zzh(i15, 0L);
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 15:
                    if ((i14 & i2) != 0) {
                        zzb = zzjr.zzi(i15, unsafe2.getInt(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 16:
                    if ((i14 & i2) != 0) {
                        zzb = zzjr.zzf(i15, unsafe2.getLong(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 17:
                    if ((i14 & i2) != 0) {
                        zzb = zzjr.zzc(i15, (zzlq) unsafe2.getObject(t, j3), zzap(i11));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 18:
                    zzb = zzmh.zzw(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzb;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 19:
                    zzv = zzmh.zzv(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 20:
                    zzv = zzmh.zzo(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 21:
                    zzv = zzmh.zzp(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 22:
                    zzv = zzmh.zzs(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 23:
                    zzv = zzmh.zzw(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 24:
                    zzv = zzmh.zzv(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 25:
                    zzv = zzmh.zzx(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 26:
                    zzb = zzmh.zzc(i15, (List) unsafe2.getObject(t, j3));
                    i12 += zzb;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 27:
                    zzb = zzmh.zzc(i15, (List<?>) unsafe2.getObject(t, j3), zzap(i11));
                    i12 += zzb;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 28:
                    zzb = zzmh.zzd(i15, (List) unsafe2.getObject(t, j3));
                    i12 += zzb;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 29:
                    zzb = zzmh.zzt(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzb;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 30:
                    zzv = zzmh.zzr(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 31:
                    zzv = zzmh.zzv(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 32:
                    zzv = zzmh.zzw(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 33:
                    zzv = zzmh.zzu(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 34:
                    zzv = zzmh.zzq(i15, (List) unsafe2.getObject(t, j3), false);
                    i12 += zzv;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 35:
                    zzi = zzmh.zzi((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 36:
                    zzi = zzmh.zzh((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 37:
                    zzi = zzmh.zza((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 38:
                    zzi = zzmh.zzb((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 39:
                    zzi = zzmh.zze((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 40:
                    zzi = zzmh.zzi((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 41:
                    zzi = zzmh.zzh((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 42:
                    zzi = zzmh.zzj((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 43:
                    zzi = zzmh.zzf((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 44:
                    zzi = zzmh.zzd((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 45:
                    zzi = zzmh.zzh((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 46:
                    zzi = zzmh.zzi((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 47:
                    zzi = zzmh.zzg((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 48:
                    zzi = zzmh.zzc((List) unsafe2.getObject(t, j3));
                    if (zzi > 0) {
                        if (this.zzul) {
                            unsafe2.putInt(t, i, zzi);
                        }
                        zzab = zzjr.zzab(i15);
                        zzad = zzjr.zzad(zzi);
                        zzk = zzab + zzad + zzi;
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 49:
                    zzb = zzmh.zzd(i15, (List) unsafe2.getObject(t, j3), zzap(i11));
                    i12 += zzb;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 50:
                    zzb = this.zzut.zzb(i15, unsafe2.getObject(t, j3), zzaq(i11));
                    i12 += zzb;
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 51:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzb(i15, (double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 52:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzk = zzjr.zzb(i15, 0.0f);
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 53:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzd(i15, zzh(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 54:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zze(i15, zzh(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 55:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzg(i15, zzg(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 56:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzg(i15, 0L);
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 57:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzk = zzjr.zzj(i15, 0);
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 58:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzk = zzjr.zzc(i15, true);
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 59:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        Object object2 = unsafe2.getObject(t, j3);
                        if (object2 instanceof zzjc) {
                            zzb = zzjr.zzc(i15, (zzjc) object2);
                        } else {
                            zzb = zzjr.zzb(i15, (String) object2);
                        }
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 60:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzmh.zzc(i15, unsafe2.getObject(t, j3), zzap(i11));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 61:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzc(i15, (zzjc) unsafe2.getObject(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 62:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzh(i15, zzg(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 63:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzl(i15, zzg(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 64:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzk = zzjr.zzk(i15, 0);
                        i12 += zzk;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 65:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzh(i15, 0L);
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 66:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzi(i15, zzg(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 67:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzf(i15, zzh(t, j3));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                case 68:
                    if (zza((zzlu<T>) t, i15, i11)) {
                        zzb = zzjr.zzc(i15, (zzlq) unsafe2.getObject(t, j3), zzap(i11));
                        i12 += zzb;
                    }
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
                default:
                    j = 0;
                    i11 += 3;
                    i4 = 1048575;
                    i5 = 1;
            }
            i11 += 3;
            i4 = 1048575;
            i5 = 1;
        }
        int zza = i12 + zza(this.zzur, t);
        if (this.zzui) {
            zzkb<?> zzb3 = this.zzus.zzb(t);
            int i18 = 0;
            for (int i19 = 0; i19 < zzb3.zzos.zzer(); i19++) {
                Map.Entry<?, Object> zzaw = zzb3.zzos.zzaw(i19);
                i18 += zzkb.zzb((zzkd) zzaw.getKey(), zzaw.getValue());
            }
            for (Map.Entry<?, Object> entry : zzb3.zzos.zzes()) {
                i18 += zzkb.zzb((zzkd) entry.getKey(), entry.getValue());
            }
            return zza + i18;
        }
        return zza;
    }

    private static <UT, UB> int zza(zzmx<UT, UB> zzmxVar, T t) {
        return zzmxVar.zzn(zzmxVar.zzr(t));
    }

    private static <E> List<E> zzd(Object obj, long j) {
        return (List) zznd.zzo(obj, j);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0513  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0a2b  */
    @Override // com.google.android.gms.internal.drive.zzmf
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r14, com.google.android.gms.internal.drive.zzns r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2918
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.drive.zzlu.zza(java.lang.Object, com.google.android.gms.internal.drive.zzns):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x04b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzb(T r19, com.google.android.gms.internal.drive.zzns r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.drive.zzlu.zzb(java.lang.Object, com.google.android.gms.internal.drive.zzns):void");
    }

    private final <K, V> void zza(zzns zznsVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zznsVar.zza(i, this.zzut.zzm(zzaq(i2)), this.zzut.zzi(obj));
        }
    }

    private static <UT, UB> void zza(zzmx<UT, UB> zzmxVar, T t, zzns zznsVar) throws IOException {
        zzmxVar.zza(zzmxVar.zzr(t), zznsVar);
    }

    private static zzmy zzo(Object obj) {
        zzkk zzkkVar = (zzkk) obj;
        zzmy zzmyVar = zzkkVar.zzrq;
        if (zzmyVar == zzmy.zzfa()) {
            zzmy zzfb = zzmy.zzfb();
            zzkkVar.zzrq = zzfb;
            return zzfb;
        }
        return zzmyVar;
    }

    private static int zza(byte[] bArr, int i, int i2, zznm zznmVar, Class<?> cls, zziz zzizVar) throws IOException {
        switch (zzlv.zzox[zznmVar.ordinal()]) {
            case 1:
                int zzb = zziy.zzb(bArr, i, zzizVar);
                zzizVar.zznm = Boolean.valueOf(zzizVar.zznl != 0);
                return zzb;
            case 2:
                return zziy.zze(bArr, i, zzizVar);
            case 3:
                zzizVar.zznm = Double.valueOf(zziy.zzc(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzizVar.zznm = Integer.valueOf(zziy.zza(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzizVar.zznm = Long.valueOf(zziy.zzb(bArr, i));
                return i + 8;
            case 8:
                zzizVar.zznm = Float.valueOf(zziy.zzd(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int zza = zziy.zza(bArr, i, zzizVar);
                zzizVar.zznm = Integer.valueOf(zzizVar.zznk);
                return zza;
            case 12:
            case 13:
                int zzb2 = zziy.zzb(bArr, i, zzizVar);
                zzizVar.zznm = Long.valueOf(zzizVar.zznl);
                return zzb2;
            case 14:
                return zziy.zza(zzmd.zzej().zzf(cls), bArr, i, i2, zzizVar);
            case 15:
                int zza2 = zziy.zza(bArr, i, zzizVar);
                zzizVar.zznm = Integer.valueOf(zzjo.zzw(zzizVar.zznk));
                return zza2;
            case 16:
                int zzb3 = zziy.zzb(bArr, i, zzizVar);
                zzizVar.zznm = Long.valueOf(zzjo.zzk(zzizVar.zznl));
                return zzb3;
            case 17:
                return zziy.zzd(bArr, i, zzizVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x0239  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01eb  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:115:0x0236 -> B:116:0x0237). Please submit an issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:66:0x016e -> B:67:0x016f). Please submit an issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:95:0x01e8 -> B:96:0x01e9). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int zza(T r17, byte[] r18, int r19, int r20, int r21, int r22, int r23, int r24, long r25, int r27, long r28, com.google.android.gms.internal.drive.zziz r30) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1128
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.drive.zzlu.zza(java.lang.Object, byte[], int, int, int, int, int, int, long, int, long, com.google.android.gms.internal.drive.zziz):int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <K, V> int zza(T t, byte[] bArr, int i, int i2, int i3, long j, zziz zzizVar) throws IOException {
        Unsafe unsafe = zzuc;
        Object zzaq = zzaq(i3);
        Object object = unsafe.getObject(t, j);
        if (this.zzut.zzj(object)) {
            Object zzl = this.zzut.zzl(zzaq);
            this.zzut.zzb(zzl, object);
            unsafe.putObject(t, j, zzl);
            object = zzl;
        }
        zzlj<?, ?> zzm = this.zzut.zzm(zzaq);
        Map<?, ?> zzh = this.zzut.zzh(object);
        int zza = zziy.zza(bArr, i, zzizVar);
        int i4 = zzizVar.zznk;
        if (i4 < 0 || i4 > i2 - zza) {
            throw zzkq.zzdi();
        }
        int i5 = i4 + zza;
        Object obj = (K) zzm.zztv;
        Object obj2 = (V) zzm.zztx;
        while (zza < i5) {
            int i6 = zza + 1;
            int i7 = bArr[zza];
            if (i7 < 0) {
                i6 = zziy.zza(i7, bArr, i6, zzizVar);
                i7 = zzizVar.zznk;
            }
            int i8 = i6;
            int i9 = i7 >>> 3;
            int i10 = i7 & 7;
            if (i9 == 1) {
                if (i10 == zzm.zztu.zzfk()) {
                    zza = zza(bArr, i8, i2, zzm.zztu, (Class<?>) null, zzizVar);
                    obj = (K) zzizVar.zznm;
                } else {
                    zza = zziy.zza(i7, bArr, i8, i2, zzizVar);
                }
            } else {
                if (i9 == 2 && i10 == zzm.zztw.zzfk()) {
                    zza = zza(bArr, i8, i2, zzm.zztw, zzm.zztx.getClass(), zzizVar);
                    obj2 = zzizVar.zznm;
                }
                zza = zziy.zza(i7, bArr, i8, i2, zzizVar);
            }
        }
        if (zza != i5) {
            throw zzkq.zzdm();
        }
        zzh.put(obj, obj2);
        return i5;
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zziz zzizVar) throws IOException {
        int zzb;
        Unsafe unsafe = zzuc;
        long j2 = this.zzud[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Double.valueOf(zziy.zzc(bArr, i)));
                    zzb = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Float.valueOf(zziy.zzd(bArr, i)));
                    zzb = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 53:
            case 54:
                if (i5 == 0) {
                    zzb = zziy.zzb(bArr, i, zzizVar);
                    unsafe.putObject(t, j, Long.valueOf(zzizVar.zznl));
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 55:
            case 62:
                if (i5 == 0) {
                    zzb = zziy.zza(bArr, i, zzizVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzizVar.zznk));
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Long.valueOf(zziy.zzb(bArr, i)));
                    zzb = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Integer.valueOf(zziy.zza(bArr, i)));
                    zzb = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 58:
                if (i5 == 0) {
                    zzb = zziy.zzb(bArr, i, zzizVar);
                    unsafe.putObject(t, j, Boolean.valueOf(zzizVar.zznl != 0));
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 59:
                if (i5 == 2) {
                    int zza = zziy.zza(bArr, i, zzizVar);
                    int i9 = zzizVar.zznk;
                    if (i9 == 0) {
                        unsafe.putObject(t, j, "");
                    } else if ((i6 & DriveFile.MODE_WRITE_ONLY) != 0 && !zznf.zze(bArr, zza, zza + i9)) {
                        throw zzkq.zzdn();
                    } else {
                        unsafe.putObject(t, j, new String(bArr, zza, i9, zzkm.UTF_8));
                        zza += i9;
                    }
                    unsafe.putInt(t, j2, i4);
                    return zza;
                }
                return i;
            case 60:
                if (i5 == 2) {
                    int zza2 = zziy.zza(zzap(i8), bArr, i, i2, zzizVar);
                    Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object == null) {
                        unsafe.putObject(t, j, zzizVar.zznm);
                    } else {
                        unsafe.putObject(t, j, zzkm.zza(object, zzizVar.zznm));
                    }
                    unsafe.putInt(t, j2, i4);
                    return zza2;
                }
                return i;
            case 61:
                if (i5 == 2) {
                    zzb = zziy.zze(bArr, i, zzizVar);
                    unsafe.putObject(t, j, zzizVar.zznm);
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 63:
                if (i5 == 0) {
                    int zza3 = zziy.zza(bArr, i, zzizVar);
                    int i10 = zzizVar.zznk;
                    zzko zzar = zzar(i8);
                    if (zzar == null || zzar.zzan(i10)) {
                        unsafe.putObject(t, j, Integer.valueOf(i10));
                        zzb = zza3;
                        unsafe.putInt(t, j2, i4);
                        return zzb;
                    }
                    zzo(t).zzb(i3, Long.valueOf(i10));
                    return zza3;
                }
                return i;
            case 66:
                if (i5 == 0) {
                    zzb = zziy.zza(bArr, i, zzizVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzjo.zzw(zzizVar.zznk)));
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 67:
                if (i5 == 0) {
                    zzb = zziy.zzb(bArr, i, zzizVar);
                    unsafe.putObject(t, j, Long.valueOf(zzjo.zzk(zzizVar.zznl)));
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            case 68:
                if (i5 == 3) {
                    zzb = zziy.zza(zzap(i8), bArr, i, i2, (i3 & (-8)) | 4, zzizVar);
                    Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object2 == null) {
                        unsafe.putObject(t, j, zzizVar.zznm);
                    } else {
                        unsafe.putObject(t, j, zzkm.zza(object2, zzizVar.zznm));
                    }
                    unsafe.putInt(t, j2, i4);
                    return zzb;
                }
                return i;
            default:
                return i;
        }
    }

    private final zzmf zzap(int i) {
        int i2 = (i / 3) << 1;
        zzmf zzmfVar = (zzmf) this.zzue[i2];
        if (zzmfVar != null) {
            return zzmfVar;
        }
        zzmf<T> zzf = zzmd.zzej().zzf((Class) this.zzue[i2 + 1]);
        this.zzue[i2] = zzf;
        return zzf;
    }

    private final Object zzaq(int i) {
        return this.zzue[(i / 3) << 1];
    }

    private final zzko zzar(int i) {
        return (zzko) this.zzue[((i / 3) << 1) + 1];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int zza(T t, byte[] bArr, int i, int i2, int i3, zziz zzizVar) throws IOException {
        int i4;
        int i5;
        Unsafe unsafe;
        int i6;
        T t2;
        zzlu<T> zzluVar;
        int i7;
        int i8;
        zzko zzar;
        byte b;
        int i9;
        int zzau;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        T t3;
        int i15;
        int i16;
        int i17;
        zziz zzizVar2;
        int i18;
        zziz zzizVar3;
        int i19;
        int i20;
        zziz zzizVar4;
        int i21;
        int i22;
        zzlu<T> zzluVar2 = this;
        T t4 = t;
        byte[] bArr2 = bArr;
        int i23 = i2;
        int i24 = i3;
        zziz zzizVar5 = zzizVar;
        Unsafe unsafe2 = zzuc;
        int i25 = i;
        int i26 = -1;
        int i27 = 0;
        int i28 = 0;
        int i29 = 0;
        int i30 = -1;
        while (true) {
            if (i25 < i23) {
                int i31 = i25 + 1;
                byte b2 = bArr2[i25];
                if (b2 < 0) {
                    i9 = zziy.zza(b2, bArr2, i31, zzizVar5);
                    b = zzizVar5.zznk;
                } else {
                    b = b2;
                    i9 = i31;
                }
                int i32 = b >>> 3;
                int i33 = b & 7;
                if (i32 > i26) {
                    zzau = zzluVar2.zzp(i32, i27 / 3);
                } else {
                    zzau = zzluVar2.zzau(i32);
                }
                int i34 = zzau;
                if (i34 == -1) {
                    i10 = i32;
                    i11 = i9;
                    i4 = i29;
                    i5 = i30;
                    unsafe = unsafe2;
                    i6 = i24;
                    i12 = 0;
                    i13 = b;
                } else {
                    int[] iArr = zzluVar2.zzud;
                    int i35 = iArr[i34 + 1];
                    int i36 = (i35 & 267386880) >>> 20;
                    int i37 = b;
                    long j = i35 & 1048575;
                    if (i36 <= 17) {
                        int i38 = iArr[i34 + 2];
                        int i39 = 1 << (i38 >>> 20);
                        int i40 = i38 & 1048575;
                        if (i40 != i30) {
                            if (i30 != -1) {
                                unsafe2.putInt(t4, i30, i29);
                            }
                            i29 = unsafe2.getInt(t4, i40);
                            i30 = i40;
                        }
                        switch (i36) {
                            case 0:
                                i15 = i34;
                                i16 = i32;
                                i18 = i30;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar3 = zzizVar;
                                i19 = i9;
                                if (i33 == 1) {
                                    zznd.zza(t4, j, zziy.zzc(bArr2, i19));
                                    i25 = i19 + 8;
                                    i29 |= i39;
                                    i30 = i18;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar3;
                                    i23 = i2;
                                    i24 = i3;
                                    break;
                                } else {
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 1:
                                i15 = i34;
                                i16 = i32;
                                i18 = i30;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar3 = zzizVar;
                                i19 = i9;
                                if (i33 == 5) {
                                    zznd.zza((Object) t4, j, zziy.zzd(bArr2, i19));
                                    i25 = i19 + 4;
                                    i29 |= i39;
                                    i30 = i18;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar3;
                                    i23 = i2;
                                    i24 = i3;
                                    break;
                                } else {
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 2:
                            case 3:
                                i15 = i34;
                                i16 = i32;
                                i18 = i30;
                                i17 = i37;
                                bArr2 = bArr;
                                i19 = i9;
                                if (i33 == 0) {
                                    int zzb = zziy.zzb(bArr2, i19, zzizVar);
                                    unsafe2.putLong(t, j, zzizVar.zznl);
                                    i29 |= i39;
                                    i25 = zzb;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar;
                                    i30 = i18;
                                    i23 = i2;
                                    i24 = i3;
                                    break;
                                } else {
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 4:
                            case 11:
                                i15 = i34;
                                i16 = i32;
                                i18 = i30;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar3 = zzizVar;
                                i19 = i9;
                                if (i33 == 0) {
                                    i25 = zziy.zza(bArr2, i19, zzizVar3);
                                    unsafe2.putInt(t4, j, zzizVar3.zznk);
                                    i29 |= i39;
                                    i30 = i18;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar3;
                                    i23 = i2;
                                    i24 = i3;
                                    break;
                                } else {
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 5:
                            case 14:
                                i15 = i34;
                                i16 = i32;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar3 = zzizVar;
                                if (i33 == 1) {
                                    i18 = i30;
                                    i19 = i9;
                                    unsafe2.putLong(t, j, zziy.zzb(bArr2, i9));
                                    i25 = i19 + 8;
                                    i29 |= i39;
                                    i30 = i18;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar3;
                                    i23 = i2;
                                    i24 = i3;
                                    break;
                                } else {
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 6:
                            case 13:
                                i20 = i2;
                                i15 = i34;
                                i16 = i32;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar4 = zzizVar;
                                if (i33 == 5) {
                                    unsafe2.putInt(t4, j, zziy.zza(bArr2, i9));
                                    i25 = i9 + 4;
                                    i29 |= i39;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar4;
                                    i24 = i3;
                                    i23 = i20;
                                    break;
                                } else {
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 7:
                                i20 = i2;
                                i15 = i34;
                                i16 = i32;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar4 = zzizVar;
                                if (i33 == 0) {
                                    int zzb2 = zziy.zzb(bArr2, i9, zzizVar4);
                                    zznd.zza(t4, j, zzizVar4.zznl != 0);
                                    i29 |= i39;
                                    i25 = zzb2;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar4;
                                    i24 = i3;
                                    i23 = i20;
                                    break;
                                } else {
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 8:
                                i20 = i2;
                                i15 = i34;
                                i16 = i32;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar4 = zzizVar;
                                if (i33 == 2) {
                                    if ((i35 & DriveFile.MODE_WRITE_ONLY) == 0) {
                                        i25 = zziy.zzc(bArr2, i9, zzizVar4);
                                    } else {
                                        i25 = zziy.zzd(bArr2, i9, zzizVar4);
                                    }
                                    unsafe2.putObject(t4, j, zzizVar4.zznm);
                                    i29 |= i39;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar4;
                                    i24 = i3;
                                    i23 = i20;
                                    break;
                                } else {
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 9:
                                i15 = i34;
                                i16 = i32;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar4 = zzizVar;
                                if (i33 == 2) {
                                    i20 = i2;
                                    i25 = zziy.zza(zzluVar2.zzap(i15), bArr2, i9, i20, zzizVar4);
                                    if ((i29 & i39) == 0) {
                                        unsafe2.putObject(t4, j, zzizVar4.zznm);
                                    } else {
                                        unsafe2.putObject(t4, j, zzkm.zza(unsafe2.getObject(t4, j), zzizVar4.zznm));
                                    }
                                    i29 |= i39;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar4;
                                    i24 = i3;
                                    i23 = i20;
                                    break;
                                } else {
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 10:
                                i15 = i34;
                                i16 = i32;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar3 = zzizVar;
                                if (i33 == 2) {
                                    i25 = zziy.zze(bArr2, i9, zzizVar3);
                                    unsafe2.putObject(t4, j, zzizVar3.zznm);
                                    i29 |= i39;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar3;
                                    i23 = i2;
                                    i24 = i3;
                                    break;
                                } else {
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 12:
                                i15 = i34;
                                i16 = i32;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar3 = zzizVar;
                                if (i33 != 0) {
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                } else {
                                    i25 = zziy.zza(bArr2, i9, zzizVar3);
                                    int i41 = zzizVar3.zznk;
                                    zzko zzar2 = zzluVar2.zzar(i15);
                                    if (zzar2 == null || zzar2.zzan(i41)) {
                                        unsafe2.putInt(t4, j, i41);
                                        i29 |= i39;
                                        i28 = i17;
                                        i27 = i15;
                                        i26 = i16;
                                        zzizVar5 = zzizVar3;
                                        i23 = i2;
                                        i24 = i3;
                                        break;
                                    } else {
                                        zzo(t).zzb(i17, Long.valueOf(i41));
                                        i28 = i17;
                                        i27 = i15;
                                        i26 = i16;
                                        zzizVar5 = zzizVar3;
                                        i23 = i2;
                                        i24 = i3;
                                    }
                                }
                                break;
                            case 15:
                                i15 = i34;
                                i16 = i32;
                                i17 = i37;
                                bArr2 = bArr;
                                zzizVar3 = zzizVar;
                                if (i33 == 0) {
                                    i25 = zziy.zza(bArr2, i9, zzizVar3);
                                    unsafe2.putInt(t4, j, zzjo.zzw(zzizVar3.zznk));
                                    i29 |= i39;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar3;
                                    i23 = i2;
                                    i24 = i3;
                                    break;
                                } else {
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 16:
                                i15 = i34;
                                i16 = i32;
                                i17 = i37;
                                if (i33 == 0) {
                                    bArr2 = bArr;
                                    int zzb3 = zziy.zzb(bArr2, i9, zzizVar);
                                    zzizVar3 = zzizVar;
                                    unsafe2.putLong(t, j, zzjo.zzk(zzizVar.zznl));
                                    i29 |= i39;
                                    i25 = zzb3;
                                    i28 = i17;
                                    i27 = i15;
                                    i26 = i16;
                                    zzizVar5 = zzizVar3;
                                    i23 = i2;
                                    i24 = i3;
                                    break;
                                } else {
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            case 17:
                                if (i33 == 3) {
                                    i25 = zziy.zza(zzluVar2.zzap(i34), bArr, i9, i2, (i32 << 3) | 4, zzizVar);
                                    if ((i29 & i39) == 0) {
                                        zzizVar2 = zzizVar;
                                        unsafe2.putObject(t4, j, zzizVar2.zznm);
                                    } else {
                                        zzizVar2 = zzizVar;
                                        unsafe2.putObject(t4, j, zzkm.zza(unsafe2.getObject(t4, j), zzizVar2.zznm));
                                    }
                                    i29 |= i39;
                                    bArr2 = bArr;
                                    i23 = i2;
                                    i28 = i37;
                                    i27 = i34;
                                    i26 = i32;
                                    i24 = i3;
                                    zzizVar5 = zzizVar2;
                                    break;
                                } else {
                                    i15 = i34;
                                    i16 = i32;
                                    i17 = i37;
                                    i18 = i30;
                                    i19 = i9;
                                    i5 = i18;
                                    i4 = i29;
                                    i11 = i19;
                                    i13 = i17;
                                    i12 = i15;
                                    unsafe = unsafe2;
                                    i10 = i16;
                                    i6 = i3;
                                    break;
                                }
                            default:
                                i15 = i34;
                                i16 = i32;
                                i18 = i30;
                                i17 = i37;
                                i19 = i9;
                                i5 = i18;
                                i4 = i29;
                                i11 = i19;
                                i13 = i17;
                                i12 = i15;
                                unsafe = unsafe2;
                                i10 = i16;
                                i6 = i3;
                                break;
                        }
                    } else {
                        i5 = i30;
                        int i42 = i9;
                        bArr2 = bArr;
                        zziz zzizVar6 = zzizVar5;
                        if (i36 != 27) {
                            i4 = i29;
                            if (i36 <= 49) {
                                i10 = i32;
                                i22 = i37;
                                i12 = i34;
                                unsafe = unsafe2;
                                i25 = zza((zzlu<T>) t, bArr, i42, i2, i37, i32, i33, i34, i35, i36, j, zzizVar);
                                if (i25 != i42) {
                                    zzluVar2 = this;
                                    t4 = t;
                                    bArr2 = bArr;
                                    i23 = i2;
                                    i24 = i3;
                                    zzizVar5 = zzizVar;
                                    i30 = i5;
                                    i27 = i12;
                                    i29 = i4;
                                    i26 = i10;
                                    i28 = i22;
                                }
                                i6 = i3;
                                i11 = i25;
                                i13 = i22;
                            } else {
                                i10 = i32;
                                i21 = i42;
                                i22 = i37;
                                i12 = i34;
                                unsafe = unsafe2;
                                if (i36 != 50) {
                                    i25 = zza((zzlu<T>) t, bArr, i21, i2, i22, i10, i33, i35, i36, j, i12, zzizVar);
                                    if (i25 != i21) {
                                        i14 = i10;
                                        zzluVar2 = this;
                                        t4 = t;
                                        bArr2 = bArr;
                                        i23 = i2;
                                        i24 = i3;
                                        zzizVar5 = zzizVar;
                                        i28 = i22;
                                        i26 = i14;
                                        i30 = i5;
                                        i27 = i12;
                                        i29 = i4;
                                    }
                                } else if (i33 == 2) {
                                    i25 = zza((zzlu<T>) t, bArr, i21, i2, i12, j, zzizVar);
                                    if (i25 != i21) {
                                        zzluVar2 = this;
                                        t4 = t;
                                        bArr2 = bArr;
                                        i23 = i2;
                                        i24 = i3;
                                        zzizVar5 = zzizVar;
                                        i30 = i5;
                                        i27 = i12;
                                        i29 = i4;
                                        i26 = i10;
                                        i28 = i22;
                                    }
                                }
                                i6 = i3;
                                i11 = i25;
                                i13 = i22;
                            }
                        } else if (i33 == 2) {
                            zzkp zzkpVar = (zzkp) unsafe2.getObject(t4, j);
                            if (!zzkpVar.zzbo()) {
                                int size = zzkpVar.size();
                                zzkpVar = zzkpVar.zzr(size == 0 ? 10 : size << 1);
                                unsafe2.putObject(t4, j, zzkpVar);
                            }
                            i25 = zziy.zza(zzluVar2.zzap(i34), i37, bArr, i42, i2, zzkpVar, zzizVar);
                            i24 = i3;
                            i26 = i32;
                            i28 = i37;
                            i27 = i34;
                            zzizVar5 = zzizVar6;
                            i30 = i5;
                            i29 = i29;
                            i23 = i2;
                        } else {
                            i4 = i29;
                            i10 = i32;
                            i21 = i42;
                            i22 = i37;
                            i12 = i34;
                            unsafe = unsafe2;
                        }
                        i6 = i3;
                        i11 = i21;
                        i13 = i22;
                    }
                    unsafe2 = unsafe;
                }
                if (i13 != i6 || i6 == 0) {
                    if (this.zzui) {
                        zzizVar5 = zzizVar;
                        if (zzizVar5.zznn != zzjx.zzci()) {
                            i14 = i10;
                            if (zzizVar5.zznn.zza(this.zzuh, i14) == null) {
                                i25 = zziy.zza(i13, bArr, i11, i2, zzo(t), zzizVar);
                                t4 = t;
                                bArr2 = bArr;
                                i23 = i2;
                                i28 = i13;
                                i24 = i6;
                                zzluVar2 = this;
                                i26 = i14;
                                i30 = i5;
                                i27 = i12;
                                i29 = i4;
                                unsafe2 = unsafe;
                            } else {
                                zzkk.zzc zzcVar = (zzkk.zzc) t;
                                zzcVar.zzdg();
                                zzkb<Object> zzkbVar = zzcVar.zzrw;
                                throw new NoSuchMethodError();
                            }
                        } else {
                            t3 = t;
                        }
                    } else {
                        t3 = t;
                        zzizVar5 = zzizVar;
                    }
                    i25 = zziy.zza(i13, bArr, i11, i2, zzo(t), zzizVar);
                    bArr2 = bArr;
                    i23 = i2;
                    i28 = i13;
                    zzluVar2 = this;
                    i26 = i10;
                    t4 = t3;
                    i27 = i12;
                    i29 = i4;
                    unsafe2 = unsafe;
                    i24 = i6;
                    i30 = i5;
                } else {
                    zzluVar = this;
                    t2 = t;
                    i7 = i11;
                    i8 = i13;
                }
            } else {
                i4 = i29;
                i5 = i30;
                unsafe = unsafe2;
                i6 = i24;
                t2 = t4;
                zzluVar = zzluVar2;
                i7 = i25;
                i8 = i28;
            }
        }
        int i43 = i5;
        int i44 = i4;
        if (i43 != -1) {
            unsafe.putInt(t2, i43, i44);
        }
        Object obj = null;
        for (int i45 = zzluVar.zzun; i45 < zzluVar.zzuo; i45++) {
            int i46 = zzluVar.zzum[i45];
            zzmx zzmxVar = zzluVar.zzur;
            int i47 = zzluVar.zzud[i46];
            Object zzo = zznd.zzo(t2, zzluVar.zzas(i46) & 1048575);
            if (zzo != null && (zzar = zzluVar.zzar(i46)) != null) {
                obj = zza(i46, i47, zzluVar.zzut.zzh(zzo), zzar, (zzko) obj, (zzmx<UT, zzko>) zzmxVar);
            }
            obj = (zzmy) obj;
        }
        if (obj != null) {
            zzluVar.zzur.zzf(t2, obj);
        }
        if (i6 == 0) {
            if (i7 != i2) {
                throw zzkq.zzdm();
            }
        } else if (i7 > i2 || i8 != i6) {
            throw zzkq.zzdm();
        }
        return i7;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x022e, code lost:
        if (r0 == r15) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0230, code lost:
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01e2, code lost:
        if (r0 == r15) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x020f, code lost:
        if (r0 == r15) goto L96;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v13, types: [int] */
    @Override // com.google.android.gms.internal.drive.zzmf
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r28, byte[] r29, int r30, int r31, com.google.android.gms.internal.drive.zziz r32) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 662
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.drive.zzlu.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.drive.zziz):void");
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final void zzd(T t) {
        int i;
        int i2 = this.zzun;
        while (true) {
            i = this.zzuo;
            if (i2 >= i) {
                break;
            }
            long zzas = zzas(this.zzum[i2]) & 1048575;
            Object zzo = zznd.zzo(t, zzas);
            if (zzo != null) {
                zznd.zza(t, zzas, this.zzut.zzk(zzo));
            }
            i2++;
        }
        int length = this.zzum.length;
        while (i < length) {
            this.zzuq.zza(t, this.zzum[i]);
            i++;
        }
        this.zzur.zzd(t);
        if (this.zzui) {
            this.zzus.zzd(t);
        }
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzko zzkoVar, UB ub, zzmx<UT, UB> zzmxVar) {
        zzlj<?, ?> zzm = this.zzut.zzm(zzaq(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!zzkoVar.zzan(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzmxVar.zzez();
                }
                zzjk zzu = zzjc.zzu(zzli.zza(zzm, next.getKey(), next.getValue()));
                try {
                    zzli.zza(zzu.zzby(), zzm, next.getKey(), next.getValue());
                    zzmxVar.zza((zzmx<UT, UB>) ub, i2, zzu.zzbx());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v14, types: [com.google.android.gms.internal.drive.zzmf] */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.google.android.gms.internal.drive.zzmf] */
    @Override // com.google.android.gms.internal.drive.zzmf
    public final boolean zzp(T t) {
        int i;
        int i2 = 0;
        int i3 = -1;
        int i4 = 0;
        while (true) {
            boolean z = true;
            if (i2 >= this.zzun) {
                return !this.zzui || this.zzus.zzb(t).isInitialized();
            }
            int i5 = this.zzum[i2];
            int i6 = this.zzud[i5];
            int zzas = zzas(i5);
            if (this.zzuk) {
                i = 0;
            } else {
                int i7 = this.zzud[i5 + 2];
                int i8 = i7 & 1048575;
                i = 1 << (i7 >>> 20);
                if (i8 != i3) {
                    i4 = zzuc.getInt(t, i8);
                    i3 = i8;
                }
            }
            if (((268435456 & zzas) != 0) && !zza((zzlu<T>) t, i5, i4, i)) {
                return false;
            }
            int i9 = (267386880 & zzas) >>> 20;
            if (i9 == 9 || i9 == 17) {
                if (zza((zzlu<T>) t, i5, i4, i) && !zza(t, zzas, zzap(i5))) {
                    return false;
                }
            } else {
                if (i9 != 27) {
                    if (i9 == 60 || i9 == 68) {
                        if (zza((zzlu<T>) t, i6, i5) && !zza(t, zzas, zzap(i5))) {
                            return false;
                        }
                    } else if (i9 != 49) {
                        if (i9 != 50) {
                            continue;
                        } else {
                            Map<?, ?> zzi = this.zzut.zzi(zznd.zzo(t, zzas & 1048575));
                            if (!zzi.isEmpty()) {
                                if (this.zzut.zzm(zzaq(i5)).zztw.zzfj() == zznr.MESSAGE) {
                                    zzmf<T> zzmfVar = 0;
                                    Iterator<?> it = zzi.values().iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        Object next = it.next();
                                        if (zzmfVar == null) {
                                            zzmfVar = zzmd.zzej().zzf(next.getClass());
                                        }
                                        boolean zzp = zzmfVar.zzp(next);
                                        zzmfVar = zzmfVar;
                                        if (!zzp) {
                                            z = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!z) {
                                return false;
                            }
                        }
                    }
                }
                List list = (List) zznd.zzo(t, zzas & 1048575);
                if (!list.isEmpty()) {
                    ?? zzap = zzap(i5);
                    int i10 = 0;
                    while (true) {
                        if (i10 >= list.size()) {
                            break;
                        } else if (!zzap.zzp(list.get(i10))) {
                            z = false;
                            break;
                        } else {
                            i10++;
                        }
                    }
                }
                if (!z) {
                    return false;
                }
            }
            i2++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzmf zzmfVar) {
        return zzmfVar.zzp(zznd.zzo(obj, i & 1048575));
    }

    private static void zza(int i, Object obj, zzns zznsVar) throws IOException {
        if (obj instanceof String) {
            zznsVar.zza(i, (String) obj);
        } else {
            zznsVar.zza(i, (zzjc) obj);
        }
    }

    private final int zzas(int i) {
        return this.zzud[i + 1];
    }

    private final int zzat(int i) {
        return this.zzud[i + 2];
    }

    private static <T> double zze(T t, long j) {
        return ((Double) zznd.zzo(t, j)).doubleValue();
    }

    private static <T> float zzf(T t, long j) {
        return ((Float) zznd.zzo(t, j)).floatValue();
    }

    private static <T> int zzg(T t, long j) {
        return ((Integer) zznd.zzo(t, j)).intValue();
    }

    private static <T> long zzh(T t, long j) {
        return ((Long) zznd.zzo(t, j)).longValue();
    }

    private static <T> boolean zzi(T t, long j) {
        return ((Boolean) zznd.zzo(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza((zzlu<T>) t, i) == zza((zzlu<T>) t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        if (this.zzuk) {
            return zza((zzlu<T>) t, i);
        }
        return (i2 & i3) != 0;
    }

    private final boolean zza(T t, int i) {
        if (this.zzuk) {
            int zzas = zzas(i);
            long j = zzas & 1048575;
            switch ((zzas & 267386880) >>> 20) {
                case 0:
                    return zznd.zzn(t, j) != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                case 1:
                    return zznd.zzm(t, j) != 0.0f;
                case 2:
                    return zznd.zzk(t, j) != 0;
                case 3:
                    return zznd.zzk(t, j) != 0;
                case 4:
                    return zznd.zzj(t, j) != 0;
                case 5:
                    return zznd.zzk(t, j) != 0;
                case 6:
                    return zznd.zzj(t, j) != 0;
                case 7:
                    return zznd.zzl(t, j);
                case 8:
                    Object zzo = zznd.zzo(t, j);
                    if (zzo instanceof String) {
                        return !((String) zzo).isEmpty();
                    } else if (zzo instanceof zzjc) {
                        return !zzjc.zznq.equals(zzo);
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 9:
                    return zznd.zzo(t, j) != null;
                case 10:
                    return !zzjc.zznq.equals(zznd.zzo(t, j));
                case 11:
                    return zznd.zzj(t, j) != 0;
                case 12:
                    return zznd.zzj(t, j) != 0;
                case 13:
                    return zznd.zzj(t, j) != 0;
                case 14:
                    return zznd.zzk(t, j) != 0;
                case 15:
                    return zznd.zzj(t, j) != 0;
                case 16:
                    return zznd.zzk(t, j) != 0;
                case 17:
                    return zznd.zzo(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        int zzat = zzat(i);
        return (zznd.zzj(t, (long) (zzat & 1048575)) & (1 << (zzat >>> 20))) != 0;
    }

    private final void zzb(T t, int i) {
        if (this.zzuk) {
            return;
        }
        int zzat = zzat(i);
        long j = zzat & 1048575;
        zznd.zza((Object) t, j, zznd.zzj(t, j) | (1 << (zzat >>> 20)));
    }

    private final boolean zza(T t, int i, int i2) {
        return zznd.zzj(t, (long) (zzat(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zznd.zza((Object) t, zzat(i2) & 1048575, i);
    }

    private final int zzau(int i) {
        if (i < this.zzuf || i > this.zzug) {
            return -1;
        }
        return zzq(i, 0);
    }

    private final int zzp(int i, int i2) {
        if (i < this.zzuf || i > this.zzug) {
            return -1;
        }
        return zzq(i, i2);
    }

    private final int zzq(int i, int i2) {
        int length = (this.zzud.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzud[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }
}
