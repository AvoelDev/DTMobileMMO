package com.facebook;

import java.io.File;
import java.util.concurrent.Callable;

/* compiled from: lambda */
/* renamed from: com.facebook.-$$Lambda$FacebookSdk$J0805Clgxed1K59G9leFVPRGzM4  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$FacebookSdk$J0805Clgxed1K59G9leFVPRGzM4 implements Callable {
    public static final /* synthetic */ $$Lambda$FacebookSdk$J0805Clgxed1K59G9leFVPRGzM4 INSTANCE = new $$Lambda$FacebookSdk$J0805Clgxed1K59G9leFVPRGzM4();

    private /* synthetic */ $$Lambda$FacebookSdk$J0805Clgxed1K59G9leFVPRGzM4() {
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        File m22sdkInitialize$lambda3;
        m22sdkInitialize$lambda3 = FacebookSdk.m22sdkInitialize$lambda3();
        return m22sdkInitialize$lambda3;
    }
}
