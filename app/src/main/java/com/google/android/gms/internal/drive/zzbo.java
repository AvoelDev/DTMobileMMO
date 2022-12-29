package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.drive.DriveFile;

/* loaded from: classes3.dex */
final class zzbo extends zzam {
    private final /* synthetic */ int zzdv;
    private final /* synthetic */ DriveFile.DownloadProgressListener zzey;
    private final /* synthetic */ zzbn zzez;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzbo(zzbn zzbnVar, GoogleApiClient googleApiClient, int i, DriveFile.DownloadProgressListener downloadProgressListener) {
        super(googleApiClient);
        this.zzez = zzbnVar;
        this.zzdv = i;
        this.zzey = downloadProgressListener;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(zzaw zzawVar) throws RemoteException {
        setCancelToken(ICancelToken.Stub.asInterface(((zzeo) zzawVar.getService()).zza(new zzgj(this.zzez.getDriveId(), this.zzdv, 0), new zzgl(this, this.zzey)).zzgs));
    }
}
