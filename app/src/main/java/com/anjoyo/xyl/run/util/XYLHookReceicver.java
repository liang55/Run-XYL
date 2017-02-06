package com.anjoyo.xyl.run.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author xyl
 * @date 创建时间：2016年5月30日 下午4:34:46
 * @TODO HookReceicver
 */
public class XYLHookReceicver extends BroadcastReceiver {
    final/* synthetic */ MainHook a;

    XYLHookReceicver(MainHook mainHook) {
        this.a = mainHook;
    }

    public void onReceive(Context context, Intent intent) {
        MainHook.m = Integer.valueOf(
                intent.getExtras().getString("magnification", "100"))
                .intValue();
        MainHook.addValue = Long.valueOf(
                intent.getExtras().getString("addvalue", "0"))
                .intValue();
        MainHook.userId =
                intent.getExtras().getString("userid", "");
        MainHook.isAuto = intent.getExtras().getBoolean(
                "autoincrement", false);
//        MainHook.allautoincrementValue = intent.getExtras().getBoolean(
//                "allautoincrement", true);
        MainHook.incrementValue = intent.getExtras().getBoolean("increment",
                true);
    }
}
