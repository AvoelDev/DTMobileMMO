package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzi {
    public static zzap zza(zzgx zzgxVar) {
        if (zzgxVar == null) {
            return zzap.zzf;
        }
        int zzj = zzgxVar.zzj() - 1;
        if (zzj == 1) {
            if (zzgxVar.zzi()) {
                return new zzat(zzgxVar.zzd());
            }
            return zzap.zzm;
        } else if (zzj == 2) {
            if (zzgxVar.zzh()) {
                return new zzah(Double.valueOf(zzgxVar.zza()));
            }
            return new zzah(null);
        } else if (zzj == 3) {
            if (zzgxVar.zzg()) {
                return new zzaf(Boolean.valueOf(zzgxVar.zzf()));
            }
            return new zzaf(null);
        } else if (zzj == 4) {
            List<zzgx> zze = zzgxVar.zze();
            ArrayList arrayList = new ArrayList();
            for (zzgx zzgxVar2 : zze) {
                arrayList.add(zza(zzgxVar2));
            }
            return new zzaq(zzgxVar.zzc(), arrayList);
        } else {
            throw new IllegalArgumentException("Unknown type found. Cannot convert entity");
        }
    }

    public static zzap zzb(Object obj) {
        if (obj == null) {
            return zzap.zzg;
        }
        if (obj instanceof String) {
            return new zzat((String) obj);
        }
        if (obj instanceof Double) {
            return new zzah((Double) obj);
        }
        if (obj instanceof Long) {
            return new zzah(Double.valueOf(((Long) obj).doubleValue()));
        }
        if (obj instanceof Integer) {
            return new zzah(Double.valueOf(((Integer) obj).doubleValue()));
        }
        if (obj instanceof Boolean) {
            return new zzaf((Boolean) obj);
        }
        if (obj instanceof Map) {
            zzam zzamVar = new zzam();
            Map map = (Map) obj;
            for (Object obj2 : map.keySet()) {
                zzap zzb = zzb(map.get(obj2));
                if (obj2 != null) {
                    if (!(obj2 instanceof String)) {
                        obj2 = obj2.toString();
                    }
                    zzamVar.zzr((String) obj2, zzb);
                }
            }
            return zzamVar;
        } else if (obj instanceof List) {
            zzae zzaeVar = new zzae();
            for (Object obj3 : (List) obj) {
                zzaeVar.zzq(zzaeVar.zzc(), zzb(obj3));
            }
            return zzaeVar;
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }
}
