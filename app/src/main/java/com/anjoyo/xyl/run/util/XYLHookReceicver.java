package com.anjoyo.xyl.run.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.anjoyo.xyl.run.BuildConfig;

import de.robv.android.xposed.XposedBridge;

/**
 * @author xyl
 * @date 创建时间：2016年5月30日 下午4:34:46
 * @TODO HookReceicver
 */
public class XYLHookReceicver extends BroadcastReceiver {
    final/* synthetic */ MainHook a;

    public XYLHookReceicver(MainHook mainHook) {
        super();
        this.a = mainHook;
    }
    public void onReceive(Context context, Intent intent) {
//        MainHook.m = Integer.valueOf(
//                intent.getExtras().getString("magnification", "50"))
//                .intValue();
//        MainHook.addValue = Long.valueOf(
//                intent.getExtras().getString("addvalue", "0"))
//                .intValue();
//        MainHook.userId =
//                intent.getExtras().getString("userid", "");
//        MainHook.incrementValue = intent.getExtras().getBoolean("increment",
//                false);
//
//        MainHook.isWeixin = intent.getExtras().getBoolean("weixin", false);
//        MainHook.isQQ = intent.getExtras().getBoolean("qq", false);
//        MainHook.isLedong = intent.getExtras().getBoolean("ledong", false);
//        MainHook.isYuedong = intent.getExtras().getBoolean("yuedong", false);
//        MainHook.isPingan = intent.getExtras().getBoolean("pingan", false);
//        MainHook.isCodoon = intent.getExtras().getBoolean("codoon", false);
//        MainHook.isWeibo = intent.getExtras().getBoolean("weibo", false);
//        MainHook.isAlipay = intent.getExtras().getBoolean("alipay", false);
//
//        MainHook.isZfbOn = intent.getExtras().getBoolean("isZfbOn", false);
//        MainHook.zfbSteps =  Long.valueOf(intent.getExtras().getString("zfbSteps", "0"))
//                .intValue();
        if (BuildConfig.DEBUG){
            XposedBridge.log("xyl-run:收到小熊跑步配置改变消息" );
        }
        if (a!=null){
            a.initData();
        }
    }
}
