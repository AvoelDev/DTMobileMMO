package com.google.android.gms.internal.games;

import android.os.Bundle;
import android.os.IBinder;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzch {
    public int zzb;
    public int zzc = -1;
    public int zzd = 0;
    public int zze = 0;
    public int zzf = 0;
    public int zzg = 0;
    public IBinder zza = null;

    public zzch(int i, IBinder iBinder) {
        this.zzb = i;
    }

    public final Bundle zza() {
        Bundle bundle = new Bundle();
        bundle.putInt("popupLocationInfo.gravity", this.zzb);
        bundle.putInt("popupLocationInfo.displayId", this.zzc);
        bundle.putInt("popupLocationInfo.left", this.zzd);
        bundle.putInt("popupLocationInfo.top", this.zze);
        bundle.putInt("popupLocationInfo.right", this.zzf);
        bundle.putInt("popupLocationInfo.bottom", this.zzg);
        return bundle;
    }
}
