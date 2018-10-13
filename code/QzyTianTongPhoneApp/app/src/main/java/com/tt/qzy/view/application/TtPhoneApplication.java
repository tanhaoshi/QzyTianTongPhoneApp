package com.tt.qzy.view.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by yj.zhang on 2018/8/25.
 */

public class TtPhoneApplication extends Application {

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
        MultiDex.install(this);
//        refWatcher = setupLeakCanary();
    }

    private void checkActivityMemory(){
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        TtPhoneApplication leakApplication = (TtPhoneApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

}
