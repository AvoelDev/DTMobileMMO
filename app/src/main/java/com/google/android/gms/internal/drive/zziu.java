package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzit;
import com.google.android.gms.internal.drive.zziu;

/* loaded from: classes3.dex */
public abstract class zziu<MessageType extends zzit<MessageType, BuilderType>, BuilderType extends zziu<MessageType, BuilderType>> implements zzlr {
    protected abstract BuilderType zza(MessageType messagetype);

    @Override // 
    /* renamed from: zzbn */
    public abstract BuilderType clone();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.drive.zzlr
    public final /* synthetic */ zzlr zza(zzlq zzlqVar) {
        if (!zzda().getClass().isInstance(zzlqVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return zza((zziu<MessageType, BuilderType>) ((zzit) zzlqVar));
    }
}
