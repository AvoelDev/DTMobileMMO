package com.appsflyer.internal;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes3.dex */
final class c {
    private IntentFilter AFInAppEventParameterName = new IntentFilter("android.intent.action.BATTERY_CHANGED");

    /* renamed from: com.appsflyer.internal.c$c  reason: collision with other inner class name */
    /* loaded from: classes3.dex */
    static final class C0014c {
        static final c AFKeystoreWrapper = new c();
    }

    c() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final e values(Context context) {
        String str = null;
        float f = 0.0f;
        try {
            Intent registerReceiver = context.registerReceiver(null, this.AFInAppEventParameterName);
            if (registerReceiver != null) {
                if (2 == registerReceiver.getIntExtra("status", -1)) {
                    int intExtra = registerReceiver.getIntExtra("plugged", -1);
                    str = intExtra != 1 ? intExtra != 2 ? intExtra != 4 ? "other" : "wireless" : "usb" : "ac";
                } else {
                    str = "no";
                }
                int intExtra2 = registerReceiver.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1);
                int intExtra3 = registerReceiver.getIntExtra("scale", -1);
                if (-1 != intExtra2 && -1 != intExtra3) {
                    f = (intExtra2 * 100.0f) / intExtra3;
                }
            }
        } catch (Throwable unused) {
        }
        return new e(f, str);
    }

    /* loaded from: classes3.dex */
    static final class e {
        final String AFKeystoreWrapper;
        final float values;

        e(float f, String str) {
            this.values = f;
            this.AFKeystoreWrapper = str;
        }
    }
}
