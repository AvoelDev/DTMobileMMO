package com.google.android.exoplayer2.source.hls;

import android.os.Handler;
import android.util.Log;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.hls.HlsChunkSource;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class HlsSampleStreamWrapper implements Loader.Callback<Chunk>, Loader.ReleaseCallback, SequenceableLoader, ExtractorOutput, SampleQueue.UpstreamFormatChangedListener {
    private static final int PRIMARY_TYPE_AUDIO = 2;
    private static final int PRIMARY_TYPE_NONE = 0;
    private static final int PRIMARY_TYPE_TEXT = 1;
    private static final int PRIMARY_TYPE_VIDEO = 3;
    public static final int SAMPLE_QUEUE_INDEX_NO_MAPPING_FATAL = -2;
    public static final int SAMPLE_QUEUE_INDEX_NO_MAPPING_NON_FATAL = -3;
    public static final int SAMPLE_QUEUE_INDEX_PENDING = -1;
    private static final String TAG = "HlsSampleStreamWrapper";
    private final Allocator allocator;
    private boolean audioSampleQueueMappingDone;
    private final Callback callback;
    private final HlsChunkSource chunkSource;
    private int chunkUid;
    private Format downstreamTrackFormat;
    private int enabledTrackGroupCount;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    private boolean haveAudioVideoSampleQueues;
    private long lastSeekPositionUs;
    private boolean loadingFinished;
    private final int minLoadableRetryCount;
    private final Format muxedAudioFormat;
    private TrackGroupArray optionalTrackGroups;
    private long pendingResetPositionUs;
    private boolean pendingResetUpstreamFormats;
    private boolean prepared;
    private int primaryTrackGroupIndex;
    private boolean released;
    private long sampleOffsetUs;
    private boolean sampleQueuesBuilt;
    private boolean seenFirstTrackSelection;
    private int[] trackGroupToSampleQueueIndex;
    private TrackGroupArray trackGroups;
    private final int trackType;
    private boolean tracksEnded;
    private boolean videoSampleQueueMappingDone;
    private final Loader loader = new Loader("Loader:HlsSampleStreamWrapper");
    private final HlsChunkSource.HlsChunkHolder nextChunkHolder = new HlsChunkSource.HlsChunkHolder();
    private int[] sampleQueueTrackIds = new int[0];
    private int audioSampleQueueIndex = -1;
    private int videoSampleQueueIndex = -1;
    private SampleQueue[] sampleQueues = new SampleQueue[0];
    private boolean[] sampleQueueIsAudioVideoFlags = new boolean[0];
    private boolean[] sampleQueuesEnabledStates = new boolean[0];
    private final ArrayList<HlsMediaChunk> mediaChunks = new ArrayList<>();
    private final ArrayList<HlsSampleStream> hlsSampleStreams = new ArrayList<>();
    private final Runnable maybeFinishPrepareRunnable = new Runnable() { // from class: com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.1
        @Override // java.lang.Runnable
        public void run() {
            HlsSampleStreamWrapper.this.maybeFinishPrepare();
        }
    };
    private final Runnable onTracksEndedRunnable = new Runnable() { // from class: com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.2
        @Override // java.lang.Runnable
        public void run() {
            HlsSampleStreamWrapper.this.onTracksEnded();
        }
    };
    private final Handler handler = new Handler();

    /* loaded from: classes.dex */
    public interface Callback extends SequenceableLoader.Callback<HlsSampleStreamWrapper> {
        void onPlaylistRefreshRequired(HlsMasterPlaylist.HlsUrl hlsUrl);

        void onPrepared();
    }

    public HlsSampleStreamWrapper(int trackType, Callback callback, HlsChunkSource chunkSource, Allocator allocator, long positionUs, Format muxedAudioFormat, int minLoadableRetryCount, MediaSourceEventListener.EventDispatcher eventDispatcher) {
        this.trackType = trackType;
        this.callback = callback;
        this.chunkSource = chunkSource;
        this.allocator = allocator;
        this.muxedAudioFormat = muxedAudioFormat;
        this.minLoadableRetryCount = minLoadableRetryCount;
        this.eventDispatcher = eventDispatcher;
        this.lastSeekPositionUs = positionUs;
        this.pendingResetPositionUs = positionUs;
    }

    public void continuePreparing() {
        if (!this.prepared) {
            continueLoading(this.lastSeekPositionUs);
        }
    }

    public void prepareWithMasterPlaylistInfo(TrackGroupArray trackGroups, int primaryTrackGroupIndex, TrackGroupArray optionalTrackGroups) {
        this.prepared = true;
        this.trackGroups = trackGroups;
        this.optionalTrackGroups = optionalTrackGroups;
        this.primaryTrackGroupIndex = primaryTrackGroupIndex;
        this.callback.onPrepared();
    }

    public void maybeThrowPrepareError() throws IOException {
        maybeThrowError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public int bindSampleQueueToSampleStream(int trackGroupIndex) {
        int sampleQueueIndex = this.trackGroupToSampleQueueIndex[trackGroupIndex];
        if (sampleQueueIndex == -1) {
            return this.optionalTrackGroups.indexOf(this.trackGroups.get(trackGroupIndex)) == -1 ? -2 : -3;
        } else if (this.sampleQueuesEnabledStates[sampleQueueIndex]) {
            return -2;
        } else {
            this.sampleQueuesEnabledStates[sampleQueueIndex] = true;
            return sampleQueueIndex;
        }
    }

    public void unbindSampleQueue(int trackGroupIndex) {
        int sampleQueueIndex = this.trackGroupToSampleQueueIndex[trackGroupIndex];
        Assertions.checkState(this.sampleQueuesEnabledStates[sampleQueueIndex]);
        this.sampleQueuesEnabledStates[sampleQueueIndex] = false;
    }

    public boolean selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs, boolean forceReset) {
        Assertions.checkState(this.prepared);
        int oldEnabledTrackGroupCount = this.enabledTrackGroupCount;
        for (int i = 0; i < selections.length; i++) {
            if (streams[i] != null && (selections[i] == null || !mayRetainStreamFlags[i])) {
                this.enabledTrackGroupCount--;
                ((HlsSampleStream) streams[i]).unbindSampleQueue();
                streams[i] = null;
            }
        }
        boolean seekRequired = forceReset || (!this.seenFirstTrackSelection ? positionUs == this.lastSeekPositionUs : oldEnabledTrackGroupCount != 0);
        TrackSelection oldPrimaryTrackSelection = this.chunkSource.getTrackSelection();
        TrackSelection primaryTrackSelection = oldPrimaryTrackSelection;
        for (int i2 = 0; i2 < selections.length; i2++) {
            if (streams[i2] == null && selections[i2] != null) {
                this.enabledTrackGroupCount++;
                TrackSelection selection = selections[i2];
                int trackGroupIndex = this.trackGroups.indexOf(selection.getTrackGroup());
                if (trackGroupIndex == this.primaryTrackGroupIndex) {
                    primaryTrackSelection = selection;
                    this.chunkSource.selectTracks(selection);
                }
                streams[i2] = new HlsSampleStream(this, trackGroupIndex);
                streamResetFlags[i2] = true;
                if (this.trackGroupToSampleQueueIndex != null) {
                    ((HlsSampleStream) streams[i2]).bindSampleQueue();
                }
                if (this.sampleQueuesBuilt && !seekRequired) {
                    SampleQueue sampleQueue = this.sampleQueues[this.trackGroupToSampleQueueIndex[trackGroupIndex]];
                    sampleQueue.rewind();
                    seekRequired = sampleQueue.advanceTo(positionUs, true, true) == -1 && sampleQueue.getReadIndex() != 0;
                }
            }
        }
        if (this.enabledTrackGroupCount == 0) {
            this.chunkSource.reset();
            this.downstreamTrackFormat = null;
            this.mediaChunks.clear();
            if (this.loader.isLoading()) {
                if (this.sampleQueuesBuilt) {
                    for (SampleQueue sampleQueue2 : this.sampleQueues) {
                        sampleQueue2.discardToEnd();
                    }
                }
                this.loader.cancelLoading();
            } else {
                resetSampleQueues();
            }
        } else {
            if (!this.mediaChunks.isEmpty() && !Util.areEqual(primaryTrackSelection, oldPrimaryTrackSelection)) {
                boolean primarySampleQueueDirty = false;
                if (!this.seenFirstTrackSelection) {
                    long bufferedDurationUs = positionUs < 0 ? -positionUs : 0L;
                    primaryTrackSelection.updateSelectedTrack(positionUs, bufferedDurationUs, C.TIME_UNSET);
                    int chunkIndex = this.chunkSource.getTrackGroup().indexOf(getLastMediaChunk().trackFormat);
                    if (primaryTrackSelection.getSelectedIndexInTrackGroup() != chunkIndex) {
                        primarySampleQueueDirty = true;
                    }
                } else {
                    primarySampleQueueDirty = true;
                }
                if (primarySampleQueueDirty) {
                    forceReset = true;
                    seekRequired = true;
                    this.pendingResetUpstreamFormats = true;
                }
            }
            if (seekRequired) {
                seekToUs(positionUs, forceReset);
                for (int i3 = 0; i3 < streams.length; i3++) {
                    if (streams[i3] != null) {
                        streamResetFlags[i3] = true;
                    }
                }
            }
        }
        updateSampleStreams(streams);
        this.seenFirstTrackSelection = true;
        return seekRequired;
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        if (this.sampleQueuesBuilt) {
            int sampleQueueCount = this.sampleQueues.length;
            for (int i = 0; i < sampleQueueCount; i++) {
                this.sampleQueues[i].discardTo(positionUs, toKeyframe, this.sampleQueuesEnabledStates[i]);
            }
        }
    }

    public boolean seekToUs(long positionUs, boolean forceReset) {
        this.lastSeekPositionUs = positionUs;
        if (!this.sampleQueuesBuilt || forceReset || isPendingReset() || !seekInsideBufferUs(positionUs)) {
            this.pendingResetPositionUs = positionUs;
            this.loadingFinished = false;
            this.mediaChunks.clear();
            if (this.loader.isLoading()) {
                this.loader.cancelLoading();
            } else {
                resetSampleQueues();
            }
            return true;
        }
        return false;
    }

    public void release() {
        SampleQueue[] sampleQueueArr;
        if (this.prepared) {
            for (SampleQueue sampleQueue : this.sampleQueues) {
                sampleQueue.discardToEnd();
            }
        }
        this.loader.release(this);
        this.handler.removeCallbacksAndMessages(null);
        this.released = true;
        this.hlsSampleStreams.clear();
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.ReleaseCallback
    public void onLoaderReleased() {
        resetSampleQueues();
    }

    public void setIsTimestampMaster(boolean isTimestampMaster) {
        this.chunkSource.setIsTimestampMaster(isTimestampMaster);
    }

    public boolean onPlaylistError(HlsMasterPlaylist.HlsUrl url, boolean shouldBlacklist) {
        return this.chunkSource.onPlaylistError(url, shouldBlacklist);
    }

    public boolean isReady(int sampleQueueIndex) {
        return this.loadingFinished || (!isPendingReset() && this.sampleQueues[sampleQueueIndex].hasNextSample());
    }

    public void maybeThrowError() throws IOException {
        this.loader.maybeThrowError();
        this.chunkSource.maybeThrowError();
    }

    public int readData(int sampleQueueIndex, FormatHolder formatHolder, DecoderInputBuffer buffer, boolean requireFormat) {
        if (isPendingReset()) {
            return -3;
        }
        if (!this.mediaChunks.isEmpty()) {
            int discardToMediaChunkIndex = 0;
            while (discardToMediaChunkIndex < this.mediaChunks.size() - 1 && finishedReadingChunk(this.mediaChunks.get(discardToMediaChunkIndex))) {
                discardToMediaChunkIndex++;
            }
            if (discardToMediaChunkIndex > 0) {
                Util.removeRange(this.mediaChunks, 0, discardToMediaChunkIndex);
            }
            HlsMediaChunk currentChunk = this.mediaChunks.get(0);
            Format trackFormat = currentChunk.trackFormat;
            if (!trackFormat.equals(this.downstreamTrackFormat)) {
                this.eventDispatcher.downstreamFormatChanged(this.trackType, trackFormat, currentChunk.trackSelectionReason, currentChunk.trackSelectionData, currentChunk.startTimeUs);
            }
            this.downstreamTrackFormat = trackFormat;
        }
        return this.sampleQueues[sampleQueueIndex].read(formatHolder, buffer, requireFormat, this.loadingFinished, this.lastSeekPositionUs);
    }

    public int skipData(int sampleQueueIndex, long positionUs) {
        if (isPendingReset()) {
            return 0;
        }
        SampleQueue sampleQueue = this.sampleQueues[sampleQueueIndex];
        if (this.loadingFinished && positionUs > sampleQueue.getLargestQueuedTimestampUs()) {
            return sampleQueue.advanceToEnd();
        }
        int skipCount = sampleQueue.advanceTo(positionUs, true, true);
        if (skipCount == -1) {
            skipCount = 0;
        }
        return skipCount;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        HlsMediaChunk lastCompletedMediaChunk;
        SampleQueue[] sampleQueueArr;
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long bufferedPositionUs = this.lastSeekPositionUs;
        HlsMediaChunk lastMediaChunk = getLastMediaChunk();
        if (lastMediaChunk.isLoadCompleted()) {
            lastCompletedMediaChunk = lastMediaChunk;
        } else {
            lastCompletedMediaChunk = this.mediaChunks.size() > 1 ? this.mediaChunks.get(this.mediaChunks.size() - 2) : null;
        }
        if (lastCompletedMediaChunk != null) {
            bufferedPositionUs = Math.max(bufferedPositionUs, lastCompletedMediaChunk.endTimeUs);
        }
        if (this.sampleQueuesBuilt) {
            for (SampleQueue sampleQueue : this.sampleQueues) {
                bufferedPositionUs = Math.max(bufferedPositionUs, sampleQueue.getLargestQueuedTimestampUs());
            }
            return bufferedPositionUs;
        }
        return bufferedPositionUs;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        return getLastMediaChunk().endTimeUs;
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long positionUs) {
        HlsMediaChunk previousChunk;
        long loadPositionUs;
        if (this.loadingFinished || this.loader.isLoading()) {
            return false;
        }
        if (isPendingReset()) {
            previousChunk = null;
            loadPositionUs = this.pendingResetPositionUs;
        } else {
            previousChunk = getLastMediaChunk();
            loadPositionUs = previousChunk.endTimeUs;
        }
        this.chunkSource.getNextChunk(previousChunk, positionUs, loadPositionUs, this.nextChunkHolder);
        boolean endOfStream = this.nextChunkHolder.endOfStream;
        Chunk loadable = this.nextChunkHolder.chunk;
        HlsMasterPlaylist.HlsUrl playlistToLoad = this.nextChunkHolder.playlist;
        this.nextChunkHolder.clear();
        if (endOfStream) {
            this.pendingResetPositionUs = C.TIME_UNSET;
            this.loadingFinished = true;
            return true;
        } else if (loadable == null) {
            if (playlistToLoad != null) {
                this.callback.onPlaylistRefreshRequired(playlistToLoad);
            }
            return false;
        } else {
            if (isMediaChunk(loadable)) {
                this.pendingResetPositionUs = C.TIME_UNSET;
                HlsMediaChunk mediaChunk = (HlsMediaChunk) loadable;
                mediaChunk.init(this);
                this.mediaChunks.add(mediaChunk);
            }
            long elapsedRealtimeMs = this.loader.startLoading(loadable, this, this.minLoadableRetryCount);
            this.eventDispatcher.loadStarted(loadable.dataSpec, loadable.type, this.trackType, loadable.trackFormat, loadable.trackSelectionReason, loadable.trackSelectionData, loadable.startTimeUs, loadable.endTimeUs, elapsedRealtimeMs);
            return true;
        }
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long positionUs) {
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCompleted(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs) {
        this.chunkSource.onChunkLoadCompleted(loadable);
        this.eventDispatcher.loadCompleted(loadable.dataSpec, loadable.type, this.trackType, loadable.trackFormat, loadable.trackSelectionReason, loadable.trackSelectionData, loadable.startTimeUs, loadable.endTimeUs, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        if (!this.prepared) {
            continueLoading(this.lastSeekPositionUs);
        } else {
            this.callback.onContinueLoadingRequested(this);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public void onLoadCanceled(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        this.eventDispatcher.loadCanceled(loadable.dataSpec, loadable.type, this.trackType, loadable.trackFormat, loadable.trackSelectionReason, loadable.trackSelectionData, loadable.startTimeUs, loadable.endTimeUs, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        if (!released) {
            resetSampleQueues();
            if (this.enabledTrackGroupCount > 0) {
                this.callback.onContinueLoadingRequested(this);
            }
        }
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Callback
    public int onLoadError(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error) {
        long bytesLoaded = loadable.bytesLoaded();
        boolean isMediaChunk = isMediaChunk(loadable);
        boolean cancelable = !isMediaChunk || bytesLoaded == 0;
        boolean canceled = false;
        if (this.chunkSource.onChunkLoadError(loadable, cancelable, error)) {
            if (isMediaChunk) {
                HlsMediaChunk removed = this.mediaChunks.remove(this.mediaChunks.size() - 1);
                Assertions.checkState(removed == loadable);
                if (this.mediaChunks.isEmpty()) {
                    this.pendingResetPositionUs = this.lastSeekPositionUs;
                }
            }
            canceled = true;
        }
        this.eventDispatcher.loadError(loadable.dataSpec, loadable.type, this.trackType, loadable.trackFormat, loadable.trackSelectionReason, loadable.trackSelectionData, loadable.startTimeUs, loadable.endTimeUs, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded(), error, canceled);
        if (!canceled) {
            return error instanceof ParserException ? 3 : 0;
        }
        if (!this.prepared) {
            continueLoading(this.lastSeekPositionUs);
        } else {
            this.callback.onContinueLoadingRequested(this);
        }
        return 2;
    }

    public void init(int chunkUid, boolean shouldSpliceIn, boolean reusingExtractor) {
        SampleQueue[] sampleQueueArr;
        SampleQueue[] sampleQueueArr2;
        if (!reusingExtractor) {
            this.audioSampleQueueMappingDone = false;
            this.videoSampleQueueMappingDone = false;
        }
        this.chunkUid = chunkUid;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.sourceId(chunkUid);
        }
        if (shouldSpliceIn) {
            for (SampleQueue sampleQueue2 : this.sampleQueues) {
                sampleQueue2.splice();
            }
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public TrackOutput track(int id, int type) {
        int trackCount = this.sampleQueues.length;
        if (type == 1) {
            if (this.audioSampleQueueIndex != -1) {
                if (this.audioSampleQueueMappingDone) {
                    return this.sampleQueueTrackIds[this.audioSampleQueueIndex] == id ? this.sampleQueues[this.audioSampleQueueIndex] : createDummyTrackOutput(id, type);
                }
                this.audioSampleQueueMappingDone = true;
                this.sampleQueueTrackIds[this.audioSampleQueueIndex] = id;
                return this.sampleQueues[this.audioSampleQueueIndex];
            } else if (this.tracksEnded) {
                return createDummyTrackOutput(id, type);
            }
        } else if (type == 2) {
            if (this.videoSampleQueueIndex != -1) {
                if (this.videoSampleQueueMappingDone) {
                    return this.sampleQueueTrackIds[this.videoSampleQueueIndex] == id ? this.sampleQueues[this.videoSampleQueueIndex] : createDummyTrackOutput(id, type);
                }
                this.videoSampleQueueMappingDone = true;
                this.sampleQueueTrackIds[this.videoSampleQueueIndex] = id;
                return this.sampleQueues[this.videoSampleQueueIndex];
            } else if (this.tracksEnded) {
                return createDummyTrackOutput(id, type);
            }
        } else {
            for (int i = 0; i < trackCount; i++) {
                if (this.sampleQueueTrackIds[i] == id) {
                    return this.sampleQueues[i];
                }
            }
            if (this.tracksEnded) {
                return createDummyTrackOutput(id, type);
            }
        }
        SampleQueue trackOutput = new SampleQueue(this.allocator);
        trackOutput.sourceId(this.chunkUid);
        trackOutput.setSampleOffsetUs(this.sampleOffsetUs);
        trackOutput.setUpstreamFormatChangeListener(this);
        this.sampleQueueTrackIds = Arrays.copyOf(this.sampleQueueTrackIds, trackCount + 1);
        this.sampleQueueTrackIds[trackCount] = id;
        this.sampleQueues = (SampleQueue[]) Arrays.copyOf(this.sampleQueues, trackCount + 1);
        this.sampleQueues[trackCount] = trackOutput;
        this.sampleQueueIsAudioVideoFlags = Arrays.copyOf(this.sampleQueueIsAudioVideoFlags, trackCount + 1);
        this.sampleQueueIsAudioVideoFlags[trackCount] = type == 1 || type == 2;
        this.haveAudioVideoSampleQueues |= this.sampleQueueIsAudioVideoFlags[trackCount];
        if (type == 1) {
            this.audioSampleQueueMappingDone = true;
            this.audioSampleQueueIndex = trackCount;
        } else if (type == 2) {
            this.videoSampleQueueMappingDone = true;
            this.videoSampleQueueIndex = trackCount;
        }
        this.sampleQueuesEnabledStates = Arrays.copyOf(this.sampleQueuesEnabledStates, trackCount + 1);
        return trackOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void endTracks() {
        this.tracksEnded = true;
        this.handler.post(this.onTracksEndedRunnable);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorOutput
    public void seekMap(SeekMap seekMap) {
    }

    @Override // com.google.android.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener
    public void onUpstreamFormatChanged(Format format) {
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public void setSampleOffsetUs(long sampleOffsetUs) {
        SampleQueue[] sampleQueueArr;
        this.sampleOffsetUs = sampleOffsetUs;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.setSampleOffsetUs(sampleOffsetUs);
        }
    }

    private void updateSampleStreams(SampleStream[] streams) {
        this.hlsSampleStreams.clear();
        for (SampleStream stream : streams) {
            if (stream != null) {
                this.hlsSampleStreams.add((HlsSampleStream) stream);
            }
        }
    }

    private boolean finishedReadingChunk(HlsMediaChunk chunk) {
        int chunkUid = chunk.uid;
        int sampleQueueCount = this.sampleQueues.length;
        for (int i = 0; i < sampleQueueCount; i++) {
            if (this.sampleQueuesEnabledStates[i] && this.sampleQueues[i].peekSourceId() == chunkUid) {
                return false;
            }
        }
        return true;
    }

    private void resetSampleQueues() {
        SampleQueue[] sampleQueueArr;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.reset(this.pendingResetUpstreamFormats);
        }
        this.pendingResetUpstreamFormats = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTracksEnded() {
        this.sampleQueuesBuilt = true;
        maybeFinishPrepare();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeFinishPrepare() {
        SampleQueue[] sampleQueueArr;
        if (!this.released && this.trackGroupToSampleQueueIndex == null && this.sampleQueuesBuilt) {
            for (SampleQueue sampleQueue : this.sampleQueues) {
                if (sampleQueue.getUpstreamFormat() == null) {
                    return;
                }
            }
            if (this.trackGroups != null) {
                mapSampleQueuesToMatchTrackGroups();
                return;
            }
            buildTracksFromSampleStreams();
            this.prepared = true;
            this.callback.onPrepared();
        }
    }

    private void mapSampleQueuesToMatchTrackGroups() {
        int trackGroupCount = this.trackGroups.length;
        this.trackGroupToSampleQueueIndex = new int[trackGroupCount];
        Arrays.fill(this.trackGroupToSampleQueueIndex, -1);
        for (int i = 0; i < trackGroupCount; i++) {
            int queueIndex = 0;
            while (true) {
                if (queueIndex < this.sampleQueues.length) {
                    SampleQueue sampleQueue = this.sampleQueues[queueIndex];
                    if (!formatsMatch(sampleQueue.getUpstreamFormat(), this.trackGroups.get(i).getFormat(0))) {
                        queueIndex++;
                    } else {
                        this.trackGroupToSampleQueueIndex[i] = queueIndex;
                        break;
                    }
                }
            }
        }
        Iterator<HlsSampleStream> it = this.hlsSampleStreams.iterator();
        while (it.hasNext()) {
            HlsSampleStream sampleStream = it.next();
            sampleStream.bindSampleQueue();
        }
    }

    private void buildTracksFromSampleStreams() {
        int trackType;
        int primaryExtractorTrackType = 0;
        int primaryExtractorTrackIndex = -1;
        int extractorTrackCount = this.sampleQueues.length;
        for (int i = 0; i < extractorTrackCount; i++) {
            String sampleMimeType = this.sampleQueues[i].getUpstreamFormat().sampleMimeType;
            if (MimeTypes.isVideo(sampleMimeType)) {
                trackType = 3;
            } else if (MimeTypes.isAudio(sampleMimeType)) {
                trackType = 2;
            } else if (MimeTypes.isText(sampleMimeType)) {
                trackType = 1;
            } else {
                trackType = 0;
            }
            if (trackType > primaryExtractorTrackType) {
                primaryExtractorTrackType = trackType;
                primaryExtractorTrackIndex = i;
            } else if (trackType == primaryExtractorTrackType && primaryExtractorTrackIndex != -1) {
                primaryExtractorTrackIndex = -1;
            }
        }
        TrackGroup chunkSourceTrackGroup = this.chunkSource.getTrackGroup();
        int chunkSourceTrackCount = chunkSourceTrackGroup.length;
        this.primaryTrackGroupIndex = -1;
        this.trackGroupToSampleQueueIndex = new int[extractorTrackCount];
        for (int i2 = 0; i2 < extractorTrackCount; i2++) {
            this.trackGroupToSampleQueueIndex[i2] = i2;
        }
        TrackGroup[] trackGroups = new TrackGroup[extractorTrackCount];
        for (int i3 = 0; i3 < extractorTrackCount; i3++) {
            Format sampleFormat = this.sampleQueues[i3].getUpstreamFormat();
            if (i3 == primaryExtractorTrackIndex) {
                Format[] formats = new Format[chunkSourceTrackCount];
                for (int j = 0; j < chunkSourceTrackCount; j++) {
                    formats[j] = deriveFormat(chunkSourceTrackGroup.getFormat(j), sampleFormat, true);
                }
                trackGroups[i3] = new TrackGroup(formats);
                this.primaryTrackGroupIndex = i3;
            } else {
                Format trackFormat = (primaryExtractorTrackType == 3 && MimeTypes.isAudio(sampleFormat.sampleMimeType)) ? this.muxedAudioFormat : null;
                trackGroups[i3] = new TrackGroup(deriveFormat(trackFormat, sampleFormat, false));
            }
        }
        this.trackGroups = new TrackGroupArray(trackGroups);
        Assertions.checkState(this.optionalTrackGroups == null);
        this.optionalTrackGroups = TrackGroupArray.EMPTY;
    }

    private HlsMediaChunk getLastMediaChunk() {
        return this.mediaChunks.get(this.mediaChunks.size() - 1);
    }

    private boolean isPendingReset() {
        return this.pendingResetPositionUs != C.TIME_UNSET;
    }

    private boolean seekInsideBufferUs(long positionUs) {
        int sampleQueueCount = this.sampleQueues.length;
        for (int i = 0; i < sampleQueueCount; i++) {
            SampleQueue sampleQueue = this.sampleQueues[i];
            sampleQueue.rewind();
            boolean seekInsideQueue = sampleQueue.advanceTo(positionUs, true, false) != -1;
            if (!seekInsideQueue && (this.sampleQueueIsAudioVideoFlags[i] || !this.haveAudioVideoSampleQueues)) {
                return false;
            }
        }
        return true;
    }

    private static Format deriveFormat(Format playlistFormat, Format sampleFormat, boolean propagateBitrate) {
        if (playlistFormat != null) {
            int bitrate = propagateBitrate ? playlistFormat.bitrate : -1;
            int sampleTrackType = MimeTypes.getTrackType(sampleFormat.sampleMimeType);
            String codecs = Util.getCodecsOfType(playlistFormat.codecs, sampleTrackType);
            String mimeType = MimeTypes.getMediaMimeType(codecs);
            if (mimeType == null) {
                mimeType = sampleFormat.sampleMimeType;
            }
            return sampleFormat.copyWithContainerInfo(playlistFormat.id, mimeType, codecs, bitrate, playlistFormat.width, playlistFormat.height, playlistFormat.selectionFlags, playlistFormat.language);
        }
        return sampleFormat;
    }

    private static boolean isMediaChunk(Chunk chunk) {
        return chunk instanceof HlsMediaChunk;
    }

    private static boolean formatsMatch(Format manifestFormat, Format sampleFormat) {
        String manifestFormatMimeType = manifestFormat.sampleMimeType;
        String sampleFormatMimeType = sampleFormat.sampleMimeType;
        int manifestFormatTrackType = MimeTypes.getTrackType(manifestFormatMimeType);
        if (manifestFormatTrackType != 3) {
            return manifestFormatTrackType == MimeTypes.getTrackType(sampleFormatMimeType);
        } else if (Util.areEqual(manifestFormatMimeType, sampleFormatMimeType)) {
            return !(MimeTypes.APPLICATION_CEA608.equals(manifestFormatMimeType) || MimeTypes.APPLICATION_CEA708.equals(manifestFormatMimeType)) || manifestFormat.accessibilityChannel == sampleFormat.accessibilityChannel;
        } else {
            return false;
        }
    }

    private static DummyTrackOutput createDummyTrackOutput(int id, int type) {
        Log.w(TAG, "Unmapped track with id " + id + " of type " + type);
        return new DummyTrackOutput();
    }
}
