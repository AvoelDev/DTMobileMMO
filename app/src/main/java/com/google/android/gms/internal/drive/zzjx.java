package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkk;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class zzjx {
    private static volatile boolean zzol;
    private static volatile zzjx zzon;
    private final Map<zza, zzkk.zzd<?, ?>> zzop;
    private static final Class<?> zzom = zzch();
    static final zzjx zzoo = new zzjx(true);

    private static Class<?> zzch() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    static final class zza {
        private final int number;
        private final Object object;

        zza(Object obj, int i) {
            this.object = obj;
            this.number = i;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.object) * 65535) + this.number;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof zza) {
                zza zzaVar = (zza) obj;
                return this.object == zzaVar.object && this.number == zzaVar.number;
            }
            return false;
        }
    }

    public static zzjx zzci() {
        return zzjw.zzcf();
    }

    public static zzjx zzcj() {
        zzjx zzjxVar = zzon;
        if (zzjxVar == null) {
            synchronized (zzjx.class) {
                zzjxVar = zzon;
                if (zzjxVar == null) {
                    zzjxVar = zzjw.zzcg();
                    zzon = zzjxVar;
                }
            }
        }
        return zzjxVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzjx zzcg() {
        return zzki.zza(zzjx.class);
    }

    public final <ContainingType extends zzlq> zzkk.zzd<ContainingType, ?> zza(ContainingType containingtype, int i) {
        return (zzkk.zzd<ContainingType, ?>) this.zzop.get(new zza(containingtype, i));
    }

    zzjx() {
        this.zzop = new HashMap();
    }

    private zzjx(boolean z) {
        this.zzop = Collections.emptyMap();
    }
}
