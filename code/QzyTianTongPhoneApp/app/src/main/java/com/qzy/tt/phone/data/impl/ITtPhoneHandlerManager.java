package com.qzy.tt.phone.data.impl;

import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.bean.SosSendMessageModel;
import com.tt.qzy.view.bean.WifiSettingModel;

public interface ITtPhoneHandlerManager {

    void setTtPhoneDataListener(ITtPhoneDataListener iTtPhoneDataListener);

    //连接服务
    void connectTtPhoneServer(String ip,int port);

    //打开sos
    void openTtPhoneSos();
    //关闭sos
    void closeTtPhoneSos();

    //打开gps
    void openTtPhoneGps();
    //关闭gps
    void closeTtPhoneGps();

    //设置sos信息
    void setTtPhoneSosValue(String phoneNumber,String text,SosSendMessageModel sosSendMessageModel);

    //wifi密码设置
    void setWifiPasswd(WifiSettingModel wifiSettingModel);

    //设置时间
    void setDateAndTime(DatetimeModel datetimeModel);

    //恢复出厂设置
    void setResetFactorySettings();

    //打开usb升级天通模块模式
    void openUsbMode();
    //关闭usb升级天通模块模式
    void closeUsbMode();


    //拨打电话
    void dialTtPhone();

    //挂断电话
    void hangupTtPhone();

    //接听
    void answerTtPhone();




}
