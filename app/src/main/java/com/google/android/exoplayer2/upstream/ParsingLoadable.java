package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class ParsingLoadable<T> implements Loader.Loadable {
    private volatile long bytesLoaded;
    private final DataSource dataSource;
    public final DataSpec dataSpec;
    private final Parser<? extends T> parser;
    private volatile T result;
    public final int type;

    /* loaded from: classes.dex */
    public interface Parser<T> {
        T parse(Uri uri, InputStream inputStream) throws IOException;
    }

    public static <T> T load(DataSource dataSource, Parser<? extends T> parser, Uri uri) throws IOException {
        ParsingLoadable<T> loadable = new ParsingLoadable<>(dataSource, uri, 0, parser);
        loadable.load();
        return loadable.getResult();
    }

    public ParsingLoadable(DataSource dataSource, Uri uri, int type, Parser<? extends T> parser) {
        this(dataSource, new DataSpec(uri, 3), type, parser);
    }

    public ParsingLoadable(DataSource dataSource, DataSpec dataSpec, int type, Parser<? extends T> parser) {
        this.dataSource = dataSource;
        this.dataSpec = dataSpec;
        this.type = type;
        this.parser = parser;
    }

    public final T getResult() {
        return this.result;
    }

    public long bytesLoaded() {
        return this.bytesLoaded;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public final void cancelLoad() {
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public final void load() throws IOException {
        DataSourceInputStream inputStream = new DataSourceInputStream(this.dataSource, this.dataSpec);
        try {
            inputStream.open();
            this.result = this.parser.parse(this.dataSource.getUri(), inputStream);
        } finally {
            this.bytesLoaded = inputStream.bytesRead();
            Util.closeQuietly(inputStream);
        }
    }
}
