package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class q extends XC_MethodHook {
    final /* synthetic */ k a;

    q(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        methodHookParam.setResult(Integer.valueOf(((int) k.a(this.a).u) * 14400));
    }
}
