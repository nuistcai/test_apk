package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Function;
import com.squareup.haha.guava.base.Joiner;
import com.squareup.haha.guava.base.Predicate;
import com.squareup.haha.guava.base.Predicates;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class Iterators {
    private static UnmodifiableListIterator<Object> EMPTY_LIST_ITERATOR = new UnmodifiableListIterator<Object>() { // from class: com.squareup.haha.guava.collect.Iterators.1
        @Override // java.util.Iterator, java.util.ListIterator
        public final boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public final Object next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.ListIterator
        public final boolean hasPrevious() {
            return false;
        }

        @Override // java.util.ListIterator
        public final Object previous() {
            throw new NoSuchElementException();
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return 0;
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return -1;
        }
    };
    private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR = new Iterator<Object>() { // from class: com.squareup.haha.guava.collect.Iterators.2
        @Override // java.util.Iterator
        public final boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator
        public final Object next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public final void remove() {
            Joiner.checkRemove(false);
        }
    };

    static <T> Iterator<T> emptyModifiableIterator() {
        return (Iterator<T>) EMPTY_MODIFIABLE_ITERATOR;
    }

    public static boolean removeAll(Iterator<?> removeFrom, Collection<?> elementsToRemove) {
        Predicate predicateIn = Predicates.in(elementsToRemove);
        Joiner.checkNotNull(predicateIn);
        boolean z = false;
        while (removeFrom.hasNext()) {
            if (predicateIn.apply(removeFrom.next())) {
                removeFrom.remove();
                z = true;
            }
        }
        return z;
    }

    public static boolean elementsEqual(Iterator<?> iterator1, Iterator<?> iterator2) {
        while (iterator1.hasNext()) {
            if (!iterator2.hasNext()) {
                return false;
            }
            Object o1 = iterator1.next();
            Object o2 = iterator2.next();
            if (!Joiner.equal(o1, o2)) {
                return false;
            }
        }
        return !iterator2.hasNext();
    }

    public static String toString(Iterator<?> iterator) {
        return Collections2.STANDARD_JOINER.appendTo(new StringBuilder("["), iterator).append(']').toString();
    }

    public static <T> Iterator<T> concat(final Iterator<? extends Iterator<? extends T>> inputs) {
        Joiner.checkNotNull(inputs);
        return new Iterator<T>() { // from class: com.squareup.haha.guava.collect.Iterators.5
            private Iterator<? extends T> current = Iterators.emptyIterator();
            private Iterator<? extends T> removeFrom;

            @Override // java.util.Iterator
            public final boolean hasNext() {
                boolean currentHasNext;
                while (true) {
                    currentHasNext = ((Iterator) Joiner.checkNotNull(this.current)).hasNext();
                    if (currentHasNext || !inputs.hasNext()) {
                        break;
                    }
                    this.current = (Iterator) inputs.next();
                }
                return currentHasNext;
            }

            @Override // java.util.Iterator
            public final T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                this.removeFrom = this.current;
                return this.current.next();
            }

            @Override // java.util.Iterator
            public final void remove() {
                Joiner.checkRemove(this.removeFrom != null);
                this.removeFrom.remove();
                this.removeFrom = null;
            }
        };
    }

    public static <F, T> Iterator<T> transform(Iterator<F> fromIterator, final Function<? super F, ? extends T> function) {
        Joiner.checkNotNull(function);
        return new TransformedIterator<F, T>(fromIterator) { // from class: com.squareup.haha.guava.collect.Iterators.8
            @Override // com.squareup.haha.guava.collect.TransformedIterator
            final T transform(F f) {
                return (T) function.apply(f);
            }
        };
    }

    static void clear(Iterator<?> iterator) {
        Joiner.checkNotNull(iterator);
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    static <T> UnmodifiableListIterator<T> forArray(final T[] tArr, final int i, int i2, int i3) {
        Joiner.checkArgument(i2 >= 0);
        Joiner.checkPositionIndexes(i, i + i2, tArr.length);
        Joiner.checkPositionIndex(i3, i2);
        if (i2 != 0) {
            return new AbstractIndexedListIterator<T>(i2, i3) { // from class: com.squareup.haha.guava.collect.Iterators.11
                @Override // com.squareup.haha.guava.collect.AbstractIndexedListIterator
                protected final T get(int i4) {
                    return (T) tArr[i + i4];
                }
            };
        }
        return (UnmodifiableListIterator<T>) EMPTY_LIST_ITERATOR;
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(@Nullable final T value) {
        return new UnmodifiableIterator<T>() { // from class: com.squareup.haha.guava.collect.Iterators.12
            private boolean done;

            @Override // java.util.Iterator
            public final boolean hasNext() {
                return !this.done;
            }

            @Override // java.util.Iterator
            public final T next() {
                if (this.done) {
                    throw new NoSuchElementException();
                }
                this.done = true;
                return (T) value;
            }
        };
    }

    public static <T> UnmodifiableIterator<T> emptyIterator() {
        return EMPTY_LIST_ITERATOR;
    }
}