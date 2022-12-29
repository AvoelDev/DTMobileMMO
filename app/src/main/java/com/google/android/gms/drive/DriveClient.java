package com.google.android.gms.drive;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.tasks.Task;

@Deprecated
/* loaded from: classes3.dex */
public abstract class DriveClient extends GoogleApi<Drive.zza> {
    public DriveClient(Context context, Drive.zza zzaVar) {
        super(context, Drive.zzw, zzaVar, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @Deprecated
    public abstract Task<DriveId> getDriveId(String str);

    @Deprecated
    public abstract Task<TransferPreferences> getUploadPreferences();

    @Deprecated
    public abstract Task<IntentSender> newCreateFileActivityIntentSender(CreateFileActivityOptions createFileActivityOptions);

    @Deprecated
    public abstract Task<IntentSender> newOpenFileActivityIntentSender(OpenFileActivityOptions openFileActivityOptions);

    @Deprecated
    public abstract Task<Void> requestSync();

    @Deprecated
    public abstract Task<Void> setUploadPreferences(TransferPreferences transferPreferences);

    public DriveClient(Activity activity, Drive.zza zzaVar) {
        super(activity, Drive.zzw, zzaVar, GoogleApi.Settings.DEFAULT_SETTINGS);
    }
}
