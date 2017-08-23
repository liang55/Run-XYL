package com.anjoyo.xyl.run.hookgps;

import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import de.robv.android.xposed.XC_MethodHook;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

public class ah {
    public LocationListener a;
    public boolean b;
    public static XC_MethodHook c;
    private Timer d;
    private String e;
    private Handler f;
    private doClass g;

    static {
        ah.c = new ai();
    }

    public ah(LocationListener arg2, String arg3, doClass arg4) {
        super();
        this.b = true;
        this.a = arg2;
        this.e = arg3;
        this.f = this.d();
        this.g = arg4;
    }

    public static Location a(doClass arg5) {
        Location v0 = new Location("gps");
        v0.setLatitude(arg5.t);
        v0.setLongitude(arg5.u);
        Bundle v1 = new Bundle();
        v1.putInt("satellites", 16);
        v0.setTime(System.currentTimeMillis());
        if(MainHook.api > 16) {
            v0.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }

        v0.setExtras(v1);
        v0.setAccuracy(arg5.x);
        v0.setBearing(arg5.y);
        v0.setSpeed(arg5.z);
        v0.setAltitude(arg5.s);
        return v0;
    }

    static Handler a(ah arg1) {
        return arg1.f;
    }

    static Method a(String arg1) {
        return ah.b(arg1);
    }

    public void a() {
        aj v1 = new aj(this);
        this.d = new Timer();
        this.d.schedule(((TimerTask)v1), 1080, 1006);
    }

    private static Method b(String str) {
        for (Method method : LocationListener.class.getDeclaredMethods()) {
            if (method.getName().equals(str)) {
                break;
            }
        }
        Method method2 = null;
        if (method2 != null) {
            method2.setAccessible(true);
        }
        return method2;
    }

    static doClass b(ah arg1) {
        return arg1.g;
    }

    public void b() {
        this.b = false;
        this.f = null;
        if(this.d != null) {
            this.d.cancel();
        }
    }

    static Method c() {
        return ah.e();
    }

    private Handler d() {
        return new ak(this, Looper.getMainLooper());
    }

    private static Method e() {
        for (Method method : GpsStatus.class.getDeclaredMethods()) {
            if (method.getName().equals("setStatus") && method.getParameterTypes().length > 6) {
                break;
            }
        }
        Method method2 = null;
        if (method2 != null) {
            method2.setAccessible(true);
        }
        return method2;
    }
}
