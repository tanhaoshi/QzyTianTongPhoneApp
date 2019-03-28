package com.qzy.tt.phone.data.impl;

import com.qzy.data.PhoneCmd;

/**
 * 主要返回 设备是否占用的消息
 */
public interface ITtPhoneCallStateBackListener {
    //设备通话占用回调
    void onPhoneCallStateBack(PhoneCmd phoneCmd);
}
