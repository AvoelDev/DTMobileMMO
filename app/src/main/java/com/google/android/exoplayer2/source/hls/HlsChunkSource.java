package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.SystemClock;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkedTrackBlacklistUtil;
import com.google.android.exoplayer2.source.chunk.DataChunk;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
class HlsChunkSource {
    private final DataSource encryptionDataSource;
    private byte[] encryptionIv;
    private String encryptionIvString;
    private byte[] encryptionKey;
    private Uri encryptionKeyUri;
    private HlsMasterPlaylist.HlsUrl expectedPlaylistUrl;
    private final HlsExtractorFactory extractorFactory;
    private IOException fatalError;
    private boolean independentSegments;
    private boolean isTimestampMaster;
    private long liveEdgeInPeriodTimeUs = C.TIME_UNSET;
    private final DataSource mediaDataSource;
    private final List<Format> muxedCaptionFormats;
    private final HlsPlaylistTracker playlistTracker;
    private byte[] scratchSpace;
    private boolean seenExpectedPlaylistError;
    private final TimestampAdjusterProvider timestampAdjusterProvider;
    private final TrackGroup trackGroup;
    private TrackSelection trackSelection;
    private final HlsMasterPlaylist.HlsUrl[] variants;

    /* loaded from: classes.dex */
    public static final class HlsChunkHolder {
        public Chunk chunk;
        public boolean endOfStream;
        public HlsMasterPlaylist.HlsUrl playlist;

        public HlsChunkHolder() {
            clear();
        }

        public void clear() {
            this.chunk = null;
            this.endOfStream = false;
            this.playlist = null;
        }
    }

    public HlsChunkSource(HlsExtractorFactory extractorFactory, HlsPlaylistTracker playlistTracker, HlsMasterPlaylist.HlsUrl[] variants, HlsDataSourceFactory dataSourceFactory, TimestampAdjusterProvider timestampAdjusterProvider, List<Format> muxedCaptionFormats) {
        this.extractorFactory = extractorFactory;
        this.playlistTracker = playlistTracker;
        this.variants = variants;
        this.timestampAdjusterProvider = timestampAdjusterProvider;
        this.muxedCaptionFormats = muxedCaptionFormats;
        Format[] variantFormats = new Format[variants.length];
        int[] initialTrackSelection = new int[variants.length];
        for (int i = 0; i < variants.length; i++) {
            variantFormats[i] = variants[i].format;
            initialTrackSelection[i] = i;
        }
        this.mediaDataSource = dataSourceFactory.createDataSource(1);
        this.encryptionDataSource = dataSourceFactory.createDataSource(3);
        this.trackGroup = new TrackGroup(variantFormats);
        this.trackSelection = new InitializationTrackSelection(this.trackGroup, initialTrackSelection);
    }

    public void maybeThrowError() throws IOException {
        if (this.fatalError != null) {
            throw this.fatalError;
        }
        if (this.expectedPlaylistUrl != null && this.seenExpectedPlaylistError) {
            this.playlistTracker.maybeThrowPlaylistRefreshError(this.expectedPlaylistUrl);
        }
    }

    public TrackGroup getTrackGroup() {
        return this.trackGroup;
    }

    public void selectTracks(TrackSelection trackSelection) {
        this.trackSelection = trackSelection;
    }

    public TrackSelection getTrackSelection() {
        return this.trackSelection;
    }

    public void reset() {
        this.fatalError = null;
    }

    public void setIsTimestampMaster(boolean isTimestampMaster) {
        this.isTimestampMaster = isTimestampMaster;
    }

