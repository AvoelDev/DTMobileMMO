package com.google.android.gms.dynamite;

import android.os.Looper;
import android.util.Log;

/* compiled from: com.google.android.gms:play-services-basement@@18.1.0 */
/* loaded from: classes3.dex */
public final class zzb {
    private static volatile ClassLoader zza;
    private static volatile Thread zzb;

    public static synchronized ClassLoader zza() {
        ClassLoader classLoader;
        synchronized (zzb.class) {
            if (zza == null) {
                zza = zzb();
            }
            classLoader = zza;
        }
        return classLoader;
    }

    private static synchronized ClassLoader zzb() {
        synchronized (zzb.class) {
            ClassLoader classLoader = null;
            if (zzb == null) {
                zzb = zzc();
                if (zzb == null) {
                    return null;
                }
            }
            synchronized (zzb) {
                try {
                    classLoader = zzb.getContextClassLoader();
                } catch (SecurityException e) {
                    String message = e.getMessage();
                    Log.w("DynamiteLoaderV2CL", "Failed to get thread context classloader " + message);
                }
            }
            return classLoader;
        }
    }

    private static synchronized Thread zzc() {
        zza zzaVar;
        SecurityException e;
        ThreadGroup threadGroup;
        zza zzaVar2;
        synchronized (zzb.class) {
            ThreadGroup threadGroup2 = Looper.getMainLooper().getThread().getThreadGroup();
            if (threadGroup2 == null) {
                return null;
            }
            synchronized (Void.class) {
                try {
                    ThreadGroup[] threadGroupArr = new ThreadGroup[threadGroup2.activeGroupCount()];
                    threadGroup2.enumerate(threadGroupArr);
                    int length = threadGroupArr.length;
                    int i = 0;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            threadGroup = null;
                            break;
                        }
                        threadGroup = threadGroupArr[i2];
                        if ("dynamiteLoader".equals(threadGroup.getName())) {
                            break;
                        }
                        i2++;
                    }
                    if (threadGroup == null) {
                        threadGroup = new ThreadGroup(threadGroup2, "dynamiteLoader");
                    }
                    Thread[] threadArr = new Thread[threadGroup.activeCount()];
                    threadGroup.enumerate(threadArr);
                    int length2 = threadArr.length;
                    while (true) {
                        if (i >= length2) {
                            zzaVar = null;
                            break;
                        }
                        zzaVar = threadArr[i];
                        if ("GmsDynamite".equals(zzaVar.getName())) {
                            break;
                        }
                        i++;
                    }
                    if (zzaVar == null) {
                        try {
                            zzaVar2 = new zza(threadGroup, "GmsDynamite");
                        } catch (SecurityException e2) {
                            e = e2;
                            e = e;
                            Log.w("DynamiteLoaderV2CL", "Failed to enumerate thread/threadgroup " + e.getMessage());
                            return zzaVar;
                        }
                        try {
                            zzaVar2.setContextClassLoader(null);
                            zzaVar2.start();
                            zzaVar = zzaVar2;
                        } catch (SecurityException e3) {
                            e = e3;
                            zzaVar = zzaVar2;
                            Log.w("DynamiteLoaderV2CL", "Failed to enumerate thread/threadgroup " + e.getMessage());
                            return zzaVar;
                        }
                    }
                } catch (SecurityException e4) {
                    e = e4;
                    zzaVar = null;
                }
            }
            return zzaVar;
        }
    }
}
