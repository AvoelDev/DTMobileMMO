package com.appsflyer;

import com.appsflyer.internal.bb;
import com.google.firebase.messaging.FirebaseMessagingService;

/* loaded from: classes3.dex */
public class FirebaseMessagingServiceListener extends FirebaseMessagingService {
    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onNewToken(String str) {
        new bb(getApplicationContext()).valueOf(str);
    }
}
