package com.google.android.gms.internal.games;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzab extends zzp implements GamesClient {
    public static final /* synthetic */ int zza = 0;

    public zzab(Activity activity, Games.GamesOptions gamesOptions) {
        super(activity, gamesOptions);
    }

    @Override // com.google.android.gms.games.GamesClient
    public final Task<Bundle> getActivationHint() {
        return doRead(TaskApiCall.builder().run(zzx.zza).setMethodKey(6622).build());
    }

    @Override // com.google.android.gms.games.GamesClient
    public final Task<String> getAppId() {
        return doRead(TaskApiCall.builder().run(zzy.zza).setMethodKey(6620).build());
    }

    @Override // com.google.android.gms.games.GamesClient
    public final Task<String> getCurrentAccountName() {
        return doRead(TaskApiCall.builder().run(zzz.zza).setMethodKey(6618).build());
    }

    @Override // com.google.android.gms.games.GamesClient
    public final Task<Intent> getSettingsIntent() {
        return doRead(TaskApiCall.builder().run(zzaa.zza).setMethodKey(6621).build());
    }

    @Override // com.google.android.gms.games.GamesClient
    public final Task<Void> setGravityForPopups(final int i) {
        return doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzv
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                int i2 = i;
                int i3 = zzab.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzW(i2);
                ((TaskCompletionSource) obj2).setResult(null);
            }
        }).setMethodKey(6616).build());
    }

    @Override // com.google.android.gms.games.GamesClient
    public final Task<Void> setViewForPopups(final View view) {
        return doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzw
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                View view2 = view;
                int i = zzab.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzX(view2);
                ((TaskCompletionSource) obj2).setResult(null);
            }
        }).setMethodKey(6617).build());
    }

    public zzab(Context context, Games.GamesOptions gamesOptions) {
        super(context, gamesOptions);
    }
}
