package com.tt.qzy.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.TtPhoneGetServerVersionProtos;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.utils.LogUtils;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.base.BaseActivity;
import com.tt.qzy.view.presenter.activity.MainAboutPresenter;
import com.tt.qzy.view.utils.APKVersionCodeUtils;
import com.tt.qzy.view.view.MainAboutView;


import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainAboutActivity extends BaseActivity<MainAboutView> implements MainAboutView{

    @BindView(R.id.about_version)
    TextView about_version;
    @BindView(R.id.about_soft_version)
    TextView about_soft_version;
    @BindView(R.id.about_fixed_version)
    TextView about_fixed_version;
    @BindView(R.id.about_number)
    TextView about_number;
    @BindView(R.id.module_version)
    TextView module_version;

    private boolean isConnect;
    private boolean isSim;
    private boolean isSignal;
    private int baterly;
    private int signalValue = 99;

    private MainAboutPresenter mAboutPresenter;

    private void getIntentData() {
        Intent intent = getIntent();
        if (null != intent.getExtras()) {
            isConnect = intent.getBooleanExtra("connect", false);
            isSim = intent.getBooleanExtra("isSim", false);
            isSignal = intent.getBooleanExtra("isSignal", false);
            baterly = intent.getIntExtra("baterly", 0);
            signalValue = intent.getIntExtra("signalValue",99);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main_about;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void initView() {
        loadData(true);
        statusLayout.setBackgroundColor(getResources().getColor(R.color.tab_stander));
        if (isConnect) {
            img5.setImageDrawable(getResources().getDrawable(R.drawable.search_network));
        } else {
            img5.setImageDrawable(getResources().getDrawable(R.drawable.search_nonetwork));
        }
        if (isSim) {
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_connect));
        } else {
            img2.setImageDrawable(getResources().getDrawable(R.drawable.sim_noconnect));
        }
        if (isSignal) {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_one));
        } else {
            img3.setImageDrawable(getResources().getDrawable(R.drawable.signal_noconnect));
        }
        percentBaterly.setText(baterly + "%");
        img1.setPower(baterly);
        signal.setText("- ("+String.valueOf(signalValue)+") dBm");
    }

    @Override
    public void initData(){
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

    private void parseServerVersion(Object o){
        PhoneCmd cmd = (PhoneCmd)o;
        TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion getServerVersion = (TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion)
                cmd.getMessage();
        LogUtils.i("look at module version = " + getServerVersion.getTiantongModelVersion());
        about_soft_version.setText(getServerVersion.getServerApkVersionName());
        about_number.setText(getServerVersion.getServerSieralNo());
        String str = getServerVersion.getTiantongModelVersion().trim().substring(0,getServerVersion.getTiantongModelVersion().trim().length()-4);
        LogUtils.i("string  = " +str);
        module_version.setText(str);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void getServerVersion(Object o) {
        parseServerVersion(o);
    }

    @Override
    public void showProgress(boolean isTrue) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String msg, boolean pullToRefresh) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getIntentData();
        about_version.setText(String.valueOf(APKVersionCodeUtils.getVerName(this)));
        mAboutPresenter = new MainAboutPresenter(MainAboutActivity.this);
        mAboutPresenter.onBindView(this);
    }
}
