package com.anjoyo.xyl.run.util;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.SparseArray;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

class RunMethodHook extends XC_MethodHook {
	final/* synthetic */LoadPackageParam mLoadPackageParam;
	final/* synthetic */MainHook mMainHook;
	final Context mContext;
	RunMethodHook(Context context, MainHook mainHook,
			LoadPackageParam loadPackageParam) {
		this.mMainHook = mainHook;
		this.mLoadPackageParam = loadPackageParam;
		this.mContext = context;
	}

	protected void beforeHookedMethod(MethodHookParam methodHookParam)
			throws Throwable {
		try {
			Sensor sensor = null;
			try {
				int intValue = ((Integer) methodHookParam.args[0]).intValue();
				Field declaredField = methodHookParam.thisObject.getClass()
						.getDeclaredField("mSensorsEvents");
				declaredField.setAccessible(true);
				sensor = ((SensorEvent) ((SparseArray) declaredField
						.get(methodHookParam.thisObject)).get(intValue)).sensor;
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
				float[] fArr;
				if (this.mLoadPackageParam.packageName
						.equals("cn.ledongli.ldl")) {
					MainHook.ldlCount++;
					if (MainHook.ldlCount % 2 == 0) {
						((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0] * 100.0f;
						fArr = (float[]) methodHookParam.args[1];
						fArr[2] = fArr[2] - 0.21875f;
						fArr = (float[]) methodHookParam.args[1];
						fArr[1] = fArr[1] - 0.875f;
					} else {
						((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0] * 10.0f;
						fArr = (float[]) methodHookParam.args[1];
						fArr[2] = fArr[2] + 20.0f;
						fArr = (float[]) methodHookParam.args[1];
						fArr[1] = fArr[1] - 0.28125f;
					}
				} else if (this.mLoadPackageParam.packageName
						.equals("com.yuedong.sport")) {
					MainHook.ydCount++;
					((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0] * 1000.0f;
					if (MainHook.ydCount % 2 == 0) {
						fArr = (float[]) methodHookParam.args[1];
						fArr[2] = fArr[2] - 0.21875f;
						fArr = (float[]) methodHookParam.args[1];
						fArr[1] = fArr[1] - 0.875f;
					} else {
						fArr = (float[]) methodHookParam.args[1];
						fArr[2] = fArr[2] + 20.0f;
						fArr = (float[]) methodHookParam.args[1];
						fArr[1] = fArr[1] - 0.28125f;
					}
				} else if (MainHook.allautoincrementValue) {
					MainHook.allCount++;
					((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0] * 1000.0f;
					if (MainHook.allCount % 2 == 0) {
						fArr = (float[]) methodHookParam.args[1];
						fArr[2] = fArr[2] - 0.21875f;
						fArr = (float[]) methodHookParam.args[1];
						fArr[1] = fArr[1] - 0.875f;
					} else {
						fArr = (float[]) methodHookParam.args[1];
						fArr[2] = fArr[2] + 20.0f;
						fArr = (float[]) methodHookParam.args[1];
						fArr[1] = fArr[1] - 0.28125f;
					}
				}
			} else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR
					|| sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
				if (this.mLoadPackageParam.packageName.equals("com.tencent.mm")) {
					if (!MainHook.autoincrementValue) {
						((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0]
								* ((float) MainHook.magnificationValue);
					} else if (MainHook.magnificationValue * MainHook.mmCount < Integer.MAX_VALUE) {
						((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0]
								+ ((float) (MainHook.magnificationValue * MainHook.mmCount));
						MainHook.mmCount++;
					} else {
						MainHook.mmCount = 0;
					}
				} else if (this.mLoadPackageParam.packageName
						.equals("com.tencent.mobileqq")) {
					if (!MainHook.autoincrementValue) {
						((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0]
								* ((float) MainHook.magnificationValue);
					} else if (MainHook.magnificationValue * MainHook.qqCount < Integer.MAX_VALUE) {
						((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0]
								+ ((float) (MainHook.magnificationValue * MainHook.qqCount));
						MainHook.qqCount++;
					} else {
						MainHook.qqCount = 0;
					}
				} else if ((this.mLoadPackageParam.packageName
						.equals("com.yuedong.sport"))
						|| (this.mLoadPackageParam.packageName
								.equals("cn.ledongli.ldl"))) {
					if (((float[]) methodHookParam.args[1])[0]
							* ((float) MainHook.magnificationValue) < 99998.0f) {
						((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0]
								* ((float) MainHook.magnificationValue);
					} else {
						((float[]) methodHookParam.args[1])[0] = 99998.0f;
					}
				} else if (MainHook.allautoincrementValue) {
					if (((float[]) methodHookParam.args[1])[0]
							* ((float) MainHook.magnificationValue) < 99998.0f) {
						((float[]) methodHookParam.args[1])[0] = ((float[]) methodHookParam.args[1])[0]
								* ((float) MainHook.magnificationValue);
					} else {
						((float[]) methodHookParam.args[1])[0] = 99998.0f;
					}
				}
				String motifyContent = this.mLoadPackageParam.packageName
						+ "修改值" + ((float[]) methodHookParam.args[1])[0];
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
