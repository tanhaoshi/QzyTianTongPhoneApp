package com.qzy.intercom.input;

import android.os.Handler;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.tiantong.ttpcm.TtAudioRecorder;


/**
 * 音频录制数据格式ENCODING_PCM_16BIT，返回数据类型为short[]
 *
 * @author yanghao1
 */
public class RecorderNew extends JobHandler {
    private TtAudioRecorder audioRecorder;

    public RecorderNew(Handler handler) {
        super(handler);
        audioRecorder = new TtAudioRecorder(new TtAudioRecorder.IReadPcmData() {
            @Override
            public void readData(byte[] rawData) {
                // LogUtils.d("readData...........");
                AudioData audioData = new AudioData(rawData);
                MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);
            }
        });
        audioRecorder.initTtAudioRecorder();
    }


    @Override
    public void run() {
        //LogUtils.e("RecorderNew run");
    }

    @Override
    public void free() {
        // 释放音频录制资源
        if(audioRecorder != null) {
            audioRecorder.releaseTtAudioRecorder();
            audioRecorder = null;
        }
    }


}
