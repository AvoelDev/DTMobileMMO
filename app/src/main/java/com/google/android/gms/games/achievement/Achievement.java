package com.google.android.gms.games.achievement;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Player;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public interface Achievement extends Freezable<Achievement>, Parcelable {
    public static final int STATE_HIDDEN = 2;
    public static final int STATE_REVEALED = 1;
    public static final int STATE_UNLOCKED = 0;
    public static final int TYPE_INCREMENTAL = 1;
    public static final int TYPE_STANDARD = 0;

    /* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface AchievementState {
    }

    /* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface AchievementType {
    }

    String getAchievementId();

    int getCurrentSteps();

    String getDescription();

    void getDescription(CharArrayBuffer charArrayBuffer);

    String getFormattedCurrentSteps();

    void getFormattedCurrentSteps(CharArrayBuffer charArrayBuffer);

    String getFormattedTotalSteps();

    void getFormattedTotalSteps(CharArrayBuffer charArrayBuffer);

    long getLastUpdatedTimestamp();

    String getName();

    void getName(CharArrayBuffer charArrayBuffer);

    Player getPlayer();

    Uri getRevealedImageUri();

    @Deprecated
    String getRevealedImageUrl();

    int getState();

    int getTotalSteps();

    int getType();

    Uri getUnlockedImageUri();

    @Deprecated
    String getUnlockedImageUrl();

    long getXpValue();

    float zza();

    Player zzb();

    String zzc();
}
