package com.qzy.audiosocket.net;

import com.qzy.intercom.util.Constants;
import com.qzy.utils.LogUtils;

import java.io.IOException;
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

    private Socket socket;

    private static OkioSocketManager okioSocketManager;

    private boolean isFree = false;

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
        try {
            socket = new Socket(Constants.UNICAST_BROADCAST_IP, Constants.UNICAST_PORT);

            mSink = Okio.buffer(Okio.sink(socket));

            mSource = Okio.buffer(Okio.source(socket));
        } catch (Exception e) {

            LogUtils.e(("connectService:" + e.getMessage()));

        }
        isFree = false;
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

            if (socket != null) {
                socket.close();
            }
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
