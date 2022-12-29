package com.android.easy2pay;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/* loaded from: classes3.dex */
class LoadingDialog extends Dialog {
    int langId;

    @Override // android.app.Dialog
    public void onBackPressed() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public LoadingDialog(Context context, int i) {
        super(context);
        this.langId = i;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCanceledOnTouchOutside(false);
        requestWindowFeature(1);
        float f = getContext().getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 1;
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        addContentView(linearLayout, layoutParams);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 1;
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOrientation(1);
        linearLayout.addView(linearLayout2, layoutParams2);
        ProgressBar progressBar = new ProgressBar(getContext(), null, 16842871);
        int i = (int) (f * 10.0f);
        progressBar.setPadding(0, i, 0, i);
        linearLayout2.addView(progressBar);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.gravity = 1;
        LinearLayout linearLayout3 = new LinearLayout(getContext());
        linearLayout3.setOrientation(1);
        linearLayout.addView(linearLayout3, layoutParams3);
        TextView textView = new TextView(getContext());
        textView.setText(Resource.STRING_WAITING_TITLE[this.langId]);
        textView.setTextColor(-3355444);
        textView.setPadding(i, i, i, i);
        linearLayout3.addView(textView);
    }
}
