package com.google.firebase.datatransport;

import android.content.Context;
import com.google.android.datatransport.runtime.TransportRuntime;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* compiled from: lambda */
/* renamed from: com.google.firebase.datatransport.-$$Lambda$TransportRegistrar$8MftFhDZTqyNaIMLf3wZTwlt260  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$TransportRegistrar$8MftFhDZTqyNaIMLf3wZTwlt260 implements ComponentFactory {
    public static final /* synthetic */ $$Lambda$TransportRegistrar$8MftFhDZTqyNaIMLf3wZTwlt260 INSTANCE = new $$Lambda$TransportRegistrar$8MftFhDZTqyNaIMLf3wZTwlt260();

    private /* synthetic */ $$Lambda$TransportRegistrar$8MftFhDZTqyNaIMLf3wZTwlt260() {
    }

    @Override // com.google.firebase.components.ComponentFactory
    public final Object create(ComponentContainer componentContainer) {
        return TransportRuntime.initialize((Context) componentContainer.get(Context.class));
    }
}
