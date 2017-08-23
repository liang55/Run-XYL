package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bk extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bk(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        methodHookParam.setResult(Double.valueOf((this.a.s.u + this.a.s.w) + (MainHook.r.nextDouble() * 5.0E-6d)));
    }
}
