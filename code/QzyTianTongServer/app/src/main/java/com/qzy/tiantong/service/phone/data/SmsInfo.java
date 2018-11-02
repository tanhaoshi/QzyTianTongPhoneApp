package com.qzy.tiantong.service.phone.data;

public class SmsInfo {
    private long id;
    private int type;   //1是接收，2是发出
    private String name;
    private String number;
    private String body;
    private String date;
    private int isRead;

    public SmsInfo(long id,int type, String name, String number, String body, String date, int read) {
        setType(type);
        setName(name);
        setNumber(number);
        setBody(body);
        setDate(date);
        setIsRead(read);
        setId(id);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SmsInfo{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", body='" + body + '\'' +
                ", date='" + date + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
