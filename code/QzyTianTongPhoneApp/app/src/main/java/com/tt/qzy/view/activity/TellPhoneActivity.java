package com.tt.qzy.view.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.qzy.QzySensorManager;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PhoneStateUtils;

import com.qzy.phone.pcm.AllLocalPcmManager;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.tt.phone.data.impl.ITtPhoneCallStateLisenter;
import com.qzy.utils.AndroidVoiceManager;
import com.qzy.utils.LogUtils;
import com.qzy.utils.TimeToolUtils;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.application.TtPhoneApplication;
import com.tt.qzy.view.layout.dialpad.InputPwdViewCall;
import com.tt.qzy.view.presenter.activity.TellPhoneActivityPresenter;
import com.tt.qzy.view.utils.AnswerBellManager;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.SPUtils;


import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TellPhoneActivity extends AppCompatActivity {

    public static final int msg_calling_time = 1;
    public static final int msg_calling_time_remove = 2;

    //根据是否拨通重复播,或当用户手动关闭当前activity
    private boolean isFinsh = true;

    @BindView(R.id.phoneNumber)
    TextView phoneNumber;

    @BindView(R.id.text_state)
    TextView text_state;
    @BindView(R.id.input_call)
    InputPwdViewCall input_call;
    @BindView(R.id.aidlName)
    TextView aidlName;

    private TellPhoneActivityPresenter mTellPhoneActivityPresenter;

    private QzySensorManager mQzySensorManager;
    private AnswerBellManager mAnswerBellManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_tell_phone);

        mQzySensorManager = new QzySensorManager(TtPhoneApplication.getInstance());
        ButterKnife.bind(this);
        String number = getIntent().getStringExtra("diapadNumber");
        phoneNumber.setText(number);
        if (!TextUtils.isEmpty(number) && number.length() >= 3) {
        }
        mTellPhoneActivityPresenter = new TellPhoneActivityPresenter(this);
        if (mTellPhoneActivityPresenter.getPhoneKeyForName(number) != null && mTellPhoneActivityPresenter.getPhoneKeyForName(number).length() > 0) {
            aidlName.setText(mTellPhoneActivityPresenter.getPhoneKeyForName(number));
        }
        //EventBusUtils.register(TellPhoneActivity.this);

        input_call.setListener(new InputPwdViewCall.InputPwdListener() {
            @Override
            public void inputString(String diapadNumber) {

                mTellPhoneActivityPresenter.endCall();
                //挂断
                onEndCallState();
            }

            @Override
            public void buttonClick(int keyCode) {
            }
        });

        countTime();

        AndroidVoiceManager.setVoiceCall(this);

        boolean isFlag = getIntent().getBooleanExtra("acceptCall", false);

        if (isFlag) {
            text_state.setText("正在接听");
            onCallingState();
        } else {
            mAnswerBellManager = new AnswerBellManager(TtPhoneApplication.getInstance());
        }

        setTtPhoneCallState();
    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_STATE:
                updatePhoneState((PhoneCmd) event.getObject());
                break;
        }
    }*/


    /**
     * 设置电话通话状态
     */
    private void setTtPhoneCallState() {
        TtPhoneDataManager.getInstance().setTtPhoneCallStateLisenter("TellPhoneActivity", new ITtPhoneCallStateLisenter() {
            @Override
            public void onTtPhoneCallState(PhoneCmd phoneCmd) {
                    updatePhoneState(phoneCmd);
            }
        });

    }

    /**
     * 刷新通话时长
     */
    private void showCallingTime(int time) {
        text_state.setText(TimeToolUtils.getFormatHMS(time));
    }

    /**
     * 展示拨打中
     */
    private void showDailing() {
        text_state.setText(getString(R.string.TMT_dial_dialing));
    }

    /**
     * 挂断中
     */
    private void endCallDailing() {
        text_state.setText(getString(R.string.TMT_dial_endcall));
    }

    private WorkHandler mHandler = new WorkHandler(this);

    static class WorkHandler extends Handler {

        private WeakReference<TellPhoneActivity> mWeakReference;
        private int time = 0;

        public WorkHandler(TellPhoneActivity tellPhoneActivity) {
            mWeakReference = new WeakReference<TellPhoneActivity>(tellPhoneActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TellPhoneActivity activity = mWeakReference.get();
            if (null != activity) {
                switch (msg.what) {
                    case msg_calling_time:
                        time += 1000;
                        activity.showCallingTime(time);
                        sendEmptyMessageDelayed(1, 1000);
                        break;
                    case msg_calling_time_remove:
                        if (hasMessages(1)) {
                            removeMessages(1);
                        }
                        time = 0;
                        activity.endCallDailing();
                        break;
                }
            }
        }
    }

    long startTime = 0;
    int count = 5;

    private void countTime() {
        startTime = System.currentTimeMillis();
    }

    /**
     * 通话状态
     */
    private void onCallingState() {
        mHandler.sendEmptyMessage(msg_calling_time);
    }

    /**
     * 挂断状态
     */
    private void onEndCallState() {
        if(mHandler != null) {
            mHandler.sendEmptyMessage(msg_calling_time_remove);
        }
        isFinsh = false;
        finish();
    }


    /**
     * 底层打电话接口
     *
     * @param phoneMumber
     */
    private void dialPhoneToServer(String phoneMumber) {
        TtPhoneDataManager.getInstance().dialTtPhone(phoneMumber);
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
                if (isFinsh) {
                    long timeDuration1 = System.currentTimeMillis() - startTime;
                    LogUtils.e("timeDuration1 = " + timeDuration1 + " count = " + count);
                    if (timeDuration1 < 5 * 1000 && count > 0) {
                        //EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL,phoneNumber.getText().toString()));

                        dialPhoneToServer(phoneNumber.getText().toString().trim());
                        count--;
                        countTime();
                        break;
                    }
                }
                if(mAnswerBellManager != null){
                    mAnswerBellManager.stopPlay();
                }
                onEndCallState();
                break;
            case RING:
                if(mAnswerBellManager != null){
                    mAnswerBellManager.stopPlay();
                }
                break;
            case CALL:
                if(!CommonData.getInstance().isCallingIp(PhoneStateUtils.getTtPhoneStateNowCallingIp(cmd))){
                    KLog.d("is not me calling  = ");
                    break;
                }
                if(mAnswerBellManager != null){
                    mAnswerBellManager.stopPlay();
                }
                if (isFinsh) {
                    long timeDuration = System.currentTimeMillis() - startTime;
                    if (timeDuration < 5 * 1000) {
                        break;
                    }
                }
                onCallingState();
                break;
            case HUANGUP:
                if (isFinsh) {
                    long timeDuration2 = System.currentTimeMillis() - startTime;
                    LogUtils.e("timeDuration2 = " + timeDuration2 + " count = " + count);
                    if (timeDuration2 < 5 * 1000 && count > 0) {
                        //  EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL,phoneNumber.getText().toString()));
                        dialPhoneToServer(phoneNumber.getText().toString().trim());
                        count--;
                        countTime();
                        break;
                    }
                }
                if(mAnswerBellManager != null){
                    mAnswerBellManager.stopPlay();
                }
                onEndCallState();
//                SPUtils.removeShare(TellPhoneActivity.this, Constans.SOS_FLAG);
                break;
            case INCOMING:
                break;
            case UNRECOGNIZED:
                if(mAnswerBellManager != null){
                    mAnswerBellManager.stopPlay();
                }
                onEndCallState();
                SPUtils.removeShare(TellPhoneActivity.this, Constans.SOS_FLAG);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            NToast.shortToast(TellPhoneActivity.this, "请点击挂断键,完成退出!");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isFinsh = false;
        mQzySensorManager.freeSenerState();
        // EventBusUtils.unregister(TellPhoneActivity.this);
        //finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler = null;
        if(mAnswerBellManager != null){
            mAnswerBellManager.release();
            mAnswerBellManager = null;
        }
        AndroidVoiceManager.setVoiceMusic(this);
    }
}
