package com.facebook.login.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.appcompat.content.res.AppCompatResources;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.InternalAppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.LoginTargetApp;
import com.facebook.login.R;
import com.facebook.login.widget.ToolTipPopup;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/* loaded from: classes3.dex */
public class LoginButton extends FacebookButtonBase {
    private static final int MAX_BUTTON_TRANSPARENCY = 255;
    private static final int MIN_BUTTON_TRANSPARENCY = 0;
    private static final String TAG = LoginButton.class.getName();
    private AccessTokenTracker accessTokenTracker;
    private ActivityResultLauncher<Collection<? extends String>> androidXLoginCaller;
    private CallbackManager callbackManager;
    private boolean confirmLogout;
    private Float customButtonRadius;
    private int customButtonTransparency;
    private final String loggerID;
    private String loginLogoutEventName;
    private LoginManager loginManager;
    private String loginText;
    private String logoutText;
    protected LoginButtonProperties properties;
    private boolean toolTipChecked;
    private long toolTipDisplayTime;
    private ToolTipMode toolTipMode;
    private ToolTipPopup toolTipPopup;
    private ToolTipPopup.Style toolTipStyle;

    /* loaded from: classes3.dex */
    public enum ToolTipMode {
        AUTOMATIC(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC, 0),
        DISPLAY_ALWAYS("display_always", 1),
        NEVER_DISPLAY("never_display", 2);
        
        public static ToolTipMode DEFAULT = AUTOMATIC;
        private int intValue;
        private String stringValue;

        public static ToolTipMode fromInt(int i) {
            ToolTipMode[] values;
            for (ToolTipMode toolTipMode : values()) {
                if (toolTipMode.getValue() == i) {
                    return toolTipMode;
                }
            }
            return null;
        }

        ToolTipMode(String str, int i) {
            this.stringValue = str;
            this.intValue = i;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.stringValue;
        }

        public int getValue() {
            return this.intValue;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class LoginButtonProperties {
        private String messengerPageId;
        private boolean resetMessengerState;
        private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
        private List<String> permissions = Collections.emptyList();
        private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
        private String authType = ServerProtocol.DIALOG_REREQUEST_AUTH_TYPE;
        private LoginTargetApp targetApp = LoginTargetApp.FACEBOOK;
        private boolean shouldSkipAccountDeduplication = false;

        LoginButtonProperties() {
        }

        public void setDefaultAudience(DefaultAudience defaultAudience) {
            this.defaultAudience = defaultAudience;
        }

        public DefaultAudience getDefaultAudience() {
            return this.defaultAudience;
        }

        public void setPermissions(List<String> list) {
            this.permissions = list;
        }

        List<String> getPermissions() {
            return this.permissions;
        }

        public void clearPermissions() {
            this.permissions = null;
        }

        public void setLoginBehavior(LoginBehavior loginBehavior) {
            this.loginBehavior = loginBehavior;
        }

        public LoginBehavior getLoginBehavior() {
            return this.loginBehavior;
        }

        public void setLoginTargetApp(LoginTargetApp loginTargetApp) {
            this.targetApp = loginTargetApp;
        }

        public LoginTargetApp getLoginTargetApp() {
            return this.targetApp;
        }

        public String getAuthType() {
            return this.authType;
        }

        public void setAuthType(String str) {
            this.authType = str;
        }

        protected void setShouldSkipAccountDeduplication(boolean z) {
            this.shouldSkipAccountDeduplication = z;
        }

        public boolean getShouldSkipAccountDeduplication() {
            return this.shouldSkipAccountDeduplication;
        }

        public String getMessengerPageId() {
            return this.messengerPageId;
        }

        public void setMessengerPageId(String str) {
            this.messengerPageId = str;
        }

        public boolean getResetMessengerState() {
            return this.resetMessengerState;
        }

        public void setResetMessengerState(boolean z) {
            this.resetMessengerState = z;
        }
    }

    public LoginButton(Context context) {
        this(context, null, 0, 0, AnalyticsEvents.EVENT_LOGIN_BUTTON_CREATE, AnalyticsEvents.EVENT_LOGIN_BUTTON_DID_TAP);
    }

    public LoginButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, AnalyticsEvents.EVENT_LOGIN_BUTTON_CREATE, AnalyticsEvents.EVENT_LOGIN_BUTTON_DID_TAP);
    }

