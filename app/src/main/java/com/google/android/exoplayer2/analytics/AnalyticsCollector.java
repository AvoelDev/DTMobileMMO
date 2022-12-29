package com.google.android.exoplayer2.analytics;

import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.view.Surface;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

/* loaded from: classes.dex */
public class AnalyticsCollector implements Player.EventListener, AudioRendererEventListener, DefaultDrmSessionEventListener, MetadataOutput, MediaSourceEventListener, BandwidthMeter.EventListener, VideoRendererEventListener {
    private final Clock clock;
    @MonotonicNonNull
    private Player player;
    private final CopyOnWriteArraySet<AnalyticsListener> listeners = new CopyOnWriteArraySet<>();
    private final MediaPeriodQueueTracker mediaPeriodQueueTracker = new MediaPeriodQueueTracker();
    private final Timeline.Window window = new Timeline.Window();

    /* loaded from: classes.dex */
    public static class Factory {
        public AnalyticsCollector createAnalyticsCollector(@Nullable Player player, Clock clock) {
            return new AnalyticsCollector(player, clock);
        }
    }

    protected AnalyticsCollector(@Nullable Player player, Clock clock) {
        this.player = player;
        this.clock = (Clock) Assertions.checkNotNull(clock);
    }

    public void addListener(AnalyticsListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(AnalyticsListener listener) {
        this.listeners.remove(listener);
    }

    public void setPlayer(Player player) {
        Assertions.checkState(this.player == null);
        this.player = (Player) Assertions.checkNotNull(player);
    }

    public final void notifySeekStarted() {
        if (!this.mediaPeriodQueueTracker.isSeeking()) {
            AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
            this.mediaPeriodQueueTracker.onSeekStarted();
            Iterator<AnalyticsListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                AnalyticsListener listener = it.next();
                listener.onSeekStarted(eventTime);
            }
        }
    }

