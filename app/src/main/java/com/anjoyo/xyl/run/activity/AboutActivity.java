package com.anjoyo.xyl.run.activity;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import com.anjoyo.xyl.run.BuildConfig;
import com.anjoyo.xyl.run.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by xyl on 2017/4/20 10:38
 * TODO
 */

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.container, new AboutFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
   static public class AboutFragment extends PreferenceFragment {
        private Preference verisionPreference;
        public AboutFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesMode(1);
            addPreferencesFromResource(R.xml.about_preference);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            verisionPreference =findPreference("versionNumber");
            verisionPreference.setSummary(BuildConfig.VERSION_NAME);
        }
    }

}
