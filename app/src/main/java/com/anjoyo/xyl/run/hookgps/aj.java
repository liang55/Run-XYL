package com.anjoyo.xyl.run.hookgps;

import android.os.Message;
import java.util.TimerTask;

class aj extends TimerTask {
    final /* synthetic */ ah a;

    aj(ah arg1) {
        super();
        this.a = arg1;
    }

    public void run() {
        Message v0 = new Message();
        v0.what = 1;
        if((this.a.b) && ah.a(this.a) != null && this.a.a != null) {
            ah.a(this.a).sendMessage(v0);
        }
    }
}
