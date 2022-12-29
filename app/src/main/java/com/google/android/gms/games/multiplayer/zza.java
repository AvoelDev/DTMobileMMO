package com.google.android.gms.games.multiplayer;

import android.os.Parcel;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
final class zza extends zzb {
    @Override // com.google.android.gms.games.multiplayer.zzb, android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return createFromParcel(parcel);
    }

    @Override // com.google.android.gms.games.multiplayer.zzb
    public final ParticipantEntity zza(Parcel parcel) {
        Integer unparcelClientVersion;
        boolean zzo;
        boolean canUnparcelSafely;
        unparcelClientVersion = ParticipantEntity.getUnparcelClientVersion();
        zzo = ParticipantEntity.zzo(unparcelClientVersion);
        if (!zzo) {
            canUnparcelSafely = ParticipantEntity.canUnparcelSafely(ParticipantEntity.class.getCanonicalName());
            if (!canUnparcelSafely) {
                return new ParticipantEntity();
            }
        }
        return super.createFromParcel(parcel);
    }
}
