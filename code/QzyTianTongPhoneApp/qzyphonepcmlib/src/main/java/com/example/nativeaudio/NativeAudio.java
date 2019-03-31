package com.example.nativeaudio;

/**
 * Created by yj.zhang on 2018/9/1.
 */

public class NativeAudio {
    /** Load jni .so on initialization */
    static {
        //System.loadLibrary("Oups");
        System.loadLibrary("native-audio-jni");

    }

    /*sampleRate 8000, input_chs , outpu_chs 都传入1， frames 传入480*/
    public static native void createEngine(int sampleRate, int input_chs, int output_chs, int frames);
    // true == PLAYING, false == PAUSED
    /*ip 为服务端IP地址， 端口为8999*/
    public static native boolean createUdpSocket(String ip, short port);
    /*资源释放*/
    public static native void shutdown();
    /*state 输入0 为挂断， 1为响铃， 2 为通话中*/

    public static native void startRecord();
    public static native void startPlayer();
    public static native void stopRecord();
    public static native void stopPlayer();

    public static native void realeseAudioDevice();


}
