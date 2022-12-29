package com.google.android.gms.plus;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.Collection;

@Deprecated
/* loaded from: classes2.dex */
public interface People {

    @Deprecated
    /* loaded from: classes2.dex */
    public interface LoadPeopleResult extends Releasable, Result {
        @Deprecated
        String getNextPageToken();

        @Deprecated
        PersonBuffer getPersonBuffer();
    }

    @Deprecated
    /* loaded from: classes2.dex */
    public interface OrderBy {
        @Deprecated
        public static final int ALPHABETICAL = 0;
        @Deprecated
        public static final int BEST = 1;
    }

    @Deprecated
    Person getCurrentPerson(GoogleApiClient googleApiClient);

    @Deprecated
    PendingResult<LoadPeopleResult> load(GoogleApiClient googleApiClient, Collection<String> collection);

    @Deprecated
    PendingResult<LoadPeopleResult> load(GoogleApiClient googleApiClient, String... strArr);

    @Deprecated
    PendingResult<LoadPeopleResult> loadConnected(GoogleApiClient googleApiClient);

    @Deprecated
    PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, int i, String str);

    @Deprecated
    PendingResult<LoadPeopleResult> loadVisible(GoogleApiClient googleApiClient, String str);
}
