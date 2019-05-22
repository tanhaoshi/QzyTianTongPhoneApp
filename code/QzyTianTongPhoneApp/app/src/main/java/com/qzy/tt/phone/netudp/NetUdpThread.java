package com.qzy.tt.phone.netudp;


import com.qzy.utils.LogUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by yj.zhang on 2018/8/9.
 */

public class NetUdpThread extends Thread {

     private final int port;

     private IUdpListener mListener;

     private boolean isReconnected = false;

    public NetUdpThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            receive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receive() throws Exception {
        byte[] buffer = new byte[1024];
        @SuppressWarnings("resource")
        DatagramSocket ds = new DatagramSocket(port);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (true) {
            ds.receive(packet);
            String s = new String(packet.getData(), 0, packet.getLength());
            ///192.168.43.1:45860    →    all client connect me
            LogUtils.e(packet.getAddress() + ":" + packet.getPort() + "    →    " + s);
            if(mListener != null){
                mListener.onMsg(s);
            }
           // && !isReconnected
            if("all client connect me".equals(s) && mListener != null ){
                //isReconnected = true;
                mListener.onConnectStateMsg();
            }if("all client connect sleep".equals(s) && mListener != null ){
                //isReconnected = true;
                mListener.onConnectSleep();
            }
        }
    }

    public void send(String msg, int port) throws Exception {
        DatagramSocket ds = new DatagramSocket();
        DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length,InetAddress.getByName("255.255.255.255"), port);
        ds.send(dp);
        ds.close();
    }

    public IUdpListener getmListener() {
        return mListener;
    }

    public void setmListener(IUdpListener mListener) {
        this.mListener = mListener;
    }

    public boolean isReconnected() {
        return isReconnected;
    }

    public void setReconnected(boolean reconnected) {
        isReconnected = reconnected;
    }

    public interface IUdpListener{
        void onConnectStateMsg();
        void onConnectSleep();
        void onMsg(String msg);
    }


}
