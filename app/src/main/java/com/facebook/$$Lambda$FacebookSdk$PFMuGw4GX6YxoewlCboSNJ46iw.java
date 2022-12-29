package com.facebook;

import com.facebook.internal.FeatureManager;

/* compiled from: lambda */
/* renamed from: com.facebook.-$$Lambda$FacebookSdk$PFMuGw4-GX6YxoewlCboSNJ46iw  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FacebookSdk$PFMuGw4GX6YxoewlCboSNJ46iw implements FeatureManager.Callback {
    public static final /* synthetic */ $$Lambda$FacebookSdk$PFMuGw4GX6YxoewlCboSNJ46iw INSTANCE = new $$Lambda$FacebookSdk$PFMuGw4GX6YxoewlCboSNJ46iw();

    private /* synthetic */ $$Lambda$FacebookSdk$PFMuGw4GX6YxoewlCboSNJ46iw() {
    }

    @Override // com.facebook.internal.FeatureManager.Callback
    public final void onCompleted(boolean z) {
        FacebookSdk.m25sdkInitialize$lambda6(z);
    }
}
