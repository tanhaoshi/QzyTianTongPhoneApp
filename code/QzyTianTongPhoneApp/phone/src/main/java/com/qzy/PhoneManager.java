package com.qzy;

import android.content.Context;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.netty.NettyClientManager;
import com.qzy.tt.data.CallPhoneProtos;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class PhoneManager {
    private Context mContext;

    private NettyClientManager mNettyClientManager;

    public PhoneManager(Context context, NettyClientManager manager) {
        mContext = context;
        mNettyClientManager = manager;
    }

    /**
     * 打电话
     *
     * @param cmd
     */
    public void sendPhoneCmd(PhoneCmd cmd) {
        if (mNettyClientManager != null) {
            mNettyClientManager.sendData(cmd);
        }
    }


}
