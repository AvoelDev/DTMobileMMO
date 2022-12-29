package com.facebook.appevents.suggestedevents;

import android.util.Patterns;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.share.internal.ShareInternalUtility;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ssjj.fnsdk.core.cg.FnSecPkgConfig;
import com.ssjjsy.net.SsjjsyRegion;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.openid.appauth.AuthorizationRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: FeatureExtractor.kt */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010$\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÁ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0006H\u0007J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0019\u001a\u00020\u0012H\u0002J \u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007J\u0012\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0007J\u0010\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u0012H\u0002J\b\u0010#\u001a\u00020\u000fH\u0007J)\u0010$\u001a\u00020\u000f2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00060&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00060&H\u0002¢\u0006\u0002\u0010(J0\u0010)\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u00122\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0002J\u0010\u0010.\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u0012H\u0002J\u0018\u0010/\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u00122\u0006\u0010*\u001a\u00020+H\u0002J\u0018\u00100\u001a\u00020\u000f2\u0006\u00101\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u0006H\u0002J(\u00100\u001a\u00020\u000f2\u0006\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u00020\u00062\u0006\u00105\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u0006H\u0002J\u0018\u00106\u001a\u00020\u001e2\u0006\u00107\u001a\u00020\u00152\u0006\u00108\u001a\u00020\u0015H\u0002J(\u00109\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u00122\n\u0010:\u001a\u00060;j\u0002`<2\n\u0010=\u001a\u00060;j\u0002`<H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\rX\u0082.¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lcom/facebook/appevents/suggestedevents/FeatureExtractor;", "", "()V", "NUM_OF_FEATURES", "", "REGEX_ADD_TO_CART_BUTTON_TEXT", "", "REGEX_ADD_TO_CART_PAGE_TITLE", "REGEX_CR_HAS_CONFIRM_PASSWORD_FIELD", "REGEX_CR_HAS_LOG_IN_KEYWORDS", "REGEX_CR_HAS_SIGN_ON_KEYWORDS", "REGEX_CR_PASSWORD_FIELD", "eventInfo", "", "initialized", "", "languageInfo", "rules", "Lorg/json/JSONObject;", "textTypeInfo", "getDenseFeatures", "", "viewHierarchy", "appName", "getInteractedNode", ViewHierarchyConstants.VIEW_KEY, "getTextFeature", "buttonText", "activityName", "initialize", "", ShareInternalUtility.STAGING_PARAM, "Ljava/io/File;", "isButton", "node", "isInitialized", "matchIndicators", "indicators", "", "values", "([Ljava/lang/String;[Ljava/lang/String;)Z", "nonparseFeatures", "siblings", "Lorg/json/JSONArray;", "screenName", "formFieldsJSON", "parseFeatures", "pruneTree", "regexMatched", "pattern", "matchText", "language", "event", "textType", "sum", "a", "b", "updateHintAndTextRecursively", "textSB", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "hintSB", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class FeatureExtractor {
    public static final FeatureExtractor INSTANCE = new FeatureExtractor();
    private static final int NUM_OF_FEATURES = 30;
    private static final String REGEX_ADD_TO_CART_BUTTON_TEXT = "(?i)add to(\\s|\\Z)|update(\\s|\\Z)|cart";
    private static final String REGEX_ADD_TO_CART_PAGE_TITLE = "(?i)add to(\\s|\\Z)|update(\\s|\\Z)|cart|shop|buy";
    private static final String REGEX_CR_HAS_CONFIRM_PASSWORD_FIELD = "(?i)(confirm.*password)|(password.*(confirmation|confirm)|confirmation)";
    private static final String REGEX_CR_HAS_LOG_IN_KEYWORDS = "(?i)(sign in)|login|signIn";
    private static final String REGEX_CR_HAS_SIGN_ON_KEYWORDS = "(?i)(sign.*(up|now)|registration|register|(create|apply).*(profile|account)|open.*account|account.*(open|creation|application)|enroll|join.*now)";
    private static final String REGEX_CR_PASSWORD_FIELD = "password";
    private static Map<String, String> eventInfo;
    private static boolean initialized;
    private static Map<String, String> languageInfo;
    private static JSONObject rules;
    private static Map<String, String> textTypeInfo;

    private FeatureExtractor() {
    }

    @JvmStatic
    public static final boolean isInitialized() {
        return initialized;
    }

    @JvmStatic
    public static final void initialize(File file) {
        try {
            FeatureExtractor featureExtractor = INSTANCE;
            rules = new JSONObject();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            fileInputStream.close();
            FeatureExtractor featureExtractor2 = INSTANCE;
            rules = new JSONObject(new String(bArr, Charsets.UTF_8));
            FeatureExtractor featureExtractor3 = INSTANCE;
            languageInfo = MapsKt.mapOf(TuplesKt.to(ViewHierarchyConstants.ENGLISH, "1"), TuplesKt.to(ViewHierarchyConstants.GERMAN, FnSecPkgConfig.CLOSE_PURCHASE), TuplesKt.to(ViewHierarchyConstants.SPANISH, "3"), TuplesKt.to(ViewHierarchyConstants.JAPANESE, FnSecPkgConfig.CLOSE_APP));
            FeatureExtractor featureExtractor4 = INSTANCE;
            eventInfo = MapsKt.mapOf(TuplesKt.to(ViewHierarchyConstants.VIEW_CONTENT, "0"), TuplesKt.to(ViewHierarchyConstants.SEARCH, "1"), TuplesKt.to(ViewHierarchyConstants.ADD_TO_CART, FnSecPkgConfig.CLOSE_PURCHASE), TuplesKt.to(ViewHierarchyConstants.ADD_TO_WISHLIST, "3"), TuplesKt.to(ViewHierarchyConstants.INITIATE_CHECKOUT, FnSecPkgConfig.CLOSE_APP), TuplesKt.to(ViewHierarchyConstants.ADD_PAYMENT_INFO, "5"), TuplesKt.to(ViewHierarchyConstants.PURCHASE, "6"), TuplesKt.to(ViewHierarchyConstants.LEAD, SsjjsyRegion.RU), TuplesKt.to(ViewHierarchyConstants.COMPLETE_REGISTRATION, "8"));
            FeatureExtractor featureExtractor5 = INSTANCE;
            textTypeInfo = MapsKt.mapOf(TuplesKt.to(ViewHierarchyConstants.BUTTON_TEXT, "1"), TuplesKt.to(ViewHierarchyConstants.PAGE_TITLE, FnSecPkgConfig.CLOSE_PURCHASE), TuplesKt.to(ViewHierarchyConstants.RESOLVED_DOCUMENT_LINK, "3"), TuplesKt.to(ViewHierarchyConstants.BUTTON_ID, FnSecPkgConfig.CLOSE_APP));
            FeatureExtractor featureExtractor6 = INSTANCE;
            initialized = true;
        } catch (Exception unused) {
        }
    }

    @JvmStatic
    public static final String getTextFeature(String buttonText, String activityName, String appName) {
        Intrinsics.checkNotNullParameter(buttonText, "buttonText");
        Intrinsics.checkNotNullParameter(activityName, "activityName");
        Intrinsics.checkNotNullParameter(appName, "appName");
        String str = appName + " | " + activityName + ", " + buttonText;
        if (str != null) {
            String lowerCase = str.toLowerCase();
            Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase()");
            return lowerCase;
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
    }

    @JvmStatic
    public static final float[] getDenseFeatures(JSONObject viewHierarchy, String appName) {
        String lowerCase;
        JSONObject jSONObject;
        String screenName;
        JSONArray jSONArray;
        JSONObject interactedNode;
        Intrinsics.checkNotNullParameter(viewHierarchy, "viewHierarchy");
        Intrinsics.checkNotNullParameter(appName, "appName");
        if (initialized) {
            FeatureExtractor featureExtractor = INSTANCE;
            float[] fArr = new float[30];
            for (int i = 0; i < 30; i++) {
                fArr[i] = 0.0f;
            }
            try {
                lowerCase = appName.toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase()");
                jSONObject = new JSONObject(viewHierarchy.optJSONObject(ViewHierarchyConstants.VIEW_KEY).toString());
                screenName = viewHierarchy.optString(ViewHierarchyConstants.SCREEN_NAME_KEY);
                jSONArray = new JSONArray();
                INSTANCE.pruneTree(jSONObject, jSONArray);
                INSTANCE.sum(fArr, INSTANCE.parseFeatures(jSONObject));
                interactedNode = INSTANCE.getInteractedNode(jSONObject);
            } catch (JSONException unused) {
            }
            if (interactedNode == null) {
                return null;
            }
            FeatureExtractor featureExtractor2 = INSTANCE;
            Intrinsics.checkNotNullExpressionValue(screenName, "screenName");
            String jSONObject2 = jSONObject.toString();
            Intrinsics.checkNotNullExpressionValue(jSONObject2, "viewTree.toString()");
            INSTANCE.sum(fArr, featureExtractor2.nonparseFeatures(interactedNode, jSONArray, screenName, jSONObject2, lowerCase));
            return fArr;
        }
        return null;
    }

    private final float[] parseFeatures(JSONObject jSONObject) {
        float[] fArr = new float[30];
        int i = 0;
        for (int i2 = 0; i2 < 30; i2++) {
            fArr[i2] = 0.0f;
        }
        String optString = jSONObject.optString("text");
        Intrinsics.checkNotNullExpressionValue(optString, "node.optString(TEXT_KEY)");
        String lowerCase = optString.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase()");
        String optString2 = jSONObject.optString(ViewHierarchyConstants.HINT_KEY);
        Intrinsics.checkNotNullExpressionValue(optString2, "node.optString(HINT_KEY)");
        String lowerCase2 = optString2.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "(this as java.lang.String).toLowerCase()");
        String optString3 = jSONObject.optString(ViewHierarchyConstants.CLASS_NAME_KEY);
        Intrinsics.checkNotNullExpressionValue(optString3, "node.optString(CLASS_NAME_KEY)");
        String lowerCase3 = optString3.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase3, "(this as java.lang.String).toLowerCase()");
        int optInt = jSONObject.optInt(ViewHierarchyConstants.INPUT_TYPE_KEY, -1);
        String[] strArr = {lowerCase, lowerCase2};
        if (matchIndicators(new String[]{"$", "amount", FirebaseAnalytics.Param.PRICE, "total"}, strArr)) {
            fArr[0] = fArr[0] + 1.0f;
        }
        if (matchIndicators(new String[]{"password", "pwd"}, strArr)) {
            fArr[1] = fArr[1] + 1.0f;
        }
        if (matchIndicators(new String[]{"tel", AuthorizationRequest.Scope.PHONE}, strArr)) {
            fArr[2] = fArr[2] + 1.0f;
        }
        if (matchIndicators(new String[]{FirebaseAnalytics.Event.SEARCH}, strArr)) {
            fArr[4] = fArr[4] + 1.0f;
        }
        if (optInt >= 0) {
            fArr[5] = fArr[5] + 1.0f;
        }
        if (optInt == 3 || optInt == 2) {
            fArr[6] = fArr[6] + 1.0f;
        }
        if (optInt == 32 || Patterns.EMAIL_ADDRESS.matcher(lowerCase).matches()) {
            fArr[7] = fArr[7] + 1.0f;
        }
        String str = lowerCase3;
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) "checkbox", false, 2, (Object) null)) {
            fArr[8] = fArr[8] + 1.0f;
        }
        if (matchIndicators(new String[]{"complete", "confirm", "done", "submit"}, new String[]{lowerCase})) {
            fArr[10] = fArr[10] + 1.0f;
        }
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) "radio", false, 2, (Object) null) && StringsKt.contains$default((CharSequence) str, (CharSequence) "button", false, 2, (Object) null)) {
            fArr[12] = fArr[12] + 1.0f;
        }
        try {
            JSONArray optJSONArray = jSONObject.optJSONArray(ViewHierarchyConstants.CHILDREN_VIEW_KEY);
            int length = optJSONArray.length();
            if (length > 0) {
                while (true) {
                    int i3 = i + 1;
                    JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                    Intrinsics.checkNotNullExpressionValue(jSONObject2, "childViews.getJSONObject(i)");
                    sum(fArr, parseFeatures(jSONObject2));
                    if (i3 >= length) {
                        break;
                    }
                    i = i3;
                }
            }
        } catch (JSONException unused) {
        }
        return fArr;
    }

    private final float[] nonparseFeatures(JSONObject jSONObject, JSONArray jSONArray, String str, String str2, String str3) {
        float[] fArr = new float[30];
        int i = 0;
        while (true) {
            if (i >= 30) {
                break;
            }
            fArr[i] = 0.0f;
            i++;
        }
        int length = jSONArray.length();
        fArr[3] = length > 1 ? length - 1.0f : 0.0f;
        try {
            int length2 = jSONArray.length();
            if (length2 > 0) {
                int i2 = 0;
                while (true) {
                    int i3 = i2 + 1;
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                    Intrinsics.checkNotNullExpressionValue(jSONObject2, "siblings.getJSONObject(i)");
                    if (isButton(jSONObject2)) {
                        fArr[9] = fArr[9] + 1.0f;
                    }
                    if (i3 >= length2) {
                        break;
                    }
                    i2 = i3;
                }
            }
        } catch (JSONException unused) {
        }
        fArr[13] = -1.0f;
        fArr[14] = -1.0f;
        String str4 = str + '|' + str3;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        updateHintAndTextRecursively(jSONObject, sb2, sb);
        String sb3 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb3, "hintSB.toString()");
        String sb4 = sb2.toString();
        Intrinsics.checkNotNullExpressionValue(sb4, "textSB.toString()");
        fArr[15] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.COMPLETE_REGISTRATION, ViewHierarchyConstants.BUTTON_TEXT, sb4) ? 1.0f : 0.0f;
        fArr[16] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.COMPLETE_REGISTRATION, ViewHierarchyConstants.PAGE_TITLE, str4) ? 1.0f : 0.0f;
        fArr[17] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.COMPLETE_REGISTRATION, ViewHierarchyConstants.BUTTON_ID, sb3) ? 1.0f : 0.0f;
        fArr[18] = StringsKt.contains$default((CharSequence) str2, (CharSequence) "password", false, 2, (Object) null) ? 1.0f : 0.0f;
        fArr[19] = regexMatched(REGEX_CR_HAS_CONFIRM_PASSWORD_FIELD, str2) ? 1.0f : 0.0f;
        fArr[20] = regexMatched(REGEX_CR_HAS_LOG_IN_KEYWORDS, str2) ? 1.0f : 0.0f;
        fArr[21] = regexMatched(REGEX_CR_HAS_SIGN_ON_KEYWORDS, str2) ? 1.0f : 0.0f;
        fArr[22] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.PURCHASE, ViewHierarchyConstants.BUTTON_TEXT, sb4) ? 1.0f : 0.0f;
        fArr[24] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.PURCHASE, ViewHierarchyConstants.PAGE_TITLE, str4) ? 1.0f : 0.0f;
        fArr[25] = regexMatched(REGEX_ADD_TO_CART_BUTTON_TEXT, sb4) ? 1.0f : 0.0f;
        fArr[27] = regexMatched(REGEX_ADD_TO_CART_PAGE_TITLE, str4) ? 1.0f : 0.0f;
        fArr[28] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.LEAD, ViewHierarchyConstants.BUTTON_TEXT, sb4) ? 1.0f : 0.0f;
        fArr[29] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.LEAD, ViewHierarchyConstants.PAGE_TITLE, str4) ? 1.0f : 0.0f;
        return fArr;
    }

    private final boolean regexMatched(String str, String str2, String str3, String str4) {
        JSONObject optJSONObject;
        JSONObject optJSONObject2;
        JSONObject optJSONObject3;
        JSONObject optJSONObject4;
        JSONObject jSONObject = rules;
        String str5 = null;
        if (jSONObject == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rules");
            throw null;
        }
        JSONObject optJSONObject5 = jSONObject.optJSONObject("rulesForLanguage");
        if (optJSONObject5 == null) {
            optJSONObject = null;
        } else {
            Map<String, String> map = languageInfo;
            if (map == null) {
                Intrinsics.throwUninitializedPropertyAccessException("languageInfo");
                throw null;
            }
            optJSONObject = optJSONObject5.optJSONObject(map.get(str));
        }
        if (optJSONObject == null || (optJSONObject2 = optJSONObject.optJSONObject("rulesForEvent")) == null) {
            optJSONObject3 = null;
        } else {
            Map<String, String> map2 = eventInfo;
            if (map2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("eventInfo");
                throw null;
            }
            optJSONObject3 = optJSONObject2.optJSONObject(map2.get(str2));
        }
        if (optJSONObject3 != null && (optJSONObject4 = optJSONObject3.optJSONObject("positiveRules")) != null) {
            Map<String, String> map3 = textTypeInfo;
            if (map3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("textTypeInfo");
                throw null;
            }
            str5 = optJSONObject4.optString(map3.get(str3));
        }
        if (str5 == null) {
            return false;
        }
        return regexMatched(str5, str4);
    }

    private final boolean regexMatched(String str, String str2) {
        return Pattern.compile(str).matcher(str2).find();
    }

    private final boolean matchIndicators(String[] strArr, String[] strArr2) {
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str = strArr[i];
            i++;
            int length2 = strArr2.length;
            int i2 = 0;
            while (i2 < length2) {
                String str2 = strArr2[i2];
                i2++;
                if (StringsKt.contains$default((CharSequence) str2, (CharSequence) str, false, 2, (Object) null)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean pruneTree(JSONObject jSONObject, JSONArray jSONArray) {
        boolean z;
        boolean z2;
        try {
            if (jSONObject.optBoolean(ViewHierarchyConstants.IS_INTERACTED_KEY)) {
                return true;
            }
            JSONArray optJSONArray = jSONObject.optJSONArray(ViewHierarchyConstants.CHILDREN_VIEW_KEY);
            int length = optJSONArray.length();
            if (length > 0) {
                int i = 0;
                while (true) {
                    int i2 = i + 1;
                    if (optJSONArray.getJSONObject(i).optBoolean(ViewHierarchyConstants.IS_INTERACTED_KEY)) {
                        z = true;
                        z2 = true;
                        break;
                    } else if (i2 >= length) {
                        break;
                    } else {
                        i = i2;
                    }
                }
            }
            z = false;
            z2 = false;
            JSONArray jSONArray2 = new JSONArray();
            if (z) {
                int length2 = optJSONArray.length();
                if (length2 > 0) {
                    int i3 = 0;
                    while (true) {
                        int i4 = i3 + 1;
                        jSONArray.put(optJSONArray.getJSONObject(i3));
                        if (i4 >= length2) {
                            break;
                        }
                        i3 = i4;
                    }
                }
            } else {
                int length3 = optJSONArray.length();
                if (length3 > 0) {
                    boolean z3 = z2;
                    int i5 = 0;
                    while (true) {
                        int i6 = i5 + 1;
                        JSONObject child = optJSONArray.getJSONObject(i5);
                        Intrinsics.checkNotNullExpressionValue(child, "child");
                        if (pruneTree(child, jSONArray)) {
                            jSONArray2.put(child);
                            z3 = true;
                        }
                        if (i6 >= length3) {
                            break;
                        }
                        i5 = i6;
                    }
                    z2 = z3;
                }
                jSONObject.put(ViewHierarchyConstants.CHILDREN_VIEW_KEY, jSONArray2);
            }
            return z2;
        } catch (JSONException unused) {
            return false;
        }
    }

    private final void sum(float[] fArr, float[] fArr2) {
        int length = fArr.length - 1;
        if (length < 0) {
            return;
        }
        int i = 0;
        while (true) {
            int i2 = i + 1;
            fArr[i] = fArr[i] + fArr2[i];
            if (i2 > length) {
                return;
            }
            i = i2;
        }
    }

    private final boolean isButton(JSONObject jSONObject) {
        return ((jSONObject.optInt(ViewHierarchyConstants.CLASS_TYPE_BITMASK_KEY) & 1) << 5) > 0;
    }

    private final void updateHintAndTextRecursively(JSONObject jSONObject, StringBuilder sb, StringBuilder sb2) {
        int length;
        String optString = jSONObject.optString("text", "");
        Intrinsics.checkNotNullExpressionValue(optString, "view.optString(TEXT_KEY, \"\")");
        String lowerCase = optString.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase()");
        String optString2 = jSONObject.optString(ViewHierarchyConstants.HINT_KEY, "");
        Intrinsics.checkNotNullExpressionValue(optString2, "view.optString(HINT_KEY, \"\")");
        String lowerCase2 = optString2.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase2, "(this as java.lang.String).toLowerCase()");
        int i = 0;
        if (lowerCase.length() > 0) {
            sb.append(lowerCase);
            sb.append(" ");
        }
        if (lowerCase2.length() > 0) {
            sb2.append(lowerCase2);
            sb2.append(" ");
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(ViewHierarchyConstants.CHILDREN_VIEW_KEY);
        if (optJSONArray == null || (length = optJSONArray.length()) <= 0) {
            return;
        }
        while (true) {
            int i2 = i + 1;
            try {
                JSONObject currentChildView = optJSONArray.getJSONObject(i);
                Intrinsics.checkNotNullExpressionValue(currentChildView, "currentChildView");
                updateHintAndTextRecursively(currentChildView, sb, sb2);
            } catch (JSONException unused) {
            }
            if (i2 >= length) {
                return;
            }
            i = i2;
        }
    }

    private final JSONObject getInteractedNode(JSONObject jSONObject) {
        if (jSONObject.optBoolean(ViewHierarchyConstants.IS_INTERACTED_KEY)) {
            return jSONObject;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(ViewHierarchyConstants.CHILDREN_VIEW_KEY);
        if (optJSONArray == null) {
            return null;
        }
        int i = 0;
        int length = optJSONArray.length();
        if (length > 0) {
            while (true) {
                int i2 = i + 1;
                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                Intrinsics.checkNotNullExpressionValue(jSONObject2, "children.getJSONObject(i)");
                JSONObject interactedNode = getInteractedNode(jSONObject2);
                if (interactedNode != null) {
                    return interactedNode;
                }
                if (i2 >= length) {
                    break;
                }
                i = i2;
            }
        }
        return null;
    }
}
