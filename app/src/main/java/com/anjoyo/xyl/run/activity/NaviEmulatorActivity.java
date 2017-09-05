package com.anjoyo.xyl.run.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviInfo;
import com.anjoyo.xyl.run.MainApplication;
import com.anjoyo.xyl.run.R;
import com.anjoyo.xyl.run.TTSController;
import com.anjoyo.xyl.run.util.Converter;
import com.anjoyo.xyl.run.util.NotiPrefrenceChangeUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Random;

/**
 * 模拟导航显示界面
 */
public class NaviEmulatorActivity extends Activity
        implements
        AMapNaviViewListener,
        OnClickListener {
    private AMapNaviView mAmapAMapNaviView;
    private LocationManager mLocationManager;
    private int speedValue = 100;
    private int maxSpeedValue = 100;
    private int minSpeedValue = 0;
    private TextView speedTv;
    private ImageView addBt, desBt;
    private Toast mToast;
    private boolean controlIsFromMockProvider;
    private boolean altitudeOpen;
    private CheckBox altitudeCb;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int roomInt = (int) (Math.random() * 1) + 1;
            int roomInt0 = (int) (Math.random() * 1) + 1;
            if (roomInt + speedValue > maxSpeedValue) {
                speedValue -= roomInt;
            } else if (roomInt - speedValue < minSpeedValue) {
                speedValue += roomInt;
            } else if (roomInt0 % 2 == 0) {
                speedValue -= roomInt;
            } else {
                speedValue += roomInt;
            }
            initSpeedView();
            handler.sendEmptyMessageDelayed(0, 60 * 1000l);
        }
    };

    public boolean isactive() {
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToast = Toast.makeText(this, "请在手机设置里打开允许模拟位置", Toast.LENGTH_SHORT);
        try {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            mLocationManager.addTestProvider("gps", false, false, false, false,
//                    false, false, false, 0, 0);
            String providerStr = LocationManager.GPS_PROVIDER;
            LocationProvider provider = mLocationManager.getProvider(providerStr);
            if (provider != null) {
                mLocationManager.addTestProvider(
                        provider.getName()
                        , provider.requiresNetwork()
                        , provider.requiresSatellite()
                        , provider.requiresCell()
                        , provider.hasMonetaryCost()
                        , provider.supportsAltitude()
                        , provider.supportsSpeed()
                        , provider.supportsBearing()
                        , provider.getPowerRequirement()
                        , provider.getAccuracy());
            } else {
                mLocationManager.addTestProvider(
                        providerStr
                        , true, true, false, false, true, true, true
                        , Criteria.POWER_HIGH, Criteria.ACCURACY_FINE);
            }
            mLocationManager.setTestProviderEnabled(providerStr, true);
            SensorManager sensor_manager_original = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            controlIsFromMockProvider = true;
            editor.putBoolean("controlIsFromMockProvider", controlIsFromMockProvider);
            editor.commit();
            NotiPrefrenceChangeUtil.refreshPrefrence();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            mLocationManager = null;
            mToast.show();
        }
        setContentView(R.layout.activity_navistander);
        init(savedInstanceState);
        MainApplication.getInstance().addActivity(this);
        handler.sendEmptyMessageDelayed(0, 60 * 1000l);
//        if (isactive()){
//            mToast.setText("打开框架模块，位置修改更通用");
//            mToast.show();
//        }
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    private void init(Bundle savedInstanceState) {
        altitudeCb = (CheckBox) findViewById(R.id.emulator_altitude_cb);
        altitudeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                altitudeOpen = b;
            }
        });
        speedTv = (TextView) findViewById(R.id.speed_value_tv);
        addBt = (ImageView) findViewById(R.id.speed_value_add_bt);
        desBt = (ImageView) findViewById(R.id.speed_value_des_bt);
        addBt.setOnClickListener(this);
        desBt.setOnClickListener(this);
        findViewById(R.id.emulator_about_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NaviEmulatorActivity.this, LocationMainActivity.class));
            }
        });
        mAmapAMapNaviView = (AMapNaviView) findViewById(R.id.standernavimap);
        mAmapAMapNaviView.onCreate(savedInstanceState);
        mAmapAMapNaviView.setAMapNaviViewListener(this);// 语音播报开始
        TTSController.getInstance(this).startSpeaking();
        if (getIntent().hasExtra("speedValue")) {
            // 设置模拟速度
            try {
                speedValue = Integer.valueOf(getIntent().getStringExtra(
                        "speedValue"));
            } catch (Exception e) {
                // TODO: handle exception
                speedValue = 5;
            }
            minSpeedValue = speedValue - 2;
            maxSpeedValue = speedValue + 2;
        }
        initSpeedView();
        // 开启模拟导航
        AMapNavi.getInstance(this).startNavi(AMapNavi.EmulatorNaviMode);
        AMapNavi.getInstance(this).setAMapNaviListener(new AMapNaviListener() {

            @Override
            public void onTrafficStatusUpdate() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartNavi(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onReCalculateRouteForYaw() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onReCalculateRouteForTrafficJam() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
                // TODO Auto-generated method stub
            }

            double altitude = 0;
            float bearing = 0;
            float accuracy = 0;
            float speed = 0;

            private Location getLoc(String paramString,
                                    NaviInfo aMapNaviLocation) {
                speed = speedValue / 3.6f;
                if (altitudeOpen) {
                    int roomInt = (int) (Math.random() * 5) + 1;
                    int roomInt0 = (int) (Math.random() * 1) + 1;
                    if (roomInt0 % 2 == 0) {
                        altitude -= (roomInt / 10.0f);
                    } else {
                        altitude += (roomInt / 10.0f);
                    }
                    if (altitude < 0 || altitude > 10000) {
                        altitude = (roomInt / 10.0f);
                    }
                }
                int roomInt = (int) (Math.random() * 360);
                bearing = roomInt;
                roomInt = (int) (Math.random() * 100) + 1;
                accuracy = 0.1f * roomInt;
                Location localLocation = new Location(paramString);
                HashMap<String, Double> map = (HashMap<String, Double>) Converter
                        .gcj_decrypt(aMapNaviLocation.getCoord().getLatitude(),
                                aMapNaviLocation.getCoord().getLongitude());
                localLocation.setLatitude(map.get("lat"));
                localLocation.setLongitude(map.get("lon"));
                localLocation.setAltitude(altitude);
                localLocation.setBearing(bearing);
                localLocation.setSpeed(speed);
                localLocation.setAccuracy(accuracy);
                localLocation.setTime(System.currentTimeMillis());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    localLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                }
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NaviEmulatorActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("latitude", (float) localLocation.getLatitude());
                editor.putFloat("longtitude", (float) localLocation.getLongitude());
                editor.commit();
                return localLocation;
            }

            private double[] getRandomLocation() {
                double x1, y1, x2, y2;

                Random rdr = new Random();// rdr为一组随机数流
                Random rdz = new Random();
                int Z = rdz.nextInt(360);// 随机数的生成范围小于360
                double R2 = 1 + rdr.nextDouble() * 10;// x2街道
                double R1 = 9 + rdr.nextDouble() * 20;// x1城镇
                if (Z <= 90 && Z >= 0) {
                    x1 = R1 * Math.sin(Z * Math.PI / 180);// (Z*Math.PI/180)为弧度制
                    y1 = R1 * Math.cos(Z * Math.PI / 180);
                    x2 = R2 * Math.sin(Z * Math.PI / 180);
                    y2 = R2 * Math.cos(Z * Math.PI / 180);
                } else if (Z <= 180 && Z > 90) {
                    x1 = R1 * Math.cos((Z - 90) * Math.PI / 180);
                    y1 = R1 * Math.sin((Z - 90) * Math.PI / 180) * -1;
                    x2 = R2 * Math.cos((Z - 90) * Math.PI / 180);
                    y2 = R2 * Math.sin((Z - 90) * Math.PI / 180) * -1;

                } else if (Z <= 270 && Z > 180) {
                    x1 = R1 * Math.sin((Z - 180) * Math.PI / 180) * -1;
                    y1 = R1 * Math.cos((Z - 180) * Math.PI / 180) * -1;
                    x2 = R2 * Math.sin((Z - 180) * Math.PI / 180) * -1;
                    y2 = R2 * Math.cos((Z - 180) * Math.PI / 180) * -1;

                } else {
                    x1 = R1 * Math.cos((Z - 270) * Math.PI / 180) * -1;
                    y1 = R1 * Math.sin((Z - 270) * Math.PI / 180);
                    x2 = R2 * Math.cos((Z - 270) * Math.PI / 180) * -1;
                    y2 = R2 * Math.sin((Z - 270) * Math.PI / 180);
                }
                double latitude1 = 0.73175 + x1 / 111;
                double longitude1 = 51.303065 + y1 / (111 * Math.cos(Z * Math.PI / 180));
                return new double[]{latitude1, longitude1};
            }

            private Location getLoc(String paramString,
                                    AMapNaviLocation aMapNaviLocation) {
                speed = speedValue / 3.6f;
                if (altitudeOpen&&aMapNaviLocation.getAltitude() <= 0) {
                    int roomInt = (int) (Math.random() * 5) + 1;
                    int roomInt0 = (int) (Math.random() * 1) + 1;
                    if (roomInt0 % 2 == 0) {
                        altitude -= (roomInt / 10.0f);
                    } else {
                        altitude += (roomInt / 10.0f);
                    }
                    if (altitude < 0 || altitude > 10000) {
                        altitude = (roomInt / 10.0f);
                    }

                } else {
                    altitude = aMapNaviLocation.getAltitude();
                }
                if (aMapNaviLocation.getBearing() <= 0) {
                    int roomInt = (int) (Math.random() * 360);
                    bearing = roomInt;
                } else {
                    bearing = aMapNaviLocation.getBearing();
                }
                if (aMapNaviLocation.getAccuracy() <= 0) {
                    int roomInt = (int) (Math.random() * 100) + 1;
                    accuracy = 0.1f * roomInt;
                } else {
                    accuracy = aMapNaviLocation.getAccuracy();
                }
                Location localLocation = new Location(paramString);
                HashMap<String, Double> map = (HashMap<String, Double>) Converter
                        .gcj_decrypt(aMapNaviLocation.getCoord().getLatitude(),
                                aMapNaviLocation.getCoord().getLongitude());
                localLocation.setLatitude(map.get("lat"));
                localLocation.setLongitude(map.get("lon"));
                localLocation.setAltitude(altitude);
                localLocation.setBearing(bearing);
                localLocation.setSpeed(speed);
                localLocation.setAccuracy(accuracy);
                localLocation.setTime(System.currentTimeMillis());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    localLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                }
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NaviEmulatorActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("latitude", (float) localLocation.getLatitude());
                editor.putFloat("longtitude", (float) localLocation.getLongitude());
                editor.commit();
                return localLocation;
            }

            @Override
            public void onNaviInfoUpdate(NaviInfo aMapNaviLocation) {
                // TODO Auto-generated method stub
                // Log.d("xxx", "导航位置更新0-----"
                // + aMapNaviLocation.getCoord().getLatitude() + "--"
                // + aMapNaviLocation.getCoord().getLongitude() + "---"
                // + aMapNaviLocation.m_CameraSpeed);
                if (mLocationManager != null) {
                    try {
                        Location localLocation = getLoc(LocationManager.GPS_PROVIDER, aMapNaviLocation);
//                        Log.d("xxx", "导航位置更新2-----" + localLocation.toString() + "==" + bearing + "==" + accuracy + "==" + speed + "===" + altitude);
                        mLocationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER,
                                localLocation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    mToast.show();
                }

            }

            @Override
            public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
                // TODO Auto-generated method stub
                // Log.d("xxx", "导航位置更新1-----" + aMapNaviLocation.toString());
                try {
                    if (mLocationManager != null) {
                        Location localLocation = getLoc(LocationManager.GPS_PROVIDER, aMapNaviLocation);
//                        Log.d("xxx", "导航位置更新1-----" + localLocation.toString() + "==" + bearing + "==" + accuracy + "==" + speed + "===" + altitude);
                        mLocationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER,
                                localLocation);
                    } else {
                        mToast.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onInitNaviSuccess() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onInitNaviFailure() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGpsOpenStatus(boolean arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onGetNavigationText(int arg0, String arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onEndEmulatorNavi() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onCalculateRouteSuccess() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onCalculateRouteFailure(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onArrivedWayPoint(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onArriveDestination() {
                // TODO Auto-generated method stub

            }
        });
        // 开启实时导航
        // AMapNavi.getInstance(this).startNavi(AMapNavi.GPSNaviMode);

    }

    /**
     * 导航界面返回按钮监听
     */
    @Override
    public void onNaviCancel() {
        MainApplication.getInstance().deleteActivity(this);
        finish();
    }

    @Override
    public void onNaviSetting() {

    }

    @Override
    public void onNaviMapMode(int arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * 返回键监听事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Intent intent = new Intent(NaviEmulatorActivity.this,
            // NaviStartActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            // startActivity(intent);
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // ------------------------------生命周期方法---------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAmapAMapNaviView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAmapAMapNaviView.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAmapAMapNaviView.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        try {
            this.mLocationManager.removeTestProvider("gps");
        } catch (Exception localException) {
        }
        mAmapAMapNaviView.onDestroy();
        // 界面结束 停止语音播报
        TTSController.getInstance(this).stopSpeaking();
        controlIsFromMockProvider = false;
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + "_preferences",
                Activity.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("controlIsFromMockProvider", controlIsFromMockProvider);
        editor.commit();
        NotiPrefrenceChangeUtil.refreshPrefrence();
    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onScanViewButtonClick() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLockMap(boolean arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.speed_value_add_bt:
                speedValue += 1;
                initSpeedView();
                break;
            case R.id.speed_value_des_bt:
                speedValue -= 1;
                initSpeedView();
                break;
            default:
                break;
        }
    }

    private void initSpeedView() {
        AMapNavi.getInstance(this).setEmulatorNaviSpeed(speedValue);
        speedTv.setText(speedValue + "km/h");
        if (speedValue >= 100) {
            addBt.setEnabled(false);
        } else {
            addBt.setEnabled(true);
        }
        if (speedValue <= 5) {
            desBt.setEnabled(false);
        } else {
            desBt.setEnabled(true);
        }
    }
}
