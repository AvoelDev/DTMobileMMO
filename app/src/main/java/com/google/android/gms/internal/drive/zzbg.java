package com.google.android.gms.internal.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
final class zzbg extends TaskApiCall<zzaw, IntentSender> {
    private final /* synthetic */ CreateFileActivityOptions zzer;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbg(zzbb zzbbVar, CreateFileActivityOptions createFileActivityOptions) {
        this.zzer = createFileActivityOptions;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    public final /* synthetic */ void doExecute(zzaw zzawVar, TaskCompletionSource<IntentSender> taskCompletionSource) throws RemoteException {
        zzaw zzawVar2 = zzawVar;
        this.zzer.zzde.zza(zzawVar2.getContext());
        taskCompletionSource.setResult(((zzeo) zzawVar2.getService()).zza(new zzu(this.zzer.zzde, this.zzer.zzdk.intValue(), this.zzer.zzba, this.zzer.zzbd, Integer.valueOf(this.zzer.zzdl))));
    }
}
