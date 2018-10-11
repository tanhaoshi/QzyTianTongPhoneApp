package com.qzy.intercom.output;

import android.os.Handler;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.tiantong.ttpcm.aidl.WritePcmManager;
import com.qzy.tiantong.lib.utils.LogUtils;


/**
 * AudioTrack音频播放
 *
 * @author yanghao1
 */
public class TrackerAidl extends JobHandler {


    // 播放标志
    private boolean isPlaying;
    private int readLen = 0;


    public TrackerAidl(Handler handler) {
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
        try {
            boolean isAidlStart = false;
            AudioData audioData;
            while (isPlaying) {
                if (WritePcmManager.getInstance().getiPcmManagerWrite() == null) {
                    Thread.sleep(3000);
                    continue;
                }

                if (!isAidlStart) {
                    isAidlStart = WritePcmManager.getInstance().start();
                }

                while ((audioData = MessageQueue.getInstance(MessageQueue.TRACKER_DATA_QUEUE).take()) != null) {
                    if (isPlaying()) {
                        byte[] bytesPkg = audioData.getEncodedData();
                        try {
                            //LogUtils.e("write pcm index = " + ByteUtils.byteToInt(bytesPkg[0]));
                            WritePcmManager.getInstance().getiPcmManagerWrite().writePcmData(bytesPkg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            WritePcmManager.getInstance().stop();
        }

        LogUtils.e("player finished ...");
    }

    @Override
    public void free() {
        isPlaying = false;
    }

}
