package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.util.GmsVersion;
import com.google.android.gms.drive.UserMetadata;
import java.util.Arrays;
import java.util.Collections;

/* loaded from: classes3.dex */
public final class zzu extends zzm<UserMetadata> {
    public zzu(String str, int i) {
        super(str, Arrays.asList(zza(str, "permissionId"), zza(str, "displayName"), zza(str, "picture"), zza(str, "isAuthenticatedUser"), zza(str, "emailAddress")), Collections.emptyList(), GmsVersion.VERSION_MANCHEGO);
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final boolean zzb(DataHolder dataHolder, int i, int i2) {
        return dataHolder.hasColumn(zzh("permissionId")) && !dataHolder.hasNull(zzh("permissionId"), i, i2);
    }

    private final String zzh(String str) {
        return zza(getName(), str);
    }

    private static String zza(String str, String str2) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(".");
        sb.append(str2);
        return sb.toString();
    }

    @Override // com.google.android.gms.drive.metadata.zza
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
        String string = dataHolder.getString(zzh("permissionId"), i, i2);
        if (string != null) {
            String string2 = dataHolder.getString(zzh("displayName"), i, i2);
            String string3 = dataHolder.getString(zzh("picture"), i, i2);
            Boolean valueOf = Boolean.valueOf(dataHolder.getBoolean(zzh("isAuthenticatedUser"), i, i2));
            return new UserMetadata(string, string2, string3, valueOf.booleanValue(), dataHolder.getString(zzh("emailAddress"), i, i2));
        }
        return null;
    }
}
