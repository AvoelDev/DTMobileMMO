package com.appsflyer.internal;

import android.content.ContentResolver;
import android.os.Build;
import android.provider.Settings;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerProperties;
import com.appsflyer.internal.d;

/* loaded from: classes3.dex */
public final class ab {
    static Boolean AFInAppEventType;
    static String valueOf;

    public static d.e.C0015d AFInAppEventParameterName(ContentResolver contentResolver) {
        String str;
        if (AFKeystoreWrapper() && contentResolver != null && AppsFlyerProperties.getInstance().getString("amazon_aid") == null && "Amazon".equals(Build.MANUFACTURER)) {
            int i = Settings.Secure.getInt(contentResolver, "limit_ad_tracking", 2);
            if (i == 0) {
                return new d.e.C0015d(Settings.Secure.getString(contentResolver, "advertising_id"), Boolean.FALSE);
            }
            if (i == 2) {
                return null;
            }
            try {
                str = Settings.Secure.getString(contentResolver, "advertising_id");
            } catch (Throwable th) {
                AFLogger.AFInAppEventType("Couldn't fetch Amazon Advertising ID (Ad-Tracking is limited!)", th);
                str = "";
            }
            return new d.e.C0015d(str, Boolean.TRUE);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.appsflyer.internal.d.e.C0015d values(android.content.Context r5) {
        /*
            com.appsflyer.AppsFlyerProperties r0 = com.appsflyer.AppsFlyerProperties.getInstance()
            java.lang.String r1 = com.appsflyer.internal.ab.valueOf
            r2 = 1
            if (r1 == 0) goto Lb
            r1 = 1
            goto Lc
        Lb:
            r1 = 0
        Lc:
            r3 = 0
            if (r1 == 0) goto L14
            java.lang.String r5 = com.appsflyer.internal.ab.valueOf
            r0 = r5
        L12:
            r5 = r3
            goto L4e
        L14:
            java.lang.Boolean r4 = com.appsflyer.internal.ab.AFInAppEventType
            if (r4 == 0) goto L1e
            boolean r4 = r4.booleanValue()
            if (r4 != 0) goto L2a
        L1e:
            java.lang.Boolean r4 = com.appsflyer.internal.ab.AFInAppEventType
            if (r4 != 0) goto L4c
            java.lang.String r4 = "collectOAID"
            boolean r2 = r0.getBoolean(r4, r2)
            if (r2 == 0) goto L4c
        L2a:
            com.appsflyer.oaid.OaidClient r2 = new com.appsflyer.oaid.OaidClient     // Catch: java.lang.Throwable -> L45
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L45
            boolean r5 = r0.isEnableLog()     // Catch: java.lang.Throwable -> L45
            r2.setLogging(r5)     // Catch: java.lang.Throwable -> L45
            com.appsflyer.oaid.OaidClient$Info r5 = r2.fetch()     // Catch: java.lang.Throwable -> L45
            if (r5 == 0) goto L4c
            java.lang.String r0 = r5.getId()     // Catch: java.lang.Throwable -> L45
            java.lang.Boolean r5 = r5.getLat()     // Catch: java.lang.Throwable -> L46
            goto L4e
        L45:
            r0 = r3
        L46:
            java.lang.String r5 = "No OAID library"
            com.appsflyer.AFLogger.values(r5)
            goto L12
        L4c:
            r5 = r3
            r0 = r5
        L4e:
            if (r0 == 0) goto L5c
            com.appsflyer.internal.d$e$d r2 = new com.appsflyer.internal.d$e$d
            r2.<init>(r0, r5)
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r1)
            r2.AFInAppEventParameterName = r5
            return r2
        L5c:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.internal.ab.values(android.content.Context):com.appsflyer.internal.d$e$d");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x003e, code lost:
        if (r8.length() == 0) goto L16;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.appsflyer.internal.d.e.C0015d valueOf(android.content.Context r11, java.util.Map<java.lang.String, java.lang.Object> r12) {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.internal.ab.valueOf(android.content.Context, java.util.Map):com.appsflyer.internal.d$e$d");
    }

    private static boolean AFKeystoreWrapper() {
        Boolean bool = AFInAppEventType;
        return bool == null || bool.booleanValue();
    }
}
