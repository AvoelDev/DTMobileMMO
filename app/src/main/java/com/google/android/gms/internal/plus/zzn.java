package com.google.android.gms.internal.plus;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.Collection;

/* loaded from: classes2.dex */
final class zzn extends zzp {
    private final /* synthetic */ Collection zzal;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzn(zzj zzjVar, GoogleApiClient googleApiClient, Collection collection) {
        super(googleApiClient, null);
        this.zzal = collection;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* synthetic */ void doExecute(com.google.android.gms.plus.internal.zzh zzhVar) throws RemoteException {
        zzhVar.zza(this, this.zzal);
    }
}
