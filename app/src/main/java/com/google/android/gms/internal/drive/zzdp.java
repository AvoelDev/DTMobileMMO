package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeListener;
import java.util.ArrayList;
import java.util.Set;

/* loaded from: classes3.dex */
public class zzdp implements DriveResource {
    protected final DriveId zzk;

    public zzdp(DriveId driveId) {
        this.zzk = driveId;
    }

    @Override // com.google.android.gms.drive.DriveResource
    public DriveId getDriveId() {
        return this.zzk;
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<DriveResource.MetadataResult> getMetadata(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzdq(this, googleApiClient, false));
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<DriveApi.MetadataBufferResult> listParents(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzdr(this, googleApiClient));
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> setParents(GoogleApiClient googleApiClient, Set<DriveId> set) {
        if (set == null) {
            throw new IllegalArgumentException("ParentIds must be provided.");
        }
        return googleApiClient.execute(new zzds(this, googleApiClient, new ArrayList(set)));
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<DriveResource.MetadataResult> updateMetadata(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("ChangeSet must be provided.");
        }
        return googleApiClient.execute(new zzdt(this, googleApiClient, metadataChangeSet));
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> delete(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzdu(this, googleApiClient));
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> addChangeListener(GoogleApiClient googleApiClient, ChangeListener changeListener) {
        return ((zzaw) googleApiClient.getClient(Drive.CLIENT_KEY)).zza(googleApiClient, this.zzk, changeListener);
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> removeChangeListener(GoogleApiClient googleApiClient, ChangeListener changeListener) {
        return ((zzaw) googleApiClient.getClient(Drive.CLIENT_KEY)).zzb(googleApiClient, this.zzk, changeListener);
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> addChangeSubscription(GoogleApiClient googleApiClient) {
        zzaw zzawVar = (zzaw) googleApiClient.getClient(Drive.CLIENT_KEY);
        zzj zzjVar = new zzj(1, this.zzk);
        Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(zzjVar.zzda, zzjVar.zzk));
        Preconditions.checkState(zzawVar.isConnected(), "Client must be connected");
        if (!zzawVar.zzec) {
            throw new IllegalStateException("Application must define an exported DriveEventService subclass in AndroidManifest.xml to add event subscriptions");
        }
        return googleApiClient.execute(new zzaz(zzawVar, googleApiClient, zzjVar));
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> removeChangeSubscription(GoogleApiClient googleApiClient) {
        zzaw zzawVar = (zzaw) googleApiClient.getClient(Drive.CLIENT_KEY);
        DriveId driveId = this.zzk;
        Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(1, driveId));
        Preconditions.checkState(zzawVar.isConnected(), "Client must be connected");
        return googleApiClient.execute(new zzba(zzawVar, googleApiClient, driveId, 1));
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> trash(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzdv(this, googleApiClient));
    }

    @Override // com.google.android.gms.drive.DriveResource
    public PendingResult<Status> untrash(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzdw(this, googleApiClient));
    }
}
