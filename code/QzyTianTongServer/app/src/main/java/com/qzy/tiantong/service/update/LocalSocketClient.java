package com.qzy.tiantong.service.update;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;

import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.lib.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/12/21/021.
 */

public class LocalSocketClient {
    /**
     * 底层服务名  例： htfsk
     */
    private static String SOCKET_NAME;
    private LocalSocket client;
    private LocalSocketAddress address;
    /**
     * 连接计数器
     */
    private int connetCount = 10;

    private ConnectThread mConnectThread;

    private ReadThread mReadThred;
    /**
     * 数据回调
     */
    private IReadDataCallback mIReadDataCallback;

    public LocalSocketClient(String socketName, IReadDataCallback readCallback) {
        SOCKET_NAME = socketName;
        mIReadDataCallback = readCallback;
        if (SOCKET_NAME != null && !SOCKET_NAME.equals("")) {
            client = new LocalSocket();
            address = new LocalSocketAddress(SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        }
    }

    /**
     * 启动socket连接
     */
    public void startSocketConnect() {
        try {
            //closeSocketConnect();
            mConnectThread = new ConnectThread();
            mConnectThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接线程
     */
    private class ConnectThread extends Thread {
        @Override
        public void run() {
            if (client != null) {
                while (!client.isConnected() && connetCount > 0) {
                    try {
                        Thread.sleep(1000);
                        LogUtils.i("Try to connect socket;connetCount:" + connetCount);
                        client.connect(address);

                        //连接成功启动 读取线程
                        mReadThred = new ReadThread();
                        mReadThred.start();

                        if (mIReadDataCallback != null) {
                            mIReadDataCallback.connected();
                        }

                    } catch (Exception e) {
                        connetCount--;
                        // LogUtils.i("Socket Connect fail");
                    }
                }

                if (connetCount < 0) {
                    if (mIReadDataCallback != null) {
                        mIReadDataCallback.disconneted();
                    }
                }
            }
        }

    }

    /**
     * 读取socket 数据
     */
    private class ReadThread extends Thread {

        @Override
        public void run() {

            try {
                byte[] data = new byte[1 * 1024];
                int length = 0;
                // 这里从本地服务读取信息
                InputStream in = client.getInputStream();
                while ((length = in.read(data)) != -1) {
                   if(mIReadDataCallback != null){
                       mIReadDataCallback.readDataCallback(Arrays.copyOf(data,length));
                   }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发送消息
     *
     * @param command 只做标识谁发送了这个命令
     * @return 返回Socket服务端的消息回执
     */
    public boolean sendCommand(String appPackage, byte[] command) {
        if (client == null || !client.isConnected()) {
            return false;
        }
        try {
            OutputStream out = client.getOutputStream();
            out.write(command);
            out.flush();
            LogUtils.d( "(" + appPackage + ") send command = " + ByteUtils.byteArrToHexString(command) );
        } catch (IOException e) {
            e.printStackTrace();
            if (mIReadDataCallback != null) {
                mIReadDataCallback.disconneted();
            }
            return false;
        }
        return true;
    }


    /**
     * 关闭Socket
     */
    public void closeSocketConnect() {
        try {
            if (client != null) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据回调接口
     */
    public interface IReadDataCallback {
        void connected();

        void disconneted();

        void readDataCallback(byte[] data);
    }




}
