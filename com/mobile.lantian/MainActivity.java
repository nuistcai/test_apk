package com.mobile.lantian;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.ViewCompat;
import cn.fly.verify.CustomUIRegister;
import cn.fly.verify.CustomViewClickListener;
import cn.fly.verify.FlyVerify;
import cn.fly.verify.GetTokenCallback;
import cn.fly.verify.OAuthPageEventCallback;
import cn.fly.verify.PageCallback;
import cn.fly.verify.PreVerifyCallback;
import cn.fly.verify.UiLocationHelper;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.datatype.UiSettings;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.ui.component.CommonProgressDialog;
import com.mobile.lantian.login.LoginTask;
import com.mobile.lantian.util.CustomizeUtils;
import com.mobile.lantian.util.PrivacyDialogUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import pl.droidsonroids.gif.GifImageView;

/* loaded from: classes.dex */
public class MainActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1001;
    private static final String TAG = "MainActivity-Demo";
    private TextView appNameTv;
    private CheckBox cbAutoFinish;
    private CheckBox cbOtherAutoFinish;
    private View layoutTest;
    private GifImageView logoIv;
    private TextView tvResult;
    private TextView uiTest;
    private Button verifyBtn;
    private Button verifyDialogBtn;
    private TextView versionTv;
    private boolean devMode = true;
    private int defaultUi = 0;
    private boolean isPreVerifyDone = true;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(getApplication());
        CrashReport.initCrashReport(getApplicationContext(), "28fe0803ee", false);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(1280);
            getWindow().clearFlags(67108864);
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(0);
            if (Build.VERSION.SDK_INT >= 23) {
                getWindow().getDecorView().setSystemUiVisibility(9216);
            }
        }
        initView();
        PrivacyDialogUtils privacyDialogUtils = new PrivacyDialogUtils();
        privacyDialogUtils.setDismissListener(new DialogInterface.OnDismissListener() { // from class: com.mobile.lantian.MainActivity.1
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                MainActivity.this.init();
            }
        });
        if (!privacyDialogUtils.showPrivacyDialogIfNeed(this, "秒验")) {
            init();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
            requestPermissions(new String[]{"android.permission.READ_PHONE_STATE"}, 0);
        }
        FlyVerify.setUiSettings(new UiSettings.Builder().build());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fly_verify_demo_main_preverify) {
            preVerify();
        } else if (id == R.id.fly_verify_demo_main_verify) {
            addCustomView();
            verify();
        }
    }

    private void initView() {
        findViewById(R.id.fly_verify_demo_main_verify2).setOnClickListener(this);
        findViewById(R.id.fly_verify_demo_main_preverify).setOnClickListener(this);
        this.layoutTest = findViewById(R.id.layoutTest);
        this.logoIv = (GifImageView) findViewById(R.id.fly_verify_demo_main_logo);
        this.verifyBtn = (Button) findViewById(R.id.fly_verify_demo_main_verify_dialog);
        this.verifyDialogBtn = (Button) findViewById(R.id.fly_verify_demo_main_verify);
        this.tvResult = (TextView) findViewById(R.id.tvResult);
        this.appNameTv = (TextView) findViewById(R.id.fly_verify_demo_main_app_name);
        this.appNameTv.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.mobile.lantian.MainActivity.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View v) {
                MainActivity.this.layoutTest.setVisibility(MainActivity.this.layoutTest.getVisibility() == 8 ? 0 : 8);
                return false;
            }
        });
        this.versionTv = (TextView) findViewById(R.id.fly_verify_demo_main_version);
        this.versionTv.setText(FlyVerify.getVersion());
        this.logoIv.setImageResource(R.drawable.fly_verify_demo_tradition);
        this.verifyBtn.setOnClickListener(this);
        this.verifyDialogBtn.setOnClickListener(this);
        this.logoIv.setOnClickListener(this);
        this.appNameTv.setOnClickListener(this);
        this.versionTv.setOnClickListener(this);
        this.cbAutoFinish = (CheckBox) findViewById(R.id.cbAutoFinish);
        this.cbOtherAutoFinish = (CheckBox) findViewById(R.id.cbOtherAutoFinish);
        findViewById(R.id.btnOther).setOnClickListener(this);
    }

    private void preVerify() {
        this.isPreVerifyDone = false;
        FlyVerify.setDebugMode(true);
        FlyVerify.preVerify(new PreVerifyCallback() { // from class: com.mobile.lantian.MainActivity.3
            @Override // cn.fly.verify.OperationCallback
            public void onComplete(Void data) {
                if (MainActivity.this.devMode) {
                    Toast.makeText(MainActivity.this, "预登录成功", 0).show();
                }
                MainActivity.this.tvResult.setText("预取号成功");
                MainActivity.this.isPreVerifyDone = true;
            }

            @Override // cn.fly.verify.OperationCallback
            public void onFailure(VerifyException e) {
                MainActivity.this.isPreVerifyDone = true;
                Throwable t = e.getCause();
                String errDetail = null;
                if (t != null) {
                    errDetail = t.getMessage();
                }
                if (MainActivity.this.devMode) {
                    Log.e(MainActivity.TAG, "preVerify failed", e);
                    int errCode = e.getCode();
                    String errMsg = e.getMessage();
                    String msg = "错误码: " + errCode + "\n错误信息: " + errMsg;
                    if (!TextUtils.isEmpty(errDetail)) {
                        msg = msg + "\n详细信息: " + errDetail;
                    }
                    MainActivity.this.tvResult.setText("预取号失败：" + msg);
                    Log.e(MainActivity.TAG, msg);
                    Toast.makeText(MainActivity.this, msg, 0).show();
                }
            }
        });
    }

    private void verify() {
        CommonProgressDialog.showProgressDialog(this);
        FlyVerify.OtherOAuthPageCallBack(new OAuthPageEventCallback() { // from class: com.mobile.lantian.MainActivity.4
            @Override // cn.fly.verify.OAuthPageEventCallback
            public void initCallback(OAuthPageEventCallback.OAuthPageEventResultCallback cb) {
                cb.pageOpenCallback(new OAuthPageEventCallback.PageOpenedCallback() { // from class: com.mobile.lantian.MainActivity.4.1
                    @Override // cn.fly.verify.OAuthPageEventCallback.PageOpenedCallback
                    public void handle() {
                        Log.i(MainActivity.TAG, System.currentTimeMillis() + " pageOpened");
                    }
                });
                cb.loginBtnClickedCallback(new OAuthPageEventCallback.LoginBtnClickedCallback() { // from class: com.mobile.lantian.MainActivity.4.2
                    @Override // cn.fly.verify.OAuthPageEventCallback.LoginBtnClickedCallback
                    public void handle() {
                        Log.i(MainActivity.TAG, System.currentTimeMillis() + " loginBtnClicked");
                    }
                });
                cb.agreementPageClosedCallback(new OAuthPageEventCallback.AgreementPageClosedCallback() { // from class: com.mobile.lantian.MainActivity.4.3
                    @Override // cn.fly.verify.OAuthPageEventCallback.AgreementPageClosedCallback
                    public void handle() {
                        Log.i(MainActivity.TAG, System.currentTimeMillis() + " agreementPageClosed");
                    }
                });
                cb.agreementPageOpenedCallback(new OAuthPageEventCallback.AgreementClickedCallback() { // from class: com.mobile.lantian.MainActivity.4.4
                    @Override // cn.fly.verify.OAuthPageEventCallback.AgreementClickedCallback
                    public void handle() {
                        Log.i(MainActivity.TAG, System.currentTimeMillis() + " agreementPageOpened");
                    }
                });
                cb.cusAgreement1ClickedCallback(new OAuthPageEventCallback.CusAgreement1ClickedCallback() { // from class: com.mobile.lantian.MainActivity.4.5
                    @Override // cn.fly.verify.OAuthPageEventCallback.CusAgreement1ClickedCallback
                    public void handle() {
                        Log.i(MainActivity.TAG, System.currentTimeMillis() + " cusAgreement1ClickedCallback");
                    }
                });
                cb.cusAgreement2ClickedCallback(new OAuthPageEventCallback.CusAgreement2ClickedCallback() { // from class: com.mobile.lantian.MainActivity.4.6
                    @Override // cn.fly.verify.OAuthPageEventCallback.CusAgreement2ClickedCallback
                    public void handle() {
                        Log.i(MainActivity.TAG, System.currentTimeMillis() + " cusAgreement2ClickedCallback");
                    }
                });
                cb.checkboxStatusChangedCallback(new OAuthPageEventCallback.CheckboxStatusChangedCallback() { // from class: com.mobile.lantian.MainActivity.4.7
                    @Override // cn.fly.verify.OAuthPageEventCallback.CheckboxStatusChangedCallback
                    public void handle(boolean b) {
                        Toast.makeText(MainActivity.this, "勾上" + b, 0).show();
                        Log.i(MainActivity.TAG, System.currentTimeMillis() + " current status is " + b);
                    }
                });
                cb.pageCloseCallback(new OAuthPageEventCallback.PageClosedCallback() { // from class: com.mobile.lantian.MainActivity.4.8
                    @Override // cn.fly.verify.OAuthPageEventCallback.PageClosedCallback
                    public void handle() {
                        Log.i(MainActivity.TAG, System.currentTimeMillis() + " pageClosed");
                        HashMap<String, List<Integer>> map = UiLocationHelper.getInstance().getViewLocations();
                        if (map == null) {
                            return;
                        }
                        for (String key : map.keySet()) {
                            List<Integer> locats = map.get(key);
                            if (locats != null && locats.size() > 0) {
                                Iterator<Integer> it = locats.iterator();
                                while (it.hasNext()) {
                                    int i = it.next().intValue();
                                    Log.i(MainActivity.TAG, i + " xywh");
                                }
                            }
                        }
                    }
                });
            }
        });
        FlyVerify.verify(new PageCallback() { // from class: com.mobile.lantian.MainActivity.5
            @Override // cn.fly.verify.PageCallback
            public void pageCallback(int code, String desc) {
                Log.i(MainActivity.TAG, "pageCallback code=" + code + ",desc=" + desc);
            }
        }, new GetTokenCallback() { // from class: com.mobile.lantian.MainActivity.6
            @Override // cn.fly.verify.OperationCallback
            public void onComplete(VerifyResult data) {
                MainActivity.this.tvResult.setText("取号成功");
                MainActivity.this.tokenToPhone(data);
            }

            @Override // cn.fly.verify.OperationCallback
            public void onFailure(VerifyException e) {
                MainActivity.this.showExceptionMsg(e);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tokenToPhone(VerifyResult data) {
        CommonProgressDialog.dismissProgressDialog();
        if (data != null) {
            Log.d(TAG, data.toJSONString());
            CommonProgressDialog.showProgressDialog(this);
            Log.i("sss", "返回" + data.toJSONString());
            LoginTask.getInstance().login(data, new ResultListener<String>() { // from class: com.mobile.lantian.MainActivity.7
                @Override // com.mobile.lantian.ResultListener
                public void onComplete(String data2) {
                    CommonProgressDialog.dismissProgressDialog();
                    Log.d(MainActivity.TAG, "Login success. data: " + data2);
                    MainActivity.this.goResultActivity(data2);
                }

                @Override // com.mobile.lantian.ResultListener
                public void onFailure(Throwable e) {
                    Log.e(MainActivity.TAG, "login failed", e);
                    CommonProgressDialog.dismissProgressDialog();
                    String msg = "获取授权码成功，应用服务器登录失败\n错误信息: " + e.getMessage();
                    Toast.makeText(MainActivity.this, msg, 0).show();
                    MainActivity.this.goResultActivity(null);
                }
            });
        }
    }

    public void showExceptionMsg(VerifyException e) {
        String msg;
        if (this.defaultUi == 1) {
            FlyVerify.finishOAuthPage();
        }
        CommonProgressDialog.dismissProgressDialog();
        int errCode = e.getCode();
        String errMsg = e.getMessage();
        Throwable t = e.getCause();
        String errDetail = null;
        if (t != null) {
            errDetail = t.getMessage();
        }
        String msg2 = "错误码: " + errCode + "\n错误信息: " + errMsg;
        if (!TextUtils.isEmpty(errDetail)) {
            msg = msg2 + "\n详细信息: " + errDetail;
        } else {
            msg = errMsg;
        }
        this.tvResult.setText("取号失败：" + msg);
        Toast.makeText(this, msg, 0).show();
        goResultActivity(null);
    }

    private void addCustomView() {
        CustomUIRegister.addCustomizedUi(CustomizeUtils.buildCustomView(this), new CustomViewClickCallback(0));
        ArrayList arrayList = new ArrayList();
        TextView textView = new TextView(this);
        textView.setText("返回");
        textView.setId(R.id.customized_view_id_div);
        textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        textView.setTextSize(16.0f);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
        params.addRule(15);
        params.leftMargin = (int) TypedValue.applyDimension(1, 35.0f, getResources().getDisplayMetrics());
        textView.setLayoutParams(params);
        arrayList.add(textView);
        CustomUIRegister.addTitleBarCustomizedUi(arrayList, new CustomViewClickCallback(1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goResultActivity(String data) {
        Intent i = new Intent(this, (Class<?>) ResultActivity.class);
        if (data != null) {
            i.putExtra("fly_verify_demo_verify_success", true);
            i.putExtra("result", data);
        } else {
            i.putExtra("fly_verify_demo_verify_success", false);
        }
        if (data != null) {
            startActivityForResult(i, 1001);
        }
        CommonProgressDialog.dismissProgressDialog();
    }

    private static class CustomViewClickCallback implements CustomViewClickListener {
        private int customUI;

        public CustomViewClickCallback(int customUI) {
            this.customUI = customUI;
        }

        @Override // cn.fly.verify.CustomViewClickListener
        public void onClick(View view) {
            int id;
            if (this.customUI == 0) {
                if (view.getId() == R.id.customized_btn_id_1) {
                    Intent intent = new Intent(view.getContext(), (Class<?>) ResultActivity.class);
                    intent.setFlags(268435456);
                    view.getContext().startActivity(intent);
                    Toast.makeText(view.getContext(), "微信 clicked", 0).show();
                }
            } else if (this.customUI == 1) {
                FlyVerify.finishOAuthPage();
            } else if (this.customUI == 4 && (id = view.getId()) != R.id.customized_btn_id_1 && id == R.id.customized_view_id) {
                return;
            }
            CommonProgressDialog.dismissProgressDialog();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onCreate(null);
    }
}