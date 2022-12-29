package com.google.android.gms.drive.metadata.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.internal.drive.zzhs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes3.dex */
public final class MetadataBundle extends AbstractSafeParcelable implements ReflectedParcelable {
    private final Bundle zzjh;
    private static final GmsLogger zzbz = new GmsLogger("MetadataBundle", "");
    public static final Parcelable.Creator<MetadataBundle> CREATOR = new zzj();

    /* JADX INFO: Access modifiers changed from: package-private */
    public MetadataBundle(Bundle bundle) {
        int i;
        this.zzjh = (Bundle) Preconditions.checkNotNull(bundle);
        this.zzjh.setClassLoader(getClass().getClassLoader());
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = this.zzjh.keySet().iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            if (zzf.zzf(next) == null) {
                arrayList.add(next);
                zzbz.wfmt("MetadataBundle", "Ignored unknown metadata field in bundle: %s", next);
            }
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            this.zzjh.remove((String) obj);
        }
    }

    public static MetadataBundle zzbe() {
        return new MetadataBundle(new Bundle());
    }

    public static <T> MetadataBundle zza(MetadataField<T> metadataField, T t) {
        MetadataBundle zzbe = zzbe();
        zzbe.zzb(metadataField, t);
        return zzbe;
    }

    public final MetadataBundle zzbf() {
        return new MetadataBundle(new Bundle(this.zzjh));
    }

    public final <T> void zzb(MetadataField<T> metadataField, T t) {
        if (zzf.zzf(metadataField.getName()) == null) {
            String valueOf = String.valueOf(metadataField.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Unregistered field: ".concat(valueOf) : new String("Unregistered field: "));
        } else {
            metadataField.zza(t, this.zzjh);
        }
    }

    public final <T> T zza(MetadataField<T> metadataField) {
        return metadataField.zza(this.zzjh);
    }

    public final <T> T zzc(MetadataField<T> metadataField) {
        T t = (T) zza(metadataField);
        this.zzjh.remove(metadataField.getName());
        return t;
    }

    public final boolean zzd(MetadataField<?> metadataField) {
        return this.zzjh.containsKey(metadataField.getName());
    }

    public final Set<MetadataField<?>> zzbg() {
        HashSet hashSet = new HashSet();
        for (String str : this.zzjh.keySet()) {
            hashSet.add(zzf.zzf(str));
        }
        return hashSet;
    }

    public final void zza(Context context) {
        BitmapTeleporter bitmapTeleporter = (BitmapTeleporter) zza(zzhs.zzkq);
        if (bitmapTeleporter != null) {
            bitmapTeleporter.setTempDir(context.getCacheDir());
        }
    }

    public final int hashCode() {
        int i = 1;
        for (String str : this.zzjh.keySet()) {
            i = (i * 31) + this.zzjh.get(str).hashCode();
        }
        return i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MetadataBundle metadataBundle = (MetadataBundle) obj;
        Set<String> keySet = this.zzjh.keySet();
        if (keySet.equals(metadataBundle.zzjh.keySet())) {
            for (String str : keySet) {
                if (!Objects.equal(this.zzjh.get(str), metadataBundle.zzjh.get(str))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, this.zzjh, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
