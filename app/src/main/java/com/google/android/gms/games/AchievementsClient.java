package com.google.android.gms.games;

import android.content.Intent;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.tasks.Task;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public interface AchievementsClient {
    Task<Intent> getAchievementsIntent();

    void increment(String str, int i);

    Task<Boolean> incrementImmediate(String str, int i);

    Task<AnnotatedData<AchievementBuffer>> load(boolean z);

    void reveal(String str);

    Task<Void> revealImmediate(String str);

    void setSteps(String str, int i);

    Task<Boolean> setStepsImmediate(String str, int i);

    void unlock(String str);

    Task<Void> unlockImmediate(String str);
}
