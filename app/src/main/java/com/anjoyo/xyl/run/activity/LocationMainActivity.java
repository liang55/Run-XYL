package com.anjoyo.xyl.run.activity;

/**
 * Created by xyl on 2017/8/16 20:54
 * TODO
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
        import android.content.SharedPreferences;
        import android.os.Bundle;
import android.widget.Toast;

import com.anjoyo.xyl.run.R;
import com.anjoyo.xyl.run.hookgps.dn;
import com.anjoyo.xyl.run.util.NotiPrefrenceChangeUtil;

public class LocationMainActivity extends Activity {
    public static int[] a;
    private static SharedPreferences b;
    private static SharedPreferences.Editor c;
    private Context d;
    private static boolean e;

    static {
        LocationMainActivity.a = new int[]{0, 1, 2, 9, 3, 5, 6, 4, 10, 11, 12, 0, 7, 14, 8, 13};
        LocationMainActivity.e = false;
    }

    public LocationMainActivity() {
        super();
    }

    static SharedPreferences a() {
        return LocationMainActivity.b;
    }

    static SharedPreferences a(SharedPreferences arg0) {
        LocationMainActivity.b = arg0;
        return arg0;
    }

    static Editor a(Editor arg0) {
        LocationMainActivity.c = arg0;
        return arg0;
    }

    static boolean a(boolean arg0) {
        LocationMainActivity.e = arg0;
        return arg0;
    }

    static Editor b() {
        return LocationMainActivity.c;
    }

    protected void onCreate(Bundle arg4) {
        super.onCreate(arg4);
        this.d = ((Context)this);
        setContentView(R.layout.activity_main);
        this.getFragmentManager().beginTransaction().replace(R.id.container, new LocationSettingsFragment()).commit();
        if (!NotiPrefrenceChangeUtil.isModuleActive()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNegativeButton("打开框架", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try{
                        ComponentName componentName = new ComponentName("de.robv.android.xposed.installer", "de.robv.android.xposed.installer.WelcomeActivity");
                        Intent intent = new Intent();
                        intent.setComponent(componentName);
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "打开X框架失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setTitle("提示");
            builder.setPositiveButton("忽略",null);
            builder.setMessage("模块尚未激活，前往Xposed框架的模块列表重新勾选并重启手机，否则模块不生效！");
            builder.show();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
        if(LocationMainActivity.e) {
            LocationMainActivity.e = false;
            if(LocationMainActivity.c != null) {
                LocationMainActivity.c.putLong("6", System.currentTimeMillis());
                LocationMainActivity.c.commit();
            }

            dn.a(this.d);
        }

        dn.i();
    }

    protected void onResume() {
        super.onResume();
    }
}

