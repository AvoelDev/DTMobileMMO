package com.google.android.gms.internal.drive;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzmk implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private Iterator<Map.Entry<K, V>> zzvj;
    private final /* synthetic */ zzmi zzvk;

    private zzmk(zzmi zzmiVar) {
        List list;
        this.zzvk = zzmiVar;
        list = this.zzvk.zzve;
        this.pos = list.size();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        List list;
        int i = this.pos;
        if (i > 0) {
            list = this.zzvk.zzve;
            if (i <= list.size()) {
                return true;
            }
        }
        return zzew().hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Map.Entry<K, V>> zzew() {
        Map map;
        if (this.zzvj == null) {
            map = this.zzvk.zzvh;
            this.zzvj = map.entrySet().iterator();
        }
        return this.zzvj;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        List list;
        if (zzew().hasNext()) {
            return (Map.Entry) zzew().next();
        }
        list = this.zzvk.zzve;
        int i = this.pos - 1;
        this.pos = i;
        return (Map.Entry) list.get(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzmk(zzmi zzmiVar, zzmj zzmjVar) {
        this(zzmiVar);
    }
}
