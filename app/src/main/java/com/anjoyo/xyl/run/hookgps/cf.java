package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class cf extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    cf(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.s.p) {
            methodHookParam.setResult(this.a.s.h);
        }
    }
}
