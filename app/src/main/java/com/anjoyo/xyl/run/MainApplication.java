package com.anjoyo.xyl.run;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class MainApplication extends Application {

	private ArrayList<Activity> activities = new ArrayList<Activity>();
	private static MainApplication instance;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MobclickAgent.setDebugMode(false);
		MobclickAgent.setCatchUncaughtExceptions(true);

		MobileAds.initialize(this,"ca-app-pub-6260442262526164~8983367539");
		Fabric.with(this, new Crashlytics());
		instance = this;
	}
	// 单例模式中获取唯一的MyApplication实例
	public static MainApplication getInstance() {
		if (null == instance) {
			instance = new MainApplication();
		}
		return instance;
	}
	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activities.add(activity);
	}
	public void deleteActivity(Activity activity) {
		activities.remove(activity);
	}
	// finish
	public void exit() {
		for (Activity activity : activities) {
			activity.finish();
		}
		activities.clear();
		MobclickAgent.onKillProcess(this);
		System.exit(0);
	}
}
