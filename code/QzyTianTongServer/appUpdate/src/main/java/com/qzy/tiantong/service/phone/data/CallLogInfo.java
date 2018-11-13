package com.qzy.tiantong.service.phone.data;

public class CallLogInfo {


    private String name;
    private String number;
    private String date;
    private int type;   // 来电:1，拨出:2,未接:3
    private long time;   //通话时长

    public CallLogInfo(String name, String number, String date, int type, long time) {
        setName(name);
        setNumber(number);
        setType(type);
        setTime(time);
        setDate(date);

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "CallLogInfo{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", type=" + type +
                ", time=" + time +
                '}';
    }
}
