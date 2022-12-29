package com.google.android.exoplayer2.upstream.cache;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.TreeSet;

/* loaded from: classes.dex */
final class CachedContent {
    private static final int VERSION_MAX = Integer.MAX_VALUE;
    private static final int VERSION_METADATA_INTRODUCED = 2;
    public final int id;
    public final String key;
    private boolean locked;
    private DefaultContentMetadata metadata = DefaultContentMetadata.EMPTY;
    private final TreeSet<SimpleCacheSpan> cachedSpans = new TreeSet<>();

    public static CachedContent readFromStream(int version, DataInputStream input) throws IOException {
        int id = input.readInt();
        String key = input.readUTF();
        CachedContent cachedContent = new CachedContent(id, key);
        if (version < 2) {
            long length = input.readLong();
            ContentMetadataMutations mutations = new ContentMetadataMutations();
            ContentMetadataInternal.setContentLength(mutations, length);
            cachedContent.applyMetadataMutations(mutations);
        } else {
            cachedContent.metadata = DefaultContentMetadata.readFromStream(input);
        }
        return cachedContent;
    }

    public CachedContent(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public void writeToStream(DataOutputStream output) throws IOException {
        output.writeInt(this.id);
        output.writeUTF(this.key);
        this.metadata.writeToStream(output);
    }

    public ContentMetadata getMetadata() {
        return this.metadata;
    }

    public boolean applyMetadataMutations(ContentMetadataMutations mutations) {
        DefaultContentMetadata oldMetadata = this.metadata;
        this.metadata = this.metadata.copyWithMutationsApplied(mutations);
        return !this.metadata.equals(oldMetadata);
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void addSpan(SimpleCacheSpan span) {
        this.cachedSpans.add(span);
    }

    public TreeSet<SimpleCacheSpan> getSpans() {
        return this.cachedSpans;
    }

    public SimpleCacheSpan getSpan(long position) {
        SimpleCacheSpan lookupSpan = SimpleCacheSpan.createLookup(this.key, position);
        SimpleCacheSpan floorSpan = this.cachedSpans.floor(lookupSpan);
        if (floorSpan == null || floorSpan.position + floorSpan.length <= position) {
            SimpleCacheSpan ceilSpan = this.cachedSpans.ceiling(lookupSpan);
            return ceilSpan == null ? SimpleCacheSpan.createOpenHole(this.key, position) : SimpleCacheSpan.createClosedHole(this.key, position, ceilSpan.position - position);
        }
        return floorSpan;
    }

    public long getCachedBytesLength(long position, long length) {
        SimpleCacheSpan span = getSpan(position);
        if (span.isHoleSpan()) {
            return -Math.min(span.isOpenEnded() ? Long.MAX_VALUE : span.length, length);
        }
        long queryEndPosition = position + length;
        long currentEndPosition = span.position + span.length;
        if (currentEndPosition < queryEndPosition) {
            for (SimpleCacheSpan next : this.cachedSpans.tailSet(span, false)) {
                if (next.position > currentEndPosition) {
                    break;
                }
                currentEndPosition = Math.max(currentEndPosition, next.position + next.length);
                if (currentEndPosition >= queryEndPosition) {
                    break;
                }
            }
        }
        return Math.min(currentEndPosition - position, length);
    }

    public SimpleCacheSpan touch(SimpleCacheSpan cacheSpan) throws Cache.CacheException {
        Assertions.checkState(this.cachedSpans.remove(cacheSpan));
        SimpleCacheSpan newCacheSpan = cacheSpan.copyWithUpdatedLastAccessTime(this.id);
        if (!cacheSpan.file.renameTo(newCacheSpan.file)) {
            throw new Cache.CacheException("Renaming of " + cacheSpan.file + " to " + newCacheSpan.file + " failed.");
        }
        this.cachedSpans.add(newCacheSpan);
        return newCacheSpan;
    }

    public boolean isEmpty() {
        return this.cachedSpans.isEmpty();
    }

    public boolean removeSpan(CacheSpan span) {
        if (this.cachedSpans.remove(span)) {
            span.file.delete();
            return true;
        }
        return false;
    }

    public int headerHashCode(int version) {
        int result = (this.id * 31) + this.key.hashCode();
        if (version < 2) {
            long length = ContentMetadataInternal.getContentLength(this.metadata);
            return (result * 31) + ((int) ((length >>> 32) ^ length));
        }
        return (result * 31) + this.metadata.hashCode();
    }

    public int hashCode() {
        int result = headerHashCode(Integer.MAX_VALUE);
        return (result * 31) + this.cachedSpans.hashCode();
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CachedContent that = (CachedContent) o;
        return this.id == that.id && this.key.equals(that.key) && this.cachedSpans.equals(that.cachedSpans) && this.metadata.equals(that.metadata);
    }
}
