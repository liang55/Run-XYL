package com.anjoyo.xyl.run.util;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.SparseArray;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import java.lang.reflect.Field;
import java.util.Random;

public class HookSensor implements IXposedHookZygoteInit, IXposedHookLoadPackage {
    private static int Count;
    private static int UPTATE_INTERVAL_TIME;
    private static int max;
    private static Object sObject;
    final Object activityThread;
    private long lastUpdateTime;
    private long lastUpdateTime1;
    private int m;
    XSharedPreferences sharedPreferences;

    /* renamed from: com.specher.sm.HookSensor.2 */
    class AnonymousClass2 extends XC_MethodHook {
        private final /* synthetic */ LoadPackageParam val$arg0;

        AnonymousClass2(LoadPackageParam loadPackageParam) {
            this.val$arg0 = loadPackageParam;
        }

        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            try {
                HookSensor.this.sharedPreferences.reload();
                if (!(HookSensor.this.sharedPreferences.getBoolean("isSys", true) && this.val$arg0.appInfo.flags == 1) && HookSensor.this.sharedPreferences.getBoolean("isStart", false)) {
                    int handle = ((Integer) param.args[0]).intValue();
                    HookSensor.sObject = param.thisObject;
                    Field field = param.thisObject.getClass().getDeclaredField("mSensorsEvents");
                    field.setAccessible(true);
                    SparseArray<SensorEvent> se = (SparseArray) field.get(param.thisObject);
                    if (se != null) {
                        Sensor ss = ((SensorEvent) se.get(handle)).sensor;
                        if (ss != null) {
                            if (ss.getType() != 11 && ss.getType() != 4 && ss.getType() != 3 && ss.getType() != 2 && ss.getType() != 5 && ss.getType() != 9 && ss.getType() != 8) {
                                if (HookSensor.Count < 15) {
                                    XposedBridge.log("\u4f20\u611f\u5668\uff1a" + ss.toString());
                                    HookSensor.Count = HookSensor.Count + 1;
                                }
                            } else {
                                return;
                            }
                        }
                    }
                    if (HookSensor.this.sharedPreferences.getBoolean("isLock", false)) {
                        ((float[]) param.args[1])[0] = 0.0f;
                        ((float[]) param.args[1])[2] = 0.0f;
                        ((float[]) param.args[1])[1] = 0.0f;
                        return;
                    }
                    HookSensor.this.m = Integer.parseInt(HookSensor.this.sharedPreferences.getString("Time", "100"));
                    long currentUpdateTime1 = System.currentTimeMillis();
                    if (currentUpdateTime1 - HookSensor.this.lastUpdateTime1 >= ((long) HookSensor.this.m)) {
                        long currentUpdateTime = System.currentTimeMillis();
                        long timeInterval = currentUpdateTime - HookSensor.this.lastUpdateTime;
                        if (HookSensor.this.sharedPreferences.getBoolean("isBaoli", false)) {
                            ((float[]) param.args[1])[0] = 10.0f + (1000.0f * new Random().nextFloat());
                            ((float[]) param.args[1])[2] = 10.0f + (1000.0f * new Random().nextFloat());
                            ((float[]) param.args[1])[1] = 10.0f + (1000.0f * new Random().nextFloat());
                        } else if (this.val$arg0.packageName.equals("com.sina.weibo")) {
                            ((float[]) param.args[1])[0] = 0.0f;
                        } else {
                            ((float[]) param.args[1])[0] = 125.0f + (1200.0f * new Random().nextFloat());
                        }
                        if (timeInterval >= ((long) HookSensor.UPTATE_INTERVAL_TIME)) {
                            HookSensor.this.lastUpdateTime = currentUpdateTime;
                            HookSensor.this.lastUpdateTime1 = currentUpdateTime1;
                            if (HookSensor.this.sharedPreferences.getBoolean("isBaoli", false)) {
                                ((float[]) param.args[1])[0] = 10.0f + (1000.0f * new Random().nextFloat());
                                ((float[]) param.args[1])[2] = 10.0f + (1000.0f * new Random().nextFloat());
                                ((float[]) param.args[1])[1] = 10.0f + (1000.0f * new Random().nextFloat());
                            } else if (this.val$arg0.packageName.equals("com.sina.weibo")) {
                                ((float[]) param.args[1])[0] = (float) (1.0d + (20.0d * Math.random()));
                            } else {
                                ((float[]) param.args[1])[0] = 125.0f + (1200.0f * new Random().nextFloat());
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public HookSensor() {
        this.m = 10;
        this.activityThread = XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", null), "currentActivityThread", new Object[0]);
    }

    static {
        UPTATE_INTERVAL_TIME = 200;
        Count = 1;
        max = Integer.MAX_VALUE;
    }

    public void handleLoadPackage(LoadPackageParam arg0) throws Throwable {
        Class<?> sensorEL = XposedHelpers.findClass("android.hardware.SystemSensorManager$SensorEventQueue", arg0.classLoader);
        Class<?> getSL = XposedHelpers.findClass("android.hardware.SensorManager", arg0.classLoader);
        if (sensorEL == null || getSL == null) {
            throw new ClassNotFoundException(arg0.getClass().getName());
        }
        try {
            XposedBridge.hookAllMethods(getSL, "getSensorList", new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if (((Integer) param.args[0]).intValue() == 1 || ((Integer) param.args[0]).intValue() == -1) {
                        param.setResult(null);
                        XposedBridge.log("\u83b7\u53d6\u4f20\u611f\u5668\u4fe1\u606f\uff1a" + param.getResult().toString());
                    } else {
                        XposedBridge.log("getSensorList\uff1a" + ((Integer) param.args[0]) + "\u83b7\u53d6\u5230\u7684\u4f20\u611f\u5668\u4fe1\u606f\uff1a" + param.getResult().toString());
                    }
                    super.afterHookedMethod(param);
                }
            });
            XposedBridge.hookAllMethods(sensorEL, "dispatchSensorEvent", new AnonymousClass2(arg0));
        } catch (Exception e) {
        }
    }

    public void initZygote(StartupParam arg0) throws Throwable {
        this.sharedPreferences = new XSharedPreferences("com.specher.sm", "setting");
        this.sharedPreferences.makeWorldReadable();
    }
}
