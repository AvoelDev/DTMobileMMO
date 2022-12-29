package com.google.android.gms.games.internal.experience;

import android.net.Uri;
import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.games.Game;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public interface ExperienceEvent extends Freezable<ExperienceEvent>, Parcelable {
    @Deprecated
    String getIconImageUrl();

    int zza();

    int zzb();

    long zzc();

    long zzd();

    long zze();

    Uri zzf();

    Game zzg();

    String zzh();

    String zzi();

    String zzj();
}
