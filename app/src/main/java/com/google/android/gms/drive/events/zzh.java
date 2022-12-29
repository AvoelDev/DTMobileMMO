package com.google.android.gms.drive.events;

import android.os.Looper;
import com.google.android.gms.drive.events.DriveEventService;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes3.dex */
final class zzh extends Thread {
    private final /* synthetic */ CountDownLatch zzcn;
    private final /* synthetic */ DriveEventService zzco;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzh(DriveEventService driveEventService, CountDownLatch countDownLatch) {
        this.zzco = driveEventService;
        this.zzcn = countDownLatch;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        CountDownLatch countDownLatch;
        CountDownLatch countDownLatch2;
        try {
            Looper.prepare();
            this.zzco.zzck = new DriveEventService.zza(this.zzco, null);
            this.zzco.zzcl = false;
            this.zzcn.countDown();
            Looper.loop();
        } finally {
            countDownLatch = this.zzco.zzcj;
            if (countDownLatch != null) {
                countDownLatch2 = this.zzco.zzcj;
                countDownLatch2.countDown();
            }
        }
    }
}
