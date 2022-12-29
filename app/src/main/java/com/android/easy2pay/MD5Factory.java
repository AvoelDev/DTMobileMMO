package com.android.easy2pay;

import java.security.MessageDigest;

/* loaded from: classes3.dex */
abstract class MD5Factory {
    MD5Factory() {
    }

    private static String convertToHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (bArr[i] >>> 4) & 15;
            int i3 = 0;
            while (true) {
                if (i2 >= 0 && i2 <= 9) {
                    stringBuffer.append((char) (i2 + 48));
                } else {
                    stringBuffer.append((char) ((i2 - 10) + 97));
                }
                i2 = bArr[i] & 15;
                int i4 = i3 + 1;
                if (i3 >= 1) {
                    break;
                }
                i3 = i4;
            }
        }
        return stringBuffer.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String generate(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return convertToHex(messageDigest.digest());
        } catch (Exception unused) {
            return null;
        }
    }
}
