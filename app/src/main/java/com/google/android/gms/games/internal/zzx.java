package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.GamesStatusUtils;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzx extends zza {
    final /* synthetic */ zzan zza;
    private final TaskCompletionSource zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzx(zzan zzanVar, TaskCompletionSource taskCompletionSource) {
        this.zza = zzanVar;
        this.zzb = taskCompletionSource;
    }

    @Override // com.google.android.gms.games.internal.zza, com.google.android.gms.games.internal.zzap
    public final void zzi(DataHolder dataHolder, DataHolder dataHolder2) {
        int statusCode = dataHolder2.getStatusCode();
        boolean z = statusCode == 3;
        if (statusCode == 10003) {
            zzan.zzs(this.zza, this.zzb);
            dataHolder.close();
            dataHolder2.close();
        } else if (statusCode == 0 || z) {
            LeaderboardBuffer leaderboardBuffer = new LeaderboardBuffer(dataHolder);
            try {
                Leaderboard freeze = leaderboardBuffer.getCount() > 0 ? leaderboardBuffer.get(0).freeze() : null;
                leaderboardBuffer.close();
                this.zzb.setResult(new AnnotatedData(new LeaderboardsClient.LeaderboardScores(freeze, new LeaderboardScoreBuffer(dataHolder2)), z));
            } catch (Throwable th) {
                try {
                    leaderboardBuffer.close();
                } catch (Throwable th2) {
                    try {
                        Throwable.class.getDeclaredMethod("addSuppressed", Throwable.class).invoke(th, th2);
                    } catch (Exception unused) {
                    }
                }
                throw th;
            }
        } else {
            GamesStatusUtils.zza(this.zzb, statusCode);
            dataHolder.close();
            dataHolder2.close();
        }
    }
}
