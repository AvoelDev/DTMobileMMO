package com.google.android.exoplayer2.source.hls;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;

/* loaded from: classes.dex */
public final class HlsMediaPeriod implements MediaPeriod, HlsSampleStreamWrapper.Callback, HlsPlaylistTracker.PlaylistEventListener {
    private final Allocator allocator;
    private final boolean allowChunklessPreparation;
    @Nullable
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    private final HlsExtractorFactory extractorFactory;
    private final int minLoadableRetryCount;
    private boolean notifiedReadingStarted;
    private int pendingPrepareCount;
    private final HlsPlaylistTracker playlistTracker;
    private TrackGroupArray trackGroups;
    private final IdentityHashMap<SampleStream, Integer> streamWrapperIndices = new IdentityHashMap<>();
    private final TimestampAdjusterProvider timestampAdjusterProvider = new TimestampAdjusterProvider();
    private HlsSampleStreamWrapper[] sampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private HlsSampleStreamWrapper[] enabledSampleStreamWrappers = new HlsSampleStreamWrapper[0];

    public HlsMediaPeriod(HlsExtractorFactory extractorFactory, HlsPlaylistTracker playlistTracker, HlsDataSourceFactory dataSourceFactory, int minLoadableRetryCount, MediaSourceEventListener.EventDispatcher eventDispatcher, Allocator allocator, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, boolean allowChunklessPreparation) {
        this.extractorFactory = extractorFactory;
        this.playlistTracker = playlistTracker;
        this.dataSourceFactory = dataSourceFactory;
        this.minLoadableRetryCount = minLoadableRetryCount;
        this.eventDispatcher = eventDispatcher;
        this.allocator = allocator;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.allowChunklessPreparation = allowChunklessPreparation;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
        eventDispatcher.mediaPeriodCreated();
    }

