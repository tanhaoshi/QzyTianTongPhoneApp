package com.qzy.intercom.network;


import com.qzy.intercom.util.Constants;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by yanghao1 on 2017/5/15.
 */

public class Unicast {

    byte[] receiveMsg = new byte[512];
    // private DatagramPacket receivePacket;
    private DatagramSocket receiveSocket;

    //private DatagramPacket sendPacket;
    private DatagramSocket sendSocket;

    private static Unicast unicast;

    private Unicast() {
        try {
            // 初始化接收Socket
            //receivePacket = new DatagramPacket(receiveMsg, receiveMsg.length);
            receiveSocket = new DatagramSocket(Constants.UNICAST_PORT);
            // 初始化发送Socket
            //sendPacket  = new DatagramPacket(receiveMsg, receiveMsg.length);
            sendSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static Unicast getUnicast() {
        if (unicast == null) {
            unicast = new Unicast();
        }
        return unicast;
    }

    public DatagramSocket getReceiveSocket() {
        return receiveSocket;
    }

    public InetAddress getInetAddress() {
        try {
            return InetAddress.getByName(Constants.UNICAST_BROADCAST_IP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setReceiveSocket(DatagramSocket receiveSocket) {
        this.receiveSocket = receiveSocket;
    }

    public DatagramSocket getSendSocket() {
        return sendSocket;
    }

    public void setSendSocket(DatagramSocket sendSocket) {
        this.sendSocket = sendSocket;
    }

    public void free() {
        if (receiveSocket != null) {
            try {
                receiveSocket.close();
                receiveSocket = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (sendSocket != null) {
            try {
                sendSocket.close();
                sendSocket = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
