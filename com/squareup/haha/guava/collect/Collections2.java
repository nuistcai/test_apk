package com.squareup.haha.guava.collect;

import com.squareup.haha.guava.base.Joiner;
import java.util.Collection;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class Collections2 {
    static final Joiner STANDARD_JOINER = new Joiner(", ").useForNull("null");

    static boolean safeContains(Collection<?> collection, @Nullable Object object) {
        Joiner.checkNotNull(collection);
        try {
            return collection.contains(object);
        } catch (ClassCastException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }
}