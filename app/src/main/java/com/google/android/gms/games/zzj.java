package com.google.android.gms.games;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.zzan;
import com.google.android.gms.games.internal.zzau;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
class zzj extends Api.AbstractClientBuilder {
    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzj(zzi zziVar) {
    }

    @Override // com.google.android.gms.common.api.Api.AbstractClientBuilder
    public final /* bridge */ /* synthetic */ Api.Client buildClient(Context context, Looper looper, ClientSettings clientSettings, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Games.GamesOptions gamesOptions = (Games.GamesOptions) obj;
        if (gamesOptions == null) {
            gamesOptions = new Games.GamesOptions.Builder(null).build();
        }
        return new zzan(context, looper, clientSettings, gamesOptions, connectionCallbacks, onConnectionFailedListener, zzau.zza());
    }

    @Override // com.google.android.gms.common.api.Api.BaseClientBuilder
    public final int getPriority() {
        return 1;
    }
}
