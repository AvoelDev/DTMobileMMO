package com.google.android.gms.internal.measurement;

import android.util.Log;
import javax.annotation.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes3.dex */
public final class zzht extends zzia {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzht(zzhx zzhxVar, String str, Long l, boolean z) {
        super(zzhxVar, str, l, true, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzia
    @Nullable
    final /* bridge */ /* synthetic */ Object zza(Object obj) {
        try {
            return Long.valueOf(Long.parseLong((String) obj));
        } catch (NumberFormatException unused) {
            String zzc = super.zzc();
            Log.e("PhenotypeFlag", "Invalid long value for " + zzc + ": " + ((String) obj));
            return null;
        }
    }
}
