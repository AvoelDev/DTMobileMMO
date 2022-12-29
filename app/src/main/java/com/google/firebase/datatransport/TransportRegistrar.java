package com.google.firebase.datatransport;

import android.content.Context;
import com.google.android.datatransport.TransportFactory;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class TransportRegistrar implements ComponentRegistrar {
    @Override // com.google.firebase.components.ComponentRegistrar
    public List<Component<?>> getComponents() {
        return Arrays.asList(Component.builder(TransportFactory.class).add(Dependency.required(Context.class)).factory($$Lambda$TransportRegistrar$8MftFhDZTqyNaIMLf3wZTwlt260.INSTANCE).build(), LibraryVersionComponent.create("fire-transport", BuildConfig.VERSION_NAME));
    }
}
