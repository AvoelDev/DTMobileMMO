package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final /* synthetic */ class zzaz implements RemoteCall {
    public final /* synthetic */ String zza;
    public final /* synthetic */ String zzb;
    public final /* synthetic */ String zzc;

    public /* synthetic */ zzaz(String str, String str2, String str3) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
    }

    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final void accept(Object obj, Object obj2) {
        String str = this.zza;
        String str2 = this.zzb;
        String str3 = this.zzc;
        int i = zzbf.zza;
        ((TaskCompletionSource) obj2).setResult(((com.google.android.gms.games.internal.zzas) ((com.google.android.gms.games.internal.zzan) obj).getService()).zzj(str, str2, str3));
    }
}
