package com.qzy.audiosocket.input;

import android.media.AudioRecord;
import android.os.Handler;

import com.qzy.intercom.data.AudioData;
import com.qzy.intercom.data.MessageQueue;
import com.qzy.intercom.job.JobHandler;
import com.qzy.intercom.util.Constants;
import com.qzy.utils.ByteUtils;
import com.qzy.utils.LogUtils;
import com.qzy.voice.VoiceInputUtils;


/**
 * 音频录制数据格式ENCODING_PCM_16BIT，返回数据类型为short[]
 *
 * @author yanghao1
 */
public class RecorderSoket extends JobHandler {

    private AudioRecord audioRecord;
    // 音频大小
    private int inAudioBufferSize;
    // 录音标志
    private boolean isRecording = false;

    public RecorderSoket(Handler handler) {
        super(handler);
        // 获取音频数据缓冲段大小
        inAudioBufferSize = AudioRecord.getMinBufferSize(
                Constants.sampleRateInHz, Constants.inputChannelConfig, Constants.audioFormat);
        LogUtils.e("inAudioBufferSize = " + inAudioBufferSize);
        // 初始化音频录制
        audioRecord = new AudioRecord(Constants.audioSource,
                Constants.sampleRateInHz, Constants.inputChannelConfig, Constants.audioFormat, inAudioBufferSize);
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }

    @Override
    public void run() {
        short[] rawData = new short[160 * 3 * 2 ];
        while (isRecording) {
            if(!isRecording){
                break;
            }
            if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
                audioRecord.startRecording();
            }
            // 实例化音频数据缓冲
           /* short[] rawData = new short[1024 * 2];
            audioRecord.read(rawData, 0, inAudioBufferSize);*/

            audioRecord.read(rawData, 0, rawData.length);



            //LogUtils.e("rawData.length = " + rawData.length);
            int readSize = rawData.length;
            short[] leftChannelAudioData = new short[readSize / 2];
            for (int i = 0; i < readSize / 2; i = i + 2) {
                leftChannelAudioData[i] = rawData[2 * i];
                leftChannelAudioData[i + 1] = rawData[2 * i + 1];
                //rightChannelAudioData[i] =  audiodata[2*i+2];
                //rightChannelAudioData[i+1] = audiodata[2*i+3];
            }

            if (VoiceInputUtils.calculateVolume(ByteUtils.toByteArray(leftChannelAudioData), 16) < 1) {
                //rawData = new byte[160 * 6 * 2 ];
                continue;
            }

            AudioData audioData = new AudioData(leftChannelAudioData);
           // AudioData audioData = new AudioData(rawData);
            MessageQueue.getInstance(MessageQueue.ENCODER_DATA_QUEUE).put(audioData);
        }
    }

    @Override
    public void free() {
        // 释放音频录制资源
        isRecording = false;
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;

    }
}
