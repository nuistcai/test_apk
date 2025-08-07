package okhttp3.internal.cache;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMarkers4;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import okhttp3.internal.Util;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/* compiled from: DiskLruCache.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010)\n\u0002\b\u0007\u0018\u0000 V2\u00020\u00012\u00020\u0002:\u0004VWXYB7\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\b\u00104\u001a\u000205H\u0002J\b\u00106\u001a\u000205H\u0016J!\u00107\u001a\u0002052\n\u00108\u001a\u000609R\u00020\u00002\u0006\u0010:\u001a\u00020\u0012H\u0000¢\u0006\u0002\b;J\u0006\u0010<\u001a\u000205J \u0010=\u001a\b\u0018\u000109R\u00020\u00002\u0006\u0010>\u001a\u00020$2\b\b\u0002\u0010?\u001a\u00020\u000bH\u0007J\u0006\u0010@\u001a\u000205J\b\u0010A\u001a\u000205H\u0016J\u0017\u0010B\u001a\b\u0018\u00010CR\u00020\u00002\u0006\u0010>\u001a\u00020$H\u0086\u0002J\u0006\u0010D\u001a\u000205J\u0006\u0010E\u001a\u00020\u0012J\b\u0010F\u001a\u00020\u0012H\u0002J\b\u0010G\u001a\u00020!H\u0002J\b\u0010H\u001a\u000205H\u0002J\b\u0010I\u001a\u000205H\u0002J\u0010\u0010J\u001a\u0002052\u0006\u0010K\u001a\u00020$H\u0002J\r\u0010L\u001a\u000205H\u0000¢\u0006\u0002\bMJ\u000e\u0010N\u001a\u00020\u00122\u0006\u0010>\u001a\u00020$J\u0019\u0010O\u001a\u00020\u00122\n\u0010P\u001a\u00060%R\u00020\u0000H\u0000¢\u0006\u0002\bQJ\u0006\u00101\u001a\u00020\u000bJ\u0010\u0010R\u001a\f\u0012\b\u0012\u00060CR\u00020\u00000SJ\u0006\u0010T\u001a\u000205J\u0010\u0010U\u001a\u0002052\u0006\u0010>\u001a\u00020$H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0012X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\"\u001a\u0012\u0012\u0004\u0012\u00020$\u0012\b\u0012\u00060%R\u00020\u00000#X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R&\u0010\n\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020\u000b8F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u000e\u0010-\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103¨\u0006Z"}, d2 = {"Lokhttp3/internal/cache/DiskLruCache;", "Ljava/io/Closeable;", "Ljava/io/Flushable;", "fileSystem", "Lokhttp3/internal/io/FileSystem;", "directory", "Ljava/io/File;", "appVersion", "", "valueCount", "maxSize", "", "executor", "Ljava/util/concurrent/Executor;", "(Lokhttp3/internal/io/FileSystem;Ljava/io/File;IIJLjava/util/concurrent/Executor;)V", "cleanupRunnable", "Ljava/lang/Runnable;", "closed", "", "getClosed$okhttp", "()Z", "setClosed$okhttp", "(Z)V", "getDirectory", "()Ljava/io/File;", "getFileSystem$okhttp", "()Lokhttp3/internal/io/FileSystem;", "hasJournalErrors", "initialized", "journalFile", "journalFileBackup", "journalFileTmp", "journalWriter", "Lokio/BufferedSink;", "lruEntries", "Ljava/util/LinkedHashMap;", "", "Lokhttp3/internal/cache/DiskLruCache$Entry;", "getLruEntries$okhttp", "()Ljava/util/LinkedHashMap;", "value", "getMaxSize", "()J", "setMaxSize", "(J)V", "mostRecentRebuildFailed", "mostRecentTrimFailed", "nextSequenceNumber", "redundantOpCount", "size", "getValueCount$okhttp", "()I", "checkNotClosed", "", "close", "completeEdit", "editor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "success", "completeEdit$okhttp", "delete", "edit", "key", "expectedSequenceNumber", "evictAll", "flush", "get", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "initialize", "isClosed", "journalRebuildRequired", "newJournalWriter", "processJournal", "readJournal", "readJournalLine", "line", "rebuildJournal", "rebuildJournal$okhttp", "remove", "removeEntry", "entry", "removeEntry$okhttp", "snapshots", "", "trimToSize", "validateKey", "Companion", "Editor", "Entry", "Snapshot", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class DiskLruCache implements Closeable, Flushable {
    private final int appVersion;
    private final Runnable cleanupRunnable;
    private boolean closed;
    private final File directory;
    private final Executor executor;
    private final FileSystem fileSystem;
    private boolean hasJournalErrors;
    private boolean initialized;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    private BufferedSink journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries;
    private long maxSize;
    private boolean mostRecentRebuildFailed;
    private boolean mostRecentTrimFailed;
    private long nextSequenceNumber;
    private int redundantOpCount;
    private long size;
    private final int valueCount;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String JOURNAL_FILE = "journal";
    public static final String JOURNAL_FILE_TEMP = "journal.tmp";
    public static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    public static final String MAGIC = "libcore.io.DiskLruCache";
    public static final String VERSION_1 = "1";
    public static final long ANY_SEQUENCE_NUMBER = -1;
    public static final Regex LEGAL_KEY_PATTERN = new Regex("[a-z0-9_-]{1,120}");
    public static final String CLEAN = "CLEAN";
    public static final String DIRTY = "DIRTY";
    public static final String REMOVE = "REMOVE";
    public static final String READ = "READ";

    public final Editor edit(String str) throws IOException {
        return edit$default(this, str, 0L, 2, null);
    }

    public DiskLruCache(FileSystem fileSystem, File directory, int appVersion, int valueCount, long maxSize, Executor executor) {
        Intrinsics.checkParameterIsNotNull(fileSystem, "fileSystem");
        Intrinsics.checkParameterIsNotNull(directory, "directory");
        Intrinsics.checkParameterIsNotNull(executor, "executor");
        this.fileSystem = fileSystem;
        this.directory = directory;
        this.appVersion = appVersion;
        this.valueCount = valueCount;
        this.executor = executor;
        this.maxSize = maxSize;
        this.lruEntries = new LinkedHashMap<>(0, 0.75f, true);
        this.cleanupRunnable = new Runnable() { // from class: okhttp3.internal.cache.DiskLruCache$cleanupRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (this.this$0) {
                    if (!this.this$0.initialized || this.this$0.getClosed()) {
                        return;
                    }
                    try {
                        this.this$0.trimToSize();
                    } catch (IOException e) {
                        this.this$0.mostRecentTrimFailed = true;
                    }
                    try {
                    } catch (IOException e2) {
                        this.this$0.mostRecentRebuildFailed = true;
                        this.this$0.journalWriter = Okio.buffer(Okio.blackhole());
                    }
                    if (this.this$0.journalRebuildRequired()) {
                        this.this$0.rebuildJournal$okhttp();
                        this.this$0.redundantOpCount = 0;
                        Unit unit = Unit.INSTANCE;
                        return;
                    }
                    Unit unit2 = Unit.INSTANCE;
                    return;
                }
            }
        };
        this.journalFile = new File(this.directory, JOURNAL_FILE);
        this.journalFileTmp = new File(this.directory, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(this.directory, JOURNAL_FILE_BACKUP);
    }

    /* renamed from: getFileSystem$okhttp, reason: from getter */
    public final FileSystem getFileSystem() {
        return this.fileSystem;
    }

    public final File getDirectory() {
        return this.directory;
    }

    /* renamed from: getValueCount$okhttp, reason: from getter */
    public final int getValueCount() {
        return this.valueCount;
    }

    public final synchronized long getMaxSize() {
        return this.maxSize;
    }

    public final synchronized void setMaxSize(long value) {
        this.maxSize = value;
        if (this.initialized) {
            this.executor.execute(this.cleanupRunnable);
        }
    }

    public final LinkedHashMap<String, Entry> getLruEntries$okhttp() {
        return this.lruEntries;
    }

    /* renamed from: getClosed$okhttp, reason: from getter */
    public final boolean getClosed() {
        return this.closed;
    }

    public final void setClosed$okhttp(boolean z) {
        this.closed = z;
    }

    public final synchronized void initialize() throws IOException {
        if (!Thread.holdsLock(this)) {
            throw new AssertionError("Assertion failed");
        }
        if (this.initialized) {
            return;
        }
        if (this.fileSystem.exists(this.journalFileBackup)) {
            if (this.fileSystem.exists(this.journalFile)) {
                this.fileSystem.delete(this.journalFileBackup);
            } else {
                this.fileSystem.rename(this.journalFileBackup, this.journalFile);
            }
        }
        if (this.fileSystem.exists(this.journalFile)) {
            try {
                readJournal();
                processJournal();
                this.initialized = true;
                return;
            } catch (IOException journalIsCorrupt) {
                Platform.INSTANCE.get().log(5, "DiskLruCache " + this.directory + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing", journalIsCorrupt);
                try {
                    delete();
                    this.closed = false;
                } catch (Throwable th) {
                    this.closed = false;
                    throw th;
                }
            }
        }
        rebuildJournal$okhttp();
        this.initialized = true;
    }

    private final void readJournal() throws IOException {
        BufferedSource bufferedSourceBuffer = Okio.buffer(this.fileSystem.source(this.journalFile));
        try {
            BufferedSource source = bufferedSourceBuffer;
            String magic = source.readUtf8LineStrict();
            String version = source.readUtf8LineStrict();
            String appVersionString = source.readUtf8LineStrict();
            String valueCountString = source.readUtf8LineStrict();
            String blank = source.readUtf8LineStrict();
            if (!(!Intrinsics.areEqual(MAGIC, magic)) && !(!Intrinsics.areEqual(VERSION_1, version)) && !(!Intrinsics.areEqual(String.valueOf(this.appVersion), appVersionString)) && !(!Intrinsics.areEqual(String.valueOf(this.valueCount), valueCountString))) {
                if (!(blank.length() > 0)) {
                    int lineCount = 0;
                    while (true) {
                        try {
                            readJournalLine(source.readUtf8LineStrict());
                            lineCount++;
                        } catch (EOFException e) {
                            this.redundantOpCount = lineCount - this.lruEntries.size();
                            if (!source.exhausted()) {
                                rebuildJournal$okhttp();
                            } else {
                                this.journalWriter = newJournalWriter();
                            }
                            Unit unit = Unit.INSTANCE;
                            kotlin.io.Closeable.closeFinally(bufferedSourceBuffer, null);
                            return;
                        }
                    }
                }
            }
            throw new IOException("unexpected journal header: [" + magic + ", " + version + ", " + valueCountString + ", " + blank + ']');
        } finally {
        }
    }

    private final BufferedSink newJournalWriter() throws FileNotFoundException {
        Sink fileSink = this.fileSystem.appendingSink(this.journalFile);
        FaultHidingSink faultHidingSink = new FaultHidingSink(fileSink, new Function1<IOException, Unit>() { // from class: okhttp3.internal.cache.DiskLruCache$newJournalWriter$faultHidingSink$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IOException iOException) {
                invoke2(iOException);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IOException it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                if (Thread.holdsLock(this.this$0)) {
                    this.this$0.hasJournalErrors = true;
                    return;
                }
                throw new AssertionError("Assertion failed");
            }
        });
        return Okio.buffer(faultHidingSink);
    }

    private final void readJournalLine(String line) throws IOException {
        String key;
        int firstSpace = StringsKt.indexOf$default((CharSequence) line, ' ', 0, false, 6, (Object) null);
        if (firstSpace == -1) {
            throw new IOException("unexpected journal line: " + line);
        }
        int keyBegin = firstSpace + 1;
        int secondSpace = StringsKt.indexOf$default((CharSequence) line, ' ', keyBegin, false, 4, (Object) null);
        if (secondSpace == -1) {
            if (line == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String strSubstring = line.substring(keyBegin);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
            key = strSubstring;
            if (firstSpace == REMOVE.length() && StringsKt.startsWith$default(line, REMOVE, false, 2, (Object) null)) {
                this.lruEntries.remove(key);
                return;
            }
        } else {
            if (line == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String strSubstring2 = line.substring(keyBegin, secondSpace);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            key = strSubstring2;
        }
        Entry entry = this.lruEntries.get(key);
        if (entry == null) {
            entry = new Entry(this, key);
            this.lruEntries.put(key, entry);
        }
        if (secondSpace == -1 || firstSpace != CLEAN.length() || !StringsKt.startsWith$default(line, CLEAN, false, 2, (Object) null)) {
            if (secondSpace == -1 && firstSpace == DIRTY.length() && StringsKt.startsWith$default(line, DIRTY, false, 2, (Object) null)) {
                entry.setCurrentEditor$okhttp(new Editor(this, entry));
                return;
            } else {
                if (secondSpace != -1 || firstSpace != READ.length() || !StringsKt.startsWith$default(line, READ, false, 2, (Object) null)) {
                    throw new IOException("unexpected journal line: " + line);
                }
                return;
            }
        }
        int i = secondSpace + 1;
        if (line == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String strSubstring3 = line.substring(i);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring3, "(this as java.lang.String).substring(startIndex)");
        List parts = StringsKt.split$default((CharSequence) strSubstring3, new char[]{' '}, false, 0, 6, (Object) null);
        entry.setReadable$okhttp(true);
        entry.setCurrentEditor$okhttp(null);
        entry.setLengths$okhttp(parts);
    }

    private final void processJournal() throws IOException {
        this.fileSystem.delete(this.journalFileTmp);
        Iterator i = this.lruEntries.values().iterator();
        while (i.hasNext()) {
            Entry next = i.next();
            Intrinsics.checkExpressionValueIsNotNull(next, "i.next()");
            Entry entry = next;
            int t = 0;
            if (entry.getCurrentEditor() == null) {
                int i2 = this.valueCount;
                while (t < i2) {
                    this.size += entry.getLengths()[t];
                    t++;
                }
            } else {
                entry.setCurrentEditor$okhttp(null);
                int i3 = this.valueCount;
                while (t < i3) {
                    this.fileSystem.delete(entry.getCleanFiles$okhttp().get(t));
                    this.fileSystem.delete(entry.getDirtyFiles$okhttp().get(t));
                    t++;
                }
                i.remove();
            }
        }
    }

    public final synchronized void rebuildJournal$okhttp() throws IOException {
        BufferedSink bufferedSink = this.journalWriter;
        if (bufferedSink != null) {
            bufferedSink.close();
        }
        BufferedSink bufferedSinkBuffer = Okio.buffer(this.fileSystem.sink(this.journalFileTmp));
        try {
            BufferedSink sink = bufferedSinkBuffer;
            sink.writeUtf8(MAGIC).writeByte(10);
            sink.writeUtf8(VERSION_1).writeByte(10);
            sink.writeDecimalLong(this.appVersion).writeByte(10);
            sink.writeDecimalLong(this.valueCount).writeByte(10);
            sink.writeByte(10);
            for (Entry entry : this.lruEntries.values()) {
                if (entry.getCurrentEditor() != null) {
                    sink.writeUtf8(DIRTY).writeByte(32);
                    sink.writeUtf8(entry.getKey());
                    sink.writeByte(10);
                } else {
                    sink.writeUtf8(CLEAN).writeByte(32);
                    sink.writeUtf8(entry.getKey());
                    entry.writeLengths$okhttp(sink);
                    sink.writeByte(10);
                }
            }
            Unit unit = Unit.INSTANCE;
            kotlin.io.Closeable.closeFinally(bufferedSinkBuffer, null);
            if (this.fileSystem.exists(this.journalFile)) {
                this.fileSystem.rename(this.journalFile, this.journalFileBackup);
            }
            this.fileSystem.rename(this.journalFileTmp, this.journalFile);
            this.fileSystem.delete(this.journalFileBackup);
            this.journalWriter = newJournalWriter();
            this.hasJournalErrors = false;
            this.mostRecentRebuildFailed = false;
        } finally {
        }
    }

    public final synchronized Snapshot get(String key) throws IOException {
        Intrinsics.checkParameterIsNotNull(key, "key");
        initialize();
        checkNotClosed();
        validateKey(key);
        Entry entry = this.lruEntries.get(key);
        if (entry == null) {
            return null;
        }
        Intrinsics.checkExpressionValueIsNotNull(entry, "lruEntries[key] ?: return null");
        if (!entry.getReadable()) {
            return null;
        }
        Snapshot snapshot = entry.snapshot$okhttp();
        if (snapshot == null) {
            return null;
        }
        this.redundantOpCount++;
        BufferedSink bufferedSink = this.journalWriter;
        if (bufferedSink == null) {
            Intrinsics.throwNpe();
        }
        bufferedSink.writeUtf8(READ).writeByte(32).writeUtf8(key).writeByte(10);
        if (journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return snapshot;
    }

    public static /* synthetic */ Editor edit$default(DiskLruCache diskLruCache, String str, long j, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            j = ANY_SEQUENCE_NUMBER;
        }
        return diskLruCache.edit(str, j);
    }

    public final synchronized Editor edit(String key, long expectedSequenceNumber) throws IOException {
        Intrinsics.checkParameterIsNotNull(key, "key");
        initialize();
        checkNotClosed();
        validateKey(key);
        Entry entry = this.lruEntries.get(key);
        if (expectedSequenceNumber != ANY_SEQUENCE_NUMBER && (entry == null || entry.getSequenceNumber() != expectedSequenceNumber)) {
            return null;
        }
        if ((entry != null ? entry.getCurrentEditor() : null) != null) {
            return null;
        }
        if (!this.mostRecentTrimFailed && !this.mostRecentRebuildFailed) {
            BufferedSink journalWriter = this.journalWriter;
            if (journalWriter == null) {
                Intrinsics.throwNpe();
            }
            journalWriter.writeUtf8(DIRTY).writeByte(32).writeUtf8(key).writeByte(10);
            journalWriter.flush();
            if (this.hasJournalErrors) {
                return null;
            }
            if (entry == null) {
                entry = new Entry(this, key);
                this.lruEntries.put(key, entry);
            }
            Editor editor = new Editor(this, entry);
            entry.setCurrentEditor$okhttp(editor);
            return editor;
        }
        this.executor.execute(this.cleanupRunnable);
        return null;
    }

    public final synchronized long size() throws IOException {
        initialize();
        return this.size;
    }

    public final synchronized void completeEdit$okhttp(Editor editor, boolean success) throws IOException {
        Intrinsics.checkParameterIsNotNull(editor, "editor");
        Entry entry = editor.getEntry();
        if (!Intrinsics.areEqual(entry.getCurrentEditor(), editor)) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (success && !entry.getReadable()) {
            int i = this.valueCount;
            for (int i2 = 0; i2 < i; i2++) {
                boolean[] written = editor.getWritten();
                if (written == null) {
                    Intrinsics.throwNpe();
                }
                if (!written[i2]) {
                    editor.abort();
                    throw new IllegalStateException("Newly created entry didn't create value for index " + i2);
                }
                if (!this.fileSystem.exists(entry.getDirtyFiles$okhttp().get(i2))) {
                    editor.abort();
                    return;
                }
            }
        }
        int i3 = this.valueCount;
        for (int i4 = 0; i4 < i3; i4++) {
            File dirty = entry.getDirtyFiles$okhttp().get(i4);
            if (success) {
                if (this.fileSystem.exists(dirty)) {
                    File clean = entry.getCleanFiles$okhttp().get(i4);
                    this.fileSystem.rename(dirty, clean);
                    long oldLength = entry.getLengths()[i4];
                    long newLength = this.fileSystem.size(clean);
                    entry.getLengths()[i4] = newLength;
                    this.size = (this.size - oldLength) + newLength;
                }
            } else {
                this.fileSystem.delete(dirty);
            }
        }
        this.redundantOpCount++;
        entry.setCurrentEditor$okhttp(null);
        BufferedSink $this$apply = this.journalWriter;
        if ($this$apply == null) {
            Intrinsics.throwNpe();
        }
        if (entry.getReadable() || success) {
            entry.setReadable$okhttp(true);
            $this$apply.writeUtf8(CLEAN).writeByte(32);
            $this$apply.writeUtf8(entry.getKey());
            entry.writeLengths$okhttp($this$apply);
            $this$apply.writeByte(10);
            if (success) {
                long j = this.nextSequenceNumber;
                this.nextSequenceNumber = 1 + j;
                entry.setSequenceNumber$okhttp(j);
            }
        } else {
            this.lruEntries.remove(entry.getKey());
            $this$apply.writeUtf8(REMOVE).writeByte(32);
            $this$apply.writeUtf8(entry.getKey());
            $this$apply.writeByte(10);
        }
        $this$apply.flush();
        if (this.size > this.maxSize || journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean journalRebuildRequired() {
        return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
    }

    public final synchronized boolean remove(String key) throws IOException {
        Intrinsics.checkParameterIsNotNull(key, "key");
        initialize();
        checkNotClosed();
        validateKey(key);
        Entry entry = this.lruEntries.get(key);
        if (entry == null) {
            return false;
        }
        Intrinsics.checkExpressionValueIsNotNull(entry, "lruEntries[key] ?: return false");
        boolean removed = removeEntry$okhttp(entry);
        if (removed && this.size <= this.maxSize) {
            this.mostRecentTrimFailed = false;
        }
        return removed;
    }

    public final boolean removeEntry$okhttp(Entry entry) throws IOException {
        Intrinsics.checkParameterIsNotNull(entry, "entry");
        Editor currentEditor = entry.getCurrentEditor();
        if (currentEditor != null) {
            currentEditor.detach$okhttp();
        }
        int i = this.valueCount;
        for (int i2 = 0; i2 < i; i2++) {
            this.fileSystem.delete(entry.getCleanFiles$okhttp().get(i2));
            this.size -= entry.getLengths()[i2];
            entry.getLengths()[i2] = 0;
        }
        this.redundantOpCount++;
        BufferedSink bufferedSink = this.journalWriter;
        if (bufferedSink == null) {
            Intrinsics.throwNpe();
        }
        bufferedSink.writeUtf8(REMOVE).writeByte(32).writeUtf8(entry.getKey()).writeByte(10);
        this.lruEntries.remove(entry.getKey());
        if (journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return true;
    }

    private final synchronized void checkNotClosed() {
        if (!(!this.closed)) {
            throw new IllegalStateException("cache is closed".toString());
        }
    }

    @Override // java.io.Flushable
    public synchronized void flush() throws IOException {
        if (this.initialized) {
            checkNotClosed();
            trimToSize();
            BufferedSink bufferedSink = this.journalWriter;
            if (bufferedSink == null) {
                Intrinsics.throwNpe();
            }
            bufferedSink.flush();
        }
    }

    public final synchronized boolean isClosed() {
        return this.closed;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        if (this.initialized && !this.closed) {
            Collection $this$toTypedArray$iv = this.lruEntries.values();
            Intrinsics.checkExpressionValueIsNotNull($this$toTypedArray$iv, "lruEntries.values");
            Object[] array = $this$toTypedArray$iv.toArray(new Entry[0]);
            if (array != null) {
                for (Entry entry : (Entry[]) array) {
                    if (entry.getCurrentEditor() != null) {
                        Editor currentEditor = entry.getCurrentEditor();
                        if (currentEditor == null) {
                            Intrinsics.throwNpe();
                        }
                        currentEditor.abort();
                    }
                }
                trimToSize();
                BufferedSink bufferedSink = this.journalWriter;
                if (bufferedSink == null) {
                    Intrinsics.throwNpe();
                }
                bufferedSink.close();
                this.journalWriter = null;
                this.closed = true;
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        this.closed = true;
    }

    public final void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            Entry next = this.lruEntries.values().iterator().next();
            Intrinsics.checkExpressionValueIsNotNull(next, "lruEntries.values.iterator().next()");
            Entry toEvict = next;
            removeEntry$okhttp(toEvict);
        }
        this.mostRecentTrimFailed = false;
    }

    public final void delete() throws IOException {
        close();
        this.fileSystem.deleteContents(this.directory);
    }

    public final synchronized void evictAll() throws IOException {
        initialize();
        Collection $this$toTypedArray$iv = this.lruEntries.values();
        Intrinsics.checkExpressionValueIsNotNull($this$toTypedArray$iv, "lruEntries.values");
        Object[] array = $this$toTypedArray$iv.toArray(new Entry[0]);
        if (array != null) {
            for (Entry entry : (Entry[]) array) {
                Intrinsics.checkExpressionValueIsNotNull(entry, "entry");
                removeEntry$okhttp(entry);
            }
            this.mostRecentTrimFailed = false;
        } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
    }

    private final void validateKey(String key) {
        if (!LEGAL_KEY_PATTERN.matches(key)) {
            throw new IllegalArgumentException(("keys must match regex [a-z0-9_-]{1,120}: \"" + key + Typography.quote).toString());
        }
    }

    /* compiled from: DiskLruCache.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000)\n\u0000\n\u0002\u0010)\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00030\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u0096\u0002J\r\u0010\u0013\u001a\u00060\u0002R\u00020\u0003H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016R'\u0010\u0004\u001a\u0018\u0012\u0014\u0012\u0012 \u0006*\b\u0018\u00010\u0005R\u00020\u00030\u0005R\u00020\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR \u0010\t\u001a\b\u0018\u00010\u0002R\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR \u0010\u000e\u001a\b\u0018\u00010\u0002R\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\r¨\u0006\u0016"}, d2 = {"okhttp3/internal/cache/DiskLruCache$snapshots$1", "", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Lokhttp3/internal/cache/DiskLruCache;", "delegate", "Lokhttp3/internal/cache/DiskLruCache$Entry;", "kotlin.jvm.PlatformType", "getDelegate", "()Ljava/util/Iterator;", "nextSnapshot", "getNextSnapshot", "()Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "setNextSnapshot", "(Lokhttp3/internal/cache/DiskLruCache$Snapshot;)V", "removeSnapshot", "getRemoveSnapshot", "setRemoveSnapshot", "hasNext", "", "next", "remove", "", "okhttp"}, k = 1, mv = {1, 1, 15})
    /* renamed from: okhttp3.internal.cache.DiskLruCache$snapshots$1, reason: invalid class name */
    public static final class AnonymousClass1 implements Iterator<Snapshot>, KMarkers4 {
        private final Iterator<Entry> delegate;
        private Snapshot nextSnapshot;
        private Snapshot removeSnapshot;

        AnonymousClass1() {
            Iterator<Entry> it = new ArrayList(DiskLruCache.this.getLruEntries$okhttp().values()).iterator();
            Intrinsics.checkExpressionValueIsNotNull(it, "ArrayList(lruEntries.values).iterator()");
            this.delegate = it;
        }

        public final Iterator<Entry> getDelegate() {
            return this.delegate;
        }

        public final Snapshot getNextSnapshot() {
            return this.nextSnapshot;
        }

        public final void setNextSnapshot(Snapshot snapshot) {
            this.nextSnapshot = snapshot;
        }

        public final Snapshot getRemoveSnapshot() {
            return this.removeSnapshot;
        }

        public final void setRemoveSnapshot(Snapshot snapshot) {
            this.removeSnapshot = snapshot;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            Snapshot snapshot;
            if (this.nextSnapshot != null) {
                return true;
            }
            synchronized (DiskLruCache.this) {
                if (DiskLruCache.this.getClosed()) {
                    return false;
                }
                while (this.delegate.hasNext()) {
                    Entry entry = this.delegate.next();
                    if (entry != null && entry.getReadable() && (snapshot = entry.snapshot$okhttp()) != null) {
                        this.nextSnapshot = snapshot;
                        return true;
                    }
                }
                Unit unit = Unit.INSTANCE;
                return false;
            }
        }

        @Override // java.util.Iterator
        public Snapshot next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.removeSnapshot = this.nextSnapshot;
            this.nextSnapshot = null;
            Snapshot snapshot = this.removeSnapshot;
            if (snapshot == null) {
                Intrinsics.throwNpe();
            }
            return snapshot;
        }

        @Override // java.util.Iterator
        public void remove() {
            Snapshot removeSnapshot = this.removeSnapshot;
            if (removeSnapshot == null) {
                throw new IllegalStateException("remove() before next()".toString());
            }
            try {
                DiskLruCache.this.remove(removeSnapshot.getKey());
            } catch (IOException e) {
            } catch (Throwable th) {
                this.removeSnapshot = null;
                throw th;
            }
            this.removeSnapshot = null;
        }
    }

    public final synchronized Iterator<Snapshot> snapshots() throws IOException {
        initialize();
        return new AnonymousClass1();
    }

    /* compiled from: DiskLruCache.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0016\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B-\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0016J\f\u0010\u000e\u001a\b\u0018\u00010\u000fR\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0002\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "Ljava/io/Closeable;", "key", "", "sequenceNumber", "", "sources", "", "Lokio/Source;", "lengths", "", "(Lokhttp3/internal/cache/DiskLruCache;Ljava/lang/String;JLjava/util/List;[J)V", "close", "", "edit", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Lokhttp3/internal/cache/DiskLruCache;", "getLength", "index", "", "getSource", "okhttp"}, k = 1, mv = {1, 1, 15})
    public final class Snapshot implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final List<Source> sources;
        final /* synthetic */ DiskLruCache this$0;

        /* JADX WARN: Multi-variable type inference failed */
        public Snapshot(DiskLruCache $outer, String key, long sequenceNumber, List<? extends Source> sources, long[] lengths) {
            Intrinsics.checkParameterIsNotNull(key, "key");
            Intrinsics.checkParameterIsNotNull(sources, "sources");
            Intrinsics.checkParameterIsNotNull(lengths, "lengths");
            this.this$0 = $outer;
            this.key = key;
            this.sequenceNumber = sequenceNumber;
            this.sources = sources;
            this.lengths = lengths;
        }

        /* renamed from: key, reason: from getter */
        public final String getKey() {
            return this.key;
        }

        public final Editor edit() throws IOException {
            return this.this$0.edit(this.key, this.sequenceNumber);
        }

        public final Source getSource(int index) {
            return this.sources.get(index);
        }

        public final long getLength(int index) {
            return this.lengths[index];
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            for (Source source : this.sources) {
                Util.closeQuietly(source);
            }
        }
    }

    /* compiled from: DiskLruCache.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0018\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0013\b\u0000\u0012\n\u0010\u0002\u001a\u00060\u0003R\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\r\u0010\u0011\u001a\u00020\u000fH\u0000¢\u0006\u0002\b\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0002\u001a\u00060\u0003R\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lokhttp3/internal/cache/DiskLruCache$Editor;", "", "entry", "Lokhttp3/internal/cache/DiskLruCache$Entry;", "Lokhttp3/internal/cache/DiskLruCache;", "(Lokhttp3/internal/cache/DiskLruCache;Lokhttp3/internal/cache/DiskLruCache$Entry;)V", "done", "", "getEntry$okhttp", "()Lokhttp3/internal/cache/DiskLruCache$Entry;", "written", "", "getWritten$okhttp", "()[Z", "abort", "", "commit", "detach", "detach$okhttp", "newSink", "Lokio/Sink;", "index", "", "newSource", "Lokio/Source;", "okhttp"}, k = 1, mv = {1, 1, 15})
    public final class Editor {
        private boolean done;
        private final Entry entry;
        final /* synthetic */ DiskLruCache this$0;
        private final boolean[] written;

        public Editor(DiskLruCache $outer, Entry entry) {
            Intrinsics.checkParameterIsNotNull(entry, "entry");
            this.this$0 = $outer;
            this.entry = entry;
            this.written = this.entry.getReadable() ? null : new boolean[$outer.getValueCount()];
        }

        /* renamed from: getEntry$okhttp, reason: from getter */
        public final Entry getEntry() {
            return this.entry;
        }

        /* renamed from: getWritten$okhttp, reason: from getter */
        public final boolean[] getWritten() {
            return this.written;
        }

        public final void detach$okhttp() {
            if (Intrinsics.areEqual(this.entry.getCurrentEditor(), this)) {
                int valueCount = this.this$0.getValueCount();
                for (int i = 0; i < valueCount; i++) {
                    try {
                        this.this$0.getFileSystem().delete(this.entry.getDirtyFiles$okhttp().get(i));
                    } catch (IOException e) {
                    }
                }
                this.entry.setCurrentEditor$okhttp(null);
            }
        }

        public final Source newSource(int index) {
            synchronized (this.this$0) {
                if (!(!this.done)) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                Source source = null;
                if (!this.entry.getReadable() || (!Intrinsics.areEqual(this.entry.getCurrentEditor(), this))) {
                    return null;
                }
                try {
                    source = this.this$0.getFileSystem().source(this.entry.getCleanFiles$okhttp().get(index));
                } catch (FileNotFoundException e) {
                }
                return source;
            }
        }

        public final Sink newSink(final int index) {
            synchronized (this.this$0) {
                if (!(!this.done)) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                if (!Intrinsics.areEqual(this.entry.getCurrentEditor(), this)) {
                    return Okio.blackhole();
                }
                if (!this.entry.getReadable()) {
                    boolean[] zArr = this.written;
                    if (zArr == null) {
                        Intrinsics.throwNpe();
                    }
                    zArr[index] = true;
                }
                File dirtyFile = this.entry.getDirtyFiles$okhttp().get(index);
                try {
                    Sink sink = this.this$0.getFileSystem().sink(dirtyFile);
                    return new FaultHidingSink(sink, new Function1<IOException, Unit>() { // from class: okhttp3.internal.cache.DiskLruCache$Editor$newSink$$inlined$synchronized$lambda$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(IOException iOException) {
                            invoke2(iOException);
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(IOException it) {
                            Intrinsics.checkParameterIsNotNull(it, "it");
                            synchronized (this.this$0.this$0) {
                                this.this$0.detach$okhttp();
                                Unit unit = Unit.INSTANCE;
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    return Okio.blackhole();
                }
            }
        }

        public final void commit() throws IOException {
            synchronized (this.this$0) {
                if (!(!this.done)) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                if (Intrinsics.areEqual(this.entry.getCurrentEditor(), this)) {
                    this.this$0.completeEdit$okhttp(this, true);
                }
                this.done = true;
                Unit unit = Unit.INSTANCE;
            }
        }

        public final void abort() throws IOException {
            synchronized (this.this$0) {
                if (!(!this.done)) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                if (Intrinsics.areEqual(this.entry.getCurrentEditor(), this)) {
                    this.this$0.completeEdit$okhttp(this, false);
                }
                this.done = true;
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* compiled from: DiskLruCache.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0016\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0080\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010%\u001a\u00020&2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00030(H\u0002J\u001b\u0010)\u001a\u00020*2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00030(H\u0000¢\u0006\u0002\b+J\u0013\u0010,\u001a\b\u0018\u00010-R\u00020\fH\u0000¢\u0006\u0002\b.J\u0015\u0010/\u001a\u00020*2\u0006\u00100\u001a\u000201H\u0000¢\u0006\u0002\b2R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR \u0010\n\u001a\b\u0018\u00010\u000bR\u00020\fX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u0016X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u00063"}, d2 = {"Lokhttp3/internal/cache/DiskLruCache$Entry;", "", "key", "", "(Lokhttp3/internal/cache/DiskLruCache;Ljava/lang/String;)V", "cleanFiles", "", "Ljava/io/File;", "getCleanFiles$okhttp", "()Ljava/util/List;", "currentEditor", "Lokhttp3/internal/cache/DiskLruCache$Editor;", "Lokhttp3/internal/cache/DiskLruCache;", "getCurrentEditor$okhttp", "()Lokhttp3/internal/cache/DiskLruCache$Editor;", "setCurrentEditor$okhttp", "(Lokhttp3/internal/cache/DiskLruCache$Editor;)V", "dirtyFiles", "getDirtyFiles$okhttp", "getKey$okhttp", "()Ljava/lang/String;", "lengths", "", "getLengths$okhttp", "()[J", "readable", "", "getReadable$okhttp", "()Z", "setReadable$okhttp", "(Z)V", "sequenceNumber", "", "getSequenceNumber$okhttp", "()J", "setSequenceNumber$okhttp", "(J)V", "invalidLengths", "Ljava/io/IOException;", "strings", "", "setLengths", "", "setLengths$okhttp", "snapshot", "Lokhttp3/internal/cache/DiskLruCache$Snapshot;", "snapshot$okhttp", "writeLengths", "writer", "Lokio/BufferedSink;", "writeLengths$okhttp", "okhttp"}, k = 1, mv = {1, 1, 15})
    public final class Entry {
        private final List<File> cleanFiles;
        private Editor currentEditor;
        private final List<File> dirtyFiles;
        private final String key;
        private final long[] lengths;
        private boolean readable;
        private long sequenceNumber;
        final /* synthetic */ DiskLruCache this$0;

        public Entry(DiskLruCache $outer, String key) {
            Intrinsics.checkParameterIsNotNull(key, "key");
            this.this$0 = $outer;
            this.key = key;
            this.lengths = new long[$outer.getValueCount()];
            this.cleanFiles = new ArrayList();
            this.dirtyFiles = new ArrayList();
            StringBuilder fileBuilder = new StringBuilder(this.key).append('.');
            int truncateTo = fileBuilder.length();
            int valueCount = $outer.getValueCount();
            for (int i = 0; i < valueCount; i++) {
                fileBuilder.append(i);
                this.cleanFiles.add(new File($outer.getDirectory(), fileBuilder.toString()));
                fileBuilder.append(".tmp");
                this.dirtyFiles.add(new File($outer.getDirectory(), fileBuilder.toString()));
                fileBuilder.setLength(truncateTo);
            }
        }

        /* renamed from: getKey$okhttp, reason: from getter */
        public final String getKey() {
            return this.key;
        }

        /* renamed from: getLengths$okhttp, reason: from getter */
        public final long[] getLengths() {
            return this.lengths;
        }

        public final List<File> getCleanFiles$okhttp() {
            return this.cleanFiles;
        }

        public final List<File> getDirtyFiles$okhttp() {
            return this.dirtyFiles;
        }

        /* renamed from: getReadable$okhttp, reason: from getter */
        public final boolean getReadable() {
            return this.readable;
        }

        public final void setReadable$okhttp(boolean z) {
            this.readable = z;
        }

        /* renamed from: getCurrentEditor$okhttp, reason: from getter */
        public final Editor getCurrentEditor() {
            return this.currentEditor;
        }

        public final void setCurrentEditor$okhttp(Editor editor) {
            this.currentEditor = editor;
        }

        /* renamed from: getSequenceNumber$okhttp, reason: from getter */
        public final long getSequenceNumber() {
            return this.sequenceNumber;
        }

        public final void setSequenceNumber$okhttp(long j) {
            this.sequenceNumber = j;
        }

        public final void setLengths$okhttp(List<String> strings) throws IOException {
            Intrinsics.checkParameterIsNotNull(strings, "strings");
            if (strings.size() != this.this$0.getValueCount()) {
                throw invalidLengths(strings);
            }
            try {
                int size = strings.size();
                for (int i = 0; i < size; i++) {
                    this.lengths[i] = Long.parseLong(strings.get(i));
                }
            } catch (NumberFormatException e) {
                throw invalidLengths(strings);
            }
        }

        public final void writeLengths$okhttp(BufferedSink writer) throws IOException {
            Intrinsics.checkParameterIsNotNull(writer, "writer");
            for (long length : this.lengths) {
                writer.writeByte(32).writeDecimalLong(length);
            }
        }

        private final IOException invalidLengths(List<String> strings) throws IOException {
            throw new IOException("unexpected journal line: " + strings);
        }

        public final Snapshot snapshot$okhttp() throws IOException {
            if (!Thread.holdsLock(this.this$0)) {
                throw new AssertionError("Assertion failed");
            }
            List<Source> sources = new ArrayList();
            long[] lengths = (long[]) this.lengths.clone();
            try {
                int valueCount = this.this$0.getValueCount();
                for (int i = 0; i < valueCount; i++) {
                    sources.add(this.this$0.getFileSystem().source(this.cleanFiles.get(i)));
                }
                return new Snapshot(this.this$0, this.key, this.sequenceNumber, sources, lengths);
            } catch (FileNotFoundException e) {
                for (Source source : sources) {
                    Util.closeQuietly(source);
                }
                try {
                    this.this$0.removeEntry$okhttp(this);
                    return null;
                } catch (IOException e2) {
                    return null;
                }
            }
        }
    }

    /* compiled from: DiskLruCache.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00068\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00068\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00068\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00068\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00068\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u00068\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u00068\u0006X\u0087D¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u00068\u0006X\u0087D¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lokhttp3/internal/cache/DiskLruCache$Companion;", "", "()V", "ANY_SEQUENCE_NUMBER", "", "CLEAN", "", "DIRTY", "JOURNAL_FILE", "JOURNAL_FILE_BACKUP", "JOURNAL_FILE_TEMP", "LEGAL_KEY_PATTERN", "Lkotlin/text/Regex;", "MAGIC", "READ", "REMOVE", "VERSION_1", "create", "Lokhttp3/internal/cache/DiskLruCache;", "fileSystem", "Lokhttp3/internal/io/FileSystem;", "directory", "Ljava/io/File;", "appVersion", "", "valueCount", "maxSize", "okhttp"}, k = 1, mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final DiskLruCache create(FileSystem fileSystem, File directory, int appVersion, int valueCount, long maxSize) {
            Intrinsics.checkParameterIsNotNull(fileSystem, "fileSystem");
            Intrinsics.checkParameterIsNotNull(directory, "directory");
            if (!(maxSize > 0)) {
                throw new IllegalArgumentException("maxSize <= 0".toString());
            }
            if (!(valueCount > 0)) {
                throw new IllegalArgumentException("valueCount <= 0".toString());
            }
            ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true));
            return new DiskLruCache(fileSystem, directory, appVersion, valueCount, maxSize, executor);
        }
    }
}