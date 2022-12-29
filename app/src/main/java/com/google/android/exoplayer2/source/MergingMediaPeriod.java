package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;

/* loaded from: classes.dex */
final class MergingMediaPeriod implements MediaPeriod.Callback, MediaPeriod {
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private MediaPeriod[] enabledPeriods;
    public final MediaPeriod[] periods;
    private TrackGroupArray trackGroups;
    private final ArrayList<MediaPeriod> childrenPendingPreparation = new ArrayList<>();
    private final IdentityHashMap<SampleStream, Integer> streamPeriodIndices = new IdentityHashMap<>();

    public MergingMediaPeriod(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, MediaPeriod... periods) {
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.periods = periods;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long positionUs) {
        MediaPeriod[] mediaPeriodArr;
        this.callback = callback;
        Collections.addAll(this.childrenPendingPreparation, this.periods);
        for (MediaPeriod period : this.periods) {
            period.prepare(this, positionUs);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        MediaPeriod[] mediaPeriodArr;
        for (MediaPeriod period : this.periods) {
            period.maybeThrowPrepareError();
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
            streamChildIndices[i] = streams[i] == null ? -1 : this.streamPeriodIndices.get(streams[i]).intValue();
            selectionChildIndices[i] = -1;
            if (selections[i] != null) {
                TrackGroup trackGroup = selections[i].getTrackGroup();
                int j = 0;
                while (true) {
                    if (j < this.periods.length) {
                        if (this.periods[j].getTrackGroups().indexOf(trackGroup) == -1) {
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
        this.streamPeriodIndices.clear();
        SampleStream[] newStreams = new SampleStream[selections.length];
        SampleStream[] childStreams = new SampleStream[selections.length];
        TrackSelection[] childSelections = new TrackSelection[selections.length];
        ArrayList<MediaPeriod> enabledPeriodsList = new ArrayList<>(this.periods.length);
        int i2 = 0;
        while (i2 < this.periods.length) {
            for (int j2 = 0; j2 < selections.length; j2++) {
                childStreams[j2] = streamChildIndices[j2] == i2 ? streams[j2] : null;
                childSelections[j2] = selectionChildIndices[j2] == i2 ? selections[j2] : null;
            }
            long selectPositionUs = this.periods[i2].selectTracks(childSelections, mayRetainStreamFlags, childStreams, streamResetFlags, positionUs);
            if (i2 == 0) {
                positionUs = selectPositionUs;
            } else if (selectPositionUs != positionUs) {
                throw new IllegalStateException("Children enabled at different positions.");
            }
            boolean periodEnabled = false;
            for (int j3 = 0; j3 < selections.length; j3++) {
                if (selectionChildIndices[j3] == i2) {
                    Assertions.checkState(childStreams[j3] != null);
                    newStreams[j3] = childStreams[j3];
                    periodEnabled = true;
                    this.streamPeriodIndices.put(childStreams[j3], Integer.valueOf(i2));
                } else if (streamChildIndices[j3] == i2) {
                    Assertions.checkState(childStreams[j3] == null);
                }
            }
            if (periodEnabled) {
                enabledPeriodsList.add(this.periods[i2]);
            }
            i2++;
        }
        System.arraycopy(newStreams, 0, streams, 0, newStreams.length);
        this.enabledPeriods = new MediaPeriod[enabledPeriodsList.size()];
        enabledPeriodsList.toArray(this.enabledPeriods);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.enabledPeriods);
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long positionUs, boolean toKeyframe) {
        MediaPeriod[] mediaPeriodArr;
        for (MediaPeriod period : this.enabledPeriods) {
            period.discardBuffer(positionUs, toKeyframe);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long positionUs) {
        this.compositeSequenceableLoader.reevaluateBuffer(positionUs);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long positionUs) {
        if (this.childrenPendingPreparation.isEmpty()) {
            return this.compositeSequenceableLoader.continueLoading(positionUs);
        }
        int childrenPendingPreparationSize = this.childrenPendingPreparation.size();
        for (int i = 0; i < childrenPendingPreparationSize; i++) {
            this.childrenPendingPreparation.get(i).continueLoading(positionUs);
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        MediaPeriod[] mediaPeriodArr;
        long positionUs = this.periods[0].readDiscontinuity();
        for (int i = 1; i < this.periods.length; i++) {
            if (this.periods[i].readDiscontinuity() != C.TIME_UNSET) {
                throw new IllegalStateException("Child reported discontinuity.");
            }
        }
        if (positionUs != C.TIME_UNSET) {
            for (MediaPeriod enabledPeriod : this.enabledPeriods) {
                if (enabledPeriod != this.periods[0] && enabledPeriod.seekToUs(positionUs) != positionUs) {
                    throw new IllegalStateException("Unexpected child seekToUs result.");
                }
            }
        }
        return positionUs;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long positionUs) {
        long positionUs2 = this.enabledPeriods[0].seekToUs(positionUs);
        for (int i = 1; i < this.enabledPeriods.length; i++) {
            if (this.enabledPeriods[i].seekToUs(positionUs2) != positionUs2) {
                throw new IllegalStateException("Unexpected child seekToUs result.");
            }
        }
        return positionUs2;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        return this.enabledPeriods[0].getAdjustedSeekPositionUs(positionUs, seekParameters);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod.Callback
    public void onPrepared(MediaPeriod preparedPeriod) {
        MediaPeriod[] mediaPeriodArr;
        int i = 0;
        this.childrenPendingPreparation.remove(preparedPeriod);
        if (this.childrenPendingPreparation.isEmpty()) {
            int totalTrackGroupCount = 0;
            for (MediaPeriod period : this.periods) {
                totalTrackGroupCount += period.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArray = new TrackGroup[totalTrackGroupCount];
            int trackGroupIndex = 0;
            MediaPeriod[] mediaPeriodArr2 = this.periods;
            int length = mediaPeriodArr2.length;
            while (i < length) {
                MediaPeriod period2 = mediaPeriodArr2[i];
                TrackGroupArray periodTrackGroups = period2.getTrackGroups();
                int periodTrackGroupCount = periodTrackGroups.length;
                int j = 0;
                int trackGroupIndex2 = trackGroupIndex;
                while (j < periodTrackGroupCount) {
                    trackGroupArray[trackGroupIndex2] = periodTrackGroups.get(j);
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

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(MediaPeriod ignored) {
        this.callback.onContinueLoadingRequested(this);
    }
}
