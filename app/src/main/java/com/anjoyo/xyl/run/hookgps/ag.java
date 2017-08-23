package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class ag extends XC_MethodHook {
    final /* synthetic */ k a;

    ag(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        methodHookParam.setResult(Integer.valueOf(k.a(this.a).B));
    }
}
