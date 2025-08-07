package cn.fly.verify.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.fly.FlySDK;
import cn.fly.tools.FakeActivity;
import cn.fly.tools.utils.ResHelper;
import cn.fly.verify.util.e;
import cn.fly.verify.v;

/* loaded from: classes.dex */
public abstract class BasePage extends FakeActivity implements View.OnClickListener {
    protected ViewGroup bodyRl;
    protected TextView centerTv;
    protected ViewGroup container;
    protected ImageView leftIv;
    protected ImageView rightIv;
    protected RelativeLayout titleBarRl;

    protected class TitleStyle {
        public boolean showLeft = true;
        public boolean showRight = false;
        public String titleResName;

        protected TitleStyle() {
        }
    }

    private void initView(Context context) {
        ImageView imageView;
        int stringRes;
        TitleStyle titleStyle = new TitleStyle();
        getTitleStyle(titleStyle);
        this.titleBarRl = (RelativeLayout) findViewByResName("fly_verify_title_bar_container");
        this.leftIv = (ImageView) findViewByResName("fly_verify_title_bar_left");
        this.leftIv.setOnClickListener(this);
        this.rightIv = (ImageView) findViewByResName("fly_verify_title_bar_right");
        this.rightIv.setOnClickListener(this);
        this.centerTv = (TextView) findViewByResName("fly_verify_title_bar_center");
        int i = 0;
        if (titleStyle.showLeft) {
            this.leftIv.setVisibility(0);
        } else {
            this.leftIv.setVisibility(8);
        }
        if (titleStyle.showRight) {
            imageView = this.rightIv;
        } else {
            imageView = this.rightIv;
            i = 4;
        }
        imageView.setVisibility(i);
        if (TextUtils.isEmpty(titleStyle.titleResName) || (stringRes = ResHelper.getStringRes(context, titleStyle.titleResName)) <= 0) {
            return;
        }
        this.centerTv.setText(stringRes);
    }

    protected abstract int getContentViewId();

    protected abstract void getTitleStyle(TitleStyle titleStyle);

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == this.leftIv.getId()) {
            if (onLeftEvent()) {
                return;
            }
            finish();
        } else if (id == this.rightIv.getId()) {
            onRightEvent();
        } else {
            onViewClicked(view);
        }
    }

    @Override // cn.fly.tools.FakeActivity
    public void onCreate() {
        try {
            Context context = getContext();
            if (context == null) {
                v.a("getContext is null,maybe fragment detach from activity");
                context = FlySDK.getContext();
                if (context == null) {
                    return;
                }
            }
            LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
            this.container = (ViewGroup) layoutInflaterFrom.inflate(ResHelper.getLayoutRes(context, "fly_verify_container"), (ViewGroup) null);
            int contentViewId = getContentViewId();
            if (contentViewId > 0) {
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
                View viewInflate = layoutInflaterFrom.inflate(contentViewId, (ViewGroup) null);
                this.bodyRl = (ViewGroup) viewInflate;
                this.container.addView(viewInflate, layoutParams);
            }
            if (this.activity == null) {
                return;
            }
            if (this.container.getParent() != null) {
                ((ViewGroup) this.container.getParent()).removeView(this.container);
            }
            this.activity.setContentView(this.container);
            e.m();
            initView(context);
            onViewCreated(null);
        } catch (Throwable th) {
            onViewCreated(th);
            finish();
        }
    }

    protected boolean onLeftEvent() {
        return false;
    }

    protected void onRightEvent() {
    }

    protected void onViewClicked(View view) {
    }

    protected abstract void onViewCreated(Throwable th);
}