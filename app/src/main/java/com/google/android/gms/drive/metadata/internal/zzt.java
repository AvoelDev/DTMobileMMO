package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;

/* loaded from: classes3.dex */
public class zzt extends com.google.android.gms.drive.metadata.zza<String> {
    public zzt(String str, int i) {
        super(str, i);
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ String zzc(DataHolder dataHolder, int i, int i2) {
        return dataHolder.getString(getName(), i, i2);
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ void zza(Bundle bundle, String str) {
        bundle.putString(getName(), str);
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ String zzb(Bundle bundle) {
        return bundle.getString(getName());
    }
}
