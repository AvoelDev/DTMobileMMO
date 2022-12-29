package com.google.android.exoplayer2.upstream.cache;

import android.util.SparseArray;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
class CachedContentIndex {
    public static final String FILE_NAME = "cached_content_index.exi";
    private static final int FLAG_ENCRYPTED_INDEX = 1;
    private static final int VERSION = 2;
    private final AtomicFile atomicFile;
    private ReusableBufferedOutputStream bufferedOutputStream;
    private boolean changed;
    private final Cipher cipher;
    private final boolean encrypt;
    private final SparseArray<String> idToKey;
    private final HashMap<String, CachedContent> keyToContent;
    private final SecretKeySpec secretKeySpec;

    public CachedContentIndex(File cacheDir) {
        this(cacheDir, null);
    }

    public CachedContentIndex(File cacheDir, byte[] secretKey) {
        this(cacheDir, secretKey, secretKey != null);
    }

    public CachedContentIndex(File cacheDir, byte[] secretKey, boolean encrypt) {
        this.encrypt = encrypt;
        if (secretKey != null) {
            Assertions.checkArgument(secretKey.length == 16);
            try {
                this.cipher = getCipher();
                this.secretKeySpec = new SecretKeySpec(secretKey, "AES");
            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                throw new IllegalStateException(e);
            }
        } else {
            Assertions.checkState(encrypt ? false : true);
            this.cipher = null;
            this.secretKeySpec = null;
        }
        this.keyToContent = new HashMap<>();
        this.idToKey = new SparseArray<>();
        this.atomicFile = new AtomicFile(new File(cacheDir, FILE_NAME));
    }

    public void load() {
        Assertions.checkState(!this.changed);
        if (!readFile()) {
            this.atomicFile.delete();
            this.keyToContent.clear();
            this.idToKey.clear();
        }
    }

    public void store() throws Cache.CacheException {
        if (this.changed) {
            writeFile();
            this.changed = false;
        }
    }

    public CachedContent getOrAdd(String key) {
        CachedContent cachedContent = this.keyToContent.get(key);
        return cachedContent == null ? addNew(key) : cachedContent;
    }

    public CachedContent get(String key) {
        return this.keyToContent.get(key);
    }

    public Collection<CachedContent> getAll() {
        return this.keyToContent.values();
    }

    public int assignIdForKey(String key) {
        return getOrAdd(key).id;
    }

    public String getKeyForId(int id) {
        return this.idToKey.get(id);
    }

    public void maybeRemove(String key) {
        CachedContent cachedContent = this.keyToContent.get(key);
        if (cachedContent != null && cachedContent.isEmpty() && !cachedContent.isLocked()) {
            this.keyToContent.remove(key);
            this.idToKey.remove(cachedContent.id);
            this.changed = true;
        }
    }

    public void removeEmpty() {
        String[] keys = new String[this.keyToContent.size()];
        this.keyToContent.keySet().toArray(keys);
        for (String key : keys) {
            maybeRemove(key);
        }
    }

    public Set<String> getKeys() {
        return this.keyToContent.keySet();
    }

    public void applyContentMetadataMutations(String key, ContentMetadataMutations mutations) {
        CachedContent cachedContent = getOrAdd(key);
        if (cachedContent.applyMetadataMutations(mutations)) {
            this.changed = true;
        }
    }

    public ContentMetadata getContentMetadata(String key) {
        CachedContent cachedContent = get(key);
        return cachedContent != null ? cachedContent.getMetadata() : DefaultContentMetadata.EMPTY;
    }

