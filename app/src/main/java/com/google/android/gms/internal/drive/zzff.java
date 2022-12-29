package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.drive.DriveId;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzff extends com.google.android.gms.drive.zzu {
    public static final Parcelable.Creator<zzff> CREATOR = new zzfg();
    private final DataHolder zzhr;
    private final List<DriveId> zzhs;
    private final com.google.android.gms.drive.zza zzht;
    private final boolean zzhu;

    @Override // com.google.android.gms.drive.zzu
    protected final void zza(Parcel parcel, int i) {
        int i2 = i | 1;
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzhr, i2, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzhs, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzht, i2, false);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzhu);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public zzff(DataHolder dataHolder, List<DriveId> list, com.google.android.gms.drive.zza zzaVar, boolean z) {
        this.zzhr = dataHolder;
        this.zzhs = list;
        this.zzht = zzaVar;
        this.zzhu = z;
    }
}
