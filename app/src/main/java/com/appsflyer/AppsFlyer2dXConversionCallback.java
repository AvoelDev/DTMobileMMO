package com.appsflyer;

import com.appsflyer.deeplink.DeepLinkListener;
import com.appsflyer.deeplink.DeepLinkResult;
import java.util.Map;

/* loaded from: classes3.dex */
public class AppsFlyer2dXConversionCallback implements AppsFlyerConversionListener, DeepLinkListener {
    public native void onAppOpenAttributionNative(Object obj);

    public native void onAttributionFailureNative(Object obj);

    public native void onDeepLinkingNative(DeepLinkResult deepLinkResult);

    public native void onInstallConversionDataLoadedNative(Object obj);

    public native void onInstallConversionFailureNative(Object obj);

    @Override // com.appsflyer.AppsFlyerConversionListener
    public void onConversionDataSuccess(Map<String, Object> map) {
        onInstallConversionDataLoadedNative(map);
    }

    @Override // com.appsflyer.AppsFlyerConversionListener
    public void onConversionDataFail(String str) {
        AFInAppEventType("onAttributionFailure", str);
    }

    @Override // com.appsflyer.AppsFlyerConversionListener
    public void onAppOpenAttribution(Map<String, String> map) {
        onAppOpenAttributionNative(map);
    }

    @Override // com.appsflyer.AppsFlyerConversionListener
    public void onAttributionFailure(String str) {
        AFInAppEventType("onInstallConversionFailure", str);
    }

    @Override // com.appsflyer.deeplink.DeepLinkListener
    public void onDeepLinking(DeepLinkResult deepLinkResult) {
        onDeepLinkingNative(deepLinkResult);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0037, code lost:
        if (r6 == 1) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
        onAttributionFailureNative(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003d, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void AFInAppEventType(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch: org.json.JSONException -> L42
            r0.<init>()     // Catch: org.json.JSONException -> L42
            java.lang.String r1 = "status"
            java.lang.String r2 = "failure"
            r0.put(r1, r2)     // Catch: org.json.JSONException -> L42
            java.lang.String r1 = "data"
            r0.put(r1, r6)     // Catch: org.json.JSONException -> L42
            r6 = -1
            int r1 = r5.hashCode()     // Catch: org.json.JSONException -> L42
            r2 = -1390007222(0xffffffffad262c4a, float:-9.445842E-12)
            r3 = 1
            if (r1 == r2) goto L2c
            r2 = 1050716216(0x3ea0a838, float:0.3137834)
            if (r1 == r2) goto L22
            goto L35
        L22:
            java.lang.String r1 = "onInstallConversionFailure"
            boolean r5 = r5.equals(r1)     // Catch: org.json.JSONException -> L42
            if (r5 == 0) goto L35
            r6 = 0
            goto L35
        L2c:
            java.lang.String r1 = "onAttributionFailure"
            boolean r5 = r5.equals(r1)     // Catch: org.json.JSONException -> L42
            if (r5 == 0) goto L35
            r6 = 1
        L35:
            if (r6 == 0) goto L3e
            if (r6 == r3) goto L3a
            goto L3d
        L3a:
            r4.onAttributionFailureNative(r0)     // Catch: org.json.JSONException -> L42
        L3d:
            return
        L3e:
            r4.onInstallConversionFailureNative(r0)     // Catch: org.json.JSONException -> L42
            return
        L42:
            r5 = move-exception
            r5.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.AppsFlyer2dXConversionCallback.AFInAppEventType(java.lang.String, java.lang.String):void");
    }
}
