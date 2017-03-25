package com.anjoyo.xyl.run.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.anjoyo.xyl.run.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.umeng.message.PushAgent;

/**
 * @author xyl
 * @date 创建时间：2015年11月30日 上午12:06:22
 * @TODO
 */
public class SplashActivity extends Activity {
	private InterstitialAd mInterstitialAd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FirebaseAnalytics.getInstance(this);
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable();
		PushAgent.getInstance(this).onAppStart();
		setContentView(R.layout.splash);
		RelativeLayout adsParent = (RelativeLayout) this
				.findViewById(R.id.adsRl);
//		AdView adView = (AdView)findViewById(R.id.adview);
//		AdRequest request = new AdRequest.Builder()
//				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//				.build();
//		adView.loadAd(request);
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId(getResources().getString(R.string.splash_ad_unit_id));
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				jump();
			}

			@Override
			public void onAdFailedToLoad(int i) {
				super.onAdFailedToLoad(i);
				jump();
			}

			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				mInterstitialAd.show();
			}
		});
		requestNewInterstitial();
	}
	private void requestNewInterstitial() {
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("2477B0A81625A7779FB9E71E404DDF43")
				.build();
		mInterstitialAd.loadAd(adRequest);
	}
	/**
	 * 不可点击的开屏，使用该jump方法，而不是用jumpWhenCanClick
	 */
	private void jump() {
		startActivity(new Intent(SplashActivity.this,
				NaviStartActivity.class));
		finish();
	}
}
