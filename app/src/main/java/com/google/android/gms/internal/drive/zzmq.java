package com.google.android.gms.internal.drive;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzmq implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private Iterator<Map.Entry<K, V>> zzvj;
    private final /* synthetic */ zzmi zzvk;
    private boolean zzvo;

    private zzmq(zzmi zzmiVar) {
        this.zzvk = zzmiVar;
        this.pos = -1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        List list;
        Map map;
        int i = this.pos + 1;
        list = this.zzvk.zzve;
        if (i >= list.size()) {
            map = this.zzvk.zzvf;
            if (map.isEmpty() || !zzew().hasNext()) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Iterator
    public final void remove() {
        List list;
        if (!this.zzvo) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzvo = false;
        this.zzvk.zzeu();
        int i = this.pos;
        list = this.zzvk.zzve;
        if (i < list.size()) {
            zzmi zzmiVar = this.zzvk;
            int i2 = this.pos;
            this.pos = i2 - 1;
            zzmiVar.zzax(i2);
            return;
        }
        zzew().remove();
    }

    private final Iterator<Map.Entry<K, V>> zzew() {
        Map map;
        if (this.zzvj == null) {
            map = this.zzvk.zzvf;
            this.zzvj = map.entrySet().iterator();
        }
        return this.zzvj;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        List list;
        List list2;
        this.zzvo = true;
        int i = this.pos + 1;
        this.pos = i;
        list = this.zzvk.zzve;
        if (i >= list.size()) {
            return (Map.Entry) zzew().next();
        }
        list2 = this.zzvk.zzve;
        return (Map.Entry) list2.get(this.pos);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzmq(zzmi zzmiVar, zzmj zzmjVar) {
        this(zzmiVar);
    }
}
