package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class v extends XC_MethodHook {
    final /* synthetic */ k a;

    v(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (!this.a.c.f.equals("")) {
            methodHookParam.setResult(this.a.c.f);
        }
    }
}
