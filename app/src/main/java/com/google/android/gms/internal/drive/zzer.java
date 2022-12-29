package com.google.android.gms.internal.drive;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
public abstract class zzer extends zzb implements zzeq {
    public zzer() {
        super("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
    }

    @Override // com.google.android.gms.internal.drive.zzb
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zza((zzfl) zzc.zza(parcel, zzfl.CREATOR));
                break;
            case 2:
                zza((zzft) zzc.zza(parcel, zzft.CREATOR));
                break;
            case 3:
                zza((zzfn) zzc.zza(parcel, zzfn.CREATOR));
                break;
            case 4:
                zza((zzfy) zzc.zza(parcel, zzfy.CREATOR));
                break;
            case 5:
                zza((zzfh) zzc.zza(parcel, zzfh.CREATOR));
                break;
            case 6:
                zza((Status) zzc.zza(parcel, Status.CREATOR));
                break;
            case 7:
                onSuccess();
                break;
            case 8:
                zza((zzfv) zzc.zza(parcel, zzfv.CREATOR));
                break;
            case 9:
                zza((zzgh) zzc.zza(parcel, zzgh.CREATOR));
                break;
            case 10:
            case 19:
            default:
                return false;
            case 11:
                zza((zzfx) zzc.zza(parcel, zzfx.CREATOR), zzip.zzb(parcel.readStrongBinder()));
                break;
            case 12:
                zza((zzgd) zzc.zza(parcel, zzgd.CREATOR));
                break;
            case 13:
                zza((zzga) zzc.zza(parcel, zzga.CREATOR));
                break;
            case 14:
                zza((zzfj) zzc.zza(parcel, zzfj.CREATOR));
                break;
            case 15:
                zzb(zzc.zza(parcel));
                break;
            case 16:
                zza((zzfr) zzc.zza(parcel, zzfr.CREATOR));
                break;
            case 17:
                zza((com.google.android.gms.drive.zza) zzc.zza(parcel, com.google.android.gms.drive.zza.CREATOR));
                break;
            case 18:
                zza((zzff) zzc.zza(parcel, zzff.CREATOR));
                break;
            case 20:
                zza((zzem) zzc.zza(parcel, zzem.CREATOR));
                break;
            case 21:
                zza((zzgz) zzc.zza(parcel, zzgz.CREATOR));
                break;
            case 22:
                zza((zzgf) zzc.zza(parcel, zzgf.CREATOR));
                break;
        }
        parcel2.writeNoException();
        return true;
    }
}
