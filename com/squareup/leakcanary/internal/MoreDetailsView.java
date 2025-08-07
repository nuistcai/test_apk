package com.squareup.leakcanary.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes.dex */
public final class MoreDetailsView extends View {
    private static final Paint iconPaint = new Paint(1);
    private boolean opened;

    static {
        iconPaint.setColor(-8083771);
    }

    public MoreDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        float strokeSize = LeakCanaryUi.dpToPixel(2.0f, getResources());
        iconPaint.setStrokeWidth(strokeSize);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int halfHeight = height / 2;
        int halfWidth = width / 2;
        if (this.opened) {
            canvas.drawLine(0.0f, halfHeight, width, halfHeight, iconPaint);
        } else {
            canvas.drawLine(0.0f, halfHeight, width, halfHeight, iconPaint);
            canvas.drawLine(halfWidth, 0.0f, halfWidth, height, iconPaint);
        }
    }

    public void setOpened(boolean opened) {
        if (opened != this.opened) {
            this.opened = opened;
            invalidate();
        }
    }
}