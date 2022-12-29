package com.google.android.gms.games;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class FriendsResolutionRequiredException extends ResolvableApiException {
    private FriendsResolutionRequiredException(Status status) {
        super(status);
    }

    public static FriendsResolutionRequiredException zza(Status status) {
        return new FriendsResolutionRequiredException(status);
    }
}
