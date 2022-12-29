package com.facebook.appevents.aam;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import com.facebook.appevents.InternalAppEventsLogger;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: MetadataViewObserver.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0001\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0011H\u0002J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0011H\u0002J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u000fH\u0002J\b\u0010\u001a\u001a\u00020\u000fH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/facebook/appevents/aam/MetadataViewObserver;", "Landroid/view/ViewTreeObserver$OnGlobalFocusChangeListener;", "activity", "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "activityWeakReference", "Ljava/lang/ref/WeakReference;", "isTracking", "Ljava/util/concurrent/atomic/AtomicBoolean;", "processedText", "", "", "uiThreadHandler", "Landroid/os/Handler;", "onGlobalFocusChanged", "", "oldView", "Landroid/view/View;", "newView", "process", ViewHierarchyConstants.VIEW_KEY, "processEditText", "runOnUIThread", "runnable", "Ljava/lang/Runnable;", "startTracking", "stopTracking", "Companion", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class MetadataViewObserver implements ViewTreeObserver.OnGlobalFocusChangeListener {
    private static final int MAX_TEXT_LENGTH = 100;
    private final WeakReference<Activity> activityWeakReference;
    private final AtomicBoolean isTracking;
    private final Set<String> processedText;
    private final Handler uiThreadHandler;
    public static final Companion Companion = new Companion(null);
    private static final Map<Integer, MetadataViewObserver> observers = new HashMap();

    public /* synthetic */ MetadataViewObserver(Activity activity, DefaultConstructorMarker defaultConstructorMarker) {
        this(activity);
    }

    public static /* synthetic */ void lambda$R4imKob1ZhJzclyp9KdknR79d24(View view, MetadataViewObserver metadataViewObserver) {
        m63process$lambda0(view, metadataViewObserver);
    }

    @JvmStatic
    public static final void startTrackingActivity(Activity activity) {
        Companion.startTrackingActivity(activity);
    }

    @JvmStatic
    public static final void stopTrackingActivity(Activity activity) {
        Companion.stopTrackingActivity(activity);
    }

    private MetadataViewObserver(Activity activity) {
        this.processedText = new LinkedHashSet();
        this.uiThreadHandler = new Handler(Looper.getMainLooper());
        this.activityWeakReference = new WeakReference<>(activity);
        this.isTracking = new AtomicBoolean(false);
    }

    public final void startTracking() {
        if (this.isTracking.getAndSet(true)) {
            return;
        }
        AppEventUtility appEventUtility = AppEventUtility.INSTANCE;
        View rootView = AppEventUtility.getRootView(this.activityWeakReference.get());
        if (rootView == null) {
            return;
        }
        ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalFocusChangeListener(this);
        }
    }

    public final void stopTracking() {
        if (this.isTracking.getAndSet(false)) {
            AppEventUtility appEventUtility = AppEventUtility.INSTANCE;
            View rootView = AppEventUtility.getRootView(this.activityWeakReference.get());
            if (rootView == null) {
                return;
            }
            ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnGlobalFocusChangeListener(this);
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalFocusChangeListener
    public void onGlobalFocusChanged(View view, View view2) {
        if (view != null) {
            process(view);
        }
        if (view2 != null) {
            process(view2);
        }
    }

    private final void process(final View view) {
        runOnUIThread(new Runnable() { // from class: com.facebook.appevents.aam.-$$Lambda$MetadataViewObserver$R4imKob1ZhJzclyp9KdknR79d24
            @Override // java.lang.Runnable
            public final void run() {
                MetadataViewObserver.lambda$R4imKob1ZhJzclyp9KdknR79d24(view, this);
            }
        });
    }

    /* renamed from: process$lambda-0 */
    public static final void m63process$lambda0(View view, MetadataViewObserver this$0) {
        Intrinsics.checkNotNullParameter(view, "$view");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (view instanceof EditText) {
            this$0.processEditText(view);
        }
    }

    private final void processEditText(View view) {
        String obj = ((EditText) view).getText().toString();
        if (obj == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        String obj2 = StringsKt.trim((CharSequence) obj).toString();
        if (obj2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = obj2.toLowerCase();
        Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase()");
        if ((lowerCase.length() == 0) || this.processedText.contains(lowerCase) || lowerCase.length() > 100) {
            return;
        }
        this.processedText.add(lowerCase);
        HashMap hashMap = new HashMap();
        MetadataMatcher metadataMatcher = MetadataMatcher.INSTANCE;
        List<String> currentViewIndicators = MetadataMatcher.getCurrentViewIndicators(view);
        List<String> list = null;
        for (MetadataRule metadataRule : MetadataRule.Companion.getRules()) {
            String preNormalize = Companion.preNormalize(metadataRule.getName(), lowerCase);
            if (metadataRule.getValRule().length() > 0) {
                MetadataMatcher metadataMatcher2 = MetadataMatcher.INSTANCE;
                if (!MetadataMatcher.matchValue(preNormalize, metadataRule.getValRule())) {
                }
            }
            MetadataMatcher metadataMatcher3 = MetadataMatcher.INSTANCE;
            if (!MetadataMatcher.matchIndicator(currentViewIndicators, metadataRule.getKeyRules())) {
                if (list == null) {
                    MetadataMatcher metadataMatcher4 = MetadataMatcher.INSTANCE;
                    list = MetadataMatcher.getAroundViewIndicators(view);
                }
                MetadataMatcher metadataMatcher5 = MetadataMatcher.INSTANCE;
                if (MetadataMatcher.matchIndicator(list, metadataRule.getKeyRules())) {
                    Companion.putUserData(hashMap, metadataRule.getName(), preNormalize);
                }
            } else {
                Companion.putUserData(hashMap, metadataRule.getName(), preNormalize);
            }
        }
        InternalAppEventsLogger.Companion.setInternalUserData(hashMap);
    }

    private final void runOnUIThread(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            this.uiThreadHandler.post(runnable);
        }
    }

    /* compiled from: MetadataViewObserver.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0002J,\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0002J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/facebook/appevents/aam/MetadataViewObserver$Companion;", "", "()V", "MAX_TEXT_LENGTH", "", "observers", "", "Lcom/facebook/appevents/aam/MetadataViewObserver;", "preNormalize", "", "key", "value", "putUserData", "", "userData", "startTrackingActivity", "activity", "Landroid/app/Activity;", "stopTrackingActivity", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void startTrackingActivity(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            int hashCode = activity.hashCode();
            Map map = MetadataViewObserver.observers;
            Integer valueOf = Integer.valueOf(hashCode);
            Object obj = map.get(valueOf);
            if (obj == null) {
                obj = new MetadataViewObserver(activity, null);
                map.put(valueOf, obj);
            }
            ((MetadataViewObserver) obj).startTracking();
        }

        @JvmStatic
        public final void stopTrackingActivity(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            MetadataViewObserver metadataViewObserver = (MetadataViewObserver) MetadataViewObserver.observers.remove(Integer.valueOf(activity.hashCode()));
            if (metadataViewObserver == null) {
                return;
            }
            metadataViewObserver.stopTracking();
        }

        public final String preNormalize(String str, String str2) {
            if (Intrinsics.areEqual("r2", str)) {
                return new Regex("[^\\d.]").replace(str2, "");
            }
            return str2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:52:0x004a, code lost:
            if (r8.equals("r5") == false) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x0053, code lost:
            if (r8.equals("r4") == false) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x0056, code lost:
            r9 = new kotlin.text.Regex("[^a-z]+").replace(r9, "");
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void putUserData(java.util.Map<java.lang.String, java.lang.String> r7, java.lang.String r8, java.lang.String r9) {
            /*
                r6 = this;
                int r0 = r8.hashCode()
                r1 = 0
                r2 = 2
                r3 = 0
                switch(r0) {
                    case 3585: goto L66;
                    case 3586: goto L4d;
                    case 3587: goto L44;
                    case 3588: goto Lc;
                    default: goto La;
                }
            La:
                goto L8c
            Lc:
                java.lang.String r0 = "r6"
                boolean r0 = r8.equals(r0)
                if (r0 != 0) goto L16
                goto L8c
            L16:
                r0 = r9
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                java.lang.String r4 = "-"
                r5 = r4
                java.lang.CharSequence r5 = (java.lang.CharSequence) r5
                boolean r1 = kotlin.text.StringsKt.contains$default(r0, r5, r3, r2, r1)
                if (r1 == 0) goto L8c
                kotlin.text.Regex r9 = new kotlin.text.Regex
                r9.<init>(r4)
                java.util.List r9 = r9.split(r0, r3)
                java.util.Collection r9 = (java.util.Collection) r9
                java.lang.String[] r0 = new java.lang.String[r3]
                java.lang.Object[] r9 = r9.toArray(r0)
                if (r9 == 0) goto L3c
                java.lang.String[] r9 = (java.lang.String[]) r9
                r9 = r9[r3]
                goto L8c
            L3c:
                java.lang.NullPointerException r7 = new java.lang.NullPointerException
                java.lang.String r8 = "null cannot be cast to non-null type kotlin.Array<T>"
                r7.<init>(r8)
                throw r7
            L44:
                java.lang.String r0 = "r5"
                boolean r0 = r8.equals(r0)
                if (r0 != 0) goto L56
                goto L8c
            L4d:
                java.lang.String r0 = "r4"
                boolean r0 = r8.equals(r0)
                if (r0 != 0) goto L56
                goto L8c
            L56:
                java.lang.CharSequence r9 = (java.lang.CharSequence) r9
                kotlin.text.Regex r0 = new kotlin.text.Regex
                java.lang.String r1 = "[^a-z]+"
                r0.<init>(r1)
                java.lang.String r1 = ""
                java.lang.String r9 = r0.replace(r9, r1)
                goto L8c
            L66:
                java.lang.String r0 = "r3"
                boolean r0 = r8.equals(r0)
                if (r0 != 0) goto L6f
                goto L8c
            L6f:
                java.lang.String r0 = "m"
                boolean r4 = kotlin.text.StringsKt.startsWith$default(r9, r0, r3, r2, r1)
                if (r4 != 0) goto L8b
                java.lang.String r4 = "b"
                boolean r4 = kotlin.text.StringsKt.startsWith$default(r9, r4, r3, r2, r1)
                if (r4 != 0) goto L8b
                java.lang.String r4 = "ge"
                boolean r9 = kotlin.text.StringsKt.startsWith$default(r9, r4, r3, r2, r1)
                if (r9 == 0) goto L88
                goto L8b
            L88:
                java.lang.String r9 = "f"
                goto L8c
            L8b:
                r9 = r0
            L8c:
                r7.put(r8, r9)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.aam.MetadataViewObserver.Companion.putUserData(java.util.Map, java.lang.String, java.lang.String):void");
        }
    }
}