    public final void notifyViewportSizeChanged(int width, int height) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onViewportSizeChange(eventTime, width, height);
        }
    }

    public final void notifyNetworkTypeChanged(@Nullable NetworkInfo networkInfo) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onNetworkTypeChanged(eventTime, networkInfo);
        }
    }

    public final void resetForNewMediaSource() {
        List<WindowAndMediaPeriodId> activeMediaPeriods = new ArrayList<>(this.mediaPeriodQueueTracker.activeMediaPeriods);
        for (WindowAndMediaPeriodId mediaPeriod : activeMediaPeriods) {
            onMediaPeriodReleased(mediaPeriod.windowIndex, mediaPeriod.mediaPeriodId);
        }
    }

    @Override // com.google.android.exoplayer2.metadata.MetadataOutput
    public final void onMetadata(Metadata metadata) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onMetadata(eventTime, metadata);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioEnabled(DecoderCounters counters) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDecoderEnabled(eventTime, 1, counters);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioSessionId(int audioSessionId) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onAudioSessionId(eventTime, audioSessionId);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDecoderInitialized(eventTime, 1, decoderName, initializationDurationMs);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioInputFormatChanged(Format format) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDecoderInputFormatChanged(eventTime, 1, format);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioSinkUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onAudioUnderrun(eventTime, bufferSize, bufferSizeMs, elapsedSinceLastFeedMs);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioRendererEventListener
    public final void onAudioDisabled(DecoderCounters counters) {
        AnalyticsListener.EventTime eventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDecoderDisabled(eventTime, 1, counters);
        }
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoEnabled(DecoderCounters counters) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDecoderEnabled(eventTime, 2, counters);
        }
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDecoderInitialized(eventTime, 2, decoderName, initializationDurationMs);
        }
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoInputFormatChanged(Format format) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDecoderInputFormatChanged(eventTime, 2, format);
        }
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onDroppedFrames(int count, long elapsedMs) {
        AnalyticsListener.EventTime eventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDroppedVideoFrames(eventTime, count, elapsedMs);
        }
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onVideoSizeChanged(eventTime, width, height, unappliedRotationDegrees, pixelWidthHeightRatio);
        }
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onRenderedFirstFrame(Surface surface) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onRenderedFirstFrame(eventTime, surface);
        }
    }

    @Override // com.google.android.exoplayer2.video.VideoRendererEventListener
    public final void onVideoDisabled(DecoderCounters counters) {
        AnalyticsListener.EventTime eventTime = generateLastReportedPlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDecoderDisabled(eventTime, 2, counters);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onMediaPeriodCreated(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        this.mediaPeriodQueueTracker.onMediaPeriodCreated(windowIndex, mediaPeriodId);
        AnalyticsListener.EventTime eventTime = generateEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onMediaPeriodCreated(eventTime);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onMediaPeriodReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        this.mediaPeriodQueueTracker.onMediaPeriodReleased(windowIndex, mediaPeriodId);
        AnalyticsListener.EventTime eventTime = generateEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onMediaPeriodReleased(eventTime);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadStarted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onLoadStarted(eventTime, loadEventInfo, mediaLoadData);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadCompleted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onLoadCompleted(eventTime, loadEventInfo, mediaLoadData);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadCanceled(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onLoadCanceled(eventTime, loadEventInfo, mediaLoadData);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onLoadError(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
        AnalyticsListener.EventTime eventTime = generateEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onLoadError(eventTime, loadEventInfo, mediaLoadData, error, wasCanceled);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onReadingStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
        this.mediaPeriodQueueTracker.onReadingStarted(windowIndex, mediaPeriodId);
        AnalyticsListener.EventTime eventTime = generateEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onReadingStarted(eventTime);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onUpstreamDiscarded(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onUpstreamDiscarded(eventTime, mediaLoadData);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceEventListener
    public final void onDownstreamFormatChanged(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaSourceEventListener.MediaLoadData mediaLoadData) {
        AnalyticsListener.EventTime eventTime = generateEventTime(windowIndex, mediaPeriodId);
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDownstreamFormatChanged(eventTime, mediaLoadData);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
        this.mediaPeriodQueueTracker.onTimelineChanged(timeline);
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onTimelineChanged(eventTime, reason);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onTracksChanged(eventTime, trackGroups, trackSelections);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onLoadingChanged(boolean isLoading) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onLoadingChanged(eventTime, isLoading);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onPlayerStateChanged(eventTime, playWhenReady, playbackState);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onRepeatModeChanged(int repeatMode) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onRepeatModeChanged(eventTime, repeatMode);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onShuffleModeChanged(eventTime, shuffleModeEnabled);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onPlayerError(ExoPlaybackException error) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onPlayerError(eventTime, error);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onPositionDiscontinuity(int reason) {
        this.mediaPeriodQueueTracker.onPositionDiscontinuity(reason);
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onPositionDiscontinuity(eventTime, reason);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onPlaybackParametersChanged(eventTime, playbackParameters);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onSeekProcessed() {
        if (this.mediaPeriodQueueTracker.isSeeking()) {
            this.mediaPeriodQueueTracker.onSeekProcessed();
            AnalyticsListener.EventTime eventTime = generatePlayingMediaPeriodEventTime();
            Iterator<AnalyticsListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                AnalyticsListener listener = it.next();
                listener.onSeekProcessed(eventTime);
            }
        }
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter.EventListener
    public final void onBandwidthSample(int elapsedMs, long bytes, long bitrate) {
        AnalyticsListener.EventTime eventTime = generateLoadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onBandwidthEstimate(eventTime, elapsedMs, bytes, bitrate);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener
    public final void onDrmKeysLoaded() {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDrmKeysLoaded(eventTime);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener
    public final void onDrmSessionManagerError(Exception error) {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDrmSessionManagerError(eventTime, error);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener
    public final void onDrmKeysRestored() {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDrmKeysRestored(eventTime);
        }
    }

    @Override // com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener
    public final void onDrmKeysRemoved() {
        AnalyticsListener.EventTime eventTime = generateReadingMediaPeriodEventTime();
        Iterator<AnalyticsListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            AnalyticsListener listener = it.next();
            listener.onDrmKeysRemoved(eventTime);
        }
    }

    protected Set<AnalyticsListener> getListeners() {
        return Collections.unmodifiableSet(this.listeners);
    }

    protected AnalyticsListener.EventTime generateEventTime(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        long eventPositionMs;
        Assertions.checkNotNull(this.player);
        long realtimeMs = this.clock.elapsedRealtime();
        Timeline timeline = this.player.getCurrentTimeline();
        if (windowIndex == this.player.getCurrentWindowIndex()) {
            if (mediaPeriodId != null && mediaPeriodId.isAd()) {
                eventPositionMs = (this.player.getCurrentAdGroupIndex() == mediaPeriodId.adGroupIndex && this.player.getCurrentAdIndexInAdGroup() == mediaPeriodId.adIndexInAdGroup) ? this.player.getCurrentPosition() : 0L;
            } else {
                eventPositionMs = this.player.getContentPosition();
            }
        } else if (windowIndex >= timeline.getWindowCount() || (mediaPeriodId != null && mediaPeriodId.isAd())) {
            eventPositionMs = 0;
        } else {
            eventPositionMs = timeline.getWindow(windowIndex, this.window).getDefaultPositionMs();
        }
        long bufferedDurationMs = this.player.getBufferedPosition() - this.player.getContentPosition();
        return new AnalyticsListener.EventTime(realtimeMs, timeline, windowIndex, mediaPeriodId, eventPositionMs, this.player.getCurrentPosition(), bufferedDurationMs);
    }

    private AnalyticsListener.EventTime generateEventTime(@Nullable WindowAndMediaPeriodId mediaPeriod) {
        if (mediaPeriod == null) {
            int windowIndex = ((Player) Assertions.checkNotNull(this.player)).getCurrentWindowIndex();
            MediaSource.MediaPeriodId mediaPeriodId = this.mediaPeriodQueueTracker.tryResolveWindowIndex(windowIndex);
            return generateEventTime(windowIndex, mediaPeriodId);
        }
        return generateEventTime(mediaPeriod.windowIndex, mediaPeriod.mediaPeriodId);
    }

    private AnalyticsListener.EventTime generateLastReportedPlayingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getLastReportedPlayingMediaPeriod());
    }

    private AnalyticsListener.EventTime generatePlayingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getPlayingMediaPeriod());
    }

    private AnalyticsListener.EventTime generateReadingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getReadingMediaPeriod());
    }

    private AnalyticsListener.EventTime generateLoadingMediaPeriodEventTime() {
        return generateEventTime(this.mediaPeriodQueueTracker.getLoadingMediaPeriod());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class MediaPeriodQueueTracker {
        private boolean isSeeking;
        private WindowAndMediaPeriodId lastReportedPlayingMediaPeriod;
        private WindowAndMediaPeriodId readingMediaPeriod;
        private final ArrayList<WindowAndMediaPeriodId> activeMediaPeriods = new ArrayList<>();
        private final Timeline.Period period = new Timeline.Period();
        private Timeline timeline = Timeline.EMPTY;

        @Nullable
        public WindowAndMediaPeriodId getPlayingMediaPeriod() {
            if (this.activeMediaPeriods.isEmpty() || this.timeline.isEmpty() || this.isSeeking) {
                return null;
            }
            return this.activeMediaPeriods.get(0);
        }

        @Nullable
        public WindowAndMediaPeriodId getLastReportedPlayingMediaPeriod() {
            return this.lastReportedPlayingMediaPeriod;
        }

        @Nullable
        public WindowAndMediaPeriodId getReadingMediaPeriod() {
            return this.readingMediaPeriod;
        }

        @Nullable
        public WindowAndMediaPeriodId getLoadingMediaPeriod() {
            if (this.activeMediaPeriods.isEmpty()) {
                return null;
            }
            return this.activeMediaPeriods.get(this.activeMediaPeriods.size() - 1);
        }

        public boolean isSeeking() {
            return this.isSeeking;
        }

        @Nullable
        public MediaSource.MediaPeriodId tryResolveWindowIndex(int windowIndex) {
            MediaSource.MediaPeriodId match = null;
            if (this.timeline != null) {
                int timelinePeriodCount = this.timeline.getPeriodCount();
                for (int i = 0; i < this.activeMediaPeriods.size(); i++) {
                    WindowAndMediaPeriodId mediaPeriod = this.activeMediaPeriods.get(i);
                    int periodIndex = mediaPeriod.mediaPeriodId.periodIndex;
                    if (periodIndex < timelinePeriodCount && this.timeline.getPeriod(periodIndex, this.period).windowIndex == windowIndex) {
                        if (match != null) {
                            return null;
                        }
                        match = mediaPeriod.mediaPeriodId;
                    }
                }
            }
            return match;
        }

        public void onPositionDiscontinuity(int reason) {
            updateLastReportedPlayingMediaPeriod();
        }

        public void onTimelineChanged(Timeline timeline) {
            for (int i = 0; i < this.activeMediaPeriods.size(); i++) {
                this.activeMediaPeriods.set(i, updateMediaPeriodToNewTimeline(this.activeMediaPeriods.get(i), timeline));
            }
            if (this.readingMediaPeriod != null) {
                this.readingMediaPeriod = updateMediaPeriodToNewTimeline(this.readingMediaPeriod, timeline);
            }
            this.timeline = timeline;
            updateLastReportedPlayingMediaPeriod();
        }

        public void onSeekStarted() {
            this.isSeeking = true;
        }

        public void onSeekProcessed() {
            this.isSeeking = false;
            updateLastReportedPlayingMediaPeriod();
        }

        public void onMediaPeriodCreated(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            this.activeMediaPeriods.add(new WindowAndMediaPeriodId(windowIndex, mediaPeriodId));
            if (this.activeMediaPeriods.size() == 1 && !this.timeline.isEmpty()) {
                updateLastReportedPlayingMediaPeriod();
            }
        }

        public void onMediaPeriodReleased(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            WindowAndMediaPeriodId mediaPeriod = new WindowAndMediaPeriodId(windowIndex, mediaPeriodId);
            this.activeMediaPeriods.remove(mediaPeriod);
            if (mediaPeriod.equals(this.readingMediaPeriod)) {
                this.readingMediaPeriod = this.activeMediaPeriods.isEmpty() ? null : this.activeMediaPeriods.get(0);
            }
        }

        public void onReadingStarted(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            this.readingMediaPeriod = new WindowAndMediaPeriodId(windowIndex, mediaPeriodId);
        }

        private void updateLastReportedPlayingMediaPeriod() {
            if (!this.activeMediaPeriods.isEmpty()) {
                this.lastReportedPlayingMediaPeriod = this.activeMediaPeriods.get(0);
            }
        }

        private WindowAndMediaPeriodId updateMediaPeriodToNewTimeline(WindowAndMediaPeriodId mediaPeriod, Timeline newTimeline) {
            if (!newTimeline.isEmpty() && !this.timeline.isEmpty()) {
                Object uid = this.timeline.getPeriod(mediaPeriod.mediaPeriodId.periodIndex, this.period, true).uid;
                int newPeriodIndex = newTimeline.getIndexOfPeriod(uid);
                if (newPeriodIndex != -1) {
                    int newWindowIndex = newTimeline.getPeriod(newPeriodIndex, this.period).windowIndex;
                    return new WindowAndMediaPeriodId(newWindowIndex, mediaPeriod.mediaPeriodId.copyWithPeriodIndex(newPeriodIndex));
                }
                return mediaPeriod;
            }
            return mediaPeriod;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class WindowAndMediaPeriodId {
        public final MediaSource.MediaPeriodId mediaPeriodId;
        public final int windowIndex;

        public WindowAndMediaPeriodId(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId) {
            this.windowIndex = windowIndex;
            this.mediaPeriodId = mediaPeriodId;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            WindowAndMediaPeriodId that = (WindowAndMediaPeriodId) other;
            return this.windowIndex == that.windowIndex && this.mediaPeriodId.equals(that.mediaPeriodId);
        }

        public int hashCode() {
            return (this.windowIndex * 31) + this.mediaPeriodId.hashCode();
        }
    }
}
