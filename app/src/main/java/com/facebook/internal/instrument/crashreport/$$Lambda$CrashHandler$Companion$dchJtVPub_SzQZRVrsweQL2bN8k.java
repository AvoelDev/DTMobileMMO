package com.facebook.internal.instrument.crashreport;

import com.facebook.internal.instrument.InstrumentData;
import com.facebook.internal.instrument.crashreport.CrashHandler;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.instrument.crashreport.-$$Lambda$CrashHandler$Companion$dchJtVPub_SzQZRVrsweQL2bN8k  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$CrashHandler$Companion$dchJtVPub_SzQZRVrsweQL2bN8k implements Comparator {
    public static final /* synthetic */ $$Lambda$CrashHandler$Companion$dchJtVPub_SzQZRVrsweQL2bN8k INSTANCE = new $$Lambda$CrashHandler$Companion$dchJtVPub_SzQZRVrsweQL2bN8k();

    private /* synthetic */ $$Lambda$CrashHandler$Companion$dchJtVPub_SzQZRVrsweQL2bN8k() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int m169sendExceptionReports$lambda2;
        m169sendExceptionReports$lambda2 = CrashHandler.Companion.m169sendExceptionReports$lambda2((InstrumentData) obj, (InstrumentData) obj2);
        return m169sendExceptionReports$lambda2;
    }
}
