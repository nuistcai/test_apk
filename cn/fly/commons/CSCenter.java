package cn.fly.commons;

import android.content.pm.PackageInfo;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import cn.fly.FlyCustomController;
import cn.fly.tools.FlyLog;
import cn.fly.tools.proguard.PublicMemberKeeper;
import java.util.List;

/* loaded from: classes.dex */
public class CSCenter implements PublicMemberKeeper {
    private static volatile CSCenter a;
    private volatile FlyCustomController b;
    private a c = new a();

    protected CSCenter() {
    }

    public static CSCenter getInstance() {
        if (a == null) {
            synchronized (CSCenter.class) {
                if (a == null) {
                    a = new CSCenter();
                }
            }
        }
        return a;
    }

    public void updateCustomController(FlyCustomController flyCustomController) {
        this.b = flyCustomController;
    }

    public boolean isCusControllerNotNull() {
        return this.b != null;
    }

    public a invocationRecord() {
        return this.c;
    }

    public boolean isLocationDataEnable() {
        if (this.b != null) {
            try {
                return this.b.isLocationDataEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isAndroidIdEnable() {
        if (this.b != null) {
            try {
                return this.b.isAndroidIdEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isOaidEnable() {
        if (this.b != null) {
            try {
                return this.b.isOaidEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isAdvertisingIdEnable() {
        if (this.b != null) {
            try {
                return this.b.isAdvertisingIdEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isWifiDataEnable() {
        if (this.b != null) {
            try {
                return this.b.isWifiDataEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isCellLocationDataEnable() {
        if (this.b != null) {
            try {
                return this.b.isCellLocationDataEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isAppListDataEnable() {
        if (this.b != null) {
            try {
                return this.b.isAppListDataEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isIpAddressEnable() {
        if (this.b != null) {
            try {
                return this.b.isIpAddressEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isPhoneStateDataEnable() {
        if (this.b != null) {
            try {
                return this.b.isPhoneStateDataEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isSocietyPlatformDataEnable() {
        if (this.b != null) {
            try {
                return this.b.isSocietyPlatformDataEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isConfigEnable() {
        if (this.b != null) {
            try {
                return this.b.isConfigEnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isDREnable() {
        if (this.b != null) {
            try {
                return this.b.isDREnable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isManufacturerAvailable() {
        if (this.b != null) {
            try {
                return this.b.isManufacturerAvailable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isModelAvailable() {
        if (this.b != null) {
            try {
                return this.b.isModelAvailable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public boolean isSystemInfoAvailable() {
        if (this.b != null) {
            try {
                return this.b.isSystemInfoAvailable();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return true;
            }
        }
        return true;
    }

    public Location getLocation() {
        if (this.b != null) {
            try {
                return this.b.getLocation();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public String getAndroidId() {
        if (this.b != null) {
            try {
                return this.b.getAndroidId();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public String getOaid() {
        if (this.b == null) {
            return null;
        }
        try {
            this.c.b = true;
            return this.b.getOaid();
        } catch (Throwable th) {
            FlyLog.getInstance().d(th);
            return null;
        }
    }

    public String getAdvertisingId() {
        if (this.b != null) {
            try {
                return this.b.getAdvertisingId();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public WifiInfo getConnectionInfo() {
        if (this.b != null) {
            try {
                return this.b.getConnectionInfo();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public List<ScanResult> getWifiScanResults() {
        if (this.b != null) {
            try {
                return this.b.getWifiScanResults();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public CellLocation getCellLocation() {
        if (this.b != null) {
            try {
                return this.b.getCellLocation();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public List<CellInfo> getAllCellInfo() {
        if (this.b != null) {
            try {
                return this.b.getAllCellInfo();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public List<NeighboringCellInfo> getNeighboringCellInfo() {
        if (this.b != null) {
            try {
                return this.b.getNeighboringCellInfo();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public List<PackageInfo> getPackageInfos() {
        if (this.b != null) {
            try {
                return this.b.getPackageInfos();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public String getIpAddress() {
        if (this.b != null) {
            try {
                return this.b.getIpAddress();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public String getCellIpv4() {
        if (this.b != null) {
            try {
                return this.b.getCellIpv4();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public String getCellIpv6() {
        if (this.b != null) {
            try {
                return this.b.getCellIpv6();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public int getActiveSubscriptionInfoCount() {
        if (this.b != null) {
            try {
                return this.b.getActiveSubscriptionInfoCount();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return -1;
            }
        }
        return -1;
    }

    public List<SubscriptionInfo> getActiveSubscriptionInfoList() {
        if (this.b != null) {
            try {
                return this.b.getActiveSubscriptionInfoList();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public String getSimOperatorName() {
        if (this.b != null) {
            try {
                return this.b.getSimOperatorName();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public String getSimOperator() {
        if (this.b != null) {
            try {
                return this.b.getSimOperator();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public int getNetworkType() {
        if (this.b != null) {
            try {
                return this.b.getNetworkType();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return -1;
            }
        }
        return -1;
    }

    public ServiceState getServiceState() {
        if (this.b != null) {
            try {
                return this.b.getServiceState();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return null;
            }
        }
        return null;
    }

    public class a {
        private boolean b = false;

        public a() {
        }

        public boolean a() {
            return this.b;
        }
    }

    public String getManufacturer() {
        if (this.b != null) {
            try {
                return this.b.getManufacturer();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return "";
            }
        }
        return "";
    }

    public String getBrand() {
        if (this.b != null) {
            try {
                return this.b.getBrand();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return "";
            }
        }
        return "";
    }

    public String getModel() {
        if (this.b != null) {
            try {
                return this.b.getModel();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return "";
            }
        }
        return "";
    }

    public String getSystemVersionName() {
        if (this.b != null) {
            try {
                return this.b.getSystemVersionName();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return Build.VERSION.RELEASE;
    }

    public int getSystemVersionCode() {
        if (this.b != null) {
            try {
                return this.b.getSystemVersionCode();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
            }
        }
        return Build.VERSION.SDK_INT;
    }

    public String getROMName() {
        if (this.b != null) {
            try {
                return this.b.getROMName();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return "";
            }
        }
        return "";
    }

    public String getROMVersion() {
        if (this.b != null) {
            try {
                return this.b.getROMVersion();
            } catch (Throwable th) {
                FlyLog.getInstance().d(th);
                return "";
            }
        }
        return "";
    }
}