package com.facebook;

import com.facebook.internal.FeatureManager;

/* compiled from: lambda */
/* renamed from: com.facebook.-$$Lambda$FacebookSdk$fGv4uIuB4ckkxwOf8O4RgtXZub4  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FacebookSdk$fGv4uIuB4ckkxwOf8O4RgtXZub4 implements FeatureManager.Callback {
    public static final /* synthetic */ $$Lambda$FacebookSdk$fGv4uIuB4ckkxwOf8O4RgtXZub4 INSTANCE = new $$Lambda$FacebookSdk$fGv4uIuB4ckkxwOf8O4RgtXZub4();

    private /* synthetic */ $$Lambda$FacebookSdk$fGv4uIuB4ckkxwOf8O4RgtXZub4() {
    }

    @Override // com.facebook.internal.FeatureManager.Callback
    public final void onCompleted(boolean z) {
        FacebookSdk.m23sdkInitialize$lambda4(z);
    }
}
