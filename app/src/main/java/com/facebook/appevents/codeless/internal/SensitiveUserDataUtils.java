package com.facebook.appevents.codeless.internal;

import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.text.CharsKt;
import kotlin.text.Regex;

/* compiled from: SensitiveUserDataUtils.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\f\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\rH\u0007¨\u0006\u000e"}, d2 = {"Lcom/facebook/appevents/codeless/internal/SensitiveUserDataUtils;", "", "()V", "isCreditCard", "", ViewHierarchyConstants.VIEW_KEY, "Landroid/widget/TextView;", "isEmail", "isPassword", "isPersonName", "isPhoneNumber", "isPostalAddress", "isSensitiveUserData", "Landroid/view/View;", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class SensitiveUserDataUtils {
    public static final SensitiveUserDataUtils INSTANCE = new SensitiveUserDataUtils();

    private SensitiveUserDataUtils() {
    }

    @JvmStatic
    public static final boolean isSensitiveUserData(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            return INSTANCE.isPassword(textView) || INSTANCE.isCreditCard(textView) || INSTANCE.isPersonName(textView) || INSTANCE.isPostalAddress(textView) || INSTANCE.isPhoneNumber(textView) || INSTANCE.isEmail(textView);
        }
        return false;
    }

    private final boolean isPassword(TextView textView) {
        if (textView.getInputType() == 128) {
            return true;
        }
        return textView.getTransformationMethod() instanceof PasswordTransformationMethod;
    }

    private final boolean isEmail(TextView textView) {
        if (textView.getInputType() == 32) {
            return true;
        }
        ViewHierarchy viewHierarchy = ViewHierarchy.INSTANCE;
        String textOfView = ViewHierarchy.getTextOfView(textView);
        if (textOfView != null) {
            String str = textOfView;
            if (str.length() == 0) {
                return false;
            }
            return Patterns.EMAIL_ADDRESS.matcher(str).matches();
        }
        return false;
    }

    private final boolean isPersonName(TextView textView) {
        return textView.getInputType() == 96;
    }

    private final boolean isPostalAddress(TextView textView) {
        return textView.getInputType() == 112;
    }

    private final boolean isPhoneNumber(TextView textView) {
        return textView.getInputType() == 3;
    }

    private final boolean isCreditCard(TextView textView) {
        int i;
        ViewHierarchy viewHierarchy = ViewHierarchy.INSTANCE;
        String replace = new Regex("\\s").replace(ViewHierarchy.getTextOfView(textView), "");
        int length = replace.length();
        if (length < 12 || length > 19) {
            return false;
        }
        int i2 = length - 1;
        if (i2 >= 0) {
            boolean z = false;
            i = 0;
            while (true) {
                int i3 = i2 - 1;
                char charAt = replace.charAt(i2);
                if (!Character.isDigit(charAt)) {
                    return false;
                }
                int digitToInt = CharsKt.digitToInt(charAt);
                if (z && (digitToInt = digitToInt * 2) > 9) {
                    digitToInt = (digitToInt % 10) + 1;
                }
                i += digitToInt;
                z = !z;
                if (i3 < 0) {
                    break;
                }
                i2 = i3;
            }
        } else {
            i = 0;
        }
        return i % 10 == 0;
    }
}
