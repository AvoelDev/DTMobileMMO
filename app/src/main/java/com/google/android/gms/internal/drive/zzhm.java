package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.drive.TransferPreferencesBuilder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
public final class zzhm extends zzhh<TransferPreferences> {
    public zzhm(TaskCompletionSource<TransferPreferences> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzfj zzfjVar) throws RemoteException {
        zzay().setResult(new TransferPreferencesBuilder(zzfjVar.zzas()).build());
    }

    @Override // com.google.android.gms.internal.drive.zzl, com.google.android.gms.internal.drive.zzeq
    public final void zza(zzga zzgaVar) throws RemoteException {
        zzay().setResult(zzgaVar.zzax());
    }
}
