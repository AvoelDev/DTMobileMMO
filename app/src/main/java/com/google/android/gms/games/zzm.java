package com.google.android.gms.games;

import com.google.android.gms.common.data.DataBufferRef;
import com.google.android.gms.common.data.DataHolder;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public class zzm extends DataBufferRef {
    public zzm(DataHolder dataHolder, int i) {
        super(dataHolder, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int zzp(String str, int i) {
        return (!hasColumn(str) || hasNull(str)) ? i : getInteger(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String zzq(String str, String str2) {
        if (!hasColumn(str) || hasNull(str)) {
            return null;
        }
        return getString(str);
    }
}
