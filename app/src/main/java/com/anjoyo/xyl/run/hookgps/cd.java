package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class cd extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    cd(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (!this.a.s.j.equals("")) {
            methodHookParam.setResult(this.a.s.j);
        }
    }
}
