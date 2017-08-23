package com.anjoyo.xyl.run.hookgps;

import android.location.GpsStatus.Listener;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import java.util.Iterator;

class ca extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    ca(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        Listener listener = (Listener) methodHookParam.args[0];
        Iterator it = this.a.lGpsl.iterator();
        while (it.hasNext()) {
            al alVar = (al) it.next();
            if (alVar.b && alVar.a == listener) {
                alVar.b();
            }
        }
        if (this.a.s.r) {
            methodHookParam.setResult(null);
        }
    }
}
