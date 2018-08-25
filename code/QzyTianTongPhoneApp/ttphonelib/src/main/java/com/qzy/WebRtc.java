package com.qzy;

/**
 * Created by Administrator on 2018-08-06.
 */

public class WebRtc {
    static {
        try {
            System.loadLibrary("webrtc_agc");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static WebRtc webrtc_agc = null;

    private WebRtc() {
        init(8000);
    }

    public static WebRtc getInstance() {
        if (webrtc_agc == null) {
            synchronized (WebRtc.class) {
                if (webrtc_agc == null) {
                    webrtc_agc = new WebRtc();
                }
            }
        }
        return webrtc_agc;
    }
    public void release(){
        destroy();
    }
    public native int init(int fs);
    public native void run(short[] pcmData);
    public native void destroy();
}
