package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import androidx.collection.LruCache;
import com.facebook.AccessToken;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzow;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzfp extends zzkn implements zzaf {
    final Map zza;
    final Map zzb;
    final Map zzc;
    final LruCache zzd;
    final com.google.android.gms.internal.measurement.zzr zze;
    private final Map zzg;
    private final Map zzh;
    private final Map zzi;
    private final Map zzj;
    private final Map zzk;
    private final Map zzl;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfp(zzkz zzkzVar) {
        super(zzkzVar);
        this.zzg = new ArrayMap();
        this.zza = new ArrayMap();
        this.zzb = new ArrayMap();
        this.zzc = new ArrayMap();
        this.zzh = new ArrayMap();
        this.zzj = new ArrayMap();
        this.zzk = new ArrayMap();
        this.zzl = new ArrayMap();
        this.zzi = new ArrayMap();
        this.zzd = new zzfm(this, 20);
        this.zze = new zzfn(this);
    }

    private final com.google.android.gms.internal.measurement.zzfe zzA(String str, byte[] bArr) {
        if (bArr == null) {
            return com.google.android.gms.internal.measurement.zzfe.zzg();
        }
        try {
            com.google.android.gms.internal.measurement.zzfe zzfeVar = (com.google.android.gms.internal.measurement.zzfe) ((com.google.android.gms.internal.measurement.zzfd) zzlb.zzl(com.google.android.gms.internal.measurement.zzfe.zze(), bArr)).zzaE();
            this.zzs.zzay().zzj().zzc("Parsed config. version, gmp_app_id", zzfeVar.zzs() ? Long.valueOf(zzfeVar.zzc()) : null, zzfeVar.zzr() ? zzfeVar.zzh() : null);
            return zzfeVar;
        } catch (com.google.android.gms.internal.measurement.zzko e) {
            this.zzs.zzay().zzk().zzc("Unable to merge remote config. appId", zzeo.zzn(str), e);
            return com.google.android.gms.internal.measurement.zzfe.zzg();
        } catch (RuntimeException e2) {
            this.zzs.zzay().zzk().zzc("Unable to merge remote config. appId", zzeo.zzn(str), e2);
            return com.google.android.gms.internal.measurement.zzfe.zzg();
        }
    }

    private final void zzB(String str, com.google.android.gms.internal.measurement.zzfd zzfdVar) {
        HashSet hashSet = new HashSet();
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        if (zzfdVar != null) {
            zzow.zzc();
            if (this.zzs.zzf().zzs(null, zzeb.zzal)) {
                for (com.google.android.gms.internal.measurement.zzfa zzfaVar : zzfdVar.zzg()) {
                    hashSet.add(zzfaVar.zzb());
                }
            }
            for (int i = 0; i < zzfdVar.zza(); i++) {
                com.google.android.gms.internal.measurement.zzfb zzfbVar = (com.google.android.gms.internal.measurement.zzfb) zzfdVar.zzb(i).zzby();
                if (zzfbVar.zzc().isEmpty()) {
                    this.zzs.zzay().zzk().zza("EventConfig contained null event name");
                } else {
                    String zzc = zzfbVar.zzc();
                    String zzb = zzgv.zzb(zzfbVar.zzc());
                    if (!TextUtils.isEmpty(zzb)) {
                        zzfbVar.zzb(zzb);
                        zzfdVar.zzd(i, zzfbVar);
                    }
                    if (zzfbVar.zzf() && zzfbVar.zzd()) {
                        arrayMap.put(zzc, true);
                    }
                    if (zzfbVar.zzg() && zzfbVar.zze()) {
                        arrayMap2.put(zzfbVar.zzc(), true);
                    }
                    if (zzfbVar.zzh()) {
                        if (zzfbVar.zza() < 2 || zzfbVar.zza() > 65535) {
                            this.zzs.zzay().zzk().zzc("Invalid sampling rate. Event name, sample rate", zzfbVar.zzc(), Integer.valueOf(zzfbVar.zza()));
                        } else {
                            arrayMap3.put(zzfbVar.zzc(), Integer.valueOf(zzfbVar.zza()));
                        }
                    }
                }
            }
        }
        this.zza.put(str, hashSet);
        this.zzb.put(str, arrayMap);
        this.zzc.put(str, arrayMap2);
        this.zzi.put(str, arrayMap3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00af, code lost:
        if (r2 == null) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0131  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzC(java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfp.zzC(java.lang.String):void");
    }

    private final void zzD(final String str, com.google.android.gms.internal.measurement.zzfe zzfeVar) {
        if (zzfeVar.zza() != 0) {
            this.zzs.zzay().zzj().zzb("EES programs found", Integer.valueOf(zzfeVar.zza()));
            com.google.android.gms.internal.measurement.zzgs zzgsVar = (com.google.android.gms.internal.measurement.zzgs) zzfeVar.zzm().get(0);
            try {
                com.google.android.gms.internal.measurement.zzc zzcVar = new com.google.android.gms.internal.measurement.zzc();
                zzcVar.zzd("internal.remoteConfig", new Callable() { // from class: com.google.android.gms.measurement.internal.zzfj
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return new com.google.android.gms.internal.measurement.zzn("internal.remoteConfig", new zzfo(zzfp.this, str));
                    }
                });
                zzcVar.zzd("internal.appMetadata", new Callable() { // from class: com.google.android.gms.measurement.internal.zzfk
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        final zzfp zzfpVar = zzfp.this;
                        final String str2 = str;
                        return new com.google.android.gms.internal.measurement.zzu("internal.appMetadata", new Callable() { // from class: com.google.android.gms.measurement.internal.zzfi
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                zzfp zzfpVar2 = zzfp.this;
                                String str3 = str2;
                                zzh zzj = zzfpVar2.zzf.zzi().zzj(str3);
                                HashMap hashMap = new HashMap();
                                hashMap.put("platform", "android");
                                hashMap.put("package_name", str3);
                                zzfpVar2.zzs.zzf().zzh();
                                hashMap.put("gmp_version", 73000L);
                                if (zzj != null) {
                                    String zzw = zzj.zzw();
                                    if (zzw != null) {
                                        hashMap.put("app_version", zzw);
                                    }
                                    hashMap.put("app_version_int", Long.valueOf(zzj.zzb()));
                                    hashMap.put("dynamite_version", Long.valueOf(zzj.zzk()));
                                }
                                return hashMap;
                            }
                        });
                    }
                });
                zzcVar.zzd("internal.logger", new Callable() { // from class: com.google.android.gms.measurement.internal.zzfl
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return new com.google.android.gms.internal.measurement.zzt(zzfp.this.zze);
                    }
                });
                zzcVar.zzc(zzgsVar);
                this.zzd.put(str, zzcVar);
                this.zzs.zzay().zzj().zzc("EES program loaded for appId, activities", str, Integer.valueOf(zzgsVar.zza().zza()));
                for (com.google.android.gms.internal.measurement.zzgq zzgqVar : zzgsVar.zza().zzd()) {
                    this.zzs.zzay().zzj().zzb("EES program activity", zzgqVar.zzb());
                }
                return;
            } catch (com.google.android.gms.internal.measurement.zzd unused) {
                this.zzs.zzay().zzd().zzb("Failed to load EES program. appId", str);
                return;
            }
        }
        this.zzd.remove(str);
    }

    private static final Map zzE(com.google.android.gms.internal.measurement.zzfe zzfeVar) {
        ArrayMap arrayMap = new ArrayMap();
        if (zzfeVar != null) {
            for (com.google.android.gms.internal.measurement.zzfi zzfiVar : zzfeVar.zzn()) {
                arrayMap.put(zzfiVar.zzb(), zzfiVar.zzc());
            }
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ com.google.android.gms.internal.measurement.zzc zzd(zzfp zzfpVar, String str) {
        zzfpVar.zzW();
        Preconditions.checkNotEmpty(str);
        if (zzfpVar.zzo(str)) {
            if (!zzfpVar.zzh.containsKey(str) || zzfpVar.zzh.get(str) == null) {
                zzfpVar.zzC(str);
            } else {
                zzfpVar.zzD(str, (com.google.android.gms.internal.measurement.zzfe) zzfpVar.zzh.get(str));
            }
            return (com.google.android.gms.internal.measurement.zzc) zzfpVar.zzd.snapshot().get(str);
        }
        return null;
    }

    @Override // com.google.android.gms.measurement.internal.zzaf
    public final String zza(String str, String str2) {
        zzg();
        zzC(str);
        Map map = (Map) this.zzg.get(str);
        if (map != null) {
            return (String) map.get(str2);
        }
        return null;
    }

    @Override // com.google.android.gms.measurement.internal.zzkn
    protected final boolean zzb() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int zzc(String str, String str2) {
        Integer num;
        zzg();
        zzC(str);
        Map map = (Map) this.zzi.get(str);
        if (map == null || (num = (Integer) map.get(str2)) == null) {
            return 1;
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final com.google.android.gms.internal.measurement.zzfe zze(String str) {
        zzW();
        zzg();
        Preconditions.checkNotEmpty(str);
        zzC(str);
        return (com.google.android.gms.internal.measurement.zzfe) this.zzh.get(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String zzf(String str) {
        zzg();
        return (String) this.zzl.get(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String zzh(String str) {
        zzg();
        return (String) this.zzk.get(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzi(String str) {
        zzg();
        zzC(str);
        return (String) this.zzj.get(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Set zzk(String str) {
        zzg();
        zzC(str);
        return (Set) this.zza.get(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzl(String str) {
        zzg();
        this.zzk.put(str, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzm(String str) {
        zzg();
        this.zzh.remove(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzn(String str) {
        zzg();
        com.google.android.gms.internal.measurement.zzfe zze = zze(str);
        if (zze == null) {
            return false;
        }
        return zze.zzq();
    }

    public final boolean zzo(String str) {
        com.google.android.gms.internal.measurement.zzfe zzfeVar;
        return (TextUtils.isEmpty(str) || (zzfeVar = (com.google.android.gms.internal.measurement.zzfe) this.zzh.get(str)) == null || zzfeVar.zza() == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzp(String str) {
        return "1".equals(zza(str, "measurement.upload.blacklist_internal"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzq(String str, String str2) {
        Boolean bool;
        zzg();
        zzC(str);
        if ("ecommerce_purchase".equals(str2) || FirebaseAnalytics.Event.PURCHASE.equals(str2) || FirebaseAnalytics.Event.REFUND.equals(str2)) {
            return true;
        }
        Map map = (Map) this.zzc.get(str);
        if (map == null || (bool = (Boolean) map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzr(String str, String str2) {
        Boolean bool;
        zzg();
        zzC(str);
        if (zzp(str) && zzlh.zzah(str2)) {
            return true;
        }
        if (zzs(str) && zzlh.zzai(str2)) {
            return true;
        }
        Map map = (Map) this.zzb.get(str);
        if (map == null || (bool = (Boolean) map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzs(String str) {
        return "1".equals(zza(str, "measurement.upload.blacklist_public"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean zzt(String str, byte[] bArr, String str2, String str3) {
        zzW();
        zzg();
        Preconditions.checkNotEmpty(str);
        com.google.android.gms.internal.measurement.zzfd zzfdVar = (com.google.android.gms.internal.measurement.zzfd) zzA(str, bArr).zzby();
        if (zzfdVar == null) {
            return false;
        }
        zzB(str, zzfdVar);
        zzD(str, (com.google.android.gms.internal.measurement.zzfe) zzfdVar.zzaE());
        this.zzh.put(str, (com.google.android.gms.internal.measurement.zzfe) zzfdVar.zzaE());
        this.zzj.put(str, zzfdVar.zze());
        this.zzk.put(str, str2);
        this.zzl.put(str, str3);
        this.zzg.put(str, zzE((com.google.android.gms.internal.measurement.zzfe) zzfdVar.zzaE()));
        this.zzf.zzi().zzB(str, new ArrayList(zzfdVar.zzf()));
        try {
            zzfdVar.zzc();
            bArr = ((com.google.android.gms.internal.measurement.zzfe) zzfdVar.zzaE()).zzbv();
        } catch (RuntimeException e) {
            this.zzs.zzay().zzk().zzc("Unable to serialize reduced-size config. Storing full config instead. appId", zzeo.zzn(str), e);
        }
        zzam zzi = this.zzf.zzi();
        Preconditions.checkNotEmpty(str);
        zzi.zzg();
        zzi.zzW();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr);
        contentValues.put("config_last_modified_time", str2);
        if (zzi.zzs.zzf().zzs(null, zzeb.zzaw)) {
            contentValues.put("e_tag", str3);
        }
        try {
            if (zzi.zzh().update("apps", contentValues, "app_id = ?", new String[]{str}) == 0) {
                zzi.zzs.zzay().zzd().zzb("Failed to update remote config (got 0). appId", zzeo.zzn(str));
            }
        } catch (SQLiteException e2) {
            zzi.zzs.zzay().zzd().zzc("Error storing remote config. appId", zzeo.zzn(str), e2);
        }
        this.zzh.put(str, (com.google.android.gms.internal.measurement.zzfe) zzfdVar.zzaE());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzu(String str) {
        zzg();
        zzC(str);
        return this.zza.get(str) != null && ((Set) this.zza.get(str)).contains("app_instance_id");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzv(String str) {
        zzg();
        zzC(str);
        if (this.zza.get(str) != null) {
            return ((Set) this.zza.get(str)).contains("device_model") || ((Set) this.zza.get(str)).contains(DeviceRequestsHelper.DEVICE_INFO_PARAM);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzw(String str) {
        zzg();
        zzC(str);
        return this.zza.get(str) != null && ((Set) this.zza.get(str)).contains("enhanced_user_id");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzx(String str) {
        zzg();
        zzC(str);
        return this.zza.get(str) != null && ((Set) this.zza.get(str)).contains("google_signals");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzy(String str) {
        zzg();
        zzC(str);
        if (this.zza.get(str) != null) {
            return ((Set) this.zza.get(str)).contains("os_version") || ((Set) this.zza.get(str)).contains(DeviceRequestsHelper.DEVICE_INFO_PARAM);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzz(String str) {
        zzg();
        zzC(str);
        return this.zza.get(str) != null && ((Set) this.zza.get(str)).contains(AccessToken.USER_ID_KEY);
    }
}
