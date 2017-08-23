package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class bt extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bt(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (dn.f(this.a.s.b)) {
            methodHookParam.setResult(this.a.s.b);
        }
    }
}
