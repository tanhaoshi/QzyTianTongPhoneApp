package com.qzy.ring;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import java.lang.reflect.Field;

/**
 * Created by yj.zhang on 2018/9/3.
 */

public class RingManager {

    private static   Ringtone r;

    private static  Vibrator vibrator;

    /**
     * 播放系统默认提示音
     *
     * @return MediaPlayer对象
     *
     * @throws Exception
     */
    public static void defaultMediaPlayer(Context context){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 播放系统默认来电铃声
     *
     * @return MediaPlayer对象
     *
     * @throws Exception
     */
    public static void playDefaultCallMediaPlayer(Context context){
        try {
            if(r== null) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                r = RingtoneManager.getRingtone(context, notification);
                r.setStreamType(AudioManager.STREAM_RING);
                setRingtoneRepeat(r);
            }
            r.play();
//            if(vibrator == null) {
//                vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
//            }
//           // 等待3秒，震动3秒，从第0个索引开始，一直循环
//            vibrator.vibrate(new long[]{3000, 3000}, 0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //反射设置闹铃重复播放
    private static void setRingtoneRepeat(Ringtone ringtone) {
        Class<Ringtone> clazz =Ringtone.class;
        try {
            Field field = clazz.getDeclaredField("mLocalPlayer");//返回一个 Field 对象，它反映此 Class 对象所表示的类或接口的指定公共成员字段（※这里要进源码查看属性字段）
            field.setAccessible(true);
            MediaPlayer target = (MediaPlayer) field.get(ringtone);//返回指定对象上此 Field 表示的字段的值
            target.setLooping(true);//设置循环
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void stopDefaultCallMediaPlayer(Context context){
        try {
            if(r== null) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                r = RingtoneManager.getRingtone(context, notification);
            }
            r.stop();

           /* if(vibrator == null) {
                vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
            }
            vibrator.cancel();*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 播放系统默认闹钟铃声
     *
     * @return MediaPlayer对象
     *
     * @throws Exception
     */
    public static void defaultAlarmMediaPlayer(Context context){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
