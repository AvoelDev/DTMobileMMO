package com.google.android.gms.drive.query;

import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.internal.drive.zzhs;
import com.google.android.gms.internal.drive.zzif;
import java.util.Date;

/* loaded from: classes3.dex */
public class SortableField {
    public static final SortableMetadataField<String> TITLE = zzhs.zzkr;
    public static final SortableMetadataField<Date> CREATED_DATE = zzif.zzld;
    public static final SortableMetadataField<Date> MODIFIED_DATE = zzif.zzlf;
    public static final SortableMetadataField<Date> MODIFIED_BY_ME_DATE = zzif.zzlg;
    public static final SortableMetadataField<Date> LAST_VIEWED_BY_ME = zzif.zzle;
    public static final SortableMetadataField<Date> SHARED_WITH_ME_DATE = zzif.zzlh;
    public static final SortableMetadataField<Long> QUOTA_USED = zzhs.zzko;
    private static final SortableMetadataField<Date> zzly = zzif.zzli;
}
