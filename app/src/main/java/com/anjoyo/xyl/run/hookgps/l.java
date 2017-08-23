package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class l extends XC_MethodHook {
    final /* synthetic */ k a;

    l(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if(k.a(this.a).a.equals("0")) {
            methodHookParam.setResult("MOBILE");
        }

        if(k.a(this.a).a.equals("1")) {
            methodHookParam.setResult("WIFI");
        }
    }
}
