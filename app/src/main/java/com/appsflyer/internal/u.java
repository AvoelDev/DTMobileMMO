package com.appsflyer.internal;

import android.content.Context;

/* loaded from: classes3.dex */
final class u {

    /* loaded from: classes3.dex */
    static final class c {
        static final u AFInAppEventParameterName = new u();
    }

    u() {
    }

    private static boolean AFInAppEventParameterName(Context context, String[] strArr) {
        for (String str : strArr) {
            if (aa.AFInAppEventParameterName(context, str)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004e, code lost:
        if (com.google.android.exoplayer2.source.chunk.ChunkedTrackBlacklistUtil.DEFAULT_TRACK_BLACKLIST_MS < (r3.getTime() - r9.getTime())) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.location.Location valueOf(android.content.Context r9) {
        /*
            r8 = this;
            java.lang.String r0 = "android.permission.ACCESS_FINE_LOCATION"
            r1 = 0
            java.lang.String r2 = "location"
            java.lang.Object r2 = r9.getSystemService(r2)     // Catch: java.lang.Throwable -> L54
            android.location.LocationManager r2 = (android.location.LocationManager) r2     // Catch: java.lang.Throwable -> L54
            java.lang.String r3 = "network"
            java.lang.String r4 = "android.permission.ACCESS_COARSE_LOCATION"
            java.lang.String[] r4 = new java.lang.String[]{r0, r4}     // Catch: java.lang.Throwable -> L54
            boolean r4 = AFInAppEventParameterName(r9, r4)     // Catch: java.lang.Throwable -> L54
            if (r4 == 0) goto L1e
            android.location.Location r3 = r2.getLastKnownLocation(r3)     // Catch: java.lang.Throwable -> L54
            goto L1f
        L1e:
            r3 = r1
        L1f:
            java.lang.String r4 = "gps"
            java.lang.String[] r0 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L54
            boolean r9 = AFInAppEventParameterName(r9, r0)     // Catch: java.lang.Throwable -> L54
            if (r9 == 0) goto L30
            android.location.Location r9 = r2.getLastKnownLocation(r4)     // Catch: java.lang.Throwable -> L54
            goto L31
        L30:
            r9 = r1
        L31:
            if (r9 != 0) goto L37
            if (r3 != 0) goto L37
            r9 = r1
            goto L51
        L37:
            if (r9 != 0) goto L3c
            if (r3 == 0) goto L3c
            goto L50
        L3c:
            if (r3 != 0) goto L40
            if (r9 != 0) goto L51
        L40:
            long r4 = r3.getTime()     // Catch: java.lang.Throwable -> L54
            long r6 = r9.getTime()     // Catch: java.lang.Throwable -> L54
            long r4 = r4 - r6
            r6 = 60000(0xea60, double:2.9644E-319)
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 >= 0) goto L51
        L50:
            r9 = r3
        L51:
            if (r9 == 0) goto L54
            r1 = r9
        L54:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.internal.u.valueOf(android.content.Context):android.location.Location");
    }
}
