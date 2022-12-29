package com.appsflyer.internal;

import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerProperties;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class ar {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static JSONObject AFKeystoreWrapper(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (Throwable th) {
            th = th;
            jSONObject = null;
        }
        try {
            boolean z = AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.DPM, false);
            if (jSONObject.optBoolean("monitor", false) && !z) {
                aj.valueOf().values();
            } else {
                aj.valueOf().AFKeystoreWrapper();
                aj.valueOf().AFInAppEventParameterName();
            }
            if (jSONObject.has("ol_id")) {
                String optString = jSONObject.optString("ol_scheme", null);
                String optString2 = jSONObject.optString("ol_domain", null);
                String optString3 = jSONObject.optString("ol_ver", null);
                if (optString != null) {
                    AppsFlyerProperties.getInstance().set(AppsFlyerProperties.ONELINK_SCHEME, optString);
                }
                if (optString2 != null) {
                    AppsFlyerProperties.getInstance().set(AppsFlyerProperties.ONELINK_DOMAIN, optString2);
                }
                if (optString3 != null) {
                    AppsFlyerProperties.getInstance().set("onelinkVersion", optString3);
                }
            }
        } catch (Throwable th2) {
            th = th2;
            AFLogger.AFInAppEventType(th.getMessage(), th);
            aj.valueOf().AFKeystoreWrapper();
            aj.valueOf().AFInAppEventParameterName();
            return jSONObject;
        }
        return jSONObject;
    }
}
