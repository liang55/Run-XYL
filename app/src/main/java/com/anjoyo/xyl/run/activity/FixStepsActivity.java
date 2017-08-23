package com.anjoyo.xyl.run.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.ApplicationInfo.DisplayNameComparator;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anjoyo.xyl.run.R;
import com.anjoyo.xyl.run.util.NotiPrefrenceChangeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Created by xyl on 2017/4/20 15:23
 * TODO
 */

public class FixStepsActivity extends AppCompatActivity {
    CheckBox a;
    CheckBox b;
    CheckBox c;
    CheckBox d;
    CheckBox e;
    TextView f;
    EditText g;
    Animation h;
    private SensorManager i;
    private Vibrator j;
    private SensorEventListener k;
    private SharedPreferences mySharedPreferences;

    public FixStepsActivity() {
        this.k = new c(this);
    }

    private void h() {
        try{
            ComponentName componentName = new ComponentName("de.robv.android.xposed.installer", "de.robv.android.xposed.installer.WelcomeActivity");
            Intent intent = new Intent();
            intent.setComponent(componentName);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "打开X框架失败", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean a() {
        return mySharedPreferences.getBoolean("isLock", false);
    }

    public void a(Boolean bool) {
        Editor edit = mySharedPreferences.edit();
        edit.putBoolean("isMove", bool.booleanValue());
        edit.putBoolean("increment", false);
        edit.commit();
        NotiPrefrenceChangeUtil.refreshPrefrence();
    }

    public void a(String str) {
        Editor edit = mySharedPreferences.edit();
        edit.putString("Ban", str);
        edit.commit();
        NotiPrefrenceChangeUtil.refreshPrefrence();
    }

    public Boolean b() {
        return Boolean.valueOf(mySharedPreferences.getBoolean("isStart", false));
    }

    public void b(Boolean bool) {
        Editor edit = mySharedPreferences.edit();
        edit.putBoolean("isSys", bool.booleanValue());
        edit.commit();
        NotiPrefrenceChangeUtil.refreshPrefrence();
    }

    public void b(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public Boolean c() {
        return Boolean.valueOf(mySharedPreferences.getBoolean("isSys", true));
    }

    public void c(Boolean bool) {
        Editor edit = mySharedPreferences.edit();
        edit.putBoolean("isLock", bool.booleanValue());
        edit.commit();
        NotiPrefrenceChangeUtil.refreshPrefrence();
    }

    public Boolean d() {
        return Boolean.valueOf(mySharedPreferences.getBoolean("isMove", false));
    }

    public void d(Boolean bool) {
        Editor edit = mySharedPreferences.edit();
        edit.putBoolean("isStart", bool.booleanValue());
        edit.putBoolean("increment", false);
        edit.commit();
        NotiPrefrenceChangeUtil.refreshPrefrence();
    }

    public String e() {
        return mySharedPreferences.getString("Time", "100");
    }

    public void e(Boolean bool) {
        Editor edit = mySharedPreferences.edit();
        edit.putBoolean("isBaoli", bool.booleanValue());
        edit.commit();
        NotiPrefrenceChangeUtil.refreshPrefrence();
    }

    public String f() {
        return mySharedPreferences.getString("Ban", "");
    }

    public Boolean g() {
        return Boolean.valueOf(mySharedPreferences.getBoolean("isBaoli", false));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mySharedPreferences = getSharedPreferences(getPackageName() + "_preferences",
                Activity.MODE_MULTI_PROCESS);
        setContentView(R.layout.fixsteps_layout);
        this.a = (CheckBox) findViewById(R.id.checkBox_keep_run);
        this.e = (CheckBox) findViewById(R.id.checkBox_lock);
        this.g = (EditText) findViewById(R.id.editText1);
        this.b = (CheckBox) findViewById(R.id.checkBox_mode_change);
        this.c = (CheckBox) findViewById(R.id.checkBox_keep_sys);
        this.d = (CheckBox) findViewById(R.id.checkBox_move);
        this.f = (TextView) findViewById(R.id.textView1);
        this.i = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.j = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.e.setChecked(a().booleanValue());
        this.c.setChecked(c().booleanValue());
        this.a.setChecked(b().booleanValue());
        this.b.setChecked(g().booleanValue());
        this.d.setChecked(d().booleanValue());
        this.g.setText(e());
        this.h = AnimationUtils.loadAnimation(this, R.anim.shake);
        this.e.setOnCheckedChangeListener(new e(this));
        this.d.setOnCheckedChangeListener(new f(this));
        this.b.setOnCheckedChangeListener(new g(this));
        this.c.setOnCheckedChangeListener(new h(this));
        this.a.setOnCheckedChangeListener(new i(this));
        if (!NotiPrefrenceChangeUtil.isModuleActive()) {
            Builder builder = new Builder(this);
            builder.setNegativeButton("打开框架", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    h();
                }
            });
            builder.setTitle("提示");
            builder.setPositiveButton("忽略",null);
            builder.setMessage("模块尚未激活，前往Xposed框架的模块列表重新勾选并重启手机，否则模块不生效！");
            builder.show();
        }
    }

    public void onHelp(View view) {
        Builder builder = new Builder(this);
        builder.setPositiveButton("确定", new k(this));
        builder.setTitle("帮助");
        builder.setMessage("软件原理:\n" +
                "本程序通过修改加速度传感器的数据,达到模拟摇动手机(刷计步器步数)的效果。\n" +
                "_____________\n" +
                "使用须知：\n" +
                "本程序为Xposed模块，需安装Xposed框架后在模块列表中启用才有效果,勾选模拟运动后便可退出程序。\n" +
                "_____________\n" +
                "*间隔时间：\n" +
                "一般情况下设置为100毫秒,大部分计步软件效果为一秒自动增加两步。\n" +
                "如果调整为6000,则可以达到自动模拟微信摇一摇的效果。\n" +
                "_____________\n" +
                "*锁定步数：\n" +
                "锁定步数开启后将锁定加速度传感器的数据(手机摇一摇功能将失效),让计步器的数据不再变化,需先开启模拟运动才有效。\n" +
                "_____________\n" +
                "*暴力模式：\n" +
                "开启暴力模式后将同时修改x、y、z三个方向的加速度，而且变动跨度增加1000倍。\n" +
                "如果模拟运动正常的话不建议开启，开启后可能导致一些计步器失效(例如微博运动)。\n" +
                "_____________\n" +
                "备注：\n" +
                "本程序理论通杀所有通过加速度传感器计算步数的软件，如果无效说明本软件可能被检测到，或者改软件使用其他传感器计步。\n" +
                "已测试有效的计步软件:平安好医生、悦动圈、微博运动、春雨计步器。\n");
        builder.show();
    }

    protected void onResume() {
        super.onResume();
        if (this.i != null) {
            this.i.registerListener(this.k, this.i.getDefaultSensor(1), 3);
        }
    }

    protected void onStop() {
        super.onStop();
        if (this.i != null) {
            this.i.unregisterListener(this.k);
        }
    }

    public void onsetwhite(View view) {
        List arrayList;
        if (c().booleanValue()) {
            arrayList = new ArrayList();
            for (ApplicationInfo applicationInfo : getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA)) {
                if ((applicationInfo.flags & 1) == 0) {
                    arrayList.add(applicationInfo);
                }
            }
        } else {
            arrayList = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        }
        Collections.sort(arrayList, new DisplayNameComparator(getPackageManager()));
        ArrayList arrayList2 = new ArrayList(Arrays.asList(f().split(",")));
        CharSequence[] charSequenceArr = new String[arrayList.size()];
        boolean[] zArr = new boolean[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            charSequenceArr[i] = ((ApplicationInfo) arrayList.get(i)).loadLabel(getPackageManager()).toString();
            zArr[i] = arrayList2.contains(((ApplicationInfo) arrayList.get(i)).packageName);
        }
        Builder builder = new Builder(this);
        builder.setTitle("选择不模拟的应用");
        builder.setMultiChoiceItems(charSequenceArr, zArr, new l(this, arrayList));
        builder.setPositiveButton("确定", new d(this));
        builder.show();
    }

