package com.anjoyo.xyl.run.hookgps;

import android.content.pm.ApplicationInfo;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import java.util.ArrayList;
import java.util.List;

class ay extends XC_MethodHook {
    final /* synthetic */ ax a;

    ay(ax axVar) {
        this.a = axVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (methodHookParam.getResult() != null) {
            List<ApplicationInfo> list = (List) methodHookParam.getResult();
            ArrayList arrayList = new ArrayList();
            for (ApplicationInfo applicationInfo : list) {
                if (!(applicationInfo.packageName.contains("com.anjoyo.xyl.run") || applicationInfo.packageName.contains("de.robv.android.xposed.installer"))) {
                    arrayList.add(applicationInfo);
                }
            }
            methodHookParam.setResult(arrayList);
        }
    }
}
