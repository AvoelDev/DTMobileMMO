package com.google.android.gms.games;

import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public interface GamesMetadataClient {
    Task<Game> getCurrentGame();

    Task<AnnotatedData<Game>> loadGame();
}
