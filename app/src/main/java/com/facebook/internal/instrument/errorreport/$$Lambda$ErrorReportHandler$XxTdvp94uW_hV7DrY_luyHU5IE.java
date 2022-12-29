package com.facebook.internal.instrument.errorreport;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.instrument.errorreport.-$$Lambda$ErrorReportHandler$-XxTdvp94uW_hV7DrY_luyHU5IE  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ErrorReportHandler$XxTdvp94uW_hV7DrY_luyHU5IE implements FilenameFilter {
    public static final /* synthetic */ $$Lambda$ErrorReportHandler$XxTdvp94uW_hV7DrY_luyHU5IE INSTANCE = new $$Lambda$ErrorReportHandler$XxTdvp94uW_hV7DrY_luyHU5IE();

    private /* synthetic */ $$Lambda$ErrorReportHandler$XxTdvp94uW_hV7DrY_luyHU5IE() {
    }

    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        boolean m172listErrorReportFiles$lambda3;
        m172listErrorReportFiles$lambda3 = ErrorReportHandler.m172listErrorReportFiles$lambda3(file, str);
        return m172listErrorReportFiles$lambda3;
    }
}
