package com.anjoyo.xyl.run.activity;

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.LocationManagerProxy;
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
import com.anjoyo.xyl.run.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

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
    private TextView speedTv;
    private ImageView addBt, desBt;
    private Toast mToast;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int roomInt = (int) (Math.random() * 3) + 1;
            if (roomInt + speedValue > maxSpeedValue) {
                speedValue -= roomInt;
            } else {
                speedValue += roomInt;
            }
            initSpeedView();
            handler.sendEmptyMessageDelayed(0, 60 * 1000l);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToast = Toast.makeText(this, "请在手机设置里打开允许模拟位置", Toast.LENGTH_SHORT);
        try {
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.addTestProvider("gps", false, false, false, false,
                    false, false, false, 0, 0);
            mLocationManager.setTestProviderEnabled("gps", true);
            SensorManager sensor_manager_original = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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
    }

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    private void init(Bundle savedInstanceState) {
        speedTv = (TextView) findViewById(R.id.speed_value_tv);
        addBt = (ImageView) findViewById(R.id.speed_value_add_bt);
        desBt = (ImageView) findViewById(R.id.speed_value_des_bt);
        addBt.setOnClickListener(this);
        desBt.setOnClickListener(this);
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
            maxSpeedValue = speedValue + 3;
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

            private Location getLoc(String paramString,
                                    NaviInfo aMapNaviLocation) {
                Location localLocation = new Location(paramString);
                HashMap<String, Double> map = (HashMap<String, Double>) Converter
                        .gcj_decrypt(aMapNaviLocation.getCoord().getLatitude(),
                                aMapNaviLocation.getCoord().getLongitude());
                localLocation.setLatitude(map.get("lat"));
                localLocation.setLongitude(map.get("lon"));
                localLocation.setAltitude(altitude);
                localLocation.setBearing(bearing);
                localLocation.setSpeed(aMapNaviLocation.m_CameraSpeed);
                localLocation.setAccuracy(accuracy);
                localLocation.setTime(System.currentTimeMillis());
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
                altitude = aMapNaviLocation.getAltitude();
                bearing = aMapNaviLocation.getBearing();
                accuracy = aMapNaviLocation.getAccuracy();
                Location localLocation = new Location(paramString);
                HashMap<String, Double> map = (HashMap<String, Double>) Converter
                        .gcj_decrypt(aMapNaviLocation.getCoord().getLatitude(),
                                aMapNaviLocation.getCoord().getLongitude());
                localLocation.setLatitude(map.get("lat"));
                localLocation.setLongitude(map.get("lon"));
                localLocation.setAltitude(altitude);
                localLocation.setBearing(bearing);
                localLocation.setSpeed(aMapNaviLocation.getSpeed());
                localLocation.setAccuracy(accuracy);
                localLocation.setTime(System.currentTimeMillis());
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
                        Location localLocation = getLoc("gps", aMapNaviLocation);
                        mLocationManager.setTestProviderLocation("gps",
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
                        Location localLocation = getLoc("gps", aMapNaviLocation);
                        mLocationManager.setTestProviderLocation("gps",
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
