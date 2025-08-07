package cn.com.chinatelecom.account.sdk.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.com.chinatelecom.account.sdk.AuthPageConfig;
import cn.com.chinatelecom.account.sdk.AuthViewConfig;
import cn.com.chinatelecom.account.sdk.CtAuth;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public final class PrivacyWebviewActivity extends Activity {
    View.OnClickListener a = new View.OnClickListener() { // from class: cn.com.chinatelecom.account.sdk.ui.PrivacyWebviewActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            PrivacyWebviewActivity.this.finish();
        }
    };
    private AuthPageConfig b;
    private AuthViewConfig c;
    private View d;
    private WebView e;
    private ProgressBar f;
    private String g;
    private String h;

    private void a() {
        this.d = findViewById(this.b.p());
        this.d.setOnClickListener(this.a);
        this.e = (WebView) findViewById(this.b.r());
        this.f = (ProgressBar) findViewById(this.b.q());
    }

    private void b() {
        if (this.c.aw != 0) {
            View viewFindViewById = findViewById(this.c.aw);
            if (this.c.d != 0) {
                viewFindViewById.setBackgroundColor(this.c.d);
            }
            if (this.c.az != 0 && this.c.ay != 0 && (this.d instanceof ImageView)) {
                ((ImageView) this.d).setImageResource(this.c.az);
            }
        }
        if (this.c.ax != 0) {
            TextView textView = (TextView) findViewById(this.c.ax);
            if (!TextUtils.isEmpty(this.h)) {
                textView.setText(this.h);
            }
            if (this.c.i != 0) {
                textView.setTextColor(this.c.i);
            }
            if (this.c.j != 0) {
                textView.setTextSize(this.c.j);
            }
        }
    }

    private void c() {
        if (TextUtils.isEmpty(this.g)) {
            return;
        }
        this.e.loadUrl(this.g);
    }

    private void d() {
        WebSettings settings = this.e.getSettings();
        settings.setTextZoom(100);
        settings.setAllowFileAccess(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setBuiltInZoomControls(true);
        settings.setSavePassword(false);
        this.e.removeJavascriptInterface("searchBoxJavaBridge_");
        this.e.removeJavascriptInterface("accessibility");
        this.e.removeJavascriptInterface("accessibilityTraversal");
        settings.setJavaScriptEnabled(true);
        this.e.setWebViewClient(new WebViewClient() { // from class: cn.com.chinatelecom.account.sdk.ui.PrivacyWebviewActivity.2
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                if (PrivacyWebviewActivity.this.f != null) {
                    PrivacyWebviewActivity.this.f.setVisibility(4);
                }
                super.onPageFinished(webView, str);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                return false;
            }
        });
        this.e.setWebChromeClient(new WebChromeClient() { // from class: cn.com.chinatelecom.account.sdk.ui.PrivacyWebviewActivity.3
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (PrivacyWebviewActivity.this.f != null) {
                    if (PrivacyWebviewActivity.this.f.getVisibility() != 0) {
                        PrivacyWebviewActivity.this.f.setVisibility(0);
                    }
                    if (i > 10) {
                        PrivacyWebviewActivity.this.f.setProgress(i);
                        PrivacyWebviewActivity.this.f.postInvalidate();
                    }
                }
            }
        });
        this.e.setOnLongClickListener(new View.OnLongClickListener() { // from class: cn.com.chinatelecom.account.sdk.ui.PrivacyWebviewActivity.4
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (this.e.canGoBack()) {
            this.e.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(bundle);
        this.b = cn.com.chinatelecom.account.sdk.a.d.a().b();
        if (this.b == null) {
            finish();
            return;
        }
        int iO = this.b.o();
        if (iO == 0) {
            finish();
        }
        this.c = cn.com.chinatelecom.account.sdk.a.d.a().c();
        setContentView(iO);
        this.g = getIntent().getStringExtra("privacyProtocolUrl");
        this.h = getIntent().getStringExtra("privacyProtocolTitle");
        if (TextUtils.isEmpty(this.h)) {
            this.h = CtAuth.CT_PRIVACY_TITLE;
        }
        a();
        d();
        if (this.c != null) {
            b();
            if (this.c.a != 0 && this.c.b) {
                e.a(this, this.c.a, this.c.b);
            }
        }
        c();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
    }
}