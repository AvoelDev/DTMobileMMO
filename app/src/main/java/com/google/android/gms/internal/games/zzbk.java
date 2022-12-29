package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final /* synthetic */ class zzbk implements RemoteCall {
    public final /* synthetic */ String zza;
    public final /* synthetic */ String zzb;
    public final /* synthetic */ SnapshotMetadataChange zzc;
    public final /* synthetic */ SnapshotContents zzd;

    public /* synthetic */ zzbk(String str, String str2, SnapshotMetadataChange snapshotMetadataChange, SnapshotContents snapshotContents) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = snapshotMetadataChange;
        this.zzd = snapshotContents;
    }

    @Override // com.google.android.gms.common.api.internal.RemoteCall
    public final void accept(Object obj, Object obj2) {
        String str = this.zza;
        String str2 = this.zzb;
        SnapshotMetadataChange snapshotMetadataChange = this.zzc;
        SnapshotContents snapshotContents = this.zzd;
        int i = zzbq.zza;
        ((com.google.android.gms.games.internal.zzan) obj).zzT((TaskCompletionSource) obj2, str, str2, snapshotMetadataChange, snapshotContents);
    }
}
