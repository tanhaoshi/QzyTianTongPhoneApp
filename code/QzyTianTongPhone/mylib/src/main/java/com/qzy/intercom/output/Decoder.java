package com.qzy.intercom.output;

import android.os.Handler;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.AudioDataUtil;


/**
 * 音频解码
 *
 * @author yanghao1
 */
public class Decoder extends JobHandler {

    public Decoder(Handler handler) {
        super(handler);
    }

    @Override
    public void run() {
        AudioData audioData;
        // 当MessageQueue为空时，take方法阻塞
        while ((audioData = MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).take()) != null) {
           // byte[] encodeData = AudioDataUtil.raw2spx(audioData.getRawData());
           // audioData.setRawData(AudioDataUtil.spx2raw(encodeData));
            //AudioDataUtil.dspraw(audioData.getRawData());
            MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).put(audioData);
        }
    }

    @Override
    public void free() {
        AudioDataUtil.free();
    }
}
