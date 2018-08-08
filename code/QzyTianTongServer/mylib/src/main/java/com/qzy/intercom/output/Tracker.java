package com.qzy.intercom.output;

import android.media.AudioTrack;
import android.os.Handler;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.ByteUtils;
import com.qzy.intercom.util.Constants;
import com.qzy.ttpcm.TtAudioTrack;
import com.qzy.utils.LogUtils;
import com.qzy.voice.VoiceManager;

import java.util.Arrays;


/**
 * AudioTrack音频播放
 *
 * @author yanghao1
 */
public class Tracker extends JobHandler {

    private TtAudioTrack audioTrack;


    public Tracker(Handler handler) {
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
