package com.qzy;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;


/**
 * Created by yj.zhang on 2018/9/21.
 */

public class QzySensorManager {
    private Context mContext;
    /**
     * 距离感应
     */
    private Sensor mSensor;
    private PowerManager.WakeLock mWakeLock;

    private  SensorManager  sensorManager;

    private  PowerManager mPowerManager;

    public QzySensorManager(Context context) {
        mContext = context;
        initSenerState();
    }


    /**
     * 初始化senser
     */
    private void initSenerState(){
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(sensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        //息屏设置
        mPowerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,"zyj_tt_phone");
    }


    /**
     * 释放 距离感应
     */
    public void freeSenerState(){
        sensorManager.unregisterListener(sensorEventListener);
        if(mWakeLock != null){
            if (mWakeLock.isHeld())
                mWakeLock.release();
        }
        mSensor = null;
        mWakeLock = null;
    }


    /**
     * sensor监听器
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[0] == 0.0) {


                //关闭屏幕
                if (!mWakeLock.isHeld())
                    mWakeLock.acquire();

            } else {
                //唤醒设备
                if (mWakeLock.isHeld())
                    mWakeLock.release();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

}
