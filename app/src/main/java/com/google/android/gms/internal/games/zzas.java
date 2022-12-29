package com.google.android.gms.internal.games;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.leaderboard.Leaderboard;
import com.google.android.gms.games.leaderboard.LeaderboardBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzas extends zzp implements LeaderboardsClient {
    public static final /* synthetic */ int zza = 0;

    public zzas(Activity activity, Games.GamesOptions gamesOptions) {
        super(activity, gamesOptions);
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<Intent> getAllLeaderboardsIntent() {
        return doRead(TaskApiCall.builder().run(zzai.zza).setMethodKey(6630).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<Intent> getLeaderboardIntent(String str) {
        return getLeaderboardIntent(str, -1, -1);
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<AnnotatedData<LeaderboardScore>> loadCurrentPlayerLeaderboardScore(final String str, final int i, final int i2) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzak
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str2 = str;
                int i3 = i;
                int i4 = i2;
                int i5 = zzas.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzE((TaskCompletionSource) obj2, str2, i3, i4);
            }
        }).setMethodKey(6633).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<AnnotatedData<Leaderboard>> loadLeaderboardMetadata(final String str, final boolean z) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzar
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str2 = str;
                boolean z2 = z;
                int i = zzas.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzI((TaskCompletionSource) obj2, str2, z2);
            }
        }).setMethodKey(6632).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<AnnotatedData<LeaderboardsClient.LeaderboardScores>> loadMoreScores(final LeaderboardScoreBuffer leaderboardScoreBuffer, final int i, final int i2) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzag
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                LeaderboardScoreBuffer leaderboardScoreBuffer2 = LeaderboardScoreBuffer.this;
                int i3 = i;
                int i4 = i2;
                int i5 = zzas.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzK((TaskCompletionSource) obj2, leaderboardScoreBuffer2, i3, i4);
            }
        }).setMethodKey(6636).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<AnnotatedData<LeaderboardsClient.LeaderboardScores>> loadPlayerCenteredScores(String str, int i, int i2, int i3) {
        return doRead(TaskApiCall.builder().run(new zzal(str, i, i2, i3, false)).setMethodKey(6635).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<AnnotatedData<LeaderboardsClient.LeaderboardScores>> loadTopScores(String str, int i, int i2, int i3) {
        return doRead(TaskApiCall.builder().run(new zzam(str, i, i2, i3, false)).setMethodKey(6634).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final void submitScore(final String str, final long j) {
        doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzan
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str2 = str;
                long j2 = j;
                TaskCompletionSource taskCompletionSource = (TaskCompletionSource) obj2;
                int i = zzas.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzZ(str2, j2, null);
            }
        }).setMethodKey(6637).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<ScoreSubmissionData> submitScoreImmediate(final String str, final long j) {
        return doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzao
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str2 = str;
                long j2 = j;
                int i = zzas.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzaa((TaskCompletionSource) obj2, str2, j2, null);
            }
        }).setMethodKey(6638).build());
    }

    public zzas(Context context, Games.GamesOptions gamesOptions) {
        super(context, gamesOptions);
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<Intent> getLeaderboardIntent(String str, int i) {
        return getLeaderboardIntent(str, i, -1);
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<Intent> getLeaderboardIntent(final String str, final int i, final int i2) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzaj
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str2 = str;
                int i3 = i;
                int i4 = i2;
                int i5 = zzas.zza;
                ((TaskCompletionSource) obj2).setResult(((com.google.android.gms.games.internal.zzas) ((com.google.android.gms.games.internal.zzan) obj).getService()).zzk(str2, i3, i4));
            }
        }).setMethodKey(6631).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<AnnotatedData<LeaderboardBuffer>> loadLeaderboardMetadata(final boolean z) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzah
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                boolean z2 = z;
                int i = zzas.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzJ((TaskCompletionSource) obj2, z2);
            }
        }).setMethodKey(6632).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<AnnotatedData<LeaderboardsClient.LeaderboardScores>> loadPlayerCenteredScores(String str, int i, int i2, int i3, boolean z) {
        return doRead(TaskApiCall.builder().run(new zzal(str, i, i2, i3, z)).setMethodKey(6635).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<AnnotatedData<LeaderboardsClient.LeaderboardScores>> loadTopScores(String str, int i, int i2, int i3, boolean z) {
        return doRead(TaskApiCall.builder().run(new zzam(str, i, i2, i3, z)).setMethodKey(6634).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final void submitScore(final String str, final long j, final String str2) {
        doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzap
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str3 = str;
                long j2 = j;
                String str4 = str2;
                TaskCompletionSource taskCompletionSource = (TaskCompletionSource) obj2;
                int i = zzas.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzZ(str3, j2, str4);
            }
        }).setMethodKey(6637).build());
    }

    @Override // com.google.android.gms.games.LeaderboardsClient
    public final Task<ScoreSubmissionData> submitScoreImmediate(final String str, final long j, final String str2) {
        return doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzaq
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str3 = str;
                long j2 = j;
                String str4 = str2;
                int i = zzas.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzaa((TaskCompletionSource) obj2, str3, j2, str4);
            }
        }).setMethodKey(6638).build());
    }
}
