package com.anjoyo.xyl.run.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    static int mmCount;
    static int qqCount;
    static int ldlCount;
    static int ydCount;
    static int allCount;
    static int magnificationValue;
    static long addValue;
    static String userId;
    static boolean autoincrementValue;
    static boolean allautoincrementValue = true;
    static boolean incrementValue = true;
    XSharedPreferences mXSharedPreferences;
    static Context context = null;
    static XYLHookReceicver mXYLHookReceicver;

    static {
        mmCount = 0;
        qqCount = 0;
        ldlCount = 0;
        ydCount = 0;
        allCount = 0;
    }

    public MainHook() {
    }

    private void initData() {
        this.mXSharedPreferences.reload();
        magnificationValue = Integer.valueOf(
                this.mXSharedPreferences.getString("magnification", "50"))
                .intValue();
        autoincrementValue = this.mXSharedPreferences.getBoolean(
                "autoincrement", false);
        allautoincrementValue = this.mXSharedPreferences.getBoolean(
                "allautoincrement", true);
        incrementValue = this.mXSharedPreferences.getBoolean("increment", true);
        addValue = Long.valueOf(
                this.mXSharedPreferences.getString("addvalue", "0"))
                .intValue();
        userId =
                this.mXSharedPreferences.getString("userid", "");
        XposedBridge.log("magnificationValue=" + magnificationValue + ";autoincrementValue=" + autoincrementValue + ";allautoincrementValue=" + allautoincrementValue + ";incrementValue=" + incrementValue + ";addValue=" + addValue + ";userId=" + userId);
    }

    public void handleLoadPackage(LoadPackageParam loadPackageParam) {
        try {
            context = (Context) XposedHelpers.callMethod(XposedHelpers
                            .callStaticMethod(XposedHelpers.findClass(
                                    "android.app.ActivityThread", null),
                                    "currentActivityThread", new Object[0]),
                    "getSystemContext", new Object[0]);
            if (mXYLHookReceicver == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter
                        .addAction("com.anjoyo.xyl.run.HOOK_SETTING_CHANGED");
                mXYLHookReceicver = new XYLHookReceicver(this);
                context.registerReceiver(mXYLHookReceicver, intentFilter);
            }
        } catch (Exception e) {
            // TODO: handle exception
            XposedBridge.log(e);
        }
        initData();
        if (!incrementValue) {
            XposedBridge.log("加速关闭");
            return;
        }
        XposedBridge.log("loadpackageName=="
                + loadPackageParam.packageName);
        if (TextUtils.equals("com.yuedong.sport", loadPackageParam.packageName)) {
            final Class<?> openSignEL = XposedHelpers.findClass(
                    "com.yuedong.common.utils.OpenSign",
                    loadPackageParam.classLoader);
            if (openSignEL == null) {
                return;
            }
            XposedBridge.hookAllMethods(openSignEL, "makeSig",
                    new XC_MethodHook() {
                        @SuppressWarnings("unchecked")
                        @Override
                        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param)
                                throws Throwable {
                            XposedBridge.log("MainHook.addValue=="
                                    + MainHook.addValue);
                            if (MainHook.addValue < 1) {
                                return;
                            }
                            if (!TextUtils.equals(
                                    "/sport/report_runner_info_step_batch",
                                    param.args[1].toString())) {
                                return;
                            }
                            HashMap<String, String> hashMap = (HashMap<String, String>) param.args[2];
                            if (hashMap != null
                                    && hashMap.containsKey("steps_array_json")) {
                                JSONArray jsonArray = new JSONArray(hashMap
                                        .get("steps_array_json"));
                                if (jsonArray == null
                                        || jsonArray.length() != 1) {
                                    return;
                                }
                                if (!TextUtils.isEmpty(MainHook.userId)) {
                                    hashMap.put("user_id", MainHook.userId);
                                    hashMap.put("client_user_id", MainHook.userId);
                                }
                                JSONObject jsonObject = jsonArray
                                        .getJSONObject(0);
                                jsonObject.remove("step");
                                jsonObject.remove("cost_time");
                                jsonObject.put("step", MainHook.addValue);
                                jsonObject.put("cost_time", 864000);
                                hashMap.put("steps_array_json", "["
                                        + jsonObject.toString() + "]");
                                XposedBridge.log("newhashMap=="
                                        + hashMap.toString());
                                MainHook.addValue = 0;
                                String motifyContent = "0";
                                Intent intent = new Intent("com.anjoyo.xyl.run.SETTING_CHANGED");
                                intent.putExtra("content", motifyContent);
                                intent.putExtra("type", 1);
                                if (MainHook.context != null) {
                                    MainHook.context.sendBroadcast(intent);
                                }
                            }
                        }

                    });
        }
        Class<?> sensorEL;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            sensorEL = XposedHelpers.findClass(
                    "android.hardware.SystemSensorManager$SensorEventQueue",
                    loadPackageParam.classLoader);
        } else {
            sensorEL = XposedHelpers.findClass(
                    "android.hardware.SensorManager$ListenerDelegate",
                    loadPackageParam.classLoader);
        }
        if (sensorEL != null) {
            XposedBridge.hookAllMethods(sensorEL, "dispatchSensorEvent",
                    new RunMethodHook(context, this, loadPackageParam));
        }

    }

    public void initZygote(StartupParam startupParam) {
        mXSharedPreferences = new XSharedPreferences("com.anjoyo.xyl.run");
    }
}
