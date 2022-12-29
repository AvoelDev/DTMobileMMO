package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.facebook.appevents.InternalAppEventsLogger;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UserSettingsManager.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0010\bÁ\u0002\u0018\u00002\u00020\u0001:\u00017B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0007J\b\u0010\u001f\u001a\u00020\u001eH\u0007J\b\u0010 \u001a\u00020\u001eH\u0007J\b\u0010!\u001a\u00020\u001eH\u0007J\b\u0010\"\u001a\u00020\u001eH\u0007J\b\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020$H\u0002J!\u0010&\u001a\u00020$2\u0012\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130(\"\u00020\u0013H\u0002¢\u0006\u0002\u0010)J\u0010\u0010*\u001a\u00020$2\u0006\u0010+\u001a\u00020\u0013H\u0002J\b\u0010,\u001a\u00020$H\u0007J\b\u0010-\u001a\u00020$H\u0002J\b\u0010.\u001a\u00020$H\u0002J\u0010\u0010/\u001a\u00020$2\u0006\u0010+\u001a\u00020\u0013H\u0002J\u0010\u00100\u001a\u00020$2\u0006\u00101\u001a\u00020\u001eH\u0007J\u0010\u00102\u001a\u00020$2\u0006\u00101\u001a\u00020\u001eH\u0007J\u0010\u00103\u001a\u00020$2\u0006\u00101\u001a\u00020\u001eH\u0007J\u0010\u00104\u001a\u00020$2\u0006\u00101\u001a\u00020\u001eH\u0007J\b\u00105\u001a\u00020$H\u0002J\u0010\u00106\u001a\u00020$2\u0006\u0010+\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lcom/facebook/UserSettingsManager;", "", "()V", "ADVERTISERID_COLLECTION_FALSE_WARNING", "", "ADVERTISERID_COLLECTION_NOT_SET_WARNING", "ADVERTISER_ID_KEY", "APPLICATION_FIELDS", "AUTOLOG_APPEVENT_NOT_SET_WARNING", "AUTO_APP_LINK_WARNING", "EVENTS_CODELESS_SETUP_ENABLED", "LAST_TIMESTAMP", "TAG", "TIMEOUT_7D", "", "USER_SETTINGS", "USER_SETTINGS_BITMASK", "VALUE", "advertiserIDCollectionEnabled", "Lcom/facebook/UserSettingsManager$UserSetting;", "autoInitEnabled", "autoLogAppEventsEnabled", "codelessSetupEnabled", "isFetchingCodelessStatus", "Ljava/util/concurrent/atomic/AtomicBoolean;", "isInitialized", "monitorEnabled", "userSettingPref", "Landroid/content/SharedPreferences;", "getAdvertiserIDCollectionEnabled", "", "getAutoInitEnabled", "getAutoLogAppEventsEnabled", "getCodelessSetupEnabled", "getMonitorEnabled", "initializeCodelessSetupEnabledAsync", "", "initializeIfNotInitialized", "initializeUserSetting", "userSettings", "", "([Lcom/facebook/UserSettingsManager$UserSetting;)V", "loadSettingFromManifest", "userSetting", "logIfAutoAppLinkEnabled", "logIfSDKSettingsChanged", "logWarnings", "readSettingFromCache", "setAdvertiserIDCollectionEnabled", "flag", "setAutoInitEnabled", "setAutoLogAppEventsEnabled", "setMonitorEnabled", "validateInitialized", "writeSettingToCache", "UserSetting", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class UserSettingsManager {
    private static final String ADVERTISERID_COLLECTION_FALSE_WARNING = "The value for AdvertiserIDCollectionEnabled is currently set to FALSE so you're sending app events without collecting Advertiser ID. This can affect the quality of your advertising and analytics results.";
    private static final String ADVERTISERID_COLLECTION_NOT_SET_WARNING = "You haven't set a value for AdvertiserIDCollectionEnabled. Set the flag to TRUE if you want to collect Advertiser ID for better advertising and analytics results. To request user consent before collecting data, set the flag value to FALSE, then change to TRUE once user consent is received. Learn more: https://developers.facebook.com/docs/app-events/getting-started-app-events-android#disable-auto-events.";
    private static final String ADVERTISER_ID_KEY = "advertiser_id";
    private static final String APPLICATION_FIELDS = "fields";
    private static final String AUTOLOG_APPEVENT_NOT_SET_WARNING = "Please set a value for AutoLogAppEventsEnabled. Set the flag to TRUE if you want to collect app install, app launch and in-app purchase events automatically. To request user consent before collecting data, set the flag value to FALSE, then change to TRUE once user consent is received. Learn more: https://developers.facebook.com/docs/app-events/getting-started-app-events-android#disable-auto-events.";
    private static final String AUTO_APP_LINK_WARNING = "You haven't set the Auto App Link URL scheme: fb<YOUR APP ID> in AndroidManifest";
    private static final String EVENTS_CODELESS_SETUP_ENABLED = "auto_event_setup_enabled";
    public static final UserSettingsManager INSTANCE = new UserSettingsManager();
    private static final String LAST_TIMESTAMP = "last_timestamp";
    private static final String TAG;
    private static final long TIMEOUT_7D = 604800000;
    private static final String USER_SETTINGS = "com.facebook.sdk.USER_SETTINGS";
    private static final String USER_SETTINGS_BITMASK = "com.facebook.sdk.USER_SETTINGS_BITMASK";
    private static final String VALUE = "value";
    private static final UserSetting advertiserIDCollectionEnabled;
    private static final UserSetting autoInitEnabled;
    private static final UserSetting autoLogAppEventsEnabled;
    private static final UserSetting codelessSetupEnabled;
    private static final AtomicBoolean isFetchingCodelessStatus;
    private static final AtomicBoolean isInitialized;
    private static final UserSetting monitorEnabled;
    private static SharedPreferences userSettingPref;

    public static /* synthetic */ void lambda$zhV21tYfRxCKaoumhNYley9SsW8(long j) {
        m38initializeCodelessSetupEnabledAsync$lambda0(j);
    }

    private UserSettingsManager() {
    }

    static {
        String name = UserSettingsManager.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "UserSettingsManager::class.java.name");
        TAG = name;
        isInitialized = new AtomicBoolean(false);
        isFetchingCodelessStatus = new AtomicBoolean(false);
        autoInitEnabled = new UserSetting(true, FacebookSdk.AUTO_INIT_ENABLED_PROPERTY);
        autoLogAppEventsEnabled = new UserSetting(true, FacebookSdk.AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY);
        advertiserIDCollectionEnabled = new UserSetting(true, FacebookSdk.ADVERTISER_ID_COLLECTION_ENABLED_PROPERTY);
        codelessSetupEnabled = new UserSetting(false, EVENTS_CODELESS_SETUP_ENABLED);
        monitorEnabled = new UserSetting(true, FacebookSdk.MONITOR_ENABLED_PROPERTY);
    }

    private final void initializeIfNotInitialized() {
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        if (FacebookSdk.isInitialized() && isInitialized.compareAndSet(false, true)) {
            FacebookSdk facebookSdk2 = FacebookSdk.INSTANCE;
            SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(USER_SETTINGS, 0);
            Intrinsics.checkNotNullExpressionValue(sharedPreferences, "FacebookSdk.getApplicationContext()\n            .getSharedPreferences(USER_SETTINGS, Context.MODE_PRIVATE)");
            userSettingPref = sharedPreferences;
            initializeUserSetting(autoLogAppEventsEnabled, advertiserIDCollectionEnabled, autoInitEnabled);
            initializeCodelessSetupEnabledAsync();
            logWarnings();
            logIfSDKSettingsChanged();
        }
    }

    private final void initializeUserSetting(UserSetting... userSettingArr) {
        int length = userSettingArr.length;
        int i = 0;
        while (i < length) {
            UserSetting userSetting = userSettingArr[i];
            i++;
            if (userSetting == codelessSetupEnabled) {
                initializeCodelessSetupEnabledAsync();
            } else if (userSetting.getValue() == null) {
                readSettingFromCache(userSetting);
                if (userSetting.getValue() == null) {
                    loadSettingFromManifest(userSetting);
                }
            } else {
                writeSettingToCache(userSetting);
            }
        }
    }

    private final void initializeCodelessSetupEnabledAsync() {
        readSettingFromCache(codelessSetupEnabled);
        final long currentTimeMillis = System.currentTimeMillis();
        if (codelessSetupEnabled.getValue() == null || currentTimeMillis - codelessSetupEnabled.getLastTS() >= TIMEOUT_7D) {
            codelessSetupEnabled.setValue(null);
            codelessSetupEnabled.setLastTS(0L);
            if (isFetchingCodelessStatus.compareAndSet(false, true)) {
                FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
                FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.-$$Lambda$UserSettingsManager$zhV21tYfRxCKaoumhNYley9SsW8
                    @Override // java.lang.Runnable
                    public final void run() {
                        UserSettingsManager.lambda$zhV21tYfRxCKaoumhNYley9SsW8(currentTimeMillis);
                    }
                });
            }
        }
    }

    /* renamed from: initializeCodelessSetupEnabledAsync$lambda-0 */
    public static final void m38initializeCodelessSetupEnabledAsync$lambda0(long j) {
        if (advertiserIDCollectionEnabled.m39getValue()) {
            FetchedAppSettingsManager fetchedAppSettingsManager = FetchedAppSettingsManager.INSTANCE;
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            FetchedAppSettings queryAppSettings = FetchedAppSettingsManager.queryAppSettings(FacebookSdk.getApplicationId(), false);
            if (queryAppSettings != null && queryAppSettings.getCodelessEventsEnabled()) {
                FacebookSdk facebookSdk2 = FacebookSdk.INSTANCE;
                AttributionIdentifiers attributionIdentifiers = AttributionIdentifiers.Companion.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
                String androidAdvertiserId = (attributionIdentifiers == null || attributionIdentifiers.getAndroidAdvertiserId() == null) ? null : attributionIdentifiers.getAndroidAdvertiserId();
                if (androidAdvertiserId != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ADVERTISER_ID_KEY, androidAdvertiserId);
                    bundle.putString("fields", EVENTS_CODELESS_SETUP_ENABLED);
                    GraphRequest newGraphPathRequest = GraphRequest.Companion.newGraphPathRequest(null, "app", null);
                    newGraphPathRequest.setParameters(bundle);
                    JSONObject jSONObject = newGraphPathRequest.executeAndWait().getJSONObject();
                    if (jSONObject != null) {
                        codelessSetupEnabled.setValue(Boolean.valueOf(jSONObject.optBoolean(EVENTS_CODELESS_SETUP_ENABLED, false)));
                        codelessSetupEnabled.setLastTS(j);
                        INSTANCE.writeSettingToCache(codelessSetupEnabled);
                    }
                }
            }
        }
        isFetchingCodelessStatus.set(false);
    }

    private final void writeSettingToCache(UserSetting userSetting) {
        validateInitialized();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("value", userSetting.getValue());
            jSONObject.put(LAST_TIMESTAMP, userSetting.getLastTS());
            SharedPreferences sharedPreferences = userSettingPref;
            if (sharedPreferences == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userSettingPref");
                throw null;
            }
            sharedPreferences.edit().putString(userSetting.getKey(), jSONObject.toString()).apply();
            logIfSDKSettingsChanged();
        } catch (Exception e) {
            Utility utility = Utility.INSTANCE;
            Utility.logd(TAG, e);
        }
    }

    private final void readSettingFromCache(UserSetting userSetting) {
        String str = "";
        validateInitialized();
        try {
            SharedPreferences sharedPreferences = userSettingPref;
            if (sharedPreferences == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userSettingPref");
                throw null;
            }
            String string = sharedPreferences.getString(userSetting.getKey(), "");
            if (string != null) {
                str = string;
            }
            if (str.length() > 0) {
                JSONObject jSONObject = new JSONObject(str);
                userSetting.setValue(Boolean.valueOf(jSONObject.getBoolean("value")));
                userSetting.setLastTS(jSONObject.getLong(LAST_TIMESTAMP));
            }
        } catch (JSONException e) {
            Utility utility = Utility.INSTANCE;
            Utility.logd(TAG, e);
        }
    }

    private final void loadSettingFromManifest(UserSetting userSetting) {
        validateInitialized();
        try {
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            Context applicationContext = FacebookSdk.getApplicationContext();
            ApplicationInfo applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
            if ((applicationInfo == null ? null : applicationInfo.metaData) == null || !applicationInfo.metaData.containsKey(userSetting.getKey())) {
                return;
            }
            userSetting.setValue(Boolean.valueOf(applicationInfo.metaData.getBoolean(userSetting.getKey(), userSetting.getDefaultVal())));
        } catch (PackageManager.NameNotFoundException e) {
            Utility utility = Utility.INSTANCE;
            Utility.logd(TAG, e);
        }
    }

    private final void logWarnings() {
        try {
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            Context applicationContext = FacebookSdk.getApplicationContext();
            ApplicationInfo applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
            if ((applicationInfo == null ? null : applicationInfo.metaData) != null) {
                if (!applicationInfo.metaData.containsKey(FacebookSdk.AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY)) {
                    Log.w(TAG, AUTOLOG_APPEVENT_NOT_SET_WARNING);
                }
                if (!applicationInfo.metaData.containsKey(FacebookSdk.ADVERTISER_ID_COLLECTION_ENABLED_PROPERTY)) {
                    Log.w(TAG, ADVERTISERID_COLLECTION_NOT_SET_WARNING);
                }
                if (getAdvertiserIDCollectionEnabled()) {
                    return;
                }
                Log.w(TAG, ADVERTISERID_COLLECTION_FALSE_WARNING);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    private final void logIfSDKSettingsChanged() {
        int i;
        ApplicationInfo applicationInfo;
        if (isInitialized.get()) {
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            if (FacebookSdk.isInitialized()) {
                FacebookSdk facebookSdk2 = FacebookSdk.INSTANCE;
                Context applicationContext = FacebookSdk.getApplicationContext();
                int i2 = 0;
                int i3 = ((autoInitEnabled.m39getValue() ? 1 : 0) << 0) | 0 | ((autoLogAppEventsEnabled.m39getValue() ? 1 : 0) << 1) | ((advertiserIDCollectionEnabled.m39getValue() ? 1 : 0) << 2) | ((monitorEnabled.m39getValue() ? 1 : 0) << 3);
                SharedPreferences sharedPreferences = userSettingPref;
                Bundle bundle = null;
                if (sharedPreferences == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("userSettingPref");
                    throw null;
                }
                int i4 = sharedPreferences.getInt(USER_SETTINGS_BITMASK, 0);
                if (i4 != i3) {
                    SharedPreferences sharedPreferences2 = userSettingPref;
                    if (sharedPreferences2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("userSettingPref");
                        throw null;
                    }
                    sharedPreferences2.edit().putInt(USER_SETTINGS_BITMASK, i3).apply();
                    try {
                        applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
                        if (applicationInfo != null) {
                            bundle = applicationInfo.metaData;
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                    if (bundle != null) {
                        String[] strArr = {FacebookSdk.AUTO_INIT_ENABLED_PROPERTY, FacebookSdk.AUTO_LOG_APP_EVENTS_ENABLED_PROPERTY, FacebookSdk.ADVERTISER_ID_COLLECTION_ENABLED_PROPERTY, FacebookSdk.MONITOR_ENABLED_PROPERTY};
                        boolean[] zArr = {true, true, true, true};
                        int length = strArr.length - 1;
                        if (length >= 0) {
                            int i5 = 0;
                            i = 0;
                            int i6 = 0;
                            while (true) {
                                int i7 = i5 + 1;
                                try {
                                    i |= (applicationInfo.metaData.containsKey(strArr[i5]) ? 1 : 0) << i5;
                                    i6 |= (applicationInfo.metaData.getBoolean(strArr[i5], zArr[i5]) ? 1 : 0) << i5;
                                    if (i7 > length) {
                                        break;
                                    }
                                    i5 = i7;
                                } catch (PackageManager.NameNotFoundException unused2) {
                                }
                            }
                            i2 = i6;
                            InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(applicationContext);
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("usage", i);
                            bundle2.putInt("initial", i2);
                            bundle2.putInt("previous", i4);
                            bundle2.putInt("current", i3);
                            internalAppEventsLogger.logChangedSettingsEvent(bundle2);
                        }
                    }
                    i = 0;
                    InternalAppEventsLogger internalAppEventsLogger2 = new InternalAppEventsLogger(applicationContext);
                    Bundle bundle22 = new Bundle();
                    bundle22.putInt("usage", i);
                    bundle22.putInt("initial", i2);
                    bundle22.putInt("previous", i4);
                    bundle22.putInt("current", i3);
                    internalAppEventsLogger2.logChangedSettingsEvent(bundle22);
                }
            }
        }
    }

    @JvmStatic
    public static final void logIfAutoAppLinkEnabled() {
        try {
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            Context applicationContext = FacebookSdk.getApplicationContext();
            ApplicationInfo applicationInfo = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 128);
            if ((applicationInfo == null ? null : applicationInfo.metaData) == null || !applicationInfo.metaData.getBoolean("com.facebook.sdk.AutoAppLinkEnabled", false)) {
                return;
            }
            InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(applicationContext);
            Bundle bundle = new Bundle();
            Utility utility = Utility.INSTANCE;
            if (!Utility.isAutoAppLinkSetup()) {
                UserSettingsManager userSettingsManager = INSTANCE;
                bundle.putString("SchemeWarning", AUTO_APP_LINK_WARNING);
                String str = TAG;
                UserSettingsManager userSettingsManager2 = INSTANCE;
                Log.w(str, AUTO_APP_LINK_WARNING);
            }
            internalAppEventsLogger.logEvent("fb_auto_applink", bundle);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    private final void validateInitialized() {
        if (!isInitialized.get()) {
            throw new FacebookSdkNotInitializedException("The UserSettingManager has not been initialized successfully");
        }
    }

    @JvmStatic
    public static final void setAutoInitEnabled(boolean z) {
        autoInitEnabled.setValue(Boolean.valueOf(z));
        autoInitEnabled.setLastTS(System.currentTimeMillis());
        if (isInitialized.get()) {
            INSTANCE.writeSettingToCache(autoInitEnabled);
        } else {
            INSTANCE.initializeIfNotInitialized();
        }
    }

    @JvmStatic
    public static final boolean getAutoInitEnabled() {
        INSTANCE.initializeIfNotInitialized();
        return autoInitEnabled.m39getValue();
    }

    @JvmStatic
    public static final void setAutoLogAppEventsEnabled(boolean z) {
        autoLogAppEventsEnabled.setValue(Boolean.valueOf(z));
        autoLogAppEventsEnabled.setLastTS(System.currentTimeMillis());
        if (isInitialized.get()) {
            INSTANCE.writeSettingToCache(autoLogAppEventsEnabled);
        } else {
            INSTANCE.initializeIfNotInitialized();
        }
    }

    @JvmStatic
    public static final boolean getAutoLogAppEventsEnabled() {
        INSTANCE.initializeIfNotInitialized();
        return autoLogAppEventsEnabled.m39getValue();
    }

    @JvmStatic
    public static final void setAdvertiserIDCollectionEnabled(boolean z) {
        advertiserIDCollectionEnabled.setValue(Boolean.valueOf(z));
        advertiserIDCollectionEnabled.setLastTS(System.currentTimeMillis());
        if (isInitialized.get()) {
            INSTANCE.writeSettingToCache(advertiserIDCollectionEnabled);
        } else {
            INSTANCE.initializeIfNotInitialized();
        }
    }

    @JvmStatic
    public static final boolean getAdvertiserIDCollectionEnabled() {
        INSTANCE.initializeIfNotInitialized();
        return advertiserIDCollectionEnabled.m39getValue();
    }

    @JvmStatic
    public static final boolean getCodelessSetupEnabled() {
        INSTANCE.initializeIfNotInitialized();
        return codelessSetupEnabled.m39getValue();
    }

    @JvmStatic
    public static final void setMonitorEnabled(boolean z) {
        monitorEnabled.setValue(Boolean.valueOf(z));
        monitorEnabled.setLastTS(System.currentTimeMillis());
        if (isInitialized.get()) {
            INSTANCE.writeSettingToCache(monitorEnabled);
        } else {
            INSTANCE.initializeIfNotInitialized();
        }
    }

    @JvmStatic
    public static final boolean getMonitorEnabled() {
        INSTANCE.initializeIfNotInitialized();
        return monitorEnabled.m39getValue();
    }

    /* compiled from: UserSettingsManager.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u000b\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0016\u001a\u00020\u0003R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0015\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006\u001b"}, d2 = {"Lcom/facebook/UserSettingsManager$UserSetting;", "", "defaultVal", "", "key", "", "(ZLjava/lang/String;)V", "getDefaultVal", "()Z", "setDefaultVal", "(Z)V", "getKey", "()Ljava/lang/String;", "setKey", "(Ljava/lang/String;)V", "lastTS", "", "getLastTS", "()J", "setLastTS", "(J)V", "value", "getValue", "()Ljava/lang/Boolean;", "setValue", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class UserSetting {
        private boolean defaultVal;
        private String key;
        private long lastTS;
        private Boolean value;

        public UserSetting(boolean z, String key) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.defaultVal = z;
            this.key = key;
        }

        public final boolean getDefaultVal() {
            return this.defaultVal;
        }

        public final String getKey() {
            return this.key;
        }

        public final void setDefaultVal(boolean z) {
            this.defaultVal = z;
        }

        public final void setKey(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.key = str;
        }

        public final Boolean getValue() {
            return this.value;
        }

        public final void setValue(Boolean bool) {
            this.value = bool;
        }

        public final long getLastTS() {
            return this.lastTS;
        }

        public final void setLastTS(long j) {
            this.lastTS = j;
        }

        /* renamed from: getValue */
        public final boolean m39getValue() {
            Boolean bool = this.value;
            return bool == null ? this.defaultVal : bool.booleanValue();
        }
    }
}
