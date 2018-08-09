package com.qzy.tiantong.service.netty.cmd;

import android.os.Handler;
import android.os.Message;

import com.qzy.data.PrototocalTools;
import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.ChangePcmPlayerDbProtos;
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
                            mServer.getQzyPhoneManager().callPhone(callPhone.getPhoneNumber());
                        } else if (callPhone.getPhonecommand() == CallPhoneProtos.CallPhone.PhoneCommand.HUANGUP) {
                            mServer.getQzyPhoneManager().hangupPhone();
                        }
                    }
                    break;
                case PrototocalTools.IProtoServerIndex.chang_pcmplayer_db:
                    ChangePcmPlayerDbProtos.ChangePcmPlayerDb changePcmPlayerDb = (ChangePcmPlayerDbProtos.ChangePcmPlayerDb)msg.obj;
                    VoiceManager.setVolume(changePcmPlayerDb.getDb());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
