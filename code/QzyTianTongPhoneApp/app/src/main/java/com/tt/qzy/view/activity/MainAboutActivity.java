package com.tt.qzy.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.TtPhoneGetServerVersionProtos;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.tt.qzy.view.R;
import com.tt.qzy.view.bean.DatetimeModel;
import com.tt.qzy.view.utils.APKVersionCodeUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;


import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainAboutActivity extends AppCompatActivity {

    @BindView(R.id.about_version)
    TextView about_version;
    @BindView(R.id.about_soft_version)
    TextView about_soft_version;
    @BindView(R.id.about_fixed_version)
    TextView about_fixed_version;
    @BindView(R.id.about_number)
    TextView about_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_about);
        ButterKnife.bind(this);
        //EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initView() {
        about_version.setText(String.valueOf(APKVersionCodeUtils.getVerName(this)));
    }

    private void initData(){
       // EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_VERSION));
        TtPhoneDataManager.getInstance().requestServerTtPhoneVersion();
    }

    @OnClick({R.id.main_quantity})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_quantity:
                finish();
                break;
        }
    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_SERVER_VERSION:
                parseServerVersion(event.getObject());
                break;
        }
    }*/

    private void parseServerVersion(Object o){
        PhoneCmd cmd = (PhoneCmd)o;
        TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion getServerVersion = (TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion)
                cmd.getMessage();
        about_soft_version.setText(getServerVersion.getServerApkVersionName());
        about_number.setText(getServerVersion.getServerSieralNo());
    }

    @Override
    protected void onStop() {
        super.onStop();
       // EventBus.getDefault().unregister(this);
    }
}
