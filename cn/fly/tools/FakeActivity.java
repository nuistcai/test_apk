package cn.fly.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import cn.fly.commons.m;
import cn.fly.tools.proguard.EverythingKeeper;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import cn.fly.tools.utils.ResHelper;
import cn.fly.tools.utils.UIHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/* loaded from: classes.dex */
public class FakeActivity implements EverythingKeeper {
    private static Class<? extends Activity> shellClass;
    protected Activity activity;
    private View contentView;
    private HashMap<String, Object> result;
    private FakeActivity resultReceiver;

    public static void setShell(Class<? extends Activity> cls) {
        shellClass = cls;
    }

    public static void registerExecutor(String str, Object obj) {
        if (shellClass != null) {
            try {
                Method method = shellClass.getMethod(m.a("016Yfl]h7glfkhkNkh'flikgk2he:fiKk4fmfl"), String.class, Object.class);
                method.setAccessible(true);
                method.invoke(null, str, obj);
                return;
            } catch (Throwable th) {
                FlyLog.getInstance().w(th);
                return;
            }
        }
        FlyUIShell.a(str, obj);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    protected int onSetTheme(int i, boolean z) {
        return i;
    }

    protected boolean disableScreenCapture() {
        if (this.activity != null) {
            this.activity.getWindow().setFlags(8192, 8192);
            return true;
        }
        return false;
    }

    public void setContentViewLayoutResName(String str) {
        int layoutRes;
        if (this.activity != null && (layoutRes = ResHelper.getLayoutRes(this.activity, str)) > 0) {
            this.activity.setContentView(layoutRes);
        }
    }

    public void setContentView(View view) {
        this.contentView = view;
    }

    public View getContentView() {
        return this.contentView;
    }

    public <T extends View> T findViewById(int i) {
        if (this.activity == null) {
            return null;
        }
        return (T) this.activity.findViewById(i);
    }

    public <T extends View> T findViewByResName(View view, String str) {
        int idRes;
        if (this.activity != null && (idRes = ResHelper.getIdRes(this.activity, str)) > 0) {
            return (T) view.findViewById(idRes);
        }
        return null;
    }

    public <T extends View> T findViewByResName(String str) {
        int idRes;
        if (this.activity != null && (idRes = ResHelper.getIdRes(this.activity, str)) > 0) {
            return (T) findViewById(idRes);
        }
        return null;
    }

    public void onCreate() {
    }

    public void onNewIntent(Intent intent) {
    }

    public void onStart() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStop() {
    }

    public void onRestart() {
    }

    public boolean onFinish() {
        return false;
    }

    public void onDestroy() {
    }

    public final void finish() {
        if (this.activity != null) {
            this.activity.finish();
        }
    }

    public boolean onKeyEvent(int i, KeyEvent keyEvent) {
        return false;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void startActivity(Intent intent) {
        startActivityForResult(intent, -1);
    }

    public void startActivityForResult(Intent intent, int i) {
        if (this.activity == null) {
            return;
        }
        this.activity.startActivityForResult(intent, i);
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
    }

    public Context getContext() {
        return this.activity;
    }

    public void show(Context context, Intent intent) {
        showForResult(context, intent, null);
    }

    public void showForResult(final Context context, Intent intent, FakeActivity fakeActivity) {
        final Intent intent2;
        String strA;
        this.resultReceiver = fakeActivity;
        if (shellClass != null) {
            intent2 = new Intent(context, shellClass);
            strA = null;
            try {
                Method method = shellClass.getMethod(m.a("016 fl6hGglfkhkRkh(flikgk5he(fi6k+fmfl"), Object.class);
                method.setAccessible(true);
                strA = (String) method.invoke(null, this);
            } catch (Throwable th) {
                FlyLog.getInstance().w(th);
            }
        } else {
            intent2 = new Intent(context, (Class<?>) FlyUIShell.class);
            strA = FlyUIShell.a(this);
        }
        intent2.putExtra(m.a("011if7fiOgejIfj>k[fkfhEh"), strA);
        intent2.putExtra(m.a("013h1gkHhe fi1k*fmflfj0gf:fhEh"), getClass().getName());
        if (intent != null) {
            intent2.putExtras(intent);
        }
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            showActivity(context, intent2);
        } else {
            UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.tools.FakeActivity.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    FakeActivity.this.showActivity(context, intent2);
                    return false;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showActivity(final Context context, final Intent intent) {
        if (!(context instanceof Activity)) {
            DH.requester(context).getTopActivity().request(new DH.DHResponder() { // from class: cn.fly.tools.FakeActivity.2
                @Override // cn.fly.tools.utils.DH.DHResponder
                public void onResponse(DH.DHResponse dHResponse) {
                    Activity topActivity = dHResponse.getTopActivity();
                    if (topActivity == null) {
                        intent.addFlags(268435456);
                        context.startActivity(intent);
                    } else {
                        topActivity.startActivity(intent);
                    }
                }
            });
        } else {
            context.startActivity(intent);
        }
    }

    public FakeActivity getParent() {
        return this.resultReceiver;
    }

    public final void setResult(HashMap<String, Object> map) {
        this.result = map;
    }

    public void sendResult() {
        if (this.resultReceiver != null) {
            this.resultReceiver.onResult(this.result);
        }
    }

    public void onResult(HashMap<String, Object> map) {
    }

    public void runOnUIThread(final Runnable runnable) {
        UIHandler.sendEmptyMessage(0, new Handler.Callback() { // from class: cn.fly.tools.FakeActivity.3
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                runnable.run();
                return false;
            }
        });
    }

    public void runOnUIThread(final Runnable runnable, long j) {
        UIHandler.sendEmptyMessageDelayed(0, j, new Handler.Callback() { // from class: cn.fly.tools.FakeActivity.4
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                runnable.run();
                return false;
            }
        });
    }

    public void setRequestedOrientation(int i) {
        if (this.activity == null) {
            return;
        }
        if (DH.SyncMtd.getOSVersionIntForFly() < 26 || getContext().getApplicationInfo().targetSdkVersion < 27) {
            this.activity.setRequestedOrientation(i);
        }
    }

    public void requestPortraitOrientation() {
        setRequestedOrientation(1);
    }

    public void requestLandscapeOrientation() {
        setRequestedOrientation(0);
    }

    public void requestSensorPortraitOrientation() {
        setRequestedOrientation(7);
    }

    public void requestSensorLandscapeOrientation() {
        setRequestedOrientation(6);
    }

    public int getOrientation() {
        return this.activity.getResources().getConfiguration().orientation;
    }

    public void requestFullScreen(boolean z) {
        if (this.activity == null) {
            return;
        }
        if (z) {
            this.activity.getWindow().addFlags(1024);
            this.activity.getWindow().clearFlags(2048);
        } else {
            this.activity.getWindow().addFlags(2048);
            this.activity.getWindow().clearFlags(1024);
        }
        this.activity.getWindow().getDecorView().requestLayout();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void requestPermissions(String[] strArr, int i) {
        if (this.activity != null && DH.SyncMtd.getOSVersionIntForFly() >= 23) {
            ReflectHelper.invokeInstanceMethod(this.activity, m.a("018Gfl<hMfgfiMh9hkWkVinUh=flfhfkhkhkfkfmLgXhk"), new Object[]{strArr, Integer.valueOf(i)}, new Class[]{String.class, Integer.TYPE}, null);
        }
    }

    public void beforeStartActivityForResult(Intent intent, int i, Bundle bundle) {
    }
}