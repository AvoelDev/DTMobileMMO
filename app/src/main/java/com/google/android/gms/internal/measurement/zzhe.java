package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzhe implements zzhj {
    private final ContentResolver zzc;
    private final Uri zzd;
    private final Runnable zze;
    private volatile Map zzh;
    private static final Map zzb = new ArrayMap();
    public static final String[] zza = {"key", "value"};
    private final ContentObserver zzf = new zzhd(this, null);
    private final Object zzg = new Object();
    private final List zzi = new ArrayList();

    private zzhe(ContentResolver contentResolver, Uri uri, Runnable runnable) {
        if (contentResolver == null) {
            throw null;
        }
        if (uri == null) {
            throw null;
        }
        this.zzc = contentResolver;
        this.zzd = uri;
        this.zze = runnable;
        contentResolver.registerContentObserver(uri, false, this.zzf);
    }

    public static zzhe zza(ContentResolver contentResolver, Uri uri, Runnable runnable) {
        zzhe zzheVar;
        synchronized (zzhe.class) {
            zzheVar = (zzhe) zzb.get(uri);
            if (zzheVar == null) {
                try {
                    zzhe zzheVar2 = new zzhe(contentResolver, uri, runnable);
                    try {
                        zzb.put(uri, zzheVar2);
                    } catch (SecurityException unused) {
                    }
                    zzheVar = zzheVar2;
                } catch (SecurityException unused2) {
                }
            }
        }
        return zzheVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized void zze() {
        synchronized (zzhe.class) {
            for (zzhe zzheVar : zzb.values()) {
                zzheVar.zzc.unregisterContentObserver(zzheVar.zzf);
            }
            zzb.clear();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Object zzb(String str) {
        return (String) zzc().get(str);
    }

    public final Map zzc() {
        Map map;
        Map map2 = this.zzh;
        if (map2 == null) {
            synchronized (this.zzg) {
                map2 = this.zzh;
                if (map2 == null) {
                    StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
                    try {
                        map = (Map) zzhh.zza(new zzhi() { // from class: com.google.android.gms.internal.measurement.zzhc
                            @Override // com.google.android.gms.internal.measurement.zzhi
                            public final Object zza() {
                                return zzhe.this.zzd();
                            }
                        });
                        StrictMode.setThreadPolicy(allowThreadDiskReads);
                    } catch (SQLiteException | IllegalStateException | SecurityException unused) {
                        Log.e("ConfigurationContentLdr", "PhenotypeFlag unable to load ContentProvider, using default values");
                        StrictMode.setThreadPolicy(allowThreadDiskReads);
                        map = null;
                    }
                    this.zzh = map;
                    map2 = map;
                }
            }
        }
        return map2 != null ? map2 : Collections.emptyMap();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final /* synthetic */ Map zzd() {
        Map hashMap;
        Cursor query = this.zzc.query(this.zzd, zza, null, null, null);
        if (query == null) {
            return Collections.emptyMap();
        }
        try {
            int count = query.getCount();
            if (count == 0) {
                return Collections.emptyMap();
            }
            if (count <= 256) {
                hashMap = new ArrayMap(count);
            } else {
                hashMap = new HashMap(count, 1.0f);
            }
            while (query.moveToNext()) {
                hashMap.put(query.getString(0), query.getString(1));
            }
            return hashMap;
        } finally {
            query.close();
        }
    }

    public final void zzf() {
        synchronized (this.zzg) {
            this.zzh = null;
            this.zze.run();
        }
        synchronized (this) {
            for (zzhf zzhfVar : this.zzi) {
                zzhfVar.zza();
            }
        }
    }
}
