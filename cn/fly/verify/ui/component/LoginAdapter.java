package cn.fly.verify.ui.component;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/* loaded from: classes.dex */
public class LoginAdapter {
    private Activity activity;
    private Button btnLogin;
    private CheckBox cbAgreement;
    private CompoundButton.OnCheckedChangeListener checkedChangeListener;
    private ImageView ivLeftClose;
    private ImageView ivLogo;
    private View.OnClickListener listener;
    private String operator;
    private RelativeLayout rlAgreement;
    private RelativeLayout rlPhone;
    private RelativeLayout rlTitle;
    private TextView tvAgreement;
    private TextView tvCenterText;
    private TextView tvSecurityPhone;
    private TextView tvSlogan;
    private TextView tvSwitchAcc;
    private ViewGroup vgBody;
    private ViewGroup vgContainer;

    public Activity getActivity() {
        return this.activity;
    }

    public CheckBox getAgreementCheckbox() {
        return this.cbAgreement;
    }

    public RelativeLayout getAgreementLayout() {
        return this.rlAgreement;
    }

    public TextView getAgreementText() {
        return this.tvAgreement;
    }

    public ViewGroup getBodyView() {
        return this.vgBody;
    }

    public TextView getCenterText() {
        return this.tvCenterText;
    }

    public ViewGroup getContainerView() {
        return this.vgContainer;
    }

    public ImageView getLeftCloseImage() {
        return this.ivLeftClose;
    }

    public Button getLoginBtn() {
        return this.btnLogin;
    }

    public ImageView getLogoImage() {
        return this.ivLogo;
    }

    public String getOperator() {
        return this.operator;
    }

    public String getOperatorName() {
        return this.operator;
    }

    public RelativeLayout getPhoneLayout() {
        return this.rlPhone;
    }

    public TextView getSecurityPhoneText() {
        return this.tvSecurityPhone;
    }

    public TextView getSloganText() {
        return this.tvSlogan;
    }

    public TextView getSwitchAccText() {
        return this.tvSwitchAcc;
    }

    public RelativeLayout getTitlelayout() {
        return this.rlTitle;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton.getId() != this.cbAgreement.getId() || this.checkedChangeListener == null) {
            return;
        }
        this.checkedChangeListener.onCheckedChanged(compoundButton, z);
    }

    public void onClick(View view) {
        int id = view.getId();
        if ((this.listener != null && id == this.btnLogin.getId()) || id == this.tvSwitchAcc.getId() || id == this.ivLeftClose.getId() || id == this.cbAgreement.getId()) {
            this.listener.onClick(view);
        }
    }

    public void onCreate() {
    }

    public void onDestroy() {
    }

    public void onResume() {
    }

    void setActivity(Activity activity) {
        this.activity = activity;
    }

    void setAgreementCheckbox(CheckBox checkBox) {
        this.cbAgreement = checkBox;
    }

    void setAgreementLayout(RelativeLayout relativeLayout) {
        this.rlAgreement = relativeLayout;
    }

    void setAgreementText(TextView textView) {
        this.tvAgreement = textView;
    }

    void setBodyView(ViewGroup viewGroup) {
        this.vgBody = viewGroup;
    }

    void setCenterText(TextView textView) {
        this.tvCenterText = textView;
    }

    void setContainerView(ViewGroup viewGroup) {
        this.vgContainer = viewGroup;
    }

    void setLeftCloseImage(ImageView imageView) {
        this.ivLeftClose = imageView;
    }

    void setLoginBtn(Button button) {
        this.btnLogin = button;
    }

    void setLogoImage(ImageView imageView) {
        this.ivLogo = imageView;
    }

    void setOnCheckChangedListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.checkedChangeListener = onCheckedChangeListener;
    }

    void setOnClickListener(View.OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    void setOperator(String str) {
        this.operator = str;
    }

    void setOperatorName(String str) {
        this.operator = str;
    }

    void setPhoneLayout(RelativeLayout relativeLayout) {
        this.rlPhone = relativeLayout;
    }

    void setSecurityPhoneText(TextView textView) {
        this.tvSecurityPhone = textView;
    }

    void setSloganText(TextView textView) {
        this.tvSlogan = textView;
    }

    void setSwitchAccText(TextView textView) {
        this.tvSwitchAcc = textView;
    }

    void setTitleLayout(RelativeLayout relativeLayout) {
        this.rlTitle = relativeLayout;
    }
}