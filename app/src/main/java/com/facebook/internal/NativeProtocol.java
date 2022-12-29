package com.facebook.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.ondeviceprocessing.RemoteServiceWrapper;
import com.facebook.bolts.AppLinks;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginTargetApp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: NativeProtocol.kt */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b;\n\u0002\u0010\u0011\n\u0002\u0010\b\n\u0002\b:\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\f\bÇ\u0002\u0018\u00002\u00020\u0001:\u000eÔ\u0001Õ\u0001Ö\u0001×\u0001Ø\u0001Ù\u0001Ú\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0083\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020~0}0|H\u0002J\u000f\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u00020~0}H\u0002J\u000f\u0010\u0085\u0001\u001a\b\u0012\u0004\u0012\u00020~0}H\u0002J\u0013\u0010\u0086\u0001\u001a\u00030\u0087\u00012\u0007\u0010\u0088\u0001\u001a\u00020~H\u0002J.\u0010\u0089\u0001\u001a\u00020A2\u0010\u0010\u008a\u0001\u001a\u000b\u0012\u0004\u0012\u00020A\u0018\u00010\u008b\u00012\u0007\u0010\u008c\u0001\u001a\u00020A2\b\u0010\u008d\u0001\u001a\u00030\u008e\u0001H\u0007J\u0018\u0010\u008f\u0001\u001a\u0005\u0018\u00010\u0090\u00012\n\u0010\u0091\u0001\u001a\u0005\u0018\u00010\u0092\u0001H\u0007J\u0093\u0001\u0010\u0093\u0001\u001a\u0005\u0018\u00010\u0094\u00012\b\u0010\u0095\u0001\u001a\u00030\u0096\u00012\u0007\u0010\u0097\u0001\u001a\u00020\u00042\u0010\u0010\u0098\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0099\u00012\u0007\u0010\u009a\u0001\u001a\u00020\u00042\b\u0010\u009b\u0001\u001a\u00030\u009c\u00012\b\u0010\u009d\u0001\u001a\u00030\u009c\u00012\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0007\u0010 \u0001\u001a\u00020\u00042\u0007\u0010¡\u0001\u001a\u00020\u00042\t\u0010¢\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010£\u0001\u001a\u00030\u009c\u00012\b\u0010¤\u0001\u001a\u00030\u009c\u00012\b\u0010¥\u0001\u001a\u00030\u009c\u0001H\u0007J½\u0001\u0010¦\u0001\u001a\u0005\u0018\u00010\u0094\u00012\u0007\u0010\u0088\u0001\u001a\u00020~2\u0007\u0010\u0097\u0001\u001a\u00020\u00042\u0010\u0010\u0098\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0099\u00012\u0007\u0010\u009a\u0001\u001a\u00020\u00042\b\u0010\u009d\u0001\u001a\u00030\u009c\u00012\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0007\u0010 \u0001\u001a\u00020\u00042\u0007\u0010¡\u0001\u001a\u00020\u00042\b\u0010§\u0001\u001a\u00030\u009c\u00012\t\u0010¢\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010£\u0001\u001a\u00030\u009c\u00012\b\u0010¨\u0001\u001a\u00030©\u00012\b\u0010¤\u0001\u001a\u00030\u009c\u00012\b\u0010¥\u0001\u001a\u00030\u009c\u00012\t\u0010ª\u0001\u001a\u0004\u0018\u00010\u00042\t\u0010«\u0001\u001a\u0004\u0018\u00010\u00042\t\u0010¬\u0001\u001a\u0004\u0018\u00010\u0004H\u0002JD\u0010\u00ad\u0001\u001a\u0005\u0018\u00010\u0094\u00012\b\u0010\u0095\u0001\u001a\u00030\u0096\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u00042\t\u0010¯\u0001\u001a\u0004\u0018\u00010\u00042\n\u0010°\u0001\u001a\u0005\u0018\u00010±\u00012\n\u0010²\u0001\u001a\u0005\u0018\u00010\u0090\u0001H\u0007J\u0016\u0010³\u0001\u001a\u0005\u0018\u00010\u0094\u00012\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0007J.\u0010´\u0001\u001a\u0005\u0018\u00010\u0094\u00012\b\u0010µ\u0001\u001a\u00030\u0094\u00012\n\u0010¶\u0001\u001a\u0005\u0018\u00010\u0090\u00012\n\u0010·\u0001\u001a\u0005\u0018\u00010\u0092\u0001H\u0007JÆ\u0001\u0010¸\u0001\u001a\t\u0012\u0005\u0012\u00030\u0094\u00010}2\n\u0010\u0095\u0001\u001a\u0005\u0018\u00010\u0096\u00012\u0007\u0010\u0097\u0001\u001a\u00020\u00042\u0010\u0010\u0098\u0001\u001a\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0099\u00012\u0007\u0010\u009a\u0001\u001a\u00020\u00042\b\u0010\u009b\u0001\u001a\u00030\u009c\u00012\b\u0010\u009d\u0001\u001a\u00030\u009c\u00012\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0007\u0010 \u0001\u001a\u00020\u00042\u0007\u0010¡\u0001\u001a\u00020\u00042\b\u0010§\u0001\u001a\u00030\u009c\u00012\t\u0010¢\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010£\u0001\u001a\u00030\u009c\u00012\b\u0010¤\u0001\u001a\u00030\u009c\u00012\b\u0010¥\u0001\u001a\u00030\u009c\u00012\t\u0010ª\u0001\u001a\u0004\u0018\u00010\u00042\t\u0010«\u0001\u001a\u0004\u0018\u00010\u00042\u000b\b\u0002\u0010¬\u0001\u001a\u0004\u0018\u00010\u0004H\u0007J\u0019\u0010¹\u0001\u001a\t\u0012\u0004\u0012\u00020A0\u008b\u00012\u0007\u0010\u0088\u0001\u001a\u00020~H\u0002J\u0016\u0010º\u0001\u001a\u0005\u0018\u00010\u0090\u00012\b\u0010»\u0001\u001a\u00030\u0094\u0001H\u0007J\u0018\u0010¼\u0001\u001a\u0005\u0018\u00010½\u00012\n\u0010»\u0001\u001a\u0005\u0018\u00010\u0094\u0001H\u0007J\u0016\u0010¾\u0001\u001a\u0005\u0018\u00010\u0090\u00012\b\u0010¿\u0001\u001a\u00030\u0094\u0001H\u0007J\u0018\u0010À\u0001\u001a\u0005\u0018\u00010\u0092\u00012\n\u0010Á\u0001\u001a\u0005\u0018\u00010\u0090\u0001H\u0007J\u001d\u0010Â\u0001\u001a\u00030±\u00012\u0007\u0010¯\u0001\u001a\u00020\u00042\b\u0010\u008d\u0001\u001a\u00030\u008e\u0001H\u0007J%\u0010Ã\u0001\u001a\u00030±\u00012\u000f\u0010Ä\u0001\u001a\n\u0012\u0004\u0012\u00020~\u0018\u00010}2\b\u0010\u008d\u0001\u001a\u00030\u008e\u0001H\u0002J\u0012\u0010Å\u0001\u001a\u00020A2\u0007\u0010Æ\u0001\u001a\u00020AH\u0007J\t\u0010Ç\u0001\u001a\u00020AH\u0007J\u0016\u0010È\u0001\u001a\u0005\u0018\u00010\u0090\u00012\b\u0010»\u0001\u001a\u00030\u0094\u0001H\u0007J\u0013\u0010É\u0001\u001a\u00020A2\b\u0010»\u0001\u001a\u00030\u0094\u0001H\u0007J\u0016\u0010Ê\u0001\u001a\u0005\u0018\u00010\u0090\u00012\b\u0010¿\u0001\u001a\u00030\u0094\u0001H\u0007J\u0014\u0010Ë\u0001\u001a\u00030\u009c\u00012\b\u0010¿\u0001\u001a\u00030\u0094\u0001H\u0007J\u0013\u0010Ì\u0001\u001a\u00030\u009c\u00012\u0007\u0010Í\u0001\u001a\u00020AH\u0007J?\u0010Î\u0001\u001a\u00030Ï\u00012\b\u0010»\u0001\u001a\u00030\u0094\u00012\t\u0010®\u0001\u001a\u0004\u0018\u00010\u00042\t\u0010¯\u0001\u001a\u0004\u0018\u00010\u00042\u0007\u0010Í\u0001\u001a\u00020A2\n\u0010Ð\u0001\u001a\u0005\u0018\u00010\u0090\u0001H\u0007J\n\u0010Ñ\u0001\u001a\u00030Ï\u0001H\u0007J-\u0010Ò\u0001\u001a\u0005\u0018\u00010\u0094\u00012\b\u0010\u0095\u0001\u001a\u00030\u0096\u00012\n\u0010»\u0001\u001a\u0005\u0018\u00010\u0094\u00012\t\u0010\u0088\u0001\u001a\u0004\u0018\u00010~H\u0007J-\u0010Ó\u0001\u001a\u0005\u0018\u00010\u0094\u00012\b\u0010\u0095\u0001\u001a\u00030\u0096\u00012\n\u0010»\u0001\u001a\u0005\u0018\u00010\u0094\u00012\t\u0010\u0088\u0001\u001a\u0004\u0018\u00010~H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010?\u001a\b\u0012\u0004\u0012\u00020A0@X\u0082\u0004¢\u0006\u0004\n\u0002\u0010BR\u000e\u0010C\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010Y\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010[\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\\\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010]\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010^\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010d\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010e\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010f\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010g\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010h\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010i\u001a\u00020AX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010j\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010k\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010l\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010m\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010p\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010q\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010u\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010v\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010w\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010x\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010y\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010z\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R \u0010{\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020~0}0|X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020~0}X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u00020~0}X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0081\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Û\u0001"}, d2 = {"Lcom/facebook/internal/NativeProtocol;", "", "()V", "ACTION_APPINVITE_DIALOG", "", "ACTION_CAMERA_EFFECT", "ACTION_FEED_DIALOG", "ACTION_LIKE_DIALOG", "ACTION_MESSAGE_DIALOG", "ACTION_OGACTIONPUBLISH_DIALOG", "ACTION_OGMESSAGEPUBLISH_DIALOG", "ACTION_SHARE_STORY", "AUDIENCE_EVERYONE", "AUDIENCE_FRIENDS", "AUDIENCE_ME", "BRIDGE_ARG_ACTION_ID_STRING", "BRIDGE_ARG_APP_NAME_STRING", "BRIDGE_ARG_ERROR_BUNDLE", "BRIDGE_ARG_ERROR_CODE", "BRIDGE_ARG_ERROR_DESCRIPTION", "BRIDGE_ARG_ERROR_JSON", "BRIDGE_ARG_ERROR_SUBCODE", "BRIDGE_ARG_ERROR_TYPE", "CONTENT_SCHEME", "ERROR_APPLICATION_ERROR", "ERROR_NETWORK_ERROR", "ERROR_PERMISSION_DENIED", "ERROR_PROTOCOL_ERROR", "ERROR_SERVICE_DISABLED", "ERROR_UNKNOWN_ERROR", "ERROR_USER_CANCELED", "EXTRA_ACCESS_TOKEN", "EXTRA_APPLICATION_ID", "EXTRA_APPLICATION_NAME", "EXTRA_AUTHENTICATION_TOKEN", "EXTRA_DATA_ACCESS_EXPIRATION_TIME", "EXTRA_DIALOG_COMPLETE_KEY", "EXTRA_DIALOG_COMPLETION_GESTURE_KEY", "EXTRA_EXPIRES_SECONDS_SINCE_EPOCH", "EXTRA_GET_INSTALL_DATA_PACKAGE", "EXTRA_GRAPH_API_VERSION", "EXTRA_LOGGER_REF", "EXTRA_NONCE", "EXTRA_PERMISSIONS", "EXTRA_PROTOCOL_ACTION", "EXTRA_PROTOCOL_BRIDGE_ARGS", "EXTRA_PROTOCOL_CALL_ID", "EXTRA_PROTOCOL_METHOD_ARGS", "EXTRA_PROTOCOL_METHOD_RESULTS", "EXTRA_PROTOCOL_VERSION", "EXTRA_PROTOCOL_VERSIONS", "EXTRA_TOAST_DURATION_MS", "EXTRA_USER_ID", "FACEBOOK_PROXY_AUTH_ACTIVITY", "FACEBOOK_PROXY_AUTH_APP_ID_KEY", "FACEBOOK_PROXY_AUTH_E2E_KEY", "FACEBOOK_PROXY_AUTH_PERMISSIONS_KEY", "FACEBOOK_SDK_VERSION_KEY", "FACEBOOK_TOKEN_REFRESH_ACTIVITY", "IMAGE_URL_KEY", "IMAGE_USER_GENERATED_KEY", "INTENT_ACTION_PLATFORM_ACTIVITY", "INTENT_ACTION_PLATFORM_SERVICE", "KNOWN_PROTOCOL_VERSIONS", "", "", "[Ljava/lang/Integer;", "MESSAGE_GET_ACCESS_TOKEN_REPLY", "MESSAGE_GET_ACCESS_TOKEN_REQUEST", "MESSAGE_GET_AK_SEAMLESS_TOKEN_REPLY", "MESSAGE_GET_AK_SEAMLESS_TOKEN_REQUEST", "MESSAGE_GET_INSTALL_DATA_REPLY", "MESSAGE_GET_INSTALL_DATA_REQUEST", "MESSAGE_GET_LIKE_STATUS_REPLY", "MESSAGE_GET_LIKE_STATUS_REQUEST", "MESSAGE_GET_LOGIN_STATUS_REPLY", "MESSAGE_GET_LOGIN_STATUS_REQUEST", "MESSAGE_GET_PROTOCOL_VERSIONS_REPLY", "MESSAGE_GET_PROTOCOL_VERSIONS_REQUEST", "NO_PROTOCOL_AVAILABLE", "OPEN_GRAPH_CREATE_OBJECT_KEY", "PLATFORM_PROVIDER", "PLATFORM_PROVIDER_VERSIONS", "PLATFORM_PROVIDER_VERSION_COLUMN", "PROTOCOL_VERSION_20121101", "PROTOCOL_VERSION_20130502", "PROTOCOL_VERSION_20130618", "PROTOCOL_VERSION_20131024", "PROTOCOL_VERSION_20131107", "PROTOCOL_VERSION_20140204", "PROTOCOL_VERSION_20140313", "PROTOCOL_VERSION_20140324", "PROTOCOL_VERSION_20140701", "PROTOCOL_VERSION_20141001", "PROTOCOL_VERSION_20141028", "PROTOCOL_VERSION_20141107", "PROTOCOL_VERSION_20141218", "PROTOCOL_VERSION_20150401", "PROTOCOL_VERSION_20150702", "PROTOCOL_VERSION_20160327", "PROTOCOL_VERSION_20161017", "PROTOCOL_VERSION_20170213", "PROTOCOL_VERSION_20170411", "PROTOCOL_VERSION_20170417", "PROTOCOL_VERSION_20171115", "PROTOCOL_VERSION_20210906", "RESULT_ARGS_ACCESS_TOKEN", "RESULT_ARGS_DIALOG_COMPLETE_KEY", "RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY", "RESULT_ARGS_EXPIRES_SECONDS_SINCE_EPOCH", "RESULT_ARGS_GRAPH_DOMAIN", "RESULT_ARGS_PERMISSIONS", "RESULT_ARGS_SIGNED_REQUEST", "STATUS_ERROR_CODE", "STATUS_ERROR_DESCRIPTION", "STATUS_ERROR_JSON", "STATUS_ERROR_SUBCODE", "STATUS_ERROR_TYPE", "TAG", "WEB_DIALOG_ACTION", "WEB_DIALOG_IS_FALLBACK", "WEB_DIALOG_PARAMS", "WEB_DIALOG_URL", "actionToAppInfoMap", "", "", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "effectCameraAppInfoList", "facebookAppInfoList", "protocolVersionsAsyncUpdating", "Ljava/util/concurrent/atomic/AtomicBoolean;", "buildActionToAppInfoMap", "buildEffectCameraAppInfoList", "buildFacebookAppList", "buildPlatformProviderVersionURI", "Landroid/net/Uri;", "appInfo", "computeLatestAvailableVersionFromVersionSpec", "allAvailableFacebookAppVersions", "Ljava/util/TreeSet;", "latestSdkVersion", "versionSpec", "", "createBundleForException", "Landroid/os/Bundle;", "e", "Lcom/facebook/FacebookException;", "createInstagramIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "applicationId", "permissions", "", "e2e", "isRerequest", "", "isForPublish", "defaultAudience", "Lcom/facebook/login/DefaultAudience;", "clientState", "authType", "messengerPageId", "resetMessengerState", "isFamilyLogin", "shouldSkipAccountDedupe", "createNativeAppIntent", "ignoreAppSwitchToLoggedOut", "targetApp", "Lcom/facebook/login/LoginTargetApp;", "nonce", "codeChallenge", "codeChallengeMethod", "createPlatformActivityIntent", "callId", NativeProtocol.WEB_DIALOG_ACTION, "versionResult", "Lcom/facebook/internal/NativeProtocol$ProtocolVersionQueryResult;", AppLinks.KEY_NAME_EXTRAS, "createPlatformServiceIntent", "createProtocolResultIntent", "requestIntent", "results", "error", "createProxyAuthIntents", "fetchAllAvailableProtocolVersionsForAppInfo", "getBridgeArgumentsFromIntent", "intent", "getCallIdFromIntent", "Ljava/util/UUID;", "getErrorDataFromResultIntent", "resultIntent", "getExceptionFromErrorData", "errorData", "getLatestAvailableProtocolVersionForAction", "getLatestAvailableProtocolVersionForAppInfoList", "appInfoList", "getLatestAvailableProtocolVersionForService", "minimumVersion", "getLatestKnownVersion", "getMethodArgumentsFromIntent", "getProtocolVersionFromIntent", "getSuccessResultsFromIntent", "isErrorResult", "isVersionCompatibleWithBucketedIntent", "version", "setupProtocolRequestIntent", "", NativeProtocol.WEB_DIALOG_PARAMS, "updateAllAvailableProtocolVersionsAsync", "validateActivityIntent", "validateServiceIntent", "EffectTestAppInfo", "InstagramAppInfo", "KatanaAppInfo", "MessengerAppInfo", "NativeAppInfo", "ProtocolVersionQueryResult", "WakizashiAppInfo", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes3.dex */
public final class NativeProtocol {
    public static final String ACTION_APPINVITE_DIALOG = "com.facebook.platform.action.request.APPINVITES_DIALOG";
    public static final String ACTION_CAMERA_EFFECT = "com.facebook.platform.action.request.CAMERA_EFFECT";
    public static final String ACTION_FEED_DIALOG = "com.facebook.platform.action.request.FEED_DIALOG";
    public static final String ACTION_LIKE_DIALOG = "com.facebook.platform.action.request.LIKE_DIALOG";
    public static final String ACTION_MESSAGE_DIALOG = "com.facebook.platform.action.request.MESSAGE_DIALOG";
    public static final String ACTION_OGACTIONPUBLISH_DIALOG = "com.facebook.platform.action.request.OGACTIONPUBLISH_DIALOG";
    public static final String ACTION_OGMESSAGEPUBLISH_DIALOG = "com.facebook.platform.action.request.OGMESSAGEPUBLISH_DIALOG";
    public static final String ACTION_SHARE_STORY = "com.facebook.platform.action.request.SHARE_STORY";
    public static final String AUDIENCE_EVERYONE = "everyone";
    public static final String AUDIENCE_FRIENDS = "friends";
    public static final String AUDIENCE_ME = "only_me";
    public static final String BRIDGE_ARG_ACTION_ID_STRING = "action_id";
    public static final String BRIDGE_ARG_APP_NAME_STRING = "app_name";
    public static final String BRIDGE_ARG_ERROR_BUNDLE = "error";
    public static final String BRIDGE_ARG_ERROR_CODE = "error_code";
    public static final String BRIDGE_ARG_ERROR_DESCRIPTION = "error_description";
    public static final String BRIDGE_ARG_ERROR_JSON = "error_json";
    public static final String BRIDGE_ARG_ERROR_SUBCODE = "error_subcode";
    public static final String BRIDGE_ARG_ERROR_TYPE = "error_type";
    private static final String CONTENT_SCHEME = "content://";
    public static final String ERROR_APPLICATION_ERROR = "ApplicationError";
    public static final String ERROR_NETWORK_ERROR = "NetworkError";
    public static final String ERROR_PERMISSION_DENIED = "PermissionDenied";
    public static final String ERROR_PROTOCOL_ERROR = "ProtocolError";
    public static final String ERROR_SERVICE_DISABLED = "ServiceDisabled";
    public static final String ERROR_UNKNOWN_ERROR = "UnknownError";
    public static final String ERROR_USER_CANCELED = "UserCanceled";
    public static final String EXTRA_ACCESS_TOKEN = "com.facebook.platform.extra.ACCESS_TOKEN";
    public static final String EXTRA_APPLICATION_ID = "com.facebook.platform.extra.APPLICATION_ID";
    public static final String EXTRA_APPLICATION_NAME = "com.facebook.platform.extra.APPLICATION_NAME";
    public static final String EXTRA_AUTHENTICATION_TOKEN = "com.facebook.platform.extra.ID_TOKEN";
    public static final String EXTRA_DATA_ACCESS_EXPIRATION_TIME = "com.facebook.platform.extra.EXTRA_DATA_ACCESS_EXPIRATION_TIME";
    public static final String EXTRA_DIALOG_COMPLETE_KEY = "com.facebook.platform.extra.DID_COMPLETE";
    public static final String EXTRA_DIALOG_COMPLETION_GESTURE_KEY = "com.facebook.platform.extra.COMPLETION_GESTURE";
    public static final String EXTRA_EXPIRES_SECONDS_SINCE_EPOCH = "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH";
    public static final String EXTRA_GET_INSTALL_DATA_PACKAGE = "com.facebook.platform.extra.INSTALLDATA_PACKAGE";
    public static final String EXTRA_GRAPH_API_VERSION = "com.facebook.platform.extra.GRAPH_API_VERSION";
    public static final String EXTRA_LOGGER_REF = "com.facebook.platform.extra.LOGGER_REF";
    public static final String EXTRA_NONCE = "com.facebook.platform.extra.NONCE";
    public static final String EXTRA_PERMISSIONS = "com.facebook.platform.extra.PERMISSIONS";
    public static final String EXTRA_PROTOCOL_ACTION = "com.facebook.platform.protocol.PROTOCOL_ACTION";
    public static final String EXTRA_PROTOCOL_BRIDGE_ARGS = "com.facebook.platform.protocol.BRIDGE_ARGS";
    public static final String EXTRA_PROTOCOL_CALL_ID = "com.facebook.platform.protocol.CALL_ID";
    public static final String EXTRA_PROTOCOL_METHOD_ARGS = "com.facebook.platform.protocol.METHOD_ARGS";
    public static final String EXTRA_PROTOCOL_METHOD_RESULTS = "com.facebook.platform.protocol.RESULT_ARGS";
    public static final String EXTRA_PROTOCOL_VERSION = "com.facebook.platform.protocol.PROTOCOL_VERSION";
    public static final String EXTRA_PROTOCOL_VERSIONS = "com.facebook.platform.extra.PROTOCOL_VERSIONS";
    public static final String EXTRA_TOAST_DURATION_MS = "com.facebook.platform.extra.EXTRA_TOAST_DURATION_MS";
    public static final String EXTRA_USER_ID = "com.facebook.platform.extra.USER_ID";
    private static final String FACEBOOK_PROXY_AUTH_ACTIVITY = "com.facebook.katana.ProxyAuth";
    public static final String FACEBOOK_PROXY_AUTH_APP_ID_KEY = "client_id";
    public static final String FACEBOOK_PROXY_AUTH_E2E_KEY = "e2e";
    public static final String FACEBOOK_PROXY_AUTH_PERMISSIONS_KEY = "scope";
    public static final String FACEBOOK_SDK_VERSION_KEY = "facebook_sdk_version";
    private static final String FACEBOOK_TOKEN_REFRESH_ACTIVITY = "com.facebook.katana.platform.TokenRefreshService";
    public static final String IMAGE_URL_KEY = "url";
    public static final String IMAGE_USER_GENERATED_KEY = "user_generated";
    public static final NativeProtocol INSTANCE = new NativeProtocol();
    public static final String INTENT_ACTION_PLATFORM_ACTIVITY = "com.facebook.platform.PLATFORM_ACTIVITY";
    public static final String INTENT_ACTION_PLATFORM_SERVICE = "com.facebook.platform.PLATFORM_SERVICE";
    private static final Integer[] KNOWN_PROTOCOL_VERSIONS;
    public static final int MESSAGE_GET_ACCESS_TOKEN_REPLY = 65537;
    public static final int MESSAGE_GET_ACCESS_TOKEN_REQUEST = 65536;
    public static final int MESSAGE_GET_AK_SEAMLESS_TOKEN_REPLY = 65545;
    public static final int MESSAGE_GET_AK_SEAMLESS_TOKEN_REQUEST = 65544;
    public static final int MESSAGE_GET_INSTALL_DATA_REPLY = 65541;
    public static final int MESSAGE_GET_INSTALL_DATA_REQUEST = 65540;
    public static final int MESSAGE_GET_LIKE_STATUS_REPLY = 65543;
    public static final int MESSAGE_GET_LIKE_STATUS_REQUEST = 65542;
    public static final int MESSAGE_GET_LOGIN_STATUS_REPLY = 65547;
    public static final int MESSAGE_GET_LOGIN_STATUS_REQUEST = 65546;
    public static final int MESSAGE_GET_PROTOCOL_VERSIONS_REPLY = 65539;
    public static final int MESSAGE_GET_PROTOCOL_VERSIONS_REQUEST = 65538;
    public static final int NO_PROTOCOL_AVAILABLE = -1;
    public static final String OPEN_GRAPH_CREATE_OBJECT_KEY = "fbsdk:create_object";
    private static final String PLATFORM_PROVIDER = ".provider.PlatformProvider";
    private static final String PLATFORM_PROVIDER_VERSIONS = ".provider.PlatformProvider/versions";
    private static final String PLATFORM_PROVIDER_VERSION_COLUMN = "version";
    public static final int PROTOCOL_VERSION_20121101 = 20121101;
    public static final int PROTOCOL_VERSION_20130502 = 20130502;
    public static final int PROTOCOL_VERSION_20130618 = 20130618;
    public static final int PROTOCOL_VERSION_20131024 = 20131024;
    public static final int PROTOCOL_VERSION_20131107 = 20131107;
    public static final int PROTOCOL_VERSION_20140204 = 20140204;
    public static final int PROTOCOL_VERSION_20140313 = 20140313;
    public static final int PROTOCOL_VERSION_20140324 = 20140324;
    public static final int PROTOCOL_VERSION_20140701 = 20140701;
    public static final int PROTOCOL_VERSION_20141001 = 20141001;
    public static final int PROTOCOL_VERSION_20141028 = 20141028;
    public static final int PROTOCOL_VERSION_20141107 = 20141107;
    public static final int PROTOCOL_VERSION_20141218 = 20141218;
    public static final int PROTOCOL_VERSION_20150401 = 20150401;
    public static final int PROTOCOL_VERSION_20150702 = 20150702;
    public static final int PROTOCOL_VERSION_20160327 = 20160327;
    public static final int PROTOCOL_VERSION_20161017 = 20161017;
    public static final int PROTOCOL_VERSION_20170213 = 20170213;
    public static final int PROTOCOL_VERSION_20170411 = 20170411;
    public static final int PROTOCOL_VERSION_20170417 = 20170417;
    public static final int PROTOCOL_VERSION_20171115 = 20171115;
    public static final int PROTOCOL_VERSION_20210906 = 20210906;
    public static final String RESULT_ARGS_ACCESS_TOKEN = "access_token";
    public static final String RESULT_ARGS_DIALOG_COMPLETE_KEY = "didComplete";
    public static final String RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY = "completionGesture";
    public static final String RESULT_ARGS_EXPIRES_SECONDS_SINCE_EPOCH = "expires_seconds_since_epoch";
    public static final String RESULT_ARGS_GRAPH_DOMAIN = "graph_domain";
    public static final String RESULT_ARGS_PERMISSIONS = "permissions";
    public static final String RESULT_ARGS_SIGNED_REQUEST = "signed request";
    public static final String STATUS_ERROR_CODE = "com.facebook.platform.status.ERROR_CODE";
    public static final String STATUS_ERROR_DESCRIPTION = "com.facebook.platform.status.ERROR_DESCRIPTION";
    public static final String STATUS_ERROR_JSON = "com.facebook.platform.status.ERROR_JSON";
    public static final String STATUS_ERROR_SUBCODE = "com.facebook.platform.status.ERROR_SUBCODE";
    public static final String STATUS_ERROR_TYPE = "com.facebook.platform.status.ERROR_TYPE";
    private static final String TAG;
    public static final String WEB_DIALOG_ACTION = "action";
    public static final String WEB_DIALOG_IS_FALLBACK = "is_fallback";
    public static final String WEB_DIALOG_PARAMS = "params";
    public static final String WEB_DIALOG_URL = "url";
    private static final Map<String, List<NativeAppInfo>> actionToAppInfoMap;
    private static final List<NativeAppInfo> effectCameraAppInfoList;
    private static final List<NativeAppInfo> facebookAppInfoList;
    private static final AtomicBoolean protocolVersionsAsyncUpdating;

    /* compiled from: NativeProtocol.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0006\u001a\u00020\u0004H\u0016¨\u0006\u0007"}, d2 = {"Lcom/facebook/internal/NativeProtocol$InstagramAppInfo;", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "()V", "getLoginActivity", "", "getPackage", "getResponseType", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    private static final class InstagramAppInfo extends NativeAppInfo {
        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public String getLoginActivity() {
            return "com.instagram.platform.AppAuthorizeActivity";
        }

        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public String getPackage() {
            return "com.instagram.android";
        }

        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public String getResponseType() {
            return ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SCOPES;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: NativeProtocol.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lcom/facebook/internal/NativeProtocol$WakizashiAppInfo;", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "()V", "getLoginActivity", "", "getPackage", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class WakizashiAppInfo extends NativeAppInfo {
        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public String getLoginActivity() {
            return NativeProtocol.FACEBOOK_PROXY_AUTH_ACTIVITY;
        }

        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public String getPackage() {
            return RemoteServiceWrapper.RECEIVER_SERVICE_PACKAGE_WAKIZASHI;
        }
    }

    private NativeProtocol() {
    }

    static {
        String name = NativeProtocol.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "NativeProtocol::class.java.name");
        TAG = name;
        facebookAppInfoList = INSTANCE.buildFacebookAppList();
        effectCameraAppInfoList = INSTANCE.buildEffectCameraAppInfoList();
        actionToAppInfoMap = INSTANCE.buildActionToAppInfoMap();
        protocolVersionsAsyncUpdating = new AtomicBoolean(false);
        KNOWN_PROTOCOL_VERSIONS = new Integer[]{Integer.valueOf((int) PROTOCOL_VERSION_20210906), Integer.valueOf((int) PROTOCOL_VERSION_20171115), Integer.valueOf((int) PROTOCOL_VERSION_20170417), Integer.valueOf((int) PROTOCOL_VERSION_20170411), Integer.valueOf((int) PROTOCOL_VERSION_20170213), Integer.valueOf((int) PROTOCOL_VERSION_20161017), Integer.valueOf((int) PROTOCOL_VERSION_20160327), Integer.valueOf((int) PROTOCOL_VERSION_20150702), Integer.valueOf((int) PROTOCOL_VERSION_20150401), Integer.valueOf((int) PROTOCOL_VERSION_20141218), Integer.valueOf((int) PROTOCOL_VERSION_20141107), Integer.valueOf((int) PROTOCOL_VERSION_20141028), Integer.valueOf((int) PROTOCOL_VERSION_20141001), Integer.valueOf((int) PROTOCOL_VERSION_20140701), Integer.valueOf((int) PROTOCOL_VERSION_20140324), Integer.valueOf((int) PROTOCOL_VERSION_20140313), Integer.valueOf((int) PROTOCOL_VERSION_20140204), Integer.valueOf((int) PROTOCOL_VERSION_20131107), Integer.valueOf((int) PROTOCOL_VERSION_20131024), Integer.valueOf((int) PROTOCOL_VERSION_20130618), Integer.valueOf((int) PROTOCOL_VERSION_20130502), Integer.valueOf((int) PROTOCOL_VERSION_20121101)};
    }

    private final List<NativeAppInfo> buildFacebookAppList() {
        return CollectionsKt.arrayListOf(new KatanaAppInfo(), new WakizashiAppInfo());
    }

    private final List<NativeAppInfo> buildEffectCameraAppInfoList() {
        ArrayList arrayListOf = CollectionsKt.arrayListOf(new EffectTestAppInfo());
        arrayListOf.addAll(buildFacebookAppList());
        return arrayListOf;
    }

    private final Map<String, List<NativeAppInfo>> buildActionToAppInfoMap() {
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new MessengerAppInfo());
        hashMap.put(ACTION_OGACTIONPUBLISH_DIALOG, facebookAppInfoList);
        hashMap.put(ACTION_FEED_DIALOG, facebookAppInfoList);
        hashMap.put(ACTION_LIKE_DIALOG, facebookAppInfoList);
        hashMap.put(ACTION_APPINVITE_DIALOG, facebookAppInfoList);
        hashMap.put(ACTION_MESSAGE_DIALOG, arrayList);
        hashMap.put(ACTION_OGMESSAGEPUBLISH_DIALOG, arrayList);
        hashMap.put(ACTION_CAMERA_EFFECT, effectCameraAppInfoList);
        hashMap.put(ACTION_SHARE_STORY, facebookAppInfoList);
        return hashMap;
    }

    @JvmStatic
    public static final Intent validateActivityIntent(Context context, Intent intent, NativeAppInfo nativeAppInfo) {
        ResolveInfo resolveActivity;
        Intrinsics.checkNotNullParameter(context, "context");
        if (intent == null || (resolveActivity = context.getPackageManager().resolveActivity(intent, 0)) == null) {
            return null;
        }
        FacebookSignatureValidator facebookSignatureValidator = FacebookSignatureValidator.INSTANCE;
        String str = resolveActivity.activityInfo.packageName;
        Intrinsics.checkNotNullExpressionValue(str, "resolveInfo.activityInfo.packageName");
        if (FacebookSignatureValidator.validateSignature(context, str)) {
            return intent;
        }
        return null;
    }

    @JvmStatic
    public static final Intent validateServiceIntent(Context context, Intent intent, NativeAppInfo nativeAppInfo) {
        ResolveInfo resolveService;
        Intrinsics.checkNotNullParameter(context, "context");
        if (intent == null || (resolveService = context.getPackageManager().resolveService(intent, 0)) == null) {
            return null;
        }
        FacebookSignatureValidator facebookSignatureValidator = FacebookSignatureValidator.INSTANCE;
        String str = resolveService.serviceInfo.packageName;
        Intrinsics.checkNotNullExpressionValue(str, "resolveInfo.serviceInfo.packageName");
        if (FacebookSignatureValidator.validateSignature(context, str)) {
            return intent;
        }
        return null;
    }

    @JvmStatic
    public static final Intent createInstagramIntent(Context context, String applicationId, Collection<String> permissions, String e2e, boolean z, boolean z2, DefaultAudience defaultAudience, String clientState, String authType, String str, boolean z3, boolean z4, boolean z5) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(applicationId, "applicationId");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(e2e, "e2e");
        Intrinsics.checkNotNullParameter(defaultAudience, "defaultAudience");
        Intrinsics.checkNotNullParameter(clientState, "clientState");
        Intrinsics.checkNotNullParameter(authType, "authType");
        InstagramAppInfo instagramAppInfo = new InstagramAppInfo();
        Intent createNativeAppIntent = INSTANCE.createNativeAppIntent(instagramAppInfo, applicationId, permissions, e2e, z2, defaultAudience, clientState, authType, false, str, z3, LoginTargetApp.INSTAGRAM, z4, z5, "", null, null);
        NativeProtocol nativeProtocol = INSTANCE;
        return validateActivityIntent(context, createNativeAppIntent, instagramAppInfo);
    }

    private final Intent createNativeAppIntent(NativeAppInfo nativeAppInfo, String str, Collection<String> collection, String str2, boolean z, DefaultAudience defaultAudience, String str3, String str4, boolean z2, String str5, boolean z3, LoginTargetApp loginTargetApp, boolean z4, boolean z5, String str6, String str7, String str8) {
        String loginActivity = nativeAppInfo.getLoginActivity();
        if (loginActivity == null) {
            return null;
        }
        Intent putExtra = new Intent().setClassName(nativeAppInfo.getPackage(), loginActivity).putExtra("client_id", str);
        Intrinsics.checkNotNullExpressionValue(putExtra, "Intent()\n            .setClassName(appInfo.getPackage(), activityName)\n            .putExtra(FACEBOOK_PROXY_AUTH_APP_ID_KEY, applicationId)");
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        putExtra.putExtra(FACEBOOK_SDK_VERSION_KEY, FacebookSdk.getSdkVersion());
        Utility utility = Utility.INSTANCE;
        if (!Utility.isNullOrEmpty(collection)) {
            putExtra.putExtra("scope", TextUtils.join(",", collection));
        }
        Utility utility2 = Utility.INSTANCE;
        if (!Utility.isNullOrEmpty(str2)) {
            putExtra.putExtra("e2e", str2);
        }
        putExtra.putExtra("state", str3);
        putExtra.putExtra(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, nativeAppInfo.getResponseType());
        putExtra.putExtra("nonce", str6);
        putExtra.putExtra(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
        if (z) {
            putExtra.putExtra("default_audience", defaultAudience.getNativeProtocolAudience());
        }
        FacebookSdk facebookSdk2 = FacebookSdk.INSTANCE;
        putExtra.putExtra(ServerProtocol.DIALOG_PARAM_LEGACY_OVERRIDE, FacebookSdk.getGraphApiVersion());
        putExtra.putExtra(ServerProtocol.DIALOG_PARAM_AUTH_TYPE, str4);
        if (z2) {
            putExtra.putExtra(ServerProtocol.DIALOG_PARAM_FAIL_ON_LOGGED_OUT, true);
        }
        putExtra.putExtra(ServerProtocol.DIALOG_PARAM_MESSENGER_PAGE_ID, str5);
        putExtra.putExtra(ServerProtocol.DIALOG_PARAM_RESET_MESSENGER_STATE, z3);
        if (z4) {
            putExtra.putExtra(ServerProtocol.DIALOG_PARAM_FX_APP, loginTargetApp.toString());
        }
        if (z5) {
            putExtra.putExtra(ServerProtocol.DIALOG_PARAM_SKIP_DEDUPE, true);
        }
        return putExtra;
    }

    @JvmStatic
    public static final List<Intent> createProxyAuthIntents(Context context, String applicationId, Collection<String> permissions, String e2e, boolean z, boolean z2, DefaultAudience defaultAudience, String clientState, String authType, boolean z3, String str, boolean z4, boolean z5, boolean z6, String str2, String str3, String str4) {
        Intrinsics.checkNotNullParameter(applicationId, "applicationId");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(e2e, "e2e");
        Intrinsics.checkNotNullParameter(defaultAudience, "defaultAudience");
        Intrinsics.checkNotNullParameter(clientState, "clientState");
        Intrinsics.checkNotNullParameter(authType, "authType");
        ArrayList arrayList = new ArrayList();
        Iterator it = facebookAppInfoList.iterator();
        while (it.hasNext()) {
            NativeAppInfo nativeAppInfo = (NativeAppInfo) it.next();
            Iterator it2 = it;
            ArrayList arrayList2 = arrayList;
            Intent createNativeAppIntent = INSTANCE.createNativeAppIntent(nativeAppInfo, applicationId, permissions, e2e, z2, defaultAudience, clientState, authType, z3, str, z4, LoginTargetApp.FACEBOOK, z5, z6, str2, str3, str4);
            if (createNativeAppIntent != null) {
                arrayList2.add(createNativeAppIntent);
            }
            arrayList = arrayList2;
            it = it2;
        }
        return arrayList;
    }

    @JvmStatic
    public static final int getLatestKnownVersion() {
        return KNOWN_PROTOCOL_VERSIONS[0].intValue();
    }

    @JvmStatic
    public static final boolean isVersionCompatibleWithBucketedIntent(int i) {
        if (ArraysKt.contains(KNOWN_PROTOCOL_VERSIONS, Integer.valueOf(i))) {
            NativeProtocol nativeProtocol = INSTANCE;
            if (i >= 20140701) {
                return true;
            }
        }
        return false;
    }

    @JvmStatic
    public static final Intent createPlatformActivityIntent(Context context, String str, String str2, ProtocolVersionQueryResult protocolVersionQueryResult, Bundle bundle) {
        NativeAppInfo appInfo;
        Intrinsics.checkNotNullParameter(context, "context");
        if (protocolVersionQueryResult == null || (appInfo = protocolVersionQueryResult.getAppInfo()) == null) {
            return null;
        }
        Intent intent = new Intent();
        NativeProtocol nativeProtocol = INSTANCE;
        Intent addCategory = intent.setAction(INTENT_ACTION_PLATFORM_ACTIVITY).setPackage(appInfo.getPackage()).addCategory("android.intent.category.DEFAULT");
        NativeProtocol nativeProtocol2 = INSTANCE;
        Intent validateActivityIntent = validateActivityIntent(context, addCategory, appInfo);
        if (validateActivityIntent == null) {
            return null;
        }
        NativeProtocol nativeProtocol3 = INSTANCE;
        setupProtocolRequestIntent(validateActivityIntent, str, str2, protocolVersionQueryResult.getProtocolVersion(), bundle);
        return validateActivityIntent;
    }

    @JvmStatic
    public static final void setupProtocolRequestIntent(Intent intent, String str, String str2, int i, Bundle bundle) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        String applicationId = FacebookSdk.getApplicationId();
        FacebookSdk facebookSdk2 = FacebookSdk.INSTANCE;
        String applicationName = FacebookSdk.getApplicationName();
        NativeProtocol nativeProtocol = INSTANCE;
        Intent putExtra = intent.putExtra(EXTRA_PROTOCOL_VERSION, i);
        NativeProtocol nativeProtocol2 = INSTANCE;
        Intent putExtra2 = putExtra.putExtra(EXTRA_PROTOCOL_ACTION, str2);
        NativeProtocol nativeProtocol3 = INSTANCE;
        putExtra2.putExtra(EXTRA_APPLICATION_ID, applicationId);
        NativeProtocol nativeProtocol4 = INSTANCE;
        if (isVersionCompatibleWithBucketedIntent(i)) {
            Bundle bundle2 = new Bundle();
            NativeProtocol nativeProtocol5 = INSTANCE;
            bundle2.putString("action_id", str);
            Utility utility = Utility.INSTANCE;
            NativeProtocol nativeProtocol6 = INSTANCE;
            Utility.putNonEmptyString(bundle2, BRIDGE_ARG_APP_NAME_STRING, applicationName);
            NativeProtocol nativeProtocol7 = INSTANCE;
            intent.putExtra(EXTRA_PROTOCOL_BRIDGE_ARGS, bundle2);
            if (bundle == null) {
                bundle = new Bundle();
            }
            NativeProtocol nativeProtocol8 = INSTANCE;
            intent.putExtra(EXTRA_PROTOCOL_METHOD_ARGS, bundle);
            return;
        }
        NativeProtocol nativeProtocol9 = INSTANCE;
        intent.putExtra(EXTRA_PROTOCOL_CALL_ID, str);
        Utility utility2 = Utility.INSTANCE;
        if (!Utility.isNullOrEmpty(applicationName)) {
            NativeProtocol nativeProtocol10 = INSTANCE;
            intent.putExtra(EXTRA_APPLICATION_NAME, applicationName);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
    }

    @JvmStatic
    public static final Intent createProtocolResultIntent(Intent requestIntent, Bundle bundle, FacebookException facebookException) {
        Intrinsics.checkNotNullParameter(requestIntent, "requestIntent");
        NativeProtocol nativeProtocol = INSTANCE;
        UUID callIdFromIntent = getCallIdFromIntent(requestIntent);
        if (callIdFromIntent == null) {
            return null;
        }
        Intent intent = new Intent();
        NativeProtocol nativeProtocol2 = INSTANCE;
        intent.putExtra(EXTRA_PROTOCOL_VERSION, getProtocolVersionFromIntent(requestIntent));
        Bundle bundle2 = new Bundle();
        NativeProtocol nativeProtocol3 = INSTANCE;
        bundle2.putString("action_id", callIdFromIntent.toString());
        if (facebookException != null) {
            NativeProtocol nativeProtocol4 = INSTANCE;
            bundle2.putBundle("error", createBundleForException(facebookException));
        }
        NativeProtocol nativeProtocol5 = INSTANCE;
        intent.putExtra(EXTRA_PROTOCOL_BRIDGE_ARGS, bundle2);
        if (bundle != null) {
            NativeProtocol nativeProtocol6 = INSTANCE;
            intent.putExtra(EXTRA_PROTOCOL_METHOD_RESULTS, bundle);
        }
        return intent;
    }

    @JvmStatic
    public static final Intent createPlatformServiceIntent(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        for (NativeAppInfo nativeAppInfo : facebookAppInfoList) {
            NativeProtocol nativeProtocol = INSTANCE;
            Intent addCategory = new Intent(INTENT_ACTION_PLATFORM_SERVICE).setPackage(nativeAppInfo.getPackage()).addCategory("android.intent.category.DEFAULT");
            NativeProtocol nativeProtocol2 = INSTANCE;
            Intent validateServiceIntent = validateServiceIntent(context, addCategory, nativeAppInfo);
            if (validateServiceIntent != null) {
                return validateServiceIntent;
            }
        }
        return null;
    }

    @JvmStatic
    public static final int getProtocolVersionFromIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        NativeProtocol nativeProtocol = INSTANCE;
        return intent.getIntExtra(EXTRA_PROTOCOL_VERSION, 0);
    }

    @JvmStatic
    public static final UUID getCallIdFromIntent(Intent intent) {
        String stringExtra;
        if (intent == null) {
            return null;
        }
        NativeProtocol nativeProtocol = INSTANCE;
        int protocolVersionFromIntent = getProtocolVersionFromIntent(intent);
        NativeProtocol nativeProtocol2 = INSTANCE;
        if (isVersionCompatibleWithBucketedIntent(protocolVersionFromIntent)) {
            NativeProtocol nativeProtocol3 = INSTANCE;
            Bundle bundleExtra = intent.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
            if (bundleExtra != null) {
                NativeProtocol nativeProtocol4 = INSTANCE;
                stringExtra = bundleExtra.getString("action_id");
            } else {
                stringExtra = null;
            }
        } else {
            NativeProtocol nativeProtocol5 = INSTANCE;
            stringExtra = intent.getStringExtra(EXTRA_PROTOCOL_CALL_ID);
        }
        if (stringExtra != null) {
            try {
                return UUID.fromString(stringExtra);
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
        return null;
    }

    @JvmStatic
    public static final Bundle getBridgeArgumentsFromIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        NativeProtocol nativeProtocol = INSTANCE;
        int protocolVersionFromIntent = getProtocolVersionFromIntent(intent);
        NativeProtocol nativeProtocol2 = INSTANCE;
        if (!isVersionCompatibleWithBucketedIntent(protocolVersionFromIntent)) {
            return null;
        }
        NativeProtocol nativeProtocol3 = INSTANCE;
        return intent.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
    }

    @JvmStatic
    public static final Bundle getMethodArgumentsFromIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        NativeProtocol nativeProtocol = INSTANCE;
        int protocolVersionFromIntent = getProtocolVersionFromIntent(intent);
        NativeProtocol nativeProtocol2 = INSTANCE;
        if (!isVersionCompatibleWithBucketedIntent(protocolVersionFromIntent)) {
            return intent.getExtras();
        }
        NativeProtocol nativeProtocol3 = INSTANCE;
        return intent.getBundleExtra(EXTRA_PROTOCOL_METHOD_ARGS);
    }

    @JvmStatic
    public static final Bundle getSuccessResultsFromIntent(Intent resultIntent) {
        Intrinsics.checkNotNullParameter(resultIntent, "resultIntent");
        NativeProtocol nativeProtocol = INSTANCE;
        int protocolVersionFromIntent = getProtocolVersionFromIntent(resultIntent);
        Bundle extras = resultIntent.getExtras();
        NativeProtocol nativeProtocol2 = INSTANCE;
        if (!isVersionCompatibleWithBucketedIntent(protocolVersionFromIntent) || extras == null) {
            return extras;
        }
        NativeProtocol nativeProtocol3 = INSTANCE;
        return extras.getBundle(EXTRA_PROTOCOL_METHOD_RESULTS);
    }

    @JvmStatic
    public static final boolean isErrorResult(Intent resultIntent) {
        Boolean valueOf;
        Intrinsics.checkNotNullParameter(resultIntent, "resultIntent");
        NativeProtocol nativeProtocol = INSTANCE;
        Bundle bridgeArgumentsFromIntent = getBridgeArgumentsFromIntent(resultIntent);
        if (bridgeArgumentsFromIntent == null) {
            valueOf = null;
        } else {
            NativeProtocol nativeProtocol2 = INSTANCE;
            valueOf = Boolean.valueOf(bridgeArgumentsFromIntent.containsKey("error"));
        }
        if (valueOf != null) {
            return valueOf.booleanValue();
        }
        NativeProtocol nativeProtocol3 = INSTANCE;
        return resultIntent.hasExtra(STATUS_ERROR_TYPE);
    }

    @JvmStatic
    public static final Bundle getErrorDataFromResultIntent(Intent resultIntent) {
        Intrinsics.checkNotNullParameter(resultIntent, "resultIntent");
        NativeProtocol nativeProtocol = INSTANCE;
        if (isErrorResult(resultIntent)) {
            NativeProtocol nativeProtocol2 = INSTANCE;
            Bundle bridgeArgumentsFromIntent = getBridgeArgumentsFromIntent(resultIntent);
            if (bridgeArgumentsFromIntent != null) {
                NativeProtocol nativeProtocol3 = INSTANCE;
                return bridgeArgumentsFromIntent.getBundle("error");
            }
            return resultIntent.getExtras();
        }
        return null;
    }

    @JvmStatic
    public static final FacebookException getExceptionFromErrorData(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        NativeProtocol nativeProtocol = INSTANCE;
        String string = bundle.getString(BRIDGE_ARG_ERROR_TYPE);
        if (string == null) {
            NativeProtocol nativeProtocol2 = INSTANCE;
            string = bundle.getString(STATUS_ERROR_TYPE);
        }
        NativeProtocol nativeProtocol3 = INSTANCE;
        String string2 = bundle.getString("error_description");
        if (string2 == null) {
            NativeProtocol nativeProtocol4 = INSTANCE;
            string2 = bundle.getString(STATUS_ERROR_DESCRIPTION);
        }
        if (string != null) {
            NativeProtocol nativeProtocol5 = INSTANCE;
            if (StringsKt.equals(string, ERROR_USER_CANCELED, true)) {
                return new FacebookOperationCanceledException(string2);
            }
        }
        return new FacebookException(string2);
    }

    @JvmStatic
    public static final Bundle createBundleForException(FacebookException facebookException) {
        if (facebookException == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        NativeProtocol nativeProtocol = INSTANCE;
        bundle.putString("error_description", facebookException.toString());
        if (facebookException instanceof FacebookOperationCanceledException) {
            NativeProtocol nativeProtocol2 = INSTANCE;
            bundle.putString(BRIDGE_ARG_ERROR_TYPE, ERROR_USER_CANCELED);
        }
        return bundle;
    }

    @JvmStatic
    public static final int getLatestAvailableProtocolVersionForService(int i) {
        return INSTANCE.getLatestAvailableProtocolVersionForAppInfoList(facebookAppInfoList, new int[]{i}).getProtocolVersion();
    }

    @JvmStatic
    public static final ProtocolVersionQueryResult getLatestAvailableProtocolVersionForAction(String action, int[] versionSpec) {
        Intrinsics.checkNotNullParameter(action, "action");
        Intrinsics.checkNotNullParameter(versionSpec, "versionSpec");
        List<NativeAppInfo> list = actionToAppInfoMap.get(action);
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        return INSTANCE.getLatestAvailableProtocolVersionForAppInfoList(list, versionSpec);
    }

    private final ProtocolVersionQueryResult getLatestAvailableProtocolVersionForAppInfoList(List<? extends NativeAppInfo> list, int[] iArr) {
        updateAllAvailableProtocolVersionsAsync();
        if (list == null) {
            return ProtocolVersionQueryResult.Companion.createEmpty();
        }
        for (NativeAppInfo nativeAppInfo : list) {
            int computeLatestAvailableVersionFromVersionSpec = computeLatestAvailableVersionFromVersionSpec(nativeAppInfo.getAvailableVersions(), getLatestKnownVersion(), iArr);
            if (computeLatestAvailableVersionFromVersionSpec != -1) {
                return ProtocolVersionQueryResult.Companion.create(nativeAppInfo, computeLatestAvailableVersionFromVersionSpec);
            }
        }
        return ProtocolVersionQueryResult.Companion.createEmpty();
    }

    @JvmStatic
    public static final void updateAllAvailableProtocolVersionsAsync() {
        if (protocolVersionsAsyncUpdating.compareAndSet(false, true)) {
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            FacebookSdk.getExecutor().execute($$Lambda$NativeProtocol$zPYoJqodmd1JFixHd0alDrwtRVk.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateAllAvailableProtocolVersionsAsync$lambda-1  reason: not valid java name */
    public static final void m144updateAllAvailableProtocolVersionsAsync$lambda1() {
        try {
            for (NativeAppInfo nativeAppInfo : facebookAppInfoList) {
                nativeAppInfo.fetchAvailableVersions(true);
            }
        } finally {
            protocolVersionsAsyncUpdating.set(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TreeSet<Integer> fetchAllAvailableProtocolVersionsForAppInfo(NativeAppInfo nativeAppInfo) {
        ProviderInfo providerInfo;
        TreeSet<Integer> treeSet = new TreeSet<>();
        FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
        ContentResolver contentResolver = FacebookSdk.getApplicationContext().getContentResolver();
        String[] strArr = {"version"};
        Uri buildPlatformProviderVersionURI = buildPlatformProviderVersionURI(nativeAppInfo);
        Cursor cursor = null;
        try {
            FacebookSdk facebookSdk2 = FacebookSdk.INSTANCE;
            try {
                providerInfo = FacebookSdk.getApplicationContext().getPackageManager().resolveContentProvider(Intrinsics.stringPlus(nativeAppInfo.getPackage(), PLATFORM_PROVIDER), 0);
            } catch (RuntimeException e) {
                Log.e(TAG, "Failed to query content resolver.", e);
                providerInfo = null;
            }
            if (providerInfo != null) {
                try {
                    try {
                        try {
                            cursor = contentResolver.query(buildPlatformProviderVersionURI, strArr, null, null, null);
                        } catch (NullPointerException unused) {
                            Log.e(TAG, "Failed to query content resolver.");
                            cursor = null;
                        }
                    } catch (IllegalArgumentException unused2) {
                        Log.e(TAG, "Failed to query content resolver.");
                        cursor = null;
                    }
                } catch (SecurityException unused3) {
                    Log.e(TAG, "Failed to query content resolver.");
                    cursor = null;
                }
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        treeSet.add(Integer.valueOf(cursor.getInt(cursor.getColumnIndex("version"))));
                    }
                }
            }
            return treeSet;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @JvmStatic
    public static final int computeLatestAvailableVersionFromVersionSpec(TreeSet<Integer> treeSet, int i, int[] versionSpec) {
        Intrinsics.checkNotNullParameter(versionSpec, "versionSpec");
        if (treeSet == null) {
            NativeProtocol nativeProtocol = INSTANCE;
            return -1;
        }
        Iterator<Integer> descendingIterator = treeSet.descendingIterator();
        int length = versionSpec.length - 1;
        int i2 = -1;
        while (descendingIterator.hasNext()) {
            Integer fbAppVersion = descendingIterator.next();
            Intrinsics.checkNotNullExpressionValue(fbAppVersion, "fbAppVersion");
            i2 = Math.max(i2, fbAppVersion.intValue());
            while (length >= 0 && versionSpec[length] > fbAppVersion.intValue()) {
                length--;
            }
            if (length < 0) {
                NativeProtocol nativeProtocol2 = INSTANCE;
                return -1;
            } else if (versionSpec[length] == fbAppVersion.intValue()) {
                if (length % 2 == 0) {
                    return Math.min(i2, i);
                }
                NativeProtocol nativeProtocol3 = INSTANCE;
                return -1;
            }
        }
        NativeProtocol nativeProtocol4 = INSTANCE;
        return -1;
    }

    private final Uri buildPlatformProviderVersionURI(NativeAppInfo nativeAppInfo) {
        Uri parse = Uri.parse("content://" + nativeAppInfo.getPackage() + PLATFORM_PROVIDER_VERSIONS);
        Intrinsics.checkNotNullExpressionValue(parse, "parse(CONTENT_SCHEME + appInfo.getPackage() + PLATFORM_PROVIDER_VERSIONS)");
        return parse;
    }

    /* compiled from: NativeProtocol.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004J\n\u0010\u000b\u001a\u0004\u0018\u00010\fH&J\b\u0010\r\u001a\u00020\fH&J\b\u0010\u000e\u001a\u00020\fH\u0016J\b\u0010\u000f\u001a\u00020\u0007H\u0016R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "", "()V", "availableVersions", "Ljava/util/TreeSet;", "", "fetchAvailableVersions", "", "force", "", "getAvailableVersions", "getLoginActivity", "", "getPackage", "getResponseType", "onAvailableVersionsNullOrEmpty", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static abstract class NativeAppInfo {
        private TreeSet<Integer> availableVersions;

        public abstract String getLoginActivity();

        public abstract String getPackage();

        public String getResponseType() {
            return ServerProtocol.DIALOG_RESPONSE_TYPE_ID_TOKEN_AND_SIGNED_REQUEST;
        }

        public void onAvailableVersionsNullOrEmpty() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:8:0x0019, code lost:
            if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) (r0 == null ? null : java.lang.Boolean.valueOf(r0.isEmpty())), (java.lang.Object) false) == false) goto L11;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.TreeSet<java.lang.Integer> getAvailableVersions() {
            /*
                r3 = this;
                java.util.TreeSet<java.lang.Integer> r0 = r3.availableVersions
                r1 = 0
                if (r0 == 0) goto L1b
                if (r0 != 0) goto L9
                r0 = 0
                goto L11
            L9:
                boolean r0 = r0.isEmpty()
                java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            L11:
                java.lang.Boolean r2 = java.lang.Boolean.valueOf(r1)
                boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
                if (r0 != 0) goto L1e
            L1b:
                r3.fetchAvailableVersions(r1)
            L1e:
                java.util.TreeSet<java.lang.Integer> r0 = r3.availableVersions
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.NativeProtocol.NativeAppInfo.getAvailableVersions():java.util.TreeSet");
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x001e, code lost:
            if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) (r3 == null ? null : java.lang.Boolean.valueOf(r3.isEmpty())), (java.lang.Object) false) == false) goto L4;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final synchronized void fetchAvailableVersions(boolean r3) {
            /*
                r2 = this;
                monitor-enter(r2)
                r0 = 0
                if (r3 != 0) goto L20
                java.util.TreeSet<java.lang.Integer> r3 = r2.availableVersions     // Catch: java.lang.Throwable -> L3c
                if (r3 == 0) goto L20
                java.util.TreeSet<java.lang.Integer> r3 = r2.availableVersions     // Catch: java.lang.Throwable -> L3c
                if (r3 != 0) goto Le
                r3 = 0
                goto L16
            Le:
                boolean r3 = r3.isEmpty()     // Catch: java.lang.Throwable -> L3c
                java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch: java.lang.Throwable -> L3c
            L16:
                java.lang.Boolean r1 = java.lang.Boolean.valueOf(r0)     // Catch: java.lang.Throwable -> L3c
                boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r1)     // Catch: java.lang.Throwable -> L3c
                if (r3 != 0) goto L28
            L20:
                com.facebook.internal.NativeProtocol r3 = com.facebook.internal.NativeProtocol.INSTANCE     // Catch: java.lang.Throwable -> L3c
                java.util.TreeSet r3 = com.facebook.internal.NativeProtocol.access$fetchAllAvailableProtocolVersionsForAppInfo(r3, r2)     // Catch: java.lang.Throwable -> L3c
                r2.availableVersions = r3     // Catch: java.lang.Throwable -> L3c
            L28:
                java.util.TreeSet<java.lang.Integer> r3 = r2.availableVersions     // Catch: java.lang.Throwable -> L3c
                java.util.Collection r3 = (java.util.Collection) r3     // Catch: java.lang.Throwable -> L3c
                if (r3 == 0) goto L34
                boolean r3 = r3.isEmpty()     // Catch: java.lang.Throwable -> L3c
                if (r3 == 0) goto L35
            L34:
                r0 = 1
            L35:
                if (r0 == 0) goto L3a
                r2.onAvailableVersionsNullOrEmpty()     // Catch: java.lang.Throwable -> L3c
            L3a:
                monitor-exit(r2)
                return
            L3c:
                r3 = move-exception
                monitor-exit(r2)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.NativeProtocol.NativeAppInfo.fetchAvailableVersions(boolean):void");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: NativeProtocol.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0002J\b\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lcom/facebook/internal/NativeProtocol$KatanaAppInfo;", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "()V", "getLoginActivity", "", "getPackage", "isAndroidAPIVersionNotLessThan30", "", "onAvailableVersionsNullOrEmpty", "", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class KatanaAppInfo extends NativeAppInfo {
        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public String getLoginActivity() {
            return NativeProtocol.FACEBOOK_PROXY_AUTH_ACTIVITY;
        }

        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public String getPackage() {
            return "com.facebook.katana";
        }

        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public void onAvailableVersionsNullOrEmpty() {
            if (isAndroidAPIVersionNotLessThan30()) {
                Log.w(NativeProtocol.TAG, "Apps that target Android API 30+ (Android 11+) cannot call Facebook native apps unless the package visibility needs are declared. Please follow https://developers.facebook.com/docs/android/troubleshooting/#faq_267321845055988 to make the declaration.");
            }
        }

        private final boolean isAndroidAPIVersionNotLessThan30() {
            FacebookSdk facebookSdk = FacebookSdk.INSTANCE;
            return FacebookSdk.getApplicationContext().getApplicationInfo().targetSdkVersion >= 30;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: NativeProtocol.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/facebook/internal/NativeProtocol$MessengerAppInfo;", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "()V", "getLoginActivity", "", "getPackage", "", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class MessengerAppInfo extends NativeAppInfo {
        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public Void getLoginActivity() {
            return null;
        }

        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public String getPackage() {
            return "com.facebook.orca";
        }

        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public /* bridge */ /* synthetic */ String getLoginActivity() {
            return (String) getLoginActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: NativeProtocol.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/facebook/internal/NativeProtocol$EffectTestAppInfo;", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "()V", "getLoginActivity", "", "getPackage", "", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class EffectTestAppInfo extends NativeAppInfo {
        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public Void getLoginActivity() {
            return null;
        }

        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public String getPackage() {
            return "com.facebook.arstudio.player";
        }

        @Override // com.facebook.internal.NativeProtocol.NativeAppInfo
        public /* bridge */ /* synthetic */ String getLoginActivity() {
            return (String) getLoginActivity();
        }
    }

    /* compiled from: NativeProtocol.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\"\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lcom/facebook/internal/NativeProtocol$ProtocolVersionQueryResult;", "", "()V", "<set-?>", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "appInfo", "getAppInfo", "()Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "", "protocolVersion", "getProtocolVersion", "()I", "Companion", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes3.dex */
    public static final class ProtocolVersionQueryResult {
        public static final Companion Companion = new Companion(null);
        private NativeAppInfo appInfo;
        private int protocolVersion;

        public /* synthetic */ ProtocolVersionQueryResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static final ProtocolVersionQueryResult create(NativeAppInfo nativeAppInfo, int i) {
            return Companion.create(nativeAppInfo, i);
        }

        @JvmStatic
        public static final ProtocolVersionQueryResult createEmpty() {
            return Companion.createEmpty();
        }

        private ProtocolVersionQueryResult() {
        }

        public final NativeAppInfo getAppInfo() {
            return this.appInfo;
        }

        public final int getProtocolVersion() {
            return this.protocolVersion;
        }

        /* compiled from: NativeProtocol.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\u0004H\u0007¨\u0006\n"}, d2 = {"Lcom/facebook/internal/NativeProtocol$ProtocolVersionQueryResult$Companion;", "", "()V", "create", "Lcom/facebook/internal/NativeProtocol$ProtocolVersionQueryResult;", "nativeAppInfo", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", "protocolVersion", "", "createEmpty", "facebook-core_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
        /* loaded from: classes3.dex */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            @JvmStatic
            public final ProtocolVersionQueryResult create(NativeAppInfo nativeAppInfo, int i) {
                ProtocolVersionQueryResult protocolVersionQueryResult = new ProtocolVersionQueryResult(null);
                protocolVersionQueryResult.appInfo = nativeAppInfo;
                protocolVersionQueryResult.protocolVersion = i;
                return protocolVersionQueryResult;
            }

            @JvmStatic
            public final ProtocolVersionQueryResult createEmpty() {
                ProtocolVersionQueryResult protocolVersionQueryResult = new ProtocolVersionQueryResult(null);
                protocolVersionQueryResult.protocolVersion = -1;
                return protocolVersionQueryResult;
            }
        }
    }
}
