package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.logging.Logger;

/* compiled from: com.google.android.gms:play-services-auth@@20.3.0 */
/* loaded from: classes3.dex */
public final class zbm {
    private static final Logger zba = new Logger("GoogleSignInCommon", new String[0]);

    public static Intent zba(Context context, GoogleSignInOptions googleSignInOptions) {
        zba.d("getFallbackSignInIntent()", new Object[0]);
        Intent zbc = zbc(context, googleSignInOptions);
        zbc.setAction("com.google.android.gms.auth.APPAUTH_SIGN_IN");
        return zbc;
    }

    public static Intent zbb(Context context, GoogleSignInOptions googleSignInOptions) {
        zba.d("getNoImplementationSignInIntent()", new Object[0]);
        Intent zbc = zbc(context, googleSignInOptions);
        zbc.setAction("com.google.android.gms.auth.NO_IMPL");
        return zbc;
    }

    public static Intent zbc(Context context, GoogleSignInOptions googleSignInOptions) {
        zba.d("getSignInIntent()", new Object[0]);
        SignInConfiguration signInConfiguration = new SignInConfiguration(context.getPackageName(), googleSignInOptions);
        Intent intent = new Intent("com.google.android.gms.auth.GOOGLE_SIGN_IN");
        intent.setPackage(context.getPackageName());
        intent.setClass(context, SignInHubActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("config", signInConfiguration);
        intent.putExtra("config", bundle);
        return intent;
    }

    public static GoogleSignInResult zbd(Intent intent) {
        if (intent == null) {
            return new GoogleSignInResult(null, Status.RESULT_INTERNAL_ERROR);
        }
        Status status = (Status) intent.getParcelableExtra("googleSignInStatus");
        GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) intent.getParcelableExtra("googleSignInAccount");
        if (googleSignInAccount == null) {
            if (status == null) {
                status = Status.RESULT_INTERNAL_ERROR;
            }
            return new GoogleSignInResult(null, status);
        }
        return new GoogleSignInResult(googleSignInAccount, Status.RESULT_SUCCESS);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.gms.common.api.OptionalPendingResult zbe(com.google.android.gms.common.api.GoogleApiClient r5, android.content.Context r6, com.google.android.gms.auth.api.signin.GoogleSignInOptions r7, boolean r8) {
        /*
            com.google.android.gms.common.logging.Logger r0 = com.google.android.gms.auth.api.signin.internal.zbm.zba
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.String r3 = "silentSignIn()"
            r0.d(r3, r2)
            com.google.android.gms.common.logging.Logger r0 = com.google.android.gms.auth.api.signin.internal.zbm.zba
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.String r3 = "getEligibleSavedSignInResult()"
            r0.d(r3, r2)
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)
            com.google.android.gms.auth.api.signin.internal.zbn r0 = com.google.android.gms.auth.api.signin.internal.zbn.zbc(r6)
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r0 = r0.zbb()
            r2 = 0
            if (r0 != 0) goto L24
        L21:
            r3 = r2
            goto L8b
        L24:
            android.accounts.Account r3 = r0.getAccount()
            android.accounts.Account r4 = r7.getAccount()
            if (r3 != 0) goto L31
            if (r4 != 0) goto L21
            goto L38
        L31:
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L38
            goto L21
        L38:
            boolean r3 = r7.isServerAuthCodeRequested()
            if (r3 == 0) goto L3f
            goto L21
        L3f:
            boolean r3 = r7.isIdTokenRequested()
            if (r3 == 0) goto L5b
            boolean r3 = r0.isIdTokenRequested()
            if (r3 != 0) goto L4c
            goto L21
        L4c:
            java.lang.String r3 = r7.getServerClientId()
            java.lang.String r4 = r0.getServerClientId()
            boolean r3 = com.google.android.gms.common.internal.Objects.equal(r3, r4)
            if (r3 != 0) goto L5b
            goto L21
        L5b:
            java.util.HashSet r3 = new java.util.HashSet
            java.util.ArrayList r0 = r0.getScopes()
            r3.<init>(r0)
            java.util.HashSet r0 = new java.util.HashSet
            java.util.ArrayList r4 = r7.getScopes()
            r0.<init>(r4)
            boolean r0 = r3.containsAll(r0)
            if (r0 != 0) goto L74
            goto L21
        L74:
            com.google.android.gms.auth.api.signin.internal.zbn r0 = com.google.android.gms.auth.api.signin.internal.zbn.zbc(r6)
            com.google.android.gms.auth.api.signin.GoogleSignInAccount r0 = r0.zba()
            if (r0 == 0) goto L21
            boolean r3 = r0.isExpired()
            if (r3 != 0) goto L21
            com.google.android.gms.auth.api.signin.GoogleSignInResult r3 = new com.google.android.gms.auth.api.signin.GoogleSignInResult
            com.google.android.gms.common.api.Status r4 = com.google.android.gms.common.api.Status.RESULT_SUCCESS
            r3.<init>(r0, r4)
        L8b:
            if (r3 == 0) goto L9b
            com.google.android.gms.common.logging.Logger r6 = com.google.android.gms.auth.api.signin.internal.zbm.zba
            java.lang.Object[] r7 = new java.lang.Object[r1]
            java.lang.String r8 = "Eligible saved sign in result found"
            r6.d(r8, r7)
            com.google.android.gms.common.api.OptionalPendingResult r5 = com.google.android.gms.common.api.PendingResults.immediatePendingResult(r3, r5)
            return r5
        L9b:
            if (r8 == 0) goto Lad
            com.google.android.gms.auth.api.signin.GoogleSignInResult r6 = new com.google.android.gms.auth.api.signin.GoogleSignInResult
            com.google.android.gms.common.api.Status r7 = new com.google.android.gms.common.api.Status
            r8 = 4
            r7.<init>(r8)
            r6.<init>(r2, r7)
            com.google.android.gms.common.api.OptionalPendingResult r5 = com.google.android.gms.common.api.PendingResults.immediatePendingResult(r6, r5)
            return r5
        Lad:
            com.google.android.gms.common.logging.Logger r8 = com.google.android.gms.auth.api.signin.internal.zbm.zba
            java.lang.Object[] r0 = new java.lang.Object[r1]
            java.lang.String r1 = "trySilentSignIn()"
            r8.d(r1, r0)
            com.google.android.gms.auth.api.signin.internal.zbg r8 = new com.google.android.gms.auth.api.signin.internal.zbg
            r8.<init>(r5, r6, r7)
            com.google.android.gms.common.api.internal.BaseImplementation$ApiMethodImpl r5 = r5.enqueue(r8)
            com.google.android.gms.common.api.internal.OptionalPendingResultImpl r6 = new com.google.android.gms.common.api.internal.OptionalPendingResultImpl
            r6.<init>(r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.internal.zbm.zbe(com.google.android.gms.common.api.GoogleApiClient, android.content.Context, com.google.android.gms.auth.api.signin.GoogleSignInOptions, boolean):com.google.android.gms.common.api.OptionalPendingResult");
    }

    public static PendingResult zbf(GoogleApiClient googleApiClient, Context context, boolean z) {
        zba.d("Revoking access", new Object[0]);
        String savedRefreshToken = Storage.getInstance(context).getSavedRefreshToken();
        zbh(context);
        if (z) {
            return zbb.zba(savedRefreshToken);
        }
        return googleApiClient.execute(new zbk(googleApiClient));
    }

    public static PendingResult zbg(GoogleApiClient googleApiClient, Context context, boolean z) {
        zba.d("Signing out", new Object[0]);
        zbh(context);
        if (z) {
            return PendingResults.immediatePendingResult(Status.RESULT_SUCCESS, googleApiClient);
        }
        return googleApiClient.execute(new zbi(googleApiClient));
    }

    private static void zbh(Context context) {
        zbn.zbc(context).zbd();
        for (GoogleApiClient googleApiClient : GoogleApiClient.getAllClients()) {
            googleApiClient.maybeSignOut();
        }
        GoogleApiManager.reportSignOut();
    }
}
