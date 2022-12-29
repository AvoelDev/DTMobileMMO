package com.google.firebase.messaging;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import org.apache.commons.httpclient.HttpStatus;

/* compiled from: lambda */
/* renamed from: com.google.firebase.messaging.-$$Lambda$FcmBroadcastProcessor$5Y63K14lse0aDUI-3iypzwmhKrc  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FcmBroadcastProcessor$5Y63K14lse0aDUI3iypzwmhKrc implements Continuation {
    public static final /* synthetic */ $$Lambda$FcmBroadcastProcessor$5Y63K14lse0aDUI3iypzwmhKrc INSTANCE = new $$Lambda$FcmBroadcastProcessor$5Y63K14lse0aDUI3iypzwmhKrc();

    private /* synthetic */ $$Lambda$FcmBroadcastProcessor$5Y63K14lse0aDUI3iypzwmhKrc() {
    }

    @Override // com.google.android.gms.tasks.Continuation
    public final Object then(Task task) {
        Integer valueOf;
        valueOf = Integer.valueOf((int) HttpStatus.SC_FORBIDDEN);
        return valueOf;
    }
}
