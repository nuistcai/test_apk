package com.cmic.gen.sdk.e;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.LineHeightSpan;
import android.widget.TextView;

/* compiled from: NoPaddingTextviewUtils.java */
/* renamed from: com.cmic.gen.sdk.e.e, reason: use source file name */
/* loaded from: classes.dex */
public class NoPaddingTextviewUtils {
    public static void a(final TextView textView, CharSequence charSequence) {
        if (textView == null || charSequence == null) {
            return;
        }
        textView.setPadding(textView.getPaddingLeft(), 0, textView.getPaddingRight(), 0);
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new LineHeightSpan() { // from class: com.cmic.gen.sdk.e.e.1
            @Override // android.text.style.LineHeightSpan
            public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int lineHeight, Paint.FontMetricsInt fm) {
                Rect rect = new Rect();
                if (Build.VERSION.SDK_INT >= 29) {
                    textView.getPaint().getTextBounds(text, 0, text.length(), rect);
                } else {
                    textView.getPaint().getTextBounds(text.toString(), 0, text.length(), rect);
                }
                if (rect.bottom - rect.top < textView.getTextSize()) {
                    float textSize = (textView.getTextSize() - (rect.bottom - rect.top)) / 2.0f;
                    fm.top = (int) (rect.top - textSize);
                    fm.bottom = (int) (rect.bottom + textSize);
                } else {
                    fm.top = rect.top;
                    fm.bottom = rect.bottom;
                }
                fm.ascent = fm.top;
                fm.descent = fm.bottom;
            }
        }, 0, charSequence.length(), 33);
        textView.setText(spannableString);
    }
}