package com.qzy.intercom.input;

import android.media.AudioRecord;
import android.os.Handler;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.Constants;
import com.qzy.utils.LogUtils;
import com.qzy.voice.VoiceManager;


/**
 * 音频录制数据格式ENCODING_PCM_16BIT，返回数据类型为short[]
 *
 * @author yanghao1
 */
public class Recorder extends JobHandler {


    // 录音标志
    private boolean isRecording = false;

    private int readLen = 0;

    public Recorder(Handler handler) {
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
        while (isRecording) {
            if (readLen == 0) {
                readLen = VoiceManager.initPcmRecorder();
            }
            if(readLen == 4096) {
                byte[] rawData = new byte[readLen];
                VoiceManager.readPcmData(rawData);
                AudioData audioData = new AudioData(rawData);
                MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);
            }
        }

        free();
    }

    @Override
    public void free() {
        // 释放音频录制资源
        VoiceManager.releasePcmRecorder();
    }

}
