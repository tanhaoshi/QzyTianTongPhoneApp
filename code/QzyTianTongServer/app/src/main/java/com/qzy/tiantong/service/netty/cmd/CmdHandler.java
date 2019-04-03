package com.qzy.tiantong.service.netty.cmd;

import android.os.Message;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.iinterface.ICmdHandler;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.ChangePcmPlayerDbProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtDeleCallLogProtos;
import com.qzy.tt.data.TtDeleSmsProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhoneAudioDataProtos;
import com.qzy.tt.data.TtPhoneGetServerVersionProtos;
import com.qzy.tt.data.TtPhoneMobileDataProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneRecoverSystemProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtPhoneSosMessageProtos;
import com.qzy.tt.data.TtPhoneSosStateProtos;
import com.qzy.tt.data.TtPhoneWifiProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.data.TtTimeProtos;
import com.qzy.tt.probuf.lib.data.PhoneAudioCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;


import org.greenrobot.eventbus.EventBus;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class CmdHandler implements ICmdHandler {

    private TianTongHandler mHandler;

    public CmdHandler(TianTongHandler handler) {
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
                case PrototocalTools.IProtoServerIndex.call_phone:
                    CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.parseDelimitedFrom(inputStream);
                    senMsg(protoId, callPhone);
                    break;
                case PrototocalTools.IProtoServerIndex.chang_pcmplayer_db:
                    ChangePcmPlayerDbProtos.ChangePcmPlayerDb changePcmPlayerDb = ChangePcmPlayerDbProtos.ChangePcmPlayerDb.parseDelimitedFrom(inputStream);
                    senMsg(protoId, changePcmPlayerDb);
                    break;
                case PrototocalTools.IProtoServerIndex.phone_audio:
                    TtPhoneAudioDataProtos.PhoneAudioData audioData = TtPhoneAudioDataProtos.PhoneAudioData.parseDelimitedFrom(inputStream);
                    senAudioData(protoId, audioData);
                    break;
                case PrototocalTools.IProtoServerIndex.phone_send_sms:
                    TtPhoneSmsProtos.TtPhoneSms ttPhoneSms = TtPhoneSmsProtos.TtPhoneSms.parseDelimitedFrom(inputStream);
                    senMsg(protoId, ttPhoneSms);
                    break;

                case PrototocalTools.IProtoServerIndex.request_gps_position:
                    TtPhonePositionProtos.TtPhonePosition ttPhonePosition = TtPhonePositionProtos.TtPhonePosition.parseDelimitedFrom(inputStream);
                    senMsg(protoId, ttPhonePosition);
                    break;
                case PrototocalTools.IProtoServerIndex.request_open_beidou_usb:
                    TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou = TtOpenBeiDouProtos.TtOpenBeiDou.parseDelimitedFrom(inputStream);
                    senMsg(protoId, ttOpenBeiDou);
                    break;
                case PrototocalTools.IProtoServerIndex.request_tt_time:
                    TtTimeProtos.TtTime ttTime = TtTimeProtos.TtTime.parseDelimitedFrom(inputStream);
                    senMsg(protoId, ttTime);
                    break;
                case PrototocalTools.IProtoServerIndex.request_phone_send_sms_read:
                    TtShortMessageProtos.TtShortMessage.ShortMessage  shortMessage = TtShortMessageProtos.TtShortMessage.ShortMessage.parseDelimitedFrom(inputStream);
                    senMsg(protoId, shortMessage);
                    break;
                case PrototocalTools.IProtoServerIndex.request_phone_set_wifi_passwd:
                    TtPhoneWifiProtos.TtWifi ttWifi = TtPhoneWifiProtos.TtWifi.parseDelimitedFrom(inputStream);
                    senMsg(protoId, ttWifi);
                    break;
                case PrototocalTools.IProtoServerIndex.request_phone_server_enable_data:
                    TtPhoneMobileDataProtos.TtPhoneMobileData ttPhoneMobileData = TtPhoneMobileDataProtos.TtPhoneMobileData.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttPhoneMobileData);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_version_info:
                    TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion ttPhoneGetServerVersion = TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttPhoneGetServerVersion);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_recover_system:
                    TtPhoneRecoverSystemProtos.TtPhoneRecoverSystem recoverSystem = TtPhoneRecoverSystemProtos.TtPhoneRecoverSystem.parseDelimitedFrom(inputStream);
                    senMsg(protoId,recoverSystem);
                    break;
                case PrototocalTools.IProtoServerIndex.request_sos_message_send:
                    TtPhoneSosMessageProtos.TtPhoneSosMessage ttPhoneSosMessage = TtPhoneSosMessageProtos.TtPhoneSosMessage.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttPhoneSosMessage);
                    break;
                case PrototocalTools.IProtoServerIndex.request_phone_server_mobile_init:
                    TtPhoneMobileDataProtos.TtPhoneMobileData ttPhoneMobileDataInit = TtPhoneMobileDataProtos.TtPhoneMobileData.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttPhoneMobileDataInit);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_sos_status:
                    TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState = TtPhoneSosStateProtos.TtPhoneSosState.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttPhoneSosState);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_sos_close:
                    TtPhoneSosStateProtos.TtPhoneSosState ttSosClose = TtPhoneSosStateProtos.TtPhoneSosState.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttSosClose);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_del_calllog:
                    TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog = TtDeleCallLogProtos.TtDeleCallLog.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttDeleCallLog);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_del_sms:
                    TtDeleSmsProtos.TtDeleSms ttDeleSms = TtDeleSmsProtos.TtDeleSms.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttDeleSms);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_call_status:
                    TtCallRecordProtos.TtCallRecordProto ttCallRecordProto = TtCallRecordProtos.TtCallRecordProto.parseDelimitedFrom(inputStream);
                    senMsg(protoId,ttCallRecordProto);
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

    private void senAudioData(int protoId, GeneratedMessageV3 messageV3) {
        EventBus.getDefault().post(PhoneAudioCmd.getPhoneAudioCmd(protoId, messageV3));
    }

}
