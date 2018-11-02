package com.tt.qzy.view.db.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShortMessageDao {
    @Id(autoincrement = true)
    private Long id;
    private String numberPhone;
    private String message;
    private String time;
    private String state;
    private int isTitle;
    private String titleName;
    private boolean isCheck;
    private String name;
    private long serverId;
    private boolean isStatus;
    @Generated(hash = 957255530)
    public ShortMessageDao(Long id, String numberPhone, String message, String time, String state, int isTitle, String titleName,
            boolean isCheck, String name, long serverId, boolean isStatus) {
        this.id = id;
        this.numberPhone = numberPhone;
        this.message = message;
        this.time = time;
        this.state = state;
        this.isTitle = isTitle;
        this.titleName = titleName;
        this.isCheck = isCheck;
        this.name = name;
        this.serverId = serverId;
        this.isStatus = isStatus;
    }

    public ShortMessageDao(String numberPhone, String message, String time,int isTitle,String titleName,String state,String name) {
        this.numberPhone = numberPhone;
        this.message = message;
        this.time = time;
        this.isTitle = isTitle;
        this.titleName = titleName;
        this.state = state;
        this.name = name;
    }

    public ShortMessageDao(String numberPhone,String message,String time,String state,String name,long serverId,
                           boolean isStatus){
        this.numberPhone = numberPhone;
        this.message = message;
        this.time = time;
        this.state = state;
        this.name = name;
        this.isTitle = 0;
        this.titleName = "";
        this.serverId = serverId;
        this.isStatus = isStatus;
    }

    @Generated(hash = 1341911062)
    public ShortMessageDao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNumberPhone() {
        return this.numberPhone;
    }
    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
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
    public boolean getIsCheck() {
        return this.isCheck;
    }
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setStatus(boolean status) {
        isStatus = status;
    }

    public boolean getIsStatus() {
        return this.isStatus;
    }

    public void setIsStatus(boolean isStatus) {
        this.isStatus = isStatus;
    }
}
