package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
/* renamed from: com.google.android.datatransport.runtime.scheduling.persistence.-$$Lambda$SQLiteEventStore$JuRIitnDqysTRRd-YN9uVkpDBM0  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$SQLiteEventStore$JuRIitnDqysTRRdYN9uVkpDBM0 implements SQLiteEventStore.Function {
    public static final /* synthetic */ $$Lambda$SQLiteEventStore$JuRIitnDqysTRRdYN9uVkpDBM0 INSTANCE = new $$Lambda$SQLiteEventStore$JuRIitnDqysTRRdYN9uVkpDBM0();

    private /* synthetic */ $$Lambda$SQLiteEventStore$JuRIitnDqysTRRdYN9uVkpDBM0() {
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore.Function
    public final Object apply(Object obj) {
        return SQLiteEventStore.lambda$clearDb$13((SQLiteDatabase) obj);
    }
}
