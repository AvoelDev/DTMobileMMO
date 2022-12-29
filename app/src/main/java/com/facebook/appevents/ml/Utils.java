package com.facebook.appevents.ml;

import android.text.TextUtils;
import com.facebook.FacebookSdk;
import com.facebook.share.internal.ShareInternalUtility;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Utils.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\n\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u001e\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\f\u001a\u00020\u0006H\u0007J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/facebook/appevents/ml/Utils;", "", "()V", "DIR_NAME", "", "getMlDir", "Ljava/io/File;", "normalizeString", "str", "parseModelWeights", "", "Lcom/facebook/appevents/ml/MTensor;", ShareInternalUtility.STAGING_PARAM, "vectorize", "", "texts", "maxLen", "", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class Utils {
    private static final String DIR_NAME = "facebook_ml/";
    public static final Utils INSTANCE = new Utils();

    private Utils() {
    }

    public final int[] vectorize(String texts, int i) {
        Intrinsics.checkNotNullParameter(texts, "texts");
        int[] iArr = new int[i];
        String normalizeString = normalizeString(texts);
        Charset forName = Charset.forName("UTF-8");
        Intrinsics.checkNotNullExpressionValue(forName, "forName(\"UTF-8\")");
        if (normalizeString != null) {
            byte[] bytes = normalizeString.getBytes(forName);
            Intrinsics.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
            if (i > 0) {
                int i2 = 0;
                while (true) {
                    int i3 = i2 + 1;
                    if (i2 < bytes.length) {
                        iArr[i2] = bytes[i2] & UByte.MAX_VALUE;
                    } else {
                        iArr[i2] = 0;
                    }
                    if (i3 >= i) {
                        break;
                    }
                    i2 = i3;
                }
            }
            return iArr;
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
    }

    @JvmStatic
    public static final File getMlDir() {
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        File filesDir = FacebookSdk.getApplicationContext().getFilesDir();
        Utils utils = INSTANCE;
        File file = new File(filesDir, DIR_NAME);
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        return null;
    }

    @JvmStatic
    public static final Map<String, MTensor> parseModelWeights(File file) {
        int i;
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int available = fileInputStream.available();
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            byte[] bArr = new byte[available];
            dataInputStream.readFully(bArr);
            dataInputStream.close();
            if (available < 4) {
                return null;
            }
            ByteBuffer wrap = ByteBuffer.wrap(bArr, 0, 4);
            wrap.order(ByteOrder.LITTLE_ENDIAN);
            int i2 = wrap.getInt();
            int i3 = i2 + 4;
            if (available < i3) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(new String(bArr, 4, i2, Charsets.UTF_8));
            JSONArray names = jSONObject.names();
            String[] strArr = new String[names.length()];
            int length = strArr.length - 1;
            if (length >= 0) {
                int i4 = 0;
                while (true) {
                    int i5 = i4 + 1;
                    strArr[i4] = names.getString(i4);
                    if (i5 > length) {
                        break;
                    }
                    i4 = i5;
                }
            }
            ArraysKt.sort((Object[]) strArr);
            HashMap hashMap = new HashMap();
            int length2 = strArr.length;
            int i6 = i3;
            int i7 = 0;
            while (i7 < length2) {
                String str = strArr[i7];
                i7++;
                if (str != null) {
                    JSONArray jSONArray = jSONObject.getJSONArray(str);
                    int[] iArr = new int[jSONArray.length()];
                    int length3 = iArr.length - 1;
                    if (length3 >= 0) {
                        int i8 = 0;
                        i = 1;
                        while (true) {
                            int i9 = i8 + 1;
                            iArr[i8] = jSONArray.getInt(i8);
                            i *= iArr[i8];
                            if (i9 > length3) {
                                break;
                            }
                            i8 = i9;
                        }
                    } else {
                        i = 1;
                    }
                    int i10 = i * 4;
                    int i11 = i6 + i10;
                    if (i11 > available) {
                        return null;
                    }
                    ByteBuffer wrap2 = ByteBuffer.wrap(bArr, i6, i10);
                    wrap2.order(ByteOrder.LITTLE_ENDIAN);
                    MTensor mTensor = new MTensor(iArr);
                    wrap2.asFloatBuffer().get(mTensor.getData(), 0, i);
                    hashMap.put(str, mTensor);
                    i6 = i11;
                }
            }
            return hashMap;
        } catch (Exception unused) {
            return null;
        }
    }

    public final String normalizeString(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        String str2 = str;
        int length = str2.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = Intrinsics.compare((int) str2.charAt(!z ? i : length), 32) <= 0;
            if (z) {
                if (!z2) {
                    break;
                }
                length--;
            } else if (z2) {
                i++;
            } else {
                z = true;
            }
        }
        Object[] array = new Regex("\\s+").split(str2.subSequence(i, length + 1).toString(), 0).toArray(new String[0]);
        if (array != null) {
            String join = TextUtils.join(" ", (String[]) array);
            Intrinsics.checkNotNullExpressionValue(join, "join(\" \", strArray)");
            return join;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }
}
