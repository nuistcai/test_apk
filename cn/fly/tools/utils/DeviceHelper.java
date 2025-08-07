package cn.fly.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.view.View;
import cn.fly.commons.C0041r;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class DeviceHelper implements PublicMemberKeeper {
    private static DeviceHelper b = new DeviceHelper();
    protected Context a;

    public static synchronized DeviceHelper getInstance(Context context) {
        if (b.a == null && context != null) {
            b.a = context.getApplicationContext();
        }
        return b;
    }

    public boolean isRooted() {
        return cn.fly.tools.b.c.a(this.a).d().a();
    }

    public String getSSID() {
        return cn.fly.tools.b.c.a(this.a).d().a(false);
    }

    public String getBssid() {
        return cn.fly.tools.b.c.a(this.a).d().b(false);
    }

    public String getModel() {
        return cn.fly.tools.b.c.a(this.a).d().m();
    }

    public String getManufacturer() {
        return cn.fly.tools.b.c.a(this.a).d().o();
    }

    public String getDeviceId() {
        return null;
    }

    public String getIMEI() {
        return null;
    }

    public String[] queryIMEI() {
        return null;
    }

    public String getSystemProperties(String str) {
        return cn.fly.tools.b.c.a(this.a).d().a(str);
    }

    public String getSerialno() {
        return null;
    }

    public int getOSVersionInt() {
        return cn.fly.tools.b.c.a(this.a).d().w();
    }

    public String getOSVersionName() {
        return cn.fly.tools.b.c.a(this.a).d().y();
    }

    public String getOSLanguage() {
        return cn.fly.tools.b.c.a(this.a).d().A();
    }

    public String getAppLanguage() {
        return cn.fly.tools.b.c.a(this.a).d().H();
    }

    public String getOSCountry() {
        return cn.fly.tools.b.c.a(this.a).d().B();
    }

    public String getScreenSize() {
        return cn.fly.tools.b.c.a(this.a).d().I();
    }

    public String getCarrier() {
        return getCarrier(false);
    }

    public String getCarrier(boolean z) {
        return cn.fly.tools.b.c.a(this.a).d().c(z);
    }

    public String getCarrierName() {
        return getCarrierName(false);
    }

    public String getCarrierName(boolean z) {
        return cn.fly.tools.b.c.a(this.a).d().e(z);
    }

    public String getSimSerialNumber() {
        return null;
    }

    public String getSignMD5() {
        return cn.fly.tools.b.c.a(this.a).d().Y();
    }

    public String getSignMD5(String str) {
        return cn.fly.tools.b.c.a(this.a).d().c(str);
    }

    public String getNetworkType() {
        return getNetworkType(false);
    }

    public String getNetworkType(boolean z) {
        return cn.fly.tools.b.c.a(this.a).d().h(z);
    }

    public boolean checkNetworkAvailable() {
        return cn.fly.tools.b.c.a(this.a).d().j(false);
    }

    public String getNetworkTypeForStatic() {
        return cn.fly.tools.b.c.a(this.a).d().J();
    }

    public String getDetailNetworkTypeForStatic() {
        return cn.fly.tools.b.c.a(this.a).d().K();
    }

    public int getPlatformCode() {
        return 1;
    }

    public String getDeviceKey() {
        return cn.fly.tools.b.c.a(this.a).d().W();
    }

    public String getDeviceKey(boolean z) {
        return cn.fly.tools.b.c.a(this.a).d().k(z);
    }

    public String getPackageName() {
        return cn.fly.tools.b.c.a(this.a).d().Z();
    }

    public String getAppName() {
        return cn.fly.tools.b.c.a(this.a).d().aa();
    }

    public String getAppName(String str) {
        return cn.fly.tools.b.c.a(this.a).d().d(str);
    }

    public int getAppVersion() {
        return cn.fly.tools.b.c.a(this.a).d().ab();
    }

    public String getAppVersionName() {
        return cn.fly.tools.b.c.a(this.a).d().ac();
    }

    public ArrayList<HashMap<String, String>> getIA(boolean z) {
        return cn.fly.tools.b.c.a(this.a).d().a(z, false);
    }

    public ArrayList<HashMap<String, String>> getSA() {
        return cn.fly.tools.b.c.a(this.a).d().V();
    }

    public boolean checkPermission(String str) {
        return cn.fly.tools.b.c.a(this.a).d().e(str);
    }

    public boolean getSdcardState() {
        return false;
    }

    public String getSdcardPath() {
        return cn.fly.tools.b.c.a(this.a).d().X();
    }

    public String getAdvertisingID() throws Throwable {
        return cn.fly.tools.b.c.a(this.a).d().j();
    }

    public String getIMSI() {
        return null;
    }

    public String[] queryIMSI() {
        return null;
    }

    public Location getLocation(int i, int i2, boolean z) {
        return cn.fly.tools.b.c.a(this.a).d().a(i, i2, z);
    }

    public ArrayList<HashMap<String, Object>> getNeighboringCellInfo() {
        return cn.fly.tools.b.c.a(this.a).d().u();
    }

    public String getDeviceType() {
        return cn.fly.tools.b.c.a(this.a).d().s();
    }

    public Activity getTopActivity() {
        return null;
    }

    public HashMap<String, Object> getCurrentWifiInfo() {
        return cn.fly.tools.b.c.a(this.a).d().v();
    }

    public boolean isPackageInstalled(String str) {
        return cn.fly.tools.b.c.a(this.a).d().b(str);
    }

    public HashMap<String, Object> getCPUInfo() {
        return cn.fly.tools.b.c.a(this.a).d().C();
    }

    public ArrayList<ArrayList<String>> getTTYDriversInfo() {
        return cn.fly.tools.b.c.a(this.a).d().D();
    }

    public String getQemuKernel() {
        return cn.fly.tools.b.c.a(this.a).d().E();
    }

    public HashMap<String, HashMap<String, Long>> getSizeInfo() {
        return cn.fly.tools.b.c.a(this.a).d().F();
    }

    public HashMap<String, Long> getMemoryInfo() {
        return cn.fly.tools.b.c.a(this.a).d().G();
    }

    public String getMIUIVersion() {
        return cn.fly.tools.b.c.a(this.a).d().k();
    }

    public boolean cx() {
        return cn.fly.tools.b.c.a(this.a).d().b();
    }

    public boolean checkPad() {
        return cn.fly.tools.b.c.a(this.a).d().c();
    }

    public boolean usbEnable() {
        return cn.fly.tools.b.c.a(this.a).d().h();
    }

    public boolean devEnable() {
        return cn.fly.tools.b.c.a(this.a).d().g();
    }

    public boolean checkUA() {
        return cn.fly.tools.b.c.a(this.a).d().f();
    }

    public boolean vpn() {
        return cn.fly.tools.b.c.a(this.a).d().e();
    }

    public boolean debugable() {
        return cn.fly.tools.b.c.a(this.a).d().d();
    }

    public boolean isWifiProxy() {
        return cn.fly.tools.b.c.a(this.a).d().i();
    }

    public String getTimezone() {
        return cn.fly.tools.b.c.a(this.a).d().N();
    }

    public String getFlavor() {
        return cn.fly.tools.b.c.a(this.a).d().O();
    }

    public String getBaseband() {
        return cn.fly.tools.b.c.a(this.a).d().P();
    }

    public String getBoardFromSysProperty() {
        return cn.fly.tools.b.c.a(this.a).d().Q();
    }

    public String getBoardPlatform() {
        return cn.fly.tools.b.c.a(this.a).d().R();
    }

    public int getDataNtType() {
        return cn.fly.tools.b.c.a(this.a).d().L();
    }

    public String getDefaultIMPkg() {
        return null;
    }

    public String getBrand() {
        return cn.fly.tools.b.c.a(this.a).d().q();
    }

    public boolean isInMainProcess() {
        return cn.fly.tools.b.c.a(this.a).d().ad();
    }

    public String getCurrentProcessName() {
        return cn.fly.tools.b.c.a(this.a).d().ae();
    }

    public Object getSystemServiceSafe(String str) {
        return C0041r.d(str);
    }

    public <T> T invokeInstanceMethod(Object obj, String str, Object... objArr) {
        return (T) ReflectHelper.invokeInstanceMethodNoThrow(obj, str, null, objArr);
    }

    public <T> T invokeInstanceMethod(Object obj, String str, Object[] objArr, Class<?>[] clsArr) {
        return (T) ReflectHelper.invokeInstanceMethod(obj, str, objArr, clsArr, null);
    }

    public Context getApplication() {
        return cn.fly.tools.b.c.a(this.a).d().ag();
    }

    public List<ResolveInfo> queryIntentServices(Intent intent, int i) {
        return cn.fly.tools.b.c.a(this.a).d().a(intent, i);
    }

    public ResolveInfo resolveActivity(Intent intent, int i) {
        return cn.fly.tools.b.c.a(this.a).d().b(intent, i);
    }

    public PackageInfo getPInfo(String str, int i) {
        return cn.fly.tools.b.c.a(this.a).d().a(false, 0, str, i);
    }

    public PackageInfo getPInfo(boolean z, String str, int i) {
        return cn.fly.tools.b.c.a(this.a).d().a(z, 0, str, i);
    }

    public PackageInfo getPInfo(int i, String str, int i2) {
        return cn.fly.tools.b.c.a(this.a).d().a(false, i, str, i2);
    }

    public String getIPAddress() {
        return cn.fly.tools.b.c.a(this.a).d().S();
    }

    public void hideSoftInput(View view) {
        C0041r.a(view);
    }

    public void showSoftInput(View view) {
        C0041r.b(view);
    }

    public String getDeviceData() {
        return cn.fly.tools.b.c.a(this.a).d().ah();
    }

    public String getDeviceDataNotAES() {
        return cn.fly.tools.b.c.a(this.a).d().ai();
    }

    public String Base64AES(String str, String str2) {
        return Data.Base64AES(str, str2);
    }

    public static Object currentActivityThread() {
        return C0041r.b();
    }

    public long getAppLastUpdateTime() {
        return cn.fly.tools.b.c.a(this.a).d().aj();
    }

    public String getDeviceName() {
        return cn.fly.tools.b.c.a(this.a).d().ak();
    }

    public String getCgroup() {
        return cn.fly.tools.b.c.a(this.a).d().al();
    }

    public String getCInfo() {
        return cn.fly.tools.b.c.a(this.a).d().am();
    }

    public String getOD() {
        return cn.fly.tools.b.c.a(this.a).d().an();
    }

    public String getODH() {
        return cn.fly.tools.b.c.a(this.a).d().ao();
    }

    public HashMap<String, Object> getALLD() {
        return cn.fly.tools.b.c.a(this.a).d().ap();
    }

    public ApplicationInfo getAInfo() {
        return cn.fly.tools.b.c.a(this.a).d().aq();
    }

    public ArrayList<HashMap<String, Object>> getAvailableWifiListOneKey() {
        return cn.fly.tools.b.c.a(this.a).d().ar();
    }

    public ApplicationInfo getAInfo(String str, int i) {
        return cn.fly.tools.b.c.a(this.a).d().a(str, i);
    }

    public ApplicationInfo getAInfo(boolean z, String str, int i) {
        return cn.fly.tools.b.c.a(this.a).d().a(z, str, i);
    }
}