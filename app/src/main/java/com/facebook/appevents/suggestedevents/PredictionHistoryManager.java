package com.facebook.appevents.suggestedevents;

import android.content.SharedPreferences;
import android.view.View;
import com.facebook.FacebookSdk;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.internal.Utility;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PredictionHistoryManager.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bÁ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0007J\u001a\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004H\u0007J\b\u0010\u0014\u001a\u00020\rH\u0002J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/facebook/appevents/suggestedevents/PredictionHistoryManager;", "", "()V", "CLICKED_PATH_STORE", "", PredictionHistoryManager.SUGGESTED_EVENTS_HISTORY, "clickedViewPaths", "", "initialized", "Ljava/util/concurrent/atomic/AtomicBoolean;", "shardPreferences", "Landroid/content/SharedPreferences;", "addPrediction", "", "pathID", "predictedEvent", "getPathID", ViewHierarchyConstants.VIEW_KEY, "Landroid/view/View;", "text", "initAndWait", "queryEvent", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class PredictionHistoryManager {
    private static final String CLICKED_PATH_STORE = "com.facebook.internal.SUGGESTED_EVENTS_HISTORY";
    private static final String SUGGESTED_EVENTS_HISTORY = "SUGGESTED_EVENTS_HISTORY";
    private static SharedPreferences shardPreferences;
    public static final PredictionHistoryManager INSTANCE = new PredictionHistoryManager();
    private static final Map<String, String> clickedViewPaths = new LinkedHashMap();
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private PredictionHistoryManager() {
    }

    private final void initAndWait() {
        if (initialized.get()) {
            return;
        }
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(CLICKED_PATH_STORE, 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "FacebookSdk.getApplicationContext()\n            .getSharedPreferences(CLICKED_PATH_STORE, Context.MODE_PRIVATE)");
        shardPreferences = sharedPreferences;
        Map<String, String> map = clickedViewPaths;
        Utility utility = Utility.INSTANCE;
        SharedPreferences sharedPreferences2 = shardPreferences;
        if (sharedPreferences2 != null) {
            String string = sharedPreferences2.getString(SUGGESTED_EVENTS_HISTORY, "");
            if (string == null) {
                string = "";
            }
            map.putAll(Utility.jsonStrToMap(string));
            initialized.set(true);
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("shardPreferences");
        throw null;
    }

    @JvmStatic
    public static final void addPrediction(String pathID, String predictedEvent) {
        Intrinsics.checkNotNullParameter(pathID, "pathID");
        Intrinsics.checkNotNullParameter(predictedEvent, "predictedEvent");
        if (!initialized.get()) {
            INSTANCE.initAndWait();
        }
        clickedViewPaths.put(pathID, predictedEvent);
        SharedPreferences sharedPreferences = shardPreferences;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shardPreferences");
            throw null;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        PredictionHistoryManager predictionHistoryManager = INSTANCE;
        Utility utility = Utility.INSTANCE;
        edit.putString(SUGGESTED_EVENTS_HISTORY, Utility.mapToJsonStr(MapsKt.toMap(clickedViewPaths))).apply();
    }

    @JvmStatic
    public static final String getPathID(View view, String text) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(text, "text");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("text", text);
            JSONArray jSONArray = new JSONArray();
            while (view != null) {
                jSONArray.put(view.getClass().getSimpleName());
                ViewHierarchy viewHierarchy = ViewHierarchy.INSTANCE;
                view = ViewHierarchy.getParentOfView(view);
            }
            jSONObject.put(ViewHierarchyConstants.CLASS_NAME_KEY, jSONArray);
        } catch (JSONException unused) {
        }
        Utility utility = Utility.INSTANCE;
        return Utility.sha256hash(jSONObject.toString());
    }

    @JvmStatic
    public static final String queryEvent(String pathID) {
        Intrinsics.checkNotNullParameter(pathID, "pathID");
        if (clickedViewPaths.containsKey(pathID)) {
            return clickedViewPaths.get(pathID);
        }
        return null;
    }
}
