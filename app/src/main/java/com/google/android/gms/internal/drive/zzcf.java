package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;

/* loaded from: classes3.dex */
final class zzcf implements DrivePreferencesApi.FileUploadPreferencesResult {
    private final Status zzdy;
    private final FileUploadPreferences zzfm;

    private zzcf(zzcb zzcbVar, Status status, FileUploadPreferences fileUploadPreferences) {
        this.zzdy = status;
        this.zzfm = fileUploadPreferences;
    }

    @Override // com.google.android.gms.drive.DrivePreferencesApi.FileUploadPreferencesResult
    public final FileUploadPreferences getFileUploadPreferences() {
        return this.zzfm;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zzdy;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzcf(zzcb zzcbVar, Status status, FileUploadPreferences fileUploadPreferences, zzcc zzccVar) {
        this(zzcbVar, status, fileUploadPreferences);
    }
}
