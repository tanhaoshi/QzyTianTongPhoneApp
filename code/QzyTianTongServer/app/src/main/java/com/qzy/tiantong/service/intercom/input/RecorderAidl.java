package com.qzy.tiantong.service.intercom.input;

import android.os.Handler;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.intercom.data.AudioData;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.job.JobHandler;
import com.qzy.tiantong.service.ttpcm.aidl.ReadPcmManager;


/**
 * 音频录制数据格式ENCODING_PCM_16BIT，返回数据类型为short[]
 *
 * @author yanghao1
 */
public class RecorderAidl extends JobHandler {


    // 录音标志
    private boolean isRecording = false;


    public RecorderAidl(Handler handler) {
        super(handler);

    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }

    @Override
    public void run() {
        try {
            boolean isAidlStart = false;
            while (isRecording) {
                if (ReadPcmManager.getInstance().getiPcmManagerRead() == null) {
                    LogUtils.e("sleep recorder ..");
                    Thread.sleep(3000);
                    continue;
                }
                if (!isAidlStart) {
                    isAidlStart = ReadPcmManager.getInstance().start();
                }
                byte[] data = ReadPcmManager.getInstance().getiPcmManagerRead().getPcmData();
                if (data != null) {
                    byte[] rawData = new byte[data.length];
                    System.arraycopy(data, 0, rawData, 0, data.length);
                    ReadPcmManager.getInstance().getiPcmManagerRead().removePcmData();
                    AudioData audioData = new AudioData(rawData);
                    MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);
                } else {
                    Thread.sleep(500);
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReadPcmManager.getInstance().stop();
        }


        LogUtils.e("recorde finished ...");
    }

    @Override
    public void free() {
        // 释放音频录制资源
        //VoiceManager.releasePcmRecorder();
        isRecording = false;
    }

}
