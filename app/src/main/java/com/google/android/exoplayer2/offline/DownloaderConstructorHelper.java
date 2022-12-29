package com.google.android.exoplayer2.offline;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DummyDataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.PriorityDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;

/* loaded from: classes.dex */
public final class DownloaderConstructorHelper {
    private final Cache cache;
    private final DataSource.Factory cacheReadDataSourceFactory;
    private final DataSink.Factory cacheWriteDataSinkFactory;
    private final PriorityTaskManager priorityTaskManager;
    private final DataSource.Factory upstreamDataSourceFactory;

    public DownloaderConstructorHelper(Cache cache, DataSource.Factory upstreamDataSourceFactory) {
        this(cache, upstreamDataSourceFactory, null, null, null);
    }

    public DownloaderConstructorHelper(Cache cache, DataSource.Factory upstreamDataSourceFactory, @Nullable DataSource.Factory cacheReadDataSourceFactory, @Nullable DataSink.Factory cacheWriteDataSinkFactory, @Nullable PriorityTaskManager priorityTaskManager) {
        Assertions.checkNotNull(upstreamDataSourceFactory);
        this.cache = cache;
        this.upstreamDataSourceFactory = upstreamDataSourceFactory;
        this.cacheReadDataSourceFactory = cacheReadDataSourceFactory;
        this.cacheWriteDataSinkFactory = cacheWriteDataSinkFactory;
        this.priorityTaskManager = priorityTaskManager;
    }

    public Cache getCache() {
        return this.cache;
    }

    public PriorityTaskManager getPriorityTaskManager() {
        return this.priorityTaskManager != null ? this.priorityTaskManager : new PriorityTaskManager();
    }

    public CacheDataSource buildCacheDataSource(boolean offline) {
        DataSource cacheReadDataSource = this.cacheReadDataSourceFactory != null ? this.cacheReadDataSourceFactory.createDataSource() : new FileDataSource();
        if (offline) {
            return new CacheDataSource(this.cache, DummyDataSource.INSTANCE, cacheReadDataSource, null, 1, null);
        }
        DataSink cacheWriteDataSink = this.cacheWriteDataSinkFactory != null ? this.cacheWriteDataSinkFactory.createDataSink() : new CacheDataSink(this.cache, 2097152L);
        DataSource upstream = this.upstreamDataSourceFactory.createDataSource();
        if (this.priorityTaskManager != null) {
            upstream = new PriorityDataSource(upstream, this.priorityTaskManager, -1000);
        }
        return new CacheDataSource(this.cache, upstream, cacheReadDataSource, cacheWriteDataSink, 1, null);
    }
}
