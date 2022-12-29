package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.Contents;

/* loaded from: classes3.dex */
public final class zzo extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzo> CREATOR = new zzp();
    private final Contents zzdf;
    private final int zzdh;
    private final Boolean zzdj;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzdf, i, false);
        SafeParcelWriter.writeBooleanObject(parcel, 3, this.zzdj, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzdh);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public zzo(Contents contents, Boolean bool, int i) {
        this.zzdf = contents;
        this.zzdj = bool;
        this.zzdh = i;
    }

    public zzo(int i, boolean z) {
        this(null, false, i);
    }
}
