package com.squareup.haha.guava.base;

import java.io.Serializable;
import java.util.Collection;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class Predicates {
    public static <T> Predicate<T> in(Collection<? extends T> target) {
        return new InPredicate(target, (byte) 0);
    }

    static {
        new Joiner(",");
    }

    static class InPredicate<T> implements Predicate<T>, Serializable {
        private final Collection<?> target;

        /* synthetic */ InPredicate(Collection x0, byte b) {
            this(x0);
        }

        private InPredicate(Collection<?> target) {
            this.target = (Collection) Joiner.checkNotNull(target);
        }

        @Override // com.squareup.haha.guava.base.Predicate
        public final boolean apply(@Nullable T t) {
            try {
                return this.target.contains(t);
            } catch (ClassCastException e) {
                return false;
            } catch (NullPointerException e2) {
                return false;
            }
        }

        public final boolean equals(@Nullable Object obj) {
            if (obj instanceof InPredicate) {
                InPredicate<?> that = (InPredicate) obj;
                return this.target.equals(that.target);
            }
            return false;
        }

        public final int hashCode() {
            return this.target.hashCode();
        }

        public final String toString() {
            return "Predicates.in(" + this.target + ")";
        }
    }
}