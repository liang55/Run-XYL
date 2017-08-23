package com.anjoyo.xyl.run.hookgps;

import android.location.GpsStatus.NmeaListener;
import android.os.Handler;
import android.os.Looper;
import java.util.Timer;
import java.util.TimerTask;

public class ao {
    public NmeaListener a;
    public boolean b;
    private Timer c;
    private String d;
    private Handler e;

    public ao(NmeaListener nmeaListener, String str) {
        this.b = true;
        this.a = nmeaListener;
        this.d = str;
        this.e = c();
    }

    private Handler c() {
        return new aq(this, Looper.getMainLooper());
    }

    public void a() {
        TimerTask apVar = new ap(this);
        this.c = new Timer();
        this.c.schedule(apVar, 1000, 1006);
    }
    static Handler a(ao arg1) {
        return arg1.e;
    }
    public void b() {
        this.b = false;
        this.e = null;
        if (this.c != null) {
            this.c.cancel();
        }
    }

}
