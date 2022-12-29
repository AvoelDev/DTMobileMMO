package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.UByte;

/* loaded from: classes.dex */
final class FloatResamplingAudioProcessor implements AudioProcessor {
    private static final int FLOAT_NAN_AS_INT = Float.floatToIntBits(Float.NaN);
    private static final double PCM_32_BIT_INT_TO_PCM_32_BIT_FLOAT_FACTOR = 4.656612875245797E-10d;
    private boolean inputEnded;
    private int sampleRateHz = -1;
    private int channelCount = -1;
    private int sourceEncoding = 0;
    private ByteBuffer buffer = EMPTY_BUFFER;
    private ByteBuffer outputBuffer = EMPTY_BUFFER;

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean configure(int sampleRateHz, int channelCount, int encoding) throws AudioProcessor.UnhandledFormatException {
        if (!Util.isEncodingHighResolutionIntegerPcm(encoding)) {
            throw new AudioProcessor.UnhandledFormatException(sampleRateHz, channelCount, encoding);
        }
        if (this.sampleRateHz == sampleRateHz && this.channelCount == channelCount && this.sourceEncoding == encoding) {
            return false;
        }
        this.sampleRateHz = sampleRateHz;
        this.channelCount = channelCount;
        this.sourceEncoding = encoding;
        return true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public boolean isActive() {
        return Util.isEncodingHighResolutionIntegerPcm(this.sourceEncoding);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public int getOutputChannelCount() {
        return this.channelCount;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public int getOutputEncoding() {
        return 4;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public int getOutputSampleRateHz() {
        return this.sampleRateHz;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer inputBuffer) {
        boolean isInput32Bit = this.sourceEncoding == 1073741824;
        int position = inputBuffer.position();
        int limit = inputBuffer.limit();
        int size = limit - position;
        int resampledSize = isInput32Bit ? size : (size / 3) * 4;
        if (this.buffer.capacity() < resampledSize) {
            this.buffer = ByteBuffer.allocateDirect(resampledSize).order(ByteOrder.nativeOrder());
        } else {
            this.buffer.clear();
        }
        if (isInput32Bit) {
            for (int i = position; i < limit; i += 4) {
                int pcm32BitInteger = (inputBuffer.get(i) & UByte.MAX_VALUE) | ((inputBuffer.get(i + 1) & UByte.MAX_VALUE) << 8) | ((inputBuffer.get(i + 2) & UByte.MAX_VALUE) << 16) | ((inputBuffer.get(i + 3) & UByte.MAX_VALUE) << 24);
                writePcm32BitFloat(pcm32BitInteger, this.buffer);
            }
        } else {
            for (int i2 = position; i2 < limit; i2 += 3) {
                int pcm32BitInteger2 = ((inputBuffer.get(i2) & UByte.MAX_VALUE) << 8) | ((inputBuffer.get(i2 + 1) & UByte.MAX_VALUE) << 16) | ((inputBuffer.get(i2 + 2) & UByte.MAX_VALUE) << 24);
                writePcm32BitFloat(pcm32BitInteger2, this.buffer);
            }
        }
        inputBuffer.position(inputBuffer.limit());
        this.buffer.flip();
        this.outputBuffer = this.buffer;
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
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void reset() {
        flush();
        this.sampleRateHz = -1;
        this.channelCount = -1;
        this.sourceEncoding = 0;
        this.buffer = EMPTY_BUFFER;
    }

    private static void writePcm32BitFloat(int pcm32BitInt, ByteBuffer buffer) {
        float pcm32BitFloat = (float) (PCM_32_BIT_INT_TO_PCM_32_BIT_FLOAT_FACTOR * pcm32BitInt);
        int floatBits = Float.floatToIntBits(pcm32BitFloat);
        if (floatBits == FLOAT_NAN_AS_INT) {
            floatBits = Float.floatToIntBits(0.0f);
        }
        buffer.putInt(floatBits);
    }
}
