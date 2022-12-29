package com.google.android.gms.drive;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaa;
import com.google.android.gms.internal.drive.zzhs;

/* loaded from: classes3.dex */
public final class MetadataBuffer extends AbstractDataBuffer<Metadata> {
    private zza zzau;

    public MetadataBuffer(DataHolder dataHolder) {
        super(dataHolder);
        dataHolder.getMetadata().setClassLoader(MetadataBuffer.class.getClassLoader());
    }

    @Deprecated
    public final String getNextPageToken() {
        return null;
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer
    public final Metadata get(int i) {
        zza zzaVar = this.zzau;
        if (zzaVar == null || zzaVar.row != i) {
            zza zzaVar2 = new zza(this.mDataHolder, i);
            this.zzau = zzaVar2;
            return zzaVar2;
        }
        return zzaVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class zza extends Metadata {
        private final int row;
        private final DataHolder zzav;
        private final int zzaw;

        public zza(DataHolder dataHolder, int i) {
            this.zzav = dataHolder;
            this.row = i;
            this.zzaw = dataHolder.getWindowIndex(i);
        }

        @Override // com.google.android.gms.drive.Metadata
        public final <T> T zza(MetadataField<T> metadataField) {
            return metadataField.zza(this.zzav, this.row, this.zzaw);
        }

        @Override // com.google.android.gms.common.data.Freezable
        public final boolean isDataValid() {
            return !this.zzav.isClosed();
        }

        @Override // com.google.android.gms.common.data.Freezable
        public final /* synthetic */ Metadata freeze() {
            MetadataBundle zzbe = MetadataBundle.zzbe();
            for (MetadataField<?> metadataField : com.google.android.gms.drive.metadata.internal.zzf.zzbc()) {
                if (metadataField != zzhs.zzkq) {
                    metadataField.zza(this.zzav, zzbe, this.row, this.zzaw);
                }
            }
            return new zzaa(zzbe);
        }
    }

    @Override // com.google.android.gms.common.data.AbstractDataBuffer, com.google.android.gms.common.data.DataBuffer, com.google.android.gms.common.api.Releasable
    public final void release() {
        if (this.mDataHolder != null) {
            com.google.android.gms.drive.metadata.internal.zzf.zza(this.mDataHolder);
        }
        super.release();
    }
}
