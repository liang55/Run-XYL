package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class ch extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    ch(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (!this.a.s.f.equals("")) {
            methodHookParam.setResult(this.a.s.f);
        }
    }
}
