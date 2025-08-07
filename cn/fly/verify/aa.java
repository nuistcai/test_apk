package cn.fly.verify;

import android.text.TextUtils;
import android.widget.ImageView;
import cn.com.chinatelecom.account.sdk.AuthPageConfig;
import cn.fly.FlySDK;
import cn.fly.tools.utils.ResHelper;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.datatype.UiSettings;
import com.cmic.gen.sdk.view.GenAuthThemeConfig;

/* loaded from: classes.dex */
public class aa {
    private static aa aH;
    public static final int a = cn.fly.verify.util.n.getColorRes(FlySDK.getContext(), "fly_verify_common_bg");
    public static final int b = cn.fly.verify.util.n.getColorRes(FlySDK.getContext(), "fly_verify_text_color_common_black");
    public static final int c = cn.fly.verify.util.n.getBitmapRes(FlySDK.getContext(), "fly_verify_page_one_key_login_close");
    public static final int d = cn.fly.verify.util.n.getBitmapRes(FlySDK.getContext(), "fly_verify_page_one_key_login_logo");
    public static final int e = cn.fly.verify.util.n.a("fly_verify_text_size_m");
    public static final int f = cn.fly.verify.util.n.getColorRes(FlySDK.getContext(), "fly_verify_text_color_common_gray");
    public static final int g = cn.fly.verify.util.n.getColorRes(FlySDK.getContext(), "fly_verify_text_color_blue");
    public static final int h = cn.fly.verify.util.n.getColorRes(FlySDK.getContext(), "fly_verify_text_color_common_gray");
    public static final int i = cn.fly.verify.util.n.getColorRes(FlySDK.getContext(), "fly_verify_main_color");
    public static final int j = cn.fly.verify.util.n.getBitmapRes(FlySDK.getContext(), "fly_verify_shape_rectangle");
    public static final int k = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_login");
    public static final int l = cn.fly.verify.util.n.getColorRes(FlySDK.getContext(), "fly_verify_text_color_common_white");
    public static final int m = cn.fly.verify.util.n.getBitmapRes(FlySDK.getContext(), "customized_checkbox_selector");
    public static final int n = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_other_login");
    public static final int o = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_agreement_customize_1");
    public static final int p = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_agreement_customize_2");
    public static final int q = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_agreement_customize_3");
    public static final int r = cn.fly.verify.util.n.getBitmapRes(FlySDK.getContext(), "fly_verify_background");
    public static final int s = cn.fly.verify.util.n.a("fly_verify_logo_width");
    public static final int t = cn.fly.verify.util.n.a("fly_verify_logo_height");
    public static final int u = cn.fly.verify.util.n.a("fly_verify_logo_offset_y");
    public static final int v = cn.fly.verify.util.n.a("fly_verify_number_field_offset_y");
    public static final int w = cn.fly.verify.util.n.a("fly_verify_switch_acc_offset_y");
    public static final int x = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_other_login");
    public static final int y = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_toast_agreement");
    public static final int z = cn.fly.verify.util.n.a("fly_verify_agreement_offset_y");
    public static final int A = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_agreement_tv_cmcc");
    public static final int B = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_agreement_tv_cmhk");
    public static final int C = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_agreement_tv");
    public static final int D = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_service_and_privacy");
    public static final int E = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_privacy_part1");
    public static final int F = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_cus_privacy_pre_1");
    public static final int G = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_cus_privacy_pre_2");
    public static final int H = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_cus_privacy_pre_3");
    public static final int I = cn.fly.verify.util.n.getStringRes(FlySDK.getContext(), "fly_verify_page_one_key_login_privacy_part2");
    public static final int J = cn.fly.verify.util.n.a("fly_verify_text_size_s");
    public static final int K = cn.fly.verify.util.n.a("fly_verify_text_size_xs");
    public static final int L = cn.fly.verify.util.n.a("fly_verify_login_btn_height");
    public static final int M = cn.fly.verify.util.n.a("fly_verify_login_btn_offset_y");
    public static final int N = cn.fly.verify.util.n.getColorRes(FlySDK.getContext(), "fly_verify_text_color_common_gray");
    public static final int O = cn.fly.verify.util.n.a("fly_verify_height_title_bar");
    public static final int P = cn.fly.verify.util.n.a("fly_verify_height_title_bar");
    public static final int Q = cn.fly.verify.util.n.a("fly_verify_logo_land_offset_y");
    public static final int R = cn.fly.verify.util.n.a("fly_verify_number_field_land_offset_y");
    public static final int S = cn.fly.verify.util.n.a("fly_verify_switch_acc_land_offset_y");
    public static final int T = cn.fly.verify.util.n.a("fly_verify_agreement_land_offset_y");
    public static final int U = cn.fly.verify.util.n.a("fly_verify_login_btn_land_width");
    public static final int V = cn.fly.verify.util.n.a("fly_verify_login_btn_land_offset_y");
    public static final int W = cn.fly.verify.util.n.a("fly_verify_slogan_land_offset_bottom_y");
    public static final int X = cn.fly.verify.util.n.a("fly_verify_logo_dialog_offset_y");
    public static final int Y = cn.fly.verify.util.n.a("fly_verify_number_field_dialog_offset_y");
    public static final int Z = cn.fly.verify.util.n.a("fly_verify_switch_acc_dialog_offset_y");
    public static final int aa = cn.fly.verify.util.n.a("fly_verify_agreement_dialog_offset_y");
    public static final int ab = cn.fly.verify.util.n.a("fly_verify_login_btn_dialog_offset_y");
    public static final int ac = cn.fly.verify.util.n.a("fly_verify_logo_dialog_offset_x");
    public static final int ad = cn.fly.verify.util.n.a("fly_verify_number_field_dialog_offset_x");
    public static final int ae = cn.fly.verify.util.n.a("fly_verify_switch_acc_dialog_offset_x");
    public static final int af = cn.fly.verify.util.n.a("fly_verify_logo_align_bottom_dialog_offset_y");
    public static final int ag = cn.fly.verify.util.n.a("fly_verify_number_align_bottom_field_dialog_offset_y");
    public static final int ah = cn.fly.verify.util.n.a("fly_verify_switch_acc_align_bottom_dialog_offset_y");
    public static final int ai = cn.fly.verify.util.n.a("fly_verify_login_btn_align_bottom_dialog_offset_y");
    public static final int aj = cn.fly.verify.util.n.a("fly_verify_agreement_align_bottom_dialog_offset_y");
    public static final int ak = cn.fly.verify.util.n.a("fly_verify_agreement_align_bottom_dialog_offset_x");
    public static final int al = cn.fly.verify.util.n.a("fly_verify_logo_land_dialog_offset_x");
    public static final int am = cn.fly.verify.util.n.a("fly_verify_logo_land_dialog_offset_y");
    public static final int an = cn.fly.verify.util.n.a("fly_verify_number_land_field_dialog_offset_x");
    public static final int ao = cn.fly.verify.util.n.a("fly_verify_number_land_field_dialog_offset_y");
    public static final int ap = cn.fly.verify.util.n.a("fly_verify_switch_acc_land_dialog_offset_x");
    public static final int aq = cn.fly.verify.util.n.a("fly_verify_switch_acc_land_dialog_offset_y");
    public static final int ar = cn.fly.verify.util.n.a("fly_verify_agreement_land_dialog_offset_y");
    public static final int as = cn.fly.verify.util.n.a("fly_verify_login_btn_land_dialog_offset_y");
    public static final int at = cn.fly.verify.util.n.a("fly_verify_logo_align_bottom_land_dialog_offset_y");
    public static final int au = cn.fly.verify.util.n.a("fly_verify_number_align_bottom_field_land_dialog_offset_y");
    public static final int av = cn.fly.verify.util.n.a("fly_verify_switch_acc_align_bottom_land_dialog_offset_y");
    public static final int aw = cn.fly.verify.util.n.a("fly_verify_login_btn_align_bottom_land_dialog_offset_y");
    public static final int ax = cn.fly.verify.util.n.a("fly_verify_agreement_align_bottom_land_dialog_offset_y");
    public static final int ay = cn.fly.verify.util.n.a("fly_verify_agreement_align_bottom_land_dialog_offset_x");
    public static final int az = cn.fly.verify.util.n.a("fly_verify_slogan_align_bottom_land_dialog_offset_x");
    public static final int aA = cn.fly.verify.util.n.a("fly_verify_slogan_align_bottom_land_dialog_offset_y");
    public static final int aB = cn.fly.verify.util.n.a("fly_verify_dialog_width");
    public static final int aC = cn.fly.verify.util.n.a("fly_verify_dialog_height");
    public static final int aD = cn.fly.verify.util.n.a("fly_verify_dialog_offset_x");
    public static final int aE = cn.fly.verify.util.n.a("fly_verify_dialog_offset_y");
    public static final int aF = cn.fly.verify.util.n.a("fly_verify_land_dialog_height");
    public static final int aG = cn.fly.verify.util.n.getBitmapRes(FlySDK.getContext(), "fly_verify_dialog_background");

