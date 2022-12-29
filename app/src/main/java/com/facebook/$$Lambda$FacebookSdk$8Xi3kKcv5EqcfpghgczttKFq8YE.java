package com.facebook;

import com.facebook.internal.FeatureManager;

/* compiled from: lambda */
/* renamed from: com.facebook.-$$Lambda$FacebookSdk$8Xi3kKcv5EqcfpghgczttKFq8YE  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FacebookSdk$8Xi3kKcv5EqcfpghgczttKFq8YE implements FeatureManager.Callback {
    public static final /* synthetic */ $$Lambda$FacebookSdk$8Xi3kKcv5EqcfpghgczttKFq8YE INSTANCE = new $$Lambda$FacebookSdk$8Xi3kKcv5EqcfpghgczttKFq8YE();

    private /* synthetic */ $$Lambda$FacebookSdk$8Xi3kKcv5EqcfpghgczttKFq8YE() {
    }

    @Override // com.facebook.internal.FeatureManager.Callback
    public final void onCompleted(boolean z) {
        FacebookSdk.m24sdkInitialize$lambda5(z);
    }
}
