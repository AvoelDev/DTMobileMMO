package com.facebook.internal.instrument;

import com.facebook.internal.FeatureManager;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.instrument.-$$Lambda$InstrumentManager$W14-zN8pSqs882CZOyfFpXGD91k  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$InstrumentManager$W14zN8pSqs882CZOyfFpXGD91k implements FeatureManager.Callback {
    public static final /* synthetic */ $$Lambda$InstrumentManager$W14zN8pSqs882CZOyfFpXGD91k INSTANCE = new $$Lambda$InstrumentManager$W14zN8pSqs882CZOyfFpXGD91k();

    private /* synthetic */ $$Lambda$InstrumentManager$W14zN8pSqs882CZOyfFpXGD91k() {
    }

    @Override // com.facebook.internal.FeatureManager.Callback
    public final void onCompleted(boolean z) {
        InstrumentManager.m158start$lambda0(z);
    }
}
