package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.GmsVersion;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class zzim extends com.google.android.gms.drive.metadata.internal.zzm<DriveId> {
    public static final zzim zzlj = new zzim();

    private zzim() {
        super("driveId", Arrays.asList("sqlId", "resourceId", "mimeType"), Arrays.asList("dbInstanceId"), GmsVersion.VERSION_HALLOUMI);
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final boolean zzb(DataHolder dataHolder, int i, int i2) {
        for (String str : zzaz()) {
            if (!dataHolder.hasColumn(str)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
        long j = dataHolder.getMetadata().getLong("dbInstanceId");
        boolean equals = DriveFolder.MIME_TYPE.equals(dataHolder.getString(zzhs.zzki.getName(), i, i2));
        String string = dataHolder.getString("resourceId", i, i2);
        return new DriveId("generated-android-null".equals(string) ? null : string, Long.valueOf(dataHolder.getLong("sqlId", i, i2)).longValue(), j, equals ? 1 : 0);
    }
}
