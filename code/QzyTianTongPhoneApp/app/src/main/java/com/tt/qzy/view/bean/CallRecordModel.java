package com.tt.qzy.view.bean;

/**
 * Created by qzy009 on 2018/8/24.
 */

public class CallRecordModel {
    private String phoneNumber;
    private String name;
    private String address;
    private String state;
    private String date;
    private int isTitle;
    private String titleName;

    public CallRecordModel(String phoneNumber,String name, String address, String state,String date,int isTitle,String titleName) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.state = state;
        this.date = date;
        this.isTitle = isTitle;
        this.titleName = titleName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIsTitle() {
        return isTitle;
    }

    public void setIsTitle(int isTitle) {
        this.isTitle = isTitle;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
