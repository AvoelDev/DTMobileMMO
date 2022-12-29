package com.facebook.internal.instrument;

import com.facebook.internal.FeatureManager;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.instrument.-$$Lambda$InstrumentManager$afbQg6_R8R87G093r1zWqUFMaOY  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$InstrumentManager$afbQg6_R8R87G093r1zWqUFMaOY implements FeatureManager.Callback {
    public static final /* synthetic */ $$Lambda$InstrumentManager$afbQg6_R8R87G093r1zWqUFMaOY INSTANCE = new $$Lambda$InstrumentManager$afbQg6_R8R87G093r1zWqUFMaOY();

    private /* synthetic */ $$Lambda$InstrumentManager$afbQg6_R8R87G093r1zWqUFMaOY() {
    }

    @Override // com.facebook.internal.FeatureManager.Callback
    public final void onCompleted(boolean z) {
        InstrumentManager.m160start$lambda2(z);
    }
}
