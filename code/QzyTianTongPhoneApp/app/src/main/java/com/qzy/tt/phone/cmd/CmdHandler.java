package com.qzy.tt.phone.cmd;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.data.PhoneAudioCmd;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.data.TtPhoneAudioDataProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.phone.eventbus.CommandClientModel;
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
                case PrototocalTools.IProtoClientIndex.tt_phone_signal:
                    TtPhoneSignalProtos.PhoneSignalStrength phoneSignalStrength = TtPhoneSignalProtos.PhoneSignalStrength.parseDelimitedFrom(inputStream);
                    sendCmdToView(protoId, phoneSignalStrength);
                    break;
                case PrototocalTools.IProtoClientIndex.tt_phone_audio: //接受服务器端audio数据
                    TtPhoneAudioDataProtos.PhoneAudioData phoneAudioData = TtPhoneAudioDataProtos.PhoneAudioData.parseDelimitedFrom(inputStream);
                    sendAudioData(protoId,phoneAudioData);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendCmdToView(int protoId, GeneratedMessageV3 messageV3) {
        CommandClientModel model = new CommandClientModel(PhoneCmd.getPhoneCmd(protoId, messageV3));
        EventBus.getDefault().post(model);
    }

    private void sendAudioData(int protoId, GeneratedMessageV3 messageV3) {
        PhoneAudioCmd model = PhoneAudioCmd.getPhoneAudioCmd(protoId, messageV3);
        EventBus.getDefault().post(model);
    }

}
