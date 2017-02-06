package com.anjoyo.xyl.run.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.anjoyo.xyl.run.BuildConfig;
import com.anjoyo.xyl.run.R;

/**
 * Created by xyl on 2016/10/5 14:52
 * TODO
 */

public class SettingFragment extends PreferenceFragment
        implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceChangeListener {
    private EditTextPreference mEditTextPreference;
    //    private EditTextPreference userEditTextPreference;
//    private EditTextPreference addValueTextPreference;
    private Preference verisionPreference;
    public static boolean isShowToast;

    public SettingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(1);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        addPreferencesFromResource(R.xml.preference);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditTextPreference = (EditTextPreference) findPreference("magnification");
//        userEditTextPreference = (EditTextPreference) findPreference("userid");
//        addValueTextPreference = (EditTextPreference) findPreference("addvalue");
        verisionPreference = (Preference) findPreference("versionNumber");
        verisionPreference.setSummary(BuildConfig.VERSION_NAME);
        changeSummary();
        isShowToast = true;
    }

    public void sendValue() {
//        http://report-segstep.51yund.com/sport/report_runner_info_step_batch
        //sign=JkoBVOOr4aIVZfq7BHjqY48fXDY%3D&phone_type=X9180&subtype=0&user_id=20091122&ver=3.1.2.8.366&kind_id=2&channel=channel_mianfeiduobao&xyy=smbgoa5r16hfwez73d&client_user_id=20091122&steps_array_json=%5B%7B%22run_ts%22%3A1465349927%2C%22cost_time%22%3A1%2C%22index%22%3A0%2C%22step%22%3A250%7D%2C%7B%22run_ts%22%3A1465350792%2C%22cost_time%22%3A0%2C%22index%22%3A1%2C%22step%22%3A100%7D%2C%7B%22run_ts%22%3A1465350824%2C%22cost_time%22%3A0%2C%22index%22%3A2%2C%22step%22%3A50%7D%5D
//        [{"run_ts":1465349927,"cost_time":1,"index":0,"step":250},{"run_ts":1465350792,"cost_time":0,"index":1,"step":100},{"run_ts":1465350824,"cost_time":0,"index":2,"step":50}]
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        isShowToast = false;
    }

    private void changeSummary() {
        if (this.mEditTextPreference != null) {
            this.mEditTextPreference.setSummary(getPreferenceManager()
                    .getSharedPreferences().getString("magnification", "50"));
//            this.userEditTextPreference.setSummary(getPreferenceManager()
//                    .getSharedPreferences().getString("userid", "默认自己UID"));
//            this.addValueTextPreference.setSummary(getPreferenceManager()
//                    .getSharedPreferences().getString("addvalue", "0"));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // TODO Auto-generated method stub
        changeSummary();
        getKey();
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        // TODO Auto-generated method stub
        changeSummary();
        getKey();
    }

    public void getKey() {
        Intent intent = new Intent("com.anjoyo.xyl.run.HOOK_SETTING_CHANGED");
        intent.putExtra("magnification", getPreferenceManager()
                .getSharedPreferences().getString("magnification", "50"));
        intent.putExtra("userid", getPreferenceManager()
                .getSharedPreferences().getString("userid", ""));
        intent.putExtra("addvalue", getPreferenceManager()
                .getSharedPreferences().getString("addvalue", "0"));
        intent.putExtra("increment", getPreferenceManager()
                .getSharedPreferences().getBoolean("increment", true));
        intent.putExtra("autoincrement", getPreferenceManager()
                .getSharedPreferences().getBoolean("autoincrement", false));
//        intent.putExtra("allautoincrement", getPreferenceManager()
//                .getSharedPreferences().getBoolean("allautoincrement", true));
        getActivity().sendBroadcast(intent);
    }

}
