package com.google.android.gms.internal.drive;

/* loaded from: classes3.dex */
abstract class zzjf implements zzjj {
    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator
    public /* synthetic */ Byte next() {
        return Byte.valueOf(nextByte());
    }
}
