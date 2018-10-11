package com.qzy.intercom.util;


/**
 * Created by yanghao1 on 2017/4/19.
 */

public class AudioDataUtil {

    /*The frame size in hardcoded for this sample code but it doesn't have to be*/
    private static int encFrameSize = 160;
    private static int decFrameSize = 160;
    private static int encodedFrameSize = 28;

    /**
     * 将raw原始音频文件编码为Speex格式
     *
     * @param audioData 原始音频数据
     * @return 编码后的数据
     */
    public static byte[] raw2spx(short[] audioData) {
        byte[] encodedData = new byte[audioData.length / encFrameSize * encodedFrameSize];
        SpeexUtils.getEnSpeex().encode(audioData, encodedData, audioData.length);
        return encodedData;
    }

    /**
     * 将Speex编码音频文件解码为raw音频格式
     *
     * @param encodedData 编码音频数据
     * @return 原始音频数据
     */
    public static short[] spx2raw(byte[] encodedData) {
        short[] shortRawData = new short[encodedData.length * decFrameSize / encodedFrameSize];
        SpeexUtils.getEnSpeex().decode(encodedData, shortRawData, encodedFrameSize);
        return shortRawData;
    }

   /* public static short[] dspraw(short[] encodedData) {
        Speex.getInstance().preprocessRunDenoise(encodedData);
        return encodedData;
    }*/

    /**
     * 释放音频编解码资源
     */
    public static void free() {
        SpeexUtils.getEnSpeex().release();
    }
}
