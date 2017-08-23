package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class ce extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    ce(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        methodHookParam.setResult(Integer.valueOf(1080));
    }
}
