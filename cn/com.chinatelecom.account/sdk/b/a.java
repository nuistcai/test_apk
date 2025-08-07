package cn.com.chinatelecom.account.sdk.b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import cn.com.chinatelecom.account.sdk.ui.PrivacyWebviewActivity;

/* loaded from: classes.dex */
public class a extends ClickableSpan {
    private String a;
    private String b;
    private String c;
    private int d;
    private Context e;

    public a(Context context, String str, String str2, int i, String str3) {
        this.e = context;
        this.a = str;
        this.b = str2;
        this.d = i;
        this.c = str3;
    }

    private void a(String str, String str2) {
        try {
            Intent intent = new Intent(this.e, (Class<?>) PrivacyWebviewActivity.class);
            intent.putExtra("privacyProtocolUrl", str);
            intent.putExtra("privacyProtocolTitle", str2);
            this.e.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.text.style.ClickableSpan
    public void onClick(View view) {
        a(this.a, this.b);
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        int color;
        if (this.d == 0) {
            if (!TextUtils.isEmpty(this.c)) {
                color = Color.parseColor(this.c);
            }
            textPaint.setUnderlineText(false);
        }
        color = this.d;
        textPaint.setColor(color);
        textPaint.setUnderlineText(false);
    }
}