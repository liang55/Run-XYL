package com.anjoyo.xyl.run.hookgps;

import android.location.LocationListener;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bx extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bx(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        LocationListener locationListener;
        if (methodHookParam.args.length > 3 && (methodHookParam.args[3] instanceof LocationListener)) {
            locationListener = (LocationListener) methodHookParam.args[3];
            if (locationListener != null) {
                ah ahVar = new ah(locationListener, this.a.pkg, this.a.s);
                ahVar.a();
                this.a.lGps.add(ahVar);
            }
        }
        if (methodHookParam.args.length == 3 && (methodHookParam.args[1] instanceof LocationListener)) {
            locationListener = (LocationListener) methodHookParam.args[1];
            if (locationListener != null) {
                ah ahVar = new ah(locationListener, this.a.pkg, this.a.s);
                ahVar.a();
                this.a.lGps.add(ahVar);
            }
        }
        if (this.a.s.r) {
            methodHookParam.setResult(null);
        }
    }
}
