package com.google.android.exoplayer2.audio;

import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/* loaded from: classes.dex */
public final class SonicAudioProcessor implements AudioProcessor {
    private static final float CLOSE_THRESHOLD = 0.01f;
    public static final float MAXIMUM_PITCH = 8.0f;
    public static final float MAXIMUM_SPEED = 8.0f;
    public static final float MINIMUM_PITCH = 0.1f;
    public static final float MINIMUM_SPEED = 0.1f;
    private static final int MIN_BYTES_FOR_SPEEDUP_CALCULATION = 1024;
    public static final int SAMPLE_RATE_NO_CHANGE = -1;
    private long inputBytes;
    private boolean inputEnded;
    private long outputBytes;
    @Nullable
    private Sonic sonic;
    private float speed = 1.0f;
    private float pitch = 1.0f;
    private int channelCount = -1;
    private int sampleRateHz = -1;
    private int outputSampleRateHz = -1;
    private ByteBuffer buffer = EMPTY_BUFFER;
    private ShortBuffer shortBuffer = this.buffer.asShortBuffer();
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    private int pendingOutputSampleRateHz = -1;

    public float setSpeed(float speed) {
        float speed2 = Util.constrainValue(speed, 0.1f, 8.0f);
        if (this.speed != speed2) {
            this.speed = speed2;
            this.sonic = null;
        }
        flush();
        return speed2;
    }

    public float setPitch(float pitch) {
        float pitch2 = Util.constrainValue(pitch, 0.1f, 8.0f);
        if (this.pitch != pitch2) {
            this.pitch = pitch2;
            this.sonic = null;
        }
        flush();
        return pitch2;
    }

    public void setOutputSampleRateHz(int sampleRateHz) {
        this.pendingOutputSampleRateHz = sampleRateHz;
    }

    public long scaleDurationForSpeedup(long duration) {
        if (this.outputBytes >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            if (this.outputSampleRateHz == this.sampleRateHz) {
                return Util.scaleLargeTimestamp(duration, this.inputBytes, this.outputBytes);
            }
            return Util.scaleLargeTimestamp(duration, this.outputSampleRateHz * this.inputBytes, this.sampleRateHz * this.outputBytes);
        }
        return (long) (this.speed * duration);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor.UnhandledFormatException {
        if (encoding != 2) {
            throw new AudioProcessor.UnhandledFormatException(sampleRateHz, channelCount, encoding);
        }
        int outputSampleRateHz = this.pendingOutputSampleRateHz == -1 ? sampleRateHz : this.pendingOutputSampleRateHz;
        if (this.sampleRateHz == sampleRateHz && this.channelCount == channelCount && this.outputSampleRateHz == outputSampleRateHz) {
            return false;
        }
        this.sampleRateHz = sampleRateHz;
        this.channelCount = channelCount;
        this.outputSampleRateHz = outputSampleRateHz;
        this.sonic = null;
        return true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isActive() {
        return this.sampleRateHz != -1 && (Math.abs(this.speed - 1.0f) >= CLOSE_THRESHOLD || Math.abs(this.pitch - 1.0f) >= CLOSE_THRESHOLD || this.outputSampleRateHz != this.sampleRateHz);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public int getOutputChannelCount() {
        return this.channelCount;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public int getOutputEncoding() {
        return 2;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public int getOutputSampleRateHz() {
        return this.outputSampleRateHz;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer inputBuffer) {
        Assertions.checkState(this.sonic != null);
        if (inputBuffer.hasRemaining()) {
            ShortBuffer shortBuffer = inputBuffer.asShortBuffer();
            int inputSize = inputBuffer.remaining();
            this.inputBytes += inputSize;
            this.sonic.queueInput(shortBuffer);
            inputBuffer.position(inputBuffer.position() + inputSize);
        }
        int outputSize = this.sonic.getFramesAvailable() * this.channelCount * 2;
        if (outputSize > 0) {
            if (this.buffer.capacity() < outputSize) {
                this.buffer = ByteBuffer.allocateDirect(outputSize).order(ByteOrder.nativeOrder());
                this.shortBuffer = this.buffer.asShortBuffer();
            } else {
                this.buffer.clear();
                this.shortBuffer.clear();
            }
            this.sonic.getOutput(this.shortBuffer);
            this.outputBytes += outputSize;
            this.buffer.limit(outputSize);
            this.outputBuffer = this.buffer;
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueEndOfStream() {
        Assertions.checkState(this.sonic != null);
        this.sonic.queueEndOfStream();
        this.inputEnded = true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public ByteBuffer getOutput() {
        ByteBuffer outputBuffer = this.outputBuffer;
        this.outputBuffer = EMPTY_BUFFER;
        return outputBuffer;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isEnded() {
        return this.inputEnded && (this.sonic == null || this.sonic.getFramesAvailable() == 0);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void flush() {
        if (isActive()) {
            if (this.sonic == null) {
                this.sonic = new Sonic(this.sampleRateHz, this.channelCount, this.speed, this.pitch, this.outputSampleRateHz);
            } else {
                this.sonic.flush();
            }
        }
        this.outputBuffer = EMPTY_BUFFER;
        this.inputBytes = 0L;
        this.outputBytes = 0L;
        this.inputEnded = false;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void reset() {
        this.speed = 1.0f;
        this.pitch = 1.0f;
        this.channelCount = -1;
        this.sampleRateHz = -1;
        this.outputSampleRateHz = -1;
        this.buffer = EMPTY_BUFFER;
        this.shortBuffer = this.buffer.asShortBuffer();
        this.outputBuffer = EMPTY_BUFFER;
        this.pendingOutputSampleRateHz = -1;
        this.sonic = null;
        this.inputBytes = 0L;
        this.outputBytes = 0L;
        this.inputEnded = false;
    }
}
