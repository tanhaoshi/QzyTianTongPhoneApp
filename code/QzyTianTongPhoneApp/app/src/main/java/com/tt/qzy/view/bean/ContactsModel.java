package com.tt.qzy.view.bean;

/**
 * Created by qzy009 on 2018/8/27.
 */

public class ContactsModel {

    private String date;
    private String phoneNumber;
    private String state;

    public ContactsModel(String date, String phoneNumber, String state) {
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
