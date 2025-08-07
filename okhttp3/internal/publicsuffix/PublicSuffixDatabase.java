package okhttp3.internal.publicsuffix;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.IDN;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.Closeable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;

/* compiled from: PublicSuffixDatabase.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0010\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\fJ\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\u0016\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "", "()V", "listRead", "Ljava/util/concurrent/atomic/AtomicBoolean;", "publicSuffixExceptionListBytes", "", "publicSuffixListBytes", "readCompleteLatch", "Ljava/util/concurrent/CountDownLatch;", "findMatchingRule", "", "", "domainLabels", "getEffectiveTldPlusOne", "domain", "readTheList", "", "readTheListUninterruptibly", "setListBytes", "Companion", "okhttp"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class PublicSuffixDatabase {
    private static final char EXCEPTION_MARKER = '!';
    public static final String PUBLIC_SUFFIX_RESOURCE = "publicsuffixes.gz";
    private byte[] publicSuffixExceptionListBytes;
    private byte[] publicSuffixListBytes;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final byte[] WILDCARD_LABEL = {(byte) 42};
    private static final List<String> PREVAILING_RULE = CollectionsKt.listOf("*");
    private static final PublicSuffixDatabase instance = new PublicSuffixDatabase();
    private final AtomicBoolean listRead = new AtomicBoolean(false);
    private final CountDownLatch readCompleteLatch = new CountDownLatch(1);

    public static final /* synthetic */ byte[] access$getPublicSuffixListBytes$p(PublicSuffixDatabase $this) {
        byte[] bArr = $this.publicSuffixListBytes;
        if (bArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
        }
        return bArr;
    }

    public final String getEffectiveTldPlusOne(String domain) throws InterruptedException {
        int firstLabelOffset;
        Intrinsics.checkParameterIsNotNull(domain, "domain");
        String unicodeDomain = IDN.toUnicode(domain);
        Intrinsics.checkExpressionValueIsNotNull(unicodeDomain, "unicodeDomain");
        List domainLabels = StringsKt.split$default((CharSequence) unicodeDomain, new char[]{'.'}, false, 0, 6, (Object) null);
        List rule = findMatchingRule(domainLabels);
        if (domainLabels.size() == rule.size() && rule.get(0).charAt(0) != '!') {
            return null;
        }
        if (rule.get(0).charAt(0) == '!') {
            firstLabelOffset = domainLabels.size() - rule.size();
        } else {
            firstLabelOffset = domainLabels.size() - (rule.size() + 1);
        }
        return SequencesKt.joinToString$default(SequencesKt.drop(CollectionsKt.asSequence(StringsKt.split$default((CharSequence) domain, new char[]{'.'}, false, 0, 6, (Object) null)), firstLabelOffset), ".", null, null, 0, null, null, 62, null);
    }

    private final List<String> findMatchingRule(List<String> domainLabels) throws InterruptedException {
        List exactRuleLabels;
        List wildcardRuleLabels;
        if (!this.listRead.get() && this.listRead.compareAndSet(false, true)) {
            readTheListUninterruptibly();
        } else {
            try {
                this.readCompleteLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!(this.publicSuffixListBytes != null)) {
            throw new IllegalStateException("Unable to load publicsuffixes.gz resource from the classpath.".toString());
        }
        int size = domainLabels.size();
        byte[][] bArr = new byte[size][];
        for (int i = 0; i < size; i++) {
            int i2 = i;
            String str = domainLabels.get(i2);
            Charset UTF_8 = StandardCharsets.UTF_8;
            Intrinsics.checkExpressionValueIsNotNull(UTF_8, "UTF_8");
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            byte[] bytes = str.getBytes(UTF_8);
            Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
            bArr[i] = bytes;
        }
        byte[][] domainLabelsUtf8Bytes = bArr;
        String exactMatch = (String) null;
        int length = domainLabelsUtf8Bytes.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            Companion companion = INSTANCE;
            byte[] bArr2 = this.publicSuffixListBytes;
            if (bArr2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
            }
            String rule = companion.binarySearch(bArr2, domainLabelsUtf8Bytes, i3);
            if (rule != null) {
                exactMatch = rule;
                break;
            }
            i3++;
        }
        String wildcardMatch = (String) null;
        if (domainLabelsUtf8Bytes.length > 1) {
            byte[][] labelsWithWildcard = (byte[][]) domainLabelsUtf8Bytes.clone();
            int length2 = labelsWithWildcard.length - 1;
            int labelIndex = 0;
            while (true) {
                if (labelIndex >= length2) {
                    break;
                }
                labelsWithWildcard[labelIndex] = WILDCARD_LABEL;
                Companion companion2 = INSTANCE;
                byte[] bArr3 = this.publicSuffixListBytes;
                if (bArr3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("publicSuffixListBytes");
                }
                String rule2 = companion2.binarySearch(bArr3, labelsWithWildcard, labelIndex);
                if (rule2 != null) {
                    wildcardMatch = rule2;
                    break;
                }
                labelIndex++;
            }
        }
        String exception = (String) null;
        if (wildcardMatch != null) {
            int length3 = domainLabelsUtf8Bytes.length - 1;
            int labelIndex2 = 0;
            while (true) {
                if (labelIndex2 >= length3) {
                    break;
                }
                Companion companion3 = INSTANCE;
                byte[] bArr4 = this.publicSuffixExceptionListBytes;
                if (bArr4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("publicSuffixExceptionListBytes");
                }
                String rule3 = companion3.binarySearch(bArr4, domainLabelsUtf8Bytes, labelIndex2);
                if (rule3 != null) {
                    exception = rule3;
                    break;
                }
                labelIndex2++;
            }
        }
        if (exception != null) {
            return StringsKt.split$default((CharSequence) (EXCEPTION_MARKER + exception), new char[]{'.'}, false, 0, 6, (Object) null);
        }
        if (exactMatch == null && wildcardMatch == null) {
            return PREVAILING_RULE;
        }
        if (exactMatch == null || (exactRuleLabels = StringsKt.split$default((CharSequence) exactMatch, new char[]{'.'}, false, 0, 6, (Object) null)) == null) {
            exactRuleLabels = CollectionsKt.emptyList();
        }
        if (wildcardMatch == null || (wildcardRuleLabels = StringsKt.split$default((CharSequence) wildcardMatch, new char[]{'.'}, false, 0, 6, (Object) null)) == null) {
            wildcardRuleLabels = CollectionsKt.emptyList();
        }
        if (exactRuleLabels.size() > wildcardRuleLabels.size()) {
            return exactRuleLabels;
        }
        return wildcardRuleLabels;
    }

    /* compiled from: PublicSuffixDatabase.kt */
    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 1, 15})
    /* renamed from: okhttp3.internal.publicsuffix.PublicSuffixDatabase$findMatchingRule$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends MutablePropertyReference0 {
        AnonymousClass1(PublicSuffixDatabase publicSuffixDatabase) {
            super(publicSuffixDatabase);
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public String getName() {
            return "publicSuffixListBytes";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(PublicSuffixDatabase.class);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public String getSignature() {
            return "getPublicSuffixListBytes()[B";
        }

        @Override // kotlin.reflect.KProperty6
        public Object get() {
            return PublicSuffixDatabase.access$getPublicSuffixListBytes$p((PublicSuffixDatabase) this.receiver);
        }

        @Override // kotlin.reflect.KProperty3
        public void set(Object value) {
            ((PublicSuffixDatabase) this.receiver).publicSuffixListBytes = (byte[]) value;
        }
    }

    private final void readTheListUninterruptibly() {
        boolean interrupted = false;
        while (true) {
            try {
                try {
                    try {
                        readTheList();
                        if (interrupted) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                        return;
                    } catch (InterruptedIOException e) {
                        Thread.interrupted();
                        interrupted = true;
                    }
                } catch (IOException e2) {
                    Platform.INSTANCE.get().log(5, "Failed to read public suffix list", e2);
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    return;
                }
            } catch (Throwable th) {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0050 A[Catch: all -> 0x005e, TryCatch #2 {, blocks: (B:10:0x0049, B:11:0x004c, B:13:0x0050, B:14:0x0053), top: B:31:0x0049 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void readTheList() throws IOException {
        InputStream resource = PublicSuffixDatabase.class.getResourceAsStream(PUBLIC_SUFFIX_RESOURCE);
        if (resource == null) {
            return;
        }
        BufferedSource bufferedSourceBuffer = Okio.buffer(new GzipSource(Okio.source(resource)));
        try {
            BufferedSource bufferedSource = bufferedSourceBuffer;
            int totalBytes = bufferedSource.readInt();
            byte[] byteArray = bufferedSource.readByteArray(totalBytes);
            int totalExceptionBytes = bufferedSource.readInt();
            byte[] byteArray2 = bufferedSource.readByteArray(totalExceptionBytes);
            Unit unit = Unit.INSTANCE;
            Closeable.closeFinally(bufferedSourceBuffer, null);
            synchronized (this) {
                if (byteArray == null) {
                    Intrinsics.throwNpe();
                    this.publicSuffixListBytes = byteArray;
                    if (byteArray2 == null) {
                        Intrinsics.throwNpe();
                    }
                    this.publicSuffixExceptionListBytes = byteArray2;
                    Unit unit2 = Unit.INSTANCE;
                } else {
                    this.publicSuffixListBytes = byteArray;
                    if (byteArray2 == null) {
                    }
                    this.publicSuffixExceptionListBytes = byteArray2;
                    Unit unit22 = Unit.INSTANCE;
                }
            }
            this.readCompleteLatch.countDown();
        } finally {
        }
    }

    public final void setListBytes(byte[] publicSuffixListBytes, byte[] publicSuffixExceptionListBytes) {
        Intrinsics.checkParameterIsNotNull(publicSuffixListBytes, "publicSuffixListBytes");
        Intrinsics.checkParameterIsNotNull(publicSuffixExceptionListBytes, "publicSuffixExceptionListBytes");
        this.publicSuffixListBytes = publicSuffixListBytes;
        this.publicSuffixExceptionListBytes = publicSuffixExceptionListBytes;
        this.listRead.set(true);
        this.readCompleteLatch.countDown();
    }

    /* compiled from: PublicSuffixDatabase.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\r\u001a\u00020\fJ)\u0010\u000e\u001a\u0004\u0018\u00010\u0007*\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\n0\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002¢\u0006\u0002\u0010\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lokhttp3/internal/publicsuffix/PublicSuffixDatabase$Companion;", "", "()V", "EXCEPTION_MARKER", "", "PREVAILING_RULE", "", "", "PUBLIC_SUFFIX_RESOURCE", "WILDCARD_LABEL", "", "instance", "Lokhttp3/internal/publicsuffix/PublicSuffixDatabase;", "get", "binarySearch", "labels", "", "labelIndex", "", "([B[[BI)Ljava/lang/String;", "okhttp"}, k = 1, mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final PublicSuffixDatabase get() {
            return PublicSuffixDatabase.instance;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String binarySearch(byte[] $this$binarySearch, byte[][] labels, int labelIndex) {
            int byte0;
            int compareResult;
            int low;
            int low2 = 0;
            int high = $this$binarySearch.length;
            String match = (String) null;
            while (low2 < high) {
                int mid = (low2 + high) / 2;
                while (mid > -1 && $this$binarySearch[mid] != ((byte) 10)) {
                    mid--;
                }
                int mid2 = mid + 1;
                int end = 1;
                while ($this$binarySearch[mid2 + end] != ((byte) 10)) {
                    end++;
                }
                int publicSuffixLength = (mid2 + end) - mid2;
                int currentLabelIndex = labelIndex;
                int currentLabelByteIndex = 0;
                int publicSuffixByteIndex = 0;
                boolean expectDot = false;
                while (true) {
                    if (!expectDot) {
                        byte0 = Util.and(labels[currentLabelIndex][currentLabelByteIndex], 255);
                    } else {
                        byte0 = 46;
                        expectDot = false;
                    }
                    int byte1 = Util.and($this$binarySearch[mid2 + publicSuffixByteIndex], 255);
                    compareResult = byte0 - byte1;
                    if (compareResult == 0) {
                        publicSuffixByteIndex++;
                        currentLabelByteIndex++;
                        if (publicSuffixByteIndex == publicSuffixLength) {
                            break;
                        }
                        if (labels[currentLabelIndex].length != currentLabelByteIndex) {
                            low = low2;
                        } else {
                            if (currentLabelIndex == labels.length - 1) {
                                break;
                            }
                            low = low2;
                            currentLabelIndex++;
                            expectDot = true;
                            currentLabelByteIndex = -1;
                        }
                        low2 = low;
                    } else {
                        break;
                    }
                }
                if (compareResult < 0) {
                    high = mid2 - 1;
                } else if (compareResult > 0) {
                    low2 = mid2 + end + 1;
                } else {
                    int low3 = publicSuffixLength - publicSuffixByteIndex;
                    int labelBytesLeft = labels[currentLabelIndex].length - currentLabelByteIndex;
                    int i = currentLabelIndex + 1;
                    int length = labels.length;
                    while (i < length) {
                        labelBytesLeft += labels[i].length;
                        i++;
                        low2 = low2;
                    }
                    int low4 = low2;
                    if (labelBytesLeft < low3) {
                        high = mid2 - 1;
                        low2 = low4;
                    } else if (labelBytesLeft > low3) {
                        low2 = mid2 + end + 1;
                    } else {
                        Charset UTF_8 = StandardCharsets.UTF_8;
                        Intrinsics.checkExpressionValueIsNotNull(UTF_8, "UTF_8");
                        String match2 = new String($this$binarySearch, mid2, publicSuffixLength, UTF_8);
                        return match2;
                    }
                }
            }
            return match;
        }
    }
}