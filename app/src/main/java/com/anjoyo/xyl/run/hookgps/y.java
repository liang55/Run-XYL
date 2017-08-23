package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class y extends XC_MethodHook {
    final /* synthetic */ k a;

    y(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.c.D > 0) {
            methodHookParam.setResult(Integer.valueOf(this.a.c.D));
        }
    }
}
