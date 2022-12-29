package com.facebook.appevents.codeless;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.appevents.codeless.ViewIndexingTrigger;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: CodelessManager.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0004H\u0002J\b\u0010\u0013\u001a\u00020\u0011H\u0007J\b\u0010\u0014\u001a\u00020\u0011H\u0007J\r\u0010\u0015\u001a\u00020\u0004H\u0001¢\u0006\u0002\b\u0016J\r\u0010\u0017\u001a\u00020\bH\u0001¢\u0006\u0002\b\u0018J\b\u0010\u0019\u001a\u00020\bH\u0002J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0015\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\bH\u0001¢\u0006\u0002\b!R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/facebook/appevents/codeless/CodelessManager;", "", "()V", "deviceSessionID", "", "isAppIndexingEnabled", "Ljava/util/concurrent/atomic/AtomicBoolean;", "isCheckingSession", "", "isCodelessEnabled", "sensorManager", "Landroid/hardware/SensorManager;", "viewIndexer", "Lcom/facebook/appevents/codeless/ViewIndexer;", "viewIndexingTrigger", "Lcom/facebook/appevents/codeless/ViewIndexingTrigger;", "checkCodelessSession", "", "applicationId", "disable", "enable", "getCurrentDeviceSessionID", "getCurrentDeviceSessionID$facebook_core_release", "getIsAppIndexingEnabled", "getIsAppIndexingEnabled$facebook_core_release", "isDebugOnEmulator", "onActivityDestroyed", "activity", "Landroid/app/Activity;", "onActivityPaused", "onActivityResumed", "updateAppIndexing", "appIndexingEnabled", "updateAppIndexing$facebook_core_release", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class CodelessManager {
    private static String deviceSessionID;
    private static volatile boolean isCheckingSession;
    private static SensorManager sensorManager;
    private static ViewIndexer viewIndexer;
    public static final CodelessManager INSTANCE = new CodelessManager();
    private static final ViewIndexingTrigger viewIndexingTrigger = new ViewIndexingTrigger();
    private static final AtomicBoolean isCodelessEnabled = new AtomicBoolean(true);
    private static final AtomicBoolean isAppIndexingEnabled = new AtomicBoolean(false);

    private final boolean isDebugOnEmulator() {
        return false;
    }

    public static /* synthetic */ void lambda$TQi4Pa1BqiqjDYxc_LHeG1oiSz0(String str) {
        m71checkCodelessSession$lambda1(str);
    }

    public static /* synthetic */ void lambda$vzd3Vq5Ies9LLUeAp7KTjGDCc58(FetchedAppSettings fetchedAppSettings, String str) {
        m72onActivityResumed$lambda0(fetchedAppSettings, str);
    }

    private CodelessManager() {
    }

    @JvmStatic
    public static final void onActivityResumed(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (isCodelessEnabled.get()) {
            CodelessMatcher.Companion.getInstance().add(activity);
            Context applicationContext = activity.getApplicationContext();
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            final String applicationId = FacebookSdk.getApplicationId();
            FetchedAppSettingsManager fetchedAppSettingsManager = FetchedAppSettingsManager.INSTANCE;
            final FetchedAppSettings appSettingsWithoutQuery = FetchedAppSettingsManager.getAppSettingsWithoutQuery(applicationId);
            if (Intrinsics.areEqual((Object) (appSettingsWithoutQuery == null ? null : Boolean.valueOf(appSettingsWithoutQuery.getCodelessEventsEnabled())), (Object) true) || INSTANCE.isDebugOnEmulator()) {
                SensorManager sensorManager2 = (SensorManager) applicationContext.getSystemService("sensor");
                if (sensorManager2 == null) {
                    return;
                }
                CodelessManager codelessManager = INSTANCE;
                sensorManager = sensorManager2;
                Sensor defaultSensor = sensorManager2.getDefaultSensor(1);
                ViewIndexer viewIndexer2 = new ViewIndexer(activity);
                CodelessManager codelessManager2 = INSTANCE;
                viewIndexer = viewIndexer2;
                viewIndexingTrigger.setOnShakeListener(new ViewIndexingTrigger.OnShakeListener() { // from class: com.facebook.appevents.codeless.-$$Lambda$CodelessManager$vzd3Vq5Ies9LLUeAp7KTjGDCc58
                    @Override // com.facebook.appevents.codeless.ViewIndexingTrigger.OnShakeListener
                    public final void onShake() {
                        CodelessManager.lambda$vzd3Vq5Ies9LLUeAp7KTjGDCc58(FetchedAppSettings.this, applicationId);
                    }
                });
                sensorManager2.registerListener(viewIndexingTrigger, defaultSensor, 2);
                if (appSettingsWithoutQuery != null && appSettingsWithoutQuery.getCodelessEventsEnabled()) {
                    viewIndexer2.schedule();
                }
            }
            if (!INSTANCE.isDebugOnEmulator() || isAppIndexingEnabled.get()) {
                return;
            }
            INSTANCE.checkCodelessSession(applicationId);
        }
    }

    /* renamed from: onActivityResumed$lambda-0 */
    public static final void m72onActivityResumed$lambda0(FetchedAppSettings fetchedAppSettings, String appId) {
        Intrinsics.checkNotNullParameter(appId, "$appId");
        boolean z = fetchedAppSettings != null && fetchedAppSettings.getCodelessEventsEnabled();
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        boolean z2 = FacebookSdk.getCodelessSetupEnabled();
        if (z && z2) {
            INSTANCE.checkCodelessSession(appId);
        }
    }

    @JvmStatic
    public static final void onActivityPaused(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (isCodelessEnabled.get()) {
            CodelessMatcher.Companion.getInstance().remove(activity);
            ViewIndexer viewIndexer2 = viewIndexer;
            if (viewIndexer2 != null) {
                viewIndexer2.unschedule();
            }
            SensorManager sensorManager2 = sensorManager;
            if (sensorManager2 == null) {
                return;
            }
            sensorManager2.unregisterListener(viewIndexingTrigger);
        }
    }

    @JvmStatic
    public static final void onActivityDestroyed(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        CodelessMatcher.Companion.getInstance().destroy(activity);
    }

    @JvmStatic
    public static final void enable() {
        isCodelessEnabled.set(true);
    }

    @JvmStatic
    public static final void disable() {
        isCodelessEnabled.set(false);
    }

    private final void checkCodelessSession(final String str) {
        if (isCheckingSession) {
            return;
        }
        isCheckingSession = true;
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.codeless.-$$Lambda$CodelessManager$TQi4Pa1BqiqjDYxc_LHeG1oiSz0
            @Override // java.lang.Runnable
            public final void run() {
                CodelessManager.lambda$TQi4Pa1BqiqjDYxc_LHeG1oiSz0(str);
            }
        });
    }

    /* renamed from: checkCodelessSession$lambda-1 */
    public static final void m71checkCodelessSession$lambda1(String str) {
        Bundle bundle = new Bundle();
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.Companion.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(Build.MODEL != null ? Build.MODEL : "");
        if ((attributionIdentifiers == null ? null : attributionIdentifiers.getAndroidAdvertiserId()) != null) {
            jSONArray.put(attributionIdentifiers.getAndroidAdvertiserId());
        } else {
            jSONArray.put("");
        }
        jSONArray.put("0");
        AppEventUtility appEventUtility = AppEventUtility.INSTANCE;
        jSONArray.put(AppEventUtility.isEmulator() ? "1" : "0");
        Utility utility = Utility.INSTANCE;
        Locale currentLocale = Utility.getCurrentLocale();
        jSONArray.put(currentLocale.getLanguage() + '_' + ((Object) currentLocale.getCountry()));
        String jSONArray2 = jSONArray.toString();
        Intrinsics.checkNotNullExpressionValue(jSONArray2, "extInfoArray.toString()");
        CodelessManager codelessManager = INSTANCE;
        bundle.putString(Constants.DEVICE_SESSION_ID, getCurrentDeviceSessionID$facebook_core_release());
        bundle.putString(Constants.EXTINFO, jSONArray2);
        GraphRequest.Companion companion = GraphRequest.Companion;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        boolean z = true;
        Object[] objArr = {str};
        String format = String.format(Locale.US, "%s/app_indexing_session", Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkNotNullExpressionValue(format, "java.lang.String.format(locale, format, *args)");
        JSONObject jSONObject = companion.newPostRequestWithBundle(null, format, bundle, null).executeAndWait().getJSONObject();
        isAppIndexingEnabled.set((jSONObject == null || !jSONObject.optBoolean(Constants.APP_INDEXING_ENABLED, false)) ? false : false);
        if (!isAppIndexingEnabled.get()) {
            CodelessManager codelessManager2 = INSTANCE;
            deviceSessionID = null;
        } else {
            ViewIndexer viewIndexer2 = viewIndexer;
            if (viewIndexer2 != null) {
                viewIndexer2.schedule();
            }
        }
        CodelessManager codelessManager3 = INSTANCE;
        isCheckingSession = false;
    }

    @JvmStatic
    public static final String getCurrentDeviceSessionID$facebook_core_release() {
        if (deviceSessionID == null) {
            CodelessManager codelessManager = INSTANCE;
            deviceSessionID = UUID.randomUUID().toString();
        }
        String str = deviceSessionID;
        if (str != null) {
            return str;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
    }
}
