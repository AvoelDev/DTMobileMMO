package com.google.android.gms.internal.drive;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum zzxc uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:444)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:368)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:333)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:318)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:258)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes3.dex */
public class zznm {
    public static final zznm zzwu = new zznm("DOUBLE", 0, zznr.DOUBLE, 1);
    public static final zznm zzwv = new zznm("FLOAT", 1, zznr.FLOAT, 5);
    public static final zznm zzww = new zznm("INT64", 2, zznr.LONG, 0);
    public static final zznm zzwx = new zznm("UINT64", 3, zznr.LONG, 0);
    public static final zznm zzwy = new zznm("INT32", 4, zznr.INT, 0);
    public static final zznm zzwz = new zznm("FIXED64", 5, zznr.LONG, 1);
    public static final zznm zzxa = new zznm("FIXED32", 6, zznr.INT, 5);
    public static final zznm zzxb = new zznm("BOOL", 7, zznr.BOOLEAN, 0);
    public static final zznm zzxc;
    public static final zznm zzxd;
    public static final zznm zzxe;
    public static final zznm zzxf;
    public static final zznm zzxg;
    public static final zznm zzxh;
    public static final zznm zzxi;
    public static final zznm zzxj;
    public static final zznm zzxk;
    public static final zznm zzxl;
    private static final /* synthetic */ zznm[] zzxo;
    private final zznr zzxm;
    private final int zzxn;

    public static zznm[] values() {
        return (zznm[]) zzxo.clone();
    }

    private zznm(String str, int i, zznr zznrVar, int i2) {
        this.zzxm = zznrVar;
        this.zzxn = i2;
    }

    public final zznr zzfj() {
        return this.zzxm;
    }

    public final int zzfk() {
        return this.zzxn;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zznm(String str, int i, zznr zznrVar, int i2, zznl zznlVar) {
        this(str, i, zznrVar, i2);
    }

    static {
        final zznr zznrVar = zznr.STRING;
        zzxc = new zznm("STRING", 8, zznrVar, 2) { // from class: com.google.android.gms.internal.drive.zznn
        };
        final zznr zznrVar2 = zznr.MESSAGE;
        zzxd = new zznm("GROUP", 9, zznrVar2, 3) { // from class: com.google.android.gms.internal.drive.zzno
        };
        final zznr zznrVar3 = zznr.MESSAGE;
        zzxe = new zznm("MESSAGE", 10, zznrVar3, 2) { // from class: com.google.android.gms.internal.drive.zznp
        };
        final zznr zznrVar4 = zznr.BYTE_STRING;
        zzxf = new zznm("BYTES", 11, zznrVar4, 2) { // from class: com.google.android.gms.internal.drive.zznq
        };
        zzxg = new zznm("UINT32", 12, zznr.INT, 0);
        zzxh = new zznm("ENUM", 13, zznr.ENUM, 0);
        zzxi = new zznm("SFIXED32", 14, zznr.INT, 5);
        zzxj = new zznm("SFIXED64", 15, zznr.LONG, 1);
        zzxk = new zznm("SINT32", 16, zznr.INT, 0);
        zzxl = new zznm("SINT64", 17, zznr.LONG, 0);
        zzxo = new zznm[]{zzwu, zzwv, zzww, zzwx, zzwy, zzwz, zzxa, zzxb, zzxc, zzxd, zzxe, zzxf, zzxg, zzxh, zzxi, zzxj, zzxk, zzxl};
    }
}
