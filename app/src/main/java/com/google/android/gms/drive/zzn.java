package com.google.android.gms.drive;

@Deprecated
/* loaded from: classes3.dex */
public final class zzn extends ExecutionOptions {
    private boolean zzat;

    public static zzn zza(ExecutionOptions executionOptions) {
        zzp zzpVar = new zzp();
        if (executionOptions != null) {
            zzpVar.setConflictStrategy(executionOptions.zzn());
            zzpVar.setNotifyOnCompletion(executionOptions.zzm());
            String zzl = executionOptions.zzl();
            if (zzl != null) {
                zzpVar.setTrackingTag(zzl);
            }
        }
        return (zzn) zzpVar.build();
    }

    private zzn(String str, boolean z, int i, boolean z2) {
        super(str, z, i);
        this.zzat = z2;
    }

    public final boolean zzp() {
        return this.zzat;
    }
}
