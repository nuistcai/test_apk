package cn.fly.verify.util;

import android.text.TextUtils;
import cn.fly.verify.GetTokenCallback;
import cn.fly.verify.PageCallback;
import cn.fly.verify.VerifyCallback;
import cn.fly.verify.common.exception.VerifyException;

/* loaded from: classes.dex */
public class c {
    public static void a(PageCallback pageCallback, VerifyCallback verifyCallback, VerifyException verifyException, VerifyException verifyException2) {
        String str;
        String str2;
        int i;
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sbAppend;
        String message;
        StringBuilder sbAppend2;
        String message2;
        int iA;
        String str3;
        String str4;
        cn.fly.verify.i.a().a(true);
        if (verifyCallback.isCanceled()) {
            return;
        }
        if (verifyException2 != null && verifyException2.getCode() == cn.fly.verify.m.INNER_OTHER_LOGIN.a()) {
            if (verifyCallback instanceof GetTokenCallback) {
                verifyCallback.onFailure(verifyException);
            } else {
                verifyCallback.onOtherLogin();
            }
            if (pageCallback == null) {
                return;
            }
            iA = cn.fly.verify.m.C_OTHER_LOGIN.a();
            str3 = "other_way_login";
            str4 = "other way login";
        } else {
            if (verifyException2 == null || verifyException2.getCode() != cn.fly.verify.m.INNER_CANCEL_LOGIN.a()) {
                if (pageCallback != null && !cn.fly.verify.i.a().h()) {
                    verifyCallback.onFailure(verifyException);
                    String strJ = cn.fly.verify.i.a().j();
                    if (TextUtils.isEmpty(strJ) && !VerifyException.isPrivacy(verifyException)) {
                        strJ = l.c();
                    }
                    if (verifyException != null) {
                        if (verifyException.getCode() != cn.fly.verify.m.C_ONE_KEY_OBTAIN_OPERATOR_ACCESS_TOKEN_ERR.a() && verifyException.getCode() != cn.fly.verify.m.C_ONE_KEY_OBTAIN_OPERATOR_ACCESS_CODE_ERR.a() && verifyException.getCode() != cn.fly.verify.m.C_ONE_KEY_OBTAIN_CT_OPERATOR_ACCESS_CODE_ERR.a() && verifyException.getCode() != cn.fly.verify.m.C_ONE_KEY_OBTAIN_CU_OPERATOR_ACCESS_CODE_ERR.a() && verifyException.getCode() != cn.fly.verify.m.C_ONE_KEY_OBTAIN_CM_OPERATOR_ACCESS_CODE_ERR.a() && verifyException.getCode() != cn.fly.verify.m.C_ONE_KEY_OBTAIN_CM_OPERATOR_ACCESS_TOKEN_ERR.a() && verifyException.getCode() != cn.fly.verify.m.C_ONE_KEY_OBTAIN_CU_OPERATOR_ACCESS_TOKEN_ERR.a() && verifyException.getCode() != cn.fly.verify.m.C_ONE_KEY_OBTAIN_CT_OPERATOR_ACCESS_TOKEN_ERR.a()) {
                            if (verifyException.getCause() != null) {
                                sbAppend2 = new StringBuilder().append(l.a("pull_up_page_err", "pull up err"));
                                message2 = verifyException.getCause().getMessage();
                            } else {
                                if (verifyException.getMessage() == null) {
                                    return;
                                }
                                sbAppend2 = new StringBuilder().append(l.a("pull_up_page_err", "pull up err"));
                                message2 = verifyException.getMessage();
                            }
                            pageCallback.pageCallback(6119145, sbAppend2.append(message2).toString());
                            return;
                        }
                        if (TextUtils.equals(strJ, "CMCC")) {
                            str = "cmcc pull up err";
                            str2 = "cmcc_pull_up_page_err";
                            i = 6119147;
                            if (verifyException.getCause() != null) {
                                sb2 = new StringBuilder();
                                sbAppend = sb2.append(l.a(str2, str)).append(" ");
                                message = verifyException.getCause().getMessage();
                            } else {
                                if (verifyException.getMessage() == null) {
                                    return;
                                }
                                sb = new StringBuilder();
                                sbAppend = sb.append(l.a(str2, str)).append(" ");
                                message = verifyException.getMessage();
                            }
                        } else if (TextUtils.equals(strJ, "CUCC")) {
                            str = "cucc pull up err";
                            str2 = "cucc_pull_up_page_err";
                            i = 6119148;
                            if (verifyException.getCause() != null) {
                                sb2 = new StringBuilder();
                                sbAppend = sb2.append(l.a(str2, str)).append(" ");
                                message = verifyException.getCause().getMessage();
                            } else {
                                if (verifyException.getMessage() == null) {
                                    return;
                                }
                                sb = new StringBuilder();
                                sbAppend = sb.append(l.a(str2, str)).append(" ");
                                message = verifyException.getMessage();
                            }
                        } else {
                            if (!TextUtils.equals(strJ, "CTCC")) {
                                return;
                            }
                            str = "ctcc pull up err";
                            str2 = "ctcc_pull_up_page_err";
                            i = 6119149;
                            if (verifyException.getCause() != null) {
                                sb2 = new StringBuilder();
                                sbAppend = sb2.append(l.a(str2, str)).append(" ");
                                message = verifyException.getCause().getMessage();
                            } else {
                                if (verifyException.getMessage() == null) {
                                    return;
                                }
                                sb = new StringBuilder();
                                sbAppend = sb.append(l.a(str2, str)).append(" ");
                                message = verifyException.getMessage();
                            }
                        }
                        pageCallback.pageCallback(i, sbAppend.append(message).toString());
                        return;
                    }
                }
                verifyCallback.onFailure(verifyException);
                return;
            }
            if (verifyCallback instanceof GetTokenCallback) {
                verifyCallback.onFailure(verifyException);
            } else {
                verifyCallback.onUserCanceled();
            }
            if (pageCallback == null) {
                return;
            }
            iA = cn.fly.verify.m.C_CANCEL_LOGIN.a();
            str3 = "click_return_button";
            str4 = "click return button";
        }
        pageCallback.pageCallback(iA, l.a(str3, str4));
    }
}