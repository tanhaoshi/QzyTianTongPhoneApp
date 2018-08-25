package com.tt.qzy.view.bean;

/**
 * Created by qzy009 on 2018/8/24.
 */

public class CallRecordModel {
    private String phoneNumber;
    private String addres;
    private String time;

    public CallRecordModel(String phoneNumber, String addres, String time) {
        this.phoneNumber = phoneNumber;
        this.addres = addres;
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
