package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzfl extends AbstractSafeParcelable {
    private final int status;
    final long zzhy;
    final long zzhz;
    private final List<com.google.android.gms.drive.zzh> zzia;
    private static final List<com.google.android.gms.drive.zzh> zzhx = Collections.emptyList();
    public static final Parcelable.Creator<zzfl> CREATOR = new zzfm();

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeLong(parcel, 2, this.zzhy);
        SafeParcelWriter.writeLong(parcel, 3, this.zzhz);
        SafeParcelWriter.writeInt(parcel, 4, this.status);
        SafeParcelWriter.writeTypedList(parcel, 5, this.zzia, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public zzfl(long j, long j2, int i, List<com.google.android.gms.drive.zzh> list) {
        this.zzhy = j;
        this.zzhz = j2;
        this.status = i;
        this.zzia = list;
    }
}
