package com.anjoyo.xyl.run.activity; /**
 * Copyright (C) 2014 Samsung Electronics Co., Ltd. All rights reserved.
 * <p>
 * Mobile Communication Division,
 * Digital Media & Communications Business, Samsung Electronics Co., Ltd.
 * <p>
 * This software and its documentation are confidential and proprietary
 * information of Samsung Electronics Co., Ltd.  No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Samsung Electronics.
 * <p>
 * Samsung Electronics makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 */

import com.anjoyo.xyl.run.R;
import com.anjoyo.xyl.run.util.StepCountReader;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants.StepCount;
import com.samsung.android.sdk.healthdata.HealthDataService;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthPermissionManager.PermissionKey;
import com.samsung.android.sdk.healthdata.HealthPermissionManager.PermissionResult;
import com.samsung.android.sdk.healthdata.HealthPermissionManager.PermissionType;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PointerIconCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;


public class SamsungStepActivity extends AppCompatActivity {

    public static final String TAG = "StepDiary";

    private TextView mStepCountTv;
    private TextView mDayTv;
    private ListView mBinningListView;

    private HealthDataStore mStore;
    private StepCountReader mReporter;
    private long mCurrentStartTime;
    private BinningListAdapter mBinningListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samsung_step);
        initView();

        // Get the start time of today in local
        mCurrentStartTime = StepCountReader.TODAY_START_UTC_TIME;
        mDayTv.setText(getFormattedTime());

        HealthDataService healthDataService = new HealthDataService();
        try {
            healthDataService.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Create a HealthDataStore instance and set its listener
        mStore = new HealthDataStore(this, mConnectionListener);

        // Request the connection to the health data store
        mStore.connectService();
        mReporter = new StepCountReader(mStore, mStepCountObserver, this);

        mBinningListAdapter = new BinningListAdapter();
        mBinningListView.setAdapter(mBinningListAdapter);
    }

    private void initView() {
        mStepCountTv = (TextView) findViewById(R.id.total_step_count);
        mDayTv = (TextView) findViewById(R.id.date_view);
        mBinningListView = (ListView) findViewById(R.id.binning_list);
        findViewById(R.id.move_before).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentStartTime -= StepCountReader.ONE_DAY;
                mDayTv.setText(getFormattedTime());
                mBinningListAdapter.changeDataSet(Collections.<StepCountReader.StepBinningData>emptyList());
                mReporter.requestDailyStepCount(mCurrentStartTime);
            }
        });

        findViewById(R.id.move_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentStartTime += StepCountReader.ONE_DAY;
                mDayTv.setText(getFormattedTime());
                mBinningListAdapter.changeDataSet(Collections.<StepCountReader.StepBinningData>emptyList());
                mReporter.requestDailyStepCount(mCurrentStartTime);
            }
        });
        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFloatingButton();
            }
        });
    }

    @Override
    public void onDestroy() {
        mStore.disconnectService();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mReporter.requestDailyStepCount(mCurrentStartTime);
    }


    void onClickFloatingButton() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.setMargins(16, 10, 16, 10);
        linearLayout.setLayoutParams(layoutParams);
        EditText inputServer = new EditText(this);
        linearLayout.addView(inputServer);
        inputServer.setFocusable(true);
        inputServer.setInputType(2);
        inputServer.setLayoutParams(layoutParams);
        inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        inputServer.setGravity(17);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle((CharSequence) "\u8bf7\u8f93\u5165\u8981\u589e\u52a0\u7684\u6b65\u6570[1,12000]\uff1a").setIcon((int) R.drawable.logo_ico).setView(linearLayout).setNegativeButton(getString(R.string.cancel), null);
        builder.setPositiveButton((CharSequence) "\u786e\u5b9a", new AnonymousClass2(inputServer));
        builder.show();
    }

    class AnonymousClass2 implements DialogInterface.OnClickListener {
        final /* synthetic */ EditText val$inputServer;

        AnonymousClass2(EditText editText) {
            this.val$inputServer = editText;
        }

        public void onClick(DialogInterface dialog, int which) {
            String inputName = this.val$inputServer.getText().toString();
            if (inputName.isEmpty()) {
                Toast.makeText(SamsungStepActivity.this, "请输入步数", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(inputName) > 12000 || Integer.parseInt(inputName) <= 0) {
                Toast.makeText(SamsungStepActivity.this, "步数范围在[1,12000]", Toast.LENGTH_SHORT).show();
            } else {
                Calendar now = Calendar.getInstance();
                Log.d("xyl", "CurrentTime : " + now.getTimeInMillis());
                long time = (long) (((((now.get(Calendar.HOUR_OF_DAY) * 3600) * PointerIconCompat.TYPE_DEFAULT) + ((now.get(Calendar.MINUTE) * 60) * PointerIconCompat.TYPE_DEFAULT)) + (now.get(Calendar.SECOND) * PointerIconCompat.TYPE_DEFAULT)) + now.get(Calendar.MILLISECOND));
                Log.d("xyl", "CurrentStartTime : " + SamsungStepActivity.this.mCurrentStartTime + " now:" + time);
                SamsungStepActivity.this.mReporter.writeStepCount(SamsungStepActivity.this.mCurrentStartTime + time, Integer.parseInt(inputName));
                SamsungStepActivity.this.onResume();
            }
        }
    }

    private final HealthDataStore.ConnectionListener mConnectionListener = new HealthDataStore.ConnectionListener() {
        @Override
        public void onConnected() {
            Log.d(TAG, "onConnected");
            if (isPermissionAcquired()) {
                mReporter.requestDailyStepCount(mCurrentStartTime);
            } else {
                requestPermission();
            }
        }

        @Override
        public void onConnectionFailed(HealthConnectionErrorResult error) {
            Log.d(TAG, "onConnectionFailed");
            showConnectionFailureDialog(error);
        }

        @Override
        public void onDisconnected() {
            Log.d(TAG, "onDisconnected");
            if (!isFinishing()) {
                mStore.connectService();
            }
        }
    };

    private String getFormattedTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd (E)", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return dateFormat.format(Long.valueOf(this.mCurrentStartTime));

    }

    private final StepCountReader.StepCountObserver mStepCountObserver = new StepCountReader.StepCountObserver() {
        @Override
        public void onChanged(int count) {
            updateStepCountView(String.valueOf(count));
        }

        @Override
        public void onBinningDataChanged(List<StepCountReader.StepBinningData> stepBinningDataList) {
            updateBinningChartView(stepBinningDataList);
        }
    };

    private void updateStepCountView(final String count) {
        // Display the today step count so far
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStepCountTv.setText(count);
            }
        });
    }

    private void updateBinningChartView(List<StepCountReader.StepBinningData> stepBinningDataList) {
        // the following code will be replaced with chart drawing code
        Log.d(TAG, "updateBinningChartView");
        mBinningListAdapter.changeDataSet(stepBinningDataList);
        for (StepCountReader.StepBinningData data : stepBinningDataList) {
            Log.d(TAG, "TIME : " + data.time + "  COUNT : " + data.count);
        }
    }

    private final HealthResultHolder.ResultListener<PermissionResult> mPermissionListener =
            new HealthResultHolder.ResultListener<PermissionResult>() {

                @Override
                public void onResult(PermissionResult result) {
                    Map<PermissionKey, Boolean> resultMap = result.getResultMap();
                    // Show a permission alarm and clear step count if permissions are not acquired
                    if (resultMap.values().contains(Boolean.FALSE)) {
                        updateStepCountView("");
                        showPermissionAlarmDialog();
                    } else {
                        // Get the daily step count of a particular day and display it
                        mReporter.requestDailyStepCount(mCurrentStartTime);
                    }
                }
            };

    private void showPermissionAlarmDialog() {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(SamsungStepActivity.this);
        alert.setTitle(R.string.notice)
                .setMessage(R.string.msg_perm_acquired)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private void showConnectionFailureDialog(final HealthConnectionErrorResult error) {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        if (error.hasResolution()) {
            switch (error.getErrorCode()) {
                case HealthConnectionErrorResult.PLATFORM_NOT_INSTALLED:
                    alert.setMessage(R.string.msg_req_install);
                    break;
                case HealthConnectionErrorResult.OLD_VERSION_PLATFORM:
                    alert.setMessage(R.string.msg_req_upgrade);
                    break;
                case HealthConnectionErrorResult.PLATFORM_DISABLED:
                    alert.setMessage(R.string.msg_req_enable);
                    break;
                case HealthConnectionErrorResult.USER_AGREEMENT_NEEDED:
                    alert.setMessage(R.string.msg_req_agree);
                    break;
                default:
                    alert.setMessage(R.string.msg_req_available);
                    break;
            }
        } else {
            alert.setMessage(R.string.msg_conn_not_available);
        }

        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (error.hasResolution()) {
                    error.resolve(SamsungStepActivity.this);
                }
            }
        });

        if (error.hasResolution()) {
            alert.setNegativeButton(R.string.cancel, null);
        }

        alert.show();
    }

    private boolean isPermissionAcquired() {
        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
        try {
            // Check whether the permissions that this application needs are acquired
            Map<PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(generatePermissionKeySet());
            return !resultMap.values().contains(Boolean.FALSE);
        } catch (Exception e) {
            Log.e(TAG, "Permission request fails.", e);
        }
        return false;
    }

    private void requestPermission() {
        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
        try {
            // Show user permission UI for allowing user to change options
            pmsManager.requestPermissions(generatePermissionKeySet(), SamsungStepActivity.this)
                    .setResultListener(mPermissionListener);
        } catch (Exception e) {
            Log.e(TAG, "Permission setting fails.", e);
        }
    }

    private Set<PermissionKey> generatePermissionKeySet() {
        Set<PermissionKey> pmsKeySet = new HashSet<>();
        pmsKeySet.add(new PermissionKey(StepCount.HEALTH_DATA_TYPE, PermissionType.WRITE));
        pmsKeySet.add(new PermissionKey(StepCountReader.STEP_SUMMARY_DATA_TYPE_NAME, PermissionType.WRITE));
        pmsKeySet.add(new PermissionKey(StepCount.HEALTH_DATA_TYPE, PermissionType.READ));
        pmsKeySet.add(new PermissionKey(StepCountReader.STEP_SUMMARY_DATA_TYPE_NAME, PermissionType.READ));

        return pmsKeySet;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        if (item.getItemId() == R.id.connect) {
            requestPermission();
        }else if (item.getItemId() == R.id.help) {
            onHelp();
        }else if (item.getItemId() == R.id.back) {
            finish();
        }

        return true;
    }
    public void onHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setTitle("帮助");
        builder.setMessage("软件原理:\n" +
                "本程序显示添加三星健康步数\n" +
                "_____________\n" +
                "首次使用请先打开三星健康的开发者模式，然后点击菜单，连接到三星健康，权限全部打开后点完成。\n" +
                "点击加号，输入步数，注意每次输入的步数范围在[1，12000],点击确定即可看到效果。\n" +
                "_____________\n" +
                "如何进入开发者模式：\n" +
                "打开三星健康，点击右上角菜单进入设置-关于三星健康，在中间可以看到版本号。\n" +
                "连续点击版本号十次，版本号前面会多出*(Developre Mode)*,说明已经打开开发者模式\n"+
                "_____________\n" +
                "修改支付宝步数：\n" +
                "三星健康+容器或者应用变量模块+支付宝选择步数来源是三星健康,详细教程QQ群:544175265\n\n");
        builder.show();
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    private class BinningListAdapter extends BaseAdapter {

        private List<StepCountReader.StepBinningData> mDataList = new ArrayList<>();

        void changeDataSet(List<StepCountReader.StepBinningData> dataList) {
            mDataList = dataList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, null);
            }

            ((TextView) convertView.findViewById(android.R.id.text1)).setText(mDataList.get(position).count + " steps");
            ((TextView) convertView.findViewById(android.R.id.text2)).setText(mDataList.get(position).time);
            return convertView;
        }
    }
}
