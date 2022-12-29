package com.google.android.gms.internal.drive;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.GmsVersion;
import com.google.android.gms.drive.metadata.CustomPropertyKey;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class zzid extends com.google.android.gms.drive.metadata.internal.zzm<AppVisibleCustomProperties> {
    public static final com.google.android.gms.drive.metadata.internal.zzg zzlc = new zzie();

    public zzid(int i) {
        super("customProperties", Arrays.asList("hasCustomProperties", "sqlId"), Arrays.asList("customPropertiesExtra", "customPropertiesExtraHolder"), GmsVersion.VERSION_LONGHORN);
    }

    private static AppVisibleCustomProperties zzf(DataHolder dataHolder, int i, int i2) {
        Bundle metadata = dataHolder.getMetadata();
        SparseArray sparseParcelableArray = metadata.getSparseParcelableArray("customPropertiesExtra");
        if (sparseParcelableArray == null) {
            if (metadata.getParcelable("customPropertiesExtraHolder") != null) {
                synchronized (dataHolder) {
                    DataHolder dataHolder2 = (DataHolder) dataHolder.getMetadata().getParcelable("customPropertiesExtraHolder");
                    if (dataHolder2 != null) {
                        Bundle metadata2 = dataHolder2.getMetadata();
                        String string = metadata2.getString("entryIdColumn");
                        String string2 = metadata2.getString("keyColumn");
                        String string3 = metadata2.getString("visibilityColumn");
                        String string4 = metadata2.getString("valueColumn");
                        LongSparseArray longSparseArray = new LongSparseArray();
                        for (int i3 = 0; i3 < dataHolder2.getCount(); i3++) {
                            int windowIndex = dataHolder2.getWindowIndex(i3);
                            long j = dataHolder2.getLong(string, i3, windowIndex);
                            String string5 = dataHolder2.getString(string2, i3, windowIndex);
                            int integer = dataHolder2.getInteger(string3, i3, windowIndex);
                            com.google.android.gms.drive.metadata.internal.zzc zzcVar = new com.google.android.gms.drive.metadata.internal.zzc(new CustomPropertyKey(string5, integer), dataHolder2.getString(string4, i3, windowIndex));
                            AppVisibleCustomProperties.zza zzaVar = (AppVisibleCustomProperties.zza) longSparseArray.get(j);
                            if (zzaVar == null) {
                                zzaVar = new AppVisibleCustomProperties.zza();
                                longSparseArray.put(j, zzaVar);
                            }
                            zzaVar.zza(zzcVar);
                        }
                        SparseArray<? extends Parcelable> sparseArray = new SparseArray<>();
                        for (int i4 = 0; i4 < dataHolder.getCount(); i4++) {
                            AppVisibleCustomProperties.zza zzaVar2 = (AppVisibleCustomProperties.zza) longSparseArray.get(dataHolder.getLong("sqlId", i4, dataHolder.getWindowIndex(i4)));
                            if (zzaVar2 != null) {
                                sparseArray.append(i4, zzaVar2.zzbb());
                            }
                        }
                        dataHolder.getMetadata().putSparseParcelableArray("customPropertiesExtra", sparseArray);
                        dataHolder2.close();
                        dataHolder.getMetadata().remove("customPropertiesExtraHolder");
                    }
                }
                sparseParcelableArray = metadata.getSparseParcelableArray("customPropertiesExtra");
            }
            if (sparseParcelableArray == null) {
                return AppVisibleCustomProperties.zzjb;
            }
        }
        return (AppVisibleCustomProperties) sparseParcelableArray.get(i, AppVisibleCustomProperties.zzjb);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzc(DataHolder dataHolder) {
        Bundle metadata = dataHolder.getMetadata();
        if (metadata == null) {
            return;
        }
        synchronized (dataHolder) {
            DataHolder dataHolder2 = (DataHolder) metadata.getParcelable("customPropertiesExtraHolder");
            if (dataHolder2 != null) {
                dataHolder2.close();
                metadata.remove("customPropertiesExtraHolder");
            }
        }
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
        return zzf(dataHolder, i, i2);
    }
}
