package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bs extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bs(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (!this.a.s.g.equals("")) {
            methodHookParam.setResult(this.a.s.g);
        }
    }
}
