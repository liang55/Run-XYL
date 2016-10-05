package com.anjoyo.xyl.run.activity;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

public class SetReceicver extends BroadcastReceiver {
    private static Toast toast;

    public void onReceive(Context context, Intent intent) {
        int type = intent.getExtras().getInt("type");
        String content = intent.getExtras().getString("content");
        switch (type) {
            case 1:
                SharedPreferences mySharedPreferences = context.getSharedPreferences(
                        context.getPackageName() + "_preferences",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("addvalue", content);
                editor.commit();
                if (toast != null) {
                    toast.cancel();
                    toast.setText("增加悦动步数成功");
                } else {
                    toast = Toast.makeText(context, "增加悦动步数成功", Toast.LENGTH_LONG);
                }
                toast.show();
                break;
            default:
                if (!SettingFragment.isShowToast) {
                    return;
                }
                if (toast != null) {
                    toast.cancel();
                    toast.setText(content);
                } else {
                    toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
                }
                toast.show();
                break;
        }
    }
}
