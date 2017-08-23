package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bj extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bj(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        methodHookParam.setResult(Double.valueOf((this.a.s.t + this.a.s.v) + (MainHook.r.nextDouble() * 5.0E-6d)));
    }
}
