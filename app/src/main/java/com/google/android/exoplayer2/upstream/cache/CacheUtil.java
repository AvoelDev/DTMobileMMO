package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.util.NavigableSet;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class CacheUtil {
    public static final int DEFAULT_BUFFER_SIZE_BYTES = 131072;

    /* loaded from: classes.dex */
    public static class CachingCounters {
        public volatile long alreadyCachedBytes;
        public volatile long contentLength = -1;
        public volatile long newlyCachedBytes;

        public long totalCachedBytes() {
            return this.alreadyCachedBytes + this.newlyCachedBytes;
        }
    }

    public static String generateKey(Uri uri) {
        return uri.toString();
    }

    public static String getKey(DataSpec dataSpec) {
        return dataSpec.key != null ? dataSpec.key : generateKey(dataSpec.uri);
    }

    public static void getCached(DataSpec dataSpec, Cache cache, CachingCounters counters) {
        String key = getKey(dataSpec);
        long start = dataSpec.absoluteStreamPosition;
        long left = dataSpec.length != -1 ? dataSpec.length : cache.getContentLength(key);
        counters.contentLength = left;
        counters.alreadyCachedBytes = 0L;
        counters.newlyCachedBytes = 0L;
        while (left != 0) {
            long blockLength = cache.getCachedLength(key, start, left != -1 ? left : Long.MAX_VALUE);
            if (blockLength > 0) {
                counters.alreadyCachedBytes += blockLength;
            } else {
                blockLength = -blockLength;
                if (blockLength == Long.MAX_VALUE) {
                    return;
                }
            }
            start += blockLength;
            if (left == -1) {
                blockLength = 0;
            }
            left -= blockLength;
        }
    }

    public static void cache(DataSpec dataSpec, Cache cache, DataSource upstream, @Nullable CachingCounters counters, @Nullable AtomicBoolean isCanceled) throws IOException, InterruptedException {
        cache(dataSpec, cache, new CacheDataSource(cache, upstream), new byte[131072], null, 0, counters, isCanceled, false);
    }

    public static void cache(DataSpec dataSpec, Cache cache, CacheDataSource dataSource, byte[] buffer, PriorityTaskManager priorityTaskManager, int priority, @Nullable CachingCounters counters, @Nullable AtomicBoolean isCanceled, boolean enableEOFException) throws IOException, InterruptedException {
        Assertions.checkNotNull(dataSource);
        Assertions.checkNotNull(buffer);
        if (counters != null) {
            getCached(dataSpec, cache, counters);
        } else {
            counters = new CachingCounters();
        }
        String key = getKey(dataSpec);
        long start = dataSpec.absoluteStreamPosition;
        long left = dataSpec.length != -1 ? dataSpec.length : cache.getContentLength(key);
        while (left != 0) {
            if (isCanceled != null && isCanceled.get()) {
                throw new InterruptedException();
            }
            long blockLength = cache.getCachedLength(key, start, left != -1 ? left : Long.MAX_VALUE);
            if (blockLength <= 0) {
                blockLength = -blockLength;
                long read = readAndDiscard(dataSpec, start, blockLength, dataSource, buffer, priorityTaskManager, priority, counters);
                if (read < blockLength) {
                    if (enableEOFException && left != -1) {
                        throw new EOFException();
                    }
                    return;
                }
            }
            start += blockLength;
            if (left == -1) {
                blockLength = 0;
            }
            left -= blockLength;
        }
    }

    private static long readAndDiscard(DataSpec dataSpec, long absoluteStreamPosition, long length, DataSource dataSource, byte[] buffer, PriorityTaskManager priorityTaskManager, int priority, CachingCounters counters) throws IOException, InterruptedException {
        DataSpec dataSpec2;
        DataSpec dataSpec3 = dataSpec;
        while (true) {
            if (priorityTaskManager != null) {
                priorityTaskManager.proceed(priority);
            }
            try {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                dataSpec2 = new DataSpec(dataSpec3.uri, dataSpec3.postBody, absoluteStreamPosition, (dataSpec3.position + absoluteStreamPosition) - dataSpec3.absoluteStreamPosition, -1L, dataSpec3.key, dataSpec3.flags | 2);
                try {
                    long resolvedLength = dataSource.open(dataSpec2);
                    if (counters.contentLength == -1 && resolvedLength != -1) {
                        counters.contentLength = dataSpec2.absoluteStreamPosition + resolvedLength;
                    }
                    long totalRead = 0;
                    while (true) {
                        if (totalRead == length) {
                            break;
                        } else if (Thread.interrupted()) {
                            throw new InterruptedException();
                        } else {
                            int read = dataSource.read(buffer, 0, length != -1 ? (int) Math.min(buffer.length, length - totalRead) : buffer.length);
                            if (read == -1) {
                                if (counters.contentLength == -1) {
                                    counters.contentLength = dataSpec2.absoluteStreamPosition + totalRead;
                                }
                            } else {
                                totalRead += read;
                                counters.newlyCachedBytes += read;
                            }
                        }
                    }
                    Util.closeQuietly(dataSource);
                    return totalRead;
                } catch (PriorityTaskManager.PriorityTooLowException e) {
                } catch (Throwable th) {
                    th = th;
                    Util.closeQuietly(dataSource);
                    throw th;
                }
            } catch (PriorityTaskManager.PriorityTooLowException e2) {
                dataSpec2 = dataSpec3;
            } catch (Throwable th2) {
                th = th2;
            }
            Util.closeQuietly(dataSource);
            dataSpec3 = dataSpec2;
        }
    }

    public static void remove(Cache cache, String key) {
        NavigableSet<CacheSpan> cachedSpans = cache.getCachedSpans(key);
        for (CacheSpan cachedSpan : cachedSpans) {
            try {
                cache.removeSpan(cachedSpan);
            } catch (Cache.CacheException e) {
            }
        }
    }

    private CacheUtil() {
    }
}
