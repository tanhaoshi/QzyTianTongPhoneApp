package com.qzy.intercom.output;

import android.os.Handler;
import android.os.Message;

import com.google.protobuf.ByteString;
import com.qzy.data.PhoneAudioCmd;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.MessageEvent;
import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.network.Unicast;
import com.qzy.intercom.util.Buffer;
import com.qzy.intercom.util.Command;
import com.qzy.tt.data.TtPhoneAudioDataProtos;
import com.qzy.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.DatagramPacket;
import java.util.Arrays;

/**
 * Created by yanghao1 on 2017/4/12.
 */

public class ReceiverNetty extends JobHandler {
    private boolean isReceiver;

    public ReceiverNetty(Handler handler) {
        super(handler);

    }

    @Override
    public void run() {
        isReceiver = true;
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEvent event) {
        if (event instanceof PhoneAudioCmd) {
            PhoneAudioCmd phoneAudioCmd = (PhoneAudioCmd) event;
            if(phoneAudioCmd.getProtoId() != PrototocalTools.IProtoClientIndex.tt_phone_audio){
                return;
            }
            TtPhoneAudioDataProtos.PhoneAudioData audioData = (TtPhoneAudioDataProtos.PhoneAudioData) phoneAudioCmd.getMessage();
            handleAudioData(audioData.getAudiodata().toByteArray());
        }
    }

    /**
     * 处理音频数据
     *
     * @param packet 音频数据包
     */
    Buffer buffer;

    private void handleAudioData(byte[] data) {
        AudioData audioData = new AudioData(Arrays.copyOfRange(data,1,data.length));
        // LogUtils.d("read  pcm data data.len =" + data.length);
        MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).put(audioData);
    }


    @Override
    public void free() {

        isReceiver = false;
        EventBus.getDefault().unregister(this);
    }
}