    public void getNextChunk(HlsMediaChunk previous, long playbackPositionUs, long loadPositionUs, HlsChunkHolder out) {
        long chunkMediaSequence;
        int oldVariantIndex = previous == null ? -1 : this.trackGroup.indexOf(previous.trackFormat);
        long bufferedDurationUs = loadPositionUs - playbackPositionUs;
        long timeToLiveEdgeUs = resolveTimeToLiveEdgeUs(playbackPositionUs);
        if (previous != null && !this.independentSegments) {
            long subtractedDurationUs = previous.getDurationUs();
            bufferedDurationUs = Math.max(0L, bufferedDurationUs - subtractedDurationUs);
            if (timeToLiveEdgeUs != C.TIME_UNSET) {
                timeToLiveEdgeUs = Math.max(0L, timeToLiveEdgeUs - subtractedDurationUs);
            }
        }
        this.trackSelection.updateSelectedTrack(playbackPositionUs, bufferedDurationUs, timeToLiveEdgeUs);
        int selectedVariantIndex = this.trackSelection.getSelectedIndexInTrackGroup();
        boolean switchingVariant = oldVariantIndex != selectedVariantIndex;
        HlsMasterPlaylist.HlsUrl selectedUrl = this.variants[selectedVariantIndex];
        if (!this.playlistTracker.isSnapshotValid(selectedUrl)) {
            out.playlist = selectedUrl;
            this.seenExpectedPlaylistError = (this.expectedPlaylistUrl == selectedUrl) & this.seenExpectedPlaylistError;
            this.expectedPlaylistUrl = selectedUrl;
            return;
        }
        HlsMediaPlaylist mediaPlaylist = this.playlistTracker.getPlaylistSnapshot(selectedUrl);
        this.independentSegments = mediaPlaylist.hasIndependentSegmentsTag;
        updateLiveEdgeTimeUs(mediaPlaylist);
        long startOfPlaylistInPeriodUs = mediaPlaylist.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
        if (previous == null || switchingVariant) {
            long endOfPlaylistInPeriodUs = startOfPlaylistInPeriodUs + mediaPlaylist.durationUs;
            long targetPositionInPeriodUs = (previous == null || this.independentSegments) ? loadPositionUs : previous.startTimeUs;
            if (!mediaPlaylist.hasEndTag && targetPositionInPeriodUs >= endOfPlaylistInPeriodUs) {
                chunkMediaSequence = mediaPlaylist.mediaSequence + mediaPlaylist.segments.size();
            } else {
                long targetPositionInPlaylistUs = targetPositionInPeriodUs - startOfPlaylistInPeriodUs;
                chunkMediaSequence = Util.binarySearchFloor((List<? extends Comparable<? super Long>>) mediaPlaylist.segments, Long.valueOf(targetPositionInPlaylistUs), true, !this.playlistTracker.isLive() || previous == null) + mediaPlaylist.mediaSequence;
                if (chunkMediaSequence < mediaPlaylist.mediaSequence && previous != null) {
                    selectedVariantIndex = oldVariantIndex;
                    selectedUrl = this.variants[selectedVariantIndex];
                    mediaPlaylist = this.playlistTracker.getPlaylistSnapshot(selectedUrl);
                    startOfPlaylistInPeriodUs = mediaPlaylist.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
                    chunkMediaSequence = previous.getNextChunkIndex();
                }
            }
        } else {
            chunkMediaSequence = previous.getNextChunkIndex();
        }
        if (chunkMediaSequence < mediaPlaylist.mediaSequence) {
            this.fatalError = new BehindLiveWindowException();
            return;
        }
        int chunkIndex = (int) (chunkMediaSequence - mediaPlaylist.mediaSequence);
        if (chunkIndex >= mediaPlaylist.segments.size()) {
            if (mediaPlaylist.hasEndTag) {
                out.endOfStream = true;
                return;
            }
            out.playlist = selectedUrl;
            this.seenExpectedPlaylistError = (this.expectedPlaylistUrl == selectedUrl) & this.seenExpectedPlaylistError;
            this.expectedPlaylistUrl = selectedUrl;
            return;
        }
        this.seenExpectedPlaylistError = false;
        this.expectedPlaylistUrl = null;
        HlsMediaPlaylist.Segment segment = mediaPlaylist.segments.get(chunkIndex);
        if (segment.fullSegmentEncryptionKeyUri != null) {
            Uri keyUri = UriUtil.resolveToUri(mediaPlaylist.baseUri, segment.fullSegmentEncryptionKeyUri);
            if (!keyUri.equals(this.encryptionKeyUri)) {
                out.chunk = newEncryptionKeyChunk(keyUri, segment.encryptionIV, selectedVariantIndex, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData());
                return;
            } else if (!Util.areEqual(segment.encryptionIV, this.encryptionIvString)) {
                setEncryptionData(keyUri, segment.encryptionIV, this.encryptionKey);
            }
        } else {
            clearEncryptionData();
        }
        DataSpec initDataSpec = null;
        HlsMediaPlaylist.Segment initSegment = segment.initializationSegment;
        if (initSegment != null) {
            Uri initSegmentUri = UriUtil.resolveToUri(mediaPlaylist.baseUri, initSegment.url);
            initDataSpec = new DataSpec(initSegmentUri, initSegment.byterangeOffset, initSegment.byterangeLength, null);
        }
        long segmentStartTimeInPeriodUs = startOfPlaylistInPeriodUs + segment.relativeStartTimeUs;
        int discontinuitySequence = mediaPlaylist.discontinuitySequence + segment.relativeDiscontinuitySequence;
        TimestampAdjuster timestampAdjuster = this.timestampAdjusterProvider.getAdjuster(discontinuitySequence);
        Uri chunkUri = UriUtil.resolveToUri(mediaPlaylist.baseUri, segment.url);
        DataSpec dataSpec = new DataSpec(chunkUri, segment.byterangeOffset, segment.byterangeLength, null);
        out.chunk = new HlsMediaChunk(this.extractorFactory, this.mediaDataSource, dataSpec, initDataSpec, selectedUrl, this.muxedCaptionFormats, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), segmentStartTimeInPeriodUs, segmentStartTimeInPeriodUs + segment.durationUs, chunkMediaSequence, discontinuitySequence, segment.hasGapTag, this.isTimestampMaster, timestampAdjuster, previous, mediaPlaylist.drmInitData, this.encryptionKey, this.encryptionIv);
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof EncryptionKeyChunk) {
            EncryptionKeyChunk encryptionKeyChunk = (EncryptionKeyChunk) chunk;
            this.scratchSpace = encryptionKeyChunk.getDataHolder();
            setEncryptionData(encryptionKeyChunk.dataSpec.uri, encryptionKeyChunk.iv, encryptionKeyChunk.getResult());
        }
    }

    public boolean onChunkLoadError(Chunk chunk, boolean cancelable, IOException error) {
        return cancelable && ChunkedTrackBlacklistUtil.maybeBlacklistTrack(this.trackSelection, this.trackSelection.indexOf(this.trackGroup.indexOf(chunk.trackFormat)), error);
    }

    public boolean onPlaylistError(HlsMasterPlaylist.HlsUrl url, boolean shouldBlacklist) {
        int trackSelectionIndex;
        boolean z = false;
        int trackGroupIndex = this.trackGroup.indexOf(url.format);
        if (trackGroupIndex == -1 || (trackSelectionIndex = this.trackSelection.indexOf(trackGroupIndex)) == -1) {
            return true;
        }
        this.seenExpectedPlaylistError = (this.expectedPlaylistUrl == url) | this.seenExpectedPlaylistError;
        if (!shouldBlacklist || this.trackSelection.blacklist(trackSelectionIndex, ChunkedTrackBlacklistUtil.DEFAULT_TRACK_BLACKLIST_MS)) {
            z = true;
        }
        return z;
    }

    private long resolveTimeToLiveEdgeUs(long playbackPositionUs) {
        boolean resolveTimeToLiveEdgePossible = this.liveEdgeInPeriodTimeUs != C.TIME_UNSET;
        return resolveTimeToLiveEdgePossible ? this.liveEdgeInPeriodTimeUs - playbackPositionUs : C.TIME_UNSET;
    }

    private void updateLiveEdgeTimeUs(HlsMediaPlaylist mediaPlaylist) {
        this.liveEdgeInPeriodTimeUs = mediaPlaylist.hasEndTag ? C.TIME_UNSET : mediaPlaylist.getEndTimeUs() - this.playlistTracker.getInitialStartTimeUs();
    }

    private EncryptionKeyChunk newEncryptionKeyChunk(Uri keyUri, String iv, int variantIndex, int trackSelectionReason, Object trackSelectionData) {
        DataSpec dataSpec = new DataSpec(keyUri, 0L, -1L, null, 1);
        return new EncryptionKeyChunk(this.encryptionDataSource, dataSpec, this.variants[variantIndex].format, trackSelectionReason, trackSelectionData, this.scratchSpace, iv);
    }

    private void setEncryptionData(Uri keyUri, String iv, byte[] secretKey) {
        String trimmedIv;
        if (Util.toLowerInvariant(iv).startsWith("0x")) {
            trimmedIv = iv.substring(2);
        } else {
            trimmedIv = iv;
        }
        byte[] ivData = new BigInteger(trimmedIv, 16).toByteArray();
        byte[] ivDataWithPadding = new byte[16];
        int offset = ivData.length > 16 ? ivData.length - 16 : 0;
        System.arraycopy(ivData, offset, ivDataWithPadding, (ivDataWithPadding.length - ivData.length) + offset, ivData.length - offset);
        this.encryptionKeyUri = keyUri;
        this.encryptionKey = secretKey;
        this.encryptionIvString = iv;
        this.encryptionIv = ivDataWithPadding;
    }

    private void clearEncryptionData() {
        this.encryptionKeyUri = null;
        this.encryptionKey = null;
        this.encryptionIvString = null;
        this.encryptionIv = null;
    }

    /* loaded from: classes.dex */
    private static final class InitializationTrackSelection extends BaseTrackSelection {
        private int selectedIndex;

        public InitializationTrackSelection(TrackGroup group, int[] tracks) {
            super(group, tracks);
            this.selectedIndex = indexOf(group.getFormat(0));
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelection
        public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs) {
            long nowMs = SystemClock.elapsedRealtime();
            if (isBlacklisted(this.selectedIndex, nowMs)) {
                for (int i = this.length - 1; i >= 0; i--) {
                    if (!isBlacklisted(i, nowMs)) {
                        this.selectedIndex = i;
                        return;
                    }
                }
                throw new IllegalStateException();
            }
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelection
        public int getSelectedIndex() {
            return this.selectedIndex;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelection
        public int getSelectionReason() {
            return 0;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelection
        public Object getSelectionData() {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class EncryptionKeyChunk extends DataChunk {
        public final String iv;
        private byte[] result;

        public EncryptionKeyChunk(DataSource dataSource, DataSpec dataSpec, Format trackFormat, int trackSelectionReason, Object trackSelectionData, byte[] scratchSpace, String iv) {
            super(dataSource, dataSpec, 3, trackFormat, trackSelectionReason, trackSelectionData, scratchSpace);
            this.iv = iv;
        }

        @Override // com.google.android.exoplayer2.source.chunk.DataChunk
        protected void consume(byte[] data, int limit) throws IOException {
            this.result = Arrays.copyOf(data, limit);
        }

        public byte[] getResult() {
            return this.result;
        }
    }
}
