package com.qzy.tt.phone.eventbus;

import com.qzy.eventbus.MessageEvent;

/**
 * Created by yj.zhang on 2018/8/2/002.
 */

public class CommandModel extends MessageEvent {
    private boolean isStartRecorder;

    private String phoneNumber;

    private String msgTag;

    public CommandModel(String msg){
        msgTag = msg;
    }

    public boolean isStartRecorder() {
        return isStartRecorder;
    }

    public void setStartRecorder(boolean startRecorder) {
        isStartRecorder = startRecorder;
    }

    public String getMsgTag() {
        return msgTag;
    }

    public void setMsgTag(String msgTag) {
        this.msgTag = msgTag;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
