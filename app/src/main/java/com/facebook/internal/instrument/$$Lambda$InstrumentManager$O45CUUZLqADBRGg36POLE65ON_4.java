package com.facebook.internal.instrument;

import com.facebook.internal.FeatureManager;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.instrument.-$$Lambda$InstrumentManager$O45CUUZLqADBRGg36POLE65ON_4  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$InstrumentManager$O45CUUZLqADBRGg36POLE65ON_4 implements FeatureManager.Callback {
    public static final /* synthetic */ $$Lambda$InstrumentManager$O45CUUZLqADBRGg36POLE65ON_4 INSTANCE = new $$Lambda$InstrumentManager$O45CUUZLqADBRGg36POLE65ON_4();

    private /* synthetic */ $$Lambda$InstrumentManager$O45CUUZLqADBRGg36POLE65ON_4() {
    }

    @Override // com.facebook.internal.FeatureManager.Callback
    public final void onCompleted(boolean z) {
        InstrumentManager.m159start$lambda1(z);
    }
}
