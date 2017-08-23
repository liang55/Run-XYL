package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class k {
    private String a;
    private LoadPackageParam b;
    public doClass c;

    public k(String arg1, LoadPackageParam arg2, doClass arg3) {
        super();
        this.a = arg1;
        this.b = arg2;
        this.c = arg3;
    }

    public void a() {
        Object[] v3;
        ClassLoader v1;
        int v7 = 2;
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", this.b.classLoader, "getSimState", new Object[]{XC_MethodReplacement.returnConstant(Integer.valueOf(5))});
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", this.b.classLoader, "isConnected", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", this.b.classLoader, "isAvailable", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
        XposedHelpers.findAndHookMethod("android.telephony.CellInfo", this.b.classLoader, "isRegistered", new Object[]{XC_MethodReplacement.returnConstant(Boolean.valueOf(true))});
        if(MainHook.api > 22) {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", this.b.classLoader, "getPhoneCount", new Object[]{XC_MethodReplacement.returnConstant(Integer.valueOf(1))});
        }

        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", this.b.classLoader, "getTypeName", new Object[]{new l(this)});
        XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", this.b.classLoader, "getDataState", new Object[]{new w(this)});
        XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", this.b.classLoader, "getWifiState", new Object[]{new aa(this)});
        XposedHelpers.findAndHookMethod("android.net.wifi.WifiManager", this.b.classLoader, "isWifiEnabled", new Object[]{new ab(this)});
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo", this.b.classLoader, "getType", new Object[]{new ac(this)});
        if(this.c.i.equals("1")) {
            v1 = this.b.classLoader;
            v3 = new Object[3];
            v3[0] = Object.class;
            v3[1] = Object[].class;
            v3[v7] = new ad(this);
            XposedHelpers.findAndHookMethod("java.lang.reflect.Method", v1, "invoke", v3);
        }

        if(this.c.i.equals("2")) {
            XposedHelpers.findAndHookMethod("java.lang.Object", this.b.classLoader, "getClass", new Object[]{new ae(this)});
        }

        XposedHelpers.findAndHookMethod("android.telephony.gsm.GsmCellLocation", this.b.classLoader, "getLac", new Object[]{new af(this)});
        XposedHelpers.findAndHookMethod("android.telephony.gsm.GsmCellLocation", this.b.classLoader, "getCid", new Object[]{new ag(this)});
        XposedHelpers.findAndHookMethod("android.telephony.cdma.CdmaCellLocation", this.b.classLoader, "getNetworkId", new Object[]{new m(this)});
        XposedHelpers.findAndHookMethod("android.telephony.cdma.CdmaCellLocation", this.b.classLoader, "getBaseStationId", new Object[]{new n(this)});
        XposedHelpers.findAndHookMethod("android.telephony.cdma.CdmaCellLocation", this.b.classLoader, "getSystemId", new Object[]{new o(this)});
        XposedHelpers.findAndHookMethod("android.telephony.cdma.CdmaCellLocation", this.b.classLoader, "getBaseStationLatitude", new Object[]{new p(this)});
        XposedHelpers.findAndHookMethod("android.telephony.cdma.CdmaCellLocation", this.b.classLoader, "getBaseStationLongitude", new Object[]{new q(this)});
        XposedHelpers.findAndHookMethod("android.app.Application", this.b.classLoader, "onCreate", new Object[]{new r(this)});
        v1 = this.b.classLoader;
        v3 = new Object[v7];
        v3[0] = String.class;
        v3[1] = new s(this);
        XposedHelpers.findAndHookMethod("android.location.LocationManager", v1, "getLastKnownLocation", v3);
        if(MainHook.api > 21) {
            XposedHelpers.findAndHookMethod("android.telephony.SubscriptionManager", this.b.classLoader, "getActiveSubscriptionInfoCountMax", new Object[]{new t(this)});
            XposedHelpers.findAndHookMethod("android.telephony.SubscriptionInfo", this.b.classLoader, "getCarrierName", new Object[]{new u(this)});
            XposedHelpers.findAndHookMethod("android.telephony.SubscriptionInfo", this.b.classLoader, "getCountryIso", new Object[]{new v(this)});
            XposedHelpers.findAndHookMethod("android.telephony.SubscriptionInfo", this.b.classLoader, "getDisplayName", new Object[]{new x(this)});
            XposedHelpers.findAndHookMethod("android.telephony.SubscriptionInfo", this.b.classLoader, "getMcc", new Object[]{new y(this)});
            XposedHelpers.findAndHookMethod("android.telephony.SubscriptionInfo", this.b.classLoader, "getMnc", new Object[]{new z(this)});
        }
    }

    static doClass a(k arg1) {
        return arg1.c;
    }
}
