package com.anjoyo.xyl.run.hookgps;

import android.telephony.TelephonyManager;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

class ae extends XC_MethodHook {
    final /* synthetic */ k a;

    ae(k kVar) {
        this.a = kVar;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        if (methodHookParam.thisObject instanceof TelephonyManager) {
            methodHookParam.setResult(Object.class);
        }
    }
}
