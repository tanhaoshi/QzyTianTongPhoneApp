package com.qzy.tiantong.lib.localsocket;

import android.content.Context;
import android.os.RemoteException;

import com.qzy.tiantong.lib.localsocket.pcm.PcmProtocolUtils;
import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.lib.utils.LogUtils;


/**
 * Created by yj.zhang on 2018/7/9/009.
 */

public class LocalPcmSocketManager {

    private Context mContext;

    private LocalSocketClient mLocalSocketClient;

    private ISocketCallback mISocketConnectCallback;

    public LocalPcmSocketManager(Context context) {
        mContext = context;

        initLocalSocket();
        connectLocalSocket();
    }


    /**
     * 初始化 socket
     */
    public void initLocalSocket() {
        //mLocalSocketClient = new LocalSocketClient("pcm_service", readSocketCallback);
        mLocalSocketClient = new LocalSocketClient("tt_socket", readSocketCallback);
    }

    /**
     * 连接socket
     */
    public void connectLocalSocket() {
        mLocalSocketClient.startSocketConnect();
    }

    /**
     * 连接回调
     * socket数据回调
     */
    private LocalSocketClient.IReadDataCallback readSocketCallback = new LocalSocketClient.IReadDataCallback() {
        @Override
        public void connected() {
            LogUtils.e("local socket connected ...");
            if (mISocketConnectCallback != null) {
                mISocketConnectCallback.connected();
            }


        }

        @Override
        public void disconneted() {
            LogUtils.e("local socket disconneted ...");
            if (mISocketConnectCallback != null) {
                mISocketConnectCallback.disconneted();

            }

            connectLocalSocket();
        }

        @Override
        public void readDataCallback(final byte[] data) {
            //使用线程池处理，防止阻塞
            // LogUtils.e("receive pcm form hw com.qzy.tt.data = " + ByteUtils.byteArrToHexString(com.qzy.tt.data));
            if (mISocketConnectCallback != null) {
                mISocketConnectCallback.onData(data);
            }

        }
    };


    /**
     * 发送数据接口
     *
     * @param command
     * @throws RemoteException
     */
    public void sendCommand(final byte[] command) throws RemoteException {

        if (mLocalSocketClient != null) {
            if (command != null && command.length > 1) {
                mLocalSocketClient.sendCommand(mContext.getPackageName(), command);
            } else {
                LogUtils.e("this protocal not a protocols");
            }
        } else {
            LogUtils.e("mLocalSocketClient is null");
        }

        /*fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {

            }
        });*/
    }

    public void setPhoneCalling() {
        try {
            sendCommand(PcmProtocolUtils.sendPhoneCalling());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setPhoneHangup() {
        try {

            sendCommand(PcmProtocolUtils.sendPhoneHangup());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setPhoneIpAndPort(String ip, int port) {
        try {
            byte[] ips = ipv4Address2BinaryArray(ip);
            byte[] command = new byte[6];
            command[0] = ips[0];
            command[1] = ips[1];
            command[2] = ips[2];
            command[3] = ips[3];
            byte[] d = ByteUtils.intToByteArray(port);
            command[4] = d[2];
            command[5] = d[3];
            sendCommand(PcmProtocolUtils.sendIpAndPort(command));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static byte[] ipv4Address2BinaryArray(String ipAdd) {
        byte[] binIP = new byte[4];
        String[] strs = ipAdd.split("\\.");
        for (int i = 0; i < strs.length; i++) {
            binIP[i] = (byte) Integer.parseInt(strs[i]);
        }
        return binIP;
    }


    /**
     * 设置回调
     *
     * @param iSocketConnectCallback
     */

    public void setSocketCallback(ISocketCallback iSocketConnectCallback) {
        this.mISocketConnectCallback = iSocketConnectCallback;
    }

    public interface ISocketCallback {
        void connected();

        void disconneted();

        void onData(byte[] data);
    }


    /**
     * 释放资源
     */
    public void release() {
        if (mLocalSocketClient != null) {
            mLocalSocketClient.closeSocketConnect();
        }
    }


}
