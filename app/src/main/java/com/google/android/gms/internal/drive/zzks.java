package com.google.android.gms.internal.drive;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/* loaded from: classes3.dex */
public enum zzks {
    VOID(Void.class, Void.class, null),
    INT(Integer.TYPE, Integer.class, 0),
    LONG(Long.TYPE, Long.class, 0L),
    FLOAT(Float.TYPE, Float.class, Float.valueOf(0.0f)),
    DOUBLE(Double.TYPE, Double.class, Double.valueOf((double) FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)),
    BOOLEAN(Boolean.TYPE, Boolean.class, false),
    STRING(String.class, String.class, ""),
    BYTE_STRING(zzjc.class, zzjc.class, zzjc.zznq),
    ENUM(Integer.TYPE, Integer.class, null),
    MESSAGE(Object.class, Object.class, null);
    
    private final Class<?> zztb;
    private final Class<?> zztc;
    private final Object zztd;

    zzks(Class cls, Class cls2, Object obj) {
        this.zztb = cls;
        this.zztc = cls2;
        this.zztd = obj;
    }

    public final Class<?> zzdo() {
        return this.zztc;
    }
}
