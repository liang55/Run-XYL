package com.anjoyo.xyl.run.hookgps;

import android.content.pm.PackageInfo;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import java.util.ArrayList;
import java.util.List;

class az extends XC_MethodHook {
    final /* synthetic */ ax a;

    az(ax axVar) {
        this.a = axVar;
    }

    protected void afterHookedMethod(MethodHookParam methodHookParam) {
        if (methodHookParam.getResult() != null) {
            List<PackageInfo> list = (List) methodHookParam.getResult();
            ArrayList arrayList = new ArrayList();
            for (PackageInfo packageInfo : list) {
                if (!(packageInfo.packageName.contains("com.anjoyo.xyl.run") || packageInfo.packageName.contains("de.robv.android.xposed.installer"))) {
                    arrayList.add(packageInfo);
                }
            }
            methodHookParam.setResult(arrayList);
        }
    }
}
