package com.qzy.tt.phone.data.impl;

import com.qzy.data.PhoneCmd;

public interface ITtPhoneCallStateLisenter {
    //电话状态回调
    void onTtPhoneCallState(PhoneCmd phoneCmd);
}
