package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.Cursor;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
/* renamed from: com.google.android.datatransport.runtime.scheduling.persistence.-$$Lambda$SQLiteEventStore$zvTv0_H2M35sUq4UNH-dCTdACM8  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$SQLiteEventStore$zvTv0_H2M35sUq4UNHdCTdACM8 implements SQLiteEventStore.Function {
    public static final /* synthetic */ $$Lambda$SQLiteEventStore$zvTv0_H2M35sUq4UNHdCTdACM8 INSTANCE = new $$Lambda$SQLiteEventStore$zvTv0_H2M35sUq4UNHdCTdACM8();

    private /* synthetic */ $$Lambda$SQLiteEventStore$zvTv0_H2M35sUq4UNHdCTdACM8() {
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore.Function
    public final Object apply(Object obj) {
        return SQLiteEventStore.lambda$getNextCallTime$5((Cursor) obj);
    }
}
