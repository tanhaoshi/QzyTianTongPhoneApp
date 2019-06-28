package com.tt.qzy.view.bean;

import android.support.annotation.NonNull;

import com.tt.qzy.view.utils.PinyinUtils;

public class MallListModel implements Comparable<MallListModel>{

    private Long  id;
    private String phone;
    private String name;
    private String letters;
    private boolean choosed;
    private String pinyin;

    public MallListModel(){
    }

    public MallListModel(String phone,String name,Long id){
        if(name == null){
            name = "无名";
        }
        pinyin = PinyinUtils.getPingYin(name); // 根据姓名获取拼音
        letters = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!letters.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            letters = "#";
        }
        this.phone = phone;
        this.name = name;
        this.id = id;
    }

    /**
     * time consuming reason
     * @since especially when there's a lot of data
     * */
    public MallListModel(String phone,String name){
        if(name == null){
            name ="无名";
        }
        pinyin = PinyinUtils.getPingYin(name); // 根据姓名获取拼音
        letters = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!letters.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            letters = "#";
        }
        this.phone = phone;
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(@NonNull MallListModel o) {
        if (letters.equals("#") && !o.getLetters().equals("#")) {
            return 1;
        } else if (!letters.equals("#") && o.getLetters().equals("#")){
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(o.getPinyin());
        }
    }
}
