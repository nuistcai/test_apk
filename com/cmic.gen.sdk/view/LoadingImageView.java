package com.cmic.gen.sdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/* loaded from: classes.dex */
public class LoadingImageView extends ImageView {
    private Animation a;
    private LinearInterpolator b;

    public LoadingImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = null;
        this.b = null;
        a();
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = null;
        this.b = null;
        a();
    }

    public LoadingImageView(Context context) {
        super(context);
        this.a = null;
        this.b = null;
        a();
    }

    protected void a() {
        this.a = AnimationUtils.loadAnimation(getContext(), ResourceUtil.c(getContext(), "umcsdk_anim_loading"));
        this.b = new LinearInterpolator();
        this.a.setInterpolator(this.b);
    }
}