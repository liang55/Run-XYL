package com.anjoyo.xyl.run.hookgps;

import android.app.Application;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class r extends XC_MethodHook {
    final /* synthetic */ k a;

    r(k kVar) {
        this.a = kVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (methodHookParam.thisObject != null) {
            k.a(this.a).a((Application) methodHookParam.thisObject);
        }
    }
}
