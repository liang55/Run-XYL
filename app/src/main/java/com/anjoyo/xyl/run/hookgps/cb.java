package com.anjoyo.xyl.run.hookgps;

import android.location.GpsStatus.NmeaListener;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class cb extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    cb(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        NmeaListener nmeaListener = (NmeaListener) methodHookParam.args[0];
        if (nmeaListener != null) {
            ao aoVar = new ao(nmeaListener, this.a.pkg);
            aoVar.a();
            this.a.lGpsn.add(aoVar);
        }
        if (this.a.s.r) {
            methodHookParam.setResult(Boolean.valueOf(true));
        }
    }
}
