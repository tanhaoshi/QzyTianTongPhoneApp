package com.qzy.tiantong.service.netty.cmd;

import android.os.Message;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.iinterface.ICmdHandler;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.ChangePcmPlayerDbProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhoneAudioDataProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtPhoneUpdateAppInfoProtos;
import com.qzy.tt.data.TtPhoneUpdateSendFileProtos;
import com.qzy.tt.probuf.lib.data.PhoneAudioCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import org.greenrobot.eventbus.EventBus;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class CmdHandlerUpdate implements ICmdHandler {

    private TianTongHandlerUpdate mHandler;

    public CmdHandlerUpdate(TianTongHandlerUpdate handler) {
        mHandler = handler;
    }

    /**
     * 处理数据
     *
     * @param inputStream
     */
    @Override
    public void handlerCmd(ByteBufInputStream inputStream) {
        try {

            if (inputStream.available() > 0 && PrototocalTools.readToFour0x5aHeaderByte(inputStream)) {
                int protoId = inputStream.readInt();
                int len = inputStream.readInt();//包长度
                LogUtils.d(" protoId = " + protoId + " len = " + len);
                if (protoId > 100 && protoId % 2 == 0) {
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
    @Override
    public void handProcessCmd(int protoId, ByteBufInputStream inputStream) {
        try {
            switch (protoId) {
                case PrototocalTools.IProtoServerIndex.request_update_phone_aapinfo:
                    TtPhoneUpdateAppInfoProtos.UpdateAppInfo updateAppInfo = TtPhoneUpdateAppInfoProtos.UpdateAppInfo.parseDelimitedFrom(inputStream);
                    senMsg(protoId,updateAppInfo);
                    break;
                case PrototocalTools.IProtoServerIndex.request_update_send_zip:
                    TtPhoneUpdateSendFileProtos.UpdateSendFile updateSendFile = TtPhoneUpdateSendFileProtos.UpdateSendFile.parseDelimitedFrom(inputStream);
                    senMsg(protoId,updateSendFile);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void senMsg(int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }


}
