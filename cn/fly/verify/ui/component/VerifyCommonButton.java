package cn.fly.verify.ui.component;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import cn.fly.tools.utils.ResHelper;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public class VerifyCommonButton extends TextView {
    private static final int DEFAULT_TEXT_SIZE = 14;

    public VerifyCommonButton(Context context) throws Resources.NotFoundException {
        super(context);
        init(context, null);
    }

    public VerifyCommonButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VerifyCommonButton(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private int getStyleableFieldId(Context context, String str, String str2) {
        String str3 = str + "_" + str2;
        try {
            for (Class<?> cls : Class.forName(context.getPackageName() + ".R").getClasses()) {
                if (cls.getSimpleName().equals("styleable")) {
                    for (Field field : cls.getFields()) {
                        if (field.getName().equals(str3)) {
                            return ((Integer) field.get(null)).intValue();
                        }
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return 0;
    }

    private void init(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        setGravity(17);
        Drawable drawable = getResources().getDrawable(ResHelper.getBitmapRes(context, "fly_verify_shape_rectangle"));
        int color = getResources().getColor(ResHelper.getColorRes(context, "fly_verify_text_color_common_white"));
        int integer = 14;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ResHelper.getStyleableRes(context, "VerifyCommonButton"));
            String string = typedArrayObtainStyledAttributes.getString(getStyleableFieldId(context, "VerifyCommonButton", "vcombtn_text"));
            if (!TextUtils.isEmpty(string)) {
                setText(string);
            }
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(getStyleableFieldId(context, "VerifyCommonButton", "vcombtn_bg"));
            if (drawable2 != null) {
                drawable = drawable2;
            }
            color = typedArrayObtainStyledAttributes.getColor(getStyleableFieldId(context, "VerifyCommonButton", "vcombtn_textColor"), color);
            integer = typedArrayObtainStyledAttributes.getInteger(getStyleableFieldId(context, "VerifyCommonButton", "vcombtn_textSize"), 14);
            typedArrayObtainStyledAttributes.recycle();
        }
        setTextSize(integer);
        setTextColor(color);
        setBackground(drawable);
    }
}