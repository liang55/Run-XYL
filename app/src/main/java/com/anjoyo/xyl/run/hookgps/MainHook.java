package com.anjoyo.xyl.run.hookgps;

import android.app.AndroidAppHelper;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationListener;
import android.os.Build.VERSION;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.util.ArrayList;
import java.util.Random;

public class MainHook implements IXposedHookLoadPackage {
    public static final int api;
    public static XSharedPreferences pref;
    public static Random r;
    public ArrayList lGps;
    public ArrayList lGpsl;
    public ArrayList lGpsn;
    public String pkg;
    public doClass s;

    static {
        r = new Random(System.currentTimeMillis());
        api = VERSION.SDK_INT;
    }

    public MainHook() {
        this.lGps = new ArrayList();
        this.lGpsl = new ArrayList();
        this.lGpsn = new ArrayList();
    }

    public void handleLoadPackage(LoadPackageParam loadPackageParam) {
        if (loadPackageParam != null) {
            this.pkg = loadPackageParam.packageName;
            if (!loadPackageParam.isFirstApplication) {
                this.pkg = loadPackageParam.processName;
            }
            if (!dn.a(this.pkg, loadPackageParam.appInfo)) {
                pref = new XSharedPreferences(MainHook.class.getPackage().getName());
                this.s = new doClass(this.pkg, pref);
                if (loadPackageParam.packageName.equals("com.anjoyo.xyl")) {
                    XposedHelpers.findAndHookMethod("om.anjoyo.xyl.activity.NaviEmulatorActivity", loadPackageParam.classLoader, "isactive", new Object[]{new XC_MethodHook(){
                        protected void afterHookedMethod(MethodHookParam methodHookParam) {
                            methodHookParam.setResult(Boolean.valueOf(false));
                        }
                    }});
                } else if (!this.pkg.equals("com.anjoyo.xyl") && !this.s.m.equals("") && dn.e(this.s.k)) {
                    this.s.a(AndroidAppHelper.currentApplication());
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getNetworkOperatorName", new Object[]{new bs(this)});
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getSimOperatorName", new Object[]{new cd(this)});
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getSimOperator", new Object[]{new cf(this)});
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getNetworkOperator", new Object[]{new cg(this)});
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getSimCountryIso", new Object[]{new ch(this)});
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getNetworkCountryIso", new Object[]{new ci(this)});
                    if (api < 23) {
                        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getNeighboringCellInfo", new Object[]{new cj(this)});
                    }
                    if (api > 16) {
                        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getAllCellInfo", new Object[]{new ck(this)});
                    }
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getCellLocation", new Object[]{new bi(this)});
                    XposedHelpers.findAndHookMethod("android.location.Location", loadPackageParam.classLoader, "getLatitude", new Object[]{new bj(this)});
                    XposedHelpers.findAndHookMethod("android.location.Location", loadPackageParam.classLoader, "getLongitude", new Object[]{new bk(this)});
                    XposedHelpers.findAndHookMethod("android.location.Location", loadPackageParam.classLoader, "getSpeed", new Object[]{new bl(this)});
                    XposedHelpers.findAndHookMethod("android.location.Location", loadPackageParam.classLoader, "getAccuracy", new Object[]{new bm(this)});
                    XposedHelpers.findAndHookMethod("android.location.Location", loadPackageParam.classLoader, "getBearing", new Object[]{new bn(this)});
                    XposedHelpers.findAndHookMethod("android.location.Location", loadPackageParam.classLoader, "getAltitude", new Object[]{new bo(this)});
                    XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", loadPackageParam.classLoader, "getScanResults", new Object[]{new bp(this)});
                    XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPackageParam.classLoader, "getMacAddress", new Object[]{new bq(this)});
                    XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPackageParam.classLoader, "getSSID", new Object[]{new br(this)});
                    XposedHelpers.findAndHookMethod("android.net.wifi.WifiInfo", loadPackageParam.classLoader, "getBSSID", new Object[]{new bt(this)});
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getNetworkType", new Object[]{new bu(this)});
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getPhoneType", new Object[]{new bv(this)});
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", loadPackageParam.classLoader, "getCurrentPhoneType", new Object[]{new bw(this)});
                    if (this.s.o) {

                        Class classLocationManager=null;
                        try {
                            classLocationManager=  Class.forName("android.location.LocationManager");
                            XposedBridge.hookAllMethods(classLocationManager, "requestLocationUpdates", new bx(this));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        XposedHelpers.findAndHookMethod("android.location.LocationManager", loadPackageParam.classLoader, "removeUpdates", new Object[]{LocationListener.class, new by(this)});
                        XposedHelpers.findAndHookMethod("android.location.LocationManager", loadPackageParam.classLoader, "getGpsStatus", new Object[]{GpsStatus.class, ah.c});
                        if (api < 24) {
                            XposedHelpers.findAndHookMethod("android.location.LocationManager", loadPackageParam.classLoader, "addGpsStatusListener", new Object[]{Listener.class, new bz(this)});
                            XposedHelpers.findAndHookMethod("android.location.LocationManager", loadPackageParam.classLoader, "removeGpsStatusListener", new Object[]{Listener.class, new ca(this)});
                            XposedHelpers.findAndHookMethod("android.location.LocationManager", loadPackageParam.classLoader, "addNmeaListener", new Object[]{NmeaListener.class, new cb(this)});
                            XposedHelpers.findAndHookMethod("android.location.LocationManager", loadPackageParam.classLoader, "removeNmeaListener", new Object[]{NmeaListener.class, new cc(this)});
                        }
                        XposedHelpers.findAndHookMethod("android.location.GpsStatus", loadPackageParam.classLoader, "getTimeToFirstFix", new Object[]{new ce(this)});
                    }
                    new k(this.pkg, loadPackageParam, this.s).a();
                    new ax(this.pkg, loadPackageParam).a();
                }
            }
        }
    }
}
