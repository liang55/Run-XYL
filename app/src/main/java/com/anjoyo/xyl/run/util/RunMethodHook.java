package com.anjoyo.xyl.run.util;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;
import android.util.SparseArray;

import com.anjoyo.xyl.run.BuildConfig;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
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
    final/* synthetic */ LoadPackageParam loadPackageParam;
    final/* synthetic */ MainHook mMainHook;
    final Context mContext;
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
                mMainHook.sObject = param.thisObject;
                int intValue = ((Integer) param.args[0]).intValue();
                Field declaredField = param.thisObject.getClass()
                        .getDeclaredField("mSensorsEvents");
                declaredField.setAccessible(true);
                sensor = ((SensorEvent) ((SparseArray) declaredField
                        .get(param.thisObject)).get(intValue)).sensor;
            } catch (Throwable e) {
                // TODO: handle exception
                XposedBridge.log("SensorEvent=NULL");
                return;
            }

            if (sensor == null) {
                XposedBridge.log("sensor=NULL");
                return;
            }
            mMainHook.initData();
            if (!mMainHook.incrementValue) {
                if (BuildConfig.DEBUG)
                    Log.d("xyl", "加速关闭");
                return;
            }
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float[] fArr;
                if (mMainHook.isLedong && loadPackageParam.packageName.equals(LEDONG)) {
                    MainHook.ledongCount += 1;
                    //完美
                    if (MainHook.ledongCount % 3 == 0) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 100;
//                        ((float[]) param.args[1])[1] += (float) -10;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.4375f;
                    } else if (MainHook.ledongCount % 2 == 0) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
//                        ((float[]) param.args[1])[2] += (float) -20;
//                        ((float[]) param.args[1])[1] += (float) -5;
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] - 0.21875f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.875f;
                    } else {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 10;
//                        ((float[]) param.args[1])[2] += (float) 20;
//                        ((float[]) param.args[1])[1] += (float) -15;
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] + 20.0f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.28125f;
                    }
                }
                if (mMainHook.isAlipay && loadPackageParam.packageName.equals(ZHIFUBAO)) {
                    MainHook.zhifubaoCount += 1;
                    //完美
                    if (MainHook.zhifubaoCount % 3 == 0) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 100;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.4375f;
                    } else if (MainHook.zhifubaoCount % 2 == 0) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] - 0.21875f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.875f;
                    } else {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 10;
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] + 20.0f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.28125f;
                    }
                }
                if (mMainHook.isYuedong && loadPackageParam.packageName.equals(YUEDONG)) {
                    MainHook.yuedongCount += 1;
                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                    if (MainHook.yuedongCount % 2 == 0) {
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] - 0.21875f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.875f;
                    } else {
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] + 20.0f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.28125f;
                    }
                }
                if (mMainHook.isPingan && loadPackageParam.packageName.equals(PINGAN)) {
                    MainHook.pinganCount += 1;
                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                    if (MainHook.pinganCount % 2 == 0) {
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] - 0.21875f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.875f;
                    } else {
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] + 20.0f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.28125f;
                    }
                }
                if (mMainHook.isCodoon && loadPackageParam.packageName.equals(CODOON)) {
                    MainHook.codoonCount += 1;
                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
                    if (MainHook.codoonCount % 2 == 0) {
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] - 0.21875f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.875f;
                    } else {
                        fArr = (float[]) param.args[1];
                        fArr[2] = fArr[2] + 20.0f;
                        fArr = (float[]) param.args[1];
                        fArr[1] = fArr[1] - 0.28125f;
                    }
                }
            }
            if (sensor.getType() == Sensor.TYPE_STEP_COUNTER || sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                if ((mMainHook.isWeixin && loadPackageParam.packageName.equals(WEXIN))) {
//                    if (MainHook.isAuto) {
                        if (MainHook.m * MainHook.weixinCount <= MainHook.max) {
                            ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + MainHook.m * MainHook.weixinCount;
                            MainHook.weixinCount += 1;
                        } else {
                            MainHook. weixinCount = 0;
                        }
//                    } else {
//                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * MainHook.m;
//                    }
                }
                if ((mMainHook.isQQ && loadPackageParam.packageName.equals(QQ))) {
//                    if (MainHook.isAuto) {
                        if (MainHook.m * MainHook.qqCount <= MainHook.max) {
                            ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + MainHook.m * MainHook.qqCount;
                            MainHook.qqCount += 1;
                        } else {
                            MainHook.qqCount = 0;
                        }
//                    } else {
//                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * MainHook.m;
//                    }
                }
                if (mMainHook.isAlipay && loadPackageParam.packageName.equals(ZHIFUBAO)) {
                    if (999 * MainHook.zhifubaoCount <= MainHook.max) {
                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + 999 * MainHook.zhifubaoCount;
                        MainHook.zhifubaoCount += 1;
                    } else {
                        MainHook.zhifubaoCount = 0;
                    }
                }
                if ((mMainHook.isWeibo && loadPackageParam.packageName.equals(WEIBO))) {
                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * MainHook.m;
                }
                XposedBridge.log("xyl-run:"+loadPackageParam.packageName + "传感器类型：" + sensor.getType() + ",修改后：" + ((float[]) param.args[1])[0]);
                String motifyContent = this.loadPackageParam.packageName
                        + "修改值" + ((float[]) param.args[1])[0];
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
