package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class br extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    br(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (!this.a.s.c.equals("")) {
            methodHookParam.setResult(this.a.s.c);
        }
    }
}
