package com.google.android.gms.internal.drive;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class zzjk {
    private final byte[] buffer;
    private final zzjr zznx;

    private zzjk(int i) {
        this.buffer = new byte[i];
        this.zznx = zzjr.zzb(this.buffer);
    }

    public final zzjc zzbx() {
        this.zznx.zzcb();
        return new zzjm(this.buffer);
    }

    public final zzjr zzby() {
        return this.zznx;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzjk(int i, zzjd zzjdVar) {
        this(i);
    }
}
