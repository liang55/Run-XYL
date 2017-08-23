package com.anjoyo.xyl.run.activity;

/**
 * Created by xyl on 2017/8/16 20:53
 * TODO
 */

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.anjoyo.xyl.run.R;
import com.anjoyo.xyl.run.hookgps.dn;

public class LocationSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
    private String a;
    private String[] b;
    private String[] c;
    private String[] d;
    private String[] e;
    private String[] f;
    private String[] g;
    private String[] h;
    private String[] i;
    private String[] j;
    private String[] k;
    private String[] l;
    private String[] m;
    private String[] n;
    private String[] o;
    private String[] p;

    public LocationSettingsFragment() {
        super();
        this.i = new String[]{"onc0", "onc1", "onc2"};
        this.j = new String[]{"on0", "on1", "on2", "os0", "os1", "os2", "oc0", "oc1", "oc2", "keyp0", "keyp1", "keyp2", "key9"};
        this.k = new String[]{"key300", "key301", "key302"};
        this.l = new String[]{"key320", "key321", "key322"};
        this.m = new String[]{"key310", "key311", "key312"};
        this.n = new String[100];
        this.o = new String[]{"key8", "key10"};
        this.p = new String[]{"speed", "accuracy", "bearing", "altitude", "ratio", "rotationratio"};
    }

    private void a() {
        String v5;
        String v4;
        String v3_1;
        int v10 = 100;
        int v9 = 6;
        int v8 = 5;
        String v2 = LocationMainActivity.a().getString("floatingtype", "0");
        String v0 = this.b[Integer.valueOf(v2).intValue()];
        if(v0.equals("")) {
            v0 = this.a;
        }

        this.findPreference("floatingtype").setSummary(((CharSequence)v0));
        v0 = this.c[Integer.valueOf(LocationMainActivity.a().getString("floatingcolor", "0")).intValue()];
        if(v0.equals("")) {
            v0 = this.a;
        }

        this.findPreference("floatingcolor").setSummary(((CharSequence)v0));
        this.findPreference("rotationratio").setEnabled(v2.equals("1"));
        String[] v2_1 = this.j;
        int v3 = v2_1.length;
        int v0_1;
        for(v0_1 = 0; v0_1 < v3; ++v0_1) {
            this.findPreference(v2_1[v0_1]).setSummary(LocationMainActivity.a().getString(v2_1[v0_1], ""));
        }

        for(v0_1 = 0; v0_1 < v10; ++v0_1) {
            v2 = "key2" + v0_1;
            v3_1 = LocationMainActivity.a().getString(v2, "");
            if(v3_1.equals("")) {
                this.findPreference(((CharSequence)v2)).setSummary("");
            }
            else if(dn.e(v3_1)) {
                this.findPreference(((CharSequence)v2)).setSummary(((CharSequence)v3_1));
            }
            else {
                this.findPreference(((CharSequence)v2)).setSummary(this.a);
            }
        }

        v2_1 = this.p;
        v3 = v2_1.length;
        for(v0_1 = 0; v0_1 < v3; ++v0_1) {
            v4 = v2_1[v0_1];
            v5 = LocationMainActivity.a().getString(v4, "");
            if(v5.equals("")) {
                this.findPreference(((CharSequence)v4)).setSummary("");
            }
            else if(dn.i(v5)) {
                this.findPreference(((CharSequence)v4)).setSummary(((CharSequence)v5));
            }
            else {
                this.findPreference(((CharSequence)v4)).setSummary(this.a);
            }
        }

        v2_1 = this.o;
        v3 = v2_1.length;
        for(v0_1 = 0; v0_1 < v3; ++v0_1) {
            v4 = v2_1[v0_1];
            v5 = LocationMainActivity.a().getString(v4, "");
            if(v5.equals("")) {
                this.findPreference(((CharSequence)v4)).setSummary("");
            }
            else if(dn.f(v5)) {
                this.findPreference(((CharSequence)v4)).setSummary(((CharSequence)v5));
            }
            else {
                this.findPreference(((CharSequence)v4)).setSummary(this.a);
            }
        }

        v2_1 = this.i;
        v3 = v2_1.length;
        for(v0_1 = 0; v0_1 < v3; ++v0_1) {
            v4 = v2_1[v0_1];
            v5 = LocationMainActivity.a().getString(v4, "");
            if(v5.equals("")) {
                this.findPreference(((CharSequence)v4)).setSummary("");
            }
            else if(dn.h(v5)) {
                this.findPreference(((CharSequence)v4)).setSummary(((CharSequence)v5));
            }
            else {
                this.findPreference(((CharSequence)v4)).setSummary(this.a);
            }
        }

        for(v0_1 = 0; v0_1 < v10; ++v0_1) {
            v2 = LocationMainActivity.a().getString("key2" + v0_1, "");
            String[] v3_2 = v2.split(",");
            if(v2.equals("")) {
                this.n[v0_1] = this.d[v0_1];
            }
            else if(dn.g(v2)) {
                this.n[v0_1] = this.d[v0_1] + " - " + v3_2[v9];
            }
            else if(dn.e(v2)) {
                this.n[v0_1] = this.d[v0_1] + " - " + v3_2[v8];
            }
            else {
                this.n[v0_1] = this.d[v0_1] + " - " + this.a;
            }
        }

        int v2_2;
        for(v2_2 = 0; v2_2 < 3; ++v2_2) {
            v0 = "location" + v2_2;
            v3_1 = LocationMainActivity.a().getString("key2" + LocationMainActivity.a().getString(v0, "0"), "");
            Preference v0_2 = this.findPreference(((CharSequence)v0));
            ((ListPreference)v0_2).setEntries(this.n);
            if(v3_1.equals("")) {
                ((ListPreference)v0_2).setSummary("");
            }
            else if(dn.g(v3_1)) {
                ((ListPreference)v0_2).setSummary(v3_1.split(",")[v9]);
            }
            else if(dn.e(v3_1)) {
                ((ListPreference)v0_2).setSummary(v3_1.split(",")[v8]);
            }
            else {
                ((ListPreference)v0_2).setSummary(this.a);
            }
        }

        v2_1 = this.k;
        v3 = v2_1.length;
        for(v0_1 = 0; v0_1 < v3; ++v0_1) {
            this.findPreference(v2_1[v0_1]).setSummary(this.e[LocationMainActivity.a[Integer.valueOf(LocationMainActivity.a().getString(v2_1[v0_1], "0")).intValue()]]);
        }

        v2_1 = this.m;
        v3 = v2_1.length;
        for(v0_1 = 0; v0_1 < v3; ++v0_1) {
            this.findPreference(v2_1[v0_1]).setSummary(this.h[Integer.valueOf(LocationMainActivity.a().getString(v2_1[v0_1], "0")).intValue()]);
        }

        v2_1 = this.l;
        v3 = v2_1.length;
        for(v0_1 = 0; v0_1 < v3; ++v0_1) {
            this.findPreference(v2_1[v0_1]).setSummary(this.f[Integer.valueOf(LocationMainActivity.a().getString(v2_1[v0_1], "-1")).intValue() + 1]);
        }

        this.findPreference("operatorfix").setSummary(this.g[Integer.valueOf(LocationMainActivity.a().getString("operatorfix", "0")).intValue()]);
    }

    public void onCreate(Bundle arg3) {
        super.onCreate(arg3);
        this.getPreferenceManager().setSharedPreferencesMode(1);
        this.addPreferencesFromResource(R.xml.location_preference);
        LocationMainActivity.a(this.getPreferenceManager().getSharedPreferences());
        LocationMainActivity.a(LocationMainActivity.a().edit());
        this.b = this.getResources().getStringArray(R.array.list_floating);
        this.c = this.getResources().getStringArray(R.array.list_colors);
        this.a = "";
        this.d = this.getResources().getStringArray(R.array.list_entries);
        this.e = this.getResources().getStringArray(R.array.list_network);
        this.f = this.getResources().getStringArray(R.array.list_networktype);
        this.g = this.getResources().getStringArray(R.array.list_operatorfix);
        this.h = this.getResources().getStringArray(R.array.list_phone);
        this.a();
    }

    public void onPause() {
        super.onPause();
        LocationMainActivity.a().unregisterOnSharedPreferenceChangeListener(((OnSharedPreferenceChangeListener)this));
    }

    public void onResume() {
        super.onResume();
        LocationMainActivity.a().registerOnSharedPreferenceChangeListener(((OnSharedPreferenceChangeListener)this));
    }

    public void onSharedPreferenceChanged(SharedPreferences arg4, String arg5) {
        LocationMainActivity.a(true);
        if(arg5.startsWith("key2")) {
            String v0 = arg4.getString(arg5, "");
            if(dn.m(v0)) {
                LocationMainActivity.b().putString(arg5, v0 + ",0,0,0,note");
            }

            LocationMainActivity.b().apply();
        }

        this.a();
    }
}

