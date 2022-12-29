package com.google.android.gms.games.stats;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public interface PlayerStats extends Freezable<PlayerStats>, Parcelable {
    public static final float UNSET_VALUE = -1.0f;

    float getAverageSessionLength();

    @Deprecated
    float getChurnProbability();

    int getDaysSinceLastPlayed();

    @Deprecated
    float getHighSpenderProbability();

    int getNumberOfPurchases();

    int getNumberOfSessions();

    float getSessionPercentile();

    float getSpendPercentile();

    @Deprecated
    float getSpendProbability();

    @Deprecated
    float getTotalSpendNext28Days();

    Bundle zza();
}
