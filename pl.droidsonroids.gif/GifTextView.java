package pl.droidsonroids.gif;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;
import java.io.IOException;
import pl.droidsonroids.gif.GifViewUtils;

/* loaded from: classes.dex */
public class GifTextView extends TextView {
    private GifViewUtils.GifViewAttributes viewAttributes;

    public GifTextView(Context context) {
        super(context);
    }

    public GifTextView(Context context, AttributeSet attrs) throws Resources.NotFoundException {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public GifTextView(Context context, AttributeSet attrs, int defStyle) throws Resources.NotFoundException {
        super(context, attrs, defStyle);
        init(attrs, defStyle, 0);
    }

    public GifTextView(Context context, AttributeSet attrs, int defStyle, int defStyleRes) throws Resources.NotFoundException {
        super(context, attrs, defStyle, defStyleRes);
        init(attrs, defStyle, defStyleRes);
    }

    private static void setDrawablesVisible(Drawable[] drawables, boolean visible) {
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                drawable.setVisible(visible, false);
            }
        }
    }

    private void init(AttributeSet attrs, int defStyle, int defStyleRes) throws Resources.NotFoundException {
        if (attrs != null) {
            Drawable left = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableLeft", 0));
            Drawable top = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableTop", 0));
            Drawable right = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableRight", 0));
            Drawable bottom = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableBottom", 0));
            Drawable start = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableStart", 0));
            Drawable end = getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableEnd", 0));
            if (getLayoutDirection() == 0) {
                if (start == null) {
                    start = left;
                }
                if (end == null) {
                    end = right;
                }
            } else {
                if (start == null) {
                    start = right;
                }
                if (end == null) {
                    end = left;
                }
            }
            setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
            setBackground(getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", 0)));
            this.viewAttributes = new GifViewUtils.GifViewAttributes(this, attrs, defStyle, defStyleRes);
            applyGifViewAttributes();
        }
        this.viewAttributes = new GifViewUtils.GifViewAttributes();
    }

    private void applyGifViewAttributes() {
        if (this.viewAttributes.mLoopCount < 0) {
            return;
        }
        for (Drawable drawable : getCompoundDrawables()) {
            GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, drawable);
        }
        for (Drawable drawable2 : getCompoundDrawablesRelative()) {
            GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, drawable2);
        }
        GifViewUtils.applyLoopCount(this.viewAttributes.mLoopCount, getBackground());
    }

    private Drawable getGifOrDefaultDrawable(int resId) throws Resources.NotFoundException {
        if (resId == 0) {
            return null;
        }
        Resources resources = getResources();
        String resourceTypeName = resources.getResourceTypeName(resId);
        if (!isInEditMode() && GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES.contains(resourceTypeName)) {
            try {
                return new GifDrawable(resources, resId);
            } catch (Resources.NotFoundException e) {
            } catch (IOException e2) {
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawable(resId, getContext().getTheme());
        }
        return resources.getDrawable(resId);
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        setCompoundDrawablesWithIntrinsicBounds(getGifOrDefaultDrawable(left), getGifOrDefaultDrawable(top), getGifOrDefaultDrawable(right), getGifOrDefaultDrawable(bottom));
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(getGifOrDefaultDrawable(start), getGifOrDefaultDrawable(top), getGifOrDefaultDrawable(end), getGifOrDefaultDrawable(bottom));
    }

    @Override // android.widget.TextView, android.view.View
    public Parcelable onSaveInstanceState() {
        Drawable[] savedDrawables = new Drawable[7];
        if (this.viewAttributes.freezesAnimation) {
            Drawable[] compoundDrawables = getCompoundDrawables();
            System.arraycopy(compoundDrawables, 0, savedDrawables, 0, compoundDrawables.length);
            Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
            savedDrawables[4] = compoundDrawablesRelative[0];
            savedDrawables[5] = compoundDrawablesRelative[2];
            savedDrawables[6] = getBackground();
        }
        return new GifViewSavedState(super.onSaveInstanceState(), savedDrawables);
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        GifViewSavedState ss = (GifViewSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        Drawable[] compoundDrawables = getCompoundDrawables();
        ss.restoreState(compoundDrawables[0], 0);
        ss.restoreState(compoundDrawables[1], 1);
        ss.restoreState(compoundDrawables[2], 2);
        ss.restoreState(compoundDrawables[3], 3);
        Drawable[] compoundDrawablesRelative = getCompoundDrawablesRelative();
        ss.restoreState(compoundDrawablesRelative[0], 4);
        ss.restoreState(compoundDrawablesRelative[2], 5);
        ss.restoreState(getBackground(), 6);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setCompoundDrawablesVisible(true);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setCompoundDrawablesVisible(false);
    }

    @Override // android.view.View
    public void setBackgroundResource(int resId) {
        setBackground(getGifOrDefaultDrawable(resId));
    }

    private void setCompoundDrawablesVisible(boolean visible) {
        setDrawablesVisible(getCompoundDrawables(), visible);
        setDrawablesVisible(getCompoundDrawablesRelative(), visible);
    }

    public void setFreezesAnimation(boolean freezesAnimation) {
        this.viewAttributes.freezesAnimation = freezesAnimation;
    }
}