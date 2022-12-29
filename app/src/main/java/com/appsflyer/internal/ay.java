package com.appsflyer.internal;

import android.app.Application;
import com.appsflyer.AFLogger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public final class ay implements Runnable {
    private static String valueOf = "https://%sgcdsdk.%s/install_data/v4.0/";
    private static final List<String> values = Arrays.asList("googleplay", "playstore", "googleplaystore");
    private final String AFInAppEventParameterName;
    final ScheduledExecutorService AFInAppEventType;
    private final Application AFKeystoreWrapper;
    private final ae AFLogger$LogLevel;
    private final int AFVersionDeclaration;
    private final AtomicInteger getLevel;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ay(ae aeVar, Application application, String str) {
        if (k.valueOf == null) {
            k.valueOf = new k();
        }
        this.AFInAppEventType = k.valueOf.AFInAppEventParameterName();
        this.getLevel = new AtomicInteger(0);
        this.AFLogger$LogLevel = aeVar;
        this.AFKeystoreWrapper = application;
        this.AFInAppEventParameterName = str;
        this.AFVersionDeclaration = 0;
    }

    private ay(ay ayVar) {
        if (k.valueOf == null) {
            k.valueOf = new k();
        }
        this.AFInAppEventType = k.valueOf.AFInAppEventParameterName();
        this.getLevel = new AtomicInteger(0);
        this.AFLogger$LogLevel = ayVar.AFLogger$LogLevel;
        this.AFKeystoreWrapper = ayVar.AFKeystoreWrapper;
        this.AFInAppEventParameterName = ayVar.AFInAppEventParameterName;
        this.AFVersionDeclaration = ayVar.AFVersionDeclaration + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void AFInAppEventType(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("[GCD-A02] Calling onConversionDataSuccess with:\n");
        sb.append(map.toString());
        AFLogger.values(sb.toString());
        ae.valueOf.onConversionDataSuccess(map);
    }

    public static void AFInAppEventParameterName(String str) {
        if (ae.valueOf != null) {
            AFLogger.values("[GCD-A02] Calling onConversionFailure with:\n".concat(String.valueOf(str)));
            ae.valueOf.onConversionDataFail(str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x028a, code lost:
        if (r8 == null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x02bc, code lost:
        r16.AFInAppEventType.shutdown();
        com.appsflyer.AFLogger.values("[GCD-A03] Server retrieving attempt finished");
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x02c6, code lost:
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0198 A[Catch: all -> 0x028d, TryCatch #2 {all -> 0x028d, blocks: (B:26:0x00f5, B:37:0x0149, B:34:0x0135, B:36:0x0139, B:38:0x0158, B:40:0x0198, B:42:0x01a6, B:44:0x01c0, B:46:0x01c6, B:47:0x01d3, B:50:0x01dd, B:52:0x01e3, B:53:0x01f7, B:54:0x0208, B:56:0x020e, B:57:0x0221, B:60:0x0233, B:62:0x023e, B:64:0x0242, B:66:0x024a, B:68:0x025e, B:72:0x026b, B:71:0x0265, B:61:0x0239), top: B:101:0x00f5, inners: #1 }] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            Method dump skipped, instructions count: 734
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.internal.ay.run():void");
    }
}
