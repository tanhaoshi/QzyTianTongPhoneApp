package com.qzy.tiantong.service.netudp;



import com.qzy.tiantong.lib.utils.LogUtils;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by yj.zhang on 2018/8/9.
 */

public class NetUdpThread extends Thread {

     private final int port;

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
        byte[] buffer = new byte[65507];
        @SuppressWarnings("resource")
        DatagramSocket ds = new DatagramSocket(port);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (true) {
            ds.receive(packet);
            String s = new String(packet.getData(), 0, packet.getLength());
            System.out.println(packet.getAddress() + ":" + packet.getPort() + "    →    " + s);
        }
    }

    public void send(String msg, int port) throws Exception {
        DatagramSocket ds = new DatagramSocket();
        DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length,InetAddress.getByName("192.168.43.255"), port);
        ds.send(dp);
        ds.close();
        LogUtils.d("all client connect me");
    }




}
