package com.anjoyo.xyl.run.hookgps;

import android.location.GpsStatus.NmeaListener;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import java.util.Iterator;

class cc extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    cc(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        NmeaListener nmeaListener = (NmeaListener) methodHookParam.args[0];
        Iterator it = this.a.lGpsn.iterator();
        while (it.hasNext()) {
            ao aoVar = (ao) it.next();
            if (aoVar.b && aoVar.a == nmeaListener) {
                aoVar.b();
            }
        }
        if (this.a.s.r) {
            methodHookParam.setResult(null);
        }
    }
}
