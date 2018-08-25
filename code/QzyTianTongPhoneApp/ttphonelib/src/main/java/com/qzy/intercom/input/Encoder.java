package com.qzy.intercom.input;

import android.os.Handler;

import com.qzy.endecode.G711Code;
import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.AudioDataUtil;
import com.qzy.utils.ByteUtils;


/**
 * 音频编码
 *
 * @author yanghao1
 */
public class Encoder extends JobHandler {

    public Encoder(Handler handler) {
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
            //data.setEncodedData(ByteUtils.toByteArray(data.getRawData()));
            boolean isg711 =false;
            if(isg711){
                data.setEncodedData(G711Code.G711aEncoder(data.getRawData()));
            }else{
                data.setEncodedData(ByteUtils.toByteArray(data.getRawData()));
            }
            MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).put(data);
        }
    }
}
