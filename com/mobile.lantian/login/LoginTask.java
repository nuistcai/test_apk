package com.mobile.lantian.login;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import cn.fly.verify.datatype.VerifyResult;
import com.alibaba.fastjson.JSONObject;
import com.mobile.lantian.ResultListener;
import com.mobile.lantian.util.Base64Utils;
import com.mobile.lantian.util.DES;
import com.mobile.lantian.util.SignUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes.dex */
public class LoginTask {
    public static final String APP_KEY = "3b92d7d4311cf";
    public static final String AppSecret = "b81ee60ecb8c45c6385decb12b5dbee5";
    private static final String URL_LOGIN = "https://identify-auth.zztfly.com/auth/auth/sdkClientFreeLogin";
    private static LoginTask instance;

    private LoginTask() {
    }

    public static LoginTask getInstance() {
        if (instance == null) {
            synchronized (LoginTask.class) {
                if (instance == null) {
                    instance = new LoginTask();
                }
            }
        }
        return instance;
    }

    public void login(VerifyResult verifyResult, final ResultListener<String> resultListener) {
        HashMap<String, Object> values = new HashMap<>();
        if (verifyResult != null) {
            values.put("appkey", APP_KEY);
            values.put("token", verifyResult.getToken());
            values.put("opToken", verifyResult.getOpToken());
            values.put("operator", verifyResult.getOperator());
            values.put("timestamp", Long.valueOf(System.currentTimeMillis()));
            values.put("sign", SignUtil.getSign(values, AppSecret));
        }
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(8L, TimeUnit.SECONDS).writeTimeout(8L, TimeUnit.SECONDS).readTimeout(16L, TimeUnit.SECONDS).retryOnConnectionFailure(true).hostnameVerifier(new HostnameVerifier() { // from class: com.mobile.lantian.login.LoginTask.1
            @Override // javax.net.ssl.HostnameVerifier
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).build();
        String json = toJson(values);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(URL_LOGIN).post(body).build();
        client.newCall(request).enqueue(new Callback() { // from class: com.mobile.lantian.login.LoginTask.2
            @Override // okhttp3.Callback
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.mobile.lantian.login.LoginTask.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        resultListener.onFailure(e);
                    }
                });
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                Log.d("JIYAN", "result : " + result);
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.mobile.lantian.login.LoginTask.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (response.code() == 200) {
                            try {
                                JSONObject jsonObject = JSONObject.parseObject(result);
                                String res1 = jsonObject.getString("res");
                                byte[] decode = DES.decode(Base64Utils.decode(res1.getBytes()), LoginTask.AppSecret.getBytes());
                                jsonObject.put("res", (Object) JSONObject.parseObject(new String(decode, "UTF-8")));
                                System.out.println(jsonObject);
                                JSONObject resJson = jsonObject.getJSONObject("res");
                                String phone = resJson.getString("phone");
                                System.out.println("用户手机号是：" + phone);
                            } catch (Throwable t) {
                                Log.e("JIYAN", t.getMessage(), t);
                            }
                            Log.d("JIYAN", "res : ");
                        }
                    }
                });
            }
        });
    }

    private String toJson(HashMap<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        Object[] keys = params.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"").append(keys[i]).append("\"").append(":");
            stringBuilder.append("\"").append(params.get(keys[i])).append("\"");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}