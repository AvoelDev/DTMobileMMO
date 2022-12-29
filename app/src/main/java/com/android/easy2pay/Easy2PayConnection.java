package com.android.easy2pay;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.openid.appauth.AuthState;

/* loaded from: classes3.dex */
class Easy2PayConnection {
    protected static final int ERROR_HTTP_CONNECTION_FAIL = 302;
    protected static final int ERROR_HTTP_RESPONSE_CODE_NOT_HTTP_OK = 301;
    private Easy2PayConnectionListener listener;

    /* JADX INFO: Access modifiers changed from: protected */
    public Easy2PayConnection(Easy2PayConnectionListener easy2PayConnectionListener) {
        this.listener = null;
        this.listener = easy2PayConnectionListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void get(final String str, final String[] strArr, final String str2) {
        new Thread(new Runnable() { // from class: com.android.easy2pay.Easy2PayConnection.1
            @Override // java.lang.Runnable
            public void run() {
                Easy2PayConnection.this.doGet(str, strArr, str2);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doGet(String str, String[] strArr, String str2) {
        HttpURLConnection httpURLConnection;
        HttpURLConnection httpURLConnection2 = null;
        r1 = null;
        String str3 = null;
        httpURLConnection2 = null;
        try {
            try {
                try {
                    httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
                    try {
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setConnectTimeout(AuthState.EXPIRY_TIME_TOLERANCE_MS);
                        httpURLConnection.setReadTimeout(AuthState.EXPIRY_TIME_TOLERANCE_MS);
                        httpURLConnection.setAllowUserInteraction(false);
                        httpURLConnection.setInstanceFollowRedirects(true);
                        httpURLConnection.setUseCaches(false);
                        if (httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            while (true) {
                                int read = inputStream.read();
                                if (read == -1) {
                                    break;
                                }
                                byteArrayOutputStream.write(read);
                            }
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                            byteArrayOutputStream.close();
                            if (this.listener != null) {
                                this.listener.onReceive(str, strArr, byteArray);
                            }
                            str3 = byteArrayOutputStream2;
                        } else if (this.listener != null) {
                            Easy2PayConnectionListener easy2PayConnectionListener = this.listener;
                            easy2PayConnectionListener.onError(str, strArr, 301, "HTTP RESPONSE CODE : " + httpURLConnection.getResponseCode() + "\ndescription : The requested resource (" + str2 + ") is not available.");
                        }
                        Log.w("E2P Connection", "GET " + str2);
                        Log.w("E2P Connection", "RESPONSE " + str3 + "\n\n");
                        httpURLConnection.disconnect();
                    } catch (Exception e) {
                        e = e;
                        httpURLConnection2 = httpURLConnection;
                        if (this.listener != null) {
                            this.listener.onError(str, strArr, 302, e.getMessage());
                        }
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        if (httpURLConnection != null) {
                            try {
                                httpURLConnection.disconnect();
                            } catch (Exception unused) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    httpURLConnection = httpURLConnection2;
                }
            } catch (Exception e2) {
                e = e2;
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (Exception unused2) {
        }
    }
}
