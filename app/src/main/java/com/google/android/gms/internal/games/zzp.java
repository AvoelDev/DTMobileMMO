package com.google.android.gms.internal.games;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.games.Games;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public class zzp extends GoogleApi {
    public zzp(Activity activity, Games.GamesOptions gamesOptions) {
        super(activity, (Api<Games.GamesOptions>) Games.zze, gamesOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.GoogleApi
    public final ClientSettings.Builder createClientSettingsBuilder() {
        ClientSettings.Builder createClientSettingsBuilder = super.createClientSettingsBuilder();
        if (getApiOptions() != null) {
            String str = ((Games.GamesOptions) getApiOptions()).zzl;
        }
        return createClientSettingsBuilder;
    }

    public zzp(Context context, Games.GamesOptions gamesOptions) {
        super(context, Games.zze, gamesOptions, GoogleApi.Settings.DEFAULT_SETTINGS);
    }
}
