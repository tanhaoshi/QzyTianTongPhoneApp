package com.qzy.tt.phone.cmd;

import android.content.Context;
import android.content.Intent;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.utils.LogUtils;
import com.socks.library.KLog;


import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class CmdHandler {

    private Context context;

    public CmdHandler(Context context) {
        this.context = context;
    }

    /**
     * 处理数据
     *
     * @param inputStream
     */
    public void handlerCmd(ByteBufInputStream inputStream) {
        try {

            if (inputStream.available() > 0 && PrototocalTools.readToFour0x5aHeaderByte(inputStream)) {
                int protoId = inputStream.readInt();
                int len = inputStream.readInt();//包长度
                LogUtils.e(" protoId = " + protoId + " len = " + len);
                if (protoId > 100 && protoId % 2 == 1) {
                    handProcessCmd(protoId, inputStream);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分协议id处理消息
     *
     * @param protoId
     * @param inputStream
     */
    private  CallPhoneStateProtos.CallPhoneState.PhoneState currentPhoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
    private void handProcessCmd(int protoId, ByteBufInputStream inputStream) {
        try {
            switch (protoId) {
                case PrototocalTools.IProtoClientIndex.call_phone_state:
                    CallPhoneStateProtos.CallPhoneState callPhoneState = CallPhoneStateProtos.CallPhoneState.parseDelimitedFrom(inputStream);
                    CallPhoneStateProtos.CallPhoneState.PhoneState state = callPhoneState.getPhoneState();
                    KLog.i("currentPhoneState = " + currentPhoneState.ordinal() + "    state = " + state.ordinal());
                    if(currentPhoneState != state){ // 与上次的状态不同才改变
                        currentPhoneState = state;
                        if( state == CallPhoneStateProtos.CallPhoneState.PhoneState.INCOMING){
                            incommingState(callPhoneState.getPhoneNumber());
                        }else {
                            sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_STATE,protoId, callPhoneState);
                        }
                    }



                    break;
                case PrototocalTools.IProtoClientIndex.tt_phone_signal:
                    TtPhoneSignalProtos.PhoneSignalStrength phoneSignalStrength = TtPhoneSignalProtos.PhoneSignalStrength.parseDelimitedFrom(inputStream);
                    sendCmdToView(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SIGNAL,protoId, phoneSignalStrength);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendCmdToView(String messageType,int protoId, GeneratedMessageV3 messageV3) {
        EventBusUtils.post(new MessageEventBus(messageType,PhoneCmd.getPhoneCmd(protoId,messageV3)));
    }



    private void incommingState(String number){
        Intent intent = new Intent("com.qzy.tt.incoming");
        intent.putExtra("phone_number",number);
        context.sendBroadcast(intent);
    }

}
