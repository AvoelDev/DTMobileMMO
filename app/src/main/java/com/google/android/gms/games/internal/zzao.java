package com.google.android.gms.games.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.video.VideoCapabilities;
import com.google.firebase.FirebaseError;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public abstract class zzao extends com.google.android.gms.internal.games.zzb implements zzap {
    public zzao() {
        super("com.google.android.gms.games.internal.IGamesCallbacks");
    }

    @Override // com.google.android.gms.internal.games.zzb
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 6001) {
            parcel.readString();
            com.google.android.gms.internal.games.zzc.zzb(parcel);
        } else if (i == 6002) {
            parcel.readString();
            com.google.android.gms.internal.games.zzc.zzb(parcel);
        } else if (i == 12011) {
            com.google.android.gms.internal.games.zzc.zzb(parcel);
            zzf((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
        } else if (i == 12012) {
            int readInt = parcel.readInt();
            String readString = parcel.readString();
            com.google.android.gms.internal.games.zzc.zzb(parcel);
            zzr(readInt, readString);
        } else if (i == 13001) {
            DataHolder dataHolder = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
            com.google.android.gms.internal.games.zzc.zzb(parcel);
        } else if (i == 13002) {
            parcel.readInt();
            com.google.android.gms.internal.games.zzc.zzb(parcel);
        } else if (i == 19001) {
            com.google.android.gms.internal.games.zzc.zzb(parcel);
            zzv(parcel.readInt(), (VideoCapabilities) com.google.android.gms.internal.games.zzc.zza(parcel, VideoCapabilities.CREATOR));
        } else if (i == 19002) {
            int readInt2 = parcel.readInt();
            boolean zzg = com.google.android.gms.internal.games.zzc.zzg(parcel);
            com.google.android.gms.internal.games.zzc.zzb(parcel);
            zzu(readInt2, zzg);
        } else {
            switch (i) {
                case 5001:
                    parcel.readInt();
                    parcel.readString();
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    break;
                case 5002:
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    zzc((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                    break;
                case 5003:
                    int readInt3 = parcel.readInt();
                    String readString2 = parcel.readString();
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    zzb(readInt3, readString2);
                    break;
                case 5004:
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    zzj((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                    break;
                case 5005:
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    zzi((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR), (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                    break;
                case 5006:
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    zzn((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                    break;
                case 5007:
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    zzm((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                    break;
                case 5008:
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    zzg((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                    break;
                case 5009:
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    zzh((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                    break;
                case 5010:
                    DataHolder dataHolder2 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    break;
                case 5011:
                    DataHolder dataHolder3 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                    break;
                default:
                    switch (i) {
                        case 5016:
                            zzo();
                            break;
                        case 5017:
                            DataHolder dataHolder4 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5018:
                            DataHolder dataHolder5 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5019:
                            DataHolder dataHolder6 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5020:
                            parcel.readInt();
                            parcel.readString();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5021:
                            DataHolder dataHolder7 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5022:
                            DataHolder dataHolder8 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5023:
                            DataHolder dataHolder9 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5024:
                            DataHolder dataHolder10 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5025:
                            DataHolder dataHolder11 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5026:
                            DataHolder dataHolder12 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            parcel.createStringArray();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5027:
                            DataHolder dataHolder13 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            parcel.createStringArray();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5028:
                            DataHolder dataHolder14 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            parcel.createStringArray();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5029:
                            DataHolder dataHolder15 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            parcel.createStringArray();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5030:
                            DataHolder dataHolder16 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            parcel.createStringArray();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5031:
                            DataHolder dataHolder17 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            parcel.createStringArray();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5032:
                            com.google.android.gms.games.multiplayer.realtime.zzb zzbVar = (com.google.android.gms.games.multiplayer.realtime.zzb) com.google.android.gms.internal.games.zzc.zza(parcel, com.google.android.gms.games.multiplayer.realtime.zzb.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5033:
                            parcel.readInt();
                            parcel.readInt();
                            parcel.readString();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5034:
                            parcel.readInt();
                            parcel.readString();
                            com.google.android.gms.internal.games.zzc.zzg(parcel);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5035:
                            DataHolder dataHolder18 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5036:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5037:
                            DataHolder dataHolder19 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5038:
                            DataHolder dataHolder20 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5039:
                            DataHolder dataHolder21 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 5040:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 9001:
                            DataHolder dataHolder22 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 11001:
                            parcel.readInt();
                            Bundle bundle = (Bundle) com.google.android.gms.internal.games.zzc.zza(parcel, Bundle.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 12001:
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            zzt((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                            break;
                        case 14001:
                            DataHolder[] dataHolderArr = (DataHolder[]) parcel.createTypedArray(DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 15001:
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            zzl((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                            break;
                        case FirebaseError.ERROR_CUSTOM_TOKEN_MISMATCH /* 17002 */:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 19008:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 19009:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 19010:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20001:
                            DataHolder dataHolder23 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20002:
                            DataHolder dataHolder24 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20003:
                            DataHolder dataHolder25 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20004:
                            DataHolder dataHolder26 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20005:
                            DataHolder dataHolder27 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20006:
                            DataHolder dataHolder28 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20007:
                            DataHolder dataHolder29 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20008:
                            DataHolder dataHolder30 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20009:
                            DataHolder dataHolder31 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20012:
                            Status status = (Status) com.google.android.gms.internal.games.zzc.zza(parcel, Status.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 20019:
                            int readInt4 = parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            zzd(readInt4);
                            break;
                        case 20020:
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            zze(parcel.readInt(), (Bundle) com.google.android.gms.internal.games.zzc.zza(parcel, Bundle.CREATOR));
                            break;
                        case 23001:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 23002:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 23003:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 23004:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 23005:
                            parcel.readInt();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 24002:
                            com.google.android.gms.internal.games.zzc.zzg(parcel);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 25002:
                            parcel.readString();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 25003:
                            Status status2 = (Status) com.google.android.gms.internal.games.zzc.zza(parcel, Status.CREATOR);
                            parcel.readString();
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        case 25004:
                            Status status3 = (Status) com.google.android.gms.internal.games.zzc.zza(parcel, Status.CREATOR);
                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                            break;
                        default:
                            switch (i) {
                                case 8001:
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    zzk((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                                    break;
                                case 8002:
                                    parcel.readInt();
                                    Bundle bundle2 = (Bundle) com.google.android.gms.internal.games.zzc.zza(parcel, Bundle.CREATOR);
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    break;
                                case 8003:
                                    DataHolder dataHolder32 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    break;
                                case 8004:
                                    DataHolder dataHolder33 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    break;
                                case 8005:
                                    DataHolder dataHolder34 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    break;
                                case 8006:
                                    DataHolder dataHolder35 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    break;
                                case 8007:
                                    parcel.readInt();
                                    parcel.readString();
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    break;
                                case 8008:
                                    DataHolder dataHolder36 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    break;
                                case 8009:
                                    parcel.readString();
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    break;
                                case 8010:
                                    parcel.readString();
                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                    break;
                                default:
                                    switch (i) {
                                        case GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED /* 10001 */:
                                            DataHolder dataHolder37 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                            break;
                                        case GamesActivityResultCodes.RESULT_SIGN_IN_FAILED /* 10002 */:
                                            parcel.readString();
                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                            break;
                                        case GamesActivityResultCodes.RESULT_LICENSE_FAILED /* 10003 */:
                                            DataHolder dataHolder38 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                            break;
                                        case GamesActivityResultCodes.RESULT_APP_MISCONFIGURED /* 10004 */:
                                            DataHolder dataHolder39 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                            break;
                                        case 10005:
                                            parcel.readInt();
                                            Bundle bundle3 = (Bundle) com.google.android.gms.internal.games.zzc.zza(parcel, Bundle.CREATOR);
                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                            break;
                                        case GamesActivityResultCodes.RESULT_NETWORK_FAILURE /* 10006 */:
                                            DataHolder dataHolder40 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                            break;
                                        default:
                                            switch (i) {
                                                case 12004:
                                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                                    zzs((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR), (Contents) com.google.android.gms.internal.games.zzc.zza(parcel, Contents.CREATOR));
                                                    break;
                                                case 12005:
                                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                                    zzp((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR));
                                                    break;
                                                case 12006:
                                                    DataHolder dataHolder41 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                                    break;
                                                case 12007:
                                                    DataHolder dataHolder42 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                                    break;
                                                case 12008:
                                                    DataHolder dataHolder43 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                                    com.google.android.gms.internal.games.zzc.zzb(parcel);
                                                    break;
                                                default:
                                                    switch (i) {
                                                        case 12014:
                                                            DataHolder dataHolder44 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                                            break;
                                                        case 12015:
                                                            parcel.readInt();
                                                            Bundle bundle4 = (Bundle) com.google.android.gms.internal.games.zzc.zza(parcel, Bundle.CREATOR);
                                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                                            break;
                                                        case 12016:
                                                            DataHolder dataHolder45 = (DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR);
                                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                                            break;
                                                        case 12017:
                                                            com.google.android.gms.internal.games.zzc.zzb(parcel);
                                                            zzq((DataHolder) com.google.android.gms.internal.games.zzc.zza(parcel, DataHolder.CREATOR), parcel.readString(), (Contents) com.google.android.gms.internal.games.zzc.zza(parcel, Contents.CREATOR), (Contents) com.google.android.gms.internal.games.zzc.zza(parcel, Contents.CREATOR), (Contents) com.google.android.gms.internal.games.zzc.zza(parcel, Contents.CREATOR));
                                                            break;
                                                        default:
                                                            return false;
                                                    }
                                            }
                                    }
                            }
                    }
            }
        }
        parcel2.writeNoException();
        return true;
    }
}
