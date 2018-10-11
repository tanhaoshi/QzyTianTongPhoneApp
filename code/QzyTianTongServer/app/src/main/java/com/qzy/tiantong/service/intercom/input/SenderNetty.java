package com.qzy.tiantong.service.intercom.input;

import android.os.Handler;

import com.google.protobuf.ByteString;

import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.service.intercom.data.AudioData;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.job.JobHandler;
import com.qzy.tt.data.TtPhoneAudioDataProtos;

import com.qzy.tt.probuf.lib.data.PhoneAudioCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import org.greenrobot.eventbus.EventBus;

/**
 * Socket发送
 *
 * @author yanghao1
 */
public class SenderNetty extends JobHandler {

    private int id = 0;

    public SenderNetty(Handler handler) {
        super(handler);
    }

    @Override
    public void run() {
        AudioData audioData;
        while ((audioData = MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).take()) != null) {
            int len = audioData.getEncodedData().length;
           // LogUtils.e("audioData.getEncodedData().length = " + len);
            byte[] data = new byte[audioData.getEncodedData().length + 1];
            if (id < 256) {
                id++;
            } else {
                id = 1;
            }

            data[0] = ByteUtils.intToByte(id);
            System.arraycopy(audioData.getEncodedData(),0,data,1,len);

            TtPhoneAudioDataProtos.PhoneAudioData phoneAudioData = TtPhoneAudioDataProtos.PhoneAudioData.newBuilder()
                    .setAudiodata(ByteString.copyFrom(data))
                    .build();
             EventBus.getDefault().post(PhoneAudioCmd.getPhoneAudioCmd(PrototocalTools.IProtoClientIndex.tt_phone_audio,phoneAudioData));
        }
    }

    @Override
    public void free() {
        //Multicast.getMulticast().free();
        //Unicast.getUnicast().free();
    }
}
