package com.google.android.gms.drive;

import com.google.android.gms.common.internal.Objects;

/* loaded from: classes3.dex */
public class TransferPreferencesBuilder {
    public static final TransferPreferences DEFAULT_PREFERENCES = new zza(1, true, 256);
    private int zzbl;
    private boolean zzbm;
    private int zzbn;

    public TransferPreferencesBuilder() {
        this(DEFAULT_PREFERENCES);
    }

    public TransferPreferencesBuilder(TransferPreferences transferPreferences) {
        this.zzbl = transferPreferences.getNetworkPreference();
        this.zzbm = transferPreferences.isRoamingAllowed();
        this.zzbn = transferPreferences.getBatteryUsagePreference();
    }

    /* loaded from: classes3.dex */
    static class zza implements TransferPreferences {
        private final int zzbl;
        private final boolean zzbm;
        private final int zzbn;

        zza(int i, boolean z, int i2) {
            this.zzbl = i;
            this.zzbm = z;
            this.zzbn = i2;
        }

        @Override // com.google.android.gms.drive.TransferPreferences
        public final int getNetworkPreference() {
            return this.zzbl;
        }

        @Override // com.google.android.gms.drive.TransferPreferences
        public final boolean isRoamingAllowed() {
            return this.zzbm;
        }

        @Override // com.google.android.gms.drive.TransferPreferences
        public final int getBatteryUsagePreference() {
            return this.zzbn;
        }

        public final int hashCode() {
            return Objects.hashCode(Integer.valueOf(this.zzbl), Boolean.valueOf(this.zzbm), Integer.valueOf(this.zzbn));
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                zza zzaVar = (zza) obj;
                if (zzaVar.zzbl == this.zzbl && zzaVar.zzbm == this.zzbm && zzaVar.zzbn == this.zzbn) {
                    return true;
                }
            }
            return false;
        }

        public final String toString() {
            return String.format("NetworkPreference: %s, IsRoamingAllowed %s, BatteryUsagePreference %s", Integer.valueOf(this.zzbl), Boolean.valueOf(this.zzbm), Integer.valueOf(this.zzbn));
        }
    }

    public TransferPreferencesBuilder(FileUploadPreferences fileUploadPreferences) {
        this.zzbl = fileUploadPreferences.getNetworkTypePreference();
        this.zzbm = fileUploadPreferences.isRoamingAllowed();
        this.zzbn = fileUploadPreferences.getBatteryUsagePreference();
    }

    public TransferPreferencesBuilder setNetworkPreference(int i) {
        this.zzbl = i;
        return this;
    }

    public TransferPreferencesBuilder setIsRoamingAllowed(boolean z) {
        this.zzbm = z;
        return this;
    }

    public TransferPreferencesBuilder setBatteryUsagePreference(int i) {
        this.zzbn = i;
        return this;
    }

    public TransferPreferences build() {
        return new zza(this.zzbl, this.zzbm, this.zzbn);
    }
}
