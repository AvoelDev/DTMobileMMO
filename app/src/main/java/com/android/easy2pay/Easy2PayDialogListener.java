package com.android.easy2pay;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public interface Easy2PayDialogListener {
    void onActivityPause();

    void onActivityResume();

    void onCancel(String str, String str2);

    void onOK(String str, String str2, String str3, String str4);
}
