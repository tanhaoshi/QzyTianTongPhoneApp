package com.qzy.intercom.output;

import android.os.Handler;

import com.qzy.data.PhoneAudioCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.tiantong.lib.eventbus.MessageEvent;
import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.tt.data.TtPhoneAudioDataProtos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
            if(phoneAudioCmd.getProtoId() != PrototocalTools.IProtoServerIndex.phone_audio){
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
