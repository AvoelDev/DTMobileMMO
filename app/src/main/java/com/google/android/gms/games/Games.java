package com.google.android.gms.games;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.FirstPartyScopes;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.event.Events;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.games.stats.Stats;
import com.google.android.gms.games.video.Videos;
import com.google.android.gms.internal.games.zzab;
import com.google.android.gms.internal.games.zzae;
import com.google.android.gms.internal.games.zzaf;
import com.google.android.gms.internal.games.zzas;
import com.google.android.gms.internal.games.zzat;
import com.google.android.gms.internal.games.zzav;
import com.google.android.gms.internal.games.zzbf;
import com.google.android.gms.internal.games.zzbg;
import com.google.android.gms.internal.games.zzbq;
import com.google.android.gms.internal.games.zzbr;
import com.google.android.gms.internal.games.zzbs;
import com.google.android.gms.internal.games.zzca;
import com.google.android.gms.internal.games.zzcb;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class Games {
    static final Api.ClientKey zza = new Api.ClientKey();
    private static final Api.AbstractClientBuilder zzp = new zzg();
    private static final Api.AbstractClientBuilder zzq = new zzh();
    public static final Scope zzb = new Scope(Scopes.GAMES);
    public static final Scope zzc = new Scope(Scopes.GAMES_LITE);
    public static final Scope zzd = new Scope(Scopes.DRIVE_APPFOLDER);
    @Deprecated
    public static final Api zze = new Api("Games.API", zzp, zza);
    public static final Scope zzf = new Scope(FirstPartyScopes.GAMES_1P);
    public static final Api zzg = new Api("Games.API_1P", zzq, zza);
    @Deprecated
    public static final GamesMetadata zzh = new zzaf();
    @Deprecated
    public static final Achievements zzi = new com.google.android.gms.internal.games.zzo();
    @Deprecated
    public static final Events zzj = new com.google.android.gms.internal.games.zzu();
    @Deprecated
    public static final Leaderboards zzk = new zzat();
    @Deprecated
    public static final Players zzl = new zzbg();
    @Deprecated
    public static final Snapshots zzm = new zzbr();
    @Deprecated
    public static final Stats zzn = new zzbs();
    @Deprecated
    public static final Videos zzo = new zzcb();

    /* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
    /* loaded from: classes3.dex */
    public static final class GamesOptions implements GoogleSignInOptionsExtension, Api.ApiOptions.HasGoogleSignInAccountOptions, Api.ApiOptions.Optional {
        public static final /* synthetic */ int zzp = 0;
        public final boolean zzb;
        public final int zzc;
        public final int zze;
        public final ArrayList zzg;
        public final GoogleSignInAccount zzk;
        public final int zzm;
        public com.google.android.gms.games.internal.zzf zzo;
        public final boolean zza = false;
        public final boolean zzd = false;
        public final String zzf = null;
        public final boolean zzh = false;
        public final boolean zzi = false;
        public final boolean zzj = false;
        public final String zzl = null;
        private final int zzq = 0;
        public final String zzn = null;

        /* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
        /* loaded from: classes3.dex */
        public static final class Builder {
            private static final AtomicInteger zzh = new AtomicInteger(0);
            boolean zza;
            int zzb;
            int zzc;
            ArrayList zzd;
            GoogleSignInAccount zze;
            int zzf;
            com.google.android.gms.games.internal.zzf zzg;

            private Builder() {
                this.zza = true;
                this.zzb = 17;
                this.zzc = 4368;
                this.zzd = new ArrayList();
                this.zze = null;
                this.zzf = 9;
                this.zzg = com.google.android.gms.games.internal.zzf.zza;
            }

            public GamesOptions build() {
                return new GamesOptions(false, this.zza, this.zzb, false, this.zzc, null, this.zzd, false, false, false, this.zze, null, 0, this.zzf, null, this.zzg, null);
            }

            public Builder setSdkVariant(int i) {
                this.zzc = i;
                return this;
            }

            public Builder setShowConnectingPopup(boolean z) {
                this.zza = z;
                this.zzb = 17;
                return this;
            }

            public Builder setShowConnectingPopup(boolean z, int i) {
                this.zza = z;
                this.zzb = i;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public /* synthetic */ Builder(zzk zzkVar) {
                this.zza = true;
                this.zzb = 17;
                this.zzc = 4368;
                this.zzd = new ArrayList();
                this.zze = null;
                this.zzf = 9;
                this.zzg = com.google.android.gms.games.internal.zzf.zza;
            }

            /* synthetic */ Builder(GamesOptions gamesOptions, zzk zzkVar) {
                this.zza = true;
                this.zzb = 17;
                this.zzc = 4368;
                this.zzd = new ArrayList();
                this.zze = null;
                this.zzf = 9;
                this.zzg = com.google.android.gms.games.internal.zzf.zza;
                if (gamesOptions != null) {
                    this.zza = gamesOptions.zzb;
                    this.zzb = gamesOptions.zzc;
                    this.zzc = gamesOptions.zze;
                    this.zzd = gamesOptions.zzg;
                    this.zze = gamesOptions.zzk;
                    this.zzf = gamesOptions.zzm;
                    this.zzg = gamesOptions.zzo;
                }
            }
        }

        /* synthetic */ GamesOptions(boolean z, boolean z2, int i, boolean z3, int i2, String str, ArrayList arrayList, boolean z4, boolean z5, boolean z6, GoogleSignInAccount googleSignInAccount, String str2, int i3, int i4, String str3, com.google.android.gms.games.internal.zzf zzfVar, zzl zzlVar) {
            this.zzb = z2;
            this.zzc = i;
            this.zze = i2;
            this.zzg = arrayList;
            this.zzk = googleSignInAccount;
            this.zzm = i4;
            this.zzo = zzfVar;
        }

        public static Builder builder() {
            return new Builder(null);
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof GamesOptions) {
                GamesOptions gamesOptions = (GamesOptions) obj;
                boolean z = gamesOptions.zza;
                if (this.zzb == gamesOptions.zzb && this.zzc == gamesOptions.zzc) {
                    boolean z2 = gamesOptions.zzd;
                    if (this.zze == gamesOptions.zze) {
                        String str = gamesOptions.zzf;
                        if (this.zzg.equals(gamesOptions.zzg)) {
                            boolean z3 = gamesOptions.zzh;
                            boolean z4 = gamesOptions.zzi;
                            boolean z5 = gamesOptions.zzj;
                            GoogleSignInAccount googleSignInAccount = this.zzk;
                            if (googleSignInAccount != null ? googleSignInAccount.equals(gamesOptions.zzk) : gamesOptions.zzk == null) {
                                String str2 = gamesOptions.zzl;
                                if (TextUtils.equals(null, null)) {
                                    int i = gamesOptions.zzq;
                                    if (this.zzm == gamesOptions.zzm) {
                                        String str3 = gamesOptions.zzn;
                                        if (Objects.equal(null, null)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
            return false;
        }

        @Override // com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
        public final int getExtensionType() {
            return 1;
        }

        @Override // com.google.android.gms.common.api.Api.ApiOptions.HasGoogleSignInAccountOptions
        public final GoogleSignInAccount getGoogleSignInAccount() {
            return this.zzk;
        }

        @Override // com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
        public final List getImpliedScopes() {
            return Collections.singletonList(Games.zzc);
        }

        public final int hashCode() {
            int hashCode = ((((((((this.zzb ? 1 : 0) + 16337) * 31) + this.zzc) * 961) + this.zze) * 961) + this.zzg.hashCode()) * 923521;
            GoogleSignInAccount googleSignInAccount = this.zzk;
            return (((hashCode + (googleSignInAccount == null ? 0 : googleSignInAccount.hashCode())) * 29791) + this.zzm) * 31;
        }

        @Override // com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
        public final Bundle toBundle() {
            return zza();
        }

        public final Bundle zza() {
            Bundle bundle = new Bundle();
            bundle.putBoolean("com.google.android.gms.games.key.isHeadless", false);
            bundle.putBoolean("com.google.android.gms.games.key.showConnectingPopup", this.zzb);
            bundle.putInt("com.google.android.gms.games.key.connectingPopupGravity", this.zzc);
            bundle.putBoolean("com.google.android.gms.games.key.retryingSignIn", false);
            bundle.putInt("com.google.android.gms.games.key.sdkVariant", this.zze);
            bundle.putString("com.google.android.gms.games.key.forceResolveAccountKey", null);
            bundle.putStringArrayList("com.google.android.gms.games.key.proxyApis", this.zzg);
            bundle.putBoolean("com.google.android.gms.games.key.unauthenticated", false);
            bundle.putBoolean("com.google.android.gms.games.key.skipPgaCheck", false);
            bundle.putBoolean("com.google.android.gms.games.key.skipWelcomePopup", false);
            bundle.putParcelable("com.google.android.gms.games.key.googleSignInAccount", this.zzk);
            bundle.putString("com.google.android.gms.games.key.realClientPackageName", null);
            bundle.putInt("com.google.android.gms.games.key.API_VERSION", this.zzm);
            bundle.putString("com.google.android.gms.games.key.gameRunToken", null);
            return bundle;
        }
    }

    /* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
    @Deprecated
    /* loaded from: classes3.dex */
    public interface GetServerAuthCodeResult extends Result {
        String getCode();
    }

    private Games() {
    }

    public static AchievementsClient getAchievementsClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new com.google.android.gms.internal.games.zzn(activity, zza(googleSignInAccount));
    }

    public static EventsClient getEventsClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new com.google.android.gms.internal.games.zzt(activity, zza(googleSignInAccount));
    }

    public static GamesClient getGamesClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzab(activity, zza(googleSignInAccount));
    }

    public static GamesMetadataClient getGamesMetadataClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzae(activity, zza(googleSignInAccount));
    }

    public static LeaderboardsClient getLeaderboardsClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzas(activity, zza(googleSignInAccount));
    }

    public static PlayerStatsClient getPlayerStatsClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzav(activity, zza(googleSignInAccount));
    }

    public static PlayersClient getPlayersClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzbf(activity, zza(googleSignInAccount));
    }

    public static SnapshotsClient getSnapshotsClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzbq(activity, zza(googleSignInAccount));
    }

    @Deprecated
    public static VideosClient getVideosClient(Activity activity, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzca(activity, zza(googleSignInAccount));
    }

    public static GamesOptions zza(GoogleSignInAccount googleSignInAccount) {
        int i = GamesOptions.zzp;
        GamesOptions.Builder builder = new GamesOptions.Builder(null, null);
        builder.zze = googleSignInAccount;
        builder.setSdkVariant(1052947);
        return builder.build();
    }

    public static GamesOptions zzb(GamesOptions gamesOptions, GoogleSignInAccount googleSignInAccount) {
        int i = GamesOptions.zzp;
        GamesOptions.Builder builder = new GamesOptions.Builder(gamesOptions, null);
        builder.zze = googleSignInAccount;
        builder.setSdkVariant(1052947);
        return builder.build();
    }

    public static AchievementsClient getAchievementsClient(Activity activity, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new com.google.android.gms.internal.games.zzn(activity, zzb(gamesOptions, googleSignInAccount));
    }

    public static EventsClient getEventsClient(Activity activity, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new com.google.android.gms.internal.games.zzt(activity, zzb(gamesOptions, googleSignInAccount));
    }

    public static GamesClient getGamesClient(Activity activity, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzab(activity, zzb(gamesOptions, googleSignInAccount));
    }

    public static GamesMetadataClient getGamesMetadataClient(Activity activity, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzae(activity, zzb(gamesOptions, googleSignInAccount));
    }

    public static LeaderboardsClient getLeaderboardsClient(Activity activity, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzas(activity, zzb(gamesOptions, googleSignInAccount));
    }

    public static PlayerStatsClient getPlayerStatsClient(Activity activity, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzav(activity, zzb(gamesOptions, googleSignInAccount));
    }

    public static PlayersClient getPlayersClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzbf(context, zza(googleSignInAccount));
    }

    public static SnapshotsClient getSnapshotsClient(Activity activity, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzbq(activity, zzb(gamesOptions, googleSignInAccount));
    }

    @Deprecated
    public static VideosClient getVideosClient(Activity activity, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzca(activity, zzb(gamesOptions, googleSignInAccount));
    }

    public static AchievementsClient getAchievementsClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new com.google.android.gms.internal.games.zzn(context, zza(googleSignInAccount));
    }

    public static EventsClient getEventsClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new com.google.android.gms.internal.games.zzt(context, zza(googleSignInAccount));
    }

    public static GamesClient getGamesClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzab(context, zza(googleSignInAccount));
    }

    public static GamesMetadataClient getGamesMetadataClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzae(context, zza(googleSignInAccount));
    }

    public static LeaderboardsClient getLeaderboardsClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzas(context, zza(googleSignInAccount));
    }

    public static PlayerStatsClient getPlayerStatsClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzav(context, zza(googleSignInAccount));
    }

    public static PlayersClient getPlayersClient(Context context, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzbf(context, zzb(gamesOptions, googleSignInAccount));
    }

    public static SnapshotsClient getSnapshotsClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzbq(context, zza(googleSignInAccount));
    }

    @Deprecated
    public static VideosClient getVideosClient(Context context, GoogleSignInAccount googleSignInAccount) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzca(context, zza(googleSignInAccount));
    }

    public static AchievementsClient getAchievementsClient(Context context, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new com.google.android.gms.internal.games.zzn(context, zzb(gamesOptions, googleSignInAccount));
    }

    public static EventsClient getEventsClient(Context context, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new com.google.android.gms.internal.games.zzt(context, zzb(gamesOptions, googleSignInAccount));
    }

    public static GamesClient getGamesClient(Context context, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzab(context, zzb(gamesOptions, googleSignInAccount));
    }

    public static GamesMetadataClient getGamesMetadataClient(Context context, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzae(context, zzb(gamesOptions, googleSignInAccount));
    }

    public static LeaderboardsClient getLeaderboardsClient(Context context, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzas(context, zzb(gamesOptions, googleSignInAccount));
    }

    public static PlayerStatsClient getPlayerStatsClient(Context context, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzav(context, zzb(gamesOptions, googleSignInAccount));
    }

    public static SnapshotsClient getSnapshotsClient(Context context, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzbq(context, zzb(gamesOptions, googleSignInAccount));
    }

    @Deprecated
    public static VideosClient getVideosClient(Context context, GoogleSignInAccount googleSignInAccount, GamesOptions gamesOptions) {
        Preconditions.checkNotNull(googleSignInAccount, "GoogleSignInAccount must not be null");
        return new zzca(context, zzb(gamesOptions, googleSignInAccount));
    }
}