    public void savetime(View view) {
        Editor edit = mySharedPreferences.edit();
        edit.putString("Time", this.g.getText().toString());
        edit.commit();
    }

    static class l implements DialogInterface.OnMultiChoiceClickListener {
        final /* synthetic */ FixStepsActivity a;
        private final /* synthetic */ List b;

        l(FixStepsActivity mainActivity, List list) {
            this.a = mainActivity;
            this.b = list;
        }

        public void onClick(DialogInterface dialogInterface, int i, boolean z) {
            ArrayList arrayList = new ArrayList(Arrays.asList(this.a.f().split(",")));
            if (z) {
                arrayList.add(((ApplicationInfo) this.b.get(i)).packageName);
            } else {
                arrayList.remove(((ApplicationInfo) this.b.get(i)).packageName);
            }
            Iterator it = arrayList.iterator();
            String str = "";
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (!str2.trim().isEmpty()) {
                    str = new StringBuilder(String.valueOf(str)).append(str2.trim()).append(",").toString();
                }
            }
            this.a.a(str);
        }
    }

    static class k implements DialogInterface.OnClickListener {
        final /* synthetic */ FixStepsActivity a;

        k(FixStepsActivity mainActivity) {
            this.a = mainActivity;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    static class i implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ FixStepsActivity a;

        i(FixStepsActivity mainActivity) {
            this.a = mainActivity;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            this.a.b(z ? "模拟运动已开启" : "模拟运动已关闭");
            this.a.d(Boolean.valueOf(z));
        }
    }

    static class f implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ FixStepsActivity a;

        f(FixStepsActivity mainActivity) {
            this.a = mainActivity;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            this.a.a(Boolean.valueOf(z));
        }
    }

    static class h implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ FixStepsActivity a;

        h(FixStepsActivity mainActivity) {
            this.a = mainActivity;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            this.a.b(Boolean.valueOf(z));
        }
    }

    static class g implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ FixStepsActivity a;

        g(FixStepsActivity mainActivity) {
            this.a = mainActivity;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            this.a.e(Boolean.valueOf(z));
        }
    }

    static class c implements SensorEventListener {
        final /* synthetic */ FixStepsActivity a;

        c(FixStepsActivity mainActivity) {
            this.a = mainActivity;
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] fArr = sensorEvent.values;
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            this.a.f.setText("x轴方向的重力加速度" + f + "\ny轴方向的重力加速度" + f2 + "\nz轴方向的重力加速度" + f3);
            if (Math.abs(f) > ((float) 13) || Math.abs(f2) > ((float) 13) || Math.abs(f3) > ((float) 13)) {
                this.a.f.startAnimation(this.a.h);
                if (this.a.d().booleanValue()) {
                    this.a.j.vibrate(200);
                }
            }
        }
    }

    static class d implements DialogInterface.OnClickListener {
        final /* synthetic */ FixStepsActivity a;

        d(FixStepsActivity mainActivity) {
            this.a = mainActivity;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    static class e implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ FixStepsActivity a;

        e(FixStepsActivity mainActivity) {
            this.a = mainActivity;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            this.a.c(Boolean.valueOf(z));
        }
    }
}
