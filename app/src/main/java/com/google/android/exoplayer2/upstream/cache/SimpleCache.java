package com.google.android.exoplayer2.upstream.cache;

import android.os.ConditionVariable;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes.dex */
public final class SimpleCache implements Cache {
    private static final String TAG = "SimpleCache";
    private static boolean cacheFolderLockingDisabled;
    private static final HashSet<File> lockedCacheDirs = new HashSet<>();
    private final File cacheDir;
    private final CacheEvictor evictor;
    private final CachedContentIndex index;
    private final HashMap<String, ArrayList<Cache.Listener>> listeners;
    private boolean released;
    private long totalSpace;

    public static synchronized boolean isCacheFolderLocked(File cacheFolder) {
        boolean contains;
        synchronized (SimpleCache.class) {
            contains = lockedCacheDirs.contains(cacheFolder.getAbsoluteFile());
        }
        return contains;
    }

    @Deprecated
    public static synchronized void disableCacheFolderLocking() {
        synchronized (SimpleCache.class) {
            cacheFolderLockingDisabled = true;
            lockedCacheDirs.clear();
        }
    }

    public SimpleCache(File cacheDir, CacheEvictor evictor) {
        this(cacheDir, evictor, null, false);
    }

    public SimpleCache(File cacheDir, CacheEvictor evictor, byte[] secretKey) {
        this(cacheDir, evictor, secretKey, secretKey != null);
    }

