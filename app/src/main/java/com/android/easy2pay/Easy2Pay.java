package com.android.easy2pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.facebook.AccessToken;
import com.ssjj.fnsdk.core.update.FNUpdateManager;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import net.openid.appauth.AuthorizationRequest;

/* loaded from: classes3.dex */
public class Easy2Pay implements Easy2PayConnectionListener, Easy2PayDialogListener {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$android$easy2pay$Easy2Pay$Language = null;
    private static final String ACTION_SMS_SENT = "algorism.ad.ohm.SMS_SENT_ACTION";
    private static final String CHECK_CHAREGD_SESSION = "CHK";
    private static final String CHECK_CHARGED_URL = "http://api.easy2pay.co/add-on/inquiry.php";
    public static final int ERROR_CANNOT_CHARGING = 306;
    public static final int ERROR_CANNOT_GET_PINCODE = 302;
    public static final int ERROR_CANNOT_GET_PRICE_LIST = 301;
    public static final int ERROR_CANNOT_SEND_SMS = 305;
    public static final int ERROR_PRICE_IS_INVALID = 304;
    public static final int ERROR_SIMCARD_NOTFOUND = 303;
    public static final int EVENT_EASY2PAY_IS_CHARGING = 202;
    public static final int EVENT_EASY2PAY_IS_CHARGING_IN_BACKGROUND = 203;
    public static final int EVENT_USER_CANCEL_CHARGE = 201;
    private static final String GET_PINCODE_SESSION = "PIN";
    private static final String GET_PINCODE_URL = "http://api.easy2pay.co/add-on/init.php";
    private static final String GET_PRICES_SESSION = "PRC";
    private static final String GET_PRICES_URL = "http://api.easy2pay.co/add-on/get-price-list2.php";
    private static final int PROGRESS_STEP = 10;
    private final Context context;
    private final Hashtable<String, String> descList;
    private Handler handler;
    private boolean hasIMSI;
    private boolean isBackgroudChecking;
    private boolean isFullScreen;
    private String langCode;
    private int langId;
    private final LoadingDialog ldDialog;
    private Easy2PayListener listener;
    private String mcc;
    private String mnc;
    private final String partnerId;
    private final ProgressBarDialog pgDialog;
    private final Vector<String[]> priceList;
    private int progressIndex;
    private final int progressMax;
    private String ptxId;
    private final String secretKey;
    private final SMSBroadcastReceiver smsListener;
    private String txId;
    private String userId;
    private String[] userPriceIds;
    private final Hashtable<String, String> validPriceIdList;

    /* loaded from: classes3.dex */
    public enum Language {
        EN,
        TH,
        MS,
        ID,
        VI;

        /* renamed from: values  reason: to resolve conflict with enum method */
        public static Language[] valuesCustom() {
            Language[] valuesCustom = values();
            int length = valuesCustom.length;
            Language[] languageArr = new Language[length];
            System.arraycopy(valuesCustom, 0, languageArr, 0, length);
            return languageArr;
        }
    }

    public String getVersion() {
        return "1.1.0.5";
    }

