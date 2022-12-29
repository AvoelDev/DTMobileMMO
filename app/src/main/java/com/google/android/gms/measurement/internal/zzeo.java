package com.google.android.gms.measurement.internal;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes3.dex */
public final class zzeo extends zzgs {
    private char zza;
    private long zzb;
    private String zzc;
    private final zzem zzd;
    private final zzem zze;
    private final zzem zzf;
    private final zzem zzg;
    private final zzem zzh;
    private final zzem zzi;
    private final zzem zzj;
    private final zzem zzk;
    private final zzem zzl;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzeo(zzfy zzfyVar) {
        super(zzfyVar);
        this.zza = (char) 0;
        this.zzb = -1L;
        this.zzd = new zzem(this, 6, false, false);
        this.zze = new zzem(this, 6, true, false);
        this.zzf = new zzem(this, 6, false, true);
        this.zzg = new zzem(this, 5, false, false);
        this.zzh = new zzem(this, 5, true, false);
        this.zzi = new zzem(this, 5, false, true);
        this.zzj = new zzem(this, 4, false, false);
        this.zzk = new zzem(this, 3, false, false);
        this.zzl = new zzem(this, 2, false, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static Object zzn(String str) {
        if (str == null) {
            return null;
        }
        return new zzen(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String zzo(boolean z, String str, Object obj, Object obj2, Object obj3) {
        String str2 = "";
        if (str == null) {
            str = "";
        }
        String zzp = zzp(z, obj);
        String zzp2 = zzp(z, obj2);
        String zzp3 = zzp(z, obj3);
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(zzp)) {
            sb.append(str2);
            sb.append(zzp);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzp2)) {
            sb.append(str2);
            sb.append(zzp2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzp3)) {
            sb.append(str2);
            sb.append(zzp3);
        }
        return sb.toString();
    }

    static String zzp(boolean z, Object obj) {
        String str;
        String className;
        if (obj == null) {
            return "";
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf(((Integer) obj).intValue());
        }
        int i = 0;
        if (obj instanceof Long) {
            if (!z) {
                return obj.toString();
            }
            Long l = (Long) obj;
            if (Math.abs(l.longValue()) < 100) {
                return obj.toString();
            }
            String str2 = obj.toString().charAt(0) == '-' ? "-" : "";
            String valueOf = String.valueOf(Math.abs(l.longValue()));
            long round = Math.round(Math.pow(10.0d, valueOf.length() - 1));
            long round2 = Math.round(Math.pow(10.0d, valueOf.length()) - 1.0d);
            return str2 + round + "..." + str2 + round2;
        } else if (obj instanceof Boolean) {
            return obj.toString();
        } else {
            if (obj instanceof Throwable) {
                Throwable th = (Throwable) obj;
                StringBuilder sb = new StringBuilder(z ? th.getClass().getName() : th.toString());
                String zzy = zzy(zzfy.class.getCanonicalName());
                StackTraceElement[] stackTrace = th.getStackTrace();
                int length = stackTrace.length;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    StackTraceElement stackTraceElement = stackTrace[i];
                    if (!stackTraceElement.isNativeMethod() && (className = stackTraceElement.getClassName()) != null && zzy(className).equals(zzy)) {
                        sb.append(": ");
                        sb.append(stackTraceElement);
                        break;
                    }
                    i++;
                }
                return sb.toString();
            } else if (!(obj instanceof zzen)) {
                return z ? "-" : obj.toString();
            } else {
                str = ((zzen) obj).zza;
                return str;
            }
        }
    }

    private static String zzy(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    public final zzem zzc() {
        return this.zzk;
    }

    public final zzem zzd() {
        return this.zzd;
    }

    public final zzem zze() {
        return this.zzf;
    }

    @Override // com.google.android.gms.measurement.internal.zzgs
    protected final boolean zzf() {
        return false;
    }

    public final zzem zzh() {
        return this.zze;
    }

    public final zzem zzi() {
        return this.zzj;
    }

    public final zzem zzj() {
        return this.zzl;
    }

    public final zzem zzk() {
        return this.zzg;
    }

    public final zzem zzl() {
        return this.zzi;
    }

    public final zzem zzm() {
        return this.zzh;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @EnsuresNonNull({"logTagDoNotUseDirectly"})
    public final String zzq() {
        String str;
        synchronized (this) {
            if (this.zzc == null) {
                if (this.zzs.zzy() == null) {
                    this.zzc = this.zzs.zzf().zzn();
                } else {
                    this.zzc = this.zzs.zzy();
                }
            }
            Preconditions.checkNotNull(this.zzc);
            str = this.zzc;
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzt(int i, boolean z, boolean z2, String str, Object obj, Object obj2, Object obj3) {
        if (!z && Log.isLoggable(zzq(), i)) {
            Log.println(i, zzq(), zzo(false, str, obj, obj2, obj3));
        }
        if (z2 || i < 5) {
            return;
        }
        Preconditions.checkNotNull(str);
        zzfv zzo = this.zzs.zzo();
        if (zzo == null) {
            Log.println(6, zzq(), "Scheduler not set. Not logging error/warn");
        } else if (!zzo.zzx()) {
            Log.println(6, zzq(), "Scheduler not initialized. Not logging error/warn");
        } else {
            zzo.zzp(new zzel(this, i >= 9 ? 8 : i, str, obj, obj2, obj3));
        }
    }
}
