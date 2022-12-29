package com.google.android.gms.drive;

import android.text.TextUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.internal.drive.zzaw;

/* loaded from: classes3.dex */
public class ExecutionOptions {
    public static final int CONFLICT_STRATEGY_KEEP_REMOTE = 1;
    public static final int CONFLICT_STRATEGY_OVERWRITE_REMOTE = 0;
    public static final int MAX_TRACKING_TAG_STRING_LENGTH = 65536;
    private final String zzan;
    private final boolean zzao;
    private final int zzap;

    public ExecutionOptions(String str, boolean z, int i) {
        this.zzan = str;
        this.zzao = z;
        this.zzap = i;
    }

    public static boolean zza(int i) {
        return i == 1;
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        protected String zzaq;
        protected boolean zzar;
        protected int zzas = 0;

        public Builder setTrackingTag(String str) {
            if (!(!TextUtils.isEmpty(str) && str.length() <= 65536)) {
                throw new IllegalArgumentException(String.format("trackingTag must not be null nor empty, and the length must be <= the maximum length (%s)", 65536));
            }
            this.zzaq = str;
            return this;
        }

        public Builder setNotifyOnCompletion(boolean z) {
            this.zzar = z;
            return this;
        }

        public Builder setConflictStrategy(int i) {
            boolean z = true;
            if (i != 0 && i != 1) {
                z = false;
            }
            if (!z) {
                StringBuilder sb = new StringBuilder(53);
                sb.append("Unrecognized value for conflict strategy: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            }
            this.zzas = i;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public final void zzo() {
            if (this.zzas == 1 && !this.zzar) {
                throw new IllegalStateException("Cannot use CONFLICT_STRATEGY_KEEP_REMOTE without requesting completion notifications");
            }
        }

        public ExecutionOptions build() {
            zzo();
            return new ExecutionOptions(this.zzaq, this.zzar, this.zzas);
        }
    }

    public final String zzl() {
        return this.zzan;
    }

    public final boolean zzm() {
        return this.zzao;
    }

    public final int zzn() {
        return this.zzap;
    }

    @Deprecated
    public final void zza(GoogleApiClient googleApiClient) {
        zza((zzaw) googleApiClient.getClient(Drive.CLIENT_KEY));
    }

    public final void zza(zzaw zzawVar) {
        if (this.zzao && !zzawVar.zzah()) {
            throw new IllegalStateException("Application must define an exported DriveEventService subclass in AndroidManifest.xml to be notified on completion");
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (obj == this) {
                return true;
            }
            ExecutionOptions executionOptions = (ExecutionOptions) obj;
            if (Objects.equal(this.zzan, executionOptions.zzan) && this.zzap == executionOptions.zzap && this.zzao == executionOptions.zzao) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzan, Integer.valueOf(this.zzap), Boolean.valueOf(this.zzao));
    }
}
