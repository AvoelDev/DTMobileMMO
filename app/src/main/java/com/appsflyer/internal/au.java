package com.appsflyer.internal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes3.dex */
public final class au {
    public final Application AFKeystoreWrapper;
    public final SharedPreferences valueOf;

    public au(Context context) {
        this.AFKeystoreWrapper = (Application) context.getApplicationContext();
        this.valueOf = ae.values(this.AFKeystoreWrapper);
    }

    public final boolean AFInAppEventType() {
        ae.values();
        return ae.valueOf(this.valueOf, "appsFlyerCount", false) == 0;
    }
}
