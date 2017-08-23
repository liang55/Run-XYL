package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bm extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bm(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.s.x > 0.0f) {
            methodHookParam.setResult(Float.valueOf(this.a.s.x + (MainHook.r.nextFloat() * 0.01f)));
        }
    }
}
