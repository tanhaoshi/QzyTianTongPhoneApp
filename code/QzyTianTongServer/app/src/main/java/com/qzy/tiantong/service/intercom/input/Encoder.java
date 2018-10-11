package com.qzy.intercom.input;

import android.os.Handler;

import com.qzy.locallib.endecode.G711Code;
import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.AudioDataUtil;
import com.qzy.intercom.util.SpeexUtils;
import com.qzy.tiantong.lib.utils.ByteUtils;


/**
 * 音频编码
 *
 * @author yanghao1
 */
public class Encoder extends JobHandler {

    public Encoder(Handler handler) {
        super(handler);
       // WebRtc.getInstance().init(8000);
    }

    @Override
    public void free() {
        AudioDataUtil.free();
       // WebRtc.getInstance().release();
    }

    @Override
    public void run() {
        AudioData data;
        // 在MessageQueue为空时，take方法阻塞
        while ((data = MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).take()) != null) {
            try {
                boolean isSpeex = false;
                if(isSpeex){
                    if(SpeexUtils.getEnSpeex().isFree()){
                        break;
                    }
                    data.setRawData(ByteUtils.byteArrayToShortArray(data.getEncodedData()));
                    data.setEncodedData(AudioDataUtil.raw2spx(data.getRawData()));
                    MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).put(data);
                    //Thread.sleep(20);
                }else {
                    boolean isg711 =false;
                    if(isg711){
                        data.setRawData(ByteUtils.byteArrayToShortArray(data.getEncodedData()));
                        data.setEncodedData(G711Code.G711aEncoder(data.getRawData()));
                    }
                    MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).put(data);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
