package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;

/* loaded from: classes3.dex */
final class zzbj extends zzam {
    private final /* synthetic */ zzbi zzev;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzbj(zzbi zzbiVar, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzev = zzbiVar;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(zzaw zzawVar) throws RemoteException {
        Contents contents;
        DriveId driveId = this.zzev.getDriveId();
        contents = this.zzev.zzes;
        ((zzeo) zzawVar.getService()).zza(new zzgj(driveId, DriveFile.MODE_WRITE_ONLY, contents.getRequestId()), new zzgl(this, null));
    }
}
