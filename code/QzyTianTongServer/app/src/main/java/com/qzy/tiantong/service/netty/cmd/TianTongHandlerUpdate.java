package com.qzy.tiantong.service.netty.cmd;

import android.os.Handler;
import android.os.Message;

import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.ChangePcmPlayerDbProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.probuf.lib.data.PrototocalTools;
import com.qzy.voice.VoiceManager;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class TianTongHandlerUpdate extends Handler {


    public TianTongHandlerUpdate() {
    }

    @Override
    public void handleMessage(Message msg) {

        try {
            switch (msg.what) {
                case PrototocalTools.IProtoServerIndex.request_update_phone_aapinfo:

                    break;
                case PrototocalTools.IProtoServerIndex.request_update_send_zip:

                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
