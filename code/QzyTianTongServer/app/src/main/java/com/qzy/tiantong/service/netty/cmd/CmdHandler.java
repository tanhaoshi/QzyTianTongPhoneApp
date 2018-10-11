package com.qzy.tiantong.service.netty.cmd;

import android.os.Message;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.data.PhoneAudioCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.ChangePcmPlayerDbProtos;
import com.qzy.tt.data.TtPhoneAudioDataProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tiantong.lib.utils.LogUtils;


import org.greenrobot.eventbus.EventBus;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class CmdHandler {

    private TianTongHandler mHandler;
    public CmdHandler(TianTongHandler handler) {
        mHandler = handler;
    }

    /**
     * 处理数据
     *
     * @param inputStream
     */
    public void handlerCmd(ByteBufInputStream inputStream) {
        try {

            if(inputStream.available() > 0 && PrototocalTools.readToFour0x5aHeaderByte(inputStream)){
                int protoId = inputStream.readInt();
                int len = inputStream.readInt();//包长度
                LogUtils.d(" protoId = " + protoId + " len = " + len);
                if(protoId > 100 && protoId % 2 == 0){
                    handProcessCmd(protoId,inputStream);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分协议id处理消息
     * @param protoId
     * @param inputStream
     */
    private void handProcessCmd(int protoId,ByteBufInputStream inputStream){
        try {
            switch (protoId) {
                case PrototocalTools.IProtoServerIndex.call_phone:
                    CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.parseDelimitedFrom(inputStream);
                    senMsg(protoId,callPhone);
                    break;
                case PrototocalTools.IProtoServerIndex.chang_pcmplayer_db:
                    ChangePcmPlayerDbProtos.ChangePcmPlayerDb changePcmPlayerDb = ChangePcmPlayerDbProtos.ChangePcmPlayerDb.parseDelimitedFrom(inputStream);
                    senMsg(protoId,changePcmPlayerDb);
                    break;
                case PrototocalTools.IProtoServerIndex.phone_audio:
                    TtPhoneAudioDataProtos.PhoneAudioData audioData = TtPhoneAudioDataProtos.PhoneAudioData.parseDelimitedFrom(inputStream);
                    senAudioData(protoId,audioData);
                    break;
                case PrototocalTools.IProtoServerIndex.phone_send_sms:
                    TtPhoneSmsProtos.TtPhoneSms ttPhoneSms = TtPhoneSmsProtos.TtPhoneSms.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttPhoneSms);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void senMsg(int what,Object obj){
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        mHandler.sendMessage(msg);
    }

    private void senAudioData(int protoId,GeneratedMessageV3 messageV3){
        EventBus.getDefault().post(PhoneAudioCmd.getPhoneAudioCmd(protoId,messageV3));
    }

}
