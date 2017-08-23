package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class cg extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    cg(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.s.p) {
            methodHookParam.setResult(this.a.s.h);
        }
    }
}
