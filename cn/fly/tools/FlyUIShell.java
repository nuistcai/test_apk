package cn.fly.tools;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import cn.fly.commons.m;
import cn.fly.tools.utils.DH;
import cn.fly.tools.utils.ReflectHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/* loaded from: classes.dex */
public class FlyUIShell extends Activity {
    private static HashMap<String, FakeActivity> a = new HashMap<>();
    private FakeActivity b;

    static {
        FlyLog.getInstance().d("===============================", new Object[0]);
        FlyLog.getInstance().d(m.a("0090jefmhhhefmfm?i-hkkh") + "2025-07-11".replace("-0", "-").replace("-", "."), new Object[0]);
        FlyLog.getInstance().d("===============================", new Object[0]);
    }

    protected static String a(Object obj) {
        return a(String.valueOf(System.currentTimeMillis()), obj);
    }

    protected static String a(String str, Object obj) {
        a.put(str, (FakeActivity) obj);
        return str;
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i) {
        if (b()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            int i2 = 0;
            while (i2 < stackTrace.length) {
                if (!stackTrace[i2].toString().startsWith(m.a("030FjiBf,ff>f5fnZifg;glfnhe.jCflMhf-fefnglRhk3gn3kfeWgjhefl8feh")) || (i2 = i2 + 2) >= stackTrace.length) {
                    i2++;
                } else {
                    int iOnSetTheme = this.b.onSetTheme(i, stackTrace[i2].toString().startsWith(m.a("048fgHfeflfmfkfefnEfll9fnhfOekAfkfffkKk7gehe7j9flQhf;fefn]lhZflghfmflfhhg@fJfi9gej,hfJek_fkfffk-kGge")));
                    if (iOnSetTheme > 0) {
                        super.setTheme(iOnSetTheme);
                        return;
                    }
                    return;
                }
            }
        }
        super.setTheme(i);
    }

    private boolean b() {
        if (this.b == null) {
            Intent intent = getIntent();
            Uri data = intent.getData();
            if (data != null && m.a("005Kfhfmhhfifk").equals(data.getScheme())) {
                this.b = a(data.getHost());
                if (this.b != null) {
                    FlyLog.getInstance().i("MUIShell found executor: " + this.b.getClass());
                    this.b.setActivity(this);
                    return true;
                }
            }
            try {
                String stringExtra = intent.getStringExtra(m.a("011ifVfiYgej_fj%k4fkfh*h"));
                String stringExtra2 = intent.getStringExtra(m.a("013h@gk8heMfiLk]fmflfjJgfEfh_h"));
                this.b = a.remove(stringExtra);
                if (this.b == null) {
                    this.b = a.remove(intent.getScheme());
                    if (this.b == null) {
                        this.b = a();
                        if (this.b == null) {
                            FlyLog.getInstance().w(new RuntimeException("Executor lost! launchTime = " + stringExtra + ", executorName: " + stringExtra2));
                            return false;
                        }
                    }
                }
                FlyLog.getInstance().i("MUIShell found executor: " + this.b.getClass());
                this.b.setActivity(this);
            } catch (Throwable th) {
                FlyLog.getInstance().w(th);
                return false;
            }
        }
        return true;
    }

