package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class ba extends XC_MethodHook {
    final /* synthetic */ ax a;

    ba(ax axVar) {
        this.a = axVar;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        if (methodHookParam.args[0] != null) {
            String str = (String) methodHookParam.args[0];
            if (str.contains("com.anjoyo.xyl.run") || str.contains("de.robv.android.xposed.installer")) {
                methodHookParam.args[0] = "pkg.no.such.name.error";
            }
        }
    }
}
