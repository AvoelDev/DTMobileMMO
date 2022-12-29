package com.google.android.gms.drive;

import com.google.android.gms.drive.metadata.internal.MetadataBundle;

/* loaded from: classes3.dex */
public final class CreateFileActivityOptions extends com.google.android.gms.internal.drive.zzq {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";

    private CreateFileActivityOptions(MetadataBundle metadataBundle, Integer num, String str, DriveId driveId, int i) {
        super(metadataBundle, num, str, driveId, i);
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        protected final CreateFileActivityBuilder builder = new CreateFileActivityBuilder();

        public Builder setInitialMetadata(MetadataChangeSet metadataChangeSet) {
            this.builder.setInitialMetadata(metadataChangeSet);
            return this;
        }

        public Builder setActivityStartFolder(DriveId driveId) {
            this.builder.setActivityStartFolder(driveId);
            return this;
        }

        public Builder setActivityTitle(String str) {
            this.builder.setActivityTitle(str);
            return this;
        }

        public Builder setInitialDriveContents(DriveContents driveContents) {
            this.builder.setInitialDriveContents(driveContents);
            return this;
        }

        public CreateFileActivityOptions build() {
            this.builder.zzg();
            return new CreateFileActivityOptions(this.builder.zzc().zzq(), Integer.valueOf(this.builder.getRequestId()), this.builder.zze(), this.builder.zzd(), this.builder.zzf());
        }
    }
}
