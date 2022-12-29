package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataBuffer;

/* loaded from: classes3.dex */
public final class zzaq implements DriveApi.MetadataBufferResult {
    private final Status zzdy;
    private final MetadataBuffer zzdz;
    private final boolean zzea;

    public zzaq(Status status, MetadataBuffer metadataBuffer, boolean z) {
        this.zzdy = status;
        this.zzdz = metadataBuffer;
        this.zzea = z;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zzdy;
    }

    @Override // com.google.android.gms.drive.DriveApi.MetadataBufferResult
    public final MetadataBuffer getMetadataBuffer() {
        return this.zzdz;
    }

    @Override // com.google.android.gms.common.api.Releasable
    public final void release() {
        MetadataBuffer metadataBuffer = this.zzdz;
        if (metadataBuffer != null) {
            metadataBuffer.release();
        }
    }
}
