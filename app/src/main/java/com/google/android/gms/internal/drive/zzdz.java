package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;

/* loaded from: classes3.dex */
final class zzdz implements DriveResource.MetadataResult {
    private final Status zzdy;
    private final Metadata zzgr;

    public zzdz(Status status, Metadata metadata) {
        this.zzdy = status;
        this.zzgr = metadata;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zzdy;
    }

    @Override // com.google.android.gms.drive.DriveResource.MetadataResult
    public final Metadata getMetadata() {
        return this.zzgr;
    }
}
