package com.facebook.internal.instrument;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.instrument.-$$Lambda$InstrumentUtility$KKWKk7SozTv_PhaG8Q_Y9XtIc94  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$InstrumentUtility$KKWKk7SozTv_PhaG8Q_Y9XtIc94 implements FilenameFilter {
    public static final /* synthetic */ $$Lambda$InstrumentUtility$KKWKk7SozTv_PhaG8Q_Y9XtIc94 INSTANCE = new $$Lambda$InstrumentUtility$KKWKk7SozTv_PhaG8Q_Y9XtIc94();

    private /* synthetic */ $$Lambda$InstrumentUtility$KKWKk7SozTv_PhaG8Q_Y9XtIc94() {
    }

    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        boolean m162listExceptionAnalysisReportFiles$lambda2;
        m162listExceptionAnalysisReportFiles$lambda2 = InstrumentUtility.m162listExceptionAnalysisReportFiles$lambda2(file, str);
        return m162listExceptionAnalysisReportFiles$lambda2;
    }
}
