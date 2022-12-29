package com.facebook.appevents.codeless;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.codeless.ViewIndexer;

/* compiled from: lambda */
/* renamed from: com.facebook.appevents.codeless.-$$Lambda$ViewIndexer$Companion$cEX9Z2yPsmYT9RtCEpi7naJzBYw  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ViewIndexer$Companion$cEX9Z2yPsmYT9RtCEpi7naJzBYw implements GraphRequest.Callback {
    public static final /* synthetic */ $$Lambda$ViewIndexer$Companion$cEX9Z2yPsmYT9RtCEpi7naJzBYw INSTANCE = new $$Lambda$ViewIndexer$Companion$cEX9Z2yPsmYT9RtCEpi7naJzBYw();

    private /* synthetic */ $$Lambda$ViewIndexer$Companion$cEX9Z2yPsmYT9RtCEpi7naJzBYw() {
    }

    @Override // com.facebook.GraphRequest.Callback
    public final void onCompleted(GraphResponse graphResponse) {
        ViewIndexer.Companion.m77buildAppIndexingRequest$lambda0(graphResponse);
    }
}
