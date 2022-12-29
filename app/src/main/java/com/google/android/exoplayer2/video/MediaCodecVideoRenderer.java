package com.google.android.exoplayer2.video;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.mediacodec.MediaFormatUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import java.nio.ByteBuffer;
import kotlin.text.Typography;

@TargetApi(16)
/* loaded from: classes.dex */
public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    private static final String KEY_CROP_BOTTOM = "crop-bottom";
    private static final String KEY_CROP_LEFT = "crop-left";
    private static final String KEY_CROP_RIGHT = "crop-right";
    private static final String KEY_CROP_TOP = "crop-top";
    private static final int MAX_PENDING_OUTPUT_STREAM_OFFSET_COUNT = 10;
    private static final int[] STANDARD_LONG_EDGE_VIDEO_PX = {1920, 1600, 1440, 1280, 960, 854, 640, 540, 480};
    private static final String TAG = "MediaCodecVideoRenderer";
    private static boolean deviceNeedsSetOutputSurfaceWorkaround;
    private static boolean evaluatedDeviceNeedsSetOutputSurfaceWorkaround;
    private final long allowedJoiningTimeMs;
    private int buffersInCodecCount;
    private CodecMaxValues codecMaxValues;
    private boolean codecNeedsSetOutputSurfaceWorkaround;
    private int consecutiveDroppedFrameCount;
    private final Context context;
    private int currentHeight;
    private float currentPixelWidthHeightRatio;
    private int currentUnappliedRotationDegrees;
    private int currentWidth;
    private final boolean deviceNeedsAutoFrcWorkaround;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private Surface dummySurface;
    private final VideoRendererEventListener.EventDispatcher eventDispatcher;
    private final VideoFrameReleaseTimeHelper frameReleaseTimeHelper;
    private long initialPositionUs;
    private long joiningDeadlineMs;
    private long lastInputTimeUs;
    private long lastRenderTimeUs;
    private final int maxDroppedFramesToNotify;
    private long outputStreamOffsetUs;
    private int pendingOutputStreamOffsetCount;
    private final long[] pendingOutputStreamOffsetsUs;
    private final long[] pendingOutputStreamSwitchTimesUs;
    private float pendingPixelWidthHeightRatio;
    private int pendingRotationDegrees;
    private boolean renderedFirstFrame;
    private int reportedHeight;
    private float reportedPixelWidthHeightRatio;
    private int reportedUnappliedRotationDegrees;
    private int reportedWidth;
    private int scalingMode;
    private Surface surface;
    private boolean tunneling;
    private int tunnelingAudioSessionId;
    OnFrameRenderedListenerV23 tunnelingOnFrameRenderedListener;

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, 0L);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs) {
        this(context, mediaCodecSelector, allowedJoiningTimeMs, null, null, -1);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs, @Nullable Handler eventHandler, @Nullable VideoRendererEventListener eventListener, int maxDroppedFrameCountToNotify) {
        this(context, mediaCodecSelector, allowedJoiningTimeMs, null, false, eventHandler, eventListener, maxDroppedFrameCountToNotify);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long allowedJoiningTimeMs, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, boolean playClearSamplesWithoutKeys, @Nullable Handler eventHandler, @Nullable VideoRendererEventListener eventListener, int maxDroppedFramesToNotify) {
        super(2, mediaCodecSelector, drmSessionManager, playClearSamplesWithoutKeys);
        this.allowedJoiningTimeMs = allowedJoiningTimeMs;
        this.maxDroppedFramesToNotify = maxDroppedFramesToNotify;
        this.context = context.getApplicationContext();
        this.frameReleaseTimeHelper = new VideoFrameReleaseTimeHelper(this.context);
        this.eventDispatcher = new VideoRendererEventListener.EventDispatcher(eventHandler, eventListener);
        this.deviceNeedsAutoFrcWorkaround = deviceNeedsAutoFrcWorkaround();
        this.pendingOutputStreamOffsetsUs = new long[10];
        this.pendingOutputStreamSwitchTimesUs = new long[10];
        this.outputStreamOffsetUs = C.TIME_UNSET;
        this.lastInputTimeUs = C.TIME_UNSET;
        this.joiningDeadlineMs = C.TIME_UNSET;
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.pendingPixelWidthHeightRatio = -1.0f;
        this.scalingMode = 1;
        clearReportedVideoSize();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected int supportsFormat(MediaCodecSelector mediaCodecSelector, DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, Format format) throws MediaCodecUtil.DecoderQueryException {
        String mimeType = format.sampleMimeType;
        if (!MimeTypes.isVideo(mimeType)) {
            return 0;
        }
        boolean requiresSecureDecryption = false;
        DrmInitData drmInitData = format.drmInitData;
        if (drmInitData != null) {
            for (int i = 0; i < drmInitData.schemeDataCount; i++) {
                requiresSecureDecryption |= drmInitData.get(i).requiresSecureDecryption;
            }
        }
        MediaCodecInfo decoderInfo = mediaCodecSelector.getDecoderInfo(mimeType, requiresSecureDecryption);
        if (decoderInfo == null) {
            return (!requiresSecureDecryption || mediaCodecSelector.getDecoderInfo(mimeType, false) == null) ? 1 : 2;
        } else if (!supportsFormatDrm(drmSessionManager, drmInitData)) {
            return 2;
        } else {
            boolean decoderCapable = decoderInfo.isCodecSupported(format.codecs);
            if (decoderCapable && format.width > 0 && format.height > 0) {
                if (Util.SDK_INT >= 21) {
                    decoderCapable = decoderInfo.isVideoSizeAndRateSupportedV21(format.width, format.height, format.frameRate);
                } else {
                    decoderCapable = format.width * format.height <= MediaCodecUtil.maxH264DecodableFrameSize();
                    if (!decoderCapable) {
                        Log.d(TAG, "FalseCheck [legacyFrameSize, " + format.width + "x" + format.height + "] [" + Util.DEVICE_DEBUG_INFO + "]");
                    }
                }
            }
            int adaptiveSupport = decoderInfo.adaptive ? 16 : 8;
            int tunnelingSupport = decoderInfo.tunneling ? 32 : 0;
            int formatSupport = decoderCapable ? 4 : 3;
            return adaptiveSupport | tunnelingSupport | formatSupport;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onEnabled(boolean joining) throws ExoPlaybackException {
        super.onEnabled(joining);
        this.tunnelingAudioSessionId = getConfiguration().tunnelingAudioSessionId;
        this.tunneling = this.tunnelingAudioSessionId != 0;
        this.eventDispatcher.enabled(this.decoderCounters);
        this.frameReleaseTimeHelper.enable();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formats, long offsetUs) throws ExoPlaybackException {
        if (this.outputStreamOffsetUs == C.TIME_UNSET) {
            this.outputStreamOffsetUs = offsetUs;
        } else {
            if (this.pendingOutputStreamOffsetCount == this.pendingOutputStreamOffsetsUs.length) {
                Log.w(TAG, "Too many stream changes, so dropping offset: " + this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1]);
            } else {
                this.pendingOutputStreamOffsetCount++;
            }
            this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1] = offsetUs;
            this.pendingOutputStreamSwitchTimesUs[this.pendingOutputStreamOffsetCount - 1] = this.lastInputTimeUs;
        }
        super.onStreamChanged(formats, offsetUs);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onPositionReset(long positionUs, boolean joining) throws ExoPlaybackException {
        super.onPositionReset(positionUs, joining);
        clearRenderedFirstFrame();
        this.initialPositionUs = C.TIME_UNSET;
        this.consecutiveDroppedFrameCount = 0;
        this.lastInputTimeUs = C.TIME_UNSET;
        if (this.pendingOutputStreamOffsetCount != 0) {
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[this.pendingOutputStreamOffsetCount - 1];
            this.pendingOutputStreamOffsetCount = 0;
        }
        if (joining) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C.TIME_UNSET;
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        if (super.isReady() && (this.renderedFirstFrame || ((this.dummySurface != null && this.surface == this.dummySurface) || getCodec() == null || this.tunneling))) {
            this.joiningDeadlineMs = C.TIME_UNSET;
            return true;
        } else if (this.joiningDeadlineMs == C.TIME_UNSET) {
            return false;
        } else {
            if (SystemClock.elapsedRealtime() >= this.joiningDeadlineMs) {
                this.joiningDeadlineMs = C.TIME_UNSET;
                return false;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onStarted() {
        super.onStarted();
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onStopped() {
        this.joiningDeadlineMs = C.TIME_UNSET;
        maybeNotifyDroppedFrames();
        super.onStopped();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onDisabled() {
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.pendingPixelWidthHeightRatio = -1.0f;
        this.outputStreamOffsetUs = C.TIME_UNSET;
        this.lastInputTimeUs = C.TIME_UNSET;
        this.pendingOutputStreamOffsetCount = 0;
        clearReportedVideoSize();
        clearRenderedFirstFrame();
        this.frameReleaseTimeHelper.disable();
        this.tunnelingOnFrameRenderedListener = null;
        this.tunneling = false;
        try {
            super.onDisabled();
        } finally {
            this.decoderCounters.ensureUpdated();
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int messageType, Object message) throws ExoPlaybackException {
        if (messageType == 1) {
            setSurface((Surface) message);
        } else if (messageType == 4) {
            this.scalingMode = ((Integer) message).intValue();
            MediaCodec codec = getCodec();
            if (codec != null) {
                codec.setVideoScalingMode(this.scalingMode);
            }
        } else {
            super.handleMessage(messageType, message);
        }
    }

    private void setSurface(Surface surface) throws ExoPlaybackException {
        if (surface == null) {
            if (this.dummySurface != null) {
                surface = this.dummySurface;
            } else {
                MediaCodecInfo codecInfo = getCodecInfo();
                if (codecInfo != null && shouldUseDummySurface(codecInfo)) {
                    this.dummySurface = DummySurface.newInstanceV17(this.context, codecInfo.secure);
                    surface = this.dummySurface;
                }
            }
        }
        if (this.surface != surface) {
            this.surface = surface;
            int state = getState();
            if (state == 1 || state == 2) {
                MediaCodec codec = getCodec();
                if (Util.SDK_INT >= 23 && codec != null && surface != null && !this.codecNeedsSetOutputSurfaceWorkaround) {
                    setOutputSurfaceV23(codec, surface);
                } else {
                    releaseCodec();
                    maybeInitCodec();
                }
            }
            if (surface != null && surface != this.dummySurface) {
                maybeRenotifyVideoSizeChanged();
                clearRenderedFirstFrame();
                if (state == 2) {
                    setJoiningDeadlineMs();
                    return;
                }
                return;
            }
            clearReportedVideoSize();
            clearRenderedFirstFrame();
        } else if (surface != null && surface != this.dummySurface) {
            maybeRenotifyVideoSizeChanged();
            maybeRenotifyRenderedFirstFrame();
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean shouldInitCodec(MediaCodecInfo codecInfo) {
        return this.surface != null || shouldUseDummySurface(codecInfo);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void configureCodec(MediaCodecInfo codecInfo, MediaCodec codec, Format format, MediaCrypto crypto) throws MediaCodecUtil.DecoderQueryException {
        this.codecMaxValues = getCodecMaxValues(codecInfo, format, getStreamFormats());
        MediaFormat mediaFormat = getMediaFormat(format, this.codecMaxValues, this.deviceNeedsAutoFrcWorkaround, this.tunnelingAudioSessionId);
        if (this.surface == null) {
            Assertions.checkState(shouldUseDummySurface(codecInfo));
            if (this.dummySurface == null) {
                this.dummySurface = DummySurface.newInstanceV17(this.context, codecInfo.secure);
            }
            this.surface = this.dummySurface;
        }
        codec.configure(mediaFormat, this.surface, crypto, 0);
        if (Util.SDK_INT >= 23 && this.tunneling) {
            this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected int canKeepCodec(MediaCodec codec, MediaCodecInfo codecInfo, Format oldFormat, Format newFormat) {
        if (!areAdaptationCompatible(codecInfo.adaptive, oldFormat, newFormat) || newFormat.width > this.codecMaxValues.width || newFormat.height > this.codecMaxValues.height || getMaxInputSize(codecInfo, newFormat) > this.codecMaxValues.inputSize) {
            return 0;
        }
        return oldFormat.initializationDataEquals(newFormat) ? 1 : 3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public void releaseCodec() {
        try {
            super.releaseCodec();
        } finally {
            this.buffersInCodecCount = 0;
            if (this.dummySurface != null) {
                if (this.surface == this.dummySurface) {
                    this.surface = null;
                }
                this.dummySurface.release();
                this.dummySurface = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public void flushCodec() throws ExoPlaybackException {
        super.flushCodec();
        this.buffersInCodecCount = 0;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onCodecInitialized(String name, long initializedTimestampMs, long initializationDurationMs) {
        this.eventDispatcher.decoderInitialized(name, initializedTimestampMs, initializationDurationMs);
        this.codecNeedsSetOutputSurfaceWorkaround = codecNeedsSetOutputSurfaceWorkaround(name);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public void onInputFormatChanged(Format newFormat) throws ExoPlaybackException {
        super.onInputFormatChanged(newFormat);
        this.eventDispatcher.inputFormatChanged(newFormat);
        this.pendingPixelWidthHeightRatio = newFormat.pixelWidthHeightRatio;
        this.pendingRotationDegrees = newFormat.rotationDegrees;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    protected void onQueueInputBuffer(DecoderInputBuffer buffer) {
        this.buffersInCodecCount++;
        this.lastInputTimeUs = Math.max(buffer.timeUs, this.lastInputTimeUs);
        if (Util.SDK_INT < 23 && this.tunneling) {
            maybeNotifyRenderedFirstFrame();
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected void onOutputFormatChanged(MediaCodec codec, MediaFormat outputFormat) {
        int integer;
        int integer2;
        boolean hasCrop = outputFormat.containsKey(KEY_CROP_RIGHT) && outputFormat.containsKey(KEY_CROP_LEFT) && outputFormat.containsKey(KEY_CROP_BOTTOM) && outputFormat.containsKey(KEY_CROP_TOP);
        if (hasCrop) {
            integer = (outputFormat.getInteger(KEY_CROP_RIGHT) - outputFormat.getInteger(KEY_CROP_LEFT)) + 1;
        } else {
            integer = outputFormat.getInteger(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
        }
        this.currentWidth = integer;
        if (hasCrop) {
            integer2 = (outputFormat.getInteger(KEY_CROP_BOTTOM) - outputFormat.getInteger(KEY_CROP_TOP)) + 1;
        } else {
            integer2 = outputFormat.getInteger(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
        }
        this.currentHeight = integer2;
        this.currentPixelWidthHeightRatio = this.pendingPixelWidthHeightRatio;
        if (Util.SDK_INT >= 21) {
            if (this.pendingRotationDegrees == 90 || this.pendingRotationDegrees == 270) {
                int rotatedHeight = this.currentWidth;
                this.currentWidth = this.currentHeight;
                this.currentHeight = rotatedHeight;
                this.currentPixelWidthHeightRatio = 1.0f / this.currentPixelWidthHeightRatio;
            }
        } else {
            this.currentUnappliedRotationDegrees = this.pendingRotationDegrees;
        }
        codec.setVideoScalingMode(this.scalingMode);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    protected boolean processOutputBuffer(long positionUs, long elapsedRealtimeUs, MediaCodec codec, ByteBuffer buffer, int bufferIndex, int bufferFlags, long bufferPresentationTimeUs, boolean shouldSkip) throws ExoPlaybackException {
        if (this.initialPositionUs == C.TIME_UNSET) {
            this.initialPositionUs = positionUs;
        }
        long presentationTimeUs = bufferPresentationTimeUs - this.outputStreamOffsetUs;
        if (shouldSkip) {
            skipOutputBuffer(codec, bufferIndex, presentationTimeUs);
            return true;
        }
        long earlyUs = bufferPresentationTimeUs - positionUs;
        if (this.surface == this.dummySurface) {
            if (isBufferLate(earlyUs)) {
                skipOutputBuffer(codec, bufferIndex, presentationTimeUs);
                return true;
            }
            return false;
        }
        long elapsedRealtimeNowUs = SystemClock.elapsedRealtime() * 1000;
        boolean isStarted = getState() == 2;
        if (!this.renderedFirstFrame || (isStarted && shouldForceRenderOutputBuffer(earlyUs, elapsedRealtimeNowUs - this.lastRenderTimeUs))) {
            if (Util.SDK_INT >= 21) {
                renderOutputBufferV21(codec, bufferIndex, presentationTimeUs, System.nanoTime());
            } else {
                renderOutputBuffer(codec, bufferIndex, presentationTimeUs);
            }
            return true;
        } else if (!isStarted || positionUs == this.initialPositionUs) {
            return false;
        } else {
            long elapsedSinceStartOfLoopUs = elapsedRealtimeNowUs - elapsedRealtimeUs;
            long systemTimeNs = System.nanoTime();
            long unadjustedFrameReleaseTimeNs = systemTimeNs + (1000 * (earlyUs - elapsedSinceStartOfLoopUs));
            long adjustedReleaseTimeNs = this.frameReleaseTimeHelper.adjustReleaseTime(bufferPresentationTimeUs, unadjustedFrameReleaseTimeNs);
            long earlyUs2 = (adjustedReleaseTimeNs - systemTimeNs) / 1000;
            if (shouldDropBuffersToKeyframe(earlyUs2, elapsedRealtimeUs) && maybeDropBuffersToKeyframe(codec, bufferIndex, presentationTimeUs, positionUs)) {
                return false;
            }
            if (shouldDropOutputBuffer(earlyUs2, elapsedRealtimeUs)) {
                dropOutputBuffer(codec, bufferIndex, presentationTimeUs);
                return true;
            }
            if (Util.SDK_INT >= 21) {
                if (earlyUs2 < 50000) {
                    renderOutputBufferV21(codec, bufferIndex, presentationTimeUs, adjustedReleaseTimeNs);
                    return true;
                }
            } else if (earlyUs2 < 30000) {
                if (earlyUs2 > 11000) {
                    try {
                        Thread.sleep((earlyUs2 - 10000) / 1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                }
                renderOutputBuffer(codec, bufferIndex, presentationTimeUs);
                return true;
            }
            return false;
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    protected void onProcessedOutputBuffer(long presentationTimeUs) {
        this.buffersInCodecCount--;
        while (this.pendingOutputStreamOffsetCount != 0 && presentationTimeUs >= this.pendingOutputStreamSwitchTimesUs[0]) {
            this.outputStreamOffsetUs = this.pendingOutputStreamOffsetsUs[0];
            this.pendingOutputStreamOffsetCount--;
            System.arraycopy(this.pendingOutputStreamOffsetsUs, 1, this.pendingOutputStreamOffsetsUs, 0, this.pendingOutputStreamOffsetCount);
            System.arraycopy(this.pendingOutputStreamSwitchTimesUs, 1, this.pendingOutputStreamSwitchTimesUs, 0, this.pendingOutputStreamOffsetCount);
        }
    }

    protected boolean shouldDropOutputBuffer(long earlyUs, long elapsedRealtimeUs) {
        return isBufferLate(earlyUs);
    }

    protected boolean shouldDropBuffersToKeyframe(long earlyUs, long elapsedRealtimeUs) {
        return isBufferVeryLate(earlyUs);
    }

    protected boolean shouldForceRenderOutputBuffer(long earlyUs, long elapsedSinceLastRenderUs) {
        return isBufferLate(earlyUs) && elapsedSinceLastRenderUs > 100000;
    }

    protected void skipOutputBuffer(MediaCodec codec, int index, long presentationTimeUs) {
        TraceUtil.beginSection("skipVideoBuffer");
        codec.releaseOutputBuffer(index, false);
        TraceUtil.endSection();
        this.decoderCounters.skippedOutputBufferCount++;
    }

    protected void dropOutputBuffer(MediaCodec codec, int index, long presentationTimeUs) {
        TraceUtil.beginSection("dropVideoBuffer");
        codec.releaseOutputBuffer(index, false);
        TraceUtil.endSection();
        updateDroppedBufferCounters(1);
    }

    protected boolean maybeDropBuffersToKeyframe(MediaCodec codec, int index, long presentationTimeUs, long positionUs) throws ExoPlaybackException {
        int droppedSourceBufferCount = skipSource(positionUs);
        if (droppedSourceBufferCount == 0) {
            return false;
        }
        this.decoderCounters.droppedToKeyframeCount++;
        updateDroppedBufferCounters(this.buffersInCodecCount + droppedSourceBufferCount);
        flushCodec();
        return true;
    }

    protected void updateDroppedBufferCounters(int droppedBufferCount) {
        this.decoderCounters.droppedBufferCount += droppedBufferCount;
        this.droppedFrames += droppedBufferCount;
        this.consecutiveDroppedFrameCount += droppedBufferCount;
        this.decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(this.consecutiveDroppedFrameCount, this.decoderCounters.maxConsecutiveDroppedBufferCount);
        if (this.droppedFrames >= this.maxDroppedFramesToNotify) {
            maybeNotifyDroppedFrames();
        }
    }

    protected void renderOutputBuffer(MediaCodec codec, int index, long presentationTimeUs) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        codec.releaseOutputBuffer(index, true);
        TraceUtil.endSection();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    @TargetApi(21)
    protected void renderOutputBufferV21(MediaCodec codec, int index, long presentationTimeUs, long releaseTimeNs) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        codec.releaseOutputBuffer(index, releaseTimeNs);
        TraceUtil.endSection();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    private boolean shouldUseDummySurface(MediaCodecInfo codecInfo) {
        return Util.SDK_INT >= 23 && !this.tunneling && !codecNeedsSetOutputSurfaceWorkaround(codecInfo.name) && (!codecInfo.secure || DummySurface.isSecureSupported(this.context));
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C.TIME_UNSET;
    }

    private void clearRenderedFirstFrame() {
        MediaCodec codec;
        this.renderedFirstFrame = false;
        if (Util.SDK_INT >= 23 && this.tunneling && (codec = getCodec()) != null) {
            this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
        }
    }

    void maybeNotifyRenderedFirstFrame() {
        if (!this.renderedFirstFrame) {
            this.renderedFirstFrame = true;
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void maybeRenotifyRenderedFirstFrame() {
        if (this.renderedFirstFrame) {
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void clearReportedVideoSize() {
        this.reportedWidth = -1;
        this.reportedHeight = -1;
        this.reportedPixelWidthHeightRatio = -1.0f;
        this.reportedUnappliedRotationDegrees = -1;
    }

    private void maybeNotifyVideoSizeChanged() {
        if (this.currentWidth == -1 && this.currentHeight == -1) {
            return;
        }
        if (this.reportedWidth != this.currentWidth || this.reportedHeight != this.currentHeight || this.reportedUnappliedRotationDegrees != this.currentUnappliedRotationDegrees || this.reportedPixelWidthHeightRatio != this.currentPixelWidthHeightRatio) {
            this.eventDispatcher.videoSizeChanged(this.currentWidth, this.currentHeight, this.currentUnappliedRotationDegrees, this.currentPixelWidthHeightRatio);
            this.reportedWidth = this.currentWidth;
            this.reportedHeight = this.currentHeight;
            this.reportedUnappliedRotationDegrees = this.currentUnappliedRotationDegrees;
            this.reportedPixelWidthHeightRatio = this.currentPixelWidthHeightRatio;
        }
    }

    private void maybeRenotifyVideoSizeChanged() {
        if (this.reportedWidth != -1 || this.reportedHeight != -1) {
            this.eventDispatcher.videoSizeChanged(this.reportedWidth, this.reportedHeight, this.reportedUnappliedRotationDegrees, this.reportedPixelWidthHeightRatio);
        }
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long now = SystemClock.elapsedRealtime();
            long elapsedMs = now - this.droppedFrameAccumulationStartTimeMs;
            this.eventDispatcher.droppedFrames(this.droppedFrames, elapsedMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = now;
        }
    }

    private static boolean isBufferLate(long earlyUs) {
        return earlyUs < -30000;
    }

    private static boolean isBufferVeryLate(long earlyUs) {
        return earlyUs < -500000;
    }

    @TargetApi(23)
    private static void setOutputSurfaceV23(MediaCodec codec, Surface surface) {
        codec.setOutputSurface(surface);
    }

    @TargetApi(21)
    private static void configureTunnelingV21(MediaFormat mediaFormat, int tunnelingAudioSessionId) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", tunnelingAudioSessionId);
    }

    @SuppressLint({"InlinedApi"})
    protected MediaFormat getMediaFormat(Format format, CodecMaxValues codecMaxValues, boolean deviceNeedsAutoFrcWorkaround, int tunnelingAudioSessionId) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", format.sampleMimeType);
        mediaFormat.setInteger(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, format.width);
        mediaFormat.setInteger(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, format.height);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format.rotationDegrees);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, format.colorInfo);
        mediaFormat.setInteger("max-width", codecMaxValues.width);
        mediaFormat.setInteger("max-height", codecMaxValues.height);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues.inputSize);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger("priority", 0);
        }
        if (deviceNeedsAutoFrcWorkaround) {
            mediaFormat.setInteger("auto-frc", 0);
        }
        if (tunnelingAudioSessionId != 0) {
            configureTunnelingV21(mediaFormat, tunnelingAudioSessionId);
        }
        return mediaFormat;
    }

    protected CodecMaxValues getCodecMaxValues(MediaCodecInfo codecInfo, Format format, Format[] streamFormats) throws MediaCodecUtil.DecoderQueryException {
        int maxWidth = format.width;
        int maxHeight = format.height;
        int maxInputSize = getMaxInputSize(codecInfo, format);
        if (streamFormats.length == 1) {
            return new CodecMaxValues(maxWidth, maxHeight, maxInputSize);
        }
        boolean haveUnknownDimensions = false;
        for (Format streamFormat : streamFormats) {
            if (areAdaptationCompatible(codecInfo.adaptive, format, streamFormat)) {
                haveUnknownDimensions |= streamFormat.width == -1 || streamFormat.height == -1;
                maxWidth = Math.max(maxWidth, streamFormat.width);
                maxHeight = Math.max(maxHeight, streamFormat.height);
                maxInputSize = Math.max(maxInputSize, getMaxInputSize(codecInfo, streamFormat));
            }
        }
        if (haveUnknownDimensions) {
            Log.w(TAG, "Resolutions unknown. Codec max resolution: " + maxWidth + "x" + maxHeight);
            Point codecMaxSize = getCodecMaxSize(codecInfo, format);
            if (codecMaxSize != null) {
                maxWidth = Math.max(maxWidth, codecMaxSize.x);
                maxHeight = Math.max(maxHeight, codecMaxSize.y);
                maxInputSize = Math.max(maxInputSize, getMaxInputSize(codecInfo, format.sampleMimeType, maxWidth, maxHeight));
                Log.w(TAG, "Codec max resolution adjusted to: " + maxWidth + "x" + maxHeight);
            }
        }
        return new CodecMaxValues(maxWidth, maxHeight, maxInputSize);
    }

    private static Point getCodecMaxSize(MediaCodecInfo codecInfo, Format format) throws MediaCodecUtil.DecoderQueryException {
        boolean isVerticalVideo = format.height > format.width;
        int formatLongEdgePx = isVerticalVideo ? format.height : format.width;
        int formatShortEdgePx = isVerticalVideo ? format.width : format.height;
        float aspectRatio = formatShortEdgePx / formatLongEdgePx;
        int[] iArr = STANDARD_LONG_EDGE_VIDEO_PX;
        int length = iArr.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < length) {
                int longEdgePx = iArr[i2];
                int shortEdgePx = (int) (longEdgePx * aspectRatio);
                if (longEdgePx <= formatLongEdgePx || shortEdgePx <= formatShortEdgePx) {
                    break;
                }
                if (Util.SDK_INT >= 21) {
                    Point alignedSize = codecInfo.alignVideoSizeV21(isVerticalVideo ? shortEdgePx : longEdgePx, isVerticalVideo ? longEdgePx : shortEdgePx);
                    float frameRate = format.frameRate;
                    if (codecInfo.isVideoSizeAndRateSupportedV21(alignedSize.x, alignedSize.y, frameRate)) {
                        return alignedSize;
                    }
                } else {
                    int longEdgePx2 = Util.ceilDivide(longEdgePx, 16) * 16;
                    int shortEdgePx2 = Util.ceilDivide(shortEdgePx, 16) * 16;
                    if (longEdgePx2 * shortEdgePx2 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                        int i3 = isVerticalVideo ? shortEdgePx2 : longEdgePx2;
                        if (!isVerticalVideo) {
                            longEdgePx2 = shortEdgePx2;
                        }
                        return new Point(i3, longEdgePx2);
                    }
                }
                i = i2 + 1;
            } else {
                return null;
            }
        }
        return null;
    }

    private static int getMaxInputSize(MediaCodecInfo codecInfo, Format format) {
        if (format.maxInputSize != -1) {
            int totalInitializationDataSize = 0;
            int initializationDataCount = format.initializationData.size();
            for (int i = 0; i < initializationDataCount; i++) {
                totalInitializationDataSize += format.initializationData.get(i).length;
            }
            return format.maxInputSize + totalInitializationDataSize;
        }
        return getMaxInputSize(codecInfo, format.sampleMimeType, format.width, format.height);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int getMaxInputSize(MediaCodecInfo codecInfo, String sampleMimeType, int width, int height) {
        char c;
        int maxPixels;
        int minCompressionRatio;
        if (width == -1 || height == -1) {
            return -1;
        }
        switch (sampleMimeType.hashCode()) {
            case -1664118616:
                if (sampleMimeType.equals(MimeTypes.VIDEO_H263)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1662541442:
                if (sampleMimeType.equals(MimeTypes.VIDEO_H265)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1187890754:
                if (sampleMimeType.equals(MimeTypes.VIDEO_MP4V)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1331836730:
                if (sampleMimeType.equals(MimeTypes.VIDEO_H264)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1599127256:
                if (sampleMimeType.equals(MimeTypes.VIDEO_VP8)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1599127257:
                if (sampleMimeType.equals(MimeTypes.VIDEO_VP9)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                maxPixels = width * height;
                minCompressionRatio = 2;
                break;
            case 2:
                if (!"BRAVIA 4K 2015".equals(Util.MODEL)) {
                    if ("Amazon".equals(Util.MANUFACTURER)) {
                        if ("KFSOWI".equals(Util.MODEL)) {
                            return -1;
                        }
                        if ("AFTS".equals(Util.MODEL) && codecInfo.secure) {
                            return -1;
                        }
                    }
                    maxPixels = Util.ceilDivide(width, 16) * Util.ceilDivide(height, 16) * 16 * 16;
                    minCompressionRatio = 2;
                    break;
                } else {
                    return -1;
                }
            case 3:
                maxPixels = width * height;
                minCompressionRatio = 2;
                break;
            case 4:
            case 5:
                maxPixels = width * height;
                minCompressionRatio = 4;
                break;
            default:
                return -1;
        }
        return (maxPixels * 3) / (minCompressionRatio * 2);
    }

    private static boolean areAdaptationCompatible(boolean codecIsAdaptive, Format first, Format second) {
        return first.sampleMimeType.equals(second.sampleMimeType) && first.rotationDegrees == second.rotationDegrees && (codecIsAdaptive || (first.width == second.width && first.height == second.height)) && Util.areEqual(first.colorInfo, second.colorInfo);
    }

    private static boolean deviceNeedsAutoFrcWorkaround() {
        return Util.SDK_INT <= 22 && "foster".equals(Util.DEVICE) && "NVIDIA".equals(Util.MANUFACTURER);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected boolean codecNeedsSetOutputSurfaceWorkaround(String name) {
        char c = 27;
        boolean z = false;
        if (Util.SDK_INT >= 27 || name.startsWith("OMX.google")) {
            return false;
        }
        synchronized (MediaCodecVideoRenderer.class) {
            if (!evaluatedDeviceNeedsSetOutputSurfaceWorkaround) {
                String str = Util.DEVICE;
                switch (str.hashCode()) {
                    case -2144781245:
                        if (str.equals("GIONEE_SWW1609")) {
                            c = '\'';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2144781185:
                        if (str.equals("GIONEE_SWW1627")) {
                            c = '(';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2144781160:
                        if (str.equals("GIONEE_SWW1631")) {
                            c = ')';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2097309513:
                        if (str.equals("K50a40")) {
                            c = '9';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2022874474:
                        if (str.equals("CP8676_I02")) {
                            c = 16;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1978993182:
                        if (str.equals("NX541J")) {
                            c = 'E';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1978990237:
                        if (str.equals("NX573J")) {
                            c = 'F';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1936688988:
                        if (str.equals("PGN528")) {
                            c = 'P';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1936688066:
                        if (str.equals("PGN610")) {
                            c = 'Q';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1936688065:
                        if (str.equals("PGN611")) {
                            c = 'R';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1931988508:
                        if (str.equals("AquaPowerM")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1696512866:
                        if (str.equals("XT1663")) {
                            c = 's';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1680025915:
                        if (str.equals("ComioS1")) {
                            c = 15;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1615810839:
                        if (str.equals("Phantom6")) {
                            c = 'S';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1554255044:
                        if (str.equals("vernee_M5")) {
                            c = 'l';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1481772737:
                        if (str.equals("panell_dl")) {
                            c = 'L';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1481772730:
                        if (str.equals("panell_ds")) {
                            c = 'M';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1481772729:
                        if (str.equals("panell_dt")) {
                            c = 'N';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1320080169:
                        if (str.equals("GiONEE_GBL7319")) {
                            c = '%';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1217592143:
                        if (str.equals("BRAVIA_ATV2")) {
                            c = '\r';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1180384755:
                        if (str.equals("iris60")) {
                            c = '5';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1139198265:
                        if (str.equals("Slate_Pro")) {
                            c = '`';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1052835013:
                        if (str.equals("namath")) {
                            c = 'C';
                            break;
                        }
                        c = 65535;
                        break;
                    case -993250464:
                        if (str.equals("A10-70F")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -965403638:
                        if (str.equals("s905x018")) {
                            c = 'b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -958336948:
                        if (str.equals("ELUGA_Ray_X")) {
                            c = 26;
                            break;
                        }
                        c = 65535;
                        break;
                    case -879245230:
                        if (str.equals("tcl_eu")) {
                            c = 'h';
                            break;
                        }
                        c = 65535;
                        break;
                    case -842500323:
                        if (str.equals("nicklaus_f")) {
                            c = 'D';
                            break;
                        }
                        c = 65535;
                        break;
                    case -821392978:
                        if (str.equals("A7000-a")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -797483286:
                        if (str.equals("SVP-DTV15")) {
                            c = 'a';
                            break;
                        }
                        c = 65535;
                        break;
                    case -794946968:
                        if (str.equals("watson")) {
                            c = 'm';
                            break;
                        }
                        c = 65535;
                        break;
                    case -788334647:
                        if (str.equals("whyred")) {
                            c = 'n';
                            break;
                        }
                        c = 65535;
                        break;
                    case -782144577:
                        if (str.equals("OnePlus5T")) {
                            c = 'G';
                            break;
                        }
                        c = 65535;
                        break;
                    case -575125681:
                        if (str.equals("GiONEE_CBL7513")) {
                            c = Typography.dollar;
                            break;
                        }
                        c = 65535;
                        break;
                    case -521118391:
                        if (str.equals("GIONEE_GBL7360")) {
                            c = Typography.amp;
                            break;
                        }
                        c = 65535;
                        break;
                    case -430914369:
                        if (str.equals("Pixi4-7_3G")) {
                            c = 'T';
                            break;
                        }
                        c = 65535;
                        break;
                    case -290434366:
                        if (str.equals("taido_row")) {
                            c = 'c';
                            break;
                        }
                        c = 65535;
                        break;
                    case -282781963:
                        if (str.equals("BLACK-1X")) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case -277133239:
                        if (str.equals("Z12_PRO")) {
                            c = 't';
                            break;
                        }
                        c = 65535;
                        break;
                    case -173639913:
                        if (str.equals("ELUGA_A3_Pro")) {
                            c = 23;
                            break;
                        }
                        c = 65535;
                        break;
                    case -56598463:
                        if (str.equals("woods_fn")) {
                            c = 'p';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2126:
                        if (str.equals("C1")) {
                            c = 14;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2564:
                        if (str.equals("Q5")) {
                            c = '\\';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2715:
                        if (str.equals("V1")) {
                            c = 'i';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2719:
                        if (str.equals("V5")) {
                            c = 'k';
                            break;
                        }
                        c = 65535;
                        break;
                    case 3483:
                        if (str.equals("mh")) {
                            c = '@';
                            break;
                        }
                        c = 65535;
                        break;
                    case 73405:
                        if (str.equals("JGZ")) {
                            c = '8';
                            break;
                        }
                        c = 65535;
                        break;
                    case 75739:
                        if (str.equals("M5c")) {
                            c = Typography.less;
                            break;
                        }
                        c = 65535;
                        break;
                    case 76779:
                        if (str.equals("MX6")) {
                            c = 'B';
                            break;
                        }
                        c = 65535;
                        break;
                    case 78669:
                        if (str.equals("P85")) {
                            c = 'J';
                            break;
                        }
                        c = 65535;
                        break;
                    case 79305:
                        if (str.equals("PLE")) {
                            c = 'V';
                            break;
                        }
                        c = 65535;
                        break;
                    case 80618:
                        if (str.equals("QX1")) {
                            c = '^';
                            break;
                        }
                        c = 65535;
                        break;
                    case 88274:
                        if (str.equals("Z80")) {
                            c = 'u';
                            break;
                        }
                        c = 65535;
                        break;
                    case 98846:
                        if (str.equals("cv1")) {
                            c = 19;
                            break;
                        }
                        c = 65535;
                        break;
                    case 98848:
                        if (str.equals("cv3")) {
                            c = 20;
                            break;
                        }
                        c = 65535;
                        break;
                    case 99329:
                        if (str.equals("deb")) {
                            c = 21;
                            break;
                        }
                        c = 65535;
                        break;
                    case 101481:
                        if (str.equals("flo")) {
                            c = '#';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1513190:
                        if (str.equals("1601")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1514184:
                        if (str.equals("1713")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1514185:
                        if (str.equals("1714")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2436959:
                        if (str.equals("P681")) {
                            c = 'I';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2463773:
                        if (str.equals("Q350")) {
                            c = 'X';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2464648:
                        if (str.equals("Q427")) {
                            c = 'Z';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2689555:
                        if (str.equals("XE2X")) {
                            c = 'r';
                            break;
                        }
                        c = 65535;
                        break;
                    case 3351335:
                        if (str.equals("mido")) {
                            c = 'A';
                            break;
                        }
                        c = 65535;
                        break;
                    case 3386211:
                        if (str.equals("p212")) {
                            c = 'H';
                            break;
                        }
                        c = 65535;
                        break;
                    case 41325051:
                        if (str.equals("MEIZU_M5")) {
                            c = '?';
                            break;
                        }
                        c = 65535;
                        break;
                    case 55178625:
                        if (str.equals("Aura_Note_2")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case 61542055:
                        if (str.equals("A1601")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 65355429:
                        if (str.equals("E5643")) {
                            c = 22;
                            break;
                        }
                        c = 65535;
                        break;
                    case 66214468:
                        if (str.equals("F3111")) {
                            c = 28;
                            break;
                        }
                        c = 65535;
                        break;
                    case 66214470:
                        if (str.equals("F3113")) {
                            c = 29;
                            break;
                        }
                        c = 65535;
                        break;
                    case 66214473:
                        if (str.equals("F3116")) {
                            c = 30;
                            break;
                        }
                        c = 65535;
                        break;
                    case 66215429:
                        if (str.equals("F3211")) {
                            c = 31;
                            break;
                        }
                        c = 65535;
                        break;
                    case 66215431:
                        if (str.equals("F3213")) {
                            c = ' ';
                            break;
                        }
                        c = 65535;
                        break;
                    case 66215433:
                        if (str.equals("F3215")) {
                            c = '!';
                            break;
                        }
                        c = 65535;
                        break;
                    case 66216390:
                        if (str.equals("F3311")) {
                            c = Typography.quote;
                            break;
                        }
                        c = 65535;
                        break;
                    case 76402249:
                        if (str.equals("PRO7S")) {
                            c = 'W';
                            break;
                        }
                        c = 65535;
                        break;
                    case 76404105:
                        if (str.equals("Q4260")) {
                            c = 'Y';
                            break;
                        }
                        c = 65535;
                        break;
                    case 76404911:
                        if (str.equals("Q4310")) {
                            c = '[';
                            break;
                        }
                        c = 65535;
                        break;
                    case 80963634:
                        if (str.equals("V23GB")) {
                            c = 'j';
                            break;
                        }
                        c = 65535;
                        break;
                    case 82882791:
                        if (str.equals("X3_HK")) {
                            c = 'q';
                            break;
                        }
                        c = 65535;
                        break;
                    case 102844228:
                        if (str.equals("le_x6")) {
                            c = ':';
                            break;
                        }
                        c = 65535;
                        break;
                    case 165221241:
                        if (str.equals("A2016a40")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 182191441:
                        if (str.equals("CPY83_I00")) {
                            c = 18;
                            break;
                        }
                        c = 65535;
                        break;
                    case 245388979:
                        if (str.equals("marino_f")) {
                            c = Typography.greater;
                            break;
                        }
                        c = 65535;
                        break;
                    case 287431619:
                        if (str.equals("griffin")) {
                            c = '-';
                            break;
                        }
                        c = 65535;
                        break;
                    case 307593612:
                        if (str.equals("A7010a48")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 308517133:
                        if (str.equals("A7020a48")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case 316215098:
                        if (str.equals("TB3-730F")) {
                            c = 'd';
                            break;
                        }
                        c = 65535;
                        break;
                    case 316215116:
                        if (str.equals("TB3-730X")) {
                            c = 'e';
                            break;
                        }
                        c = 65535;
                        break;
                    case 316246811:
                        if (str.equals("TB3-850F")) {
                            c = 'f';
                            break;
                        }
                        c = 65535;
                        break;
                    case 316246818:
                        if (str.equals("TB3-850M")) {
                            c = 'g';
                            break;
                        }
                        c = 65535;
                        break;
                    case 407160593:
                        if (str.equals("Pixi5-10_4G")) {
                            c = 'U';
                            break;
                        }
                        c = 65535;
                        break;
                    case 507412548:
                        if (str.equals("QM16XE_U")) {
                            c = ']';
                            break;
                        }
                        c = 65535;
                        break;
                    case 793982701:
                        if (str.equals("GIONEE_WBL5708")) {
                            c = '*';
                            break;
                        }
                        c = 65535;
                        break;
                    case 794038622:
                        if (str.equals("GIONEE_WBL7365")) {
                            c = '+';
                            break;
                        }
                        c = 65535;
                        break;
                    case 794040393:
                        if (str.equals("GIONEE_WBL7519")) {
                            c = ',';
                            break;
                        }
                        c = 65535;
                        break;
                    case 835649806:
                        if (str.equals("manning")) {
                            c = '=';
                            break;
                        }
                        c = 65535;
                        break;
                    case 917340916:
                        if (str.equals("A7000plus")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 958008161:
                        if (str.equals("j2xlteins")) {
                            c = '7';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1060579533:
                        if (str.equals("panell_d")) {
                            c = 'K';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1150207623:
                        if (str.equals("LS-5017")) {
                            c = ';';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1176899427:
                        if (str.equals("itel_S41")) {
                            c = '6';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1280332038:
                        if (str.equals("hwALE-H")) {
                            c = '/';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1306947716:
                        if (str.equals("EverStar_S")) {
                            break;
                        }
                        c = 65535;
                        break;
                    case 1349174697:
                        if (str.equals("htc_e56ml_dtul")) {
                            c = '.';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1522194893:
                        if (str.equals("woods_f")) {
                            c = 'o';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1691543273:
                        if (str.equals("CPH1609")) {
                            c = 17;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1709443163:
                        if (str.equals("iball8735_9806")) {
                            c = '3';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1865889110:
                        if (str.equals("santoni")) {
                            c = '_';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1906253259:
                        if (str.equals("PB2-670M")) {
                            c = 'O';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1977196784:
                        if (str.equals("Infinix-X572")) {
                            c = '4';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2029784656:
                        if (str.equals("HWBLN-H")) {
                            c = '0';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2030379515:
                        if (str.equals("HWCAM-H")) {
                            c = '1';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2047190025:
                        if (str.equals("ELUGA_Note")) {
                            c = 24;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2047252157:
                        if (str.equals("ELUGA_Prim")) {
                            c = 25;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2048319463:
                        if (str.equals("HWVNS-H")) {
                            c = '2';
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case '\b':
                    case '\t':
                    case '\n':
                    case 11:
                    case '\f':
                    case '\r':
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case ' ':
                    case '!':
                    case '\"':
                    case '#':
                    case '$':
                    case '%':
                    case '&':
                    case '\'':
                    case '(':
                    case ')':
                    case '*':
                    case '+':
                    case ',':
                    case '-':
                    case '.':
                    case '/':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case ':':
                    case ';':
                    case '<':
                    case '=':
                    case '>':
                    case '?':
                    case '@':
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'E':
                    case 'F':
                    case 'G':
                    case 'H':
                    case 'I':
                    case 'J':
                    case 'K':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'O':
                    case 'P':
                    case 'Q':
                    case 'R':
                    case 'S':
                    case 'T':
                    case 'U':
                    case 'V':
                    case 'W':
                    case 'X':
                    case 'Y':
                    case 'Z':
                    case '[':
                    case '\\':
                    case ']':
                    case '^':
                    case '_':
                    case '`':
                    case 'a':
                    case 'b':
                    case 'c':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'g':
                    case 'h':
                    case 'i':
                    case 'j':
                    case 'k':
                    case 'l':
                    case 'm':
                    case 'n':
                    case 'o':
                    case 'p':
                    case 'q':
                    case 'r':
                    case 's':
                    case 't':
                    case 'u':
                        deviceNeedsSetOutputSurfaceWorkaround = true;
                        break;
                }
                String str2 = Util.MODEL;
                switch (str2.hashCode()) {
                    case 2006354:
                        if (str2.equals("AFTA")) {
                            break;
                        }
                        z = true;
                        break;
                    case 2006367:
                        if (str2.equals("AFTN")) {
                            z = true;
                            break;
                        }
                        z = true;
                        break;
                    default:
                        z = true;
                        break;
                }
                switch (z) {
                    case false:
                    case true:
                        deviceNeedsSetOutputSurfaceWorkaround = true;
                        break;
                }
                evaluatedDeviceNeedsSetOutputSurfaceWorkaround = true;
            }
        }
        return deviceNeedsSetOutputSurfaceWorkaround;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int width, int height, int inputSize) {
            this.width = width;
            this.height = height;
            this.inputSize = inputSize;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    /* loaded from: classes.dex */
    public final class OnFrameRenderedListenerV23 implements MediaCodec.OnFrameRenderedListener {
        private OnFrameRenderedListenerV23(MediaCodec codec) {
            codec.setOnFrameRenderedListener(this, new Handler());
        }

        @Override // android.media.MediaCodec.OnFrameRenderedListener
        public void onFrameRendered(@NonNull MediaCodec codec, long presentationTimeUs, long nanoTime) {
            if (this == MediaCodecVideoRenderer.this.tunnelingOnFrameRenderedListener) {
                MediaCodecVideoRenderer.this.maybeNotifyRenderedFirstFrame();
            }
        }
    }
}
