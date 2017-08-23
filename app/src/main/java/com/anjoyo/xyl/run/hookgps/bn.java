package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bn extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bn(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.s.y > 0.0f) {
            methodHookParam.setResult(Float.valueOf(this.a.s.y + (MainHook.r.nextFloat() * 0.01f)));
        }
    }
}
