package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Pair;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.events.DriveEvent;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzee extends zzet {
    private static final GmsLogger zzbz = new GmsLogger("EventCallback", "");
    private final com.google.android.gms.drive.events.zzi zzgt;
    private final zzeg zzgu;
    private final List<Integer> zzgv = new ArrayList();
    private final int zzda = 1;

    public zzee(Looper looper, Context context, int i, com.google.android.gms.drive.events.zzi zziVar) {
        this.zzgt = zziVar;
        this.zzgu = new zzeg(looper, context);
    }

    public final void zzf(int i) {
        this.zzgv.add(1);
    }

    public final boolean zzg(int i) {
        return this.zzgv.contains(1);
    }

    @Override // com.google.android.gms.internal.drive.zzes
    public final void zzc(zzfp zzfpVar) throws RemoteException {
        DriveEvent zzat = zzfpVar.zzat();
        Preconditions.checkState(this.zzda == zzat.getType());
        Preconditions.checkState(this.zzgv.contains(Integer.valueOf(zzat.getType())));
        zzeg zzegVar = this.zzgu;
        zzegVar.sendMessage(zzegVar.obtainMessage(1, new Pair(this.zzgt, zzat)));
    }
}
