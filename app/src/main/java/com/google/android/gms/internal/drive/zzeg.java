package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.CompletionListener;
import com.google.android.gms.drive.events.DriveEvent;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class zzeg extends zzir {
    private final Context zzgw;

    private zzeg(Looper looper, Context context) {
        super(looper);
        this.zzgw = context;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        GmsLogger gmsLogger;
        GmsLogger gmsLogger2;
        if (message.what == 1) {
            Pair pair = (Pair) message.obj;
            com.google.android.gms.drive.events.zzi zziVar = (com.google.android.gms.drive.events.zzi) pair.first;
            DriveEvent driveEvent = (DriveEvent) pair.second;
            int type = driveEvent.getType();
            if (type == 1) {
                ((ChangeListener) zziVar).onChange((ChangeEvent) driveEvent);
                return;
            } else if (type == 2) {
                ((CompletionListener) zziVar).onCompletion((CompletionEvent) driveEvent);
                return;
            } else if (type != 3) {
                if (type == 4) {
                    ((com.google.android.gms.drive.events.zzd) zziVar).zza((com.google.android.gms.drive.events.zzb) driveEvent);
                    return;
                } else if (type != 8) {
                    gmsLogger = zzee.zzbz;
                    gmsLogger.wfmt("EventCallback", "Unexpected event: %s", driveEvent);
                    return;
                } else {
                    ((com.google.android.gms.drive.events.zzl) zziVar).zza(new zze(((com.google.android.gms.drive.events.zzr) driveEvent).zzac()));
                    return;
                }
            } else {
                com.google.android.gms.drive.events.zzq zzqVar = (com.google.android.gms.drive.events.zzq) zziVar;
                com.google.android.gms.drive.events.zzo zzoVar = (com.google.android.gms.drive.events.zzo) driveEvent;
                DataHolder zzz = zzoVar.zzz();
                if (zzz != null) {
                    zzqVar.zza(new zzeh(new MetadataBuffer(zzz)));
                }
                if (zzoVar.zzaa()) {
                    zzqVar.zzc(zzoVar.zzab());
                    return;
                }
                return;
            }
        }
        gmsLogger2 = zzee.zzbz;
        gmsLogger2.efmt("EventCallback", "Don't know how to handle this event in context %s", this.zzgw);
    }
}
