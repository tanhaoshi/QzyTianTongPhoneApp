package com.qzy.tiantong.service.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.intercom.IntercomManager;
import com.qzy.intercom.util.ByteUtils;
import com.qzy.intercom.util.Constants;
import com.qzy.netty.NettyServerManager;
import com.qzy.tiantong.service.cmd.CmdHandler;
import com.qzy.tiantong.service.cmd.TianTongHandler;
import com.qzy.tiantong.service.phone.BroadcastManager;
import com.qzy.tiantong.service.phone.QzyPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.utils.LogUtils;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class TianTongServiceManager implements ITianTongServer {
    private Context mContext;

    /**
     * netty连接管理
     **/
    private NettyServerManager mNettyServerManager;
    /**
     * 底层pcm连接管理
     **/
    private IntercomManager mIntercomManager;
    /**
     * 广播管理
     **/
    private BroadcastManager mBroadcastManager;
    /**
     * 电话和wifi连接管理
     **/
    private QzyPhoneManager mQzyPhoneManager;

    private TianTongHandler mTianTongHandler;

    private CmdHandler mCmdHandler;

    public TianTongServiceManager(Context context) {
        mContext = context;

        mQzyPhoneManager = new QzyPhoneManager(context, this);
        mTianTongHandler = new TianTongHandler(this);
        mCmdHandler = new CmdHandler(mTianTongHandler);

        initNettyServer();

        initBroadcast();
    }


    /**
     * 初始化netty服务
     */
    private void initNettyServer() {

        mNettyServerManager = new NettyServerManager(new NettyServerManager.INettyServerListener() {
            @Override
            public void onReceiveData(ByteBufInputStream inputStream) {
                mCmdHandler.handlerCmd(inputStream);
            }

            @Override
            public void onConnected(String ip) {
                Constants.UNICAST_BROADCAST_IP = ip;
                mTianTongHandler.sendEmptyMessage(TianTongHandler.msg_init_localpcm);
            }

            @Override
            public void onDisconnected(String ip) {

            }
        });
        mNettyServerManager.startNettyServer();
    }


    /**
     * 初始化广播接收管理类
     */
    private void initBroadcast() {
        mBroadcastManager = new BroadcastManager(mContext, this);
        mBroadcastManager.registerReceiver();
    }


    /**
     * 释放资源
     */
    public void release() {
        if (mNettyServerManager != null) {
            mNettyServerManager.release();
        }

        if (mIntercomManager != null) {
            mIntercomManager.release();
        }

        if (mBroadcastManager != null) {
            mBroadcastManager.release();
        }

    }

    @Override
    public QzyPhoneManager getQzyPhoneManager() {
        return mQzyPhoneManager;
    }

    @Override
    public void initLocalPcmDevice() {
        mIntercomManager = new IntercomManager();
    }

    @Override
    public void startRecorder() {
        if (mIntercomManager != null) {
            mIntercomManager.startRecord();
        }
    }

    @Override
    public void startPlayer() {
        if (mIntercomManager != null) {
            mIntercomManager.startPlayer();
        }

    }

    @Override
    public void closeRecorderAndPlayer() {
        if (mIntercomManager != null) {
            mIntercomManager.stopPlayer();
            mIntercomManager.stopRecord();
        }
    }

    @Override
    public void onPhoneStateChange(TtPhoneState state) {
        LogUtils.e("onPhoneStateChange " + state.ordinal());
        CallPhoneStateProtos.CallPhoneState.PhoneState phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
        if (state == TtPhoneState.NOCALL) {
           // phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
        } else if (state == TtPhoneState.RING) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.RING;
            mTianTongHandler.sendEmptyMessageDelayed(222,1000 * 50);
        } else if (state == TtPhoneState.CALL) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.CALL;
        } else if (state == TtPhoneState.HUANGUP) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP;
        }
        if (mNettyServerManager != null) {
            CallPhoneStateProtos.CallPhoneState callPhoneState = CallPhoneStateProtos.CallPhoneState.newBuilder()
                    .setPhoneState(phoneState)
                    .build();
            PhoneCmd cmd = new PhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state);
            cmd.setMessage(callPhoneState);
            mNettyServerManager.sendData(cmd);
        }
    }


}
