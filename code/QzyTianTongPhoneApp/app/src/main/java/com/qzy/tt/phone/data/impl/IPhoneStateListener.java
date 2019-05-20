package com.qzy.tt.phone.data.impl;

import com.qzy.tt.data.CallPhoneStateProtos;

public interface IPhoneStateListener {

    void selectPhoneState(CallPhoneStateProtos.CallPhoneState.PhoneState phoneState);
}
