package cn.fly.verify;

import android.app.Activity;
import android.content.Context;
import cn.fly.FlySDK;
import cn.fly.commons.FLYVERIFY;
import cn.fly.tools.proguard.EverythingKeeper;
import cn.fly.verify.datatype.LandUiSettings;
import cn.fly.verify.datatype.UiSettings;
import cn.fly.verify.ui.component.LoginAdapter;

/* loaded from: classes.dex */
public class FlyVerify implements EverythingKeeper {
    private static final l impl = l.a();

    public static void OtherOAuthPageCallBack(OAuthPageEventCallback oAuthPageEventCallback) {
        impl.a(oAuthPageEventCallback);
    }

    public static void autoFinishOAuthPage(boolean z) {
        impl.a(z);
    }

    public static void closeOrientationDetector(boolean z) {
        i.a().c(z);
    }

    public static void finishOAuthPage() {
        impl.b();
    }

    public static Activity getAuthPageActivity() {
        return i.a().l();
    }

    @Deprecated
    public static Context getContext() {
        return FlySDK.getContext();
    }

    public static String getVersion() {
        return "13.7.6";
    }

    @Deprecated
    public static void init(Context context, String str, String str2) {
        FlySDK.init(context, str, str2);
    }

    public static boolean isVerifySupport() {
        return impl.d();
    }

    public static void otherLoginAutoFinishOAuthPage(boolean z) {
        impl.b(z);
    }

    public static void preVerify(OperationCallback operationCallback) {
        impl.a(operationCallback);
    }

    public static void preVerify(PreVerifyCallback preVerifyCallback) {
        impl.a(preVerifyCallback);
    }

    public static void preVerify(ResultCallback<Void> resultCallback) {
        impl.a(resultCallback);
    }

    public static void refreshOAuthPage() {
        impl.c();
    }

    public static void setAdapterClass(Class<? extends LoginAdapter> cls) {
        impl.a(cls);
    }

    public static void setAdapterFullName(String str) {
        impl.a(str);
    }

    @Deprecated
    public static void setChannel(int i) {
        try {
            FlySDK.setChannel(new FLYVERIFY(), i);
        } catch (Throwable th) {
        }
    }

    public static void setDebugMode(boolean z) {
        impl.c(z);
    }

    public static void setLandUiSettings(LandUiSettings landUiSettings) {
        impl.a(landUiSettings);
    }

    public static void setTimeOut(int i) {
        impl.a(i);
    }

    public static void setUiSettings(UiSettings uiSettings) {
        impl.a(uiSettings);
    }

    @Deprecated
    public static void submitPolicyGrantResult(CustomController customController, boolean z) {
        FlySDK.submitPolicyGrantResult(customController, z);
    }

    @Deprecated
    public static void submitPolicyGrantResult(boolean z) {
        FlySDK.submitPolicyGrantResult(z);
    }

    @Deprecated
    public static void updateCustomController(CustomController customController) {
        FlySDK.updateFlyCustomController(customController);
    }

    public static void verify(PageCallback pageCallback, GetTokenCallback getTokenCallback) {
        impl.a(pageCallback, getTokenCallback);
    }

    public static void verify(VerifyCallback verifyCallback) {
        impl.a(verifyCallback);
    }

    public static void verify(VerifyResultCallback verifyResultCallback) {
        impl.a(verifyResultCallback);
    }

    public static void verifyWithId(VerifyCallback verifyCallback, String str) {
        if (!FlySDK.isForb()) {
            u uVar = new u(g.VERIFY);
            uVar.a((String) null, (String) null, "check callback", "cb:" + (verifyCallback != null) + ",id:" + str);
            uVar.b();
            impl.a(uVar);
        }
        impl.a(verifyCallback);
    }
}