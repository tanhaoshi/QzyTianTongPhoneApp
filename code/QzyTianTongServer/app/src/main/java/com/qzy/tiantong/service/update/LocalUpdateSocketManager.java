package com.qzy.tiantong.service.update;

import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.lib.utils.LogUtils;

public class LocalUpdateSocketManager implements IUpdateLocalTool {

    private LocalSocketClient socketClient;

    public LocalUpdateSocketManager() {
        initSocket();
    }

    /**
     * 初始化socket
     */
    private void initSocket() {
        socketClient = new LocalSocketClient("update", new LocalSocketClient.IReadDataCallback() {
            @Override
            public void connected() {
                LogUtils.d("update connected ....");
            }

            @Override
            public void disconneted() {
                LogUtils.d("update disconneted....");
            }

            @Override
            public void readDataCallback(byte[] data) {
                LogUtils.d("read update data = " + ByteUtils.byteArrToHexString(data));
            }
        });
        socketClient.startSocketConnect();
    }


    /**
     * 发送命令
     * @param command
     */
    private void senCommand(byte[] command) {
        if (socketClient == null) {
            LogUtils.e("socketClient is null ...");
            return;
        }

        socketClient.sendCommand("com.qzy.tt.update", command);
    }

    @Override
    public void startLocalUpdte() {
        senCommand(UpdateCommandTool.getStartCommand(1));
    }

    /**
     * 释放
     */
    @Override
    public void free() {
        socketClient.closeSocketConnect();
    }


}
