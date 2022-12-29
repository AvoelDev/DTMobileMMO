package com.facebook.appevents.iap;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InAppPurchaseAutoLogger.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/facebook/appevents/iap/InAppPurchaseAutoLogger;", "", "()V", "BILLING_CLIENT_PURCHASE_NAME", "", "logPurchase", "", "startIapLogging", "context", "Landroid/content/Context;", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class InAppPurchaseAutoLogger {
    private static final String BILLING_CLIENT_PURCHASE_NAME = "com.android.billingclient.api.Purchase";
    public static final InAppPurchaseAutoLogger INSTANCE = new InAppPurchaseAutoLogger();

    private InAppPurchaseAutoLogger() {
    }

    @JvmStatic
    public static final void startIapLogging(Context context) {
        InAppPurchaseBillingClientWrapper orCreateInstance;
        Intrinsics.checkNotNullParameter(context, "context");
        InAppPurchaseUtils inAppPurchaseUtils = InAppPurchaseUtils.INSTANCE;
        InAppPurchaseAutoLogger inAppPurchaseAutoLogger = INSTANCE;
        if (InAppPurchaseUtils.getClass(BILLING_CLIENT_PURCHASE_NAME) == null || (orCreateInstance = InAppPurchaseBillingClientWrapper.Companion.getOrCreateInstance(context)) == null || !InAppPurchaseBillingClientWrapper.Companion.isServiceConnected().get()) {
            return;
        }
        InAppPurchaseLoggerManager inAppPurchaseLoggerManager = InAppPurchaseLoggerManager.INSTANCE;
        if (InAppPurchaseLoggerManager.eligibleQueryPurchaseHistory()) {
            orCreateInstance.queryPurchaseHistory("inapp", $$Lambda$InAppPurchaseAutoLogger$687IX7AaP5K7d5ER9XfsPCHYVU.INSTANCE);
        } else {
            orCreateInstance.queryPurchase("inapp", $$Lambda$InAppPurchaseAutoLogger$Wwi5DeOus3p2VMp5XwmTDyqKUU8.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: startIapLogging$lambda-0  reason: not valid java name */
    public static final void m81startIapLogging$lambda0() {
        INSTANCE.logPurchase();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: startIapLogging$lambda-1  reason: not valid java name */
    public static final void m82startIapLogging$lambda1() {
        INSTANCE.logPurchase();
    }

    private final void logPurchase() {
        InAppPurchaseLoggerManager inAppPurchaseLoggerManager = InAppPurchaseLoggerManager.INSTANCE;
        InAppPurchaseLoggerManager.filterPurchaseLogging(InAppPurchaseBillingClientWrapper.Companion.getPurchaseDetailsMap(), InAppPurchaseBillingClientWrapper.Companion.getSkuDetailsMap());
        InAppPurchaseBillingClientWrapper.Companion.getPurchaseDetailsMap().clear();
    }
}
