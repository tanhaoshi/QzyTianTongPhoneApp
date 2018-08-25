package com.qzy.tt.phone.service;

import android.content.Context;

import com.djw.rtptest.RtpManager;
import com.qzy.PhoneManager;
import com.qzy.audiosocket.SocketIntercomManager;
import com.qzy.data.PhoneAudioCmd;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.MessageEvent;
import com.qzy.eventbus.NettyStateModel;
import com.qzy.intercom.IntercomManager;
import com.qzy.netty.NettyClientManager;
import com.qzy.test.VoiceTtManagerTools;
import com.qzy.tt.phone.cmd.CmdHandler;
import com.qzy.tt.phone.eventbus.CommandModel;
import com.qzy.utils.AndroidVoiceManager;

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

    private RtpManager mRtpManager;

    private SocketIntercomManager mSocketIntercomManager;

    private VoiceTtManagerTools mVoiceTtManagerTools;

    private PhoneManager mPhoneManager;

    private CmdHandler mCmdHandler;

    // 1 :loca pcm  2: udb  3: rtp 4: tcp/ip
    private int useProtocalIndex = 2;

    public PhoneServiceManager(Context context) {
        mContext = context;

        mNettyClientManager = new NettyClientManager(this);
        mNettyClientManager.startConnect();
        mPhoneManager = new PhoneManager(context, mNettyClientManager);
        //mVoiceManager = new VoiceManager();
        mCmdHandler = new CmdHandler();
        initProtocal();
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化通讯协议
     */
    private void initProtocal() {
        if (useProtocalIndex == 1) {
            mVoiceTtManagerTools = new VoiceTtManagerTools();
        } else if (useProtocalIndex == 2) {
            mIntercomManager = new IntercomManager();

        } else if (useProtocalIndex == 3) {
            mRtpManager = new RtpManager();
        } else if (useProtocalIndex == 4) {
            mSocketIntercomManager = new SocketIntercomManager();
        }
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
            if (!commandModel.getMsgTag().equals(PhoneServiceManager.class.getSimpleName())) {
                if (commandModel.isStartRecorder()) {
                    startProtocal();
                } else {
                    stopProtocal();
                }
            }
        } else if (event instanceof PhoneCmd) {
            if (mPhoneManager != null) {
                mPhoneManager.sendPhoneCmd((PhoneCmd) event);
            }
        } else if (event instanceof PhoneAudioCmd) {
            if (mPhoneManager != null) {
                PhoneAudioCmd phoneAudioCmd = (PhoneAudioCmd) event;
                if(phoneAudioCmd.getProtoId() != PrototocalTools.IProtoServerIndex.phone_audio){
                    return;
                }
                mPhoneManager.sendPhoneAudioCmd(phoneAudioCmd);
            }
        }
    }

    /**
     * 开始录音
     */
    public void startProtocal() {


        if (useProtocalIndex == 1) {
            if (mVoiceTtManagerTools != null) {
                mVoiceTtManagerTools.init();
            }
        } else if (useProtocalIndex == 2) {
            if (mIntercomManager != null) {
                mIntercomManager.init();
            }
        } else if (useProtocalIndex == 3) {
            if (mRtpManager != null) {
                mRtpManager.init();
            }
        } else if (useProtocalIndex == 4) {
            if (mSocketIntercomManager != null) {
                mSocketIntercomManager.init();
            }
        }
        AndroidVoiceManager.setVoiceCall(mContext);
        setRecorderState(true);
    }


    /**
     * 结束录音
     */
    public void stopProtocal() {

        if (useProtocalIndex == 1) {
            if (mVoiceTtManagerTools != null) {
                mVoiceTtManagerTools.free();
            }
        } else if (useProtocalIndex == 2) {
            if (mIntercomManager != null) {
                mIntercomManager.release();
            }
        } else if (useProtocalIndex == 3) {
            if (mRtpManager != null) {
                mRtpManager.free();
            }
        } else if (useProtocalIndex == 4) {
            if (mSocketIntercomManager != null) {
                mSocketIntercomManager.release();
            }
        }
        AndroidVoiceManager.setVoiceMusic(mContext);
        setRecorderState(false);
    }


    /**
     * 开启录音方法
     *
     * @param state
     */
    private void setRecorderState(boolean state) {
        CommandModel model = new CommandModel(PhoneServiceManager.class.getSimpleName());
        model.setStartRecorder(state);
        EventBus.getDefault().post(model);
    }

    private void releaseProtocal() {
        if (useProtocalIndex == 2) {
            if (mIntercomManager != null) {
                mIntercomManager.release();
            }
        } else if (useProtocalIndex == 3) {
            if (mRtpManager != null) {
                mRtpManager.free();
            }
        }
    }


    /**
     * 释放
     */
    public void relese() {
        if (mNettyClientManager != null) {
            mNettyClientManager.release();
        }
        releaseProtocal();

        EventBus.getDefault().unregister(this);
    }


}
