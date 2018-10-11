package com.qzy.intercom.util;

import com.qzy.speex.Speex;

/**
 * Created by yj.zhang on 2018/8/16.
 */

public class SpeexUtils {

    private static Speex enSpeex;
    private static Speex deSpeex;

    public static Speex getEnSpeex() {
        if(enSpeex == null){
            enSpeex = new Speex();
        }
        return enSpeex;
    }


    public static Speex getDeSpeex() {
        if(deSpeex == null){
            deSpeex = new Speex();
        }
        return deSpeex;
    }

    public static void free(){
        enSpeex = null;
        deSpeex = null;
    }

}
