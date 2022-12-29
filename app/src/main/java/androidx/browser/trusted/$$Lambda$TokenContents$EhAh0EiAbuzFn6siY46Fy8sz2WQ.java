package androidx.browser.trusted;

import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: androidx.browser.trusted.-$$Lambda$TokenContents$EhAh0EiAbuzFn6siY46Fy8sz2WQ  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$TokenContents$EhAh0EiAbuzFn6siY46Fy8sz2WQ implements Comparator {
    public static final /* synthetic */ $$Lambda$TokenContents$EhAh0EiAbuzFn6siY46Fy8sz2WQ INSTANCE = new $$Lambda$TokenContents$EhAh0EiAbuzFn6siY46Fy8sz2WQ();

    private /* synthetic */ $$Lambda$TokenContents$EhAh0EiAbuzFn6siY46Fy8sz2WQ() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int compareByteArrays;
        compareByteArrays = TokenContents.compareByteArrays((byte[]) obj, (byte[]) obj2);
        return compareByteArrays;
    }
}
