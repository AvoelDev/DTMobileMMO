package com.google.android.exoplayer2.source.ads;

import android.net.Uri;
import android.support.annotation.CheckResult;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class AdPlaybackState {
    public static final int AD_STATE_AVAILABLE = 1;
    public static final int AD_STATE_ERROR = 4;
    public static final int AD_STATE_PLAYED = 3;
    public static final int AD_STATE_SKIPPED = 2;
    public static final int AD_STATE_UNAVAILABLE = 0;
    public static final AdPlaybackState NONE = new AdPlaybackState(new long[0]);
    public final int adGroupCount;
    public final long[] adGroupTimesUs;
    public final AdGroup[] adGroups;
    public final long adResumePositionUs;
    public final long contentDurationUs;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface AdState {
    }

    /* loaded from: classes.dex */
    public static final class AdGroup {
        public final int count;
        public final long[] durationsUs;
        public final int[] states;
        public final Uri[] uris;

        public AdGroup() {
            this(-1, new int[0], new Uri[0], new long[0]);
        }

        private AdGroup(int count, int[] states, Uri[] uris, long[] durationsUs) {
            Assertions.checkArgument(states.length == uris.length);
            this.count = count;
            this.states = states;
            this.uris = uris;
            this.durationsUs = durationsUs;
        }

        public int getFirstAdIndexToPlay() {
            return getNextAdIndexToPlay(-1);
        }

        public int getNextAdIndexToPlay(int lastPlayedAdIndex) {
            int nextAdIndexToPlay = lastPlayedAdIndex + 1;
            while (nextAdIndexToPlay < this.states.length && this.states[nextAdIndexToPlay] != 0 && this.states[nextAdIndexToPlay] != 1) {
                nextAdIndexToPlay++;
            }
            return nextAdIndexToPlay;
        }

        public boolean hasUnplayedAds() {
            return this.count == -1 || getFirstAdIndexToPlay() < this.count;
        }

        @CheckResult
        public AdGroup withAdCount(int count) {
            Assertions.checkArgument(this.count == -1 && this.states.length <= count);
            int[] states = copyStatesWithSpaceForAdCount(this.states, count);
            long[] durationsUs = copyDurationsUsWithSpaceForAdCount(this.durationsUs, count);
            Uri[] uris = (Uri[]) Arrays.copyOf(this.uris, count);
            return new AdGroup(count, states, uris, durationsUs);
        }

        @CheckResult
        public AdGroup withAdUri(Uri uri, int index) {
            Assertions.checkArgument(this.count == -1 || index < this.count);
            int[] states = copyStatesWithSpaceForAdCount(this.states, index + 1);
            Assertions.checkArgument(states[index] == 0);
            long[] durationsUs = this.durationsUs.length == states.length ? this.durationsUs : copyDurationsUsWithSpaceForAdCount(this.durationsUs, states.length);
            Uri[] uris = (Uri[]) Arrays.copyOf(this.uris, states.length);
            uris[index] = uri;
            states[index] = 1;
            return new AdGroup(this.count, states, uris, durationsUs);
        }

        @CheckResult
        public AdGroup withAdState(int state, int index) {
            boolean z = false;
            Assertions.checkArgument(this.count == -1 || index < this.count);
            int[] states = copyStatesWithSpaceForAdCount(this.states, index + 1);
            if (states[index] == 0 || states[index] == 1 || states[index] == state) {
                z = true;
            }
            Assertions.checkArgument(z);
            long[] durationsUs = this.durationsUs.length == states.length ? this.durationsUs : copyDurationsUsWithSpaceForAdCount(this.durationsUs, states.length);
            Uri[] uris = this.uris.length == states.length ? this.uris : (Uri[]) Arrays.copyOf(this.uris, states.length);
            states[index] = state;
            return new AdGroup(this.count, states, uris, durationsUs);
        }

        @CheckResult
        public AdGroup withAdDurationsUs(long[] durationsUs) {
            Assertions.checkArgument(this.count == -1 || durationsUs.length <= this.uris.length);
            if (durationsUs.length < this.uris.length) {
                durationsUs = copyDurationsUsWithSpaceForAdCount(durationsUs, this.uris.length);
            }
            return new AdGroup(this.count, this.states, this.uris, durationsUs);
        }

        @CheckResult
        public AdGroup withAllAdsSkipped() {
            if (this.count == -1) {
                return new AdGroup(0, new int[0], new Uri[0], new long[0]);
            }
            int count = this.states.length;
            int[] states = Arrays.copyOf(this.states, count);
            for (int i = 0; i < count; i++) {
                if (states[i] == 1 || states[i] == 0) {
                    states[i] = 2;
                }
            }
            return new AdGroup(count, states, this.uris, this.durationsUs);
        }

        @CheckResult
        private static int[] copyStatesWithSpaceForAdCount(int[] states, int count) {
            int oldStateCount = states.length;
            int newStateCount = Math.max(count, oldStateCount);
            int[] states2 = Arrays.copyOf(states, newStateCount);
            Arrays.fill(states2, oldStateCount, newStateCount, 0);
            return states2;
        }

        @CheckResult
        private static long[] copyDurationsUsWithSpaceForAdCount(long[] durationsUs, int count) {
            int oldDurationsUsCount = durationsUs.length;
            int newDurationsUsCount = Math.max(count, oldDurationsUsCount);
            long[] durationsUs2 = Arrays.copyOf(durationsUs, newDurationsUsCount);
            Arrays.fill(durationsUs2, oldDurationsUsCount, newDurationsUsCount, (long) C.TIME_UNSET);
            return durationsUs2;
        }
    }

    public AdPlaybackState(long... adGroupTimesUs) {
        int count = adGroupTimesUs.length;
        this.adGroupCount = count;
        this.adGroupTimesUs = Arrays.copyOf(adGroupTimesUs, count);
        this.adGroups = new AdGroup[count];
        for (int i = 0; i < count; i++) {
            this.adGroups[i] = new AdGroup();
        }
        this.adResumePositionUs = 0L;
        this.contentDurationUs = C.TIME_UNSET;
    }

    private AdPlaybackState(long[] adGroupTimesUs, AdGroup[] adGroups, long adResumePositionUs, long contentDurationUs) {
        this.adGroupCount = adGroups.length;
        this.adGroupTimesUs = adGroupTimesUs;
        this.adGroups = adGroups;
        this.adResumePositionUs = adResumePositionUs;
        this.contentDurationUs = contentDurationUs;
    }

    public int getAdGroupIndexForPositionUs(long positionUs) {
        int index = this.adGroupTimesUs.length - 1;
        while (index >= 0 && (this.adGroupTimesUs[index] == Long.MIN_VALUE || this.adGroupTimesUs[index] > positionUs)) {
            index--;
        }
        if (index < 0 || !this.adGroups[index].hasUnplayedAds()) {
            return -1;
        }
        return index;
    }

    public int getAdGroupIndexAfterPositionUs(long positionUs) {
        int index = 0;
        while (index < this.adGroupTimesUs.length && this.adGroupTimesUs[index] != Long.MIN_VALUE && (positionUs >= this.adGroupTimesUs[index] || !this.adGroups[index].hasUnplayedAds())) {
            index++;
        }
        if (index < this.adGroupTimesUs.length) {
            return index;
        }
        return -1;
    }

    @CheckResult
    public AdPlaybackState withAdCount(int adGroupIndex, int adCount) {
        Assertions.checkArgument(adCount > 0);
        if (this.adGroups[adGroupIndex].count != adCount) {
            AdGroup[] adGroups = (AdGroup[]) Arrays.copyOf(this.adGroups, this.adGroups.length);
            adGroups[adGroupIndex] = this.adGroups[adGroupIndex].withAdCount(adCount);
            return new AdPlaybackState(this.adGroupTimesUs, adGroups, this.adResumePositionUs, this.contentDurationUs);
        }
        return this;
    }

    @CheckResult
    public AdPlaybackState withAdUri(int adGroupIndex, int adIndexInAdGroup, Uri uri) {
        AdGroup[] adGroups = (AdGroup[]) Arrays.copyOf(this.adGroups, this.adGroups.length);
        adGroups[adGroupIndex] = adGroups[adGroupIndex].withAdUri(uri, adIndexInAdGroup);
        return new AdPlaybackState(this.adGroupTimesUs, adGroups, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withPlayedAd(int adGroupIndex, int adIndexInAdGroup) {
        AdGroup[] adGroups = (AdGroup[]) Arrays.copyOf(this.adGroups, this.adGroups.length);
        adGroups[adGroupIndex] = adGroups[adGroupIndex].withAdState(3, adIndexInAdGroup);
        return new AdPlaybackState(this.adGroupTimesUs, adGroups, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withSkippedAd(int adGroupIndex, int adIndexInAdGroup) {
        AdGroup[] adGroups = (AdGroup[]) Arrays.copyOf(this.adGroups, this.adGroups.length);
        adGroups[adGroupIndex] = adGroups[adGroupIndex].withAdState(2, adIndexInAdGroup);
        return new AdPlaybackState(this.adGroupTimesUs, adGroups, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withAdLoadError(int adGroupIndex, int adIndexInAdGroup) {
        AdGroup[] adGroups = (AdGroup[]) Arrays.copyOf(this.adGroups, this.adGroups.length);
        adGroups[adGroupIndex] = adGroups[adGroupIndex].withAdState(4, adIndexInAdGroup);
        return new AdPlaybackState(this.adGroupTimesUs, adGroups, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withSkippedAdGroup(int adGroupIndex) {
        AdGroup[] adGroups = (AdGroup[]) Arrays.copyOf(this.adGroups, this.adGroups.length);
        adGroups[adGroupIndex] = adGroups[adGroupIndex].withAllAdsSkipped();
        return new AdPlaybackState(this.adGroupTimesUs, adGroups, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withAdDurationsUs(long[][] adDurationUs) {
        AdGroup[] adGroups = (AdGroup[]) Arrays.copyOf(this.adGroups, this.adGroups.length);
        for (int adGroupIndex = 0; adGroupIndex < this.adGroupCount; adGroupIndex++) {
            adGroups[adGroupIndex] = adGroups[adGroupIndex].withAdDurationsUs(adDurationUs[adGroupIndex]);
        }
        return new AdPlaybackState(this.adGroupTimesUs, adGroups, this.adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withAdResumePositionUs(long adResumePositionUs) {
        return this.adResumePositionUs == adResumePositionUs ? this : new AdPlaybackState(this.adGroupTimesUs, this.adGroups, adResumePositionUs, this.contentDurationUs);
    }

    @CheckResult
    public AdPlaybackState withContentDurationUs(long contentDurationUs) {
        return this.contentDurationUs == contentDurationUs ? this : new AdPlaybackState(this.adGroupTimesUs, this.adGroups, this.adResumePositionUs, contentDurationUs);
    }
}
