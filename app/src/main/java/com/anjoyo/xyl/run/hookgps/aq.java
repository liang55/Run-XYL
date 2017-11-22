package com.anjoyo.xyl.run.hookgps;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import de.robv.android.xposed.XposedHelpers;

class aq extends Handler {
    final /* synthetic */ ao a;

    aq(ao aoVar, Looper looper) {
        super(looper);
        this.a = aoVar;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case 3:
                try {
                    if (this.a.a != null) {
                        XposedHelpers.callMethod(this.a.a, "onNmeaReceived", new Object[]{Long.valueOf(System.currentTimeMillis()), "$GPRMC,000000.000,A,0000.0000,N,0000.0000,W,0.01,0.01,,,,A*4C\r\n"});
                        break;
                    }
                } catch (Throwable e) {
                    dn.log(e);
                    break;
                }
                break;
        }
    }
}
