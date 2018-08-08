package com.qzy.intercom.output;

import android.os.Handler;

import com.qzy.WebRtc;
import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.AudioDataUtil;
import com.qzy.utils.ByteUtils;
import com.sws.WebRtcTools;


/**
 * 音频解码
 *
 * @author yanghao1
 */
public class Decoder extends JobHandler {

    public Decoder(Handler handler) {
        super(handler);
       // WebRtc.getInstance().init(8000);
    }

    @Override
    public void run() {
        AudioData audioData;
        // 当MessageQueue为空时，take方法阻塞
        while ((audioData = MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).take()) != null) {
          // byte[] dataPcm = WebRtcTools.process(audioData.getEncodedData());
            byte[] dataPcm = audioData.getEncodedData();
            int readSize = dataPcm.length;
            byte[] chanelData = new byte[readSize * 2];
            for (int i = 0; i < readSize; i = i + 2) {
                chanelData[2 * i] = dataPcm[i];
                chanelData[2 * i + 1] = dataPcm[i + 1];
                chanelData[2 * i + 2] = dataPcm[i];
                chanelData[2 * i + 3] = dataPcm[i + 1];
            }

            short[] spcmData = ByteUtils.byteArrayToShortArray(chanelData);
            audioData.setRawData(spcmData);
            MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).put(audioData);
        }
    }

    @Override
    public void free() {
       // WebRtc.getInstance().release();
        AudioDataUtil.free();
    }
}
