package com.qzy.audiosocket.input;

import android.os.Handler;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.voice.VoiceManager;


/**
 * 音频录制数据格式ENCODING_PCM_16BIT，返回数据类型为short[]
 *
 * @author yanghao1
 */
public class RecorderSocket extends JobHandler {


    // 录音标志
    private boolean isRecording = false;

    private int readLen = -1;

    private boolean isFree = false;

    public RecorderSocket(Handler handler) {
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
        if (readLen == -1) {
            readLen = VoiceManager.initPcmRecorder();
        }
        byte[] rawData = new byte[readLen];
        while (isRecording) {
            //LogUtils.e("readLen == " + readLen);
            if (isRecording && readLen == 160 * 6) {
                VoiceManager.readPcmData(rawData);
                for(int i =0 ;i<3;i++) {
                    byte[] d = new byte[320];
                    System.arraycopy(rawData,i*320,d,0,d.length);
                    AudioData audioData = new AudioData(d);
                    MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);
                }
            }
        }

        VoiceManager.releasePcmRecorder();

        LogUtils.e("recorde finished ...");
    }

    @Override
    public void free() {
        // 释放音频录制资源
        //VoiceManager.releasePcmRecorder();
        isRecording = false;
        readLen = -1;
    }

}
