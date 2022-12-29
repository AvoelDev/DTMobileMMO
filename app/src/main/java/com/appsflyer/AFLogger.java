package com.appsflyer;

import android.util.Log;
import com.appsflyer.internal.ae;
import com.appsflyer.internal.aj;
import com.appsflyer.internal.at;

/* loaded from: classes3.dex */
public final class AFLogger {
    private static final long values = System.currentTimeMillis();

    public static void AFInAppEventType(String str, boolean z) {
        if (AFInAppEventType(LogLevel.INFO)) {
            Log.i("AppsFlyer_6.3.2", AFInAppEventParameterName(str, false));
        }
        if (z) {
            aj.valueOf().AFInAppEventType(null, "I", AFInAppEventParameterName(str, true));
        }
    }

    private static String AFInAppEventParameterName(String str, boolean z) {
        if (str == null) {
            str = "null";
        }
        if (z || LogLevel.VERBOSE.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel())) {
            StringBuilder sb = new StringBuilder("(");
            sb.append(System.currentTimeMillis() - values);
            sb.append(") [");
            sb.append(Thread.currentThread().getName());
            sb.append("] ");
            sb.append(str);
            return sb.toString();
        }
        return str;
    }

    private static void AFKeystoreWrapper(String str, Throwable th, boolean z, boolean z2) {
        if (AFInAppEventType(LogLevel.ERROR)) {
            if (str == null) {
                StringBuilder sb = new StringBuilder();
                sb.append(th.getClass().getSimpleName());
                sb.append(" at ");
                sb.append(th.getStackTrace()[0].toString());
                str = sb.toString();
            }
            String AFInAppEventParameterName = AFInAppEventParameterName(str, false);
            if (z2) {
                Log.e("AppsFlyer_6.3.2", AFInAppEventParameterName, th);
            } else if (z) {
                Log.d("AppsFlyer_6.3.2", AFInAppEventParameterName);
            }
        }
        aj valueOf = aj.valueOf();
        Throwable cause = th.getCause();
        valueOf.AFInAppEventType("exception", th.getClass().getSimpleName(), aj.AFInAppEventType(cause == null ? th.getMessage() : cause.getMessage(), cause == null ? th.getStackTrace() : cause.getStackTrace()));
        if (at.AFInAppEventParameterName != null) {
            ae.values(at.AFInAppEventParameterName).edit().putLong("exception_number", (at.AFInAppEventParameterName == null ? -1L : ae.values(at.AFInAppEventParameterName).getLong("exception_number", 0L)) + 1).apply();
        }
    }

    public static void valueOf(String str) {
        if (AFInAppEventType(LogLevel.WARNING)) {
            Log.w("AppsFlyer_6.3.2", AFInAppEventParameterName(str, false));
        }
        aj.valueOf().AFInAppEventType(null, "W", AFInAppEventParameterName(str, true));
    }

    public static void AFInAppEventParameterName(String str) {
        if (AFInAppEventType(LogLevel.VERBOSE)) {
            Log.v("AppsFlyer_6.3.2", AFInAppEventParameterName(str, false));
        }
        aj.valueOf().AFInAppEventType(null, "V", AFInAppEventParameterName(str, true));
    }

    private static boolean AFInAppEventType(LogLevel logLevel) {
        return logLevel.getLevel() <= AppsFlyerProperties.getInstance().getInt("logLevel", LogLevel.NONE.getLevel());
    }

    public static void AFInAppEventType(String str) {
        if (!valueOf()) {
            Log.d("AppsFlyer_6.3.2", AFInAppEventParameterName(str, false));
        }
        aj.valueOf().AFInAppEventType(null, "F", str);
    }

    private static boolean valueOf() {
        return AppsFlyerProperties.getInstance().isLogsDisabledCompletely();
    }

    public static void AFKeystoreWrapper(String str) {
        AFInAppEventType(str, true);
    }

    public static void AFInAppEventType(String str, Throwable th) {
        AFKeystoreWrapper(str, th, true, false);
    }

    public static void AFInAppEventParameterName(String str, Throwable th) {
        AFKeystoreWrapper(str, th, true, true);
    }

    public static void values(Throwable th) {
        AFKeystoreWrapper(null, th, false, false);
    }

    public static void init(String str) {
        valueOf(str);
    }

    /* loaded from: classes3.dex */
    public enum LogLevel {
        NONE(0),
        ERROR(1),
        WARNING(2),
        INFO(3),
        DEBUG(4),
        VERBOSE(5);
        
        private int AFInAppEventParameterName;

        LogLevel(int i) {
            this.AFInAppEventParameterName = i;
        }

        public final int getLevel() {
            return this.AFInAppEventParameterName;
        }
    }

    public static void values(String str) {
        if (AFInAppEventType(LogLevel.DEBUG)) {
            Log.d("AppsFlyer_6.3.2", AFInAppEventParameterName(str, false));
        }
        aj.valueOf().AFInAppEventType(null, "D", AFInAppEventParameterName(str, true));
    }
}
