package com.anjoyo.xyl.run.util;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.SparseArray;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

class RunMethodHook extends XC_MethodHook {

    public static final String WEXIN = "com.tencent.mm";
    public static final String QQ = "com.tencent.mobileqq";
    public static final String YUEDONG = "com.yuedong.sport";
    public static final String LEDONG = "cn.ledongli.ldl";
    public static final String PINGAN = "com.pingan.papd";
    public static final String CODOON = "com.codoon.gps";
    public static final String WEIBO = "com.sina.weibo";
    public static final String ZHIFUBAO = "com.eg.android.AlipayGphone";
    private static boolean isWeixin = true, isQQ = true,  isLedong = true, isYuedong = true, isPingan = true, isCodoon = true, isWeibo = true, isAlipay = true;
    final/* synthetic */ LoadPackageParam loadPackageParam;
    final/* synthetic */ MainHook mMainHook;
    final Context mContext;
    public static Object sObject;

    RunMethodHook(Context context, MainHook mainHook,
                  LoadPackageParam loadPackageParam) {
        this.mMainHook = mainHook;
        this.loadPackageParam = loadPackageParam;
        this.mContext = context;
    }

    protected void beforeHookedMethod(MethodHookParam param)
            throws Throwable {
        try {
            Sensor sensor = null;
            try {
                int intValue = ((Integer) param.args[0]).intValue();
                Field declaredField = param.thisObject.getClass()
                        .getDeclaredField("mSensorsEvents");
                declaredField.setAccessible(true);
                sensor = ((SensorEvent) ((SparseArray) declaredField
                        .get(param.thisObject)).get(intValue)).sensor;
            } catch (Exception e) {
                // TODO: handle exception
                XposedBridge.log("SensorEvent=NULL");
//				XposedBridge.log(e);
                return;
            }

            if (sensor == null) {
                XposedBridge.log("sensor=NULL");
                return;
            }
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (isLedong && loadPackageParam.packageName.equals(LEDONG)) {
                    MainHook.ledongCount += 1;
                    //完美
                    if (MainHook.ledongCount % 3 == 0) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 100;
                        ((float[]) param.args[1])[1] += (float) -10;
                    } else if (MainHook.ledongCount % 2 == 0) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                        ((float[]) param.args[1])[2] += (float) -20;
                        ((float[]) param.args[1])[1] += (float) -5;
                    } else {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 10;
                        ((float[]) param.args[1])[2] += (float) 20;
                        ((float[]) param.args[1])[1] += (float) -15;
                    }
                }
                if (isAlipay && loadPackageParam.packageName.equals(ZHIFUBAO)) {
                    MainHook.zhifubaoCount += 1;
                    //完美
                    if (MainHook.zhifubaoCount % 3 == 0) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 100;
                        ((float[]) param.args[1])[1] += (float) -10;
                    } else if (MainHook.zhifubaoCount % 2 == 0) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                        ((float[]) param.args[1])[2] += (float) -20;
                        ((float[]) param.args[1])[1] += (float) -5;
                    } else {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 10;
                        ((float[]) param.args[1])[2] += (float) 20;
                        ((float[]) param.args[1])[1] += (float) -15;
                    }
                }
                if (isYuedong && loadPackageParam.packageName.equals(YUEDONG)) {
                    MainHook.yuedongCount += 1;
                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                    if (MainHook.yuedongCount % 2 == 0) {
                        ((float[]) param.args[1])[2] += (float) -20;
                        ((float[]) param.args[1])[1] += (float) -5;
                    } else {
                        ((float[]) param.args[1])[2] += (float) 20;
                        ((float[]) param.args[1])[1] += (float) -15;
                    }
                }
                if (isPingan && loadPackageParam.packageName.equals(PINGAN)) {
                    MainHook.pinganCount += 1;
                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                    if (MainHook.pinganCount % 2 == 0) {
                        ((float[]) param.args[1])[2] += (float) -20;
                        ((float[]) param.args[1])[1] += (float) -5;
                    } else {
                        ((float[]) param.args[1])[2] += (float) 20;
                        ((float[]) param.args[1])[1] += (float) -15;
                    }
                }
                if (isCodoon && loadPackageParam.packageName.equals(CODOON)) {
                    MainHook.codoonCount += 1;
                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                    if (MainHook.codoonCount % 2 == 0) {
                        ((float[]) param.args[1])[2] += (float) -20;
                        ((float[]) param.args[1])[1] += (float) -5;
                    } else {
                        ((float[]) param.args[1])[2] += (float) 20;
                        ((float[]) param.args[1])[1] += (float) -15;
                    }
                }
            }
            if (sensor.getType() == Sensor.TYPE_STEP_COUNTER || sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                if ((isWeixin && loadPackageParam.packageName.equals(WEXIN))) {
                    if (MainHook.isAuto) {
                        if (MainHook.m * MainHook.weixinCount <= MainHook.max) {
                            ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + MainHook.m * MainHook.weixinCount;
                            MainHook.weixinCount += 1;
                        } else {
                            MainHook. weixinCount = 0;
                        }
                    } else {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * MainHook.m;
                    }
                }
                if ((isQQ && loadPackageParam.packageName.equals(QQ))) {
                    if (MainHook.isAuto) {
                        if (MainHook.m * MainHook.qqCount <= MainHook.max) {
                            ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + MainHook.m * MainHook.qqCount;
                            MainHook.qqCount += 1;
                        } else {
                            MainHook.qqCount = 0;
                        }
                    } else {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * MainHook.m;
                    }
                }
                if (isAlipay && loadPackageParam.packageName.equals(ZHIFUBAO)) {
                    if (10000 * MainHook.zhifubaoCount <= MainHook.max) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + 10000 * MainHook.zhifubaoCount;
                        MainHook.zhifubaoCount += 1;
                    } else {
                        MainHook.zhifubaoCount = 0;
                    }
                }
                if ((isWeibo && loadPackageParam.packageName.equals(WEIBO))) {
                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * MainHook.m;
                }
                XposedBridge.log(loadPackageParam.packageName + "传感器类型：" + sensor.getType() + ",修改后：" + ((float[]) param.args[1])[0]);
                String motifyContent = this.loadPackageParam.packageName
                        + "修改值" + ((float[]) param.args[1])[0];
                // Log.d("xxx",motifyContent);
                XposedBridge.log(motifyContent);
                Intent intent = new Intent("com.anjoyo.xyl.run.SETTING_CHANGED");
                intent.putExtra("content", motifyContent);
                if (mContext != null) {
                    mContext.sendBroadcast(intent);
                }
            }
        } catch (Throwable throwable) {
            // TODO: handle exception
            throwable.printStackTrace();
            throw throwable;
        }
    }
}
