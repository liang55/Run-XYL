package com.anjoyo.xyl.run.hookgps;

import android.location.LocationListener;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import java.util.Iterator;

class by extends XC_MethodHook {
    final /* synthetic */ MainHook a;

    by(MainHook mainHook) {
        this.a = mainHook;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        LocationListener locationListener = (LocationListener) methodHookParam.args[0];
        Iterator it = this.a.lGps.iterator();
        while (it.hasNext()) {
            ah ahVar = (ah) it.next();
            if (ahVar.b && ahVar.a == locationListener) {
                ahVar.b();
            }
        }
        if (this.a.s.r) {
            methodHookParam.setResult(null);
        }
    }
}
