package com.google.android.gms.drive.query;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SearchableOrderedMetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzif;
import java.util.Date;

/* loaded from: classes3.dex */
public class SearchableField {
    public static final SearchableMetadataField<String> TITLE = zzhs.zzkr;
    public static final SearchableMetadataField<String> MIME_TYPE = zzhs.zzki;
    public static final SearchableMetadataField<Boolean> TRASHED = zzhs.zzks;
    public static final SearchableCollectionMetadataField<DriveId> PARENTS = zzhs.zzkn;
    public static final SearchableOrderedMetadataField<Date> zzlu = zzif.zzlh;
    public static final SearchableMetadataField<Boolean> STARRED = zzhs.zzkp;
    public static final SearchableOrderedMetadataField<Date> MODIFIED_DATE = zzif.zzlf;
    public static final SearchableOrderedMetadataField<Date> LAST_VIEWED_BY_ME = zzif.zzle;
    public static final SearchableMetadataField<Boolean> IS_PINNED = zzhs.zzka;
    public static final SearchableMetadataField<AppVisibleCustomProperties> zzlv = zzhs.zzjn;
}
