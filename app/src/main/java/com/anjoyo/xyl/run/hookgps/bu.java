package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bu extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bu(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.s.F != 0) {
            methodHookParam.setResult(Integer.valueOf(this.a.s.F));
        }
    }
}
