package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
class zzz extends zza {
    private final ListenerHolder zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzz(ListenerHolder listenerHolder) {
        this.zza = (ListenerHolder) Preconditions.checkNotNull(listenerHolder, "Callback must not be null");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzw(zzg zzgVar) {
        ListenerHolder listenerHolder = this.zza;
        int i = zzan.zze;
        listenerHolder.notifyListener(new zzj(zzgVar, null));
    }
}
