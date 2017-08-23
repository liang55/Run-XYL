package com.anjoyo.xyl.run.hookgps;

import android.os.Message;
import java.util.TimerTask;

class am extends TimerTask {
    final /* synthetic */ al a;

    am(al alVar) {
        this.a = alVar;
    }

    public void run() {
        Message v0 = new Message();
        v0.what = 2;
        if((this.a.b) && al.a(this.a) != null && this.a.a != null) {
            al.a(this.a).sendMessage(v0);
        }
    }
}
