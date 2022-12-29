package com.google.android.gms.internal.games;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzbf extends zzp implements PlayersClient {
    public static final /* synthetic */ int zza = 0;

    public zzbf(Activity activity, Games.GamesOptions gamesOptions) {
        super(activity, gamesOptions);
    }

    private final Task zza(final String str, final int i) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzax
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str2 = str;
                int i2 = i;
                int i3 = zzbf.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzO((TaskCompletionSource) obj2, str2, i2, true, false);
            }
        }).setMethodKey(6647).build());
    }

    private final Task zzb(final String str, final int i, final boolean z) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzay
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str2 = str;
                int i2 = i;
                boolean z2 = z;
                int i3 = zzbf.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzO((TaskCompletionSource) obj2, str2, i2, false, z2);
            }
        }).setMethodKey(6646).build());
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<Intent> getCompareProfileIntent(final Player player) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzaw
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                Player player2 = Player.this;
                int i = zzbf.zza;
                PlayerEntity playerEntity = new PlayerEntity(player2);
                Intent zzi = ((com.google.android.gms.games.internal.zzas) ((com.google.android.gms.games.internal.zzan) obj).getService()).zzi(playerEntity);
                zzi.setExtrasClassLoader(playerEntity.getClass().getClassLoader());
                ((TaskCompletionSource) obj2).setResult(zzi);
            }
        }).setMethodKey(6643).build());
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<Intent> getCompareProfileIntentWithAlternativeNameHints(String str, String str2, String str3) {
        return doRead(TaskApiCall.builder().run(new zzaz(str, str2, str3)).setMethodKey(6644).build());
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<Player> getCurrentPlayer() {
        return doRead(TaskApiCall.builder().run(zzbc.zza).setMethodKey(6641).build());
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<String> getCurrentPlayerId() {
        return doRead(TaskApiCall.builder().run(zzbd.zza).setMethodKey(6640).build());
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<Intent> getPlayerSearchIntent() {
        return doRead(TaskApiCall.builder().run(zzbe.zza).setMethodKey(6645).build());
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<AnnotatedData<PlayerBuffer>> loadFriends(int i, boolean z) {
        return zzb("friends_all", i, z);
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<AnnotatedData<PlayerBuffer>> loadMoreFriends(int i) {
        return zza("friends_all", i);
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<AnnotatedData<PlayerBuffer>> loadMoreRecentlyPlayedWithPlayers(int i) {
        return zza("played_with", i);
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<AnnotatedData<Player>> loadPlayer(String str) {
        return doRead(TaskApiCall.builder().run(new zzba(str, false)).setMethodKey(6642).build());
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<AnnotatedData<PlayerBuffer>> loadRecentlyPlayedWithPlayers(int i, boolean z) {
        return zzb("played_with", i, z);
    }

    public zzbf(Context context, Games.GamesOptions gamesOptions) {
        super(context, gamesOptions);
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<Intent> getCompareProfileIntent(String str) {
        return doRead(TaskApiCall.builder().run(new zzaz(str, null, null)).setMethodKey(6644).build());
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<AnnotatedData<Player>> getCurrentPlayer(final boolean z) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzbb
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                boolean z2 = z;
                int i = zzbf.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzz((TaskCompletionSource) obj2, z2);
            }
        }).setMethodKey(6641).build());
    }

    @Override // com.google.android.gms.games.PlayersClient
    public final Task<AnnotatedData<Player>> loadPlayer(String str, boolean z) {
        return doRead(TaskApiCall.builder().run(new zzba(str, z)).setMethodKey(6642).build());
    }
}