    @Override // com.android.easy2pay.Easy2PayDialogListener
    public void onActivityResume() {
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$android$easy2pay$Easy2Pay$Language() {
        int[] iArr = $SWITCH_TABLE$com$android$easy2pay$Easy2Pay$Language;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[Language.valuesCustom().length];
        try {
            iArr2[Language.EN.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[Language.ID.ordinal()] = 4;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[Language.MS.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[Language.TH.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[Language.VI.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        $SWITCH_TABLE$com$android$easy2pay$Easy2Pay$Language = iArr2;
        return iArr2;
    }

    @Deprecated
    public Easy2Pay(Context context, String str, String str2) {
        this(context, str, str2, 120);
    }

    @Deprecated
    public Easy2Pay(Context context, String str, String str2, boolean z) {
        this(context, str, str2, 120, z, null, null, Language.TH);
    }

    @Deprecated
    public Easy2Pay(Context context, String str, String str2, int i) {
        this(context, str, str2, i, false, null, null, Language.TH);
    }

    public Easy2Pay(Context context, String str, String str2, int i, boolean z) {
        this(context, str, str2, i, z, null, null, Language.TH);
    }

    public Easy2Pay(Context context, String str, String str2, int i, String str3, String str4) {
        this(context, str, str2, i, false, str3, str4, Language.EN);
    }

    public Easy2Pay(Context context, String str, String str2, int i, boolean z, String str3, String str4) {
        this(context, str, str2, i, z, str3, str4, Language.EN);
    }

    public Easy2Pay(Context context, String str, String str2, int i, boolean z, Language language) {
        this(context, str, str2, i, z, null, null, language);
    }

    public Easy2Pay(Context context, String str, String str2, int i, boolean z, String str3, String str4, Language language) {
        this.hasIMSI = false;
        this.userPriceIds = null;
        this.progressIndex = 0;
        this.isFullScreen = false;
        this.ptxId = null;
        this.userId = null;
        this.txId = null;
        this.isBackgroudChecking = false;
        this.context = context;
        this.partnerId = str;
        this.secretKey = str2;
        this.progressMax = i + 10;
        this.isFullScreen = z;
        if (str3 == null || str4 == null) {
            try {
                String networkOperator = ((TelephonyManager) context.getSystemService(AuthorizationRequest.Scope.PHONE)).getNetworkOperator();
                if (networkOperator != null) {
                    str3 = str3 == null ? networkOperator.substring(0, 3) : str3;
                    if (str4 == null) {
                        str4 = networkOperator.substring(3);
                    }
                }
            } catch (Exception unused) {
            }
            str4 = "00";
            str3 = "000";
        }
        this.mcc = str3;
        this.mnc = str4;
        setLangauge(language);
        this.ldDialog = new LoadingDialog(context, this.langId);
        this.pgDialog = new ProgressBarDialog(context, this.progressMax, this.langId);
        this.handler = new Handler();
        this.priceList = new Vector<>();
        this.validPriceIdList = new Hashtable<>();
        this.descList = new Hashtable<>();
        this.descList.put("title", Resource.STRING_TITLE_VALUE[this.langId]);
        this.descList.put("description", Resource.STRING_DESC_VALUE[this.langId]);
        this.smsListener = new SMSBroadcastReceiver(this, null);
    }

    public void setLangauge(Language language) {
        int i = $SWITCH_TABLE$com$android$easy2pay$Easy2Pay$Language()[language.ordinal()];
        if (i == 1) {
            this.langId = 0;
            this.langCode = "EN";
        } else if (i == 2) {
            this.langId = 1;
            this.langCode = "TH";
        } else if (i == 3) {
            this.langId = 2;
            this.langCode = "MS";
        } else if (i == 4) {
            this.langId = 3;
            this.langCode = "ID";
        } else if (i == 5) {
            this.langId = 4;
            this.langCode = "VI";
        } else {
            this.langId = 0;
            this.langCode = "EN";
        }
    }

    public void setEasy2PayListener(Easy2PayListener easy2PayListener) {
        this.listener = easy2PayListener;
        this.pgDialog.setEasy2Pay(this);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:55:0x02b1
        	at jadx.core.dex.visitors.blocks.BlockProcessor.checkForUnreachableBlocks(BlockProcessor.java:81)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:47)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    public void purchase(java.lang.String r22, java.lang.String r23, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 764
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.easy2pay.Easy2Pay.purchase(java.lang.String, java.lang.String, java.lang.String):void");
    }

    @Deprecated
    public void purchase(String str, String str2) {
        purchase(str, str2, (String[]) null);
    }

    @Deprecated
    public void purchase(String str, String str2, String[] strArr) {
        if (this.isBackgroudChecking) {
            showAlert(Resource.STRING_WAITING_FOR_BACKGROUND_CHARGING[this.langId]);
            return;
        }
        this.ptxId = str;
        this.userId = str2;
        if (!this.hasIMSI) {
            try {
                if (((TelephonyManager) this.context.getSystemService(AuthorizationRequest.Scope.PHONE)).getSubscriberId() == null) {
                    if (this.listener != null) {
                        this.listener.onError(null, null, null, 303, Resource.STRING_ERROR_SIMCARD_INACTIVE[this.langId]);
                        return;
                    }
                    return;
                }
                this.hasIMSI = true;
            } catch (Exception unused) {
            }
        } else if (this.mcc.equals("000") && this.mnc.equals("00")) {
            Easy2PayListener easy2PayListener = this.listener;
            if (easy2PayListener != null) {
                easy2PayListener.onError(null, null, null, 303, Resource.STRING_ERROR_SIMCARD_INACTIVE[this.langId]);
                return;
            }
            return;
        }
        this.userPriceIds = strArr;
        this.ldDialog.show();
        String genP_TXID = genP_TXID();
        String generate = MD5Factory.generate(String.valueOf(stringSortAndCat(new String[]{"lang", "mcc", "mnc", "p_txid", "partner_id"}, new String[]{this.langCode, this.mcc, this.mnc, genP_TXID, this.partnerId})) + this.secretKey);
        Easy2PayConnection easy2PayConnection = new Easy2PayConnection(this);
        String[] strArr2 = {str, str2};
        easy2PayConnection.get(GET_PRICES_SESSION, strArr2, "http://api.easy2pay.co/add-on/get-price-list2.php?p_txid=" + URLEncoder.encode(genP_TXID) + "&partner_id=" + URLEncoder.encode(this.partnerId) + "&mcc=" + URLEncoder.encode(this.mcc) + "&mnc=" + URLEncoder.encode(this.mnc) + "&lang=" + URLEncoder.encode(this.langCode) + "&sig=" + URLEncoder.encode(generate));
    }

    public void checkCharging(String str, String str2, String str3) {
        checkCharged(str, str2, str3, false);
    }

    private String genP_TXID() {
        String format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        String format2 = new SimpleDateFormat("HHmmssSSSSSS", Locale.getDefault()).format(new Date());
        return String.valueOf(format) + format2;
    }

    public String getPtxId() {
        return this.ptxId;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getTxId() {
        return this.txId;
    }

    private String stringSortAndCat(String[] strArr, String[] strArr2) {
        String[][] strArr3 = (String[][]) Array.newInstance(String.class, strArr.length, 2);
        for (int i = 0; i < strArr.length; i++) {
            strArr3[i][0] = strArr[i];
            strArr3[i][1] = strArr2[i];
        }
        Arrays.sort(strArr3, new Comparator<String[]>() { // from class: com.android.easy2pay.Easy2Pay.1
            @Override // java.util.Comparator
            public int compare(String[] strArr4, String[] strArr5) {
                return strArr4[0].compareTo(strArr5[0]);
            }
        });
        String str = "";
        for (String[] strArr4 : strArr3) {
            str = String.valueOf(str) + strArr4[1];
        }
        return str;
    }

    public void close() {
        if (this.pgDialog.isShowing()) {
            this.pgDialog.dismiss();
        }
        if (this.ldDialog.isShowing()) {
            this.ldDialog.dismiss();
        }
    }

    @Override // com.android.easy2pay.Easy2PayConnectionListener
    public final void onReceive(String str, final String[] strArr, byte[] bArr) {
        String str2;
        String str3;
        String str4;
        final String str5;
        String[] split;
        String str6;
        char c = 2;
        char c2 = 3;
        char c3 = 1;
        if (str.equals(GET_PRICES_SESSION)) {
            this.ldDialog.dismiss();
            try {
                str6 = new String(bArr, "UTF-8");
            } catch (Exception unused) {
                str6 = new String(bArr);
            }
            try {
                XMLElement xMLElement = new XMLElement();
                xMLElement.parseString(str6);
                Iterator it = xMLElement.getChildren().iterator();
                while (it.hasNext()) {
                    XMLElement xMLElement2 = (XMLElement) it.next();
                    if (xMLElement2.getName().equals("title")) {
                        this.descList.put("title", xMLElement2.getContent());
                    } else if (xMLElement2.getName().equals("description")) {
                        this.descList.put("description", xMLElement2.getContent());
                    } else if (xMLElement2.getName().equals("prices")) {
                        this.priceList.clear();
                        this.validPriceIdList.clear();
                        Vector children = xMLElement2.getChildren();
                        for (int i = 0; i < children.size(); i++) {
                            String[] strArr2 = new String[3];
                            Iterator it2 = ((XMLElement) children.elementAt(i)).getChildren().iterator();
                            while (it2.hasNext()) {
                                XMLElement xMLElement3 = (XMLElement) it2.next();
                                if (xMLElement3.getName().equals("priceId")) {
                                    strArr2[0] = xMLElement3.getContent();
                                } else if (xMLElement3.getName().equals("priceDescription")) {
                                    strArr2[1] = xMLElement3.getContent();
                                } else if (xMLElement3.getName().equals("shortcode")) {
                                    strArr2[2] = xMLElement3.getContent();
                                }
                            }
                            this.priceList.add(strArr2);
                            Hashtable<String, String> hashtable = this.validPriceIdList;
                            String str7 = strArr2[0];
                            StringBuilder sb = new StringBuilder();
                            sb.append(i);
                            hashtable.put(str7, sb.toString());
                        }
                    }
                }
                if (this.userPriceIds == null && this.priceList.size() > 0) {
                    this.userPriceIds = new String[this.priceList.size()];
                    for (int i2 = 0; i2 < this.priceList.size(); i2++) {
                        this.userPriceIds[i2] = this.priceList.elementAt(i2)[0];
                    }
                }
                if (this.userPriceIds != null && this.userPriceIds.length > 0) {
                    final Vector vector = new Vector();
                    for (int i3 = 0; i3 < this.userPriceIds.length; i3++) {
                        String str8 = this.validPriceIdList.get(this.userPriceIds[i3]);
                        if (str8 != null) {
                            vector.addElement(this.priceList.elementAt(Integer.parseInt(str8)));
                        }
                    }
                    if (vector.size() > 0) {
                        this.handler.post(new Runnable() { // from class: com.android.easy2pay.Easy2Pay.2
                            @Override // java.lang.Runnable
                            public void run() {
                                if (!Easy2Pay.this.isFullScreen) {
                                    Context context = Easy2Pay.this.context;
                                    String str9 = (String) Easy2Pay.this.descList.get("title");
                                    String str10 = (String) Easy2Pay.this.descList.get("description");
                                    Vector vector2 = vector;
                                    String[] strArr3 = strArr;
                                    String str11 = strArr3[0];
                                    String str12 = strArr3[1];
                                    Easy2Pay easy2Pay = Easy2Pay.this;
                                    new Easy2PayDialog(context, str9, str10, vector2, str11, str12, easy2Pay, easy2Pay.langId).show();
                                    return;
                                }
                                Easy2PayScreen easy2PayScreen = new Easy2PayScreen();
                                easy2PayScreen.setEasy2PayDialogListener(Easy2Pay.this);
                                Intent intent = new Intent(Easy2Pay.this.context, easy2PayScreen.getClass());
                                intent.putExtra("title", (String) Easy2Pay.this.descList.get("title"));
                                intent.putExtra(FNUpdateManager.PARAM_DESC, (String) Easy2Pay.this.descList.get("description"));
                                String[] strArr4 = new String[vector.size()];
                                String[] strArr5 = new String[vector.size()];
                                String[] strArr6 = new String[vector.size()];
                                for (int i4 = 0; i4 < vector.size(); i4++) {
                                    String[] strArr7 = (String[]) vector.elementAt(i4);
                                    strArr4[i4] = strArr7[0];
                                    strArr5[i4] = strArr7[1];
                                    strArr6[i4] = strArr7[2];
                                }
                                intent.putExtra("pids", strArr4);
                                intent.putExtra("pdescs", strArr5);
                                intent.putExtra("scodes", strArr6);
                                intent.putExtra("ptxid", strArr[0]);
                                intent.putExtra("userid", strArr[1]);
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(Easy2Pay.this.langId);
                                intent.putExtra("langId", sb2.toString());
                                ((Activity) Easy2Pay.this.context).startActivity(intent);
                            }
                        });
                        return;
                    }
                    Log.w("Easy2Pay", "validPrices.size() is " + vector.size());
                    if (this.listener != null) {
                        this.listener.onError(strArr[0], strArr[1], null, 304, Resource.STRING_ERROR_PRICE_IS_INVALID[this.langId]);
                        return;
                    }
                    return;
                }
                if (this.userPriceIds != null) {
                    Log.w("Easy2Pay", "userPriceIds.length is " + this.userPriceIds.length);
                } else {
                    Log.w("Easy2Pay", "userPriceIds.length is null!");
                }
                if (this.listener != null) {
                    this.listener.onError(strArr[0], strArr[1], null, 304, Resource.STRING_ERROR_PRICE_IS_INVALID[this.langId]);
                    return;
                }
                return;
            } catch (Exception unused2) {
                Easy2PayListener easy2PayListener = this.listener;
                if (easy2PayListener != null) {
                    easy2PayListener.onError(strArr[0], strArr[1], null, 301, Resource.STRING_ERROR_XML_IS_INVALID[this.langId]);
                    return;
                }
                return;
            }
        }
        String str9 = "";
        if (str.equals(GET_PINCODE_SESSION)) {
            this.ldDialog.dismiss();
            try {
                str3 = new String(bArr, "UTF-8");
            } catch (Exception unused3) {
                str3 = new String(bArr);
            }
            try {
                XMLElement xMLElement4 = new XMLElement();
                xMLElement4.parseString(str3);
                Iterator it3 = xMLElement4.getChildren().iterator();
                String str10 = "";
                final String str11 = str10;
                String str12 = str11;
                String str13 = str12;
                String str14 = str13;
                while (it3.hasNext()) {
                    XMLElement xMLElement5 = (XMLElement) it3.next();
                    if (xMLElement5.getName().equals("status")) {
                        str10 = xMLElement5.getContent();
                    } else if (xMLElement5.getName().equals("statusDetail")) {
                        str14 = xMLElement5.getContent();
                    } else if (xMLElement5.getName().equals("pTxId")) {
                        str11 = xMLElement5.getContent();
                    } else if (xMLElement5.getName().equals("txId")) {
                        str12 = xMLElement5.getContent();
                    } else {
                        if (xMLElement5.getName().equals("pin")) {
                            str13 = xMLElement5.getContent();
                        }
                        c = 2;
                        c3 = 1;
                    }
                    c = 2;
                }
                if (str10.equals("200")) {
                    final String str15 = strArr[c3];
                    String str16 = strArr[c];
                    if (str16.contains("|")) {
                        str5 = String.valueOf(split[c3]) + " ";
                        str4 = str16.split("\\|")[0];
                    } else {
                        str4 = str16;
                        str5 = "";
                    }
                    final String str17 = str12;
                    final String str18 = str13;
                    final String str19 = str4;
                    this.handler.post(new Runnable() { // from class: com.android.easy2pay.Easy2Pay.3
                        @Override // java.lang.Runnable
                        public void run() {
                            Easy2Pay.this.progressIndex = 0;
                            Easy2Pay.this.pgDialog.setPtxId(str11);
                            Easy2Pay.this.pgDialog.setUserId(str15);
                            Easy2Pay.this.pgDialog.setTxId(str17);
                            Easy2Pay.this.pgDialog.show();
                            if (Easy2Pay.this.listener != null) {
                                Easy2Pay.this.listener.onEvent(str11, str15, str17, 202, Resource.STRING_EVENT_EASY2PAY_IS_CHARGING[Easy2Pay.this.langId]);
                            }
                            Easy2Pay.this.context.registerReceiver(Easy2Pay.this.smsListener, new IntentFilter(Easy2Pay.ACTION_SMS_SENT));
                            SmsManager smsManager = SmsManager.getDefault();
                            Intent intent = new Intent(Easy2Pay.ACTION_SMS_SENT);
                            intent.putExtra("ptxId", str11);
                            intent.putExtra("txId", str17);
                            intent.putExtra("userId", str15);
                            PendingIntent broadcast = PendingIntent.getBroadcast(Easy2Pay.this.context, 0, intent, 134217728);
                            String str20 = String.valueOf(str5) + Easy2Pay.this.partnerId + " " + str18;
                            Log.w("Easy2Pay", "Sending SMS: shortcode=" + str19 + ", message=" + str20);
                            smsManager.sendTextMessage(str19, null, str20, broadcast, null);
                        }
                    });
                } else if (this.listener != null) {
                    this.listener.onError(strArr[0], strArr[c3], null, 302, str14);
                }
            } catch (Exception unused4) {
                Easy2PayListener easy2PayListener2 = this.listener;
                if (easy2PayListener2 != null) {
                    easy2PayListener2.onError(strArr[0], strArr[1], null, 302, Resource.STRING_ERROR_XML_IS_INVALID[this.langId]);
                }
            }
        } else if (str.equals(CHECK_CHAREGD_SESSION)) {
            try {
                str2 = new String(bArr, "UTF-8");
            } catch (Exception unused5) {
                str2 = new String(bArr);
            }
            try {
                XMLElement xMLElement6 = new XMLElement();
                xMLElement6.parseString(str2);
                Iterator it4 = xMLElement6.getChildren().iterator();
                String str20 = "";
                String str21 = str20;
                String str22 = str21;
                String str23 = str22;
                String str24 = str23;
                while (it4.hasNext()) {
                    XMLElement xMLElement7 = (XMLElement) it4.next();
                    if (xMLElement7.getName().equals("status")) {
                        str22 = xMLElement7.getContent();
                    } else if (xMLElement7.getName().equals("statusDetail")) {
                        str24 = xMLElement7.getContent();
                    } else if (xMLElement7.getName().equals("pTxId")) {
                        str9 = xMLElement7.getContent();
                    } else {
                        if (xMLElement7.getName().equals("userId")) {
                            str20 = xMLElement7.getContent();
                        } else if (xMLElement7.getName().equals("priceId")) {
                            str23 = xMLElement7.getContent();
                        } else if (xMLElement7.getName().equals("txId")) {
                            str21 = xMLElement7.getContent();
                        }
                        c2 = 3;
                    }
                }
                this.ptxId = str9;
                this.userId = str20;
                this.txId = str21;
                if (str22.equals("200")) {
                    if (this.pgDialog.isShowing()) {
                        this.pgDialog.setWaitProgress(this.progressMax);
                        this.pgDialog.dismiss();
                    }
                    this.isBackgroudChecking = false;
                    if (this.listener != null) {
                        this.listener.onPurchaseResult(str9, str20, str21, str23, Integer.parseInt(str22), str24);
                    }
                    if (strArr[c2] == null) {
                        showAlert(Resource.STRING_ALERT_CHARGED[this.langId]);
                        return;
                    }
                    return;
                }
                if (!str22.equals("201") && !str22.equals("100")) {
                    if (str22.equals("404")) {
                        return;
                    }
                    if (this.pgDialog.isShowing()) {
                        this.pgDialog.setWaitProgress(this.progressMax);
                        this.pgDialog.dismiss();
                    }
                    this.isBackgroudChecking = false;
                    if (this.listener != null) {
                        this.listener.onPurchaseResult(str9, str20, str21, str23, Integer.parseInt(str22), str24);
                    }
                    if (strArr[c2] == null) {
                        showAlert(Resource.STRING_ALERT_CANNOT_CHARGING[this.langId]);
                        return;
                    }
                    return;
                }
                if (strArr[c2] == null) {
                    if (str9.equals(this.pgDialog.getPtxId())) {
                        if (this.pgDialog.isShowing()) {
                            this.progressIndex += 10;
                            this.pgDialog.setWaitProgress(this.progressIndex);
                            if (this.progressIndex < this.progressMax) {
                                try {
                                    Thread.sleep(10000L);
                                } catch (Exception unused6) {
                                }
                                checkCharged(str9, str20, str21);
                                return;
                            }
                            if (this.listener != null) {
                                this.listener.onEvent(str9, str20, str21, 203, Resource.STRING_EVENT_BACKGROUND_CHARGING[this.langId]);
                            }
                            this.pgDialog.dismiss();
                            showAlert(Resource.STRING_ALERT_BACKGROUND_CHARGING[this.langId]);
                            backgroundCheckCharged(str9, str20, str21);
                            return;
                        }
                        backgroundCheckCharged(str9, str20, str21);
                        return;
                    }
                    backgroundCheckCharged(str9, str20, str21);
                } else if (this.listener != null) {
                    this.listener.onEvent(str9, str20, str21, 202, Resource.STRING_EVENT_EASY2PAY_IS_CHARGING[this.langId]);
                }
            } catch (Exception unused7) {
                Easy2PayListener easy2PayListener3 = this.listener;
                if (easy2PayListener3 != null) {
                    easy2PayListener3.onError(this.pgDialog.isShowing() ? this.pgDialog.getPtxId() : null, this.pgDialog.isShowing() ? this.pgDialog.getUserId() : null, null, ERROR_CANNOT_CHARGING, Resource.STRING_ERROR_XML_IS_INVALID[this.langId]);
                }
            }
        }
    }

    @Override // com.android.easy2pay.Easy2PayConnectionListener
    public final void onError(String str, String[] strArr, int i, String str2) {
        if (str.equals(GET_PRICES_SESSION)) {
            this.ldDialog.dismiss();
            Easy2PayListener easy2PayListener = this.listener;
            if (easy2PayListener != null) {
                easy2PayListener.onError(strArr[0], strArr[1], null, 301, str2);
            }
        } else if (str.equals(GET_PINCODE_SESSION)) {
            this.ldDialog.dismiss();
            Easy2PayListener easy2PayListener2 = this.listener;
            if (easy2PayListener2 != null) {
                easy2PayListener2.onError(strArr[0], strArr[1], null, 302, str2);
            }
        } else if (str.equals(CHECK_CHAREGD_SESSION)) {
            String str3 = strArr[0];
            String str4 = strArr[1];
            String str5 = strArr[2];
            if (strArr[3] == null) {
                if (str3.equals(this.pgDialog.getPtxId())) {
                    if (this.pgDialog.isShowing()) {
                        this.progressIndex += 10;
                        this.pgDialog.setWaitProgress(this.progressIndex);
                        if (this.progressIndex < this.progressMax) {
                            try {
                                Thread.sleep(10000L);
                            } catch (Exception unused) {
                            }
                            checkCharged(str3, str4, str5);
                            return;
                        }
                        Easy2PayListener easy2PayListener3 = this.listener;
                        if (easy2PayListener3 != null) {
                            easy2PayListener3.onEvent(str3, str4, str5, 203, Resource.STRING_EVENT_BACKGROUND_CHARGING[this.langId]);
                        }
                        this.pgDialog.dismiss();
                        showAlert(Resource.STRING_ALERT_BACKGROUND_CHARGING[this.langId]);
                        backgroundCheckCharged(str3, str4, str5);
                        return;
                    }
                    backgroundCheckCharged(str3, str4, str5);
                    return;
                }
                backgroundCheckCharged(str3, str4, str5);
                return;
            }
            Easy2PayListener easy2PayListener4 = this.listener;
            if (easy2PayListener4 != null) {
                easy2PayListener4.onEvent(str3, str4, str5, 202, Resource.STRING_EVENT_EASY2PAY_IS_CHARGING[this.langId]);
            }
        }
    }

    @Override // com.android.easy2pay.Easy2PayDialogListener
    public final void onOK(String str, String str2, String str3, String str4) {
        this.ldDialog.show();
        if (str2 == null) {
            str2 = "";
        }
        String generate = MD5Factory.generate(String.valueOf(stringSortAndCat(new String[]{"p_txid", AccessToken.USER_ID_KEY, "price_id", "partner_id", "msisdn"}, new String[]{str, str2, str3, this.partnerId, ""})) + this.secretKey);
        Easy2PayConnection easy2PayConnection = new Easy2PayConnection(this);
        String[] strArr = {str, str2, str4};
        easy2PayConnection.get(GET_PINCODE_SESSION, strArr, "http://api.easy2pay.co/add-on/init.php?p_txid=" + str + "&user_id=" + str2 + "&price_id=" + str3 + "&partner_id=" + this.partnerId + "&msisdn=&sig=" + generate);
    }

    @Override // com.android.easy2pay.Easy2PayDialogListener
    public final void onCancel(String str, String str2) {
        Easy2PayListener easy2PayListener = this.listener;
        if (easy2PayListener != null) {
            easy2PayListener.onEvent(str, str2, null, 201, Resource.STRING_EVENT_USER_CANCEL_CHARGE[this.langId]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAlert(final String str) {
        this.handler.post(new Runnable() { // from class: com.android.easy2pay.Easy2Pay.4
            @Override // java.lang.Runnable
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Easy2Pay.this.context);
                builder.setCancelable(false);
                builder.setMessage(str).setPositiveButton(Resource.TXT_BUTTON_OK[Easy2Pay.this.langId], (DialogInterface.OnClickListener) null).show();
            }
        });
    }

    private void backgroundCheckCharged(String str, String str2, String str3) {
        try {
            Thread.sleep(10000L);
        } catch (Exception unused) {
        }
        checkCharged(str, str2, str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkCharged(String str, String str2, String str3) {
        checkCharged(str, str2, str3, true);
    }

    private void checkCharged(String str, String str2, String str3, boolean z) {
        String sb;
        this.isBackgroudChecking = true;
        String generate = MD5Factory.generate(String.valueOf(str3) + this.secretKey);
        Easy2PayConnection easy2PayConnection = new Easy2PayConnection(this);
        String[] strArr = new String[4];
        strArr[0] = str;
        strArr[1] = str2;
        strArr[2] = str3;
        if (z) {
            sb = null;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(z);
            sb = sb2.toString();
        }
        strArr[3] = sb;
        easy2PayConnection.get(CHECK_CHAREGD_SESSION, strArr, "http://api.easy2pay.co/add-on/inquiry.php?txid=" + str3 + "&sig=" + generate);
    }

    @Override // com.android.easy2pay.Easy2PayDialogListener
    public void onActivityPause() {
        if (this.pgDialog.isShowing()) {
            this.pgDialog.setWaitProgress(this.progressMax);
            this.pgDialog.dismiss();
            showAlert(Resource.STRING_ALERT_BACKGROUND_CHARGING[this.langId]);
            String str = this.ptxId;
            checkCharging(str, this.userId, str);
        }
    }

    /* loaded from: classes3.dex */
    private class SMSBroadcastReceiver extends BroadcastReceiver {
        private SMSBroadcastReceiver() {
        }

        /* synthetic */ SMSBroadcastReceiver(Easy2Pay easy2Pay, SMSBroadcastReceiver sMSBroadcastReceiver) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String stringExtra = intent.getStringExtra("ptxId");
            String stringExtra2 = intent.getStringExtra("txId");
            String stringExtra3 = intent.getStringExtra("userId");
            if (getResultCode() != -1) {
                if (Easy2Pay.this.listener != null) {
                    Easy2Pay.this.listener.onError(stringExtra, stringExtra3, stringExtra2, 305, "Can not send message because no sevice, null PDU, radio off or etc.");
                }
                Easy2Pay.this.pgDialog.dismiss();
                Easy2Pay.this.showAlert(Resource.STRING_ALERT_CANNOT_SEND_SMS[Easy2Pay.this.langId]);
            } else {
                Easy2Pay.this.progressIndex += 10;
                Easy2Pay.this.pgDialog.setWaitProgress(Easy2Pay.this.progressIndex);
                Easy2Pay.this.checkCharged(stringExtra, stringExtra3, stringExtra2);
            }
            context.unregisterReceiver(Easy2Pay.this.smsListener);
        }
    }
}
