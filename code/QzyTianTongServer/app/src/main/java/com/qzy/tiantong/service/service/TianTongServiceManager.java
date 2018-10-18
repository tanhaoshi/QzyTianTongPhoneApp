package com.qzy.tiantong.service.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


import com.qzy.audiosocket.SocketIntercomManager;

import com.qzy.locallib.selfpcm.TtPcmIntercomManager;

import com.qzy.tiantong.lib.localsocket.LocalPcmSocketManager;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.intercom.IntercomManager;
import com.qzy.tiantong.service.intercom.util.Constants;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tiantong.service.netty.PhoneNettyManager;
import com.qzy.tiantong.service.netty.cmd.CmdHandler;
import com.qzy.tiantong.service.netty.cmd.TianTongHandler;
import com.qzy.tiantong.service.phone.BroadcastManager;
import com.qzy.tiantong.service.phone.CallLogManager;
import com.qzy.tiantong.service.phone.PhoneClientManager;
import com.qzy.tiantong.service.phone.QzyPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;
import com.qzy.tiantong.service.rtptest.Global;
import com.qzy.tiantong.service.rtptest.RtpManager;
import com.qzy.tiantong.service.ttpcm.aidl.ReadPcmManager;
import com.qzy.tiantong.service.ttpcm.aidl.WritePcmManager;
import com.qzy.tt.data.TtPhoneSmsProtos;


