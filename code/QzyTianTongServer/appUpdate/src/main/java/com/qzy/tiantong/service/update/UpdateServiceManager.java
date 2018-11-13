package com.qzy.tiantong.service.update;

import android.content.Context;

import com.qzy.ftpserver.FtpServerManager;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.lib.utils.MD5Utils;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tiantong.service.netty.cmd.CmdHandlerUpdate;
import com.qzy.tiantong.service.netty.cmd.TianTongHandlerUpdate;
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

    private IniFile mIniFile;

    //创建ftp上传文件
    private FtpServerManager mFtpServerManager;


    private IUpdateLocalTool mLocalUpdateSocketManager;

    //WiFi管理
    private TiantongWifiManager mTiantongWifiManager;

    public UpdateServiceManager(Context context) {
        mContext = context;
        mTiantongWifiManager = new TiantongWifiManager(context);

        mIniFile = new IniFile();

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
                if (state) {
                    checkUpdateIniFile();
                }

            }

            @Override
            public void onBackupSuccess() {
                if (UpdateFileManager.checkUpdateFileMD5("/mnt/sdcard/update/backup")) {  //升级成功
                    UpdateLocalConfigBean localConfigBean = mIniFile.getUpdateLocalConfigBean(true);
                    localConfigBean.setBackup("1");
                    mIniFile.setUpdateLocalConfigBean(localConfigBean);
                    mLocalUpdateSocketManager.startLocalUpdte();
                }
            }

            @Override
            public void onBackupFailed() {
                 mLocalUpdateSocketManager.startLocalBackup();
            }

            @Override
            public void onUpdateSuccess() {
                if (UpdateFileManager.checkUpdateFileMD5("/mnt/sdcard/update")) {  //升级成功

                    //升级成功将新的版本号和md5写到原始配置文件
                    UpdateConfigBean updateConfigBean = mIniFile.getUpdateConfigBeanNew(true);
                    mIniFile.setUpdateConfigBean(updateConfigBean);

                    //设置升级成功标志位
                    UpdateLocalConfigBean localConfigBean = mIniFile.getUpdateLocalConfigBean(true);
                    localConfigBean.setUpdate("1");
                    localConfigBean.setUpdateStart(false);
                    mIniFile.setUpdateLocalConfigBean(localConfigBean);

                    //发送手机端升级成功
                    sendUpdateSuccess();

                    //重启
                    mLocalUpdateSocketManager.startLocalReboot();
                }
            }

            @Override
            public void onUpdateFailed() {
                mLocalUpdateSocketManager.startLocalUpdte();
            }

            @Override
            public void onRecoverSuccess() {
                UpdateLocalConfigBean localConfigBean = mIniFile.getUpdateLocalConfigBean(true);
                localConfigBean.setRecover("1");
                mIniFile.setUpdateLocalConfigBean(localConfigBean);
                mLocalUpdateSocketManager.startLocalReboot();
            }

            @Override
            public void onRecoverFailed() {
                mLocalUpdateSocketManager.startLocalRecover();
            }

            @Override
            public void onRebootSuccess() {
                UpdateLocalConfigBean localConfigBean = mIniFile.getUpdateLocalConfigBean(true);
                localConfigBean.setReboot("1");
                mIniFile.setUpdateLocalConfigBean(localConfigBean);
            }

            @Override
            public void onRebootFailed() {
                mLocalUpdateSocketManager.startLocalReboot();
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
            UpdateConfigBean updateConfigBean = mIniFile.getUpdateConfigBean(true);
            int nowServerVersion = Integer.parseInt(updateConfigBean.getServer_version().replace(".", ""));
            LogUtils.d(" nowServerVersion = " + nowServerVersion);
            if (serverAppVersion > nowServerVersion) {
                //更新配置信息
                updateConfigBean.setApp_version(appVer);
                updateConfigBean.setServer_version(serverVer);
                updateConfigBean.setZip_md(zipMd5);
                mIniFile.setUpdateConfigBeanNew(updateConfigBean);


                //如果需要升级就把所有的升级流程设置为0
                UpdateLocalConfigBean localConfigBean = new UpdateLocalConfigBean();
                localConfigBean.setUpdateStart(true);
                mIniFile.setUpdateLocalConfigBean(localConfigBean);

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
                String md = mIniFile.getUpdateConfigBeanNew(true).getZip_md();
                String fileMd = MD5Utils.getFileMD5(file);
                LogUtils.d("md5 = " + md + " fileMd5 = " + fileMd);
                if (fileMd.equals(md)) {
                    sendReceiveZipFileFinish(updateSendFile.getIp(), true);
                    //开始与底层进行通讯
                    ///
                    ///
                    ///
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
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendReceiveZipFileFinish(updateSendFile.getIp(), false);
        }

    }

    /**
     * 检查是否有升级异常发生
     */
    private void checkUpdateIniFile() {
        try {
            UpdateLocalConfigBean localConfigBean = mIniFile.getUpdateLocalConfigBean(true);
            if (!localConfigBean.isUpdateStart()) {
                LogUtils.e("no update error ....");
                return;
            }
            if (localConfigBean.getBackup().equals("0")) {
                mLocalUpdateSocketManager.startLocalBackup();
                return;
            }

            if (localConfigBean.getUpdate().equals("0")) {
                mLocalUpdateSocketManager.startLocalUpdte();
                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
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
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_update_phone_aapinfo, updateResponse));
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
            mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_update_send_zip, updateResponse));
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
