package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bw extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bw(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.s.G != 0) {
            methodHookParam.setResult(Integer.valueOf(this.a.s.G));
        }
    }
}
