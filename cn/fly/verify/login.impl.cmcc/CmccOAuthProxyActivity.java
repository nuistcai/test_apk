package cn.fly.verify.login.impl.cmcc;

import android.R;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.tools.utils.ResHelper;
import cn.fly.verify.OAuthPageEventCallback;
import cn.fly.verify.PageCallback;
import cn.fly.verify.ab;
import cn.fly.verify.ag;
import cn.fly.verify.ai;
import cn.fly.verify.common.exception.VerifyException;
import cn.fly.verify.d;
import cn.fly.verify.datatype.VerifyResult;
import cn.fly.verify.e;
import cn.fly.verify.i;
import cn.fly.verify.k;
import cn.fly.verify.m;
import cn.fly.verify.u;
import cn.fly.verify.ui.component.CommonProgressDialog;
import cn.fly.verify.ui.component.OneKeyLoginLayout;
import cn.fly.verify.util.l;
import cn.fly.verify.util.o;
import cn.fly.verify.v;
import cn.fly.verify.x;
import cn.fly.verify.y;
import com.cmic.gen.sdk.auth.GenTokenListener;
import com.cmic.gen.sdk.e.OverTimeUtils;
import com.cmic.gen.sdk.view.GenLoginAuthActivity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class CmccOAuthProxyActivity extends GenLoginAuthActivity implements y {
    private static final String TAG = "CmccOAuthProxyActivity";
    private static CmccOAuthProxyActivity instance;
    private d<VerifyResult> callback;
    private ImageButton closeBtn;
    private ViewGroup contentView;
    private String fakeNum;
    private u logRecorder;
    private TextView numberTv;
    private OneKeyLoginLayout oneKeyLoginLayout;
    private RelativeLayout parentLoginRl;
    private CheckBox privacyCb;
    private ag settings;
    private OAuthPageEventCallback.OAuthPageEventWrapper wrapper;
    private boolean restoreCheckboxState = false;
    private final GenTokenListener listener = new GenTokenListener() { // from class: cn.fly.verify.login.impl.cmcc.CmccOAuthProxyActivity.1
        @Override // com.cmic.gen.sdk.auth.GenTokenListener
        public void onGetTokenComplete(int i, JSONObject jSONObject) {
            CmccOAuthProxyActivity cmccOAuthProxyActivity;
            ab abVarD;
            try {
                v.a("new listener onGetTokenComplete: " + jSONObject.toString());
                cn.fly.verify.datatype.d dVar = new cn.fly.verify.datatype.d(i, jSONObject);
                if (dVar.c() == 200020) {
                    return;
                }
                if (dVar.c() == 200060) {
                    if (i.a().n()) {
                        abVarD = ab.d();
                        abVarD.b();
                    } else {
                        cmccOAuthProxyActivity = CmccOAuthProxyActivity.this;
                        cmccOAuthProxyActivity.reduceLoginCount();
                    }
                }
                String strB = dVar.b();
                e.b(0);
                String strA = ai.a(ab.d().b, ab.d().d, ab.d().e, ab.d().f);
                if (!dVar.a() || TextUtils.isEmpty(strA)) {
                    CmccOAuthProxyActivity.this.callback.a(new VerifyException(dVar.c(), jSONObject == null ? null : jSONObject.toString()));
                } else {
                    CmccOAuthProxyActivity.this.callback.a((d) new VerifyResult(strB, strA, ab.d().i));
                }
                if (i.a().m()) {
                    abVarD = ab.d();
                    abVarD.b();
                } else {
                    cmccOAuthProxyActivity = CmccOAuthProxyActivity.this;
                    cmccOAuthProxyActivity.reduceLoginCount();
                }
            } catch (Throwable th) {
                v.a(th, "CMCC onGetTokenComplete error");
                CmccOAuthProxyActivity.this.callback.a(new VerifyException(m.INNER_OTHER_EXCEPTION_ERR.a(), l.a(th)));
            }
        }
    };

    private void addView() {
        this.oneKeyLoginLayout = new OneKeyLoginLayout(this, ab.d().i, this);
        setContentView(this.oneKeyLoginLayout);
        this.contentView = (ViewGroup) ((ViewGroup) getWindow().getDecorView().findViewById(R.id.content)).getChildAt(0);
        if (this.contentView != null) {
            this.contentView.setOnClickListener(this);
        }
    }

    private static List<View> getChild(View view) {
        ArrayList arrayList = new ArrayList();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                arrayList.add(childAt);
                arrayList.addAll(getChild(childAt));
            }
        }
        return arrayList;
    }

    public static CmccOAuthProxyActivity getInstance() {
        return instance;
    }

    private void getOtherOauthPageCallback() {
        this.wrapper = k.a().h();
        if (this.wrapper != null && this.wrapper.pageOpened != null) {
            try {
                this.wrapper.pageOpened.handle();
            } catch (Throwable th) {
                v.a(th, "pageOpened ==> User Code error");
            }
        }
        PageCallback pageCallbackJ = k.a().j();
        if (pageCallbackJ != null) {
            pageCallbackJ.pageCallback(6119140, l.a("oauthpage_opened", "oauthpage opened"));
        }
        i.a().b(true);
    }

    private void initView() {
        if (this.contentView != null) {
            for (View view : getChild(this.contentView)) {
                if (view instanceof CheckBox) {
                    this.privacyCb = (CheckBox) view;
                }
            }
            this.parentLoginRl = (RelativeLayout) this.contentView.findViewById(17476);
            this.closeBtn = (ImageButton) this.contentView.findViewById(26214);
            this.numberTv = (TextView) this.contentView.findViewById(30583);
            this.contentView.setVisibility(8);
        }
        if (this.numberTv != null) {
            this.fakeNum = this.numberTv.getText().toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reduceLoginCount() {
        try {
            ReflectHelper.setInstanceField(getInstance(), "v", Integer.valueOf(((Integer) ReflectHelper.getInstanceField(getInstance(), "v")).intValue() - 1));
        } catch (Throwable th) {
            v.a("reflect cm sdk filed == v == failed,please check CMSDK");
        }
    }

    @Override // cn.fly.verify.y
    public void cancelLogin() {
        x.b().a(true);
        i.a().a(true);
        i.a().b(false);
        if (this.closeBtn != null) {
            this.closeBtn.performClick();
            return;
        }
        if (this.callback != null) {
            this.callback.a(new VerifyException(m.INNER_CANCEL_LOGIN));
        }
        finish();
    }

    @Override // cn.fly.verify.y
    public void customizeLogin() {
        if (this.logRecorder != null) {
            this.logRecorder.a(ab.d().i, ab.d().b, "login_start");
        }
        if (this.privacyCb != null) {
            this.privacyCb.setChecked(true);
        }
        if (this.parentLoginRl != null) {
            this.parentLoginRl.performClick();
        }
        if (this.wrapper == null || this.wrapper.loginBtnClicked == null) {
            return;
        }
        try {
            this.wrapper.loginBtnClicked.handle();
        } catch (Throwable th) {
            v.a(th, "loginBtnClicked ==> User Code error");
        }
    }

    @Override // cn.fly.verify.y
    public void doOtherLogin() {
        if (this.callback != null) {
            this.callback.a(new VerifyException(m.INNER_OTHER_LOGIN));
        }
        if (i.a().n()) {
            ab.d().b();
        }
    }

    @Override // android.app.Activity
    public void finish() {
        int animRes;
        String str;
        int animRes2;
        k.a().g();
        instance = null;
        x.b().a(true);
        if (this.settings == null) {
            return;
        }
        if (this.settings.an()) {
            animRes = this.settings.aq();
            animRes2 = this.settings.ar();
        } else {
            if (this.settings.ah()) {
                animRes = ResHelper.getAnimRes(this, "fly_verify_translate_in");
                str = "fly_verify_translate_out";
            } else if (this.settings.aj()) {
                animRes = ResHelper.getAnimRes(this, "fly_verify_translate_right_in");
                str = "fly_verify_translate_left_out";
            } else if (this.settings.ai()) {
                animRes = ResHelper.getAnimRes(this, "fly_verify_translate_bottom_in");
                str = "fly_verify_translate_bottom_out";
            } else {
                if (!this.settings.ak()) {
                    if (this.settings.al()) {
                        animRes = ResHelper.getAnimRes(this, "fly_verify_fade_in");
                        str = "fly_verify_fade_out";
                    }
                    super.finish();
                }
                animRes = ResHelper.getAnimRes(this, "fly_verify_zoom_in");
                str = "fly_verify_zoom_out";
            }
            animRes2 = ResHelper.getAnimRes(this, str);
        }
        overridePendingTransition(animRes, animRes2);
        super.finish();
    }

    @Override // cn.fly.verify.y
    public d<VerifyResult> getCallback() {
        return ab.d().e();
    }

    @Override // cn.fly.verify.y
    public String getFakeNumber() {
        return TextUtils.isEmpty(this.fakeNum) ? "" : this.fakeNum;
    }

    @Override // com.cmic.gen.sdk.view.GenLoginAuthActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (this.contentView == null || id != this.contentView.getId() || this.settings == null || !this.settings.aT()) {
            return;
        }
        cancelLogin();
    }

    @Override // com.cmic.gen.sdk.view.GenLoginAuthActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        getOtherOauthPageCallback();
        o.a(this);
        this.callback = getCallback();
        this.settings = o.a(getResources().getConfiguration().orientation);
        o.a(this, this.settings);
        super.onCreate(bundle);
        if (bundle != null) {
            finish();
            return;
        }
        if (cn.fly.verify.util.e.m() >= 21) {
            getWindow().clearFlags(67108864);
            getWindow().clearFlags(134217728);
        }
        o.b(this, this.settings);
        o.b(this);
        instance = this;
        this.contentView = (ViewGroup) ((ViewGroup) getWindow().getDecorView().findViewById(R.id.content)).getChildAt(0);
        if (this.contentView != null) {
            this.contentView.setOnClickListener(this);
        }
        initView();
        addView();
        x.b().a(false);
        this.logRecorder = x.b().a();
        if (this.logRecorder != null) {
            this.logRecorder.a(ab.d().i, ab.d().b, "open_authpage_end");
        }
        try {
            String stringExtra = getIntent().getStringExtra("traceId");
            if (!TextUtils.isEmpty(stringExtra)) {
                OverTimeUtils.a(stringExtra, this.listener);
            }
        } catch (Throwable th) {
            v.a(th, "cm sdk may changed,please check");
        }
        d<VerifyResult> dVarE = ab.d().e();
        if (dVarE == null || !dVarE.a.getAndSet(true)) {
            return;
        }
        v.a("auth success after timeout");
        if (this.logRecorder != null) {
            this.logRecorder.a("CMCC", (String) null, "timeout_success");
            this.logRecorder.b();
        }
        finish();
    }

    @Override // com.cmic.gen.sdk.view.GenLoginAuthActivity, android.app.Activity
    protected void onDestroy() {
        instance = null;
        i.a().a(true);
        i.a().b(false);
        CommonProgressDialog.dismissProgressDialog();
        super.onDestroy();
        if (this.oneKeyLoginLayout != null && this.oneKeyLoginLayout.getLoginAdapter() != null) {
            this.oneKeyLoginLayout.getLoginAdapter().onDestroy();
        }
        k.a().g();
        if (this.wrapper == null || this.wrapper.pageclosed == null) {
            return;
        }
        try {
            this.wrapper.pageclosed.handle();
        } catch (Throwable th) {
            v.a(th, "pageclosed ==> User Code error");
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getRepeatCount() != 0) {
            return false;
        }
        super.onKeyDown(i, keyEvent);
        cancelLogin();
        return false;
    }

    @Override // com.cmic.gen.sdk.view.GenLoginAuthActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.oneKeyLoginLayout == null || this.oneKeyLoginLayout.getLoginAdapter() == null) {
            return;
        }
        this.oneKeyLoginLayout.getLoginAdapter().onResume();
    }

    public void refresh() {
        if (this.oneKeyLoginLayout != null) {
            this.restoreCheckboxState = this.oneKeyLoginLayout.getCheckboxState();
        }
        onCreate(null);
        if (this.oneKeyLoginLayout != null) {
            this.oneKeyLoginLayout.resetCheckboxState(this.restoreCheckboxState);
        }
    }
}