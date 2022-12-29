package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes3.dex */
public abstract class zza<T> implements MetadataField<T> {
    private final String fieldName;
    private final Set<String> zziw;
    private final Set<String> zzix;
    private final int zziy;

    /* JADX INFO: Access modifiers changed from: protected */
    public zza(String str, int i) {
        this.fieldName = (String) Preconditions.checkNotNull(str, "fieldName");
        this.zziw = Collections.singleton(str);
        this.zzix = Collections.emptySet();
        this.zziy = i;
    }

    protected abstract void zza(Bundle bundle, T t);

    protected abstract T zzb(Bundle bundle);

    protected abstract T zzc(DataHolder dataHolder, int i, int i2);

    /* JADX INFO: Access modifiers changed from: protected */
    public zza(String str, Collection<String> collection, Collection<String> collection2, int i) {
        this.fieldName = (String) Preconditions.checkNotNull(str, "fieldName");
        this.zziw = Collections.unmodifiableSet(new HashSet(collection));
        this.zzix = Collections.unmodifiableSet(new HashSet(collection2));
        this.zziy = i;
    }

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final String getName() {
        return this.fieldName;
    }

    public final Collection<String> zzaz() {
        return this.zziw;
    }

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final T zza(Bundle bundle) {
        Preconditions.checkNotNull(bundle, "bundle");
        if (bundle.get(this.fieldName) != null) {
            return zzb(bundle);
        }
        return null;
    }

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final void zza(T t, Bundle bundle) {
        Preconditions.checkNotNull(bundle, "bundle");
        if (t == null) {
            bundle.putString(this.fieldName, null);
        } else {
            zza(bundle, (Bundle) t);
        }
    }

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final void zza(DataHolder dataHolder, MetadataBundle metadataBundle, int i, int i2) {
        Preconditions.checkNotNull(dataHolder, "dataHolder");
        Preconditions.checkNotNull(metadataBundle, "bundle");
        if (zzb(dataHolder, i, i2)) {
            metadataBundle.zzb(this, zzc(dataHolder, i, i2));
        }
    }

    public String toString() {
        return this.fieldName;
    }

    @Override // com.google.android.gms.drive.metadata.MetadataField
    public final T zza(DataHolder dataHolder, int i, int i2) {
        if (zzb(dataHolder, i, i2)) {
            return zzc(dataHolder, i, i2);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x000c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean zzb(com.google.android.gms.common.data.DataHolder r4, int r5, int r6) {
        /*
            r3 = this;
            java.util.Set<java.lang.String> r0 = r3.zziw
            java.util.Iterator r0 = r0.iterator()
        L6:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L26
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = r4.isClosed()
            if (r2 != 0) goto L24
            boolean r2 = r4.hasColumn(r1)
            if (r2 == 0) goto L24
            boolean r1 = r4.hasNull(r1, r5, r6)
            if (r1 == 0) goto L6
        L24:
            r4 = 0
            return r4
        L26:
            r4 = 1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.drive.metadata.zza.zzb(com.google.android.gms.common.data.DataHolder, int, int):boolean");
    }
}
