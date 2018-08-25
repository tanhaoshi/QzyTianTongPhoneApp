package com.qzy.tt.phone.eventbus;

import com.qzy.eventbus.MessageEvent;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class CallingModel extends MessageEvent {
    private String phone_number;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
