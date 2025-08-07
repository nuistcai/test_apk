package com.mobile.lantian.util;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.util.DisplayMetrics;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import cn.fly.verify.FlyVerify;

/* loaded from: classes.dex */
public class PrivacyDialogUtils {
    public static final String KEY_PRIVACY = "privacy";
    private DialogInterface.OnDismissListener dismissListener;
    private AlertDialog downloadDialog;
    private SharedPreferences sharedPreferences;

    public void setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    public boolean showPrivacyDialogIfNeed(Activity activity, String sdkName) {
        if (this.downloadDialog != null && this.downloadDialog.isShowing()) {
            return true;
        }
        this.sharedPreferences = activity.getSharedPreferences(KEY_PRIVACY, 0);
        if (this.sharedPreferences.getBoolean(KEY_PRIVACY, false)) {
            return false;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        WebView webView = new WebView(activity);
        int width = screenWidth - 60;
        LinearLayout.LayoutParams webViewParams = new LinearLayout.LayoutParams(width, (int) (width * 1.1f));
        webView.setScrollBarStyle(0);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setSavePassword(false);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(0);
        }
        webView.setWebViewClient(new WebViewClient() { // from class: com.mobile.lantian.util.PrivacyDialogUtils.1
            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                try {
                    handler.proceed();
                } catch (Throwable th) {
                }
            }
        });
        linearLayout.addView(webView, webViewParams);
        try {
            AlertDialog.Builder b = new AlertDialog.Builder(activity, R.style.Theme.Material.Light.Dialog);
            b.setTitle(sdkName + " 隐私政策");
            b.setCancelable(false);
            b.setView(linearLayout);
            b.setPositiveButton("同意", new DialogInterface.OnClickListener() { // from class: com.mobile.lantian.util.PrivacyDialogUtils.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    FlyVerify.submitPolicyGrantResult(true);
                    PrivacyDialogUtils.this.sharedPreferences.edit().putBoolean(PrivacyDialogUtils.KEY_PRIVACY, true).apply();
                }
            });
            b.setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.mobile.lantian.util.PrivacyDialogUtils.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            webView.loadUrl("file:///android_asset/www/privacySec.html");
            this.downloadDialog = b.create();
            if (this.dismissListener != null) {
                this.downloadDialog.setOnDismissListener(this.dismissListener);
            }
            this.downloadDialog.show();
        } catch (Throwable th) {
        }
        return true;
    }
}