package com.qzy.intercom.input;

import android.os.Handler;

import com.qzy.WebRtc;
import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.AudioDataUtil;
import com.qzy.intercom.util.ByteUtils;
import com.qzy.utils.LogUtils;


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
            //LogUtils.d("Encoder tid = " + android.os.Process.myTid()+" name "+Thread.currentThread().getName());
            //data.setEncodedData(AudioDataUtil.raw2spx(data.getRawData()));
           // byte[] encode = data.getEncodedData();
           // data.setEncodedData(ByteUtils.shortArrayToByteArray(AudioDataUtil.spx2raw(encode)));
           // data.setEncodedData(ByteUtils.toByteArray(data.getRawData()));
            /*byte[] pcmData = data.getEncodedData();
            short[] sPcmData = ByteUtils.byteArrayToShortArray(pcmData);
            WebRtc.getInstance().run(sPcmData);
            pcmData = ByteUtils.shortArrayToByteArray(sPcmData);
            data.setEncodedData(pcmData);*/
            MessageQueue.getInstance(MessageQueue.SENDER_DATA_QUEUE).put(data);
        }
    }
}