    public void release() {
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr;
        this.playlistTracker.removeListener(this);
        for (HlsSampleStreamWrapper sampleStreamWrapper : this.sampleStreamWrappers) {
            sampleStreamWrapper.release();
        }
        this.callback = null;
        this.eventDispatcher.mediaPeriodReleased();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long positionUs) {
        this.callback = callback;
        this.playlistTracker.addListener(this);
        buildAndPrepareSampleStreamWrappers(positionUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr;
        for (HlsSampleStreamWrapper sampleStreamWrapper : this.sampleStreamWrappers) {
            sampleStreamWrapper.maybeThrowPrepareError();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        int[] streamChildIndices = new int[selections.length];
        int[] selectionChildIndices = new int[selections.length];
        for (int i = 0; i < selections.length; i++) {
            streamChildIndices[i] = streams[i] == null ? -1 : this.streamWrapperIndices.get(streams[i]).intValue();
            selectionChildIndices[i] = -1;
            if (selections[i] != null) {
                TrackGroup trackGroup = selections[i].getTrackGroup();
                int j = 0;
                while (true) {
                    if (j < this.sampleStreamWrappers.length) {
                        if (this.sampleStreamWrappers[j].getTrackGroups().indexOf(trackGroup) == -1) {
                            j++;
                        } else {
                            selectionChildIndices[i] = j;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        boolean forceReset = false;
        this.streamWrapperIndices.clear();
        SampleStream[] newStreams = new SampleStream[selections.length];
        SampleStream[] childStreams = new SampleStream[selections.length];
        TrackSelection[] childSelections = new TrackSelection[selections.length];
        int newEnabledSampleStreamWrapperCount = 0;
        HlsSampleStreamWrapper[] newEnabledSampleStreamWrappers = new HlsSampleStreamWrapper[this.sampleStreamWrappers.length];
        int i2 = 0;
        while (i2 < this.sampleStreamWrappers.length) {
            for (int j2 = 0; j2 < selections.length; j2++) {
                childStreams[j2] = streamChildIndices[j2] == i2 ? streams[j2] : null;
                childSelections[j2] = selectionChildIndices[j2] == i2 ? selections[j2] : null;
            }
            HlsSampleStreamWrapper sampleStreamWrapper = this.sampleStreamWrappers[i2];
            boolean wasReset = sampleStreamWrapper.selectTracks(childSelections, mayRetainStreamFlags, childStreams, streamResetFlags, positionUs, forceReset);
            boolean wrapperEnabled = false;
            for (int j3 = 0; j3 < selections.length; j3++) {
                if (selectionChildIndices[j3] == i2) {
                    Assertions.checkState(childStreams[j3] != null);
                    newStreams[j3] = childStreams[j3];
                    wrapperEnabled = true;
                    this.streamWrapperIndices.put(childStreams[j3], Integer.valueOf(i2));
                } else if (streamChildIndices[j3] == i2) {
                    Assertions.checkState(childStreams[j3] == null);
                }
            }
            if (wrapperEnabled) {
                newEnabledSampleStreamWrappers[newEnabledSampleStreamWrapperCount] = sampleStreamWrapper;
                int newEnabledSampleStreamWrapperCount2 = newEnabledSampleStreamWrapperCount + 1;
                if (newEnabledSampleStreamWrapperCount == 0) {
                    sampleStreamWrapper.setIsTimestampMaster(true);
                    if (wasReset || this.enabledSampleStreamWrappers.length == 0 || sampleStreamWrapper != this.enabledSampleStreamWrappers[0]) {
                        this.timestampAdjusterProvider.reset();
                        forceReset = true;
                        newEnabledSampleStreamWrapperCount = newEnabledSampleStreamWrapperCount2;
                    }
                } else {
                    sampleStreamWrapper.setIsTimestampMaster(false);
                }
                newEnabledSampleStreamWrapperCount = newEnabledSampleStreamWrapperCount2;
            }
            i2++;
        }
        System.arraycopy(newStreams, 0, streams, 0, newStreams.length);
        this.enabledSampleStreamWrappers = (HlsSampleStreamWrapper[]) Arrays.copyOf(newEnabledSampleStreamWrappers, newEnabledSampleStreamWrapperCount);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.enabledSampleStreamWrappers);
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long positionUs, boolean toKeyframe) {
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr;
        for (HlsSampleStreamWrapper sampleStreamWrapper : this.enabledSampleStreamWrappers) {
            sampleStreamWrapper.discardBuffer(positionUs, toKeyframe);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long positionUs) {
        this.compositeSequenceableLoader.reevaluateBuffer(positionUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long positionUs) {
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr;
        if (this.trackGroups == null) {
            for (HlsSampleStreamWrapper wrapper : this.sampleStreamWrappers) {
                wrapper.continuePreparing();
            }
            return false;
        }
        return this.compositeSequenceableLoader.continueLoading(positionUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        if (!this.notifiedReadingStarted) {
            this.eventDispatcher.readingStarted();
            this.notifiedReadingStarted = true;
            return C.TIME_UNSET;
        }
        return C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long positionUs) {
        if (this.enabledSampleStreamWrappers.length > 0) {
            boolean forceReset = this.enabledSampleStreamWrappers[0].seekToUs(positionUs, false);
            for (int i = 1; i < this.enabledSampleStreamWrappers.length; i++) {
                this.enabledSampleStreamWrappers[i].seekToUs(positionUs, forceReset);
            }
            if (forceReset) {
                this.timestampAdjusterProvider.reset();
            }
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback
    public void onPrepared() {
        int i = 0;
        int i2 = this.pendingPrepareCount - 1;
        this.pendingPrepareCount = i2;
        if (i2 <= 0) {
            int totalTrackGroupCount = 0;
            for (HlsSampleStreamWrapper sampleStreamWrapper : this.sampleStreamWrappers) {
                totalTrackGroupCount += sampleStreamWrapper.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArray = new TrackGroup[totalTrackGroupCount];
            int trackGroupIndex = 0;
            HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.sampleStreamWrappers;
            int length = hlsSampleStreamWrapperArr.length;
            while (i < length) {
                HlsSampleStreamWrapper sampleStreamWrapper2 = hlsSampleStreamWrapperArr[i];
                int wrapperTrackGroupCount = sampleStreamWrapper2.getTrackGroups().length;
                int j = 0;
                int trackGroupIndex2 = trackGroupIndex;
                while (j < wrapperTrackGroupCount) {
                    trackGroupArray[trackGroupIndex2] = sampleStreamWrapper2.getTrackGroups().get(j);
                    j++;
                    trackGroupIndex2++;
                }
                i++;
                trackGroupIndex = trackGroupIndex2;
            }
            this.trackGroups = new TrackGroupArray(trackGroupArray);
            this.callback.onPrepared(this);
        }
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback
    public void onPlaylistRefreshRequired(HlsMasterPlaylist.HlsUrl url) {
        this.playlistTracker.refreshPlaylist(url);
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(HlsSampleStreamWrapper sampleStreamWrapper) {
        this.callback.onContinueLoadingRequested(this);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
    public void onPlaylistChanged() {
        this.callback.onContinueLoadingRequested(this);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
    public boolean onPlaylistError(HlsMasterPlaylist.HlsUrl url, boolean shouldBlacklist) {
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr;
        boolean noBlacklistingFailure = true;
        for (HlsSampleStreamWrapper streamWrapper : this.sampleStreamWrappers) {
            noBlacklistingFailure &= streamWrapper.onPlaylistError(url, shouldBlacklist);
        }
        this.callback.onContinueLoadingRequested(this);
        return noBlacklistingFailure;
    }

    private void buildAndPrepareSampleStreamWrappers(long positionUs) {
        HlsMasterPlaylist masterPlaylist = this.playlistTracker.getMasterPlaylist();
        List<HlsMasterPlaylist.HlsUrl> audioRenditions = masterPlaylist.audios;
        List<HlsMasterPlaylist.HlsUrl> subtitleRenditions = masterPlaylist.subtitles;
        int wrapperCount = audioRenditions.size() + 1 + subtitleRenditions.size();
        this.sampleStreamWrappers = new HlsSampleStreamWrapper[wrapperCount];
        this.pendingPrepareCount = wrapperCount;
        buildAndPrepareMainSampleStreamWrapper(masterPlaylist, positionUs);
        int currentWrapperIndex = 1;
        int i = 0;
        while (i < audioRenditions.size()) {
            HlsMasterPlaylist.HlsUrl audioRendition = audioRenditions.get(i);
            HlsSampleStreamWrapper sampleStreamWrapper = buildSampleStreamWrapper(1, new HlsMasterPlaylist.HlsUrl[]{audioRendition}, null, Collections.emptyList(), positionUs);
            int currentWrapperIndex2 = currentWrapperIndex + 1;
            this.sampleStreamWrappers[currentWrapperIndex] = sampleStreamWrapper;
            Format renditionFormat = audioRendition.format;
            if (this.allowChunklessPreparation && renditionFormat.codecs != null) {
                sampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroupArray(new TrackGroup(audioRendition.format)), 0, TrackGroupArray.EMPTY);
            } else {
                sampleStreamWrapper.continuePreparing();
            }
            i++;
            currentWrapperIndex = currentWrapperIndex2;
        }
        int i2 = 0;
        while (i2 < subtitleRenditions.size()) {
            HlsMasterPlaylist.HlsUrl url = subtitleRenditions.get(i2);
            HlsSampleStreamWrapper sampleStreamWrapper2 = buildSampleStreamWrapper(3, new HlsMasterPlaylist.HlsUrl[]{url}, null, Collections.emptyList(), positionUs);
            this.sampleStreamWrappers[currentWrapperIndex] = sampleStreamWrapper2;
            sampleStreamWrapper2.prepareWithMasterPlaylistInfo(new TrackGroupArray(new TrackGroup(url.format)), 0, TrackGroupArray.EMPTY);
            i2++;
            currentWrapperIndex++;
        }
        this.enabledSampleStreamWrappers = this.sampleStreamWrappers;
    }

    private void buildAndPrepareMainSampleStreamWrapper(HlsMasterPlaylist masterPlaylist, long positionUs) {
        ArrayList<HlsMasterPlaylist.HlsUrl> arrayList = new ArrayList<>(masterPlaylist.variants);
        ArrayList<HlsMasterPlaylist.HlsUrl> definiteVideoVariants = new ArrayList<>();
        ArrayList<HlsMasterPlaylist.HlsUrl> definiteAudioOnlyVariants = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            HlsMasterPlaylist.HlsUrl variant = arrayList.get(i);
            Format format = variant.format;
            if (format.height > 0 || Util.getCodecsOfType(format.codecs, 2) != null) {
                definiteVideoVariants.add(variant);
            } else if (Util.getCodecsOfType(format.codecs, 1) != null) {
                definiteAudioOnlyVariants.add(variant);
            }
        }
        if (!definiteVideoVariants.isEmpty()) {
            arrayList = definiteVideoVariants;
        } else if (definiteAudioOnlyVariants.size() < arrayList.size()) {
            arrayList.removeAll(definiteAudioOnlyVariants);
        }
        Assertions.checkArgument(!arrayList.isEmpty());
        HlsMasterPlaylist.HlsUrl[] variants = (HlsMasterPlaylist.HlsUrl[]) arrayList.toArray(new HlsMasterPlaylist.HlsUrl[0]);
        String codecs = variants[0].format.codecs;
        HlsSampleStreamWrapper sampleStreamWrapper = buildSampleStreamWrapper(0, variants, masterPlaylist.muxedAudioFormat, masterPlaylist.muxedCaptionFormats, positionUs);
        this.sampleStreamWrappers[0] = sampleStreamWrapper;
        if (this.allowChunklessPreparation && codecs != null) {
            boolean variantsContainVideoCodecs = Util.getCodecsOfType(codecs, 2) != null;
            boolean variantsContainAudioCodecs = Util.getCodecsOfType(codecs, 1) != null;
            List<TrackGroup> muxedTrackGroups = new ArrayList<>();
            if (variantsContainVideoCodecs) {
                Format[] videoFormats = new Format[arrayList.size()];
                for (int i2 = 0; i2 < videoFormats.length; i2++) {
                    videoFormats[i2] = deriveVideoFormat(variants[i2].format);
                }
                muxedTrackGroups.add(new TrackGroup(videoFormats));
                if (variantsContainAudioCodecs && (masterPlaylist.muxedAudioFormat != null || masterPlaylist.audios.isEmpty())) {
                    muxedTrackGroups.add(new TrackGroup(deriveMuxedAudioFormat(variants[0].format, masterPlaylist.muxedAudioFormat, -1)));
                }
                List<Format> ccFormats = masterPlaylist.muxedCaptionFormats;
                if (ccFormats != null) {
                    for (int i3 = 0; i3 < ccFormats.size(); i3++) {
                        muxedTrackGroups.add(new TrackGroup(ccFormats.get(i3)));
                    }
                }
            } else if (variantsContainAudioCodecs) {
                Format[] audioFormats = new Format[arrayList.size()];
                for (int i4 = 0; i4 < audioFormats.length; i4++) {
                    Format variantFormat = variants[i4].format;
                    audioFormats[i4] = deriveMuxedAudioFormat(variantFormat, masterPlaylist.muxedAudioFormat, variantFormat.bitrate);
                }
                muxedTrackGroups.add(new TrackGroup(audioFormats));
            } else {
                throw new IllegalArgumentException("Unexpected codecs attribute: " + codecs);
            }
            TrackGroup id3TrackGroup = new TrackGroup(Format.createSampleFormat("ID3", MimeTypes.APPLICATION_ID3, null, -1, null));
            muxedTrackGroups.add(id3TrackGroup);
            sampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroupArray((TrackGroup[]) muxedTrackGroups.toArray(new TrackGroup[0])), 0, new TrackGroupArray(id3TrackGroup));
            return;
        }
        sampleStreamWrapper.setIsTimestampMaster(true);
        sampleStreamWrapper.continuePreparing();
    }

    private HlsSampleStreamWrapper buildSampleStreamWrapper(int trackType, HlsMasterPlaylist.HlsUrl[] variants, Format muxedAudioFormat, List<Format> muxedCaptionFormats, long positionUs) {
        HlsChunkSource defaultChunkSource = new HlsChunkSource(this.extractorFactory, this.playlistTracker, variants, this.dataSourceFactory, this.timestampAdjusterProvider, muxedCaptionFormats);
        return new HlsSampleStreamWrapper(trackType, this, defaultChunkSource, this.allocator, positionUs, muxedAudioFormat, this.minLoadableRetryCount, this.eventDispatcher);
    }

    private static Format deriveVideoFormat(Format variantFormat) {
        String codecs = Util.getCodecsOfType(variantFormat.codecs, 2);
        String mimeType = MimeTypes.getMediaMimeType(codecs);
        return Format.createVideoSampleFormat(variantFormat.id, mimeType, codecs, variantFormat.bitrate, -1, variantFormat.width, variantFormat.height, variantFormat.frameRate, null, null);
    }

    private static Format deriveMuxedAudioFormat(Format variantFormat, Format mediaTagFormat, int bitrate) {
        String codecs;
        int channelCount = -1;
        int selectionFlags = 0;
        String language = null;
        if (mediaTagFormat != null) {
            codecs = mediaTagFormat.codecs;
            channelCount = mediaTagFormat.channelCount;
            selectionFlags = mediaTagFormat.selectionFlags;
            language = mediaTagFormat.language;
        } else {
            codecs = Util.getCodecsOfType(variantFormat.codecs, 1);
        }
        String mimeType = MimeTypes.getMediaMimeType(codecs);
        return Format.createAudioSampleFormat(variantFormat.id, mimeType, codecs, bitrate, -1, channelCount, -1, null, null, selectionFlags, language);
    }
}
