package com.tt.qzy.view.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

public class RingToneUtils {

    private static MediaPlayer mMediaPlayer;
    /**
     * 获取铃声Uri
     */
    public static Uri getDetaultRingtoneUri(Context context,int type){
        return RingtoneManager.getActualDefaultRingtoneUri(context,type);
    }

    /**
     * 根据相应的Uri获取相应的铃声(Ringtone)
     */
    public Ringtone getDefaultRingtone(Context context,int type){
        return RingtoneManager.getRingtone(context,RingtoneManager.getActualDefaultRingtoneUri(context,type));
    }

    /**
     * 播放Ringtone
     */

    public static void playRingtone(Context context){
        mMediaPlayer = MediaPlayer.create(context,getDetaultRingtoneUri((context),RingtoneManager.TYPE_RINGTONE));
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public static void stopRingtone(Context context){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
        }else{
            mMediaPlayer = MediaPlayer.create(context,getDetaultRingtoneUri((context),RingtoneManager.TYPE_RINGTONE));
            mMediaPlayer.stop();
        }
    }
}
