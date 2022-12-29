package com.facebook.internal.instrument.anrreport;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.Utility;
import com.facebook.internal.instrument.InstrumentData;
import com.facebook.internal.instrument.InstrumentUtility;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ANRHandler.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/facebook/internal/instrument/anrreport/ANRHandler;", "", "()V", "MAX_ANR_REPORT_NUM", "", "enabled", "Ljava/util/concurrent/atomic/AtomicBoolean;", "enable", "", "sendANRReports", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class ANRHandler {
    private static final int MAX_ANR_REPORT_NUM = 5;
    public static final ANRHandler INSTANCE = new ANRHandler();
    private static final AtomicBoolean enabled = new AtomicBoolean(false);

    private ANRHandler() {
    }

    @JvmStatic
    public static final synchronized void enable() {
        synchronized (ANRHandler.class) {
            if (enabled.getAndSet(true)) {
                return;
            }
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            if (FacebookSdk.getAutoLogAppEventsEnabled()) {
                ANRHandler aNRHandler = INSTANCE;
                sendANRReports();
            }
            ANRDetector aNRDetector = ANRDetector.INSTANCE;
            ANRDetector.start();
        }
    }

    @JvmStatic
    public static final void sendANRReports() {
        Utility utility = Utility.INSTANCE;
        if (Utility.isDataProcessingRestricted()) {
            return;
        }
        InstrumentUtility instrumentUtility = InstrumentUtility.INSTANCE;
        File[] listAnrReportFiles = InstrumentUtility.listAnrReportFiles();
        ArrayList arrayList = new ArrayList(listAnrReportFiles.length);
        for (File file : listAnrReportFiles) {
            InstrumentData.Builder builder = InstrumentData.Builder.INSTANCE;
            arrayList.add(InstrumentData.Builder.load(file));
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (((InstrumentData) obj).isValid()) {
                arrayList2.add(obj);
            }
        }
        final List sortedWith = CollectionsKt.sortedWith(arrayList2, $$Lambda$ANRHandler$32rOoGQOVWzJ05Z7eDHv0iZy0M.INSTANCE);
        JSONArray jSONArray = new JSONArray();
        Iterator<Integer> it = RangesKt.until(0, Math.min(sortedWith.size(), 5)).iterator();
        while (it.hasNext()) {
            jSONArray.put(sortedWith.get(((IntIterator) it).nextInt()));
        }
        InstrumentUtility instrumentUtility2 = InstrumentUtility.INSTANCE;
        InstrumentUtility.sendReports("anr_reports", jSONArray, new GraphRequest.Callback() { // from class: com.facebook.internal.instrument.anrreport.-$$Lambda$ANRHandler$6UFAPDz95M5FpTysuVr8bpTxa54
            @Override // com.facebook.GraphRequest.Callback
            public final void onCompleted(GraphResponse graphResponse) {
                ANRHandler.m168sendANRReports$lambda5(sortedWith, graphResponse);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendANRReports$lambda-2  reason: not valid java name */
    public static final int m167sendANRReports$lambda2(InstrumentData instrumentData, InstrumentData o2) {
        Intrinsics.checkNotNullExpressionValue(o2, "o2");
        return instrumentData.compareTo(o2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendANRReports$lambda-5  reason: not valid java name */
    public static final void m168sendANRReports$lambda5(List validReports, GraphResponse response) {
        Intrinsics.checkNotNullParameter(validReports, "$validReports");
        Intrinsics.checkNotNullParameter(response, "response");
        try {
            if (response.getError() == null) {
                JSONObject jsonObject = response.getJsonObject();
                if (Intrinsics.areEqual((Object) (jsonObject == null ? null : Boolean.valueOf(jsonObject.getBoolean("success"))), (Object) true)) {
                    Iterator it = validReports.iterator();
                    while (it.hasNext()) {
                        ((InstrumentData) it.next()).clear();
                    }
                }
            }
        } catch (JSONException unused) {
        }
    }
}
