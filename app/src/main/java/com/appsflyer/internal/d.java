package com.appsflyer.internal;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Process;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.Gravity;
import android.webkit.URLUtil;
import com.appsflyer.AFLogger;
import com.ssjj.fnsdk.core.TLog;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.text.Typography;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class d {
    private static char AFInAppEventParameterName = 37945;
    private static char AFInAppEventType = 2566;
    private static char AFKeystoreWrapper = 53514;
    private static int AFVersionDeclaration = 0;
    private static int getLevel = 1;
    private static char valueOf = 4568;
    private static long values = 123364835749994368L;

    d() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String values(Context context, long j) {
        String AFInAppEventParameterName2;
        String intern;
        String AFInAppEventParameterName3;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        sb2.append((valueOf(AFInAppEventParameterName("閺ﱤ⭾\ud861툗룹㮄\udec8镣뛫煄깅\ue9f4猽嵵䑱柖㼜\uf675鉀煄깅\ue9f4猽嵵䑱柖㼜\uf262∩핁\u17ed䝟燎卭瑕").intern()) ? values("師䆚") : AFInAppEventParameterName("큮\ud8fc")).intern());
        StringBuilder sb4 = new StringBuilder();
        String packageName = context.getPackageName();
        String AFKeystoreWrapper2 = AFKeystoreWrapper(packageName);
        sb2.append(values("師䆚").intern());
        sb4.append(AFKeystoreWrapper2);
        if (values(context) == null) {
            sb2.append(AFInAppEventParameterName("큮\ud8fc").intern());
            sb4.append(packageName);
        } else {
            sb2.append(values("師䆚").intern());
            sb4.append(packageName);
            int i = AFVersionDeclaration + 111;
            getLevel = i % 128;
            int i2 = i % 2;
        }
        String valueOf2 = valueOf(context);
        if (valueOf2 == null) {
            sb2.append(AFInAppEventParameterName("큮\ud8fc").intern());
            sb4.append(packageName);
        } else {
            sb2.append(values("師䆚").intern());
            sb4.append(valueOf2);
        }
        sb4.append(values(context, packageName));
        sb.append(sb4.toString());
        try {
            sb.append(new SimpleDateFormat(values("ދᡲჯक़Ǖ㨚㊏⬀⏵將咊䴦䕌緇癷滈权鿈頜").intern(), Locale.US).format(new Date(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime)));
            sb.append(j);
            if (!(!valueOf(AFInAppEventParameterName("洰⊭⭾\ud861툗룹㮄\udec8\uf4b9թ\uef45㧀\uf21d䕻錰䇷\udb0d\ue1aa\ue2d6噗ᬤ㸬㏡⅀\udb0d\ue1aa").intern()))) {
                int i3 = getLevel + 33;
                AFVersionDeclaration = i3 % 128;
                int i4 = i3 % 2;
                AFInAppEventParameterName2 = values("師䆚");
            } else {
                AFInAppEventParameterName2 = AFInAppEventParameterName("큮\ud8fc");
            }
            sb3.append(AFInAppEventParameterName2.intern());
            if (valueOf(values("룷꜖渀㔁ﰮ茼䨣ᄥ\ud816齇♇\ued6f둰筼ɣ즋邕埉ẍꖰ沢㎰𥉉臃").intern())) {
                int i5 = AFVersionDeclaration + 57;
                getLevel = i5 % 128;
                if ((i5 % 2 == 0 ? '0' : (char) 0) != 0) {
                    intern = values("師䆚").intern();
                    Object obj = null;
                    super.hashCode();
                } else {
                    intern = values("師䆚").intern();
                }
                int i6 = getLevel + 21;
                AFVersionDeclaration = i6 % 128;
                int i7 = i6 % 2;
            } else {
                intern = AFInAppEventParameterName("큮\ud8fc").intern();
            }
            sb3.append(intern);
            if (valueOf(AFInAppEventParameterName("\ude82\ue6a8⭾\ud861툗룹㮄\udec8\uf4b9թ\uef45㧀⋽䜪嗚\uee4fᵧ߂왴֙\ude9b죷").intern())) {
                int i8 = getLevel + 77;
                AFVersionDeclaration = i8 % 128;
                int i9 = i8 % 2;
                AFInAppEventParameterName3 = values("師䆚");
            } else {
                AFInAppEventParameterName3 = AFInAppEventParameterName("큮\ud8fc");
            }
            sb3.append(AFInAppEventParameterName3.intern());
            sb3.append((valueOf(values("㧥☄氤뉋\uf866㺖䒷誧킆ᛣ崗挣ꤒ\uef74㕴箂").intern()) ? values("師䆚") : AFInAppEventParameterName("큮\ud8fc")).intern());
            String AFInAppEventType2 = af.AFInAppEventType(af.values(sb.toString()));
            String obj2 = sb2.toString();
            StringBuilder sb5 = new StringBuilder(AFInAppEventType2);
            sb5.setCharAt(17, Integer.toString(Integer.parseInt(obj2, 2), 16).charAt(0));
            String obj3 = sb5.toString();
            String obj4 = sb3.toString();
            StringBuilder sb6 = new StringBuilder(obj3);
            sb6.setCharAt(27, Integer.toString(Integer.parseInt(obj4, 2), 16).charAt(0));
            return AFKeystoreWrapper(sb6.toString(), Long.valueOf(j));
        } catch (PackageManager.NameNotFoundException unused) {
            return values("嗧䨅됯ṗ䡹뉢\u1c8f䛡낍᪦䓢꽘ᥧ䍚굀ᜰ䇃ꯣᗘ羑ꧩᑇ縿꡵መ籫ꛏწ窢꒙ຢ祈ꍑ").intern();
        }
    }

    private static String AFKeystoreWrapper(String str, Long l) {
        if (str != null && l != null) {
            int i = getLevel + 49;
            AFVersionDeclaration = i % 128;
            int i2 = i % 2;
            if (str.length() == 32) {
                StringBuilder sb = new StringBuilder(str);
                String obj = l.toString();
                long j = 0;
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    if (i3 >= obj.length()) {
                        break;
                    }
                    int i5 = getLevel + 85;
                    AFVersionDeclaration = i5 % 128;
                    if ((i5 % 2 != 0 ? '4' : '\r') != '4') {
                        i4 += Character.getNumericValue(obj.charAt(i3));
                        i3++;
                    } else {
                        i4 >>= Character.getNumericValue(obj.charAt(i3));
                        i3 += 49;
                    }
                }
                String hexString = Integer.toHexString(i4);
                sb.replace(7, hexString.length() + 7, hexString);
                int i6 = 0;
                while (true) {
                    if (i6 >= sb.length()) {
                        break;
                    }
                    j += Character.getNumericValue(sb.charAt(i6));
                    i6++;
                }
                while (true) {
                    if ((j > 100 ? (char) 28 : (char) 24) == 24) {
                        break;
                    }
                    int i7 = getLevel + 13;
                    AFVersionDeclaration = i7 % 128;
                    j = i7 % 2 != 0 ? j & 100 : j % 100;
                    int i8 = AFVersionDeclaration + 3;
                    getLevel = i8 % 128;
                    int i9 = i8 % 2;
                }
                sb.insert(23, (int) j);
                if (j < 10) {
                    sb.insert(23, AFInAppEventParameterName("큮\ud8fc").intern());
                }
                return sb.toString();
            }
        }
        return values("嗧䨅됯ṗ䡹뉢\u1c8f䛡낍᪦䓢꽘ᥧ䍚굀ᜰ䇃ꯣᗘ羑ꧩᑇ縿꡵መ籫ꛏწ窢꒙ຢ祈ꍑ").intern();
    }

    private static boolean valueOf(String str) {
        int i = AFVersionDeclaration + 51;
        getLevel = i % 128;
        int i2 = i % 2;
        try {
            Class.forName(str);
            int i3 = AFVersionDeclaration + 79;
            getLevel = i3 % 128;
            int i4 = i3 % 2;
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private static String AFKeystoreWrapper(String str) {
        int i = 1;
        if (!str.contains(values("\udb3b쒕").intern())) {
            int i2 = AFVersionDeclaration + 97;
            getLevel = i2 % 128;
            if (!(i2 % 2 == 0)) {
                return str;
            }
            Object obj = null;
            super.hashCode();
            return str;
        }
        String[] split = str.split(AFInAppEventParameterName("귫౮ቮ᪺").intern());
        int length = split.length;
        StringBuilder sb = new StringBuilder();
        int i3 = length - 1;
        sb.append(split[i3]);
        sb.append(values("\udb3b쒕").intern());
        int i4 = AFVersionDeclaration + 79;
        getLevel = i4 % 128;
        int i5 = i4 % 2;
        while (true) {
            if ((i < i3 ? (char) 31 : '\f') == '\f') {
                sb.append(split[0]);
                return sb.toString();
            }
            int i6 = getLevel + 107;
            AFVersionDeclaration = i6 % 128;
            int i7 = i6 % 2;
            sb.append(split[i]);
            sb.append(values("\udb3b쒕").intern());
            i++;
        }
    }

    private static String values(Context context) {
        if ((System.getProperties().containsKey(values("\ue219ﷳ\udbd3립鞅痓卿ㅀས\ued15쬗꣣蛈撬䊬").intern()) ? 'A' : '[') != 'A') {
            return null;
        }
        try {
            Matcher matcher = Pattern.compile(AFInAppEventParameterName("쨩ℏ즼㊈視鹫缡ꭖ㚛泧䕰뫰").intern()).matcher(context.getCacheDir().getPath().replace(AFInAppEventParameterName("ᨎ쭜䶴陊\uf408帬祖軖").intern(), ""));
            if ((matcher.find() ? '/' : (char) 19) != 19) {
                int i = AFVersionDeclaration + 109;
                getLevel = i % 128;
                int i2 = i % 2;
                String group = matcher.group(1);
                int i3 = getLevel + 117;
                AFVersionDeclaration = i3 % 128;
                int i4 = i3 % 2;
                return group;
            }
            return null;
        } catch (Exception e2) {
            aj valueOf2 = aj.valueOf();
            String intern = AFInAppEventParameterName("湀\ud841倴連断\u2fec팷惂뤚뫗\uf31d戴\ue9f4猽㪬⎮髼ᐁ").intern();
            StringBuilder sb = new StringBuilder();
            sb.append(AFInAppEventParameterName("壐樅\ueda7弙삤烉ӯ븢꜡㙤㕌櫩钴후嫶䌌볃놙倴連ᷤ蓉섎奱걏︠\uf837푤ꍟԅଣ窀\uf31d戴\ue9f4猽㪬⎮髼ᐁ륞㠻").intern());
            sb.append(e2);
            valueOf2.AFInAppEventType(null, intern, sb.toString());
            return null;
        }
    }

    private static String valueOf(Context context) {
        PackageManager packageManager;
        String packageName;
        int i = getLevel + 41;
        AFVersionDeclaration = i % 128;
        try {
            if ((i % 2 != 0 ? '#' : ':') != ':') {
                packageManager = context.getPackageManager();
                packageName = context.getPackageName();
            } else {
                packageManager = context.getPackageManager();
                packageName = context.getPackageName();
            }
            String str = packageManager.getPackageInfo(packageName, 0).packageName;
            int i2 = AFVersionDeclaration + 47;
            getLevel = i2 % 128;
            if (i2 % 2 != 0) {
            }
            return str;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    private static String values(Context context, String str) {
        boolean z;
        Object[] objArr = null;
        try {
            int i = getLevel + 123;
            AFVersionDeclaration = i % 128;
            int i2 = i % 2;
            for (ApplicationInfo applicationInfo : (List) PackageManager.class.getDeclaredMethod(AFInAppEventParameterName("\uf6b8鿮钴후돘≷蕃昴钸䙡삤烉迏꣪璿\ue58b荤ᑪ䶴陊㪬⎮髼ᐁ쓎\ud849").intern(), Integer.TYPE).invoke(context.getPackageManager(), 0)) {
                int i3 = getLevel + 39;
                AFVersionDeclaration = i3 % 128;
                int i4 = i3 % 2;
                if (((PackageItemInfo) applicationInfo).packageName.equals(str)) {
                    z = false;
                    continue;
                } else {
                    z = true;
                    continue;
                }
                if (!z) {
                    int i5 = getLevel + 13;
                    AFVersionDeclaration = i5 % 128;
                    if (!(i5 % 2 != 0)) {
                        return Boolean.TRUE.toString();
                    }
                    String obj = Boolean.TRUE.toString();
                    int length = objArr.length;
                    return obj;
                }
            }
        } catch (IllegalAccessException e2) {
            aj valueOf2 = aj.valueOf();
            String intern = AFInAppEventParameterName("꾭孶倴連断\u2fec팷惂偸빁㛓ே삤烉⸳\uf401篛䠭쑅뒕䥦\uf855铺ꢻ㜃ꨍ").intern();
            StringBuilder sb = new StringBuilder();
            sb.append(AFInAppEventParameterName("求좌\ueda7弙삤烉ӯ븢寬쇈䃺裒ꠃ\u0a57榐햘㛓ே삤烉⸳\uf401鞾嗉\udbf1㛐钴후嵵䑱ӯ븢\uf837푤ꍟԅଣ窀\uf31d戴\ue9f4猽㪬⎮髼ᐁ륞㠻").intern());
            sb.append(e2);
            valueOf2.AFInAppEventType(null, intern, sb.toString());
        } catch (NoSuchMethodException e3) {
            aj valueOf3 = aj.valueOf();
            String intern2 = AFInAppEventParameterName("꾭孶倴連断\u2fec팷惂偸빁㛓ே삤烉⸳\uf401篛䠭쑅뒕䥦\uf855铺ꢻ㜃ꨍ").intern();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(AFInAppEventParameterName("求좌\ueda7弙삤烉ӯ븢寬쇈䃺裒ꠃ\u0a57榐햘㛓ே삤烉⸳\uf401鞾嗉\udbf1㛐钴후嵵䑱ӯ븢\uf837푤ꍟԅଣ窀\uf31d戴\ue9f4猽㪬⎮髼ᐁ륞㠻").intern());
            sb2.append(e3);
            valueOf3.AFInAppEventType(null, intern2, sb2.toString());
        } catch (InvocationTargetException e4) {
            aj valueOf4 = aj.valueOf();
            String intern3 = AFInAppEventParameterName("꾭孶倴連断\u2fec팷惂偸빁㛓ே삤烉⸳\uf401篛䠭쑅뒕䥦\uf855铺ꢻ㜃ꨍ").intern();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(AFInAppEventParameterName("求좌\ueda7弙삤烉ӯ븢寬쇈䃺裒ꠃ\u0a57榐햘㛓ே삤烉⸳\uf401鞾嗉\udbf1㛐钴후嵵䑱ӯ븢\uf837푤ꍟԅଣ窀\uf31d戴\ue9f4猽㪬⎮髼ᐁ륞㠻").intern());
            sb3.append(e4);
            valueOf4.AFInAppEventType(null, intern3, sb3.toString());
        }
        return Boolean.FALSE.toString();
    }

    /* loaded from: classes3.dex */
    static class e extends HashMap<String, Object> {
        private static int AFInAppEventParameterName = 0;
        private static int AFLogger$LogLevel = 1;
        private static char[] valueOf = {28, '9', '9', '9', 'O', 'e', 'h', 129, 260, 266, 271, 272, 253, 243, 264, 270, 261, 258, 243, 239, 263, 265, 'g', 209, 207, 205, 205, '2', 'J', '2', '4', '2', 'J', 'N', '5', 'K', 'L', '1', '0', '4', 'M', 'a', 'a', 'c', 'c', 's', 231, 233, 237, 234, 231, 232, 244, 255, 248, 238, 239, 247, 237, 228, 219, 241, 273, 271, 273, 270, 261, 234, 244, 276, 268, 271, 276, 270, 238, 236, 265, 271, 275, 270, 268, 266, ':', 'l', 'i', 'n', 'j', 'k', 'i', 'j', 't', 's', 'k', Typography.less, 'X', '7', 166, 'g', 166, 'g', Typography.section, 'f', Typography.section, 'f', 156, Typography.pound, 151, 170, 158, 153, 'h', 'h'};
        private static long values = 3100330032987487916L;
        private final Context AFInAppEventType;
        private final Map<String, Object> AFKeystoreWrapper;

        /* JADX INFO: Access modifiers changed from: package-private */
        public e(Map<String, Object> map, Context context) {
            this.AFKeystoreWrapper = map;
            this.AFInAppEventType = context;
            put(valueOf(), AFInAppEventParameterName());
        }

        private static StringBuilder valueOf(String... strArr) throws Exception {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (true) {
                if (i >= 3) {
                    break;
                }
                int i2 = AFLogger$LogLevel + 19;
                AFInAppEventParameterName = i2 % 128;
                if ((i2 % 2 != 0 ? Typography.less : ')') != '<') {
                    arrayList.add(Integer.valueOf(strArr[i].length()));
                    i++;
                } else {
                    arrayList.add(Integer.valueOf(strArr[i].length()));
                    i += 0;
                }
            }
            Collections.sort(arrayList);
            int intValue = ((Integer) arrayList.get(0)).intValue();
            StringBuilder sb = new StringBuilder();
            int i3 = 0;
            while (i3 < intValue) {
                int i4 = AFLogger$LogLevel + 75;
                AFInAppEventParameterName = i4 % 128;
                int i5 = i4 % 2;
                Integer num = null;
                for (int i6 = 0; i6 < 3; i6++) {
                    int i7 = AFLogger$LogLevel + 31;
                    AFInAppEventParameterName = i7 % 128;
                    int i8 = i7 % 2;
                    int charAt = strArr[i6].charAt(i3);
                    if ((num == null ? 'U' : '8') != 'U') {
                        charAt ^= num.intValue();
                    }
                    num = Integer.valueOf(charAt);
                }
                sb.append(Integer.toHexString(num.intValue()));
                i3++;
                int i9 = AFLogger$LogLevel + 1;
                AFInAppEventParameterName = i9 % 128;
                int i10 = i9 % 2;
            }
            return sb;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v3 */
        /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.String] */
        private String valueOf() {
            int i = AFLogger$LogLevel + 49;
            AFInAppEventParameterName = i % 128;
            int i2 = i % 2;
            int i3 = 4;
            try {
                String num = Integer.toString(Build.VERSION.SDK_INT);
                String obj = this.AFKeystoreWrapper.get(AFInAppEventParameterName("薝ṹ藼ꂳ鎙\uee9e덭輝缥ᦂ雳맚烨ᓞ鱋븉").intern()).toString();
                String obj2 = this.AFKeystoreWrapper.get(AFInAppEventParameterName("羹毌翛픒࠻甂ឞ⯴蔏").intern()).toString();
                if ((obj2 == null ? (char) 27 : '%') != '%') {
                    int i4 = AFLogger$LogLevel + 81;
                    AFInAppEventParameterName = i4 % 128;
                    int i5 = i4 % 2;
                    obj2 = AFInAppEventParameterName("墧꺥壩၆❫婬矶䮰ꈋ\ua95b∪絀").intern();
                }
                StringBuilder sb = new StringBuilder(obj);
                sb.reverse();
                StringBuilder valueOf2 = valueOf(num, obj2, sb.toString());
                int length = valueOf2.length();
                if (length > 4) {
                    valueOf2.delete(4, length);
                } else {
                    while (true) {
                        if ((length < 4 ? '\f' : '9') != '\f') {
                            break;
                        }
                        length++;
                        valueOf2.append('1');
                    }
                }
                valueOf2.insert(0, AFInAppEventParameterName("骭\ue135髆忼㴛䀥䧐").intern());
                i3 = valueOf2.toString();
                return i3;
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(AFInAppEventParameterName("㘷栢㙑훯᥄摵쪏\uf6e7첄濗ᱝ쀴썔抍ᚨ쟱옠砧ॴ\ub74f6羣ఆ큆퍇狓ۖퟶ혈䠃㥺\ueaaa\uecf5俻㱽\ue056\ue3a9䊠㛈\ue713\ue615塚⦒諾ﲋ彃").intern());
                sb2.append(e);
                AFLogger.AFInAppEventParameterName(sb2.toString());
                int[] iArr = new int[i3];
                // fill-array-data instruction
                iArr[0] = 0;
                iArr[1] = 7;
                iArr[2] = 0;
                iArr[3] = 7;
                return AFInAppEventParameterName(iArr, "\u0001\u0000\u0000\u0000\u0001\u0001\u0000", Process.supportsProcesses()).intern();
            }
        }

        private String AFInAppEventParameterName() {
            String obj;
            int i;
            try {
                String obj2 = this.AFKeystoreWrapper.get(AFInAppEventParameterName("薝ṹ藼ꂳ鎙\uee9e덭輝缥ᦂ雳맚烨ᓞ鱋븉").intern()).toString();
                String obj3 = this.AFKeystoreWrapper.get(AFInAppEventParameterName(new int[]{7, 15, TLog.C157, 0}, "\u0001\u0001\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001", !URLUtil.isJavaScriptUrl("javascript:")).intern()).toString();
                String replaceAll = AFInAppEventParameterName(new int[]{22, 5, TLog.C157, 1}, "\u0001\u0000\u0000\u0001\u0001", !TextUtils.equals("", "")).intern().replaceAll(AFInAppEventParameterName("툮\ueaaf툃呱꺒펩\uf849쑣⢙").intern(), "");
                StringBuilder sb = new StringBuilder();
                sb.append(obj2);
                sb.append(obj3);
                sb.append(replaceAll);
                String values2 = af.values(sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(values2.substring(0, 16));
                obj = sb2.toString();
            } catch (Exception e) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(AFInAppEventParameterName("꾊偓꿬\uee9e层ⅳㆁ෩唹垦奛㬺嫩嫼厮㳿徝䁖䱲↓䕋䟒䤀⭈䫺䪢䏍⳼侠瀧籮ᇭ畋瞋礯᭕穌竗珓ᰎ羹怲沏ƴ散杼楱ଭ").intern());
                sb3.append(e);
                AFLogger.AFInAppEventParameterName(sb3.toString());
                StringBuilder sb4 = new StringBuilder();
                sb4.append("");
                sb4.append(AFInAppEventParameterName(new int[]{27, 18, 0, 13}, "\u0001\u0001\u0001\u0001\u0000\u0000\u0001\u0000\u0001\u0000\u0000\u0001\u0001\u0000\u0001\u0001\u0001\u0000", PhoneNumberUtils.isGlobalPhoneNumber("")).intern());
                obj = sb4.toString();
            }
            try {
                Intent registerReceiver = this.AFInAppEventType.registerReceiver(null, new IntentFilter(AFInAppEventParameterName(new int[]{45, 37, 163, 0}, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0000\u0000\u0001\u0000\u0001\u0001\u0000\u0001\u0000\u0000\u0001\u0001\u0000\u0001\u0001\u0000\u0001\u0000\u0001\u0000\u0000\u0001", !TextUtils.isGraphic("")).intern()));
                int i2 = -2700;
                if ((registerReceiver != null ? 'a' : 'D') == 'a') {
                    int i3 = AFInAppEventParameterName + 17;
                    AFLogger$LogLevel = i3 % 128;
                    int i4 = i3 % 2;
                    i2 = registerReceiver.getIntExtra(AFInAppEventParameterName(new int[]{82, 11, 0, 0}, "\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001", !URLUtil.isJavaScriptUrl("javascript:")).intern(), -2700);
                }
                String str = this.AFInAppEventType.getApplicationInfo().nativeLibraryDir;
                if (str != null) {
                    int i5 = AFInAppEventParameterName + 107;
                    AFLogger$LogLevel = i5 % 128;
                    int i6 = i5 % 2;
                    if (str.contains(AFInAppEventParameterName(new int[]{93, 3, 0, 3}, "\u0000\u0000\u0000", !URLUtil.isHttpUrl("http://")).intern())) {
                        int i7 = AFLogger$LogLevel + 73;
                        AFInAppEventParameterName = i7 % 128;
                        if ((i7 % 2 != 0 ? (char) 16 : Typography.quote) != 16) {
                            i = 1;
                            int size = ((SensorManager) this.AFInAppEventType.getSystemService(AFInAppEventParameterName("妿ટ姌둖篔ۢ䒔磣ꌓ൸").intern())).getSensorList(-1).size();
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(AFInAppEventParameterName("涞淼颍㲲ⓔ").intern());
                            sb5.append(i2);
                            sb5.append(AFInAppEventParameterName("㓷印㓑\ueda4ʵ脧").intern());
                            sb5.append(i);
                            sb5.append(AFInAppEventParameterName("ᓹꮼᓟᕣ襺瑞").intern());
                            sb5.append(size);
                            sb5.append(AFInAppEventParameterName("⳰赻Ⳗ㎧鱤盬").intern());
                            sb5.append(this.AFKeystoreWrapper.size());
                            String obj4 = sb5.toString();
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(obj);
                            sb6.append(C0015d.values(C0015d.AFInAppEventType(C0015d.valueOf(obj4))));
                            return sb6.toString();
                        }
                    }
                }
                i = 0;
                int size2 = ((SensorManager) this.AFInAppEventType.getSystemService(AFInAppEventParameterName("妿ટ姌둖篔ۢ䒔磣ꌓ൸").intern())).getSensorList(-1).size();
                StringBuilder sb52 = new StringBuilder();
                sb52.append(AFInAppEventParameterName("涞淼颍㲲ⓔ").intern());
                sb52.append(i2);
                sb52.append(AFInAppEventParameterName("㓷印㓑\ueda4ʵ脧").intern());
                sb52.append(i);
                sb52.append(AFInAppEventParameterName("ᓹꮼᓟᕣ襺瑞").intern());
                sb52.append(size2);
                sb52.append(AFInAppEventParameterName("⳰赻Ⳗ㎧鱤盬").intern());
                sb52.append(this.AFKeystoreWrapper.size());
                String obj42 = sb52.toString();
                StringBuilder sb62 = new StringBuilder();
                sb62.append(obj);
                sb62.append(C0015d.values(C0015d.AFInAppEventType(C0015d.valueOf(obj42))));
                return sb62.toString();
            } catch (Exception e2) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(AFInAppEventParameterName("꾊偓꿬\uee9e层ⅳㆁ෩唹垦奛㬺嫩嫼厮㳿徝䁖䱲↓䕋䟒䤀⭈䫺䪢䏍⳼侠瀧籮ᇭ畋瞋礯᭕穌竗珓ᰎ羹怲沏ƴ散杼楱ଭ").intern());
                sb7.append(e2);
                AFLogger.AFInAppEventParameterName(sb7.toString());
                StringBuilder sb8 = new StringBuilder();
                sb8.append(obj);
                sb8.append(AFInAppEventParameterName(new int[]{96, 16, 54, 9}, null, !Gravity.isHorizontal(0)).intern());
                return sb8.toString();
            }
        }

        /* renamed from: com.appsflyer.internal.d$e$d  reason: collision with other inner class name */
        /* loaded from: classes3.dex */
        public static class C0015d {
            Boolean AFInAppEventParameterName;
            public final Boolean valueOf;
            public final String values;

            C0015d() {
            }

            static byte[] valueOf(String str) throws Exception {
                return str.getBytes();
            }

            static byte[] AFInAppEventType(byte[] bArr) throws Exception {
                for (int i = 0; i < bArr.length; i++) {
                    bArr[i] = (byte) (bArr[i] ^ ((i % 2) + 42));
                }
                return bArr;
            }

            static String values(byte[] bArr) throws Exception {
                StringBuilder sb = new StringBuilder();
                for (byte b : bArr) {
                    String hexString = Integer.toHexString(b);
                    if (hexString.length() == 1) {
                        hexString = "0".concat(String.valueOf(hexString));
                    }
                    sb.append(hexString);
                }
                return sb.toString();
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            public C0015d(String str, Boolean bool) {
                this.values = str;
                this.valueOf = bool;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r11v1 */
        /* JADX WARN: Type inference failed for: r11v13, types: [char[]] */
        /* JADX WARN: Type inference failed for: r11v9 */
        private static String AFInAppEventParameterName(String str) {
            if (!(str == 0)) {
                int i = AFLogger$LogLevel + 3;
                AFInAppEventParameterName = i % 128;
                int i2 = i % 2;
                str = str.toCharArray();
            }
            char[] valueOf2 = bz.valueOf(values, (char[]) str);
            int i3 = 4;
            while (true) {
                if (!(i3 < valueOf2.length)) {
                    break;
                }
                int i4 = AFLogger$LogLevel + 39;
                AFInAppEventParameterName = i4 % 128;
                int i5 = i4 % 2;
                valueOf2[i3] = (char) ((valueOf2[i3] ^ valueOf2[i3 % 4]) ^ ((i3 - 4) * values));
                i3++;
            }
            String str2 = new String(valueOf2, 4, valueOf2.length - 4);
            int i6 = AFInAppEventParameterName + 19;
            AFLogger$LogLevel = i6 % 128;
            if ((i6 % 2 == 0 ? '!' : '7') != '7') {
                ?? r11 = 0;
                int length = r11.length;
                return str2;
            }
            return str2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r13v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r13v1 */
        /* JADX WARN: Type inference failed for: r13v14, types: [byte[]] */
        private static String AFInAppEventParameterName(int[] iArr, String str, boolean z) {
            char[] cArr;
            char[] cArr2;
            if (!(str == 0)) {
                str = str.getBytes("ISO-8859-1");
            }
            byte[] bArr = (byte[]) str;
            int i = iArr[0];
            int i2 = iArr[1];
            int i3 = iArr[2];
            int i4 = iArr[3];
            char[] cArr3 = new char[i2];
            System.arraycopy(valueOf, i, cArr3, 0, i2);
            if (bArr != null) {
                cArr = new char[i2];
                char c = 0;
                for (int i5 = 0; i5 < i2; i5++) {
                    if ((bArr[i5] == 1 ? 'b' : (char) 20) != 20) {
                        cArr[i5] = (char) (((cArr3[i5] << 1) + 1) - c);
                    } else {
                        cArr[i5] = (char) ((cArr3[i5] << 1) - c);
                    }
                    c = cArr[i5];
                }
            } else {
                cArr = cArr3;
            }
            if (i4 > 0) {
                int i6 = AFInAppEventParameterName + 125;
                AFLogger$LogLevel = i6 % 128;
                if (i6 % 2 == 0) {
                    char[] cArr4 = new char[i2];
                    System.arraycopy(cArr, 0, cArr4, 0, i2);
                    System.arraycopy(cArr4, 1, cArr, i2 >>> i4, i4);
                    System.arraycopy(cArr4, i4, cArr, 1, i2 << i4);
                } else {
                    char[] cArr5 = new char[i2];
                    System.arraycopy(cArr, 0, cArr5, 0, i2);
                    int i7 = i2 - i4;
                    System.arraycopy(cArr5, 0, cArr, i7, i4);
                    System.arraycopy(cArr5, i4, cArr, 0, i7);
                }
            }
            if (z) {
                cArr2 = new char[i2];
                for (int i8 = 0; i8 < i2; i8++) {
                    cArr2[i8] = cArr[(i2 - i8) - 1];
                }
            } else {
                cArr2 = cArr;
            }
            if (i3 > 0) {
                int i9 = AFInAppEventParameterName + 65;
                AFLogger$LogLevel = i9 % 128;
                int i10 = i9 % 2;
                int i11 = 0;
                while (i11 < i2) {
                    cArr2[i11] = (char) (cArr2[i11] - iArr[2]);
                    i11++;
                    int i12 = AFInAppEventParameterName + 7;
                    AFLogger$LogLevel = i12 % 128;
                    if (i12 % 2 == 0) {
                    }
                }
            }
            return new String(cArr2);
        }
    }

    private static String AFInAppEventParameterName(String str) {
        boolean z = str != null;
        char[] cArr = str;
        if (z) {
            int i = getLevel + 121;
            AFVersionDeclaration = i % 128;
            int i2 = i % 2;
            cArr = str.toCharArray();
        }
        char[] cArr2 = cArr;
        char[] cArr3 = new char[cArr2.length];
        char[] cArr4 = new char[2];
        int i3 = 0;
        while (true) {
            if ((i3 < cArr2.length ? ']' : 'S') == ']') {
                int i4 = AFVersionDeclaration + 99;
                getLevel = i4 % 128;
                int i5 = i4 % 2;
                cArr4[0] = cArr2[i3];
                int i6 = i3 + 1;
                cArr4[1] = cArr2[i6];
                by.AFInAppEventParameterName(cArr4, AFKeystoreWrapper, AFInAppEventParameterName, valueOf, AFInAppEventType);
                cArr3[i3] = cArr4[0];
                cArr3[i6] = cArr4[1];
                i3 += 2;
            } else {
                return new String(cArr3, 1, (int) cArr3[0]);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [int] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [int] */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v4, types: [char[]] */
    /* JADX WARN: Type inference failed for: r8v5, types: [char[]] */
    private static String values(String str) {
        char c = 1;
        if (str != 0) {
            int i = getLevel + 1;
            AFVersionDeclaration = i % 128;
            if ((i % 2 != 0 ? (char) 11 : '\n') != '\n') {
                str = str.toCharArray();
                Object obj = null;
                super.hashCode();
            } else {
                str = str.toCharArray();
            }
        }
        char[] cArr = (char[]) str;
        char c2 = cArr[0];
        char[] cArr2 = new char[cArr.length - 1];
        while (c < cArr.length) {
            int i2 = getLevel + 119;
            AFVersionDeclaration = i2 % 128;
            if ((i2 % 2 != 0 ? '3' : '#') != '3') {
                cArr2[c - 1] = (char) ((cArr[c] ^ (c * c2)) ^ values);
                c++;
            } else {
                cArr2[c % 0] = (char) ((cArr[c] | (c * c2)) & values);
                c += 27;
            }
        }
        return new String(cArr2);
    }
}
