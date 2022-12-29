package com.facebook.internal.instrument.anrreport;

import com.facebook.internal.instrument.InstrumentData;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.instrument.anrreport.-$$Lambda$ANRHandler$32rOoGQOVWzJ05Z7-eDHv0iZy0M  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ANRHandler$32rOoGQOVWzJ05Z7eDHv0iZy0M implements Comparator {
    public static final /* synthetic */ $$Lambda$ANRHandler$32rOoGQOVWzJ05Z7eDHv0iZy0M INSTANCE = new $$Lambda$ANRHandler$32rOoGQOVWzJ05Z7eDHv0iZy0M();

    private /* synthetic */ $$Lambda$ANRHandler$32rOoGQOVWzJ05Z7eDHv0iZy0M() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int m167sendANRReports$lambda2;
        m167sendANRReports$lambda2 = ANRHandler.m167sendANRReports$lambda2((InstrumentData) obj, (InstrumentData) obj2);
        return m167sendANRReports$lambda2;
    }
}
