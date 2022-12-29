package com.facebook;

import com.facebook.internal.FeatureManager;

/* compiled from: lambda */
/* renamed from: com.facebook.-$$Lambda$FacebookSdk$4yZ8An2h0IO8z381mtb9us73H-E  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FacebookSdk$4yZ8An2h0IO8z381mtb9us73HE implements FeatureManager.Callback {
    public static final /* synthetic */ $$Lambda$FacebookSdk$4yZ8An2h0IO8z381mtb9us73HE INSTANCE = new $$Lambda$FacebookSdk$4yZ8An2h0IO8z381mtb9us73HE();

    private /* synthetic */ $$Lambda$FacebookSdk$4yZ8An2h0IO8z381mtb9us73HE() {
    }

    @Override // com.facebook.internal.FeatureManager.Callback
    public final void onCompleted(boolean z) {
        FacebookSdk.m27sdkInitialize$lambda8(z);
    }
}
