package com.qzy.tt.phone.data.impl;

import com.qzy.data.PhoneCmd;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.SMAgrementModel;
import com.tt.qzy.view.bean.SosSendMessageModel;
import com.tt.qzy.view.bean.TtBeidouOpenBean;
import com.tt.qzy.view.bean.WifiSettingModel;

public interface ITtPhoneHandlerManager {

    //连接服务
    void connectTtPhoneServer(String ip,int port);

    //断开服务连接
    void disconnectTtPhoneServer();

    //请求服务sos 是否打开
    void getTtPhoneSosState();
    //关闭sos
    void closeTtPhoneSos();

    //打开gps
    void openTtPhoneGps(TtBeidouOpenBean ttBeidouOpenBean);
    //关闭gps
    void closeTtPhoneGps(TtBeidouOpenBean ttBeidouOpenBean);

    //设置sos信息
    void setTtPhoneSosValue(SosSendMessageModel sosSendMessageModel);

    //wifi密码设置
    void setWifiPasswd(WifiSettingModel wifiSettingModel);

    //设置时间
    void setDateAndTime(DatetimeModel datetimeModel);

    //恢复出厂设置
    void setResetFactorySettings();

    //打开usb升级天通模块模式
    void openUsbMode(TtBeidouOpenBean ttBeidouOpenBean);
    //关闭usb升级天通模块模式
    void closeUsbMode(TtBeidouOpenBean ttBeidouOpenBean);


    //拨打电话
    void dialTtPhone(String phoneNumber);

    //挂断电话
    void hangupTtPhone();

    //接听
    void answerTtPhone();


    //请求设备通话记录
    void requestCallRecord();

    //请求设备短信记录
    void requestShortMessage();

    //发送短信已读状态到服务器端
    void requestServerShortMessageStatus(SMAgrementModel smAgrementModel);


    /**
     * 请求服务端版本号
     */
     void requestServerTtPhoneVersion();





    //设置电话数据回调接口
    void setTtPhoneDataListener(ITtPhoneDataListener iTtPhoneDataListener);
    void removeTtPhoneDataListener();

    //设置通话状态回调接口
    void setTtPhoneCallStateLisenter(String tag,ITtPhoneCallStateLisenter lisenter);
    void removeTtPhoneCallStateLisenter(String tag);

    //设置电话设置占用回调
    void setITtPhoneCallStateBackListener(String tag,ITtPhoneCallStateBackListener lisenter);
    void removeITtPhoneCallStateBackListener(String tag);

    //设置关于服务端app 版本号
    void setIMainAboutListener(IMainAboutListener iMainAboutListener);
    void removeIMainAboutListener();
}
