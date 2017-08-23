package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bl extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bl(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.s.z > 0.0f) {
            methodHookParam.setResult(Float.valueOf(this.a.s.z + (MainHook.r.nextFloat() * 0.01f)));
        }
    }
}
