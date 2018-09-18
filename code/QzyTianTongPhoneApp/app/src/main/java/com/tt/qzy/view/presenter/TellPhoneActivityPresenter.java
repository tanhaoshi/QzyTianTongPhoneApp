package com.tt.qzy.view.presenter;

import android.content.Context;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.data.TtPhoneState;
import com.qzy.utils.LogUtils;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class TellPhoneActivityPresenter {
    private Context mContext;


    public TellPhoneActivityPresenter(Context context) {
        mContext = context;
    }

    /**
     * 挂断
     */
    public void endCall(){
        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_HUNGUP));
    }

    /**
     * 接听
     */
    public void acceptCall(){
        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_ACCEPTCALL));
    }


}
