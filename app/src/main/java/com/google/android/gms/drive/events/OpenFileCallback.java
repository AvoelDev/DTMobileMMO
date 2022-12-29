package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveContents;

/* loaded from: classes3.dex */
public abstract class OpenFileCallback {
    public abstract void onContents(DriveContents driveContents);

    public abstract void onError(Exception exc);

    public abstract void onProgress(long j, long j2);
}
