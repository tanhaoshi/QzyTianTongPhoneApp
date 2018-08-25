package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.eventbus.CallingModel;
import com.qzy.utils.ToastUtils;
import com.tt.qzy.view.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TellPhoneActivity extends AppCompatActivity {

    @BindView(R.id.phoneNumber)
    TextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_phone);
        ButterKnife.bind(this);
        String number = getIntent().getStringExtra("diapadNumber");
        phoneNumber.setText(number);
        if (!TextUtils.isEmpty(number) && number.length() >= 3) {
            if (CommonData.isConnected) {
                //ContactHelper.makePhoneCall(number);
                callPhone(number);
            } else {
                ToastUtils.showToast(this, "未连接天通猫");
            }
        }
    }

    /**
     * 打电话
     *
     * @param phoneNumber
     */
    private void callPhone(String phoneNumber) {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.localWifiIp)
                .setPhoneNumber(phoneNumber)
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.CALL)
                .build();
        EventBus.getDefault().post(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));

//        if(showCallingView()){
//            setCallingPhoneNumber(phoneNumber);
//        }
        setCallingPhoneNumber(phoneNumber);
    }


//    private boolean isDCallingShowing() {
//        return mCallingFragment != null && mCallingFragment.isVisible();
//    }
//
//    public boolean showCallingView() {
//        if(!isDCallingShowing()){
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.show(mCallingFragment).commit();
//            return true;
//        }
//        return false;
//    }

    public void setCallingPhoneNumber(String phoneNumber){
        CallingModel callingModel = new CallingModel();
        callingModel.setPhone_number(phoneNumber);
        EventBus.getDefault().post(callingModel);
    }
}
