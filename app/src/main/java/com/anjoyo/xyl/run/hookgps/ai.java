package com.anjoyo.xyl.run.hookgps;

import android.location.GpsStatus;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

final class ai extends XC_MethodHook {
    boolean a;
    boolean b;
    int c;
    int[] d;
    float[] e;
    float[] f;
    float[] g;
    int h;
    int i;
    int j;

    ai() {
        super();
        this.a = true;
        this.b = true;
        this.c = 16;
        this.d = new int[16];
        this.e = new float[16];
        this.f = new float[16];
        this.g = new float[16];
        this.h = 65535;
        this.i = 65535;
        this.j = 65535;
    }

    protected void afterHookedMethod(MethodHookParam arg13) {
        int v11 = 2;
        Object v0 = XposedHelpers.newInstance(GpsStatus.class, new Object[0]);
        long v4 = System.currentTimeMillis() / 1000 % 10 % 2;
        if((this.a) && (this.b)) {
            this.a = false;
            String v1 = "";
            int v2;
            for(v2 = 0; v2 < 16; ++v2) {
                this.d[v2] = MainHook.r.nextInt(v11) + 1;
                if(v2 > 0) {
                    this.d[v2] += this.d[v2 - 1];
                }

                this.e[v2] = MainHook.r.nextFloat() * 63f;
                v1 = this.e[v2] < 20f ? v1 + "0" : v1 + "1";
                this.f[v2] = MainHook.r.nextFloat() * 90f;
                this.g[v2] = MainHook.r.nextFloat() * 360f;
            }

            int v1_1 = Integer.parseInt(v1, v11);
            this.h = v1_1;
            this.i = v1_1;
            this.j = v1_1;
        }

        if(v4 < 1) {
            this.b = false;
            this.a = true;
        }
        else {
            this.b = true;
        }

        Method v1_2 = ah.c();
        if(v1_2 != null) {
            try {
                v1_2.invoke(v0, Integer.valueOf(this.c), this.d, this.e, this.f, this.g, Integer.valueOf(this.h), Integer.valueOf(this.i), Integer.valueOf(this.j));
                arg13.setResult(v0);
            }
            catch(Exception v0_1) {
                dn.a(((Throwable)v0_1));
            }
        }
    }
}
