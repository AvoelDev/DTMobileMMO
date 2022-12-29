package com.google.android.exoplayer2.drm;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public final class DrmInitData implements Comparator<SchemeData>, Parcelable {
    public static final Parcelable.Creator<DrmInitData> CREATOR = new Parcelable.Creator<DrmInitData>() { // from class: com.google.android.exoplayer2.drm.DrmInitData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DrmInitData createFromParcel(Parcel in) {
            return new DrmInitData(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DrmInitData[] newArray(int size) {
            return new DrmInitData[size];
        }
    };
    private int hashCode;
    public final int schemeDataCount;
    private final SchemeData[] schemeDatas;
    @Nullable
    public final String schemeType;

    @Nullable
    public static DrmInitData createSessionCreationData(@Nullable DrmInitData manifestData, @Nullable DrmInitData mediaData) {
        SchemeData[] schemeDataArr;
        SchemeData[] schemeDataArr2;
        ArrayList<SchemeData> result = new ArrayList<>();
        String schemeType = null;
        if (manifestData != null) {
            schemeType = manifestData.schemeType;
            for (SchemeData data : manifestData.schemeDatas) {
                if (data.hasData()) {
                    result.add(data);
                }
            }
        }
        if (mediaData != null) {
            if (schemeType == null) {
                schemeType = mediaData.schemeType;
            }
            int manifestDatasCount = result.size();
            for (SchemeData data2 : mediaData.schemeDatas) {
                if (data2.hasData() && !containsSchemeDataWithUuid(result, manifestDatasCount, data2.uuid)) {
                    result.add(data2);
                }
            }
        }
        if (result.isEmpty()) {
            return null;
        }
        return new DrmInitData(schemeType, result);
    }

    public DrmInitData(List<SchemeData> schemeDatas) {
        this(null, false, (SchemeData[]) schemeDatas.toArray(new SchemeData[schemeDatas.size()]));
    }

    public DrmInitData(String schemeType, List<SchemeData> schemeDatas) {
        this(schemeType, false, (SchemeData[]) schemeDatas.toArray(new SchemeData[schemeDatas.size()]));
    }

    public DrmInitData(SchemeData... schemeDatas) {
        this((String) null, schemeDatas);
    }

    public DrmInitData(@Nullable String schemeType, SchemeData... schemeDatas) {
        this(schemeType, true, schemeDatas);
    }

    private DrmInitData(@Nullable String schemeType, boolean cloneSchemeDatas, SchemeData... schemeDatas) {
        this.schemeType = schemeType;
        schemeDatas = cloneSchemeDatas ? (SchemeData[]) schemeDatas.clone() : schemeDatas;
        Arrays.sort(schemeDatas, this);
        this.schemeDatas = schemeDatas;
        this.schemeDataCount = schemeDatas.length;
    }

    DrmInitData(Parcel in) {
        this.schemeType = in.readString();
        this.schemeDatas = (SchemeData[]) in.createTypedArray(SchemeData.CREATOR);
        this.schemeDataCount = this.schemeDatas.length;
    }

    @Deprecated
    public SchemeData get(UUID uuid) {
        SchemeData[] schemeDataArr;
        for (SchemeData schemeData : this.schemeDatas) {
            if (schemeData.matches(uuid)) {
                return schemeData;
            }
        }
        return null;
    }

    public SchemeData get(int index) {
        return this.schemeDatas[index];
    }

    public DrmInitData copyWithSchemeType(@Nullable String schemeType) {
        return Util.areEqual(this.schemeType, schemeType) ? this : new DrmInitData(schemeType, false, this.schemeDatas);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            int result = this.schemeType == null ? 0 : this.schemeType.hashCode();
            this.hashCode = (result * 31) + Arrays.hashCode(this.schemeDatas);
        }
        return this.hashCode;
    }

    @Override // java.util.Comparator
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DrmInitData other = (DrmInitData) obj;
        return Util.areEqual(this.schemeType, other.schemeType) && Arrays.equals(this.schemeDatas, other.schemeDatas);
    }

    @Override // java.util.Comparator
    public int compare(SchemeData first, SchemeData second) {
        if (C.UUID_NIL.equals(first.uuid)) {
            return C.UUID_NIL.equals(second.uuid) ? 0 : 1;
        }
        return first.uuid.compareTo(second.uuid);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schemeType);
        dest.writeTypedArray(this.schemeDatas, 0);
    }

    private static boolean containsSchemeDataWithUuid(ArrayList<SchemeData> datas, int limit, UUID uuid) {
        for (int i = 0; i < limit; i++) {
            if (datas.get(i).uuid.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    /* loaded from: classes.dex */
    public static final class SchemeData implements Parcelable {
        public static final Parcelable.Creator<SchemeData> CREATOR = new Parcelable.Creator<SchemeData>() { // from class: com.google.android.exoplayer2.drm.DrmInitData.SchemeData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SchemeData createFromParcel(Parcel in) {
                return new SchemeData(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SchemeData[] newArray(int size) {
                return new SchemeData[size];
            }
        };
        public final byte[] data;
        private int hashCode;
        @Nullable
        public final String licenseServerUrl;
        public final String mimeType;
        public final boolean requiresSecureDecryption;
        private final UUID uuid;

        public SchemeData(UUID uuid, String mimeType, byte[] data) {
            this(uuid, mimeType, data, false);
        }

        public SchemeData(UUID uuid, String mimeType, byte[] data, boolean requiresSecureDecryption) {
            this(uuid, null, mimeType, data, requiresSecureDecryption);
        }

        public SchemeData(UUID uuid, @Nullable String licenseServerUrl, String mimeType, byte[] data, boolean requiresSecureDecryption) {
            this.uuid = (UUID) Assertions.checkNotNull(uuid);
            this.licenseServerUrl = licenseServerUrl;
            this.mimeType = (String) Assertions.checkNotNull(mimeType);
            this.data = data;
            this.requiresSecureDecryption = requiresSecureDecryption;
        }

        SchemeData(Parcel in) {
            this.uuid = new UUID(in.readLong(), in.readLong());
            this.licenseServerUrl = in.readString();
            this.mimeType = in.readString();
            this.data = in.createByteArray();
            this.requiresSecureDecryption = in.readByte() != 0;
        }

        public boolean matches(UUID schemeUuid) {
            return C.UUID_NIL.equals(this.uuid) || schemeUuid.equals(this.uuid);
        }

        public boolean canReplace(SchemeData other) {
            return hasData() && !other.hasData() && matches(other.uuid);
        }

        public boolean hasData() {
            return this.data != null;
        }

        public boolean equals(@Nullable Object obj) {
            if (obj instanceof SchemeData) {
                if (obj != this) {
                    SchemeData other = (SchemeData) obj;
                    return Util.areEqual(this.licenseServerUrl, other.licenseServerUrl) && Util.areEqual(this.mimeType, other.mimeType) && Util.areEqual(this.uuid, other.uuid) && Arrays.equals(this.data, other.data);
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                int result = this.uuid.hashCode();
                this.hashCode = (((((result * 31) + (this.licenseServerUrl == null ? 0 : this.licenseServerUrl.hashCode())) * 31) + this.mimeType.hashCode()) * 31) + Arrays.hashCode(this.data);
            }
            return this.hashCode;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.uuid.getMostSignificantBits());
            dest.writeLong(this.uuid.getLeastSignificantBits());
            dest.writeString(this.licenseServerUrl);
            dest.writeString(this.mimeType);
            dest.writeByteArray(this.data);
            dest.writeByte((byte) (this.requiresSecureDecryption ? 1 : 0));
        }
    }
}
