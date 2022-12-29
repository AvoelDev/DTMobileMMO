package com.google.android.gms.internal.drive;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/* loaded from: classes3.dex */
public enum zznr {
    INT(0),
    LONG(0L),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf((double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)),
    BOOLEAN(false),
    STRING(""),
    BYTE_STRING(zzjc.zznq),
    ENUM(null),
    MESSAGE(null);
    
    private final Object zztd;

    zznr(Object obj) {
        this.zztd = obj;
    }
}
