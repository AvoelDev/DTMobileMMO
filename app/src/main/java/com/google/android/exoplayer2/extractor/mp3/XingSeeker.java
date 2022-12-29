package com.google.android.exoplayer2.extractor.mp3;

import android.util.Log;
import com.google.android.exoplayer2.extractor.MpegAudioHeader;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class XingSeeker implements Mp3Extractor.Seeker {
    private static final String TAG = "XingSeeker";
    private final long dataSize;
    private final long dataStartPosition;
    private final long durationUs;
    private final long[] tableOfContents;
    private final int xingFrameSize;

    public static XingSeeker create(long inputLength, long position, MpegAudioHeader mpegAudioHeader, ParsableByteArray frame) {
        int frameCount;
        int samplesPerFrame = mpegAudioHeader.samplesPerFrame;
        int sampleRate = mpegAudioHeader.sampleRate;
        int flags = frame.readInt();
        if ((flags & 1) != 1 || (frameCount = frame.readUnsignedIntToInt()) == 0) {
            return null;
        }
        long durationUs = Util.scaleLargeTimestamp(frameCount, samplesPerFrame * 1000000, sampleRate);
        if ((flags & 6) != 6) {
            return new XingSeeker(position, mpegAudioHeader.frameSize, durationUs);
        }
        long dataSize = frame.readUnsignedIntToInt();
        long[] tableOfContents = new long[100];
        for (int i = 0; i < 100; i++) {
            tableOfContents[i] = frame.readUnsignedByte();
        }
        if (inputLength != -1 && inputLength != position + dataSize) {
            Log.w(TAG, "XING data size mismatch: " + inputLength + ", " + (position + dataSize));
        }
        return new XingSeeker(position, mpegAudioHeader.frameSize, durationUs, dataSize, tableOfContents);
    }

    private XingSeeker(long dataStartPosition, int xingFrameSize, long durationUs) {
        this(dataStartPosition, xingFrameSize, durationUs, -1L, null);
    }

    private XingSeeker(long dataStartPosition, int xingFrameSize, long durationUs, long dataSize, long[] tableOfContents) {
        this.dataStartPosition = dataStartPosition;
        this.xingFrameSize = xingFrameSize;
        this.durationUs = durationUs;
        this.dataSize = dataSize;
        this.tableOfContents = tableOfContents;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public boolean isSeekable() {
        return this.tableOfContents != null;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public SeekMap.SeekPoints getSeekPoints(long timeUs) {
        double scaledPosition;
        if (!isSeekable()) {
            return new SeekMap.SeekPoints(new SeekPoint(0L, this.dataStartPosition + this.xingFrameSize));
        }
        long timeUs2 = Util.constrainValue(timeUs, 0L, this.durationUs);
        double percent = (timeUs2 * 100.0d) / this.durationUs;
        if (percent <= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            scaledPosition = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        } else if (percent >= 100.0d) {
            scaledPosition = 256.0d;
        } else {
            int prevTableIndex = (int) percent;
            double prevScaledPosition = this.tableOfContents[prevTableIndex];
            double nextScaledPosition = prevTableIndex == 99 ? 256.0d : this.tableOfContents[prevTableIndex + 1];
            double interpolateFraction = percent - prevTableIndex;
            scaledPosition = prevScaledPosition + ((nextScaledPosition - prevScaledPosition) * interpolateFraction);
        }
        long positionOffset = Math.round((scaledPosition / 256.0d) * this.dataSize);
        return new SeekMap.SeekPoints(new SeekPoint(timeUs2, this.dataStartPosition + Util.constrainValue(positionOffset, this.xingFrameSize, this.dataSize - 1)));
    }

    @Override // com.google.android.exoplayer2.extractor.mp3.Mp3Extractor.Seeker
    public long getTimeUs(long position) {
        long positionOffset = position - this.dataStartPosition;
        if (!isSeekable() || positionOffset <= this.xingFrameSize) {
            return 0L;
        }
        double scaledPosition = (positionOffset * 256.0d) / this.dataSize;
        int prevTableIndex = Util.binarySearchFloor(this.tableOfContents, (long) scaledPosition, true, true);
        long prevTimeUs = getTimeUsForTableIndex(prevTableIndex);
        long prevScaledPosition = this.tableOfContents[prevTableIndex];
        long nextTimeUs = getTimeUsForTableIndex(prevTableIndex + 1);
        long nextScaledPosition = prevTableIndex == 99 ? 256L : this.tableOfContents[prevTableIndex + 1];
        double interpolateFraction = prevScaledPosition == nextScaledPosition ? FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE : (scaledPosition - prevScaledPosition) / (nextScaledPosition - prevScaledPosition);
        return Math.round((nextTimeUs - prevTimeUs) * interpolateFraction) + prevTimeUs;
    }

    @Override // com.google.android.exoplayer2.extractor.SeekMap
    public long getDurationUs() {
        return this.durationUs;
    }

    private long getTimeUsForTableIndex(int tableIndex) {
        return (this.durationUs * tableIndex) / 100;
    }
}
