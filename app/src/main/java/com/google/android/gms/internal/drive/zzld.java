package com.google.android.gms.internal.drive;

/* loaded from: classes3.dex */
final class zzld extends zzla {
    private zzld() {
        super();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.drive.zzla
    public final void zza(Object obj, long j) {
        zzc(obj, j).zzbp();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.gms.internal.drive.zzkp] */
    @Override // com.google.android.gms.internal.drive.zzla
    public final <E> void zza(Object obj, Object obj2, long j) {
        zzkp<E> zzc = zzc(obj, j);
        zzkp<E> zzc2 = zzc(obj2, j);
        int size = zzc.size();
        int size2 = zzc2.size();
        zzkp<E> zzkpVar = zzc;
        zzkpVar = zzc;
        if (size > 0 && size2 > 0) {
            boolean zzbo = zzc.zzbo();
            zzkp<E> zzkpVar2 = zzc;
            if (!zzbo) {
                zzkpVar2 = zzc.zzr(size2 + size);
            }
            zzkpVar2.addAll(zzc2);
            zzkpVar = zzkpVar2;
        }
        if (size > 0) {
            zzc2 = zzkpVar;
        }
        zznd.zza(obj, j, zzc2);
    }

    private static <E> zzkp<E> zzc(Object obj, long j) {
        return (zzkp) zznd.zzo(obj, j);
    }
}
