package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore;

/* compiled from: lambda */
/* renamed from: com.google.android.datatransport.runtime.scheduling.persistence.-$$Lambda$SQLiteEventStore$_zNmnq_oSh5TqXd-sai-M-G51KU  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$SQLiteEventStore$_zNmnq_oSh5TqXdsaiMG51KU implements SQLiteEventStore.Function {
    public static final /* synthetic */ $$Lambda$SQLiteEventStore$_zNmnq_oSh5TqXdsaiMG51KU INSTANCE = new $$Lambda$SQLiteEventStore$_zNmnq_oSh5TqXdsaiMG51KU();

    private /* synthetic */ $$Lambda$SQLiteEventStore$_zNmnq_oSh5TqXdsaiMG51KU() {
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.SQLiteEventStore.Function
    public final Object apply(Object obj) {
        return SQLiteEventStore.lambda$loadActiveContexts$10((SQLiteDatabase) obj);
    }
}
