package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
final class TrimmingAudioProcessor implements AudioProcessor {
    private int endBufferSize;
    private boolean inputEnded;
    private boolean isActive;
    private int pendingTrimStartBytes;
    private int trimEndFrames;
    private int trimStartFrames;
    private ByteBuffer buffer = EMPTY_BUFFER;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;
    private int channelCount = -1;
    private int sampleRateHz = -1;
    private byte[] endBuffer = new byte[0];

    public void setTrimFrameCount(int trimStartFrames, int trimEndFrames) {
        this.trimStartFrames = trimStartFrames;
        this.trimEndFrames = trimEndFrames;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor.UnhandledFormatException {
        if (encoding != 2) {
            throw new AudioProcessor.UnhandledFormatException(sampleRateHz, channelCount, encoding);
        }
        this.channelCount = channelCount;
        this.sampleRateHz = sampleRateHz;
        this.endBuffer = new byte[this.trimEndFrames * channelCount * 2];
        this.endBufferSize = 0;
        this.pendingTrimStartBytes = this.trimStartFrames * channelCount * 2;
        boolean wasActive = this.isActive;
        this.isActive = (this.trimStartFrames == 0 && this.trimEndFrames == 0) ? false : true;
        return wasActive != this.isActive;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isActive() {
        return this.isActive;
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
        return this.sampleRateHz;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer inputBuffer) {
        int position = inputBuffer.position();
        int limit = inputBuffer.limit();
        int remaining = limit - position;
        int trimBytes = Math.min(remaining, this.pendingTrimStartBytes);
        this.pendingTrimStartBytes -= trimBytes;
        inputBuffer.position(position + trimBytes);
        if (this.pendingTrimStartBytes <= 0) {
            int remaining2 = remaining - trimBytes;
            int remainingBytesToOutput = (this.endBufferSize + remaining2) - this.endBuffer.length;
            if (this.buffer.capacity() < remainingBytesToOutput) {
                this.buffer = ByteBuffer.allocateDirect(remainingBytesToOutput).order(ByteOrder.nativeOrder());
            } else {
                this.buffer.clear();
            }
            int endBufferBytesToOutput = Util.constrainValue(remainingBytesToOutput, 0, this.endBufferSize);
            this.buffer.put(this.endBuffer, 0, endBufferBytesToOutput);
            int inputBufferBytesToOutput = Util.constrainValue(remainingBytesToOutput - endBufferBytesToOutput, 0, remaining2);
            inputBuffer.limit(inputBuffer.position() + inputBufferBytesToOutput);
            this.buffer.put(inputBuffer);
            inputBuffer.limit(limit);
            int remaining3 = remaining2 - inputBufferBytesToOutput;
            this.endBufferSize -= endBufferBytesToOutput;
            System.arraycopy(this.endBuffer, endBufferBytesToOutput, this.endBuffer, 0, this.endBufferSize);
            inputBuffer.get(this.endBuffer, this.endBufferSize, remaining3);
            this.endBufferSize += remaining3;
            this.buffer.flip();
            this.outputBuffer = this.buffer;
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueEndOfStream() {
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
        return this.inputEnded && this.outputBuffer == EMPTY_BUFFER;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void flush() {
        this.outputBuffer = EMPTY_BUFFER;
        this.inputEnded = false;
        this.pendingTrimStartBytes = 0;
        this.endBufferSize = 0;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void reset() {
        flush();
        this.buffer = EMPTY_BUFFER;
        this.channelCount = -1;
        this.sampleRateHz = -1;
        this.endBuffer = new byte[0];
    }
}
