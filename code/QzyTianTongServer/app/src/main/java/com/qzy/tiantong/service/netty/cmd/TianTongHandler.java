package com.qzy.tiantong.service.netty.cmd;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.qzy.tiantong.service.phone.TtPhoneSystemanager;
import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.ChangePcmPlayerDbProtos;
import com.qzy.tt.data.TtBeiDouStatuss;
import com.qzy.tt.data.TtDeleCallLogProtos;
import com.qzy.tt.data.TtDeleSmsProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
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
import com.qzy.tt.probuf.lib.data.PrototocalTools;
import com.qzy.voice.VoiceManager;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class TianTongHandler extends Handler {

    public static final int msg_init_localpcm = 0x01;

    private ITianTongServer mServer;

    public TianTongHandler(ITianTongServer server) {
        mServer = server;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void handleMessage(Message msg) {

        try {
            switch (msg.what) {
                case 222:
                    //mServer.startRecorder();
                    break;
                case msg_init_localpcm:
                    //mServer.initLocalPcmDevice();
                    break;
                case PrototocalTools.IProtoServerIndex.call_phone:
                    CallPhoneProtos.CallPhone callPhone = (CallPhoneProtos.CallPhone) msg.obj;
                    if (callPhone != null) {
                        if (callPhone.getPhonecommand() == CallPhoneProtos.CallPhone.PhoneCommand.CALL) {
                            mServer.getQzyPhoneManager().callPhone(callPhone.getIp(), callPhone.getPhoneNumber());
                        } else if (callPhone.getPhonecommand() == CallPhoneProtos.CallPhone.PhoneCommand.HUANGUP) {
                            mServer.getQzyPhoneManager().hangupPhone(callPhone.getIp());
                        } else if (callPhone.getPhonecommand() == CallPhoneProtos.CallPhone.PhoneCommand.ACCEPTCALL) {
                            mServer.getQzyPhoneManager().acceptCalling(callPhone.getIp());
                        }
                    }
                    break;
                case PrototocalTools.IProtoServerIndex.chang_pcmplayer_db:
                    ChangePcmPlayerDbProtos.ChangePcmPlayerDb changePcmPlayerDb = (ChangePcmPlayerDbProtos.ChangePcmPlayerDb) msg.obj;
                    VoiceManager.setVolume(changePcmPlayerDb.getDb());
                    break;
                case PrototocalTools.IProtoServerIndex.phone_send_sms:
                    TtPhoneSmsProtos.TtPhoneSms ttPhoneSms = (TtPhoneSmsProtos.TtPhoneSms) msg.obj;
                    mServer.sendSms(ttPhoneSms);
                    break;
                case PrototocalTools.IProtoServerIndex.request_gps_position:
                    mServer.getPhoneNettyManager().getmGpsManager().parseProtocalControl((TtPhonePositionProtos.TtPhonePosition) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_open_beidou_usb:
                    mServer.getPhoneNettyManager().getmTtUsbManager().parseUsbModel((TtOpenBeiDouProtos.TtOpenBeiDou) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_tt_time:
                    mServer.getPhoneNettyManager().getmDateTimeManager().setDataAndTime((TtTimeProtos.TtTime) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_phone_send_sms_read:
                    mServer.getPhoneNettyManager().getmSmsPhoneManager().updateSmsRead((TtShortMessageProtos.TtShortMessage.ShortMessage) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_phone_set_wifi_passwd:
                    mServer.getQzyPhoneManager().setWifiPasswd((TtPhoneWifiProtos.TtWifi) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_phone_server_enable_data:
                    mServer.getPhoneNettyManager().setEnablePhoneData((TtPhoneMobileDataProtos.TtPhoneMobileData) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_version_info:
                    mServer.getPhoneNettyManager().getServerVerion((TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_recover_system:
                    mServer.getPhoneNettyManager().getRecoverSystem((TtPhoneRecoverSystemProtos.TtPhoneRecoverSystem) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_sos_message_send:
                    mServer.getPhoneNettyManager().getmSmsPhoneManager().getSaveSosMsg((TtPhoneSosMessageProtos.TtPhoneSosMessage) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_phone_server_mobile_init:
                    mServer.getPhoneNettyManager().getServerMobileDataStatus();
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_sos_status:
                    mServer.getPhoneNettyManager().getServerSosInitStatus();
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_sos_close:
                    mServer.getPhoneNettyManager().closeServerSos((TtPhoneSosStateProtos.TtPhoneSosState) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_del_calllog:
                    mServer.getPhoneNettyManager().deleteCallLog((TtDeleCallLogProtos.TtDeleCallLog) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_server_del_sms:
                   // TtDeleSmsProtos.TtDeleSms ttDeleSms = TtDeleSmsProtos.TtDeleSms.parseDelimitedFrom(inputStream);
                   // senMsg(protoId,ttDeleSms);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