    private aa() {
    }

    public static aa a() {
        if (aH == null) {
            synchronized (aa.class) {
                if (aH == null) {
                    aH = new aa();
                }
            }
        }
        return aH;
    }

    private GenAuthThemeConfig.Builder c() {
        double dPxToDip = cn.fly.verify.util.n.pxToDip(FlySDK.getContext(), cn.fly.verify.util.n.getScreenHeight(FlySDK.getContext()));
        Double.isNaN(dPxToDip);
        Double.isNaN(dPxToDip);
        int i2 = (int) (0.35d * dPxToDip);
        Double.isNaN(dPxToDip);
        return new GenAuthThemeConfig.Builder().setNavTextColor(cn.fly.verify.util.n.c(b)).setNumberColor(cn.fly.verify.util.n.c(b)).setNumberSize(cn.fly.verify.util.n.b(e), false).setLogBtnImgPath(cn.fly.verify.util.n.g(j)).setLogBtnText(cn.fly.verify.util.n.d(k)).setLogBtnTextColor(cn.fly.verify.util.n.c(l)).setPrivacyState(true).setClauseColor(cn.fly.verify.util.n.c(h), cn.fly.verify.util.n.c(i)).setUncheckedImgPath(cn.fly.verify.util.n.g(m)).setCheckedImgPath(cn.fly.verify.util.n.g(m)).setNumFieldOffsetY((int) (0.2d * dPxToDip)).setLogBtnOffsetY(i2).setPrivacyOffsetY((int) (dPxToDip * 0.43d));
    }

    private ag d() {
        return new ag().a(cn.fly.verify.util.n.c(a)).a("").b(cn.fly.verify.util.n.c(b)).a(cn.fly.verify.util.n.e(c)).d(true).c(false).e(false).N(cn.fly.verify.util.n.b(J)).O(cn.fly.verify.util.n.b(O)).P(cn.fly.verify.util.n.b(P)).Q(-1).R(-1).S(-1).e(cn.fly.verify.util.n.e(r)).f(false).b(cn.fly.verify.util.n.h(d)).g(false).i(cn.fly.verify.util.n.b(s)).j(cn.fly.verify.util.n.b(t)).k(-1).l(cn.fly.verify.util.n.b(u)).ab(-1).X(-1).z(false).c(cn.fly.verify.util.n.c(b)).d(cn.fly.verify.util.n.b(e)).m(-1).n(cn.fly.verify.util.n.b(v)).t(false).ac(-1).Y(-1).A(false).e(cn.fly.verify.util.n.c(g)).f(cn.fly.verify.util.n.b(J)).a(false).F(-1).G(cn.fly.verify.util.n.b(w)).b(cn.fly.verify.util.n.d(x)).ad(-1).Z(-1).B(false).d(cn.fly.verify.util.n.e(j)).i(cn.fly.verify.util.n.d(k)).h(cn.fly.verify.util.n.c(l)).v(cn.fly.verify.util.n.b(J)).w(-1).x(cn.fly.verify.util.n.b(L)).y(-1).z(cn.fly.verify.util.n.b(M)).v(false).ae(-1).aa(-1).D(false).b(false).g(cn.fly.verify.util.n.c(i)).c(cn.fly.verify.util.n.e(m)).h(false).a(1.0f).b(1.0f).o(-1).p(-1).q(cn.fly.verify.util.n.b(z)).r(-1).H(cn.fly.verify.util.n.c(b)).j(false).I(cn.fly.verify.util.n.b(K)).u(false).C(false).j(cn.fly.verify.util.n.d(A)).k(cn.fly.verify.util.n.d(B)).l(cn.fly.verify.util.n.d(D)).m(cn.fly.verify.util.n.d(C)).n(cn.fly.verify.util.n.d(E)).o(cn.fly.verify.util.n.d(F)).p(cn.fly.verify.util.n.d(G)).q(cn.fly.verify.util.n.d(H)).r(cn.fly.verify.util.n.d(I)).i(false).E(cn.fly.verify.util.n.c(N)).D(cn.fly.verify.util.n.b(K)).A(-1).B(-1).C(30).af(-1).E(false).s(cn.fly.verify.util.n.c(i)).t(cn.fly.verify.util.n.c(i)).u(cn.fly.verify.util.n.c(i)).w(false).x(false).k(false).m(false).l(false).n(false).o(false).ag(0).s(cn.fly.verify.util.n.d(y)).a(ImageView.ScaleType.CENTER_INSIDE).b(ImageView.ScaleType.CENTER_INSIDE).g(cn.fly.verify.util.n.e(c)).O(false).Q(false).al(cn.fly.verify.util.n.b(O)).am(cn.fly.verify.util.n.b(P)).ao(cn.fly.verify.util.n.c(b)).an(cn.fly.verify.util.n.b(J)).P(false);
    }

