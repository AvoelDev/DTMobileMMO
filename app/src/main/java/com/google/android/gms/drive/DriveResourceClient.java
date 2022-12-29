package com.google.android.gms.drive;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.events.ListenerToken;
import com.google.android.gms.drive.events.OnChangeListener;
import com.google.android.gms.drive.events.OpenFileCallback;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.tasks.Task;
import java.util.Set;

@Deprecated
/* loaded from: classes3.dex */
public abstract class DriveResourceClient extends GoogleApi<Drive.zza> {
    public DriveResourceClient(Context context, Drive.zza zzaVar) {
        super(context, Drive.zzw, zzaVar, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @Deprecated
    public abstract Task<ListenerToken> addChangeListener(DriveResource driveResource, OnChangeListener onChangeListener);

    @Deprecated
    public abstract Task<Void> addChangeSubscription(DriveResource driveResource);

    @Deprecated
    public abstract Task<Boolean> cancelOpenFileCallback(ListenerToken listenerToken);

    @Deprecated
    public abstract Task<Void> commitContents(DriveContents driveContents, MetadataChangeSet metadataChangeSet);

    @Deprecated
    public abstract Task<Void> commitContents(DriveContents driveContents, MetadataChangeSet metadataChangeSet, ExecutionOptions executionOptions);

    @Deprecated
    public abstract Task<DriveContents> createContents();

    @Deprecated
    public abstract Task<DriveFile> createFile(DriveFolder driveFolder, MetadataChangeSet metadataChangeSet, DriveContents driveContents);

    @Deprecated
    public abstract Task<DriveFile> createFile(DriveFolder driveFolder, MetadataChangeSet metadataChangeSet, DriveContents driveContents, ExecutionOptions executionOptions);

    @Deprecated
    public abstract Task<DriveFolder> createFolder(DriveFolder driveFolder, MetadataChangeSet metadataChangeSet);

    @Deprecated
    public abstract Task<Void> delete(DriveResource driveResource);

    @Deprecated
    public abstract Task<Void> discardContents(DriveContents driveContents);

    @Deprecated
    public abstract Task<DriveFolder> getAppFolder();

    @Deprecated
    public abstract Task<Metadata> getMetadata(DriveResource driveResource);

    @Deprecated
    public abstract Task<DriveFolder> getRootFolder();

    @Deprecated
    public abstract Task<MetadataBuffer> listChildren(DriveFolder driveFolder);

    @Deprecated
    public abstract Task<MetadataBuffer> listParents(DriveResource driveResource);

    @Deprecated
    public abstract Task<DriveContents> openFile(DriveFile driveFile, int i);

    @Deprecated
    public abstract Task<ListenerToken> openFile(DriveFile driveFile, int i, OpenFileCallback openFileCallback);

    @Deprecated
    public abstract Task<MetadataBuffer> query(Query query);

    @Deprecated
    public abstract Task<MetadataBuffer> queryChildren(DriveFolder driveFolder, Query query);

    @Deprecated
    public abstract Task<Boolean> removeChangeListener(ListenerToken listenerToken);

    @Deprecated
    public abstract Task<Void> removeChangeSubscription(DriveResource driveResource);

    @Deprecated
    public abstract Task<DriveContents> reopenContentsForWrite(DriveContents driveContents);

    @Deprecated
    public abstract Task<Void> setParents(DriveResource driveResource, Set<DriveId> set);

    @Deprecated
    public abstract Task<Void> trash(DriveResource driveResource);

    @Deprecated
    public abstract Task<Void> untrash(DriveResource driveResource);

    @Deprecated
    public abstract Task<Metadata> updateMetadata(DriveResource driveResource, MetadataChangeSet metadataChangeSet);

    public DriveResourceClient(Activity activity, Drive.zza zzaVar) {
        super(activity, Drive.zzw, zzaVar, GoogleApi.Settings.DEFAULT_SETTINGS);
    }
}
