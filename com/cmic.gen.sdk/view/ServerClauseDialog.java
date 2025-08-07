package com.cmic.gen.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.InputDeviceCompat;
import com.cmic.gen.sdk.ConstantsEx;
import com.cmic.gen.sdk.auth.GenAuthnHelper;

/* compiled from: ServerClauseDialog.java */
/* renamed from: com.cmic.gen.sdk.view.d, reason: use source file name */
/* loaded from: classes.dex */
public class ServerClauseDialog extends Dialog {
    private WebView a;
    private String b;
    private String c;
    private LinearLayout d;

    public ServerClauseDialog(Context context, int i, String str, String str2) {
        super(context, i);
        try {
            this.c = str;
            this.b = str2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void a() {
        requestWindowFeature(1);
        int i = 0;
        getWindow().setFeatureDrawableAlpha(0, 0);
        GenAuthThemeConfig authThemeConfig = GenAuthnHelper.getInstance(getContext()).getAuthThemeConfig();
        if (Build.VERSION.SDK_INT >= 21 && authThemeConfig.getStatusBarColor() != 0) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().clearFlags(67108864);
            getWindow().setStatusBarColor(authThemeConfig.getClauseStatusColor());
            getWindow().setNavigationBarColor(authThemeConfig.getStatusBarColor());
        }
        if (Build.VERSION.SDK_INT >= 23 && authThemeConfig.isLightColor()) {
            i = 8192;
        }
        if (authThemeConfig.isNavHidden()) {
            i |= InputDeviceCompat.SOURCE_TOUCHSCREEN;
        }
        getWindow().getDecorView().setSystemUiVisibility(i);
        setContentView(c());
    }

    private ViewGroup c() {
        String str;
        View viewFindViewById;
        try {
            this.d = new LinearLayout(getContext());
            this.d.setOrientation(1);
            this.d.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            GenAuthThemeConfig authThemeConfig = GenAuthnHelper.getInstance(getContext()).getAuthThemeConfig();
            int clauseLayoutResID = authThemeConfig.getClauseLayoutResID();
            String str2 = TextUtils.isEmpty(this.c) ? ConstantsEx.g[authThemeConfig.getAppLanguageType()] : this.c;
            if (!authThemeConfig.isGetWebViewTitle()) {
                str = str2;
            } else {
                str = "";
            }
            if (clauseLayoutResID != -1) {
                String clauseLayoutReturnID = authThemeConfig.getClauseLayoutReturnID();
                RelativeLayout relativeLayoutA = UmcActivityUtil.a(getContext(), getLayoutInflater().inflate(clauseLayoutResID, (ViewGroup) this.d, false), 1118481, ResourceUtil.a(getContext(), clauseLayoutReturnID), str, null);
                if (!TextUtils.isEmpty(clauseLayoutReturnID) && (viewFindViewById = relativeLayoutA.findViewById(ResourceUtil.a(getContext(), clauseLayoutReturnID))) != null) {
                    viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.cmic.gen.sdk.view.d.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            ServerClauseDialog.this.a.stopLoading();
                            ServerClauseDialog.this.b();
                        }
                    });
                }
                this.d.addView(relativeLayoutA);
            } else {
                this.d.addView(UmcActivityUtil.a(getContext(), null, 1118481, 2236962, str, new View.OnClickListener() { // from class: com.cmic.gen.sdk.view.d.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        ServerClauseDialog.this.a.stopLoading();
                        ServerClauseDialog.this.b();
                    }
                }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.d;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        if (this.a != null) {
            this.a.stopLoading();
        }
    }

    public void b() {
        if (this.a.canGoBack()) {
            this.a.goBack();
        } else {
            dismiss();
        }
    }

    @Override // android.app.Dialog
    public void show() {
        if (this.d == null) {
            a();
        }
        if (this.a == null) {
            d();
        }
        super.show();
    }

    private void d() {
        final GenAuthThemeConfig authThemeConfig = GenAuthnHelper.getInstance(getContext()).getAuthThemeConfig();
        this.a = new WebView(getContext());
        WebSettings settings = this.a.getSettings();
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setSavePassword(false);
        settings.setDomStorageEnabled(authThemeConfig.getWebStorage());
        settings.setJavaScriptEnabled(true);
        this.d.addView(this.a, new LinearLayout.LayoutParams(-1, -1));
        this.a.setWebViewClient(new WebViewClient() { // from class: com.cmic.gen.sdk.view.d.3
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (authThemeConfig.isGetWebViewTitle()) {
                    ((TextView) ServerClauseDialog.this.d.findViewById(1118481).findViewById(3355443)).setText(ServerClauseDialog.this.a.getTitle());
                }
            }
        });
        this.a.loadUrl(this.b);
    }
}