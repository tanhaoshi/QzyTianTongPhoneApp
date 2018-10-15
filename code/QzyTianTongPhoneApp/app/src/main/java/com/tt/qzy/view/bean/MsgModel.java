package com.tt.qzy.view.bean;

/**
 * Created by qzy009 on 2018/8/28.
 */

public class MsgModel {

    public static final int TYPE_SENT = 1;//接受短信
    public static final int TYPE_RECEIVE = 2;//发送短信
    private String content;
    private int type;

    public MsgModel(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public MsgModel(String content) {
        this.content = content;
    }


    public void setType(int type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
