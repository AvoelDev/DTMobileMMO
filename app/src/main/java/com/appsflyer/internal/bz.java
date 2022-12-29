package com.appsflyer.internal;

/* loaded from: classes3.dex */
public final class bz {
    public static char[] valueOf(long j, char[] cArr) {
        char[] cArr2 = new char[cArr.length];
        int i = 0;
        int i2 = 4;
        for (int i3 = 0; i3 < cArr.length; i3++) {
            if ((((j >>> i3) & 1) != 1 || i >= 4) && i2 < cArr2.length) {
                cArr2[i2] = cArr[i3];
                i2++;
            } else {
                cArr2[i] = cArr[i3];
                i++;
            }
        }
        return cArr2;
    }
}
