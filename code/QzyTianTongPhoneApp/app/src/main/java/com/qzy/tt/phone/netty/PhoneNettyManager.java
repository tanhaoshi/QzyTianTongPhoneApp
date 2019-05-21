package com.qzy.tt.phone.netty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Observable;
import android.os.Handler;
import android.os.Looper;

import com.google.protobuf.ByteString;
import com.qzy.androidftp.FtpClienManager;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.netty.NettyClient;
import com.qzy.netty.NettyClientManager;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtDeleCallLogProtos;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.data.TtPhoneConnectBeatProtos;
import com.qzy.tt.data.TtPhoneGetServerVersionProtos;
import com.qzy.tt.data.TtPhoneMobileDataProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtPhoneRecoverSystemProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtPhoneSosMessageProtos;
import com.qzy.tt.data.TtPhoneSosStateProtos;
import com.qzy.tt.data.TtPhoneUpdateAppInfoProtos;
import com.qzy.tt.data.TtPhoneUpdateSendFileProtos;
import com.qzy.tt.data.TtPhoneWifiProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.data.TtTimeProtos;
import com.qzy.tt.phone.cmd.CmdHandler;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.SmsBean;
import com.qzy.tt.phone.netudp.NetUdpThread;
import com.qzy.utils.LogUtils;
import com.socks.library.KLog;
import com.tt.qzy.view.bean.AppInfoModel;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.EnableDataModel;
import com.tt.qzy.view.bean.ProtobufMessageModel;
import com.tt.qzy.view.bean.SMAgrementModel;
import com.tt.qzy.view.bean.SosSendMessageModel;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.bean.WifiSettingModel;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.utils.ToastUtil;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class PhoneNettyManager {
    private Context mContext;

    private NettyClientManager mNettyClientManager;

    private CmdHandler mCmdHandler;

    private String ip;
    private int port;

    private NetUdpThread mNetUdpThread;

    private Handler mhandler = new Handler(Looper.getMainLooper());

    //用户点击连接
    private boolean isUserHandlerConnect = false;

    //由猫唤醒导致重连标志
    private boolean isUdpHandlerConnect = false;

    //netty通道异常 导致重连标志位
    private boolean isNettyException = false;

    //休眠前断开连接
    private boolean isSleepDisconnect = false;

    public PhoneNettyManager(Context context) {
        mContext = context;
        mNettyClientManager = new NettyClientManager(nettyListener);
        mCmdHandler = new CmdHandler(context);
        initUdbConnect();
        KLog.i("TtPhoneService boolean flag value = " + (Boolean) SPUtils.getShare(mContext, Constans.SERVER_FLAG, false));
        if ((Boolean) SPUtils.getShare(mContext, Constans.SERVER_FLAG, false)) {
            connect(Constans.PORT, Constans.IP);
            SPUtils.putShare(mContext, Constans.SERVER_FLAG, false);
        }
    }

    /**
     * 做udp重连消息
     */
    private void initUdbConnect() {
        mNetUdpThread = new NetUdpThread(8991);
        mNetUdpThread.start();
        mNetUdpThread.setmListener(udpListener);
        mCmdHandler.setOnCheckListener(new CmdHandler.CheckBeatListener() {
            @Override
            public void checkBeatState(boolean isBeat) {
                KLog.i("look check state = " + isBeat);
                isBeatState = isBeat;
            }
        });
    }

    public void checkConnectBeat() {
        if (NettyClient.getInstance().getConnectHanlerCtx() != null) {
            KLog.i("send beat");
            blockTaskCheckState();
        } else {
            //为空 释放重连
            KLog.i("disconnect then connect");
            mNettyClientManager.stop();
            mNettyClientManager = null;
            mNettyClientManager = new NettyClientManager(nettyListener);
            mNettyClientManager.startReconnected(Constans.PORT, Constans.IP);
        }
    }

    private void blockTaskCheckState() {
        io.reactivex.Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> observableEmitter) {
                sendBeat();
                try {
                    Thread.sleep(2000);
                    observableEmitter.onNext(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (isBeatState) {
                            isBeatState = false;
                            isUdpHandlerConnect = false;
                            KLog.i("return");
                            return;
                        } else {
                            isBeatState = false;
                            KLog.i("reconnect");
                            mNettyClientManager.stop();
                            mNettyClientManager = null;
                            mNettyClientManager = new NettyClientManager(nettyListener);
                            mNettyClientManager.startReconnected(Constans.PORT, Constans.IP);
                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public volatile boolean isBeatState = false;

    public void sendBeat() {
        TtPhoneConnectBeatProtos.TtPhoneConnectBeat ttPhoneConnectBeat =
                TtPhoneConnectBeatProtos.TtPhoneConnectBeat
                        .newBuilder()
                        .setIsConnect(true)
                        .setRequest(true)
                        .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.REQUEST_CONNECT_BEAT, ttPhoneConnectBeat));
    }

    private NetUdpThread.IUdpListener udpListener = new NetUdpThread.IUdpListener() {
        @Override
        public void onConnectStateMsg() {
            LogUtils.d("server call client connect ");
            if (!isUserHandlerConnect) {
                LogUtils.d("user not connect so return ");
                return;
            }
            isUdpHandlerConnect = true;
            //checkConnectBeat();
            isSleepDisconnect = false;
            mNettyClientManager.startReconnected(Constans.PORT, Constans.IP);
            mhandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mNetUdpThread.setReconnected(false);
                }
            }, 6000);
        }

        @Override
        public void onConnectSleep() {
         //   isSleepDisconnect = true;
            /*if(mNettyClientManager != null){
                mNettyClientManager.stop();
            }*/
        }

        @Override
        public void onMsg(final String msg) {
            /*mhandler.post(new Runnable() {
                @Override
                public void run() {
                    NToast.longToast(mContext.getApplicationContext(),"udp =======" + msg);
                }
            });*/
        }
    };

    /**
     * 释放udp
     */
    private void releaseUdpConnect() {
        if (mNetUdpThread != null && mNetUdpThread.isAlive()) {
            mNetUdpThread.interrupt();
        }
        mNetUdpThread = null;
    }

    /**
     * 开始连接
     */
    public void connect(int port, String ip) {
        isUserHandlerConnect = true;
        this.ip = ip;
        this.port = port;
        mNettyClientManager.startConnect(port, ip);
    }

    /**
     * 断开连接
     */
    public void stop() {
        isUserHandlerConnect = false;
        mNettyClientManager.stop();
    }

    /*
     * 拨打电话
     *
     * @param phoneNumber
     */
    public void dialPhone(String phoneNumber) {
        KLog.i("tell Phone phoneNumber ... ");
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhoneNumber(phoneNumber)
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.CALL)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }

    /**
     * 查询当前电话状态
     */
    public void selectPhoneState(){
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.UNRECOGNIZED)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }

    /**
     * 挂断电话
     */
    public void endCall() {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.HUANGUP)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }

    /**
     * 接听电话接口
     */
    public void acceptCall() {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.ACCEPTCALL)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }

    /**
     * 发送连接状态
     */
    private void setConnectedState() {
        boolean isconnected = mNettyClientManager.isConnected();
        CommonData.getInstance().setConnected(isconnected);
        sendConnectedState(isconnected);
    }


    /**
     * 返回连接状态到应用层
     *
     * @param isconnected
     */
    private void sendConnectedState(boolean isconnected) {
        if (mCmdHandler.getmAllDataListener() != null) {
            mCmdHandler.getmAllDataListener().isTtServerConnected(isconnected);
        }
    }

    /**
     * 发送短
     * @param object
     */
    public void sendSms(Object object) {
        SmsBean smsBean = (SmsBean) object;
        TtPhoneSmsProtos.TtPhoneSms ttPhoneSms = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setIsSend(true)
                .setPhoneNumber(smsBean.getPhoneNumber())
                .setMessageText(smsBean.getMsg())
                .setIsReceiverSuccess(false)
                .setIsSendSuccess(false)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.phone_send_sms, ttPhoneSms));
    }

    /**
     * 请求gps准确位置
     */
    public void requestGpsPosition(Object o) {
        TtBeidouOpenBean ttBeidouOpenBean = (TtBeidouOpenBean) o;
        TtPhonePositionProtos.TtPhonePosition ttPhonePosition = TtPhonePositionProtos.TtPhonePosition.newBuilder()
                .setIsOpen(ttBeidouOpenBean.isSwitch())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_gps_position, ttPhonePosition));
    }

    /**
     * 打开usb升级
     */
    public void openBeidou(Object o) {
        TtBeidouOpenBean ttBeidouOpenBean = (TtBeidouOpenBean) o;
        TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou = TtOpenBeiDouProtos.TtOpenBeiDou.newBuilder()
                .setIsOpen(ttBeidouOpenBean.isSwitch())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_open_beidou_usb, ttOpenBeiDou));
    }

    /**
     * 请求设备通话记录
     */
    public void requestCallRecord() {
        TtCallRecordProtos.TtCallRecordProto ttCallRecordProto = TtCallRecordProtos.TtCallRecordProto.newBuilder()
                .setRequest(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_call_record, ttCallRecordProto));
    }

    /**
     * 请求设备短信记录
     */
    public void requestShortMessage() {
        TtShortMessageProtos.TtShortMessage ttShortMessage = TtShortMessageProtos.TtShortMessage.newBuilder()
                .setRequest(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_short_message, ttShortMessage));
    }

    /**
     * 请求设备服务APP是否需要更新
     */
    public void requestServerVersion(Object o) {
        AppInfoModel appInfoModel = (AppInfoModel) o;
        TtPhoneUpdateAppInfoProtos.UpdateAppInfo updateAppInfo = TtPhoneUpdateAppInfoProtos.UpdateAppInfo.newBuilder()
                .setPhoneAppVersion(appInfoModel.getPhoneAppVersion())
                .setServerAppVersion(appInfoModel.getServerAppVersion())
                .setIp(appInfoModel.getIp())
                .setTiantongUpdateMd(appInfoModel.getTiantongUpdateMd())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_update_phone_aapinfo, updateAppInfo));
    }

    /**
     * 发送当前时间至服务器
     */
    public void requestServerDatetime(Object o) {
        DatetimeModel datetimeModel = (DatetimeModel) o;
        TtTimeProtos.TtTime ttTime = TtTimeProtos.TtTime.newBuilder()
                .setDateTime(datetimeModel.getDateTime())
                .setIsSync(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_tt_time, ttTime));
    }

    /**
     * 发送至服务器修改短信读的状态
     */
    public void requestServerShortMessageStatus(Object o) {
        SMAgrementModel smAgrementModel = (SMAgrementModel) o;
        TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage = TtShortMessageProtos.TtShortMessage.ShortMessage.newBuilder()
                .setIsRead(true)
                .setId(smAgrementModel.getId())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_phone_send_sms_read, shortMessage));
    }

    /**
     * 发送至服务器修改wifi密码
     */
    public void requestServerWifipassword(Object o) {
        WifiSettingModel wifiSettingModel = (WifiSettingModel) o;
        TtPhoneWifiProtos.TtWifi ttWifi = TtPhoneWifiProtos.TtWifi.newBuilder()
                .setPasswd(wifiSettingModel.getWifiPassword())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_phone_set_wifi_passwd, ttWifi));
    }

    /**
     * 开始链接下载
     */
    public void startUpload() {
        try {
            InputStream inputStream = mContext.getAssets().open("tiantong_update.zip");
            File file = new File("/mnt/sdcard/tiantong_update.zip");
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] read = new byte[1024 * 1024];
            int len = 0;
            while ((len = inputStream.read(read)) != -1) {
                outputStream.write(read, 0, len);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final FtpClienManager mFtpClienManager = new FtpClienManager();
        mFtpClienManager.ftpConnet(new FtpClienManager.IConnectListener() {
            @Override
            public void onConnected(boolean isConnect) {
                if (isConnect) {
                    upload(mFtpClienManager);
                }
            }
        });
    }

    public void upload(FtpClienManager mFtpClienManager) {
        mFtpClienManager.upload("/mnt/sdcard/tiantong_update.zip", new FTPDataTransferListener() {
            @Override
            public void started() {
                LogUtils.e("-----------------started");
                mCmdHandler.getmAllDataListener();
            }

            @Override
            public void transferred(int i) {
                //                   LogUtils.d("-----------------transferred");
                KLog.i("view download progress = " + i);
            }

            @Override
            public void completed() {
                LogUtils.e("-----------------completed");
                //发送命令告诉服务端开始上传文件完成
                sendZipFile(true, new byte[1]);
            }

            @Override
            public void aborted() {
                LogUtils.e("-----------------aborted");
            }

            @Override
            public void failed() {
                LogUtils.e("-----------------failed");
            }
        });
    }

    public void sendZipFile(boolean isFinish, byte[] data) {
        TtPhoneUpdateSendFileProtos.UpdateSendFile.PFile pFile = TtPhoneUpdateSendFileProtos.UpdateSendFile.PFile.newBuilder()
                .setFilename("tiantong_update.zip")
                .setData(ByteString.copyFrom(data))
                .build();
        TtPhoneUpdateSendFileProtos.UpdateSendFile updateSendFile = TtPhoneUpdateSendFileProtos.UpdateSendFile.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setIsSendFileFinish(isFinish)
                .setFileData(pFile)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_update_send_zip, updateSendFile));
    }

    /**
     * 请求服务端打开设备移动数据
     */
    public void requestEnableData(Object o) {
        EnableDataModel dataModel = (EnableDataModel) o;
        TtPhoneMobileDataProtos.TtPhoneMobileData mobileData = TtPhoneMobileDataProtos.TtPhoneMobileData.newBuilder()
                .setIsEnableData(dataModel.isEnableData())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_phone_server_enable_data, mobileData));
    }

    /**
     * 请求服务端版本号
     */
    public void requestServerVersion() {
        if (!(CommonData.getInstance().getLocalWifiIp() == null)) {
            TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion ttPhoneGetServerVersion = TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.newBuilder()
                    .setIsRequest(true)
                    .setIp(CommonData.getInstance().getLocalWifiIp())
                    .build();
            sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_version_info, ttPhoneGetServerVersion));
        }
    }

    /**
     * 设备服务端sos设置保存
     */
    public void requestSosSendMessage(Object o) {
        SosSendMessageModel sosSendMessageModel = (SosSendMessageModel) o;
        TtPhoneSosMessageProtos.TtPhoneSosMessage ttPhoneSosMessage = TtPhoneSosMessageProtos.TtPhoneSosMessage.newBuilder()
                .setMessageContent(sosSendMessageModel.getMessageContent())
                .setPhoneNumber(sosSendMessageModel.getPhoneNumber())
                .setDelaytime(sosSendMessageModel.getDelaytime())
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_sos_message_send, ttPhoneSosMessage));
    }

    /**
     * 获取SOS信息值
     */
    public void requesSosMessageValue() {
        TtPhoneSosMessageProtos.TtPhoneSosMessage ttPhoneSosMessage = TtPhoneSosMessageProtos.TtPhoneSosMessage.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_sos_info_msg, ttPhoneSosMessage));
    }

    /**
     * 恢复设备出厂设置
     */
    public void requestServerRecoverSystem() {
        TtPhoneRecoverSystemProtos.TtPhoneRecoverSystem recoverSystem = TtPhoneRecoverSystemProtos.TtPhoneRecoverSystem.newBuilder()
                .setIsRecover(true)
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_recover_system, recoverSystem));
    }

    /**
     * 获取设备移动数据状态
     */
    public void reuqestServerMobileStatus() {
        TtPhoneMobileDataProtos.TtPhoneMobileData mobileData = TtPhoneMobileDataProtos.TtPhoneMobileData.newBuilder()
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_phone_server_mobile_init, mobileData));
    }

    /**
     * 获取设备SOS初始状态
     */
    public void requestServerSosStatus() {
        TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState = TtPhoneSosStateProtos.TtPhoneSosState.newBuilder()
                .setIsRequest(true)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_sos_status, ttPhoneSosState));
    }

    /**
     * 关闭服务设备SOS
     */
    public void requestServerSosSwitch(boolean isOpen) {
        TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState = TtPhoneSosStateProtos.TtPhoneSosState.newBuilder()
                .setIsRequest(true)
                .setIsSwitch(isOpen)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_sos_close, ttPhoneSosState));
    }

    /**
     * 请求服务删除该号码下通话记录
     */
    public void requestServerDeleteMessage(Object o) {
        ProtobufMessageModel messageModel = (ProtobufMessageModel) o;
        if (messageModel.isDelete()) {
            TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                    .setIsDeleAll(messageModel.isDelete())
                    .setIp(CommonData.getInstance().getLocalWifiIp())
                    .build();
            sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_del_calllog, ttDeleCallLog));
        } else {
            TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                    .setIsDeleAll(messageModel.isDelete())
                    .setIp(CommonData.getInstance().getLocalWifiIp())
                    .setPhonenumber(messageModel.getPhoneNumber())
                    .setServerDataId(messageModel.getServerId())
                    .build();
            sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_del_calllog, ttDeleCallLog));
        }
    }

    /**
     * 请求服务删除该短信息下的记录
     */
    public void requestServerShortMessageDelete(Object o) {
        ProtobufMessageModel messageModel = (ProtobufMessageModel) o;
        if (messageModel.isDelete()) {
            TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                    .setIp(CommonData.getInstance().getLocalWifiIp())
                    .setIsDeleAll(messageModel.isDelete())
                    .build();
            sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_del_sms, ttDeleCallLog));
        } else {
            TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                    .setIp(CommonData.getInstance().getLocalWifiIp())
                    .setIsDeleAll(messageModel.isDelete())
                    .setPhonenumber(messageModel.getPhoneNumber())
                    .build();
            sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_del_sms, ttDeleCallLog));
        }
    }

    /**
     * 将服务端的系统数据库修改未接状态
     *
     * @param o
     */
    public void requestServerPhoneStatus(Object o) {
        TtCallRecordProtos.TtCallRecordProto ttCallRecordProto = (TtCallRecordProtos.TtCallRecordProto) o;
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.request_server_call_status, ttCallRecordProto));
    }

    private NettyClientManager.INettyListener nettyListener = new NettyClientManager.INettyListener() {
        @SuppressLint("CheckResult")
        @Override
        public void onReceiveData(final ByteBufInputStream inputStream) {
            if (mCmdHandler != null) {
               /* Flowable.create(new FlowableOnSubscribe<ByteBufInputStream>() {
                    @Override
                    public void subscribe(FlowableEmitter<ByteBufInputStream> flowableEmitter) throws Exception {
                        flowableEmitter.onNext(inputStream);
                    }
                }, BackpressureStrategy.ERROR)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Consumer<ByteBufInputStream>() {
                            @Override
                            public void accept(ByteBufInputStream byteBufInputStream) throws Exception {

                            }
                        });*/
                mCmdHandler.handlerCmd(inputStream);
            }
        }

        @Override
        public void onConnected() {
            LogUtils.i("netty connected ...");

            if (isUdpHandlerConnect) {
                isUdpHandlerConnect = false;
            }

            if (isNettyException) {
                isNettyException = false;
            }

            if(isSleepDisconnect){
                isSleepDisconnect = false;
            }

            setConnectedState();
        }

        @Override
        public void onDisconnected() {
            LogUtils.i("netty disconnected ...");
            if (isUserHandlerConnect && isUdpHandlerConnect) {
                LogUtils.i("netty disconnected is wakeup ");
                return;
            }

            if(isUserHandlerConnect && isSleepDisconnect){
                LogUtils.i("netty disconnected is sleep ");
                isSleepDisconnect = false;
                return;
            }

            if (isUserHandlerConnect && isNettyException) {
                LogUtils.i("netty disconnected is exception ");
                return;
            }

            sendConnectedState(false);
            if (mCmdHandler != null) {
                mCmdHandler.resetPhoneState();
            }
            setConnectedState();
        }

        @Override
        public void onException(ChannelHandlerContext ctx) {
            LogUtils.i("netty onException...");
            if(isUserHandlerConnect || isUdpHandlerConnect){
                LogUtils.i("netty onException..isUserHandlerConnect or isUdpHandlerConnect.");
                return;
            }
           if(!isNettyException){
               isNettyException = true;
                mNettyClientManager.stop();
                mNettyClientManager.startReconnected(Constans.PORT, Constans.IP);
           }
        }

    };

    /**
     * 发送命令到设备
     *
     * @param cmd
     */
    public void sendPhoneCmd(PhoneCmd cmd) {
        if (mNettyClientManager != null) {
            mNettyClientManager.sendData(cmd);
        }
    }

    /**
     * 释放
     */
    public void free() {
        if (mNettyClientManager != null) {
            mNettyClientManager.free();
        }
        CommonData.getInstance().free();
        mCmdHandler.release();
        releaseUdpConnect();
    }


    public CmdHandler getmCmdHandler() {
        return mCmdHandler;
    }


}
