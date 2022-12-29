package com.google.android.gms.internal.drive;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzml extends zzmr {
    private final /* synthetic */ zzmi zzvk;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private zzml(zzmi zzmiVar) {
        super(zzmiVar, null);
        this.zzvk = zzmiVar;
    }

    @Override // com.google.android.gms.internal.drive.zzmr, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzmk(this.zzvk, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzml(zzmi zzmiVar, zzmj zzmjVar) {
        this(zzmiVar);
    }
}
