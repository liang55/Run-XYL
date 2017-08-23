package com.anjoyo.xyl.run.hookgps;

import android.telephony.TelephonyManager;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class ad extends XC_MethodHook {
    final /* synthetic */ k a;

    ad(k kVar) {
        this.a = kVar;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        if (methodHookParam.args[0] instanceof TelephonyManager) {
            methodHookParam.setResult(null);
        }
    }
}
