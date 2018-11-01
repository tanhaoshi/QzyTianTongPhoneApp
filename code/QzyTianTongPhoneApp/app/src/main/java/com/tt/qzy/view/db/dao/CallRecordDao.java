package com.tt.qzy.view.db.dao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CallRecordDao {
    @Id(autoincrement = true)
    private Long id;
    private String phoneNumber;
    private String name;
    private String address;
    private String state;
    private String date;
    private long duration;
    private int isTitle;
    private String titleName;
    private int count;
    @Generated(hash = 1973110022)
    public CallRecordDao(Long id, String phoneNumber, String name, String address,
            String state, String date, long duration, int isTitle, String titleName,
            int count) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.address = address;
        this.state = state;
        this.date = date;
        this.duration = duration;
        this.isTitle = isTitle;
        this.titleName = titleName;
        this.count = count;
    }

    public CallRecordDao(String phoneNumber, String name, String address,
                         String state, String date, int isTitle, String titleName){
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.address = address;
        this.state = state;
        this.date = date;
        this.isTitle = isTitle;
        this.titleName = titleName;
    }

    public CallRecordDao(String phoneNumber, String name, String address,
                         String state, String date,long duration){
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.address = address;
        this.state = state;
        this.date = date;
        this.isTitle = 0;
        this.titleName = "";
        this.duration = duration;
    }

    @Generated(hash = 1511194593)
    public CallRecordDao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getIsTitle() {
        return this.isTitle;
    }
    public void setIsTitle(int isTitle) {
        this.isTitle = isTitle;
    }
    public String getTitleName() {
        return this.titleName;
    }
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
