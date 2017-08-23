package com.anjoyo.xyl.run.hookgps;

import android.os.Message;
import java.util.TimerTask;

class ap extends TimerTask {
    final /* synthetic */ ao a;

    ap(ao aoVar) {
        this.a = aoVar;
    }

    public void run() {
        Message v0 = new Message();
        v0.what = 3;
        if((this.a.b) && ao.a(this.a) != null && this.a.a != null) {
            ao.a(this.a).sendMessage(v0);
        }
    }
}
