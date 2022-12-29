package com.google.android.gms.drive;

import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.FilterHolder;
import java.util.List;

/* loaded from: classes3.dex */
public final class OpenFileActivityOptions {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    public final String zzba;
    public final String[] zzbb;
    public final DriveId zzbd;
    public final FilterHolder zzbe;

    private OpenFileActivityOptions(String str, String[] strArr, Filter filter, DriveId driveId) {
        this.zzba = str;
        this.zzbb = strArr;
        this.zzbe = filter == null ? null : new FilterHolder(filter);
        this.zzbd = driveId;
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private final OpenFileActivityBuilder zzbf = new OpenFileActivityBuilder();

        public Builder setActivityTitle(String str) {
            this.zzbf.setActivityTitle(str);
            return this;
        }

        public Builder setMimeType(List<String> list) {
            this.zzbf.setMimeType((String[]) list.toArray(new String[0]));
            return this;
        }

        public Builder setSelectionFilter(Filter filter) {
            this.zzbf.setSelectionFilter(filter);
            return this;
        }

        public Builder setActivityStartFolder(DriveId driveId) {
            this.zzbf.setActivityStartFolder(driveId);
            return this;
        }

        public OpenFileActivityOptions build() {
            this.zzbf.zzg();
            return new OpenFileActivityOptions(this.zzbf.getTitle(), this.zzbf.zzs(), this.zzbf.zzt(), this.zzbf.zzu());
        }
    }
}
