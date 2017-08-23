package com.anjoyo.xyl.run.hookgps;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import java.util.ArrayList;

class bp extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    bp(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (this.a.s.q) {
            methodHookParam.setResult(new ArrayList());
        }
    }
}
