package com.google.android.gms.internal.drive;

import android.content.IntentSender;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzep extends zza implements zzeo {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzep(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.drive.internal.IDriveService");
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzek zzekVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzekVar);
        zzc.zza(zza, zzeqVar);
        zzb(1, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzgq zzgqVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzgqVar);
        zzc.zza(zza, zzeqVar);
        zzb(2, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzhf zzhfVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzhfVar);
        zzc.zza(zza, zzeqVar);
        zzb(3, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzr zzrVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzrVar);
        zzc.zza(zza, zzeqVar);
        zzb(4, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzw zzwVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzwVar);
        zzc.zza(zza, zzeqVar);
        zzb(5, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzy zzyVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzyVar);
        zzc.zza(zza, zzeqVar);
        zzb(6, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final zzec zza(zzgj zzgjVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzgjVar);
        zzc.zza(zza, zzeqVar);
        Parcel zza2 = zza(7, zza);
        zzec zzecVar = (zzec) zzc.zza(zza2, zzec.CREATOR);
        zza2.recycle();
        return zzecVar;
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzo zzoVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzoVar);
        zzc.zza(zza, zzeqVar);
        zzb(8, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzeqVar);
        zzb(9, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final IntentSender zza(zzgm zzgmVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzgmVar);
        Parcel zza2 = zza(10, zza);
        IntentSender intentSender = (IntentSender) zzc.zza(zza2, IntentSender.CREATOR);
        zza2.recycle();
        return intentSender;
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final IntentSender zza(zzu zzuVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzuVar);
        Parcel zza2 = zza(11, zza);
        IntentSender intentSender = (IntentSender) zzc.zza(zza2, IntentSender.CREATOR);
        zza2.recycle();
        return intentSender;
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzex zzexVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzexVar);
        zzc.zza(zza, zzeqVar);
        zzb(13, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzj zzjVar, zzes zzesVar, String str, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzjVar);
        zzc.zza(zza, zzesVar);
        zza.writeString(null);
        zzc.zza(zza, zzeqVar);
        zzb(14, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzgs zzgsVar, zzes zzesVar, String str, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzgsVar);
        zzc.zza(zza, zzesVar);
        zza.writeString(null);
        zzc.zza(zza, zzeqVar);
        zzb(15, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzad zzadVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzadVar);
        zzb(16, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzhb zzhbVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzhbVar);
        zzc.zza(zza, zzeqVar);
        zzb(17, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzm zzmVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzmVar);
        zzc.zza(zza, zzeqVar);
        zzb(18, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzab zzabVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzabVar);
        zzc.zza(zza, zzeqVar);
        zzb(24, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzgw zzgwVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzgwVar);
        zzc.zza(zza, zzeqVar);
        zzb(28, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zzb(zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzeqVar);
        zzb(35, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzgu zzguVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzguVar);
        zzc.zza(zza, zzeqVar);
        zzb(36, zza);
    }

    @Override // com.google.android.gms.internal.drive.zzeo
    public final void zza(zzhd zzhdVar, zzeq zzeqVar) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, zzhdVar);
        zzc.zza(zza, zzeqVar);
        zzb(38, zza);
    }
}
