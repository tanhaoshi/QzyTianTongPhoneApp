package com.hwacreate.at;

import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.internal.telephony.Phone;

public class AtThread extends Thread {
	
	private String mCmd;
    private long mMaxTimeExcute;
    private String mAtResult;
    private boolean mIsSync;
    private Handler mAtHandler;
    private boolean mSuccess = false;
    private Handler mHandler;
    private Looper mLooper;
    private int key;

    private static final int SEND_AT_VIA_TUNNEL = 1;
    private static final String LOG_TAG = "AtSendThread";

    AtThread(String name, String cmd, boolean isSync) {
        super(name);
        mCmd = cmd;
        mAtResult = null;
        mMaxTimeExcute = 50 * 1000;
        mIsSync = false;
    }

    AtThread(String name, String cmd, boolean isSync, long max, Handler handler, int keyNum) {
        super(name);
        mCmd = cmd;
        mMaxTimeExcute = max;
        mAtResult = null;
        mIsSync = isSync;
        mHandler = handler;
        key = keyNum;
    }

    public void setAtResult(String result) {
        if (result == null) {
            Log.e(LOG_TAG, "[GSMPhone] setAtResult() result is null;");
        }
        mAtResult = result;
    }

    public void exit() {
        if (mLooper != null) {
            mLooper.quit();
            mLooper = null;
        }
    }

    public void run() {
        Looper.prepare();
        mLooper = Looper.myLooper();
        synchronized (AtThread.this) {
            mAtHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    AsyncResult ar = (AsyncResult) msg.obj;
                    Log.d(LOG_TAG, "SEND_AT_VIA_TUNNEL msg.what = "+ msg.what);
                    switch (msg.what) {
                        case SEND_AT_VIA_TUNNEL:
                            Log.d("AtSyncThread", "SEND_AT_VIA_TUNNEL");
                            synchronized (AtThread.this) {
                                if (ar.exception == null && ar.result != null) {
                                    mAtResult = ar.result.toString();
                                }
                                mSuccess = true;
                                AtThread.this.notifyAll();
                            }
                            break;
                    }
                }
            };
            AtThread.this.notifyAll();
        }
        Looper.loop();
    }


    synchronized String sendAt(Phone phone) {
        while (mAtHandler == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Message callback = Message.obtain(mAtHandler, SEND_AT_VIA_TUNNEL);
        phone.sendAtToModem(mCmd, callback);
        int count = 0;
        while (!mSuccess) {
            try {
                Log.d("AtSendThread", "wait for done");
                mMaxTimeExcute--;
                count++;
                wait(100);
                //if (mMaxTimeExcute == 0) {
                if(count == 8){
                    mAtResult = "Error AT TIME OUT";
                    return mAtResult;
                }
            } catch (InterruptedException e) {
                // Restore the interrupted status
                Thread.currentThread().interrupt();
            }
        }
        Log.d("AtSendThread", "successfull! result = " + mAtResult);
        Message msg = new Message();
        msg.what = key;
        Bundle bd = new Bundle();
        bd.putString("result", mAtResult);
        bd.putString("command_value", mCmd);
        msg.setData(bd);
        mHandler.sendMessage(msg);
        return mAtResult;
    }

}
