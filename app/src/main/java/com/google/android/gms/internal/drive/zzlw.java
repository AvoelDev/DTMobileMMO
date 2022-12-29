package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzlw<T> implements zzmf<T> {
    private final zzlq zzuh;
    private final boolean zzui;
    private final zzmx<?, ?> zzur;
    private final zzjy<?> zzus;

    private zzlw(zzmx<?, ?> zzmxVar, zzjy<?> zzjyVar, zzlq zzlqVar) {
        this.zzur = zzmxVar;
        this.zzui = zzjyVar.zze(zzlqVar);
        this.zzus = zzjyVar;
        this.zzuh = zzlqVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> zzlw<T> zza(zzmx<?, ?> zzmxVar, zzjy<?> zzjyVar, zzlq zzlqVar) {
        return new zzlw<>(zzmxVar, zzjyVar, zzlqVar);
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final T newInstance() {
        return (T) this.zzuh.zzcz().zzde();
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final boolean equals(T t, T t2) {
        if (this.zzur.zzr(t).equals(this.zzur.zzr(t2))) {
            if (this.zzui) {
                return this.zzus.zzb(t).equals(this.zzus.zzb(t2));
            }
            return true;
        }
        return false;
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final int hashCode(T t) {
        int hashCode = this.zzur.zzr(t).hashCode();
        return this.zzui ? (hashCode * 53) + this.zzus.zzb(t).hashCode() : hashCode;
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final void zzc(T t, T t2) {
        zzmh.zza(this.zzur, t, t2);
        if (this.zzui) {
            zzmh.zza(this.zzus, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final void zza(T t, zzns zznsVar) throws IOException {
        Iterator<Map.Entry<?, Object>> it = this.zzus.zzb(t).iterator();
        while (it.hasNext()) {
            Map.Entry<?, Object> next = it.next();
            zzkd zzkdVar = (zzkd) next.getKey();
            if (zzkdVar.zzcr() != zznr.MESSAGE || zzkdVar.zzcs() || zzkdVar.zzct()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (next instanceof zzkv) {
                zznsVar.zza(zzkdVar.zzcp(), (Object) ((zzkv) next).zzdq().zzbl());
            } else {
                zznsVar.zza(zzkdVar.zzcp(), next.getValue());
            }
        }
        zzmx<?, ?> zzmxVar = this.zzur;
        zzmxVar.zzc(zzmxVar.zzr(t), zznsVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0099 A[EDGE_INSN: B:58:0x0099->B:34:0x0099 ?: BREAK  , SYNTHETIC] */
    @Override // com.google.android.gms.internal.drive.zzmf
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r10, byte[] r11, int r12, int r13, com.google.android.gms.internal.drive.zziz r14) throws java.io.IOException {
        /*
            r9 = this;
            r0 = r10
            com.google.android.gms.internal.drive.zzkk r0 = (com.google.android.gms.internal.drive.zzkk) r0
            com.google.android.gms.internal.drive.zzmy r1 = r0.zzrq
            com.google.android.gms.internal.drive.zzmy r2 = com.google.android.gms.internal.drive.zzmy.zzfa()
            if (r1 != r2) goto L11
            com.google.android.gms.internal.drive.zzmy r1 = com.google.android.gms.internal.drive.zzmy.zzfb()
            r0.zzrq = r1
        L11:
            com.google.android.gms.internal.drive.zzkk$zzc r10 = (com.google.android.gms.internal.drive.zzkk.zzc) r10
            r10.zzdg()
            r10 = 0
            r0 = r10
        L18:
            if (r12 >= r13) goto La4
            int r4 = com.google.android.gms.internal.drive.zziy.zza(r11, r12, r14)
            int r2 = r14.zznk
            r12 = 11
            r3 = 2
            if (r2 == r12) goto L51
            r12 = r2 & 7
            if (r12 != r3) goto L4c
            com.google.android.gms.internal.drive.zzjy<?> r12 = r9.zzus
            com.google.android.gms.internal.drive.zzjx r0 = r14.zznn
            com.google.android.gms.internal.drive.zzlq r3 = r9.zzuh
            int r5 = r2 >>> 3
            java.lang.Object r12 = r12.zza(r0, r3, r5)
            r0 = r12
            com.google.android.gms.internal.drive.zzkk$zzd r0 = (com.google.android.gms.internal.drive.zzkk.zzd) r0
            if (r0 != 0) goto L43
            r3 = r11
            r5 = r13
            r6 = r1
            r7 = r14
            int r12 = com.google.android.gms.internal.drive.zziy.zza(r2, r3, r4, r5, r6, r7)
            goto L18
        L43:
            com.google.android.gms.internal.drive.zzmd.zzej()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L4c:
            int r12 = com.google.android.gms.internal.drive.zziy.zza(r2, r11, r4, r13, r14)
            goto L18
        L51:
            r12 = 0
            r2 = r10
        L53:
            if (r4 >= r13) goto L99
            int r4 = com.google.android.gms.internal.drive.zziy.zza(r11, r4, r14)
            int r5 = r14.zznk
            int r6 = r5 >>> 3
            r7 = r5 & 7
            if (r6 == r3) goto L7b
            r8 = 3
            if (r6 == r8) goto L65
            goto L90
        L65:
            if (r0 != 0) goto L72
            if (r7 != r3) goto L90
            int r4 = com.google.android.gms.internal.drive.zziy.zze(r11, r4, r14)
            java.lang.Object r2 = r14.zznm
            com.google.android.gms.internal.drive.zzjc r2 = (com.google.android.gms.internal.drive.zzjc) r2
            goto L53
        L72:
            com.google.android.gms.internal.drive.zzmd.zzej()
            java.lang.NoSuchMethodError r10 = new java.lang.NoSuchMethodError
            r10.<init>()
            throw r10
        L7b:
            if (r7 != 0) goto L90
            int r4 = com.google.android.gms.internal.drive.zziy.zza(r11, r4, r14)
            int r12 = r14.zznk
            com.google.android.gms.internal.drive.zzjy<?> r0 = r9.zzus
            com.google.android.gms.internal.drive.zzjx r5 = r14.zznn
            com.google.android.gms.internal.drive.zzlq r6 = r9.zzuh
            java.lang.Object r0 = r0.zza(r5, r6, r12)
            com.google.android.gms.internal.drive.zzkk$zzd r0 = (com.google.android.gms.internal.drive.zzkk.zzd) r0
            goto L53
        L90:
            r6 = 12
            if (r5 == r6) goto L99
            int r4 = com.google.android.gms.internal.drive.zziy.zza(r5, r11, r4, r13, r14)
            goto L53
        L99:
            if (r2 == 0) goto La1
            int r12 = r12 << 3
            r12 = r12 | r3
            r1.zzb(r12, r2)
        La1:
            r12 = r4
            goto L18
        La4:
            if (r12 != r13) goto La7
            return
        La7:
            com.google.android.gms.internal.drive.zzkq r10 = com.google.android.gms.internal.drive.zzkq.zzdm()
            goto Lad
        Lac:
            throw r10
        Lad:
            goto Lac
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.drive.zzlw.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.drive.zziz):void");
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final void zzd(T t) {
        this.zzur.zzd(t);
        this.zzus.zzd(t);
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final boolean zzp(T t) {
        return this.zzus.zzb(t).isInitialized();
    }

    @Override // com.google.android.gms.internal.drive.zzmf
    public final int zzn(T t) {
        zzmx<?, ?> zzmxVar = this.zzur;
        int zzs = zzmxVar.zzs(zzmxVar.zzr(t)) + 0;
        return this.zzui ? zzs + this.zzus.zzb(t).zzco() : zzs;
    }
}
