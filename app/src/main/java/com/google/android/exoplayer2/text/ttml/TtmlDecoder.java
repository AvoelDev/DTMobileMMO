package com.google.android.exoplayer2.text.ttml;

import android.text.Layout;
import android.util.Log;
import com.facebook.appevents.UserDataStore;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ColorParser;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public final class TtmlDecoder extends SimpleSubtitleDecoder {
    private static final String ATTR_BEGIN = "begin";
    private static final String ATTR_DURATION = "dur";
    private static final String ATTR_END = "end";
    private static final String ATTR_REGION = "region";
    private static final String ATTR_STYLE = "style";
    private static final int DEFAULT_FRAME_RATE = 30;
    private static final String TAG = "TtmlDecoder";
    private static final String TTP = "http://www.w3.org/ns/ttml#parameter";
    private final XmlPullParserFactory xmlParserFactory;
    private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    private static final Pattern FONT_SIZE = Pattern.compile("^(([0-9]*.)?[0-9]+)(px|em|%)$");
    private static final Pattern PERCENTAGE_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)% (\\d+\\.?\\d*?)%$");
    private static final Pattern CELL_RESOLUTION = Pattern.compile("^(\\d+) (\\d+)$");
    private static final FrameAndTickRate DEFAULT_FRAME_AND_TICK_RATE = new FrameAndTickRate(30.0f, 1, 1);
    private static final CellResolution DEFAULT_CELL_RESOLUTION = new CellResolution(32, 15);

    public TtmlDecoder() {
        super(TAG);
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
            this.xmlParserFactory.setNamespaceAware(true);
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    public TtmlSubtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        try {
            XmlPullParser xmlParser = this.xmlParserFactory.newPullParser();
            Map<String, TtmlStyle> globalStyles = new HashMap<>();
            Map<String, TtmlRegion> regionMap = new HashMap<>();
            regionMap.put("", new TtmlRegion(null));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes, 0, length);
            xmlParser.setInput(inputStream, null);
            TtmlSubtitle ttmlSubtitle = null;
            ArrayDeque<TtmlNode> nodeStack = new ArrayDeque<>();
            int unsupportedNodeDepth = 0;
            FrameAndTickRate frameAndTickRate = DEFAULT_FRAME_AND_TICK_RATE;
            CellResolution cellResolution = DEFAULT_CELL_RESOLUTION;
            for (int eventType = xmlParser.getEventType(); eventType != 1; eventType = xmlParser.getEventType()) {
                TtmlNode parent = nodeStack.peek();
                if (unsupportedNodeDepth == 0) {
                    String name = xmlParser.getName();
                    if (eventType == 2) {
                        if (TtmlNode.TAG_TT.equals(name)) {
                            frameAndTickRate = parseFrameAndTickRates(xmlParser);
                            cellResolution = parseCellResolution(xmlParser, DEFAULT_CELL_RESOLUTION);
                        }
                        if (!isSupportedTag(name)) {
                            Log.i(TAG, "Ignoring unsupported tag: " + xmlParser.getName());
                            unsupportedNodeDepth++;
                        } else if (TtmlNode.TAG_HEAD.equals(name)) {
                            parseHeader(xmlParser, globalStyles, regionMap, cellResolution);
                        } else {
                            try {
                                TtmlNode node = parseNode(xmlParser, parent, regionMap, frameAndTickRate);
                                nodeStack.push(node);
                                if (parent != null) {
                                    parent.addChild(node);
                                }
                            } catch (SubtitleDecoderException e) {
                                Log.w(TAG, "Suppressing parser error", e);
                                unsupportedNodeDepth++;
                            }
                        }
                    } else if (eventType == 4) {
                        parent.addChild(TtmlNode.buildTextNode(xmlParser.getText()));
                    } else if (eventType == 3) {
                        if (xmlParser.getName().equals(TtmlNode.TAG_TT)) {
                            ttmlSubtitle = new TtmlSubtitle(nodeStack.peek(), globalStyles, regionMap);
                        }
                        nodeStack.pop();
                    }
                } else if (eventType == 2) {
                    unsupportedNodeDepth++;
                } else if (eventType == 3) {
                    unsupportedNodeDepth--;
                }
                xmlParser.next();
            }
            return ttmlSubtitle;
        } catch (IOException e2) {
            throw new IllegalStateException("Unexpected error when reading input.", e2);
        } catch (XmlPullParserException xppe) {
            throw new SubtitleDecoderException("Unable to decode source", xppe);
        }
    }

    private FrameAndTickRate parseFrameAndTickRates(XmlPullParser xmlParser) throws SubtitleDecoderException {
        int frameRate = 30;
        String frameRateString = xmlParser.getAttributeValue(TTP, "frameRate");
        if (frameRateString != null) {
            frameRate = Integer.parseInt(frameRateString);
        }
        float frameRateMultiplier = 1.0f;
        String frameRateMultiplierString = xmlParser.getAttributeValue(TTP, "frameRateMultiplier");
        if (frameRateMultiplierString != null) {
            String[] parts = Util.split(frameRateMultiplierString, " ");
            if (parts.length != 2) {
                throw new SubtitleDecoderException("frameRateMultiplier doesn't have 2 parts");
            }
            float numerator = Integer.parseInt(parts[0]);
            float denominator = Integer.parseInt(parts[1]);
            frameRateMultiplier = numerator / denominator;
        }
        int subFrameRate = DEFAULT_FRAME_AND_TICK_RATE.subFrameRate;
        String subFrameRateString = xmlParser.getAttributeValue(TTP, "subFrameRate");
        if (subFrameRateString != null) {
            subFrameRate = Integer.parseInt(subFrameRateString);
        }
        int tickRate = DEFAULT_FRAME_AND_TICK_RATE.tickRate;
        String tickRateString = xmlParser.getAttributeValue(TTP, "tickRate");
        if (tickRateString != null) {
            tickRate = Integer.parseInt(tickRateString);
        }
        return new FrameAndTickRate(frameRate * frameRateMultiplier, subFrameRate, tickRate);
    }

    private CellResolution parseCellResolution(XmlPullParser xmlParser, CellResolution defaultValue) throws SubtitleDecoderException {
        String cellResolution = xmlParser.getAttributeValue(TTP, "cellResolution");
        if (cellResolution != null) {
            Matcher cellResolutionMatcher = CELL_RESOLUTION.matcher(cellResolution);
            if (!cellResolutionMatcher.matches()) {
                Log.w(TAG, "Ignoring malformed cell resolution: " + cellResolution);
                return defaultValue;
            }
            try {
                int columns = Integer.parseInt(cellResolutionMatcher.group(1));
                int rows = Integer.parseInt(cellResolutionMatcher.group(2));
                if (columns == 0 || rows == 0) {
                    throw new SubtitleDecoderException("Invalid cell resolution " + columns + " " + rows);
                }
                return new CellResolution(columns, rows);
            } catch (NumberFormatException e) {
                Log.w(TAG, "Ignoring malformed cell resolution: " + cellResolution);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private Map<String, TtmlStyle> parseHeader(XmlPullParser xmlParser, Map<String, TtmlStyle> globalStyles, Map<String, TtmlRegion> globalRegions, CellResolution cellResolution) throws IOException, XmlPullParserException {
        TtmlRegion ttmlRegion;
        String[] parseStyleIds;
        do {
            xmlParser.next();
            if (XmlPullParserUtil.isStartTag(xmlParser, "style")) {
                String parentStyleId = XmlPullParserUtil.getAttributeValue(xmlParser, "style");
                TtmlStyle style = parseStyleAttributes(xmlParser, new TtmlStyle());
                if (parentStyleId != null) {
                    for (String id : parseStyleIds(parentStyleId)) {
                        style.chain(globalStyles.get(id));
                    }
                }
                if (style.getId() != null) {
                    globalStyles.put(style.getId(), style);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlParser, "region") && (ttmlRegion = parseRegionAttributes(xmlParser, cellResolution)) != null) {
                globalRegions.put(ttmlRegion.id, ttmlRegion);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlParser, TtmlNode.TAG_HEAD));
        return globalStyles;
    }

    private TtmlRegion parseRegionAttributes(XmlPullParser xmlParser, CellResolution cellResolution) {
        String regionId = XmlPullParserUtil.getAttributeValue(xmlParser, "id");
        if (regionId == null) {
            return null;
        }
        String regionOrigin = XmlPullParserUtil.getAttributeValue(xmlParser, "origin");
        if (regionOrigin != null) {
            Matcher originMatcher = PERCENTAGE_COORDINATES.matcher(regionOrigin);
            if (originMatcher.matches()) {
                try {
                    float position = Float.parseFloat(originMatcher.group(1)) / 100.0f;
                    float line = Float.parseFloat(originMatcher.group(2)) / 100.0f;
                    String regionExtent = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_TTS_EXTENT);
                    if (regionExtent != null) {
                        Matcher extentMatcher = PERCENTAGE_COORDINATES.matcher(regionExtent);
                        if (extentMatcher.matches()) {
                            try {
                                float width = Float.parseFloat(extentMatcher.group(1)) / 100.0f;
                                float height = Float.parseFloat(extentMatcher.group(2)) / 100.0f;
                                int lineAnchor = 0;
                                String displayAlign = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_TTS_DISPLAY_ALIGN);
                                if (displayAlign != null) {
                                    String lowerInvariant = Util.toLowerInvariant(displayAlign);
                                    char c = 65535;
                                    switch (lowerInvariant.hashCode()) {
                                        case -1364013995:
                                            if (lowerInvariant.equals(TtmlNode.CENTER)) {
                                                c = 0;
                                                break;
                                            }
                                            break;
                                        case 92734940:
                                            if (lowerInvariant.equals("after")) {
                                                c = 1;
                                                break;
                                            }
                                            break;
                                    }
                                    switch (c) {
                                        case 0:
                                            lineAnchor = 1;
                                            line += height / 2.0f;
                                            break;
                                        case 1:
                                            lineAnchor = 2;
                                            line += height;
                                            break;
                                    }
                                }
                                float regionTextHeight = 1.0f / cellResolution.rows;
                                return new TtmlRegion(regionId, position, line, 0, lineAnchor, width, 1, regionTextHeight);
                            } catch (NumberFormatException e) {
                                Log.w(TAG, "Ignoring region with malformed extent: " + regionOrigin);
                                return null;
                            }
                        }
                        Log.w(TAG, "Ignoring region with unsupported extent: " + regionOrigin);
                        return null;
                    }
                    Log.w(TAG, "Ignoring region without an extent");
                    return null;
                } catch (NumberFormatException e2) {
                    Log.w(TAG, "Ignoring region with malformed origin: " + regionOrigin);
                    return null;
                }
            }
            Log.w(TAG, "Ignoring region with unsupported origin: " + regionOrigin);
            return null;
        }
        Log.w(TAG, "Ignoring region without an origin");
        return null;
    }

    private String[] parseStyleIds(String parentStyleIds) {
        String parentStyleIds2 = parentStyleIds.trim();
        return parentStyleIds2.isEmpty() ? new String[0] : Util.split(parentStyleIds2, "\\s+");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private TtmlStyle parseStyleAttributes(XmlPullParser parser, TtmlStyle style) {
        char c;
        boolean z;
        char c2;
        int attributeCount = parser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeValue = parser.getAttributeValue(i);
            String attributeName = parser.getAttributeName(i);
            switch (attributeName.hashCode()) {
                case -1550943582:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_FONT_STYLE)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -1224696685:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_FONT_FAMILY)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1065511464:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_TEXT_ALIGN)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -879295043:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_TEXT_DECORATION)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -734428249:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_FONT_WEIGHT)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 3355:
                    if (attributeName.equals("id")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 94842723:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_COLOR)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 365601008:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_FONT_SIZE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1287124693:
                    if (attributeName.equals(TtmlNode.ATTR_TTS_BACKGROUND_COLOR)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if ("style".equals(parser.getName())) {
                        style = createIfNull(style).setId(attributeValue);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    style = createIfNull(style);
                    try {
                        style.setBackgroundColor(ColorParser.parseTtmlColor(attributeValue));
                        break;
                    } catch (IllegalArgumentException e) {
                        Log.w(TAG, "Failed parsing background value: " + attributeValue);
                        break;
                    }
                case 2:
                    style = createIfNull(style);
                    try {
                        style.setFontColor(ColorParser.parseTtmlColor(attributeValue));
                        break;
                    } catch (IllegalArgumentException e2) {
                        Log.w(TAG, "Failed parsing color value: " + attributeValue);
                        break;
                    }
                case 3:
                    style = createIfNull(style).setFontFamily(attributeValue);
                    break;
                case 4:
                    try {
                        style = createIfNull(style);
                        parseFontSize(attributeValue, style);
                        break;
                    } catch (SubtitleDecoderException e3) {
                        Log.w(TAG, "Failed parsing fontSize value: " + attributeValue);
                        break;
                    }
                case 5:
                    style = createIfNull(style).setBold(TtmlNode.BOLD.equalsIgnoreCase(attributeValue));
                    break;
                case 6:
                    style = createIfNull(style).setItalic(TtmlNode.ITALIC.equalsIgnoreCase(attributeValue));
                    break;
                case 7:
                    String lowerInvariant = Util.toLowerInvariant(attributeValue);
                    switch (lowerInvariant.hashCode()) {
                        case -1364013995:
                            if (lowerInvariant.equals(TtmlNode.CENTER)) {
                                c2 = 4;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 100571:
                            if (lowerInvariant.equals("end")) {
                                c2 = 3;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 3317767:
                            if (lowerInvariant.equals("left")) {
                                c2 = 0;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 108511772:
                            if (lowerInvariant.equals(TtmlNode.RIGHT)) {
                                c2 = 2;
                                break;
                            }
                            c2 = 65535;
                            break;
                        case 109757538:
                            if (lowerInvariant.equals("start")) {
                                c2 = 1;
                                break;
                            }
                            c2 = 65535;
                            break;
                        default:
                            c2 = 65535;
                            break;
                    }
                    switch (c2) {
                        case 0:
                            style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_NORMAL);
                            continue;
                        case 1:
                            style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_NORMAL);
                            continue;
                        case 2:
                            style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_OPPOSITE);
                            continue;
                        case 3:
                            style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_OPPOSITE);
                            continue;
                        case 4:
                            style = createIfNull(style).setTextAlign(Layout.Alignment.ALIGN_CENTER);
                            continue;
                    }
                case '\b':
                    String lowerInvariant2 = Util.toLowerInvariant(attributeValue);
                    switch (lowerInvariant2.hashCode()) {
                        case -1461280213:
                            if (lowerInvariant2.equals(TtmlNode.NO_UNDERLINE)) {
                                z = true;
                                break;
                            }
                            z = true;
                            break;
                        case -1026963764:
                            if (lowerInvariant2.equals(TtmlNode.UNDERLINE)) {
                                z = true;
                                break;
                            }
                            z = true;
                            break;
                        case 913457136:
                            if (lowerInvariant2.equals(TtmlNode.NO_LINETHROUGH)) {
                                z = true;
                                break;
                            }
                            z = true;
                            break;
                        case 1679736913:
                            if (lowerInvariant2.equals(TtmlNode.LINETHROUGH)) {
                                z = false;
                                break;
                            }
                            z = true;
                            break;
                        default:
                            z = true;
                            break;
                    }
                    switch (z) {
                        case false:
                            style = createIfNull(style).setLinethrough(true);
                            continue;
                        case true:
                            style = createIfNull(style).setLinethrough(false);
                            continue;
                        case true:
                            style = createIfNull(style).setUnderline(true);
                            continue;
                        case true:
                            style = createIfNull(style).setUnderline(false);
                            continue;
                    }
            }
        }
        return style;
    }

    private TtmlStyle createIfNull(TtmlStyle style) {
        return style == null ? new TtmlStyle() : style;
    }

    private TtmlNode parseNode(XmlPullParser parser, TtmlNode parent, Map<String, TtmlRegion> regionMap, FrameAndTickRate frameAndTickRate) throws SubtitleDecoderException {
        long duration = C.TIME_UNSET;
        long startTime = C.TIME_UNSET;
        long endTime = C.TIME_UNSET;
        String regionId = "";
        String[] styleIds = null;
        int attributeCount = parser.getAttributeCount();
        TtmlStyle style = parseStyleAttributes(parser, null);
        for (int i = 0; i < attributeCount; i++) {
            String attr = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            char c = 65535;
            switch (attr.hashCode()) {
                case -934795532:
                    if (attr.equals("region")) {
                        c = 4;
                        break;
                    }
                    break;
                case 99841:
                    if (attr.equals(ATTR_DURATION)) {
                        c = 2;
                        break;
                    }
                    break;
                case 100571:
                    if (attr.equals("end")) {
                        c = 1;
                        break;
                    }
                    break;
                case 93616297:
                    if (attr.equals(ATTR_BEGIN)) {
                        c = 0;
                        break;
                    }
                    break;
                case 109780401:
                    if (attr.equals("style")) {
                        c = 3;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    startTime = parseTimeExpression(value, frameAndTickRate);
                    break;
                case 1:
                    endTime = parseTimeExpression(value, frameAndTickRate);
                    break;
                case 2:
                    duration = parseTimeExpression(value, frameAndTickRate);
                    break;
                case 3:
                    String[] ids = parseStyleIds(value);
                    if (ids.length > 0) {
                        styleIds = ids;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (regionMap.containsKey(value)) {
                        regionId = value;
                        break;
                    } else {
                        break;
                    }
            }
        }
        if (parent != null && parent.startTimeUs != C.TIME_UNSET) {
            if (startTime != C.TIME_UNSET) {
                startTime += parent.startTimeUs;
            }
            if (endTime != C.TIME_UNSET) {
                endTime += parent.startTimeUs;
            }
        }
        if (endTime == C.TIME_UNSET) {
            if (duration != C.TIME_UNSET) {
                endTime = startTime + duration;
            } else if (parent != null && parent.endTimeUs != C.TIME_UNSET) {
                endTime = parent.endTimeUs;
            }
        }
        return TtmlNode.buildNode(parser.getName(), startTime, endTime, style, styleIds, regionId);
    }

    private static boolean isSupportedTag(String tag) {
        return tag.equals(TtmlNode.TAG_TT) || tag.equals(TtmlNode.TAG_HEAD) || tag.equals(TtmlNode.TAG_BODY) || tag.equals(TtmlNode.TAG_DIV) || tag.equals(TtmlNode.TAG_P) || tag.equals(TtmlNode.TAG_SPAN) || tag.equals(TtmlNode.TAG_BR) || tag.equals("style") || tag.equals(TtmlNode.TAG_STYLING) || tag.equals(TtmlNode.TAG_LAYOUT) || tag.equals("region") || tag.equals(TtmlNode.TAG_METADATA) || tag.equals(TtmlNode.TAG_SMPTE_IMAGE) || tag.equals(TtmlNode.TAG_SMPTE_DATA) || tag.equals(TtmlNode.TAG_SMPTE_INFORMATION);
    }

    private static void parseFontSize(String expression, TtmlStyle out) throws SubtitleDecoderException {
        Matcher matcher;
        String[] expressions = Util.split(expression, "\\s+");
        if (expressions.length == 1) {
            matcher = FONT_SIZE.matcher(expression);
        } else if (expressions.length == 2) {
            matcher = FONT_SIZE.matcher(expressions[1]);
            Log.w(TAG, "Multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
        } else {
            throw new SubtitleDecoderException("Invalid number of entries for fontSize: " + expressions.length + ".");
        }
        if (matcher.matches()) {
            String unit = matcher.group(3);
            char c = 65535;
            switch (unit.hashCode()) {
                case 37:
                    if (unit.equals("%")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3240:
                    if (unit.equals(UserDataStore.EMAIL)) {
                        c = 1;
                        break;
                    }
                    break;
                case 3592:
                    if (unit.equals("px")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    out.setFontSizeUnit(1);
                    break;
                case 1:
                    out.setFontSizeUnit(2);
                    break;
                case 2:
                    out.setFontSizeUnit(3);
                    break;
                default:
                    throw new SubtitleDecoderException("Invalid unit for fontSize: '" + unit + "'.");
            }
            out.setFontSize(Float.valueOf(matcher.group(1)).floatValue());
            return;
        }
        throw new SubtitleDecoderException("Invalid expression for fontSize: '" + expression + "'.");
    }

    private static long parseTimeExpression(String time, FrameAndTickRate frameAndTickRate) throws SubtitleDecoderException {
        Matcher matcher = CLOCK_TIME.matcher(time);
        if (matcher.matches()) {
            String hours = matcher.group(1);
            double durationSeconds = Long.parseLong(hours) * 3600;
            String minutes = matcher.group(2);
            String seconds = matcher.group(3);
            double durationSeconds2 = durationSeconds + (Long.parseLong(minutes) * 60) + Long.parseLong(seconds);
            String fraction = matcher.group(4);
            double durationSeconds3 = durationSeconds2 + (fraction != null ? Double.parseDouble(fraction) : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
            String frames = matcher.group(5);
            double durationSeconds4 = durationSeconds3 + (frames != null ? ((float) Long.parseLong(frames)) / frameAndTickRate.effectiveFrameRate : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
            String subframes = matcher.group(6);
            return (long) (1000000.0d * (durationSeconds4 + (subframes != null ? (Long.parseLong(subframes) / frameAndTickRate.subFrameRate) / frameAndTickRate.effectiveFrameRate : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)));
        }
        Matcher matcher2 = OFFSET_TIME.matcher(time);
        if (matcher2.matches()) {
            String timeValue = matcher2.group(1);
            double offsetSeconds = Double.parseDouble(timeValue);
            String unit = matcher2.group(2);
            char c = 65535;
            switch (unit.hashCode()) {
                case 102:
                    if (unit.equals("f")) {
                        c = 4;
                        break;
                    }
                    break;
                case 104:
                    if (unit.equals("h")) {
                        c = 0;
                        break;
                    }
                    break;
                case 109:
                    if (unit.equals("m")) {
                        c = 1;
                        break;
                    }
                    break;
                case 115:
                    if (unit.equals("s")) {
                        c = 2;
                        break;
                    }
                    break;
                case 116:
                    if (unit.equals("t")) {
                        c = 5;
                        break;
                    }
                    break;
                case 3494:
                    if (unit.equals("ms")) {
                        c = 3;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    offsetSeconds *= 3600.0d;
                    break;
                case 1:
                    offsetSeconds *= 60.0d;
                    break;
                case 3:
                    offsetSeconds /= 1000.0d;
                    break;
                case 4:
                    offsetSeconds /= frameAndTickRate.effectiveFrameRate;
                    break;
                case 5:
                    offsetSeconds /= frameAndTickRate.tickRate;
                    break;
            }
            return (long) (1000000.0d * offsetSeconds);
        }
        throw new SubtitleDecoderException("Malformed time expression: " + time);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class FrameAndTickRate {
        final float effectiveFrameRate;
        final int subFrameRate;
        final int tickRate;

        FrameAndTickRate(float effectiveFrameRate, int subFrameRate, int tickRate) {
            this.effectiveFrameRate = effectiveFrameRate;
            this.subFrameRate = subFrameRate;
            this.tickRate = tickRate;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class CellResolution {
        final int columns;
        final int rows;

        CellResolution(int columns, int rows) {
            this.columns = columns;
            this.rows = rows;
        }
    }
}
