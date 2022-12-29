package com.android.easy2pay;

/* loaded from: classes3.dex */
public interface Easy2PayListener {
    void onError(String str, String str2, String str3, int i, String str4);

    void onEvent(String str, String str2, String str3, int i, String str4);

    void onPurchaseResult(String str, String str2, String str3, String str4, int i, String str5);
}
