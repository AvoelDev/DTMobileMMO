package com.google.android.gms.internal.games;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.EventsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.event.EventBuffer;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzt extends zzp implements EventsClient {
    public static final /* synthetic */ int zza = 0;

    public zzt(Activity activity, Games.GamesOptions gamesOptions) {
        super(activity, gamesOptions);
    }

    @Override // com.google.android.gms.games.EventsClient
    public final void increment(final String str, final int i) {
        doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzq
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                String str2 = str;
                int i2 = i;
                TaskCompletionSource taskCompletionSource = (TaskCompletionSource) obj2;
                int i3 = zzt.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzB(str2, i2);
            }
        }).setMethodKey(6615).build());
    }

    @Override // com.google.android.gms.games.EventsClient
    public final Task<AnnotatedData<EventBuffer>> load(final boolean z) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzr
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                boolean z2 = z;
                int i = zzt.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzF((TaskCompletionSource) obj2, z2);
            }
        }).setMethodKey(6613).build());
    }

    @Override // com.google.android.gms.games.EventsClient
    public final Task<AnnotatedData<EventBuffer>> loadByIds(final boolean z, final String... strArr) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.games.zzs
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) {
                boolean z2 = z;
                String[] strArr2 = strArr;
                int i = zzt.zza;
                ((com.google.android.gms.games.internal.zzan) obj).zzG((TaskCompletionSource) obj2, z2, strArr2);
            }
        }).setMethodKey(6614).build());
    }

    public zzt(Context context, Games.GamesOptions gamesOptions) {
        super(context, gamesOptions);
    }
}
