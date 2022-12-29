package com.google.android.gms.internal.drive;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
final class zzkl extends zziw<Integer> implements zzkp<Integer>, zzmc, RandomAccess {
    private static final zzkl zzsl;
    private int size;
    private int[] zzsm;

    zzkl() {
        this(new int[10], 0);
    }

    private zzkl(int[] iArr, int i) {
        this.zzsm = iArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        zzbq();
        if (i2 < i) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        int[] iArr = this.zzsm;
        System.arraycopy(iArr, i2, iArr, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.drive.zziw, java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzkl)) {
            return super.equals(obj);
        }
        zzkl zzklVar = (zzkl) obj;
        if (this.size != zzklVar.size) {
            return false;
        }
        int[] iArr = zzklVar.zzsm;
        for (int i = 0; i < this.size; i++) {
            if (this.zzsm[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.google.android.gms.internal.drive.zziw, java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.zzsm[i2];
        }
        return i;
    }

    public final int getInt(int i) {
        zzp(i);
        return this.zzsm[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    public final void zzam(int i) {
        zzo(this.size, i);
    }

    private final void zzo(int i, int i2) {
        int i3;
        zzbq();
        if (i < 0 || i > (i3 = this.size)) {
            throw new IndexOutOfBoundsException(zzq(i));
        }
        int[] iArr = this.zzsm;
        if (i3 < iArr.length) {
            System.arraycopy(iArr, i, iArr, i + 1, i3 - i);
        } else {
            int[] iArr2 = new int[((i3 * 3) / 2) + 1];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            System.arraycopy(this.zzsm, i, iArr2, i + 1, this.size - i);
            this.zzsm = iArr2;
        }
        this.zzsm[i] = i2;
        this.size++;
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.drive.zziw, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends Integer> collection) {
        zzbq();
        zzkm.checkNotNull(collection);
        if (!(collection instanceof zzkl)) {
            return super.addAll(collection);
        }
        zzkl zzklVar = (zzkl) collection;
        int i = zzklVar.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 < i) {
            throw new OutOfMemoryError();
        }
        int i3 = i2 + i;
        int[] iArr = this.zzsm;
        if (i3 > iArr.length) {
            this.zzsm = Arrays.copyOf(iArr, i3);
        }
        System.arraycopy(zzklVar.zzsm, 0, this.zzsm, this.size, zzklVar.size);
        this.size = i3;
        this.modCount++;
        return true;
    }

    @Override // com.google.android.gms.internal.drive.zziw, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        zzbq();
        for (int i = 0; i < this.size; i++) {
            if (obj.equals(Integer.valueOf(this.zzsm[i]))) {
                int[] iArr = this.zzsm;
                System.arraycopy(iArr, i + 1, iArr, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzp(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException(zzq(i));
        }
    }

    private final String zzq(int i) {
        int i2 = this.size;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i);
        sb.append(", Size:");
        sb.append(i2);
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.drive.zziw, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        zzbq();
        zzp(i);
        int[] iArr = this.zzsm;
        int i2 = iArr[i];
        iArr[i] = intValue;
        return Integer.valueOf(i2);
    }

    @Override // com.google.android.gms.internal.drive.zziw, java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        int i2;
        zzbq();
        zzp(i);
        int[] iArr = this.zzsm;
        int i3 = iArr[i];
        if (i < this.size - 1) {
            System.arraycopy(iArr, i + 1, iArr, i, (i2 - i) - 1);
        }
        this.size--;
        this.modCount++;
        return Integer.valueOf(i3);
    }

    @Override // com.google.android.gms.internal.drive.zziw, java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzo(i, ((Integer) obj).intValue());
    }

    @Override // com.google.android.gms.internal.drive.zzkp
    public final /* synthetic */ zzkp<Integer> zzr(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new zzkl(Arrays.copyOf(this.zzsm, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(getInt(i));
    }

    static {
        zzkl zzklVar = new zzkl(new int[0], 0);
        zzsl = zzklVar;
        zzklVar.zzbp();
    }
}
