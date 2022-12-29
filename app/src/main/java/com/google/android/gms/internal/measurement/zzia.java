package com.google.android.gms.internal.measurement;

import android.content.Context;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes3.dex */
public abstract class zzia {
    public static final /* synthetic */ int zzc = 0;
    private static final Object zzd = new Object();
    @Nullable
    private static volatile zzhy zze = null;
    private static volatile boolean zzf = false;
    private static final AtomicReference zzg = new AtomicReference();
    private static final zzic zzh = new zzic(zzhs.zza, null);
    private static final AtomicInteger zzi = new AtomicInteger();
    final zzhx zza;
    final String zzb;
    private final Object zzj;
    private volatile int zzk = -1;
    private volatile Object zzl;
    private final boolean zzm;

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzia(zzhx zzhxVar, String str, Object obj, boolean z, zzhz zzhzVar) {
        if (zzhxVar.zzb == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.zza = zzhxVar;
        this.zzb = str;
        this.zzj = obj;
        this.zzm = true;
    }

    public static void zzd() {
        zzi.incrementAndGet();
    }

    public static void zze(final Context context) {
        if (zze == null) {
            boolean z = zzf;
            synchronized (zzd) {
                if (zze == null) {
                    boolean z2 = zzf;
                    boolean z3 = zzf;
                    synchronized (zzd) {
                        zzhy zzhyVar = zze;
                        Context applicationContext = context.getApplicationContext();
                        if (applicationContext != null) {
                            context = applicationContext;
                        }
                        if (zzhyVar == null || zzhyVar.zza() != context) {
                            zzhe.zze();
                            zzib.zzc();
                            zzhm.zze();
                            zze = new zzhb(context, zzil.zza(new zzih() { // from class: com.google.android.gms.internal.measurement.zzhr
                                @Override // com.google.android.gms.internal.measurement.zzih
                                public final Object zza() {
                                    Context context2 = context;
                                    int i = zzia.zzc;
                                    return zzhn.zza(context2);
                                }
                            }));
                            zzi.incrementAndGet();
                        }
                    }
                }
            }
        }
    }

    abstract Object zza(Object obj);

    /* JADX WARN: Removed duplicated region for block: B:41:0x00a5 A[Catch: all -> 0x00e1, TryCatch #0 {, blocks: (B:12:0x001c, B:14:0x0020, B:16:0x0028, B:18:0x0031, B:20:0x003f, B:24:0x0068, B:26:0x0072, B:42:0x00a7, B:44:0x00b7, B:46:0x00cd, B:47:0x00d0, B:48:0x00d4, B:30:0x007b, B:32:0x0081, B:36:0x0097, B:38:0x009d, B:41:0x00a5, B:35:0x0093, B:22:0x0058, B:49:0x00d9, B:50:0x00de, B:51:0x00df), top: B:58:0x001c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object zzb() {
        /*
            Method dump skipped, instructions count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzia.zzb():java.lang.Object");
    }

    public final String zzc() {
        String str = this.zza.zzd;
        return this.zzb;
    }
}
