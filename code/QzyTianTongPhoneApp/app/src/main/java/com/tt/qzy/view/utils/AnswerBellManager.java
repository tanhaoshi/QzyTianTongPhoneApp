package com.tt.qzy.view.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class AnswerBellManager {

    private Context mContext;
    private MediaPlayer myMediaPlayer = null;

    public AnswerBellManager(Context context){
        this.mContext = context;
        initMediaPlay(mContext);
    }

    private void initMediaPlay(Context context){
        AudioManager mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        int mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前音乐音量
        int maxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获取最大声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); // 设置为最大声音，可通过SeekBar更改音量大小

        AssetFileDescriptor fileDescriptor;
        try {
            fileDescriptor = context.getAssets().openFd("answerthebell.mp3");
            myMediaPlayer = new MediaPlayer();
            myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            myMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),

                    fileDescriptor.getStartOffset(),

                    fileDescriptor.getLength());

            myMediaPlayer.prepare();
            myMediaPlayer.start();
            myMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//设置重复播放
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    myMediaPlayer.start();
                    myMediaPlayer.setLooping(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlay(){
        if(myMediaPlayer != null){
            if(myMediaPlayer.isPlaying()){
                myMediaPlayer.stop();
            }
        }
    }

    public void release(){
        if(myMediaPlayer != null){
            try{
                myMediaPlayer.release();
            }catch (Exception e){
                e.printStackTrace();
            }
            myMediaPlayer = null;
        }
    }
}
