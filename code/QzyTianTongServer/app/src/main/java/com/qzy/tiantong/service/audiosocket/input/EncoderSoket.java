package com.qzy.tiantong.service.audiosocket.input;

import android.os.Handler;

import com.qzy.tiantong.service.intercom.data.AudioData;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.job.JobHandler;


/**
 * 音频编码
 *
 * @author yanghao1
 */
public class EncoderSoket extends JobHandler {

    public EncoderSoket(Handler handler) {
        super(handler);
    }

    @Override
    public void free() {
       // AudioDataUtil.free();
    }

    @Override
    public void run() {
        AudioData data;
        // 在MessageQueue为空时，take方法阻塞
        while ((data = MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).take()) != null) {
            //data.setEncodedData(AudioDataUtil.raw2spx(data.getRawData()));
            MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).put(data);
        }
    }
}