    public SimpleCache(File cacheDir, CacheEvictor evictor, byte[] secretKey, boolean encrypt) {
        this(cacheDir, evictor, new CachedContentIndex(cacheDir, secretKey, encrypt));
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.google.android.exoplayer2.upstream.cache.SimpleCache$1] */
    SimpleCache(File cacheDir, CacheEvictor evictor, CachedContentIndex index) {
        if (!lockFolder(cacheDir)) {
            throw new IllegalStateException("Another SimpleCache instance uses the folder: " + cacheDir);
        }
        this.cacheDir = cacheDir;
        this.evictor = evictor;
        this.index = index;
        this.listeners = new HashMap<>();
        final ConditionVariable conditionVariable = new ConditionVariable();
        new Thread("SimpleCache.initialize()") { // from class: com.google.android.exoplayer2.upstream.cache.SimpleCache.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (SimpleCache.this) {
                    conditionVariable.open();
                    SimpleCache.this.initialize();
                    SimpleCache.this.evictor.onCacheInitialized();
                }
            }
        }.start();
        conditionVariable.block();
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void release() throws Cache.CacheException {
        if (!this.released) {
            this.listeners.clear();
            removeStaleSpansAndCachedContents();
            unlockFolder(this.cacheDir);
            this.released = true;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized NavigableSet<CacheSpan> addListener(String key, Cache.Listener listener) {
        Assertions.checkState(!this.released);
        ArrayList<Cache.Listener> listenersForKey = this.listeners.get(key);
        if (listenersForKey == null) {
            listenersForKey = new ArrayList<>();
            this.listeners.put(key, listenersForKey);
        }
        listenersForKey.add(listener);
        return getCachedSpans(key);
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void removeListener(String key, Cache.Listener listener) {
        ArrayList<Cache.Listener> listenersForKey;
        if (!this.released && (listenersForKey = this.listeners.get(key)) != null) {
            listenersForKey.remove(listener);
            if (listenersForKey.isEmpty()) {
                this.listeners.remove(key);
            }
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    @NonNull
    public synchronized NavigableSet<CacheSpan> getCachedSpans(String key) {
        CachedContent cachedContent;
        Assertions.checkState(!this.released);
        cachedContent = this.index.get(key);
        return (cachedContent == null || cachedContent.isEmpty()) ? new TreeSet() : new TreeSet((Collection) cachedContent.getSpans());
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized Set<String> getKeys() {
        Assertions.checkState(!this.released);
        return new HashSet(this.index.getKeys());
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getCacheSpace() {
        Assertions.checkState(!this.released);
        return this.totalSpace;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized SimpleCacheSpan startReadWrite(String key, long position) throws InterruptedException, Cache.CacheException {
        SimpleCacheSpan span;
        while (true) {
            span = startReadWriteNonBlocking(key, position);
            if (span == null) {
                wait();
            }
        }
        return span;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized SimpleCacheSpan startReadWriteNonBlocking(String key, long position) throws Cache.CacheException {
        SimpleCacheSpan newCacheSpan;
        synchronized (this) {
            Assertions.checkState(this.released ? false : true);
            SimpleCacheSpan cacheSpan = getSpan(key, position);
            if (cacheSpan.isCached) {
                newCacheSpan = this.index.get(key).touch(cacheSpan);
                notifySpanTouched(cacheSpan, newCacheSpan);
            } else {
                CachedContent cachedContent = this.index.getOrAdd(key);
                if (!cachedContent.isLocked()) {
                    cachedContent.setLocked(true);
                    newCacheSpan = cacheSpan;
                } else {
                    newCacheSpan = null;
                }
            }
        }
        return newCacheSpan;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized File startFile(String key, long position, long maxLength) throws Cache.CacheException {
        CachedContent cachedContent;
        Assertions.checkState(!this.released);
        cachedContent = this.index.get(key);
        Assertions.checkNotNull(cachedContent);
        Assertions.checkState(cachedContent.isLocked());
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdirs();
            removeStaleSpansAndCachedContents();
        }
        this.evictor.onStartFile(this, key, position, maxLength);
        return SimpleCacheSpan.getCacheFile(this.cacheDir, cachedContent.id, position, System.currentTimeMillis());
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void commitFile(File file) throws Cache.CacheException {
        synchronized (this) {
            Assertions.checkState(!this.released);
            SimpleCacheSpan span = SimpleCacheSpan.createCacheEntry(file, this.index);
            Assertions.checkState(span != null);
            CachedContent cachedContent = this.index.get(span.key);
            Assertions.checkNotNull(cachedContent);
            Assertions.checkState(cachedContent.isLocked());
            if (file.exists()) {
                if (file.length() == 0) {
                    file.delete();
                } else {
                    long length = ContentMetadataInternal.getContentLength(cachedContent.getMetadata());
                    if (length != -1) {
                        Assertions.checkState(span.position + span.length <= length);
                    }
                    addSpan(span);
                    this.index.store();
                    notifyAll();
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void releaseHoleSpan(CacheSpan holeSpan) {
        synchronized (this) {
            Assertions.checkState(this.released ? false : true);
            CachedContent cachedContent = this.index.get(holeSpan.key);
            Assertions.checkNotNull(cachedContent);
            Assertions.checkState(cachedContent.isLocked());
            cachedContent.setLocked(false);
            this.index.maybeRemove(cachedContent.key);
            notifyAll();
        }
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void removeSpan(CacheSpan span) throws Cache.CacheException {
        synchronized (this) {
            Assertions.checkState(this.released ? false : true);
            removeSpan(span, true);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0019, code lost:
        if (r0.getCachedBytesLength(r8, r10) >= r10) goto L12;
     */
    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isCached(java.lang.String r7, long r8, long r10) {
        /*
            r6 = this;
            r1 = 1
            r2 = 0
            monitor-enter(r6)
            boolean r3 = r6.released     // Catch: java.lang.Throwable -> L21
            if (r3 != 0) goto L1d
            r3 = r1
        L8:
            com.google.android.exoplayer2.util.Assertions.checkState(r3)     // Catch: java.lang.Throwable -> L21
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex r3 = r6.index     // Catch: java.lang.Throwable -> L21
            com.google.android.exoplayer2.upstream.cache.CachedContent r0 = r3.get(r7)     // Catch: java.lang.Throwable -> L21
            if (r0 == 0) goto L1f
            long r4 = r0.getCachedBytesLength(r8, r10)     // Catch: java.lang.Throwable -> L21
            int r3 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r3 < 0) goto L1f
        L1b:
            monitor-exit(r6)
            return r1
        L1d:
            r3 = r2
            goto L8
        L1f:
            r1 = r2
            goto L1b
        L21:
            r1 = move-exception
            monitor-exit(r6)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.isCached(java.lang.String, long, long):boolean");
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getCachedLength(String key, long position, long length) {
        CachedContent cachedContent;
        Assertions.checkState(!this.released);
        cachedContent = this.index.get(key);
        return cachedContent != null ? cachedContent.getCachedBytesLength(position, length) : -length;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void setContentLength(String key, long length) throws Cache.CacheException {
        ContentMetadataMutations mutations = new ContentMetadataMutations();
        ContentMetadataInternal.setContentLength(mutations, length);
        applyContentMetadataMutations(key, mutations);
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized long getContentLength(String key) {
        return ContentMetadataInternal.getContentLength(getContentMetadata(key));
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized void applyContentMetadataMutations(String key, ContentMetadataMutations mutations) throws Cache.CacheException {
        Assertions.checkState(!this.released);
        this.index.applyContentMetadataMutations(key, mutations);
        this.index.store();
    }

    @Override // com.google.android.exoplayer2.upstream.cache.Cache
    public synchronized ContentMetadata getContentMetadata(String key) {
        Assertions.checkState(!this.released);
        return this.index.getContentMetadata(key);
    }

    private SimpleCacheSpan getSpan(String key, long position) throws Cache.CacheException {
        CachedContent cachedContent = this.index.get(key);
        if (cachedContent == null) {
            return SimpleCacheSpan.createOpenHole(key, position);
        }
        while (true) {
            SimpleCacheSpan span = cachedContent.getSpan(position);
            if (span.isCached && !span.file.exists()) {
                removeStaleSpansAndCachedContents();
            } else {
                return span;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initialize() {
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdirs();
            return;
        }
        this.index.load();
        File[] files = this.cacheDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.getName().equals(CachedContentIndex.FILE_NAME)) {
                    SimpleCacheSpan span = file.length() > 0 ? SimpleCacheSpan.createCacheEntry(file, this.index) : null;
                    if (span != null) {
                        addSpan(span);
                    } else {
                        file.delete();
                    }
                }
            }
            this.index.removeEmpty();
            try {
                this.index.store();
            } catch (Cache.CacheException e) {
                Log.e(TAG, "Storing index file failed", e);
            }
        }
    }

    private void addSpan(SimpleCacheSpan span) {
        this.index.getOrAdd(span.key).addSpan(span);
        this.totalSpace += span.length;
        notifySpanAdded(span);
    }

    private void removeSpan(CacheSpan span, boolean removeEmptyCachedContent) throws Cache.CacheException {
        CachedContent cachedContent = this.index.get(span.key);
        if (cachedContent != null && cachedContent.removeSpan(span)) {
            this.totalSpace -= span.length;
            if (removeEmptyCachedContent) {
                try {
                    this.index.maybeRemove(cachedContent.key);
                    this.index.store();
                } finally {
                    notifySpanRemoved(span);
                }
            }
        }
    }

    private void removeStaleSpansAndCachedContents() throws Cache.CacheException {
        ArrayList<CacheSpan> spansToBeRemoved = new ArrayList<>();
        for (CachedContent cachedContent : this.index.getAll()) {
            Iterator<SimpleCacheSpan> it = cachedContent.getSpans().iterator();
            while (it.hasNext()) {
                CacheSpan span = it.next();
                if (!span.file.exists()) {
                    spansToBeRemoved.add(span);
                }
            }
        }
        for (int i = 0; i < spansToBeRemoved.size(); i++) {
            removeSpan(spansToBeRemoved.get(i), false);
        }
        this.index.removeEmpty();
        this.index.store();
    }

    private void notifySpanRemoved(CacheSpan span) {
        ArrayList<Cache.Listener> keyListeners = this.listeners.get(span.key);
        if (keyListeners != null) {
            for (int i = keyListeners.size() - 1; i >= 0; i--) {
                keyListeners.get(i).onSpanRemoved(this, span);
            }
        }
        this.evictor.onSpanRemoved(this, span);
    }

    private void notifySpanAdded(SimpleCacheSpan span) {
        ArrayList<Cache.Listener> keyListeners = this.listeners.get(span.key);
        if (keyListeners != null) {
            for (int i = keyListeners.size() - 1; i >= 0; i--) {
                keyListeners.get(i).onSpanAdded(this, span);
            }
        }
        this.evictor.onSpanAdded(this, span);
    }

    private void notifySpanTouched(SimpleCacheSpan oldSpan, CacheSpan newSpan) {
        ArrayList<Cache.Listener> keyListeners = this.listeners.get(oldSpan.key);
        if (keyListeners != null) {
            for (int i = keyListeners.size() - 1; i >= 0; i--) {
                keyListeners.get(i).onSpanTouched(this, oldSpan, newSpan);
            }
        }
        this.evictor.onSpanTouched(this, oldSpan, newSpan);
    }

    private static synchronized boolean lockFolder(File cacheDir) {
        boolean add;
        synchronized (SimpleCache.class) {
            add = cacheFolderLockingDisabled ? true : lockedCacheDirs.add(cacheDir.getAbsoluteFile());
        }
        return add;
    }

    private static synchronized void unlockFolder(File cacheDir) {
        synchronized (SimpleCache.class) {
            if (!cacheFolderLockingDisabled) {
                lockedCacheDirs.remove(cacheDir.getAbsoluteFile());
            }
        }
    }
}
