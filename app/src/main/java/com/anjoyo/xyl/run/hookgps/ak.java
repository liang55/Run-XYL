package com.anjoyo.xyl.run.hookgps;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.reflect.Method;

class ak extends Handler {
    final /* synthetic */ ah a;

    ak(ah ahVar, Looper looper) {
        super(looper);
        this.a = ahVar;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case 1: {
                Bundle v0 = new Bundle();
                v0.putInt("satellites", 16);
                Method v1 = ah.a("onLocationChanged");
                Method v2 = ah.a("onStatusChanged");
                try {
                    if (this.a.a == null) {
                        return;
                    }
                    if (v1 == null) {
                        return;
                    }
                    v1.invoke(this.a.a, ah.a(ah.b(this.a)));
                    v2.invoke(this.a.a, "gps", Integer.valueOf(2), v0);
                } catch (Exception v0_1) {
                    dn.log(((Throwable) v0_1));
                }
            }
        }
    }
}
