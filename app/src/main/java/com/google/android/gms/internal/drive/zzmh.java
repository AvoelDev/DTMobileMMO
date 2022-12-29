package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class zzmh {
    private static final Class<?> zzuz = zzep();
    private static final zzmx<?, ?> zzva = zzf(false);
    private static final zzmx<?, ?> zzvb = zzf(true);
    private static final zzmx<?, ?> zzvc = new zzmz();

    public static void zzg(Class<?> cls) {
        Class<?> cls2;
        if (!zzkk.class.isAssignableFrom(cls) && (cls2 = zzuz) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Double> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzg(i, list, z);
    }

    public static void zzb(int i, List<Float> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzf(i, list, z);
    }

    public static void zzc(int i, List<Long> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzc(i, list, z);
    }

    public static void zzd(int i, List<Long> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzd(i, list, z);
    }

    public static void zze(int i, List<Long> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzn(i, list, z);
    }

    public static void zzf(int i, List<Long> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zze(i, list, z);
    }

    public static void zzg(int i, List<Long> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzl(i, list, z);
    }

    public static void zzh(int i, List<Integer> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zza(i, list, z);
    }

    public static void zzi(int i, List<Integer> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzj(i, list, z);
    }

    public static void zzj(int i, List<Integer> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzm(i, list, z);
    }

    public static void zzk(int i, List<Integer> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzb(i, list, z);
    }

    public static void zzl(int i, List<Integer> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzk(i, list, z);
    }

    public static void zzm(int i, List<Integer> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzh(i, list, z);
    }

    public static void zzn(int i, List<Boolean> list, zzns zznsVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzi(i, list, z);
    }

    public static void zza(int i, List<String> list, zzns zznsVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zza(i, list);
    }

    public static void zzb(int i, List<zzjc> list, zzns zznsVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzb(i, list);
    }

    public static void zza(int i, List<?> list, zzns zznsVar, zzmf zzmfVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zza(i, list, zzmfVar);
    }

    public static void zzb(int i, List<?> list, zzns zznsVar, zzmf zzmfVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zznsVar.zzb(i, list, zzmfVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzle) {
            zzle zzleVar = (zzle) list;
            i = 0;
            while (i2 < size) {
                i += zzjr.zzo(zzleVar.getLong(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzjr.zzo(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzo(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zza(list) + (list.size() * zzjr.zzab(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzb(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzle) {
            zzle zzleVar = (zzle) list;
            i = 0;
            while (i2 < size) {
                i += zzjr.zzp(zzleVar.getLong(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzjr.zzp(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzp(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzb(list) + (size * zzjr.zzab(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzc(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzle) {
            zzle zzleVar = (zzle) list;
            i = 0;
            while (i2 < size) {
                i += zzjr.zzq(zzleVar.getLong(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzjr.zzq(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzq(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzc(list) + (size * zzjr.zzab(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzd(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzkl) {
            zzkl zzklVar = (zzkl) list;
            i = 0;
            while (i2 < size) {
                i += zzjr.zzah(zzklVar.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzjr.zzah(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzr(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzjr.zzab(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zze(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzkl) {
            zzkl zzklVar = (zzkl) list;
            i = 0;
            while (i2 < size) {
                i += zzjr.zzac(zzklVar.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzjr.zzac(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzs(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zze(list) + (size * zzjr.zzab(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzf(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzkl) {
            zzkl zzklVar = (zzkl) list;
            i = 0;
            while (i2 < size) {
                i += zzjr.zzad(zzklVar.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzjr.zzad(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzt(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzf(list) + (size * zzjr.zzab(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzg(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzkl) {
            zzkl zzklVar = (zzkl) list;
            i = 0;
            while (i2 < size) {
                i += zzjr.zzae(zzklVar.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzjr.zzae(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzu(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzg(list) + (size * zzjr.zzab(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzh(List<?> list) {
        return list.size() << 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzv(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzjr.zzj(i, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzi(List<?> list) {
        return list.size() << 3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzw(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzjr.zzg(i, 0L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzj(List<?> list) {
        return list.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzx(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzjr.zzc(i, true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzc(int i, List<?> list) {
        int zzm;
        int zzm2;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int zzab = zzjr.zzab(i) * size;
        if (list instanceof zzkz) {
            zzkz zzkzVar = (zzkz) list;
            while (i2 < size) {
                Object zzao = zzkzVar.zzao(i2);
                if (zzao instanceof zzjc) {
                    zzm2 = zzjr.zzb((zzjc) zzao);
                } else {
                    zzm2 = zzjr.zzm((String) zzao);
                }
                zzab += zzm2;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzjc) {
                    zzm = zzjr.zzb((zzjc) obj);
                } else {
                    zzm = zzjr.zzm((String) obj);
                }
                zzab += zzm;
                i2++;
            }
        }
        return zzab;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzc(int i, Object obj, zzmf zzmfVar) {
        if (obj instanceof zzkx) {
            return zzjr.zza(i, (zzkx) obj);
        }
        return zzjr.zzb(i, (zzlq) obj, zzmfVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzc(int i, List<?> list, zzmf zzmfVar) {
        int zza;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzab = zzjr.zzab(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzkx) {
                zza = zzjr.zza((zzkx) obj);
            } else {
                zza = zzjr.zza((zzlq) obj, zzmfVar);
            }
            zzab += zza;
        }
        return zzab;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzd(int i, List<zzjc> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzab = size * zzjr.zzab(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzab += zzjr.zzb(list.get(i2));
        }
        return zzab;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzd(int i, List<zzlq> list, zzmf zzmfVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzjr.zzc(i, list.get(i3), zzmfVar);
        }
        return i2;
    }

    public static zzmx<?, ?> zzem() {
        return zzva;
    }

    public static zzmx<?, ?> zzen() {
        return zzvb;
    }

    public static zzmx<?, ?> zzeo() {
        return zzvc;
    }

    private static zzmx<?, ?> zzf(boolean z) {
        try {
            Class<?> zzeq = zzeq();
            if (zzeq == null) {
                return null;
            }
            return (zzmx) zzeq.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzep() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzeq() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean zzd(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> void zza(zzll zzllVar, T t, T t2, long j) {
        zznd.zza(t, j, zzllVar.zzb(zznd.zzo(t, j), zznd.zzo(t2, j)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T, FT extends zzkd<FT>> void zza(zzjy<FT> zzjyVar, T t, T t2) {
        zzkb<FT> zzb = zzjyVar.zzb(t2);
        if (zzb.zzos.isEmpty()) {
            return;
        }
        zzjyVar.zzc(t).zza(zzb);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T, UT, UB> void zza(zzmx<UT, UB> zzmxVar, T t, T t2) {
        zzmxVar.zze(t, zzmxVar.zzg(zzmxVar.zzr(t), zzmxVar.zzr(t2)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <UT, UB> UB zza(int i, List<Integer> list, zzko zzkoVar, UB ub, zzmx<UT, UB> zzmxVar) {
        UB ub2;
        int intValue;
        if (zzkoVar == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            ub2 = ub;
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue2 = list.get(i3).intValue();
                if (zzkoVar.zzan(intValue2)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue2));
                    }
                    i2++;
                } else {
                    ub2 = (UB) zza(i, intValue2, ub2, zzmxVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            loop1: while (true) {
                ub2 = ub;
                while (it.hasNext()) {
                    intValue = it.next().intValue();
                    if (!zzkoVar.zzan(intValue)) {
                        break;
                    }
                }
                ub = (UB) zza(i, intValue, ub2, zzmxVar);
                it.remove();
            }
        }
        return ub2;
    }

    private static <UT, UB> UB zza(int i, int i2, UB ub, zzmx<UT, UB> zzmxVar) {
        if (ub == null) {
            ub = zzmxVar.zzez();
        }
        zzmxVar.zza((zzmx<UT, UB>) ub, i, i2);
        return ub;
    }
}
