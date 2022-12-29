package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.MpegAudioHeader;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.util.Util;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class ConstantBitrateSeeker implements Mp3Extractor.Seeker {
    private static final int BITS_PER_BYTE = 8;
    private final int bitrate;
    private final long dataSize;
    private final long durationUs;
    private final long firstFramePosition;
    private final int frameSize;

    public ConstantBitrateSeeker(long inputLength, long firstFramePosition, MpegAudioHeader mpegAudioHeader) {
        this.firstFramePosition = firstFramePosition;
        this.frameSize = mpegAudioHeader.frameSize;
        this.bitrate = mpegAudioHeader.bitrate;
        if (inputLength == -1) {
            this.dataSize = -1L;
            this.durationUs = C.TIME_UNSET;
            return;
        }
        this.dataSize = inputLength - firstFramePosition;
        this.durationUs = getTimeUs(inputLength);
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return this.dataSize != -1;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        if (this.dataSize == -1) {
            return new SeekMap.SeekPoints(new SeekPoint(0L, this.firstFramePosition));
        }
        long positionOffset = (this.bitrate * timeUs) / 8000000;
        long positionOffset2 = Util.constrainValue((positionOffset / this.frameSize) * this.frameSize, 0L, this.dataSize - this.frameSize);
        long seekPosition = this.firstFramePosition + positionOffset2;
        long seekTimeUs = getTimeUs(seekPosition);
        SeekPoint seekPoint = new SeekPoint(seekTimeUs, seekPosition);
        if (seekTimeUs >= timeUs || positionOffset2 == this.dataSize - this.frameSize) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        long secondSeekPosition = seekPosition + this.frameSize;
        long secondSeekTimeUs = getTimeUs(secondSeekPosition);
        SeekPoint secondSeekPoint = new SeekPoint(secondSeekTimeUs, secondSeekPosition);
        return new SeekMap.SeekPoints(seekPoint, secondSeekPoint);
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Mp3Extractor.Seeker
    public long getTimeUs(long position) {
        return ((Math.max(0L, position - this.firstFramePosition) * 1000000) * 8) / this.bitrate;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }
}
