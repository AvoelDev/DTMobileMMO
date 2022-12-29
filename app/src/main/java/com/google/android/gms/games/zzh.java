package com.google.android.gms.games;

import com.google.android.gms.games.Games;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
final class zzh extends zzj {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzh() {
        super(null);
    }

    @Override // com.google.android.gms.common.api.Api.BaseClientBuilder
    public final /* synthetic */ List getImpliedScopes(Object obj) {
        Games.GamesOptions gamesOptions = (Games.GamesOptions) obj;
        return Collections.singletonList(Games.zzf);
    }
}
