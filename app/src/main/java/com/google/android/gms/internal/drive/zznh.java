package com.google.android.gms.internal.drive;

/* loaded from: classes3.dex */
abstract class zznh {
    abstract int zzb(int i, byte[] bArr, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int zzb(CharSequence charSequence, byte[] bArr, int i, int i2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String zzg(byte[] bArr, int i, int i2) throws zzkq;

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zze(byte[] bArr, int i, int i2) {
        return zzb(0, bArr, i, i2) == 0;
    }
}
