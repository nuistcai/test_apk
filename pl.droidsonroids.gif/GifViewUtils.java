package pl.droidsonroids.gif;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.asm.Opcodes;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
final class GifViewUtils {
    static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    static final List<String> SUPPORTED_RESOURCE_TYPE_NAMES = Arrays.asList("raw", "drawable", "mipmap");

    private GifViewUtils() {
    }

    static GifImageViewAttributes initImageView(ImageView view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null && !view.isInEditMode()) {
            GifImageViewAttributes viewAttributes = new GifImageViewAttributes(view, attrs, defStyleAttr, defStyleRes);
            int loopCount = viewAttributes.mLoopCount;
            if (loopCount >= 0) {
                applyLoopCount(loopCount, view.getDrawable());
                applyLoopCount(loopCount, view.getBackground());
            }
            return viewAttributes;
        }
        return new GifImageViewAttributes();
    }

    static void applyLoopCount(int loopCount, Drawable drawable) {
        if (drawable instanceof GifDrawable) {
            ((GifDrawable) drawable).setLoopCount(loopCount);
        }
    }

    static boolean setResource(ImageView view, boolean isSrc, int resId) throws Resources.NotFoundException {
        Resources res = view.getResources();
        if (res != null) {
            try {
                String resourceTypeName = res.getResourceTypeName(resId);
                if (!SUPPORTED_RESOURCE_TYPE_NAMES.contains(resourceTypeName)) {
                    return false;
                }
                GifDrawable d = new GifDrawable(res, resId);
                if (isSrc) {
                    view.setImageDrawable(d);
                    return true;
                }
                view.setBackground(d);
                return true;
            } catch (Resources.NotFoundException e) {
            } catch (IOException e2) {
            }
        }
        return false;
    }

    static boolean setGifImageUri(ImageView imageView, Uri uri) {
        if (uri != null) {
            try {
                imageView.setImageDrawable(new GifDrawable(imageView.getContext().getContentResolver(), uri));
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    static float getDensityScale(Resources res, int id) {
        int density;
        TypedValue value = new TypedValue();
        res.getValue(id, value, true);
        int resourceDensity = value.density;
        if (resourceDensity == 0) {
            density = Opcodes.IF_ICMPNE;
        } else if (resourceDensity != 65535) {
            density = resourceDensity;
        } else {
            density = 0;
        }
        int targetDensity = res.getDisplayMetrics().densityDpi;
        if (density > 0 && targetDensity > 0) {
            return targetDensity / density;
        }
        return 1.0f;
    }

    static class GifViewAttributes {
        boolean freezesAnimation;
        final int mLoopCount;

        GifViewAttributes(View view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            TypedArray gifViewAttributes = view.getContext().obtainStyledAttributes(attrs, R.styleable.GifView, defStyleAttr, defStyleRes);
            this.freezesAnimation = gifViewAttributes.getBoolean(R.styleable.GifView_freezesAnimation, false);
            this.mLoopCount = gifViewAttributes.getInt(R.styleable.GifView_loopCount, -1);
            gifViewAttributes.recycle();
        }

        GifViewAttributes() {
            this.freezesAnimation = false;
            this.mLoopCount = -1;
        }
    }

    static class GifImageViewAttributes extends GifViewAttributes {
        final int mBackgroundResId;
        final int mSourceResId;

        GifImageViewAttributes(ImageView view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(view, attrs, defStyleAttr, defStyleRes);
            this.mSourceResId = getResourceId(view, attrs, true);
            this.mBackgroundResId = getResourceId(view, attrs, false);
        }

        GifImageViewAttributes() {
            this.mSourceResId = 0;
            this.mBackgroundResId = 0;
        }

        private static int getResourceId(ImageView view, AttributeSet attrs, boolean isSrc) throws Resources.NotFoundException {
            int resId = attrs.getAttributeResourceValue(GifViewUtils.ANDROID_NS, isSrc ? "src" : "background", 0);
            if (resId > 0) {
                String resourceTypeName = view.getResources().getResourceTypeName(resId);
                if (GifViewUtils.SUPPORTED_RESOURCE_TYPE_NAMES.contains(resourceTypeName) && !GifViewUtils.setResource(view, isSrc, resId)) {
                    return resId;
                }
            }
            return 0;
        }
    }
}