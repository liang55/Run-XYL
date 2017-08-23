package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class aa extends XC_MethodHook {
    final /* synthetic */ k a;

    aa(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (k.a(this.a).a.equals("0")) {
            methodHookParam.setResult(Integer.valueOf(1));
        }
        if (k.a(this.a).a.equals("1")) {
            methodHookParam.setResult(Integer.valueOf(3));
        }
    }
}