    private FakeActivity a(String str) {
        Object objNewInstance;
        try {
            if (!TextUtils.isEmpty(str)) {
                if (str.startsWith(".")) {
                    str = getPackageName() + str;
                }
                String strImportClass = ReflectHelper.importClass(str);
                if (!TextUtils.isEmpty(strImportClass) && (objNewInstance = ReflectHelper.newInstance(strImportClass, new Object[0])) != null && (objNewInstance instanceof FakeActivity)) {
                    return (FakeActivity) objNewInstance;
                }
                return null;
            }
            return null;
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return null;
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws NoSuchFieldException {
        if (b()) {
            FlyLog.getInstance().d(this.b.getClass().getSimpleName() + " onCreate", new Object[0]);
            if (DH.SyncMtd.getOSVersionIntForFly() == 26 && d()) {
                c();
            }
            if (DH.SyncMtd.getOSVersionIntForFly() >= 21) {
                this.b.activity.getWindow().addFlags(Integer.MIN_VALUE);
                if (DH.SyncMtd.getOSVersionIntForFly() < 35) {
                    try {
                        ReflectHelper.invokeInstanceMethod(this.b.activity.getWindow(), "setStatusBarColor", new Object[]{0}, new Class[]{Integer.TYPE});
                    } catch (Throwable th) {
                    }
                }
            }
            super.onCreate(bundle);
            this.b.onCreate();
            return;
        }
        super.onCreate(bundle);
        finish();
    }

    public FakeActivity a() {
        String string;
        try {
            string = getPackageManager().getActivityInfo(getComponentName(), 128).metaData.getString(m.a("015_fe1h$gh>f*fi-ik5hfMek_fkfffkRkLge"));
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            string = null;
        }
        return a(string);
    }

    @Override // android.app.Activity
    public void setContentView(int i) {
        setContentView(LayoutInflater.from(this).inflate(i, (ViewGroup) null));
    }

    @Override // android.app.Activity
    public void setContentView(View view) {
        if (view == null) {
            return;
        }
        super.setContentView(view);
        if (this.b != null) {
            this.b.setContentView(view);
        }
    }

    @Override // android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            return;
        }
        if (layoutParams == null) {
            super.setContentView(view);
        } else {
            super.setContentView(view, layoutParams);
        }
        if (this.b != null) {
            this.b.setContentView(view);
        }
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        if (this.b == null) {
            super.onNewIntent(intent);
        } else {
            this.b.onNewIntent(intent);
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        if (this.b != null) {
            FlyLog.getInstance().d(this.b.getClass().getSimpleName() + " onStart", new Object[0]);
            this.b.onStart();
        }
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onResume() {
        if (this.b != null) {
            FlyLog.getInstance().d(this.b.getClass().getSimpleName() + " onResume", new Object[0]);
            this.b.onResume();
        }
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onPause() {
        if (this.b != null) {
            FlyLog.getInstance().d(this.b.getClass().getSimpleName() + " onPause", new Object[0]);
            this.b.onPause();
        }
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onStop() {
        if (this.b != null) {
            FlyLog.getInstance().d(this.b.getClass().getSimpleName() + " onStop", new Object[0]);
            this.b.onStop();
        }
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        if (this.b != null) {
            FlyLog.getInstance().d(this.b.getClass().getSimpleName() + " onRestart", new Object[0]);
            this.b.onRestart();
        }
        super.onRestart();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.b != null) {
            this.b.sendResult();
            FlyLog.getInstance().d(this.b.getClass().getSimpleName() + " onDestroy", new Object[0]);
            this.b.onDestroy();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (this.b != null) {
            this.b.onActivityResult(i, i2, intent);
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean zOnKeyEvent;
        try {
            if (this.b == null) {
                zOnKeyEvent = false;
            } else {
                zOnKeyEvent = this.b.onKeyEvent(i, keyEvent);
            }
            if (zOnKeyEvent) {
                return true;
            }
            return super.onKeyDown(i, keyEvent);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return false;
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        boolean zOnKeyEvent;
        try {
            if (this.b == null) {
                zOnKeyEvent = false;
            } else {
                zOnKeyEvent = this.b.onKeyEvent(i, keyEvent);
            }
            if (zOnKeyEvent) {
                return true;
            }
            return super.onKeyUp(i, keyEvent);
        } catch (Throwable th) {
            FlyLog.getInstance().w(th);
            return false;
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.b != null) {
            this.b.onConfigurationChanged(configuration);
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (this.b != null) {
            this.b.onRequestPermissionsResult(i, strArr, iArr);
        }
    }

    @Override // android.app.Activity
    public void finish() {
        if (this.b != null && this.b.onFinish()) {
            return;
        }
        super.finish();
    }

    @Override // android.app.Activity
    public void setRequestedOrientation(int i) {
        if (DH.SyncMtd.getOSVersionIntForFly() == 26 && d()) {
            return;
        }
        super.setRequestedOrientation(i);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean zOnCreateOptionsMenu = super.onCreateOptionsMenu(menu);
        if (this.b != null) {
            return this.b.onCreateOptionsMenu(menu);
        }
        return zOnCreateOptionsMenu;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        boolean zOnOptionsItemSelected = super.onOptionsItemSelected(menuItem);
        if (this.b != null) {
            return this.b.onOptionsItemSelected(menuItem);
        }
        return zOnOptionsItemSelected;
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int i) {
        if (this.b != null) {
            this.b.beforeStartActivityForResult(intent, i, null);
        }
        super.startActivityForResult(intent, i);
    }

    @Override // android.app.Activity
    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        if (this.b != null) {
            this.b.beforeStartActivityForResult(intent, i, bundle);
        }
        if (DH.SyncMtd.getOSVersionIntForFly() >= 16) {
            super.startActivityForResult(intent, i, bundle);
        } else {
            super.startActivityForResult(intent, i);
        }
    }

    private boolean c() throws NoSuchFieldException {
        if (DH.SyncMtd.getOSVersionIntForFly() > 27) {
            return false;
        }
        try {
            Field declaredField = Activity.class.getDeclaredField(m.a("013*fhhfPekQfkfffkHk0gegg<gNghfm"));
            declaredField.setAccessible(true);
            ((ActivityInfo) declaredField.get(this)).screenOrientation = -1;
            declaredField.setAccessible(false);
            return true;
        } catch (Exception e) {
            FlyLog.getInstance().w(e, "Fix orientation for 8.0 encountered exception", new Object[0]);
            return false;
        }
    }

    private boolean d() throws NoSuchMethodException, SecurityException {
        Method method;
        boolean zBooleanValue;
        boolean z = false;
        if (DH.SyncMtd.getOSVersionIntForFly() > 27) {
            return false;
        }
        try {
            TypedArray typedArrayObtainStyledAttributes = this.b.activity.obtainStyledAttributes((int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null));
            try {
                method = ActivityInfo.class.getMethod(m.a("023FfkhkheflRfg5hk4i]fi*ehgkYijflie@i'fmJfk6fkFg?gl"), TypedArray.class);
                method.setAccessible(true);
                zBooleanValue = ((Boolean) method.invoke(null, typedArrayObtainStyledAttributes)).booleanValue();
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
        }
        try {
            method.setAccessible(false);
            return zBooleanValue;
        } catch (Exception e3) {
            e = e3;
            z = zBooleanValue;
            FlyLog.getInstance().w(e);
            return z;
        }
    }
}