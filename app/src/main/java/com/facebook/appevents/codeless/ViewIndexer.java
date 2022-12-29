package com.facebook.appevents.codeless;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.codeless.ViewIndexer;
import com.facebook.appevents.codeless.internal.Constants;
import com.facebook.appevents.codeless.internal.UnityReflection;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.internal.InternalSettings;
import com.facebook.internal.Logger;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.google.firebase.remoteconfig.RemoteConfigConstants;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ViewIndexer.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u0000 \u00162\u00020\u0001:\u0002\u0016\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u0012\u001a\u00020\u000eJ\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\nH\u0002J\u0006\u0010\u0015\u001a\u00020\u000eR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/facebook/appevents/codeless/ViewIndexer;", "", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "activityReference", "Ljava/lang/ref/WeakReference;", "indexingTimer", "Ljava/util/Timer;", "previousDigest", "", "uiThreadHandler", "Landroid/os/Handler;", "processRequest", "", "request", "Lcom/facebook/GraphRequest;", "currentDigest", "schedule", "sendToServer", ViewIndexer.TREE_PARAM, "unschedule", "Companion", "ScreenshotTaker", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class ViewIndexer {
    private static final String APP_VERSION_PARAM = "app_version";
    public static final Companion Companion = new Companion(null);
    private static final String PLATFORM_PARAM = "platform";
    private static final String REQUEST_TYPE = "request_type";
    private static final String SUCCESS = "success";
    private static final String TAG;
    private static final String TREE_PARAM = "tree";
    private static ViewIndexer instance;
    private final WeakReference<Activity> activityReference;
    private Timer indexingTimer;
    private String previousDigest;
    private final Handler uiThreadHandler;

    @JvmStatic
    public static final GraphRequest buildAppIndexingRequest(String str, AccessToken accessToken, String str2, String str3) {
        return Companion.buildAppIndexingRequest(str, accessToken, str2, str3);
    }

    public static /* synthetic */ void lambda$SNI35ZAcf4eugvBKQmdtKvY0Qc4(String str, ViewIndexer viewIndexer) {
        m76sendToServer$lambda1(str, viewIndexer);
    }

    /* renamed from: lambda$wH8MFozvspdKtoLapzfWw-N04DQ */
    public static /* synthetic */ void m74lambda$wH8MFozvspdKtoLapzfWwN04DQ(ViewIndexer viewIndexer, TimerTask timerTask) {
        m75schedule$lambda0(viewIndexer, timerTask);
    }

    @JvmStatic
    public static final void sendToServerUnityInstance(String str) {
        Companion.sendToServerUnityInstance(str);
    }

    public ViewIndexer(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activityReference = new WeakReference<>(activity);
        this.previousDigest = null;
        this.uiThreadHandler = new Handler(Looper.getMainLooper());
        Companion companion = Companion;
        instance = this;
    }

    public static final /* synthetic */ WeakReference access$getActivityReference$p(ViewIndexer viewIndexer) {
        return viewIndexer.activityReference;
    }

    public static final /* synthetic */ String access$getTAG$cp() {
        return TAG;
    }

    public static final /* synthetic */ Handler access$getUiThreadHandler$p(ViewIndexer viewIndexer) {
        return viewIndexer.uiThreadHandler;
    }

    public static final /* synthetic */ void access$sendToServer(ViewIndexer viewIndexer, String str) {
        viewIndexer.sendToServer(str);
    }

    public final void schedule() {
        final TimerTask timerTask = new TimerTask() { // from class: com.facebook.appevents.codeless.ViewIndexer$schedule$indexingTask$1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                boolean z;
                String str;
                try {
                    Activity activity = (Activity) ViewIndexer.access$getActivityReference$p(ViewIndexer.this).get();
                    AppEventUtility appEventUtility = AppEventUtility.INSTANCE;
                    View rootView = AppEventUtility.getRootView(activity);
                    if (activity != null && rootView != null) {
                        String simpleName = activity.getClass().getSimpleName();
                        Intrinsics.checkNotNullExpressionValue(simpleName, "activity.javaClass.simpleName");
                        CodelessManager codelessManager = CodelessManager.INSTANCE;
                        z = CodelessManager.isAppIndexingEnabled.get();
                        if (z) {
                            InternalSettings internalSettings = InternalSettings.INSTANCE;
                            if (InternalSettings.isUnityApp()) {
                                UnityReflection unityReflection = UnityReflection.INSTANCE;
                                UnityReflection.captureViewHierarchy();
                                return;
                            }
                            FutureTask futureTask = new FutureTask(new ViewIndexer.ScreenshotTaker(rootView));
                            ViewIndexer.access$getUiThreadHandler$p(ViewIndexer.this).post(futureTask);
                            try {
                                str = (String) futureTask.get(1L, TimeUnit.SECONDS);
                            } catch (Exception e) {
                                Log.e(ViewIndexer.access$getTAG$cp(), "Failed to take screenshot.", e);
                                str = "";
                            }
                            JSONObject jSONObject = new JSONObject();
                            try {
                                jSONObject.put(ViewHierarchyConstants.SCREEN_NAME_KEY, simpleName);
                                jSONObject.put("screenshot", str);
                                JSONArray jSONArray = new JSONArray();
                                ViewHierarchy viewHierarchy = ViewHierarchy.INSTANCE;
                                jSONArray.put(ViewHierarchy.getDictionaryOfView(rootView));
                                jSONObject.put(ViewHierarchyConstants.VIEW_KEY, jSONArray);
                            } catch (JSONException unused) {
                                Log.e(ViewIndexer.access$getTAG$cp(), "Failed to create JSONObject");
                            }
                            String jSONObject2 = jSONObject.toString();
                            Intrinsics.checkNotNullExpressionValue(jSONObject2, "viewTree.toString()");
                            ViewIndexer.access$sendToServer(ViewIndexer.this, jSONObject2);
                        }
                    }
                } catch (Exception e2) {
                    Log.e(ViewIndexer.access$getTAG$cp(), "UI Component tree indexing failure!", e2);
                }
            }
        };
        try {
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.codeless.-$$Lambda$ViewIndexer$wH8MFozvspdKtoLapzfWw-N04DQ
                @Override // java.lang.Runnable
                public final void run() {
                    ViewIndexer.m74lambda$wH8MFozvspdKtoLapzfWwN04DQ(ViewIndexer.this, timerTask);
                }
            });
        } catch (RejectedExecutionException e) {
            Log.e(TAG, "Error scheduling indexing job", e);
        }
    }

    /* renamed from: schedule$lambda-0 */
    public static final void m75schedule$lambda0(ViewIndexer this$0, TimerTask indexingTask) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(indexingTask, "$indexingTask");
        try {
            Timer timer = this$0.indexingTimer;
            if (timer != null) {
                timer.cancel();
            }
            this$0.previousDigest = null;
            Timer timer2 = new Timer();
            timer2.scheduleAtFixedRate(indexingTask, 0L, 1000L);
            this$0.indexingTimer = timer2;
        } catch (Exception e) {
            Log.e(TAG, "Error scheduling indexing job", e);
        }
    }

    public final void unschedule() {
        if (this.activityReference.get() == null) {
            return;
        }
        try {
            Timer timer = this.indexingTimer;
            if (timer != null) {
                timer.cancel();
            }
            this.indexingTimer = null;
        } catch (Exception e) {
            Log.e(TAG, "Error unscheduling indexing job", e);
        }
    }

    public final void sendToServer(final String str) {
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.codeless.-$$Lambda$ViewIndexer$SNI35ZAcf4eugvBKQmdtKvY0Qc4
            @Override // java.lang.Runnable
            public final void run() {
                ViewIndexer.lambda$SNI35ZAcf4eugvBKQmdtKvY0Qc4(str, this);
            }
        });
    }

    /* renamed from: sendToServer$lambda-1 */
    public static final void m76sendToServer$lambda1(String tree, ViewIndexer this$0) {
        Intrinsics.checkNotNullParameter(tree, "$tree");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Utility utility = Utility.INSTANCE;
        String md5hash = Utility.md5hash(tree);
        AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
        if (md5hash == null || !Intrinsics.areEqual(md5hash, this$0.previousDigest)) {
            Companion companion = Companion;
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            this$0.processRequest(companion.buildAppIndexingRequest(tree, currentAccessToken, FacebookSdk.getApplicationId(), Constants.APP_INDEXING), md5hash);
        }
    }

    public final void processRequest(GraphRequest graphRequest, String str) {
        if (graphRequest == null) {
            return;
        }
        GraphResponse executeAndWait = graphRequest.executeAndWait();
        try {
            JSONObject jSONObject = executeAndWait.getJSONObject();
            if (jSONObject != null) {
                if (Intrinsics.areEqual(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE, jSONObject.optString("success"))) {
                    Logger.Companion.log(LoggingBehavior.APP_EVENTS, TAG, "Successfully send UI component tree to server");
                    this.previousDigest = str;
                }
                if (jSONObject.has(Constants.APP_INDEXING_ENABLED)) {
                    boolean z = jSONObject.getBoolean(Constants.APP_INDEXING_ENABLED);
                    CodelessManager codelessManager = CodelessManager.INSTANCE;
                    CodelessManager.isAppIndexingEnabled.set(z);
                    return;
                }
                return;
            }
            Log.e(TAG, Intrinsics.stringPlus("Error sending UI component tree to Facebook: ", executeAndWait.getError()));
        } catch (JSONException e) {
            Log.e(TAG, "Error decoding server response.", e);
        }
    }

    /* compiled from: ViewIndexer.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0007\u001a\u00020\u0002H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/facebook/appevents/codeless/ViewIndexer$ScreenshotTaker;", "Ljava/util/concurrent/Callable;", "", "rootView", "Landroid/view/View;", "(Landroid/view/View;)V", "Ljava/lang/ref/WeakReference;", "call", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class ScreenshotTaker implements Callable<String> {
        private final WeakReference<View> rootView;

        public ScreenshotTaker(View rootView) {
            Intrinsics.checkNotNullParameter(rootView, "rootView");
            this.rootView = new WeakReference<>(rootView);
        }

        @Override // java.util.concurrent.Callable
        public String call() {
            View view = this.rootView.get();
            if (view == null || view.getWidth() == 0 || view.getHeight() == 0) {
                return "";
            }
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
            view.draw(new Canvas(createBitmap));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            createBitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
            String encodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
            Intrinsics.checkNotNullExpressionValue(encodeToString, "encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)");
            return encodeToString;
        }
    }

    /* compiled from: ViewIndexer.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0007J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/facebook/appevents/codeless/ViewIndexer$Companion;", "", "()V", "APP_VERSION_PARAM", "", "PLATFORM_PARAM", "REQUEST_TYPE", "SUCCESS", "TAG", "TREE_PARAM", "instance", "Lcom/facebook/appevents/codeless/ViewIndexer;", "buildAppIndexingRequest", "Lcom/facebook/GraphRequest;", "appIndex", "accessToken", "Lcom/facebook/AccessToken;", RemoteConfigConstants.RequestFieldKey.APP_ID, "requestType", "sendToServerUnityInstance", "", ViewIndexer.TREE_PARAM, "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void sendToServerUnityInstance(String tree) {
            Intrinsics.checkNotNullParameter(tree, "tree");
            ViewIndexer viewIndexer = ViewIndexer.instance;
            if (viewIndexer == null) {
                return;
            }
            viewIndexer.sendToServer(tree);
        }

        @JvmStatic
        public final GraphRequest buildAppIndexingRequest(String str, AccessToken accessToken, String str2, String requestType) {
            Intrinsics.checkNotNullParameter(requestType, "requestType");
            if (str == null) {
                return null;
            }
            GraphRequest.Companion companion = GraphRequest.Companion;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objArr = {str2};
            String format = String.format(Locale.US, "%s/app_indexing", Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkNotNullExpressionValue(format, "java.lang.String.format(locale, format, *args)");
            GraphRequest newPostRequest = companion.newPostRequest(accessToken, format, null, null);
            Bundle parameters = newPostRequest.getParameters();
            if (parameters == null) {
                parameters = new Bundle();
            }
            parameters.putString(ViewIndexer.TREE_PARAM, str);
            AppEventUtility appEventUtility = AppEventUtility.INSTANCE;
            parameters.putString(ViewIndexer.APP_VERSION_PARAM, AppEventUtility.getAppVersion());
            parameters.putString(ViewIndexer.PLATFORM_PARAM, "android");
            parameters.putString(ViewIndexer.REQUEST_TYPE, requestType);
            if (Intrinsics.areEqual(requestType, Constants.APP_INDEXING)) {
                CodelessManager codelessManager = CodelessManager.INSTANCE;
                parameters.putString(Constants.DEVICE_SESSION_ID, CodelessManager.getCurrentDeviceSessionID$facebook_core_release());
            }
            newPostRequest.setParameters(parameters);
            newPostRequest.setCallback($$Lambda$ViewIndexer$Companion$cEX9Z2yPsmYT9RtCEpi7naJzBYw.INSTANCE);
            return newPostRequest;
        }

        /* renamed from: buildAppIndexingRequest$lambda-0 */
        public static final void m77buildAppIndexingRequest$lambda0(GraphResponse it) {
            Intrinsics.checkNotNullParameter(it, "it");
            Logger.Companion.log(LoggingBehavior.APP_EVENTS, ViewIndexer.TAG, "App index sent to FB!");
        }
    }

    static {
        String canonicalName = ViewIndexer.class.getCanonicalName();
        if (canonicalName == null) {
            canonicalName = "";
        }
        TAG = canonicalName;
    }
}
