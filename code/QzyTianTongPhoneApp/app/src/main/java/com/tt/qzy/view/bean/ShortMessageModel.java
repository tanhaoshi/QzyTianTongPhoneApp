package com.tt.qzy.view.bean;

/**
 * Created by qzy009 on 2018/8/24.
 */

public class ShortMessageModel {

    private String numberPhone;
    private String message;
    private String time;

    public ShortMessageModel(String numberPhone, String message, String time) {
        this.numberPhone = numberPhone;
        this.message = message;
        this.time = time;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
