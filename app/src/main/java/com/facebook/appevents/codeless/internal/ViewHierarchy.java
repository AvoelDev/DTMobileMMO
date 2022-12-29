package com.facebook.appevents.codeless.internal;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.facebook.internal.Utility;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ViewHierarchy.kt */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0010\u001a\u0004\u0018\u00010\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0007J\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u00132\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0007J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\fH\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\fH\u0007J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\fH\u0002J\u0016\u0010\u0018\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0004H\u0002J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0007J\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0007J\u0012\u0010\u001f\u001a\u00020\u00042\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0007J\u0014\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0007J\u0012\u0010\"\u001a\u00020\u00042\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0007J\u001e\u0010#\u001a\u0004\u0018\u00010\f2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010\fH\u0002J\u0010\u0010'\u001a\u00020%2\u0006\u0010\u0011\u001a\u00020\fH\u0002J\b\u0010(\u001a\u00020)H\u0002J\u0010\u0010*\u001a\u00020+2\u0006\u0010\u0011\u001a\u00020\fH\u0003J\u0018\u0010,\u001a\u00020+2\u0006\u0010\u0011\u001a\u00020\f2\b\u0010&\u001a\u0004\u0018\u00010\fJ\u0010\u0010-\u001a\u00020+2\u0006\u0010\u0011\u001a\u00020\fH\u0002J\u001a\u0010.\u001a\u00020)2\u0006\u0010\u0011\u001a\u00020\f2\b\u0010/\u001a\u0004\u0018\u00010\u001cH\u0007J \u00100\u001a\u00020)2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u00101\u001a\u00020\u00162\u0006\u00102\u001a\u000203H\u0007J\u0018\u00104\u001a\u00020)2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u00101\u001a\u00020\u0016H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lcom/facebook/appevents/codeless/internal/ViewHierarchy;", "", "()V", "CLASS_RCTROOTVIEW", "", "CLASS_RCTVIEWGROUP", "CLASS_TOUCHTARGETHELPER", "ICON_MAX_EDGE_LENGTH", "", "METHOD_FIND_TOUCHTARGET_VIEW", "RCTRootViewReference", "Ljava/lang/ref/WeakReference;", "Landroid/view/View;", "TAG", "methodFindTouchTargetView", "Ljava/lang/reflect/Method;", "findRCTRootView", ViewHierarchyConstants.VIEW_KEY, "getChildrenOfView", "", "getClassTypeBitmask", "getDictionaryOfView", "Lorg/json/JSONObject;", "getDimensionOfView", "getExistingClass", "Ljava/lang/Class;", "className", "getExistingOnClickListener", "Landroid/view/View$OnClickListener;", "getExistingOnTouchListener", "Landroid/view/View$OnTouchListener;", "getHintOfView", "getParentOfView", "Landroid/view/ViewGroup;", "getTextOfView", "getTouchReactView", FirebaseAnalytics.Param.LOCATION, "", "RCTRootView", "getViewLocationOnScreen", "initTouchTargetHelperMethods", "", "isAdapterViewItem", "", "isRCTButton", "isRCTRootView", "setOnClickListener", "newListener", "updateAppearanceOfView", "json", "displayDensity", "", "updateBasicInfoOfView", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class ViewHierarchy {
    private static final String CLASS_RCTROOTVIEW = "com.facebook.react.ReactRootView";
    private static final String CLASS_RCTVIEWGROUP = "com.facebook.react.views.view.ReactViewGroup";
    private static final String CLASS_TOUCHTARGETHELPER = "com.facebook.react.uimanager.TouchTargetHelper";
    private static final int ICON_MAX_EDGE_LENGTH = 44;
    private static final String METHOD_FIND_TOUCHTARGET_VIEW = "findTouchTargetView";
    private static Method methodFindTouchTargetView;
    public static final ViewHierarchy INSTANCE = new ViewHierarchy();
    private static final String TAG = ViewHierarchy.class.getCanonicalName();
    private static WeakReference<View> RCTRootViewReference = new WeakReference<>(null);

    private ViewHierarchy() {
    }

    @JvmStatic
    public static final ViewGroup getParentOfView(View view) {
        if (view == null) {
            return null;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            return (ViewGroup) parent;
        }
        return null;
    }

    @JvmStatic
    public static final List<View> getChildrenOfView(View view) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            int i = 0;
            if (childCount > 0) {
                while (true) {
                    int i2 = i + 1;
                    arrayList.add(viewGroup.getChildAt(i));
                    if (i2 >= childCount) {
                        break;
                    }
                    i = i2;
                }
            }
        }
        return arrayList;
    }

    @JvmStatic
    public static final void updateBasicInfoOfView(View view, JSONObject json) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(json, "json");
        try {
            ViewHierarchy viewHierarchy = INSTANCE;
            String textOfView = getTextOfView(view);
            ViewHierarchy viewHierarchy2 = INSTANCE;
            String hintOfView = getHintOfView(view);
            Object tag = view.getTag();
            CharSequence contentDescription = view.getContentDescription();
            json.put(ViewHierarchyConstants.CLASS_NAME_KEY, view.getClass().getCanonicalName());
            ViewHierarchy viewHierarchy3 = INSTANCE;
            json.put(ViewHierarchyConstants.CLASS_TYPE_BITMASK_KEY, getClassTypeBitmask(view));
            json.put("id", view.getId());
            SensitiveUserDataUtils sensitiveUserDataUtils = SensitiveUserDataUtils.INSTANCE;
            if (!SensitiveUserDataUtils.isSensitiveUserData(view)) {
                Utility utility = Utility.INSTANCE;
                Utility utility2 = Utility.INSTANCE;
                json.put("text", Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(textOfView), ""));
            } else {
                json.put("text", "");
                json.put(ViewHierarchyConstants.IS_USER_INPUT_KEY, true);
            }
            Utility utility3 = Utility.INSTANCE;
            Utility utility4 = Utility.INSTANCE;
            json.put(ViewHierarchyConstants.HINT_KEY, Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(hintOfView), ""));
            if (tag != null) {
                Utility utility5 = Utility.INSTANCE;
                Utility utility6 = Utility.INSTANCE;
                json.put(ViewHierarchyConstants.TAG_KEY, Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(tag.toString()), ""));
            }
            if (contentDescription != null) {
                Utility utility7 = Utility.INSTANCE;
                Utility utility8 = Utility.INSTANCE;
                json.put("description", Utility.coerceValueIfNullOrEmpty(Utility.sha256hash(contentDescription.toString()), ""));
            }
            json.put(ViewHierarchyConstants.DIMENSION_KEY, INSTANCE.getDimensionOfView(view));
        } catch (JSONException e) {
            Utility utility9 = Utility.INSTANCE;
            Utility.logd(TAG, e);
        }
    }

    @JvmStatic
    public static final void updateAppearanceOfView(View view, JSONObject json, float f) {
        Bitmap bitmap;
        Typeface typeface;
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(json, "json");
        try {
            JSONObject jSONObject = new JSONObject();
            if ((view instanceof TextView) && (typeface = ((TextView) view).getTypeface()) != null) {
                jSONObject.put(ViewHierarchyConstants.TEXT_SIZE, ((TextView) view).getTextSize());
                jSONObject.put(ViewHierarchyConstants.TEXT_IS_BOLD, typeface.isBold());
                jSONObject.put(ViewHierarchyConstants.TEXT_IS_ITALIC, typeface.isItalic());
                json.put(ViewHierarchyConstants.TEXT_STYLE, jSONObject);
            }
            if (view instanceof ImageView) {
                Drawable drawable = ((ImageView) view).getDrawable();
                if (drawable instanceof BitmapDrawable) {
                    ViewHierarchy viewHierarchy = INSTANCE;
                    float f2 = 44;
                    if (view.getHeight() / f <= f2) {
                        float width = view.getWidth() / f;
                        ViewHierarchy viewHierarchy2 = INSTANCE;
                        if (width > f2 || (bitmap = ((BitmapDrawable) drawable).getBitmap()) == null) {
                            return;
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        json.put(ViewHierarchyConstants.ICON_BITMAP, Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0));
                    }
                }
            }
        } catch (JSONException e) {
            Utility utility = Utility.INSTANCE;
            Utility.logd(TAG, e);
        }
    }

    @JvmStatic
    public static final JSONObject getDictionaryOfView(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        String name = view.getClass().getName();
        ViewHierarchy viewHierarchy = INSTANCE;
        if (Intrinsics.areEqual(name, CLASS_RCTROOTVIEW)) {
            ViewHierarchy viewHierarchy2 = INSTANCE;
            RCTRootViewReference = new WeakReference<>(view);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            ViewHierarchy viewHierarchy3 = INSTANCE;
            updateBasicInfoOfView(view, jSONObject);
            JSONArray jSONArray = new JSONArray();
            ViewHierarchy viewHierarchy4 = INSTANCE;
            List<View> childrenOfView = getChildrenOfView(view);
            int i = 0;
            int size = childrenOfView.size() - 1;
            if (size >= 0) {
                while (true) {
                    int i2 = i + 1;
                    ViewHierarchy viewHierarchy5 = INSTANCE;
                    jSONArray.put(getDictionaryOfView(childrenOfView.get(i)));
                    if (i2 > size) {
                        break;
                    }
                    i = i2;
                }
            }
            jSONObject.put(ViewHierarchyConstants.CHILDREN_VIEW_KEY, jSONArray);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to create JSONObject for view.", e);
        }
        return jSONObject;
    }

    @JvmStatic
    public static final int getClassTypeBitmask(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        int i = view instanceof ImageView ? 2 : 0;
        if (view.isClickable()) {
            i |= 32;
        }
        ViewHierarchy viewHierarchy = INSTANCE;
        if (isAdapterViewItem(view)) {
            i |= 512;
        }
        if (!(view instanceof TextView)) {
            return ((view instanceof Spinner) || (view instanceof DatePicker)) ? i | 4096 : view instanceof RatingBar ? i | 65536 : view instanceof RadioGroup ? i | 16384 : ((view instanceof ViewGroup) && INSTANCE.isRCTButton(view, RCTRootViewReference.get())) ? i | 64 : i;
        }
        int i2 = i | 1024 | 1;
        if (view instanceof Button) {
            i2 |= 4;
            if (view instanceof Switch) {
                i2 |= 8192;
            } else if (view instanceof CheckBox) {
                i2 |= 32768;
            }
        }
        return view instanceof EditText ? i2 | 2048 : i2;
    }

    @JvmStatic
    private static final boolean isAdapterViewItem(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof AdapterView) {
            return true;
        }
        Class<?> existingClass = INSTANCE.getExistingClass("android.support.v4.view.NestedScrollingChild");
        if (existingClass == null || !existingClass.isInstance(parent)) {
            Class<?> existingClass2 = INSTANCE.getExistingClass("androidx.core.view.NestedScrollingChild");
            return existingClass2 != null && existingClass2.isInstance(parent);
        }
        return true;
    }

    @JvmStatic
    public static final String getTextOfView(View view) {
        CharSequence valueOf;
        Object selectedItem;
        String obj;
        if (view instanceof TextView) {
            valueOf = ((TextView) view).getText();
            if (view instanceof Switch) {
                valueOf = ((Switch) view).isChecked() ? "1" : "0";
            }
        } else if (view instanceof Spinner) {
            Spinner spinner = (Spinner) view;
            if (spinner.getCount() > 0 && (selectedItem = spinner.getSelectedItem()) != null) {
                valueOf = selectedItem.toString();
            }
            valueOf = null;
        } else {
            int i = 0;
            if (view instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) view;
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                Object[] objArr = {Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(dayOfMonth)};
                valueOf = String.format("%04d-%02d-%02d", Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkNotNullExpressionValue(valueOf, "java.lang.String.format(format, *args)");
            } else if (view instanceof TimePicker) {
                TimePicker timePicker = (TimePicker) view;
                Integer currentHour = timePicker.getCurrentHour();
                Intrinsics.checkNotNullExpressionValue(currentHour, "view.currentHour");
                int intValue = currentHour.intValue();
                Integer currentMinute = timePicker.getCurrentMinute();
                Intrinsics.checkNotNullExpressionValue(currentMinute, "view.currentMinute");
                int intValue2 = currentMinute.intValue();
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                Object[] objArr2 = {Integer.valueOf(intValue), Integer.valueOf(intValue2)};
                valueOf = String.format("%02d:%02d", Arrays.copyOf(objArr2, objArr2.length));
                Intrinsics.checkNotNullExpressionValue(valueOf, "java.lang.String.format(format, *args)");
            } else if (view instanceof RadioGroup) {
                RadioGroup radioGroup = (RadioGroup) view;
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                int childCount = radioGroup.getChildCount();
                if (childCount > 0) {
                    while (true) {
                        int i2 = i + 1;
                        View childAt = radioGroup.getChildAt(i);
                        if (childAt.getId() == checkedRadioButtonId && (childAt instanceof RadioButton)) {
                            valueOf = ((RadioButton) childAt).getText();
                            break;
                        } else if (i2 >= childCount) {
                            break;
                        } else {
                            i = i2;
                        }
                    }
                }
                valueOf = null;
            } else {
                if (view instanceof RatingBar) {
                    valueOf = String.valueOf(((RatingBar) view).getRating());
                }
                valueOf = null;
            }
        }
        return (valueOf == null || (obj = valueOf.toString()) == null) ? "" : obj;
    }

    @JvmStatic
    public static final String getHintOfView(View view) {
        CharSequence hint;
        String obj;
        if (view instanceof EditText) {
            hint = ((EditText) view).getHint();
        } else {
            hint = view instanceof TextView ? ((TextView) view).getHint() : null;
        }
        return (hint == null || (obj = hint.toString()) == null) ? "" : obj;
    }

    private final JSONObject getDimensionOfView(View view) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ViewHierarchyConstants.DIMENSION_TOP_KEY, view.getTop());
            jSONObject.put("left", view.getLeft());
            jSONObject.put(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, view.getWidth());
            jSONObject.put(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, view.getHeight());
            jSONObject.put(ViewHierarchyConstants.DIMENSION_SCROLL_X_KEY, view.getScrollX());
            jSONObject.put(ViewHierarchyConstants.DIMENSION_SCROLL_Y_KEY, view.getScrollY());
            jSONObject.put(ViewHierarchyConstants.DIMENSION_VISIBILITY_KEY, view.getVisibility());
        } catch (JSONException e) {
            Log.e(TAG, "Failed to create JSONObject for dimension.", e);
        }
        return jSONObject;
    }

    @JvmStatic
    public static final View.OnClickListener getExistingOnClickListener(View view) {
        try {
            Field declaredField = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
            Intrinsics.checkNotNullExpressionValue(declaredField, "forName(\"android.view.View\").getDeclaredField(\"mListenerInfo\")");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(view);
            if (obj == null) {
                return null;
            }
            Field declaredField2 = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnClickListener");
            Intrinsics.checkNotNullExpressionValue(declaredField2, "forName(\"android.view.View\\$ListenerInfo\").getDeclaredField(\"mOnClickListener\")");
            declaredField2.setAccessible(true);
            Object obj2 = declaredField2.get(obj);
            if (obj2 != null) {
                return (View.OnClickListener) obj2;
            }
            throw new NullPointerException("null cannot be cast to non-null type android.view.View.OnClickListener");
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException unused) {
            return null;
        }
    }

    @JvmStatic
    public static final void setOnClickListener(View view, View.OnClickListener onClickListener) {
        Field field;
        Field field2;
        Intrinsics.checkNotNullParameter(view, "view");
        Object obj = null;
        try {
            try {
                field = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
                try {
                    field2 = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnClickListener");
                } catch (ClassNotFoundException | NoSuchFieldException unused) {
                    field2 = null;
                    if (field != null) {
                    }
                    view.setOnClickListener(onClickListener);
                    return;
                }
            } catch (Exception unused2) {
                return;
            }
        } catch (ClassNotFoundException | NoSuchFieldException unused3) {
            field = null;
        }
        if (field != null || field2 == null) {
            view.setOnClickListener(onClickListener);
            return;
        }
        field.setAccessible(true);
        field2.setAccessible(true);
        try {
            field.setAccessible(true);
            obj = field.get(view);
        } catch (IllegalAccessException unused4) {
        }
        if (obj == null) {
            view.setOnClickListener(onClickListener);
        } else {
            field2.set(obj, onClickListener);
        }
    }

    @JvmStatic
    public static final View.OnTouchListener getExistingOnTouchListener(View view) {
        try {
            Field declaredField = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
            Intrinsics.checkNotNullExpressionValue(declaredField, "forName(\"android.view.View\").getDeclaredField(\"mListenerInfo\")");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(view);
            if (obj == null) {
                return null;
            }
            Field declaredField2 = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnTouchListener");
            Intrinsics.checkNotNullExpressionValue(declaredField2, "forName(\"android.view.View\\$ListenerInfo\").getDeclaredField(\"mOnTouchListener\")");
            declaredField2.setAccessible(true);
            Object obj2 = declaredField2.get(obj);
            if (obj2 != null) {
                return (View.OnTouchListener) obj2;
            }
            throw new NullPointerException("null cannot be cast to non-null type android.view.View.OnTouchListener");
        } catch (ClassNotFoundException e) {
            Utility utility = Utility.INSTANCE;
            Utility.logd(TAG, e);
            return null;
        } catch (IllegalAccessException e2) {
            Utility utility2 = Utility.INSTANCE;
            Utility.logd(TAG, e2);
            return null;
        } catch (NoSuchFieldException e3) {
            Utility utility3 = Utility.INSTANCE;
            Utility.logd(TAG, e3);
            return null;
        }
    }

    private final View getTouchReactView(float[] fArr, View view) {
        initTouchTargetHelperMethods();
        Method method = methodFindTouchTargetView;
        if (method != null && view != null) {
            try {
                if (method == null) {
                    throw new IllegalStateException("Required value was null.".toString());
                }
                Object invoke = method.invoke(null, fArr, view);
                if (invoke == null) {
                    throw new NullPointerException("null cannot be cast to non-null type android.view.View");
                }
                View view2 = (View) invoke;
                if (view2.getId() > 0) {
                    ViewParent parent = view2.getParent();
                    if (parent != null) {
                        return (View) parent;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type android.view.View");
                }
            } catch (IllegalAccessException e) {
                Utility utility = Utility.INSTANCE;
                Utility.logd(TAG, e);
            } catch (InvocationTargetException e2) {
                Utility utility2 = Utility.INSTANCE;
                Utility.logd(TAG, e2);
            }
        }
        return null;
    }

    public final boolean isRCTButton(View view, View view2) {
        View touchReactView;
        Intrinsics.checkNotNullParameter(view, "view");
        String name = view.getClass().getName();
        Intrinsics.checkNotNullExpressionValue(name, "view.javaClass.name");
        return Intrinsics.areEqual(name, CLASS_RCTVIEWGROUP) && (touchReactView = getTouchReactView(getViewLocationOnScreen(view), view2)) != null && touchReactView.getId() == view.getId();
    }

    private final boolean isRCTRootView(View view) {
        return Intrinsics.areEqual(view.getClass().getName(), CLASS_RCTROOTVIEW);
    }

    @JvmStatic
    public static final View findRCTRootView(View view) {
        while (view != null) {
            if (INSTANCE.isRCTRootView(view)) {
                return view;
            }
            ViewParent parent = view.getParent();
            if (!(parent instanceof View)) {
                return null;
            }
            view = (View) parent;
        }
        return null;
    }

    private final float[] getViewLocationOnScreen(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return new float[]{iArr[0], iArr[1]};
    }

    private final void initTouchTargetHelperMethods() {
        if (methodFindTouchTargetView != null) {
            return;
        }
        try {
            Class<?> cls = Class.forName(CLASS_TOUCHTARGETHELPER);
            Intrinsics.checkNotNullExpressionValue(cls, "forName(CLASS_TOUCHTARGETHELPER)");
            methodFindTouchTargetView = cls.getDeclaredMethod(METHOD_FIND_TOUCHTARGET_VIEW, float[].class, ViewGroup.class);
            Method method = methodFindTouchTargetView;
            if (method == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            method.setAccessible(true);
        } catch (ClassNotFoundException e) {
            Utility utility = Utility.INSTANCE;
            Utility.logd(TAG, e);
        } catch (NoSuchMethodException e2) {
            Utility utility2 = Utility.INSTANCE;
            Utility.logd(TAG, e2);
        }
    }

    private final Class<?> getExistingClass(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }
}
