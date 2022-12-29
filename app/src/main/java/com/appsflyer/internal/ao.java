package com.appsflyer.internal;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerLib;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.facebook.internal.ServerProtocol;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class ao extends ak {
    private String AFLogger$LogLevel;
    private String AFVersionDeclaration;
    boolean valueOf;
    b values;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public interface b {
        void AFInAppEventParameterName(String str);

        void AFInAppEventParameterName(Map<String, String> map);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ao(Uri uri, ae aeVar, Context context) {
        super(aeVar, context, "GET");
        String[] strArr;
        this.valueOf = false;
        if (TextUtils.isEmpty(uri.getHost()) || TextUtils.isEmpty(uri.getPath())) {
            return;
        }
        String[] strArr2 = {"onelink.me", "onelnk.com", "app.aflink.com"};
        boolean z = false;
        for (int i = 0; i < 3; i++) {
            if (uri.getHost().contains(strArr2[i])) {
                z = true;
            }
        }
        if (j.AppsFlyer2dXConversionCallback != null) {
            StringBuilder sb = new StringBuilder("Validate if link ");
            sb.append(uri);
            sb.append(" belongs to custom domains: ");
            sb.append(Arrays.asList(j.AppsFlyer2dXConversionCallback));
            AFLogger.AFInAppEventParameterName(sb.toString());
            for (String str : j.AppsFlyer2dXConversionCallback) {
                if (uri.getHost().contains(str) && !TextUtils.isEmpty(str)) {
                    AFLogger.values("Link matches custom domain: ".concat(String.valueOf(str)));
                    this.valueOf = true;
                    z = true;
                }
            }
        }
        String[] split = uri.getPath().split(CookieSpec.PATH_DELIM);
        if (z && split.length == 3) {
            this.AFKeystoreWrapper = split[1];
            this.AFVersionDeclaration = split[2];
            this.AFLogger$LogLevel = uri.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean AFKeystoreWrapper() {
        return (TextUtils.isEmpty(this.AFKeystoreWrapper) || TextUtils.isEmpty(this.AFVersionDeclaration) || this.AFKeystoreWrapper.equals("app")) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.appsflyer.internal.ak
    protected final void AFInAppEventParameterName(HttpsURLConnection httpsURLConnection) {
        httpsURLConnection.setRequestProperty("Af-UUID", this.AppsFlyer2dXConversionCallback);
        String valueOf = String.valueOf(this.getLevel.get("build_number"));
        httpsURLConnection.setRequestProperty("Af-Meta-Sdk-Ver", valueOf);
        httpsURLConnection.setRequestProperty("Af-Meta-Counter", String.valueOf(this.getLevel.get("counter")));
        httpsURLConnection.setRequestProperty("Af-Meta-Model", String.valueOf(this.getLevel.get(DeviceRequestsHelper.DEVICE_INFO_MODEL)));
        httpsURLConnection.setRequestProperty("Af-Meta-Platform", String.valueOf(this.getLevel.get("platformextension")));
        httpsURLConnection.setRequestProperty("Af-Meta-System-Version", String.valueOf(this.getLevel.get(ServerProtocol.DIALOG_PARAM_SDK_VERSION)));
        values(httpsURLConnection, this.AFInAppEventType, this.AppsFlyer2dXConversionCallback, this.AFKeystoreWrapper, this.AFVersionDeclaration, valueOf);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.appsflyer.internal.ak
    protected final String AFInAppEventParameterName() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(AFInAppEventParameterName, AppsFlyerLib.getInstance().getHostPrefix(), ae.values().getHostName()));
        sb.append(CookieSpec.PATH_DELIM);
        sb.append(this.AFKeystoreWrapper);
        sb.append("?id=");
        sb.append(this.AFVersionDeclaration);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.appsflyer.internal.ak
    protected final void AFKeystoreWrapper(String str) {
        try {
            HashMap hashMap = new HashMap();
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, jSONObject.optString(next));
            }
            this.values.AFInAppEventParameterName(hashMap);
        } catch (JSONException e) {
            this.values.AFInAppEventParameterName("Can't parse OneLink data");
            AFLogger.AFInAppEventType("Error while parsing to json ".concat(String.valueOf(str)), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.appsflyer.internal.ak
    protected final void AFInAppEventType() {
        String str = this.AFLogger$LogLevel;
        if (str == null) {
            str = "Can't get OneLink data";
        }
        this.values.AFInAppEventParameterName(str);
    }
}
