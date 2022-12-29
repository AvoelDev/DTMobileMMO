package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzfv extends zzgs {
    private static final AtomicLong zza = new AtomicLong(Long.MIN_VALUE);
    private zzfu zzb;
    private zzfu zzc;
    private final PriorityBlockingQueue zzd;
    private final BlockingQueue zze;
    private final Thread.UncaughtExceptionHandler zzf;
    private final Thread.UncaughtExceptionHandler zzg;
    private final Object zzh;
    private final Semaphore zzi;
    private volatile boolean zzj;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfv(zzfy zzfyVar) {
        super(zzfyVar);
        this.zzh = new Object();
        this.zzi = new Semaphore(2);
        this.zzd = new PriorityBlockingQueue();
        this.zze = new LinkedBlockingQueue();
        this.zzf = new zzfs(this, "Thread death: Uncaught exception on worker thread");
        this.zzg = new zzfs(this, "Thread death: Uncaught exception on network thread");
    }

    private final void zzt(zzft zzftVar) {
        synchronized (this.zzh) {
            this.zzd.add(zzftVar);
            zzfu zzfuVar = this.zzb;
            if (zzfuVar != null) {
                zzfuVar.zza();
            } else {
                this.zzb = new zzfu(this, "Measurement Worker", this.zzd);
                this.zzb.setUncaughtExceptionHandler(this.zzf);
                this.zzb.start();
            }
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final void zzax() {
        if (Thread.currentThread() != this.zzc) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Object zzd(AtomicReference atomicReference, long j, String str, Runnable runnable) {
        synchronized (atomicReference) {
            this.zzs.zzaz().zzp(runnable);
            try {
                atomicReference.wait(j);
            } catch (InterruptedException unused) {
                zzem zzk = this.zzs.zzay().zzk();
                zzk.zza("Interrupted waiting for " + str);
                return null;
            }
        }
        Object obj = atomicReference.get();
        if (obj == null) {
            this.zzs.zzay().zzk().zza("Timed out waiting for ".concat(str));
        }
        return obj;
    }

    @Override // com.google.android.gms.measurement.internal.zzgs
    protected final boolean zzf() {
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzgr
    public final void zzg() {
        if (Thread.currentThread() != this.zzb) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final Future zzh(Callable callable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(callable);
        zzft zzftVar = new zzft(this, callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            if (!this.zzd.isEmpty()) {
                this.zzs.zzay().zzk().zza("Callable skipped the worker queue.");
            }
            zzftVar.run();
        } else {
            zzt(zzftVar);
        }
        return zzftVar;
    }

    public final Future zzi(Callable callable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(callable);
        zzft zzftVar = new zzft(this, callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzb) {
            zzftVar.run();
        } else {
            zzt(zzftVar);
        }
        return zzftVar;
    }

    public final void zzo(Runnable runnable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(runnable);
        zzft zzftVar = new zzft(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzh) {
            this.zze.add(zzftVar);
            zzfu zzfuVar = this.zzc;
            if (zzfuVar != null) {
                zzfuVar.zza();
            } else {
                this.zzc = new zzfu(this, "Measurement Network", this.zze);
                this.zzc.setUncaughtExceptionHandler(this.zzg);
                this.zzc.start();
            }
        }
    }

    public final void zzp(Runnable runnable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(runnable);
        zzt(new zzft(this, runnable, false, "Task exception on worker thread"));
    }

    public final void zzq(Runnable runnable) throws IllegalStateException {
        zzu();
        Preconditions.checkNotNull(runnable);
        zzt(new zzft(this, runnable, true, "Task exception on worker thread"));
    }

    public final boolean zzs() {
        return Thread.currentThread() == this.zzb;
    }
}
