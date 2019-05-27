package com.qzy.tiantong.service.update;

import android.content.Context;

import com.qzy.ftpserver.FtpServerManager;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tiantong.service.netty.cmd.CmdHandlerUpdate;
import com.qzy.tiantong.service.netty.cmd.TianTongHandlerUpdate;
import com.qzy.tiantong.service.utils.TtUpdateUtils;
import com.qzy.tt.data.TtPhoneUpdateAppInfoProtos;
import com.qzy.tt.data.TtPhoneUpdateResponseProtos;
import com.qzy.tt.data.TtPhoneUpdateSendFileProtos;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import java.io.File;

import io.netty.buffer.ByteBufInputStream;

public class UpdateServiceManager implements IUpdateManager {

    private Context mContext;

    //通讯管理
    private NettyServerManager mNettyServerManager;

    private CmdHandlerUpdate mCmdHandlerUpdate;
    private TianTongHandlerUpdate mTianTongHandler;

    //创建ftp上传文件
    private FtpServerManager mFtpServerManager;


    private IUpdateLocalTool mLocalUpdateSocketManager;

    //WiFi管理
    private TiantongWifiManager mTiantongWifiManager;

    public UpdateServiceManager(Context context) {
        mContext = context;
        mTiantongWifiManager = new TiantongWifiManager(context);

        initNettyManager();


        if (mFtpServerManager == null) {
            mFtpServerManager = new FtpServerManager();
            mFtpServerManager.onStartServer();
        }

        initLocalManager();
    }


    /**
     * 连接底层服务
     */
    private void initLocalManager() {
        //升级协议
        mLocalUpdateSocketManager = new LocalUpdateSocketManager(new LocalUpdateSocketManager.IDataListener() {


            @Override
            public void onConnect(boolean state) {

            }

            @Override
            public void onBackupSuccess() {
                LogUtils.d("onBackupSuccess");
                mLocalUpdateSocketManager.startLocalUpdte();
            }

            @Override
            public void onBackupFailed() {
                LogUtils.d("onBackupFailed");
                sendNeedUpdate("", false);
            }

            @Override
            public void onUpdateSuccess() {
                LogUtils.d("onUpdateSuccess");
//                if (UpdateFileManager.checkUpdateFileMD5("/mnt/sdcard/update")) {  //升级成功

                    //发送手机端升级成功
                    sendUpdateSuccess();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(3000);
                                //重启
                                mLocalUpdateSocketManager.startLocalReboot();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
//                }
            }

            @Override
            public void onUpdateFailed() {
                LogUtils.d("onUpdateFailed");
//                sendNeedUpdate("", false);
            }

            @Override
            public void onRecoverSuccess() {
                LogUtils.d("onRecoverSuccess");
                mLocalUpdateSocketManager.startLocalReboot();
            }

            @Override
            public void onRecoverFailed() {
                LogUtils.d("onRecoverFailed");
                sendNeedUpdate("", false);
            }

            @Override
            public void onRebootSuccess() {
                LogUtils.d("onRebootSuccess");
            }

            @Override
            public void onRebootFailed() {
                LogUtils.d("onRebootFailed");
                sendNeedUpdate("", false);
            }

        });
    }


    /**
     * 初始化netty
     */
    private void initNettyManager() {
        mTianTongHandler = new TianTongHandlerUpdate(this);
        mCmdHandlerUpdate = new CmdHandlerUpdate(mTianTongHandler);
        mNettyServerManager = new NettyServerManager(new NettyServerManager.INettyServerListener() {
            @Override
            public void onReceiveData(ByteBufInputStream inputStream) {
                mCmdHandlerUpdate.handlerCmd(inputStream);
            }

            @Override
            public void onConnected(String ip) {

            }

            @Override
            public void onDisconnected(String ip) {

            }
        });
        mNettyServerManager.startNettyServer(9998);
    }

