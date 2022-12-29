package com.appsflyer.internal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public final class z {
    private static final BitSet AppsFlyer2dXConversionCallback = new BitSet(6);
    private static final Handler getLevel = new Handler(Looper.getMainLooper());
    private static volatile z init;
    private final SensorManager onAppOpenAttributionNative;
    private boolean onDeepLinkingNative;
    final Handler valueOf;
    boolean values;
    final Object AFInAppEventParameterName = new Object();
    private final Map<x, x> onInstallConversionFailureNative = new HashMap(AppsFlyer2dXConversionCallback.size());
    private final Map<x, Map<String, Object>> onInstallConversionDataLoadedNative = new ConcurrentHashMap(AppsFlyer2dXConversionCallback.size());
    final Runnable AFKeystoreWrapper = new Runnable() { // from class: com.appsflyer.internal.z.1
        @Override // java.lang.Runnable
        public final void run() {
            synchronized (z.this.AFInAppEventParameterName) {
                final z zVar = z.this;
                zVar.AFLogger$LogLevel.execute(new Runnable() { // from class: com.appsflyer.internal.z.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        try {
                            for (Sensor sensor : z.this.onAppOpenAttributionNative.getSensorList(-1)) {
                                if (z.values(sensor.getType())) {
                                    x xVar = new x(sensor, z.this.AFLogger$LogLevel);
                                    if (!z.this.onInstallConversionFailureNative.containsKey(xVar)) {
                                        z.this.onInstallConversionFailureNative.put(xVar, xVar);
                                    }
                                    z.this.onAppOpenAttributionNative.registerListener((SensorEventListener) z.this.onInstallConversionFailureNative.get(xVar), sensor, 0);
                                }
                            }
                        } catch (Throwable unused) {
                        }
                        z.this.onDeepLinkingNative = true;
                    }
                });
                z.this.valueOf.postDelayed(z.this.onConversionDataSuccess, 100L);
                z.this.values = true;
            }
        }
    };
    final Runnable AFInAppEventType = new Runnable() { // from class: com.appsflyer.internal.z.4
        @Override // java.lang.Runnable
        public final void run() {
            synchronized (z.this.AFInAppEventParameterName) {
                z zVar = z.this;
                zVar.AFLogger$LogLevel.execute(new AnonymousClass10());
            }
        }
    };
    final Runnable AFVersionDeclaration = new Runnable() { // from class: com.appsflyer.internal.z.5
        @Override // java.lang.Runnable
        public final void run() {
            synchronized (z.this.AFInAppEventParameterName) {
                if (z.this.values) {
                    z.this.valueOf.removeCallbacks(z.this.AFKeystoreWrapper);
                    z.this.valueOf.removeCallbacks(z.this.AFInAppEventType);
                    z zVar = z.this;
                    zVar.AFLogger$LogLevel.execute(new AnonymousClass10());
                    z.this.values = false;
                }
            }
        }
    };
    private int onAttributionFailureNative = 1;
    private long onAppOpenAttribution = 0;
    private final Runnable onConversionDataSuccess = new Runnable() { // from class: com.appsflyer.internal.z.2
        @Override // java.lang.Runnable
        public final void run() {
            synchronized (z.this.AFInAppEventParameterName) {
                if (z.this.onAttributionFailureNative == 0) {
                    z.this.onAttributionFailureNative = 1;
                }
                z.this.valueOf.postDelayed(z.this.AFInAppEventType, z.this.onAttributionFailureNative * 500);
            }
        }
    };
    final Executor AFLogger$LogLevel = Executors.newSingleThreadExecutor();

    static {
        AppsFlyer2dXConversionCallback.set(1);
        AppsFlyer2dXConversionCallback.set(2);
        AppsFlyer2dXConversionCallback.set(4);
    }

    private z(SensorManager sensorManager, Handler handler) {
        this.onAppOpenAttributionNative = sensorManager;
        this.valueOf = handler;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static z AFKeystoreWrapper(Context context) {
        if (init != null) {
            return init;
        }
        return AFKeystoreWrapper((SensorManager) context.getApplicationContext().getSystemService("sensor"), getLevel);
    }

    private static z AFKeystoreWrapper(SensorManager sensorManager, Handler handler) {
        if (init == null) {
            synchronized (z.class) {
                if (init == null) {
                    init = new z(sensorManager, handler);
                }
            }
        }
        return init;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean values(int i) {
        return i >= 0 && AppsFlyer2dXConversionCallback.get(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void AFInAppEventType() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.onAppOpenAttribution;
        if (j != 0) {
            this.onAttributionFailureNative++;
            if (j - currentTimeMillis < 500) {
                this.valueOf.removeCallbacks(this.AFInAppEventType);
                this.valueOf.post(this.AFKeystoreWrapper);
            }
        } else {
            this.valueOf.post(this.AFVersionDeclaration);
            this.valueOf.post(this.AFKeystoreWrapper);
        }
        this.onAppOpenAttribution = currentTimeMillis;
    }

    /* renamed from: com.appsflyer.internal.z$10  reason: invalid class name */
    /* loaded from: classes3.dex */
    final class AnonymousClass10 implements Runnable {
        AnonymousClass10() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                if (!z.this.onInstallConversionFailureNative.isEmpty()) {
                    for (x xVar : z.this.onInstallConversionFailureNative.values()) {
                        z.this.onAppOpenAttributionNative.unregisterListener(xVar);
                        xVar.values(z.this.onInstallConversionDataLoadedNative, true);
                    }
                }
            } catch (Throwable unused) {
            }
            z.this.onAttributionFailureNative = 0;
            z.this.onDeepLinkingNative = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final List<Map<String, Object>> values() {
        for (x xVar : this.onInstallConversionFailureNative.values()) {
            xVar.values(this.onInstallConversionDataLoadedNative, true);
        }
        Map<x, Map<String, Object>> map = this.onInstallConversionDataLoadedNative;
        if (map == null || map.isEmpty()) {
            return new CopyOnWriteArrayList(Collections.emptyList());
        }
        return new CopyOnWriteArrayList(this.onInstallConversionDataLoadedNative.values());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final List<Map<String, Object>> AFInAppEventParameterName() {
        synchronized (this.AFInAppEventParameterName) {
            if (!this.onInstallConversionFailureNative.isEmpty() && this.onDeepLinkingNative) {
                for (x xVar : this.onInstallConversionFailureNative.values()) {
                    xVar.values(this.onInstallConversionDataLoadedNative, false);
                }
            }
            if (this.onInstallConversionDataLoadedNative.isEmpty()) {
                return new CopyOnWriteArrayList(Collections.emptyList());
            }
            return new CopyOnWriteArrayList(this.onInstallConversionDataLoadedNative.values());
        }
    }
}
