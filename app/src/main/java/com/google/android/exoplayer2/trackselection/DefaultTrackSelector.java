package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class DefaultTrackSelector extends MappingTrackSelector {
    private static final float FRACTION_TO_CONSIDER_FULLSCREEN = 0.98f;
    private static final int[] NO_TRACKS = new int[0];
    private static final int WITHIN_RENDERER_CAPABILITIES_BONUS = 1000;
    @Nullable
    private final TrackSelection.Factory adaptiveTrackSelectionFactory;
    private final AtomicReference<Parameters> parametersReference;

    /* loaded from: classes.dex */
    public static final class ParametersBuilder {
        private boolean allowMixedMimeAdaptiveness;
        private boolean allowNonSeamlessAdaptiveness;
        private int disabledTextTrackSelectionFlags;
        private boolean exceedRendererCapabilitiesIfNecessary;
        private boolean exceedVideoConstraintsIfNecessary;
        private boolean forceLowestBitrate;
        private int maxVideoBitrate;
        private int maxVideoHeight;
        private int maxVideoWidth;
        @Nullable
        private String preferredAudioLanguage;
        @Nullable
        private String preferredTextLanguage;
        private final SparseBooleanArray rendererDisabledFlags;
        private boolean selectUndeterminedTextLanguage;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        private int tunnelingAudioSessionId;
        private int viewportHeight;
        private boolean viewportOrientationMayChange;
        private int viewportWidth;

        public ParametersBuilder() {
            this(Parameters.DEFAULT);
        }

        private ParametersBuilder(Parameters initialValues) {
            this.selectionOverrides = cloneSelectionOverrides(initialValues.selectionOverrides);
            this.rendererDisabledFlags = initialValues.rendererDisabledFlags.clone();
            this.preferredAudioLanguage = initialValues.preferredAudioLanguage;
            this.preferredTextLanguage = initialValues.preferredTextLanguage;
            this.selectUndeterminedTextLanguage = initialValues.selectUndeterminedTextLanguage;
            this.disabledTextTrackSelectionFlags = initialValues.disabledTextTrackSelectionFlags;
            this.forceLowestBitrate = initialValues.forceLowestBitrate;
            this.allowMixedMimeAdaptiveness = initialValues.allowMixedMimeAdaptiveness;
            this.allowNonSeamlessAdaptiveness = initialValues.allowNonSeamlessAdaptiveness;
            this.maxVideoWidth = initialValues.maxVideoWidth;
            this.maxVideoHeight = initialValues.maxVideoHeight;
            this.maxVideoBitrate = initialValues.maxVideoBitrate;
            this.exceedVideoConstraintsIfNecessary = initialValues.exceedVideoConstraintsIfNecessary;
            this.exceedRendererCapabilitiesIfNecessary = initialValues.exceedRendererCapabilitiesIfNecessary;
            this.viewportWidth = initialValues.viewportWidth;
            this.viewportHeight = initialValues.viewportHeight;
            this.viewportOrientationMayChange = initialValues.viewportOrientationMayChange;
            this.tunnelingAudioSessionId = initialValues.tunnelingAudioSessionId;
        }

        public ParametersBuilder setPreferredAudioLanguage(String preferredAudioLanguage) {
            this.preferredAudioLanguage = preferredAudioLanguage;
            return this;
        }

        public ParametersBuilder setPreferredTextLanguage(String preferredTextLanguage) {
            this.preferredTextLanguage = preferredTextLanguage;
            return this;
        }

        public ParametersBuilder setSelectUndeterminedTextLanguage(boolean selectUndeterminedTextLanguage) {
            this.selectUndeterminedTextLanguage = selectUndeterminedTextLanguage;
            return this;
        }

        public ParametersBuilder setDisabledTextTrackSelectionFlags(int disabledTextTrackSelectionFlags) {
            this.disabledTextTrackSelectionFlags = disabledTextTrackSelectionFlags;
            return this;
        }

        public ParametersBuilder setForceLowestBitrate(boolean forceLowestBitrate) {
            this.forceLowestBitrate = forceLowestBitrate;
            return this;
        }

        public ParametersBuilder setAllowMixedMimeAdaptiveness(boolean allowMixedMimeAdaptiveness) {
            this.allowMixedMimeAdaptiveness = allowMixedMimeAdaptiveness;
            return this;
        }

        public ParametersBuilder setAllowNonSeamlessAdaptiveness(boolean allowNonSeamlessAdaptiveness) {
            this.allowNonSeamlessAdaptiveness = allowNonSeamlessAdaptiveness;
            return this;
        }

        public ParametersBuilder setMaxVideoSizeSd() {
            return setMaxVideoSize(1279, 719);
        }

        public ParametersBuilder clearVideoSizeConstraints() {
            return setMaxVideoSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        public ParametersBuilder setMaxVideoSize(int maxVideoWidth, int maxVideoHeight) {
            this.maxVideoWidth = maxVideoWidth;
            this.maxVideoHeight = maxVideoHeight;
            return this;
        }

        public ParametersBuilder setMaxVideoBitrate(int maxVideoBitrate) {
            this.maxVideoBitrate = maxVideoBitrate;
            return this;
        }

        public ParametersBuilder setExceedVideoConstraintsIfNecessary(boolean exceedVideoConstraintsIfNecessary) {
            this.exceedVideoConstraintsIfNecessary = exceedVideoConstraintsIfNecessary;
            return this;
        }

        public ParametersBuilder setExceedRendererCapabilitiesIfNecessary(boolean exceedRendererCapabilitiesIfNecessary) {
            this.exceedRendererCapabilitiesIfNecessary = exceedRendererCapabilitiesIfNecessary;
            return this;
        }

        public ParametersBuilder setViewportSizeToPhysicalDisplaySize(Context context, boolean viewportOrientationMayChange) {
            Point viewportSize = Util.getPhysicalDisplaySize(context);
            return setViewportSize(viewportSize.x, viewportSize.y, viewportOrientationMayChange);
        }

        public ParametersBuilder clearViewportSizeConstraints() {
            return setViewportSize(Integer.MAX_VALUE, Integer.MAX_VALUE, true);
        }

        public ParametersBuilder setViewportSize(int viewportWidth, int viewportHeight, boolean viewportOrientationMayChange) {
            this.viewportWidth = viewportWidth;
            this.viewportHeight = viewportHeight;
            this.viewportOrientationMayChange = viewportOrientationMayChange;
            return this;
        }

        public final ParametersBuilder setRendererDisabled(int rendererIndex, boolean disabled) {
            if (this.rendererDisabledFlags.get(rendererIndex) != disabled) {
                if (disabled) {
                    this.rendererDisabledFlags.put(rendererIndex, true);
                } else {
                    this.rendererDisabledFlags.delete(rendererIndex);
                }
            }
            return this;
        }

        public final ParametersBuilder setSelectionOverride(int rendererIndex, TrackGroupArray groups, SelectionOverride override) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            if (overrides == null) {
                overrides = new HashMap<>();
                this.selectionOverrides.put(rendererIndex, overrides);
            }
            if (!overrides.containsKey(groups) || !Util.areEqual(overrides.get(groups), override)) {
                overrides.put(groups, override);
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverride(int rendererIndex, TrackGroupArray groups) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            if (overrides != null && overrides.containsKey(groups)) {
                overrides.remove(groups);
                if (overrides.isEmpty()) {
                    this.selectionOverrides.remove(rendererIndex);
                }
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides(int rendererIndex) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            if (overrides != null && !overrides.isEmpty()) {
                this.selectionOverrides.remove(rendererIndex);
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides() {
            if (this.selectionOverrides.size() != 0) {
                this.selectionOverrides.clear();
            }
            return this;
        }

        public ParametersBuilder setTunnelingAudioSessionId(int tunnelingAudioSessionId) {
            if (this.tunnelingAudioSessionId != tunnelingAudioSessionId) {
                this.tunnelingAudioSessionId = tunnelingAudioSessionId;
            }
            return this;
        }

        public Parameters build() {
            return new Parameters(this.selectionOverrides, this.rendererDisabledFlags, this.preferredAudioLanguage, this.preferredTextLanguage, this.selectUndeterminedTextLanguage, this.disabledTextTrackSelectionFlags, this.forceLowestBitrate, this.allowMixedMimeAdaptiveness, this.allowNonSeamlessAdaptiveness, this.maxVideoWidth, this.maxVideoHeight, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, this.exceedRendererCapabilitiesIfNecessary, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange, this.tunnelingAudioSessionId);
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> cloneSelectionOverrides(SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides) {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> clone = new SparseArray<>();
            for (int i = 0; i < selectionOverrides.size(); i++) {
                clone.put(selectionOverrides.keyAt(i), new HashMap(selectionOverrides.valueAt(i)));
            }
            return clone;
        }
    }

    /* loaded from: classes.dex */
    public static final class Parameters implements Parcelable {
        public final boolean allowMixedMimeAdaptiveness;
        public final boolean allowNonSeamlessAdaptiveness;
        public final int disabledTextTrackSelectionFlags;
        public final boolean exceedRendererCapabilitiesIfNecessary;
        public final boolean exceedVideoConstraintsIfNecessary;
        public final boolean forceLowestBitrate;
        public final int maxVideoBitrate;
        public final int maxVideoHeight;
        public final int maxVideoWidth;
        @Nullable
        public final String preferredAudioLanguage;
        @Nullable
        public final String preferredTextLanguage;
        private final SparseBooleanArray rendererDisabledFlags;
        public final boolean selectUndeterminedTextLanguage;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides;
        public final int tunnelingAudioSessionId;
        public final int viewportHeight;
        public final boolean viewportOrientationMayChange;
        public final int viewportWidth;
        public static final Parameters DEFAULT = new Parameters();
        public static final Parcelable.Creator<Parameters> CREATOR = new Parcelable.Creator<Parameters>() { // from class: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Parameters createFromParcel(Parcel in) {
                return new Parameters(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Parameters[] newArray(int size) {
                return new Parameters[size];
            }
        };

        private Parameters() {
            this(new SparseArray(), new SparseBooleanArray(), null, null, false, 0, false, false, true, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, true, true, Integer.MAX_VALUE, Integer.MAX_VALUE, true, 0);
        }

        Parameters(SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides, SparseBooleanArray rendererDisabledFlags, @Nullable String preferredAudioLanguage, @Nullable String preferredTextLanguage, boolean selectUndeterminedTextLanguage, int disabledTextTrackSelectionFlags, boolean forceLowestBitrate, boolean allowMixedMimeAdaptiveness, boolean allowNonSeamlessAdaptiveness, int maxVideoWidth, int maxVideoHeight, int maxVideoBitrate, boolean exceedVideoConstraintsIfNecessary, boolean exceedRendererCapabilitiesIfNecessary, int viewportWidth, int viewportHeight, boolean viewportOrientationMayChange, int tunnelingAudioSessionId) {
            this.selectionOverrides = selectionOverrides;
            this.rendererDisabledFlags = rendererDisabledFlags;
            this.preferredAudioLanguage = Util.normalizeLanguageCode(preferredAudioLanguage);
            this.preferredTextLanguage = Util.normalizeLanguageCode(preferredTextLanguage);
            this.selectUndeterminedTextLanguage = selectUndeterminedTextLanguage;
            this.disabledTextTrackSelectionFlags = disabledTextTrackSelectionFlags;
            this.forceLowestBitrate = forceLowestBitrate;
            this.allowMixedMimeAdaptiveness = allowMixedMimeAdaptiveness;
            this.allowNonSeamlessAdaptiveness = allowNonSeamlessAdaptiveness;
            this.maxVideoWidth = maxVideoWidth;
            this.maxVideoHeight = maxVideoHeight;
            this.maxVideoBitrate = maxVideoBitrate;
            this.exceedVideoConstraintsIfNecessary = exceedVideoConstraintsIfNecessary;
            this.exceedRendererCapabilitiesIfNecessary = exceedRendererCapabilitiesIfNecessary;
            this.viewportWidth = viewportWidth;
            this.viewportHeight = viewportHeight;
            this.viewportOrientationMayChange = viewportOrientationMayChange;
            this.tunnelingAudioSessionId = tunnelingAudioSessionId;
        }

        Parameters(Parcel in) {
            this.selectionOverrides = readSelectionOverrides(in);
            this.rendererDisabledFlags = in.readSparseBooleanArray();
            this.preferredAudioLanguage = in.readString();
            this.preferredTextLanguage = in.readString();
            this.selectUndeterminedTextLanguage = Util.readBoolean(in);
            this.disabledTextTrackSelectionFlags = in.readInt();
            this.forceLowestBitrate = Util.readBoolean(in);
            this.allowMixedMimeAdaptiveness = Util.readBoolean(in);
            this.allowNonSeamlessAdaptiveness = Util.readBoolean(in);
            this.maxVideoWidth = in.readInt();
            this.maxVideoHeight = in.readInt();
            this.maxVideoBitrate = in.readInt();
            this.exceedVideoConstraintsIfNecessary = Util.readBoolean(in);
            this.exceedRendererCapabilitiesIfNecessary = Util.readBoolean(in);
            this.viewportWidth = in.readInt();
            this.viewportHeight = in.readInt();
            this.viewportOrientationMayChange = Util.readBoolean(in);
            this.tunnelingAudioSessionId = in.readInt();
        }

        public final boolean getRendererDisabled(int rendererIndex) {
            return this.rendererDisabledFlags.get(rendererIndex);
        }

        public final boolean hasSelectionOverride(int rendererIndex, TrackGroupArray groups) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            return overrides != null && overrides.containsKey(groups);
        }

        @Nullable
        public final SelectionOverride getSelectionOverride(int rendererIndex, TrackGroupArray groups) {
            Map<TrackGroupArray, SelectionOverride> overrides = this.selectionOverrides.get(rendererIndex);
            if (overrides != null) {
                return overrides.get(groups);
            }
            return null;
        }

        public ParametersBuilder buildUpon() {
            return new ParametersBuilder(this);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Parameters other = (Parameters) obj;
            return this.selectUndeterminedTextLanguage == other.selectUndeterminedTextLanguage && this.disabledTextTrackSelectionFlags == other.disabledTextTrackSelectionFlags && this.forceLowestBitrate == other.forceLowestBitrate && this.allowMixedMimeAdaptiveness == other.allowMixedMimeAdaptiveness && this.allowNonSeamlessAdaptiveness == other.allowNonSeamlessAdaptiveness && this.maxVideoWidth == other.maxVideoWidth && this.maxVideoHeight == other.maxVideoHeight && this.exceedVideoConstraintsIfNecessary == other.exceedVideoConstraintsIfNecessary && this.exceedRendererCapabilitiesIfNecessary == other.exceedRendererCapabilitiesIfNecessary && this.viewportOrientationMayChange == other.viewportOrientationMayChange && this.viewportWidth == other.viewportWidth && this.viewportHeight == other.viewportHeight && this.maxVideoBitrate == other.maxVideoBitrate && this.tunnelingAudioSessionId == other.tunnelingAudioSessionId && TextUtils.equals(this.preferredAudioLanguage, other.preferredAudioLanguage) && TextUtils.equals(this.preferredTextLanguage, other.preferredTextLanguage) && areRendererDisabledFlagsEqual(this.rendererDisabledFlags, other.rendererDisabledFlags) && areSelectionOverridesEqual(this.selectionOverrides, other.selectionOverrides);
        }

        public int hashCode() {
            int result = this.selectUndeterminedTextLanguage ? 1 : 0;
            return (((((((((((((((((((((((((((((result * 31) + this.disabledTextTrackSelectionFlags) * 31) + (this.forceLowestBitrate ? 1 : 0)) * 31) + (this.allowMixedMimeAdaptiveness ? 1 : 0)) * 31) + (this.allowNonSeamlessAdaptiveness ? 1 : 0)) * 31) + this.maxVideoWidth) * 31) + this.maxVideoHeight) * 31) + (this.exceedVideoConstraintsIfNecessary ? 1 : 0)) * 31) + (this.exceedRendererCapabilitiesIfNecessary ? 1 : 0)) * 31) + (this.viewportOrientationMayChange ? 1 : 0)) * 31) + this.viewportWidth) * 31) + this.viewportHeight) * 31) + this.maxVideoBitrate) * 31) + this.tunnelingAudioSessionId) * 31) + (this.preferredAudioLanguage == null ? 0 : this.preferredAudioLanguage.hashCode())) * 31) + (this.preferredTextLanguage != null ? this.preferredTextLanguage.hashCode() : 0);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            writeSelectionOverridesToParcel(dest, this.selectionOverrides);
            dest.writeSparseBooleanArray(this.rendererDisabledFlags);
            dest.writeString(this.preferredAudioLanguage);
            dest.writeString(this.preferredTextLanguage);
            Util.writeBoolean(dest, this.selectUndeterminedTextLanguage);
            dest.writeInt(this.disabledTextTrackSelectionFlags);
            Util.writeBoolean(dest, this.forceLowestBitrate);
            Util.writeBoolean(dest, this.allowMixedMimeAdaptiveness);
            Util.writeBoolean(dest, this.allowNonSeamlessAdaptiveness);
            dest.writeInt(this.maxVideoWidth);
            dest.writeInt(this.maxVideoHeight);
            dest.writeInt(this.maxVideoBitrate);
            Util.writeBoolean(dest, this.exceedVideoConstraintsIfNecessary);
            Util.writeBoolean(dest, this.exceedRendererCapabilitiesIfNecessary);
            dest.writeInt(this.viewportWidth);
            dest.writeInt(this.viewportHeight);
            Util.writeBoolean(dest, this.viewportOrientationMayChange);
            dest.writeInt(this.tunnelingAudioSessionId);
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> readSelectionOverrides(Parcel in) {
            int renderersWithOverridesCount = in.readInt();
            SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides = new SparseArray<>(renderersWithOverridesCount);
            for (int i = 0; i < renderersWithOverridesCount; i++) {
                int rendererIndex = in.readInt();
                int overrideCount = in.readInt();
                Map<TrackGroupArray, SelectionOverride> overrides = new HashMap<>(overrideCount);
                for (int j = 0; j < overrideCount; j++) {
                    TrackGroupArray trackGroups = (TrackGroupArray) in.readParcelable(TrackGroupArray.class.getClassLoader());
                    SelectionOverride override = (SelectionOverride) in.readParcelable(SelectionOverride.class.getClassLoader());
                    overrides.put(trackGroups, override);
                }
                selectionOverrides.put(rendererIndex, overrides);
            }
            return selectionOverrides;
        }

        private static void writeSelectionOverridesToParcel(Parcel dest, SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides) {
            int renderersWithOverridesCount = selectionOverrides.size();
            dest.writeInt(renderersWithOverridesCount);
            for (int i = 0; i < renderersWithOverridesCount; i++) {
                int rendererIndex = selectionOverrides.keyAt(i);
                Map<TrackGroupArray, SelectionOverride> overrides = selectionOverrides.valueAt(i);
                int overrideCount = overrides.size();
                dest.writeInt(rendererIndex);
                dest.writeInt(overrideCount);
                for (Map.Entry<TrackGroupArray, SelectionOverride> override : overrides.entrySet()) {
                    dest.writeParcelable(override.getKey(), 0);
                    dest.writeParcelable(override.getValue(), 0);
                }
            }
        }

        private static boolean areRendererDisabledFlagsEqual(SparseBooleanArray first, SparseBooleanArray second) {
            int firstSize = first.size();
            if (second.size() != firstSize) {
                return false;
            }
            for (int indexInFirst = 0; indexInFirst < firstSize; indexInFirst++) {
                if (second.indexOfKey(first.keyAt(indexInFirst)) < 0) {
                    return false;
                }
            }
            return true;
        }

        private static boolean areSelectionOverridesEqual(SparseArray<Map<TrackGroupArray, SelectionOverride>> first, SparseArray<Map<TrackGroupArray, SelectionOverride>> second) {
            int firstSize = first.size();
            if (second.size() != firstSize) {
                return false;
            }
            for (int indexInFirst = 0; indexInFirst < firstSize; indexInFirst++) {
                int indexInSecond = second.indexOfKey(first.keyAt(indexInFirst));
                if (indexInSecond < 0 || !areSelectionOverridesEqual(first.valueAt(indexInFirst), second.valueAt(indexInSecond))) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x001a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static boolean areSelectionOverridesEqual(java.util.Map<com.google.android.exoplayer2.source.TrackGroupArray, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride> r7, java.util.Map<com.google.android.exoplayer2.source.TrackGroupArray, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride> r8) {
            /*
                r3 = 0
                int r1 = r7.size()
                int r4 = r8.size()
                if (r4 == r1) goto Lc
            Lb:
                return r3
            Lc:
                java.util.Set r4 = r7.entrySet()
                java.util.Iterator r4 = r4.iterator()
            L14:
                boolean r5 = r4.hasNext()
                if (r5 == 0) goto L3b
                java.lang.Object r0 = r4.next()
                java.util.Map$Entry r0 = (java.util.Map.Entry) r0
                java.lang.Object r2 = r0.getKey()
                com.google.android.exoplayer2.source.TrackGroupArray r2 = (com.google.android.exoplayer2.source.TrackGroupArray) r2
                boolean r5 = r8.containsKey(r2)
                if (r5 == 0) goto Lb
                java.lang.Object r5 = r0.getValue()
                java.lang.Object r6 = r8.get(r2)
                boolean r5 = com.google.android.exoplayer2.util.Util.areEqual(r5, r6)
                if (r5 != 0) goto L14
                goto Lb
            L3b:
                r3 = 1
                goto Lb
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters.areSelectionOverridesEqual(java.util.Map, java.util.Map):boolean");
        }
    }

    /* loaded from: classes.dex */
    public static final class SelectionOverride implements Parcelable {
        public static final Parcelable.Creator<SelectionOverride> CREATOR = new Parcelable.Creator<SelectionOverride>() { // from class: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SelectionOverride createFromParcel(Parcel in) {
                return new SelectionOverride(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SelectionOverride[] newArray(int size) {
                return new SelectionOverride[size];
            }
        };
        public final int groupIndex;
        public final int length;
        public final int[] tracks;

        public SelectionOverride(int groupIndex, int... tracks) {
            this.groupIndex = groupIndex;
            this.tracks = Arrays.copyOf(tracks, tracks.length);
            this.length = tracks.length;
            Arrays.sort(this.tracks);
        }

        SelectionOverride(Parcel in) {
            this.groupIndex = in.readInt();
            this.length = in.readByte();
            this.tracks = new int[this.length];
            in.readIntArray(this.tracks);
        }

        public boolean containsTrack(int track) {
            int[] iArr;
            for (int overrideTrack : this.tracks) {
                if (overrideTrack == track) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (this.groupIndex * 31) + Arrays.hashCode(this.tracks);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SelectionOverride other = (SelectionOverride) obj;
            return this.groupIndex == other.groupIndex && Arrays.equals(this.tracks, other.tracks);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.groupIndex);
            dest.writeInt(this.tracks.length);
            dest.writeIntArray(this.tracks);
        }
    }

    public DefaultTrackSelector() {
        this((TrackSelection.Factory) null);
    }

    public DefaultTrackSelector(BandwidthMeter bandwidthMeter) {
        this(new AdaptiveTrackSelection.Factory(bandwidthMeter));
    }

    public DefaultTrackSelector(@Nullable TrackSelection.Factory adaptiveTrackSelectionFactory) {
        this.adaptiveTrackSelectionFactory = adaptiveTrackSelectionFactory;
        this.parametersReference = new AtomicReference<>(Parameters.DEFAULT);
    }

    public void setParameters(Parameters parameters) {
        Assertions.checkNotNull(parameters);
        if (!this.parametersReference.getAndSet(parameters).equals(parameters)) {
            invalidate();
        }
    }

    public void setParameters(ParametersBuilder parametersBuilder) {
        setParameters(parametersBuilder.build());
    }

    public Parameters getParameters() {
        return this.parametersReference.get();
    }

    public ParametersBuilder buildUponParameters() {
        return getParameters().buildUpon();
    }

    @Deprecated
    public final void setRendererDisabled(int rendererIndex, boolean disabled) {
        setParameters(buildUponParameters().setRendererDisabled(rendererIndex, disabled));
    }

    @Deprecated
    public final boolean getRendererDisabled(int rendererIndex) {
        return getParameters().getRendererDisabled(rendererIndex);
    }

    @Deprecated
    public final void setSelectionOverride(int rendererIndex, TrackGroupArray groups, SelectionOverride override) {
        setParameters(buildUponParameters().setSelectionOverride(rendererIndex, groups, override));
    }

    @Deprecated
    public final boolean hasSelectionOverride(int rendererIndex, TrackGroupArray groups) {
        return getParameters().hasSelectionOverride(rendererIndex, groups);
    }

    @Nullable
    @Deprecated
    public final SelectionOverride getSelectionOverride(int rendererIndex, TrackGroupArray groups) {
        return getParameters().getSelectionOverride(rendererIndex, groups);
    }

    @Deprecated
    public final void clearSelectionOverride(int rendererIndex, TrackGroupArray groups) {
        setParameters(buildUponParameters().clearSelectionOverride(rendererIndex, groups));
    }

    @Deprecated
    public final void clearSelectionOverrides(int rendererIndex) {
        setParameters(buildUponParameters().clearSelectionOverrides(rendererIndex));
    }

    @Deprecated
    public final void clearSelectionOverrides() {
        setParameters(buildUponParameters().clearSelectionOverrides());
    }

    @Deprecated
    public void setTunnelingAudioSessionId(int tunnelingAudioSessionId) {
        setParameters(buildUponParameters().setTunnelingAudioSessionId(tunnelingAudioSessionId));
    }

    @Override // com.google.android.exoplayer2.trackselection.MappingTrackSelector
    protected final Pair<RendererConfiguration[], TrackSelection[]> selectTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] rendererFormatSupports, int[] rendererMixedMimeTypeAdaptationSupports) throws ExoPlaybackException {
        Parameters params = this.parametersReference.get();
        int rendererCount = mappedTrackInfo.getRendererCount();
        TrackSelection[] rendererTrackSelections = selectAllTracks(mappedTrackInfo, rendererFormatSupports, rendererMixedMimeTypeAdaptationSupports, params);
        for (int i = 0; i < rendererCount; i++) {
            if (params.getRendererDisabled(i)) {
                rendererTrackSelections[i] = null;
            } else {
                TrackGroupArray rendererTrackGroups = mappedTrackInfo.getTrackGroups(i);
                if (params.hasSelectionOverride(i, rendererTrackGroups)) {
                    SelectionOverride override = params.getSelectionOverride(i, rendererTrackGroups);
                    if (override == null) {
                        rendererTrackSelections[i] = null;
                    } else if (override.length == 1) {
                        rendererTrackSelections[i] = new FixedTrackSelection(rendererTrackGroups.get(override.groupIndex), override.tracks[0]);
                    } else {
                        rendererTrackSelections[i] = ((TrackSelection.Factory) Assertions.checkNotNull(this.adaptiveTrackSelectionFactory)).createTrackSelection(rendererTrackGroups.get(override.groupIndex), override.tracks);
                    }
                }
            }
        }
        RendererConfiguration[] rendererConfigurations = new RendererConfiguration[rendererCount];
        for (int i2 = 0; i2 < rendererCount; i2++) {
            boolean forceRendererDisabled = params.getRendererDisabled(i2);
            boolean rendererEnabled = !forceRendererDisabled && (mappedTrackInfo.getRendererType(i2) == 5 || rendererTrackSelections[i2] != null);
            rendererConfigurations[i2] = rendererEnabled ? RendererConfiguration.DEFAULT : null;
        }
        maybeConfigureRenderersForTunneling(mappedTrackInfo, rendererFormatSupports, rendererConfigurations, rendererTrackSelections, params.tunnelingAudioSessionId);
        return Pair.create(rendererConfigurations, rendererTrackSelections);
    }

    protected TrackSelection[] selectAllTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] rendererFormatSupports, int[] rendererMixedMimeTypeAdaptationSupports, Parameters params) throws ExoPlaybackException {
        int rendererCount = mappedTrackInfo.getRendererCount();
        TrackSelection[] rendererTrackSelections = new TrackSelection[rendererCount];
        boolean seenVideoRendererWithMappedTracks = false;
        boolean selectedVideoTracks = false;
        for (int i = 0; i < rendererCount; i++) {
            if (2 == mappedTrackInfo.getRendererType(i)) {
                if (!selectedVideoTracks) {
                    rendererTrackSelections[i] = selectVideoTrack(mappedTrackInfo.getTrackGroups(i), rendererFormatSupports[i], rendererMixedMimeTypeAdaptationSupports[i], params, this.adaptiveTrackSelectionFactory);
                    selectedVideoTracks = rendererTrackSelections[i] != null;
                }
                seenVideoRendererWithMappedTracks |= mappedTrackInfo.getTrackGroups(i).length > 0;
            }
        }
        boolean selectedAudioTracks = false;
        boolean selectedTextTracks = false;
        for (int i2 = 0; i2 < rendererCount; i2++) {
            int trackType = mappedTrackInfo.getRendererType(i2);
            switch (trackType) {
                case 1:
                    if (selectedAudioTracks) {
                        break;
                    } else {
                        rendererTrackSelections[i2] = selectAudioTrack(mappedTrackInfo.getTrackGroups(i2), rendererFormatSupports[i2], rendererMixedMimeTypeAdaptationSupports[i2], params, seenVideoRendererWithMappedTracks ? null : this.adaptiveTrackSelectionFactory);
                        if (rendererTrackSelections[i2] != null) {
                            selectedAudioTracks = true;
                            break;
                        } else {
                            selectedAudioTracks = false;
                            break;
                        }
                    }
                case 2:
                    break;
                case 3:
                    if (selectedTextTracks) {
                        break;
                    } else {
                        rendererTrackSelections[i2] = selectTextTrack(mappedTrackInfo.getTrackGroups(i2), rendererFormatSupports[i2], params);
                        if (rendererTrackSelections[i2] != null) {
                            selectedTextTracks = true;
                            break;
                        } else {
                            selectedTextTracks = false;
                            break;
                        }
                    }
                default:
                    rendererTrackSelections[i2] = selectOtherTrack(trackType, mappedTrackInfo.getTrackGroups(i2), rendererFormatSupports[i2], params);
                    break;
            }
        }
        return rendererTrackSelections;
    }

    @Nullable
    protected TrackSelection selectVideoTrack(TrackGroupArray groups, int[][] formatSupports, int mixedMimeTypeAdaptationSupports, Parameters params, @Nullable TrackSelection.Factory adaptiveTrackSelectionFactory) throws ExoPlaybackException {
        TrackSelection selection = null;
        if (!params.forceLowestBitrate && adaptiveTrackSelectionFactory != null) {
            selection = selectAdaptiveVideoTrack(groups, formatSupports, mixedMimeTypeAdaptationSupports, params, adaptiveTrackSelectionFactory);
        }
        if (selection == null) {
            return selectFixedVideoTrack(groups, formatSupports, params);
        }
        return selection;
    }

    @Nullable
    private static TrackSelection selectAdaptiveVideoTrack(TrackGroupArray groups, int[][] formatSupport, int mixedMimeTypeAdaptationSupports, Parameters params, TrackSelection.Factory adaptiveTrackSelectionFactory) throws ExoPlaybackException {
        int requiredAdaptiveSupport = params.allowNonSeamlessAdaptiveness ? 24 : 16;
        boolean allowMixedMimeTypes = params.allowMixedMimeAdaptiveness && (mixedMimeTypeAdaptationSupports & requiredAdaptiveSupport) != 0;
        for (int i = 0; i < groups.length; i++) {
            TrackGroup group = groups.get(i);
            int[] adaptiveTracks = getAdaptiveVideoTracksForGroup(group, formatSupport[i], allowMixedMimeTypes, requiredAdaptiveSupport, params.maxVideoWidth, params.maxVideoHeight, params.maxVideoBitrate, params.viewportWidth, params.viewportHeight, params.viewportOrientationMayChange);
            if (adaptiveTracks.length > 0) {
                return ((TrackSelection.Factory) Assertions.checkNotNull(adaptiveTrackSelectionFactory)).createTrackSelection(group, adaptiveTracks);
            }
        }
        return null;
    }

    private static int[] getAdaptiveVideoTracksForGroup(TrackGroup group, int[] formatSupport, boolean allowMixedMimeTypes, int requiredAdaptiveSupport, int maxVideoWidth, int maxVideoHeight, int maxVideoBitrate, int viewportWidth, int viewportHeight, boolean viewportOrientationMayChange) {
        if (group.length < 2) {
            return NO_TRACKS;
        }
        List<Integer> selectedTrackIndices = getViewportFilteredTrackIndices(group, viewportWidth, viewportHeight, viewportOrientationMayChange);
        if (selectedTrackIndices.size() < 2) {
            return NO_TRACKS;
        }
        String selectedMimeType = null;
        if (!allowMixedMimeTypes) {
            HashSet<String> seenMimeTypes = new HashSet<>();
            int selectedMimeTypeTrackCount = 0;
            for (int i = 0; i < selectedTrackIndices.size(); i++) {
                int trackIndex = selectedTrackIndices.get(i).intValue();
                String sampleMimeType = group.getFormat(trackIndex).sampleMimeType;
                if (seenMimeTypes.add(sampleMimeType)) {
                    int countForMimeType = getAdaptiveVideoTrackCountForMimeType(group, formatSupport, requiredAdaptiveSupport, sampleMimeType, maxVideoWidth, maxVideoHeight, maxVideoBitrate, selectedTrackIndices);
                    if (countForMimeType > selectedMimeTypeTrackCount) {
                        selectedMimeType = sampleMimeType;
                        selectedMimeTypeTrackCount = countForMimeType;
                    }
                }
            }
        }
        filterAdaptiveVideoTrackCountForMimeType(group, formatSupport, requiredAdaptiveSupport, selectedMimeType, maxVideoWidth, maxVideoHeight, maxVideoBitrate, selectedTrackIndices);
        return selectedTrackIndices.size() < 2 ? NO_TRACKS : Util.toArray(selectedTrackIndices);
    }

    private static int getAdaptiveVideoTrackCountForMimeType(TrackGroup group, int[] formatSupport, int requiredAdaptiveSupport, @Nullable String mimeType, int maxVideoWidth, int maxVideoHeight, int maxVideoBitrate, List<Integer> selectedTrackIndices) {
        int adaptiveTrackCount = 0;
        for (int i = 0; i < selectedTrackIndices.size(); i++) {
            int trackIndex = selectedTrackIndices.get(i).intValue();
            if (isSupportedAdaptiveVideoTrack(group.getFormat(trackIndex), mimeType, formatSupport[trackIndex], requiredAdaptiveSupport, maxVideoWidth, maxVideoHeight, maxVideoBitrate)) {
                adaptiveTrackCount++;
            }
        }
        return adaptiveTrackCount;
    }

    private static void filterAdaptiveVideoTrackCountForMimeType(TrackGroup group, int[] formatSupport, int requiredAdaptiveSupport, @Nullable String mimeType, int maxVideoWidth, int maxVideoHeight, int maxVideoBitrate, List<Integer> selectedTrackIndices) {
        for (int i = selectedTrackIndices.size() - 1; i >= 0; i--) {
            int trackIndex = selectedTrackIndices.get(i).intValue();
            if (!isSupportedAdaptiveVideoTrack(group.getFormat(trackIndex), mimeType, formatSupport[trackIndex], requiredAdaptiveSupport, maxVideoWidth, maxVideoHeight, maxVideoBitrate)) {
                selectedTrackIndices.remove(i);
            }
        }
    }

    private static boolean isSupportedAdaptiveVideoTrack(Format format, @Nullable String mimeType, int formatSupport, int requiredAdaptiveSupport, int maxVideoWidth, int maxVideoHeight, int maxVideoBitrate) {
        if (!isSupported(formatSupport, false) || (formatSupport & requiredAdaptiveSupport) == 0) {
            return false;
        }
        if (mimeType == null || Util.areEqual(format.sampleMimeType, mimeType)) {
            if (format.width == -1 || format.width <= maxVideoWidth) {
                if (format.height == -1 || format.height <= maxVideoHeight) {
                    return format.bitrate == -1 || format.bitrate <= maxVideoBitrate;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Nullable
    private static TrackSelection selectFixedVideoTrack(TrackGroupArray groups, int[][] formatSupports, Parameters params) {
        int comparisonResult;
        TrackGroup selectedGroup = null;
        int selectedTrackIndex = 0;
        int selectedTrackScore = 0;
        int selectedBitrate = -1;
        int selectedPixelCount = -1;
        for (int groupIndex = 0; groupIndex < groups.length; groupIndex++) {
            TrackGroup trackGroup = groups.get(groupIndex);
            List<Integer> selectedTrackIndices = getViewportFilteredTrackIndices(trackGroup, params.viewportWidth, params.viewportHeight, params.viewportOrientationMayChange);
            int[] trackFormatSupport = formatSupports[groupIndex];
            for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
                if (isSupported(trackFormatSupport[trackIndex], params.exceedRendererCapabilitiesIfNecessary)) {
                    Format format = trackGroup.getFormat(trackIndex);
                    boolean isWithinConstraints = selectedTrackIndices.contains(Integer.valueOf(trackIndex)) && (format.width == -1 || format.width <= params.maxVideoWidth) && ((format.height == -1 || format.height <= params.maxVideoHeight) && (format.bitrate == -1 || format.bitrate <= params.maxVideoBitrate));
                    if (isWithinConstraints || params.exceedVideoConstraintsIfNecessary) {
                        int trackScore = isWithinConstraints ? 2 : 1;
                        boolean isWithinCapabilities = isSupported(trackFormatSupport[trackIndex], false);
                        if (isWithinCapabilities) {
                            trackScore += 1000;
                        }
                        boolean selectTrack = trackScore > selectedTrackScore;
                        if (trackScore == selectedTrackScore) {
                            if (params.forceLowestBitrate) {
                                selectTrack = compareFormatValues(format.bitrate, selectedBitrate) < 0;
                            } else {
                                int formatPixelCount = format.getPixelCount();
                                if (formatPixelCount != selectedPixelCount) {
                                    comparisonResult = compareFormatValues(formatPixelCount, selectedPixelCount);
                                } else {
                                    comparisonResult = compareFormatValues(format.bitrate, selectedBitrate);
                                }
                                if (isWithinCapabilities && isWithinConstraints) {
                                    selectTrack = comparisonResult > 0;
                                } else {
                                    selectTrack = comparisonResult < 0;
                                }
                            }
                        }
                        if (selectTrack) {
                            selectedGroup = trackGroup;
                            selectedTrackIndex = trackIndex;
                            selectedTrackScore = trackScore;
                            selectedBitrate = format.bitrate;
                            selectedPixelCount = format.getPixelCount();
                        }
                    }
                }
            }
        }
        if (selectedGroup == null) {
            return null;
        }
        return new FixedTrackSelection(selectedGroup, selectedTrackIndex);
    }

    @Nullable
    protected TrackSelection selectAudioTrack(TrackGroupArray groups, int[][] formatSupports, int mixedMimeTypeAdaptationSupports, Parameters params, @Nullable TrackSelection.Factory adaptiveTrackSelectionFactory) throws ExoPlaybackException {
        int selectedTrackIndex = -1;
        int selectedGroupIndex = -1;
        AudioTrackScore selectedTrackScore = null;
        for (int groupIndex = 0; groupIndex < groups.length; groupIndex++) {
            TrackGroup trackGroup = groups.get(groupIndex);
            int[] trackFormatSupport = formatSupports[groupIndex];
            for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
                if (isSupported(trackFormatSupport[trackIndex], params.exceedRendererCapabilitiesIfNecessary)) {
                    Format format = trackGroup.getFormat(trackIndex);
                    AudioTrackScore trackScore = new AudioTrackScore(format, params, trackFormatSupport[trackIndex]);
                    if (selectedTrackScore == null || trackScore.compareTo(selectedTrackScore) > 0) {
                        selectedGroupIndex = groupIndex;
                        selectedTrackIndex = trackIndex;
                        selectedTrackScore = trackScore;
                    }
                }
            }
        }
        if (selectedGroupIndex == -1) {
            return null;
        }
        TrackGroup selectedGroup = groups.get(selectedGroupIndex);
        if (!params.forceLowestBitrate && adaptiveTrackSelectionFactory != null) {
            int[] adaptiveTracks = getAdaptiveAudioTracks(selectedGroup, formatSupports[selectedGroupIndex], params.allowMixedMimeAdaptiveness);
            if (adaptiveTracks.length > 0) {
                return adaptiveTrackSelectionFactory.createTrackSelection(selectedGroup, adaptiveTracks);
            }
        }
        return new FixedTrackSelection(selectedGroup, selectedTrackIndex);
    }

    private static int[] getAdaptiveAudioTracks(TrackGroup group, int[] formatSupport, boolean allowMixedMimeTypes) {
        int configurationCount;
        int selectedConfigurationTrackCount = 0;
        AudioConfigurationTuple selectedConfiguration = null;
        HashSet<AudioConfigurationTuple> seenConfigurationTuples = new HashSet<>();
        for (int i = 0; i < group.length; i++) {
            Format format = group.getFormat(i);
            AudioConfigurationTuple configuration = new AudioConfigurationTuple(format.channelCount, format.sampleRate, allowMixedMimeTypes ? null : format.sampleMimeType);
            if (seenConfigurationTuples.add(configuration) && (configurationCount = getAdaptiveAudioTrackCount(group, formatSupport, configuration)) > selectedConfigurationTrackCount) {
                selectedConfiguration = configuration;
                selectedConfigurationTrackCount = configurationCount;
            }
        }
        if (selectedConfigurationTrackCount > 1) {
            int[] adaptiveIndices = new int[selectedConfigurationTrackCount];
            int index = 0;
            for (int i2 = 0; i2 < group.length; i2++) {
                if (isSupportedAdaptiveAudioTrack(group.getFormat(i2), formatSupport[i2], (AudioConfigurationTuple) Assertions.checkNotNull(selectedConfiguration))) {
                    adaptiveIndices[index] = i2;
                    index++;
                }
            }
            return adaptiveIndices;
        }
        return NO_TRACKS;
    }

    private static int getAdaptiveAudioTrackCount(TrackGroup group, int[] formatSupport, AudioConfigurationTuple configuration) {
        int count = 0;
        for (int i = 0; i < group.length; i++) {
            if (isSupportedAdaptiveAudioTrack(group.getFormat(i), formatSupport[i], configuration)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isSupportedAdaptiveAudioTrack(Format format, int formatSupport, AudioConfigurationTuple configuration) {
        if (isSupported(formatSupport, false) && format.channelCount == configuration.channelCount && format.sampleRate == configuration.sampleRate) {
            return configuration.mimeType == null || TextUtils.equals(configuration.mimeType, format.sampleMimeType);
        }
        return false;
    }

    @Nullable
    protected TrackSelection selectTextTrack(TrackGroupArray groups, int[][] formatSupport, Parameters params) throws ExoPlaybackException {
        int trackScore;
        int trackScore2;
        TrackGroup selectedGroup = null;
        int selectedTrackIndex = 0;
        int selectedTrackScore = 0;
        for (int groupIndex = 0; groupIndex < groups.length; groupIndex++) {
            TrackGroup trackGroup = groups.get(groupIndex);
            int[] trackFormatSupport = formatSupport[groupIndex];
            for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
                if (isSupported(trackFormatSupport[trackIndex], params.exceedRendererCapabilitiesIfNecessary)) {
                    Format format = trackGroup.getFormat(trackIndex);
                    int maskedSelectionFlags = format.selectionFlags & (params.disabledTextTrackSelectionFlags ^ (-1));
                    boolean isDefault = (maskedSelectionFlags & 1) != 0;
                    boolean isForced = (maskedSelectionFlags & 2) != 0;
                    boolean preferredLanguageFound = formatHasLanguage(format, params.preferredTextLanguage);
                    if (preferredLanguageFound || (params.selectUndeterminedTextLanguage && formatHasNoLanguage(format))) {
                        if (isDefault) {
                            trackScore = 8;
                        } else if (!isForced) {
                            trackScore = 6;
                        } else {
                            trackScore = 4;
                        }
                        trackScore2 = trackScore + (preferredLanguageFound ? 1 : 0);
                    } else if (isDefault) {
                        trackScore2 = 3;
                    } else if (isForced) {
                        if (formatHasLanguage(format, params.preferredAudioLanguage)) {
                            trackScore2 = 2;
                        } else {
                            trackScore2 = 1;
                        }
                    }
                    if (isSupported(trackFormatSupport[trackIndex], false)) {
                        trackScore2 += 1000;
                    }
                    if (trackScore2 > selectedTrackScore) {
                        selectedGroup = trackGroup;
                        selectedTrackIndex = trackIndex;
                        selectedTrackScore = trackScore2;
                    }
                }
            }
        }
        if (selectedGroup == null) {
            return null;
        }
        return new FixedTrackSelection(selectedGroup, selectedTrackIndex);
    }

    @Nullable
    protected TrackSelection selectOtherTrack(int trackType, TrackGroupArray groups, int[][] formatSupport, Parameters params) throws ExoPlaybackException {
        TrackGroup selectedGroup = null;
        int selectedTrackIndex = 0;
        int selectedTrackScore = 0;
        for (int groupIndex = 0; groupIndex < groups.length; groupIndex++) {
            TrackGroup trackGroup = groups.get(groupIndex);
            int[] trackFormatSupport = formatSupport[groupIndex];
            for (int trackIndex = 0; trackIndex < trackGroup.length; trackIndex++) {
                if (isSupported(trackFormatSupport[trackIndex], params.exceedRendererCapabilitiesIfNecessary)) {
                    Format format = trackGroup.getFormat(trackIndex);
                    boolean isDefault = (format.selectionFlags & 1) != 0;
                    int trackScore = isDefault ? 2 : 1;
                    if (isSupported(trackFormatSupport[trackIndex], false)) {
                        trackScore += 1000;
                    }
                    if (trackScore > selectedTrackScore) {
                        selectedGroup = trackGroup;
                        selectedTrackIndex = trackIndex;
                        selectedTrackScore = trackScore;
                    }
                }
            }
        }
        if (selectedGroup == null) {
            return null;
        }
        return new FixedTrackSelection(selectedGroup, selectedTrackIndex);
    }

    private static void maybeConfigureRenderersForTunneling(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] renderererFormatSupports, RendererConfiguration[] rendererConfigurations, TrackSelection[] trackSelections, int tunnelingAudioSessionId) {
        boolean z = true;
        if (tunnelingAudioSessionId != 0) {
            int tunnelingAudioRendererIndex = -1;
            int tunnelingVideoRendererIndex = -1;
            boolean enableTunneling = true;
            int i = 0;
            while (true) {
                if (i >= mappedTrackInfo.getRendererCount()) {
                    break;
                }
                int rendererType = mappedTrackInfo.getRendererType(i);
                TrackSelection trackSelection = trackSelections[i];
                if ((rendererType == 1 || rendererType == 2) && trackSelection != null && rendererSupportsTunneling(renderererFormatSupports[i], mappedTrackInfo.getTrackGroups(i), trackSelection)) {
                    if (rendererType == 1) {
                        if (tunnelingAudioRendererIndex != -1) {
                            enableTunneling = false;
                            break;
                        }
                        tunnelingAudioRendererIndex = i;
                    } else if (tunnelingVideoRendererIndex != -1) {
                        enableTunneling = false;
                        break;
                    } else {
                        tunnelingVideoRendererIndex = i;
                    }
                }
                i++;
            }
            if (enableTunneling & ((tunnelingAudioRendererIndex == -1 || tunnelingVideoRendererIndex == -1) ? false : false)) {
                RendererConfiguration tunnelingRendererConfiguration = new RendererConfiguration(tunnelingAudioSessionId);
                rendererConfigurations[tunnelingAudioRendererIndex] = tunnelingRendererConfiguration;
                rendererConfigurations[tunnelingVideoRendererIndex] = tunnelingRendererConfiguration;
            }
        }
    }

    private static boolean rendererSupportsTunneling(int[][] formatSupports, TrackGroupArray trackGroups, TrackSelection selection) {
        if (selection == null) {
            return false;
        }
        int trackGroupIndex = trackGroups.indexOf(selection.getTrackGroup());
        for (int i = 0; i < selection.length(); i++) {
            int trackFormatSupport = formatSupports[trackGroupIndex][selection.getIndexInTrackGroup(i)];
            if ((trackFormatSupport & 32) != 32) {
                return false;
            }
        }
        return true;
    }

    private static int compareFormatValues(int first, int second) {
        if (first == -1) {
            return second == -1 ? 0 : -1;
        } else if (second == -1) {
            return 1;
        } else {
            return first - second;
        }
    }

    protected static boolean isSupported(int formatSupport, boolean allowExceedsCapabilities) {
        int maskedSupport = formatSupport & 7;
        return maskedSupport == 4 || (allowExceedsCapabilities && maskedSupport == 3);
    }

    protected static boolean formatHasNoLanguage(Format format) {
        return TextUtils.isEmpty(format.language) || formatHasLanguage(format, C.LANGUAGE_UNDETERMINED);
    }

    protected static boolean formatHasLanguage(Format format, @Nullable String language) {
        return language != null && TextUtils.equals(language, Util.normalizeLanguageCode(format.language));
    }

    private static List<Integer> getViewportFilteredTrackIndices(TrackGroup group, int viewportWidth, int viewportHeight, boolean orientationMayChange) {
        ArrayList<Integer> selectedTrackIndices = new ArrayList<>(group.length);
        for (int i = 0; i < group.length; i++) {
            selectedTrackIndices.add(Integer.valueOf(i));
        }
        if (viewportWidth != Integer.MAX_VALUE && viewportHeight != Integer.MAX_VALUE) {
            int maxVideoPixelsToRetain = Integer.MAX_VALUE;
            for (int i2 = 0; i2 < group.length; i2++) {
                Format format = group.getFormat(i2);
                if (format.width > 0 && format.height > 0) {
                    Point maxVideoSizeInViewport = getMaxVideoSizeInViewport(orientationMayChange, viewportWidth, viewportHeight, format.width, format.height);
                    int videoPixels = format.width * format.height;
                    if (format.width >= ((int) (maxVideoSizeInViewport.x * FRACTION_TO_CONSIDER_FULLSCREEN)) && format.height >= ((int) (maxVideoSizeInViewport.y * FRACTION_TO_CONSIDER_FULLSCREEN)) && videoPixels < maxVideoPixelsToRetain) {
                        maxVideoPixelsToRetain = videoPixels;
                    }
                }
            }
            if (maxVideoPixelsToRetain != Integer.MAX_VALUE) {
                for (int i3 = selectedTrackIndices.size() - 1; i3 >= 0; i3--) {
                    int pixelCount = group.getFormat(selectedTrackIndices.get(i3).intValue()).getPixelCount();
                    if (pixelCount == -1 || pixelCount > maxVideoPixelsToRetain) {
                        selectedTrackIndices.remove(i3);
                    }
                }
            }
        }
        return selectedTrackIndices;
    }

    private static Point getMaxVideoSizeInViewport(boolean orientationMayChange, int viewportWidth, int viewportHeight, int videoWidth, int videoHeight) {
        if (orientationMayChange) {
            if ((videoWidth > videoHeight) != (viewportWidth > viewportHeight)) {
                viewportWidth = viewportHeight;
                viewportHeight = viewportWidth;
            }
        }
        if (videoWidth * viewportHeight >= videoHeight * viewportWidth) {
            return new Point(viewportWidth, Util.ceilDivide(viewportWidth * videoHeight, videoWidth));
        }
        return new Point(Util.ceilDivide(viewportHeight * videoWidth, videoHeight), viewportHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class AudioTrackScore implements Comparable<AudioTrackScore> {
        private final int bitrate;
        private final int channelCount;
        private final int defaultSelectionFlagScore;
        private final int matchLanguageScore;
        private final Parameters parameters;
        private final int sampleRate;
        private final int withinRendererCapabilitiesScore;

        public AudioTrackScore(Format format, Parameters parameters, int formatSupport) {
            this.parameters = parameters;
            this.withinRendererCapabilitiesScore = DefaultTrackSelector.isSupported(formatSupport, false) ? 1 : 0;
            this.matchLanguageScore = DefaultTrackSelector.formatHasLanguage(format, parameters.preferredAudioLanguage) ? 1 : 0;
            this.defaultSelectionFlagScore = (format.selectionFlags & 1) == 0 ? 0 : 1;
            this.channelCount = format.channelCount;
            this.sampleRate = format.sampleRate;
            this.bitrate = format.bitrate;
        }

        @Override // java.lang.Comparable
        public int compareTo(AudioTrackScore other) {
            if (this.withinRendererCapabilitiesScore != other.withinRendererCapabilitiesScore) {
                return DefaultTrackSelector.compareInts(this.withinRendererCapabilitiesScore, other.withinRendererCapabilitiesScore);
            }
            if (this.matchLanguageScore != other.matchLanguageScore) {
                return DefaultTrackSelector.compareInts(this.matchLanguageScore, other.matchLanguageScore);
            }
            if (this.defaultSelectionFlagScore != other.defaultSelectionFlagScore) {
                return DefaultTrackSelector.compareInts(this.defaultSelectionFlagScore, other.defaultSelectionFlagScore);
            }
            if (this.parameters.forceLowestBitrate) {
                return DefaultTrackSelector.compareInts(other.bitrate, this.bitrate);
            }
            int resultSign = this.withinRendererCapabilitiesScore != 1 ? -1 : 1;
            if (this.channelCount != other.channelCount) {
                return DefaultTrackSelector.compareInts(this.channelCount, other.channelCount) * resultSign;
            }
            return this.sampleRate != other.sampleRate ? DefaultTrackSelector.compareInts(this.sampleRate, other.sampleRate) * resultSign : DefaultTrackSelector.compareInts(this.bitrate, other.bitrate) * resultSign;
        }

        public boolean equals(@Nullable Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AudioTrackScore that = (AudioTrackScore) o;
            return this.withinRendererCapabilitiesScore == that.withinRendererCapabilitiesScore && this.matchLanguageScore == that.matchLanguageScore && this.defaultSelectionFlagScore == that.defaultSelectionFlagScore && this.channelCount == that.channelCount && this.sampleRate == that.sampleRate && this.bitrate == that.bitrate;
        }

        public int hashCode() {
            int result = this.withinRendererCapabilitiesScore;
            return (((((((((result * 31) + this.matchLanguageScore) * 31) + this.defaultSelectionFlagScore) * 31) + this.channelCount) * 31) + this.sampleRate) * 31) + this.bitrate;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareInts(int first, int second) {
        if (first > second) {
            return 1;
        }
        return second > first ? -1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class AudioConfigurationTuple {
        public final int channelCount;
        @Nullable
        public final String mimeType;
        public final int sampleRate;

        public AudioConfigurationTuple(int channelCount, int sampleRate, @Nullable String mimeType) {
            this.channelCount = channelCount;
            this.sampleRate = sampleRate;
            this.mimeType = mimeType;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AudioConfigurationTuple other = (AudioConfigurationTuple) obj;
            return this.channelCount == other.channelCount && this.sampleRate == other.sampleRate && TextUtils.equals(this.mimeType, other.mimeType);
        }

        public int hashCode() {
            int result = this.channelCount;
            return (((result * 31) + this.sampleRate) * 31) + (this.mimeType != null ? this.mimeType.hashCode() : 0);
        }
    }
}
