package com.google.android.gms.internal.measurement;

import android.util.Log;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes3.dex */
public final class zzhu extends zzia {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhu(zzhx zzhxVar, String str, Boolean bool, boolean z) {
        super(zzhxVar, str, bool, true, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.measurement.zzia
    @Nullable
    final /* bridge */ /* synthetic */ Object zza(Object obj) {
        if (zzgz.zzc.matcher(obj).matches()) {
            return true;
        }
        if (!zzgz.zzd.matcher(obj).matches()) {
            String zzc = super.zzc();
            Log.e("PhenotypeFlag", "Invalid boolean value for " + zzc + ": " + ((String) obj));
            return null;
        }
        return false;
    }
}
