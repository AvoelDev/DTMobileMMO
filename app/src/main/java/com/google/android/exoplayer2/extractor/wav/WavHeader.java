package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
final class WavHeader implements SeekMap {
    private final int averageBytesPerSecond;
    private final int bitsPerSample;
    private final int blockAlignment;
    private long dataSize;
    private long dataStartPosition;
    private final int encoding;
    private final int numChannels;
    private final int sampleRateHz;

    public WavHeader(int numChannels, int sampleRateHz, int averageBytesPerSecond, int blockAlignment, int bitsPerSample, int encoding) {
        this.numChannels = numChannels;
        this.sampleRateHz = sampleRateHz;
        this.averageBytesPerSecond = averageBytesPerSecond;
        this.blockAlignment = blockAlignment;
        this.bitsPerSample = bitsPerSample;
        this.encoding = encoding;
    }

    public void setDataBounds(long dataStartPosition, long dataSize) {
        this.dataStartPosition = dataStartPosition;
        this.dataSize = dataSize;
    }

    public boolean hasDataBounds() {
        return (this.dataStartPosition == 0 || this.dataSize == 0) ? false : true;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        long numFrames = this.dataSize / this.blockAlignment;
        return (1000000 * numFrames) / this.sampleRateHz;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        long positionOffset = (this.averageBytesPerSecond * timeUs) / 1000000;
        long positionOffset2 = Util.constrainValue((positionOffset / this.blockAlignment) * this.blockAlignment, 0L, this.dataSize - this.blockAlignment);
        long seekPosition = this.dataStartPosition + positionOffset2;
        long seekTimeUs = getTimeUs(seekPosition);
        SeekPoint seekPoint = new SeekPoint(seekTimeUs, seekPosition);
        if (seekTimeUs >= timeUs || positionOffset2 == this.dataSize - this.blockAlignment) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        long secondSeekPosition = seekPosition + this.blockAlignment;
        long secondSeekTimeUs = getTimeUs(secondSeekPosition);
        SeekPoint secondSeekPoint = new SeekPoint(secondSeekTimeUs, secondSeekPosition);
        return new SeekMap.SeekPoints(seekPoint, secondSeekPoint);
    }

    public long getTimeUs(long position) {
        long positionOffset = Math.max(0L, position - this.dataStartPosition);
        return (1000000 * positionOffset) / this.averageBytesPerSecond;
    }

    public int getBytesPerFrame() {
        return this.blockAlignment;
    }

    public int getBitrate() {
        return this.sampleRateHz * this.bitsPerSample * this.numChannels;
    }

    public int getSampleRateHz() {
        return this.sampleRateHz;
    }

    public int getNumChannels() {
        return this.numChannels;
    }

    public int getEncoding() {
        return this.encoding;
    }
}
