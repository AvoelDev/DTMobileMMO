package com.google.android.exoplayer2.source;

import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class BaseMediaSource implements MediaSource {
    private Object manifest;
    private ExoPlayer player;
    private Timeline timeline;
    private final ArrayList<MediaSource.SourceInfoRefreshListener> sourceInfoListeners = new ArrayList<>(1);
    private final MediaSourceEventListener.EventDispatcher eventDispatcher = new MediaSourceEventListener.EventDispatcher();

    protected abstract void prepareSourceInternal(ExoPlayer exoPlayer, boolean z);

    protected abstract void releaseSourceInternal();

    /* JADX INFO: Access modifiers changed from: protected */
    public final void refreshSourceInfo(Timeline timeline, @Nullable Object manifest) {
        this.timeline = timeline;
        this.manifest = manifest;
        Iterator<MediaSource.SourceInfoRefreshListener> it = this.sourceInfoListeners.iterator();
        while (it.hasNext()) {
            MediaSource.SourceInfoRefreshListener listener = it.next();
            listener.onSourceInfoRefreshed(this, timeline, manifest);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final MediaSourceEventListener.EventDispatcher createEventDispatcher(@Nullable MediaSource.MediaPeriodId mediaPeriodId) {
        return this.eventDispatcher.withParameters(0, mediaPeriodId, 0L);
    }

    protected final MediaSourceEventListener.EventDispatcher createEventDispatcher(MediaSource.MediaPeriodId mediaPeriodId, long mediaTimeOffsetMs) {
        Assertions.checkArgument(mediaPeriodId != null);
        return this.eventDispatcher.withParameters(0, mediaPeriodId, mediaTimeOffsetMs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final MediaSourceEventListener.EventDispatcher createEventDispatcher(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, long mediaTimeOffsetMs) {
        return this.eventDispatcher.withParameters(windowIndex, mediaPeriodId, mediaTimeOffsetMs);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void addEventListener(Handler handler, MediaSourceEventListener eventListener) {
        this.eventDispatcher.addEventListener(handler, eventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void removeEventListener(MediaSourceEventListener eventListener) {
        this.eventDispatcher.removeEventListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void prepareSource(ExoPlayer player, boolean isTopLevelSource, MediaSource.SourceInfoRefreshListener listener) {
        Assertions.checkArgument(this.player == null || this.player == player);
        this.sourceInfoListeners.add(listener);
        if (this.player == null) {
            this.player = player;
            prepareSourceInternal(player, isTopLevelSource);
        } else if (this.timeline != null) {
            listener.onSourceInfoRefreshed(this, this.timeline, this.manifest);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSource
    public final void releaseSource(MediaSource.SourceInfoRefreshListener listener) {
        this.sourceInfoListeners.remove(listener);
        if (this.sourceInfoListeners.isEmpty()) {
            this.player = null;
            this.timeline = null;
            this.manifest = null;
            releaseSourceInternal();
        }
    }
}
