package com.tt.qzy.view.bean;

/**
 * Created by qzy009 on 2018/8/24.
 */

public class ShortMessageModel {

    private String numberPhone;
    private String message;
    private String time;
    private int isTitle;
    private String titleName;
    private boolean isCheck;

    public ShortMessageModel(String numberPhone, String message, String time,int isTitle,String titleName) {
        this.numberPhone = numberPhone;
        this.message = message;
        this.time = time;
        this.isTitle = isTitle;
        this.titleName = titleName;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
