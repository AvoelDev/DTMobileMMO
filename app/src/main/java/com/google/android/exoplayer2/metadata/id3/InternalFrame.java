package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class InternalFrame extends Id3Frame {
    public static final Parcelable.Creator<InternalFrame> CREATOR = new Parcelable.Creator<InternalFrame>() { // from class: com.google.android.exoplayer2.metadata.id3.InternalFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InternalFrame createFromParcel(Parcel in) {
            return new InternalFrame(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InternalFrame[] newArray(int size) {
            return new InternalFrame[size];
        }
    };
    public static final String ID = "----";
    public final String description;
    public final String domain;
    public final String text;

    public InternalFrame(String domain, String description, String text) {
        super(ID);
        this.domain = domain;
        this.description = description;
        this.text = text;
    }

    InternalFrame(Parcel in) {
        super(ID);
        this.domain = (String) Assertions.checkNotNull(in.readString());
        this.description = (String) Assertions.checkNotNull(in.readString());
        this.text = (String) Assertions.checkNotNull(in.readString());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InternalFrame other = (InternalFrame) obj;
        return Util.areEqual(this.description, other.description) && Util.areEqual(this.domain, other.domain) && Util.areEqual(this.text, other.text);
    }

    public int hashCode() {
        int result = (this.domain != null ? this.domain.hashCode() : 0) + 527;
        return (((result * 31) + (this.description != null ? this.description.hashCode() : 0)) * 31) + (this.text != null ? this.text.hashCode() : 0);
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Frame
    public String toString() {
        return this.id + ": domain=" + this.domain + ", description=" + this.description;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.domain);
        dest.writeString(this.text);
    }
}
