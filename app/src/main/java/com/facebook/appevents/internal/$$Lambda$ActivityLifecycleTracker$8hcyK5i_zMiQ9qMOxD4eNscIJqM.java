package com.facebook.appevents.internal;

import com.facebook.internal.FeatureManager;

/* compiled from: lambda */
/* renamed from: com.facebook.appevents.internal.-$$Lambda$ActivityLifecycleTracker$8hcyK5i_zMiQ9qMOxD4eNscIJqM  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ActivityLifecycleTracker$8hcyK5i_zMiQ9qMOxD4eNscIJqM implements FeatureManager.Callback {
    public static final /* synthetic */ $$Lambda$ActivityLifecycleTracker$8hcyK5i_zMiQ9qMOxD4eNscIJqM INSTANCE = new $$Lambda$ActivityLifecycleTracker$8hcyK5i_zMiQ9qMOxD4eNscIJqM();

    private /* synthetic */ $$Lambda$ActivityLifecycleTracker$8hcyK5i_zMiQ9qMOxD4eNscIJqM() {
    }

    @Override // com.facebook.internal.FeatureManager.Callback
    public final void onCompleted(boolean z) {
        ActivityLifecycleTracker.m89startTracking$lambda0(z);
    }
}
