package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.query.Query;

@Deprecated
/* loaded from: classes3.dex */
public interface DriveApi {

    @Deprecated
    /* loaded from: classes3.dex */
    public interface DriveContentsResult extends Result {
        DriveContents getDriveContents();
    }

    @Deprecated
    /* loaded from: classes3.dex */
    public interface DriveIdResult extends Result {
        DriveId getDriveId();
    }

    @Deprecated
    /* loaded from: classes3.dex */
    public interface MetadataBufferResult extends Releasable, Result {
        MetadataBuffer getMetadataBuffer();
    }

    @Deprecated
    PendingResult<DriveIdResult> fetchDriveId(GoogleApiClient googleApiClient, String str);

    @Deprecated
    DriveFolder getAppFolder(GoogleApiClient googleApiClient);

    @Deprecated
    DriveFolder getRootFolder(GoogleApiClient googleApiClient);

    @Deprecated
    CreateFileActivityBuilder newCreateFileActivityBuilder();

    @Deprecated
    PendingResult<DriveContentsResult> newDriveContents(GoogleApiClient googleApiClient);

    @Deprecated
    OpenFileActivityBuilder newOpenFileActivityBuilder();

    @Deprecated
    PendingResult<MetadataBufferResult> query(GoogleApiClient googleApiClient, Query query);

    @Deprecated
    PendingResult<Status> requestSync(GoogleApiClient googleApiClient);
}
