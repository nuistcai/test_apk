package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import kotlin.collections.ArraysKt;
import kotlin.collections.UIterators4;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMarkers;

/* compiled from: UShortArray.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0017\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087@\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001-B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\bø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\u001b\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\t\u0010\u001e\u001a\u00020\u0004HÖ\u0001J\u000f\u0010\u001f\u001a\u00020\u000fH\u0016¢\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020#H\u0096\u0002¢\u0006\u0004\b$\u0010%J#\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0002H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b)\u0010*J\t\u0010+\u001a\u00020,HÖ\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006."}, d2 = {"Lkotlin/UShortArray;", "", "Lkotlin/UShort;", "size", "", "constructor-impl", "(I)[S", "storage", "", "([S)[S", "getSize-impl", "([S)I", "storage$annotations", "()V", "contains", "", "element", "contains-xj2QHRw", "([SS)Z", "containsAll", "elements", "containsAll-impl", "([SLjava/util/Collection;)Z", "equals", "other", "", "get", "index", "get-impl", "([SI)S", "hashCode", "isEmpty", "isEmpty-impl", "([S)Z", "iterator", "Lkotlin/collections/UShortIterator;", "iterator-impl", "([S)Lkotlin/collections/UShortIterator;", "set", "", "value", "set-01HTLdE", "([SIS)V", "toString", "", "Iterator", "kotlin-stdlib"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes.dex */
public final class UShortArray implements Collection<UShort>, KMarkers {
    private final short[] storage;

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UShortArray m304boximpl(short[] v) {
        Intrinsics.checkParameterIsNotNull(v, "v");
        return new UShortArray(v);
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m309equalsimpl(short[] sArr, Object obj) {
        return (obj instanceof UShortArray) && Intrinsics.areEqual(sArr, ((UShortArray) obj).getStorage());
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m310equalsimpl0(short[] p1, short[] p2) {
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        Intrinsics.checkParameterIsNotNull(p2, "p2");
        throw null;
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m313hashCodeimpl(short[] sArr) {
        if (sArr != null) {
            return Arrays.hashCode(sArr);
        }
        return 0;
    }

    public static /* synthetic */ void storage$annotations() {
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m317toStringimpl(short[] sArr) {
        return "UShortArray(storage=" + Arrays.toString(sArr) + ")";
    }

    @Override // java.util.Collection
    public /* synthetic */ boolean add(UShort uShort) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: add-xj2QHRw, reason: not valid java name */
    public boolean m318addxj2QHRw(short s) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean addAll(Collection<? extends UShort> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* renamed from: contains-xj2QHRw, reason: not valid java name */
    public boolean m319containsxj2QHRw(short s) {
        return m307containsxj2QHRw(this.storage, s);
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection<? extends Object> collection) {
        return m308containsAllimpl(this.storage, collection);
    }

    @Override // java.util.Collection
    public boolean equals(Object other) {
        return m309equalsimpl(this.storage, other);
    }

    public int getSize() {
        return m312getSizeimpl(this.storage);
    }

    @Override // java.util.Collection
    public int hashCode() {
        return m313hashCodeimpl(this.storage);
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return m314isEmptyimpl(this.storage);
    }

    @Override // java.util.Collection, java.lang.Iterable
    public UIterators4 iterator() {
        return m315iteratorimpl(this.storage);
    }

    @Override // java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) CollectionToArray.toArray(this, tArr);
    }

    public String toString() {
        return m317toStringimpl(this.storage);
    }

    /* renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ short[] getStorage() {
        return this.storage;
    }

    @Override // java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof UShort) {
            return m319containsxj2QHRw(((UShort) obj).getData());
        }
        return false;
    }

    @Override // java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }

    private /* synthetic */ UShortArray(short[] storage) {
        Intrinsics.checkParameterIsNotNull(storage, "storage");
        this.storage = storage;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static short[] m306constructorimpl(short[] storage) {
        Intrinsics.checkParameterIsNotNull(storage, "storage");
        return storage;
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static short[] m305constructorimpl(int size) {
        return m306constructorimpl(new short[size]);
    }

    /* renamed from: get-impl, reason: not valid java name */
    public static final short m311getimpl(short[] $this, int index) {
        return UShort.m262constructorimpl($this[index]);
    }

    /* renamed from: set-01HTLdE, reason: not valid java name */
    public static final void m316set01HTLdE(short[] $this, int index, short value) {
        $this[index] = value;
    }

    /* renamed from: getSize-impl, reason: not valid java name */
    public static int m312getSizeimpl(short[] $this) {
        return $this.length;
    }

    /* renamed from: iterator-impl, reason: not valid java name */
    public static UIterators4 m315iteratorimpl(short[] $this) {
        return new Iterator($this);
    }

    /* compiled from: UShortArray.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0017\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0010\u0010\t\u001a\u00020\nH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, d2 = {"Lkotlin/UShortArray$Iterator;", "Lkotlin/collections/UShortIterator;", "array", "", "([S)V", "index", "", "hasNext", "", "nextUShort", "Lkotlin/UShort;", "()S", "kotlin-stdlib"}, k = 1, mv = {1, 1, 15})
    private static final class Iterator extends UIterators4 {
        private final short[] array;
        private int index;

        public Iterator(short[] array) {
            Intrinsics.checkParameterIsNotNull(array, "array");
            this.array = array;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.index < this.array.length;
        }

        @Override // kotlin.collections.UIterators4
        public short nextUShort() {
            if (this.index >= this.array.length) {
                throw new NoSuchElementException(String.valueOf(this.index));
            }
            short[] sArr = this.array;
            int i = this.index;
            this.index = i + 1;
            return UShort.m262constructorimpl(sArr[i]);
        }
    }

    /* renamed from: contains-xj2QHRw, reason: not valid java name */
    public static boolean m307containsxj2QHRw(short[] $this, short element) {
        return ArraysKt.contains($this, element);
    }

    /* renamed from: containsAll-impl, reason: not valid java name */
    public static boolean m308containsAllimpl(short[] $this, Collection<UShort> elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Collection<UShort> $this$all$iv = elements;
        if ($this$all$iv.isEmpty()) {
            return true;
        }
        for (Object element$iv : $this$all$iv) {
            if (!((element$iv instanceof UShort) && ArraysKt.contains($this, ((UShort) element$iv).getData()))) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: isEmpty-impl, reason: not valid java name */
    public static boolean m314isEmptyimpl(short[] $this) {
        return $this.length == 0;
    }
}