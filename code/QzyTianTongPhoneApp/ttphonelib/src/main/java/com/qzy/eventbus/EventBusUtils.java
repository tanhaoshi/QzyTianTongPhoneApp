package com.qzy.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class EventBusUtils {

    public static void register(Object o) {
        if (!EventBus.getDefault().isRegistered(o)) {
            EventBus.getDefault().register(o);
        }
    }

    public static void unregister(Object o) {
        if (EventBus.getDefault().isRegistered(o)) {
            EventBus.getDefault().unregister(o);
        }
    }

    public static void post(Object o) {
        EventBus.getDefault().post(o);
    }

}
