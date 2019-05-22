package com.tt.qzy.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PhoneStateUtils;

import com.qzy.ring.RingManager;
import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.tt.phone.data.impl.ITtPhoneCallStateBackListener;
import com.qzy.tt.phone.data.impl.ITtPhoneCallStateLisenter;
import com.qzy.utils.LogUtils;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.presenter.activity.TellPhoneActivityPresenter;
import com.tt.qzy.view.presenter.manager.SyncManager;
import com.tt.qzy.view.receiver.OverallReceiver;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.SPUtils;


import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class TellPhoneIncomingActivity extends AppCompatActivity {

    @BindView(R.id.txtv_phoneNumber)
    TextView txtv_phoneNumber;

    @BindView(R.id.btn_accept)
    ImageView btn_accept;

    @BindView(R.id.btn_endcall)
    ImageView btn_endcall;

    private String phoneNumber = "";

    private TellPhoneActivityPresenter mTellPhoneActivityPresenter;

    //问题一：当挂断多次出现的时候

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_tell_phone_incoming);
        registerReceiver();
        RingManager.playDefaultCallMediaPlayer(getApplication());
        ButterKnife.bind(this);
        phoneNumber = getIntent().getStringExtra("diapadNumber");
        txtv_phoneNumber.setText(phoneNumber);
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() >= 3) {
        }
        mTellPhoneActivityPresenter = new TellPhoneActivityPresenter(this);

        setTtPhoneCallState();
        setTtPhoneCallStateBackListener();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(OverallReceiver.CLEAR_TELL_PHONE_ACTIVITY);
        registerReceiver(broadcastReceiver,intentFilter);
    }

    private void unregisterReceiver() {
        unregisterReceiver(broadcastReceiver);
    }

    @OnClick({R.id.btn_accept, R.id.btn_endcall})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:

                isAlert = false;

                onCallingState();

                String name = getPhoneKeyForName(phoneNumber);

                if (null != name && name.length() > 0) {
                    CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, name, "", "1", DateUtil.backTimeFomat(new Date()), 20);

                    CallRecordManager.getInstance(this).insertCallRecord(callRecordDao, this);

                } else {
                    CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, "", "", "1", DateUtil.backTimeFomat(new Date()), 20);

                    CallRecordManager.getInstance(this).insertCallRecord(callRecordDao, this);
                }
                break;
            case R.id.btn_endcall:

                mTellPhoneActivityPresenter.endCall();

                onEndCallState();

                String phoneName = getPhoneKeyForName(phoneNumber);

                if (null != phoneName && phoneName.length() > 0) {
                    CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, phoneName, "", "3", DateUtil.backTimeFomat(new Date()), 20);

                    CallRecordManager.getInstance(this).insertCallRecord(callRecordDao, this);

                } else {

                    CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, "", "", "3", DateUtil.backTimeFomat(new Date()), 20);

                    CallRecordManager.getInstance(this).insertCallRecord(callRecordDao, this);
                }
                break;
        }
    }

    public String getPhoneKeyForName(String phone) {
        List<MailListDao> listModels = MailListManager.getInstance(this).getByPhoneList(phone);
        String name;
        if (listModels.size() > 0) {
            name = listModels.get(0).getName();
        } else {
            name = "";
        }
        return name;
    }

    /**
     * 设置电话设备占用回调
     */
    private void setTtPhoneCallStateBackListener() {
        TtPhoneDataManager.getInstance().setITtPhoneCallStateBackListener("TellPhoneIncomingActivity", new ITtPhoneCallStateBackListener() {
            @Override
            public void onPhoneCallStateBack(PhoneCmd phoneCmd) {
                onTianTongCallStatus(phoneCmd);
            }
        });
    }


    /**
     * 设置电话通话状态
     */
    private void setTtPhoneCallState() {
        TtPhoneDataManager.getInstance().setTtPhoneCallStateLisenter("TellPhoneIncomingActivity", new ITtPhoneCallStateLisenter() {
            @Override
            public void onTtPhoneCallState(PhoneCmd phoneCmd) {
                updatePhoneState(phoneCmd);
            }
        });

    }

    public void onTianTongCallStatus(Object o) {
        PhoneCmd cmd = (PhoneCmd) o;
        CallPhoneBackProtos.CallPhoneBack callPhoneBack = (CallPhoneBackProtos.CallPhoneBack) cmd.getMessage();
        KLog.i("tt_call_status is = " + callPhoneBack.getIsCalling());
        if (callPhoneBack.getIsCalling()) {
            if (callPhoneBack.getIp().equals(CommonData.getInstance().getLocalWifiIp())) {

            } else {
                KLog.i("电话状态产生finsh");
                finish();
            }
        } else {

        }
    }

    /**
     * 通话状态
     */
    private void onCallingState() {
        Intent intent = new Intent(TellPhoneIncomingActivity.this, TellPhoneActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("diapadNumber", phoneNumber);
        intent.putExtra("acceptCall", true);
        startActivity(intent);
        RingManager.stopDefaultCallMediaPlayer(getApplication());
        mTellPhoneActivityPresenter.acceptCall();
        KLog.i("通话产生finsh");
        finish();
    }

    /**
     * 挂断状态
     */
    private void onEndCallState() {
        RingManager.stopDefaultCallMediaPlayer(getApplication());
        KLog.i("挂断产生finsh");
        finish();
    }

    /**
     * 更新天通电话状态
     *
     * @param cmd
     */
    private void updatePhoneState(PhoneCmd cmd) {
        KLog.i("phone state = " + PhoneStateUtils.getTtPhoneState(cmd).ordinal());
        switch (PhoneStateUtils.getTtPhoneState(cmd)) {
            case NOCALL:
                onEndCallState();
                break;
            case RING:
                break;
            case CALL:
                if(!CommonData.getInstance().isCallingIp(PhoneStateUtils.getTtPhoneStateNowCallingIp(cmd))){
                    KLog.d("is not me calling  = ");
                    break;
                }
                //onCallingState();
                break;
            case HUANGUP:
                KLog.i("产生挂断");

                disposeAlert();

                String phoneName = getPhoneKeyForName(phoneNumber);

                if (null != phoneName && phoneName.length() > 0) {
                    CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, phoneName, "", "3", DateUtil.backTimeFomat(new Date()), 20);

                    CallRecordManager.getInstance(this).insertCallRecord(callRecordDao, this);

                } else {

                    CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, "", "", "3", DateUtil.backTimeFomat(new Date()), 20);

                    CallRecordManager.getInstance(this).insertCallRecord(callRecordDao, this);
                }

                onEndCallState();

                break;
            case INCOMING:
                break;
            case UNRECOGNIZED:
                onEndCallState();
                break;
        }
    }

    private volatile boolean isAlert = true;

    private void disposeAlert() {
        KLog.i("dispose Alerter ");
        synchronized (this){
            if (isAlert ) {
                Integer recordCount = (Integer) SPUtils.getShare(this, Constans.RECORD_ISREAD, 0);
                recordCount = recordCount + 1;
                SPUtils.putShare(this, Constans.RECORD_ISREAD, recordCount);

                Flowable.just(recordCount)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                TtPhoneDataManager.getInstance().getISyncDataListener("MainActivityPresenter").onCallingLogSyncFinish(Integer.valueOf(integer.intValue()));
                            }
                        });
            }
            isAlert = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        RingManager.stopDefaultCallMediaPlayer(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(OverallReceiver.CLEAR_TELL_PHONE_ACTIVITY)){
                KLog.i("wifi波动finsh");
                finish();
            }
        }
    };
}
