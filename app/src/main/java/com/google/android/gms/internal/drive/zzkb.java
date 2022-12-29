package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class zzkb<FieldDescriptorType extends zzkd<FieldDescriptorType>> {
    private static final zzkb zzov = new zzkb(true);
    private boolean zzot;
    private boolean zzou = false;
    final zzmi<FieldDescriptorType, Object> zzos = zzmi.zzav(16);

    private zzkb() {
    }

    private zzkb(boolean z) {
        zzbp();
    }

    public static <T extends zzkd<T>> zzkb<T> zzcn() {
        return zzov;
    }

    public final void zzbp() {
        if (this.zzot) {
            return;
        }
        this.zzos.zzbp();
        this.zzot = true;
    }

    public final boolean isImmutable() {
        return this.zzot;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzkb) {
            return this.zzos.equals(((zzkb) obj).zzos);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzos.hashCode();
    }

    public final Iterator<Map.Entry<FieldDescriptorType, Object>> iterator() {
        if (this.zzou) {
            return new zzkw(this.zzos.entrySet().iterator());
        }
        return this.zzos.entrySet().iterator();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Iterator<Map.Entry<FieldDescriptorType, Object>> descendingIterator() {
        if (this.zzou) {
            return new zzkw(this.zzos.zzet().iterator());
        }
        return this.zzos.zzet().iterator();
    }

    private final Object zza(FieldDescriptorType fielddescriptortype) {
        Object obj = this.zzos.get(fielddescriptortype);
        return obj instanceof zzkt ? zzkt.zzdp() : obj;
    }

    private final void zza(FieldDescriptorType fielddescriptortype, Object obj) {
        if (fielddescriptortype.zzcs()) {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(fielddescriptortype.zzcq(), obj2);
            }
            obj = arrayList;
        } else {
            zza(fielddescriptortype.zzcq(), obj);
        }
        if (obj instanceof zzkt) {
            this.zzou = true;
        }
        this.zzos.zza((zzmi<FieldDescriptorType, Object>) fielddescriptortype, (FieldDescriptorType) obj);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0024, code lost:
        if ((r3 instanceof com.google.android.gms.internal.drive.zzkn) == false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002e, code lost:
        if ((r3 instanceof byte[]) == false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x001b, code lost:
        if ((r3 instanceof com.google.android.gms.internal.drive.zzkt) == false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void zza(com.google.android.gms.internal.drive.zznm r2, java.lang.Object r3) {
        /*
            com.google.android.gms.internal.drive.zzkm.checkNotNull(r3)
            int[] r0 = com.google.android.gms.internal.drive.zzkc.zzow
            com.google.android.gms.internal.drive.zznr r2 = r2.zzfj()
            int r2 = r2.ordinal()
            r2 = r0[r2]
            r0 = 1
            r1 = 0
            switch(r2) {
                case 1: goto L40;
                case 2: goto L3d;
                case 3: goto L3a;
                case 4: goto L37;
                case 5: goto L34;
                case 6: goto L31;
                case 7: goto L28;
                case 8: goto L1e;
                case 9: goto L15;
                default: goto L14;
            }
        L14:
            goto L43
        L15:
            boolean r2 = r3 instanceof com.google.android.gms.internal.drive.zzlq
            if (r2 != 0) goto L26
            boolean r2 = r3 instanceof com.google.android.gms.internal.drive.zzkt
            if (r2 == 0) goto L43
            goto L26
        L1e:
            boolean r2 = r3 instanceof java.lang.Integer
            if (r2 != 0) goto L26
            boolean r2 = r3 instanceof com.google.android.gms.internal.drive.zzkn
            if (r2 == 0) goto L43
        L26:
            r1 = 1
            goto L43
        L28:
            boolean r2 = r3 instanceof com.google.android.gms.internal.drive.zzjc
            if (r2 != 0) goto L26
            boolean r2 = r3 instanceof byte[]
            if (r2 == 0) goto L43
            goto L26
        L31:
            boolean r0 = r3 instanceof java.lang.String
            goto L42
        L34:
            boolean r0 = r3 instanceof java.lang.Boolean
            goto L42
        L37:
            boolean r0 = r3 instanceof java.lang.Double
            goto L42
        L3a:
            boolean r0 = r3 instanceof java.lang.Float
            goto L42
        L3d:
            boolean r0 = r3 instanceof java.lang.Long
            goto L42
        L40:
            boolean r0 = r3 instanceof java.lang.Integer
        L42:
            r1 = r0
        L43:
            if (r1 == 0) goto L46
            return
        L46:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Wrong object type used with protocol message reflection."
            r2.<init>(r3)
            goto L4f
        L4e:
            throw r2
        L4f:
            goto L4e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.drive.zzkb.zza(com.google.android.gms.internal.drive.zznm, java.lang.Object):void");
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzos.zzer(); i++) {
            if (!zzb(this.zzos.zzaw(i))) {
                return false;
            }
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.zzos.zzes()) {
            if (!zzb(entry)) {
                return false;
            }
        }
        return true;
    }

    private static boolean zzb(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorType key = entry.getKey();
        if (key.zzcr() == zznr.MESSAGE) {
            if (key.zzcs()) {
                for (zzlq zzlqVar : (List) entry.getValue()) {
                    if (!zzlqVar.isInitialized()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof zzlq) {
                    if (!((zzlq) value).isInitialized()) {
                        return false;
                    }
                } else if (value instanceof zzkt) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public final void zza(zzkb<FieldDescriptorType> zzkbVar) {
        for (int i = 0; i < zzkbVar.zzos.zzer(); i++) {
            zzc(zzkbVar.zzos.zzaw(i));
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : zzkbVar.zzos.zzes()) {
            zzc(entry);
        }
    }

    private static Object zze(Object obj) {
        if (obj instanceof zzlx) {
            return ((zzlx) obj).zzef();
        }
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            byte[] bArr2 = new byte[bArr.length];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            return bArr2;
        }
        return obj;
    }

    private final void zzc(Map.Entry<FieldDescriptorType, Object> entry) {
        zzlq zzdf;
        FieldDescriptorType key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzkt) {
            value = zzkt.zzdp();
        }
        if (key.zzcs()) {
            Object zza = zza((zzkb<FieldDescriptorType>) key);
            if (zza == null) {
                zza = new ArrayList();
            }
            for (Object obj : (List) value) {
                ((List) zza).add(zze(obj));
            }
            this.zzos.zza((zzmi<FieldDescriptorType, Object>) key, (FieldDescriptorType) zza);
        } else if (key.zzcr() == zznr.MESSAGE) {
            Object zza2 = zza((zzkb<FieldDescriptorType>) key);
            if (zza2 == null) {
                this.zzos.zza((zzmi<FieldDescriptorType, Object>) key, (FieldDescriptorType) zze(value));
                return;
            }
            if (zza2 instanceof zzlx) {
                zzdf = key.zza((zzlx) zza2, (zzlx) value);
            } else {
                zzdf = key.zza(((zzlq) zza2).zzcy(), (zzlq) value).zzdf();
            }
            this.zzos.zza((zzmi<FieldDescriptorType, Object>) key, (FieldDescriptorType) zzdf);
        } else {
            this.zzos.zza((zzmi<FieldDescriptorType, Object>) key, (FieldDescriptorType) zze(value));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void zza(zzjr zzjrVar, zznm zznmVar, int i, Object obj) throws IOException {
        if (zznmVar == zznm.zzxd) {
            zzlq zzlqVar = (zzlq) obj;
            zzkm.zzf(zzlqVar);
            zzjrVar.zzb(i, 3);
            zzlqVar.zzb(zzjrVar);
            zzjrVar.zzb(i, 4);
            return;
        }
        zzjrVar.zzb(i, zznmVar.zzfk());
        switch (zzkc.zzox[zznmVar.ordinal()]) {
            case 1:
                zzjrVar.zza(((Double) obj).doubleValue());
                return;
            case 2:
                zzjrVar.zza(((Float) obj).floatValue());
                return;
            case 3:
                zzjrVar.zzl(((Long) obj).longValue());
                return;
            case 4:
                zzjrVar.zzl(((Long) obj).longValue());
                return;
            case 5:
                zzjrVar.zzx(((Integer) obj).intValue());
                return;
            case 6:
                zzjrVar.zzn(((Long) obj).longValue());
                return;
            case 7:
                zzjrVar.zzaa(((Integer) obj).intValue());
                return;
            case 8:
                zzjrVar.zzc(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzlq) obj).zzb(zzjrVar);
                return;
            case 10:
                zzjrVar.zzb((zzlq) obj);
                return;
            case 11:
                if (obj instanceof zzjc) {
                    zzjrVar.zza((zzjc) obj);
                    return;
                } else {
                    zzjrVar.zzl((String) obj);
                    return;
                }
            case 12:
                if (obj instanceof zzjc) {
                    zzjrVar.zza((zzjc) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                zzjrVar.zzd(bArr, 0, bArr.length);
                return;
            case 13:
                zzjrVar.zzy(((Integer) obj).intValue());
                return;
            case 14:
                zzjrVar.zzaa(((Integer) obj).intValue());
                return;
            case 15:
                zzjrVar.zzn(((Long) obj).longValue());
                return;
            case 16:
                zzjrVar.zzz(((Integer) obj).intValue());
                return;
            case 17:
                zzjrVar.zzm(((Long) obj).longValue());
                return;
            case 18:
                if (obj instanceof zzkn) {
                    zzjrVar.zzx(((zzkn) obj).zzcp());
                    return;
                } else {
                    zzjrVar.zzx(((Integer) obj).intValue());
                    return;
                }
            default:
                return;
        }
    }

    public final int zzco() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzos.zzer(); i2++) {
            i += zzd(this.zzos.zzaw(i2));
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.zzos.zzes()) {
            i += zzd(entry);
        }
        return i;
    }

    private static int zzd(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorType key = entry.getKey();
        Object value = entry.getValue();
        if (key.zzcr() == zznr.MESSAGE && !key.zzcs() && !key.zzct()) {
            if (value instanceof zzkt) {
                return zzjr.zzb(entry.getKey().zzcp(), (zzkt) value);
            }
            return zzjr.zzb(entry.getKey().zzcp(), (zzlq) value);
        }
        return zzb((zzkd<?>) key, value);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(zznm zznmVar, int i, Object obj) {
        int zzab = zzjr.zzab(i);
        if (zznmVar == zznm.zzxd) {
            zzkm.zzf((zzlq) obj);
            zzab <<= 1;
        }
        return zzab + zzb(zznmVar, obj);
    }

    private static int zzb(zznm zznmVar, Object obj) {
        switch (zzkc.zzox[zznmVar.ordinal()]) {
            case 1:
                return zzjr.zzb(((Double) obj).doubleValue());
            case 2:
                return zzjr.zzb(((Float) obj).floatValue());
            case 3:
                return zzjr.zzo(((Long) obj).longValue());
            case 4:
                return zzjr.zzp(((Long) obj).longValue());
            case 5:
                return zzjr.zzac(((Integer) obj).intValue());
            case 6:
                return zzjr.zzr(((Long) obj).longValue());
            case 7:
                return zzjr.zzaf(((Integer) obj).intValue());
            case 8:
                return zzjr.zzd(((Boolean) obj).booleanValue());
            case 9:
                return zzjr.zzd((zzlq) obj);
            case 10:
                if (obj instanceof zzkt) {
                    return zzjr.zza((zzkt) obj);
                }
                return zzjr.zzc((zzlq) obj);
            case 11:
                if (obj instanceof zzjc) {
                    return zzjr.zzb((zzjc) obj);
                }
                return zzjr.zzm((String) obj);
            case 12:
                if (obj instanceof zzjc) {
                    return zzjr.zzb((zzjc) obj);
                }
                return zzjr.zzc((byte[]) obj);
            case 13:
                return zzjr.zzad(((Integer) obj).intValue());
            case 14:
                return zzjr.zzag(((Integer) obj).intValue());
            case 15:
                return zzjr.zzs(((Long) obj).longValue());
            case 16:
                return zzjr.zzae(((Integer) obj).intValue());
            case 17:
                return zzjr.zzq(((Long) obj).longValue());
            case 18:
                if (obj instanceof zzkn) {
                    return zzjr.zzah(((zzkn) obj).zzcp());
                }
                return zzjr.zzah(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int zzb(zzkd<?> zzkdVar, Object obj) {
        zznm zzcq = zzkdVar.zzcq();
        int zzcp = zzkdVar.zzcp();
        if (zzkdVar.zzcs()) {
            int i = 0;
            if (zzkdVar.zzct()) {
                for (Object obj2 : (List) obj) {
                    i += zzb(zzcq, obj2);
                }
                return zzjr.zzab(zzcp) + i + zzjr.zzaj(i);
            }
            for (Object obj3 : (List) obj) {
                i += zza(zzcq, zzcp, obj3);
            }
            return i;
        }
        return zza(zzcq, zzcp, obj);
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzkb zzkbVar = new zzkb();
        for (int i = 0; i < this.zzos.zzer(); i++) {
            Map.Entry<FieldDescriptorType, Object> zzaw = this.zzos.zzaw(i);
            zzkbVar.zza((zzkb) zzaw.getKey(), zzaw.getValue());
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.zzos.zzes()) {
            zzkbVar.zza((zzkb) entry.getKey(), entry.getValue());
        }
        zzkbVar.zzou = this.zzou;
        return zzkbVar;
    }
}
