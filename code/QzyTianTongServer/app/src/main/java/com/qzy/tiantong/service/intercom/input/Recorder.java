package com.qzy.tiantong.service.intercom.input;

import android.os.Handler;


import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.intercom.data.AudioData;
import com.qzy.tiantong.service.intercom.data.MessageQueue;
import com.qzy.tiantong.service.intercom.job.JobHandler;
import com.qzy.voice.VoiceManager;


/**
 * 音频录制数据格式ENCODING_PCM_16BIT，返回数据类型为short[]
 *
 * @author yanghao1
 */
public class Recorder extends JobHandler {


    // 录音标志
    private boolean isRecording = false;

    private int readLen = -1;

    private boolean isFree = false;

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
        if (readLen == -1) {
            readLen = VoiceManager.initPcmRecorder();
        }
        byte[] rawData = new byte[readLen];
        while (isRecording) {
            //LogUtils.e("readLen == " + readLen);
            if (isRecording && readLen == 160 * 6 * 2) {
                long start = System.currentTimeMillis();
                VoiceManager.readPcmData(rawData);
                long end = System.currentTimeMillis() - start;
                if(end > 180) {
                    LogUtils.e("read time over 180 time = " + end);
                }
                /*for(int i =0 ;i<3;i++) {
                    byte[] d = new byte[320];
                    System.arraycopy(rawData,i*320,d,0,d.length);
                    AudioData audioData = new AudioData(d);
                    MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);
                }*/
                /*byte[] d = new byte[rawData.length];
                System.arraycopy(rawData,0,d,0,d.length);
                AudioData audioData = new AudioData(d);
                MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);*/

                 for(int i =0 ;i<2;i++) {
                    byte[] d = new byte[960];
                    System.arraycopy(rawData,i*960,d,0,d.length);
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
