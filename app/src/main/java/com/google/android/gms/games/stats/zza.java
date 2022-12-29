package com.google.android.gms.games.stats;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zza implements Parcelable.Creator {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void zza(PlayerStatsEntity playerStatsEntity, Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeFloat(parcel, 1, playerStatsEntity.getAverageSessionLength());
        SafeParcelWriter.writeFloat(parcel, 2, playerStatsEntity.getChurnProbability());
        SafeParcelWriter.writeInt(parcel, 3, playerStatsEntity.getDaysSinceLastPlayed());
        SafeParcelWriter.writeInt(parcel, 4, playerStatsEntity.getNumberOfPurchases());
        SafeParcelWriter.writeInt(parcel, 5, playerStatsEntity.getNumberOfSessions());
        SafeParcelWriter.writeFloat(parcel, 6, playerStatsEntity.getSessionPercentile());
        SafeParcelWriter.writeFloat(parcel, 7, playerStatsEntity.getSpendPercentile());
        SafeParcelWriter.writeBundle(parcel, 8, playerStatsEntity.zza(), false);
        SafeParcelWriter.writeFloat(parcel, 9, playerStatsEntity.getSpendProbability());
        SafeParcelWriter.writeFloat(parcel, 10, playerStatsEntity.getHighSpenderProbability());
        SafeParcelWriter.writeFloat(parcel, 11, playerStatsEntity.getTotalSpendNext28Days());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        Bundle bundle = null;
        float f = 0.0f;
        float f2 = 0.0f;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 1:
                    f = SafeParcelReader.readFloat(parcel, readHeader);
                    break;
                case 2:
                    f2 = SafeParcelReader.readFloat(parcel, readHeader);
                    break;
                case 3:
                    i = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 4:
                    i2 = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 5:
                    i3 = SafeParcelReader.readInt(parcel, readHeader);
                    break;
                case 6:
                    f3 = SafeParcelReader.readFloat(parcel, readHeader);
                    break;
                case 7:
                    f4 = SafeParcelReader.readFloat(parcel, readHeader);
                    break;
                case 8:
                    bundle = SafeParcelReader.createBundle(parcel, readHeader);
                    break;
                case 9:
                    f5 = SafeParcelReader.readFloat(parcel, readHeader);
                    break;
                case 10:
                    f6 = SafeParcelReader.readFloat(parcel, readHeader);
                    break;
                case 11:
                    f7 = SafeParcelReader.readFloat(parcel, readHeader);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, validateObjectHeader);
        return new PlayerStatsEntity(f, f2, i, i2, i3, f3, f4, bundle, f5, f6, f7);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new PlayerStatsEntity[i];
    }
}
