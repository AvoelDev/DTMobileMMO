package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;

/* loaded from: classes3.dex */
final class zzgl extends zzl {
    private final BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> zzdx;
    private final DriveFile.DownloadProgressListener zziq;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgl(BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> resultHolder, DriveFile.DownloadProgressListener downloadProgressListener) {
        this.zzdx = resultHolder;
        this.zziq = downloadProgressListener;
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfh zzfhVar) throws RemoteException {
        Status status;
        if (zzfhVar.zzhv) {
            status = new Status(-1);
        } else {
            status = Status.RESULT_SUCCESS;
        }
        this.zzdx.setResult(new zzal(status, new zzbi(zzfhVar.zzes)));
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfl zzflVar) throws RemoteException {
        DriveFile.DownloadProgressListener downloadProgressListener = this.zziq;
        if (downloadProgressListener != null) {
            downloadProgressListener.onProgress(zzflVar.zzhy, zzflVar.zzhz);
        }
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(Status status) throws RemoteException {
        this.zzdx.setResult(new zzal(status, null));
    }
}
