package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.DriveFile;

/* loaded from: classes3.dex */
final class zzbp implements DriveFile.DownloadProgressListener {
    private final ListenerHolder<DriveFile.DownloadProgressListener> zzfa;

    public zzbp(ListenerHolder<DriveFile.DownloadProgressListener> listenerHolder) {
        this.zzfa = listenerHolder;
    }

    @Override // com.google.android.gms.drive.DriveFile.DownloadProgressListener
    public final void onProgress(long j, long j2) {
        this.zzfa.notifyListener(new zzbq(this, j, j2));
    }
}
