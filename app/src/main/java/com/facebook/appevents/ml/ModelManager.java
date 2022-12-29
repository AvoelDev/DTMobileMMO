package com.facebook.appevents.ml;

import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.GraphRequest;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.integrity.IntegrityManager;
import com.facebook.appevents.internal.FileDownloadTask;
import com.facebook.appevents.ml.ModelManager;
import com.facebook.appevents.suggestedevents.SuggestedEventsManager;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.facebook.internal.FeatureManager;
import com.facebook.internal.Utility;
import com.facebook.share.internal.ShareConstants;
import com.ssjj.fnsdk.core.update.EventUpdate;
import com.ssjj.fnsdk.platform.FNApiEN;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ModelManager.kt */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\bÇ\u0002\u0018\u00002\u00020\u0001:\u000278B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u0019H\u0007J\b\u0010\u001d\u001a\u00020\u0019H\u0002J\n\u0010\u001e\u001a\u0004\u0018\u00010\u001bH\u0002J\u0012\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%H\u0002J\u0014\u0010&\u001a\u0004\u0018\u00010'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J\u0010\u0010*\u001a\u00020\u001b2\u0006\u0010+\u001a\u00020\u001bH\u0002J9\u0010,\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010-2\u0006\u0010!\u001a\u00020\"2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020'0-2\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00040-H\u0007¢\u0006\u0002\u00100J%\u00101\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010-2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020'H\u0002¢\u0006\u0002\u00105J%\u00106\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010-2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020'H\u0002¢\u0006\u0002\u00105R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014R\u001c\u0010\u0015\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00170\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lcom/facebook/appevents/ml/ModelManager;", "", "()V", "ASSET_URI_KEY", "", "CACHE_KEY_MODELS", "CACHE_KEY_REQUEST_TIMESTAMP", "MODEL_ASSERT_STORE", "MODEL_REQUEST_INTERVAL_MILLISECONDS", "", "MTML_INTEGRITY_DETECT_PREDICTION", "", "MTML_SUGGESTED_EVENTS_PREDICTION", "MTML_USE_CASE", "RULES_URI_KEY", "THRESHOLD_KEY", "USE_CASE_KEY", "VERSION_ID_KEY", "isLocaleEnglish", "", "()Z", "taskHandlers", "", "Lcom/facebook/appevents/ml/ModelManager$TaskHandler;", "addModels", "", ModelManager.CACHE_KEY_MODELS, "Lorg/json/JSONObject;", "enable", "enableMTML", "fetchModels", "getRuleFile", "Ljava/io/File;", "task", "Lcom/facebook/appevents/ml/ModelManager$Task;", "isValidTimestamp", "timestamp", "", "parseJsonArray", "", "jsonArray", "Lorg/json/JSONArray;", "parseRawJsonObject", "jsonObject", "predict", "", "denses", "texts", "(Lcom/facebook/appevents/ml/ModelManager$Task;[[F[Ljava/lang/String;)[Ljava/lang/String;", "processIntegrityDetectionResult", "res", "Lcom/facebook/appevents/ml/MTensor;", ModelManager.THRESHOLD_KEY, "(Lcom/facebook/appevents/ml/MTensor;[F)[Ljava/lang/String;", "processSuggestedEventResult", "Task", "TaskHandler", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class ModelManager {
    private static final String ASSET_URI_KEY = "asset_uri";
    private static final String CACHE_KEY_MODELS = "models";
    private static final String CACHE_KEY_REQUEST_TIMESTAMP = "model_request_timestamp";
    private static final String MODEL_ASSERT_STORE = "com.facebook.internal.MODEL_STORE";
    public static final int MODEL_REQUEST_INTERVAL_MILLISECONDS = 259200000;
    private static final String MTML_USE_CASE = "MTML";
    private static final String RULES_URI_KEY = "rules_uri";
    private static final String THRESHOLD_KEY = "thresholds";
    private static final String USE_CASE_KEY = "use_case";
    private static final String VERSION_ID_KEY = "version_id";
    public static final ModelManager INSTANCE = new ModelManager();
    private static final Map<String, TaskHandler> taskHandlers = new ConcurrentHashMap();
    private static final List<String> MTML_SUGGESTED_EVENTS_PREDICTION = CollectionsKt.listOf((Object[]) new String[]{"other", AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, AppEventsConstants.EVENT_NAME_ADDED_TO_CART, AppEventsConstants.EVENT_NAME_PURCHASED, AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT});
    private static final List<String> MTML_INTEGRITY_DETECT_PREDICTION = CollectionsKt.listOf((Object[]) new String[]{"none", "address", IntegrityManager.INTEGRITY_TYPE_HEALTH});

    /* compiled from: ModelManager.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Task.valuesCustom().length];
            iArr[Task.MTML_APP_EVENT_PREDICTION.ordinal()] = 1;
            iArr[Task.MTML_INTEGRITY_DETECT.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private ModelManager() {
    }

    /* compiled from: ModelManager.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0004j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lcom/facebook/appevents/ml/ModelManager$Task;", "", "(Ljava/lang/String;I)V", "toKey", "", "toUseCase", "MTML_INTEGRITY_DETECT", "MTML_APP_EVENT_PREDICTION", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public enum Task {
        MTML_INTEGRITY_DETECT,
        MTML_APP_EVENT_PREDICTION;

        /* compiled from: ModelManager.kt */
        @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
        /* loaded from: classes3.dex */
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Task.valuesCustom().length];
                iArr[Task.MTML_INTEGRITY_DETECT.ordinal()] = 1;
                iArr[Task.MTML_APP_EVENT_PREDICTION.ordinal()] = 2;
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public final String toKey() {
            int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
            if (i != 1) {
                if (i == 2) {
                    return "app_event_pred";
                }
                throw new NoWhenBranchMatchedException();
            }
            return "integrity_detect";
        }

        public final String toUseCase() {
            int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
            if (i != 1) {
                if (i == 2) {
                    return "MTML_APP_EVENT_PRED";
                }
                throw new NoWhenBranchMatchedException();
            }
            return "MTML_INTEGRITY_DETECT";
        }

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static Task[] valuesCustom() {
            Task[] valuesCustom = values();
            return (Task[]) Arrays.copyOf(valuesCustom, valuesCustom.length);
        }
    }

    @JvmStatic
    public static final void enable() {
        Utility utility = Utility.INSTANCE;
        Utility.runOnNonUiThread($$Lambda$ModelManager$KNMvWVVOx1J2nhRNfxa7Dpikjp0.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0056 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0057 A[Catch: Exception -> 0x0078, TryCatch #0 {Exception -> 0x0078, blocks: (B:31:0x0004, B:33:0x0018, B:38:0x0025, B:40:0x0030, B:42:0x0040, B:44:0x0046, B:50:0x006e, B:46:0x004e, B:49:0x0057, B:39:0x002b), top: B:53:0x0004 }] */
    /* renamed from: enable$lambda-0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m90enable$lambda0() {
        /*
            java.lang.String r0 = "model_request_timestamp"
            java.lang.String r1 = "models"
            com.facebook.FacebookSdk r2 = com.facebook.FacebookSdk.INSTANCE     // Catch: java.lang.Exception -> L78
            android.content.Context r2 = com.facebook.FacebookSdk.getApplicationContext()     // Catch: java.lang.Exception -> L78
            java.lang.String r3 = "com.facebook.internal.MODEL_STORE"
            r4 = 0
            android.content.SharedPreferences r2 = r2.getSharedPreferences(r3, r4)     // Catch: java.lang.Exception -> L78
            r3 = 0
            java.lang.String r3 = r2.getString(r1, r3)     // Catch: java.lang.Exception -> L78
            if (r3 == 0) goto L2b
            r5 = r3
            java.lang.CharSequence r5 = (java.lang.CharSequence) r5     // Catch: java.lang.Exception -> L78
            int r5 = r5.length()     // Catch: java.lang.Exception -> L78
            if (r5 != 0) goto L22
            r4 = 1
        L22:
            if (r4 == 0) goto L25
            goto L2b
        L25:
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch: java.lang.Exception -> L78
            r4.<init>(r3)     // Catch: java.lang.Exception -> L78
            goto L30
        L2b:
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch: java.lang.Exception -> L78
            r4.<init>()     // Catch: java.lang.Exception -> L78
        L30:
            r5 = 0
            long r5 = r2.getLong(r0, r5)     // Catch: java.lang.Exception -> L78
            com.facebook.internal.FeatureManager r3 = com.facebook.internal.FeatureManager.INSTANCE     // Catch: java.lang.Exception -> L78
            com.facebook.internal.FeatureManager$Feature r3 = com.facebook.internal.FeatureManager.Feature.ModelRequest     // Catch: java.lang.Exception -> L78
            boolean r3 = com.facebook.internal.FeatureManager.isEnabled(r3)     // Catch: java.lang.Exception -> L78
            if (r3 == 0) goto L4e
            int r3 = r4.length()     // Catch: java.lang.Exception -> L78
            if (r3 == 0) goto L4e
            com.facebook.appevents.ml.ModelManager r3 = com.facebook.appevents.ml.ModelManager.INSTANCE     // Catch: java.lang.Exception -> L78
            boolean r3 = r3.isValidTimestamp(r5)     // Catch: java.lang.Exception -> L78
            if (r3 != 0) goto L6e
        L4e:
            com.facebook.appevents.ml.ModelManager r3 = com.facebook.appevents.ml.ModelManager.INSTANCE     // Catch: java.lang.Exception -> L78
            org.json.JSONObject r4 = r3.fetchModels()     // Catch: java.lang.Exception -> L78
            if (r4 != 0) goto L57
            return
        L57:
            android.content.SharedPreferences$Editor r2 = r2.edit()     // Catch: java.lang.Exception -> L78
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Exception -> L78
            android.content.SharedPreferences$Editor r1 = r2.putString(r1, r3)     // Catch: java.lang.Exception -> L78
            long r2 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Exception -> L78
            android.content.SharedPreferences$Editor r0 = r1.putLong(r0, r2)     // Catch: java.lang.Exception -> L78
            r0.apply()     // Catch: java.lang.Exception -> L78
        L6e:
            com.facebook.appevents.ml.ModelManager r0 = com.facebook.appevents.ml.ModelManager.INSTANCE     // Catch: java.lang.Exception -> L78
            r0.addModels(r4)     // Catch: java.lang.Exception -> L78
            com.facebook.appevents.ml.ModelManager r0 = com.facebook.appevents.ml.ModelManager.INSTANCE     // Catch: java.lang.Exception -> L78
            r0.enableMTML()     // Catch: java.lang.Exception -> L78
        L78:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.ml.ModelManager.m90enable$lambda0():void");
    }

    private final boolean isValidTimestamp(long j) {
        return j != 0 && System.currentTimeMillis() - j < 259200000;
    }

    private final void addModels(JSONObject jSONObject) {
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            try {
                TaskHandler build = TaskHandler.Companion.build(jSONObject.getJSONObject(keys.next()));
                if (build != null) {
                    taskHandlers.put(build.getUseCase(), build);
                }
            } catch (JSONException unused) {
                return;
            }
        }
    }

    private final JSONObject parseRawJsonObject(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("data");
            int i = 0;
            int length = jSONArray.length();
            if (length <= 0) {
                return jSONObject2;
            }
            while (true) {
                int i2 = i + 1;
                JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put(VERSION_ID_KEY, jSONObject3.getString(VERSION_ID_KEY));
                jSONObject4.put(USE_CASE_KEY, jSONObject3.getString(USE_CASE_KEY));
                jSONObject4.put(THRESHOLD_KEY, jSONObject3.getJSONArray(THRESHOLD_KEY));
                jSONObject4.put(ASSET_URI_KEY, jSONObject3.getString(ASSET_URI_KEY));
                if (jSONObject3.has(RULES_URI_KEY)) {
                    jSONObject4.put(RULES_URI_KEY, jSONObject3.getString(RULES_URI_KEY));
                }
                jSONObject2.put(jSONObject3.getString(USE_CASE_KEY), jSONObject4);
                if (i2 >= length) {
                    return jSONObject2;
                }
                i = i2;
            }
        } catch (JSONException unused) {
            return new JSONObject();
        }
    }

    private final JSONObject fetchModels() {
        String[] strArr = {USE_CASE_KEY, VERSION_ID_KEY, ASSET_URI_KEY, RULES_URI_KEY, THRESHOLD_KEY};
        Bundle bundle = new Bundle();
        bundle.putString(GraphRequest.FIELDS_PARAM, TextUtils.join(",", strArr));
        GraphRequest newGraphPathRequest = GraphRequest.Companion.newGraphPathRequest(null, "app/model_asset", null);
        newGraphPathRequest.setParameters(bundle);
        JSONObject jSONObject = newGraphPathRequest.executeAndWait().getJSONObject();
        if (jSONObject == null) {
            return null;
        }
        return parseRawJsonObject(jSONObject);
    }

    private final void enableMTML() {
        ArrayList arrayList = new ArrayList();
        String str = null;
        int i = 0;
        for (Map.Entry<String, TaskHandler> entry : taskHandlers.entrySet()) {
            String key = entry.getKey();
            TaskHandler value = entry.getValue();
            if (Intrinsics.areEqual(key, Task.MTML_APP_EVENT_PREDICTION.toUseCase())) {
                String assetUri = value.getAssetUri();
                int max = Math.max(i, value.getVersionId());
                FeatureManager featureManager = FeatureManager.INSTANCE;
                if (FeatureManager.isEnabled(FeatureManager.Feature.SuggestedEvents) && isLocaleEnglish()) {
                    arrayList.add(value.setOnPostExecute($$Lambda$ModelManager$b9HGJdS7rH7dtnacHJy1lAJYPTY.INSTANCE));
                }
                str = assetUri;
                i = max;
            }
            if (Intrinsics.areEqual(key, Task.MTML_INTEGRITY_DETECT.toUseCase())) {
                str = value.getAssetUri();
                i = Math.max(i, value.getVersionId());
                FeatureManager featureManager2 = FeatureManager.INSTANCE;
                if (FeatureManager.isEnabled(FeatureManager.Feature.IntelligentIntegrity)) {
                    arrayList.add(value.setOnPostExecute($$Lambda$ModelManager$hezzgqVvKqPN9xHTZB2MDRWPuaY.INSTANCE));
                }
            }
        }
        if (str == null || i <= 0 || arrayList.isEmpty()) {
            return;
        }
        TaskHandler.Companion.execute(new TaskHandler(MTML_USE_CASE, str, null, i, null), arrayList);
    }

    /* renamed from: enableMTML$lambda-1 */
    public static final void m91enableMTML$lambda1() {
        SuggestedEventsManager suggestedEventsManager = SuggestedEventsManager.INSTANCE;
        SuggestedEventsManager.enable();
    }

    /* renamed from: enableMTML$lambda-2 */
    public static final void m92enableMTML$lambda2() {
        IntegrityManager integrityManager = IntegrityManager.INSTANCE;
        IntegrityManager.enable();
    }

    private final boolean isLocaleEnglish() {
        Utility utility = Utility.INSTANCE;
        Locale resourceLocale = Utility.getResourceLocale();
        if (resourceLocale != null) {
            String language = resourceLocale.getLanguage();
            Intrinsics.checkNotNullExpressionValue(language, "locale.language");
            if (!StringsKt.contains$default((CharSequence) language, (CharSequence) FNApiEN.language_en, false, 2, (Object) null)) {
                return false;
            }
        }
        return true;
    }

    public final float[] parseJsonArray(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        float[] fArr = new float[jSONArray.length()];
        int i = 0;
        int length = jSONArray.length();
        if (length > 0) {
            while (true) {
                int i2 = i + 1;
                try {
                    String string = jSONArray.getString(i);
                    Intrinsics.checkNotNullExpressionValue(string, "jsonArray.getString(i)");
                    fArr[i] = Float.parseFloat(string);
                } catch (JSONException unused) {
                }
                if (i2 >= length) {
                    break;
                }
                i = i2;
            }
        }
        return fArr;
    }

    @JvmStatic
    public static final File getRuleFile(Task task) {
        Intrinsics.checkNotNullParameter(task, "task");
        TaskHandler taskHandler = taskHandlers.get(task.toUseCase());
        if (taskHandler == null) {
            return null;
        }
        return taskHandler.getRuleFile();
    }

    @JvmStatic
    public static final String[] predict(Task task, float[][] denses, String[] texts) {
        Intrinsics.checkNotNullParameter(task, "task");
        Intrinsics.checkNotNullParameter(denses, "denses");
        Intrinsics.checkNotNullParameter(texts, "texts");
        TaskHandler taskHandler = taskHandlers.get(task.toUseCase());
        Model model = taskHandler == null ? null : taskHandler.getModel();
        if (model == null) {
            return null;
        }
        float[] thresholds = taskHandler.getThresholds();
        int length = texts.length;
        int length2 = denses[0].length;
        MTensor mTensor = new MTensor(new int[]{length, length2});
        if (length > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                System.arraycopy(denses[i], 0, mTensor.getData(), i * length2, length2);
                if (i2 >= length) {
                    break;
                }
                i = i2;
            }
        }
        MTensor predictOnMTML = model.predictOnMTML(mTensor, texts, task.toKey());
        if (predictOnMTML != null && thresholds != null) {
            if (!(predictOnMTML.getData().length == 0)) {
                if (!(thresholds.length == 0)) {
                    int i3 = WhenMappings.$EnumSwitchMapping$0[task.ordinal()];
                    if (i3 != 1) {
                        if (i3 == 2) {
                            return INSTANCE.processIntegrityDetectionResult(predictOnMTML, thresholds);
                        }
                        throw new NoWhenBranchMatchedException();
                    }
                    return INSTANCE.processSuggestedEventResult(predictOnMTML, thresholds);
                }
            }
        }
        return null;
    }

    private final String[] processSuggestedEventResult(MTensor mTensor, float[] fArr) {
        int shape = mTensor.getShape(0);
        int shape2 = mTensor.getShape(1);
        float[] data = mTensor.getData();
        if (shape2 != fArr.length) {
            return null;
        }
        IntRange until = RangesKt.until(0, shape);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(until, 10));
        Iterator<Integer> it = until.iterator();
        while (it.hasNext()) {
            int nextInt = ((IntIterator) it).nextInt();
            int length = fArr.length;
            String str = "other";
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = i2 + 1;
                if (data[(nextInt * shape2) + i2] >= fArr[i]) {
                    str = MTML_SUGGESTED_EVENTS_PREDICTION.get(i2);
                }
                i++;
                i2 = i3;
            }
            arrayList.add(str);
        }
        Object[] array = arrayList.toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    private final String[] processIntegrityDetectionResult(MTensor mTensor, float[] fArr) {
        int shape = mTensor.getShape(0);
        int shape2 = mTensor.getShape(1);
        float[] data = mTensor.getData();
        if (shape2 != fArr.length) {
            return null;
        }
        IntRange until = RangesKt.until(0, shape);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(until, 10));
        Iterator<Integer> it = until.iterator();
        while (it.hasNext()) {
            int nextInt = ((IntIterator) it).nextInt();
            int length = fArr.length;
            String str = "none";
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = i2 + 1;
                if (data[(nextInt * shape2) + i2] >= fArr[i]) {
                    str = MTML_INTEGRITY_DETECT_PREDICTION.get(i2);
                }
                i++;
                i2 = i3;
            }
            arrayList.add(str);
        }
        Object[] array = arrayList.toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    /* compiled from: ModelManager.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u0000 *2\u00020\u0001:\u0001*B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0010\u0010)\u001a\u00020\u00002\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\f\"\u0004\b\u001e\u0010\u000eR\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\f\"\u0004\b$\u0010\u000eR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(¨\u0006+"}, d2 = {"Lcom/facebook/appevents/ml/ModelManager$TaskHandler;", "", "useCase", "", "assetUri", "ruleUri", "versionId", "", ModelManager.THRESHOLD_KEY, "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I[F)V", "getAssetUri", "()Ljava/lang/String;", "setAssetUri", "(Ljava/lang/String;)V", DeviceRequestsHelper.DEVICE_INFO_MODEL, "Lcom/facebook/appevents/ml/Model;", "getModel", "()Lcom/facebook/appevents/ml/Model;", "setModel", "(Lcom/facebook/appevents/ml/Model;)V", "onPostExecute", "Ljava/lang/Runnable;", "ruleFile", "Ljava/io/File;", "getRuleFile", "()Ljava/io/File;", "setRuleFile", "(Ljava/io/File;)V", "getRuleUri", "setRuleUri", "getThresholds", "()[F", "setThresholds", "([F)V", "getUseCase", "setUseCase", "getVersionId", "()I", "setVersionId", "(I)V", "setOnPostExecute", "Companion", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class TaskHandler {
        public static final Companion Companion = new Companion(null);
        private String assetUri;
        private Model model;
        private Runnable onPostExecute;
        private File ruleFile;
        private String ruleUri;
        private float[] thresholds;
        private String useCase;
        private int versionId;

        public TaskHandler(String useCase, String assetUri, String str, int i, float[] fArr) {
            Intrinsics.checkNotNullParameter(useCase, "useCase");
            Intrinsics.checkNotNullParameter(assetUri, "assetUri");
            this.useCase = useCase;
            this.assetUri = assetUri;
            this.ruleUri = str;
            this.versionId = i;
            this.thresholds = fArr;
        }

        public final String getUseCase() {
            return this.useCase;
        }

        public final void setUseCase(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.useCase = str;
        }

        public final String getAssetUri() {
            return this.assetUri;
        }

        public final void setAssetUri(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.assetUri = str;
        }

        public final String getRuleUri() {
            return this.ruleUri;
        }

        public final void setRuleUri(String str) {
            this.ruleUri = str;
        }

        public final int getVersionId() {
            return this.versionId;
        }

        public final void setVersionId(int i) {
            this.versionId = i;
        }

        public final float[] getThresholds() {
            return this.thresholds;
        }

        public final void setThresholds(float[] fArr) {
            this.thresholds = fArr;
        }

        public final File getRuleFile() {
            return this.ruleFile;
        }

        public final void setRuleFile(File file) {
            this.ruleFile = file;
        }

        public final Model getModel() {
            return this.model;
        }

        public final void setModel(Model model) {
            this.model = model;
        }

        public final TaskHandler setOnPostExecute(Runnable runnable) {
            this.onPostExecute = runnable;
            return this;
        }

        /* compiled from: ModelManager.kt */
        @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\"\u0010\r\u001a\u00020\b2\b\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u000e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0004J\u001c\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00042\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0016¨\u0006\u0017"}, d2 = {"Lcom/facebook/appevents/ml/ModelManager$TaskHandler$Companion;", "", "()V", "build", "Lcom/facebook/appevents/ml/ModelManager$TaskHandler;", "json", "Lorg/json/JSONObject;", "deleteOldFiles", "", "useCase", "", "versionId", "", EventUpdate.btn_download, ShareConstants.MEDIA_URI, "name", "onComplete", "Lcom/facebook/appevents/internal/FileDownloadTask$Callback;", "execute", "handler", "master", "slaves", "", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
        /* loaded from: classes3.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public static /* synthetic */ void lambda$tuOAgqtZTdthPCm1H_2rgX2tsR0(List list, File file) {
                m93execute$lambda1(list, file);
            }

            private Companion() {
            }

            public final TaskHandler build(JSONObject jSONObject) {
                if (jSONObject == null) {
                    return null;
                }
                try {
                    String useCase = jSONObject.getString(ModelManager.USE_CASE_KEY);
                    String assetUri = jSONObject.getString(ModelManager.ASSET_URI_KEY);
                    String optString = jSONObject.optString(ModelManager.RULES_URI_KEY, null);
                    int i = jSONObject.getInt(ModelManager.VERSION_ID_KEY);
                    float[] parseJsonArray = ModelManager.INSTANCE.parseJsonArray(jSONObject.getJSONArray(ModelManager.THRESHOLD_KEY));
                    Intrinsics.checkNotNullExpressionValue(useCase, "useCase");
                    Intrinsics.checkNotNullExpressionValue(assetUri, "assetUri");
                    return new TaskHandler(useCase, assetUri, optString, i, parseJsonArray);
                } catch (Exception unused) {
                    return null;
                }
            }

            public final void execute(TaskHandler handler) {
                Intrinsics.checkNotNullParameter(handler, "handler");
                execute(handler, CollectionsKt.listOf(handler));
            }

            public final void execute(TaskHandler master, final List<TaskHandler> slaves) {
                Intrinsics.checkNotNullParameter(master, "master");
                Intrinsics.checkNotNullParameter(slaves, "slaves");
                deleteOldFiles(master.getUseCase(), master.getVersionId());
                download(master.getAssetUri(), master.getUseCase() + '_' + master.getVersionId(), new FileDownloadTask.Callback() { // from class: com.facebook.appevents.ml.-$$Lambda$ModelManager$TaskHandler$Companion$tuOAgqtZTdthPCm1H_2rgX2tsR0
                    @Override // com.facebook.appevents.internal.FileDownloadTask.Callback
                    public final void onComplete(File file) {
                        ModelManager.TaskHandler.Companion.lambda$tuOAgqtZTdthPCm1H_2rgX2tsR0(slaves, file);
                    }
                });
            }

            /* renamed from: execute$lambda-1 */
            public static final void m93execute$lambda1(List slaves, File file) {
                Intrinsics.checkNotNullParameter(slaves, "$slaves");
                Intrinsics.checkNotNullParameter(file, "file");
                final Model build = Model.Companion.build(file);
                if (build != null) {
                    Iterator it = slaves.iterator();
                    while (it.hasNext()) {
                        final TaskHandler taskHandler = (TaskHandler) it.next();
                        TaskHandler.Companion.download(taskHandler.getRuleUri(), taskHandler.getUseCase() + '_' + taskHandler.getVersionId() + "_rule", new FileDownloadTask.Callback() { // from class: com.facebook.appevents.ml.-$$Lambda$ModelManager$TaskHandler$Companion$RiKUFcSTGa2hv93aQw_DYc3SLEI
                            @Override // com.facebook.appevents.internal.FileDownloadTask.Callback
                            public final void onComplete(File file2) {
                                ModelManager.TaskHandler.Companion.m94execute$lambda1$lambda0(ModelManager.TaskHandler.this, build, file2);
                            }
                        });
                    }
                }
            }

            /* renamed from: execute$lambda-1$lambda-0 */
            public static final void m94execute$lambda1$lambda0(TaskHandler slave, Model model, File file) {
                Intrinsics.checkNotNullParameter(slave, "$slave");
                Intrinsics.checkNotNullParameter(file, "file");
                slave.setModel(model);
                slave.setRuleFile(file);
                Runnable runnable = slave.onPostExecute;
                if (runnable == null) {
                    return;
                }
                runnable.run();
            }

            private final void deleteOldFiles(String str, int i) {
                File[] listFiles;
                Utils utils = Utils.INSTANCE;
                File mlDir = Utils.getMlDir();
                if (mlDir == null || (listFiles = mlDir.listFiles()) == null) {
                    return;
                }
                if (listFiles.length == 0) {
                    return;
                }
                String str2 = str + '_' + i;
                int length = listFiles.length;
                int i2 = 0;
                while (i2 < length) {
                    File file = listFiles[i2];
                    i2++;
                    String name = file.getName();
                    Intrinsics.checkNotNullExpressionValue(name, "name");
                    if (StringsKt.startsWith$default(name, str, false, 2, (Object) null) && !StringsKt.startsWith$default(name, str2, false, 2, (Object) null)) {
                        file.delete();
                    }
                }
            }

            private final void download(String str, String str2, FileDownloadTask.Callback callback) {
                Utils utils = Utils.INSTANCE;
                File file = new File(Utils.getMlDir(), str2);
                if (str == null || file.exists()) {
                    callback.onComplete(file);
                } else {
                    new FileDownloadTask(str, file, callback).execute(new String[0]);
                }
            }
        }
    }
}
