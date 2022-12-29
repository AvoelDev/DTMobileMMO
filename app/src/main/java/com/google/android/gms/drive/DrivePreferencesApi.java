package com.google.android.gms.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

@Deprecated
/* loaded from: classes3.dex */
public interface DrivePreferencesApi {

    @Deprecated
    /* loaded from: classes3.dex */
    public interface FileUploadPreferencesResult extends Result {
        FileUploadPreferences getFileUploadPreferences();
    }

    @Deprecated
    PendingResult<FileUploadPreferencesResult> getFileUploadPreferences(GoogleApiClient googleApiClient);

    @Deprecated
    PendingResult<Status> setFileUploadPreferences(GoogleApiClient googleApiClient, FileUploadPreferences fileUploadPreferences);
}
