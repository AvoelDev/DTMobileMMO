package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final /* synthetic */ class zzx implements RemoteCall {
    public static final /* synthetic */ zzx zza = new zzx();

    private /* synthetic */ zzx() {
    }

    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final void accept(Object obj, Object obj2) {
        com.google.android.gms.games.internal.zzan zzanVar = (com.google.android.gms.games.internal.zzan) obj;
        int i = zzab.zza;
        ((TaskCompletionSource) obj2).setResult(null);
    }
}
