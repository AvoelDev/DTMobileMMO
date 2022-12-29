package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveFile;

/* loaded from: classes3.dex */
final class zzah extends zzam {
    private final /* synthetic */ int zzdv = DriveFile.MODE_WRITE_ONLY;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzah(zzaf zzafVar, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient);
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(zzaw zzawVar) throws RemoteException {
        ((zzeo) zzawVar.getService()).zza(new zzr(this.zzdv), new zzak(this));
    }
}
