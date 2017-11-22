package com.anjoyo.xyl.run.hookgps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;
import java.util.Set;

class LocationChangeBroadcastReceiver extends BroadcastReceiver {
    private doClass a;

    LocationChangeBroadcastReceiver(doClass arg1) {
        super();
        this.a = arg1;
    }

    public void onReceive(Context arg11, Intent arg12) {
        String[] v0_3;
        String v0_2;
        if (arg12.getAction().equals("xyl_fix_location")) {
            Bundle v4 = arg12.getExtras();
            Log.d("xyl","onReceive==="+v4.toString());
            Set v5 = v4.keySet();
            Iterator v6 = v5.iterator();
            while (v6.hasNext()) {
                Object v0 = v6.next();
                if (v0.equals("ssid")) {
                    this.a.c = String.valueOf(v4.get("ssid"));
                } else if (v0.equals("bssid")) {
                    v0_2 = String.valueOf(v4.get("bssid"));
                    if (!dn.f(v0_2)) {
                        continue;
                    }
                    this.a.b = v0_2;
                } else if (v0.equals("speed")) {
                    v0_2 = String.valueOf(v4.get("speed"));
                    if (!dn.j(v0_2)) {
                        continue;
                    }
                    this.a.z = Float.parseFloat(v0_2);
                } else if (v0.equals("changed")) {
                    doClass.a(this.a, false);
                    this.a.a();
                } else if (v0.equals("altitude")) {
                    v0_2 = String.valueOf(v4.get("altitude"));
                    if (!dn.j(v0_2)) {
                        continue;
                    }
                    this.a.s = Double.parseDouble(v0_2);
                } else if (v0.equals("accuracy")) {
                    v0_2 = String.valueOf(v4.get("accuracy"));
                    if (!dn.j(v0_2)) {
                        continue;
                    }
                    this.a.x = Float.parseFloat(v0_2);
                } else if (v0.equals("bearing")) {
                    v0_2 = String.valueOf(v4.get("bearing"));
                    if (!dn.j(v0_2)) {
                        continue;
                    }
                    this.a.y = Float.parseFloat(v0_2);
                }
            }

            if ((!v5.contains("task") || !String.valueOf(v4.get("task")).startsWith(this.a.n)) && !this.a.e.equals("99")) {
                return;
            }

            if (v5.contains("loc")) {
                v0_2 = String.valueOf(v4.get("loc"));
                if (!doClass.a(this.a)) {
                    doClass.a(this.a, v0_2);
                } else if (dn.e(v0_2)) {
                    v0_3 = v0_2.split(",");
                    doClass.a(this.a, Double.parseDouble(v0_3[0]));
                    doClass.b(this.a, Double.parseDouble(v0_3[1]));
                }
            }

            if (v5.contains("latlng")) {
                v0_2 = String.valueOf(v4.get("latlng"));
                if (dn.m(v0_2)) {
                    v0_3 = v0_2.split(",");
                    if (doClass.a(this.a)) {
                        doClass.a(this.a, Double.parseDouble(v0_3[0]));
                        doClass.b(this.a, Double.parseDouble(v0_3[1]));
                    } else {
                        this.a.t = Double.parseDouble(v0_3[0]);
                        this.a.u = Double.parseDouble(v0_3[1]);
                    }
                }
            }

            if (v5.contains("lat")) {
                v0_2 = String.valueOf(v4.get("lat"));
                if (dn.j(v0_2)) {
                    if (doClass.a(this.a)) {
                        doClass.a(this.a, Double.parseDouble(v0_2));
                    } else {
                        this.a.t = Double.parseDouble(v0_2);
                    }
                }
            }

            if (v5.contains("lng")) {
                v0_2 = String.valueOf(v4.get("lng"));
                if (dn.j(v0_2)) {
                    if (doClass.a(this.a)) {
                        doClass.b(this.a, Double.parseDouble(v0_2));
                    } else {
                        this.a.u = Double.parseDouble(v0_2);
                    }
                }
            }

            if (v5.contains("offset")) {
                v0_2 = String.valueOf(v4.get("offset"));
                if (dn.i(v0_2)) {
                    v0_2 = v0_2.split("\\.")[0];
                    if (dn.k(v0_2)) {
                        doClass.a(this.a, Integer.parseInt(v0_2));
                    }
                }
            }

            doClass.b(this.a);
            if (v5.contains("lac")) {
                v0_2 = String.valueOf(v4.get("lac"));
                if (dn.k(v0_2)) {
                    this.a.A = Integer.parseInt(v0_2);
                }
            }

            if (v5.contains("cid")) {
                v0_2 = String.valueOf(v4.get("cid"));
                if (dn.k(v0_2)) {
                    this.a.B = Integer.parseInt(v0_2);
                }
            }

            if (v5.contains("sid")) {
                v0_2 = String.valueOf(v4.get("sid"));
                if (dn.k(v0_2)) {
                    this.a.C = Integer.parseInt(v0_2);
                }
            }

            if (v5.contains("changed")) {
                return;
            }

            doClass.c(this.a);
        }
    }
}

