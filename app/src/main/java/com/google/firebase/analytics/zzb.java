package com.google.firebase.analytics;

import com.google.android.gms.internal.measurement.zzee;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement-api@@21.1.1 */
/* loaded from: classes2.dex */
final class zzb implements Callable {
    final /* synthetic */ FirebaseAnalytics zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzb(FirebaseAnalytics firebaseAnalytics) {
        this.zza = firebaseAnalytics;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzee zzeeVar;
        zzeeVar = this.zza.zzb;
        return zzeeVar.zzk();
    }
}
