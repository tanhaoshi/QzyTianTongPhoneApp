package com.tt.qzy.view.bean;

/**
 * Created by qzy009 on 2018/8/23.
 */

public class SortModel {
    private String name;
    private String letters;//显示拼音的首字母
    private String phone;
    private boolean choosed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
