package com.google.android.gms.games.internal;

import com.google.android.gms.games.GamesStatusUtils;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzae extends zza {
    final TaskCompletionSource zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzae(TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    @Override // com.google.android.gms.games.internal.zza, com.google.android.gms.games.internal.zzap
    public final void zzu(int i, boolean z) {
        if (i == 0) {
            this.zza.setResult(Boolean.valueOf(z));
        } else {
            GamesStatusUtils.zza(this.zza, i);
        }
    }
}
