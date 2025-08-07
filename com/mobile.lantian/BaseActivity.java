package com.mobile.lantian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/* loaded from: classes.dex */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    private TextView centerTv;
    private TextView leftTv;
    private ImageView rightIv;

    protected abstract int getContentViewId();

    protected abstract void getTitleStyle(TitleStyle titleStyle);

    protected abstract void onViewCreated();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup container = (ViewGroup) inflater.inflate(R.layout.fly_verify_demo_container, (ViewGroup) null);
        int contentId = getContentViewId();
        if (contentId > 0) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            View content = inflater.inflate(contentId, (ViewGroup) null);
            container.addView(content, params);
        }
        setContentView(container);
        onViewCreated();
        initView();
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == this.leftTv.getId()) {
            boolean handled = onLeftEvent();
            if (!handled) {
                finish();
                return;
            }
            return;
        }
        if (id == this.rightIv.getId()) {
            onRightEvent();
        } else {
            onViewClicked(v);
        }
    }

    protected void onRightEvent() {
    }

    protected boolean onLeftEvent() {
        return false;
    }

    protected void onViewClicked(View v) {
    }

    private void initView() {
        TitleStyle titleStyle = new TitleStyle();
        getTitleStyle(titleStyle);
        this.leftTv = (TextView) findViewById(R.id.fly_verify_demo_title_bar_left);
        this.leftTv.setOnClickListener(this);
        this.rightIv = (ImageView) findViewById(R.id.fly_verify_demo_title_bar_right);
        this.rightIv.setOnClickListener(this);
        this.centerTv = (TextView) findViewById(R.id.fly_verify_demo_title_bar_center);
        if (titleStyle.showLeft) {
            this.leftTv.setVisibility(0);
        } else {
            this.leftTv.setVisibility(8);
        }
        if (titleStyle.showRight) {
            this.rightIv.setVisibility(0);
        } else {
            this.rightIv.setVisibility(8);
        }
        TextUtils.isEmpty(titleStyle.titleResName);
    }

    protected class TitleStyle {
        public boolean showLeft = true;
        public boolean showRight = false;
        public String titleResName;

        protected TitleStyle() {
        }
    }
}