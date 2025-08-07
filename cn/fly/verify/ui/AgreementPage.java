package cn.fly.verify.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.fly.tools.utils.ResHelper;
import cn.fly.verify.OAuthPageEventCallback;
import cn.fly.verify.ag;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.datatype.UiSettings;
import cn.fly.verify.k;
import cn.fly.verify.ui.BasePage;
import cn.fly.verify.util.e;
import cn.fly.verify.util.l;
import cn.fly.verify.util.o;
import cn.fly.verify.v;

/* loaded from: classes.dex */
public class AgreementPage extends BasePage {
    private static final String TAG = "AgreementPage";
    private WebView agreementWv;
    private LandUiSettings landUiSettings;
    private int privacyType;
    private ProgressBar progressBar;
    private ag settings;
    private boolean showCustomTitleText = false;
    private String title;
    private UiSettings uiSettings;
    private String url;
    private OAuthPageEventCallback.OAuthPageEventWrapper wrapper;

    /* JADX WARN: Removed duplicated region for block: B:35:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void initView() {
        TextView textView;
        String strBy;
        this.agreementWv = (WebView) findViewByResName("fly_verify_page_agreement_wv");
        this.progressBar = (ProgressBar) findViewByResName("fly_verify_page_progressBar");
        if (this.settings != null) {
            this.centerTv.setTextColor(this.settings.bK());
            this.centerTv.setTypeface(this.settings.bL() ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            this.centerTv.setTextSize(this.settings.bJ());
            this.centerTv.setVisibility(this.settings.bM() ? 8 : 0);
            if (this.privacyType == 0 && !TextUtils.isEmpty(this.settings.bv())) {
                textView = this.centerTv;
                strBy = this.settings.bv();
            } else if (this.privacyType == 1 && !TextUtils.isEmpty(this.settings.bw())) {
                textView = this.centerTv;
                strBy = this.settings.bw();
            } else if (this.privacyType != 2 || TextUtils.isEmpty(this.settings.bx())) {
                if (this.privacyType == 3 && !TextUtils.isEmpty(this.settings.by())) {
                    textView = this.centerTv;
                    strBy = this.settings.by();
                }
                this.leftIv.setScaleType(this.settings.bI());
                this.leftIv.setImageDrawable(this.settings.bz());
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ResHelper.dipToPx(this.activity, 50), ResHelper.dipToPx(this.activity, 50));
                layoutParams.width = ResHelper.dipToPx(this.activity, this.settings.bG());
                layoutParams.width = ResHelper.dipToPx(this.activity, this.settings.bH());
                this.leftIv.setLayoutParams(layoutParams);
                this.leftIv.setVisibility(!this.settings.bF() ? 4 : 0);
            } else {
                textView = this.centerTv;
                strBy = this.settings.bx();
            }
            textView.setText(strBy);
            this.showCustomTitleText = true;
            this.leftIv.setScaleType(this.settings.bI());
            this.leftIv.setImageDrawable(this.settings.bz());
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ResHelper.dipToPx(this.activity, 50), ResHelper.dipToPx(this.activity, 50));
            layoutParams2.width = ResHelper.dipToPx(this.activity, this.settings.bG());
            layoutParams2.width = ResHelper.dipToPx(this.activity, this.settings.bH());
            this.leftIv.setLayoutParams(layoutParams2);
            this.leftIv.setVisibility(!this.settings.bF() ? 4 : 0);
        }
        if (e.m() >= 11) {
            this.agreementWv.removeJavascriptInterface("searchBoxJavaBridge_");
        }
        this.agreementWv.postDelayed(new Runnable() { // from class: cn.fly.verify.ui.AgreementPage.1
            @Override // java.lang.Runnable
            public void run() {
                AgreementPage.this.agreementWv.loadUrl(AgreementPage.this.url);
            }
        }, 500L);
        final WebSettings settings = this.agreementWv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        l.a(new e.a<Boolean>() { // from class: cn.fly.verify.ui.AgreementPage.2
            @Override // cn.fly.verify.util.e.a
            public void onResult(Boolean bool, Throwable th) {
                WebSettings webSettings;
                int i;
                if (bool.booleanValue()) {
                    webSettings = settings;
                    i = 2;
                } else {
                    webSettings = settings;
                    i = 1;
                }
                webSettings.setCacheMode(i);
            }
        });
        settings.setAllowFileAccess(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDomStorageEnabled(true);
        settings.setSavePassword(false);
        this.agreementWv.setWebViewClient(new WebViewClient() { // from class: cn.fly.verify.ui.AgreementPage.3
            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                Toast.makeText(AgreementPage.this.activity.getApplicationContext(), ResHelper.getStringRes(AgreementPage.this.activity.getApplicationContext(), "fly_verify_page_one_key_login_agreement_ssl_error"), 0).show();
                sslErrorHandler.cancel();
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                v.a("shouldOverrideUrlLoading Url: " + str);
                webView.loadUrl(str);
                return true;
            }
        });
        this.agreementWv.setWebChromeClient(new WebChromeClient() { // from class: cn.fly.verify.ui.AgreementPage.4
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i) {
                AgreementPage.this.progressBar.setProgress(i);
                if (i == 100) {
                    AgreementPage.this.progressBar.setVisibility(8);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView, String str) {
                super.onReceivedTitle(webView, str);
                if (AgreementPage.this.showCustomTitleText) {
                    return;
                }
                AgreementPage.this.centerTv.setText(str);
            }
        });
    }

    @Override // cn.fly.verify.ui.BasePage
    protected int getContentViewId() {
        return ResHelper.getLayoutRes(getContext(), "fly_verify_page_agreement");
    }

    @Override // cn.fly.verify.ui.BasePage
    protected void getTitleStyle(BasePage.TitleStyle titleStyle) {
    }

    @Override // cn.fly.tools.FakeActivity
    public void onDestroy() {
        super.onDestroy();
        this.wrapper = k.a().h();
        if (this.wrapper == null || this.wrapper.agreementPageClosed == null) {
            return;
        }
        this.wrapper.agreementPageClosed.handle();
    }

    @Override // cn.fly.verify.ui.BasePage
    protected void onViewCreated(Throwable th) {
        if (this.activity == null) {
            return;
        }
        this.settings = o.a(this.activity.getResources().getConfiguration().orientation);
        o.b(this.activity, this.settings);
        if ((this.settings == null || !this.settings.as()) && e.m() >= 21) {
            this.activity.getWindow().clearFlags(Integer.MIN_VALUE);
        }
        o.b(this.activity);
        Intent intent = this.activity.getIntent();
        this.url = intent.getStringExtra("extra_agreement_url");
        this.privacyType = intent.getIntExtra("privacyType", -1);
        initView();
    }
}