package com.google.firebase.messaging;

import com.google.android.datatransport.Transformer;
import com.google.firebase.messaging.reporting.MessagingClientEventExtension;

/* compiled from: lambda */
/* renamed from: com.google.firebase.messaging.-$$Lambda$uEJG-TB5tb-7m58JwTwENhOn6oY  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$uEJGTB5tb7m58JwTwENhOn6oY implements Transformer {
    public static final /* synthetic */ $$Lambda$uEJGTB5tb7m58JwTwENhOn6oY INSTANCE = new $$Lambda$uEJGTB5tb7m58JwTwENhOn6oY();

    private /* synthetic */ $$Lambda$uEJGTB5tb7m58JwTwENhOn6oY() {
    }

    @Override // com.google.android.datatransport.Transformer
    public final Object apply(Object obj) {
        return ((MessagingClientEventExtension) obj).toByteArray();
    }
}
