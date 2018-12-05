package com.tt.qzy.view.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.io.IOException;

public class RingToneUtils {

    public static void playRing(Context context) {
        MediaPlayer player = null;
        try {

            player = new MediaPlayer();

            AssetManager assetManager = context.getAssets();

            AssetFileDescriptor fileDescriptor = assetManager.openFd("shortmessage.mp3");

            player.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),
                    fileDescriptor.getStartOffset());

            player.prepare();

            player.start();

        } catch (IOException e) {

            e.printStackTrace();

        }finally {
            player.release();
        }
    }

}
