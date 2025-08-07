package com.mobile.lantian;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobile.lantian.BaseActivity;

/* loaded from: classes.dex */
public class ResultActivity extends BaseActivity {
    private ImageView backIv;
    private ImageView phoneIv;
    private TextView phoneTv;
    private ImageView resultImgIv;
    private TextView successTv;
    private TextView tryAgainTv;

    @Override // com.mobile.lantian.BaseActivity
    protected int getContentViewId() {
        return R.layout.activity_result;
    }

    @Override // com.mobile.lantian.BaseActivity
    protected void getTitleStyle(BaseActivity.TitleStyle titleStyle) {
        titleStyle.showLeft = true;
    }

    @Override // com.mobile.lantian.BaseActivity
    protected void onViewCreated() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(1280);
            getWindow().clearFlags(67108864);
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(0);
        }
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
        initView();
        initContent();
    }

    private void initContent() {
        Intent intent = getIntent();
        if (intent.getBooleanExtra("fly_verify_demo_verify_success", true)) {
            this.resultImgIv.setImageDrawable(getResources().getDrawable(R.drawable.fly_verify_demo_verify_success));
            if (intent.hasExtra("result")) {
                String result = intent.getStringExtra("result");
                if (result != null) {
                    this.phoneTv.setText(result);
                    return;
                }
                return;
            }
            this.phoneTv.setVisibility(8);
            this.phoneIv.setVisibility(8);
            return;
        }
        this.resultImgIv.setImageDrawable(getResources().getDrawable(R.drawable.fly_verify_demo_verify_failed));
        this.successTv.setText("登录失败");
        this.phoneTv.setVisibility(8);
        this.phoneIv.setVisibility(8);
    }

    private void initView() {
        this.tryAgainTv = (TextView) findViewById(R.id.fly_verify_demo_verify_result_one_more_try);
        this.phoneTv = (TextView) findViewById(R.id.fly_verify_demo_success_phone_tv);
        this.successTv = (TextView) findViewById(R.id.fly_verify_demo_verify_result_success);
        this.resultImgIv = (ImageView) findViewById(R.id.fly_verify_demo_verify_result_image);
        this.backIv = (ImageView) findViewById(R.id.fly_verify_demo_title_bar_left_iv);
        this.phoneIv = (ImageView) findViewById(R.id.fly_verify_demo_success_phone_iv);
        this.backIv.setOnClickListener(this);
        this.tryAgainTv.setOnClickListener(this);
    }

    @Override // com.mobile.lantian.BaseActivity
    protected void onViewClicked(View v) {
        int id = v.getId();
        if (id == this.tryAgainTv.getId()) {
            finish();
        } else if (id == this.backIv.getId()) {
            finish();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onCreate(null);
    }
}