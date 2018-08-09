package com.qzy.tiantong.service.service;

import android.content.Context;

import com.qzy.intercom.IntercomManager;
import com.qzy.intercom.util.Constants;
import com.qzy.netty.NettyServerManager;
import com.qzy.tiantong.service.netty.PhoneNettyManager;
import com.qzy.tiantong.service.netty.cmd.CmdHandler;
import com.qzy.tiantong.service.netty.cmd.TianTongHandler;
import com.qzy.tiantong.service.phone.BroadcastManager;
import com.qzy.tiantong.service.phone.QzyPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;
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

    //管理server与phone端通讯命令
    private PhoneNettyManager mPhoneNettyManager;

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
                initPhoneNettyManager();
                Constants.UNICAST_BROADCAST_IP = ip;
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
     * 初始化 Phone客户端管理工具
     */
    private void initPhoneNettyManager() {
        mPhoneNettyManager = new PhoneNettyManager(mNettyServerManager);
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
    public void onPhoneStateChange(TtPhoneState state) {

        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.updateTtCallPhoneState(state);
        }
    }

    @Override
    public void onPhoneSignalStrengthChange(int value) {
        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.sendTtCallPhoneSignalToClient(value);
        }
    }

    @Override
    public void initTtPcmDevice() {
        LogUtils.e("initTtPcmDevice");
        if (mIntercomManager == null) {
           // mIntercomManager = new IntercomManager();
        }
    }

    @Override
    public void freeTtPcmDevice() {
        if (mIntercomManager != null) {
           // mIntercomManager.release();
            mIntercomManager = null;
        }
    }


}
