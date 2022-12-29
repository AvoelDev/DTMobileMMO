package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.DataHolder;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class PlayerRef extends zzm implements Player {
    private final com.google.android.gms.games.internal.player.zzd zza;
    private final PlayerLevelInfo zzb;
    private final com.google.android.gms.games.internal.player.zzc zzc;
    private final zzt zzd;
    private final zzc zze;

    public PlayerRef(DataHolder dataHolder, int i, String str) {
        super(dataHolder, i);
        this.zza = new com.google.android.gms.games.internal.player.zzd(null);
        this.zzc = new com.google.android.gms.games.internal.player.zzc(dataHolder, i, this.zza);
        this.zzd = new zzt(dataHolder, i, this.zza);
        this.zze = new zzc(dataHolder, i, this.zza);
        if (hasNull(this.zza.zzj) || getLong(this.zza.zzj) == -1) {
            this.zzb = null;
            return;
        }
        int integer = getInteger(this.zza.zzk);
        int integer2 = getInteger(this.zza.zzn);
        PlayerLevel playerLevel = new PlayerLevel(integer, getLong(this.zza.zzl), getLong(this.zza.zzm));
        this.zzb = new PlayerLevelInfo(getLong(this.zza.zzj), getLong(this.zza.zzp), playerLevel, integer != integer2 ? new PlayerLevel(integer2, getLong(this.zza.zzm), getLong(this.zza.zzo)) : playerLevel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.google.android.gms.common.data.DataBufferRef
    public final boolean equals(Object obj) {
        return PlayerEntity.zzn(this, obj);
    }

    @Override // com.google.android.gms.common.data.Freezable
    public final /* synthetic */ Player freeze() {
        return new PlayerEntity(this);
    }

    @Override // com.google.android.gms.games.Player
    public final Uri getBannerImageLandscapeUri() {
        return parseUri(this.zza.zzB);
    }

    @Override // com.google.android.gms.games.Player
    public String getBannerImageLandscapeUrl() {
        return getString(this.zza.zzC);
    }

    @Override // com.google.android.gms.games.Player
    public final Uri getBannerImagePortraitUri() {
        return parseUri(this.zza.zzD);
    }

    @Override // com.google.android.gms.games.Player
    public String getBannerImagePortraitUrl() {
        return getString(this.zza.zzE);
    }

    @Override // com.google.android.gms.games.Player
    public final CurrentPlayerInfo getCurrentPlayerInfo() {
        if (this.zze.zza()) {
            return this.zze;
        }
        return null;
    }

    @Override // com.google.android.gms.games.Player
    public final String getDisplayName() {
        return getString(this.zza.zzb);
    }

    @Override // com.google.android.gms.games.Player
    public final Uri getHiResImageUri() {
        return parseUri(this.zza.zze);
    }

    @Override // com.google.android.gms.games.Player
    public String getHiResImageUrl() {
        return getString(this.zza.zzf);
    }

    @Override // com.google.android.gms.games.Player
    public final Uri getIconImageUri() {
        return parseUri(this.zza.zzc);
    }

    @Override // com.google.android.gms.games.Player
    public String getIconImageUrl() {
        return getString(this.zza.zzd);
    }

    @Override // com.google.android.gms.games.Player
    public final long getLastPlayedWithTimestamp() {
        if (!hasColumn(this.zza.zzi) || hasNull(this.zza.zzi)) {
            return -1L;
        }
        return getLong(this.zza.zzi);
    }

    @Override // com.google.android.gms.games.Player
    public final PlayerLevelInfo getLevelInfo() {
        return this.zzb;
    }

    @Override // com.google.android.gms.games.Player
    public final String getPlayerId() {
        return getString(this.zza.zza);
    }

    @Override // com.google.android.gms.games.Player
    public final PlayerRelationshipInfo getRelationshipInfo() {
        zzt zztVar = this.zzd;
        if (zztVar.getFriendStatus() == -1 && zztVar.zzb() == null && zztVar.zza() == null) {
            return null;
        }
        return this.zzd;
    }

    @Override // com.google.android.gms.games.Player
    public final long getRetrievedTimestamp() {
        return getLong(this.zza.zzg);
    }

    @Override // com.google.android.gms.games.Player
    public final String getTitle() {
        return getString(this.zza.zzq);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean hasHiResImage() {
        return getHiResImageUri() != null;
    }

    @Override // com.google.android.gms.games.Player
    public final boolean hasIconImage() {
        return getIconImageUri() != null;
    }

    @Override // com.google.android.gms.common.data.DataBufferRef
    public final int hashCode() {
        return PlayerEntity.zzi(this);
    }

    public final String toString() {
        return PlayerEntity.zzk(this);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        new PlayerEntity(this).writeToParcel(parcel, i);
    }

    @Override // com.google.android.gms.games.Player
    public final int zza() {
        return getInteger(this.zza.zzh);
    }

    @Override // com.google.android.gms.games.Player
    public final long zzb() {
        String str = this.zza.zzF;
        if (!hasColumn(str) || hasNull(str)) {
            return -1L;
        }
        return getLong(str);
    }

    @Override // com.google.android.gms.games.Player
    public final com.google.android.gms.games.internal.player.zza zzc() {
        if (hasNull(this.zza.zzs)) {
            return null;
        }
        return this.zzc;
    }

    @Override // com.google.android.gms.games.Player
    public final String zzd() {
        return getString(this.zza.zzz);
    }

    @Override // com.google.android.gms.games.Player
    public final String zze() {
        return getString(this.zza.zzA);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean zzf() {
        return getBoolean(this.zza.zzy);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean zzg() {
        return hasColumn(this.zza.zzL) && getBoolean(this.zza.zzL);
    }

    @Override // com.google.android.gms.games.Player
    public final boolean zzh() {
        return getBoolean(this.zza.zzr);
    }

    @Override // com.google.android.gms.games.Player
    public final void getDisplayName(CharArrayBuffer charArrayBuffer) {
        copyToBuffer(this.zza.zzb, charArrayBuffer);
    }

    @Override // com.google.android.gms.games.Player
    public final void getTitle(CharArrayBuffer charArrayBuffer) {
        copyToBuffer(this.zza.zzq, charArrayBuffer);
    }
}
