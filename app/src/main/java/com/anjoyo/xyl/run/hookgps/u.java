package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class u extends XC_MethodHook {
    final /* synthetic */ k a;

    u(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (!this.a.c.j.equals("")) {
            methodHookParam.setResult(this.a.c.j);
        }
    }
}
