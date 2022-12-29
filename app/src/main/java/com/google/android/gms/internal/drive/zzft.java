package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* loaded from: classes3.dex */
public final class zzft extends com.google.android.gms.drive.zzu {
    public static final Parcelable.Creator<zzft> CREATOR = new zzfu();
    final boolean zzea;
    final DataHolder zzii;

    @Override // com.google.android.gms.drive.zzu
    protected final void zza(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzii, i, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzea);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public zzft(DataHolder dataHolder, boolean z) {
        this.zzii = dataHolder;
        this.zzea = z;
    }

    public final DataHolder zzau() {
        return this.zzii;
    }
}
