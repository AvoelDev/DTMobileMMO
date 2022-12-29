package com.android.vending.billing;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import com.facebook.internal.ServerProtocol;
import com.ssjj.fnsdk.core.listener.FNEvent;

/* loaded from: classes3.dex */
public class PullView extends FrameLayout {
    public PullView(Context context) {
        super(context);
        init();
    }

    public PullView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        Log.i(ServerProtocol.DIALOG_PARAM_SDK_VERSION, FNEvent.FN_EVENT_INIT);
    }
}
