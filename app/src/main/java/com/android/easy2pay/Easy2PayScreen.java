package com.android.easy2pay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.ssjj.fnsdk.core.update.FNUpdateManager;
import java.util.Vector;

/* loaded from: classes3.dex */
public class Easy2PayScreen extends Activity {
    private static final float BUTTION_OK_WEIGHT = 0.7f;
    private static final float DIMENSIONS_DIFF_PORTRAIT = 40.0f;
    private static Easy2PayDialogListener listener;
    private String description;
    private int langId;
    private Vector<String[]> priceList;
    private String ptxId;
    private String title;
    private String userId;

    /* JADX INFO: Access modifiers changed from: protected */
    public void setEasy2PayDialogListener(Easy2PayDialogListener easy2PayDialogListener) {
        listener = easy2PayDialogListener;
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        String sb;
        final Spinner spinner;
        super.onCreate(bundle);
        requestWindowFeature(1);
        Intent intent = getIntent();
        this.title = intent.getStringExtra("title");
        this.description = intent.getStringExtra(FNUpdateManager.PARAM_DESC);
        this.priceList = new Vector<>();
        String[] stringArrayExtra = intent.getStringArrayExtra("pids");
        String[] stringArrayExtra2 = intent.getStringArrayExtra("pdescs");
        String[] stringArrayExtra3 = intent.getStringArrayExtra("scodes");
        for (int i = 0; i < stringArrayExtra.length; i++) {
            this.priceList.addElement(new String[]{stringArrayExtra[i], stringArrayExtra2[i], stringArrayExtra3[i]});
        }
        this.ptxId = intent.getStringExtra("ptxid");
        this.userId = intent.getStringExtra("userid");
        this.langId = Integer.parseInt(intent.getStringExtra("langId"));
        float f = getResources().getDisplayMetrics().density;
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(0);
        linearLayout.setBackgroundColor(-1358954496);
        linearLayout.setGravity(16);
        setContentView(linearLayout, new LinearLayout.LayoutParams(-1, -1));
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(1);
        linearLayout2.setGravity(1);
        linearLayout.addView(linearLayout2, new LinearLayout.LayoutParams(-1, -2));
        LinearLayout linearLayout3 = new LinearLayout(this);
        linearLayout3.setPadding(3, 3, 3, 3);
        linearLayout3.setBackgroundColor(-1342177281);
        int i2 = ((int) (320.0f * f)) - ((int) ((DIMENSIONS_DIFF_PORTRAIT * f) + 0.5f));
        linearLayout2.addView(linearLayout3, new LinearLayout.LayoutParams(i2, -2));
        LinearLayout linearLayout4 = new LinearLayout(this);
        linearLayout4.setOrientation(1);
        linearLayout4.setBackgroundColor(-1358954496);
        linearLayout3.addView(linearLayout4, new LinearLayout.LayoutParams(i2, -2));
        TextView textView = new TextView(this);
        textView.setText(this.title);
        textView.setTextColor(-1);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setBackgroundColor(2144128204);
        int i3 = (int) (10.0f * f);
        int i4 = (int) (2.0f * f);
        textView.setPadding(i3, i4, i3, i4);
        linearLayout4.addView(textView);
        TextView textView2 = new TextView(this);
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
        int i5 = (int) (f * 5.0f);
        textView2.setPadding(i3, i5, i3, i5);
        linearLayout4.addView(textView2);
        if (this.priceList.size() > 1) {
            spinner = new Spinner(this);
            String[] strArr = new String[this.priceList.size()];
            for (int i6 = 0; i6 < this.priceList.size(); i6++) {
                strArr[i6] = this.priceList.elementAt(i6)[1];
            }
            spinner.setAdapter((SpinnerAdapter) new ArrayAdapter(this, 17367048, strArr));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(i3, 0, i3, i5);
            layoutParams.gravity = 1;
            linearLayout4.addView(spinner, layoutParams);
        } else {
            spinner = null;
        }
        LinearLayout linearLayout5 = new LinearLayout(this);
        linearLayout5.setOrientation(0);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams2.setMargins(i3, i5, i3, i5);
        linearLayout4.addView(linearLayout5, layoutParams2);
        Button button = new Button(this);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.weight = BUTTION_OK_WEIGHT;
        button.setLayoutParams(layoutParams3);
        button.setText(Resource.TXT_BUTTON_OK[this.langId]);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.easy2pay.Easy2PayScreen.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Easy2PayScreen.listener != null) {
                    Vector vector = Easy2PayScreen.this.priceList;
                    Spinner spinner2 = spinner;
                    String[] strArr2 = (String[]) vector.elementAt(spinner2 != null ? spinner2.getSelectedItemPosition() : 0);
                    Easy2PayScreen.listener.onOK(Easy2PayScreen.this.ptxId, Easy2PayScreen.this.userId, strArr2[0], strArr2[2]);
                }
                Easy2PayScreen.this.finish();
            }
        });
        linearLayout5.addView(button);
        Button button2 = new Button(this);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams4.weight = 0.3f;
        button2.setLayoutParams(layoutParams4);
        button2.setText(Resource.TXT_BUTTON_CANCEL[this.langId]);
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.easy2pay.Easy2PayScreen.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Easy2PayScreen.listener != null) {
                    Easy2PayScreen.listener.onCancel(Easy2PayScreen.this.ptxId, Easy2PayScreen.this.userId);
                }
                Easy2PayScreen.this.finish();
            }
        });
        linearLayout5.addView(button2);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        Easy2PayDialogListener easy2PayDialogListener = listener;
        if (easy2PayDialogListener != null) {
            easy2PayDialogListener.onCancel(this.ptxId, this.userId);
        }
        super.onBackPressed();
    }
}
