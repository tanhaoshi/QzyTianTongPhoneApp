package com.qzy.tt.phone.cmd;

import android.os.Message;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.phone.eventbus.CommandClientModel;
import com.qzy.tt.phone.eventbus.CommandModel;
import com.qzy.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class CmdHandler {


    public CmdHandler() {
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
    private void handProcessCmd(int protoId, ByteBufInputStream inputStream) {
        try {
            switch (protoId) {
                case PrototocalTools.IProtoClientIndex.call_phone_state:
                    CallPhoneStateProtos.CallPhoneState callPhoneState = CallPhoneStateProtos.CallPhoneState.parseDelimitedFrom(inputStream);
                    sendCmdToView(protoId, callPhoneState);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendCmdToView(int protoId, GeneratedMessageV3 messageV3) {
        PhoneCmd cmd = new PhoneCmd(protoId);
        cmd.setMessage(messageV3);
        CommandClientModel model = new CommandClientModel(cmd);
        EventBus.getDefault().post(model);
    }

}
