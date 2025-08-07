package okio;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.UByte;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Options.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u00142\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001\u0014B\u001f\b\u0002\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\rH\u0096\u0002R\u001e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0005X\u0080\u0004¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0015"}, d2 = {"Lokio/Options;", "Ljava/util/AbstractList;", "Lokio/ByteString;", "Ljava/util/RandomAccess;", "byteStrings", "", "trie", "", "([Lokio/ByteString;[I)V", "getByteStrings$jvm", "()[Lokio/ByteString;", "[Lokio/ByteString;", "size", "", "getSize", "()I", "getTrie$jvm", "()[I", "get", "index", "Companion", "jvm"}, k = 1, mv = {1, 1, 11})
/* loaded from: classes.dex */
public final class Options extends AbstractList<ByteString> implements RandomAccess {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    @JvmStatic
    public static final Options of(ByteString... byteStringArr) {
        return INSTANCE.of(byteStringArr);
    }

    private Options(ByteString[] byteStrings, int[] trie) {
        this.byteStrings = byteStrings;
        this.trie = trie;
    }

    public /* synthetic */ Options(ByteString[] byteStrings, int[] trie, DefaultConstructorMarker $constructor_marker) {
        this(byteStrings, trie);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean contains(Object obj) {
        if (obj != null ? obj instanceof ByteString : true) {
            return contains((ByteString) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ByteString byteString) {
        return super.contains((Object) byteString);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj != null ? obj instanceof ByteString : true) {
            return indexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ByteString byteString) {
        return super.indexOf((Object) byteString);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj != null ? obj instanceof ByteString : true) {
            return lastIndexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ByteString byteString) {
        return super.lastIndexOf((Object) byteString);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ ByteString remove(int i) {
        return removeAt(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean remove(Object obj) {
        if (obj != null ? obj instanceof ByteString : true) {
            return remove((ByteString) obj);
        }
        return false;
    }

    public /* bridge */ boolean remove(ByteString byteString) {
        return super.remove((Object) byteString);
    }

    public /* bridge */ ByteString removeAt(int i) {
        return (ByteString) super.remove(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ int size() {
        return getSize();
    }

    /* renamed from: getByteStrings$jvm, reason: from getter */
    public final ByteString[] getByteStrings() {
        return this.byteStrings;
    }

    /* renamed from: getTrie$jvm, reason: from getter */
    public final int[] getTrie() {
        return this.trie;
    }

    public int getSize() {
        return this.byteStrings.length;
    }

    @Override // java.util.AbstractList, java.util.List
    public ByteString get(int index) {
        return this.byteStrings[index];
    }

    /* compiled from: Options.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JT\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002J!\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u0016\"\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0017R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"}, d2 = {"Lokio/Options$Companion;", "", "()V", "intCount", "", "Lokio/Buffer;", "getIntCount", "(Lokio/Buffer;)J", "buildTrieRecursive", "", "nodeOffset", "node", "byteStringOffset", "", "byteStrings", "", "Lokio/ByteString;", "fromIndex", "toIndex", "indexes", "of", "Lokio/Options;", "", "([Lokio/ByteString;)Lokio/Options;", "jvm"}, k = 1, mv = {1, 1, 11})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX WARN: Code restructure failed: missing block: B:61:0x0104, code lost:
        
            continue;
         */
        @JvmStatic
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Options of(ByteString... byteStrings) throws IOException {
            Intrinsics.checkParameterIsNotNull(byteStrings, "byteStrings");
            int i = 0;
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (byteStrings.length == 0) {
                return new Options(new ByteString[0], new int[]{0, -1}, defaultConstructorMarker);
            }
            List mutableList = ArraysKt.toMutableList(byteStrings);
            CollectionsKt.sort(mutableList);
            ArrayList arrayList = new ArrayList(byteStrings.length);
            for (ByteString byteString : byteStrings) {
                arrayList.add(-1);
            }
            Object[] array = arrayList.toArray(new Integer[0]);
            if (array == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            Integer[] numArr = (Integer[]) array;
            List listMutableListOf = CollectionsKt.mutableListOf((Integer[]) Arrays.copyOf(numArr, numArr.length));
            int length = byteStrings.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                listMutableListOf.set(CollectionsKt.binarySearch$default(mutableList, byteStrings[i2], 0, 0, 6, (Object) null), Integer.valueOf(i3));
                i2++;
                i3++;
            }
            if (!(((ByteString) mutableList.get(0)).size() > 0)) {
                throw new IllegalArgumentException("the empty byte string is not a supported option".toString());
            }
            int i4 = 0;
            while (i4 < mutableList.size()) {
                ByteString byteString2 = (ByteString) mutableList.get(i4);
                int i5 = i4 + 1;
                int i6 = i5;
                while (i6 < mutableList.size()) {
                    ByteString byteString3 = (ByteString) mutableList.get(i6);
                    if (!byteString3.startsWith(byteString2)) {
                        break;
                    }
                    if (!(byteString3.size() != byteString2.size())) {
                        throw new IllegalArgumentException(("duplicate option: " + byteString3).toString());
                    }
                    if (((Number) listMutableListOf.get(i6)).intValue() > ((Number) listMutableListOf.get(i4)).intValue()) {
                        mutableList.remove(i6);
                        listMutableListOf.remove(i6);
                    } else {
                        i6++;
                    }
                }
                i4 = i5;
            }
            Buffer buffer = new Buffer();
            buildTrieRecursive$default(this, 0L, buffer, 0, mutableList, 0, 0, listMutableListOf, 53, null);
            int[] iArr = new int[(int) getIntCount(buffer)];
            while (!buffer.exhausted()) {
                iArr[i] = buffer.readInt();
                i++;
            }
            return new Options((ByteString[]) byteStrings.clone(), iArr, defaultConstructorMarker);
        }

        static /* bridge */ /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) throws IOException {
            companion.buildTrieRecursive((i4 & 1) != 0 ? 0L : j, buffer, (i4 & 4) != 0 ? 0 : i, list, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? list.size() : i3, list2);
        }

        private final void buildTrieRecursive(long nodeOffset, Buffer node, int byteStringOffset, List<? extends ByteString> byteStrings, int fromIndex, int toIndex, List<Integer> indexes) throws IOException {
            int fromIndex2;
            ByteString from;
            int prefixIndex;
            ByteString from2;
            int rangeEnd;
            int selectChoiceCount;
            Buffer childNodes;
            int prefixIndex2;
            ByteString from3;
            int fromIndex3;
            List<Integer> list = indexes;
            if (!(fromIndex < toIndex)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            for (int i = fromIndex; i < toIndex; i++) {
                if (!(byteStrings.get(i).size() >= byteStringOffset)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
            ByteString from4 = byteStrings.get(fromIndex);
            ByteString to = byteStrings.get(toIndex - 1);
            if (byteStringOffset != from4.size()) {
                fromIndex2 = fromIndex;
                from = from4;
                prefixIndex = -1;
            } else {
                int prefixIndex3 = list.get(fromIndex).intValue();
                int fromIndex4 = fromIndex + 1;
                fromIndex2 = fromIndex4;
                from = byteStrings.get(fromIndex4);
                prefixIndex = prefixIndex3;
            }
            if (from.getByte(byteStringOffset) != to.getByte(byteStringOffset)) {
                int i2 = fromIndex2 + 1;
                int selectChoiceCount2 = 1;
                while (i2 < toIndex) {
                    int i3 = i2;
                    if (byteStrings.get(i3 - 1).getByte(byteStringOffset) != byteStrings.get(i3).getByte(byteStringOffset)) {
                        selectChoiceCount2++;
                    }
                    i2 = i3 + 1;
                }
                long childNodesOffset = nodeOffset + getIntCount(node) + 2 + (selectChoiceCount2 * 2);
                node.writeInt(selectChoiceCount2);
                int prefixIndex4 = prefixIndex;
                node.writeInt(prefixIndex4);
                for (int i4 = fromIndex2; i4 < toIndex; i4++) {
                    byte rangeByte = byteStrings.get(i4).getByte(byteStringOffset);
                    if (i4 == fromIndex2 || rangeByte != byteStrings.get(i4 - 1).getByte(byteStringOffset)) {
                        int other$iv = 255 & rangeByte;
                        node.writeInt(other$iv);
                    }
                }
                Buffer childNodes2 = new Buffer();
                int rangeStart = fromIndex2;
                while (rangeStart < toIndex) {
                    byte rangeByte2 = byteStrings.get(rangeStart).getByte(byteStringOffset);
                    int rangeEnd2 = toIndex;
                    int i5 = rangeStart + 1;
                    while (true) {
                        if (i5 >= toIndex) {
                            i5 = rangeEnd2;
                            break;
                        }
                        int rangeEnd3 = rangeEnd2;
                        if (rangeByte2 != byteStrings.get(i5).getByte(byteStringOffset)) {
                            break;
                        }
                        i5++;
                        rangeEnd2 = rangeEnd3;
                    }
                    if (rangeStart + 1 != i5 || byteStringOffset + 1 != byteStrings.get(rangeStart).size()) {
                        node.writeInt(((int) (childNodesOffset + getIntCount(childNodes2))) * (-1));
                        rangeEnd = i5;
                        selectChoiceCount = selectChoiceCount2;
                        childNodes = childNodes2;
                        prefixIndex2 = prefixIndex4;
                        from3 = from;
                        fromIndex3 = fromIndex2;
                        buildTrieRecursive(childNodesOffset, childNodes2, byteStringOffset + 1, byteStrings, rangeStart, rangeEnd, indexes);
                    } else {
                        node.writeInt(list.get(rangeStart).intValue());
                        rangeEnd = i5;
                        selectChoiceCount = selectChoiceCount2;
                        childNodes = childNodes2;
                        prefixIndex2 = prefixIndex4;
                        from3 = from;
                        fromIndex3 = fromIndex2;
                    }
                    rangeStart = rangeEnd;
                    fromIndex2 = fromIndex3;
                    prefixIndex4 = prefixIndex2;
                    childNodes2 = childNodes;
                    selectChoiceCount2 = selectChoiceCount;
                    from = from3;
                    list = indexes;
                }
                node.writeAll(childNodes2);
                return;
            }
            int prefixIndex5 = prefixIndex;
            ByteString from5 = from;
            int fromIndex5 = fromIndex2;
            int iMin = Math.min(from5.size(), to.size());
            int scanByteCount = 0;
            int scanByteCount2 = byteStringOffset;
            while (true) {
                if (scanByteCount2 >= iMin) {
                    from2 = from5;
                    break;
                }
                from2 = from5;
                if (from2.getByte(scanByteCount2) != to.getByte(scanByteCount2)) {
                    break;
                }
                scanByteCount++;
                scanByteCount2++;
                from5 = from2;
            }
            long childNodesOffset2 = nodeOffset + getIntCount(node) + 2 + scanByteCount + 1;
            node.writeInt(-scanByteCount);
            node.writeInt(prefixIndex5);
            int i6 = byteStringOffset + scanByteCount;
            for (int i7 = byteStringOffset; i7 < i6; i7++) {
                byte $receiver$iv = from2.getByte(i7);
                node.writeInt($receiver$iv & UByte.MAX_VALUE);
            }
            if (fromIndex5 + 1 != toIndex) {
                Buffer childNodes3 = new Buffer();
                node.writeInt(((int) (childNodesOffset2 + getIntCount(childNodes3))) * (-1));
                buildTrieRecursive(childNodesOffset2, childNodes3, byteStringOffset + scanByteCount, byteStrings, fromIndex5, toIndex, indexes);
                node.writeAll(childNodes3);
                return;
            }
            if (byteStringOffset + scanByteCount == byteStrings.get(fromIndex5).size()) {
                node.writeInt(indexes.get(fromIndex5).intValue());
                return;
            }
            throw new IllegalStateException("Check failed.".toString());
        }

        private final long getIntCount(Buffer $receiver) {
            return $receiver.size() / 4;
        }
    }
}