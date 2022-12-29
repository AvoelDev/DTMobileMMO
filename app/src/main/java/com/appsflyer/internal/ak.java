package com.appsflyer.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.text.AndroidCharacter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewConfiguration;
import com.appsflyer.AppsFlyerProperties;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.facebook.internal.ServerProtocol;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;

/* loaded from: classes3.dex */
public abstract class ak implements Runnable {
    protected static String AFInAppEventParameterName = null;
    private static int AFLogger$LogLevel = 0;
    private static int AFVersionDeclaration = 0;
    private static int onAppOpenAttributionNative = 1;
    private static short[] onAttributionFailureNative;
    private static int onDeepLinkingNative;
    private static int onInstallConversionDataLoadedNative;
    private static byte[] onInstallConversionFailureNative;
    private static String valueOf;
    public final String AFInAppEventType;
    public String AFKeystoreWrapper;
    public final String AppsFlyer2dXConversionCallback = UUID.randomUUID().toString();
    public final Map<String, Object> getLevel = valueOf();
    private final Context init;
    private final ae values;

    static void values() {
        AFLogger$LogLevel = 91;
        AFVersionDeclaration = -784549513;
        onDeepLinkingNative = -1400650653;
        onInstallConversionFailureNative = new byte[]{-79, -13, -3, 1, 19, -13, 7, -2, 22, 38, -57, 37};
    }

    protected abstract String AFInAppEventParameterName();

    protected abstract void AFInAppEventParameterName(HttpsURLConnection httpsURLConnection) throws JSONException, IOException;

    protected abstract void AFInAppEventType();

    protected abstract void AFKeystoreWrapper(String str);

    static {
        values();
        valueOf = "v2";
        StringBuilder sb = new StringBuilder("https://%sonelink.%s/shortlink-sdk/");
        sb.append(valueOf);
        AFInAppEventParameterName = sb.toString();
        int i = onInstallConversionDataLoadedNative + 43;
        onAppOpenAttributionNative = i % 128;
        if (i % 2 != 0) {
            return;
        }
        int i2 = 74 / 0;
    }

    public ak(ae aeVar, Context context, String str) {
        this.values = aeVar;
        this.init = context;
        this.AFInAppEventType = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void values(HttpsURLConnection httpsURLConnection, String... strArr) {
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        arrayList.add(1, valueOf);
        String join = TextUtils.join("\u2063", arrayList.toArray());
        StringBuilder sb = new StringBuilder();
        sb.append(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY));
        sb.append(this.AppsFlyer2dXConversionCallback);
        sb.append(valueOf);
        httpsURLConnection.setRequestProperty(valueOf((byte) (ViewConfiguration.getPressedStateDuration() >> 16), 1400650718 - (ViewConfiguration.getWindowTouchSlop() >> 8), (short) View.getDefaultSize(0, 0), Color.rgb(0, 0, 0) + 801326729, (-88) - AndroidCharacter.getEastAsianWidth('0')).intern(), af.values(join, sb.toString()));
        int i = onAppOpenAttributionNative + 33;
        onInstallConversionDataLoadedNative = i % 128;
        int i2 = i % 2;
    }

