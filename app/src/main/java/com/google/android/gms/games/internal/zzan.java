package com.google.android.gms.games.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.BinderWrapper;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.FriendsResolutionRequiredException;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.GameBuffer;
import com.google.android.gms.games.GameEntity;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClientStatusCodes;
import com.google.android.gms.games.GamesStatusUtils;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerBuffer;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.SnapshotMetadataChangeEntity;
import com.google.android.gms.internal.games.zzcf;
import com.google.android.gms.internal.games.zzci;
import com.google.android.gms.internal.games.zzck;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class zzan extends GmsClient {
    public static final /* synthetic */ int zze = 0;
    private final zzcf zzf;
    private final String zzg;
    private PlayerEntity zzh;
    private GameEntity zzi;
    private final zzat zzj;
    private boolean zzk;
    private final long zzl;
    private final Games.GamesOptions zzm;
    private final zzau zzn;

    public zzan(Context context, Looper looper, ClientSettings clientSettings, Games.GamesOptions gamesOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, zzau zzauVar) {
        super(context, looper, 1, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzf = new zzi(this);
        this.zzk = false;
        this.zzg = clientSettings.getRealClientPackageName();
        this.zzn = (zzau) Preconditions.checkNotNull(zzauVar);
        this.zzj = zzat.zzc(this, clientSettings.getGravityForPopups());
        this.zzl = hashCode();
        this.zzm = gamesOptions;
        boolean z = this.zzm.zzh;
        if (clientSettings.getViewForPopups() != null || (context instanceof Activity)) {
            this.zzj.zze(clientSettings.getViewForPopups());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzad(RemoteException remoteException) {
        zzck.zze("GamesGmsClientImpl", "service died", remoteException);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ void zzs(zzan zzanVar, TaskCompletionSource taskCompletionSource) {
        try {
            taskCompletionSource.setException(FriendsResolutionRequiredException.zza(GamesClientStatusCodes.zzb(GamesClientStatusCodes.CONSENT_REQUIRED, ((zzas) zzanVar.getService()).zzf())));
        } catch (RemoteException e) {
            taskCompletionSource.setException(e);
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final void connect(BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zzh = null;
        this.zzi = null;
        super.connect(connectionProgressReportCallbacks);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesService");
        return queryLocalInterface instanceof zzas ? (zzas) queryLocalInterface : new zzas(iBinder);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final void disconnect() {
        this.zzk = false;
        if (isConnected()) {
            try {
                this.zzf.zzb();
                ((zzas) getService()).zzv(this.zzl);
            } catch (RemoteException unused) {
                zzck.zzd("GamesGmsClientImpl", "Failed to notify client disconnect.");
            }
        }
        super.disconnect();
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Feature[] getApiFeatures() {
        return com.google.android.gms.games.zzd.zzb;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Bundle getConnectionHint() {
        return null;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final Bundle getGetServiceRequestExtraArgs() {
        String locale = getContext().getResources().getConfiguration().locale.toString();
        Bundle zza = this.zzm.zza();
        zza.putString(ServiceSpecificExtraArgs.GamesExtraArgs.GAME_PACKAGE_NAME, this.zzg);
        zza.putString(ServiceSpecificExtraArgs.GamesExtraArgs.DESIRED_LOCALE, locale);
        zza.putParcelable(ServiceSpecificExtraArgs.GamesExtraArgs.WINDOW_TOKEN, new BinderWrapper(this.zzj.zzb()));
        if (!zza.containsKey("com.google.android.gms.games.key.API_VERSION")) {
            zza.putInt("com.google.android.gms.games.key.API_VERSION", 9);
        }
        zza.putBundle(ServiceSpecificExtraArgs.GamesExtraArgs.SIGNIN_OPTIONS, SignInClientImpl.createBundleFromClientSettings(getClientSettings()));
        return zza;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    @Override // com.google.android.gms.common.internal.GmsClient, com.google.android.gms.common.api.Api.Client
    public final Set getScopesForConnectionlessNonSignIn() {
        return getScopes();
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.games.internal.IGamesService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getStartServiceAction() {
        return "com.google.android.gms.games.service.START";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final /* bridge */ /* synthetic */ void onConnectedLocked(IInterface iInterface) {
        zzas zzasVar = (zzas) iInterface;
        super.onConnectedLocked(zzasVar);
        if (this.zzk) {
            this.zzj.zzg();
            this.zzk = false;
        }
        Games.GamesOptions gamesOptions = this.zzm;
        boolean z = gamesOptions.zza;
        boolean z2 = gamesOptions.zzh;
        try {
            zzasVar.zzU(new zzk(new zzci(this.zzj.zzd())), this.zzl);
        } catch (RemoteException e) {
            zzad(e);
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        super.onConnectionFailed(connectionResult);
        this.zzk = false;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        int i3 = 0;
        if (i != 0) {
            i3 = i;
        } else if (bundle != null) {
            bundle.setClassLoader(zzan.class.getClassLoader());
            this.zzk = bundle.getBoolean("show_welcome_popup");
            this.zzh = (PlayerEntity) bundle.getParcelable("com.google.android.gms.games.current_player");
            this.zzi = (GameEntity) bundle.getParcelable("com.google.android.gms.games.current_game");
        }
        super.onPostInitHandler(i3, iBinder, bundle, i2);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final void onUserSignOut(BaseGmsClient.SignOutCallbacks signOutCallbacks) {
        try {
            zzl zzlVar = new zzl(signOutCallbacks);
            this.zzf.zzb();
            try {
                ((zzas) getService()).zzZ(new zzm(zzlVar));
            } catch (SecurityException unused) {
                zzlVar.setFailedResult(GamesClientStatusCodes.zza(4));
            }
        } catch (RemoteException unused2) {
            signOutCallbacks.onSignOutComplete();
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final boolean requiresAccount() {
        return true;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final boolean requiresSignIn() {
        Games.GamesOptions gamesOptions = this.zzm;
        zzf zzfVar = gamesOptions.zzo;
        String str = gamesOptions.zzl;
        boolean z = gamesOptions.zzh;
        return true;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final boolean usesClientTelemetry() {
        return true;
    }

    public final void zzB(String str, int i) {
        this.zzf.zzc(str, i);
    }

    public final void zzC(TaskCompletionSource taskCompletionSource, int i) throws RemoteException {
        try {
            ((zzas) getService()).zzE(new zzae(taskCompletionSource), i);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzD(TaskCompletionSource taskCompletionSource, boolean z) throws RemoteException {
        try {
            ((zzas) getService()).zzF(new zzp(taskCompletionSource), z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzE(TaskCompletionSource taskCompletionSource, String str, int i, int i2) throws RemoteException {
        try {
            ((zzas) getService()).zzA(new zzw(this, taskCompletionSource), null, str, i, i2);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzF(TaskCompletionSource taskCompletionSource, boolean z) throws RemoteException {
        this.zzf.zzb();
        try {
            ((zzas) getService()).zzG(new zzs(taskCompletionSource), z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzG(TaskCompletionSource taskCompletionSource, boolean z, String... strArr) throws RemoteException {
        this.zzf.zzb();
        try {
            ((zzas) getService()).zzH(new zzs(taskCompletionSource), z, strArr);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzH(TaskCompletionSource taskCompletionSource) throws RemoteException {
        try {
            ((zzas) getService()).zzI(new zzu(taskCompletionSource));
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzI(TaskCompletionSource taskCompletionSource, String str, boolean z) throws RemoteException {
        try {
            ((zzas) getService()).zzJ(new zzv(taskCompletionSource), str, z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzJ(TaskCompletionSource taskCompletionSource, boolean z) throws RemoteException {
        try {
            ((zzas) getService()).zzK(new zzy(taskCompletionSource), z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzK(TaskCompletionSource taskCompletionSource, LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) throws RemoteException {
        try {
            ((zzas) getService()).zzL(new zzx(this, taskCompletionSource), leaderboardScoreBuffer.zza().zza(), i, i2);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzL(TaskCompletionSource taskCompletionSource, String str, boolean z) throws RemoteException {
        try {
            ((zzas) getService()).zzO(new zzai(taskCompletionSource), str, z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzM(TaskCompletionSource taskCompletionSource, String str, int i, int i2, int i3, boolean z) throws RemoteException {
        try {
            ((zzas) getService()).zzM(new zzx(this, taskCompletionSource), str, i, i2, i3, z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzN(TaskCompletionSource taskCompletionSource, boolean z) throws RemoteException {
        try {
            ((zzas) getService()).zzN(new zzag(taskCompletionSource), z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzO(TaskCompletionSource taskCompletionSource, String str, int i, boolean z, boolean z2) throws RemoteException {
        if (str.equals("played_with") || str.equals("friends_all")) {
            try {
                ((zzas) getService()).zzP(new zzah(this, taskCompletionSource), str, i, z, z2);
                return;
            } catch (SecurityException e) {
                GamesStatusUtils.zzb(taskCompletionSource, e);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player collection: ".concat(str));
    }

    public final void zzP(TaskCompletionSource taskCompletionSource, boolean z) throws RemoteException {
        try {
            ((zzas) getService()).zzQ(new zzad(taskCompletionSource), z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzQ(TaskCompletionSource taskCompletionSource, String str, int i, int i2, int i3, boolean z) throws RemoteException {
        try {
            ((zzas) getService()).zzR(new zzx(this, taskCompletionSource), str, i, i2, i3, z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzR(TaskCompletionSource taskCompletionSource, String str, boolean z, int i) throws RemoteException {
        try {
            ((zzas) getService()).zzS(new zzam(taskCompletionSource), str, z, i);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzS(ListenerHolder listenerHolder) throws RemoteException {
        ((zzas) getService()).zzT(new zzh(listenerHolder), this.zzl);
    }

    public final void zzT(TaskCompletionSource taskCompletionSource, String str, String str2, SnapshotMetadataChange snapshotMetadataChange, SnapshotContents snapshotContents) throws RemoteException {
        Preconditions.checkState(!snapshotContents.isClosed(), "SnapshotContents already closed");
        BitmapTeleporter zza = snapshotMetadataChange.zza();
        if (zza != null) {
            zza.setTempDir(getContext().getCacheDir());
        }
        Contents zza2 = snapshotContents.zza();
        snapshotContents.zzb();
        try {
            ((zzas) getService()).zzV(new zzam(taskCompletionSource), str, str2, (SnapshotMetadataChangeEntity) snapshotMetadataChange, zza2);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzW(int i) {
        this.zzj.zzf(i);
    }

    public final void zzX(View view) {
        this.zzj.zze(view);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzY(IBinder iBinder, Bundle bundle) {
        if (isConnected()) {
            zzf zzfVar = this.zzm.zzo;
            try {
                ((zzas) getService()).zzY(iBinder, bundle);
                this.zzn.zzb();
            } catch (RemoteException e) {
                zzad(e);
            }
        }
    }

    public final void zzZ(String str, long j, String str2) throws RemoteException {
        try {
            ((zzas) getService()).zzaa(null, str, j, str2);
        } catch (SecurityException unused) {
        }
    }

    public final void zzaa(TaskCompletionSource taskCompletionSource, String str, long j, String str2) throws RemoteException {
        try {
            ((zzas) getService()).zzaa(new zzak(taskCompletionSource), str, j, str2);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzac() throws RemoteException {
        ((zzas) getService()).zzac(this.zzl);
    }

    public final Game zzp() throws RemoteException {
        checkConnected();
        synchronized (this) {
            if (this.zzi == null) {
                GameBuffer gameBuffer = new GameBuffer(((zzas) getService()).zzp());
                if (gameBuffer.getCount() > 0) {
                    this.zzi = new GameEntity(gameBuffer.get(0));
                }
                gameBuffer.release();
            }
        }
        return this.zzi;
    }

    public final Player zzq() throws RemoteException {
        checkConnected();
        synchronized (this) {
            if (this.zzh == null) {
                PlayerBuffer playerBuffer = new PlayerBuffer(((zzas) getService()).zzq());
                if (playerBuffer.getCount() > 0) {
                    this.zzh = new PlayerEntity(playerBuffer.get(0));
                }
                playerBuffer.release();
            }
        }
        return this.zzh;
    }

    public final String zzr(boolean z) throws RemoteException {
        PlayerEntity playerEntity = this.zzh;
        return playerEntity != null ? playerEntity.getPlayerId() : ((zzas) getService()).zzt();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzu() {
        if (isConnected()) {
            try {
                ((zzas) getService()).zzu();
            } catch (RemoteException e) {
                zzad(e);
            }
        }
    }

    public final void zzv(TaskCompletionSource taskCompletionSource, Snapshot snapshot, SnapshotMetadataChange snapshotMetadataChange) throws RemoteException {
        SnapshotContents snapshotContents = snapshot.getSnapshotContents();
        Preconditions.checkState(!snapshotContents.isClosed(), "Snapshot already closed");
        BitmapTeleporter zza = snapshotMetadataChange.zza();
        if (zza != null) {
            zza.setTempDir(getContext().getCacheDir());
        }
        Contents zza2 = snapshotContents.zza();
        snapshotContents.zzb();
        try {
            ((zzas) getService()).zzw(new zzq(taskCompletionSource), snapshot.getMetadata().getSnapshotId(), (SnapshotMetadataChangeEntity) snapshotMetadataChange, zza2);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzw(TaskCompletionSource taskCompletionSource, String str) throws RemoteException {
        try {
            ((zzas) getService()).zzx(new zzr(taskCompletionSource), str);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzx(TaskCompletionSource taskCompletionSource) throws RemoteException {
        try {
            ((zzas) getService()).zzB(new zzaf(taskCompletionSource));
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzy(TaskCompletionSource taskCompletionSource) throws RemoteException {
        try {
            ((zzas) getService()).zzz(new zzac(taskCompletionSource));
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzz(TaskCompletionSource taskCompletionSource, boolean z) throws RemoteException {
        try {
            ((zzas) getService()).zzO(new zzai(taskCompletionSource), null, z);
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzU(TaskCompletionSource taskCompletionSource, String str) throws RemoteException {
        try {
            ((zzas) getService()).zzW(taskCompletionSource == null ? null : new zzo(taskCompletionSource), str, this.zzj.zzb(), this.zzj.zza());
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzab(TaskCompletionSource taskCompletionSource, String str) throws RemoteException {
        try {
            ((zzas) getService()).zzab(taskCompletionSource == null ? null : new zzo(taskCompletionSource), str, this.zzj.zzb(), this.zzj.zza());
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzA(TaskCompletionSource taskCompletionSource, String str, int i) throws RemoteException {
        try {
            ((zzas) getService()).zzC(taskCompletionSource == null ? null : new zzn(taskCompletionSource), str, i, this.zzj.zzb(), this.zzj.zza());
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }

    public final void zzV(TaskCompletionSource taskCompletionSource, String str, int i) throws RemoteException {
        try {
            ((zzas) getService()).zzX(taskCompletionSource == null ? null : new zzn(taskCompletionSource), str, i, this.zzj.zzb(), this.zzj.zza());
        } catch (SecurityException e) {
            GamesStatusUtils.zzb(taskCompletionSource, e);
        }
    }
}
