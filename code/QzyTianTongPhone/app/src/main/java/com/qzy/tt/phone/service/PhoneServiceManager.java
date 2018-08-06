package com.qzy.tt.phone.service;

import android.content.Context;
import android.widget.Toast;

import com.qzy.eventbus.MessageEvent;
import com.qzy.eventbus.NettyStateModel;
import com.qzy.intercom.IntercomManager;
import com.qzy.intercom.input.Encoder;
import com.qzy.netty.NettyClientManager;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.eventbus.CommandModel;
import com.qzy.voice.VoiceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class PhoneServiceManager implements NettyClientManager.INettyListener {

    private Context mContext;

    private NettyClientManager mNettyClientManager;

    //private VoiceManager mVoiceManager;

    private IntercomManager mIntercomManager;

    public PhoneServiceManager(Context context) {
        mContext = context;

        mNettyClientManager = new NettyClientManager(this);
        mNettyClientManager.startConnect();

        //mVoiceManager = new VoiceManager();

        mIntercomManager = new IntercomManager();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onReceiveData(byte[] data) {

    }

    @Override
    public void onConnected() {
        EventBus.getDefault().post(new NettyStateModel(true));
        // mVoiceManager.start();
    }

    @Override
    public void onDisconnected() {
        EventBus.getDefault().post(new NettyStateModel(false));
        // mVoiceManager.stop();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEvent event) {
        if (event instanceof CommandModel) {
            CommandModel commandModel = (CommandModel) event;
            if(!commandModel.getMsgTag().equals(PhoneServiceManager.class.getSimpleName())){
                if (commandModel.isStartRecorder()) {
                    startRecord();
                } else {
                    stopRecord();
                }
            }

            if(commandModel.getMsgTag().equals("call_phone")){
                callPhone(commandModel.getPhoneNumber());
            }

        }

    }

    /**
     * 开始录音
     */
    public void startRecord() {
        if (mIntercomManager != null) {
            mIntercomManager.startRecord();
            setRecorderState(true);
        }
    }


    /**
     * 结束录音
     */
    public void stopRecord() {
        if (mIntercomManager != null) {
            mIntercomManager.stopRecord();
            setRecorderState(false);
        }
    }

    /**
     * 打电话
     * @param phone
     */
    public void callPhone(String phone){
        if(mNettyClientManager != null){

            if(phone != null && !phone.equals("")&&phone.length()==11){
                mNettyClientManager.sendData(phone.getBytes());
            }else {
               // Toast.makeText(MainActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            }
        }

    }


    /**
     * 开启录音方法
     * @param state
     */
    private void setRecorderState(boolean state){
        CommandModel model = new CommandModel(PhoneServiceManager.class.getSimpleName());
        model.setStartRecorder(state);
        EventBus.getDefault().post(model);
    }

    /**
     * 释放
     */
    public void relese() {
        if (mNettyClientManager != null) {
            mNettyClientManager.release();
        }

        if (mIntercomManager != null) {
            mIntercomManager.release();
        }
        EventBus.getDefault().unregister(this);
    }


}
