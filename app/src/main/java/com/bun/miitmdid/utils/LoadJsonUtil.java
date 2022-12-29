package com.bun.miitmdid.utils;

import android.content.Context;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.netease.nis.sdkwrapper.Utils;

/* loaded from: classes.dex */
public class LoadJsonUtil {
    public static String LoadJsonFromAsset(Context context, String str) {
        Object[] objArr = new Object[5];
        objArr[1] = context;
        objArr[2] = str;
        objArr[3] = Integer.valueOf((int) TsExtractor.TS_STREAM_TYPE_SPLICE_INFO);
        objArr[4] = 1606976968606L;
        return (String) Utils.rL(objArr);
    }
}
