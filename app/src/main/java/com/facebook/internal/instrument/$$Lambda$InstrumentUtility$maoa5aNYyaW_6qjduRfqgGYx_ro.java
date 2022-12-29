package com.facebook.internal.instrument;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.instrument.-$$Lambda$InstrumentUtility$maoa5aNYyaW_6qjduRfqgGYx_ro  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$InstrumentUtility$maoa5aNYyaW_6qjduRfqgGYx_ro implements FilenameFilter {
    public static final /* synthetic */ $$Lambda$InstrumentUtility$maoa5aNYyaW_6qjduRfqgGYx_ro INSTANCE = new $$Lambda$InstrumentUtility$maoa5aNYyaW_6qjduRfqgGYx_ro();

    private /* synthetic */ $$Lambda$InstrumentUtility$maoa5aNYyaW_6qjduRfqgGYx_ro() {
    }

    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        boolean m161listAnrReportFiles$lambda1;
        m161listAnrReportFiles$lambda1 = InstrumentUtility.m161listAnrReportFiles$lambda1(file, str);
        return m161listAnrReportFiles$lambda1;
    }
}
