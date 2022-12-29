package com.google.android.gms.internal.drive;

import com.google.android.gms.common.internal.Objects;
import java.util.Locale;

/* loaded from: classes3.dex */
public final class zze implements com.google.android.gms.drive.events.zzk {
    private final com.google.android.gms.drive.events.zzm zzcv;
    private final long zzcw;
    private final long zzcx;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.drive.events.zzm, com.google.android.gms.internal.drive.zzf] */
    public zze(zzh zzhVar) {
        this.zzcv = new zzf(zzhVar);
        this.zzcw = zzhVar.zzcw;
        this.zzcx = zzhVar.zzcx;
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (obj == this) {
                return true;
            }
            zze zzeVar = (zze) obj;
            if (Objects.equal(this.zzcv, zzeVar.zzcv) && this.zzcw == zzeVar.zzcw && this.zzcx == zzeVar.zzcx) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(Long.valueOf(this.zzcx), Long.valueOf(this.zzcw), Long.valueOf(this.zzcx));
    }

    public final String toString() {
        return String.format(Locale.US, "FileTransferProgress[FileTransferState: %s, BytesTransferred: %d, TotalBytes: %d]", this.zzcv.toString(), Long.valueOf(this.zzcw), Long.valueOf(this.zzcx));
    }
}
