package com.anjoyo.xyl.run.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.anjoyo.xyl.run.R;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.umeng.message.PushAgent;

/**
 * @author xyl
 * @date 创建时间：2015年11月30日 上午12:06:22
 * @TODO
 */
public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		PushAgent.getInstance(this).onAppStart();
		setContentView(R.layout.splash);
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);
		SplashAdListener listener = new SplashAdListener() {
			@Override
			public void onAdDismissed() {
				Log.i("RSplashActivity", "onAdDismissed");
				jumpWhenCanClick();// 跳转至您的应用主界面
			}

			@Override
			public void onAdFailed(String arg0) {
				Log.i("RSplashActivity", "onAdFailed");
				jump();
			}

			@Override
			public void onAdPresent() {
				Log.i("RSplashActivity", "onAdPresent");
			}

			@Override
			public void onAdClick() {
				Log.i("RSplashActivity", "onAdClick");
				// 设置开屏可接受点击时，该回调可用
			}
		};

		/**
		 * 构造函数： new SplashAd(Context context, ViewGroup adsParent,
		 * SplashAdListener listener,String adPlaceId, boolean canClick);
		 */
		String adPlaceId = "2354640"; // 重要：不填写代码位id不能出广告
		new SplashAd(this, adsParent, listener, adPlaceId, true);

	}

	/**
	 * 当设置开屏可点击时，需要等待跳转页面关闭后，再切换至您的主窗口。故此时需要增加waitingOnRestart判断。
	 * 另外，点击开屏还需要在onRestart中调用jumpWhenCanClick接口。
	 */
	public boolean waitingOnRestart = false;
	private void jumpWhenCanClick() {
		if (this.hasWindowFocus() || waitingOnRestart) {
			this.startActivity(new Intent(SplashActivity.this,
					NaviStartActivity.class));
			this.finish();
		} else {
			waitingOnRestart = true;
		}

	}

	/**
	 * 不可点击的开屏，使用该jump方法，而不是用jumpWhenCanClick
	 */
	private void jump() {
		this.startActivity(new Intent(SplashActivity.this,
				NaviStartActivity.class));
		this.finish();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (waitingOnRestart) {
			jumpWhenCanClick();
		}
	}

}
