package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
public class zzhh<T> extends zzl {
    private TaskCompletionSource<T> zziv;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhh(TaskCompletionSource<T> taskCompletionSource) {
        this.zziv = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(Status status) throws RemoteException {
        this.zziv.setException(new ApiException(status));
    }

    public final TaskCompletionSource<T> zzay() {
        return this.zziv;
    }
}
