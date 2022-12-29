package com.google.android.exoplayer2.drm;

import android.os.Handler;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public interface DefaultDrmSessionEventListener {
    void onDrmKeysLoaded();

    void onDrmKeysRemoved();

    void onDrmKeysRestored();

    void onDrmSessionManagerError(Exception exc);

    /* loaded from: classes.dex */
    public static final class EventDispatcher {
        private final CopyOnWriteArrayList<HandlerAndListener> listeners = new CopyOnWriteArrayList<>();

        public void addListener(Handler handler, DefaultDrmSessionEventListener eventListener) {
            Assertions.checkArgument((handler == null || eventListener == null) ? false : true);
            this.listeners.add(new HandlerAndListener(handler, eventListener));
        }

        public void removeListener(DefaultDrmSessionEventListener eventListener) {
            Iterator<HandlerAndListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                HandlerAndListener handlerAndListener = it.next();
                if (handlerAndListener.listener == eventListener) {
                    this.listeners.remove(handlerAndListener);
                }
            }
        }

        public void drmKeysLoaded() {
            Iterator<HandlerAndListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                HandlerAndListener handlerAndListener = it.next();
                final DefaultDrmSessionEventListener listener = handlerAndListener.listener;
                handlerAndListener.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener.EventDispatcher.1
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onDrmKeysLoaded();
                    }
                });
            }
        }

        public void drmSessionManagerError(final Exception e) {
            Iterator<HandlerAndListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                HandlerAndListener handlerAndListener = it.next();
                final DefaultDrmSessionEventListener listener = handlerAndListener.listener;
                handlerAndListener.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener.EventDispatcher.2
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onDrmSessionManagerError(e);
                    }
                });
            }
        }

        public void drmKeysRestored() {
            Iterator<HandlerAndListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                HandlerAndListener handlerAndListener = it.next();
                final DefaultDrmSessionEventListener listener = handlerAndListener.listener;
                handlerAndListener.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener.EventDispatcher.3
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onDrmKeysRestored();
                    }
                });
            }
        }

        public void drmKeysRemoved() {
            Iterator<HandlerAndListener> it = this.listeners.iterator();
            while (it.hasNext()) {
                HandlerAndListener handlerAndListener = it.next();
                final DefaultDrmSessionEventListener listener = handlerAndListener.listener;
                handlerAndListener.handler.post(new Runnable() { // from class: com.google.android.exoplayer2.drm.DefaultDrmSessionEventListener.EventDispatcher.4
                    @Override // java.lang.Runnable
                    public void run() {
                        listener.onDrmKeysRemoved();
                    }
                });
            }
        }

        /* loaded from: classes.dex */
        private static final class HandlerAndListener {
            public final Handler handler;
            public final DefaultDrmSessionEventListener listener;

            public HandlerAndListener(Handler handler, DefaultDrmSessionEventListener eventListener) {
                this.handler = handler;
                this.listener = eventListener;
            }
        }
    }
}
