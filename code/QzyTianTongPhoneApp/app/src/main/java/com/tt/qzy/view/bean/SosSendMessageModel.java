package com.tt.qzy.view.bean;

public class SosSendMessageModel {

    private String messageContent;

    private String phoneNumber;

    private int delaytime;

    public SosSendMessageModel(String messageContent,String phoneNumber,int delaytime){
        this.messageContent = messageContent;
        this.phoneNumber = phoneNumber;
        this.delaytime = delaytime;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDelaytime() {
        return delaytime;
    }

    public void setDelaytime(int delaytime) {
        this.delaytime = delaytime;
    }
}
