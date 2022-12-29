package com.google.android.exoplayer2.source.hls.playlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.DrmInitData;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class HlsMediaPlaylist extends HlsPlaylist {
    public static final int PLAYLIST_TYPE_EVENT = 2;
    public static final int PLAYLIST_TYPE_UNKNOWN = 0;
    public static final int PLAYLIST_TYPE_VOD = 1;
    public final int discontinuitySequence;
    public final DrmInitData drmInitData;
    public final long durationUs;
    public final boolean hasDiscontinuitySequence;
    public final boolean hasEndTag;
    public final boolean hasIndependentSegmentsTag;
    public final boolean hasProgramDateTime;
    public final long mediaSequence;
    public final int playlistType;
    public final List<Segment> segments;
    public final long startOffsetUs;
    public final long startTimeUs;
    public final long targetDurationUs;
    public final int version;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface PlaylistType {
    }

    /* loaded from: classes.dex */
    public static final class Segment implements Comparable<Long> {
        public final long byterangeLength;
        public final long byterangeOffset;
        public final long durationUs;
        public final String encryptionIV;
        public final String fullSegmentEncryptionKeyUri;
        public final boolean hasGapTag;
        @Nullable
        public final Segment initializationSegment;
        public final int relativeDiscontinuitySequence;
        public final long relativeStartTimeUs;
        public final String url;

        public Segment(String uri, long byterangeOffset, long byterangeLength) {
            this(uri, null, 0L, -1, C.TIME_UNSET, null, null, byterangeOffset, byterangeLength, false);
        }

        public Segment(String url, Segment initializationSegment, long durationUs, int relativeDiscontinuitySequence, long relativeStartTimeUs, String fullSegmentEncryptionKeyUri, String encryptionIV, long byterangeOffset, long byterangeLength, boolean hasGapTag) {
            this.url = url;
            this.initializationSegment = initializationSegment;
            this.durationUs = durationUs;
            this.relativeDiscontinuitySequence = relativeDiscontinuitySequence;
            this.relativeStartTimeUs = relativeStartTimeUs;
            this.fullSegmentEncryptionKeyUri = fullSegmentEncryptionKeyUri;
            this.encryptionIV = encryptionIV;
            this.byterangeOffset = byterangeOffset;
            this.byterangeLength = byterangeLength;
            this.hasGapTag = hasGapTag;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull Long relativeStartTimeUs) {
            if (this.relativeStartTimeUs > relativeStartTimeUs.longValue()) {
                return 1;
            }
            return this.relativeStartTimeUs < relativeStartTimeUs.longValue() ? -1 : 0;
        }
    }

    public HlsMediaPlaylist(int playlistType, String baseUri, List<String> tags, long startOffsetUs, long startTimeUs, boolean hasDiscontinuitySequence, int discontinuitySequence, long mediaSequence, int version, long targetDurationUs, boolean hasIndependentSegmentsTag, boolean hasEndTag, boolean hasProgramDateTime, DrmInitData drmInitData, List<Segment> segments) {
        super(baseUri, tags);
        this.playlistType = playlistType;
        this.startTimeUs = startTimeUs;
        this.hasDiscontinuitySequence = hasDiscontinuitySequence;
        this.discontinuitySequence = discontinuitySequence;
        this.mediaSequence = mediaSequence;
        this.version = version;
        this.targetDurationUs = targetDurationUs;
        this.hasIndependentSegmentsTag = hasIndependentSegmentsTag;
        this.hasEndTag = hasEndTag;
        this.hasProgramDateTime = hasProgramDateTime;
        this.drmInitData = drmInitData;
        this.segments = Collections.unmodifiableList(segments);
        if (!segments.isEmpty()) {
            Segment last = segments.get(segments.size() - 1);
            this.durationUs = last.relativeStartTimeUs + last.durationUs;
        } else {
            this.durationUs = 0L;
        }
        if (startOffsetUs == C.TIME_UNSET) {
            startOffsetUs = C.TIME_UNSET;
        } else if (startOffsetUs < 0) {
            startOffsetUs += this.durationUs;
        }
        this.startOffsetUs = startOffsetUs;
    }

    @Override // com.google.android.exoplayer2.offline.FilterableManifest
    /* renamed from: copy */
    public HlsPlaylist copy2(List<RenditionKey> renditionKeys) {
        return this;
    }

    public boolean isNewerThan(HlsMediaPlaylist other) {
        if (other == null || this.mediaSequence > other.mediaSequence) {
            return true;
        }
        if (this.mediaSequence >= other.mediaSequence) {
            int segmentCount = this.segments.size();
            int otherSegmentCount = other.segments.size();
            return segmentCount > otherSegmentCount || (segmentCount == otherSegmentCount && this.hasEndTag && !other.hasEndTag);
        }
        return false;
    }

    public long getEndTimeUs() {
        return this.startTimeUs + this.durationUs;
    }

    public HlsMediaPlaylist copyWith(long startTimeUs, int discontinuitySequence) {
        return new HlsMediaPlaylist(this.playlistType, this.baseUri, this.tags, this.startOffsetUs, startTimeUs, true, discontinuitySequence, this.mediaSequence, this.version, this.targetDurationUs, this.hasIndependentSegmentsTag, this.hasEndTag, this.hasProgramDateTime, this.drmInitData, this.segments);
    }

    public HlsMediaPlaylist copyWithEndTag() {
        return this.hasEndTag ? this : new HlsMediaPlaylist(this.playlistType, this.baseUri, this.tags, this.startOffsetUs, this.startTimeUs, this.hasDiscontinuitySequence, this.discontinuitySequence, this.mediaSequence, this.version, this.targetDurationUs, this.hasIndependentSegmentsTag, true, this.hasProgramDateTime, this.drmInitData, this.segments);
    }
}
