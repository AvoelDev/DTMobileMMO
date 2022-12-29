package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveFolder;

/* loaded from: classes3.dex */
final class zzbz implements DriveFolder.DriveFolderResult {
    private final Status zzdy;
    private final DriveFolder zzfj;

    public zzbz(Status status, DriveFolder driveFolder) {
        this.zzdy = status;
        this.zzfj = driveFolder;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zzdy;
    }

    @Override // com.google.android.gms.drive.DriveFolder.DriveFolderResult
    public final DriveFolder getDriveFolder() {
        return this.zzfj;
    }
}
