package com.tt.qzy.view.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by yj.zhang on 2018/8/25.
 */

public class TtPhoneApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
    }
}
