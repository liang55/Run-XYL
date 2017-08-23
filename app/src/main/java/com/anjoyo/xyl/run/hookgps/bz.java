package com.anjoyo.xyl.run.hookgps;

import android.location.GpsStatus.Listener;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedHelpers;

class bz extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bz(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        Listener listener = (Listener) methodHookParam.args[0];
        if (listener != null) {
            al alVar = new al(listener, this.a.pkg);
            XposedHelpers.callMethod(listener, "onGpsStatusChanged", new Object[]{Integer.valueOf(1)});
            XposedHelpers.callMethod(listener, "onGpsStatusChanged", new Object[]{Integer.valueOf(3)});
            alVar.a();
            this.a.lGpsl.add(alVar);
        }
        if (this.a.s.r) {
            methodHookParam.setResult(Boolean.valueOf(true));
        }
    }
}
