package com.squareup.haha.guava.base;

import com.squareup.haha.guava.collect.ImmutableList;
import com.squareup.haha.guava.collect.Iterators;
import com.squareup.haha.guava.collect.Lists$RandomAccessReverseList;
import com.squareup.haha.guava.collect.Lists$ReverseList;
import com.squareup.haha.guava.collect.Multiset;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class Joiner {
    private final String separator;

    /* synthetic */ Joiner(Joiner x0, byte b) {
        this(x0);
    }

    public Joiner(String separator) {
        this.separator = (String) checkNotNull(separator);
    }

    private Joiner(Joiner prototype) {
        this.separator = prototype.separator;
    }

    public final StringBuilder appendTo(StringBuilder builder, Iterator<?> parts) throws IOException {
        try {
            checkNotNull(builder);
            if (parts.hasNext()) {
                builder.append(toString(parts.next()));
                while (parts.hasNext()) {
                    builder.append((CharSequence) this.separator);
                    builder.append(toString(parts.next()));
                }
            }
            return builder;
        } catch (IOException impossible) {
            throw new AssertionError(impossible);
        }
    }

    @CheckReturnValue
    public Joiner useForNull(final String nullText) {
        checkNotNull(nullText);
        return new Joiner(this) { // from class: com.squareup.haha.guava.base.Joiner.1
            {
                byte b = 0;
            }

            @Override // com.squareup.haha.guava.base.Joiner
            final CharSequence toString(@Nullable Object part) {
                return part == null ? nullText : Joiner.this.toString(part);
            }

            @Override // com.squareup.haha.guava.base.Joiner
            public final Joiner useForNull(String nullText2) {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }

    public static final class MapJoiner {
        public /* synthetic */ MapJoiner(Joiner x0, String x1, byte b) {
            this(x0, x1);
        }

        private MapJoiner(Joiner joiner, String keyValueSeparator) {
            Joiner.checkNotNull(keyValueSeparator);
        }
    }

    CharSequence toString(Object part) {
        checkNotNull(part);
        return part instanceof CharSequence ? (CharSequence) part : part.toString();
    }

    @CheckReturnValue
    public static boolean equal(Object a, Object b) {
        if (a != b) {
            return a != null && a.equals(b);
        }
        return true;
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static int checkElementIndex(int index, int size) {
        String str;
        if (index >= 0 && index < size) {
            return index;
        }
        if (index < 0) {
            str = format("%s (%s) must not be negative", "index", Integer.valueOf(index));
        } else {
            if (size < 0) {
                throw new IllegalArgumentException("negative size: " + size);
            }
            str = format("%s (%s) must be less than size (%s)", "index", Integer.valueOf(index), Integer.valueOf(size));
        }
        throw new IndexOutOfBoundsException(str);
    }

    public static int checkPositionIndex(int index, int size) {
        if (index >= 0 && index <= size) {
            return index;
        }
        throw new IndexOutOfBoundsException(badPositionIndex(index, size, "index"));
    }

    static String badPositionIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, Integer.valueOf(index));
        }
        if (size >= 0) {
            return format("%s (%s) must not be greater than size (%s)", desc, Integer.valueOf(index), Integer.valueOf(size));
        }
        throw new IllegalArgumentException("negative size: " + size);
    }

    public static void checkPositionIndexes(int start, int end, int size) {
        String strBadPositionIndex;
        if (start >= 0 && end >= start && end <= size) {
            return;
        }
        if (start < 0 || start > size) {
            strBadPositionIndex = badPositionIndex(start, size, "start index");
        } else if (end < 0 || end > size) {
            strBadPositionIndex = badPositionIndex(end, size, "end index");
        } else {
            strBadPositionIndex = format("end index (%s) must not be less than start index (%s)", Integer.valueOf(end), Integer.valueOf(start));
        }
        throw new IndexOutOfBoundsException(strBadPositionIndex);
    }

    static String format(String template, Object... args) {
        int placeholderStart;
        String template2 = String.valueOf(template);
        StringBuilder builder = new StringBuilder(template2.length() + (args.length * 16));
        int templateStart = 0;
        int i = 0;
        while (i < args.length && (placeholderStart = template2.indexOf("%s", templateStart)) != -1) {
            builder.append(template2.substring(templateStart, placeholderStart));
            builder.append(args[i]);
            templateStart = placeholderStart + 2;
            i++;
        }
        builder.append(template2.substring(templateStart));
        if (i < args.length) {
            builder.append(" [");
            int i2 = i + 1;
            builder.append(args[i]);
            while (true) {
                int i3 = i2;
                int i4 = args.length;
                if (i3 >= i4) {
                    break;
                }
                builder.append(", ");
                i2 = i3 + 1;
                builder.append(args[i3]);
            }
            builder.append(']');
        }
        return builder.toString();
    }

    public static void checkRemove(boolean canRemove) {
        if (canRemove) {
        } else {
            throw new IllegalStateException(String.valueOf("no calls to next() since the last call to remove()"));
        }
    }

    public static <T> List<T> reverse(List<T> list) {
        if (list instanceof ImmutableList) {
            return ((ImmutableList) list).reverse();
        }
        if (!(list instanceof Lists$ReverseList)) {
            if (list instanceof RandomAccess) {
                return new Lists$RandomAccessReverseList(list);
            }
            return new Lists$ReverseList(list);
        }
        return ((Lists$ReverseList) list).forwardList;
    }

    public static boolean equalsImpl(Set<?> s, Object object) {
        if (s == object) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set<?> o = (Set) object;
        try {
            if (s.size() == o.size()) {
                if (s.containsAll(o)) {
                    return true;
                }
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public static boolean removeAllImpl(Set<?> set, Iterator<?> iterator) {
        boolean changed = false;
        while (iterator.hasNext()) {
            changed |= set.remove(iterator.next());
        }
        return changed;
    }

    public static boolean removeAllImpl(Set<?> set, Collection<?> collection) {
        checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if ((collection instanceof Set) && collection.size() > set.size()) {
            return Iterators.removeAll(set.iterator(), collection);
        }
        return removeAllImpl(set, collection.iterator());
    }

    public static int hash(Object object) {
        if (object == null) {
            return 0;
        }
        return object.hashCode();
    }
}