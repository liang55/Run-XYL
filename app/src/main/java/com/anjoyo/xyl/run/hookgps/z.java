package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class z extends XC_MethodHook {
    final /* synthetic */ k a;

    z(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.c.E > 0) {
            methodHookParam.setResult(Integer.valueOf(this.a.c.E));
        }
    }
}
