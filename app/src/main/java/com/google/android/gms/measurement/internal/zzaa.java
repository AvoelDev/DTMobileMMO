package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-measurement@@21.1.1 */
/* loaded from: classes2.dex */
public final class zzaa extends zzkn {
    private String zza;
    private Set zzb;
    private Map zzc;
    private Long zzd;
    private Long zze;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzaa(zzkz zzkzVar) {
        super(zzkzVar);
    }

    private final zzu zzd(Integer num) {
        if (this.zzc.containsKey(num)) {
            return (zzu) this.zzc.get(num);
        }
        zzu zzuVar = new zzu(this, this.zza, null);
        this.zzc.put(num, zzuVar);
        return zzuVar;
    }

    private final boolean zzf(int i, int i2) {
        zzu zzuVar = (zzu) this.zzc.get(Integer.valueOf(i));
        if (zzuVar == null) {
            return false;
        }
        return zzu.zzb(zzuVar).get(i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't wrap try/catch for region: R(18:(6:19|20|21|22|23|(21:(7:25|26|27|28|(1:30)(3:519|(1:521)(1:523)|522)|31|(1:34)(1:33))|(1:36)|37|38|39|40|41|42|(3:44|(1:46)|47)(4:477|(6:478|479|480|481|482|(1:485)(1:484))|(1:487)|488)|48|(1:50)(6:286|(6:288|289|290|291|292|(2:(3:294|(1:296)|297)|299)(1:462))(1:476)|306|(10:309|(3:313|(4:316|(5:318|319|(1:321)(1:325)|322|323)(1:326)|324|314)|327)|328|(3:332|(4:335|(3:340|341|342)|343|333)|346)|347|(3:349|(6:352|(2:354|(3:356|357|358))(1:361)|359|360|358|350)|362)|363|(3:372|(8:375|(1:377)|378|(1:380)|381|(3:383|384|385)(1:387)|386|373)|388)|389|307)|395|396)|51|(3:182|(4:185|(10:187|188|(1:190)(1:283)|191|(10:193|194|195|196|197|198|199|200|(4:202|(11:203|204|205|206|207|208|209|(3:211|212|213)(1:256)|214|215|(1:218)(1:217))|(1:220)|221)(2:263|264)|222)(1:282)|223|(4:226|(3:244|245|246)(4:228|229|(2:230|(2:232|(1:234)(2:235|236))(1:243))|(3:238|239|240)(1:242))|241|224)|247|248|249)(1:284)|250|183)|285)|53|54|(3:81|(6:84|(7:86|87|88|89|90|(3:(9:92|93|94|95|96|(1:98)(1:157)|99|100|(1:103)(1:102))|(1:105)|106)(2:164|165)|107)(1:180)|108|(2:109|(2:111|(3:147|148|149)(6:113|(2:114|(4:116|(3:118|(1:120)(1:143)|121)(1:144)|122|(1:1)(2:126|(1:128)(2:129|130)))(2:145|146))|(2:135|134)|132|133|134))(0))|150|82)|181)|56|57|(9:60|61|62|63|64|65|(2:67|68)(1:70)|69|58)|78|79)(1:527))|39|40|41|42|(0)(0)|48|(0)(0)|51|(0)|53|54|(0)|56|57|(1:58)|78|79) */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x02d3, code lost:
        if (r5 != null) goto L301;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x02d5, code lost:
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x02dd, code lost:
        if (r5 != null) goto L301;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0302, code lost:
        if (r5 == null) goto L302;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0305, code lost:
        com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r1);
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r13);
        r1 = new androidx.collection.ArrayMap();
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0314, code lost:
        if (r13.isEmpty() == false) goto L397;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0316, code lost:
        r21 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x031a, code lost:
        r3 = r13.keySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0326, code lost:
        if (r3.hasNext() == false) goto L461;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0328, code lost:
        r4 = ((java.lang.Integer) r3.next()).intValue();
        r5 = java.lang.Integer.valueOf(r4);
        r6 = (com.google.android.gms.internal.measurement.zzgh) r13.get(r5);
        r7 = (java.util.List) r0.get(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0342, code lost:
        if (r7 == null) goto L460;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0348, code lost:
        if (r7.isEmpty() == false) goto L404;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x034c, code lost:
        r17 = r0;
        r0 = r64.zzf.zzu().zzq(r6.zzk(), r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0360, code lost:
        if (r0.isEmpty() != false) goto L406;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0362, code lost:
        r5 = (com.google.android.gms.internal.measurement.zzgg) r6.zzby();
        r5.zzf();
        r5.zzb(r0);
        r20 = r3;
        r0 = r64.zzf.zzu().zzq(r6.zzn(), r7);
        r5.zzh();
        r5.zzd(r0);
        com.google.android.gms.internal.measurement.zzob.zzc();
        r21 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0396, code lost:
        if (r64.zzs.zzf().zzs(null, com.google.android.gms.measurement.internal.zzeb.zzaA) == false) goto L438;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0398, code lost:
        r0 = new java.util.ArrayList();
        r3 = r6.zzj().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x03a9, code lost:
        if (r3.hasNext() == false) goto L422;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x03ab, code lost:
        r8 = (com.google.android.gms.internal.measurement.zzfq) r3.next();
        r23 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x03c1, code lost:
        if (r7.contains(java.lang.Integer.valueOf(r8.zza())) != false) goto L421;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x03c3, code lost:
        r0.add(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x03c6, code lost:
        r3 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x03ca, code lost:
        r5.zze();
        r5.zza(r0);
        r0 = new java.util.ArrayList();
        r3 = r6.zzm().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x03e1, code lost:
        if (r3.hasNext() == false) goto L433;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x03e3, code lost:
        r6 = (com.google.android.gms.internal.measurement.zzgj) r3.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x03f5, code lost:
        if (r7.contains(java.lang.Integer.valueOf(r6.zzb())) != false) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x03f7, code lost:
        r0.add(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x03fb, code lost:
        r5.zzg();
        r5.zzc(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0402, code lost:
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0407, code lost:
        if (r0 >= r6.zza()) goto L447;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0419, code lost:
        if (r7.contains(java.lang.Integer.valueOf(r6.zze(r0).zza())) == false) goto L446;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x041b, code lost:
        r5.zzi(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x041e, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0421, code lost:
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0426, code lost:
        if (r0 >= r6.zzc()) goto L457;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0438, code lost:
        if (r7.contains(java.lang.Integer.valueOf(r6.zzi(r0).zzb())) == false) goto L456;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x043a, code lost:
        r5.zzj(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x043d, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0440, code lost:
        r1.put(java.lang.Integer.valueOf(r4), (com.google.android.gms.internal.measurement.zzgh) r5.zzaE());
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x044e, code lost:
        r0 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0452, code lost:
        r17 = r0;
        r20 = r3;
        r21 = r8;
        r1.put(r5, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x045b, code lost:
        r0 = r17;
        r3 = r20;
        r8 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0463, code lost:
        r0 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:297:0x079c, code lost:
        if (r5 != null) goto L255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x079e, code lost:
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:315:0x07d0, code lost:
        if (r5 != null) goto L255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x094f, code lost:
        if (r13 != null) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x0951, code lost:
        r13.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:385:0x097b, code lost:
        if (r13 == null) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:418:0x0a79, code lost:
        r0 = r64.zzs.zzay().zzk();
        r6 = com.google.android.gms.measurement.internal.zzeo.zzn(r64.zza);
     */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x0a8d, code lost:
        if (r7.zzj() == false) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:420:0x0a8f, code lost:
        r7 = java.lang.Integer.valueOf(r7.zza());
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0a98, code lost:
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x0a99, code lost:
        r0.zzc("Invalid property filter ID. appId, id", r6, java.lang.String.valueOf(r7));
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0151, code lost:
        if (r5 != null) goto L529;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0153, code lost:
        r5.close();
        r5 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0177, code lost:
        if (r5 == null) goto L542;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x022d, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x022e, code lost:
        r18 = "audience_id";
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0233, code lost:
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0235, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0236, code lost:
        r18 = "audience_id";
        r19 = "data";
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x023b, code lost:
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x023e, code lost:
        r5 = null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x061e  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x0886  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x0ad6  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x0b68  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b6 A[Catch: all -> 0x022a, SQLiteException -> 0x022d, TRY_LEAVE, TryCatch #9 {all -> 0x022a, blocks: (B:63:0x01b0, B:65:0x01b6, B:69:0x01c6, B:70:0x01cb, B:71:0x01d5, B:72:0x01e5, B:79:0x0211, B:74:0x01f4, B:76:0x0204, B:78:0x020a, B:100:0x023f), top: B:462:0x0196 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01c6 A[Catch: all -> 0x022a, SQLiteException -> 0x022d, TRY_ENTER, TryCatch #9 {all -> 0x022a, blocks: (B:63:0x01b0, B:65:0x01b6, B:69:0x01c6, B:70:0x01cb, B:71:0x01d5, B:72:0x01e5, B:79:0x0211, B:74:0x01f4, B:76:0x0204, B:78:0x020a, B:100:0x023f), top: B:462:0x0196 }] */
    /* JADX WARN: Type inference failed for: r5v11, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v55, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v56 */
    /* JADX WARN: Type inference failed for: r5v57, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List zza(java.lang.String r65, java.util.List r66, java.util.List r67, java.lang.Long r68, java.lang.Long r69) {
        /*
            Method dump skipped, instructions count: 2926
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzaa.zza(java.lang.String, java.util.List, java.util.List, java.lang.Long, java.lang.Long):java.util.List");
    }

    @Override // com.google.android.gms.measurement.internal.zzkn
    protected final boolean zzb() {
        return false;
    }
}
