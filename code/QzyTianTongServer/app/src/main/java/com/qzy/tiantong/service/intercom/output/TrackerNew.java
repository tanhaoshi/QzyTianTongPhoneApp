package com.qzy.tiantong.service.intercom.output;

import android.os.Handler;


import com.qzy.tiantong.service.intercom.data.AudioData;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.job.JobHandler;
import com.qzy.tiantong.service.ttpcm.TtAudioTrack;


/**
 * AudioTrack音频播放
 *
 * @author yanghao1
 */
public class TrackerNew extends JobHandler {

    private TtAudioTrack audioTrack;


    public TrackerNew(Handler handler) {
        super(handler);

    }

    @Override
    public void run() {
        audioTrack = new TtAudioTrack();
        audioTrack.initTtAudioTrack();
        AudioData audioData;
        while ((audioData = MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).take()) != null) {
            if (audioTrack != null) {
                byte[] bytesPkg = audioData.getEncodedData();
                try {
                   // LogUtils.e("write pcm index = " + ByteUtils.byteToInt(bytesPkg[0]));
                    audioTrack.setPcmData(bytesPkg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void free() {
        if(audioTrack != null){
            audioTrack.release();
            audioTrack = null;
        }

    }
}
