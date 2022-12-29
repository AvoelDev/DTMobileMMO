package com.facebook.internal;

import com.facebook.internal.FileLruCache;
import java.io.File;
import java.io.FilenameFilter;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.-$$Lambda$FileLruCache$BufferFile$Uwf3BLxD-u3AhwSmJosUeq5paAY  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FileLruCache$BufferFile$Uwf3BLxDu3AhwSmJosUeq5paAY implements FilenameFilter {
    public static final /* synthetic */ $$Lambda$FileLruCache$BufferFile$Uwf3BLxDu3AhwSmJosUeq5paAY INSTANCE = new $$Lambda$FileLruCache$BufferFile$Uwf3BLxDu3AhwSmJosUeq5paAY();

    private /* synthetic */ $$Lambda$FileLruCache$BufferFile$Uwf3BLxDu3AhwSmJosUeq5paAY() {
    }

    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        boolean m136filterExcludeBufferFiles$lambda0;
        m136filterExcludeBufferFiles$lambda0 = FileLruCache.BufferFile.m136filterExcludeBufferFiles$lambda0(file, str);
        return m136filterExcludeBufferFiles$lambda0;
    }
}
