package com.facebook.appevents;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.appevents.internal.AppEventUtility;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AnalyticsUserIDStore.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\n\u0010\u000b\u001a\u0004\u0018\u00010\u0004H\u0007J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\rH\u0007J\u0012\u0010\u000f\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/facebook/appevents/AnalyticsUserIDStore;", "", "()V", "ANALYTICS_USER_ID_KEY", "", "TAG", "initialized", "", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "userID", "getUserID", "initAndWait", "", "initStore", "setUserID", "id", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class AnalyticsUserIDStore {
    private static final String ANALYTICS_USER_ID_KEY = "com.facebook.appevents.AnalyticsUserIDStore.userID";
    public static final AnalyticsUserIDStore INSTANCE = new AnalyticsUserIDStore();
    private static final String TAG;
    private static volatile boolean initialized;
    private static final ReentrantReadWriteLock lock;
    private static String userID;

    public static /* synthetic */ void lambda$oRrdTAtH5CdDSyWBjB6F5zajeZg(String str) {
        m41setUserID$lambda1(str);
    }

    private AnalyticsUserIDStore() {
    }

    static {
        String simpleName = AnalyticsUserIDStore.class.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "AnalyticsUserIDStore::class.java.simpleName");
        TAG = simpleName;
        lock = new ReentrantReadWriteLock();
    }

    @JvmStatic
    public static final void initStore() {
        if (initialized) {
            return;
        }
        InternalAppEventsLogger.Companion.getAnalyticsExecutor().execute($$Lambda$AnalyticsUserIDStore$tbenDbD_e_3vWhw1CCsMI8yNRVU.INSTANCE);
    }

    /* renamed from: initStore$lambda-0 */
    public static final void m40initStore$lambda0() {
        INSTANCE.initAndWait();
    }

    @JvmStatic
    public static final void setUserID(final String str) {
        AppEventUtility appEventUtility = AppEventUtility.INSTANCE;
        AppEventUtility.assertIsNotMainThread();
        if (!initialized) {
            Log.w(TAG, "initStore should have been called before calling setUserID");
            INSTANCE.initAndWait();
        }
        InternalAppEventsLogger.Companion.getAnalyticsExecutor().execute(new Runnable() { // from class: com.facebook.appevents.-$$Lambda$AnalyticsUserIDStore$oRrdTAtH5CdDSyWBjB6F5zajeZg
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticsUserIDStore.lambda$oRrdTAtH5CdDSyWBjB6F5zajeZg(str);
            }
        });
    }

    /* renamed from: setUserID$lambda-1 */
    public static final void m41setUserID$lambda1(String str) {
        lock.writeLock().lock();
        try {
            AnalyticsUserIDStore analyticsUserIDStore = INSTANCE;
            userID = str;
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
            edit.putString(ANALYTICS_USER_ID_KEY, userID);
            edit.apply();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @JvmStatic
    public static final String getUserID() {
        if (!initialized) {
            Log.w(TAG, "initStore should have been called before calling setUserID");
            INSTANCE.initAndWait();
        }
        lock.readLock().lock();
        try {
            return userID;
        } finally {
            lock.readLock().unlock();
        }
    }

    private final void initAndWait() {
        if (initialized) {
            return;
        }
        lock.writeLock().lock();
        try {
            if (initialized) {
                return;
            }
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            userID = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).getString(ANALYTICS_USER_ID_KEY, null);
            initialized = true;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
