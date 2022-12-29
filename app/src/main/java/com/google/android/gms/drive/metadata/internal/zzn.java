package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class zzn implements Parcelable.Creator<ParentDriveIdSet> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ ParentDriveIdSet[] newArray(int i) {
        return new ParentDriveIdSet[i];
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ ParentDriveIdSet createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        ArrayList arrayList = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            if (SafeParcelReader.getFieldId(readHeader) == 2) {
                arrayList = SafeParcelReader.createTypedList(parcel, readHeader, zzq.CREATOR);
            } else {
                SafeParcelReader.skipUnknownField(parcel, readHeader);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new ParentDriveIdSet(arrayList);
    }
}