    private Map<String, Object> valueOf() {
        HashMap hashMap = new HashMap();
        hashMap.put("build_number", "6.3.2");
        hashMap.put("counter", Integer.valueOf(ae.valueOf(ae.values(this.init), "appsFlyerCount", false)));
        hashMap.put(DeviceRequestsHelper.DEVICE_INFO_MODEL, Build.MODEL);
        hashMap.put("brand", Build.BRAND);
        hashMap.put(ServerProtocol.DIALOG_PARAM_SDK_VERSION, Integer.toString(Build.VERSION.SDK_INT));
        try {
            hashMap.put("app_version_name", this.init.getPackageManager().getPackageInfo(this.init.getPackageName(), 0).versionName);
            int i = onAppOpenAttributionNative + 101;
            onInstallConversionDataLoadedNative = i % 128;
            if (i % 2 != 0) {
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        hashMap.put("app_id", this.init.getPackageName());
        hashMap.put("platformextension", new al().AFInAppEventParameterName());
        return hashMap;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00b7  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r8 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = r8.AFInAppEventParameterName()
            java.lang.String r2 = java.lang.String.valueOf(r1)
            java.lang.String r3 = "oneLinkUrl: "
            java.lang.String r2 = r3.concat(r2)
            com.appsflyer.AFLogger.AFInAppEventParameterName(r2)
            r2 = 1
            r3 = 0
            java.net.URL r4 = new java.net.URL     // Catch: java.lang.Throwable -> L74
            r4.<init>(r1)     // Catch: java.lang.Throwable -> L74
            java.net.URLConnection r4 = r4.openConnection()     // Catch: java.lang.Throwable -> L74
            javax.net.ssl.HttpsURLConnection r4 = (javax.net.ssl.HttpsURLConnection) r4     // Catch: java.lang.Throwable -> L74
            java.lang.String r5 = "content-type"
            java.lang.String r6 = "application/json"
            r4.setRequestProperty(r5, r6)     // Catch: java.lang.Throwable -> L74
            r5 = 3000(0xbb8, float:4.204E-42)
            r4.setReadTimeout(r5)     // Catch: java.lang.Throwable -> L74
            r4.setConnectTimeout(r5)     // Catch: java.lang.Throwable -> L74
            java.lang.String r5 = r8.AFInAppEventType     // Catch: java.lang.Throwable -> L74
            r4.setRequestMethod(r5)     // Catch: java.lang.Throwable -> L74
            r8.AFInAppEventParameterName(r4)     // Catch: java.lang.Throwable -> L74
            int r5 = r4.getResponseCode()     // Catch: java.lang.Throwable -> L74
            java.lang.String r4 = com.appsflyer.internal.ae.AFKeystoreWrapper(r4)     // Catch: java.lang.Throwable -> L74
            r6 = 200(0xc8, float:2.8E-43)
            if (r5 != r6) goto L45
            r6 = 1
            goto L46
        L45:
            r6 = 0
        L46:
            if (r6 == 0) goto L58
            int r5 = com.appsflyer.internal.ak.onInstallConversionDataLoadedNative
            int r5 = r5 + 107
            int r6 = r5 % 128
            com.appsflyer.internal.ak.onAppOpenAttributionNative = r6
            int r5 = r5 % 2
            java.lang.String r5 = "Status 200 ok"
            com.appsflyer.AFLogger.AFKeystoreWrapper(r5)     // Catch: java.lang.Throwable -> L6f
            goto L9c
        L58:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6f
            java.lang.String r6 = "Response code = "
            r0.<init>(r6)     // Catch: java.lang.Throwable -> L6f
            r0.append(r5)     // Catch: java.lang.Throwable -> L6f
            java.lang.String r5 = " content = "
            r0.append(r5)     // Catch: java.lang.Throwable -> L6f
            r0.append(r4)     // Catch: java.lang.Throwable -> L6f
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L6f
            goto L9c
        L6f:
            r0 = move-exception
            r7 = r4
            r4 = r0
            r0 = r7
            goto L75
        L74:
            r4 = move-exception
        L75:
            java.lang.String r5 = java.lang.String.valueOf(r1)
            java.lang.String r6 = "Error while calling "
            java.lang.String r5 = r6.concat(r5)
            com.appsflyer.AFLogger.AFInAppEventType(r5, r4)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r6)
            r5.append(r1)
            java.lang.String r1 = " stacktrace: "
            r5.append(r1)
            java.lang.String r1 = r4.toString()
            r5.append(r1)
            java.lang.String r1 = r5.toString()
            r4 = r0
            r0 = r1
        L9c:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto La3
            goto La4
        La3:
            r2 = 0
        La4:
            if (r2 == 0) goto Lb7
            java.lang.String r0 = java.lang.String.valueOf(r4)
            java.lang.String r1 = "Connection call succeeded: "
            java.lang.String r0 = r1.concat(r0)
            com.appsflyer.AFLogger.AFKeystoreWrapper(r0)
            r8.AFKeystoreWrapper(r4)
            return
        Lb7:
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r1 = "Connection error: "
            java.lang.String r0 = r1.concat(r0)
            com.appsflyer.AFLogger.init(r0)
            r8.AFInAppEventType()
            int r0 = com.appsflyer.internal.ak.onInstallConversionDataLoadedNative
            int r0 = r0 + 115
            int r1 = r0 % 128
            com.appsflyer.internal.ak.onAppOpenAttributionNative = r1
            int r0 = r0 % 2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.internal.ak.run():void");
    }

    private static String valueOf(byte b, int i, short s, int i2, int i3) {
        int i4;
        int i5;
        StringBuilder sb = new StringBuilder();
        int i6 = i3 + AFLogger$LogLevel;
        boolean z = (i6 == -1 ? (char) 17 : ';') == 17;
        if (z) {
            if (onInstallConversionFailureNative != null) {
                i6 = (byte) (onInstallConversionFailureNative[AFVersionDeclaration + i2] + AFLogger$LogLevel);
            } else {
                i6 = (short) (onAttributionFailureNative[AFVersionDeclaration + i2] + AFLogger$LogLevel);
            }
        }
        if (i6 > 0) {
            int i7 = ((i2 + i6) - 2) + AFVersionDeclaration + (z ? 1 : 0);
            char c = (char) (i + onDeepLinkingNative);
            sb.append(c);
            for (int i8 = 1; i8 < i6; i8++) {
                int i9 = onAppOpenAttributionNative + 45;
                onInstallConversionDataLoadedNative = i9 % 128;
                int i10 = i9 % 2;
                if ((onInstallConversionFailureNative != null ? (char) 19 : '.') != 19) {
                    i4 = i7 - 1;
                    c = (char) (c + (((short) (onAttributionFailureNative[i7] + s)) ^ b));
                    int i11 = onAppOpenAttributionNative + 11;
                    onInstallConversionDataLoadedNative = i11 % 128;
                    int i12 = i11 % 2;
                } else {
                    int i13 = onAppOpenAttributionNative + 17;
                    onInstallConversionDataLoadedNative = i13 % 128;
                    if (i13 % 2 != 0) {
                        i4 = i7 + 119;
                        i5 = c >> (((byte) (onInstallConversionFailureNative[i7] >>> s)) | b);
                    } else {
                        i4 = i7 - 1;
                        i5 = c + (((byte) (onInstallConversionFailureNative[i7] + s)) ^ b);
                    }
                    c = (char) i5;
                }
                i7 = i4;
                sb.append(c);
            }
        }
        String obj = sb.toString();
        int i14 = onAppOpenAttributionNative + 25;
        onInstallConversionDataLoadedNative = i14 % 128;
        int i15 = i14 % 2;
        return obj;
    }
}
