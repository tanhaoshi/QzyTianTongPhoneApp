package com.qzy.tiantong.service.audiosocket.net;


import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.intercom.util.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by yj.zhang on 2018/8/17.
 */

public class OkioSocketManager {

    private BufferedSink mSink;

    private BufferedSource mSource;

    private ServerSocket server;

    private static OkioSocketManager okioSocketManager;

    private boolean isFree = false;

    private Thread thread;

    public static OkioSocketManager getInstance() {
        if (okioSocketManager == null) {
            okioSocketManager = new OkioSocketManager();
        }
        return okioSocketManager;
    }

    private OkioSocketManager() {
        runSoketConnect();
    }

    private void runSoketConnect() {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    isFree = false;
                    server = new ServerSocket(Constants.UNICAST_PORT);
                    Socket client = null;
                    while (!isFree) {
                        client = server.accept();
                        LogUtils.e("new client " + client.getInetAddress().getHostAddress().toString());
                        mSink = Okio.buffer(Okio.sink(client));
                        mSource = Okio.buffer(Okio.source(client));
                    }
                } catch (Exception e) {

                    LogUtils.e(("connectService:" + e.getMessage()));

                }
            }
        });
        thread.start();


    }

    public void free() {
        isFree = true;
        try {
            if (mSink != null) {
                mSink.close();
            }

            if (mSource != null) {
                mSource.close();
            }

            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
            thread = null;
            okioSocketManager = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFree() {
        return isFree;
    }


    public BufferedSink getmSink() {
        return mSink;
    }


    public BufferedSource getmSource() {
        return mSource;
    }

}
