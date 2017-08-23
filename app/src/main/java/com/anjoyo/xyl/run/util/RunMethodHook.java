package com.anjoyo.xyl.run.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.SparseArray;

import java.lang.reflect.Field;
import java.util.Random;

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
    static Context mContext;

    RunMethodHook(Context context, MainHook mainHook,
                  LoadPackageParam loadPackageParam) {
        this.mMainHook = mainHook;
        this.loadPackageParam = loadPackageParam;
        this.mContext = context;
    }

    protected void beforeHookedMethod(MethodHookParam methodHookParam)
            throws Throwable {
        if (mContext == null) {
            mMainHook.bindReceicver();
            mContext = MainHook.context;
        }
        boolean isSystemApp =loadPackageParam.packageName.contains("google")||loadPackageParam.packageName.contains("android");
//        if (mMainHook.mXSharedPreferences.getBoolean("increment", false)) {
//            if (loadPackageParam.packageName.equals(RunMethodHook.ZHIFUBAO) || loadPackageParam.packageName.equals(RunMethodHook.WEIBO) || loadPackageParam.packageName.equals(RunMethodHook.PINGAN) || loadPackageParam.packageName.equals(RunMethodHook.WEXIN) || loadPackageParam.packageName.equals(RunMethodHook.QQ) || loadPackageParam.packageName.equals(RunMethodHook.LEDONG) || loadPackageParam.packageName.equals(RunMethodHook.YUEDONG) || loadPackageParam.packageName.equals(RunMethodHook.CODOON)) {
////                mMainHook.initData();
//                if (BuildConfig.DEBUG)
//                    Log.d("xyl", "步数加倍开始");
//                try {
//                    Sensor sensor = null;
//                    try {
//                        mMainHook.sObject = param.thisObject;
//                        int intValue = ((Integer) param.args[0]).intValue();
//                        Field declaredField = param.thisObject.getClass()
//                                .getDeclaredField("mSensorsEvents");
//                        declaredField.setAccessible(true);
//                        sensor = ((SensorEvent) ((SparseArray) declaredField
//                                .get(param.thisObject)).get(intValue)).sensor;
//                    } catch (Throwable e) {
//                        // TODO: handle exception
//                        XposedBridge.log("SensorEvent=NULL");
//                    }
//
//                    if (sensor == null) {
//                        XposedBridge.log("sensor=NULL");
//                    } else {
//                        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//                            float[] fArr;
//                            if (mMainHook.isLedong && loadPackageParam.packageName.equals(LEDONG)) {
//                                MainHook.ledongCount += 1;
//                                //完美
//                                if (MainHook.ledongCount % 3 == 0) {
//                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 100;
////                        ((float[]) param.args[1])[1] += (float) -10;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.4375f;
//                                } else if (MainHook.ledongCount % 2 == 0) {
//                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
////                        ((float[]) param.args[1])[2] += (float) -20;
////                        ((float[]) param.args[1])[1] += (float) -5;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] - 0.21875f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.875f;
//                                } else {
//                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 10;
////                        ((float[]) param.args[1])[2] += (float) 20;
////                        ((float[]) param.args[1])[1] += (float) -15;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] + 20.0f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.28125f;
//                                }
//                            }
//                            if (mMainHook.isAlipay && loadPackageParam.packageName.equals(ZHIFUBAO)) {
//                                MainHook.zhifubaoCount += 1;
//                                //完美
//                                if (MainHook.zhifubaoCount % 3 == 0) {
//                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 100;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.4375f;
//                                } else if (MainHook.zhifubaoCount % 2 == 0) {
//                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] - 0.21875f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.875f;
//                                } else {
//                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 10;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] + 20.0f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.28125f;
//                                }
//                            }
//                            if (mMainHook.isYuedong && loadPackageParam.packageName.equals(YUEDONG)) {
//                                MainHook.yuedongCount += 1;
//                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
//                                if (MainHook.yuedongCount % 2 == 0) {
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] - 0.21875f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.875f;
//                                } else {
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] + 20.0f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.28125f;
//                                }
//                            }
//                            if (mMainHook.isPingan && loadPackageParam.packageName.equals(PINGAN)) {
//                                MainHook.pinganCount += 1;
//                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
//                                if (MainHook.pinganCount % 2 == 0) {
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] - 0.21875f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.875f;
//                                } else {
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] + 20.0f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.28125f;
//                                }
//                            }
//                            if (mMainHook.isCodoon && loadPackageParam.packageName.equals(CODOON)) {
//                                MainHook.codoonCount += 1;
//                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * 1000;
//                                if (MainHook.codoonCount % 2 == 0) {
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] - 0.21875f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.875f;
//                                } else {
//                                    fArr = (float[]) param.args[1];
//                                    fArr[2] = fArr[2] + 20.0f;
//                                    fArr = (float[]) param.args[1];
//                                    fArr[1] = fArr[1] - 0.28125f;
//                                }
//                            }
//                        }
//                        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER || sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
//                            if ((mMainHook.isWeixin && loadPackageParam.packageName.equals(WEXIN))) {
////                    if (MainHook.isAuto) {
//                                if (MainHook.m * MainHook.weixinCount <= MainHook.max) {
//                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + MainHook.m * MainHook.weixinCount;
//                                    MainHook.weixinCount += 1;
//                                } else {
//                                    MainHook.weixinCount = 0;
//                                }
////                    } else {
////                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * MainHook.m;
////                    }
//                            }
//                            if ((mMainHook.isQQ && loadPackageParam.packageName.equals(QQ))) {
////                    if (MainHook.isAuto) {
//                                if (MainHook.m * MainHook.qqCount <= MainHook.max) {
//                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + MainHook.m * MainHook.qqCount;
//                                    MainHook.qqCount += 1;
//                                } else {
//                                    MainHook.qqCount = 0;
//                                }
////                    } else {
////                        ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * MainHook.m;
////                    }
//                            }
//                            if (mMainHook.isAlipay && loadPackageParam.packageName.equals(ZHIFUBAO)) {
//                                if (999 * MainHook.zhifubaoCount <= MainHook.max) {
//                                    ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] + 999 * MainHook.zhifubaoCount;
//                                    MainHook.zhifubaoCount += 1;
//                                } else {
//                                    MainHook.zhifubaoCount = 0;
//                                }
//                            }
//                            if ((mMainHook.isWeibo && loadPackageParam.packageName.equals(WEIBO))) {
//                                ((float[]) param.args[1])[0] = ((float[]) param.args[1])[0] * MainHook.m;
//                            }
//                            XposedBridge.log("xyl-run:小熊跑步" + loadPackageParam.packageName + "传感器类型：" + sensor.getType() + ",修改后：" + ((float[]) param.args[1])[0]);
//                            String motifyContent = this.loadPackageParam.packageName
//                                    + "修改值" + ((float[]) param.args[1])[0];
//                            XposedBridge.log(motifyContent);
//                            Intent intent = new Intent("com.anjoyo.xyl.run.SETTING_CHANGED");
//                            intent.putExtra("content", motifyContent);
//                            if (mContext != null) {
//                                mContext.sendBroadcast(intent);
//                            } else {
//                                Context context = (Context) XposedHelpers.callMethod(XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", null), "currentActivityThread", new Object[0]), "getSystemContext", new Object[0]);
//                                if (context != null) {
//                                    context.sendBroadcast(intent);
//                                }
//                            }
//                        }
//                    }
//                } catch (Throwable throwable) {
//                    // TODO: handle exception
//                    throwable.printStackTrace();
//                }
//            }
//        } else {
            try {
                this.mMainHook.mXSharedPreferences.reload();
//                if ((!this.mMainHook.mXSharedPreferences.getBoolean("isSys", true) || !isSystemApp) && !this.mMainHook.mXSharedPreferences.getString("Ban", "").contains(this.loadPackageParam.packageName) && this.mMainHook.mXSharedPreferences.getBoolean("isStart", false)) {
                if ((!this.mMainHook.mXSharedPreferences.getBoolean("isSys", true) || (this.loadPackageParam.appInfo.flags & 1) <= 0) && !this.mMainHook.mXSharedPreferences.getString("Ban", "").contains(this.loadPackageParam.packageName) && this.mMainHook.mXSharedPreferences.getBoolean("isStart", false)) {
                    XposedBridge.log("xyl-run: isStart====="+this.mMainHook.mXSharedPreferences.getBoolean("isStart", false));
                    int intValue = ((Integer) methodHookParam.args[0]).intValue();
                    Field declaredField = methodHookParam.thisObject.getClass().getDeclaredField("mSensorsEvents");
                    declaredField.setAccessible(true);
                    SparseArray sparseArray = (SparseArray) declaredField.get(methodHookParam.thisObject);
                    if (sparseArray != null) {
                        Sensor sensor = ((SensorEvent) sparseArray.get(intValue)).sensor;
                        if (sensor != null) {
                            if (sensor.getType() != 11 && sensor.getType() != 4 && sensor.getType() != 3 && sensor.getType() != 2 && sensor.getType() != 5 && sensor.getType() != 9 && sensor.getType() != 8) {
                                if (mMainHook.e < 2) {
                                    XposedBridge.log("xyl-run:对 " + this.loadPackageParam.packageName + (this.loadPackageParam.appInfo.className == null ? "" : " \u7684 " + this.loadPackageParam.appInfo.className) + "已Hook传感器：" + sensor.getName());
                                    mMainHook.e = mMainHook.e + 1;
                                }
                            } else {
                                return;
                            }
                        }
                    }
                    if (this.mMainHook.mXSharedPreferences.getBoolean("isLock", false)) {
                        ((float[]) methodHookParam.args[1])[0] = 0.0f;
                        ((float[]) methodHookParam.args[1])[2] = 0.0f;
                        ((float[]) methodHookParam.args[1])[1] = 0.0f;
                        return;
                    }
                    this.mMainHook.d = Integer.parseInt(this.mMainHook.mXSharedPreferences.getString("Time", "100"));
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - this.mMainHook.h >= ((long) this.mMainHook.d)) {
                        long currentTimeMillis2 = System.currentTimeMillis();
                        long c = currentTimeMillis2 - this.mMainHook.g;
                        if (this.mMainHook.mXSharedPreferences.getBoolean("isBaoli", false)) {
                            ((float[]) methodHookParam.args[1])[0] = (new Random().nextFloat() * 1000.0f) + 10.0f;
                            ((float[]) methodHookParam.args[1])[2] = (new Random().nextFloat() * 1000.0f) + 10.0f;
                            ((float[]) methodHookParam.args[1])[1] = (new Random().nextFloat() * 1000.0f) + 10.0f;
                        } else if (!this.loadPackageParam.packageName.equals("com.sina.weibo")) {
                            ((float[]) methodHookParam.args[1])[0] = 125.0f + (1200.0f * new Random().nextFloat());
                        }
                        if (c >= ((long) mMainHook.c)) {
                            this.mMainHook.g = currentTimeMillis2;
                            this.mMainHook.h = currentTimeMillis;
                            if (this.mMainHook.mXSharedPreferences.getBoolean("isBaoli", false)) {
                                ((float[]) methodHookParam.args[1])[0] = (new Random().nextFloat() * 1000.0f) + 10.0f;
                                ((float[]) methodHookParam.args[1])[2] = (new Random().nextFloat() * 1000.0f) + 10.0f;
                                ((float[]) methodHookParam.args[1])[1] = (new Random().nextFloat() * 1000.0f) + 10.0f;
                            } else if (this.loadPackageParam.packageName.equals("com.sina.weibo")) {
                                ((float[]) methodHookParam.args[1])[0] = (float) (((double) ((float[]) methodHookParam.args[1])[0]) + (Math.random() * 10.0d));
                            } else {
                                ((float[]) methodHookParam.args[1])[0] = 125.0f + (1200.0f * new Random().nextFloat());
                            }
                        }
                    }

                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
//        }
    }
}
