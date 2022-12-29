package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.GmsVersion;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes3.dex */
public final class zzo extends zzl<DriveId> implements SearchableCollectionMetadataField<DriveId> {
    public static final zzg zzjk = new zzp();

    public zzo(int i) {
        super("parents", Collections.emptySet(), Arrays.asList("parentsExtra", "dbInstanceId", "parentsExtraHolder"), GmsVersion.VERSION_HALLOUMI);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.internal.zzl
    public final Collection<DriveId> zzc(Bundle bundle) {
        Collection zzb = super.zzb(bundle);
        if (zzb == null) {
            return null;
        }
        return new HashSet(zzb);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.drive.metadata.zzb
    public final Collection<DriveId> zzd(DataHolder dataHolder, int i, int i2) {
        Bundle metadata = dataHolder.getMetadata();
        ArrayList parcelableArrayList = metadata.getParcelableArrayList("parentsExtra");
        if (parcelableArrayList == null) {
            if (metadata.getParcelable("parentsExtraHolder") != null) {
                synchronized (dataHolder) {
                    DataHolder dataHolder2 = (DataHolder) dataHolder.getMetadata().getParcelable("parentsExtraHolder");
                    if (dataHolder2 != null) {
                        int count = dataHolder.getCount();
                        ArrayList<? extends Parcelable> arrayList = new ArrayList<>(count);
                        HashMap hashMap = new HashMap(count);
                        for (int i3 = 0; i3 < count; i3++) {
                            int windowIndex = dataHolder.getWindowIndex(i3);
                            ParentDriveIdSet parentDriveIdSet = new ParentDriveIdSet();
                            arrayList.add(parentDriveIdSet);
                            hashMap.put(Long.valueOf(dataHolder.getLong("sqlId", i3, windowIndex)), parentDriveIdSet);
                        }
                        Bundle metadata2 = dataHolder2.getMetadata();
                        String string = metadata2.getString("childSqlIdColumn");
                        String string2 = metadata2.getString("parentSqlIdColumn");
                        String string3 = metadata2.getString("parentResIdColumn");
                        int count2 = dataHolder2.getCount();
                        for (int i4 = 0; i4 < count2; i4++) {
                            int windowIndex2 = dataHolder2.getWindowIndex(i4);
                            ((ParentDriveIdSet) hashMap.get(Long.valueOf(dataHolder2.getLong(string, i4, windowIndex2)))).zzjj.add(new zzq(dataHolder2.getString(string3, i4, windowIndex2), dataHolder2.getLong(string2, i4, windowIndex2), 1));
                        }
                        dataHolder.getMetadata().putParcelableArrayList("parentsExtra", arrayList);
                        dataHolder2.close();
                        dataHolder.getMetadata().remove("parentsExtraHolder");
                    }
                }
                parcelableArrayList = metadata.getParcelableArrayList("parentsExtra");
            }
            if (parcelableArrayList == null) {
                return null;
            }
        }
        long j = metadata.getLong("dbInstanceId");
        HashSet hashSet = new HashSet();
        for (zzq zzqVar : ((ParentDriveIdSet) parcelableArrayList.get(i)).zzjj) {
            hashSet.add(new DriveId(zzqVar.zzad, zzqVar.zzae, j, zzqVar.zzaf));
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzc(DataHolder dataHolder) {
        Bundle metadata = dataHolder.getMetadata();
        if (metadata == null) {
            return;
        }
        synchronized (dataHolder) {
            DataHolder dataHolder2 = (DataHolder) metadata.getParcelable("parentsExtraHolder");
            if (dataHolder2 != null) {
                dataHolder2.close();
                metadata.remove("parentsExtraHolder");
            }
        }
    }

    @Override // com.google.android.gms.drive.metadata.zzb, com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
        return zzc(dataHolder, i, i2);
    }

    @Override // com.google.android.gms.drive.metadata.internal.zzl, com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ Object zzb(Bundle bundle) {
        return zzb(bundle);
    }
}
