package com.facebook.appevents.ondeviceprocessing;

import android.content.Context;
import android.content.SharedPreferences;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEvent;
import com.facebook.appevents.AppEventsConstants;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: OnDeviceProcessingManager.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\b\u0010\n\u001a\u00020\u0007H\u0007J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0007J\u001c\u0010\u000e\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0005H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/facebook/appevents/ondeviceprocessing/OnDeviceProcessingManager;", "", "()V", "ALLOWED_IMPLICIT_EVENTS", "", "", "isEventEligibleForOnDeviceProcessing", "", "event", "Lcom/facebook/appevents/AppEvent;", "isOnDeviceProcessingEnabled", "sendCustomEventAsync", "", "applicationId", "sendInstallEventAsync", "preferencesName", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class OnDeviceProcessingManager {
    public static final OnDeviceProcessingManager INSTANCE = new OnDeviceProcessingManager();
    private static final Set<String> ALLOWED_IMPLICIT_EVENTS = SetsKt.setOf((Object[]) new String[]{AppEventsConstants.EVENT_NAME_PURCHASED, AppEventsConstants.EVENT_NAME_START_TRIAL, AppEventsConstants.EVENT_NAME_SUBSCRIBE});

    public static /* synthetic */ void lambda$6zAc79UpPGdEEZiXgVsq7HYMOsM(String str, AppEvent appEvent) {
        m95sendCustomEventAsync$lambda1(str, appEvent);
    }

    public static /* synthetic */ void lambda$KHCL1CriT4yh6K97BX3fXiiTdBk(Context context, String str, String str2) {
        m96sendInstallEventAsync$lambda0(context, str, str2);
    }

    private OnDeviceProcessingManager() {
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x001d  */
    @kotlin.jvm.JvmStatic
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean isOnDeviceProcessingEnabled() {
        /*
            com.facebook.FacebookSdk r0 = com.facebook.FacebookSdk.INSTANCE
            android.content.Context r0 = com.facebook.FacebookSdk.getApplicationContext()
            com.facebook.FacebookSdk r1 = com.facebook.FacebookSdk.INSTANCE
            boolean r0 = com.facebook.FacebookSdk.getLimitEventAndDataUsage(r0)
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L1a
            com.facebook.internal.Utility r0 = com.facebook.internal.Utility.INSTANCE
            boolean r0 = com.facebook.internal.Utility.isDataProcessingRestricted()
            if (r0 != 0) goto L1a
            r0 = 1
            goto L1b
        L1a:
            r0 = 0
        L1b:
            if (r0 == 0) goto L26
            com.facebook.appevents.ondeviceprocessing.RemoteServiceWrapper r0 = com.facebook.appevents.ondeviceprocessing.RemoteServiceWrapper.INSTANCE
            boolean r0 = com.facebook.appevents.ondeviceprocessing.RemoteServiceWrapper.isServiceAvailable()
            if (r0 == 0) goto L26
            goto L27
        L26:
            r1 = 0
        L27:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.ondeviceprocessing.OnDeviceProcessingManager.isOnDeviceProcessingEnabled():boolean");
    }

    @JvmStatic
    public static final void sendInstallEventAsync(final String str, final String str2) {
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        final Context applicationContext = FacebookSdk.getApplicationContext();
        if (applicationContext == null || str == null || str2 == null) {
            return;
        }
        FacebookSdk facebookSdk2 = FacebookSdk.INSTANCE;
        FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.ondeviceprocessing.-$$Lambda$OnDeviceProcessingManager$KHCL1CriT4yh6K97BX3fXiiTdBk
            @Override // java.lang.Runnable
            public final void run() {
                OnDeviceProcessingManager.lambda$KHCL1CriT4yh6K97BX3fXiiTdBk(applicationContext, str2, str);
            }
        });
    }

    /* renamed from: sendInstallEventAsync$lambda-0 */
    public static final void m96sendInstallEventAsync$lambda0(Context context, String str, String str2) {
        Intrinsics.checkNotNullParameter(context, "$context");
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, 0);
        String stringPlus = Intrinsics.stringPlus(str2, "pingForOnDevice");
        if (sharedPreferences.getLong(stringPlus, 0L) == 0) {
            RemoteServiceWrapper remoteServiceWrapper = RemoteServiceWrapper.INSTANCE;
            RemoteServiceWrapper.sendInstallEvent(str2);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putLong(stringPlus, System.currentTimeMillis());
            edit.apply();
        }
    }

    @JvmStatic
    public static final void sendCustomEventAsync(final String applicationId, final AppEvent event) {
        Intrinsics.checkNotNullParameter(applicationId, "applicationId");
        Intrinsics.checkNotNullParameter(event, "event");
        if (INSTANCE.isEventEligibleForOnDeviceProcessing(event)) {
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.ondeviceprocessing.-$$Lambda$OnDeviceProcessingManager$6zAc79UpPGdEEZiXgVsq7HYMOsM
                @Override // java.lang.Runnable
                public final void run() {
                    OnDeviceProcessingManager.lambda$6zAc79UpPGdEEZiXgVsq7HYMOsM(applicationId, event);
                }
            });
        }
    }

    /* renamed from: sendCustomEventAsync$lambda-1 */
    public static final void m95sendCustomEventAsync$lambda1(String applicationId, AppEvent event) {
        Intrinsics.checkNotNullParameter(applicationId, "$applicationId");
        Intrinsics.checkNotNullParameter(event, "$event");
        RemoteServiceWrapper remoteServiceWrapper = RemoteServiceWrapper.INSTANCE;
        RemoteServiceWrapper.sendCustomEvents(applicationId, CollectionsKt.listOf(event));
    }

    private final boolean isEventEligibleForOnDeviceProcessing(AppEvent appEvent) {
        return (appEvent.isImplicit() ^ true) || (appEvent.isImplicit() && ALLOWED_IMPLICIT_EVENTS.contains(appEvent.getName()));
    }
}
