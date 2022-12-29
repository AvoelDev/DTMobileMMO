package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.CreateFileActivityBuilder;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;
import com.google.android.gms.drive.query.Query;

@Deprecated
/* loaded from: classes3.dex */
public final class zzaf implements DriveApi {
    @Override // com.google.android.gms.drive.DriveApi
    public final PendingResult<DriveApi.MetadataBufferResult> query(GoogleApiClient googleApiClient, Query query) {
        if (query == null) {
            throw new IllegalArgumentException("Query must be provided.");
        }
        return googleApiClient.enqueue(new zzag(this, googleApiClient, query));
    }

    @Override // com.google.android.gms.drive.DriveApi
    public final PendingResult<DriveApi.DriveContentsResult> newDriveContents(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzah(this, googleApiClient, DriveFile.MODE_WRITE_ONLY));
    }

    @Override // com.google.android.gms.drive.DriveApi
    public final PendingResult<DriveApi.DriveIdResult> fetchDriveId(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.enqueue(new zzai(this, googleApiClient, str));
    }

    @Override // com.google.android.gms.drive.DriveApi
    public final DriveFolder getRootFolder(GoogleApiClient googleApiClient) {
        zzaw zzawVar = (zzaw) googleApiClient.getClient(Drive.CLIENT_KEY);
        if (!zzawVar.zzag()) {
            throw new IllegalStateException("Client is not yet connected");
        }
        DriveId zzae = zzawVar.zzae();
        if (zzae != null) {
            return new zzbs(zzae);
        }
        return null;
    }

    @Override // com.google.android.gms.drive.DriveApi
    public final DriveFolder getAppFolder(GoogleApiClient googleApiClient) {
        zzaw zzawVar = (zzaw) googleApiClient.getClient(Drive.CLIENT_KEY);
        if (!zzawVar.zzag()) {
            throw new IllegalStateException("Client is not yet connected");
        }
        DriveId zzaf = zzawVar.zzaf();
        if (zzaf != null) {
            return new zzbs(zzaf);
        }
        return null;
    }

    @Override // com.google.android.gms.drive.DriveApi
    public final PendingResult<Status> requestSync(GoogleApiClient googleApiClient) {
        return googleApiClient.execute(new zzaj(this, googleApiClient));
    }

    @Override // com.google.android.gms.drive.DriveApi
    public final OpenFileActivityBuilder newOpenFileActivityBuilder() {
        return new OpenFileActivityBuilder();
    }

    @Override // com.google.android.gms.drive.DriveApi
    public final CreateFileActivityBuilder newCreateFileActivityBuilder() {
        return new CreateFileActivityBuilder();
    }
}
