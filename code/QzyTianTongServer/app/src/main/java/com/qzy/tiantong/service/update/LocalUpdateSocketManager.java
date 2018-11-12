package com.qzy.tiantong.service.update;

import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.lib.utils.LogUtils;

public class LocalUpdateSocketManager implements IUpdateLocalTool {

    private LocalSocketClient socketClient;

    private IDataListener listener;

    public LocalUpdateSocketManager(IDataListener listener) {
        this.listener = listener;
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
                if (listener != null) {
                    listener.onConnect(true);
                }
            }

            @Override
            public void disconneted() {
                LogUtils.d("update disconneted....");
                if (listener != null) {
                    listener.onConnect(false);
                }
            }

            @Override
            public void readDataCallback(byte[] data) {
                String dataStr = ByteUtils.byteArrToHexString(data).trim();
                LogUtils.d("read update data = " + dataStr);

                if (listener == null) {
                    LogUtils.e("listener is null ... ");
                    return;
                }
                handlerLocalCommand(data);
            }
        });
        socketClient.startSocketConnect();
    }

    /**
     * 处理协议命令结果
     *
     * @param data
     */
    private void handlerLocalCommand(byte[] data) {
        try {
            if (data != null && data.length > 3) {
                byte command = data[2];
                byte dataValue = data[3];
                switch (command) {
                    case UpdateCommandTool.command_back_up:
                        if (dataValue == (byte) 0x00) {
                            listener.onBackupSuccess();
                        } else {
                            listener.onBackupFailed();
                        }
                        break;
                    case UpdateCommandTool.command_update:
                        if (dataValue == (byte) 0x00) {
                            listener.onUpdateSuccess();
                        } else {
                            listener.onUpdateFailed();
                        }
                        break;
                    case UpdateCommandTool.command_recover:
                        if (dataValue == (byte) 0x00) {
                            listener.onRecoverSuccess();
                        } else {
                            listener.onRecoverFailed();
                        }
                        break;
                    case UpdateCommandTool.command_reboot:
                        if (dataValue == (byte) 0x00) {
                            listener.onRebootSuccess();
                        } else {
                            listener.onRebootFailed();
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 发送命令
     *
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
    public void startLocalBackup() {
        senCommand(UpdateCommandTool.getBackupCommand());
    }

    @Override
    public void startLocalUpdte() {
        senCommand(UpdateCommandTool.getUpdateCommand());
    }

    @Override
    public void startLocalRecover() {
        senCommand(UpdateCommandTool.getRecoverCommand());
    }

    @Override
    public void startLocalReboot() {
        senCommand(UpdateCommandTool.getRebootCommand());
    }

    /**
     * 释放
     */
    @Override
    public void free() {
        socketClient.closeSocketConnect();
    }

    public interface IDataListener {
        void onConnect(boolean state);

        void onBackupSuccess();

        void onBackupFailed();

        void onUpdateSuccess();

        void onUpdateFailed();

        void onRecoverSuccess();

        void onRecoverFailed();

        void onRebootSuccess();

        void onRebootFailed();
    }


}
