package com.facebook.appevents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.cloudbridge.AppEventsCAPIManager;
import com.facebook.appevents.cloudbridge.AppEventsConversionsAPITransformerWebRequests;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: AppEventQueue.kt */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÁ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J*\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007J\u001e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00190!2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\u001fH\u0007J\u0010\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%H\u0007J\u0010\u0010&\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%H\u0007J\u000e\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00150(H\u0007J0\u0010)\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010*\u001a\u00020\u00192\u0006\u0010+\u001a\u00020,2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007J\b\u0010-\u001a\u00020\u0013H\u0007J\u001a\u0010.\u001a\u0004\u0018\u00010\u001f2\u0006\u0010$\u001a\u00020%2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n \u0011*\u0004\u0018\u00010\u00100\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/facebook/appevents/AppEventQueue;", "", "()V", "FLUSH_PERIOD_IN_SECONDS", "", "NO_CONNECTIVITY_ERROR_CODE", "NUM_LOG_EVENTS_TO_TRY_TO_FLUSH_AFTER", "TAG", "", "appEventCollection", "Lcom/facebook/appevents/AppEventCollection;", "flushRunnable", "Ljava/lang/Runnable;", "scheduledFuture", "Ljava/util/concurrent/ScheduledFuture;", "singleThreadExecutor", "Ljava/util/concurrent/ScheduledExecutorService;", "kotlin.jvm.PlatformType", "add", "", "accessTokenAppId", "Lcom/facebook/appevents/AccessTokenAppIdPair;", "appEvent", "Lcom/facebook/appevents/AppEvent;", "buildRequestForSession", "Lcom/facebook/GraphRequest;", "appEvents", "Lcom/facebook/appevents/SessionEventsState;", "limitEventUsage", "", "flushState", "Lcom/facebook/appevents/FlushStatistics;", "buildRequests", "", "flushResults", "flush", "reason", "Lcom/facebook/appevents/FlushReason;", "flushAndWait", "getKeySet", "", "handleResponse", "request", "response", "Lcom/facebook/GraphResponse;", "persistToDisk", "sendEventsToServer", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class AppEventQueue {
    private static final int FLUSH_PERIOD_IN_SECONDS = 15;
    public static final AppEventQueue INSTANCE = new AppEventQueue();
    private static final int NO_CONNECTIVITY_ERROR_CODE = -1;
    private static final int NUM_LOG_EVENTS_TO_TRY_TO_FLUSH_AFTER;
    private static final String TAG;
    private static volatile AppEventCollection appEventCollection;
    private static final Runnable flushRunnable;
    private static ScheduledFuture<?> scheduledFuture;
    private static final ScheduledExecutorService singleThreadExecutor;

    public static /* synthetic */ void lambda$05p3OWkwuoqlEyC8Xdbe2fNhQJ8(FlushReason flushReason) {
        m44flush$lambda2(flushReason);
    }

    public static /* synthetic */ void lambda$FezUgrl1wEYMx3BPZRTEKVMw1nY(AccessTokenAppIdPair accessTokenAppIdPair, SessionEventsState sessionEventsState) {
        m46handleResponse$lambda5(accessTokenAppIdPair, sessionEventsState);
    }

    public static /* synthetic */ void lambda$NwzIDb30a3_Edt_Ctpc0yU_l8sg(AccessTokenAppIdPair accessTokenAppIdPair, AppEvent appEvent) {
        m42add$lambda3(accessTokenAppIdPair, appEvent);
    }

    private AppEventQueue() {
    }

    static {
        String name = AppEventQueue.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "AppEventQueue::class.java.name");
        TAG = name;
        NUM_LOG_EVENTS_TO_TRY_TO_FLUSH_AFTER = 100;
        appEventCollection = new AppEventCollection();
        singleThreadExecutor = Executors.newSingleThreadScheduledExecutor();
        flushRunnable = $$Lambda$AppEventQueue$JfKbahcEddW7eTw_HAXhtpIoDNY.INSTANCE;
    }

    /* renamed from: flushRunnable$lambda-0 */
    public static final void m45flushRunnable$lambda0() {
        AppEventQueue appEventQueue = INSTANCE;
        scheduledFuture = null;
        if (AppEventsLogger.Companion.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY) {
            AppEventQueue appEventQueue2 = INSTANCE;
            flushAndWait(FlushReason.TIMER);
        }
    }

    @JvmStatic
    public static final void persistToDisk() {
        singleThreadExecutor.execute($$Lambda$AppEventQueue$62J7sj8JMyiA3W4h7e07NMp8t6E.INSTANCE);
    }

    /* renamed from: persistToDisk$lambda-1 */
    public static final void m47persistToDisk$lambda1() {
        AppEventStore appEventStore = AppEventStore.INSTANCE;
        AppEventStore.persistEvents(appEventCollection);
        AppEventQueue appEventQueue = INSTANCE;
        appEventCollection = new AppEventCollection();
    }

    @JvmStatic
    public static final void flush(final FlushReason reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        singleThreadExecutor.execute(new Runnable() { // from class: com.facebook.appevents.-$$Lambda$AppEventQueue$05p3OWkwuoqlEyC8Xdbe2fNhQJ8
            @Override // java.lang.Runnable
            public final void run() {
                AppEventQueue.lambda$05p3OWkwuoqlEyC8Xdbe2fNhQJ8(FlushReason.this);
            }
        });
    }

    /* renamed from: flush$lambda-2 */
    public static final void m44flush$lambda2(FlushReason reason) {
        Intrinsics.checkNotNullParameter(reason, "$reason");
        AppEventQueue appEventQueue = INSTANCE;
        flushAndWait(reason);
    }

    @JvmStatic
    public static final void add(final AccessTokenAppIdPair accessTokenAppId, final AppEvent appEvent) {
        Intrinsics.checkNotNullParameter(accessTokenAppId, "accessTokenAppId");
        Intrinsics.checkNotNullParameter(appEvent, "appEvent");
        singleThreadExecutor.execute(new Runnable() { // from class: com.facebook.appevents.-$$Lambda$AppEventQueue$NwzIDb30a3_Edt_Ctpc0yU_l8sg
            @Override // java.lang.Runnable
            public final void run() {
                AppEventQueue.lambda$NwzIDb30a3_Edt_Ctpc0yU_l8sg(AccessTokenAppIdPair.this, appEvent);
            }
        });
    }

    /* renamed from: add$lambda-3 */
    public static final void m42add$lambda3(AccessTokenAppIdPair accessTokenAppId, AppEvent appEvent) {
        Intrinsics.checkNotNullParameter(accessTokenAppId, "$accessTokenAppId");
        Intrinsics.checkNotNullParameter(appEvent, "$appEvent");
        appEventCollection.addEvent(accessTokenAppId, appEvent);
        if (AppEventsLogger.Companion.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY && appEventCollection.getEventCount() > NUM_LOG_EVENTS_TO_TRY_TO_FLUSH_AFTER) {
            AppEventQueue appEventQueue = INSTANCE;
            flushAndWait(FlushReason.EVENT_THRESHOLD);
        } else if (scheduledFuture == null) {
            AppEventQueue appEventQueue2 = INSTANCE;
            scheduledFuture = singleThreadExecutor.schedule(flushRunnable, 15L, TimeUnit.SECONDS);
        }
    }

    @JvmStatic
    public static final Set<AccessTokenAppIdPair> getKeySet() {
        return appEventCollection.keySet();
    }

    @JvmStatic
    public static final void flushAndWait(FlushReason reason) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        AppEventDiskStore appEventDiskStore = AppEventDiskStore.INSTANCE;
        appEventCollection.addPersistedEvents(AppEventDiskStore.readAndClearStore());
        try {
            AppEventQueue appEventQueue = INSTANCE;
            FlushStatistics sendEventsToServer = sendEventsToServer(reason, appEventCollection);
            if (sendEventsToServer != null) {
                Intent intent = new Intent(AppEventsLogger.ACTION_APP_EVENTS_FLUSHED);
                intent.putExtra(AppEventsLogger.APP_EVENTS_EXTRA_NUM_EVENTS_FLUSHED, sendEventsToServer.getNumEvents());
                intent.putExtra(AppEventsLogger.APP_EVENTS_EXTRA_FLUSH_RESULT, sendEventsToServer.getResult());
                FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
                LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()).sendBroadcast(intent);
            }
        } catch (Exception e) {
            Log.w(TAG, "Caught unexpected exception while flushing app events: ", e);
        }
    }

    @JvmStatic
    public static final FlushStatistics sendEventsToServer(FlushReason reason, AppEventCollection appEventCollection2) {
        Intrinsics.checkNotNullParameter(reason, "reason");
        Intrinsics.checkNotNullParameter(appEventCollection2, "appEventCollection");
        FlushStatistics flushStatistics = new FlushStatistics();
        AppEventQueue appEventQueue = INSTANCE;
        List<GraphRequest> buildRequests = buildRequests(appEventCollection2, flushStatistics);
        if (!buildRequests.isEmpty()) {
            Logger.Companion.log(LoggingBehavior.APP_EVENTS, TAG, "Flushing %d events due to %s.", Integer.valueOf(flushStatistics.getNumEvents()), reason.toString());
            for (GraphRequest graphRequest : buildRequests) {
                graphRequest.executeAndWait();
            }
            return flushStatistics;
        }
        return null;
    }

    @JvmStatic
    public static final List<GraphRequest> buildRequests(AppEventCollection appEventCollection2, FlushStatistics flushResults) {
        Intrinsics.checkNotNullParameter(appEventCollection2, "appEventCollection");
        Intrinsics.checkNotNullParameter(flushResults, "flushResults");
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        Context applicationContext = FacebookSdk.getApplicationContext();
        FacebookSdk facebookSdk2 = FacebookSdk.INSTANCE;
        boolean limitEventAndDataUsage = FacebookSdk.getLimitEventAndDataUsage(applicationContext);
        ArrayList arrayList = new ArrayList();
        for (AccessTokenAppIdPair accessTokenAppIdPair : appEventCollection2.keySet()) {
            AppEventQueue appEventQueue = INSTANCE;
            SessionEventsState sessionEventsState = appEventCollection2.get(accessTokenAppIdPair);
            if (sessionEventsState != null) {
                GraphRequest buildRequestForSession = buildRequestForSession(accessTokenAppIdPair, sessionEventsState, limitEventAndDataUsage, flushResults);
                if (buildRequestForSession != null) {
                    arrayList.add(buildRequestForSession);
                    if (AppEventsCAPIManager.INSTANCE.isEnabled$facebook_core_release()) {
                        AppEventsConversionsAPITransformerWebRequests appEventsConversionsAPITransformerWebRequests = AppEventsConversionsAPITransformerWebRequests.INSTANCE;
                        AppEventsConversionsAPITransformerWebRequests.transformGraphRequestAndSendToCAPIGEndPoint(buildRequestForSession);
                    }
                }
            } else {
                throw new IllegalStateException("Required value was null.".toString());
            }
        }
        return arrayList;
    }

    @JvmStatic
    public static final GraphRequest buildRequestForSession(final AccessTokenAppIdPair accessTokenAppId, final SessionEventsState appEvents, boolean z, final FlushStatistics flushState) {
        Intrinsics.checkNotNullParameter(accessTokenAppId, "accessTokenAppId");
        Intrinsics.checkNotNullParameter(appEvents, "appEvents");
        Intrinsics.checkNotNullParameter(flushState, "flushState");
        String applicationId = accessTokenAppId.getApplicationId();
        FetchedAppSettingsManager fetchedAppSettingsManager = FetchedAppSettingsManager.INSTANCE;
        FetchedAppSettings queryAppSettings = FetchedAppSettingsManager.queryAppSettings(applicationId, false);
        GraphRequest.Companion companion = GraphRequest.Companion;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {applicationId};
        String format = String.format("%s/activities", Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkNotNullExpressionValue(format, "java.lang.String.format(format, *args)");
        final GraphRequest newPostRequest = companion.newPostRequest(null, format, null, null);
        newPostRequest.setForceApplicationRequest(true);
        Bundle parameters = newPostRequest.getParameters();
        if (parameters == null) {
            parameters = new Bundle();
        }
        parameters.putString("access_token", accessTokenAppId.getAccessTokenString());
        String pushNotificationsRegistrationId = InternalAppEventsLogger.Companion.getPushNotificationsRegistrationId();
        if (pushNotificationsRegistrationId != null) {
            parameters.putString("device_token", pushNotificationsRegistrationId);
        }
        String installReferrer = AppEventsLoggerImpl.Companion.getInstallReferrer();
        if (installReferrer != null) {
            parameters.putString("install_referrer", installReferrer);
        }
        newPostRequest.setParameters(parameters);
        boolean supportsImplicitLogging = queryAppSettings != null ? queryAppSettings.supportsImplicitLogging() : false;
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        int populateRequest = appEvents.populateRequest(newPostRequest, FacebookSdk.getApplicationContext(), supportsImplicitLogging, z);
        if (populateRequest == 0) {
            return null;
        }
        flushState.setNumEvents(flushState.getNumEvents() + populateRequest);
        newPostRequest.setCallback(new GraphRequest.Callback() { // from class: com.facebook.appevents.-$$Lambda$AppEventQueue$CwLjkzUGr5zDi942fVK4Gi3jmAM
            @Override // com.facebook.GraphRequest.Callback
            public final void onCompleted(GraphResponse graphResponse) {
                AppEventQueue.m43buildRequestForSession$lambda4(AccessTokenAppIdPair.this, newPostRequest, appEvents, flushState, graphResponse);
            }
        });
        return newPostRequest;
    }

    /* renamed from: buildRequestForSession$lambda-4 */
    public static final void m43buildRequestForSession$lambda4(AccessTokenAppIdPair accessTokenAppId, GraphRequest postRequest, SessionEventsState appEvents, FlushStatistics flushState, GraphResponse response) {
        Intrinsics.checkNotNullParameter(accessTokenAppId, "$accessTokenAppId");
        Intrinsics.checkNotNullParameter(postRequest, "$postRequest");
        Intrinsics.checkNotNullParameter(appEvents, "$appEvents");
        Intrinsics.checkNotNullParameter(flushState, "$flushState");
        Intrinsics.checkNotNullParameter(response, "response");
        AppEventQueue appEventQueue = INSTANCE;
        handleResponse(accessTokenAppId, postRequest, response, appEvents, flushState);
    }

    @JvmStatic
    public static final void handleResponse(final AccessTokenAppIdPair accessTokenAppId, GraphRequest request, GraphResponse response, final SessionEventsState appEvents, FlushStatistics flushState) {
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(accessTokenAppId, "accessTokenAppId");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(response, "response");
        Intrinsics.checkNotNullParameter(appEvents, "appEvents");
        Intrinsics.checkNotNullParameter(flushState, "flushState");
        FacebookRequestError error = response.getError();
        FlushResult flushResult = FlushResult.SUCCESS;
        if (error != null) {
            int errorCode = error.getErrorCode();
            AppEventQueue appEventQueue = INSTANCE;
            if (errorCode == -1) {
                flushResult = FlushResult.NO_CONNECTIVITY;
                str = "Failed: No Connectivity";
            } else {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                Object[] objArr = {response.toString(), error.toString()};
                str = String.format("Failed:\n  Response: %s\n  Error %s", Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(format, *args)");
                flushResult = FlushResult.SERVER_ERROR;
            }
        } else {
            str = "Success";
        }
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.APP_EVENTS)) {
            try {
                str2 = new JSONArray((String) request.getTag()).toString(2);
                Intrinsics.checkNotNullExpressionValue(str2, "{\n            val jsonArray = JSONArray(eventsJsonString)\n            jsonArray.toString(2)\n          }");
            } catch (JSONException unused) {
                str2 = "<Can't encode events for debug logging>";
            }
            Logger.Companion.log(LoggingBehavior.APP_EVENTS, TAG, "Flush completed\nParams: %s\n  Result: %s\n  Events JSON: %s", String.valueOf(request.getGraphObject()), str, str2);
        }
        appEvents.clearInFlightAndStats(error != null);
        if (flushResult == FlushResult.NO_CONNECTIVITY) {
            FacebookSdk facebookSdk2 = FacebookSdk.INSTANCE;
            FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.-$$Lambda$AppEventQueue$FezUgrl1wEYMx3BPZRTEKVMw1nY
                @Override // java.lang.Runnable
                public final void run() {
                    AppEventQueue.lambda$FezUgrl1wEYMx3BPZRTEKVMw1nY(AccessTokenAppIdPair.this, appEvents);
                }
            });
        }
        if (flushResult == FlushResult.SUCCESS || flushState.getResult() == FlushResult.NO_CONNECTIVITY) {
            return;
        }
        flushState.setResult(flushResult);
    }

    /* renamed from: handleResponse$lambda-5 */
    public static final void m46handleResponse$lambda5(AccessTokenAppIdPair accessTokenAppId, SessionEventsState appEvents) {
        Intrinsics.checkNotNullParameter(accessTokenAppId, "$accessTokenAppId");
        Intrinsics.checkNotNullParameter(appEvents, "$appEvents");
        AppEventStore appEventStore = AppEventStore.INSTANCE;
        AppEventStore.persistEvents(accessTokenAppId, appEvents);
    }
}
