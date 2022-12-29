package com.google.android.gms.games.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.games.zzce;
import com.google.android.gms.internal.games.zzck;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
final class zzt extends zzce {
    final /* synthetic */ zzan zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzt(zzan zzanVar) {
        super(zzanVar.getContext().getMainLooper(), 1000);
        this.zza = zzanVar;
    }

    @Override // com.google.android.gms.internal.games.zzce
    protected final void zza(String str, int i) {
        try {
            if (this.zza.isConnected()) {
                ((zzas) this.zza.getService()).zzD(str, i);
                return;
            }
            zzck.zza("GamesGmsClientImpl", "Unable to increment event " + str + " by " + i + " because the games client is no longer connected");
        } catch (RemoteException e) {
            zzan.zzad(e);
        } catch (SecurityException e2) {
            int i2 = zzan.zze;
            zzck.zzb("GamesGmsClientImpl", "Is player signed out?", e2);
        }
    }
}
