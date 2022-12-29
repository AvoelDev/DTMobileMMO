package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class zzbk extends zzav {
    private final /* synthetic */ zzbi zzev;
    private final /* synthetic */ MetadataChangeSet zzew;
    private final /* synthetic */ com.google.android.gms.drive.zzn zzex;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzbk(zzbi zzbiVar, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, com.google.android.gms.drive.zzn zznVar) {
        super(googleApiClient);
        this.zzev = zzbiVar;
        this.zzew = metadataChangeSet;
        this.zzex = zznVar;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(zzaw zzawVar) throws RemoteException {
        Contents contents;
        Contents contents2;
        Contents contents3;
        zzaw zzawVar2 = zzawVar;
        this.zzew.zzq().zza(zzawVar2.getContext());
        contents = this.zzev.zzes;
        DriveId driveId = contents.getDriveId();
        MetadataBundle zzq = this.zzew.zzq();
        contents2 = this.zzev.zzes;
        int requestId = contents2.getRequestId();
        contents3 = this.zzev.zzes;
        ((zzeo) zzawVar2.getService()).zza(new zzm(driveId, zzq, requestId, contents3.zzb(), this.zzex), new zzgy(this));
    }
}
