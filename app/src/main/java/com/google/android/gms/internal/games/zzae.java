package com.google.android.gms.internal.games;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesMetadataClient;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzae extends zzp implements GamesMetadataClient {
    public static final /* synthetic */ int zza = 0;

    public zzae(Activity activity, Games.GamesOptions gamesOptions) {
        super(activity, gamesOptions);
    }

    @Override // com.google.android.gms.games.GamesMetadataClient
    public final Task<Game> getCurrentGame() {
        return doRead(TaskApiCall.builder().run(zzac.zza).setMethodKey(6628).build());
    }

    @Override // com.google.android.gms.games.GamesMetadataClient
    public final Task<AnnotatedData<Game>> loadGame() {
        return doRead(TaskApiCall.builder().run(zzad.zza).setMethodKey(6629).build());
    }

    public zzae(Context context, Games.GamesOptions gamesOptions) {
        super(context, gamesOptions);
    }
}
