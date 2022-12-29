package com.google.android.gms.internal.drive;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class zzmi<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzot;
    private final int zzvd;
    private List<zzmp> zzve;
    private Map<K, V> zzvf;
    private volatile zzmr zzvg;
    private Map<K, V> zzvh;
    private volatile zzml zzvi;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <FieldDescriptorType extends zzkd<FieldDescriptorType>> zzmi<FieldDescriptorType, Object> zzav(int i) {
        return new zzmj(i);
    }

    private zzmi(int i) {
        this.zzvd = i;
        this.zzve = Collections.emptyList();
        this.zzvf = Collections.emptyMap();
        this.zzvh = Collections.emptyMap();
    }

    public void zzbp() {
        Map<K, V> unmodifiableMap;
        Map<K, V> unmodifiableMap2;
        if (this.zzot) {
            return;
        }
        if (this.zzvf.isEmpty()) {
            unmodifiableMap = Collections.emptyMap();
        } else {
            unmodifiableMap = Collections.unmodifiableMap(this.zzvf);
        }
        this.zzvf = unmodifiableMap;
        if (this.zzvh.isEmpty()) {
            unmodifiableMap2 = Collections.emptyMap();
        } else {
            unmodifiableMap2 = Collections.unmodifiableMap(this.zzvh);
        }
        this.zzvh = unmodifiableMap2;
        this.zzot = true;
    }

    public final boolean isImmutable() {
        return this.zzot;
    }

    public final int zzer() {
        return this.zzve.size();
    }

    public final Map.Entry<K, V> zzaw(int i) {
        return this.zzve.get(i);
    }

    public final Iterable<Map.Entry<K, V>> zzes() {
        if (this.zzvf.isEmpty()) {
            return zzmm.zzex();
        }
        return this.zzvf.entrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzve.size() + this.zzvf.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza((zzmi<K, V>) comparable) >= 0 || this.zzvf.containsKey(comparable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza((zzmi<K, V>) comparable);
        if (zza >= 0) {
            return (V) this.zzve.get(zza).getValue();
        }
        return this.zzvf.get(comparable);
    }

    public final V zza(K k, V v) {
        zzeu();
        int zza = zza((zzmi<K, V>) k);
        if (zza >= 0) {
            return (V) this.zzve.get(zza).setValue(v);
        }
        zzeu();
        if (this.zzve.isEmpty() && !(this.zzve instanceof ArrayList)) {
            this.zzve = new ArrayList(this.zzvd);
        }
        int i = -(zza + 1);
        if (i >= this.zzvd) {
            return zzev().put(k, v);
        }
        int size = this.zzve.size();
        int i2 = this.zzvd;
        if (size == i2) {
            zzmp remove = this.zzve.remove(i2 - 1);
            zzev().put((K) remove.getKey(), (V) remove.getValue());
        }
        this.zzve.add(i, new zzmp(this, k, v));
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzeu();
        if (!this.zzve.isEmpty()) {
            this.zzve.clear();
        }
        if (this.zzvf.isEmpty()) {
            return;
        }
        this.zzvf.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzeu();
        Comparable comparable = (Comparable) obj;
        int zza = zza((zzmi<K, V>) comparable);
        if (zza >= 0) {
            return (V) zzax(zza);
        }
        if (this.zzvf.isEmpty()) {
            return null;
        }
        return this.zzvf.remove(comparable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzax(int i) {
        zzeu();
        V v = (V) this.zzve.remove(i).getValue();
        if (!this.zzvf.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzev().entrySet().iterator();
            this.zzve.add(new zzmp(this, it.next()));
            it.remove();
        }
        return v;
    }

    private final int zza(K k) {
        int size = this.zzve.size() - 1;
        if (size >= 0) {
            int compareTo = k.compareTo((Comparable) this.zzve.get(size).getKey());
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            int compareTo2 = k.compareTo((Comparable) this.zzve.get(i2).getKey());
            if (compareTo2 < 0) {
                size = i2 - 1;
            } else if (compareTo2 <= 0) {
                return i2;
            } else {
                i = i2 + 1;
            }
        }
        return -(i + 1);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zzvg == null) {
            this.zzvg = new zzmr(this, null);
        }
        return this.zzvg;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Set<Map.Entry<K, V>> zzet() {
        if (this.zzvi == null) {
            this.zzvi = new zzml(this, null);
        }
        return this.zzvi;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzeu() {
        if (this.zzot) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzev() {
        zzeu();
        if (this.zzvf.isEmpty() && !(this.zzvf instanceof TreeMap)) {
            this.zzvf = new TreeMap();
            this.zzvh = ((TreeMap) this.zzvf).descendingMap();
        }
        return (SortedMap) this.zzvf;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzmi)) {
            return super.equals(obj);
        }
        zzmi zzmiVar = (zzmi) obj;
        int size = size();
        if (size != zzmiVar.size()) {
            return false;
        }
        int zzer = zzer();
        if (zzer != zzmiVar.zzer()) {
            return entrySet().equals(zzmiVar.entrySet());
        }
        for (int i = 0; i < zzer; i++) {
            if (!zzaw(i).equals(zzmiVar.zzaw(i))) {
                return false;
            }
        }
        if (zzer != size) {
            return this.zzvf.equals(zzmiVar.zzvf);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int zzer = zzer();
        int i = 0;
        for (int i2 = 0; i2 < zzer; i2++) {
            i += this.zzve.get(i2).hashCode();
        }
        return this.zzvf.size() > 0 ? i + this.zzvf.hashCode() : i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((zzmi<K, V>) ((Comparable) obj), (Comparable) obj2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzmi(int i, zzmj zzmjVar) {
        this(i);
    }
}