    /**
     * 释放netty
     */
    private void freeNettyManager() {
        if (mNettyServerManager == null) {
            LogUtils.e("mNettyServerManager is null ");
            return;
        }
        mNettyServerManager.release();
    }


    @Override
    public void checkUpdate(TtPhoneUpdateAppInfoProtos.UpdateAppInfo updateAppInfo) {
        try {
            String appVer = updateAppInfo.getPhoneAppVersion();
            String serverVer = updateAppInfo.getServerAppVersion();
            int phoneAppVersion = Integer.parseInt(appVer.replace(".", ""));
            int serverAppVersion = Integer.parseInt(serverVer.replace(".", ""));
            String zipMd5 = updateAppInfo.getTiantongUpdateMd();
            LogUtils.d(" phoneAppVersion = " + phoneAppVersion + " serverAppVersion = " + serverAppVersion + " zipMd5 = " + zipMd5);

            int nowServerVersion = TtUpdateUtils.getAppVersionCode(mContext);

            LogUtils.d(" nowServerVersion = " + nowServerVersion);

            if (serverAppVersion > nowServerVersion) {

                sendNeedUpdate(updateAppInfo.getIp(), true);

            } else {

                sendNeedUpdate(updateAppInfo.getIp(), false);
            }

            //处理动作

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void receiverZipFile(TtPhoneUpdateSendFileProtos.UpdateSendFile updateSendFile) {

        try {

            String fileName = updateSendFile.getFileData().getFilename();
            File file = new File("/mnt/sdcard/tiantong_update/tiantong_update/" + fileName);

            boolean isFinish = updateSendFile.getIsSendFileFinish();

            LogUtils.d("isFinish = " + isFinish + " fileName = " + fileName);

            if (isFinish) {

                sendReceiveZipFileFinish(updateSendFile.getIp(), true);

                if (!UpdateFileManager.unzipUpdteZip(file.getAbsolutePath())) {
                    return;
                }
                if (!UpdateFileManager.copyShFile()) {
                    return;
                }
                //开始底层升级流程
                mLocalUpdateSocketManager.startLocalBackup();

            } else {
                sendReceiveZipFileFinish(updateSendFile.getIp(), false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendReceiveZipFileFinish(updateSendFile.getIp(), false);
        }

    }


    /**
     * 发送升级成功
     */
    private void sendUpdateSuccess() {
        if (mNettyServerManager != null) {
            TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = TtPhoneUpdateResponseProtos.UpdateResponse.newBuilder()
                    .setIsUpdateFinish(true)
                    .build();
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_update_send_zip, updateResponse));
        }

    }

    /**
     * 发送是否需要升级
     *
     * @param ip
     * @param flag
     */
    private void sendNeedUpdate(String ip, boolean flag) {
        if (mNettyServerManager != null) {
            TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = TtPhoneUpdateResponseProtos.UpdateResponse.newBuilder()
                    .setIp(ip)
                    .setIsUpdate(flag)
                    .build();
            mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_update_phone_aapinfo, updateResponse));
        }

    }

    /**
     * 发送接受文件完成的消息给手机
     */
    private void sendReceiveZipFileFinish(String ip, boolean flag) {
        if (mNettyServerManager != null) {
            TtPhoneUpdateResponseProtos.UpdateResponse updateResponse = TtPhoneUpdateResponseProtos.UpdateResponse.newBuilder()
                    .setIp(ip)
                    .setIsSendFileFinish(flag)
                    .build();
            mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_update_send_failed, updateResponse));
        }

    }


    /**
     * 释放
     */
    public void free() {

        if (mTiantongWifiManager != null) {
            mTiantongWifiManager.free();
        }

        if (mLocalUpdateSocketManager != null) {
            mLocalUpdateSocketManager.free();
        }

        freeNettyManager();

        if (mFtpServerManager != null) {
            mFtpServerManager.stopFtpServer();
        }
    }


}
