package com.anjoyo.xyl.run.hookgps;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import de.robv.android.xposed.XposedHelpers;

class an extends Handler {
    final /* synthetic */ al a;

    an(al alVar, Looper looper) {
        super(looper);
        this.a = alVar;
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 2:
                try {
                    if (this.a.a != null) {
                        if (!this.a.c) {
                            XposedHelpers.callMethod(this.a.a, "onGpsStatusChanged", new Object[]{Integer.valueOf(3)});
                            this.a.c = true;
                        }
                        XposedHelpers.callMethod(this.a.a, "onGpsStatusChanged", new Object[]{Integer.valueOf(4)});
                        break;
                    }
                } catch (Throwable e) {
                    dn.a(e);
                    break;
                }
                break;
        }
        super.handleMessage(message);
    }
}
