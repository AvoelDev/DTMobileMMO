package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

/* loaded from: classes3.dex */
public final class zzbs extends zzdp implements DriveFolder {
    public zzbs(DriveId driveId) {
        super(driveId);
    }

    @Override // com.google.android.gms.drive.DriveFolder
    public final PendingResult<DriveApi.MetadataBufferResult> listChildren(GoogleApiClient googleApiClient) {
        return queryChildren(googleApiClient, null);
    }

    @Override // com.google.android.gms.drive.DriveFolder
    public final PendingResult<DriveApi.MetadataBufferResult> queryChildren(GoogleApiClient googleApiClient, Query query) {
        return new zzaf().query(googleApiClient, zza(query, getDriveId()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Query zza(Query query, DriveId driveId) {
        Query.Builder addFilter = new Query.Builder().addFilter(Filters.in(SearchableField.PARENTS, driveId));
        if (query != null) {
            if (query.getFilter() != null) {
                addFilter.addFilter(query.getFilter());
            }
            addFilter.setPageToken(query.getPageToken());
            addFilter.setSortOrder(query.getSortOrder());
        }
        return addFilter.build();
    }

    @Override // com.google.android.gms.drive.DriveFolder
    public final PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, DriveContents driveContents) {
        return createFile(googleApiClient, metadataChangeSet, driveContents, null);
    }

    @Override // com.google.android.gms.drive.DriveFolder
    public final PendingResult<DriveFolder.DriveFileResult> createFile(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, DriveContents driveContents, ExecutionOptions executionOptions) {
        if (executionOptions == null) {
            executionOptions = new ExecutionOptions.Builder().build();
        }
        ExecutionOptions executionOptions2 = executionOptions;
        if (executionOptions2.zzn() == 0) {
            if (metadataChangeSet == null) {
                throw new IllegalArgumentException("MetadataChangeSet must be provided.");
            }
            com.google.android.gms.drive.metadata.internal.zzk zzg = com.google.android.gms.drive.metadata.internal.zzk.zzg(metadataChangeSet.getMimeType());
            if (zzg == null || !zzg.isFolder()) {
                if (executionOptions2 == null) {
                    throw new IllegalArgumentException("ExecutionOptions must be provided");
                }
                executionOptions2.zza(googleApiClient);
                if (driveContents != null) {
                    if (!(driveContents instanceof zzbi)) {
                        throw new IllegalArgumentException("Only DriveContents obtained from the Drive API are accepted.");
                    }
                    if (driveContents.getDriveId() != null) {
                        throw new IllegalArgumentException("Only DriveContents obtained through DriveApi.newDriveContents are accepted for file creation.");
                    }
                    if (driveContents.zzk()) {
                        throw new IllegalArgumentException("DriveContents are already closed.");
                    }
                }
                zzb(metadataChangeSet);
                int zza = zza(driveContents, com.google.android.gms.drive.metadata.internal.zzk.zzg(metadataChangeSet.getMimeType()));
                com.google.android.gms.drive.metadata.internal.zzk zzg2 = com.google.android.gms.drive.metadata.internal.zzk.zzg(metadataChangeSet.getMimeType());
                return googleApiClient.execute(new zzbt(this, googleApiClient, metadataChangeSet, zza, (zzg2 == null || !zzg2.zzbh()) ? 0 : 1, executionOptions2));
            }
            throw new IllegalArgumentException("May not create folders using this method. Use DriveFolder.createFolder() instead of mime type application/vnd.google-apps.folder");
        }
        throw new IllegalStateException("May not set a conflict strategy for new file creation.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void zzb(MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("MetadataChangeSet must be provided.");
        }
        com.google.android.gms.drive.metadata.internal.zzk zzg = com.google.android.gms.drive.metadata.internal.zzk.zzg(metadataChangeSet.getMimeType());
        if (zzg != null) {
            if (!((zzg.zzbh() || zzg.isFolder()) ? false : true)) {
                throw new IllegalArgumentException("May not create shortcut files using this method. Use DriveFolder.createShortcutFile() instead.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zza(DriveContents driveContents, com.google.android.gms.drive.metadata.internal.zzk zzkVar) {
        if (driveContents == null) {
            return (zzkVar == null || !zzkVar.zzbh()) ? 1 : 0;
        }
        int requestId = driveContents.zzi().getRequestId();
        driveContents.zzj();
        return requestId;
    }

    @Override // com.google.android.gms.drive.DriveFolder
    public final PendingResult<DriveFolder.DriveFolderResult> createFolder(GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        if (metadataChangeSet == null) {
            throw new IllegalArgumentException("MetadataChangeSet must be provided.");
        }
        if (metadataChangeSet.getMimeType() != null && !metadataChangeSet.getMimeType().equals(DriveFolder.MIME_TYPE)) {
            throw new IllegalArgumentException("The mimetype must be of type application/vnd.google-apps.folder");
        }
        return googleApiClient.execute(new zzbu(this, googleApiClient, metadataChangeSet));
    }
}
