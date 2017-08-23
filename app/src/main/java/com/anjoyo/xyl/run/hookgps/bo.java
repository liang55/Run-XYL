package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bo extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bo(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.s.s > 0.0d) {
            methodHookParam.setResult(Double.valueOf(this.a.s.s + (MainHook.r.nextDouble() * 0.01d)));
        }
    }
}
