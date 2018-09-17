package com.qzy.eventbus;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class MessageEventBus {
    private String type;
    private Object object;

    public MessageEventBus(String type) {
        this.type = type;
    }


    public MessageEventBus(String type, Object o) {
        this.type = type;
        object = o;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
