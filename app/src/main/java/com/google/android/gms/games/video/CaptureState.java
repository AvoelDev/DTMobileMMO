package com.google.android.gms.games.video;

import android.os.Bundle;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class CaptureState {
    private final boolean zza;
    private final int zzb;
    private final int zzc;
    private final boolean zzd;
    private final boolean zze;

    private CaptureState(boolean z, int i, int i2, boolean z2, boolean z3) {
        Preconditions.checkArgument(VideoConfiguration.isValidCaptureMode(i, true));
        Preconditions.checkArgument(VideoConfiguration.isValidQualityLevel(i2, true));
        this.zza = z;
        this.zzb = i;
        this.zzc = i2;
        this.zzd = z2;
        this.zze = z3;
    }

    public static CaptureState zza(Bundle bundle) {
        if (bundle == null || bundle.get("IsCapturing") == null) {
            return null;
        }
        return new CaptureState(bundle.getBoolean("IsCapturing", false), bundle.getInt("CaptureMode", -1), bundle.getInt("CaptureQuality", -1), bundle.getBoolean("IsOverlayVisible", false), bundle.getBoolean("IsPaused", false));
    }

    public int getCaptureMode() {
        return this.zzb;
    }

    public int getCaptureQuality() {
        return this.zzc;
    }

    public boolean isCapturing() {
        return this.zza;
    }

    public boolean isOverlayVisible() {
        return this.zzd;
    }

    public boolean isPaused() {
        return this.zze;
    }

    public String toString() {
        return Objects.toStringHelper(this).add("IsCapturing", Boolean.valueOf(this.zza)).add("CaptureMode", Integer.valueOf(this.zzb)).add("CaptureQuality", Integer.valueOf(this.zzc)).add("IsOverlayVisible", Boolean.valueOf(this.zzd)).add("IsPaused", Boolean.valueOf(this.zze)).toString();
    }
}