import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class TianTongServiceManager implements ITianTongServer {
    private Context mContext;

    // 1 :loca pcm  2: udb  3: rtp 4: tcp/ip
    private int useProtocalIndex = 0;

    private boolean isUseAidl = false;

    private boolean isUsePmcServer = false;

    /**
     * netty连接管理
     **/
    private NettyServerManager mNettyServerManager;
    /**
     * 底层pcm连接管理
     **/
    private IntercomManager mIntercomManager;

    private RtpManager mRtpManager;

    private SocketIntercomManager mSocketIntercomManager;

    private TtPcmIntercomManager mTtPcmIntercomManager;

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


    private boolean isUdpPcmLocal = true;
    private LocalPcmSocketManager mLocalPcmSocketManager;


    public TianTongServiceManager(Context context) {
        mContext = context;

        mQzyPhoneManager = new QzyPhoneManager(context, this);
        mTianTongHandler = new TianTongHandler(this);
        mCmdHandler = new CmdHandler(mTianTongHandler);

        initNettyServer();
        initPhoneNettyManager();

        initBroadcast();

        if (isUseAidl) {
            ReadPcmManager.init(context);
            WritePcmManager.init(context);
        }

        if (isUsePmcServer) {
            mContext.startService(new Intent(mContext, PcmServices.class));
        }

        if (isUdpPcmLocal) {
            mLocalPcmSocketManager = new LocalPcmSocketManager(context);
        }

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

                if (isUsePmcServer) {
                    Intent intent = new Intent(PcmServices.action_set_ip);
                    intent.putExtra(PcmServices.extra_ip, ip);
                    mContext.sendBroadcast(intent);
                } else {
                    Constants.UNICAST_BROADCAST_IP = ip;
                }
                Global.IP = ip;


                //发送通讯录
                CallLogManager.syncCallLogInfo(ip, mContext, mPhoneNettyManager);

                //发送短信数据库
                CallLogManager.syncSmsInfo(ip, mContext, mPhoneNettyManager);

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
        mPhoneNettyManager = new PhoneNettyManager(mContext, mNettyServerManager);
    }


    /**
     * 设置打电话的用户ip
     *
     * @param ip
     */
    @Override
    public synchronized boolean setCurrenCallingIp(String ip) {
        LogUtils.e("1111 setCurrenCallingIp = " + ip);
        String callingIp = PhoneClientManager.getInstance().isCallingIp();

        if (!TextUtils.isEmpty(callingIp)) {
            LogUtils.e("1111 callingIp = " + callingIp);
            if (mPhoneNettyManager != null) {
                mPhoneNettyManager.sendTtCallPhoneBackToClient(null, callingIp, true);
            }

            return false;
        }

        LogUtils.e("setCurrenCallingIp = " + ip);
        PhoneClientManager.getInstance().setCurrentCallingUser(ip);

        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.sendTtCallPhoneBackToClient(null, ip, true);
        }


        if (mLocalPcmSocketManager != null) {
            mLocalPcmSocketManager.setPhoneIpAndPort(ip, Constants.UNICAST_PORT);
        }

        return true;
    }

    @Override
    public void setEndCallingIp(String ip) {
        PhoneClientManager.getInstance().setEndCallUser(ip);
    }


    /**
     * 释放资源
     */
    public void release() {

        freeTtPcmDevice();

        if (mNettyServerManager != null) {
            mNettyServerManager.release();
        }

       /* if (mIntercomManager != null) {
            mIntercomManager.release();
        }*/

        if (mBroadcastManager != null) {
            mBroadcastManager.release();
        }

        if (mQzyPhoneManager != null) {
            mQzyPhoneManager.release();
        }

        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.free();
        }

        if (isUseAidl) {
            if (ReadPcmManager.getInstance() != null) {
                ReadPcmManager.getInstance().free();
            }

            if (WritePcmManager.getInstance() != null) {
                WritePcmManager.getInstance().free();
            }
        }


        if (isUsePmcServer) {
            mContext.sendBroadcast(new Intent(PcmServices.action_stop));
        }

        if (mLocalPcmSocketManager != null) {
            mLocalPcmSocketManager.release();
        }

    }

    @Override
    public QzyPhoneManager getQzyPhoneManager() {
        return mQzyPhoneManager;
    }

    @Override
    public PhoneNettyManager getPhoneNettyManager() {
        return mPhoneNettyManager;
    }


    @Override
    public void onPhoneStateChange(TtPhoneState state) {

        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.updateTtCallPhoneState(state, "");
        }
    }

    @Override
    public void onPhoneIncoming(TtPhoneState state, String phoneNumber) {
        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.updateTtCallPhoneState(state, phoneNumber);
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

        if (mLocalPcmSocketManager != null) {
            mLocalPcmSocketManager.setPhoneCalling();
            return;
        }

        if (useProtocalIndex == 1) {
            if (mTtPcmIntercomManager == null) {
                mTtPcmIntercomManager = new TtPcmIntercomManager();
            }
        } else if (useProtocalIndex == 2) {
            if (isUsePmcServer) {
                mContext.sendBroadcast(new Intent(PcmServices.action_start_pcm));
            } else {
                if (mIntercomManager == null) {
                    mIntercomManager = new IntercomManager();
                }
            }


        } else if (useProtocalIndex == 3) {
            if (mRtpManager == null) {
                mRtpManager = new RtpManager();
            }
        } else if (useProtocalIndex == 4) {
            if (mSocketIntercomManager == null) {
                mSocketIntercomManager = new SocketIntercomManager();
            }
        }
    }

    @Override
    public void freeTtPcmDevice() {
        LogUtils.e("freeTtPcmDevice");
        if (mLocalPcmSocketManager != null) {
            mLocalPcmSocketManager.setPhoneHangup();
            return;
        }

        if (useProtocalIndex == 1) {
            if (mTtPcmIntercomManager != null) {
                mTtPcmIntercomManager.release();
                mTtPcmIntercomManager = null;
            }
        } else if (useProtocalIndex == 2) {
            if (isUsePmcServer) {
                mContext.sendBroadcast(new Intent(PcmServices.action_stop_pcm));
            } else {
                if (mIntercomManager != null) {
                    mIntercomManager.release();
                    mIntercomManager = null;
                }
            }
        } else if (useProtocalIndex == 3) {
            if (mRtpManager != null) {
                mRtpManager.free();
                mRtpManager = null;
            }
        } else if (useProtocalIndex == 4) {
            if (mSocketIntercomManager == null) {
                mSocketIntercomManager.release();
                mSocketIntercomManager = null;
            }
        }

    }

    @Override
    public void sendSms(TtPhoneSmsProtos.TtPhoneSms ttPhoneSms) {
        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.senSms(ttPhoneSms);
        }
    }


}
