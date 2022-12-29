package com.facebook;

import com.facebook.internal.FeatureManager;

/* compiled from: lambda */
/* renamed from: com.facebook.-$$Lambda$FacebookSdk$xnPiGMIrZY-efkOoczDJVIjnueQ  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FacebookSdk$xnPiGMIrZYefkOoczDJVIjnueQ implements FeatureManager.Callback {
    public static final /* synthetic */ $$Lambda$FacebookSdk$xnPiGMIrZYefkOoczDJVIjnueQ INSTANCE = new $$Lambda$FacebookSdk$xnPiGMIrZYefkOoczDJVIjnueQ();

    private /* synthetic */ $$Lambda$FacebookSdk$xnPiGMIrZYefkOoczDJVIjnueQ() {
    }

    @Override // com.facebook.internal.FeatureManager.Callback
    public final void onCompleted(boolean z) {
        FacebookSdk.m26sdkInitialize$lambda7(z);
    }
}