    private ag e() {
        return new ag().a(cn.fly.verify.util.n.c(a)).a("").b(cn.fly.verify.util.n.c(b)).a(cn.fly.verify.util.n.e(c)).d(true).c(false).N(cn.fly.verify.util.n.b(J)).e(false).O(cn.fly.verify.util.n.b(O)).P(cn.fly.verify.util.n.b(P)).Q(-1).R(-1).S(-1).e(cn.fly.verify.util.n.e(r)).f(false).b(cn.fly.verify.util.n.h(d)).g(false).i(cn.fly.verify.util.n.b(s)).j(cn.fly.verify.util.n.b(t)).k(-1).l(cn.fly.verify.util.n.b(Q)).ab(-1).X(-1).z(false).c(cn.fly.verify.util.n.c(b)).d(cn.fly.verify.util.n.b(e)).m(-1).n(cn.fly.verify.util.n.b(R)).ac(-1).Y(-1).t(false).A(false).e(cn.fly.verify.util.n.c(g)).f(cn.fly.verify.util.n.b(J)).a(false).F(-1).G(cn.fly.verify.util.n.b(S)).b(cn.fly.verify.util.n.d(x)).ad(-1).Z(-1).B(false).d(cn.fly.verify.util.n.e(j)).i(cn.fly.verify.util.n.d(k)).h(cn.fly.verify.util.n.c(l)).v(cn.fly.verify.util.n.b(J)).w(cn.fly.verify.util.n.b(U)).x(cn.fly.verify.util.n.b(L)).y(-1).z(cn.fly.verify.util.n.b(V)).ae(-1).aa(-1).D(false).v(false).b(false).g(cn.fly.verify.util.n.c(i)).c(cn.fly.verify.util.n.e(m)).h(false).a(1.0f).b(1.0f).o(-1).p(-1).q(cn.fly.verify.util.n.b(T)).r(-1).H(cn.fly.verify.util.n.c(b)).j(false).C(false).u(false).I(cn.fly.verify.util.n.b(K)).j(cn.fly.verify.util.n.d(A)).k(cn.fly.verify.util.n.d(B)).l(cn.fly.verify.util.n.d(D)).m(cn.fly.verify.util.n.d(C)).n(cn.fly.verify.util.n.d(E)).o(cn.fly.verify.util.n.d(F)).p(cn.fly.verify.util.n.d(G)).q(cn.fly.verify.util.n.d(H)).r(cn.fly.verify.util.n.d(I)).i(false).E(cn.fly.verify.util.n.c(N)).D(cn.fly.verify.util.n.b(K)).A(-1).B(-1).C(cn.fly.verify.util.n.b(W)).af(-1).E(false).i(false).s(cn.fly.verify.util.n.c(i)).t(cn.fly.verify.util.n.c(i)).u(cn.fly.verify.util.n.c(i)).k(false).m(false).l(false).n(false).o(false).w(false).x(false).ag(0).s(cn.fly.verify.util.n.d(y)).a(ImageView.ScaleType.CENTER_INSIDE).b(ImageView.ScaleType.CENTER_INSIDE).g(cn.fly.verify.util.n.e(c)).O(false).Q(false).al(cn.fly.verify.util.n.b(O)).am(cn.fly.verify.util.n.b(P)).ao(cn.fly.verify.util.n.c(b)).an(cn.fly.verify.util.n.b(J)).P(false);
    }

