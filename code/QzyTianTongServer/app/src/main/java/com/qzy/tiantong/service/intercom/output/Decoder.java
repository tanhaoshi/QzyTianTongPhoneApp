package com.qzy.tiantong.service.intercom.output;

import android.os.Handler;

import com.qzy.locallib.endecode.G711Code;
import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.service.intercom.data.AudioData;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.job.JobHandler;
import com.qzy.tiantong.service.intercom.util.AudioDataUtilDe;
import com.qzy.tiantong.service.intercom.util.SpeexUtils;


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
           // LogUtils.d("Decoder tid = " + android.os.Process.myTid()+" name "+Thread.currentThread().getName());
            boolean isSpeex = false;
            if(isSpeex) {
                if (SpeexUtils.getDeSpeex().isFree()) {
                    break;
                }
                audioData.setRawData(AudioDataUtilDe.spx2raw(audioData.getEncodedData()));
                audioData.setEncodedData(ByteUtils.toByteArray(audioData.getRawData()));
            }

            boolean isg711 =false;
            if(isg711){
                audioData.setRawData(G711Code.G711aDecoder(audioData.getEncodedData()));
                audioData.setEncodedData(ByteUtils.toByteArray(audioData.getRawData()));
            }

            MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).put(audioData);
        }
    }

    @Override
    public void free() {
        AudioDataUtilDe.free();
    }
}
