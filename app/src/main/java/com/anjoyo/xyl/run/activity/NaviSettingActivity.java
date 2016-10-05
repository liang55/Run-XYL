package com.anjoyo.xyl.run.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anjoyo.xyl.run.R;
import com.umeng.analytics.MobclickAgent;

/**
 * 设置界面
 */
public class NaviSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.container, new SettingFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

}
