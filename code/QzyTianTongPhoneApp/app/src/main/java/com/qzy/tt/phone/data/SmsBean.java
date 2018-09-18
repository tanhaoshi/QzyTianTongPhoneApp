package com.qzy.tt.phone.data;

/**
 * Created by yj.zhang on 2018/9/18.
 */

public class SmsBean {
    private String phoneNumber;
    private String msg;

    public SmsBean(String phone,String message){
        phoneNumber = phone;
        msg = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
