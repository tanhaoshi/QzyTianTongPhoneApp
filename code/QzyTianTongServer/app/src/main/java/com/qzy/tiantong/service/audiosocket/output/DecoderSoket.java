package com.qzy.audiosocket.output;

import android.os.Handler;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.AudioDataUtilDe;
import com.qzy.intercom.util.SpeexUtils;
import com.qzy.tiantong.lib.utils.ByteUtils;


/**
 * 音频解码
 *
 * @author yanghao1
 */
public class DecoderSoket extends JobHandler {

    public DecoderSoket(Handler handler) {
        super(handler);
       // WebRtc.getInstance().init(8000);
    }

    @Override
    public void run() {
        AudioData audioData;
        // 当MessageQueue为空时，take方法阻塞
       // WebRtcTools webRtcTools = WebRtcTools.getInstance();
        while ((audioData = MessageQueue.getInstance(MessageQueue.DECODER_DATA_QUEUE).take()) != null) {
              boolean isSpeex = false;
             if(isSpeex){
                  if(SpeexUtils.getDeSpeex().isFree()){
                       break;
                  }
                 //audioData.setRawData(ByteUtils.byteArrayToShortArray(audioData.getEncodedData()));
                // audioData.setEncodedData(AudioDataUtil.raw2spx(audioData.getRawData()));
                 audioData.setRawData(AudioDataUtilDe.spx2raw(audioData.getEncodedData()));
                 short[] ra = audioData.getRawData();
                 int size = ra.length;
                 short[] channelTwo = new short[size * 2];
                 for (int i = 0; i < size; i = i + 2) {
                     channelTwo[2 * i] = ra[i];
                     channelTwo[2 * i + 1] = ra[i + 1];
                     channelTwo[2 * i + 2] = ra[i];
                     channelTwo[2 * i + 3] = ra[i + 1];
                 }
                 audioData.setEncodedData(ByteUtils.toByteArray(channelTwo));
             }else{
                 byte[] dataPcm = audioData.getEncodedData();
                 int readSize = dataPcm.length;
                 byte[] chanelData = new byte[readSize * 2];
                 for (int i = 0; i < readSize; i = i + 2) {
                     chanelData[2 * i] = dataPcm[i];
                     chanelData[2 * i + 1] = dataPcm[i + 1];
                     chanelData[2 * i + 2] = dataPcm[i];
                     chanelData[2 * i + 3] = dataPcm[i + 1];
                 }


                 audioData.setEncodedData(chanelData);
             }



            MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).put(audioData);
        }
      //  webRtcTools.free();
    }

    @Override
    public void free() {
       // WebRtc.getInstance().release();
        AudioDataUtilDe.free();
    }
}
