package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzkk.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public abstract class zzkk<MessageType extends zzkk<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzit<MessageType, BuilderType> {
    private static Map<Object, zzkk<?, ?>> zzrs = new ConcurrentHashMap();
    protected zzmy zzrq = zzmy.zzfa();
    private int zzrr = -1;

    /* loaded from: classes3.dex */
    public static class zzb<T extends zzkk<T, ?>> extends zziv<T> {
        private final T zzrt;

        public zzb(T t) {
            this.zzrt = t;
        }
    }

    /* loaded from: classes3.dex */
    public static class zzd<ContainingType extends zzlq, Type> extends zzjv<ContainingType, Type> {
    }

    /* loaded from: classes3.dex */
    public enum zze {
        public static final int zzrx = 1;
        public static final int zzry = 2;
        public static final int zzrz = 3;
        public static final int zzsa = 4;
        public static final int zzsb = 5;
        public static final int zzsc = 6;
        public static final int zzsd = 7;
        private static final /* synthetic */ int[] zzse = {zzrx, zzry, zzrz, zzsa, zzsb, zzsc, zzsd};
        public static final int zzsf = 1;
        public static final int zzsg = 2;
        private static final /* synthetic */ int[] zzsh = {zzsf, zzsg};
        public static final int zzsi = 1;
        public static final int zzsj = 2;
        private static final /* synthetic */ int[] zzsk = {zzsi, zzsj};

        public static int[] zzdh() {
            return (int[]) zzse.clone();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract Object zza(int i, Object obj, Object obj2);

    /* loaded from: classes3.dex */
    public static abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType> extends zzkk<MessageType, BuilderType> implements zzls {
        protected zzkb<Object> zzrw = zzkb.zzcn();

        /* JADX INFO: Access modifiers changed from: package-private */
        public final zzkb<Object> zzdg() {
            if (this.zzrw.isImmutable()) {
                this.zzrw = (zzkb) this.zzrw.clone();
            }
            return this.zzrw;
        }
    }

    public String toString() {
        return zzlt.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zzne != 0) {
            return this.zzne;
        }
        this.zzne = zzmd.zzej().zzq(this).hashCode(this);
        return this.zzne;
    }

    /* loaded from: classes3.dex */
    public static abstract class zza<MessageType extends zzkk<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zziu<MessageType, BuilderType> {
        private final MessageType zzrt;
        protected MessageType zzru;
        private boolean zzrv = false;

        /* JADX INFO: Access modifiers changed from: protected */
        public zza(MessageType messagetype) {
            this.zzrt = messagetype;
            this.zzru = (MessageType) messagetype.zza(zze.zzsa, null, null);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final void zzdb() {
            if (this.zzrv) {
                MessageType messagetype = (MessageType) this.zzru.zza(zze.zzsa, null, null);
                zza(messagetype, this.zzru);
                this.zzru = messagetype;
                this.zzrv = false;
            }
        }

        @Override // com.google.android.gms.internal.drive.zzls
        public final boolean isInitialized() {
            return zzkk.zza(this.zzru, false);
        }

        @Override // com.google.android.gms.internal.drive.zzlr
        /* renamed from: zzdc */
        public MessageType zzde() {
            if (this.zzrv) {
                return this.zzru;
            }
            this.zzru.zzbp();
            this.zzrv = true;
            return this.zzru;
        }

        @Override // com.google.android.gms.internal.drive.zzlr
        /* renamed from: zzdd */
        public final MessageType zzdf() {
            MessageType messagetype = (MessageType) zzde();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw new zzmw(messagetype);
        }

        public final BuilderType zza(MessageType messagetype) {
            zzdb();
            zza(this.zzru, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzmd.zzej().zzq(messagetype).zzc(messagetype, messagetype2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.drive.zziu
        protected final /* synthetic */ zziu zza(zzit zzitVar) {
            return zza((zza<MessageType, BuilderType>) ((zzkk) zzitVar));
        }

        @Override // com.google.android.gms.internal.drive.zziu
        public final /* synthetic */ zziu zzbn() {
            return (zza) clone();
        }

        @Override // com.google.android.gms.internal.drive.zzls
        public final /* synthetic */ zzlq zzda() {
            return this.zzrt;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.drive.zziu
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zzaVar = (zza) this.zzrt.zza(zze.zzsb, null, null);
            zzaVar.zza((zza) ((zzkk) zzde()));
            return zzaVar;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (((zzkk) zza(zze.zzsc, (Object) null, (Object) null)).getClass().isInstance(obj)) {
            return zzmd.zzej().zzq(this).equals(this, (zzkk) obj);
        }
        return false;
    }

    protected final void zzbp() {
        zzmd.zzej().zzq(this).zzd(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <MessageType extends zzkk<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> BuilderType zzcw() {
        return (BuilderType) zza(zze.zzsb, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.drive.zzls
    public final boolean isInitialized() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    @Override // com.google.android.gms.internal.drive.zzit
    final int zzbm() {
        return this.zzrr;
    }

    @Override // com.google.android.gms.internal.drive.zzit
    final void zzo(int i) {
        this.zzrr = i;
    }

    @Override // com.google.android.gms.internal.drive.zzlq
    public final void zzb(zzjr zzjrVar) throws IOException {
        zzmd.zzej().zzf(getClass()).zza(this, zzjt.zza(zzjrVar));
    }

    @Override // com.google.android.gms.internal.drive.zzlq
    public final int zzcx() {
        if (this.zzrr == -1) {
            this.zzrr = zzmd.zzej().zzq(this).zzn(this);
        }
        return this.zzrr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T extends zzkk<?, ?>> T zzd(Class<T> cls) {
        zzkk<?, ?> zzkkVar = zzrs.get(cls);
        if (zzkkVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzkkVar = zzrs.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzkkVar == null) {
            zzkkVar = (T) ((zzkk) zznd.zzh(cls)).zza(zze.zzsc, (Object) null, (Object) null);
            if (zzkkVar == null) {
                throw new IllegalStateException();
            }
            zzrs.put(cls, zzkkVar);
        }
        return (T) zzkkVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static <T extends zzkk<?, ?>> void zza(Class<T> cls, T t) {
        zzrs.put(cls, t);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static Object zza(zzlq zzlqVar, String str, Object[] objArr) {
        return new zzme(zzlqVar, str, objArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static final <T extends zzkk<T, ?>> boolean zza(T t, boolean z) {
        byte byteValue = ((Byte) t.zza(zze.zzrx, null, null)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean zzp = zzmd.zzej().zzq(t).zzp(t);
        if (z) {
            t.zza(zze.zzry, zzp ? t : null, null);
        }
        return zzp;
    }

    private static <T extends zzkk<T, ?>> T zza(T t, byte[] bArr, int i, int i2, zzjx zzjxVar) throws zzkq {
        T t2 = (T) t.zza(zze.zzsa, null, null);
        try {
            zzmd.zzej().zzq(t2).zza(t2, bArr, 0, i2, new zziz(zzjxVar));
            t2.zzbp();
            if (t2.zzne == 0) {
                return t2;
            }
            throw new RuntimeException();
        } catch (IOException e) {
            if (e.getCause() instanceof zzkq) {
                throw ((zzkq) e.getCause());
            }
            throw new zzkq(e.getMessage()).zzg(t2);
        } catch (IndexOutOfBoundsException unused) {
            throw zzkq.zzdi().zzg(t2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static <T extends zzkk<T, ?>> T zza(T t, byte[] bArr, zzjx zzjxVar) throws zzkq {
        T t2 = (T) zza(t, bArr, 0, bArr.length, zzjxVar);
        if (t2 == null || t2.isInitialized()) {
            return t2;
        }
        throw new zzkq(new zzmw(t2).getMessage()).zzg(t2);
    }

    @Override // com.google.android.gms.internal.drive.zzlq
    public final /* synthetic */ zzlr zzcy() {
        zza zzaVar = (zza) zza(zze.zzsb, (Object) null, (Object) null);
        zzaVar.zza((zza) this);
        return zzaVar;
    }

    @Override // com.google.android.gms.internal.drive.zzlq
    public final /* synthetic */ zzlr zzcz() {
        return (zza) zza(zze.zzsb, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.drive.zzls
    public final /* synthetic */ zzlq zzda() {
        return (zzkk) zza(zze.zzsc, (Object) null, (Object) null);
    }
}
