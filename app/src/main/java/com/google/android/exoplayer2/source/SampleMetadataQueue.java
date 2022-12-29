package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class SampleMetadataQueue {
    private static final int SAMPLE_CAPACITY_INCREMENT = 1000;
    private int absoluteFirstIndex;
    private int length;
    private int readPosition;
    private int relativeFirstIndex;
    private Format upstreamFormat;
    private int upstreamSourceId;
    private int capacity = 1000;
    private int[] sourceIds = new int[this.capacity];
    private long[] offsets = new long[this.capacity];
    private long[] timesUs = new long[this.capacity];
    private int[] flags = new int[this.capacity];
    private int[] sizes = new int[this.capacity];
    private TrackOutput.CryptoData[] cryptoDatas = new TrackOutput.CryptoData[this.capacity];
    private Format[] formats = new Format[this.capacity];
    private long largestDiscardedTimestampUs = Long.MIN_VALUE;
    private long largestQueuedTimestampUs = Long.MIN_VALUE;
    private boolean upstreamFormatRequired = true;
    private boolean upstreamKeyframeRequired = true;

    /* loaded from: classes.dex */
    public static final class SampleExtrasHolder {
        public TrackOutput.CryptoData cryptoData;
        public long offset;
        public int size;
    }

    public void reset(boolean resetUpstreamFormat) {
        this.length = 0;
        this.absoluteFirstIndex = 0;
        this.relativeFirstIndex = 0;
        this.readPosition = 0;
        this.upstreamKeyframeRequired = true;
        this.largestDiscardedTimestampUs = Long.MIN_VALUE;
        this.largestQueuedTimestampUs = Long.MIN_VALUE;
        if (resetUpstreamFormat) {
            this.upstreamFormat = null;
            this.upstreamFormatRequired = true;
        }
    }

    public int getWriteIndex() {
        return this.absoluteFirstIndex + this.length;
    }

    public long discardUpstreamSamples(int discardFromIndex) {
        int discardCount = getWriteIndex() - discardFromIndex;
        Assertions.checkArgument(discardCount >= 0 && discardCount <= this.length - this.readPosition);
        this.length -= discardCount;
        this.largestQueuedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(this.length));
        if (this.length == 0) {
            return 0L;
        }
        int relativeLastWriteIndex = getRelativeIndex(this.length - 1);
        return this.offsets[relativeLastWriteIndex] + this.sizes[relativeLastWriteIndex];
    }

    public void sourceId(int sourceId) {
        this.upstreamSourceId = sourceId;
    }

    public int getFirstIndex() {
        return this.absoluteFirstIndex;
    }

    public int getReadIndex() {
        return this.absoluteFirstIndex + this.readPosition;
    }

    public int peekSourceId() {
        int relativeReadIndex = getRelativeIndex(this.readPosition);
        return hasNextSample() ? this.sourceIds[relativeReadIndex] : this.upstreamSourceId;
    }

    public synchronized boolean hasNextSample() {
        return this.readPosition != this.length;
    }

    public synchronized Format getUpstreamFormat() {
        return this.upstreamFormatRequired ? null : this.upstreamFormat;
    }

    public synchronized long getLargestQueuedTimestampUs() {
        return this.largestQueuedTimestampUs;
    }

    public synchronized long getFirstTimestampUs() {
        return this.length == 0 ? Long.MIN_VALUE : this.timesUs[this.relativeFirstIndex];
    }

    public synchronized void rewind() {
        this.readPosition = 0;
    }

    public synchronized int read(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired, boolean loadingFinished, Format downstreamFormat, SampleExtrasHolder extrasHolder) {
        int i = -4;
        synchronized (this) {
            if (!hasNextSample()) {
                if (loadingFinished) {
                    buffer.setFlags(4);
                } else if (this.upstreamFormat == null || (!formatRequired && this.upstreamFormat == downstreamFormat)) {
                    i = -3;
                } else {
                    formatHolder.format = this.upstreamFormat;
                    i = -5;
                }
            } else {
                int relativeReadIndex = getRelativeIndex(this.readPosition);
                if (formatRequired || this.formats[relativeReadIndex] != downstreamFormat) {
                    formatHolder.format = this.formats[relativeReadIndex];
                    i = -5;
                } else if (buffer.isFlagsOnly()) {
                    i = -3;
                } else {
                    buffer.timeUs = this.timesUs[relativeReadIndex];
                    buffer.setFlags(this.flags[relativeReadIndex]);
                    extrasHolder.size = this.sizes[relativeReadIndex];
                    extrasHolder.offset = this.offsets[relativeReadIndex];
                    extrasHolder.cryptoData = this.cryptoDatas[relativeReadIndex];
                    this.readPosition++;
                }
            }
        }
        return i;
    }

    public synchronized int advanceTo(long timeUs, boolean toKeyframe, boolean allowTimeBeyondBuffer) {
        int offset;
        int relativeReadIndex = getRelativeIndex(this.readPosition);
        if (!hasNextSample() || timeUs < this.timesUs[relativeReadIndex] || (timeUs > this.largestQueuedTimestampUs && !allowTimeBeyondBuffer)) {
            offset = -1;
        } else {
            offset = findSampleBefore(relativeReadIndex, this.length - this.readPosition, timeUs, toKeyframe);
            if (offset == -1) {
                offset = -1;
            } else {
                this.readPosition += offset;
            }
        }
        return offset;
    }

    public synchronized int advanceToEnd() {
        int skipCount;
        skipCount = this.length - this.readPosition;
        this.readPosition = this.length;
        return skipCount;
    }

    public synchronized boolean setReadPosition(int sampleIndex) {
        boolean z;
        if (this.absoluteFirstIndex > sampleIndex || sampleIndex > this.absoluteFirstIndex + this.length) {
            z = false;
        } else {
            this.readPosition = sampleIndex - this.absoluteFirstIndex;
            z = true;
        }
        return z;
    }

    public synchronized long discardTo(long timeUs, boolean toKeyframe, boolean stopAtReadPosition) {
        long j;
        if (this.length == 0 || timeUs < this.timesUs[this.relativeFirstIndex]) {
            j = -1;
        } else {
            int searchLength = (!stopAtReadPosition || this.readPosition == this.length) ? this.length : this.readPosition + 1;
            int discardCount = findSampleBefore(this.relativeFirstIndex, searchLength, timeUs, toKeyframe);
            j = discardCount == -1 ? -1L : discardSamples(discardCount);
        }
        return j;
    }

    public synchronized long discardToRead() {
        return this.readPosition == 0 ? -1L : discardSamples(this.readPosition);
    }

    public synchronized long discardToEnd() {
        return this.length == 0 ? -1L : discardSamples(this.length);
    }

    public synchronized boolean format(Format format) {
        boolean z = false;
        synchronized (this) {
            if (format == null) {
                this.upstreamFormatRequired = true;
            } else {
                this.upstreamFormatRequired = false;
                if (!Util.areEqual(format, this.upstreamFormat)) {
                    this.upstreamFormat = format;
                    z = true;
                }
            }
        }
        return z;
    }

    public synchronized void commitSample(long timeUs, int sampleFlags, long offset, int size, TrackOutput.CryptoData cryptoData) {
        if (this.upstreamKeyframeRequired) {
            if ((sampleFlags & 1) != 0) {
                this.upstreamKeyframeRequired = false;
            }
        }
        Assertions.checkState(!this.upstreamFormatRequired);
        commitSampleTimestamp(timeUs);
        int relativeEndIndex = getRelativeIndex(this.length);
        this.timesUs[relativeEndIndex] = timeUs;
        this.offsets[relativeEndIndex] = offset;
        this.sizes[relativeEndIndex] = size;
        this.flags[relativeEndIndex] = sampleFlags;
        this.cryptoDatas[relativeEndIndex] = cryptoData;
        this.formats[relativeEndIndex] = this.upstreamFormat;
        this.sourceIds[relativeEndIndex] = this.upstreamSourceId;
        this.length++;
        if (this.length == this.capacity) {
            int newCapacity = this.capacity + 1000;
            int[] newSourceIds = new int[newCapacity];
            long[] newOffsets = new long[newCapacity];
            long[] newTimesUs = new long[newCapacity];
            int[] newFlags = new int[newCapacity];
            int[] newSizes = new int[newCapacity];
            TrackOutput.CryptoData[] newCryptoDatas = new TrackOutput.CryptoData[newCapacity];
            Format[] newFormats = new Format[newCapacity];
            int beforeWrap = this.capacity - this.relativeFirstIndex;
            System.arraycopy(this.offsets, this.relativeFirstIndex, newOffsets, 0, beforeWrap);
            System.arraycopy(this.timesUs, this.relativeFirstIndex, newTimesUs, 0, beforeWrap);
            System.arraycopy(this.flags, this.relativeFirstIndex, newFlags, 0, beforeWrap);
            System.arraycopy(this.sizes, this.relativeFirstIndex, newSizes, 0, beforeWrap);
            System.arraycopy(this.cryptoDatas, this.relativeFirstIndex, newCryptoDatas, 0, beforeWrap);
            System.arraycopy(this.formats, this.relativeFirstIndex, newFormats, 0, beforeWrap);
            System.arraycopy(this.sourceIds, this.relativeFirstIndex, newSourceIds, 0, beforeWrap);
            int afterWrap = this.relativeFirstIndex;
            System.arraycopy(this.offsets, 0, newOffsets, beforeWrap, afterWrap);
            System.arraycopy(this.timesUs, 0, newTimesUs, beforeWrap, afterWrap);
            System.arraycopy(this.flags, 0, newFlags, beforeWrap, afterWrap);
            System.arraycopy(this.sizes, 0, newSizes, beforeWrap, afterWrap);
            System.arraycopy(this.cryptoDatas, 0, newCryptoDatas, beforeWrap, afterWrap);
            System.arraycopy(this.formats, 0, newFormats, beforeWrap, afterWrap);
            System.arraycopy(this.sourceIds, 0, newSourceIds, beforeWrap, afterWrap);
            this.offsets = newOffsets;
            this.timesUs = newTimesUs;
            this.flags = newFlags;
            this.sizes = newSizes;
            this.cryptoDatas = newCryptoDatas;
            this.formats = newFormats;
            this.sourceIds = newSourceIds;
            this.relativeFirstIndex = 0;
            this.length = this.capacity;
            this.capacity = newCapacity;
        }
    }

    public synchronized void commitSampleTimestamp(long timeUs) {
        this.largestQueuedTimestampUs = Math.max(this.largestQueuedTimestampUs, timeUs);
    }

    public synchronized boolean attemptSplice(long timeUs) {
        boolean z = true;
        synchronized (this) {
            if (this.length == 0) {
                if (timeUs <= this.largestDiscardedTimestampUs) {
                    z = false;
                }
            } else {
                long largestReadTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(this.readPosition));
                if (largestReadTimestampUs >= timeUs) {
                    z = false;
                } else {
                    int retainCount = this.length;
                    int relativeSampleIndex = getRelativeIndex(this.length - 1);
                    while (retainCount > this.readPosition && this.timesUs[relativeSampleIndex] >= timeUs) {
                        retainCount--;
                        relativeSampleIndex--;
                        if (relativeSampleIndex == -1) {
                            relativeSampleIndex = this.capacity - 1;
                        }
                    }
                    discardUpstreamSamples(this.absoluteFirstIndex + retainCount);
                }
            }
        }
        return z;
    }

    private int findSampleBefore(int relativeStartIndex, int length, long timeUs, boolean keyframe) {
        int sampleCountToTarget = -1;
        int searchIndex = relativeStartIndex;
        for (int i = 0; i < length && this.timesUs[searchIndex] <= timeUs; i++) {
            if (!keyframe || (this.flags[searchIndex] & 1) != 0) {
                sampleCountToTarget = i;
            }
            searchIndex++;
            if (searchIndex == this.capacity) {
                searchIndex = 0;
            }
        }
        return sampleCountToTarget;
    }

    private long discardSamples(int discardCount) {
        this.largestDiscardedTimestampUs = Math.max(this.largestDiscardedTimestampUs, getLargestTimestamp(discardCount));
        this.length -= discardCount;
        this.absoluteFirstIndex += discardCount;
        this.relativeFirstIndex += discardCount;
        if (this.relativeFirstIndex >= this.capacity) {
            this.relativeFirstIndex -= this.capacity;
        }
        this.readPosition -= discardCount;
        if (this.readPosition < 0) {
            this.readPosition = 0;
        }
        if (this.length == 0) {
            int relativeLastDiscardIndex = (this.relativeFirstIndex == 0 ? this.capacity : this.relativeFirstIndex) - 1;
            return this.offsets[relativeLastDiscardIndex] + this.sizes[relativeLastDiscardIndex];
        }
        return this.offsets[this.relativeFirstIndex];
    }

    private long getLargestTimestamp(int length) {
        if (length == 0) {
            return Long.MIN_VALUE;
        }
        long largestTimestampUs = Long.MIN_VALUE;
        int relativeSampleIndex = getRelativeIndex(length - 1);
        for (int i = 0; i < length; i++) {
            largestTimestampUs = Math.max(largestTimestampUs, this.timesUs[relativeSampleIndex]);
            if ((this.flags[relativeSampleIndex] & 1) == 0) {
                relativeSampleIndex--;
                if (relativeSampleIndex == -1) {
                    relativeSampleIndex = this.capacity - 1;
                }
            } else {
                return largestTimestampUs;
            }
        }
        return largestTimestampUs;
    }

    private int getRelativeIndex(int offset) {
        int relativeIndex = this.relativeFirstIndex + offset;
        return relativeIndex < this.capacity ? relativeIndex : relativeIndex - this.capacity;
    }
}
