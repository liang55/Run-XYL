package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bi extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bi(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        methodHookParam.setResult(i.a(this.a.s.A, this.a.s.B, this.a.s.C, this.a.s.F));
    }
}
