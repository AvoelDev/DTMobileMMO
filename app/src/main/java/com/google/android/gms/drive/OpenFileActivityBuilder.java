package com.google.android.gms.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzgm;

@Deprecated
/* loaded from: classes3.dex */
public class OpenFileActivityBuilder {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    private String zzba;
    private String[] zzbb;
    private Filter zzbc;
    private DriveId zzbd;

    public OpenFileActivityBuilder setActivityTitle(String str) {
        this.zzba = (String) Preconditions.checkNotNull(str);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String getTitle() {
        return this.zzba;
    }

    public OpenFileActivityBuilder setMimeType(String[] strArr) {
        Preconditions.checkArgument(strArr != null, "mimeTypes may not be null");
        this.zzbb = strArr;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String[] zzs() {
        return this.zzbb;
    }

    public OpenFileActivityBuilder setSelectionFilter(Filter filter) {
        Preconditions.checkArgument(filter != null, "filter may not be null");
        Preconditions.checkArgument(true ^ com.google.android.gms.drive.query.internal.zzk.zza(filter), "FullTextSearchFilter cannot be used as a selection filter");
        this.zzbc = filter;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Filter zzt() {
        return this.zzbc;
    }

    public OpenFileActivityBuilder setActivityStartFolder(DriveId driveId) {
        this.zzbd = (DriveId) Preconditions.checkNotNull(driveId);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final DriveId zzu() {
        return this.zzbd;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzg() {
        if (this.zzbb == null) {
            this.zzbb = new String[0];
        }
        if (this.zzbb.length > 0 && this.zzbc != null) {
            throw new IllegalStateException("Cannot use a selection filter and set mimetypes simultaneously");
        }
    }

    public IntentSender build(GoogleApiClient googleApiClient) {
        Preconditions.checkState(googleApiClient.isConnected(), "Client must be connected");
        zzg();
        Filter filter = this.zzbc;
        try {
            return ((zzeo) ((zzaw) googleApiClient.getClient(Drive.CLIENT_KEY)).getService()).zza(new zzgm(this.zzba, this.zzbb, this.zzbd, filter == null ? null : new FilterHolder(filter)));
        } catch (RemoteException e) {
            throw new RuntimeException("Unable to connect Drive Play Service", e);
        }
    }
}
