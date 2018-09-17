package com.qzy.utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by yj.zhang on 2018/8/14.
 */

public class AndroidVoiceManager {

    public static void setVoiceCall(Context ctx){
        setAudioMode(ctx, AudioManager.MODE_IN_COMMUNICATION);
    }

    public static void setVoiceMusic(Context ctx){
        setAudioMode(ctx, AudioManager.MODE_NORMAL);
    }


    /**
     * 设置语音播放的模式
     * @param ctx
     * @param mode
     */
    private static void setAudioMode(Context ctx, int mode) {
        if (mode != AudioManager.MODE_NORMAL && mode != AudioManager.MODE_IN_COMMUNICATION) {
            return;
        }
        AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        if (mode == AudioManager.MODE_NORMAL) {
            audioManager.setSpeakerphoneOn(true);//打开扬声器
        } else if (mode == AudioManager.MODE_IN_COMMUNICATION) {
            audioManager.setSpeakerphoneOn(false);//关闭扬声器
        }
        audioManager.setMode(mode);
    }

}
