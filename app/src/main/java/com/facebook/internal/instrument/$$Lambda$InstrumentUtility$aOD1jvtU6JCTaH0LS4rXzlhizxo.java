package com.facebook.internal.instrument;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.instrument.-$$Lambda$InstrumentUtility$aOD1jvtU6JCTaH0LS4rXzlhizxo  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$InstrumentUtility$aOD1jvtU6JCTaH0LS4rXzlhizxo implements FilenameFilter {
    public static final /* synthetic */ $$Lambda$InstrumentUtility$aOD1jvtU6JCTaH0LS4rXzlhizxo INSTANCE = new $$Lambda$InstrumentUtility$aOD1jvtU6JCTaH0LS4rXzlhizxo();

    private /* synthetic */ $$Lambda$InstrumentUtility$aOD1jvtU6JCTaH0LS4rXzlhizxo() {
    }

    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        boolean m163listExceptionReportFiles$lambda3;
        m163listExceptionReportFiles$lambda3 = InstrumentUtility.m163listExceptionReportFiles$lambda3(file, str);
        return m163listExceptionReportFiles$lambda3;
    }
}
