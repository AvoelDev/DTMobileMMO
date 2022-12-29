package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
/* renamed from: com.google.android.datatransport.runtime.scheduling.persistence.-$$Lambda$SQLiteEventStore$owZ5jXLTVeOk7pcUNJL0LV1Qcms  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$SQLiteEventStore$owZ5jXLTVeOk7pcUNJL0LV1Qcms implements SQLiteEventStore.Function {
    public static final /* synthetic */ $$Lambda$SQLiteEventStore$owZ5jXLTVeOk7pcUNJL0LV1Qcms INSTANCE = new $$Lambda$SQLiteEventStore$owZ5jXLTVeOk7pcUNJL0LV1Qcms();

    private /* synthetic */ $$Lambda$SQLiteEventStore$owZ5jXLTVeOk7pcUNJL0LV1Qcms() {
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore.Function
    public final Object apply(Object obj) {
        Boolean valueOf;
        Cursor cursor = (Cursor) obj;
        valueOf = Boolean.valueOf(r0.getCount() > 0);
        return valueOf;
    }
}
