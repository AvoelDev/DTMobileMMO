package com.google.android.datatransport.cct.internal;

import android.util.SparseArray;

/* loaded from: classes3.dex */
public enum QosTier {
    DEFAULT(0),
    UNMETERED_ONLY(1),
    UNMETERED_OR_DAILY(2),
    FAST_IF_RADIO_AWAKE(3),
    NEVER(4),
    UNRECOGNIZED(-1);
    
    private static final SparseArray<QosTier> valueMap = new SparseArray<>();
    private final int value;

    static {
        valueMap.put(0, DEFAULT);
        valueMap.put(1, UNMETERED_ONLY);
        valueMap.put(2, UNMETERED_OR_DAILY);
        valueMap.put(3, FAST_IF_RADIO_AWAKE);
        valueMap.put(4, NEVER);
        valueMap.put(-1, UNRECOGNIZED);
    }

    QosTier(int i) {
        this.value = i;
    }

    public final int getNumber() {
        return this.value;
    }

    public static QosTier forNumber(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            return null;
                        }
                        return NEVER;
                    }
                    return FAST_IF_RADIO_AWAKE;
                }
                return UNMETERED_OR_DAILY;
            }
            return UNMETERED_ONLY;
        }
        return DEFAULT;
    }
}
