package com.anjoyo.xyl.run.hookgps;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import de.robv.android.xposed.XSharedPreferences;

public class doClass{
    public int A;
    public int B;
    public int C;
    public int D;
    public int E;
    public int F;
    public int G;
    private SharedPreferences H;
    private Editor I;
    private String[] J;
    private XSharedPreferences K;
    private boolean L;
    private boolean M;
    private double N;
    private double O;
    private int P;
    private long Q;
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public boolean o;
    public boolean p;
    public boolean q;
    public boolean r;
    public double s;
    public double t;
    public double u;
    public double v;
    public double w;
    public float x;
    public float y;
    public float z;

    public doClass(String arg7, XSharedPreferences arg8) {
        super();
        this.L = true;
        this.M = false;
        this.N = 0;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = "";
        this.f = "";
        this.g = "";
        this.h = "";
        this.i = "";
        this.j = "";
        this.k = "";
        this.l = "";
        this.m = "";
        this.n = "";
        this.o = false;
        this.p = false;
        this.q = false;
        this.r = false;
        this.s = -1;
        this.t = 0;
        this.u = 0;
        this.v = 0;
        this.w = 0;
        this.x = -1f;
        this.y = -1f;
        this.z = -1f;
        this.A = 0;
        this.B = 0;
        this.C = 0;
        this.D = 0;
        this.E = 0;
        this.F = 0;
        this.G = 0;
        this.l = arg7;
        this.K = arg8;
        this.K.makeWorldReadable();
        this.a();
    }

    static boolean a(doClass arg0, boolean arg1) {
        arg0.M = arg1;
        return arg1;
    }

    public void a() {
        this.K.reload();
        String[] v0 = dn.a(this.l, this.a("keyp0"), this.a("keyp1"), this.a("keyp2"));
        if(!v0[0].equals("")) {
            this.m = v0[0];
            this.n = v0[1];
            this.Q = this.K.getLong("6", 0);
            this.q = this.K.getBoolean("key33" + this.m, false);
            this.o = this.K.getBoolean("gps", false);
            this.r = this.K.getBoolean("requestloc", false);
            String v0_1 = this.a("onc", "");
            if(dn.h(v0_1)) {
                this.h = v0_1;
            }

            this.j = this.a("os", "");
            this.f = this.a("oc", "");
            this.g = this.a("on", "");
            this.F = Integer.parseInt(this.a("key30", "0"));
            this.G = Integer.parseInt(this.a("key31", "0"));
            this.a = this.a("key32", "-1");
            this.d = this.a("key10");
            this.i = this.a("operatorfix");
            this.b(this.a("location", ""));
            v0_1 = this.a("speed");
            if(dn.j(v0_1)) {
                this.z = Float.parseFloat(v0_1);
            }

            v0_1 = this.a("accuracy");
            if(dn.j(v0_1)) {
                this.x = Float.parseFloat(v0_1);
            }

            v0_1 = this.a("altitude");
            if(dn.j(v0_1)) {
                this.s = Double.parseDouble(v0_1);
            }

            v0_1 = this.a("bearing");
            if(!dn.j(v0_1)) {
                return;
            }

            this.y = Float.parseFloat(v0_1);
        }
    }

    static boolean a(doClass arg1) {
        return arg1.M;
    }

    static double a(doClass arg1, double arg2) {
        arg1.N = arg2;
        return arg2;
    }

    static void a(doClass arg0, String arg1) {
        arg0.c(arg1);
    }

    static int a(doClass arg0, int arg1) {
        arg0.P = arg1;
        return arg1;
    }

    public static double a(SharedPreferences arg2, String arg3, double arg4) {
        return Double.longBitsToDouble(arg2.getLong(arg3, Double.doubleToLongBits(arg4)));
    }

    public static void a(Editor arg2, String arg3, double arg4) {
        arg2.putLong(arg3, Double.doubleToRawLongBits(arg4));
    }

