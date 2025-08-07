package com.unicom.online.account.shield;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.unicom.online.account.kernel.a;
import com.unicom.online.account.kernel.ac;
import com.unicom.online.account.kernel.b;
import com.unicom.online.account.kernel.h;
import com.unicom.online.account.kernel.i;
import com.unicom.online.account.kernel.j;
import com.unicom.online.account.kernel.x;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class UniAccountHelper {
    private static final int CU_GET_TOKEN_GM = 3;
    private static final int CU_GET_TOKEN_IT = 2;
    private static final int CU_GET_TOKEN_LOOP = 32;
    private static final int CU_GET_UAID_GM = 25;
    private static final int CU_GET_UAID_IT = 24;
    private static final int CU_GET_UAID_LOOP = 33;
    private static final int CU_MOBILE_AUTH_GM = 5;
    private static final int CU_MOBILE_AUTH_IT = 4;
    private static final int ID_0_STOP_ONCE_SUCCESS = 0;
    private static final int ID_1_STOP_ALL_SEND = 1;
    private static final int LOOP_TYPE_TOKEN = 1;
    private static final int LOOP_TYPE_UAID = 2;
    private static final int LoopMaxNum = 5;
    private static final int SUCCESS = 100;
    private static final boolean enableToken = true;
    private static final boolean enableUAID = false;
    private Context mContext = null;
    Handler mainHandler;
    private static volatile UniAccountHelper s_instance = null;
    private static int loopNumToken = 0;
    private static int loopNumUaid = 0;
    private static int loopNumCommon = 0;
    private static int LoopTypeFlag = 0;

    private UniAccountHelper() {
    }

    private UniAccountHelper clearCache_one(int i) {
        if (this.mContext == null) {
            a.a("clearCache sdk未初始化 ---> " + System.currentTimeMillis());
            return null;
        }
        b.a();
        i.a().a(this.mContext, i);
        return s_instance;
    }

    private static int clrLoopTypeFlag() {
        LoopTypeFlag = 0;
        return 0;
    }

    private void cuGetTokenLoop(int i, int i2, int i3, ResultListener resultListener) throws JSONException {
        a.a("cuGetTokenLoop ---> " + System.currentTimeMillis());
        if (isInUaidLoop()) {
            initFail(i2, resultListener, 410023, "取号服务中，勿重复调用", i3);
            return;
        }
        if (isInTokenLoop()) {
            initFail(i2, resultListener, 410023, "取号服务中，勿重复调用", i3);
            return;
        }
        setLoopTypeFlag(1);
        int iLoopNumAdd = loopNumToken + loopNumAdd(i);
        loopNumToken = iLoopNumAdd;
        loopNumCommon = iLoopNumAdd;
        cuGetTokenUaidLoopCommon(0, i2, i3, resultListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cuGetTokenUaidLoopCommon(final int i, final int i2, final int i3, final ResultListener resultListener) throws JSONException {
        ResultListener resultListener2;
        if (loopNumCommon == 0) {
            cuResetLoopSrc();
            return;
        }
        switch (i) {
            case 1:
                resultListener2 = new ResultListener() { // from class: com.unicom.online.account.shield.UniAccountHelper.2
                    @Override // com.unicom.online.account.shield.ResultListener
                    public void onResult(String str) throws JSONException {
                        try {
                            UniAccountHelper.this.loopNumCommonMinus();
                            JSONObject jSONObject = new JSONObject(str);
                            b.d(jSONObject.optString("seq"));
                            jSONObject.put("type", i2);
                            resultListener.onResult(str);
                            if (UniAccountHelper.loopNumCommon == 0) {
                                UniAccountHelper.cuResetLoopSrc();
                            } else if (UniAccountHelper.loopNumCommon > 0) {
                                UniAccountHelper.this.cuGetTokenUaidLoopCommon(i, i2, i3, resultListener);
                            }
                        } catch (JSONException e) {
                            a.a(e);
                        }
                    }
                };
                break;
            default:
                resultListener2 = new ResultListener() { // from class: com.unicom.online.account.shield.UniAccountHelper.1
                    @Override // com.unicom.online.account.shield.ResultListener
                    public void onResult(String str) throws JSONException {
                        try {
                            UniAccountHelper.this.loopNumCommonMinus();
                            JSONObject jSONObject = new JSONObject(str);
                            b.d(jSONObject.optString("seq"));
                            int i4 = jSONObject.getInt("resultCode");
                            jSONObject.put("type", i2);
                            if (i4 == 100) {
                                UniAccountHelper.cuResetLoopSrc();
                                resultListener.onResult(str);
                            } else if (UniAccountHelper.loopNumCommon == 0) {
                                UniAccountHelper.cuResetLoopSrc();
                                resultListener.onResult(str);
                            } else if (UniAccountHelper.loopNumCommon > 0) {
                                UniAccountHelper.this.cuGetTokenUaidLoopCommon(i, i2, i3, resultListener);
                            }
                        } catch (JSONException e) {
                            a.a(e);
                        }
                    }
                };
                break;
        }
        cuPreGetToken(i3, i2, "cuPreGetToken", resultListener2);
    }

    private void cuGetUaid(int i, ResultListener resultListener) throws JSONException {
        initFail(0, resultListener, 410022, "不支持该服务", i);
    }

    private void cuGetUaidLoop(int i, int i2, int i3, ResultListener resultListener) throws JSONException {
        initFail(i2, resultListener, 410022, "不支持该服务", i3);
    }

    private void cuGetUaidLoop(int i, int i2, ResultListener resultListener) throws JSONException {
        initFail(0, resultListener, 410022, "不支持该服务", i2);
    }

    private static boolean cuIsToken(int i) {
        return ac.a(i);
    }

    private static boolean cuIsUaid(int i) {
        return ac.b(i);
    }

    private void cuPreGetToken(final int i, final int i2, final String str, final ResultListener resultListener) throws JSONException {
        String strA;
        if (this.mContext == null) {
            initFail(i2, resultListener, "sdk未初始化", i);
        }
        if (!i.b(this.mContext.getApplicationContext())) {
            initFail(i2, resultListener, 410004, "数据网络未开启", i);
            return;
        }
        if (getUseCacheFlag()) {
            i iVarA = i.a();
            Context context = this.mContext;
            if (str.equals(iVarA.b)) {
                strA = j.a(context, i.a(i2), str + iVarA.c);
            } else {
                strA = "";
            }
            if (b.e(strA).booleanValue()) {
                try {
                    JSONObject jSONObject = new JSONObject(strA);
                    int i3 = jSONObject.getInt("resultCode");
                    try {
                        jSONObject.getInt("type");
                    } catch (Exception e) {
                    }
                    if (cuIsToken(2)) {
                        long j = jSONObject.getJSONObject("resultData").getLong("exp");
                        if (i3 == 100 && j > System.currentTimeMillis()) {
                            resultListener.onResult(strA);
                            return;
                        }
                    } else if (cuIsUaid(2)) {
                        long j2 = jSONObject.getLong("exp");
                        if (i3 == 100 && j2 > System.currentTimeMillis()) {
                            resultListener.onResult(strA);
                            return;
                        }
                    }
                } catch (Exception e2) {
                }
            }
        }
        i.a();
        if (!i.a(this.mContext)) {
            initFail(i2, resultListener, 410025, "操作频繁,请稍后再试", i);
            return;
        }
        if (!str.equals("cuPreGetToken")) {
            initFail(i2, resultListener, "sdk参数错误", i);
            return;
        }
        switch (i2) {
            case 2:
            case 3:
            case 4:
            case 5:
                i iVarA2 = i.a();
                h hVar = new h() { // from class: com.unicom.online.account.shield.UniAccountHelper.4
                    @Override // com.unicom.online.account.kernel.h
                    public void onResult(String str2) throws JSONException {
                        i.a();
                        boolean zC = i.c();
                        try {
                            final JSONObject jSONObject2 = new JSONObject(str2);
                            String strOptString = jSONObject2.optString("seq");
                            b.d(strOptString);
                            int i4 = jSONObject2.getInt("resultCode");
                            String string = jSONObject2.getString("resultMsg");
                            String strA2 = "";
                            if (i4 == 100) {
                                JSONObject jSONObject3 = jSONObject2.getJSONObject("resultData");
                                b.b(jSONObject3.optString("fakeMobile"));
                                b.c(jSONObject3.optString("accessCode"));
                                b.b(jSONObject3.getLong("exp"));
                                b.a(System.currentTimeMillis());
                                String strOptString2 = jSONObject2.optString("operator");
                                if (!TextUtils.isEmpty(strOptString2)) {
                                    b.a(strOptString2);
                                }
                                if (4 == i2 || 5 == i2) {
                                    jSONObject3.put("fakeMobile", (Object) null);
                                }
                                if (UniAccountHelper.this.getUseCacheFlag()) {
                                    i.a().a(UniAccountHelper.this.mContext, i2);
                                    i.a().a(UniAccountHelper.this.mContext, i2, str, jSONObject2.toString());
                                }
                                if (zC) {
                                    i.a();
                                    strA2 = i.a(0, 0, strOptString, i4, i, i4, string);
                                }
                            } else if (zC) {
                                int i5 = i4 == 410000 ? 2 : 3;
                                i.a();
                                strA2 = i.a(1, i5, strOptString, i4, i, i4, string);
                            }
                            a.b(jSONObject2.toString());
                            if (UniAccountHelper.this.mainHandler == null || !ac.f) {
                                resultListener.onResult(jSONObject2.toString());
                            } else {
                                UniAccountHelper.this.mainHandler.post(new Runnable() { // from class: com.unicom.online.account.shield.UniAccountHelper.4.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        resultListener.onResult(jSONObject2.toString());
                                    }
                                });
                            }
                            if (zC) {
                                i.a().b(strA2);
                            }
                        } catch (JSONException e3) {
                            a.a(e3);
                        }
                    }
                };
                if (iVarA2.a("cuPreGetToken", i, i2, hVar)) {
                    new x().a(iVarA2.a, i, i2, hVar);
                    break;
                }
                break;
            case 24:
            case 25:
                i iVarA3 = i.a();
                h hVar2 = new h() { // from class: com.unicom.online.account.shield.UniAccountHelper.3
                    @Override // com.unicom.online.account.kernel.h
                    public void onResult(String str2) {
                        try {
                            final JSONObject jSONObject2 = new JSONObject(str2);
                            b.d(jSONObject2.optString("seq"));
                            if (jSONObject2.getInt("resultCode") == 100 && UniAccountHelper.this.getUseCacheFlag()) {
                                i.a().a(UniAccountHelper.this.mContext, i2);
                                i.a().a(UniAccountHelper.this.mContext, i2, str, jSONObject2.toString());
                            }
                            a.b(jSONObject2.toString());
                            if (UniAccountHelper.this.mainHandler == null || !ac.f) {
                                resultListener.onResult(jSONObject2.toString());
                            } else {
                                UniAccountHelper.this.mainHandler.post(new Runnable() { // from class: com.unicom.online.account.shield.UniAccountHelper.3.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        resultListener.onResult(jSONObject2.toString());
                                    }
                                });
                            }
                        } catch (JSONException e3) {
                            a.a(e3);
                        }
                    }
                };
                if (iVarA3.a("cuPreGetUAID", i, i2, hVar2)) {
                    new x().a(iVarA3.a, i, i2, hVar2);
                    break;
                }
                break;
            default:
                initFail(i2, resultListener, "sdk type 参数错误", i);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void cuResetLoopSrc() {
        loopNumCommon = 0;
        loopNumToken = 0;
        loopNumUaid = 0;
        clrLoopTypeFlag();
    }

    public static String getCertFingerType() {
        return ac.e;
    }

    private String getHostName() {
        i.a();
        return i.d();
    }

    public static UniAccountHelper getInstance() {
        if (s_instance == null) {
            synchronized (UniAccountHelper.class) {
                if (s_instance == null) {
                    s_instance = new UniAccountHelper();
                }
            }
        }
        return s_instance;
    }

    private static int getLoopTypeFlag() {
        return LoopTypeFlag;
    }

    private String getSdkVersion() {
        i.a();
        return i.b();
    }

    private UniAccountHelper initCustmType(int i) {
        i.a();
        i.b(i);
        return s_instance;
    }

    private void initFail(int i, ResultListener resultListener, int i2, String str, int i3) throws JSONException {
        a.b("type:" + i + "\nmsg:" + str);
        try {
            String strG = i.g();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("resultCode", i2);
            jSONObject.put("resultMsg", str);
            jSONObject.put("resultData", "");
            jSONObject.put("seq", strG);
            cuResetLoopSrc();
            if (resultListener != null) {
                resultListener.onResult(jSONObject.toString());
            }
            i.a();
            if (i.c()) {
                i.a();
                i.a().b(i.a(1, 1, strG, i2, i3, i2, str));
            }
        } catch (Exception e) {
            a.a(e);
        }
    }

    private void initFail(int i, ResultListener resultListener, String str, int i2) throws JSONException {
        initFail(i, resultListener, 410021, str, i2);
    }

    private UniAccountHelper initHostName(String str) {
        i.a();
        if (i.c(str)) {
            return s_instance;
        }
        a.b("初始化参数错误");
        return null;
    }

    private static boolean isInTokenLoop() {
        return (LoopTypeFlag & 1) != 0;
    }

    private static boolean isInUaidLoop() {
        return (LoopTypeFlag & 2) != 0;
    }

    private int loopNumAdd(int i) {
        if (i >= 5) {
            return 5;
        }
        if (i <= 1) {
            return 1;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loopNumCommonMinus() {
        loopNumCommon = loopNumCommon > 5 ? 4 : loopNumCommon > 0 ? loopNumCommon - 1 : 0;
    }

    private static int setLoopTypeFlag(int i) {
        int i2 = i | LoopTypeFlag;
        LoopTypeFlag = i2;
        return i2;
    }

    public UniAccountHelper clearCache() {
        if (this.mContext == null) {
            a.a("clearCache sdk未初始化 ---> " + System.currentTimeMillis());
            return null;
        }
        b.a();
        i.a();
        i.c(this.mContext);
        return s_instance;
    }

    public String cuDebugInfo(String str) {
        return this.mContext == null ? "sdk 未初始化, context 为空" : i.a().a(str);
    }

    public void cuGetToken(int i, ResultListener resultListener) throws JSONException {
        cuPreGetToken(i, ac.a ? 3 : 2, "cuPreGetToken", resultListener);
    }

    public void cuGetTokenLoop(int i, int i2, ResultListener resultListener) throws JSONException {
        cuGetTokenLoop(i, ac.a ? 3 : 2, i2, resultListener);
    }

    public void cuMobileAuth(int i, ResultListener resultListener) throws JSONException {
        cuPreGetToken(i, ac.a ? 5 : 4, "cuPreGetToken", resultListener);
    }

    public boolean getUseCacheFlag() {
        return ac.d;
    }

    public UniAccountHelper init(Context context, String str) {
        return init(context, str, false);
    }

    public UniAccountHelper init(Context context, String str, boolean z) {
        String str2;
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str)) {
            str2 = "初始化参数不能为空";
        } else if (this.mContext != null) {
            str2 = "重复初始化";
        } else {
            this.mContext = context.getApplicationContext();
            if (i.a().a(context, str, str, z)) {
                b.a = str;
                this.mainHandler = new Handler(Looper.getMainLooper());
                return s_instance;
            }
            str2 = "UniAuthHelper.getInstance().init 初始化错误";
        }
        a.b(str2);
        return null;
    }

    public void releaseNetwork() {
        i.a();
        i.f();
    }

    public UniAccountHelper setCBinMainThread(boolean z) {
        ac.f = z;
        return s_instance;
    }

    public UniAccountHelper setCertFingerType(String str) {
        if (!str.equalsIgnoreCase("MD5") && !str.equalsIgnoreCase("SHA1") && !str.equalsIgnoreCase("SHA256") && !str.equalsIgnoreCase("sm3")) {
            return null;
        }
        ac.e = str.toLowerCase();
        return s_instance;
    }

    public UniAccountHelper setCryptoGM(boolean z) {
        ac.a = z;
        return s_instance;
    }

    public void setLogEnable(boolean z) {
        a.a(z);
        i.a();
        i.a(z);
    }

    public UniAccountHelper setUseCacheFlag(boolean z) {
        ac.d = z;
        return s_instance;
    }
}