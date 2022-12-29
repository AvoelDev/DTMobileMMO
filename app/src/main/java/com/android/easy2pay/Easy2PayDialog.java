package com.android.easy2pay;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import java.util.Vector;

/* loaded from: classes3.dex */
class Easy2PayDialog extends Dialog {
    private static final float BUTTION_OK_WEIGHT = 0.7f;
    private static final float DIMENSIONS_DIFF_PORTRAIT = 40.0f;
    private final String description;
    private final int langId;
    private final Easy2PayDialogListener listener;
    private final Vector<String[]> priceList;
    private final String ptxId;
    private final String title;
    private final String userId;

    /* JADX INFO: Access modifiers changed from: protected */
    public Easy2PayDialog(Context context, String str, String str2, Vector<String[]> vector, String str3, String str4, Easy2PayDialogListener easy2PayDialogListener, int i) {
        super(context);
        this.listener = easy2PayDialogListener;
        this.title = str;
        this.description = str2;
        this.priceList = vector;
        this.ptxId = str3;
        this.userId = str4;
        this.langId = i;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        String sb;
        final Spinner spinner;
        super.onCreate(bundle);
        setCanceledOnTouchOutside(false);
        requestWindowFeature(1);
        float f = getContext().getResources().getDisplayMetrics().density;
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        addContentView(linearLayout, new LinearLayout.LayoutParams(((int) (320.0f * f)) - ((int) ((DIMENSIONS_DIFF_PORTRAIT * f) + 0.5f)), -2));
        TextView textView = new TextView(getContext());
        textView.setText(this.title);
        textView.setTextColor(-1);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setBackgroundColor(2144128204);
        int i = (int) (10.0f * f);
        int i2 = (int) (2.0f * f);
        textView.setPadding(i, i2, i, i2);
        linearLayout.addView(textView);
        TextView textView2 = new TextView(getContext());
        String str = this.description;
        if (str.contains("%s")) {
            sb = str.replace("%s", this.priceList.elementAt(0)[1]);
        } else {
            StringBuilder sb2 = new StringBuilder(String.valueOf(str));
            sb2.append(this.priceList.size() == 1 ? " \"" + this.priceList.elementAt(0)[1] + "\" ?" : " ?");
            sb = sb2.toString();
        }
        textView2.setText(sb);
        textView2.setTextColor(-3355444);
        int i3 = (int) (f * 5.0f);
        textView2.setPadding(i, i3, i, i3);
        linearLayout.addView(textView2);
        if (this.priceList.size() > 1) {
            spinner = new Spinner(getContext());
            String[] strArr = new String[this.priceList.size()];
            for (int i4 = 0; i4 < this.priceList.size(); i4++) {
                strArr[i4] = this.priceList.elementAt(i4)[1];
            }
            spinner.setAdapter((SpinnerAdapter) new ArrayAdapter(getContext(), 17367048, strArr));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(i, 0, i, i3);
            layoutParams.gravity = 1;
            linearLayout.addView(spinner, layoutParams);
        } else {
            spinner = null;
        }
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setOrientation(0);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams2.setMargins(i, i3, i, i3);
        linearLayout.addView(linearLayout2, layoutParams2);
        Button button = new Button(getContext());
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.weight = BUTTION_OK_WEIGHT;
        button.setLayoutParams(layoutParams3);
        button.setText(Resource.TXT_BUTTON_OK[this.langId]);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.easy2pay.Easy2PayDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Easy2PayDialog.this.listener != null) {
                    Vector vector = Easy2PayDialog.this.priceList;
                    Spinner spinner2 = spinner;
                    String[] strArr2 = (String[]) vector.elementAt(spinner2 != null ? spinner2.getSelectedItemPosition() : 0);
                    Easy2PayDialog.this.listener.onOK(Easy2PayDialog.this.ptxId, Easy2PayDialog.this.userId, strArr2[0], strArr2[2]);
                }
                Easy2PayDialog.this.dismiss();
            }
        });
        linearLayout2.addView(button);
        Button button2 = new Button(getContext());
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams4.weight = 0.3f;
        button2.setLayoutParams(layoutParams4);
        button2.setText(Resource.TXT_BUTTON_CANCEL[this.langId]);
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.easy2pay.Easy2PayDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Easy2PayDialog.this.listener != null) {
                    Easy2PayDialog.this.listener.onCancel(Easy2PayDialog.this.ptxId, Easy2PayDialog.this.userId);
                }
                Easy2PayDialog.this.dismiss();
            }
        });
        linearLayout2.addView(button2);
    }
}
