package com.google.android.gms.games.internal;

import com.google.android.gms.games.VideosClient;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
final class zzj extends zzab {
    final /* synthetic */ zzg zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzj(zzg zzgVar, byte[] bArr) {
        super(null);
        this.zza = zzgVar;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    public final void notifyListener(Object obj) {
        ((VideosClient.OnCaptureOverlayStateListener) obj).onCaptureOverlayStateChanged(this.zza.zza);
    }
}
