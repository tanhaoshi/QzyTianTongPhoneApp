package com.qzy.tiantong.service.update;

import com.qzy.tt.data.TtPhoneUpdateAppInfoProtos;
import com.qzy.tt.data.TtPhoneUpdateSendFileProtos;

public interface IUpdateManager {
    //检查是否需要升级
    void checkUpdate(TtPhoneUpdateAppInfoProtos.UpdateAppInfo updateAppInfo);

    //接受zip文件
    void receiverZipFile(TtPhoneUpdateSendFileProtos.UpdateSendFile updateSendFile);
}
