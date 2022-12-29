package com.google.android.gms.internal.play_billing;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Arrays;
import javax.annotation.CheckForNull;
import kotlin.UByte;

/* compiled from: com.android.billingclient:billing@@5.0.0 */
/* loaded from: classes3.dex */
final class zzaf extends zzx {
    static final zzx zza = new zzaf(null, new Object[0], 0);
    final transient Object[] zzb;
    @CheckForNull
    private final transient Object zzc;
    private final transient int zzd;

    private zzaf(@CheckForNull Object obj, Object[] objArr, int i) {
        this.zzc = obj;
        this.zzb = objArr;
        this.zzd = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r2v13, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r5v4, types: [int[]] */
    /* JADX WARN: Type inference failed for: r5v7 */
    public static zzaf zzf(int i, Object[] objArr, zzw zzwVar) {
        short[] sArr;
        Object[] objArr2;
        int i2;
        Object[] objArr3;
        Object[] objArr4 = objArr;
        if (i == 0) {
            return (zzaf) zza;
        }
        Object[] objArr5 = null;
        if (i == 1) {
            Object obj = objArr4[0];
            obj.getClass();
            Object obj2 = objArr4[1];
            obj2.getClass();
            zzp.zza(obj, obj2);
            return new zzaf(null, objArr4, 1);
        }
        zzm.zzb(i, objArr4.length >> 1, FirebaseAnalytics.Param.INDEX);
        char c = 2;
        int max = Math.max(i, 2);
        int i3 = 1073741824;
        if (max < 751619276) {
            int highestOneBit = Integer.highestOneBit(max - 1);
            i3 = highestOneBit + highestOneBit;
            while (true) {
                double d = i3;
                Double.isNaN(d);
                if (d * 0.7d >= max) {
                    break;
                }
                i3 += i3;
            }
        } else if (max >= 1073741824) {
            throw new IllegalArgumentException("collection too large");
        }
        if (i == 1) {
            Object obj3 = objArr4[0];
            obj3.getClass();
            Object obj4 = objArr4[1];
            obj4.getClass();
            zzp.zza(obj3, obj4);
        } else {
            int i4 = i3 - 1;
            char c2 = 65535;
            if (i3 <= 128) {
                byte[] bArr = new byte[i3];
                Arrays.fill(bArr, (byte) -1);
                zzv zzvVar = null;
                int i5 = 0;
                for (int i6 = 0; i6 < i; i6++) {
                    int i7 = i6 + i6;
                    int i8 = i5 + i5;
                    Object obj5 = objArr4[i7];
                    obj5.getClass();
                    Object obj6 = objArr4[i7 ^ 1];
                    obj6.getClass();
                    zzp.zza(obj5, obj6);
                    int zza2 = zzq.zza(obj5.hashCode());
                    while (true) {
                        int i9 = zza2 & i4;
                        int i10 = bArr[i9] & UByte.MAX_VALUE;
                        if (i10 != 255) {
                            if (obj5.equals(objArr4[i10])) {
                                int i11 = i10 ^ 1;
                                Object obj7 = objArr4[i11];
                                obj7.getClass();
                                zzvVar = new zzv(obj5, obj6, obj7);
                                objArr4[i11] = obj6;
                                break;
                            }
                            zza2 = i9 + 1;
                        } else {
                            bArr[i9] = (byte) i8;
                            if (i5 < i6) {
                                objArr4[i8] = obj5;
                                objArr4[i8 ^ 1] = obj6;
                            }
                            i5++;
                        }
                    }
                }
                if (i5 == i) {
                    objArr2 = bArr;
                    c = 2;
                    objArr5 = objArr2;
                } else {
                    c = 2;
                    objArr5 = new Object[]{bArr, Integer.valueOf(i5), zzvVar};
                }
            } else {
                if (i3 <= 32768) {
                    sArr = new short[i3];
                    Arrays.fill(sArr, (short) -1);
                    zzv zzvVar2 = null;
                    int i12 = 0;
                    for (int i13 = 0; i13 < i; i13++) {
                        int i14 = i13 + i13;
                        int i15 = i12 + i12;
                        Object obj8 = objArr4[i14];
                        obj8.getClass();
                        Object obj9 = objArr4[i14 ^ 1];
                        obj9.getClass();
                        zzp.zza(obj8, obj9);
                        int zza3 = zzq.zza(obj8.hashCode());
                        while (true) {
                            int i16 = zza3 & i4;
                            char c3 = (char) sArr[i16];
                            if (c3 != 65535) {
                                if (obj8.equals(objArr4[c3])) {
                                    int i17 = c3 ^ 1;
                                    Object obj10 = objArr4[i17];
                                    obj10.getClass();
                                    zzv zzvVar3 = new zzv(obj8, obj9, obj10);
                                    objArr4[i17] = obj9;
                                    zzvVar2 = zzvVar3;
                                    break;
                                }
                                zza3 = i16 + 1;
                            } else {
                                sArr[i16] = (short) i15;
                                if (i12 < i13) {
                                    objArr4[i15] = obj8;
                                    objArr4[i15 ^ 1] = obj9;
                                }
                                i12++;
                            }
                        }
                    }
                    if (i12 != i) {
                        c = 2;
                        objArr5 = new Object[]{sArr, Integer.valueOf(i12), zzvVar2};
                    }
                    objArr2 = sArr;
                } else {
                    sArr = new int[i3];
                    Arrays.fill((int[]) sArr, -1);
                    zzv zzvVar4 = null;
                    int i18 = 0;
                    int i19 = 0;
                    while (i18 < i) {
                        int i20 = i18 + i18;
                        int i21 = i19 + i19;
                        Object obj11 = objArr4[i20];
                        obj11.getClass();
                        Object obj12 = objArr4[i20 ^ 1];
                        obj12.getClass();
                        zzp.zza(obj11, obj12);
                        int zza4 = zzq.zza(obj11.hashCode());
                        while (true) {
                            int i22 = zza4 & i4;
                            ?? r15 = sArr[i22];
                            if (r15 != c2) {
                                if (obj11.equals(objArr4[r15])) {
                                    int i23 = r15 ^ 1;
                                    Object obj13 = objArr4[i23];
                                    obj13.getClass();
                                    zzv zzvVar5 = new zzv(obj11, obj12, obj13);
                                    objArr4[i23] = obj12;
                                    zzvVar4 = zzvVar5;
                                    break;
                                }
                                zza4 = i22 + 1;
                                c2 = 65535;
                            } else {
                                sArr[i22] = i21;
                                if (i19 < i18) {
                                    objArr4[i21] = obj11;
                                    objArr4[i21 ^ 1] = obj12;
                                }
                                i19++;
                            }
                        }
                        i18++;
                        c2 = 65535;
                    }
                    if (i19 != i) {
                        c = 2;
                        objArr5 = new Object[]{sArr, Integer.valueOf(i19), zzvVar4};
                    }
                    objArr2 = sArr;
                }
                c = 2;
                objArr5 = objArr2;
            }
        }
        if (objArr5 instanceof Object[]) {
            Object[] objArr6 = objArr5;
            zzwVar.zzc = (zzv) objArr6[c];
            objArr3 = objArr6[0];
            i2 = ((Integer) objArr6[1]).intValue();
            objArr4 = Arrays.copyOf(objArr4, i2 + i2);
        } else {
            Object[] objArr7 = objArr5;
            i2 = i;
            objArr3 = objArr7;
        }
        return new zzaf(objArr3, objArr4, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x009e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009f A[RETURN] */
    @Override // com.google.android.gms.internal.play_billing.zzx, java.util.Map
    @javax.annotation.CheckForNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object get(@javax.annotation.CheckForNull java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = r9.zzc
            java.lang.Object[] r1 = r9.zzb
            int r2 = r9.zzd
            r3 = 0
            if (r10 != 0) goto Lc
        L9:
            r10 = r3
            goto L9c
        Lc:
            r4 = 1
            if (r2 != r4) goto L22
            r0 = 0
            r0 = r1[r0]
            r0.getClass()
            boolean r10 = r0.equals(r10)
            if (r10 == 0) goto L9
            r10 = r1[r4]
            r10.getClass()
            goto L9c
        L22:
            if (r0 != 0) goto L25
            goto L9
        L25:
            boolean r2 = r0 instanceof byte[]
            r5 = -1
            if (r2 == 0) goto L51
            r2 = r0
            byte[] r2 = (byte[]) r2
            int r0 = r2.length
            int r6 = r0 + (-1)
            int r0 = r10.hashCode()
            int r0 = com.google.android.gms.internal.play_billing.zzq.zza(r0)
        L38:
            r0 = r0 & r6
            r5 = r2[r0]
            r7 = 255(0xff, float:3.57E-43)
            r5 = r5 & r7
            if (r5 != r7) goto L41
            goto L9
        L41:
            r7 = r1[r5]
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L4e
            r10 = r5 ^ 1
            r10 = r1[r10]
            goto L9c
        L4e:
            int r0 = r0 + 1
            goto L38
        L51:
            boolean r2 = r0 instanceof short[]
            if (r2 == 0) goto L7d
            r2 = r0
            short[] r2 = (short[]) r2
            int r0 = r2.length
            int r6 = r0 + (-1)
            int r0 = r10.hashCode()
            int r0 = com.google.android.gms.internal.play_billing.zzq.zza(r0)
        L63:
            r0 = r0 & r6
            short r5 = r2[r0]
            char r5 = (char) r5
            r7 = 65535(0xffff, float:9.1834E-41)
            if (r5 != r7) goto L6d
            goto L9
        L6d:
            r7 = r1[r5]
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L7a
            r10 = r5 ^ 1
            r10 = r1[r10]
            goto L9c
        L7a:
            int r0 = r0 + 1
            goto L63
        L7d:
            int[] r0 = (int[]) r0
            int r2 = r0.length
            int r2 = r2 + r5
            int r6 = r10.hashCode()
            int r6 = com.google.android.gms.internal.play_billing.zzq.zza(r6)
        L89:
            r6 = r6 & r2
            r7 = r0[r6]
            if (r7 != r5) goto L90
            goto L9
        L90:
            r8 = r1[r7]
            boolean r8 = r10.equals(r8)
            if (r8 == 0) goto La0
            r10 = r7 ^ 1
            r10 = r1[r10]
        L9c:
            if (r10 != 0) goto L9f
            return r3
        L9f:
            return r10
        La0:
            int r6 = r6 + 1
            goto L89
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzaf.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public final int size() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.play_billing.zzx
    final zzr zza() {
        return new zzae(this.zzb, 1, this.zzd);
    }

    @Override // com.google.android.gms.internal.play_billing.zzx
    final zzy zzc() {
        return new zzac(this, this.zzb, 0, this.zzd);
    }

    @Override // com.google.android.gms.internal.play_billing.zzx
    final zzy zzd() {
        return new zzad(this, new zzae(this.zzb, 0, this.zzd));
    }
}
