package com.google.android.gms.drive.query;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.drive.query.internal.zzf;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class SortOrder extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SortOrder> CREATOR = new zzc();
    private final List<zzf> zzlw;
    private final boolean zzlx;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SortOrder(List<zzf> list, boolean z) {
        this.zzlw = list;
        this.zzlx = z;
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private final List<zzf> zzlw = new ArrayList();
        private boolean zzlx = false;

        public Builder addSortAscending(SortableMetadataField sortableMetadataField) {
            this.zzlw.add(new zzf(sortableMetadataField.getName(), true));
            return this;
        }

        public Builder addSortDescending(SortableMetadataField sortableMetadataField) {
            this.zzlw.add(new zzf(sortableMetadataField.getName(), false));
            return this;
        }

        public SortOrder build() {
            return new SortOrder(this.zzlw, false);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 1, this.zzlw, false);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzlx);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public String toString() {
        return String.format(Locale.US, "SortOrder[%s, %s]", TextUtils.join(",", this.zzlw), Boolean.valueOf(this.zzlx));
    }
}
