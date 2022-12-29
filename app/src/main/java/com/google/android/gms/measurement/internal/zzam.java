package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzns;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.ssjj.fnsdk.core.cg.FnSecPkgConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzam extends zzkn {
    private static final String[] zza = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_bundled_day", "ALTER TABLE events ADD COLUMN last_bundled_day INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;", "current_session_count", "ALTER TABLE events ADD COLUMN current_session_count INTEGER;"};
    private static final String[] zzb = {"origin", "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzc = {"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;", "admob_app_id", "ALTER TABLE apps ADD COLUMN admob_app_id TEXT;", "linked_admob_app_id", "ALTER TABLE apps ADD COLUMN linked_admob_app_id TEXT;", "dynamite_version", "ALTER TABLE apps ADD COLUMN dynamite_version INTEGER;", "safelisted_events", "ALTER TABLE apps ADD COLUMN safelisted_events TEXT;", "ga_app_id", "ALTER TABLE apps ADD COLUMN ga_app_id TEXT;", "config_last_modified_time", "ALTER TABLE apps ADD COLUMN config_last_modified_time TEXT;", "e_tag", "ALTER TABLE apps ADD COLUMN e_tag TEXT;", "session_stitching_token", "ALTER TABLE apps ADD COLUMN session_stitching_token TEXT;"};
    private static final String[] zzd = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zze = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    private static final String[] zzg = {"session_scoped", "ALTER TABLE event_filters ADD COLUMN session_scoped BOOLEAN;"};
    private static final String[] zzh = {"session_scoped", "ALTER TABLE property_filters ADD COLUMN session_scoped BOOLEAN;"};
    private static final String[] zzi = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzal zzj;
    private final zzkj zzk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzam(zzkz zzkzVar) {
        super(zzkzVar);
        this.zzk = new zzkj(this.zzs.zzav());
        this.zzs.zzf();
        this.zzj = new zzal(this, this.zzs.zzau(), "google_app_measurement.db");
    }

    static final void zzV(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty("value");
        Preconditions.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put("value", (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put("value", (Long) obj);
        } else if (obj instanceof Double) {
            contentValues.put("value", (Double) obj);
        } else {
            throw new IllegalArgumentException("Invalid value type");
        }
    }

    private final long zzZ(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            try {
                Cursor rawQuery = zzh().rawQuery(str, strArr);
                if (rawQuery.moveToFirst()) {
                    long j = rawQuery.getLong(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return j;
                }
                throw new SQLiteException("Database returned empty set");
            } catch (SQLiteException e) {
                this.zzs.zzay().zzd().zzc("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    private final long zzaa(String str, String[] strArr, long j) {
        Cursor cursor = null;
        try {
            try {
                cursor = zzh().rawQuery(str, strArr);
                if (cursor.moveToFirst()) {
                    return cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            } catch (SQLiteException e) {
                this.zzs.zzay().zzd().zzc("Database error", str, e);
                throw e;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public final void zzA(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzg();
        zzW();
        try {
            zzh().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            this.zzs.zzay().zzd().zzd("Error deleting user property. appId", zzeo.zzn(str), this.zzs.zzj().zzf(str2), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:100:0x0340, code lost:
        r11.put("session_scoped", r0);
        r11.put("data", r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0354, code lost:
        if (zzh().insertWithOnConflict("property_filters", null, r11, 5) != (-1)) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0356, code lost:
        r23.zzs.zzay().zzd().zzb("Failed to insert property filter (got -1). appId", com.google.android.gms.measurement.internal.zzeo.zzn(r24));
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x036a, code lost:
        r0 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x036e, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x036f, code lost:
        r23.zzs.zzay().zzd().zzc("Error storing property filter. appId", com.google.android.gms.measurement.internal.zzeo.zzn(r24), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0382, code lost:
        zzW();
        zzg();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24);
        r0 = zzh();
        r3 = r18;
        r0.delete("property_filters", r3, new java.lang.String[]{r24, java.lang.String.valueOf(r10)});
        r0.delete("event_filters", r3, new java.lang.String[]{r24, java.lang.String.valueOf(r10)});
        r18 = r3;
        r7 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x03b9, code lost:
        r7 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0184, code lost:
        r11 = r0.zzh().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0190, code lost:
        if (r11.hasNext() == false) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x019c, code lost:
        if (((com.google.android.gms.internal.measurement.zzes) r11.next()).zzj() != false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x019e, code lost:
        r23.zzs.zzay().zzk().zzc("Property filter with no ID. Audience definition ignored. appId, audienceId", com.google.android.gms.measurement.internal.zzeo.zzn(r24), java.lang.Integer.valueOf(r10));
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01b7, code lost:
        r11 = r0.zzg().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01cd, code lost:
        if (r11.hasNext() == false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01cf, code lost:
        r12 = (com.google.android.gms.internal.measurement.zzej) r11.next();
        zzW();
        zzg();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x01e9, code lost:
        if (r12.zzg().isEmpty() == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01eb, code lost:
        r0 = r23.zzs.zzay().zzk();
        r9 = com.google.android.gms.measurement.internal.zzeo.zzn(r24);
        r11 = java.lang.Integer.valueOf(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0203, code lost:
        if (r12.zzp() == false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0205, code lost:
        r17 = java.lang.Integer.valueOf(r12.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0210, code lost:
        r17 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0212, code lost:
        r0.zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", r9, r11, java.lang.String.valueOf(r17));
        r21 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x021d, code lost:
        r3 = r12.zzbv();
        r21 = r7;
        r7 = new android.content.ContentValues();
        r7.put("app_id", r24);
        r7.put("audience_id", java.lang.Integer.valueOf(r10));
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0236, code lost:
        if (r12.zzp() == false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0238, code lost:
        r9 = java.lang.Integer.valueOf(r12.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0241, code lost:
        r9 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0242, code lost:
        r7.put("filter_id", r9);
        r7.put("event_name", r12.zzg());
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0252, code lost:
        if (r12.zzq() == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0254, code lost:
        r9 = java.lang.Boolean.valueOf(r12.zzn());
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x025d, code lost:
        r9 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x025e, code lost:
        r7.put("session_scoped", r9);
        r7.put("data", r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0272, code lost:
        if (zzh().insertWithOnConflict("event_filters", null, r7, 5) != (-1)) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0274, code lost:
        r23.zzs.zzay().zzd().zzb("Failed to insert event filter (got -1). appId", com.google.android.gms.measurement.internal.zzeo.zzn(r24));
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0287, code lost:
        r7 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x028d, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x028e, code lost:
        r23.zzs.zzay().zzd().zzc("Error storing event filter. appId", com.google.android.gms.measurement.internal.zzeo.zzn(r24), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x02a3, code lost:
        r21 = r7;
        r0 = r0.zzh().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x02b1, code lost:
        if (r0.hasNext() == false) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x02b3, code lost:
        r3 = (com.google.android.gms.internal.measurement.zzes) r0.next();
        zzW();
        zzg();
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r24);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x02cd, code lost:
        if (r3.zze().isEmpty() == false) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x02cf, code lost:
        r0 = r23.zzs.zzay().zzk();
        r8 = com.google.android.gms.measurement.internal.zzeo.zzn(r24);
        r9 = java.lang.Integer.valueOf(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x02e7, code lost:
        if (r3.zzj() == false) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x02e9, code lost:
        r17 = java.lang.Integer.valueOf(r3.zza());
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x02f4, code lost:
        r17 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x02f6, code lost:
        r0.zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", r8, r9, java.lang.String.valueOf(r17));
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x02ff, code lost:
        r7 = r3.zzbv();
        r11 = new android.content.ContentValues();
        r11.put("app_id", r24);
        r11.put("audience_id", java.lang.Integer.valueOf(r10));
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0316, code lost:
        if (r3.zzj() == false) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0318, code lost:
        r12 = java.lang.Integer.valueOf(r3.zza());
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0321, code lost:
        r12 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0322, code lost:
        r11.put("filter_id", r12);
        r22 = r0;
        r11.put("property_name", r3.zze());
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0334, code lost:
        if (r3.zzk() == false) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0336, code lost:
        r0 = java.lang.Boolean.valueOf(r3.zzi());
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x033f, code lost:
        r0 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzB(java.lang.String r24, java.util.List r25) {
        /*
            Method dump skipped, instructions count: 1193
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzB(java.lang.String, java.util.List):void");
    }

    public final void zzC() {
        zzW();
        zzh().setTransactionSuccessful();
    }

    public final void zzD(zzh zzhVar) {
        Preconditions.checkNotNull(zzhVar);
        zzg();
        zzW();
        String zzt = zzhVar.zzt();
        Preconditions.checkNotNull(zzt);
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzt);
        contentValues.put("app_instance_id", zzhVar.zzu());
        contentValues.put("gmp_app_id", zzhVar.zzy());
        contentValues.put("resettable_device_id_hash", zzhVar.zzA());
        contentValues.put("last_bundle_index", Long.valueOf(zzhVar.zzo()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzhVar.zzp()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzhVar.zzn()));
        contentValues.put("app_version", zzhVar.zzw());
        contentValues.put("app_store", zzhVar.zzv());
        contentValues.put("gmp_version", Long.valueOf(zzhVar.zzm()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzhVar.zzj()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzhVar.zzai()));
        contentValues.put("day", Long.valueOf(zzhVar.zzi()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzhVar.zzg()));
        contentValues.put("daily_events_count", Long.valueOf(zzhVar.zzf()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzhVar.zzd()));
        contentValues.put("config_fetched_time", Long.valueOf(zzhVar.zzc()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzhVar.zzl()));
        contentValues.put("app_version_int", Long.valueOf(zzhVar.zzb()));
        contentValues.put("firebase_instance_id", zzhVar.zzx());
        contentValues.put("daily_error_events_count", Long.valueOf(zzhVar.zze()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzhVar.zzh()));
        contentValues.put("health_monitor_sample", zzhVar.zzz());
        zzhVar.zza();
        contentValues.put("android_id", (Long) 0L);
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzhVar.zzah()));
        contentValues.put("admob_app_id", zzhVar.zzr());
        contentValues.put("dynamite_version", Long.valueOf(zzhVar.zzk()));
        contentValues.put("session_stitching_token", zzhVar.zzB());
        List zzC = zzhVar.zzC();
        if (zzC != null) {
            if (zzC.isEmpty()) {
                this.zzs.zzay().zzk().zzb("Safelisted events should not be an empty list. appId", zzt);
            } else {
                contentValues.put("safelisted_events", TextUtils.join(",", zzC));
            }
        }
        zzns.zzc();
        if (this.zzs.zzf().zzs(null, zzeb.zzai) && !contentValues.containsKey("safelisted_events")) {
            contentValues.put("safelisted_events", (String) null);
        }
        try {
            SQLiteDatabase zzh2 = zzh();
            if (zzh2.update("apps", contentValues, "app_id = ?", new String[]{zzt}) == 0 && zzh2.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                this.zzs.zzay().zzd().zzb("Failed to insert/update app (got -1). appId", zzeo.zzn(zzt));
            }
        } catch (SQLiteException e) {
            this.zzs.zzay().zzd().zzc("Error storing app. appId", zzeo.zzn(zzt), e);
        }
    }

    public final void zzE(zzas zzasVar) {
        Preconditions.checkNotNull(zzasVar);
        zzg();
        zzW();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzasVar.zza);
        contentValues.put("name", zzasVar.zzb);
        contentValues.put("lifetime_count", Long.valueOf(zzasVar.zzc));
        contentValues.put("current_bundle_count", Long.valueOf(zzasVar.zzd));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzasVar.zzf));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzasVar.zzg));
        contentValues.put("last_bundled_day", zzasVar.zzh);
        contentValues.put("last_sampled_complex_event_id", zzasVar.zzi);
        contentValues.put("last_sampling_rate", zzasVar.zzj);
        contentValues.put("current_session_count", Long.valueOf(zzasVar.zze));
        Boolean bool = zzasVar.zzk;
        contentValues.put("last_exempt_from_sampling", (bool == null || !bool.booleanValue()) ? null : 1L);
        try {
            if (zzh().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                this.zzs.zzay().zzd().zzb("Failed to insert/update event aggregates (got -1). appId", zzeo.zzn(zzasVar.zza));
            }
        } catch (SQLiteException e) {
            this.zzs.zzay().zzd().zzc("Error storing event aggregates. appId", zzeo.zzn(zzasVar.zza), e);
        }
    }

    public final boolean zzF() {
        return zzZ("select count(1) > 0 from raw_events", null) != 0;
    }

    public final boolean zzG() {
        return zzZ("select count(1) > 0 from queue where has_realtime = 1", null) != 0;
    }

    public final boolean zzH() {
        return zzZ("select count(1) > 0 from raw_events where realtime = 1", null) != 0;
    }

    protected final boolean zzI() {
        Context zzau = this.zzs.zzau();
        this.zzs.zzf();
        return zzau.getDatabasePath("google_app_measurement.db").exists();
    }

    public final boolean zzJ(String str, Long l, long j, com.google.android.gms.internal.measurement.zzfs zzfsVar) {
        zzg();
        zzW();
        Preconditions.checkNotNull(zzfsVar);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l);
        byte[] zzbv = zzfsVar.zzbv();
        this.zzs.zzay().zzj().zzc("Saving complex main event, appId, data size", this.zzs.zzj().zzd(str), Integer.valueOf(zzbv.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("event_id", l);
        contentValues.put("children_to_process", Long.valueOf(j));
        contentValues.put("main_event", zzbv);
        try {
            if (zzh().insertWithOnConflict("main_event_params", null, contentValues, 5) == -1) {
                this.zzs.zzay().zzd().zzb("Failed to insert complex main event (got -1). appId", zzeo.zzn(str));
                return false;
            }
            return true;
        } catch (SQLiteException e) {
            this.zzs.zzay().zzd().zzc("Error storing complex main event. appId", zzeo.zzn(str), e);
            return false;
        }
    }

    public final boolean zzK(zzac zzacVar) {
        Preconditions.checkNotNull(zzacVar);
        zzg();
        zzW();
        String str = zzacVar.zza;
        Preconditions.checkNotNull(str);
        if (zzp(str, zzacVar.zzc.zzb) == null) {
            long zzZ = zzZ("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{str});
            this.zzs.zzf();
            if (zzZ >= 1000) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("origin", zzacVar.zzb);
        contentValues.put("name", zzacVar.zzc.zzb);
        zzV(contentValues, "value", Preconditions.checkNotNull(zzacVar.zzc.zza()));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, Boolean.valueOf(zzacVar.zze));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, zzacVar.zzf);
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.valueOf(zzacVar.zzh));
        contentValues.put("timed_out_event", this.zzs.zzv().zzan(zzacVar.zzg));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, Long.valueOf(zzacVar.zzd));
        contentValues.put("triggered_event", this.zzs.zzv().zzan(zzacVar.zzi));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_TIMESTAMP, Long.valueOf(zzacVar.zzc.zzc));
        contentValues.put(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.valueOf(zzacVar.zzj));
        contentValues.put("expired_event", this.zzs.zzv().zzan(zzacVar.zzk));
        try {
            if (zzh().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                this.zzs.zzay().zzd().zzb("Failed to insert/update conditional user property (got -1)", zzeo.zzn(str));
            }
        } catch (SQLiteException e) {
            this.zzs.zzay().zzd().zzc("Error storing conditional user property", zzeo.zzn(str), e);
        }
        return true;
    }

    public final boolean zzL(zzle zzleVar) {
        Preconditions.checkNotNull(zzleVar);
        zzg();
        zzW();
        if (zzp(zzleVar.zza, zzleVar.zzc) == null) {
            if (zzlh.zzai(zzleVar.zzc)) {
                if (zzZ("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzleVar.zza}) >= this.zzs.zzf().zzf(zzleVar.zza, zzeb.zzF, 25, 100)) {
                    return false;
                }
            } else if (!"_npa".equals(zzleVar.zzc)) {
                long zzZ = zzZ("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzleVar.zza, zzleVar.zzb});
                this.zzs.zzf();
                if (zzZ >= 25) {
                    return false;
                }
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzleVar.zza);
        contentValues.put("origin", zzleVar.zzb);
        contentValues.put("name", zzleVar.zzc);
        contentValues.put("set_timestamp", Long.valueOf(zzleVar.zzd));
        zzV(contentValues, "value", zzleVar.zze);
        try {
            if (zzh().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                this.zzs.zzay().zzd().zzb("Failed to insert/update user property (got -1). appId", zzeo.zzn(zzleVar.zza));
            }
        } catch (SQLiteException e) {
            this.zzs.zzay().zzd().zzc("Error storing user property. appId", zzeo.zzn(zzleVar.zza), e);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r4v4, types: [boolean] */
    public final void zzU(String str, long j, long j2, zzkw zzkwVar) {
        Cursor cursor;
        ?? r4;
        Cursor rawQuery;
        String string;
        String str2;
        String[] strArr;
        Preconditions.checkNotNull(zzkwVar);
        zzg();
        zzW();
        String str3 = null;
        try {
            try {
                SQLiteDatabase zzh2 = zzh();
                r4 = TextUtils.isEmpty(null);
                try {
                    if (r4 != 0) {
                        rawQuery = zzh2.rawQuery("select app_id, metadata_fingerprint from raw_events where " + (j2 != -1 ? "rowid <= ? and " : "") + "app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;", j2 != -1 ? new String[]{String.valueOf(j2), String.valueOf(j)} : new String[]{String.valueOf(j)});
                        if (!rawQuery.moveToFirst()) {
                            if (rawQuery != null) {
                                rawQuery.close();
                                return;
                            }
                            return;
                        }
                        str3 = rawQuery.getString(0);
                        string = rawQuery.getString(1);
                        rawQuery.close();
                    } else {
                        rawQuery = zzh2.rawQuery("select metadata_fingerprint from raw_events where app_id = ?" + (j2 != -1 ? " and rowid <= ?" : "") + " order by rowid limit 1;", j2 != -1 ? new String[]{null, String.valueOf(j2)} : new String[]{null});
                        if (!rawQuery.moveToFirst()) {
                            if (rawQuery != null) {
                                rawQuery.close();
                                return;
                            }
                            return;
                        }
                        string = rawQuery.getString(0);
                        rawQuery.close();
                    }
                    Cursor cursor2 = rawQuery;
                    String str4 = string;
                    try {
                        Cursor query = zzh2.query("raw_events_metadata", new String[]{TtmlNode.TAG_METADATA}, "app_id = ? and metadata_fingerprint = ?", new String[]{str3, str4}, null, null, "rowid", FnSecPkgConfig.CLOSE_PURCHASE);
                        try {
                            if (!query.moveToFirst()) {
                                this.zzs.zzay().zzd().zzb("Raw event metadata record is missing. appId", zzeo.zzn(str3));
                                if (query != null) {
                                    query.close();
                                    return;
                                }
                                return;
                            }
                            try {
                                try {
                                    com.google.android.gms.internal.measurement.zzgc zzgcVar = (com.google.android.gms.internal.measurement.zzgc) ((com.google.android.gms.internal.measurement.zzgb) zzlb.zzl(com.google.android.gms.internal.measurement.zzgc.zzt(), query.getBlob(0))).zzaE();
                                    if (query.moveToNext()) {
                                        this.zzs.zzay().zzk().zzb("Get multiple raw event metadata records, expected one. appId", zzeo.zzn(str3));
                                    }
                                    query.close();
                                    Preconditions.checkNotNull(zzgcVar);
                                    zzkwVar.zza = zzgcVar;
                                    if (j2 != -1) {
                                        str2 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
                                        strArr = new String[]{str3, str4, String.valueOf(j2)};
                                    } else {
                                        str2 = "app_id = ? and metadata_fingerprint = ?";
                                        strArr = new String[]{str3, str4};
                                    }
                                    Cursor query2 = zzh2.query("raw_events", new String[]{"rowid", "name", "timestamp", "data"}, str2, strArr, null, null, "rowid", null);
                                    if (!query2.moveToFirst()) {
                                        this.zzs.zzay().zzk().zzb("Raw event data disappeared while in transaction. appId", zzeo.zzn(str3));
                                        if (query2 != null) {
                                            query2.close();
                                            return;
                                        }
                                        return;
                                    }
                                    do {
                                        long j3 = query2.getLong(0);
                                        try {
                                            com.google.android.gms.internal.measurement.zzfr zzfrVar = (com.google.android.gms.internal.measurement.zzfr) zzlb.zzl(com.google.android.gms.internal.measurement.zzfs.zze(), query2.getBlob(3));
                                            zzfrVar.zzi(query2.getString(1));
                                            zzfrVar.zzm(query2.getLong(2));
                                            if (!zzkwVar.zza(j3, (com.google.android.gms.internal.measurement.zzfs) zzfrVar.zzaE())) {
                                                if (query2 != null) {
                                                    query2.close();
                                                    return;
                                                }
                                                return;
                                            }
                                        } catch (IOException e) {
                                            this.zzs.zzay().zzd().zzc("Data loss. Failed to merge raw event. appId", zzeo.zzn(str3), e);
                                        }
                                    } while (query2.moveToNext());
                                    if (query2 != null) {
                                        query2.close();
                                    }
                                } catch (IOException e2) {
                                    this.zzs.zzay().zzd().zzc("Data loss. Failed to merge raw event metadata. appId", zzeo.zzn(str3), e2);
                                    if (query != null) {
                                        query.close();
                                    }
                                }
                            } catch (SQLiteException e3) {
                                e = e3;
                                r4 = query;
                                this.zzs.zzay().zzd().zzc("Data loss. Error selecting raw event. appId", zzeo.zzn(str3), e);
                                if (r4 != 0) {
                                    r4.close();
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor = query;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteException e4) {
                            e = e4;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (SQLiteException e5) {
                        e = e5;
                        r4 = cursor2;
                    } catch (Throwable th3) {
                        th = th3;
                        cursor = cursor2;
                    }
                } catch (SQLiteException e6) {
                    e = e6;
                }
            } catch (SQLiteException e7) {
                e = e7;
                r4 = 0;
            } catch (Throwable th4) {
                th = th4;
                cursor = null;
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    public final int zza(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzg();
        zzW();
        try {
            return zzh().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            this.zzs.zzay().zzd().zzd("Error deleting conditional property", zzeo.zzn(str), this.zzs.zzj().zzf(str2), e);
            return 0;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzkn
    protected final boolean zzb() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final long zzc(String str, String str2) {
        long j;
        ContentValues contentValues;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty("first_open_count");
        zzg();
        zzW();
        SQLiteDatabase zzh2 = zzh();
        zzh2.beginTransaction();
        try {
            try {
                j = zzaa("select first_open_count from app2 where app_id=?", new String[]{str}, -1L);
                if (j == -1) {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("app_id", str);
                    contentValues2.put("first_open_count", (Integer) 0);
                    contentValues2.put("previous_install_count", (Integer) 0);
                    if (zzh2.insertWithOnConflict("app2", null, contentValues2, 5) == -1) {
                        this.zzs.zzay().zzd().zzc("Failed to insert column (got -1). appId", zzeo.zzn(str), "first_open_count");
                        return -1L;
                    }
                    j = 0;
                }
            } catch (SQLiteException e) {
                e = e;
                j = 0;
            }
            try {
                contentValues = new ContentValues();
                contentValues.put("app_id", str);
                contentValues.put("first_open_count", Long.valueOf(1 + j));
            } catch (SQLiteException e2) {
                e = e2;
                this.zzs.zzay().zzd().zzd("Error inserting column. appId", zzeo.zzn(str), "first_open_count", e);
                return j;
            }
            if (zzh2.update("app2", contentValues, "app_id = ?", new String[]{str}) == 0) {
                this.zzs.zzay().zzd().zzc("Failed to update column (got 0). appId", zzeo.zzn(str), "first_open_count");
                return -1L;
            }
            zzh2.setTransactionSuccessful();
            return j;
        } finally {
            zzh2.endTransaction();
        }
    }

    public final long zzd() {
        return zzaa("select max(bundle_end_timestamp) from queue", null, 0L);
    }

    public final long zze() {
        return zzaa("select max(timestamp) from raw_events", null, 0L);
    }

    public final long zzf(String str) {
        Preconditions.checkNotEmpty(str);
        return zzaa("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final SQLiteDatabase zzh() {
        zzg();
        try {
            return this.zzj.getWritableDatabase();
        } catch (SQLiteException e) {
            this.zzs.zzay().zzk().zzb("Error opening database", e);
            throw e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00e0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle zzi(java.lang.String r8) {
        /*
            r7 = this;
            r7.zzg()
            r7.zzW()
            r0 = 0
            android.database.sqlite.SQLiteDatabase r1 = r7.zzh()     // Catch: java.lang.Throwable -> Lc4 android.database.sqlite.SQLiteException -> Lc7
            r2 = 1
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch: java.lang.Throwable -> Lc4 android.database.sqlite.SQLiteException -> Lc7
            r3 = 0
            r2[r3] = r8     // Catch: java.lang.Throwable -> Lc4 android.database.sqlite.SQLiteException -> Lc7
            java.lang.String r4 = "select parameters from default_event_params where app_id=?"
            android.database.Cursor r1 = r1.rawQuery(r4, r2)     // Catch: java.lang.Throwable -> Lc4 android.database.sqlite.SQLiteException -> Lc7
            boolean r2 = r1.moveToFirst()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            if (r2 != 0) goto L32
            com.google.android.gms.measurement.internal.zzfy r8 = r7.zzs     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.measurement.internal.zzeo r8 = r8.zzay()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.measurement.internal.zzem r8 = r8.zzj()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            java.lang.String r2 = "Default event parameters not found"
            r8.zza(r2)     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            if (r1 == 0) goto L31
            r1.close()
        L31:
            return r0
        L32:
            byte[] r2 = r1.getBlob(r3)     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.internal.measurement.zzfr r3 = com.google.android.gms.internal.measurement.zzfs.zze()     // Catch: java.io.IOException -> La6 java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.internal.measurement.zzlk r2 = com.google.android.gms.measurement.internal.zzlb.zzl(r3, r2)     // Catch: java.io.IOException -> La6 java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.internal.measurement.zzfr r2 = (com.google.android.gms.internal.measurement.zzfr) r2     // Catch: java.io.IOException -> La6 java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.internal.measurement.zzke r2 = r2.zzaE()     // Catch: java.io.IOException -> La6 java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.internal.measurement.zzfs r2 = (com.google.android.gms.internal.measurement.zzfs) r2     // Catch: java.io.IOException -> La6 java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.measurement.internal.zzkz r8 = r7.zzf     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            r8.zzu()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            java.util.List r8 = r2.zzi()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            android.os.Bundle r2 = new android.os.Bundle     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            r2.<init>()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            java.util.Iterator r8 = r8.iterator()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
        L58:
            boolean r3 = r8.hasNext()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            if (r3 == 0) goto La0
            java.lang.Object r3 = r8.next()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.internal.measurement.zzfw r3 = (com.google.android.gms.internal.measurement.zzfw) r3     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            java.lang.String r4 = r3.zzg()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            boolean r5 = r3.zzu()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            if (r5 == 0) goto L76
            double r5 = r3.zza()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            r2.putDouble(r4, r5)     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            goto L58
        L76:
            boolean r5 = r3.zzv()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            if (r5 == 0) goto L84
            float r3 = r3.zzb()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            r2.putFloat(r4, r3)     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            goto L58
        L84:
            boolean r5 = r3.zzy()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            if (r5 == 0) goto L92
            java.lang.String r3 = r3.zzh()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            r2.putString(r4, r3)     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            goto L58
        L92:
            boolean r5 = r3.zzw()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            if (r5 == 0) goto L58
            long r5 = r3.zzd()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            r2.putLong(r4, r5)     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            goto L58
        La0:
            if (r1 == 0) goto La5
            r1.close()
        La5:
            return r2
        La6:
            r2 = move-exception
            com.google.android.gms.measurement.internal.zzfy r3 = r7.zzs     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.measurement.internal.zzeo r3 = r3.zzay()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            com.google.android.gms.measurement.internal.zzem r3 = r3.zzd()     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            java.lang.String r4 = "Failed to retrieve default event parameters. appId"
            java.lang.Object r8 = com.google.android.gms.measurement.internal.zzeo.zzn(r8)     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            r3.zzc(r4, r8, r2)     // Catch: java.lang.Throwable -> Lc0 android.database.sqlite.SQLiteException -> Lc2
            if (r1 == 0) goto Lbf
            r1.close()
        Lbf:
            return r0
        Lc0:
            r8 = move-exception
            goto Lde
        Lc2:
            r8 = move-exception
            goto Lc9
        Lc4:
            r8 = move-exception
            r1 = r0
            goto Lde
        Lc7:
            r8 = move-exception
            r1 = r0
        Lc9:
            com.google.android.gms.measurement.internal.zzfy r2 = r7.zzs     // Catch: java.lang.Throwable -> Lc0
            com.google.android.gms.measurement.internal.zzeo r2 = r2.zzay()     // Catch: java.lang.Throwable -> Lc0
            com.google.android.gms.measurement.internal.zzem r2 = r2.zzd()     // Catch: java.lang.Throwable -> Lc0
            java.lang.String r3 = "Error selecting default event parameters"
            r2.zzb(r3, r8)     // Catch: java.lang.Throwable -> Lc0
            if (r1 == 0) goto Ldd
            r1.close()
        Ldd:
            return r0
        Lde:
            if (r1 == 0) goto Le3
            r1.close()
        Le3:
            goto Le5
        Le4:
            throw r8
        Le5:
            goto Le4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzi(java.lang.String):android.os.Bundle");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x011f A[Catch: all -> 0x01cf, SQLiteException -> 0x01d1, TryCatch #2 {SQLiteException -> 0x01d1, blocks: (B:4:0x0061, B:9:0x006d, B:11:0x00d0, B:16:0x00da, B:20:0x0124, B:22:0x0153, B:26:0x015b, B:30:0x0176, B:32:0x0181, B:33:0x0193, B:35:0x01a4, B:36:0x01ad, B:38:0x01b6, B:29:0x0172, B:19:0x011f), top: B:57:0x0061 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0172 A[Catch: all -> 0x01cf, SQLiteException -> 0x01d1, TryCatch #2 {SQLiteException -> 0x01d1, blocks: (B:4:0x0061, B:9:0x006d, B:11:0x00d0, B:16:0x00da, B:20:0x0124, B:22:0x0153, B:26:0x015b, B:30:0x0176, B:32:0x0181, B:33:0x0193, B:35:0x01a4, B:36:0x01ad, B:38:0x01b6, B:29:0x0172, B:19:0x011f), top: B:57:0x0061 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0181 A[Catch: all -> 0x01cf, SQLiteException -> 0x01d1, TryCatch #2 {SQLiteException -> 0x01d1, blocks: (B:4:0x0061, B:9:0x006d, B:11:0x00d0, B:16:0x00da, B:20:0x0124, B:22:0x0153, B:26:0x015b, B:30:0x0176, B:32:0x0181, B:33:0x0193, B:35:0x01a4, B:36:0x01ad, B:38:0x01b6, B:29:0x0172, B:19:0x011f), top: B:57:0x0061 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x01a4 A[Catch: all -> 0x01cf, SQLiteException -> 0x01d1, TryCatch #2 {SQLiteException -> 0x01d1, blocks: (B:4:0x0061, B:9:0x006d, B:11:0x00d0, B:16:0x00da, B:20:0x0124, B:22:0x0153, B:26:0x015b, B:30:0x0176, B:32:0x0181, B:33:0x0193, B:35:0x01a4, B:36:0x01ad, B:38:0x01b6, B:29:0x0172, B:19:0x011f), top: B:57:0x0061 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x01b6 A[Catch: all -> 0x01cf, SQLiteException -> 0x01d1, TRY_LEAVE, TryCatch #2 {SQLiteException -> 0x01d1, blocks: (B:4:0x0061, B:9:0x006d, B:11:0x00d0, B:16:0x00da, B:20:0x0124, B:22:0x0153, B:26:0x015b, B:30:0x0176, B:32:0x0181, B:33:0x0193, B:35:0x01a4, B:36:0x01ad, B:38:0x01b6, B:29:0x0172, B:19:0x011f), top: B:57:0x0061 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01f3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.measurement.internal.zzh zzj(java.lang.String r35) {
        /*
            Method dump skipped, instructions count: 503
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzj(java.lang.String):com.google.android.gms.measurement.internal.zzh");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x012f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.measurement.internal.zzac zzk(java.lang.String r31, java.lang.String r32) {
        /*
            Method dump skipped, instructions count: 307
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzk(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzac");
    }

    public final zzak zzl(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        return zzm(j, str, 1L, false, false, z3, false, z5);
    }

    public final zzak zzm(long j, String str, long j2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Preconditions.checkNotEmpty(str);
        zzg();
        zzW();
        String[] strArr = {str};
        zzak zzakVar = new zzak();
        Cursor cursor = null;
        try {
            try {
                SQLiteDatabase zzh2 = zzh();
                Cursor query = zzh2.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
                if (!query.moveToFirst()) {
                    this.zzs.zzay().zzk().zzb("Not updating daily counts, app is not known. appId", zzeo.zzn(str));
                    if (query != null) {
                        query.close();
                    }
                    return zzakVar;
                }
                if (query.getLong(0) == j) {
                    zzakVar.zzb = query.getLong(1);
                    zzakVar.zza = query.getLong(2);
                    zzakVar.zzc = query.getLong(3);
                    zzakVar.zzd = query.getLong(4);
                    zzakVar.zze = query.getLong(5);
                }
                if (z) {
                    zzakVar.zzb += j2;
                }
                if (z2) {
                    zzakVar.zza += j2;
                }
                if (z3) {
                    zzakVar.zzc += j2;
                }
                if (z4) {
                    zzakVar.zzd += j2;
                }
                if (z5) {
                    zzakVar.zze += j2;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("day", Long.valueOf(j));
                contentValues.put("daily_public_events_count", Long.valueOf(zzakVar.zza));
                contentValues.put("daily_events_count", Long.valueOf(zzakVar.zzb));
                contentValues.put("daily_conversions_count", Long.valueOf(zzakVar.zzc));
                contentValues.put("daily_error_events_count", Long.valueOf(zzakVar.zzd));
                contentValues.put("daily_realtime_events_count", Long.valueOf(zzakVar.zze));
                zzh2.update("apps", contentValues, "app_id=?", strArr);
                if (query != null) {
                    query.close();
                }
                return zzakVar;
            } catch (SQLiteException e) {
                this.zzs.zzay().zzd().zzc("Error updating daily counts. appId", zzeo.zzn(str), e);
                if (0 != 0) {
                    cursor.close();
                }
                return zzakVar;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x014f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.measurement.internal.zzas zzn(java.lang.String r28, java.lang.String r29) {
        /*
            Method dump skipped, instructions count: 339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzn(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzas");
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.google.android.gms.measurement.internal.zzle zzp(java.lang.String r20, java.lang.String r21) {
        /*
            r19 = this;
            r1 = r19
            r9 = r21
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r20)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r21)
            r19.zzg()
            r19.zzW()
            r10 = 0
            android.database.sqlite.SQLiteDatabase r11 = r19.zzh()     // Catch: java.lang.Throwable -> L83 android.database.sqlite.SQLiteException -> L86
            java.lang.String r0 = "set_timestamp"
            java.lang.String r2 = "value"
            java.lang.String r3 = "origin"
            java.lang.String[] r13 = new java.lang.String[]{r0, r2, r3}     // Catch: java.lang.Throwable -> L83 android.database.sqlite.SQLiteException -> L86
            r0 = 2
            java.lang.String[] r15 = new java.lang.String[r0]     // Catch: java.lang.Throwable -> L83 android.database.sqlite.SQLiteException -> L86
            r2 = 0
            r15[r2] = r20     // Catch: java.lang.Throwable -> L83 android.database.sqlite.SQLiteException -> L86
            r3 = 1
            r15[r3] = r9     // Catch: java.lang.Throwable -> L83 android.database.sqlite.SQLiteException -> L86
            java.lang.String r12 = "user_attributes"
            java.lang.String r14 = "app_id=? and name=?"
            r16 = 0
            r17 = 0
            r18 = 0
            android.database.Cursor r11 = r11.query(r12, r13, r14, r15, r16, r17, r18)     // Catch: java.lang.Throwable -> L83 android.database.sqlite.SQLiteException -> L86
            boolean r4 = r11.moveToFirst()     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            if (r4 != 0) goto L42
            if (r11 == 0) goto L41
            r11.close()
        L41:
            return r10
        L42:
            long r6 = r11.getLong(r2)     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            java.lang.Object r8 = r1.zzq(r11, r3)     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            if (r8 != 0) goto L52
            if (r11 == 0) goto L51
            r11.close()
        L51:
            return r10
        L52:
            java.lang.String r4 = r11.getString(r0)     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            com.google.android.gms.measurement.internal.zzle r0 = new com.google.android.gms.measurement.internal.zzle     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            r2 = r0
            r3 = r20
            r5 = r21
            r2.<init>(r3, r4, r5, r6, r8)     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            boolean r2 = r11.moveToNext()     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            if (r2 == 0) goto L79
            com.google.android.gms.measurement.internal.zzfy r2 = r1.zzs     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            com.google.android.gms.measurement.internal.zzeo r2 = r2.zzay()     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            com.google.android.gms.measurement.internal.zzem r2 = r2.zzd()     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            java.lang.String r3 = "Got multiple records for user property, expected one. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeo.zzn(r20)     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
            r2.zzb(r3, r4)     // Catch: java.lang.Throwable -> L7f android.database.sqlite.SQLiteException -> L81
        L79:
            if (r11 == 0) goto L7e
            r11.close()
        L7e:
            return r0
        L7f:
            r0 = move-exception
            goto Lab
        L81:
            r0 = move-exception
            goto L88
        L83:
            r0 = move-exception
            r11 = r10
            goto Lab
        L86:
            r0 = move-exception
            r11 = r10
        L88:
            com.google.android.gms.measurement.internal.zzfy r2 = r1.zzs     // Catch: java.lang.Throwable -> L7f
            com.google.android.gms.measurement.internal.zzeo r2 = r2.zzay()     // Catch: java.lang.Throwable -> L7f
            com.google.android.gms.measurement.internal.zzem r2 = r2.zzd()     // Catch: java.lang.Throwable -> L7f
            java.lang.String r3 = "Error querying user property. appId"
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzeo.zzn(r20)     // Catch: java.lang.Throwable -> L7f
            com.google.android.gms.measurement.internal.zzfy r5 = r1.zzs     // Catch: java.lang.Throwable -> L7f
            com.google.android.gms.measurement.internal.zzej r5 = r5.zzj()     // Catch: java.lang.Throwable -> L7f
            java.lang.String r5 = r5.zzf(r9)     // Catch: java.lang.Throwable -> L7f
            r2.zzd(r3, r4, r5, r0)     // Catch: java.lang.Throwable -> L7f
            if (r11 == 0) goto Laa
            r11.close()
        Laa:
            return r10
        Lab:
            if (r11 == 0) goto Lb0
            r11.close()
        Lb0:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzp(java.lang.String, java.lang.String):com.google.android.gms.measurement.internal.zzle");
    }

    final Object zzq(Cursor cursor, int i) {
        int type = cursor.getType(i);
        if (type == 0) {
            this.zzs.zzay().zzd().zza("Loaded invalid null value from database");
            return null;
        } else if (type != 1) {
            if (type != 2) {
                if (type != 3) {
                    if (type != 4) {
                        this.zzs.zzay().zzd().zzb("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
                        return null;
                    }
                    this.zzs.zzay().zzd().zza("Loaded invalid blob type value, ignoring it");
                    return null;
                }
                return cursor.getString(i);
            }
            return Double.valueOf(cursor.getDouble(i));
        } else {
            return Long.valueOf(cursor.getLong(i));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0045  */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v5, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String zzr() {
        /*
            r6 = this;
            android.database.sqlite.SQLiteDatabase r0 = r6.zzh()
            r1 = 0
            java.lang.String r2 = "select app_id from queue order by has_realtime desc, rowid asc limit 1;"
            android.database.Cursor r0 = r0.rawQuery(r2, r1)     // Catch: java.lang.Throwable -> L26 android.database.sqlite.SQLiteException -> L2b
            boolean r2 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L22 android.database.sqlite.SQLiteException -> L24
            if (r2 == 0) goto L1c
            r2 = 0
            java.lang.String r1 = r0.getString(r2)     // Catch: java.lang.Throwable -> L22 android.database.sqlite.SQLiteException -> L24
            if (r0 == 0) goto L1b
            r0.close()
        L1b:
            return r1
        L1c:
            if (r0 == 0) goto L21
            r0.close()
        L21:
            return r1
        L22:
            r1 = move-exception
            goto L43
        L24:
            r2 = move-exception
            goto L2e
        L26:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L43
        L2b:
            r0 = move-exception
            r2 = r0
            r0 = r1
        L2e:
            com.google.android.gms.measurement.internal.zzfy r3 = r6.zzs     // Catch: java.lang.Throwable -> L22
            com.google.android.gms.measurement.internal.zzeo r3 = r3.zzay()     // Catch: java.lang.Throwable -> L22
            com.google.android.gms.measurement.internal.zzem r3 = r3.zzd()     // Catch: java.lang.Throwable -> L22
            java.lang.String r4 = "Database error getting next bundle app id"
            r3.zzb(r4, r2)     // Catch: java.lang.Throwable -> L22
            if (r0 == 0) goto L42
            r0.close()
        L42:
            return r1
        L43:
            if (r0 == 0) goto L48
            r0.close()
        L48:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzr():java.lang.String");
    }

    public final List zzs(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzg();
        zzW();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            sb.append(" and name glob ?");
        }
        return zzt(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0058, code lost:
        r2 = r27.zzs.zzay().zzd();
        r27.zzs.zzf();
        r2.zzb("Read more than the max allowed conditional properties, ignoring extra", 1000);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List zzt(java.lang.String r28, java.lang.String[] r29) {
        /*
            Method dump skipped, instructions count: 311
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzt(java.lang.String, java.lang.String[]):java.util.List");
    }

    public final List zzu(String str) {
        Preconditions.checkNotEmpty(str);
        zzg();
        zzW();
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                this.zzs.zzf();
                cursor = zzh().query("user_attributes", new String[]{"name", "origin", "set_timestamp", "value"}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayList;
                }
                do {
                    String string = cursor.getString(0);
                    String string2 = cursor.getString(1);
                    if (string2 == null) {
                        string2 = "";
                    }
                    String str2 = string2;
                    long j = cursor.getLong(2);
                    Object zzq = zzq(cursor, 3);
                    if (zzq == null) {
                        this.zzs.zzay().zzd().zzb("Read invalid user property value, ignoring it. appId", zzeo.zzn(str));
                    } else {
                        arrayList.add(new zzle(str, str2, string, j, zzq));
                    }
                } while (cursor.moveToNext());
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList;
            } catch (SQLiteException e) {
                this.zzs.zzay().zzd().zzc("Error querying user properties. appId", zzeo.zzn(str), e);
                List emptyList = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return emptyList;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x00a9, code lost:
        r0 = r17.zzs.zzay().zzd();
        r17.zzs.zzf();
        r0.zzb("Read more than the max allowed user properties, ignoring excess", 1000);
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0128  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List zzv(java.lang.String r18, java.lang.String r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 308
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzam.zzv(java.lang.String, java.lang.String, java.lang.String):java.util.List");
    }

    public final void zzw() {
        zzW();
        zzh().beginTransaction();
    }

    public final void zzx() {
        zzW();
        zzh().endTransaction();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzy(List list) {
        zzg();
        zzW();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (zzI()) {
            String str = "(" + TextUtils.join(",", list) + ")";
            if (zzZ("SELECT COUNT(1) FROM queue WHERE rowid IN " + str + " AND retry_count =  2147483647 LIMIT 1", null) > 0) {
                this.zzs.zzay().zzk().zza("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                zzh().execSQL("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN " + str + " AND (retry_count IS NULL OR retry_count < 2147483647)");
            } catch (SQLiteException e) {
                this.zzs.zzay().zzd().zzb("Error incrementing retry count. error", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzz() {
        zzg();
        zzW();
        if (zzI()) {
            long zza2 = this.zzf.zzs().zza.zza();
            long elapsedRealtime = this.zzs.zzav().elapsedRealtime();
            long abs = Math.abs(elapsedRealtime - zza2);
            this.zzs.zzf();
            if (abs > ((Long) zzeb.zzx.zza(null)).longValue()) {
                this.zzf.zzs().zza.zzb(elapsedRealtime);
                zzg();
                zzW();
                if (zzI()) {
                    SQLiteDatabase zzh2 = zzh();
                    this.zzs.zzf();
                    int delete = zzh2.delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(this.zzs.zzav().currentTimeMillis()), String.valueOf(zzag.zzA())});
                    if (delete > 0) {
                        this.zzs.zzay().zzj().zzb("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
                    }
                }
            }
        }
    }
}
