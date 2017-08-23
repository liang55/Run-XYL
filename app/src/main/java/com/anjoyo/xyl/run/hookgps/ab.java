package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class ab extends XC_MethodHook {
    final /* synthetic */ k a;

    ab(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (k.a(this.a).a.equals("0")) {
            methodHookParam.setResult(Boolean.valueOf(false));
        }
        if (k.a(this.a).a.equals("1")) {
            methodHookParam.setResult(Boolean.valueOf(true));
        }
    }
}
