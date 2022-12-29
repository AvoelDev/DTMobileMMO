package com.google.android.gms.drive;

import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzif;
import com.google.android.gms.internal.drive.zzin;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class Metadata implements Freezable<Metadata> {
    public static final int CONTENT_AVAILABLE_LOCALLY = 1;
    public static final int CONTENT_NOT_AVAILABLE_LOCALLY = 0;

    public abstract <T> T zza(MetadataField<T> metadataField);

    public String getAlternateLink() {
        return (String) zza(zzhs.zzjm);
    }

    public int getContentAvailability() {
        Integer num = (Integer) zza(zzin.zzlk);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public Date getCreatedDate() {
        return (Date) zza(zzif.zzld);
    }

    public Map<CustomPropertyKey, String> getCustomProperties() {
        AppVisibleCustomProperties appVisibleCustomProperties = (AppVisibleCustomProperties) zza(zzhs.zzjn);
        if (appVisibleCustomProperties == null) {
            return Collections.emptyMap();
        }
        return appVisibleCustomProperties.zzba();
    }

    public String getDescription() {
        return (String) zza(zzhs.zzjo);
    }

    public DriveId getDriveId() {
        return (DriveId) zza(zzhs.zzjl);
    }

    public String getEmbedLink() {
        return (String) zza(zzhs.zzjp);
    }

    public String getFileExtension() {
        return (String) zza(zzhs.zzjq);
    }

    public long getFileSize() {
        return ((Long) zza(zzhs.zzjr)).longValue();
    }

    public Date getLastViewedByMeDate() {
        return (Date) zza(zzif.zzle);
    }

    public String getMimeType() {
        return (String) zza(zzhs.zzki);
    }

    public Date getModifiedByMeDate() {
        return (Date) zza(zzif.zzlg);
    }

    public Date getModifiedDate() {
        return (Date) zza(zzif.zzlf);
    }

    public String getOriginalFilename() {
        return (String) zza(zzhs.zzkj);
    }

    public boolean isPinnable() {
        Boolean bool = (Boolean) zza(zzin.zzll);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isPinned() {
        Boolean bool = (Boolean) zza(zzhs.zzka);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public long getQuotaBytesUsed() {
        return ((Long) zza(zzhs.zzko)).longValue();
    }

    public Date getSharedWithMeDate() {
        return (Date) zza(zzif.zzlh);
    }

    public String getTitle() {
        return (String) zza(zzhs.zzkr);
    }

    public String getWebContentLink() {
        return (String) zza(zzhs.zzkt);
    }

    public String getWebViewLink() {
        return (String) zza(zzhs.zzku);
    }

    public boolean isInAppFolder() {
        Boolean bool = (Boolean) zza(zzhs.zzjv);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isEditable() {
        Boolean bool = (Boolean) zza(zzhs.zzjx);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isFolder() {
        return DriveFolder.MIME_TYPE.equals(getMimeType());
    }

    public boolean isRestricted() {
        Boolean bool = (Boolean) zza(zzhs.zzkc);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isShared() {
        Boolean bool = (Boolean) zza(zzhs.zzkd);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isStarred() {
        Boolean bool = (Boolean) zza(zzhs.zzkp);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isTrashed() {
        Boolean bool = (Boolean) zza(zzhs.zzks);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isTrashable() {
        Boolean bool = (Boolean) zza(zzhs.zzkg);
        return bool == null || bool.booleanValue();
    }

    public boolean isExplicitlyTrashed() {
        Boolean bool = (Boolean) zza(zzhs.zzjy);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public boolean isViewed() {
        Boolean bool = (Boolean) zza(zzhs.zzkh);
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }
}
