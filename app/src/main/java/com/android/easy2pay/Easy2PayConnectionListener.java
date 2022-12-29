package com.android.easy2pay;

/* loaded from: classes3.dex */
interface Easy2PayConnectionListener {
    void onError(String str, String[] strArr, int i, String str2);

    void onReceive(String str, String[] strArr, byte[] bArr);
}
