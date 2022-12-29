package com.android.billingclient.api;

import java.util.List;

/* compiled from: com.android.billingclient:billing@@5.0.0 */
/* loaded from: classes3.dex */
public interface PurchasesUpdatedListener {
    void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list);
}
