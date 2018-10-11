package com.qzy.tiantong.service.intercom.output;

import android.os.Handler;


import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.intercom.data.AudioData;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.job.JobHandler;
import com.qzy.voice.VoiceManager;


/**
 * AudioTrack音频播放
 *
 * @author yanghao1
 */
public class Tracker extends JobHandler {


    // 播放标志
    private boolean isPlaying;
    private int readLen = 0;

    public Tracker(Handler handler) {
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
        if (readLen == 0) {
            readLen = VoiceManager.initPcmPlayer();
        }
        while (isPlaying){
            if (readLen == 160 * 6 * 2) {
                AudioData audioData;
                while ((audioData = MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).take()) != null) {
                    if (isPlaying()) {
                        byte[] bytesPkg = audioData.getEncodedData();
                        try {
                            //LogUtils.e("write pcm index = " + ByteUtils.byteToInt(bytesPkg[0]));
                            VoiceManager.writePcmData(bytesPkg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        VoiceManager.releasePcmPlayer();
        LogUtils.e("player finished ...");
    }

    @Override
    public void free() {
        isPlaying = false;
    }

}
