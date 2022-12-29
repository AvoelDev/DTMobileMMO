package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.1.1 */
/* loaded from: classes2.dex */
final class zzmr implements Iterator {
    final Iterator zza;
    final /* synthetic */ zzms zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzmr(zzms zzmsVar) {
        zzkt zzktVar;
        this.zzb = zzmsVar;
        zzktVar = this.zzb.zza;
        this.zza = zzktVar.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Object next() {
        return (String) this.zza.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
