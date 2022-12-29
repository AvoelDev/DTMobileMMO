package com.google.android.exoplayer2;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.StandaloneMediaClock;

/* loaded from: classes.dex */
final class DefaultMediaClock implements MediaClock {
    private final PlaybackParameterListener listener;
    @Nullable
    private MediaClock rendererClock;
    @Nullable
    private Renderer rendererClockSource;
    private final StandaloneMediaClock standaloneMediaClock;

    /* loaded from: classes.dex */
    public interface PlaybackParameterListener {
        void onPlaybackParametersChanged(PlaybackParameters playbackParameters);
    }

    public DefaultMediaClock(PlaybackParameterListener listener, Clock clock) {
        this.listener = listener;
        this.standaloneMediaClock = new StandaloneMediaClock(clock);
    }

    public void start() {
        this.standaloneMediaClock.start();
    }

    public void stop() {
        this.standaloneMediaClock.stop();
    }

    public void resetPosition(long positionUs) {
        this.standaloneMediaClock.resetPosition(positionUs);
    }

    public void onRendererEnabled(Renderer renderer) throws ExoPlaybackException {
        MediaClock rendererMediaClock = renderer.getMediaClock();
        if (rendererMediaClock != null && rendererMediaClock != this.rendererClock) {
            if (this.rendererClock != null) {
                throw ExoPlaybackException.createForUnexpected(new IllegalStateException("Multiple renderer media clocks enabled."));
            }
            this.rendererClock = rendererMediaClock;
            this.rendererClockSource = renderer;
            this.rendererClock.setPlaybackParameters(this.standaloneMediaClock.getPlaybackParameters());
            ensureSynced();
        }
    }

    public void onRendererDisabled(Renderer renderer) {
        if (renderer == this.rendererClockSource) {
            this.rendererClock = null;
            this.rendererClockSource = null;
        }
    }

    public long syncAndGetPositionUs() {
        if (isUsingRendererClock()) {
            ensureSynced();
            return this.rendererClock.getPositionUs();
        }
        return this.standaloneMediaClock.getPositionUs();
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        return isUsingRendererClock() ? this.rendererClock.getPositionUs() : this.standaloneMediaClock.getPositionUs();
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (this.rendererClock != null) {
            playbackParameters = this.rendererClock.setPlaybackParameters(playbackParameters);
        }
        this.standaloneMediaClock.setPlaybackParameters(playbackParameters);
        this.listener.onPlaybackParametersChanged(playbackParameters);
        return playbackParameters;
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        return this.rendererClock != null ? this.rendererClock.getPlaybackParameters() : this.standaloneMediaClock.getPlaybackParameters();
    }

    private void ensureSynced() {
        long rendererClockPositionUs = this.rendererClock.getPositionUs();
        this.standaloneMediaClock.resetPosition(rendererClockPositionUs);
        PlaybackParameters playbackParameters = this.rendererClock.getPlaybackParameters();
        if (!playbackParameters.equals(this.standaloneMediaClock.getPlaybackParameters())) {
            this.standaloneMediaClock.setPlaybackParameters(playbackParameters);
            this.listener.onPlaybackParametersChanged(playbackParameters);
        }
    }

    private boolean isUsingRendererClock() {
        return (this.rendererClockSource == null || this.rendererClockSource.isEnded() || (!this.rendererClockSource.isReady() && this.rendererClockSource.hasReadStreamToEnd())) ? false : true;
    }
}
