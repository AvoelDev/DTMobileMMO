package com.google.android.gms.internal.drive;

import java.util.Comparator;

/* loaded from: classes3.dex */
final class zzje implements Comparator<zzjc> {
    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzjc zzjcVar, zzjc zzjcVar2) {
        int zza;
        int zza2;
        zzjc zzjcVar3 = zzjcVar;
        zzjc zzjcVar4 = zzjcVar2;
        zzjj zzjjVar = (zzjj) zzjcVar3.iterator();
        zzjj zzjjVar2 = (zzjj) zzjcVar4.iterator();
        while (zzjjVar.hasNext() && zzjjVar2.hasNext()) {
            zza = zzjc.zza(zzjjVar.nextByte());
            zza2 = zzjc.zza(zzjjVar2.nextByte());
            int compare = Integer.compare(zza, zza2);
            if (compare != 0) {
                return compare;
            }
        }
        return Integer.compare(zzjcVar3.size(), zzjcVar4.size());
    }
}
