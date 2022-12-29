package com.google.android.exoplayer2.text.cea;

import android.util.Log;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class CeaUtil {
    private static final int COUNTRY_CODE = 181;
    private static final int PAYLOAD_TYPE_CC = 4;
    private static final int PROVIDER_CODE_ATSC = 49;
    private static final int PROVIDER_CODE_DIRECTV = 47;
    private static final String TAG = "CeaUtil";
    private static final int USER_DATA_TYPE_CODE = 3;
    private static final int USER_ID_GA94 = Util.getIntegerCodeForString("GA94");
    private static final int USER_ID_DTG1 = Util.getIntegerCodeForString("DTG1");

    public static void consume(long presentationTimeUs, ParsableByteArray seiBuffer, TrackOutput[] outputs) {
        while (seiBuffer.bytesLeft() > 1) {
            int payloadType = readNon255TerminatedValue(seiBuffer);
            int payloadSize = readNon255TerminatedValue(seiBuffer);
            int nextPayloadPosition = seiBuffer.getPosition() + payloadSize;
            if (payloadSize == -1 || payloadSize > seiBuffer.bytesLeft()) {
                Log.w(TAG, "Skipping remainder of malformed SEI NAL unit.");
                nextPayloadPosition = seiBuffer.limit();
            } else if (payloadType == 4 && payloadSize >= 8) {
                int countryCode = seiBuffer.readUnsignedByte();
                int providerCode = seiBuffer.readUnsignedShort();
                int userIdentifier = 0;
                if (providerCode == 49) {
                    userIdentifier = seiBuffer.readInt();
                }
                int userDataTypeCode = seiBuffer.readUnsignedByte();
                if (providerCode == 47) {
                    seiBuffer.skipBytes(1);
                }
                boolean messageIsSupportedCeaCaption = countryCode == COUNTRY_CODE && (providerCode == 49 || providerCode == 47) && userDataTypeCode == 3;
                if (providerCode == 49) {
                    messageIsSupportedCeaCaption &= userIdentifier == USER_ID_GA94 || userIdentifier == USER_ID_DTG1;
                }
                if (messageIsSupportedCeaCaption) {
                    int ccCount = seiBuffer.readUnsignedByte() & 31;
                    seiBuffer.skipBytes(1);
                    int sampleLength = ccCount * 3;
                    int sampleStartPosition = seiBuffer.getPosition();
                    int length = outputs.length;
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 < length) {
                            TrackOutput output = outputs[i2];
                            seiBuffer.setPosition(sampleStartPosition);
                            output.sampleData(seiBuffer, sampleLength);
                            output.sampleMetadata(presentationTimeUs, 1, sampleLength, 0, null);
                            i = i2 + 1;
                        }
                    }
                }
            }
            seiBuffer.setPosition(nextPayloadPosition);
        }
    }

    private static int readNon255TerminatedValue(ParsableByteArray buffer) {
        int value = 0;
        while (buffer.bytesLeft() != 0) {
            int b = buffer.readUnsignedByte();
            value += b;
            if (b != 255) {
                return value;
            }
        }
        return -1;
    }

    private CeaUtil() {
    }
}