    private boolean readFile() {
        GeneralSecurityException e;
        DataInputStream input = null;
        try {
            InputStream inputStream = new BufferedInputStream(this.atomicFile.openRead());
            DataInputStream input2 = new DataInputStream(inputStream);
            try {
                int version = input2.readInt();
                if (version < 0 || version > 2) {
                    if (input2 != null) {
                        Util.closeQuietly(input2);
                    }
                    return false;
                }
                int flags = input2.readInt();
                if ((flags & 1) == 0) {
                    if (this.encrypt) {
                        this.changed = true;
                    }
                    input = input2;
                } else if (this.cipher == null) {
                    if (input2 != null) {
                        Util.closeQuietly(input2);
                    }
                    return false;
                } else {
                    byte[] initializationVector = new byte[16];
                    input2.readFully(initializationVector);
                    IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);
                    try {
                        this.cipher.init(2, this.secretKeySpec, ivParameterSpec);
                        input = new DataInputStream(new CipherInputStream(inputStream, this.cipher));
                    } catch (InvalidAlgorithmParameterException e2) {
                        e = e2;
                        throw new IllegalStateException(e);
                    } catch (InvalidKeyException e3) {
                        e = e3;
                        throw new IllegalStateException(e);
                    }
                }
                int count = input.readInt();
                int hashCode = 0;
                for (int i = 0; i < count; i++) {
                    CachedContent cachedContent = CachedContent.readFromStream(version, input);
                    add(cachedContent);
                    hashCode += cachedContent.headerHashCode(version);
                }
                int fileHashCode = input.readInt();
                boolean isEOF = input.read() == -1;
                if (fileHashCode == hashCode && isEOF) {
                    if (input != null) {
                        Util.closeQuietly(input);
                    }
                    return true;
                } else if (input != null) {
                    Util.closeQuietly(input);
                    return false;
                } else {
                    return false;
                }
            } catch (IOException e4) {
                input = input2;
                if (input != null) {
                    Util.closeQuietly(input);
                    return false;
                }
                return false;
            } catch (Throwable th) {
                th = th;
                input = input2;
                if (input != null) {
                    Util.closeQuietly(input);
                }
                throw th;
            }
        } catch (IOException e5) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void writeFile() throws Cache.CacheException {
        GeneralSecurityException e;
        DataOutputStream output = null;
        try {
            try {
                OutputStream outputStream = this.atomicFile.startWrite();
                if (this.bufferedOutputStream == null) {
                    this.bufferedOutputStream = new ReusableBufferedOutputStream(outputStream);
                } else {
                    this.bufferedOutputStream.reset(outputStream);
                }
                DataOutputStream output2 = new DataOutputStream(this.bufferedOutputStream);
                try {
                    output2.writeInt(2);
                    int flags = this.encrypt ? 1 : 0;
                    output2.writeInt(flags);
                    if (this.encrypt) {
                        byte[] initializationVector = new byte[16];
                        new Random().nextBytes(initializationVector);
                        output2.write(initializationVector);
                        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);
                        try {
                            this.cipher.init(1, this.secretKeySpec, ivParameterSpec);
                            output2.flush();
                            output = new DataOutputStream(new CipherOutputStream(this.bufferedOutputStream, this.cipher));
                        } catch (InvalidAlgorithmParameterException e2) {
                            e = e2;
                            throw new IllegalStateException(e);
                        } catch (InvalidKeyException e3) {
                            e = e3;
                            throw new IllegalStateException(e);
                        }
                    } else {
                        output = output2;
                    }
                    output.writeInt(this.keyToContent.size());
                    int hashCode = 0;
                    for (CachedContent cachedContent : this.keyToContent.values()) {
                        cachedContent.writeToStream(output);
                        hashCode += cachedContent.headerHashCode(2);
                    }
                    output.writeInt(hashCode);
                    this.atomicFile.endWrite(output);
                    Util.closeQuietly((Closeable) null);
                } catch (IOException e4) {
                    e = e4;
                    output = output2;
                    throw new Cache.CacheException(e);
                } catch (Throwable th) {
                    th = th;
                    output = output2;
                    Util.closeQuietly(output);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e5) {
            e = e5;
        }
    }

    private CachedContent addNew(String key) {
        int id = getNewId(this.idToKey);
        CachedContent cachedContent = new CachedContent(id, key);
        add(cachedContent);
        this.changed = true;
        return cachedContent;
    }

    private void add(CachedContent cachedContent) {
        this.keyToContent.put(cachedContent.key, cachedContent);
        this.idToKey.put(cachedContent.id, cachedContent.key);
    }

    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (Util.SDK_INT == 18) {
            try {
                return Cipher.getInstance("AES/CBC/PKCS5PADDING", "BC");
            } catch (Throwable th) {
            }
        }
        return Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }

    public static int getNewId(SparseArray<String> idToKey) {
        int size = idToKey.size();
        int id = size == 0 ? 0 : idToKey.keyAt(size - 1) + 1;
        if (id < 0) {
            id = 0;
            while (id < size && id == idToKey.keyAt(id)) {
                id++;
            }
        }
        return id;
    }
}