    public LoginButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, AnalyticsEvents.EVENT_LOGIN_BUTTON_CREATE, AnalyticsEvents.EVENT_LOGIN_BUTTON_DID_TAP);
    }

    protected LoginButton(Context context, AttributeSet attributeSet, int i, int i2, String str, String str2) {
        super(context, attributeSet, i, i2, str, str2);
        this.properties = new LoginButtonProperties();
        this.loginLogoutEventName = AnalyticsEvents.EVENT_LOGIN_VIEW_USAGE;
        this.toolTipStyle = ToolTipPopup.Style.BLUE;
        this.toolTipDisplayTime = ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME;
        this.customButtonTransparency = 255;
        this.loggerID = UUID.randomUUID().toString();
        this.callbackManager = null;
        this.androidXLoginCaller = null;
    }

    public void setLoginText(String str) {
        this.loginText = str;
        setButtonText();
    }

    public void setLogoutText(String str) {
        this.logoutText = str;
        setButtonText();
    }

    public void setDefaultAudience(DefaultAudience defaultAudience) {
        this.properties.setDefaultAudience(defaultAudience);
    }

    public DefaultAudience getDefaultAudience() {
        return this.properties.getDefaultAudience();
    }

    public void setReadPermissions(List<String> list) {
        this.properties.setPermissions(list);
    }

    public void setReadPermissions(String... strArr) {
        this.properties.setPermissions(Arrays.asList(strArr));
    }

    public void setPermissions(List<String> list) {
        this.properties.setPermissions(list);
    }

    public void setPermissions(String... strArr) {
        this.properties.setPermissions(Arrays.asList(strArr));
    }

    public void setPublishPermissions(List<String> list) {
        this.properties.setPermissions(list);
    }

    public void setPublishPermissions(String... strArr) {
        this.properties.setPermissions(Arrays.asList(strArr));
    }

    public void clearPermissions() {
        this.properties.clearPermissions();
    }

    public void setLoginBehavior(LoginBehavior loginBehavior) {
        this.properties.setLoginBehavior(loginBehavior);
    }

    public LoginBehavior getLoginBehavior() {
        return this.properties.getLoginBehavior();
    }

    public void setLoginTargetApp(LoginTargetApp loginTargetApp) {
        this.properties.setLoginTargetApp(loginTargetApp);
    }

    public LoginTargetApp getLoginTargetApp() {
        return this.properties.getLoginTargetApp();
    }

    public String getAuthType() {
        return this.properties.getAuthType();
    }

    public String getMessengerPageId() {
        return this.properties.getMessengerPageId();
    }

    public boolean getResetMessengerState() {
        return this.properties.getResetMessengerState();
    }

    public void setAuthType(String str) {
        this.properties.setAuthType(str);
    }

    public void setMessengerPageId(String str) {
        this.properties.setMessengerPageId(str);
    }

    public void setResetMessengerState(boolean z) {
        this.properties.setResetMessengerState(z);
    }

    public void setToolTipStyle(ToolTipPopup.Style style) {
        this.toolTipStyle = style;
    }

    public void setToolTipMode(ToolTipMode toolTipMode) {
        this.toolTipMode = toolTipMode;
    }

    public ToolTipMode getToolTipMode() {
        return this.toolTipMode;
    }

    public void setToolTipDisplayTime(long j) {
        this.toolTipDisplayTime = j;
    }

    public boolean getShouldSkipAccountDeduplication() {
        return this.properties.getShouldSkipAccountDeduplication();
    }

    public long getToolTipDisplayTime() {
        return this.toolTipDisplayTime;
    }

    public String getLoggerID() {
        return this.loggerID;
    }

    public void dismissToolTip() {
        ToolTipPopup toolTipPopup = this.toolTipPopup;
        if (toolTipPopup != null) {
            toolTipPopup.dismiss();
            this.toolTipPopup = null;
        }
    }

    public void registerCallback(CallbackManager callbackManager, FacebookCallback<LoginResult> facebookCallback) {
        getLoginManager().registerCallback(callbackManager, facebookCallback);
        CallbackManager callbackManager2 = this.callbackManager;
        if (callbackManager2 == null) {
            this.callbackManager = callbackManager;
        } else if (callbackManager2 != callbackManager) {
            Log.w(TAG, "You're registering a callback on the one Facebook login button with two different callback managers. It's almost wrong and may cause unexpected results. Only the first callback manager will be used for handling activity result with androidx.");
        }
    }

    public void unregisterCallback(CallbackManager callbackManager) {
        getLoginManager().unregisterCallback(callbackManager);
    }

    public CallbackManager getCallbackManager() {
        return this.callbackManager;
    }

    protected int getLoginButtonContinueLabel() {
        return R.string.com_facebook_loginview_log_in_button_continue;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.FacebookButtonBase, android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getContext() instanceof ActivityResultRegistryOwner) {
            this.androidXLoginCaller = ((ActivityResultRegistryOwner) getContext()).getActivityResultRegistry().register("facebook-login", getLoginManager().createLogInActivityResultContract(this.callbackManager, this.loggerID), new ActivityResultCallback<CallbackManager.ActivityResultParameters>() { // from class: com.facebook.login.widget.LoginButton.1
                @Override // androidx.activity.result.ActivityResultCallback
                public void onActivityResult(CallbackManager.ActivityResultParameters activityResultParameters) {
                }
            });
        }
        AccessTokenTracker accessTokenTracker = this.accessTokenTracker;
        if (accessTokenTracker == null || accessTokenTracker.isTracking()) {
            return;
        }
        this.accessTokenTracker.startTracking();
        setButtonText();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.FacebookButtonBase, android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.toolTipChecked || isInEditMode()) {
            return;
        }
        this.toolTipChecked = true;
        checkToolTipSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showToolTipPerSettings(FetchedAppSettings fetchedAppSettings) {
        if (fetchedAppSettings != null && fetchedAppSettings.getNuxEnabled() && getVisibility() == 0) {
            displayToolTip(fetchedAppSettings.getNuxContent());
        }
    }

    private void displayToolTip(String str) {
        this.toolTipPopup = new ToolTipPopup(str, this);
        this.toolTipPopup.setStyle(this.toolTipStyle);
        this.toolTipPopup.setNuxDisplayTime(this.toolTipDisplayTime);
        this.toolTipPopup.show();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.facebook.login.widget.LoginButton$4  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode = new int[ToolTipMode.values().length];

        static {
            try {
                $SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode[ToolTipMode.AUTOMATIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode[ToolTipMode.DISPLAY_ALWAYS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode[ToolTipMode.NEVER_DISPLAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void checkToolTipSettings() {
        int i = AnonymousClass4.$SwitchMap$com$facebook$login$widget$LoginButton$ToolTipMode[this.toolTipMode.ordinal()];
        if (i == 1) {
            final String metadataApplicationId = Utility.getMetadataApplicationId(getContext());
            FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.login.widget.LoginButton.2
                @Override // java.lang.Runnable
                public void run() {
                    final FetchedAppSettings queryAppSettings = FetchedAppSettingsManager.queryAppSettings(metadataApplicationId, false);
                    LoginButton.this.getActivity().runOnUiThread(new Runnable() { // from class: com.facebook.login.widget.LoginButton.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            LoginButton.this.showToolTipPerSettings(queryAppSettings);
                        }
                    });
                }
            });
        } else if (i != 2) {
        } else {
            displayToolTip(getResources().getString(R.string.com_facebook_tooltip_default));
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setButtonText();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActivityResultLauncher<Collection<? extends String>> activityResultLauncher = this.androidXLoginCaller;
        if (activityResultLauncher != null) {
            activityResultLauncher.unregister();
        }
        AccessTokenTracker accessTokenTracker = this.accessTokenTracker;
        if (accessTokenTracker != null) {
            accessTokenTracker.stopTracking();
        }
        dismissToolTip();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i != 0) {
            dismissToolTip();
        }
    }

    List<String> getPermissions() {
        return this.properties.getPermissions();
    }

    void setProperties(LoginButtonProperties loginButtonProperties) {
        this.properties = loginButtonProperties;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.FacebookButtonBase
    public void configureButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super.configureButton(context, attributeSet, i, i2);
        setInternalOnClickListener(getNewLoginClickListener());
        parseLoginButtonAttributes(context, attributeSet, i, i2);
        if (isInEditMode()) {
            setBackgroundColor(getResources().getColor(com.facebook.common.R.color.com_facebook_blue));
            this.loginText = "Continue with Facebook";
        } else {
            this.accessTokenTracker = new AccessTokenTracker() { // from class: com.facebook.login.widget.LoginButton.3
                @Override // com.facebook.AccessTokenTracker
                protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                    LoginButton.this.setButtonText();
                    LoginButton.this.setButtonIcon();
                }
            };
        }
        setButtonText();
        setButtonRadius();
        setButtonTransparency();
        setButtonIcon();
    }

    protected LoginClickListener getNewLoginClickListener() {
        return new LoginClickListener();
    }

    @Override // com.facebook.FacebookButtonBase
    protected int getDefaultStyleResource() {
        return R.style.com_facebook_loginview_default_style;
    }

    protected void parseLoginButtonAttributes(Context context, AttributeSet attributeSet, int i, int i2) {
        this.toolTipMode = ToolTipMode.DEFAULT;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.com_facebook_login_view, i, i2);
        try {
            this.confirmLogout = obtainStyledAttributes.getBoolean(R.styleable.com_facebook_login_view_com_facebook_confirm_logout, true);
            this.loginText = obtainStyledAttributes.getString(R.styleable.com_facebook_login_view_com_facebook_login_text);
            this.logoutText = obtainStyledAttributes.getString(R.styleable.com_facebook_login_view_com_facebook_logout_text);
            this.toolTipMode = ToolTipMode.fromInt(obtainStyledAttributes.getInt(R.styleable.com_facebook_login_view_com_facebook_tooltip_mode, ToolTipMode.DEFAULT.getValue()));
            if (obtainStyledAttributes.hasValue(R.styleable.com_facebook_login_view_com_facebook_login_button_radius)) {
                this.customButtonRadius = Float.valueOf(obtainStyledAttributes.getDimension(R.styleable.com_facebook_login_view_com_facebook_login_button_radius, 0.0f));
            }
            this.customButtonTransparency = obtainStyledAttributes.getInteger(R.styleable.com_facebook_login_view_com_facebook_login_button_transparency, 255);
            if (this.customButtonTransparency < 0) {
                this.customButtonTransparency = 0;
            }
            if (this.customButtonTransparency > 255) {
                this.customButtonTransparency = 255;
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        int compoundPaddingTop = getCompoundPaddingTop() + ((int) Math.ceil(Math.abs(fontMetrics.top) + Math.abs(fontMetrics.bottom))) + getCompoundPaddingBottom();
        Resources resources = getResources();
        int loginButtonWidth = getLoginButtonWidth(i);
        String str = this.logoutText;
        if (str == null) {
            str = resources.getString(R.string.com_facebook_loginview_log_out_button);
        }
        setMeasuredDimension(resolveSize(Math.max(loginButtonWidth, measureButtonWidth(str)), i), compoundPaddingTop);
    }

    protected int getLoginButtonWidth(int i) {
        Resources resources = getResources();
        String str = this.loginText;
        if (str == null) {
            str = resources.getString(R.string.com_facebook_loginview_log_in_button_continue);
            int measureButtonWidth = measureButtonWidth(str);
            if (resolveSize(measureButtonWidth, i) < measureButtonWidth) {
                str = resources.getString(R.string.com_facebook_loginview_log_in_button);
            }
        }
        return measureButtonWidth(str);
    }

    private int measureButtonWidth(String str) {
        return getCompoundPaddingLeft() + getCompoundDrawablePadding() + measureTextWidth(str) + getCompoundPaddingRight();
    }

    protected void setButtonText() {
        Resources resources = getResources();
        if (!isInEditMode() && AccessToken.isCurrentAccessTokenActive()) {
            String str = this.logoutText;
            if (str == null) {
                str = resources.getString(R.string.com_facebook_loginview_log_out_button);
            }
            setText(str);
            return;
        }
        String str2 = this.loginText;
        if (str2 != null) {
            setText(str2);
            return;
        }
        String string = resources.getString(getLoginButtonContinueLabel());
        int width = getWidth();
        if (width != 0 && measureButtonWidth(string) > width) {
            string = resources.getString(R.string.com_facebook_loginview_log_in_button);
        }
        setText(string);
    }

    protected void setButtonIcon() {
        setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getContext(), com.facebook.common.R.drawable.com_facebook_button_icon), (Drawable) null, (Drawable) null, (Drawable) null);
    }

    protected void setButtonRadius() {
        if (this.customButtonRadius == null) {
            return;
        }
        Drawable background = getBackground();
        if (Build.VERSION.SDK_INT >= 29 && (background instanceof StateListDrawable)) {
            StateListDrawable stateListDrawable = (StateListDrawable) background;
            for (int i = 0; i < stateListDrawable.getStateCount(); i++) {
                GradientDrawable gradientDrawable = (GradientDrawable) stateListDrawable.getStateDrawable(i);
                if (gradientDrawable != null) {
                    gradientDrawable.setCornerRadius(this.customButtonRadius.floatValue());
                }
            }
        }
        if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setCornerRadius(this.customButtonRadius.floatValue());
        }
    }

    protected void setButtonTransparency() {
        getBackground().setAlpha(this.customButtonTransparency);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.FacebookButtonBase
    public int getDefaultRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
    }

    LoginManager getLoginManager() {
        if (this.loginManager == null) {
            this.loginManager = LoginManager.getInstance();
        }
        return this.loginManager;
    }

    void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes3.dex */
    public class LoginClickListener implements View.OnClickListener {
        protected boolean isFamilyLogin() {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public LoginClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            LoginButton.this.callExternalOnClickListener(view);
            AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
            if (AccessToken.isCurrentAccessTokenActive()) {
                performLogout(LoginButton.this.getContext());
            } else {
                performLogin();
            }
            InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(LoginButton.this.getContext());
            Bundle bundle = new Bundle();
            bundle.putInt("logging_in", currentAccessToken != null ? 0 : 1);
            bundle.putInt("access_token_expired", AccessToken.isCurrentAccessTokenActive() ? 1 : 0);
            internalAppEventsLogger.logEventImplicitly(LoginButton.this.loginLogoutEventName, bundle);
        }

        protected void performLogin() {
            LoginManager loginManager = getLoginManager();
            if (LoginButton.this.androidXLoginCaller != null) {
                ((LoginManager.FacebookLoginActivityResultContract) LoginButton.this.androidXLoginCaller.getContract()).setCallbackManager(LoginButton.this.callbackManager != null ? LoginButton.this.callbackManager : new CallbackManagerImpl());
                LoginButton.this.androidXLoginCaller.launch(LoginButton.this.properties.permissions);
            } else if (LoginButton.this.getFragment() != null) {
                loginManager.logIn(LoginButton.this.getFragment(), LoginButton.this.properties.permissions, LoginButton.this.getLoggerID());
            } else if (LoginButton.this.getNativeFragment() == null) {
                loginManager.logIn(LoginButton.this.getActivity(), LoginButton.this.properties.permissions, LoginButton.this.getLoggerID());
            } else {
                loginManager.logIn(LoginButton.this.getNativeFragment(), LoginButton.this.properties.permissions, LoginButton.this.getLoggerID());
            }
        }

        protected void performLogout(Context context) {
            String string;
            final LoginManager loginManager = getLoginManager();
            if (LoginButton.this.confirmLogout) {
                String string2 = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_log_out_action);
                String string3 = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_cancel_action);
                Profile currentProfile = Profile.getCurrentProfile();
                if (currentProfile != null && currentProfile.getName() != null) {
                    string = String.format(LoginButton.this.getResources().getString(R.string.com_facebook_loginview_logged_in_as), currentProfile.getName());
                } else {
                    string = LoginButton.this.getResources().getString(R.string.com_facebook_loginview_logged_in_using_facebook);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(string).setCancelable(true).setPositiveButton(string2, new DialogInterface.OnClickListener() { // from class: com.facebook.login.widget.LoginButton.LoginClickListener.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loginManager.logOut();
                    }
                }).setNegativeButton(string3, (DialogInterface.OnClickListener) null);
                builder.create().show();
                return;
            }
            loginManager.logOut();
        }

        protected LoginManager getLoginManager() {
            LoginManager loginManager = LoginManager.getInstance();
            loginManager.setDefaultAudience(LoginButton.this.getDefaultAudience());
            loginManager.setLoginBehavior(LoginButton.this.getLoginBehavior());
            loginManager.setLoginTargetApp(getLoginTargetApp());
            loginManager.setAuthType(LoginButton.this.getAuthType());
            loginManager.setFamilyLogin(isFamilyLogin());
            loginManager.setShouldSkipAccountDeduplication(LoginButton.this.getShouldSkipAccountDeduplication());
            loginManager.setMessengerPageId(LoginButton.this.getMessengerPageId());
            loginManager.setResetMessengerState(LoginButton.this.getResetMessengerState());
            return loginManager;
        }

        protected LoginTargetApp getLoginTargetApp() {
            return LoginTargetApp.FACEBOOK;
        }
    }
}
