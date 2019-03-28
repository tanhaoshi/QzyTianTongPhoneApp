package com.tt.qzy.view.activity;

import android.content.Intent;
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
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.db.dao.CallRecordDao;
import com.tt.qzy.view.db.dao.MailListDao;
import com.tt.qzy.view.db.manager.CallRecordManager;
import com.tt.qzy.view.db.manager.MailListManager;
import com.tt.qzy.view.presenter.activity.TellPhoneActivityPresenter;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.DateUtil;
import com.tt.qzy.view.utils.SPUtils;


import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TellPhoneIncomingActivity extends AppCompatActivity {

    @BindView(R.id.txtv_phoneNumber)
    TextView txtv_phoneNumber;

    @BindView(R.id.btn_accept)
    ImageView btn_accept;

    @BindView(R.id.btn_endcall)
    ImageView btn_endcall;

    private String phoneNumber = "";

    private TellPhoneActivityPresenter mTellPhoneActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_tell_phone_incoming);
        RingManager.playDefaultCallMediaPlayer(TtPhoneApplication.getInstance());
        ButterKnife.bind(this);
        phoneNumber = getIntent().getStringExtra("diapadNumber");
        txtv_phoneNumber.setText(phoneNumber);
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() >= 3) {
        }
        mTellPhoneActivityPresenter = new TellPhoneActivityPresenter(this);
        //  EventBusUtils.register(this);
        setTtPhoneCallState();
        setTtPhoneCallStateBackListener();
    }

    @OnClick({R.id.btn_accept, R.id.btn_endcall})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
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

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_STATE:
                updatePhoneState((PhoneCmd) event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_RESPONSE_CALL_STATE:
                onTianTongCallStatus(event.getObject());
                break;
        }
    }*/


    /**
     * 设置电话设备占用回调
     */
    private void setTtPhoneCallStateBackListener() {
        TtPhoneDataManager.getInstance().setITtPhoneCallStateBackListener("BaseActivity", new ITtPhoneCallStateBackListener() {
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
                finish();
            }
        } else {
            finish();
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
        RingManager.stopDefaultCallMediaPlayer(TtPhoneApplication.getInstance());
        mTellPhoneActivityPresenter.acceptCall();
        finish();
    }

    /**
     * 挂断状态
     */
    private void onEndCallState() {
        RingManager.stopDefaultCallMediaPlayer(TtPhoneApplication.getInstance());
        mTellPhoneActivityPresenter.endCall();
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
                onCallingState();
                break;
            case HUANGUP:

                onEndCallState();

                disposeAlert();

                String phoneName = getPhoneKeyForName(phoneNumber);

                if (null != phoneName && phoneName.length() > 0) {
                    CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, phoneName, "", "3", DateUtil.backTimeFomat(new Date()), 20);

                    CallRecordManager.getInstance(this).insertCallRecord(callRecordDao, this);

                } else {

                    CallRecordDao callRecordDao = new CallRecordDao(phoneNumber, "", "", "3", DateUtil.backTimeFomat(new Date()), 20);

                    CallRecordManager.getInstance(this).insertCallRecord(callRecordDao, this);
                }
                break;
            case INCOMING:
                break;
            case UNRECOGNIZED:
                onEndCallState();
                break;
        }
    }

    private boolean isAlert = true;

    private synchronized void disposeAlert() {
        if (isAlert) {
            Integer recordCount = (Integer) SPUtils.getShare(this, Constans.RECORD_ISREAD, 0);
            recordCount = recordCount + 1;
            SPUtils.putShare(this, Constans.RECORD_ISREAD, recordCount);

          /*  EventBus.getDefault().post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_LOCAL_RECORD_CALL_HISTROY
                    ,Integer.valueOf(recordCount)));*/
            isAlert = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        RingManager.stopDefaultCallMediaPlayer(TtPhoneApplication.getInstance());
        // EventBusUtils.unregister(this);
        isAlert = true;
    }
}
