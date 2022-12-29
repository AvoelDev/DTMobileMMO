package com.facebook.internal;

import com.facebook.internal.FileLruCache;
import java.io.File;
import java.io.FilenameFilter;

/* compiled from: lambda */
/* renamed from: com.facebook.internal.-$$Lambda$FileLruCache$BufferFile$unX9NeGxjJ5DVb3-vL7tbz4CiEM  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FileLruCache$BufferFile$unX9NeGxjJ5DVb3vL7tbz4CiEM implements FilenameFilter {
    public static final /* synthetic */ $$Lambda$FileLruCache$BufferFile$unX9NeGxjJ5DVb3vL7tbz4CiEM INSTANCE = new $$Lambda$FileLruCache$BufferFile$unX9NeGxjJ5DVb3vL7tbz4CiEM();

    private /* synthetic */ $$Lambda$FileLruCache$BufferFile$unX9NeGxjJ5DVb3vL7tbz4CiEM() {
    }

    @Override // java.io.FilenameFilter
    public final boolean accept(File file, String str) {
        boolean m137filterExcludeNonBufferFiles$lambda1;
        m137filterExcludeNonBufferFiles$lambda1 = FileLruCache.BufferFile.m137filterExcludeNonBufferFiles$lambda1(file, str);
        return m137filterExcludeNonBufferFiles$lambda1;
    }
}
