package com.tt.qzy.view.db.dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class MailListDao {

    @Id(autoincrement = true)
    private Long id;
    private String phone;
    private String name;
    private String ownership;
    private String tellTime;
    private String dataTime;
    private String createDate;
    private String mail;
    private String qq;
    @Generated(hash = 129014901)
    public MailListDao(Long id, String phone, String name, String ownership,
            String tellTime, String dataTime, String createDate, String mail,
            String qq) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.ownership = ownership;
        this.tellTime = tellTime;
        this.dataTime = dataTime;
        this.createDate = createDate;
        this.mail = mail;
        this.qq = qq;
    }

    public MailListDao(String phone,String name,String ownership,String tellTime,String dataTime,String createDate,String mail,String qq){
        this.phone = phone;
        this.name = name;
        this.ownership = ownership;
        this.tellTime = tellTime;
        this.dataTime = dataTime;
        this.createDate = createDate;
        this.mail = mail;
        this.qq = qq;
    }

    @Generated(hash = 1958615879)
    public MailListDao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOwnership() {
        return this.ownership;
    }
    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }
    public String getTellTime() {
        return this.tellTime;
    }
    public void setTellTime(String tellTime) {
        this.tellTime = tellTime;
    }
    public String getDataTime() {
        return this.dataTime;
    }
    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
    public String getCreateDate() {
        return this.createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getMail() {
        return this.mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getQq() {
        return this.qq;
    }
    public void setQq(String qq) {
        this.qq = qq;
    }
}
