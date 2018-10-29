package com.qzy.tiantong.service.netty.cmd;

import android.os.Handler;
import android.os.Message;

import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tiantong.service.update.IUpdateManager;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.ChangePcmPlayerDbProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtPhoneUpdateAppInfoProtos;
import com.qzy.tt.data.TtPhoneUpdateSendFileProtos;
import com.qzy.tt.probuf.lib.data.PrototocalTools;
import com.qzy.voice.VoiceManager;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class TianTongHandlerUpdate extends Handler {

    private IUpdateManager mIUpdateManager;

    public TianTongHandlerUpdate(IUpdateManager updateManager) {
        mIUpdateManager = updateManager;
    }

    @Override
    public void handleMessage(Message msg) {

        try {
            switch (msg.what) {
                case PrototocalTools.IProtoServerIndex.request_update_phone_aapinfo:
                    mIUpdateManager.checkUpdate((TtPhoneUpdateAppInfoProtos.UpdateAppInfo) msg.obj);
                    break;
                case PrototocalTools.IProtoServerIndex.request_update_send_zip:
                    mIUpdateManager.receiverZipFile((TtPhoneUpdateSendFileProtos.UpdateSendFile) msg.obj);
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
