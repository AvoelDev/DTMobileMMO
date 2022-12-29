package com.google.android.gms.internal.plus;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;

/* loaded from: classes2.dex */
final class zzk extends zzp {
    private final /* synthetic */ int zzaj;
    private final /* synthetic */ String zzak;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzk(zzj zzjVar, GoogleApiClient googleApiClient, int i, String str) {
        super(googleApiClient, null);
        this.zzaj = i;
        this.zzak = str;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(com.google.android.gms.plus.internal.zzh zzhVar) throws RemoteException {
        setCancelToken(zzhVar.zza(this, this.zzaj, this.zzak));
    }
}
