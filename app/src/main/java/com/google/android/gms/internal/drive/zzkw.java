package com.google.android.gms.internal.drive;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzkw<K> implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zztg;

    public zzkw(Iterator<Map.Entry<K, Object>> it) {
        this.zztg = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zztg.hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zztg.remove();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Map.Entry<K, Object> next = this.zztg.next();
        return next.getValue() instanceof zzkt ? new zzkv(next) : next;
    }
}
