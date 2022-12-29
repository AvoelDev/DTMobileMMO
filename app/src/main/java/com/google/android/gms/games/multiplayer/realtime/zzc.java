package com.google.android.gms.games.multiplayer.realtime;

import android.os.Parcel;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
final class zzc extends zzd {
    @Override // com.google.android.gms.games.multiplayer.realtime.zzd, android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return createFromParcel(parcel);
    }

    @Override // com.google.android.gms.games.multiplayer.realtime.zzd
    public final RoomEntity zza(Parcel parcel) {
        Integer unparcelClientVersion;
        boolean zzo;
        boolean canUnparcelSafely;
        unparcelClientVersion = RoomEntity.getUnparcelClientVersion();
        zzo = RoomEntity.zzo(unparcelClientVersion);
        if (!zzo) {
            canUnparcelSafely = RoomEntity.canUnparcelSafely(RoomEntity.class.getCanonicalName());
            if (!canUnparcelSafely) {
                return new RoomEntity();
            }
        }
        return super.createFromParcel(parcel);
    }
}
