package com.anjoyo.xyl.run.hookgps;

import com.google.android.gms.common.GooglePlayServicesUtil;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedHelpers.ClassNotFoundError;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ax {
    private LoadPackageParam a;
    private String b;

    public ax(String str, LoadPackageParam loadPackageParam) {
        this.a = loadPackageParam;
        this.b = str;
    }

    public void a() {
        if (!this.b.contains(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE)) {
            try {
                XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", this.a.classLoader, "getInstalledApplications", new Object[]{Integer.TYPE, new ay(this)});
                XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", this.a.classLoader, "getInstalledPackages", new Object[]{Integer.TYPE, new az(this)});
            } catch (ClassNotFoundError e) {
                dn.d(this.b + " ClassNotFoundError");
            } catch (NoSuchMethodError e2) {
                dn.d(this.b + " NoSuchMethodError");
            }
            XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", this.a.classLoader, "getPackageInfo", new Object[]{String.class, Integer.TYPE, new ba(this)});
        }
    }
}
