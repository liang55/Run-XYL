package com.anjoyo.xyl.run.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.anjoyo.xyl.run.R;
import com.anjoyo.xyl.run.util.OkHttpClientManager;
import com.anjoyo.xyl.run.util.OpenSign;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

public class SetYoDoNumActivity extends AppCompatActivity {
    private boolean start = false;
    private Button sendCodeBt;
    private EditText editText, resultEt;
    private BroadcastReceiver ydInfoReceiver;
    private String signkey;
    private int uid;
    private String xyy;
    private TextView signkeyTv, uidTv, xyyTv, curNumTv;
    private int cur_run = 0;
    private int cur_riding = 0;
    private int cur_step = 0;
    private long lastTime;
    private Handler mhHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    testSendSmsCode();
                    if (start) {
                        mhHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_yodo_num);
        initView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter
                .addAction("com.anjoyo.xyl.run.yd_info");
        ydInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int action = intent.getIntExtra("action", 0);
                if (action == 0) {
                    signkey = intent.getStringExtra("signkey");
                    signkeyTv.setText("悦动signkey: " + signkey);
                } else {
                    uid = intent.getIntExtra("uid", 0);
                    xyy = intent.getStringExtra("xyy");
                    uidTv.setText("悦动uid: " + uid);
                    xyyTv.setText("悦动xyy: " + xyy);
                }
            }
        };
        registerReceiver(ydInfoReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(ydInfoReceiver);
    }

    private void initView() {
        sendCodeBt = (Button) findViewById(R.id.sendcode_tv);
        editText = (EditText) findViewById(R.id.num_tv);
        signkeyTv = (TextView) findViewById(R.id.signkey_tv);
        uidTv = (TextView) findViewById(R.id.uid_tv);
        xyyTv = (TextView) findViewById(R.id.xyy_tv);
        resultEt = (EditText) findViewById(R.id.result_tv);
        curNumTv = (TextView) findViewById(R.id.cur_num_tv);
//        sendCodeBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (start) {
//                    start = false;
//                    sendCodeBt.setText("开始发送验证码");
//                    mhHandler.removeMessages(0);
//                } else {
//                    start = true;
//                    sendCodeBt.setText("停止发送验证码");
//                    mhHandler.sendEmptyMessage(0);
//
//                }
//            }
//        });
        findViewById(R.id.getyd_prama_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uid == 0 || TextUtils.isEmpty(signkey) || TextUtils.isEmpty(xyy)) {
                    try {
                        Toast.makeText(SetYoDoNumActivity.this, "打开悦动,返回即可查看同步参数", Toast.LENGTH_LONG).show();
                        Intent intent = getPackageManager().getLaunchIntentForPackage("com.yuedong.sport");
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(SetYoDoNumActivity.this, "没有安装悦动", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        findViewById(R.id.addnum_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (uid == 0 || TextUtils.isEmpty(signkey) || TextUtils.isEmpty(xyy)) {
                        try {
                            Toast.makeText(SetYoDoNumActivity.this, "参数不合法,需要同步悦动用户信息", Toast.LENGTH_LONG).show();
                            Intent intent = getPackageManager().getLaunchIntentForPackage("com.yuedong.sport");
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(SetYoDoNumActivity.this, "没有安装悦动", Toast.LENGTH_LONG).show();
                        }
                        return;
                    }
                    int num = Integer.valueOf(editText.getText().toString());
                    if (num == 0) {
                        Toast.makeText(SetYoDoNumActivity.this, "步数异常", Toast.LENGTH_LONG).show();
                        return;
                    }
//                    testSendFixNum(num);
                    get_user_run_aim_v2(num);
                } catch (Exception e) {
                    Toast.makeText(SetYoDoNumActivity.this, "步数不合法", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.activity_set_yodo_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (System.currentTimeMillis() - lastTime > 2000) {
                    Toast.makeText(SetYoDoNumActivity.this, "双击打开或关闭操作日志", Toast.LENGTH_SHORT).show();
                    lastTime = System.currentTimeMillis();
                } else {
                    if (resultEt.isShown()) {
                        resultEt.setVisibility(View.INVISIBLE);
                    } else {
                        resultEt.setVisibility(View.VISIBLE);
                    }
                    lastTime = 0;
                }
            }
        });
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开模拟跑步贴吧
                Uri uri = Uri.parse("http://tieba.baidu.com/f?kw=%C4%A3%C4%E2%C5%DC%B2%BD&amp;fr=home");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void testSendSmsCode() {
//        http://api.51yund.com/sport/send_phone_verify_code?
// sign=zeaUwNL6%2Ftf8JKtMi0gMkTys8sg%3D&os=4.4.2&phone=15090632060&source=android_app&phone_type=X9180&device_id=358376240911414&language=zh&sdk=19&ver=3.1.2.8.511
// &channel=channel_baidu&xyy=&client_user_id=-1
// sign=oQlheOiDEsKNQAYw2PBE9NZ%2FS2g%3D&os=4.4.2&phone=15090632060&source=android_app&phone_type=X9180&device_id=358376240911414&ver=3.1.2.8.511
// &language=zh&channel=channel_baidu&client_user_id=-1&xyy=
//        str==POST++++str2=/sport/send_phone_verify_code++++str3=g937qm5gw0ehzdo8vnu2&
//                09-23 16:24:16.529 6406-6406/? D/xxx: makeSig hashMap=={phone=15090632060, ver=3.1.2.8.366, phone_type=X9180, channel=channel_mianfeiduobao, client_user_id=131303126}
//        sign=chb6Z18EACXfc%2BnXkG9rnHSb88w%3D&phone=15090632060&ver=3.1.2.8.366&phone_type=X9180&channel=channel_mianfeiduobao&xyy=smbgoa5r16hfwez73d&client_user_id=131303126
//        sign=XouMFUCMzLrN%2FvoAY0lYa%2Bj%2BSsw%3D&phone=15090632060&ver=3.1.2.8.366&phone_type=X9180&channel=channel_mianfeiduobao&xyy=&client_user_id=-1

        HashMap<String, String> hashMap = new HashMap();
//        hashMap.put("os", "4.4.2");

        hashMap.put("phone", getTel());
        hashMap.put("ver", "3.1.2.8.366");
//        hashMap.put("source", "android_app");
        hashMap.put("phone_type", "X9180");
//        hashMap.put("device_id", "358376240911414");
//        hashMap.put("language", "zh");
        hashMap.put("channel", "channel_mianfeiduobao");
        hashMap.put("client_user_id", "-1");
        try {
            String sign = OpenSign.makeSig("POST", "/sport/send_phone_verify_code", hashMap);
            hashMap.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        hashMap.put("xyy", "");
        OkHttpClientManager.postAsyn("http://api.51yund.com/sport/send_phone_verify_code", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response) {
                Log.d("xxx", response.toString());
            }
        }, hashMap);
    }

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 返回手机号码
     */
    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    private static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + thrid;
    }

    private void testSendFixNum(final long num) {
//        cmd=report_deamon_record&source=android_app&phone_type=X9180&manufacturer=ZTE&ver=3.1.2.8.366&channel=channel_mianfeiduobao
// &msg=cur_step%20%3A%2010000&SDK_version=19&client_user_id=20091122&os_version=4.4.2
//        cmd=report_deamon_record&source=android_app&phone_type=X9180&manufacturer=ZTE&ver=3.1.2.8.366&channel=channel_mianfeiduobao&msg=cur_step%20%3A%2044&SDK_version=19&client_user_id=20091122&os_version=4.4.2
//        {phone_type=X9180, subtype=0, user_id=20091122, kind_id=2, ver=3.1.2.8.366, channel=channel_mianfeiduobao, steps_array_json=[{"run_ts":1474622141,"cost_time":2,"index":0,"step":9}], client_user_id=20091122}
//        /sport/get_runner_path_data++++str3=g937qm5gw0ehzdo8vnu2&
//         D/xxx: makeSig hashMap=={ver=3.1.2.8.366, phone_type=X9180, channel=channel_mianfeiduobao, client_user_id=20091122, runner_id=1351264971}
//         D/xxx: MainHook.addValue==0
//         D/xxx: makeSig str==POST++++str2=/sport/report_runner_info_step_batch++++str3=g937qm5gw0ehzdo8vnu2&
//         D/xxx: makeSig hashMap=={phone_type=X9180, subtype=0, user_id=20091122, kind_id=2, ver=3.1.2.8.366, channel=channel_mianfeiduobao, steps_array_json=[{"run_ts":1474622225,"cost_time":0,"index":0,"step":11}], client_user_id=20091122}
//        sign=xTc%2BiPI%2BIpkHs9mhCBNTmH7gpUg%3D&passwd=68bf7c606995a9bab889edaba1969798&phone=15311411876&source=android_app&phone_type=X9180&ver=3.1.2.8.366&channel=channel_mianfeiduobao&xyy=&client_user_id=-1
//        POST http://api.51yund.com/sport/login HTTP/1.1
//        sign=xTc%2BiPI%2BIpkHs9mhCBNTmH7gpUg%3D&passwd=68bf7c606995a9bab889edaba1969798&phone=15311411876&source=android_app&phone_type=X9180&ver=3.1.2.8.366&channel=channel_mianfeiduobao&xyy=&client_user_id=-1
//
//        POST http://report-segstep.51yund.com/sport/report_runner_info_step_batch HTTP/1.1
//        sign=uYI%2BbivhBL9Ji235vn%2FX%2FphCCRM%3D&phone_type=X9180&subtype=0&user_id=20091122&ver=3.1.2.8.366&kind_id=2&channel=channel_mianfeiduobao&xyy=smbgoa5r16hfwez73d&client_user_id=20091122&steps_array_json=%5B%7B%22run_ts%22%3A1474624594%2C%22cost_time%22%3A0%2C%22index%22%3A0%2C%22step%22%3A9%7D%5D
//        http://api.51yund.com/sport/login?sign=xTc+iPI+IpkHs9mhCBNTmH7gpUg=&passwd=68bf7c606995a9bab889edaba1969798&phone=15311411876&source=android_app&phone_type=X9180&ver=3.1.2.8.366&channel=channel_mianfeiduobao&xyy=&client_user_id=-1
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("phone_type", Build.MODEL);
        hashMap.put("subtype", "0");
        hashMap.put("user_id", uid + "");
        hashMap.put("ver", "3.1.2.8.366");
        hashMap.put("kind_id", "2");
        hashMap.put("channel", "channel_mianfeiduobao");
        RunBean runBean = new RunBean();
        runBean.setRun_ts(System.currentTimeMillis() / 1000 - num * 2);
        runBean.setCost_time(num * 2);
        runBean.setStep(num);
        runBean.setIndex(0);
        ArrayList<RunBean> runBeanActivity = new ArrayList();
        runBeanActivity.add(runBean);
        hashMap.put("steps_array_json", JSON.toJSONString(runBeanActivity));
        hashMap.put("client_user_id", uid + "");
//        hashMap.put("channel", "channel_baidu");
//        hashMap.put("SDK_version", "19");
//
//        hashMap.put("msg", "cur_step:" + num);
        try {
            String sign = OpenSign.makeSig("POST", "/sport/report_runner_info_step_batch", hashMap, signkey);
            hashMap.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hashMap.put("xyy", xyy);
        OkHttpClientManager.postAsyn("http://report-segstep.51yund.com/sport/report_runner_info_step_batch ", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
                Toast.makeText(SetYoDoNumActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
//                [{"cost_time":20000,"index":0,"run_ts":1475613429341,"step":10}]
//                {"code":0,"msg":"ok","flag":1,"cnt":0,"steps_array_resp":[{"index":0,"step":1130,"run_ts":1474624144,"cost_time":402,"code":0,"err":0,"runner_id":2018149395}],"next_cnt":10}
//                Toast.makeText(SetYoDoNumActivity.this, response, Toast.LENGTH_LONG).show();
//                resultEt.setText("");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("msg");
                    if (TextUtils.equals("ok", msg)) {
                        Toast.makeText(SetYoDoNumActivity.this, "修改成功，稍后重新打开悦动查看的增加步数", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SetYoDoNumActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resultEt.setText(resultEt.getText().append("report_runner_info_step_batch():").append("\n").append(response).append("\n"));
//                reportFixNum(num);
//                get_user_score();
                get_user_score();
//                get_user_run_aim_v2(num);
            }
        }, hashMap);
    }

    private void get_user_run_aim_v2(final long num) {
//        POST http://api.51yund.com/sport/get_user_run_aim_v2 HTTP/1.1
//        sign=bvSgwi3Id24%2FDSgdeXgvyKO4Sj8%3D&riding_distance=0.0&phone_type=HM2A&run_distance=0.0&user_id=20091122&ver=3.1.2.8.366&kind_id=100&channel=channel_mianfeiduobao&xyy=smbgoa5r16hfwez73d&client_user_id=20091122&step=2825
//        {riding_distance=0.0, phone_type=HM2A, run_distance=0.0, user_id=20091122, ver=3.1.2.8.366, kind_id=100, channel=channel_mianfeiduobao, client_user_id=20091122, step=3149}
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("riding_distance", cur_riding + "");
        hashMap.put("phone_type", Build.MODEL);
        hashMap.put("run_distance", cur_run + "");
        hashMap.put("user_id", uid + "");
        hashMap.put("ver", "3.1.2.8.366");
        hashMap.put("kind_id", "100");
        hashMap.put("channel", "channel_mianfeiduobao");
        hashMap.put("client_user_id", uid + "");
        hashMap.put("step", cur_step + "");
        try {
            String sign = OpenSign.makeSig("POST", "/sport/get_user_run_aim_v2", hashMap, signkey);
            hashMap.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hashMap.put("xyy", xyy);
        OkHttpClientManager.postAsyn("http://api.51yund.com/sport/get_user_run_aim_v2", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
                Toast.makeText(SetYoDoNumActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("day_infos");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        int kind_id = jsonArray.getJSONObject(i).getInt("kind_id");
                        if (kind_id == 0) {
                            cur_run = jsonArray.getJSONObject(i).getInt("today_distance");
                        } else if (kind_id == 2) {
                            cur_step = jsonArray.getJSONObject(i).getInt("today_step");
                        } else {
                            cur_riding = jsonArray.getJSONObject(i).getInt("today_distance");
                        }
                    }
                    reportFixNum(num);
                    curNumTv.setText("当前步数(重新打开悦动即可同步步数): " + cur_step);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resultEt.setText("");
                resultEt.setText(resultEt.getText().append("get_user_run_aim_v2()").append("\n").append(response.toString()).append("\n"));
            }
        }, hashMap);
    }

    private void get_user_score() {
//        POST http://api.51yund.com/ranklist/get_user_score HTTP/1.1
//        sign=VKh6JbMZ0XjmfBmPRjsXszzJUGc%3D&phone_type=HM2A&user_id=20091122&ver=3.1.2.8.366&channel=channel_mianfeiduobao&xyy=smbgoa5r16hfwez73d&client_user_id=20091122&cur_steps=471
//        {user_id=20091122, ver=3.1.2.8.366, phone_type=HM2A, channel=channel_mianfeiduobao, client_user_id=20091122, cur_steps=3149}
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("phone_type", Build.MODEL);
        hashMap.put("user_id", uid + "");
        hashMap.put("ver", "3.1.2.8.366");
        hashMap.put("channel", "channel_mianfeiduobao");
        hashMap.put("client_user_id", uid + "");
        hashMap.put("cur_steps", cur_step + "");
        try {
            String sign = OpenSign.makeSig("POST", "/ranklist/get_user_score", hashMap, signkey);
            hashMap.put("sign", sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hashMap.put("xyy", xyy);

        OkHttpClientManager.postAsyn("http://api.51yund.com/ranklist/get_user_score", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
                Toast.makeText(SetYoDoNumActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                resultEt.setText(resultEt.getText().append("get_user_score()").append("\n").append(response.toString()).append("\n"));
            }
        }, hashMap);
    }

    private void reportFixNum(final long num) {

        //        POST http://report.51yund.com/sport/report HTTP/1.1
//        cmd=report_deamon_record&source=android_app&phone_type=HM2A&manufacturer=Xiaomi&ver=3.1.2.8.366&channel=channel_mianfeiduobao&msg=cur_step%20%3A%20209&SDK_version=19&client_user_id=20091122&os_version=4.4.4
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("cmd", "report_deamon_record");
        hashMap.put("source", "android_app");
        hashMap.put("phone_type", Build.MODEL);
        hashMap.put("manufacturer", Build.BRAND);
        hashMap.put("ver", "3.1.2.8.366");
        hashMap.put("channel", "channel_mianfeiduobao");
        cur_step += num;
        hashMap.put("msg", "cur_step : " + cur_step);
        hashMap.put("SDK_version", "" + Build.VERSION.SDK_INT);
        hashMap.put("client_user_id", uid + "");
        hashMap.put("os_version", Build.VERSION.RELEASE + "");


        OkHttpClientManager.postAsyn("http://report.51yund.com/sport/report ", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
                Toast.makeText(SetYoDoNumActivity.this, "请求异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                resultEt.setText(resultEt.getText().append("report()").append("\n").append(response.toString()).append("\n"));
                testSendFixNum(num);
            }
        }, hashMap);
    }

    static class RunBean {
        //        "run_ts":1474622225,"cost_time":0,"index":0,"step":11
        private long run_ts;
        private long cost_time;
        private int index;
        private long step;

        public long getCost_time() {
            return cost_time;
        }

        public void setCost_time(long cost_time) {
            this.cost_time = cost_time;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getRun_ts() {
            return run_ts;
        }

        public void setRun_ts(long run_ts) {
            this.run_ts = run_ts;
        }

        public long getStep() {
            return step;
        }

        public void setStep(long step) {
            this.step = step;
        }
    }
}
