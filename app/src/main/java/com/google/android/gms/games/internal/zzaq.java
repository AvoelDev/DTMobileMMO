package com.google.android.gms.games.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.games.zzci;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public abstract class zzaq extends com.google.android.gms.internal.games.zzb implements zzar {
    public zzaq() {
        super("com.google.android.gms.games.internal.IGamesClient");
    }

    @Override // com.google.android.gms.internal.games.zzb
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 1001) {
            zzci zzb = zzb();
            parcel2.writeNoException();
            com.google.android.gms.internal.games.zzc.zze(parcel2, zzb);
            return true;
        }
        return false;
    }
}
