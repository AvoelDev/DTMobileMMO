package com.google.android.gms.games;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class GamesCallbackStatusCodes {
    @Deprecated
    public static final int CLIENT_RECONNECT_REQUIRED = 2;
    public static final int INTERNAL_ERROR = 1;
    public static final int OK = 0;

    private GamesCallbackStatusCodes() {
    }

    public static String getStatusCodeString(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 6003) {
                        if (i != 7004) {
                            if (i != 7000) {
                                if (i != 7001) {
                                    return "unknown games callback status code: " + i;
                                }
                                return "REAL_TIME_MESSAGE_SEND_FAILED";
                            }
                            return "REAL_TIME_CONNECTION_FAILED";
                        }
                        return "REAL_TIME_ROOM_NOT_JOINED";
                    }
                    return "MULTIPLAYER_DISABLED";
                }
                return "CLIENT_RECONNECT_REQUIRED";
            }
            return "INTERNAL_ERROR";
        }
        return "OK";
    }
}
