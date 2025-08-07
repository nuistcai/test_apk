package com.mobile.lantian.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.ViewCompat;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.datatype.UiSettings;
import com.alibaba.fastjson.asm.Opcodes;
import com.mobile.lantian.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CustomizeUtils {
    private static Context context;
    private static String url;

    public static UiSettings customizeUi(Context context2) {
        context = context2;
        return new UiSettings.Builder().setAgreementUncheckHintType(0).setNavColorId(R.color.fly_verify_demo_text_color_blue).setNavTransparent(true).setNavHidden(false).setNavCloseImgId(R.drawable.fly_verify_demo_back).setNavCloseImgHidden(false).setLogoImgId(R.drawable.fly_verify_demo_logo).setLogoHidden(false).setLogoWidth(R.dimen.fly_verify_demo_common_80).setLogoHeight(R.dimen.fly_verify_demo_common_80).setNumberColorId(R.color.fly_verify_demo_text_color_common_black).setNumberSizeId(R.dimen.fly_verify_demo_text_size_m).setSwitchAccColorId(R.color.fly_verify_demo_text_color_blue).setSwitchAccTextSize(R.dimen.fly_verify_demo_text_size_s).setSwitchAccHidden(true).setSwitchAccOffsetBottomY(50).setLoginBtnImgId(R.drawable.fly_verify_demo_shape_rectangle).setLoginBtnTextId(R.string.fly_verify_demo_one_key_login).setLoginBtnTextColorId(R.color.fly_verify_demo_text_color_common_white).setLoginBtnTextSize(R.dimen.fly_verify_demo_text_size_s).setLoginBtnWidth(250).setLoginBtnHeight(45).setCheckboxHidden(false).setCheckboxImgId(R.drawable.fly_verify_demo_customized_checkbox_selector).setCheckboxDefaultState(true).setAgreementHidden(false).setCusAgreementNameId1(R.string.fly_verify_demo_service).setCusAgreementUrl1("http://www.xxx.com/policy/zh").setAgreementGravityLeft(true).setAgreementOffsetX(R.dimen.fly_verify_demo_common_50).setAgreementOffsetRightX(R.dimen.fly_verify_demo_common_50).setSloganTextSize(R.dimen.fly_verify_demo_text_size_xs).setSloganOffsetY(Opcodes.GETFIELD).setSloganTextColor(R.color.fly_verify_demo_text_color_common_gray).setImmersiveTheme(true).setImmersiveStatusTextColorBlack(true).setStartActivityTransitionAnim(R.anim.translate_in, R.anim.translate_out).setFinishActivityTransitionAnim(R.anim.translate_in, R.anim.translate_out).setAgreementUncheckHintText("请阅读并勾选隐私协议").setAgreementTextStart("同意").setAgreementTextEnd("并授权秒验Demo获取本机号码").setAgreementCmccText("《移动统一认证服务条款》").setAgreementCuccText("《联通统一认证服务条款》").setAgreementCtccText("《电信统一认证服务条款》").setAgreementColorId(R.color.fly_verify_demo_main_color).setAgreementOffsetBottomY(100).setAgreementOffsetRightX(80).build();
    }

    public static LandUiSettings customizeLandUi() {
        return new LandUiSettings.Builder().setLogoImgId(R.drawable.fly_verify_demo_logo_small).setLogoWidth(45).setLogoHeight(45).setLogoOffsetY(10).setNumberOffsetY(60).setNumberSizeId(16).setSloganOffsetY(90).setLoginBtnOffsetY(120).setSwitchAccHidden(true).setNavCloseImgId(R.drawable.fly_verify_demo_back).setLoginBtnImgId(R.drawable.fly_verify_demo_shape_rectangle).setLoginBtnTextId("一键登录").setAgreementOffsetBottomY(20).setImmersiveTheme(true).setImmersiveStatusTextColorBlack(true).setCheckboxHidden(false).setCheckboxDefaultState(true).setCheckboxImgId(R.drawable.fly_verify_demo_customized_checkbox_selector).setAgreementColorId(R.color.fly_verify_demo_main_color).setCusAgreementNameId1(R.string.fly_verify_demo_service).setCusAgreementUrl1("http://www.xxx.com/policy/zh").setAgreementTextStart("同意").setAgreementTextEnd("并授权秒验Demo获取本机号码").setAgreementCmccText("《移动统一认证服务条款》").setAgreementCuccText("《联通统一认证服务条款》").setAgreementCtccText("《电信统一认证服务条款》").setStartActivityTransitionAnim(R.anim.translate_in, R.anim.translate_out).setFinishActivityTransitionAnim(R.anim.translate_in, R.anim.translate_out).build();
    }

    public static UiSettings customizeUi0() {
        return new UiSettings.Builder().setAgreementUncheckHintType(0).setNavColorId(R.color.fly_verify_demo_text_color_blue).setNavTransparent(true).setNavHidden(false).setNavCloseImgId(R.drawable.fly_verify_demo_close).setNavCloseImgHidden(false).setLogoImgId(R.drawable.ic_launcher).setLogoHidden(false).setLogoWidth(R.dimen.fly_verify_demo_common_80).setLogoHeight(R.dimen.fly_verify_demo_common_80).setNumberColorId(R.color.fly_verify_demo_text_color_common_black).setNumberSizeId(R.dimen.fly_verify_demo_text_size_m).setSwitchAccColorId(R.color.fly_verify_demo_text_color_blue).setSwitchAccTextSize(R.dimen.fly_verify_demo_text_size_s).setSwitchAccHidden(false).setLoginBtnImgId(R.drawable.fly_verify_demo_shape_rectangle).setLoginBtnTextId(R.string.fly_verify_demo_login).setLoginBtnTextColorId(R.color.fly_verify_demo_text_color_common_white).setLoginBtnTextSize(R.dimen.fly_verify_demo_text_size_s).setCheckboxHidden(false).setCheckboxImgId(R.drawable.fly_verify_demo_customized_checkbox_selector).setAgreementGravityLeft(true).setAgreementOffsetX(R.dimen.fly_verify_demo_common_50).setAgreementOffsetRightX(R.dimen.fly_verify_demo_common_50).setSloganTextSize(R.dimen.fly_verify_demo_text_size_xs).setSloganTextColor(R.color.fly_verify_demo_text_color_common_gray).setImmersiveTheme(false).setImmersiveStatusTextColorBlack(false).setStartActivityTransitionAnim(R.anim.translate_in, R.anim.translate_out).setFinishActivityTransitionAnim(R.anim.translate_in, R.anim.translate_out).setAgreementUncheckHintText("请阅读并勾选隐私协议").setAgreementUncheckHintType(0).setFullScreen(false).setNavTextBold(true).setNumberBold(true).setSwitchAccTextBold(true).setAgreementTextBold(true).setLoginBtnTextBold(true).setSloganTextBold(true).setAgreementTextWithUnderLine(true).setAgreementUncheckToast(Toast.makeText(context, "请先阅读隐私协议", 0)).setNavCloseImgScaleType(ImageView.ScaleType.CENTER_INSIDE).setAgreementPageTitle("title0").setCusAgreementPageOneTitle("title1").setCusAgreementPageTwoTitle("title2").setCusAgreementPageThreeTitle("title3").setAgreementPageCloseImg(context.getResources().getDrawable(R.drawable.fly_verify_demo_close)).setAgreementPageCloseImgScaleType(ImageView.ScaleType.FIT_CENTER).setAgreementPageCloseImgHidden(false).setAgreementPageCloseImgWidth(30).setAgreementPageCloseImgHeight(30).setAgreementPageTitleTextSize(16).setAgreementPageTitleTextColor(-11626753).setAgreementPageTitleTextBold(true).setAgreementPageTitleHidden(false).setCheckboxScaleX(1.5f).setCheckboxScaleY(1.5f).setCheckboxOffsetX(10).setCheckboxOffsetY(5).build();
    }

    public static UiSettings customizedUiFlutter() {
        return new UiSettings.Builder().setLoginBtnTextId(R.string.fly_verify_demo_one_key_login).setLoginBtnTextColorId(R.color.fly_verify_demo_text_color_common_white).setLoginBtnTextSize(24).setLoginBtnHeight(50).setLoginBtnWidth(100).setLoginBtnAlignParentRight(true).setLoginBtnTextBold(true).setNavCloseImgWidth(60).setNavCloseImgHeight(100).setNavCloseImgOffsetX(30).setNavCloseImgOffsetRightX(40).setNavCloseImgHidden(true).setNavTextId("返回2").setNavCloseImgScaleType(ImageView.ScaleType.CENTER).setBackgroundImgId(R.drawable.fly_verify_background_demo_dialog).setBackgroundClickClose(true).setFullScreen(false).setImmersiveTheme(false).setImmersiveStatusTextColorBlack(false).build();
    }

    public static UiSettings customizeUi1() {
        return new UiSettings.Builder().setAgreementUncheckHintType(1).setNavColorId(R.color.fly_verify_demo_text_color_blue).setNavTransparent(true).setNavHidden(false).setBackgroundClickClose(false).setNavTextId(R.string.fly_verify_demo_verify).setNavTextSize(R.dimen.fly_verify_demo_text_size_s).setNavTextColorId(R.color.fly_verify_demo_text_color_common_black).setNavCloseImgId(R.drawable.fly_verify_demo_close).setNavCloseImgHidden(false).setLogoImgId(R.drawable.ic_launcher).setLogoHidden(false).setLogoWidth(R.dimen.fly_verify_demo_common_100).setLogoHeight(R.dimen.fly_verify_demo_common_100).setLogoOffsetX(R.dimen.fly_verify_demo_common_30).setLogoOffsetY(R.dimen.fly_verify_demo_common_30).setLogoOffsetRightX(R.dimen.fly_verify_demo_logo_offset_right_x_customize).setLogoAlignParentRight(false).setNumberColorId(R.color.fly_verify_demo_text_color_common_black).setNumberSizeId(R.dimen.fly_verify_demo_text_size_m).setNumberOffsetX(R.dimen.fly_verify_demo_common_180).setNumberOffsetY(R.dimen.fly_verify_demo_common_50).setNumberOffsetRightX(R.dimen.fly_verify_demo_common_30).setNumberAlignParentRight(true).setSwitchAccColorId(R.color.fly_verify_demo_text_color_blue).setSwitchAccTextSize(R.dimen.fly_verify_demo_text_size_s).setSwitchAccHidden(false).setSwitchAccOffsetX(R.dimen.fly_verify_demo_common_200).setSwitchAccOffsetY(R.dimen.fly_verify_demo_common_100).setSwitchAccText(R.string.fly_verify_demo_other_login).setSwitchAccOffsetRightX(R.dimen.fly_verify_demo_common_50).setSwitchAccAlignParentRight(true).setLoginBtnImgId(R.drawable.fly_verify_demo_shape_rectangle).setLoginBtnTextId(R.string.fly_verify_demo_login).setLoginBtnTextColorId(R.color.fly_verify_demo_text_color_common_white).setLoginBtnTextSize(R.dimen.fly_verify_demo_text_size_s).setLoginBtnWidth(R.dimen.fly_verify_demo_common_200).setLoginBtnHeight(R.dimen.fly_verify_demo_login_btn_height_customize).setLoginBtnOffsetX(R.dimen.fly_verify_demo_common_80).setLoginBtnOffsetY(R.dimen.fly_verify_demo_login_btn_offset_y_customize).setLoginBtnOffsetRightX(R.dimen.fly_verify_demo_common_80).setLoginBtnAlignParentRight(true).setCheckboxHidden(false).setCheckboxImgId(R.drawable.fly_verify_demo_customized_checkbox_selector).setAgreementColorId(R.color.fly_verify_demo_main_color).setCusAgreementNameId1(R.string.fly_verify_demo_customize_agreement_name_1).setCusAgreementUrl1("http://www.baidu.com").setCusAgreementColor1(R.color.fly_verify_demo_main_color).setCusAgreementNameId2(R.string.fly_verify_demo_customize_agreement_name_2).setCusAgreementUrl2("https://www.jianshu.com").setCusAgreementColor2(R.color.fly_verify_demo_main_color).setAgreementGravityLeft(true).setAgreementBaseTextColorId(R.color.fly_verify_demo_text_color_common_black).setAgreementOffsetX(R.dimen.fly_verify_demo_common_50).setAgreementOffsetRightX(R.dimen.fly_verify_demo_common_50).setAgreementOffsetY(R.dimen.fly_verify_demo_agreement_offset_y_customize).setAgreementAlignParentRight(true).setAgreementTextSize(R.dimen.fly_verify_demo_text_size_s).setSloganTextSize(R.dimen.fly_verify_demo_text_size_s).setSloganTextColor(R.color.fly_verify_demo_main_color).setSloganOffsetY(R.dimen.fly_verify_demo_slogan_offset_y_customize).setImmersiveTheme(true).setImmersiveStatusTextColorBlack(true).setStartActivityTransitionAnim(R.anim.zoom_in, R.anim.zoom_out).setFinishActivityTransitionAnim(R.anim.zoom_in, R.anim.zoom_out).setBackgroundClickClose(true).build();
    }

    public static UiSettings customizeUi2() {
        return new UiSettings.Builder().setCheckboxDefaultState(true).setLogoOffsetBottomY(R.dimen.fly_verify_demo_logo_offset_bottom_y_customize).setNumberOffsetBottomY(R.dimen.fly_verify_demo_common_300).setSwitchAccOffsetBottomY(R.dimen.fly_verify_demo_switch_acc_offset_bottom_y_customize).setLoginBtnOffsetBottomY(R.dimen.fly_verify_demo_common_200).setNumberOffsetRightX(R.dimen.fly_verify_demo_common_30).setSwitchAccOffsetRightX(R.dimen.fly_verify_demo_common_50).setLoginBtnOffsetRightX(R.dimen.fly_verify_demo_common_80).setLoginBtnOffsetX(R.dimen.fly_verify_demo_common_80).setSloganOffsetRightX(R.dimen.fly_verify_demo_common_30).setNumberAlignParentRight(true).setSwitchAccAlignParentRight(true).setAgreementAlignParentRight(false).setLoginBtnAlignParentRight(true).setSloganAlignParentRight(true).setBackgroundClickClose(false).setImmersiveTheme(false).setImmersiveStatusTextColorBlack(false).setDialogMaskBackgroundClickClose(true).setStartActivityTransitionAnim(R.anim.translate_bottom_in, R.anim.translate_bottom_out).setFinishActivityTransitionAnim(R.anim.translate_bottom_in, R.anim.translate_bottom_out).setDialogTheme(true).setDialogAlignBottom(true).setBottomTranslateAnim(true).build();
    }

    public static UiSettings customizeUi3() {
        return new UiSettings.Builder().setNavHidden(true).setLogoHidden(true).setNumberHidden(true).setSwitchAccHidden(true).setLoginBtnHidden(true).setAgreementHidden(true).setSloganHidden(true).build();
    }

    public static UiSettings customizeUi4() {
        return new UiSettings.Builder().setNavCloseImgOffsetRightX(R.dimen.fly_verify_demo_common_5).setDialogMaskBackgroundClickClose(true).setLogoImgId(R.drawable.fly_verify_demo_logo_small).setNavColorId(R.color.fly_verify_demo_main_color).setLogoHeight(30).setLogoWidth(30).setLogoOffsetX(110).setLogoOffsetY(1).setNumberOffsetY(45).setNumberOffsetX(R.dimen.fly_verify_demo_number_offsetx).setNumberOffsetRightX(R.dimen.fly_verify_demo_number_offsetx).setSloganOffsetY(80).setLoginBtnOffsetY(120).setLoginBtnWidth(250).setLoginBtnImgId(R.drawable.fly_verify_demo_shape_rectangle).setLoginBtnTextId("一键登录").setLogoHidden(true).setSwitchAccHidden(false).setSwitchAccOffsetX(R.dimen.fly_verify_demo_swicth_offsetx).setSwitchAccOffsetRightX(R.dimen.fly_verify_demo_swicth_offsetx).setSwitchAccOffsetY(100).setCheckboxHidden(false).setCheckboxDefaultState(true).setCheckboxImgId(R.drawable.fly_verify_demo_customized_checkbox_selector).setAgreementOffsetY(Opcodes.GETFIELD).setAgreementOffsetX(60).setAgreementOffsetRightX(60).setAgreementColorId(R.color.fly_verify_demo_main_color).setAgreementTextStart("同意").setAgreementTextEnd("并授权秒验Demo获取本机号码").setAgreementCmccText("《移动统一认证服务条款》").setAgreementCuccText("《联通统一认证服务条款》").setAgreementCtccText("《电信统一认证服务条款》").setCusAgreementNameId1(R.string.fly_verify_demo_service).setCusAgreementUrl1("http://www.xxx.com/policy/zh").setBottomTranslateAnim(true).setBackgroundImgId(R.drawable.fly_verify_background_demo_dialog).setDialogTheme(true).setDialogAlignBottom(true).setDialogWidth(350).setDialogHeight(300).setDialogOffsetX(60).setDialogOffsetY(R.dimen.fly_verify_demo_common_30).build();
    }

    public static UiSettings customizeUi5() {
        return new UiSettings.Builder().setDialogTheme(true).setDialogAlignBottom(true).build();
    }

    public static LandUiSettings customizeLandUi4() {
        return new LandUiSettings.Builder().setNavCloseImgOffsetRightX(R.dimen.fly_verify_demo_common_5).setDialogMaskBackgroundClickClose(true).setLogoImgId(R.drawable.fly_verify_demo_logo_small).setNavColorId(R.color.fly_verify_demo_main_color).setLogoHeight(30).setLogoWidth(30).setLogoOffsetY(1).setLogoOffsetX(110).setNumberOffsetY(45).setNumberOffsetX(95).setSloganOffsetY(75).setLoginBtnOffsetY(100).setLoginBtnWidth(Opcodes.GOTO_W).setLoginBtnImgId(R.drawable.fly_verify_demo_shape_rectangle).setLoginBtnTextId("一键登录").setLogoHidden(true).setSwitchAccHidden(true).setCheckboxHidden(false).setCheckboxDefaultState(true).setCheckboxImgId(R.drawable.fly_verify_demo_customized_checkbox_selector).setAgreementOffsetY(150).setAgreementOffsetX(60).setAgreementOffsetRightX(60).setAgreementColorId(R.color.fly_verify_demo_main_color).setCusAgreementNameId1(R.string.fly_verify_demo_service).setCusAgreementUrl1("http://www.xxx.com/policy/zh").setAgreementTextStart("同意").setAgreementTextEnd("并授权秒验Demo获取本机号码").setAgreementCmccText("《移动统一认证服务条款》").setAgreementCuccText("《联通统一认证服务条款》").setAgreementCtccText("《电信统一认证服务条款》").setBottomTranslateAnim(true).setBackgroundImgId(R.drawable.fly_verify_background_demo_dialog).setDialogTheme(true).setDialogAlignBottom(false).setDialogWidth(300).setDialogHeight(250).setDialogOffsetX(60).setDialogOffsetY(R.dimen.fly_verify_demo_common_30).build();
    }

    public static LandUiSettings customizeUi5(Context context2) {
        return new LandUiSettings.Builder().setNavColorId(-1).setNavTextId("一键登录").setNavTextColorId(ViewCompat.MEASURED_STATE_MASK).setNavCloseImgId(context2.getResources().getDrawable(R.drawable.fly_verify_demo_close)).setNavHidden(false).setNavTransparent(true).setNavCloseImgHidden(false).setNavTextSize(16).setNavCloseImgWidth(30).setNavCloseImgHeight(30).setNavCloseImgOffsetX(15).setNavCloseImgOffsetY(15).setLogoImgId(context2.getResources().getDrawable(R.drawable.fly_verify_demo_logo)).setLogoWidth(80).setLogoHeight(80).setLogoOffsetX(150).setLogoOffsetY(30).setLogoHidden(false).setLogoOffsetRightX(15).setLogoAlignParentRight(false).setNumberColorId(ViewCompat.MEASURED_STATE_MASK).setNumberSizeId(20).setNumberOffsetX(30).setNumberOffsetY(40).setNumberHidden(false).setNumberOffsetRightX(150).setNumberAlignParentRight(true).setSwitchAccColorId(-11626753).setSwitchAccTextSize(16).setSwitchAccHidden(false).setSwitchAccOffsetX(15).setSwitchAccOffsetY(85).setSwitchAccText("其他方式登录").setSwitchAccOffsetRightX(Opcodes.IF_ICMPNE).setSwitchAccAlignParentRight(true).setCheckboxImgId(context2.getResources().getDrawable(R.drawable.fly_verify_demo_customized_checkbox_selector)).setCheckboxDefaultState(false).setCheckboxHidden(false).setAgreementColorId(-99762).setAgreementOffsetX(50).setAgreementOffsetRightX(50).setAgreementOffsetY(210).setAgreementGravityLeft(false).setAgreementBaseTextColorId(ViewCompat.MEASURED_STATE_MASK).setAgreementTextSize(15).setAgreementCmccText("《中国移动服务条款》").setAgreementCuccText("《中国联通服务条款》").setAgreementCtccText("《中国电信服务条款》").setAgreementTextStart("同意").setAgreementTextAnd1("和").setAgreementTextAnd2("、").setAgreementTextEnd("并使用本机号登录").setAgreementHidden(false).setAgreementAlignParentRight(false).setCusAgreementNameId1("隐私服务协议一").setCusAgreementUrl1("http://baidu.com").setCusAgreementNameId2("隐私服务协议二").setCusAgreementUrl2("http://baidu.com").setCusAgreementColor1(-99762).setCusAgreementColor2(-99762).setLoginBtnImgId(context2.getResources().getDrawable(R.drawable.fly_verify_demo_shape_rectangle)).setLoginBtnTextId("登录").setLoginBtnTextColorId(-1).setLoginBtnTextSize(16).setLoginBtnWidth(Opcodes.GOTO_W).setLoginBtnHeight(40).setLoginBtnOffsetY(150).setLoginBtnHidden(false).setBackgroundImgId(context2.getResources().getDrawable(R.color.fly_verify_demo_common_bg)).setBackgroundClickClose(false).setSloganOffsetRightX(15).setSloganAlignParentRight(true).setSloganOffsetX(15).setSloganOffsetY(Opcodes.GOTO_W).setSloganOffsetBottomY(15).setSloganTextSize(16).setSloganTextColor(-40121).setSloganHidden(false).setStartActivityTransitionAnim(R.anim.zoom_in, R.anim.zoom_out).setFinishActivityTransitionAnim(R.anim.zoom_in, R.anim.zoom_out).setImmersiveTheme(false).setImmersiveStatusTextColorBlack(true).build();
    }

    public static List<View> buildCustomView(Context context2) {
        TextView point = new TextView(context2);
        point.setId(R.id.customized_btn_id_2);
        RelativeLayout.LayoutParams pointParams1 = new RelativeLayout.LayoutParams(1, 1);
        pointParams1.addRule(14);
        pointParams1.addRule(12);
        pointParams1.bottomMargin = (int) TypedValue.applyDimension(1, 50.0f, context2.getResources().getDisplayMetrics());
        point.setLayoutParams(pointParams1);
        ImageView wechat = new ImageView(context2);
        wechat.setId(R.id.customized_btn_id_1);
        wechat.setImageDrawable(context2.getResources().getDrawable(R.drawable.fly_verify_demo_wechat));
        RelativeLayout.LayoutParams wechatParams1 = new RelativeLayout.LayoutParams(-2, -2);
        wechatParams1.addRule(12);
        wechatParams1.addRule(0, R.id.customized_btn_id_2);
        wechatParams1.rightMargin = (int) TypedValue.applyDimension(1, 20.0f, context2.getResources().getDisplayMetrics());
        wechatParams1.bottomMargin = (int) TypedValue.applyDimension(1, 50.0f, context2.getResources().getDisplayMetrics());
        wechat.setLayoutParams(wechatParams1);
        ImageView user = new ImageView(context2);
        user.setId(R.id.customized_btn_id_3);
        user.setImageDrawable(context2.getResources().getDrawable(R.drawable.fly_verify_demo_user));
        RelativeLayout.LayoutParams userParams1 = new RelativeLayout.LayoutParams(-2, -2);
        userParams1.addRule(12);
        userParams1.addRule(1, R.id.customized_btn_id_2);
        userParams1.leftMargin = (int) TypedValue.applyDimension(1, 20.0f, context2.getResources().getDisplayMetrics());
        userParams1.bottomMargin = (int) TypedValue.applyDimension(1, 50.0f, context2.getResources().getDisplayMetrics());
        user.setLayoutParams(userParams1);
        List<View> views = new ArrayList<>();
        views.add(wechat);
        views.add(point);
        views.add(user);
        return views;
    }

    public static List<View> buildCustomView2(Context context2) {
        View view = new View(context2);
        view.setId(R.id.customized_view_id);
        view.setBackground(context2.getResources().getDrawable(R.drawable.fly_verify_background_demo));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
        params.addRule(14);
        params.topMargin = (int) TypedValue.applyDimension(1, 260.0f, context2.getResources().getDisplayMetrics());
        view.setLayoutParams(params);
        ImageView btn1 = new ImageView(context2);
        btn1.setId(R.id.customized_btn_id_1);
        btn1.setImageDrawable(context2.getResources().getDrawable(R.drawable.fly_verify_demo_close));
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(-2, -2);
        params1.topMargin = (int) TypedValue.applyDimension(1, 280.0f, context2.getResources().getDisplayMetrics());
        params1.leftMargin = (int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics());
        btn1.setLayoutParams(params1);
        List<View> views = new ArrayList<>();
        views.add(view);
        views.add(btn1);
        return views;
    }

    public static List<View> buildCustomView3(Context context2) {
        ImageView iv0 = new ImageView(context2);
        iv0.setId(R.id.customized_btn_id_0);
        iv0.setImageDrawable(context2.getResources().getDrawable(R.drawable.fly_verify_demo_close));
        RelativeLayout.LayoutParams params0 = new RelativeLayout.LayoutParams(-2, -2);
        params0.addRule(9);
        params0.topMargin = (int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics());
        params0.leftMargin = (int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics());
        iv0.setLayoutParams(params0);
        EditText et1 = new EditText(context2);
        et1.setId(R.id.customized_et_id_0);
        et1.setBackground(null);
        et1.setHint("请输入账号");
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(-1, -2);
        params2.addRule(3, iv0.getId());
        params2.topMargin = (int) TypedValue.applyDimension(1, 30.0f, context2.getResources().getDisplayMetrics());
        params2.leftMargin = (int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics());
        et1.setLayoutParams(params2);
        View view0 = new View(context2);
        view0.setId(R.id.customized_view_id_div);
        view0.setBackgroundColor(context2.getResources().getColor(R.color.fly_verify_demo_text_color_common_gray));
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(-1, 1);
        params4.addRule(3, et1.getId());
        params4.leftMargin = (int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics());
        params4.rightMargin = (int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics());
        view0.setLayoutParams(params4);
        EditText et2 = new EditText(context2);
        et2.setId(R.id.customized_et_id_1);
        et2.setInputType(128);
        et2.setHint("请输入密码");
        et2.setBackground(null);
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(-1, -2);
        params3.addRule(3, view0.getId());
        params3.topMargin = (int) TypedValue.applyDimension(1, 30.0f, context2.getResources().getDisplayMetrics());
        params3.leftMargin = (int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics());
        et2.setLayoutParams(params3);
        View view1 = new View(context2);
        view1.setId(R.id.customized_view_id_div1);
        view1.setBackgroundColor(context2.getResources().getColor(R.color.fly_verify_demo_text_color_common_gray));
        RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(-1, 1);
        params5.addRule(3, et2.getId());
        params5.leftMargin = (int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics());
        params5.rightMargin = (int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics());
        view1.setLayoutParams(params5);
        Button button = new Button(context2);
        button.setId(R.id.customized_btn_id_3);
        button.setText("登录");
        button.setBackground(context2.getResources().getDrawable(R.drawable.fly_verify_demo_shape_rectangle));
        RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(-1, -2);
        params6.leftMargin = (int) TypedValue.applyDimension(1, 30.0f, context2.getResources().getDisplayMetrics());
        params6.rightMargin = (int) TypedValue.applyDimension(1, 30.0f, context2.getResources().getDisplayMetrics());
        params6.topMargin = (int) TypedValue.applyDimension(1, 30.0f, context2.getResources().getDisplayMetrics());
        params6.addRule(3, view1.getId());
        button.setLayoutParams(params6);
        TextView view = new TextView(context2);
        view.setId(R.id.customized_view_id);
        view.setText("其他方式登录");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
        params.addRule(14);
        params.addRule(12);
        params.bottomMargin = (int) TypedValue.applyDimension(1, 150.0f, context2.getResources().getDisplayMetrics());
        view.setLayoutParams(params);
        ImageView iv1 = new ImageView(context2);
        iv1.setId(R.id.customized_btn_id_1);
        iv1.setImageDrawable(context2.getResources().getDrawable(R.drawable.fly_verify_demo_wechat));
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(-2, -2);
        params1.addRule(14);
        params1.addRule(12);
        params1.bottomMargin = (int) TypedValue.applyDimension(1, 120.0f, context2.getResources().getDisplayMetrics());
        iv1.setLayoutParams(params1);
        List<View> views = new ArrayList<>();
        views.add(iv0);
        views.add(view);
        views.add(iv1);
        views.add(et1);
        views.add(view0);
        views.add(et2);
        views.add(view1);
        views.add(button);
        return views;
    }

    public static List<View> buildCustomView4(Context context2) throws Resources.NotFoundException {
        Drawable logo = context2.getResources().getDrawable(R.drawable.fly_verify_demo_logo_small);
        logo.setBounds(0, 0, (int) TypedValue.applyDimension(1, 30.0f, context2.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(1, 30.0f, context2.getResources().getDisplayMetrics()));
        TextView view = new TextView(context2);
        view.setId(R.id.customized_view_id);
        view.setText(R.string.app_name);
        view.setTextSize(20.0f);
        view.setTextColor(context2.getResources().getColor(R.color.fly_verify_demo_text_color_common_black));
        view.setTypeface(Typeface.DEFAULT_BOLD);
        view.setGravity(17);
        view.setCompoundDrawables(logo, null, null, null);
        view.setCompoundDrawablePadding((int) TypedValue.applyDimension(1, 15.0f, context2.getResources().getDisplayMetrics()));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
        params.addRule(14);
        params.topMargin = (int) TypedValue.applyDimension(1, 5.0f, context2.getResources().getDisplayMetrics());
        view.setLayoutParams(params);
        List<View> views = new ArrayList<>();
        views.add(view);
        return views;
    }
}