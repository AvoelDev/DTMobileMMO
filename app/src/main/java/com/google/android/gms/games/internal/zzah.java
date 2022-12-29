package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.GamesStatusUtils;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzah extends zza {
    final /* synthetic */ zzan zza;
    private final TaskCompletionSource zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzah(zzan zzanVar, TaskCompletionSource taskCompletionSource) {
        this.zza = zzanVar;
        this.zzb = taskCompletionSource;
    }

    @Override // com.google.android.gms.games.internal.zza, com.google.android.gms.games.internal.zzap
    public final void zzg(DataHolder dataHolder) {
        zzm(dataHolder);
    }

    @Override // com.google.android.gms.games.internal.zza, com.google.android.gms.games.internal.zzap
    public final void zzm(DataHolder dataHolder) {
        int statusCode = dataHolder.getStatusCode();
        if (statusCode == 10003) {
            zzan.zzs(this.zza, this.zzb);
            dataHolder.close();
            return;
        }
        boolean z = statusCode == 3;
        if (statusCode == 0 || z) {
            this.zzb.setResult(new AnnotatedData(new PlayerBuffer(dataHolder), z));
            return;
        }
        GamesStatusUtils.zza(this.zzb, statusCode);
        dataHolder.close();
    }
}
