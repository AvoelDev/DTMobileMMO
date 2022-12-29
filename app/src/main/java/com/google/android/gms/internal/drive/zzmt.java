package com.google.android.gms.internal.drive;

/* loaded from: classes3.dex */
final class zzmt {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static String zzc(zzjc zzjcVar) {
        zzmu zzmuVar = new zzmu(zzjcVar);
        StringBuilder sb = new StringBuilder(zzmuVar.size());
        for (int i = 0; i < zzmuVar.size(); i++) {
            byte zzs = zzmuVar.zzs(i);
            if (zzs == 34) {
                sb.append("\\\"");
            } else if (zzs == 39) {
                sb.append("\\'");
            } else if (zzs != 92) {
                switch (zzs) {
                    case 7:
                        sb.append("\\a");
                        continue;
                    case 8:
                        sb.append("\\b");
                        continue;
                    case 9:
                        sb.append("\\t");
                        continue;
                    case 10:
                        sb.append("\\n");
                        continue;
                    case 11:
                        sb.append("\\v");
                        continue;
                    case 12:
                        sb.append("\\f");
                        continue;
                    case 13:
                        sb.append("\\r");
                        continue;
                    default:
                        if (zzs >= 32 && zzs <= 126) {
                            sb.append((char) zzs);
                            continue;
                        } else {
                            sb.append('\\');
                            sb.append((char) (((zzs >>> 6) & 3) + 48));
                            sb.append((char) (((zzs >>> 3) & 7) + 48));
                            sb.append((char) ((zzs & 7) + 48));
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }
}
