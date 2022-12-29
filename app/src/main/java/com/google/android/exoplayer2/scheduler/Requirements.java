package com.google.android.exoplayer2.scheduler;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.PowerManager;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class Requirements {
    private static final int DEVICE_CHARGING = 16;
    private static final int DEVICE_IDLE = 8;
    public static final int NETWORK_TYPE_ANY = 1;
    private static final int NETWORK_TYPE_MASK = 7;
    public static final int NETWORK_TYPE_METERED = 4;
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_NOT_ROAMING = 3;
    private static final String[] NETWORK_TYPE_STRINGS = null;
    public static final int NETWORK_TYPE_UNMETERED = 2;
    private static final String TAG = "Requirements";
    private final int requirements;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface NetworkType {
    }

    public Requirements(int networkType, boolean charging, boolean idle) {
        this((idle ? 8 : 0) | (charging ? 16 : 0) | networkType);
    }

    public Requirements(int requirementsData) {
        this.requirements = requirementsData;
    }

    public int getRequiredNetworkType() {
        return this.requirements & 7;
    }

    public boolean isChargingRequired() {
        return (this.requirements & 16) != 0;
    }

    public boolean isIdleRequired() {
        return (this.requirements & 8) != 0;
    }

    public boolean checkRequirements(Context context) {
        return checkNetworkRequirements(context) && checkChargingRequirement(context) && checkIdleRequirement(context);
    }

    public int getRequirementsData() {
        return this.requirements;
    }

    private boolean checkNetworkRequirements(Context context) {
        int networkRequirement = getRequiredNetworkType();
        if (networkRequirement == 0) {
            return true;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            logd("No network info or no connection.");
            return false;
        } else if (checkInternetConnectivity(connectivityManager)) {
            if (networkRequirement != 1) {
                if (networkRequirement == 3) {
                    boolean roaming = networkInfo.isRoaming();
                    logd("Roaming: " + roaming);
                    return !roaming;
                }
                boolean activeNetworkMetered = isActiveNetworkMetered(connectivityManager, networkInfo);
                logd("Metered network: " + activeNetworkMetered);
                if (networkRequirement == 2) {
                    return !activeNetworkMetered;
                } else if (networkRequirement == 4) {
                    return activeNetworkMetered;
                } else {
                    throw new IllegalStateException();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean checkChargingRequirement(Context context) {
        if (isChargingRequired()) {
            Intent batteryStatus = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (batteryStatus != null) {
                int status = batteryStatus.getIntExtra("status", -1);
                return status == 2 || status == 5;
            }
            return false;
        }
        return true;
    }

    private boolean checkIdleRequirement(Context context) {
        if (isIdleRequired()) {
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            return Util.SDK_INT >= 23 ? !powerManager.isDeviceIdleMode() : Util.SDK_INT >= 20 ? !powerManager.isInteractive() : !powerManager.isScreenOn();
        }
        return true;
    }

    private static boolean checkInternetConnectivity(ConnectivityManager connectivityManager) {
        if (Util.SDK_INT < 23) {
            return true;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            logd("No active network.");
            return false;
        }
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
        boolean validated = networkCapabilities == null || !networkCapabilities.hasCapability(16);
        logd("Network capability validated: " + validated);
        return !validated;
    }

    private static boolean isActiveNetworkMetered(ConnectivityManager connectivityManager, NetworkInfo networkInfo) {
        if (Util.SDK_INT >= 16) {
            return connectivityManager.isActiveNetworkMetered();
        }
        int type = networkInfo.getType();
        return (type == 1 || type == 7 || type == 9) ? false : true;
    }

    private static void logd(String message) {
    }

    public String toString() {
        return super.toString();
    }
}
