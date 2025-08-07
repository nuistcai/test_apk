package com.squareup.haha.guava.base;

import javax.annotation.Nullable;

/* loaded from: classes.dex */
public interface Function<F, T> {
    @Nullable
    T apply(@Nullable F f);
}