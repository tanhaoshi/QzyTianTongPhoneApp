package com.qzy.tiantong.service.service;

import com.qzy.tiantong.service.phone.QzyPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public interface ITianTongServer {

    QzyPhoneManager getQzyPhoneManager();

    /**  初始化天通资源  **/
   /* void initLocalPcmDevice();
    void startRecorder();
    void startPlayer();
    void closeRecorderAndPlayer();*/

     void onPhoneStateChange(TtPhoneState state);

    void initTtPcmDevice();
    void freeTtPcmDevice();


}
