package com.tt.qzy.view.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.provider.Settings;

/**
 * Created by qzy009 on 2018/8/25.
 */

public class AudioUtil {

    private final Object mToneGeneratorLock = new Object();
    private ToneGenerator mToneGenerator;
    private static final int TONE_LENGTH_INFINITE = -1;
    private static final int TONE_RELATIVE_VOLUME = 80;
    private static final int DIAL_TONE_STREAM_TYPE = AudioManager.STREAM_DTMF;

    private static volatile AudioUtil mAudioUtil;
    private boolean mDTMFToneEnabled;

    private AudioUtil(Context context){
        final ContentResolver contentResolver = context
                .getContentResolver();
        mDTMFToneEnabled = Settings.System.getInt(contentResolver,
                Settings.System.DTMF_TONE_WHEN_DIALING, 1) == 1;
        synchronized (mToneGeneratorLock) {
            if (mToneGenerator == null) {
                try {
                    mToneGenerator = new ToneGenerator(DIAL_TONE_STREAM_TYPE,
                            TONE_RELATIVE_VOLUME);
                } catch (RuntimeException e) {
                    mToneGenerator = null;
                }
            }
        }
    }

    public static AudioUtil getInstance(Context context){
        if(mAudioUtil == null){
            synchronized(AudioUtil.class){
                if(mAudioUtil == null){
                    mAudioUtil = new AudioUtil(context);
                    mAudioUtil.initAudio(context);
                }
            }
        }
        return mAudioUtil;
    }

    private void initAudio(Context context){
        AudioManager audioManager = (AudioManager)context
                .getSystemService(Context.AUDIO_SERVICE);
        int ringerMode = audioManager.getRingerMode();
        if ((ringerMode == AudioManager.RINGER_MODE_SILENT)
                || (ringerMode == AudioManager.RINGER_MODE_VIBRATE)) {
            return;
        }
    }

    /**
     * 播放
     * @param tone
     */
    public void playTone(int tone){
        if (!mDTMFToneEnabled) {
            return;
        }
        synchronized (mToneGeneratorLock) {
            if (mToneGenerator == null) {
                return;
            }
            mToneGenerator.startTone(tone, TONE_LENGTH_INFINITE);
        }
    }

    /**
     * 暂停
     */
    public void stopTone() {
        if (!mDTMFToneEnabled) {
            return;
        }
        synchronized (mToneGeneratorLock) {
            if (mToneGenerator == null) {
                return;
            }
            mToneGenerator.stopTone();
        }
    }
}
