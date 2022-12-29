package com.facebook;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import org.json.JSONObject;

/* compiled from: lambda */
/* renamed from: com.facebook.-$$Lambda$FacebookSdk$2ro22SgLIEGEQvscKl0ZvNzqZbA  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FacebookSdk$2ro22SgLIEGEQvscKl0ZvNzqZbA implements FacebookSdk.GraphRequestCreator {
    public static final /* synthetic */ $$Lambda$FacebookSdk$2ro22SgLIEGEQvscKl0ZvNzqZbA INSTANCE = new $$Lambda$FacebookSdk$2ro22SgLIEGEQvscKl0ZvNzqZbA();

    private /* synthetic */ $$Lambda$FacebookSdk$2ro22SgLIEGEQvscKl0ZvNzqZbA() {
    }

    @Override // com.facebook.FacebookSdk.GraphRequestCreator
    public final GraphRequest createPostRequest(AccessToken accessToken, String str, JSONObject jSONObject, GraphRequest.Callback callback) {
        GraphRequest m17graphRequestCreator$lambda0;
        m17graphRequestCreator$lambda0 = FacebookSdk.m17graphRequestCreator$lambda0(accessToken, str, jSONObject, callback);
        return m17graphRequestCreator$lambda0;
    }
}