    public ag a(LandUiSettings landUiSettings) {
        ag agVarE = e();
        if (landUiSettings != null) {
            agVarE.a(landUiSettings.getNeedFitsSystemWindow()).a(cn.fly.verify.util.n.b(landUiSettings.getNavColorId(), a)).a(cn.fly.verify.util.n.a(landUiSettings.getNavTextId(), landUiSettings.getNavText(), -1)).b(cn.fly.verify.util.n.b(landUiSettings.getNavTextColorId(), b)).a(cn.fly.verify.util.n.a(landUiSettings.getNavCloseImgId(), landUiSettings.getNavCloseImg(), c)).d(landUiSettings.isNavTransparent()).c(landUiSettings.isNavHidden()).e(landUiSettings.isNavCloseImgHidden()).a(landUiSettings.getNavCloseImgScaleType()).N(cn.fly.verify.util.n.a(landUiSettings.getNavTextSize(), J)).O(cn.fly.verify.util.n.a(landUiSettings.getNavCloseImgWidth(), O)).P(cn.fly.verify.util.n.a(landUiSettings.getNavCloseImgHeight(), P)).Q(cn.fly.verify.util.n.a(landUiSettings.getNavCloseImgOffsetX(), -1)).R(cn.fly.verify.util.n.a(landUiSettings.getNavCloseImgOffsetRightX(), -1)).S(cn.fly.verify.util.n.a(landUiSettings.getNavCloseImgOffsetY(), -1)).e(cn.fly.verify.util.n.a(landUiSettings.getBackgroundImgId(), landUiSettings.getBackgroundImg(), r)).f(landUiSettings.isBackgroundClickClose()).b(cn.fly.verify.util.n.a(landUiSettings.getLogoImgId(), landUiSettings.getLogoImg(), cn.fly.verify.util.n.i(d))).g(landUiSettings.isLogoHidden()).i(cn.fly.verify.util.n.a(landUiSettings.getLogoWidth(), s)).j(cn.fly.verify.util.n.a(landUiSettings.getLogoHeight(), t)).k(cn.fly.verify.util.n.a(landUiSettings.getLogoOffsetX(), -1)).l(cn.fly.verify.util.n.a(landUiSettings.getLogoOffsetY(), Q)).X(cn.fly.verify.util.n.a(landUiSettings.getLogoOffsetBottomY(), -1)).ab(cn.fly.verify.util.n.a(landUiSettings.getLogoOffsetRightX(), -1)).z(landUiSettings.isLogoAlignParentRight()).c(cn.fly.verify.util.n.b(landUiSettings.getNumberColorId(), b)).d(cn.fly.verify.util.n.a(landUiSettings.getNumberSizeId(), e)).m(cn.fly.verify.util.n.a(landUiSettings.getNumberOffsetX(), -1)).n(cn.fly.verify.util.n.a(landUiSettings.getNumberOffsetY(), R)).t(landUiSettings.isNumberHidden()).Y(cn.fly.verify.util.n.a(landUiSettings.getNumberOffsetBottomY(), -1)).ac(cn.fly.verify.util.n.a(landUiSettings.getNumberOffsetRightX(), -1)).A(landUiSettings.isNumberAlignParentRight()).e(cn.fly.verify.util.n.b(landUiSettings.getSwitchAccColorId(), g)).f(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccTextSize(), J)).a(landUiSettings.isSwitchAccHidden()).F(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccOffsetX(), -1)).G(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccOffsetY(), S)).b(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccText(), landUiSettings.getSwitchAccTextString(), x)).Z(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccOffsetBottomY(), -1)).ad(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccOffsetRightX(), -1)).B(landUiSettings.isSwitchAccAlignParentRight()).d(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnImgId(), landUiSettings.getLoginBtnImg(), j)).i(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnTextId(), landUiSettings.getLoginBtnText(), k)).h(cn.fly.verify.util.n.b(landUiSettings.getLoginBtnTextColorId(), l)).v(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnTextSize(), J)).w(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnWidth(), U)).x(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnHeight(), L)).y(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnOffsetX(), -1)).z(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnOffsetY(), V)).v(landUiSettings.isLoginBtnHidden()).aa(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnOffsetBottomY(), -1)).ae(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnOffsetRightX(), -1)).D(landUiSettings.isLoginBtnAlignParentRight()).b(landUiSettings.getCheckboxDefaultState()).g(cn.fly.verify.util.n.b(landUiSettings.getAgreementColorId(), i)).c(cn.fly.verify.util.n.a(landUiSettings.getCheckboxImgId(), landUiSettings.getCheckboxImg(), m)).h(landUiSettings.isCheckboxHidden()).ah(cn.fly.verify.util.n.a(landUiSettings.getCheckboxOffsetX(), -1)).ai(cn.fly.verify.util.n.a(landUiSettings.getCheckboxOffsetRightX(), -1)).aj(cn.fly.verify.util.n.a(landUiSettings.getCheckboxOffsetY(), -1)).ak(cn.fly.verify.util.n.a(landUiSettings.getCheckboxOffsetBottomY(), -1)).a(landUiSettings.getCheckboxScaleX()).b(landUiSettings.getCheckboxScaleY()).H(cn.fly.verify.util.n.b(landUiSettings.getAgreementBaseTextColorId(), b)).j(landUiSettings.isAgreementGravityLeft()).o(cn.fly.verify.util.n.a(landUiSettings.getAgreementOffsetX(), -1)).p(cn.fly.verify.util.n.a(landUiSettings.getAgreementOffsetRightX(), -1)).q(cn.fly.verify.util.n.a(landUiSettings.getAgreementOffsetY(), T)).r(cn.fly.verify.util.n.a(landUiSettings.getAgreementOffsetBottomY(), -1)).I(cn.fly.verify.util.n.a(landUiSettings.getAgreementTextSize(), K)).u(landUiSettings.isAgreementHidden()).j(cn.fly.verify.util.n.a(landUiSettings.getAgreementCmccText(), landUiSettings.getAgreementCmccTextString(), A)).k(cn.fly.verify.util.n.a(landUiSettings.getAgreementCMHKText(), landUiSettings.getAgreementCMHKTextString(), B)).l(cn.fly.verify.util.n.a(landUiSettings.getAgreementCuccText(), landUiSettings.getAgreementCuccTextString(), D)).m(cn.fly.verify.util.n.a(landUiSettings.getAgreementCtccText(), landUiSettings.getAgreementCtccTextString(), C)).n(cn.fly.verify.util.n.a(landUiSettings.getAgreementTextStart(), landUiSettings.getAgreementTextStartString(), E)).o(cn.fly.verify.util.n.a(landUiSettings.getAgreementTextAnd1(), landUiSettings.getAgreementTextAndString1(), F)).p(cn.fly.verify.util.n.a(landUiSettings.getAgreementTextAnd2(), landUiSettings.getAgreementTextAndString2(), G)).q(cn.fly.verify.util.n.a(landUiSettings.getAgreementTextAnd3(), landUiSettings.getAgreementTextAndString3(), H)).r(cn.fly.verify.util.n.a(landUiSettings.getAgreementTextEnd(), landUiSettings.getAgreementTextEndString(), I)).C(landUiSettings.isAgreementAlignParentRight()).i(landUiSettings.isSloganHidden()).E(cn.fly.verify.util.n.b(landUiSettings.getSloganTextColor(), N)).D(cn.fly.verify.util.n.a(landUiSettings.getSloganTextSize(), K)).A(cn.fly.verify.util.n.a(landUiSettings.getSloganOffsetX(), -1)).B(cn.fly.verify.util.n.a(landUiSettings.getSloganOffsetY(), -1)).af(cn.fly.verify.util.n.a(landUiSettings.getSloganOffsetRightX(), -1)).E(landUiSettings.isSloganAlignParentRight()).r(landUiSettings.isImmersiveTheme()).s(landUiSettings.isImmersiveStatusTextColorBlack()).w(landUiSettings.isDialogTheme()).x(landUiSettings.isDialogAlignBottom()).k(landUiSettings.isTranslateAnim()).m(landUiSettings.isRightTranslateAnim()).l(landUiSettings.isBottomTranslateAnim()).n(landUiSettings.isZoomAnim()).o(landUiSettings.isFadeAnim()).ag(landUiSettings.getAgreementUncheckHintType()).s(cn.fly.verify.util.n.a(landUiSettings.getAgreementUncheckHintTextId(), landUiSettings.getAgreementUncheckHintText(), y)).F(landUiSettings.isNavTextBold()).H(landUiSettings.isNumberBold()).I(landUiSettings.isSwitchAccTextBold()).K(landUiSettings.isLoginBtnTextBold()).J(landUiSettings.isAgreementTextBold()).L(landUiSettings.isSloganTextBold()).M(landUiSettings.isAgreementTextWithUnderLine()).G(landUiSettings.isFullScreen()).N(landUiSettings.isVirtualButtonTransparent()).t(cn.fly.verify.util.n.a(landUiSettings.getAgreementPageTitle(), landUiSettings.getAgreementPageTitleString(), -1)).u(cn.fly.verify.util.n.a(landUiSettings.getCusAgreementPageOneTitle(), landUiSettings.getCusAgreementPageOneTitleString(), -1)).v(cn.fly.verify.util.n.a(landUiSettings.getCusAgreementPageTwoTitle(), landUiSettings.getCusAgreementPageTwoTitleString(), -1)).w(cn.fly.verify.util.n.a(landUiSettings.getCusAgreementPageThreeTitle(), landUiSettings.getCusAgreementPageThreeTitleString(), -1)).g(cn.fly.verify.util.n.a(landUiSettings.getAgreementPageCloseImgId(), landUiSettings.getAgreementPageCloseImg(), c)).al(cn.fly.verify.util.n.a(landUiSettings.getAgreementPageCloseImgWidth(), O)).am(cn.fly.verify.util.n.a(landUiSettings.getAgreementPageCloseImgHeight(), P)).O(landUiSettings.isAgreementPageCloseImgHidden()).b(landUiSettings.getAgreementPageCloseImgScaleType()).Q(landUiSettings.isAgreementPageTitleHidden()).ao(cn.fly.verify.util.n.b(landUiSettings.getAgreementPageTitleTextColor(), b)).an(cn.fly.verify.util.n.a(landUiSettings.getAgreementPageTitleTextSize(), J)).P(landUiSettings.isAgreementPageTitleTextBold());
            agVarE.C(cn.fly.verify.util.n.a(landUiSettings.getSloganOffsetY(), -1) <= 0 ? cn.fly.verify.util.n.a(landUiSettings.getSloganOffsetBottomY(), W) : cn.fly.verify.util.n.a(landUiSettings.getSloganOffsetBottomY(), -1));
            if ((landUiSettings.getCusAgreementNameId1() > 0 || !TextUtils.isEmpty(landUiSettings.getCusAgreementName1())) && !TextUtils.isEmpty(landUiSettings.getCusAgreementUrl1())) {
                agVarE.c(cn.fly.verify.util.n.a(landUiSettings.getCusAgreementNameId1(), landUiSettings.getCusAgreementName1(), o)).d(landUiSettings.getCusAgreementUrl1());
                agVarE.s(cn.fly.verify.util.n.b(landUiSettings.isCusAgreementColor1Seted() ? landUiSettings.getCusAgreementColor1() : landUiSettings.getAgreementColorId(), i));
            }
            if ((landUiSettings.getCusAgreementNameId2() > 0 || !TextUtils.isEmpty(landUiSettings.getCusAgreementName2())) && !TextUtils.isEmpty(landUiSettings.getCusAgreementUrl2())) {
                agVarE.e(cn.fly.verify.util.n.a(landUiSettings.getCusAgreementNameId2(), landUiSettings.getCusAgreementName2(), p)).f(landUiSettings.getCusAgreementUrl2());
                agVarE.t(cn.fly.verify.util.n.b(landUiSettings.isCusAgreementColor2Seted() ? landUiSettings.getCusAgreementColor2() : landUiSettings.getAgreementColorId(), i));
            }
            if ((landUiSettings.getCusAgreementNameId3() > 0 || !TextUtils.isEmpty(landUiSettings.getCusAgreementName3())) && !TextUtils.isEmpty(landUiSettings.getCusAgreementUrl3())) {
                agVarE.g(cn.fly.verify.util.n.a(landUiSettings.getCusAgreementNameId3(), landUiSettings.getCusAgreementName3(), q)).h(landUiSettings.getCusAgreementUrl3());
                agVarE.u(cn.fly.verify.util.n.b(landUiSettings.isCusAgreementColor3Seted() ? landUiSettings.getCusAgreementColor3() : landUiSettings.getAgreementColorId(), i));
            }
            if (landUiSettings.getAgreementText() != null) {
                agVarE.a(landUiSettings.getAgreementText());
            }
            if (landUiSettings.getAgreementUncheckToast() != null) {
                agVarE.a(landUiSettings.getAgreementUncheckToast());
            }
            if (landUiSettings.hasStartActivityAnim()) {
                agVarE.p(true).J(cn.fly.verify.util.n.e(landUiSettings.getStartInAnim(), cn.fly.verify.util.n.getAnimRes(FlySDK.getContext(), "fly_verify_translate_in"))).K(cn.fly.verify.util.n.e(landUiSettings.getStartOutAnim(), cn.fly.verify.util.n.getAnimRes(FlySDK.getContext(), "fly_verify_translate_out")));
            }
            if (landUiSettings.hasFinishActivityAnim()) {
                agVarE.q(true).L(cn.fly.verify.util.n.e(landUiSettings.getFinishInAnim(), cn.fly.verify.util.n.getAnimRes(FlySDK.getContext(), "fly_verify_translate_in"))).M(cn.fly.verify.util.n.e(landUiSettings.getFinishOutAnim(), cn.fly.verify.util.n.getAnimRes(FlySDK.getContext(), "fly_verify_translate_out")));
            }
            if (landUiSettings.isDialogTheme()) {
                agVarE.V(cn.fly.verify.util.n.a(landUiSettings.getDialogWidth(), aB)).W(cn.fly.verify.util.n.a(landUiSettings.getDialogHeight(), aC)).T(cn.fly.verify.util.n.a(landUiSettings.getDialogOffsetX(), aD)).U(cn.fly.verify.util.n.a(landUiSettings.getDialogOffsetY(), aE)).f(cn.fly.verify.util.n.a(landUiSettings.getDialogMaskBackground(), landUiSettings.getDialogBackgroundDrawable(), aG)).y(landUiSettings.isDialogBackgroundClickClose()).k(cn.fly.verify.util.n.a(landUiSettings.getLogoOffsetX(), al)).l(cn.fly.verify.util.n.a(landUiSettings.getLogoOffsetY(), am)).m(cn.fly.verify.util.n.a(landUiSettings.getNumberOffsetX(), an)).n(cn.fly.verify.util.n.a(landUiSettings.getNumberOffsetY(), ao)).F(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccOffsetX(), ap)).G(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccOffsetY(), aq)).z(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnOffsetY(), as)).q(cn.fly.verify.util.n.a(landUiSettings.getAgreementOffsetY(), ar)).o(cn.fly.verify.util.n.a(landUiSettings.getAgreementOffsetX(), -1)).p(cn.fly.verify.util.n.a(landUiSettings.getAgreementOffsetRightX(), -1));
                if (landUiSettings.isDialogAlignBottom()) {
                    agVarE.W(cn.fly.verify.util.n.a(landUiSettings.getDialogHeight(), aF)).k(cn.fly.verify.util.n.a(landUiSettings.getLogoOffsetX(), ac)).l(cn.fly.verify.util.n.a(landUiSettings.getLogoOffsetY(), at)).m(cn.fly.verify.util.n.a(landUiSettings.getNumberOffsetX(), ad)).n(cn.fly.verify.util.n.a(landUiSettings.getNumberOffsetY(), au)).F(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccOffsetX(), ae)).G(cn.fly.verify.util.n.a(landUiSettings.getSwitchAccOffsetY(), av)).z(cn.fly.verify.util.n.a(landUiSettings.getLoginBtnOffsetY(), aw)).q(cn.fly.verify.util.n.a(landUiSettings.getAgreementOffsetY(), ax)).o(cn.fly.verify.util.n.a(landUiSettings.getAgreementOffsetY(), ay)).A(cn.fly.verify.util.n.a(landUiSettings.getSloganOffsetX(), az)).B(cn.fly.verify.util.n.a(landUiSettings.getSloganOffsetY(), aA)).C(cn.fly.verify.util.n.a(landUiSettings.getSloganOffsetBottomY(), -1));
                }
            }
        }
        return agVarE;
    }

    public GenAuthThemeConfig a(UiSettings uiSettings) {
        GenAuthThemeConfig.Builder builderC = c();
        if (uiSettings != null) {
            builderC.setNavTextColor(cn.fly.verify.util.n.b(uiSettings.getNavTextColorId(), b)).setNumberColor(cn.fly.verify.util.n.b(uiSettings.getNumberColorId(), b)).setNumberSize(cn.fly.verify.util.n.a(uiSettings.getNumberSizeId(), e), false).setLogBtnImgPath(cn.fly.verify.util.n.d(uiSettings.getLoginBtnImgId(), j)).setLogBtnText(cn.fly.verify.util.n.c(uiSettings.getLoginBtnTextId(), k)).setLogBtnTextColor(cn.fly.verify.util.n.b(uiSettings.getLoginBtnTextColorId(), l)).setPrivacyState(uiSettings.getCheckboxDefaultState()).setClauseColor(cn.fly.verify.util.n.c(h), cn.fly.verify.util.n.b(uiSettings.getAgreementColorId(), i)).setUncheckedImgPath(cn.fly.verify.util.n.d(uiSettings.getCheckboxImgId(), m)).setCheckedImgPath(cn.fly.verify.util.n.d(uiSettings.getCheckboxImgId(), m));
            if (uiSettings.getCusAgreementNameId1() > 0) {
                TextUtils.isEmpty(uiSettings.getCusAgreementUrl1());
            }
            if (uiSettings.getCusAgreementNameId2() > 0) {
                TextUtils.isEmpty(uiSettings.getCusAgreementUrl2());
            }
        }
        return builderC.build();
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00e3, code lost:
    
        if (r1.isFadeAnim() != false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x015d, code lost:
    
        if (r2.isFadeAnim() != false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x015f, code lost:
    
        r1 = cn.fly.tools.utils.ResHelper.getAnimRes(cn.fly.FlySDK.getContext(), "fly_verify_fade_in");
        r2 = cn.fly.tools.utils.ResHelper.getAnimRes(cn.fly.FlySDK.getContext(), "fly_verify_fade_out");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AuthPageConfig b() {
        int finishInAnim;
        int finishOutAnim;
        AuthPageConfig.Builder authActivityViewIds = new AuthPageConfig.Builder().setAuthActivityLayoutId(ResHelper.getLayoutRes(FlySDK.getContext(), "fly_verify_page_one_key_login_ctcc")).setAuthActivityViewIds(ResHelper.getIdRes(FlySDK.getContext(), "ct_account_nav_goback"), ResHelper.getIdRes(FlySDK.getContext(), "ct_account_desensphone"), ResHelper.getIdRes(FlySDK.getContext(), "ct_account_brand_view"), ResHelper.getIdRes(FlySDK.getContext(), "ct_account_login_btn"), ResHelper.getIdRes(FlySDK.getContext(), "ct_account_login_loading"), ResHelper.getIdRes(FlySDK.getContext(), "ct_account_login_text"), ResHelper.getIdRes(FlySDK.getContext(), "ct_account_other_login_way"), ResHelper.getIdRes(FlySDK.getContext(), "ct_auth_privacy_checkbox"), ResHelper.getIdRes(FlySDK.getContext(), "ct_auth_privacy_text"));
        UiSettings uiSettingsC = i.a().c();
        LandUiSettings landUiSettingsD = i.a().d();
        if (uiSettingsC != null || landUiSettingsD != null) {
            if (uiSettingsC != null) {
                if (uiSettingsC.hasFinishActivityAnim()) {
                    authActivityViewIds.setFinishActivityTransition(uiSettingsC.getFinishInAnim(), uiSettingsC.getFinishOutAnim());
                } else {
                    if (uiSettingsC.isTranslateAnim()) {
                        finishInAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_in");
                        finishOutAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_out");
                    } else if (uiSettingsC.isRightTranslateAnim()) {
                        finishInAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_right_in");
                        finishOutAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_left_out");
                    } else {
                        if (!uiSettingsC.isBottomTranslateAnim()) {
                            if (!uiSettingsC.isZoomAnim()) {
                            }
                            finishInAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_zoom_in");
                            finishOutAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_zoom_out");
                        }
                        finishInAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_bottom_in");
                        finishOutAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_bottom_out");
                    }
                    authActivityViewIds.setFinishActivityTransition(finishInAnim, finishOutAnim);
                }
            } else if (landUiSettingsD.hasFinishActivityAnim()) {
                finishInAnim = landUiSettingsD.getFinishInAnim();
                finishOutAnim = landUiSettingsD.getFinishOutAnim();
                authActivityViewIds.setFinishActivityTransition(finishInAnim, finishOutAnim);
            } else {
                if (!landUiSettingsD.isTranslateAnim()) {
                    if (landUiSettingsD.isRightTranslateAnim()) {
                        authActivityViewIds.setFinishActivityTransition(ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_right_in"), ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_left_out"));
                        return null;
                    }
                    if (!landUiSettingsD.isBottomTranslateAnim()) {
                        if (!landUiSettingsD.isZoomAnim()) {
                        }
                        finishInAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_zoom_in");
                        finishOutAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_zoom_out");
                        authActivityViewIds.setFinishActivityTransition(finishInAnim, finishOutAnim);
                    }
                    finishInAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_bottom_in");
                    finishOutAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_bottom_out");
                    authActivityViewIds.setFinishActivityTransition(finishInAnim, finishOutAnim);
                }
                finishInAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_in");
                finishOutAnim = ResHelper.getAnimRes(FlySDK.getContext(), "fly_verify_translate_out");
                authActivityViewIds.setFinishActivityTransition(finishInAnim, finishOutAnim);
            }
        }
        return authActivityViewIds.build();
    }

    public ag b(UiSettings uiSettings) {
        ag agVarD = d();
        if (uiSettings != null) {
            agVarD.a(uiSettings.getNeedFitsSystemWindow()).a(cn.fly.verify.util.n.b(uiSettings.getNavColorId(), a)).a(cn.fly.verify.util.n.a(uiSettings.getNavTextId(), uiSettings.getNavText(), -1)).b(cn.fly.verify.util.n.b(uiSettings.getNavTextColorId(), b)).a(cn.fly.verify.util.n.a(uiSettings.getNavCloseImgId(), uiSettings.getNavCloseImg(), c)).d(uiSettings.isNavTransparent()).c(uiSettings.isNavHidden()).e(uiSettings.isNavCloseImgHidden()).a(uiSettings.getNavCloseImgScaleType()).N(cn.fly.verify.util.n.a(uiSettings.getNavTextSize(), J)).O(cn.fly.verify.util.n.a(uiSettings.getNavCloseImgWidth(), O)).P(cn.fly.verify.util.n.a(uiSettings.getNavCloseImgHeight(), P)).Q(cn.fly.verify.util.n.a(uiSettings.getNavCloseImgOffsetX(), -1)).R(cn.fly.verify.util.n.a(uiSettings.getNavCloseImgOffsetRightX(), -1)).S(cn.fly.verify.util.n.a(uiSettings.getNavCloseImgOffsetY(), -1)).e(cn.fly.verify.util.n.a(uiSettings.getBackgroundImgId(), uiSettings.getBackgroundImg(), r)).f(uiSettings.isBackgroundClickClose()).b(cn.fly.verify.util.n.a(uiSettings.getLogoImgId(), uiSettings.getLogoImg(), cn.fly.verify.util.n.i(d))).g(uiSettings.isLogoHidden()).i(cn.fly.verify.util.n.a(uiSettings.getLogoWidth(), s)).j(cn.fly.verify.util.n.a(uiSettings.getLogoHeight(), t)).k(cn.fly.verify.util.n.a(uiSettings.getLogoOffsetX(), -1)).l(cn.fly.verify.util.n.a(uiSettings.getLogoOffsetY(), u)).X(cn.fly.verify.util.n.a(uiSettings.getLogoOffsetBottomY(), -1)).ab(cn.fly.verify.util.n.a(uiSettings.getLogoOffsetRightX(), -1)).z(uiSettings.isLogoAlignParentRight()).c(cn.fly.verify.util.n.b(uiSettings.getNumberColorId(), b)).d(cn.fly.verify.util.n.a(uiSettings.getNumberSizeId(), e)).m(cn.fly.verify.util.n.a(uiSettings.getNumberOffsetX(), -1)).n(cn.fly.verify.util.n.a(uiSettings.getNumberOffsetY(), v)).t(uiSettings.isNumberHidden()).Y(cn.fly.verify.util.n.a(uiSettings.getNumberOffsetBottomY(), -1)).ac(cn.fly.verify.util.n.a(uiSettings.getNumberOffsetRightX(), -1)).A(uiSettings.isNumberAlignParentRight()).e(cn.fly.verify.util.n.b(uiSettings.getSwitchAccColorId(), g)).f(cn.fly.verify.util.n.a(uiSettings.getSwitchAccTextSize(), J)).a(uiSettings.isSwitchAccHidden()).F(cn.fly.verify.util.n.a(uiSettings.getSwitchAccOffsetX(), -1)).G(cn.fly.verify.util.n.a(uiSettings.getSwitchAccOffsetY(), w)).b(cn.fly.verify.util.n.a(uiSettings.getSwitchAccText(), uiSettings.getSwitchAccTextString(), x)).Z(cn.fly.verify.util.n.a(uiSettings.getSwitchAccOffsetBottomY(), -1)).ad(cn.fly.verify.util.n.a(uiSettings.getSwitchAccOffsetRightX(), -1)).B(uiSettings.isSwitchAccAlignParentRight()).d(cn.fly.verify.util.n.a(uiSettings.getLoginBtnImgId(), uiSettings.getLoginBtnImg(), j)).i(cn.fly.verify.util.n.a(uiSettings.getLoginBtnTextId(), uiSettings.getLoginBtnText(), k)).h(cn.fly.verify.util.n.b(uiSettings.getLoginBtnTextColorId(), l)).v(cn.fly.verify.util.n.a(uiSettings.getLoginBtnTextSize(), J)).w(cn.fly.verify.util.n.a(uiSettings.getLoginBtnWidth(), -1)).x(cn.fly.verify.util.n.a(uiSettings.getLoginBtnHeight(), L)).y(cn.fly.verify.util.n.a(uiSettings.getLoginBtnOffsetX(), -1)).z(cn.fly.verify.util.n.a(uiSettings.getLoginBtnOffsetY(), M)).v(uiSettings.isLoginBtnHidden()).aa(cn.fly.verify.util.n.a(uiSettings.getLoginBtnOffsetBottomY(), -1)).ae(cn.fly.verify.util.n.a(uiSettings.getLoginBtnOffsetRightX(), -1)).D(uiSettings.isLoginBtnAlignParentRight()).b(uiSettings.getCheckboxDefaultState()).g(cn.fly.verify.util.n.b(uiSettings.getAgreementColorId(), i)).c(cn.fly.verify.util.n.a(uiSettings.getCheckboxImgId(), uiSettings.getCheckboxImg(), m)).h(uiSettings.isCheckboxHidden()).ah(cn.fly.verify.util.n.a(uiSettings.getCheckboxOffsetX(), -1)).ai(cn.fly.verify.util.n.a(uiSettings.getCheckboxOffsetRightX(), -1)).aj(cn.fly.verify.util.n.a(uiSettings.getCheckboxOffsetY(), -1)).ak(cn.fly.verify.util.n.a(uiSettings.getCheckboxOffsetBottomY(), -1)).a(uiSettings.getCheckboxScaleX()).b(uiSettings.getCheckboxScaleY()).H(cn.fly.verify.util.n.b(uiSettings.getAgreementBaseTextColorId(), b)).j(uiSettings.isAgreementGravityLeft()).o(cn.fly.verify.util.n.a(uiSettings.getAgreementOffsetX(), -1)).p(cn.fly.verify.util.n.a(uiSettings.getAgreementOffsetRightX(), -1)).q(cn.fly.verify.util.n.a(uiSettings.getAgreementOffsetY(), z)).r(cn.fly.verify.util.n.a(uiSettings.getAgreementOffsetBottomY(), -1)).I(cn.fly.verify.util.n.a(uiSettings.getAgreementTextSize(), K)).u(uiSettings.isAgreementHidden()).j(cn.fly.verify.util.n.a(uiSettings.getAgreementCmccText(), uiSettings.getAgreementCmccTextString(), A)).k(cn.fly.verify.util.n.a(uiSettings.getAgreementCMHKText(), uiSettings.getAgreementCMHKTextString(), B)).l(cn.fly.verify.util.n.a(uiSettings.getAgreementCuccText(), uiSettings.getAgreementCuccTextString(), D)).m(cn.fly.verify.util.n.a(uiSettings.getAgreementCtccText(), uiSettings.getAgreementCtccTextString(), C)).n(cn.fly.verify.util.n.a(uiSettings.getAgreementTextStart(), uiSettings.getAgreementTextStartString(), E)).o(cn.fly.verify.util.n.a(uiSettings.getAgreementTextAnd1(), uiSettings.getAgreementTextAndString1(), F)).p(cn.fly.verify.util.n.a(uiSettings.getAgreementTextAnd2(), uiSettings.getAgreementTextAndString2(), G)).q(cn.fly.verify.util.n.a(uiSettings.getAgreementTextAnd3(), uiSettings.getAgreementTextAndString3(), H)).r(cn.fly.verify.util.n.a(uiSettings.getAgreementTextEnd(), uiSettings.getAgreementTextEndString(), I)).C(uiSettings.isAgreementAlignParentRight()).i(uiSettings.isSloganHidden()).E(cn.fly.verify.util.n.b(uiSettings.getSloganTextColor(), N)).D(cn.fly.verify.util.n.a(uiSettings.getSloganTextSize(), K)).A(cn.fly.verify.util.n.a(uiSettings.getSloganOffsetX(), -1)).B(cn.fly.verify.util.n.a(uiSettings.getSloganOffsetY(), -1)).af(cn.fly.verify.util.n.a(uiSettings.getSloganOffsetRightX(), -1)).E(uiSettings.isSloganAlignParentRight()).r(uiSettings.isImmersiveTheme()).s(uiSettings.isImmersiveStatusTextColorBlack()).w(uiSettings.isDialogTheme()).x(uiSettings.isDialogAlignBottom()).k(uiSettings.isTranslateAnim()).m(uiSettings.isRightTranslateAnim()).l(uiSettings.isBottomTranslateAnim()).n(uiSettings.isZoomAnim()).o(uiSettings.isFadeAnim()).ag(uiSettings.getAgreementUncheckHintType()).s(cn.fly.verify.util.n.a(uiSettings.getAgreementUncheckHintTextId(), uiSettings.getAgreementUncheckHintText(), y)).F(uiSettings.isNavTextBold()).H(uiSettings.isNumberBold()).I(uiSettings.isSwitchAccTextBold()).K(uiSettings.isLoginBtnTextBold()).J(uiSettings.isAgreementTextBold()).L(uiSettings.isSloganTextBold()).M(uiSettings.isAgreementTextWithUnderLine()).G(uiSettings.isFullScreen()).N(uiSettings.isVirtualButtonTransparent()).t(cn.fly.verify.util.n.a(uiSettings.getAgreementPageTitle(), uiSettings.getAgreementPageTitleString(), -1)).u(cn.fly.verify.util.n.a(uiSettings.getCusAgreementPageOneTitle(), uiSettings.getCusAgreementPageOneTitleString(), -1)).v(cn.fly.verify.util.n.a(uiSettings.getCusAgreementPageTwoTitle(), uiSettings.getCusAgreementPageTwoTitleString(), -1)).w(cn.fly.verify.util.n.a(uiSettings.getCusAgreementPageThreeTitle(), uiSettings.getCusAgreementPageThreeTitleString(), -1)).g(cn.fly.verify.util.n.a(uiSettings.getAgreementPageCloseImgId(), uiSettings.getAgreementPageCloseImg(), c)).al(cn.fly.verify.util.n.a(uiSettings.getAgreementPageCloseImgWidth(), O)).am(cn.fly.verify.util.n.a(uiSettings.getAgreementPageCloseImgHeight(), P)).O(uiSettings.isAgreementPageCloseImgHidden()).b(uiSettings.getAgreementPageCloseImgScaleType()).Q(uiSettings.isAgreementPageTitleHidden()).ao(cn.fly.verify.util.n.b(uiSettings.getAgreementPageTitleTextColor(), b)).an(cn.fly.verify.util.n.a(uiSettings.getAgreementPageTitleTextSize(), J)).P(uiSettings.isAgreementPageTitleTextBold());
            agVarD.C(cn.fly.verify.util.n.a(uiSettings.getSloganOffsetY(), -1) <= 0 ? cn.fly.verify.util.n.a(uiSettings.getSloganOffsetBottomY(), 30) : cn.fly.verify.util.n.a(uiSettings.getSloganOffsetBottomY(), -1));
            if ((uiSettings.getCusAgreementNameId1() > 0 || !TextUtils.isEmpty(uiSettings.getCusAgreementName1())) && !TextUtils.isEmpty(uiSettings.getCusAgreementUrl1())) {
                agVarD.c(cn.fly.verify.util.n.a(uiSettings.getCusAgreementNameId1(), uiSettings.getCusAgreementName1(), o)).d(uiSettings.getCusAgreementUrl1());
                agVarD.s(cn.fly.verify.util.n.b(uiSettings.isCusAgreementColor1Seted() ? uiSettings.getCusAgreementColor1() : uiSettings.getAgreementColorId(), i));
            }
            if ((uiSettings.getCusAgreementNameId2() > 0 || !TextUtils.isEmpty(uiSettings.getCusAgreementName2())) && !TextUtils.isEmpty(uiSettings.getCusAgreementUrl2())) {
                agVarD.e(cn.fly.verify.util.n.a(uiSettings.getCusAgreementNameId2(), uiSettings.getCusAgreementName2(), p)).f(uiSettings.getCusAgreementUrl2());
                agVarD.t(cn.fly.verify.util.n.b(uiSettings.isCusAgreementColor2Seted() ? uiSettings.getCusAgreementColor2() : uiSettings.getAgreementColorId(), i));
            }
            if ((uiSettings.getCusAgreementNameId3() > 0 || !TextUtils.isEmpty(uiSettings.getCusAgreementName3())) && !TextUtils.isEmpty(uiSettings.getCusAgreementUrl3())) {
                agVarD.g(cn.fly.verify.util.n.a(uiSettings.getCusAgreementNameId3(), uiSettings.getCusAgreementName3(), q)).h(uiSettings.getCusAgreementUrl3());
                agVarD.u(cn.fly.verify.util.n.b(uiSettings.isCusAgreementColor3Seted() ? uiSettings.getCusAgreementColor3() : uiSettings.getAgreementColorId(), i));
            }
            if (uiSettings.getAgreementText() != null) {
                agVarD.a(uiSettings.getAgreementText());
            }
            if (uiSettings.getAgreementUncheckToast() != null) {
                agVarD.a(uiSettings.getAgreementUncheckToast());
            }
            if (uiSettings.hasStartActivityAnim()) {
                agVarD.p(true).J(cn.fly.verify.util.n.e(uiSettings.getStartInAnim(), cn.fly.verify.util.n.getAnimRes(FlySDK.getContext(), "fly_verify_translate_in"))).K(cn.fly.verify.util.n.e(uiSettings.getStartOutAnim(), cn.fly.verify.util.n.getAnimRes(FlySDK.getContext(), "fly_verify_translate_out")));
            }
            if (uiSettings.hasFinishActivityAnim()) {
                agVarD.q(true).L(cn.fly.verify.util.n.e(uiSettings.getFinishInAnim(), cn.fly.verify.util.n.getAnimRes(FlySDK.getContext(), "fly_verify_translate_in"))).M(cn.fly.verify.util.n.e(uiSettings.getFinishOutAnim(), cn.fly.verify.util.n.getAnimRes(FlySDK.getContext(), "fly_verify_translate_out")));
            }
            if (uiSettings.isDialogTheme()) {
                agVarD.V(cn.fly.verify.util.n.a(uiSettings.getDialogWidth(), aB)).W(cn.fly.verify.util.n.a(uiSettings.getDialogHeight(), aC)).T(cn.fly.verify.util.n.a(uiSettings.getDialogOffsetX(), aD)).U(cn.fly.verify.util.n.a(uiSettings.getDialogOffsetY(), aE)).f(cn.fly.verify.util.n.a(uiSettings.getDialogMaskBackground(), uiSettings.getDialogBackgroundDrawable(), aG)).y(uiSettings.isDialogBackgroundClickClose()).l(cn.fly.verify.util.n.a(uiSettings.getLogoOffsetY(), X)).n(cn.fly.verify.util.n.a(uiSettings.getNumberOffsetY(), Y)).G(cn.fly.verify.util.n.a(uiSettings.getSwitchAccOffsetY(), Z)).z(cn.fly.verify.util.n.a(uiSettings.getLoginBtnOffsetY(), ab)).q(cn.fly.verify.util.n.a(uiSettings.getAgreementOffsetY(), aa));
                if (uiSettings.isDialogAlignBottom()) {
                    agVarD.k(cn.fly.verify.util.n.a(uiSettings.getLogoOffsetX(), ac)).l(cn.fly.verify.util.n.a(uiSettings.getLogoOffsetY(), af)).m(cn.fly.verify.util.n.a(uiSettings.getNumberOffsetX(), ad)).n(cn.fly.verify.util.n.a(uiSettings.getNumberOffsetY(), ag)).F(cn.fly.verify.util.n.a(uiSettings.getSwitchAccOffsetX(), ae)).G(cn.fly.verify.util.n.a(uiSettings.getSwitchAccOffsetY(), ah)).z(cn.fly.verify.util.n.a(uiSettings.getLoginBtnOffsetY(), ai)).q(cn.fly.verify.util.n.a(uiSettings.getAgreementOffsetY(), aj));
                }
            }
        }
        return agVarD;
    }
}