package com.anjoyo.xyl.run;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.umeng.analytics.MobclickAgent;

public class MainApplication extends Application {

	private ArrayList<Activity> activities = new ArrayList<Activity>();
	private static MainApplication instance;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MobclickAgent.setDebugMode(false);
		MobclickAgent.setCatchUncaughtExceptions(true);
		FirebaseApp.initializeApp(this);
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