    private double[] a(double arg14, double arg16) {
        double v0 = arg14 / 6371000;
        double v2 = Math.toRadians(this.t);
        Math.toRadians(this.u);
        double v4 = Math.toRadians(arg16);
        double v6 = Math.asin(Math.sin(v2) * Math.cos(v0) + Math.cos(v2) * Math.sin(v0) * Math.cos(v4));
        return new double[]{Math.toDegrees(v6 - v2), Math.toDegrees(Math.atan2(Math.sin(v4) * Math.sin(v0) * Math.cos(v2), Math.cos(v0) - Math.sin(v2) * Math.sin(v6)))};
    }

    public String a(String arg3) {
        return this.K.getString(arg3, "");
    }

    public String a(String arg4, String arg5) {
        return this.K.getString(arg4 + this.m, arg5);
    }

    public void a(Context arg5) {
        if((this.L) && arg5 != null) {
            try {
                if(arg5.isRestricted()) {
                    return;
                }

                arg5.registerReceiver(new LocationChangeBroadcastReceiver(this), new IntentFilter("xyl"));
                this.L = false;
                this.H = arg5.getSharedPreferences("prs", 0);
                this.I = this.H.edit();
                if(this.H.getLong("6", 0) <= this.Q) {
                    return;
                }

                this.t = doClass.a(this.H, "0", this.t);
                this.u = doClass.a(this.H, "1", this.u);
                this.P = this.H.getInt("2", this.P);
                this.A = this.H.getInt("3", this.A);
                this.B = this.H.getInt("4", this.B);
                this.C = this.H.getInt("5", this.C);
            }
            catch(SecurityException v0) {
                dn.d(this.l + "   SecurityException");
            }
        }
    }

    static double b(doClass arg1, double arg2) {
        arg1.O = arg2;
        return arg2;
    }

    static void b(doClass arg0) {
        arg0.b();
    }

    private void b() {
        if(this.P > 0) {
            double[] v0 = this.a(MainHook.r.nextDouble() * (((double)this.P)), MainHook.r.nextDouble() * 360);
            this.v = v0[0];
            this.w = v0[1];
        }
    }

    private void b(String arg3) {
        if(dn.l(arg3)) {
            this.e = arg3;
            this.c(this.a("key2" + this.e));
        }
    }

    static void c(doClass arg0) {
        arg0.c();
    }

    private void c(String arg8) {
        int v0 = 5;
        int v5 = 3;
        if(dn.e(arg8)) {
            this.k = arg8;
            this.J = this.k.split(",");
            if(dn.e(this.k)) {
                this.A = Integer.parseInt(this.J[v5]);
                this.B = Integer.parseInt(this.J[4]);
                this.t = Double.parseDouble(this.J[0]);
                this.u = Double.parseDouble(this.J[1]);
                this.P = Integer.parseInt(this.J[2]);
                this.b();
            }

            if(dn.g(this.k)) {
                this.C = Integer.parseInt(this.J[v0]);
                v0 = 6;
            }

            String[] v0_1 = dn.o(this.J[v0]);
            if(v0_1 != null) {
                this.F = Integer.parseInt(v0_1[0]);
                this.h = v0_1[1];
            }

            this.p = dn.h(this.h);
            String v0_2 = this.p ? this.h.substring(0, v5) : "0";
            this.D = Integer.parseInt(v0_2);
            v0_2 = this.p ? this.h.substring(v5) : "0";
            this.E = Integer.parseInt(v0_2);
        }
    }

    private void c() {
        if(this.I != null) {
            doClass.a(this.I, "0", this.t);
            doClass.a(this.I, "1", this.u);
            this.I.putInt("2", this.P);
            this.I.putInt("3", this.A);
            this.I.putInt("4", this.B);
            this.I.putInt("5", this.C);
            this.I.putLong("6", System.currentTimeMillis());
            this.I.apply();
        }
    }
}
