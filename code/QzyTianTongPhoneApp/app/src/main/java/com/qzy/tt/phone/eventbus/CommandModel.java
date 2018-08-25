package com.qzy.tt.phone.eventbus;

import com.qzy.eventbus.MessageEvent;

/**
 * Created by yj.zhang on 2018/8/2/002.
 */

public class CommandModel extends MessageEvent {
    private boolean isStartRecorder;


    private String msgTag;

   // private PhoneCmd cmd;

    public CommandModel(String msg){
        msgTag = msg;
    }

    /*public CommandModel(PhoneCmd cmd){
       this.cmd = cmd;
    }*/

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

   /*public PhoneCmd getCmd() {
        return cmd;
    }

    public void setCmd(PhoneCmd cmd) {
        this.cmd = cmd;
    }*/

}
