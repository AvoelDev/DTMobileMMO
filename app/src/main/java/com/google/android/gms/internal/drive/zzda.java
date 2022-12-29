package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzda extends TaskApiCall<zzaw, Void> {
    private final /* synthetic */ DriveContents zzfx;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzda(zzch zzchVar, DriveContents driveContents) {
        this.zzfx = driveContents;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    public final /* synthetic */ void doExecute(zzaw zzawVar, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        ((zzeo) zzawVar.getService()).zza(new zzo(this.zzfx.zzi().getRequestId(), false), new zzhr(taskCompletionSource));
    }
}
