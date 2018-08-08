package com.qzy.tt.phone.service;

import android.content.Context;
import android.widget.Toast;

import com.qzy.PhoneManager;
import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.MessageEvent;
import com.qzy.eventbus.NettyStateModel;
import com.qzy.intercom.IntercomManager;
import com.qzy.intercom.input.Encoder;
import com.qzy.intercom.util.IPUtil;
import com.qzy.netty.NettyClientManager;
import com.qzy.tt.phone.cmd.CmdHandler;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.eventbus.CommandModel;
import com.qzy.voice.VoiceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class PhoneServiceManager implements NettyClientManager.INettyListener {

    private Context mContext;

    private NettyClientManager mNettyClientManager;

    //private VoiceManager mVoiceManager;

    private IntercomManager mIntercomManager;

    private PhoneManager mPhoneManager;

    private CmdHandler mCmdHandler;

    public PhoneServiceManager(Context context) {
        mContext = context;

        mNettyClientManager = new NettyClientManager(this);
        mNettyClientManager.startConnect();
        mPhoneManager = new PhoneManager(context,mNettyClientManager);
        //mVoiceManager = new VoiceManager();
        mCmdHandler = new CmdHandler();
        mIntercomManager = new IntercomManager();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onReceiveData(ByteBufInputStream inputStream) {
        mCmdHandler.handlerCmd(inputStream);
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
        }else if( event instanceof PhoneCmd){
            if(mPhoneManager != null){
                mPhoneManager.sendPhoneCmd((PhoneCmd) event);
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
