package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Set;

/* loaded from: classes3.dex */
final class zzi {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static MetadataField<?> zza(MetadataBundle metadataBundle) {
        Set<MetadataField<?>> zzbg = metadataBundle.zzbg();
        if (zzbg.size() != 1) {
            throw new IllegalArgumentException("bundle should have exactly 1 populated field");
        }
        return zzbg.iterator().next();
    }
}
