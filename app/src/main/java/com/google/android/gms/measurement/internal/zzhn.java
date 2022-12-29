package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzhn implements Runnable {
    final /* synthetic */ Bundle zza;
    final /* synthetic */ zzid zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhn(zzid zzidVar, Bundle bundle) {
        this.zzb = zzidVar;
        this.zza = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzid zzidVar = this.zzb;
        Bundle bundle = this.zza;
        zzidVar.zzg();
        zzidVar.zza();
        Preconditions.checkNotNull(bundle);
        String checkNotEmpty = Preconditions.checkNotEmpty(bundle.getString("name"));
        if (!zzidVar.zzs.zzJ()) {
            zzidVar.zzs.zzay().zzj().zza("Conditional property not cleared since app measurement is disabled");
            return;
        }
        try {
            zzidVar.zzs.zzt().zzE(new zzac(bundle.getString("app_id"), "", new zzlc(checkNotEmpty, 0L, null, ""), bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), bundle.getBoolean(AppMeasurementSdk.ConditionalUserProperty.ACTIVE), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME), null, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT), null, bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE), zzidVar.zzs.zzv().zzz(bundle.getString("app_id"), bundle.getString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME), bundle.getBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS), "", bundle.getLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP), true, true)));
        } catch (IllegalArgumentException unused) {
        }
    }
}
