package com.qzy.intercom.output;

import android.os.Handler;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.voice.VoiceManager;

import java.util.Arrays;


/**
 * AudioTrack音频播放
 *
 * @author yanghao1
 */
public class TrackerOld extends JobHandler {


    // 播放标志
    private boolean isPlaying;
    private int readLen = 0;

    public TrackerOld(Handler handler) {
        super(handler);

    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    @Override
    public void run() {
        while (isPlaying){
            if (readLen == 0) {
                readLen = VoiceManager.initPcmPlayer();
            }
            if (readLen == 160 * 8) {
                AudioData audioData;
                while ((audioData = MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).take()) != null) {
                    if (isPlaying()) {
                        byte[] bytesPkg = audioData.getEncodedData();
                        try {
                            //LogUtils.e("write pcm index = " + ByteUtils.byteToInt(bytesPkg[0]));
                            VoiceManager.writePcmData(Arrays.copyOfRange(bytesPkg,1,bytesPkg.length));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        free();
    }

    @Override
    public void free() {
        VoiceManager.releasePcmPlayer();
    }
}
