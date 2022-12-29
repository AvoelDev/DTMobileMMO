package com.appsflyer.internal;

import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.AppsFlyerProperties;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.ssjj.fn.common.realname.RealNameConstant;
import com.ssjj.fnsdk.core.listener.FNEvent;
import com.ssjj.fnsdk.core.oaidProvider.IImeiOaidProvider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class aj {
    private static aj valueOf = null;
    private static String values = "https://%smonitorsdk.%s/remote-debug?app_id=";
    private JSONObject AFKeystoreWrapper;
    private int AppsFlyer2dXConversionCallback;
    private boolean AFInAppEventType = true;
    private final List<String> AFVersionDeclaration = new ArrayList();
    private String init = FNEvent.FN_STATE_FAIL;
    private boolean AFLogger$LogLevel = AppsFlyerProperties.getInstance().getBoolean(AppsFlyerProperties.DPM, false);
    boolean AFInAppEventParameterName = true ^ this.AFLogger$LogLevel;
    private boolean getLevel = false;

    private aj() {
        this.AppsFlyer2dXConversionCallback = 0;
        this.AppsFlyer2dXConversionCallback = 0;
    }

    public static aj valueOf() {
        if (valueOf == null) {
            valueOf = new aj();
        }
        return valueOf;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized void AFInAppEventParameterName(String str) {
        this.init = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized void values() {
        this.getLevel = true;
        AFInAppEventType("r_debugging_on", new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())), new String[0]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized void AFInAppEventParameterName() {
        AFInAppEventType("r_debugging_off", new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())), new String[0]);
        this.getLevel = false;
        this.AFInAppEventType = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized void AFInAppEventType() {
        this.AFKeystoreWrapper = null;
        valueOf = null;
    }

    private boolean AFLogger$LogLevel() {
        if (this.AFInAppEventParameterName) {
            return this.AFInAppEventType || this.getLevel;
        }
        return false;
    }

    private synchronized void valueOf(String str, String str2, String str3, String str4, String str5, String str6) {
        try {
            this.AFKeystoreWrapper.put("brand", str);
            this.AFKeystoreWrapper.put(DeviceRequestsHelper.DEVICE_INFO_MODEL, str2);
            this.AFKeystoreWrapper.put("platform", "Android");
            this.AFKeystoreWrapper.put("platform_version", str3);
            if (str4 != null && str4.length() > 0) {
                this.AFKeystoreWrapper.put("advertiserId", str4);
            }
            if (str5 != null && str5.length() > 0) {
                this.AFKeystoreWrapper.put(IImeiOaidProvider.IMEI_KEY, str5);
            }
            if (str6 != null && str6.length() > 0) {
                this.AFKeystoreWrapper.put("android_id", str6);
            }
        } catch (Throwable unused) {
        }
    }

    private synchronized void valueOf(String str, String str2, String str3, String str4) {
        try {
            this.AFKeystoreWrapper.put("sdk_version", str);
            if (str2 != null && str2.length() > 0) {
                this.AFKeystoreWrapper.put("devkey", str2);
            }
            if (str3 != null && str3.length() > 0) {
                this.AFKeystoreWrapper.put("originalAppsFlyerId", str3);
            }
            if (str4 != null && str4.length() > 0) {
                this.AFKeystoreWrapper.put(RealNameConstant.PARAM_PLAYER_UID, str4);
            }
        } catch (Throwable unused) {
        }
    }

    private synchronized void AFKeystoreWrapper(String str, String str2, String str3, String str4) {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    this.AFKeystoreWrapper.put("app_id", str);
                }
            } catch (Throwable unused) {
                return;
            }
        }
        if (str2 != null && str2.length() > 0) {
            this.AFKeystoreWrapper.put("app_version", str2);
        }
        if (str3 != null && str3.length() > 0) {
            this.AFKeystoreWrapper.put(AppsFlyerProperties.CHANNEL, str3);
        }
        if (str4 != null && str4.length() > 0) {
            this.AFKeystoreWrapper.put("preInstall", str4);
        }
    }

    public final synchronized void AFInAppEventType(String str, String str2, String... strArr) {
        String obj;
        if (AFLogger$LogLevel()) {
            if (this.AppsFlyer2dXConversionCallback < 98304) {
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    String join = TextUtils.join(", ", strArr);
                    if (str != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(currentTimeMillis);
                        sb.append(" ");
                        sb.append(Thread.currentThread().getId());
                        sb.append(" _/AppsFlyer_6.3.2 [");
                        sb.append(str);
                        sb.append("] ");
                        sb.append(str2);
                        sb.append(" ");
                        sb.append(join);
                        obj = sb.toString();
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(currentTimeMillis);
                        sb2.append(" ");
                        sb2.append(Thread.currentThread().getId());
                        sb2.append(" ");
                        sb2.append(str2);
                        sb2.append("/AppsFlyer_6.3.2 ");
                        sb2.append(join);
                        obj = sb2.toString();
                    }
                    this.AFVersionDeclaration.add(obj);
                    this.AppsFlyer2dXConversionCallback += obj.length() << 1;
                } catch (Throwable unused) {
                }
            }
        }
    }

    private synchronized String init() {
        String str;
        str = null;
        try {
            this.AFKeystoreWrapper.put("data", new JSONArray((Collection) this.AFVersionDeclaration));
            str = this.AFKeystoreWrapper.toString();
            AFVersionDeclaration();
        } catch (JSONException unused) {
        }
        return str;
    }

    private synchronized void AFInAppEventType(String str, PackageManager packageManager) {
        AppsFlyerProperties appsFlyerProperties = AppsFlyerProperties.getInstance();
        ae values2 = ae.values();
        String string = appsFlyerProperties.getString("remote_debug_static_data");
        if (string != null) {
            try {
                this.AFKeystoreWrapper = new JSONObject(string);
            } catch (Throwable unused) {
            }
        } else {
            this.AFKeystoreWrapper = new JSONObject();
            valueOf(Build.BRAND, Build.MODEL, Build.VERSION.RELEASE, appsFlyerProperties.getString("advertiserId"), values2.AppsFlyer2dXConversionCallback, values2.AFLogger$LogLevel);
            StringBuilder sb = new StringBuilder("6.3.2.");
            sb.append(ae.AFInAppEventParameterName);
            valueOf(sb.toString(), appsFlyerProperties.getString(AppsFlyerProperties.AF_KEY), appsFlyerProperties.getString("KSAppsFlyerId"), appsFlyerProperties.getString(RealNameConstant.PARAM_PLAYER_UID));
            try {
                int i = packageManager.getPackageInfo(str, 0).versionCode;
                AFKeystoreWrapper(str, String.valueOf(i), appsFlyerProperties.getString(AppsFlyerProperties.CHANNEL), appsFlyerProperties.getString("preInstallName"));
            } catch (Throwable unused2) {
            }
            appsFlyerProperties.set("remote_debug_static_data", this.AFKeystoreWrapper.toString());
        }
        try {
            this.AFKeystoreWrapper.put("launch_counter", this.init);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String[] AFInAppEventType(String str, StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr == null) {
            return new String[]{str};
        }
        String[] strArr = new String[stackTraceElementArr.length + 1];
        strArr[0] = str;
        for (int i = 1; i < stackTraceElementArr.length; i++) {
            strArr[i] = stackTraceElementArr[i].toString();
        }
        return strArr;
    }

    private synchronized void AFVersionDeclaration() {
        this.AFVersionDeclaration.clear();
        this.AppsFlyer2dXConversionCallback = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized void AFKeystoreWrapper() {
        this.AFInAppEventType = false;
        AFVersionDeclaration();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean getLevel() {
        return this.getLevel;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void valueOf(String str, PackageManager packageManager) {
        try {
            if (valueOf == null) {
                valueOf = new aj();
            }
            valueOf.AFInAppEventType(str, packageManager);
            if (valueOf == null) {
                valueOf = new aj();
            }
            String init = valueOf.init();
            bi biVar = new bi();
            biVar.onDeepLinkingNative = init;
            biVar.onConversionDataFail = AppsFlyerLib.getInstance().isStopped();
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(values, AppsFlyerLib.getInstance().getHostPrefix(), ae.values().getHostName()));
            sb.append(str);
            new Thread(new m((bf) biVar.AFInAppEventParameterName(sb.toString()))).start();
        } catch (Throwable th) {
            AFLogger.values(th);
        }
    }
}
