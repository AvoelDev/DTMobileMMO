package com.appsflyer.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.facebook.internal.AttributionIdentifiers;
import com.ssjj.fnsdk.core.FnDtProvider;

/* loaded from: classes3.dex */
final class ah extends aw<String> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public ah(Context context) {
        super(context, AttributionIdentifiers.ATTRIBUTION_ID_CONTENT_PROVIDER, "E3F9E1E0CF99D0E56A055BA65E241B3399F7CEA524326B0CDD6EC1327ED0FDC1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.appsflyer.internal.aw
    /* renamed from: valueOf */
    public String AFKeystoreWrapper() {
        Cursor cursor = null;
        try {
            ContentResolver contentResolver = this.values.getContentResolver();
            StringBuilder sb = new StringBuilder(FnDtProvider.PROVIDER_PROTOCOL);
            sb.append(this.valueOf);
            Cursor query = contentResolver.query(Uri.parse(sb.toString()), new String[]{"aid"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndex("aid"));
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final String AFInAppEventParameterName() {
        new Thread(this.AFInAppEventParameterName).start();
        return (String) super.AFInAppEventType();
    }

    @Override // com.appsflyer.internal.aw
    public final /* synthetic */ String AFInAppEventType() {
        new Thread(this.AFInAppEventParameterName).start();
        return (String) super.AFInAppEventType();
    }
}
