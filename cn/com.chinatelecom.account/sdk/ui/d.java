package cn.com.chinatelecom.account.sdk.ui;

import android.os.Environment;
import android.text.TextUtils;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class d {
    private static final a a = b();

    public enum a {
        MIUI,
        Flyme,
        EMUI,
        ColorOS,
        FuntouchOS,
        SmartisanOS,
        EUI,
        Sense,
        AmigoOS,
        _360OS,
        NubiaUI,
        H2OS,
        YunOS,
        YuLong,
        SamSung,
        Sony,
        Lenovo,
        LG,
        Google,
        Other;

        private int u = -1;
        private String v;

        a() {
        }

        void a(int i) {
            this.u = i;
        }

        void a(String str) {
            this.v = str;
        }
    }

    public static a a() {
        return a;
    }

    /* JADX WARN: Removed duplicated region for block: B:153:0x0209  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:295:0x0426 -> B:352:0x043b). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static a b() throws Throwable {
        Properties properties;
        FileInputStream fileInputStream;
        char c;
        a aVar = a.Other;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                try {
                    properties = new Properties();
                    fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                    try {
                        properties.load(fileInputStream);
                        c = 1;
                    } catch (IOException e) {
                        e = e;
                        fileInputStream2 = fileInputStream;
                        e.printStackTrace();
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        return aVar;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        if (!properties.containsKey("ro.miui.ui.version.name") && !properties.containsKey("ro.miui.ui.version.code")) {
            if (properties.containsKey("ro.build.version.emui") || properties.containsKey("ro.build.hw_emui_api_level") || properties.containsKey("ro.confg.hw_systemversion")) {
                aVar = a.EMUI;
                if (properties.containsKey("ro.build.version.emui")) {
                    String property = properties.getProperty("ro.build.version.emui");
                    Matcher matcher = Pattern.compile("EmotionUI_([\\d.]+)").matcher(property);
                    if (!TextUtils.isEmpty(property) && matcher.find()) {
                        try {
                            String strGroup = matcher.group(1);
                            aVar.a(strGroup);
                            aVar.a(Integer.parseInt(strGroup.split("\\.")[0]));
                        } catch (Exception e5) {
                            e = e5;
                            e.printStackTrace();
                            fileInputStream.close();
                            return aVar;
                        }
                    }
                }
            } else if (properties.containsKey("ro.meizu.setupwizard.flyme") || properties.containsKey("ro.flyme.published")) {
                aVar = a.Flyme;
                if (properties.containsKey("ro.build.display.id")) {
                    String property2 = properties.getProperty("ro.build.display.id");
                    Matcher matcher2 = Pattern.compile("Flyme[^\\d]*([\\d.]+)[^\\d]*").matcher(property2);
                    if (!TextUtils.isEmpty(property2) && matcher2.find()) {
                        try {
                            String strGroup2 = matcher2.group(1);
                            aVar.a(strGroup2);
                            aVar.a(Integer.parseInt(strGroup2.split("\\.")[0]));
                        } catch (Exception e6) {
                            e = e6;
                            e.printStackTrace();
                            fileInputStream.close();
                            return aVar;
                        }
                    }
                }
            } else if (properties.containsKey("ro.oppo.theme.version") || properties.containsKey("ro.oppo.version") || properties.containsKey("ro.rom.different.version")) {
                aVar = a.ColorOS;
                if (properties.containsKey("ro.rom.different.version")) {
                    String property3 = properties.getProperty("ro.rom.different.version");
                    Matcher matcher3 = Pattern.compile("ColorOS([\\d.]+)").matcher(property3);
                    if (!TextUtils.isEmpty(property3) && matcher3.find()) {
                        try {
                            String strGroup3 = matcher3.group(1);
                            aVar.a(strGroup3);
                            aVar.a(Integer.parseInt(strGroup3.split("\\.")[0]));
                        } catch (Exception e7) {
                            e = e7;
                            e.printStackTrace();
                            fileInputStream.close();
                            return aVar;
                        }
                    }
                }
            } else if (properties.containsKey("ro.vivo.os.name") || properties.containsKey("ro.vivo.os.version") || properties.containsKey("ro.vivo.os.build.display.id")) {
                aVar = a.FuntouchOS;
                if (properties.containsKey("ro.vivo.os.version")) {
                    String property4 = properties.getProperty("ro.vivo.os.version");
                    if (!TextUtils.isEmpty(property4) && property4.matches("[\\d.]+")) {
                        try {
                            aVar.a(property4);
                            aVar.a(Integer.parseInt(property4.split("\\.")[0]));
                        } catch (Exception e8) {
                            e = e8;
                            e.printStackTrace();
                            fileInputStream.close();
                            return aVar;
                        }
                    }
                }
            } else if (properties.containsKey("ro.letv.release.version") || properties.containsKey("ro.product.letv_name") || properties.containsKey("ro.product.letv_model")) {
                aVar = a.EUI;
                if (properties.containsKey("ro.letv.release.version")) {
                    String property5 = properties.getProperty("ro.letv.release.version");
                    Matcher matcher4 = Pattern.compile("([\\d.]+)[^\\d]*").matcher(property5);
                    if (!TextUtils.isEmpty(property5) && matcher4.find()) {
                        try {
                            String strGroup4 = matcher4.group(1);
                            aVar.a(strGroup4);
                            aVar.a(Integer.parseInt(strGroup4.split("\\.")[0]));
                        } catch (Exception e9) {
                            e = e9;
                            e.printStackTrace();
                            fileInputStream.close();
                            return aVar;
                        }
                    }
                }
            } else if (properties.containsKey("ro.gn.gnromvernumber") || properties.containsKey("ro.gn.amigo.systemui.support")) {
                aVar = a.AmigoOS;
                if (properties.containsKey("ro.build.display.id")) {
                    String property6 = properties.getProperty("ro.build.display.id");
                    Matcher matcher5 = Pattern.compile("amigo([\\d.]+)[a-zA-Z]*").matcher(property6);
                    if (!TextUtils.isEmpty(property6) && matcher5.find()) {
                        try {
                            String strGroup5 = matcher5.group(1);
                            aVar.a(strGroup5);
                            aVar.a(Integer.parseInt(strGroup5.split("\\.")[0]));
                        } catch (Exception e10) {
                            e = e10;
                            e.printStackTrace();
                            fileInputStream.close();
                            return aVar;
                        }
                    }
                }
            } else if (properties.containsKey("ro.sony.irremote.protocol_type") || properties.containsKey("ro.sony.fota.encrypteddata")) {
                aVar = a.Sony;
            } else if (properties.containsKey("ro.yulong.version.release") || properties.containsKey("ro.yulong.version.tag")) {
                aVar = a.YuLong;
            } else if (properties.containsKey("htc.build.stage") || properties.containsKey("ro.htc.bluetooth.sap")) {
                aVar = a.Sense;
            } else if (properties.containsKey("ro.lge.swversion") || properties.containsKey("ro.lge.swversion_short") || properties.containsKey("ro.lge.factoryversion")) {
                aVar = a.LG;
            } else if (properties.containsKey("ro.lenovo.device") || properties.containsKey("ro.lenovo.platform") || properties.containsKey("ro.lenovo.adb")) {
                aVar = a.Lenovo;
            } else if (properties.containsKey("ro.build.display.id")) {
                String property7 = properties.getProperty("ro.build.display.id");
                if (!TextUtils.isEmpty(property7)) {
                    if (property7.contains("Flyme")) {
                        a aVar2 = a.Flyme;
                        try {
                            fileInputStream.close();
                        } catch (IOException e11) {
                            e11.printStackTrace();
                        }
                        return aVar2;
                    }
                    if (property7.contains("amigo")) {
                        a aVar3 = a.AmigoOS;
                        try {
                            fileInputStream.close();
                        } catch (IOException e12) {
                            e12.printStackTrace();
                        }
                        return aVar3;
                    }
                }
            } else if (properties.containsKey("ro.build.version.base_os")) {
                String property8 = properties.getProperty("ro.build.version.base_os");
                if (!TextUtils.isEmpty(property8)) {
                    if (property8.contains("OPPO")) {
                        a aVar4 = a.ColorOS;
                        try {
                            fileInputStream.close();
                        } catch (IOException e13) {
                            e13.printStackTrace();
                        }
                        return aVar4;
                    }
                    if (property8.contains(LeakCanaryInternals.SAMSUNG)) {
                        a aVar5 = a.SamSung;
                        try {
                            fileInputStream.close();
                        } catch (IOException e14) {
                            e14.printStackTrace();
                        }
                        return aVar5;
                    }
                }
            } else if (properties.containsKey("ro.com.google.clientidbase")) {
                String property9 = properties.getProperty("ro.com.google.clientidbase");
                switch (property9.hashCode()) {
                    case -1297558593:
                        if (!property9.equals("android-gionee")) {
                            c = 65535;
                            break;
                        } else {
                            c = '\b';
                            break;
                        }
                    case -1158135215:
                        if (property9.equals("android-lenovo")) {
                            c = 7;
                            break;
                        }
                        break;
                    case -1037975490:
                        if (property9.equals("android-oppo")) {
                            break;
                        }
                        break;
                    case -1037773494:
                        if (property9.equals("android-vivo")) {
                            c = 2;
                            break;
                        }
                        break;
                    case -811278887:
                        if (property9.equals("android-xiaomi")) {
                            c = 0;
                            break;
                        }
                        break;
                    case -652932276:
                        if (property9.equals("android-coolpad")) {
                            c = 5;
                            break;
                        }
                        break;
                    case -380192433:
                        if (property9.equals("android-htc-rev")) {
                            c = 6;
                            break;
                        }
                        break;
                    case -64814069:
                        if (property9.equals("android-sonyericsson")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 259783324:
                        if (property9.equals("android-samsung")) {
                            c = 3;
                            break;
                        }
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        a aVar6 = a.MIUI;
                        try {
                            fileInputStream.close();
                        } catch (IOException e15) {
                            e15.printStackTrace();
                        }
                        return aVar6;
                    case 1:
                        a aVar7 = a.ColorOS;
                        try {
                            fileInputStream.close();
                        } catch (IOException e16) {
                            e16.printStackTrace();
                        }
                        return aVar7;
                    case 2:
                        a aVar8 = a.FuntouchOS;
                        try {
                            fileInputStream.close();
                        } catch (IOException e17) {
                            e17.printStackTrace();
                        }
                        return aVar8;
                    case 3:
                        a aVar9 = a.SamSung;
                        try {
                            fileInputStream.close();
                        } catch (IOException e18) {
                            e18.printStackTrace();
                        }
                        return aVar9;
                    case 4:
                        a aVar10 = a.Sony;
                        try {
                            fileInputStream.close();
                        } catch (IOException e19) {
                            e19.printStackTrace();
                        }
                        return aVar10;
                    case 5:
                        a aVar11 = a.YuLong;
                        try {
                            fileInputStream.close();
                        } catch (IOException e20) {
                            e20.printStackTrace();
                        }
                        return aVar11;
                    case 6:
                        a aVar12 = a.Sense;
                        try {
                            fileInputStream.close();
                        } catch (IOException e21) {
                            e21.printStackTrace();
                        }
                        return aVar12;
                    case 7:
                        a aVar13 = a.Lenovo;
                        try {
                            fileInputStream.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                        return aVar13;
                    case '\b':
                        a aVar14 = a.AmigoOS;
                        try {
                            fileInputStream.close();
                        } catch (IOException e23) {
                            e23.printStackTrace();
                        }
                        return aVar14;
                }
            }
            return aVar;
        }
        aVar = a.MIUI;
        if (properties.containsKey("ro.miui.ui.version.name")) {
            String property10 = properties.getProperty("ro.miui.ui.version.name");
            if (!TextUtils.isEmpty(property10) && property10.matches("[Vv]\\d+")) {
                try {
                    aVar.a(Integer.parseInt(property10.split("[Vv]")[1]));
                } catch (Exception e24) {
                    e24.printStackTrace();
                }
            }
        }
        if (properties.containsKey("ro.build.version.incremental")) {
            String property11 = properties.getProperty("ro.build.version.incremental");
            if (!TextUtils.isEmpty(property11) && property11.matches("[\\d.]+")) {
                aVar.a(property11);
            }
        }
        fileInputStream.close();
        return aVar;
    }
}