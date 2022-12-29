package com.google.android.gms.games;

import androidx.core.view.PointerIconCompat;
import com.google.android.gms.auth.api.proxy.AuthApiStatusCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-games@@23.0.0 */
/* loaded from: classes3.dex */
public final class GamesStatusUtils {
    private GamesStatusUtils() {
    }

    public static void zza(TaskCompletionSource taskCompletionSource, int i) {
        int i2;
        Status zza = GamesStatusCodes.zza(i);
        int i3 = GamesClientStatusCodes.NETWORK_ERROR_NO_DATA;
        int statusCode = zza.getStatusCode();
        if (statusCode == 1) {
            i2 = 8;
        } else if (statusCode == 2) {
            i2 = 26502;
        } else if (statusCode == 3) {
            i2 = 26503;
        } else if (statusCode == 4) {
            i2 = GamesClientStatusCodes.NETWORK_ERROR_NO_DATA;
        } else if (statusCode == 5) {
            i2 = 26505;
        } else if (statusCode != 6) {
            if (statusCode != 7) {
                if (statusCode == 1500) {
                    i2 = 26540;
                } else if (statusCode != 1501) {
                    switch (statusCode) {
                        case 7:
                            break;
                        case 8:
                            i2 = GamesClientStatusCodes.APP_MISCONFIGURED;
                            break;
                        case 9:
                            i2 = GamesClientStatusCodes.GAME_NOT_FOUND;
                            break;
                        case 500:
                            i2 = 26520;
                            break;
                        case 9000:
                            i2 = GamesClientStatusCodes.VIDEO_NOT_ACTIVE;
                            break;
                        case 9001:
                            i2 = GamesClientStatusCodes.VIDEO_UNSUPPORTED;
                            break;
                        case 9002:
                            i2 = GamesClientStatusCodes.VIDEO_PERMISSION_ERROR;
                            break;
                        case 9003:
                            i2 = GamesClientStatusCodes.VIDEO_STORAGE_ERROR;
                            break;
                        case 9004:
                            i2 = GamesClientStatusCodes.VIDEO_UNEXPECTED_CAPTURE_ERROR;
                            break;
                        case 9006:
                            i2 = GamesClientStatusCodes.VIDEO_ALREADY_CAPTURING;
                            break;
                        case 9009:
                            i2 = GamesClientStatusCodes.VIDEO_OUT_OF_DISK_SPACE;
                            break;
                        case 9010:
                            i2 = 26627;
                            break;
                        case 9011:
                            i2 = 26628;
                            break;
                        case 9012:
                            i2 = 26629;
                            break;
                        case 9016:
                            i2 = 26630;
                            break;
                        case 9017:
                            i2 = 26631;
                            break;
                        case 9018:
                            i2 = 26632;
                            break;
                        case 9200:
                            i2 = 26650;
                            break;
                        case 9202:
                            i2 = 26652;
                            break;
                        case 10000:
                            i2 = 26700;
                            break;
                        case GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED /* 10001 */:
                            i2 = 26701;
                            break;
                        case GamesActivityResultCodes.RESULT_SIGN_IN_FAILED /* 10002 */:
                            i2 = 26702;
                            break;
                        case GamesActivityResultCodes.RESULT_LICENSE_FAILED /* 10003 */:
                            i2 = GamesClientStatusCodes.CONSENT_REQUIRED;
                            break;
                        case GamesActivityResultCodes.RESULT_APP_MISCONFIGURED /* 10004 */:
                            i2 = 26704;
                            break;
                        default:
                            switch (statusCode) {
                                case 1000:
                                    i2 = 26530;
                                    break;
                                case 1001:
                                    i2 = 26531;
                                    break;
                                case 1002:
                                    i2 = 26532;
                                    break;
                                case 1003:
                                    i2 = 26533;
                                    break;
                                case 1004:
                                    i2 = 26534;
                                    break;
                                case 1005:
                                    i2 = 26535;
                                    break;
                                case PointerIconCompat.TYPE_CELL /* 1006 */:
                                    i2 = 26536;
                                    break;
                                default:
                                    switch (statusCode) {
                                        case 2000:
                                            i2 = 26550;
                                            break;
                                        case 2001:
                                            i2 = 26551;
                                            break;
                                        case 2002:
                                            i2 = 26552;
                                            break;
                                        default:
                                            switch (statusCode) {
                                                case 3000:
                                                    i2 = GamesClientStatusCodes.ACHIEVEMENT_UNLOCK_FAILURE;
                                                    break;
                                                case AuthApiStatusCodes.AUTH_API_ACCESS_FORBIDDEN /* 3001 */:
                                                    i2 = GamesClientStatusCodes.ACHIEVEMENT_UNKNOWN;
                                                    break;
                                                case AuthApiStatusCodes.AUTH_API_CLIENT_ERROR /* 3002 */:
                                                    i2 = GamesClientStatusCodes.ACHIEVEMENT_NOT_INCREMENTAL;
                                                    break;
                                                case AuthApiStatusCodes.AUTH_API_SERVER_ERROR /* 3003 */:
                                                    i2 = GamesClientStatusCodes.ACHIEVEMENT_UNLOCKED;
                                                    break;
                                                default:
                                                    switch (statusCode) {
                                                        case 4000:
                                                            i2 = GamesClientStatusCodes.SNAPSHOT_NOT_FOUND;
                                                            break;
                                                        case 4001:
                                                            i2 = GamesClientStatusCodes.SNAPSHOT_CREATION_FAILED;
                                                            break;
                                                        case 4002:
                                                            i2 = GamesClientStatusCodes.SNAPSHOT_CONTENTS_UNAVAILABLE;
                                                            break;
                                                        case 4003:
                                                            i2 = GamesClientStatusCodes.SNAPSHOT_COMMIT_FAILED;
                                                            break;
                                                        case 4004:
                                                            i2 = 26574;
                                                            break;
                                                        case 4005:
                                                            i2 = GamesClientStatusCodes.SNAPSHOT_FOLDER_UNAVAILABLE;
                                                            break;
                                                        case 4006:
                                                            i2 = GamesClientStatusCodes.SNAPSHOT_CONFLICT_MISSING;
                                                            break;
                                                        default:
                                                            switch (statusCode) {
                                                                case 6000:
                                                                    i2 = 26580;
                                                                    break;
                                                                case 6001:
                                                                    i2 = 26581;
                                                                    break;
                                                                case 6002:
                                                                    i2 = 26582;
                                                                    break;
                                                                case 6003:
                                                                    i2 = 26583;
                                                                    break;
                                                                case 6004:
                                                                    i2 = 26584;
                                                                    break;
                                                                default:
                                                                    switch (statusCode) {
                                                                        case 6500:
                                                                            i2 = 26590;
                                                                            break;
                                                                        case 6501:
                                                                            i2 = 26591;
                                                                            break;
                                                                        case 6502:
                                                                            i2 = 26592;
                                                                            break;
                                                                        case 6503:
                                                                            i2 = 26593;
                                                                            break;
                                                                        case 6504:
                                                                            i2 = 26594;
                                                                            break;
                                                                        case 6505:
                                                                            i2 = 26595;
                                                                            break;
                                                                        case 6506:
                                                                            i2 = 26596;
                                                                            break;
                                                                        case 6507:
                                                                            i2 = 26597;
                                                                            break;
                                                                        default:
                                                                            switch (statusCode) {
                                                                                case 7000:
                                                                                    i2 = 26600;
                                                                                    break;
                                                                                case 7001:
                                                                                    i2 = 26601;
                                                                                    break;
                                                                                case 7002:
                                                                                    i2 = 26602;
                                                                                    break;
                                                                                case 7003:
                                                                                    i2 = 26603;
                                                                                    break;
                                                                                case 7004:
                                                                                    i2 = 26604;
                                                                                    break;
                                                                                case 7005:
                                                                                    i2 = 26605;
                                                                                    break;
                                                                                case 7006:
                                                                                    i2 = 26606;
                                                                                    break;
                                                                                case 7007:
                                                                                    i2 = GamesClientStatusCodes.OPERATION_IN_FLIGHT;
                                                                                    break;
                                                                                default:
                                                                                    switch (statusCode) {
                                                                                        case 8000:
                                                                                            i2 = 26610;
                                                                                            break;
                                                                                        case 8001:
                                                                                            i2 = 26611;
                                                                                            break;
                                                                                        case 8002:
                                                                                            i2 = 26612;
                                                                                            break;
                                                                                        case 8003:
                                                                                            i2 = 26613;
                                                                                            break;
                                                                                        default:
                                                                                            i2 = statusCode;
                                                                                            break;
                                                                                    }
                                                                            }
                                                                    }
                                                            }
                                                    }
                                            }
                                    }
                            }
                    }
                } else {
                    i2 = 26541;
                }
            }
            i2 = GamesClientStatusCodes.LICENSE_CHECK_FAILED;
        } else {
            i2 = GamesClientStatusCodes.NETWORK_ERROR_OPERATION_FAILED;
        }
        if (i2 != zza.getStatusCode()) {
            if (GamesStatusCodes.zzb(zza.getStatusCode()).equals(zza.getStatusMessage())) {
                zza = GamesClientStatusCodes.zzb(i2, zza.getResolution());
            } else {
                switch (statusCode) {
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 10:
                        break;
                    case 9:
                    default:
                        zza = new Status(i2, zza.getStatusMessage(), zza.getResolution());
                        break;
                }
            }
        }
        taskCompletionSource.setException(ApiExceptionUtil.fromStatus(zza));
    }

    public static void zzb(TaskCompletionSource taskCompletionSource, SecurityException securityException) {
        if (taskCompletionSource != null) {
            taskCompletionSource.trySetException(new ApiException(GamesClientStatusCodes.zza(4)));
        }
    }
}
