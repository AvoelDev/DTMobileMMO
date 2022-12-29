package com.google.android.exoplayer2.drm;

import android.annotation.TargetApi;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.httpclient.methods.multipart.FilePart;

@TargetApi(18)
/* loaded from: classes.dex */
public final class HttpMediaDrmCallback implements MediaDrmCallback {
    private static final int MAX_MANUAL_REDIRECTS = 5;
    private final HttpDataSource.Factory dataSourceFactory;
    private final String defaultLicenseUrl;
    private final boolean forceDefaultLicenseUrl;
    private final Map<String, String> keyRequestProperties;

    public HttpMediaDrmCallback(String defaultLicenseUrl, HttpDataSource.Factory dataSourceFactory) {
        this(defaultLicenseUrl, false, dataSourceFactory);
    }

    public HttpMediaDrmCallback(String defaultLicenseUrl, boolean forceDefaultLicenseUrl, HttpDataSource.Factory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
        this.defaultLicenseUrl = defaultLicenseUrl;
        this.forceDefaultLicenseUrl = forceDefaultLicenseUrl;
        this.keyRequestProperties = new HashMap();
    }

    public void setKeyRequestProperty(String name, String value) {
        Assertions.checkNotNull(name);
        Assertions.checkNotNull(value);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.put(name, value);
        }
    }

    public void clearKeyRequestProperty(String name) {
        Assertions.checkNotNull(name);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.remove(name);
        }
    }

    public void clearAllKeyRequestProperties() {
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.clear();
        }
    }

    @Override // com.google.android.exoplayer2.drm.MediaDrmCallback
    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest request) throws IOException {
        String url = request.getDefaultUrl() + "&signedRequest=" + Util.fromUtf8Bytes(request.getData());
        return executePost(this.dataSourceFactory, url, new byte[0], null);
    }

    @Override // com.google.android.exoplayer2.drm.MediaDrmCallback
    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest request, @Nullable String mediaProvidedLicenseServerUrl) throws Exception {
        String contentType;
        String url = request.getDefaultUrl();
        if (TextUtils.isEmpty(url)) {
            url = mediaProvidedLicenseServerUrl;
        }
        if (this.forceDefaultLicenseUrl || TextUtils.isEmpty(url)) {
            url = this.defaultLicenseUrl;
        }
        Map<String, String> requestProperties = new HashMap<>();
        if (C.PLAYREADY_UUID.equals(uuid)) {
            contentType = "text/xml";
        } else {
            contentType = C.CLEARKEY_UUID.equals(uuid) ? "application/json" : FilePart.DEFAULT_CONTENT_TYPE;
        }
        requestProperties.put("Content-Type", contentType);
        if (C.PLAYREADY_UUID.equals(uuid)) {
            requestProperties.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
        }
        synchronized (this.keyRequestProperties) {
            requestProperties.putAll(this.keyRequestProperties);
        }
        return executePost(this.dataSourceFactory, url, request.getData(), requestProperties);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0062 A[Catch: all -> 0x0069, TryCatch #1 {all -> 0x0069, InvalidResponseCodeException -> 0x004b, blocks: (B:10:0x0043, B:14:0x004c, B:16:0x0052, B:22:0x0062, B:24:0x0068, B:18:0x0058), top: B:32:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0075 A[DONT_GENERATE, LOOP:1: B:9:0x002b->B:31:0x0075, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0068 A[EDGE_INSN: B:37:0x0068->B:24:0x0068 ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static byte[] executePost(com.google.android.exoplayer2.upstream.HttpDataSource.Factory r18, java.lang.String r19, byte[] r20, java.util.Map<java.lang.String, java.lang.String> r21) throws java.io.IOException {
        /*
            com.google.android.exoplayer2.upstream.HttpDataSource r0 = r18.createDataSource()
            if (r21 == 0) goto L2a
            java.util.Set r2 = r21.entrySet()
            java.util.Iterator r4 = r2.iterator()
        Le:
            boolean r2 = r4.hasNext()
            if (r2 == 0) goto L2a
            java.lang.Object r17 = r4.next()
            java.util.Map$Entry r17 = (java.util.Map.Entry) r17
            java.lang.Object r2 = r17.getKey()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r3 = r17.getValue()
            java.lang.String r3 = (java.lang.String) r3
            r0.setRequestProperty(r2, r3)
            goto Le
        L2a:
            r14 = 0
        L2b:
            com.google.android.exoplayer2.upstream.DataSpec r1 = new com.google.android.exoplayer2.upstream.DataSpec
            android.net.Uri r2 = android.net.Uri.parse(r19)
            r4 = 0
            r6 = 0
            r8 = -1
            r10 = 0
            r11 = 1
            r3 = r20
            r1.<init>(r2, r3, r4, r6, r8, r10, r11)
            com.google.android.exoplayer2.upstream.DataSourceInputStream r13 = new com.google.android.exoplayer2.upstream.DataSourceInputStream
            r13.<init>(r0, r1)
            byte[] r2 = com.google.android.exoplayer2.util.Util.toByteArray(r13)     // Catch: com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException -> L4b java.lang.Throwable -> L69
            com.google.android.exoplayer2.util.Util.closeQuietly(r13)
            return r2
        L4b:
            r12 = move-exception
            int r2 = r12.responseCode     // Catch: java.lang.Throwable -> L69
            r3 = 307(0x133, float:4.3E-43)
            if (r2 == r3) goto L58
            int r2 = r12.responseCode     // Catch: java.lang.Throwable -> L69
            r3 = 308(0x134, float:4.32E-43)
            if (r2 != r3) goto L6f
        L58:
            int r15 = r14 + 1
            r2 = 5
            if (r14 >= r2) goto L6e
            r16 = 1
            r14 = r15
        L60:
            if (r16 == 0) goto L72
            java.lang.String r19 = getRedirectUrl(r12)     // Catch: java.lang.Throwable -> L69
        L66:
            if (r19 != 0) goto L75
            throw r12     // Catch: java.lang.Throwable -> L69
        L69:
            r2 = move-exception
            com.google.android.exoplayer2.util.Util.closeQuietly(r13)
            throw r2
        L6e:
            r14 = r15
        L6f:
            r16 = 0
            goto L60
        L72:
            r19 = 0
            goto L66
        L75:
            com.google.android.exoplayer2.util.Util.closeQuietly(r13)
            goto L2b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.HttpMediaDrmCallback.executePost(com.google.android.exoplayer2.upstream.HttpDataSource$Factory, java.lang.String, byte[], java.util.Map):byte[]");
    }

    private static String getRedirectUrl(HttpDataSource.InvalidResponseCodeException exception) {
        List<String> locationHeaders;
        Map<String, List<String>> headerFields = exception.headerFields;
        if (headerFields == null || (locationHeaders = headerFields.get("Location")) == null || locationHeaders.isEmpty()) {
            return null;
        }
        return locationHeaders.get(0);
    }
}
