package com.anjoyo.xyl.run.hookgps;

import android.location.GpsStatus.Listener;
import android.os.Handler;
import android.os.Looper;
import de.robv.android.xposed.XposedHelpers;
import java.util.Timer;
import java.util.TimerTask;

public class al {
    public Listener a;
    public boolean b;
    public boolean c;
    private Timer d;
    private String e;
    private Handler f;

    public al(Listener arg2, String arg3) {
        super();
        this.b = true;
        this.c = false;
        this.a = arg2;
        this.e = arg3;
        this.f = this.c();
    }

    public void a() {
        am v1 = new am(this);
        this.d = new Timer();
        this.d.schedule(((TimerTask)v1), 1080, 1006);
    }

    static Handler a(al arg1) {
        return arg1.f;
    }

    public void b() {
        if(this.a != null) {
            XposedHelpers.callMethod(this.a, "onGpsStatusChanged", new Object[]{Integer.valueOf(2)});
        }

        this.b = false;
        this.f = null;
        if(this.d != null) {
            this.d.cancel();
        }
    }

    private Handler c() {
        return new an(this, Looper.getMainLooper());
    }
}
