package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes3.dex */
public final class zzip extends zzb implements zzio {
    public static zzio zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.realtime.internal.IRealtimeService");
        if (queryLocalInterface instanceof zzio) {
            return (zzio) queryLocalInterface;
        }
        return new zziq(iBinder);
    }
}
