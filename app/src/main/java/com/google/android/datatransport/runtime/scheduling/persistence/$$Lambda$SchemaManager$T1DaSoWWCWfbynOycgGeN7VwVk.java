package com.google.android.datatransport.runtime.scheduling.persistence;

import android.database.sqlite.SQLiteDatabase;
import com.google.android.datatransport.runtime.scheduling.persistence.SchemaManager;

/* compiled from: lambda */
/* renamed from: com.google.android.datatransport.runtime.scheduling.persistence.-$$Lambda$SchemaManager$T1DaSoWWCWfbynOycgGeN7Vw-Vk  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$SchemaManager$T1DaSoWWCWfbynOycgGeN7VwVk implements SchemaManager.Migration {
    public static final /* synthetic */ $$Lambda$SchemaManager$T1DaSoWWCWfbynOycgGeN7VwVk INSTANCE = new $$Lambda$SchemaManager$T1DaSoWWCWfbynOycgGeN7VwVk();

    private /* synthetic */ $$Lambda$SchemaManager$T1DaSoWWCWfbynOycgGeN7VwVk() {
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.SchemaManager.Migration
    public final void upgrade(SQLiteDatabase sQLiteDatabase) {
        SchemaManager.lambda$static$4(sQLiteDatabase);
    }
}
