package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.drive.events.ListenerToken;

/* loaded from: classes3.dex */
public final class zzg implements ListenerToken {
    private final ListenerHolder.ListenerKey zzcy;
    private ICancelToken zzcz = null;

    public zzg(ListenerHolder.ListenerKey listenerKey) {
        this.zzcy = listenerKey;
    }

    public final ListenerHolder.ListenerKey zzad() {
        return this.zzcy;
    }

    public final void setCancelToken(ICancelToken iCancelToken) {
        this.zzcz = iCancelToken;
    }

    public final boolean cancel() {
        ICancelToken iCancelToken = this.zzcz;
        if (iCancelToken != null) {
            try {
                iCancelToken.cancel();
                return true;
            } catch (RemoteException unused) {
                return false;
            }
        }
        return false;
    }
}
