package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.util.GmsVersion;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.MetadataField;
import java.util.Collections;

/* loaded from: classes3.dex */
public final class zzhs {
    public static final MetadataField<DriveId> zzjl = zzim.zzlj;
    public static final MetadataField<String> zzjm = new com.google.android.gms.drive.metadata.internal.zzt("alternateLink", GmsVersion.VERSION_JARLSBERG);
    public static final zzhv zzjn = new zzhv(GmsVersion.VERSION_LONGHORN);
    public static final MetadataField<String> zzjo = new com.google.android.gms.drive.metadata.internal.zzt("description", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<String> zzjp = new com.google.android.gms.drive.metadata.internal.zzt("embedLink", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<String> zzjq = new com.google.android.gms.drive.metadata.internal.zzt("fileExtension", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<Long> zzjr = new com.google.android.gms.drive.metadata.internal.zzi("fileSize", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<String> zzjs = new com.google.android.gms.drive.metadata.internal.zzt("folderColorRgb", GmsVersion.VERSION_QUESO);
    public static final MetadataField<Boolean> zzjt = new com.google.android.gms.drive.metadata.internal.zzb("hasThumbnail", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<String> zzju = new com.google.android.gms.drive.metadata.internal.zzt("indexableText", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<Boolean> zzjv = new com.google.android.gms.drive.metadata.internal.zzb("isAppData", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<Boolean> zzjw = new com.google.android.gms.drive.metadata.internal.zzb("isCopyable", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<Boolean> zzjx = new com.google.android.gms.drive.metadata.internal.zzb("isEditable", GmsVersion.VERSION_HALLOUMI);
    public static final MetadataField<Boolean> zzjy = new zzht("isExplicitlyTrashed", Collections.singleton("trashed"), Collections.emptySet(), GmsVersion.VERSION_ORLA);
    public static final MetadataField<Boolean> zzjz = new com.google.android.gms.drive.metadata.internal.zzb("isLocalContentUpToDate", GmsVersion.VERSION_REBLOCHON);
    public static final zzhw zzka = new zzhw("isPinned", GmsVersion.VERSION_HALLOUMI);
    public static final MetadataField<Boolean> zzkb = new com.google.android.gms.drive.metadata.internal.zzb("isOpenable", GmsVersion.VERSION_PARMESAN);
    public static final MetadataField<Boolean> zzkc = new com.google.android.gms.drive.metadata.internal.zzb("isRestricted", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<Boolean> zzkd = new com.google.android.gms.drive.metadata.internal.zzb("isShared", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<Boolean> zzke = new com.google.android.gms.drive.metadata.internal.zzb("isGooglePhotosFolder", GmsVersion.VERSION_ORLA);
    public static final MetadataField<Boolean> zzkf = new com.google.android.gms.drive.metadata.internal.zzb("isGooglePhotosRootFolder", GmsVersion.VERSION_ORLA);
    public static final MetadataField<Boolean> zzkg = new com.google.android.gms.drive.metadata.internal.zzb("isTrashable", GmsVersion.VERSION_KENAFA);
    public static final MetadataField<Boolean> zzkh = new com.google.android.gms.drive.metadata.internal.zzb("isViewed", GmsVersion.VERSION_JARLSBERG);
    public static final zzhx zzki = new zzhx(GmsVersion.VERSION_HALLOUMI);
    public static final MetadataField<String> zzkj = new com.google.android.gms.drive.metadata.internal.zzt("originalFilename", GmsVersion.VERSION_JARLSBERG);
    public static final com.google.android.gms.drive.metadata.zzb<String> zzkk = new com.google.android.gms.drive.metadata.internal.zzs("ownerNames", GmsVersion.VERSION_JARLSBERG);
    public static final com.google.android.gms.drive.metadata.internal.zzu zzkl = new com.google.android.gms.drive.metadata.internal.zzu("lastModifyingUser", GmsVersion.VERSION_MANCHEGO);
    public static final com.google.android.gms.drive.metadata.internal.zzu zzkm = new com.google.android.gms.drive.metadata.internal.zzu("sharingUser", GmsVersion.VERSION_MANCHEGO);
    public static final com.google.android.gms.drive.metadata.internal.zzo zzkn = new com.google.android.gms.drive.metadata.internal.zzo(GmsVersion.VERSION_HALLOUMI);
    public static final zzhy zzko = new zzhy("quotaBytesUsed", GmsVersion.VERSION_JARLSBERG);
    public static final zzia zzkp = new zzia("starred", GmsVersion.VERSION_HALLOUMI);
    public static final MetadataField<BitmapTeleporter> zzkq = new zzhu("thumbnail", Collections.emptySet(), Collections.emptySet(), GmsVersion.VERSION_KENAFA);
    public static final zzib zzkr = new zzib("title", GmsVersion.VERSION_HALLOUMI);
    public static final zzic zzks = new zzic("trashed", GmsVersion.VERSION_HALLOUMI);
    public static final MetadataField<String> zzkt = new com.google.android.gms.drive.metadata.internal.zzt("webContentLink", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<String> zzku = new com.google.android.gms.drive.metadata.internal.zzt("webViewLink", GmsVersion.VERSION_JARLSBERG);
    public static final MetadataField<String> zzkv = new com.google.android.gms.drive.metadata.internal.zzt("uniqueIdentifier", GmsVersion.VERSION_LONGHORN);
    public static final com.google.android.gms.drive.metadata.internal.zzb zzkw = new com.google.android.gms.drive.metadata.internal.zzb("writersCanShare", GmsVersion.VERSION_MANCHEGO);
    public static final MetadataField<String> zzkx = new com.google.android.gms.drive.metadata.internal.zzt("role", GmsVersion.VERSION_MANCHEGO);
    public static final MetadataField<String> zzky = new com.google.android.gms.drive.metadata.internal.zzt("md5Checksum", GmsVersion.VERSION_ORLA);
    public static final zzhz zzkz = new zzhz(GmsVersion.VERSION_ORLA);
    public static final MetadataField<String> zzla = new com.google.android.gms.drive.metadata.internal.zzt("recencyReason", GmsVersion.VERSION_SAGA);
    public static final MetadataField<Boolean> zzlb = new com.google.android.gms.drive.metadata.internal.zzb("subscribed", GmsVersion.VERSION_SAGA);
}
