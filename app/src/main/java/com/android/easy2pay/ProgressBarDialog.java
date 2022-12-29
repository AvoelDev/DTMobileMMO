package com.android.easy2pay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class ProgressBarDialog extends Dialog {
    private static final float DIMENSIONS_DIFF_LANDSCAPE = 140.0f;
    private static final float DIMENSIONS_DIFF_PORTRAIT = 80.0f;
    private Context context;
    private Easy2Pay e2p;
    private int langId;
    private final ProgressBar pg_wait;
    private int progressMax;
    private String ptxId;
    private String txId;
    private String userId;

    /* JADX INFO: Access modifiers changed from: protected */
    public ProgressBarDialog(Context context, int i, int i2) {
        super(context);
        this.ptxId = "";
        this.userId = "";
        this.txId = "";
        this.context = context;
        this.progressMax = i;
        this.pg_wait = new ProgressBar(context, null, 16842872);
        this.pg_wait.setMax(i);
        this.langId = i2;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCanceledOnTouchOutside(false);
        requestWindowFeature(1);
        Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
        float f = getContext().getResources().getDisplayMetrics().density;
        float f2 = getContext().getResources().getConfiguration().orientation == 2 ? DIMENSIONS_DIFF_LANDSCAPE : DIMENSIONS_DIFF_PORTRAIT;
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        addContentView(linearLayout, new LinearLayout.LayoutParams(defaultDisplay.getWidth() - ((int) ((f2 * f) + 0.5f)), -2));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        int i = (int) (10.0f * f);
        layoutParams.setMargins(i, i, i, i);
        layoutParams.gravity = 16;
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOrientation(0);
        linearLayout.addView(linearLayout2, layoutParams);
        ProgressBar progressBar = new ProgressBar(getContext(), null, 16842873);
        progressBar.setPadding(i, 0, i, 0);
        linearLayout2.addView(progressBar);
        TextView textView = new TextView(getContext());
        textView.setText(Resource.STRING_PROGRESS_TITLE[this.langId]);
        textView.setTextColor(-3355444);
        int i2 = (int) (f * 5.0f);
        textView.setPadding(i2, 0, i2, 0);
        linearLayout2.addView(textView);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams2.setMargins(i, 0, i, i);
        linearLayout.addView(this.pg_wait, layoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setPtxId(String str) {
        this.ptxId = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getPtxId() {
        return this.ptxId;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setUserId(String str) {
        this.userId = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setTxId(String str) {
        this.txId = str;
    }

    protected String getTxId() {
        return this.txId;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getUserId() {
        return this.userId;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setWaitProgress(int i) {
        this.pg_wait.setProgress(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setEasy2Pay(Easy2Pay easy2Pay) {
        this.e2p = easy2Pay;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        if (isShowing()) {
            setWaitProgress(this.progressMax);
            dismiss();
            if (this.e2p != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
                builder.setCancelable(false);
                builder.setMessage(Resource.STRING_PG_DIALOG_BACK_PRESSED[this.langId]).setPositiveButton(Resource.TXT_BUTTON_OK[this.langId], (DialogInterface.OnClickListener) null).show();
                Easy2Pay easy2Pay = this.e2p;
                easy2Pay.checkCharging(easy2Pay.getPtxId(), this.e2p.getUserId(), this.e2p.getTxId());
            }
        }
    }
}
